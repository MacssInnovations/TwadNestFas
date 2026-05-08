package Servlets.FAS.FAS1.PaymentSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Cheque_Dishonour_System extends HttpServlet {
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
        ResultSet rs = null, rs2 = null, rs3 = null,rs4=null;
        //CallableStatement cs=null,cs1=null;
        PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null,ps4=null;
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
        Date txtDoc_date = null;
        Date received_date = null;
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String txtdoctype = request.getParameter("doc_type");

        int CashBookYear = 0;
        int CashBookMonth = 0;
      //  int txtCheque_DD_NO = 0;
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
        try {
            CashBookYear = Integer.parseInt(CashBook_Year);
            System.out.println("CashBook_Year After is:" + CashBookYear);
        } catch (Exception e) {
            System.out.println("Exception in Year:" + e);
            CashBookYear = 0;
        }
        try {
            CashBookMonth = Integer.parseInt(CashBook_Month);
            System.out.println("CashBook Month After is:" + CashBookMonth);
        } catch (Exception e) {
            System.out.println("Exception in Month:" + e);
            CashBookMonth = 0;
        }
        if (strCommand.equalsIgnoreCase("chequenodetails")){
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            xml = "<response><command>chequenodetails</command>"; 
            try{
          
        String cheqsql= "SELECT * FROM (select i.CHEQUE_DD_NO as cheqno from FAS_FUND_RECEIPT_BY_OFFICE i where i.CHEQUE_OR_DD='C' and\n" + 
                "(i.CHEQ_DISHONOUR_STATUS is null or i.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_UNIT_ID=? \n" + 
                "and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=?  and \n" + 
                "i.CASHBOOK_MONTH=? and  i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y'\n" + 
                "union\n" +
                "select i.CHEQUE_DD_NO as cheqno from FAS_FUND_RECEIPT_BY_HO i where i.CHEQUE_OR_DD='C' and\n" + 
                "(i.CHEQ_DISHONOUR_STATUS is null or i.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_UNIT_ID=? \n" + 
                "and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=?  and \n" + 
                "i.CASHBOOK_MONTH=? and  i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y' " +
                "union " +
                "select j.CHEQUE_DD_NO as cheqno from FAS_RECEIPT_MASTER i,FAS_RECEIPT_TRANSACTION j\n" + 
                "where i.RECEIPT_NO=j.RECEIPT_NO and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and \n" + 
                "(j.CHEQ_DISHONOUR_STATUS is null or j.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID \n" + 
                " and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH and \n" + 
                "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=? \n" + 
                " and i.CASHBOOK_MONTH=? and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.RECEIPT_TYPE='B'\n" + 
                "and i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y' ) as opt1 order by cheqno " ;
        
        
           System.out.println("chequenodetails--->"+cheqsql);
           
           
                ps=con.prepareStatement(cheqsql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, CashBookYear);
                ps.setInt(4, CashBookMonth);
                ps.setInt(5, cmbAcc_UnitCode);
                ps.setInt(6, cmbOffice_code);
                ps.setInt(7, CashBookYear);
                ps.setInt(8, CashBookMonth);
                ps.setInt(9, cmbAcc_UnitCode);
                ps.setInt(10, cmbOffice_code);
                ps.setInt(11, CashBookYear);
                ps.setInt(12, CashBookMonth);
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
             Calendar c;
             xml = "<response><command>Voucher_Details</command>";
             String txtCheque_No = "";


             try {
                 txtCheque_No = 
                         request.getParameter("txtCheque_No");
             } catch (NumberFormatException e) {
                 System.out.println("exception" + e);
             }
             System.out.println("txtCheque_No " + txtCheque_No);

            

             try {
         String vousql= "select i.RECEIPT_NO as vno from FAS_FUND_RECEIPT_BY_OFFICE i where i.CHEQUE_OR_DD='C' and\n" + 
                "(i.CHEQ_DISHONOUR_STATUS is null or i.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_UNIT_ID=? \n" + 
                "and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=?  and \n" + 
                "i.CASHBOOK_MONTH=? and i.CHEQUE_DD_NO=? and  i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y'\n" + 
                "union\n" + 
                "select i.RECEIPT_NO as vno from FAS_FUND_RECEIPT_BY_HO i where i.CHEQUE_OR_DD='C' and\n" + 
                "(i.CHEQ_DISHONOUR_STATUS is null or i.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_UNIT_ID=? \n" + 
                "and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=?  and \n" + 
                "i.CASHBOOK_MONTH=? and i.CHEQUE_DD_NO=? and  i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y'\n" + 
                "union\n" + 
                "select i.RECEIPT_NO as vno from FAS_RECEIPT_MASTER i,FAS_RECEIPT_TRANSACTION j\n" + 
                "where i.RECEIPT_NO=j.RECEIPT_NO and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and \n" + 
                "(j.CHEQ_DISHONOUR_STATUS is null or j.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID \n" + 
                " and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH and \n" + 
                "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=? \n" + 
                " and i.CASHBOOK_MONTH=? and j.CHEQUE_DD_NO=? and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.RECEIPT_TYPE='B'\n" + 
                "and i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y' " ;
            
                 ps=con.prepareStatement(vousql);
                 ps.setInt(1, cmbAcc_UnitCode);
                 ps.setInt(2, cmbOffice_code);
                 ps.setInt(3, CashBookYear);
                 ps.setInt(4, CashBookMonth);
                 ps.setString(5,txtCheque_No);
                 ps.setInt(6, cmbAcc_UnitCode);
                 ps.setInt(7, cmbOffice_code);
                 ps.setInt(8, CashBookYear);
                 ps.setInt(9, CashBookMonth);
                 ps.setString(10,txtCheque_No);
                 ps.setInt(11, cmbAcc_UnitCode);
                 ps.setInt(12, cmbOffice_code);
                 ps.setInt(13, CashBookYear);
                 ps.setInt(14, CashBookMonth);
                 ps.setString(15,txtCheque_No);
                 rs = ps.executeQuery();
        
                 int count = 0;
                 while (rs.next()) {


                       

                     xml = 
                 xml + "<Rec_No>" + rs.getInt("vno") + "</Rec_No>"
                      
                      
                 ;
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
             Calendar c;
             xml = "<response><command>Other_Details</command>";
             String txtCheque_No = "";
             int txtVoucher_No=0;
             System.out.println("inside otherdetails");  

             try {
                 txtCheque_No = 
                         request.getParameter("txtCheque_No");
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
                 String cheqsql= "select i.RECEIPT_NO as vno,to_char(i.RECEIPT_DATE,'DD/MM/YYYY') as transdate,i.CHEQUE_OR_DD as cheqtype,'Fund Receipt' as doctype,\n" + 
                "i.CHEQUE_DD_NO as cheqno,to_char(i.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate from FAS_FUND_RECEIPT_BY_OFFICE i where i.CHEQUE_OR_DD='C' and\n" + 
                "(i.CHEQ_DISHONOUR_STATUS is null or i.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_UNIT_ID=? \n" + 
                "and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=?  and \n" + 
                "i.CASHBOOK_MONTH=? and i.CHEQUE_DD_NO=? and i.RECEIPT_NO=? and  i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y'\n" + 
                "union\n" + 
                "select i.RECEIPT_NO as vno,to_char(i.RECEIPT_DATE,'DD/MM/YYYY') as transdate,i.CHEQUE_OR_DD as cheqtype,'Fund Receipt HO' as doctype,\n" + 
                "i.CHEQUE_DD_NO as cheqno,to_char(i.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate from FAS_FUND_RECEIPT_BY_HO i where i.CHEQUE_OR_DD='C' and\n" + 
                "(i.CHEQ_DISHONOUR_STATUS is null or i.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_UNIT_ID=? \n" + 
                "and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=?  and \n" + 
                "i.CASHBOOK_MONTH=? and i.CHEQUE_DD_NO=? and i.RECEIPT_NO=? and  i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y'\n" + 
                "union\n" + 
                "select i.RECEIPT_NO as vno,to_char(i.RECEIPT_DATE,'DD/MM/YYYY') as transdate,j.CHEQUE_OR_DD as cheqtype,'Bank Receipt' as doctype,\n" + 
                "j.CHEQUE_DD_NO as cheqno,to_char(j.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate from FAS_RECEIPT_MASTER i,FAS_RECEIPT_TRANSACTION j\n" + 
                "where i.RECEIPT_NO=j.RECEIPT_NO and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and \n" + 
                "(j.CHEQ_DISHONOUR_STATUS is null or j.CHEQ_DISHONOUR_STATUS!='Y') and i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID \n" + 
                " and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH and \n" + 
                "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=? \n" + 
                " and i.CASHBOOK_MONTH=? and j.CHEQUE_DD_NO=? and j.RECEIPT_NO=? and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.RECEIPT_TYPE='B'\n" + 
                "and i.RECEIPT_STATUS!='C' and i.REMITTANCE_STATUS='Y' " ;
                           
                                ps=con.prepareStatement(cheqsql);
                                ps.setInt(1, cmbAcc_UnitCode);
                                ps.setInt(2, cmbOffice_code);
                                ps.setInt(3, CashBookYear);
                                ps.setInt(4, CashBookMonth);
                                ps.setString(5,txtCheque_No);
                                ps.setInt(6,txtVoucher_No);
                                ps.setInt(7, cmbAcc_UnitCode);
                                ps.setInt(8, cmbOffice_code);
                                ps.setInt(9, CashBookYear);
                                ps.setInt(10, CashBookMonth);
                                ps.setString(11,txtCheque_No);
                                ps.setInt(12,txtVoucher_No);
                                ps.setInt(13, cmbAcc_UnitCode);
                                ps.setInt(14, cmbOffice_code);
                                ps.setInt(15, CashBookYear);
                                ps.setInt(16, CashBookMonth);
                                ps.setString(17,txtCheque_No);
                                ps.setInt(18,txtVoucher_No);
                                rs = ps.executeQuery();

                 int count = 0;
                 while (rs.next()) {

                     xml = xml +"<cheq_dd>" + rs.getString("cheqtype") + "</cheq_dd>" + 
                      "<che_DD_date>" + rs.getString("cheqdate") + "</che_DD_date>" + 
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
                 ps.close();
                 rs.close();
             
             }
                 catch (Exception e) {
                                 System.out.println("catch..HERE.in load VOUCHER." + e);
                                 xml = xml + "<flag>failure</flag>";
                             }
                             
                 try {
                     String cheqsql= "select COALESCE(sum(i.TOTAL_AMOUNT),0) as tot from FAS_FUND_RECEIPT_BY_OFFICE i \n" + 
                     "where (i.CHEQUE_OR_DD='C' or i.CHEQUE_OR_DD='D') \n" + 
                     "and (i.CHEQ_DISHONOUR_STATUS is null or i.CHEQ_DISHONOUR_STATUS!='Y') and  i.RECEIPT_STATUS ='L'\n" + 
                     "and i.ACCOUNTING_UNIT_ID=? and i.ACCOUNTING_FOR_OFFICE_ID=? \n" + 
                     "and i.CASHBOOK_YEAR=? and i.CASHBOOK_MONTH=? and i.CHEQUE_DD_NO =?  and i.REMITTANCE_STATUS='Y' and I.RECEIPT_NO              =?\n" + 
                     "union\n" + 
                     "select COALESCE(sum(i.TOTAL_AMOUNT),0) as tot from FAS_FUND_RECEIPT_BY_HO i \n" + 
                     "where (i.CHEQUE_OR_DD='C' or i.CHEQUE_OR_DD='D') \n" + 
                     "and (i.CHEQ_DISHONOUR_STATUS is null or i.CHEQ_DISHONOUR_STATUS!='Y') and  i.RECEIPT_STATUS ='L'\n" + 
                     "and i.ACCOUNTING_UNIT_ID=? and i.ACCOUNTING_FOR_OFFICE_ID=? \n" + 
                     "and i.CASHBOOK_YEAR=? and i.CASHBOOK_MONTH=? and i.CHEQUE_DD_NO =? and i.REMITTANCE_STATUS='Y' and I.RECEIPT_NO              =?\n" + 
                     "union\n" + 
                     "select sum(j.AMOUNT) as tot from FAS_RECEIPT_MASTER i,FAS_RECEIPT_TRANSACTION j \n" + 
                     "where  i.RECEIPT_NO=j.RECEIPT_NO and (j.CHEQ_DISHONOUR_STATUS is null or j.CHEQ_DISHONOUR_STATUS!='Y') \n" + 
                     "and i.ACCOUNTING_UNIT_ID=j.ACCOUNTING_UNIT_ID and  \n" + 
                     "i.ACCOUNTING_FOR_OFFICE_ID=j.ACCOUNTING_FOR_OFFICE_ID  \n" + 
                     "and i.CASHBOOK_YEAR=j.CASHBOOK_YEAR and i.CASHBOOK_MONTH = j.CASHBOOK_MONTH  \n" + 
                     "and (j.CHEQUE_OR_DD='C' or j.CHEQUE_OR_DD='D') and i.RECEIPT_STATUS ='L' and i.REMITTANCE_STATUS='Y' and i.RECEIPT_TYPE='B' and \n" + 
                     "i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.CASHBOOK_YEAR=? \n" + 
                     "and i.CASHBOOK_MONTH=? and j.CHEQUE_DD_NO =?  and I.RECEIPT_NO              =?";
                               
                                    ps=con.prepareStatement(cheqsql);
                                    ps.setInt(1, cmbAcc_UnitCode);
                                    ps.setInt(2, cmbOffice_code);
                                    ps.setInt(3, CashBookYear);
                                    ps.setInt(4, CashBookMonth);
                                    ps.setString(5,txtCheque_No);
                                    ps.setInt(6, txtVoucher_No);
                                    ps.setInt(7, cmbAcc_UnitCode);
                                    ps.setInt(8, cmbOffice_code);
                                    ps.setInt(9, CashBookYear);
                                    ps.setInt(10, CashBookMonth);
                                    ps.setString(11,txtCheque_No);
                                    ps.setInt(12, txtVoucher_No);
                                    ps.setInt(13, cmbAcc_UnitCode);
                                    ps.setInt(14, cmbOffice_code);
                                    ps.setInt(15, CashBookYear);
                                    ps.setInt(16, CashBookMonth);
                                    ps.setString(17,txtCheque_No);
                                    ps.setInt(18,txtVoucher_No);
                                    rs = ps.executeQuery();

                     int count = 0;
                     while (rs.next()) {
                    	 if(rs.getInt("tot")>0) {
                    		 xml = xml+"<Total_amt>" + rs.getInt("tot") + "</Total_amt>" ;
                    	 }
                        
                        
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
        ResultSet rs = null, rs2 = null, rs3 = null,rs4=null;
        //  CallableStatement cs=null,cs1=null;
        PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null,ps4=null;
        int max_branch_id = 0;

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
            Calendar c,c1,c4;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtReceipt_No = 0;
            Date txtDoc_date = null;
            Date received_date = null;
            Date che_DD_date = null;
            double txtAmount = 0;
            double payAmount=0;
            int subledger = 0;
            int subledger_trans=0,account_no=0;
            /*NK ON 29SEP20*/
            BigDecimal account_no1 = null;
            /*NK ON 29SEP20*/
            int accnthead=0;String accnthead1="";String acc_head="";int acc_head1=0;
            int subledgertype=0;
            int accnthead_trans=0;
            int subledgertype_trans=0;
            int challan_no=0;
            int transn_records=0;
            Date challan_date=null;
            String crdrindicator="";
            String bank_col_ac="";
            int sub_acc_type=0;
            String crdrindicator_trans="";
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            String CashBook_Year = request.getParameter("txtCB_Year");
            String CashBook_Month = request.getParameter("txtCB_Month");
            //  String CashBook_Year_Cur = request.getParameter("txtCash_year");
            //  String CashBook_Month_Cur = request.getParameter("txtCash_Month");
            String txtCheque_DD = request.getParameter("txtCheque_DD");
            String txtCheq_DD_New=request.getParameter("txtCheq_DD_Issued");
            String txtCheque_DD2 = "",txtCheque_DD3 = "";String doctype = "";String remtype="";
            String remarks_date="";
            String paid_to="";
            String paydate="";
           int CashBookYear_Cur=0,CashBookMonth_Cur=0;
            int CashBookYear = 0;
            int CashBookMonth = 0;
            int cbrefno_trans=0;
            Date txtCrea_date=null;
       //     String doctype = "";
            String txtRemarks = "";
            String txtCheque_DD_NO = "";
           String txtCheque_DD_NO2 = "";
            Date txtCheque_DD_date2 = null;
            double txtAmount2 = 0;
            int txtVoucher_No = 0;
            int cbrefno=0;
            Date trans_cbrefdate=null;
            Date cbref_transdate=null;
            try {
                txtVoucher_No = 
                        Integer.parseInt(request.getParameter("txtReceipt_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);
            
            try {
                txtCheque_DD_NO = 
                       request.getParameter("txtCheque_No");
                        System.out.println("txtCheque_DD_NO"+txtCheque_DD_NO);
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
                System.out.println("remarks_date"+remarks_date);
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
            String[] sd = request.getParameter("txtDoc_date").split("/");
            c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, 
                        Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtDoc_date = new Date(d.getTime());
            System.out.println("txtDoc_date " + txtDoc_date);
            
            
            // added on 30/09/2011        ***********
            String txtoption="";
            try
            {
                txtoption=request.getParameter("txtoption");
                System.out.println("option selected is :::::::::::::::"+txtoption);
            }
            catch(Exception e)
            {
                System.out.println("Exception while getting the option either month wise or chequewise");            
            }
            if(txtoption.equalsIgnoreCase("M"))
            {
                        System.out.println("testtttttttttttt******************** if Month wise ");
                        try {
                            CashBookYear = Integer.parseInt(CashBook_Year);
                            System.out.println("CashBook_Year After is:" + CashBookYear);
                        } catch (Exception e) {
                            System.out.println("Exception in Year:" + e);
                            CashBookYear = 0;
                        }
            
                        try {
                            CashBookMonth = Integer.parseInt(CashBook_Month);
                            System.out.println("CashBook Month After is:" + CashBookMonth);
                        } catch (Exception e) {
                            System.out.println("Exception in Month:" + e);
                            CashBookMonth = 0;
                        }
            }
            else if(txtoption.equalsIgnoreCase("C"))
            {
                        System.out.println("if chequewise chosen ******************88");
                        try {
                            CashBookYear = Integer.parseInt(sd[2]);
                            System.out.println("CashBook_Year After is:" + CashBookYear);
                        } catch (Exception e) {
                            System.out.println("Exception in Year:" + e);
                            CashBookYear = 0;
                        }
                        
                        try {
                            CashBookMonth = Integer.parseInt(sd[1]);
                            System.out.println("CashBook Month After is:" + CashBookMonth);
                        } catch (Exception e) {
                            System.out.println("Exception in Month:" + e);
                            CashBookMonth = 0;
                        }
            }
            
          /*  try {
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
            }  */

            try {
                txtReceipt_No = 
                        Integer.parseInt(request.getParameter("txtReceipt_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception in voucher no" + e);
            }
            System.out.println("txtReceipt_No " + txtReceipt_No);
            String[] sd1 = 
                request.getParameter("txtCheque_DD_date").split("/");
            c1 = 
  new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1, 
                        Integer.parseInt(sd1[0]));
            java.util.Date d1 = c1.getTime();
            che_DD_date = new Date(d1.getTime());
            System.out.println("che_DD_date " + che_DD_date);

            try {
                txtCheque_DD = request.getParameter("txtCheque_DD");
            } catch (Exception e) {
                System.out.println("excepion in cheque/dd type :" + e);
            }
            
            
            String[] sd4=request.getParameter("txtCrea_date").split("/");
            c4=new GregorianCalendar(Integer.parseInt(sd4[2]),Integer.parseInt(sd4[1])-1,Integer.parseInt(sd4[0]));
            java.util.Date d4=c4.getTime();
            txtCrea_date=new Date(d4.getTime());
            System.out.println("txtCrea_date******ie cheq dishonour date **** "+txtCrea_date);
            /*current cash book month and year of dishonour table is taken from the dishonour date */
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
             System.out.println("txtCash_Month_hid  from dishonor date " + CashBookMonth_Cur);
            
            
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
             System.out.println("txtCheque_DD2^^^^^^^"+txtCheque_DD2);
            //Null pointer Exception 26/08/2011
             if(txtCheque_DD2.equalsIgnoreCase("Y"))
             {
                    try {
                        txtCheque_DD3 = request.getParameter("txtCheque_DD3");
                    } catch (Exception e) {
                        System.out.println("excepion in create voucher :" + e);
                    }
             }
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
                System.out.println("txtAmount2 " + txtAmount2);
            }
            
            if (doctype.equalsIgnoreCase("Fund Receipt")) {
            remtype="F";
            System.out.println("remtype===<<<<====="+remtype);
            
            }
           else if (doctype.equalsIgnoreCase("Fund Receipt HO")) {
            remtype="FH";
            System.out.println("remtype===<<<<====="+remtype);
            
            }
            else if (doctype.equalsIgnoreCase("Bank Receipt")){
                remtype="B";
                System.out.println("remtype===<<<<====="+remtype);
            }
       
            try {
            
            // System.out.println("received_date " + txtDoc_date);

            if (doctype.equalsIgnoreCase("Fund Receipt")) {
            ps = con.prepareStatement ("select CHALLAN_NO,CHALLAN_DATE,RECEIPT_DATE as paydate, '-' as paid_to from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
            "and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and  RECEIPT_DATE=? and RECEIPT_NO=?");

            }
           else if (doctype.equalsIgnoreCase("Fund Receipt HO")) {
                ps = con.prepareStatement ("select CHALLAN_NO,CHALLAN_DATE,RECEIPT_DATE as paydate, '-' as paid_to from FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                "and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and  RECEIPT_DATE=? and RECEIPT_NO=?");

                }
            
            else if (doctype.equalsIgnoreCase("Bank Receipt")){
              ps =con.prepareStatement("select CHALLAN_NO,CHALLAN_DATE,RECEIPT_DATE as paydate,RECEIVED_FROM as paid_to from FAS_RECEIPT_MASTER \n" + 
              "where  ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" + 
              "and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_DATE=? and RECEIPT_NO=?");
           /* ps = con.prepareStatement ("select b.CHALLAN_NO,b.CHALLAN_DATE,b.RECEIPT_DATE as paydate, a.RECEIVED_FROM as paid_to from FAS_RECEIPT_TRANSACTION a,FAS_RECEIPT_MASTER b where a.RECEIPT_NO=b.RECEIPT_NO and a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? " +
            "and a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=? and b.RECEIPT_DATE=? and a.RECEIPT_NO=? and a.CHEQUE_DD_NO=?");
                  */
            }

            // ps = con.prepareStatement(sql3);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, cmbOffice_code);
            ps.setInt(3, CashBookYear);
            ps.setInt(4, CashBookMonth);
            ps.setDate(5, txtDoc_date);
            ps.setInt(6, txtVoucher_No);
          //  ps.setString(7, txtCheque_DD_NO);
            rs = ps.executeQuery();


            

            System.out.println("txtCrea_date3 " + txtDoc_date);
            System.out.println("outside if loop");

            while (rs.next()) {

            System.out.println("indide if of receipt master loop");
            challan_no=rs.getInt("CHALLAN_NO");
            challan_date=rs.getDate("CHALLAN_DATE");
            paydate=rs.getString("paydate");
                if((rs.getString("PAID_TO"))==null){
                    paid_to="";
                }
                else{
                paid_to=rs.getString("PAID_TO");}
           // paid_to=rs.getString("paid_to");
            }

            } catch (Exception e) {
            System.out.println("catch..HERE.in failure to retrieve." + e);
            xml = xml + "<flag>failure</flag>";
            }
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
            if (txtCheque_DD2.equalsIgnoreCase("Y")) {
            
                String[] sd3 = request.getParameter("txtCheque_DD_date2").split("/");
                c =   new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1, 
                            Integer.parseInt(sd3[0]));
                java.util.Date d3 = c.getTime();
                txtCheque_DD_date2 = new Date(d3.getTime());
                System.out.println("issue_cheque_date " + txtCheque_DD_date2);

                try {
                    txtCheque_DD_NO2 = 
                            request.getParameter("txtCheque_DD_NO2");
                } catch (Exception e) {
                    System.out.println("excepion in issued cheque number :" + e);
                }
                System.out.println("issue cheque yes/no"+txtCheque_DD_NO2);
                
                String[] sd2 = request.getParameter("txtCrea_date2").split("/");
                c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1, 
                            Integer.parseInt(sd2[0]));
                java.util.Date d2= c.getTime();
                received_date = new Date(d2.getTime());
                System.out.println("received_date " + received_date);
                
               
                try {
                    txtAmount2 = 
                            Double.parseDouble(request.getParameter("txtAmount2"));
                } catch (Exception e) {
                    System.out.println("exception" + e);
                }
                System.out.println("txtAmount2 " + txtAmount2);
                if (doctype.equalsIgnoreCase("Bank Receipt")) {
                 
                                   
                               try {
                                         
                                
                                       ps2 = con.prepareStatement("UPDATE FAS_RECEIPT_TRANSACTION SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                  " AND RECEIPT_NO=? and CHEQUE_DD_NO=? and CHEQ_DISHONOUR_STATUS is null");
                                         
                                       
                                       ps2.setString(1,"Y");
                                       ps2.setTimestamp(2, ts);
                                       ps2.setString(3, update_user);
                                       ps2.setInt(4, cmbAcc_UnitCode);
                                       ps2.setInt(5, cmbOffice_code);
                                       ps2.setInt(6, CashBookYear);
                                       ps2.setInt( 7, CashBookMonth);
                                       ps2.setInt(8, txtVoucher_No);
                                       ps2.setString(9, txtCheque_DD_NO );
                                     
                                         
                                       
                                       ps2.executeUpdate();
                                       ps2.close();
                                        xml = xml + "<flag>success</flag>";
                                        System.out.println("here update in receipt entry ok");
                                     } catch (Exception e) {
                                         System.out.println("exception in update receipt entry:" + e);
                                               }
                             
                            }
                            
                            
                else if (doctype.equalsIgnoreCase("Fund Receipt")) {
                    try {
                             
                    
                           ps2 = con.prepareStatement("UPDATE FAS_FUND_RECEIPT_BY_OFFICE SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                      " AND RECEIPT_NO=? and CHEQUE_DD_NO=? and CHEQ_DISHONOUR_STATUS is null");
                           
                           ps2.setString(1,"Y");
                           ps2.setTimestamp(2, ts);
                           ps2.setString(3, update_user);  
                           ps2.setInt(4, cmbAcc_UnitCode);
                           ps2.setInt(5, cmbOffice_code);
                           ps2.setInt(6, CashBookYear);
                           ps2.setInt(7, CashBookMonth);
                           ps2.setInt(8, txtVoucher_No);
                           ps2.setString(9, txtCheque_DD_NO ); 
                             
                           
                           ps2.executeUpdate();
                           ps2.close();
                            xml = xml + "<flag>success</flag>";
                            System.out.println("here update in receipt entry ok");
                         } catch (Exception e) {
                             System.out.println("exception in journal entry:" + e);
                                   }
                }
                else if (doctype.equalsIgnoreCase("Fund Receipt HO")) {
                    try {
                             
                    
                           ps2 = con.prepareStatement("UPDATE FAS_FUND_RECEIPT_BY_HO SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                      " AND RECEIPT_NO=? and CHEQUE_DD_NO=? and CHEQ_DISHONOUR_STATUS is null");
                           
                           ps2.setString(1,"Y");
                           ps2.setTimestamp(2, ts);
                           ps2.setString(3, update_user);  
                           ps2.setInt(4, cmbAcc_UnitCode);
                           ps2.setInt(5, cmbOffice_code);
                           ps2.setInt(6, CashBookYear);
                           ps2.setInt(7, CashBookMonth);
                           ps2.setInt(8, txtVoucher_No);
                           ps2.setString(9, txtCheque_DD_NO ); 
                             
                           
                            ps2.executeUpdate();
                           ps2.close();
                            xml = xml + "<flag>success</flag>";
                            System.out.println("here update in receipt entry ok");
                         } catch (Exception e) {
                             System.out.println("exception in journal entry:" + e);
                                   }
                }
                

                String sql_fund = ("insert into FAS_CHEQUE_DISHONOUR(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH, " +
                                   " DOCUMENT_DATE,DOC_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,REMARKS,RECEIVED_CHEQUE,RECEIVED_CHEQUE_NO, " +
                                   " RECEIVED_CHEQUE_DATE,RECEIVED_CHEQUE_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,DOCUMENT_TYPE,RECIEVED_DATE,RECEIVED_CHEQUE_OR_DD,CHEQ_DISHONOUR_DATE,CREA_PAY_VOU) " +
                                   " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " ) ;
                try {

                    System.out.println("here begin..add for yes condn");
                    ps = con.prepareStatement(sql_fund);
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setInt(3, CashBookYear_Cur);
                    ps.setInt(4, CashBookMonth_Cur);
                    ps.setDate(5, txtDoc_date);
                    ps.setInt(6, txtReceipt_No);
                    ps.setString(7, txtCheque_DD);
                    ps.setString(8, txtCheque_DD_NO);
                    ps.setDate(9, che_DD_date);
                    ps.setDouble(10, txtAmount);
                    ps.setString(11, txtRemarks);
                    ps.setString(12, txtCheque_DD2);
                    ps.setString(13, txtCheque_DD_NO2);
                    ps.setDate(14, txtCheque_DD_date2);
                    ps.setDouble(15, txtAmount2);
                    ps.setString(16, update_user);
                    ps.setTimestamp(17, ts);
                    ps.setString(18,doctype);
                    ps.setDate(19, received_date);
                    ps.setString(20,txtCheq_DD_New);
                    ps.setDate(21,txtCrea_date);
                    ps.setString(22,txtCheque_DD3);
                    ps.executeUpdate();
                    xml = xml + "<flag>success</flag>";
                    System.out.println("here is ok");


                    sendMessage(response, 
                                " Cross Reference details stored and a GJV with  created Successfully for the voucher no"+txtReceipt_No, 
                                "ok");
                } catch (Exception e) {

                    System.out.println("Exception occur due to " + e);
                    
                }
           
          try {
                   System.out.println("CHALLAN_DATE"+challan_date);
                   System.out.println("challan_no"+challan_no);
                   System.out.println("inside ");
                   System.out.println("remtype"+remtype);  
          
                   ps2 = con.prepareStatement("UPDATE FAS_REMITTANCE SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                              " AND CHALLAN_NO=? AND CHALLAN_DATE=? AND REMITTANCE_TYPE=?  and CHEQ_DISHONOUR_STATUS is null");
                  
                   ps2.setString(1,"Y");
                   ps2.setTimestamp(2, ts);
                   ps2.setString(3, update_user);
                   ps2.setInt(4, cmbAcc_UnitCode);
                   ps2.setInt(5, cmbOffice_code);
                   ps2.setInt(6, CashBookYear);
                   ps2.setInt(7, CashBookMonth);
                   ps2.setInt(8, challan_no);
                   ps2.setDate(9,challan_date);
                   ps2.setString(10,remtype);
                   System.out.println("UPDATE FAS_REMITTANCE SET CHEQ_DISHONOUR_STATUS='Y',UPDATED_DATE='"+ts+"',UPDATED_BY_USER_ID='"+update_user+"' WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" "+
                                   "AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR="+CashBookYear+" AND CASHBOOK_MONTH="+CashBookMonth+" " +
                                   " AND CHALLAN_NO="+challan_no+"  AND REMITTANCE_TYPE="+remtype+"  ");
                  ps2.executeUpdate();
                  ps2.close();
                  xml = xml + "<flag>success</flag>";
                  System.out.println("here is remitance update ok>>>>>");
               } catch (Exception e) {
                   System.out.println("exception in remitance update:" + e);
                         }
                         
                    
                 
                  
                  
    //***********************checking for yes condn if new amount is not same as original****************************
            if(txtCheque_DD2.equalsIgnoreCase("Y")&&(txtCheque_DD3.equalsIgnoreCase("Y"))) {
             System.out.println("inside y condn with difference in amount");

               /*    try {
                   System.out.println("inside remitance");
                          ps2 = con.prepareStatement("UPDATE FAS_REMITTANCE SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                     " AND CHALLAN_NO=?  AND REMITTANCE_TYPE=?  ");
                         
                          ps2.setString(1,"Y");
                          ps2.setTimestamp(2, ts);
                          ps2.setString(3, update_user);
                          ps2.setInt(4, cmbAcc_UnitCode);
                          ps2.setInt(5, cmbOffice_code);
                          ps2.setInt(6, CashBookYear);
                          ps2.setInt(7, CashBookMonth);
                          ps2.setInt(8, challan_no);
                          ps2.setString(9,remtype);
                         
                          ps2.executeUpdate();
                          ps2.close();
                           xml = xml + "<flag>success</flag>";
                           System.out.println("here is updation in remitance ok");
                        } catch (Exception e) {
                            System.out.println("exception in remitance update:" + e);
                                  }
                  */

                               try {
                                           ps2 = 
                               con.prepareStatement("SELECT coalesce(max(VOUCHER_NO),0)+1 AS br_id FROM FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and " +
                                        "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                                           ps2.setInt(1, cmbAcc_UnitCode);
                                           ps2.setInt(2, cmbOffice_code);
                                           ps2.setInt(3, CashBookYear_Cur);
                                           ps2.setInt(4, CashBookMonth_Cur);
                                          // ps2.setDate(5, txtDoc_date);
                                           rs2 = ps2.executeQuery();

                                           while (rs2.next()) {
                                               max_branch_id = rs2.getInt("br_id");
                                           }
                                           
                                           System.out.println("Maximum Brach ID is ==" + 
                                                              max_branch_id);

                                        //   ps2.close();
                                     //      rs2.close();

                                       } catch (Exception e) {
                                           System.out.println("Failed to Fetch Maximum Branch ID " + 
                                                              e);
                                       }
                                       
                                    //--------------for bank recipt type getting values for subledger type,code,accounthead,indicator from reciept transaction and reciept master------------//   
                                     ArrayList<String> H_code = new ArrayList();                                         
                                     ArrayList<String> SL_code = new ArrayList();                     
                                     ArrayList<String> SL_type = new ArrayList();                     
                                     ArrayList<String> CR_DR_type = new ArrayList();                     
                                     ArrayList<String> Amt_value=new ArrayList();
                                     if(doctype.equalsIgnoreCase("Bank Receipt")) {
                                       try {
                                           ps2 = 
                                     con.prepareStatement("SELECT ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,RECEIPT_NO,AMOUNT FROM FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and " +
                                        "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=? and CHEQUE_DD_NO=?");
                                           ps2.setInt(1, cmbAcc_UnitCode);
                                           ps2.setInt(2, cmbOffice_code);
                                           ps2.setInt(3, CashBookYear);
                                           ps2.setInt(4, CashBookMonth);
                                           ps2.setInt(5, txtVoucher_No);
                                           ps2.setString(6, txtCheque_DD_NO );
                                           rs2 = ps2.executeQuery();
                                       int k=0;
                                       while (rs2.next()) {
                                       
                                           H_code.add(rs2.getString("ACCOUNT_HEAD_CODE"));
                                           
                                           SL_type.add(rs2.getString("SUB_LEDGER_TYPE_CODE"));
                                           SL_code.add(rs2.getString("SUB_LEDGER_CODE"));
                                           CR_DR_type.add(rs2.getString("CR_DR_INDICATOR"));
                                           Amt_value.add(rs2.getString("Amount"));
                                           System.out.println("H_code ==" + H_code.get(k));
                                           System.out.println("SL_code"+SL_code.get(k));
                                           System.out.println("SL_type"+SL_type.get(k));
                                           System.out.println("CR_DR_type"+CR_DR_type.get(k));  
                                           System.out.println("Amt_value"+Amt_value.get(k));
                                           k++;
                                       }

                                       k--;
                                       
                                       ps2.close();
                                       rs2.close();

                                       } catch (Exception e) {
                                       System.out.println("Failed to Fetch values from payment_transaction-------------->>" + e);
                                       }
                        
                                       remtype="B";
                                       //----------
                                       try {
                                           ps3 = 
                                       con.prepareStatement("SELECT RECEIPT_NO,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,TOTAL_AMOUNT,RECEIPT_DATE,account_no FROM FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and " +
                                        "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=?");
                                           ps3.setInt(1, cmbAcc_UnitCode);
                                           ps3.setInt(2, cmbOffice_code);
                                           ps3.setInt(3, CashBookYear);
                                           ps3.setInt(4, CashBookMonth);
                                           ps3.setInt(5, txtVoucher_No);
                                           rs3 = ps3.executeQuery();

                                           while (rs3.next()) {
                                               subledger = rs3.getInt("SUB_LEDGER_CODE");
                                               accnthead=rs3.getInt("ACCOUNT_HEAD_CODE");
                                               accnthead1=rs3.getString("ACCOUNT_HEAD_CODE");
                                               /*NK ON 29SEP20*/
                                               //account_no=rs3.getInt("account_no");
                                               account_no1=rs3.getBigDecimal("account_no");
                                               /*NK ON 29SEP20*/
                                               bank_col_ac=rs3.getString("account_no");
                                               subledgertype=rs3.getInt("SUB_LEDGER_TYPE_CODE");
                                               sub_acc_type=rs3.getInt("SUB_LEDGER_TYPE_CODE");
                                               crdrindicator=rs3.getString("CR_DR_INDICATOR");
                                               payAmount=rs3.getDouble("TOTAL_AMOUNT");
                                               cbrefno=rs3.getInt("RECEIPT_NO");
                                               trans_cbrefdate=rs3.getDate("RECEIPT_DATE");
                                           }


                                           System.out.println("subledger is ==" + subledger);
                                           System.out.println("Accounthead"+accnthead);
                                           System.out.println("Subledger"+subledgertype);
                                           System.out.println("crdrindicator"+crdrindicator);
                                           System.out.println("cbrefno"+cbrefno);
                                           System.out.println("trans_cbrefdate"+trans_cbrefdate);
                                           ps3.close();
                                           rs3.close();

                                       } catch (Exception e) {
                                           System.out.println("Failed to Fetch values from receipt_master " + 
                                                              e);
                                       }
                                   }
          else if (doctype.equalsIgnoreCase("Fund Receipt")) {
                    try {
                             
                    
                           ps2 = con.prepareStatement("UPDATE FAS_FUND_RECEIPT_BY_OFFICE SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                      " AND RECEIPT_NO=? and CHEQUE_DD_NO=? and CHEQ_DISHONOUR_STATUS is null");
                           
                           ps2.setString(1,"Y");
                           ps2.setTimestamp(2, ts);
                           ps2.setString(3, update_user);  
                           ps2.setInt(4, cmbAcc_UnitCode);
                           ps2.setInt(5, cmbOffice_code);
                           ps2.setInt(6, CashBookYear);
                           ps2.setInt(7, CashBookMonth);
                           ps2.setInt(8, txtVoucher_No);
                           ps2.setString(9, txtCheque_DD_NO ); 
                             
                           
                           ps2.executeUpdate();
                           ps2.close();
                            xml = xml + "<flag>success</flag>";
                            System.out.println("here update in receipt entry ok");
                         } catch (Exception e) {
                             System.out.println("exception in journal entry:" + e);
                                   }
                   
                                       
                                           try {
                                               ps4 = 
                                           con.prepareStatement("SELECT CR_ACCOUNT_HEAD_CODE,DR_ACCOUNT_HEAD_CODE,TOTAL_AMOUNT,RECEIPT_NO,RECEIPT_DATE FROM FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and " +
                                            "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=? and CHEQUE_DD_NO=?");
                                               ps4.setInt(1, cmbAcc_UnitCode);
                                               ps4.setInt(2, cmbOffice_code);
                                               ps4.setInt(3, CashBookYear);
                                               ps4.setInt(4, CashBookMonth);
                                               ps4.setInt(5, txtVoucher_No);
                                               ps2.setString(6, txtCheque_DD_NO );
                                               rs4 = ps4.executeQuery();

                                               while (rs4.next()) {
                                              accnthead          = rs4.getInt("CR_ACCOUNT_HEAD_CODE");
                                            accnthead_trans        =rs4.getInt("DR_ACCOUNT_HEAD_CODE");
                                              
                                                 payAmount=rs4.getDouble("TOTAL_AMOUNT");
                                                   txtAmount=rs4.getDouble("TOTAL_AMOUNT");
                                                   cbrefno=rs4.getInt("RECEIPT_NO");
                                                     trans_cbrefdate=rs4.getDate("RECEIPT_DATE");
                                               }
                                           }
                                           catch (Exception e) {System.out.println("Failed to Fetch FAS_FUND_RECEIPT_BY_OFFICE details " + e);
                                                                               }
                                            remtype="F";
                                           subledger=0;
                                           subledgertype=0;
                                           
                                                   
                }
                          else if (doctype.equalsIgnoreCase("Fund Receipt HO")) {
                                    try {
                                             
                                    
                                           ps2 = con.prepareStatement("UPDATE FAS_FUND_RECEIPT_BY_HO  SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                      " AND RECEIPT_NO=? and CHEQUE_DD_NO=? and CHEQ_DISHONOUR_STATUS is null");
                                           
                                           ps2.setString(1,"Y");
                                           ps2.setTimestamp(2, ts);
                                           ps2.setString(3, update_user);  
                                           ps2.setInt(4, cmbAcc_UnitCode);
                                           ps2.setInt(5, cmbOffice_code);
                                           ps2.setInt(6, CashBookYear);
                                           ps2.setInt(7, CashBookMonth);
                                           ps2.setInt(8, txtVoucher_No);
                                           ps2.setString(9, txtCheque_DD_NO ); 
                                             
                                           
                                           ps2.executeUpdate();
                                           ps2.close();
                                            xml = xml + "<flag>success</flag>";
                                            System.out.println("here update in receipt entry ok");
                                         } catch (Exception e) {
                                             System.out.println("exception in journal entry:" + e);
                                                   }
                                 
                                    try {
                                        ps4 = 
                                    con.prepareStatement("SELECT CR_ACCOUNT_HEAD_CODE,DR_ACCOUNT_HEAD_CODE,TOTAL_AMOUNT,RECEIPT_NO,RECEIPT_DATE FROM FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=? and " +
                                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=? and CHEQUE_DD_NO=?");
                                        ps4.setInt(1, cmbAcc_UnitCode);
                                        ps4.setInt(2, cmbOffice_code);
                                        ps4.setInt(3, CashBookYear);
                                        ps4.setInt(4, CashBookMonth);
                                        ps4.setInt(5, txtVoucher_No);
                                        ps2.setString(6, txtCheque_DD_NO );
                                        rs4 = ps4.executeQuery();

                                        while (rs4.next()) {
                                       accnthead          = rs4.getInt("CR_ACCOUNT_HEAD_CODE");
                                     accnthead_trans        =rs4.getInt("DR_ACCOUNT_HEAD_CODE");
                                           // subledgertype=rs4.getInt("SUB_LEDGER_TYPE_CODE");
                                         // crdrindicator=rs4.getString("CR_DR_INDICATOR");
                                          payAmount=rs4.getDouble("TOTAL_AMOUNT");
                                            txtAmount=rs4.getDouble("TOTAL_AMOUNT");
                                            cbrefno=rs4.getInt("RECEIPT_NO");
                                              trans_cbrefdate=rs4.getDate("RECEIPT_DATE");
                                        }
                                    }
                                    catch (Exception e) {System.out.println("Failed to Fetch FAS_FUND_RECEIPT_BY_HO details " + e);
                                                                        }
                                     remtype="F";
                                    subledger=0;
                                    subledgertype=0;                  
                                }
                
                               if (doctype.equalsIgnoreCase("Bank Receipt")) {
                                   try {
                                       
                               ps4 = con.prepareStatement("select max(SL_NO) AS trn_records  FROM FAS_RECEIPT_TRANSACTION WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=?" +
                                                         "AND CASHBOOK_MONTH=? AND RECEIPT_NO=? AND CHEQUE_DD_NO=?");
                                   ps4.setInt(1, cmbAcc_UnitCode);
                                   ps4.setInt(2, cmbOffice_code);
                                   ps4.setInt(3, CashBookYear);
                                   ps4.setInt(4, CashBookMonth);
                                   ps4.setInt(5, txtVoucher_No);
                                   ps4.setString(6, txtCheque_DD_NO );
                                   rs4 = ps4.executeQuery();  
                                       while (rs4.next()) {
                                       transn_records = rs4.getInt("trn_records");
                                       System.out.println(transn_records);
                                         }
                                   }
                                   catch (Exception e) {
                                     System.out.println("Failed to Fetch trn_records from FAS_RECEIPT_TRANSACTION " +  e);
                                                                       }
                                   //Joan Added on 07 Jan 2015
                                   subledger=6;
                                   subledgertype=0;              
                               }
                               else if (doctype.equalsIgnoreCase("Fund Receipt")){
                                   transn_records=1;
                               }
                          else if (doctype.equalsIgnoreCase("Fund Receipt HO")){
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
                                          // ps2.setTimestamp(5,ts);
                                           ps2.setDate(5,txtCrea_date);
                                           ps2.setInt(6, max_branch_id);
                                           ps2.setInt(7, 57);
                                        
                                        //Joan Added on 07 Jan 2015
                                           if (doctype.equalsIgnoreCase("Bank Receipt"))
                                           {   ps2.setInt(8,6);
                                        	     ps2.setLong(9,Long.parseLong(bank_col_ac));}
                                           else
                                           {  ps2.setInt(8,subledger);
                                           ps2.setInt(9,subledgertype);
                                           }
                                           ps2.setString(10, txtCheque_DD_NO);
                                           ps2.setDate(11, che_DD_date);
                                           ps2.setString(12,remtype);
                                           ps2.setInt(13,transn_records);
                                           ps2.setString(14,"GJV created on Cheque dishonour for cheqno:"+txtCheque_DD_NO+"'cheqdate:"+che_DD_date+"RecieptNo:"+txtVoucher_No+"ReceivedFrom:"+paid_to+"ReceiptDate:"+paydate+"");
                                           ps2.setString(15,"A");
                                           ps2.setString(16, "GJV");
                                           ps2.setString(17, "L");
                                           ps2.setString(18, update_user);
                                           ps2.setTimestamp(19, ts);
                                         //  max_branch_id++;
                                          ps2.executeUpdate();
                                           xml = xml + "<flag>success</flag>";
                                           System.out.println("here is journal entry ok");

                                        } catch (Exception e) {
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
                         
                      String Tran="",txtCheque_Tran1="",txtCheque_Tran2="";
                    //   System.out.println("crdrindicator=="+crdrindicator);
                     if (doctype.equalsIgnoreCase("Bank Receipt")) 
                     {
                                     if (crdrindicator_trans.equalsIgnoreCase("CR")) {txtCheque_Tran2="DR";}//cbrefno=0;cbrefdate="";}
                                     else if (crdrindicator_trans.equalsIgnoreCase("DR"))  txtCheque_Tran2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                     System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                                     if (crdrindicator.equalsIgnoreCase("CR")) {txtCheque_Tran1="DR";}//cbrefno=0;cbrefdate="";}
                                     else if (crdrindicator.equalsIgnoreCase("DR"))  txtCheque_Tran1="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                     System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                                  
                    }
                         
                          else if (doctype.equalsIgnoreCase("Fund Receipt")){
                           
                         System.out.println("inside fund" );
                             txtCheque_Tran1="DR";
                             txtCheque_Tran2="CR";
                             crdrindicator="CR";
                             crdrindicator_trans="DR";
                             subledger=0;
                             subledgertype=0;
                         
                     }
                          else if (doctype.equalsIgnoreCase("Fund Receipt HO")){
                           
                          System.out.println("inside fund" );
                             txtCheque_Tran1="DR";
                             txtCheque_Tran2="CR";
                             crdrindicator="CR";
                             crdrindicator_trans="DR";
                             subledger=0;
                             subledgertype=0;
                          
                          }
                     
          
                               
                                   //-----------journal transaction from receipt master----------
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
      // Code modified on 06-12-19 by Kanaga 
                                         //acc_head=accnthead1.substring(0,3);
                                         boolean acc_headNew=accnthead1.startsWith("82");
                                         boolean acc_headNew1=accnthead1.endsWith("01");
                                         System.out.println("acc_headNew>>>>"+acc_headNew);  
                                         System.out.println("acc_headNew1>>>>"+acc_headNew1); 
                                         
                                         System.out.println("getting the substring from account head in fresh receipt Y Y condition **********"+acc_head);
                                         //acc_head1=Integer.parseInt(acc_head);
                                        // if(acc_head1==820) {
                                         if(acc_headNew==true && acc_headNew1==true){
                                             ps2.setInt(9,6);
                                             /*NK ON 29SEP20*/
                                             //ps2.setInt(10,account_no);
                                             ps2.setFloat(10,account_no1.longValue());
                                             /*NK ON 29SEP20*/
                                         }
                                         else{
                                         ps2.setInt(9,subledgertype);
                                         ps2.setInt(10,subledger);
                                         }
                                         ps2.setString(11,txtCheque_DD);
                                         ps2.setString(12, txtCheque_DD_NO);
                                         ps2.setDate(13, che_DD_date);
                                         
                                         ps2.setDouble(14,txtAmount);
                                         ps2.setString(15,"GJV created on Cheque Dishonour for cheqno:"+txtCheque_DD_NO+"cheqdate:"+che_DD_date+"ReceiptNo:"+txtVoucher_No+"ReceivedFrom:"+paid_to+"ReceiptDate:"+paydate+"");
                                        
                                         ps2.setString(16, update_user);
                                         ps2.setTimestamp(17, ts);
                                         
                                         ps2.setInt(18,cbrefno);
                                         ps2.setDate(19,trans_cbrefdate);
                                         SL_NO++;
                                        // max_branch_id++;
                                         ps2.executeUpdate();
                                         xml = xml + "<flag>success</flag>";
                                         System.out.println("here is journaltransaction entry from payment_master ok");

                                     } catch (Exception e) {
                                         System.out.println("exception in journal entry:" + e);
                                     }
                                      
                                      ///------------------------
                                       if(doctype.equalsIgnoreCase("Bank Receipt")){
                                       System.out.println("doctype for journal trns:"+doctype);
                                                   String rad_sub_CR_DR="";
                                                   int ahead_trans=0,cmbSL_type=0,cmbSL_Code=0;
                                                   double Amt_ind=0;
                                                   
                                                    try {
                                                       System.out.println("doctype for journal trns:"+doctype);
                                                       
                                                       System.out.println("H_Code Length ------------------->>>"+H_code.size());                    
                                                       
                                                       for(int k=0;k<H_code.size();k++)
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
                                                            
                                                                try{ahead_trans=Integer.parseInt(H_code.get(k));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                                try{rad_sub_CR_DR=CR_DR_type.get(k);}catch(Exception e){System.out.println("exception in CR_DR Indicator "+e);}    
                                                                try{cmbSL_type=Integer.parseInt(SL_type.get(k));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                                try{cmbSL_Code=Integer.parseInt(SL_code.get(k));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                                try{Amt_ind=Double.parseDouble(Amt_value.get(k));}catch(Exception e){System.out.println("exception in trans "+e);}
                                                                
                                                                System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                                                System.out.println("AH_code[k] "+ahead_trans);
                                                                System.out.println("IND[k] "+rad_sub_CR_DR);
                                                                System.out.println("ST[k]"+cmbSL_type);
                                                                System.out.println("SL[k]"+cmbSL_Code); 
                                                                System.out.println("Amt_ind"+Amt_ind);
                                                                System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                                                
                                                                System.out.println("Heeeeeeeeeeeeee");
                                                                String  AHead_trans=H_code.get(k);

// Code modified on 06-12-19 by Kanaga 
//                                                                String AHead_trans1=AHead_trans.substring(0,3);
//                                                                int AHead_trans2=Integer.parseInt(AHead_trans1);
//                                                                System.out.println("value of first 3 digits of accHead:::::::"+AHead_trans2);
//                                                                
                                                                String rad_sub_CR_DR2="";
                                                                if(doctype.equalsIgnoreCase("Bank Receipt")){
                                                                    if (rad_sub_CR_DR.equalsIgnoreCase("CR")) {rad_sub_CR_DR2="DR";}//cbrefno=0;cbrefdate="";}
                                                                    else if (rad_sub_CR_DR.equalsIgnoreCase("DR"))  rad_sub_CR_DR2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                                                    System.out.println("rad_sub_CR_DR=="+rad_sub_CR_DR2);
                                                                    
                                                                }
                                                                else if(doctype.equalsIgnoreCase("Fund Receipt")){
                                                                
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
                                                            
                                                            
                                                            boolean AHead_trans1=AHead_trans.startsWith("82");
                                                            boolean AHead_trans2=AHead_trans.endsWith("01");
                                                            System.out.println("acc_headNew1>>>>"+AHead_trans1);
                                                            System.out.println("acc_headNew2>>>>"+AHead_trans2);
                                                            
                                                                // added to check if account head code starts as 820 then the subledgertype is bank as 6 and subledgercode as account no
                                                                if(AHead_trans1==true && AHead_trans2==true) 
                                                                {
                                                                        ps2.setInt(9,6);
                                                                        ps2.setInt(10,account_no);
                                                                }
                                                                else
                                                                {
                                                                    ps2.setInt(9,cmbSL_type);
                                                                    ps2.setInt(10,cmbSL_Code);
                                                                }
                                                                
                                                           
                                                            ps2.setString(11,txtCheque_DD);
                                                            ps2.setString(12, txtCheque_DD_NO);
                                                            ps2.setDate(13, che_DD_date);
                                                            ps2.setDouble(14,Amt_ind);    
                                                            ps2.setString(15,"GJV created on Cheque Dishonour for cheqno:"+txtCheque_DD_NO+"cheqdate:"+che_DD_date+"ReceiptNo:"+txtVoucher_No+"ReceivedFrom:"+paid_to+"ReceiptDate:"+paydate+"");
                                                            ps2.setString(16, update_user);
                                                            ps2.setTimestamp(17, ts);                         
                                                            ps2.setInt(18,cbrefno_trans);
                                                            ps2.setDate(19,cbref_transdate);
                                                            
                                                            SL_NO++;
                                                           ps2.executeUpdate();
                                                            
                                                            xml = xml + "<flag>success</flag>";
                                                            System.out.println("here is journaltransaction entry ok");
                                                            
                                                            
                                                            }
                                                        } catch (Exception e) {
                                                            System.out.println("exception in journal entry:" + e);
                                                        }
                                               }
                                    else {           
                                      System.out.println("doctype for journal trns:"+doctype);
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
                                               ps2.setString(8,txtCheque_Tran2);
                                               ps2.setInt(9,subledgertype_trans);
                                               ps2.setInt(10,subledger_trans);
                                               ps2.setString(11,txtCheque_DD);
                                               ps2.setString(12, txtCheque_DD_NO);
                                               ps2.setDate(13, che_DD_date);
                                               
                                               ps2.setDouble(14,txtAmount);
                                               ps2.setString(15,"GJV created on Cheque Dishonour for cheqno:"+txtCheque_DD_NO+"cheqdate:"+che_DD_date+"ReceiptNo:"+txtVoucher_No+"ReceivedFrom:"+paid_to+"ReceiptDate:"+paydate+"");
                                              
                                               ps2.setString(16, update_user);
                                               ps2.setTimestamp(17, ts);
                                               
                                               ps2.setInt(18,cbrefno_trans);
                                               ps2.setDate(19,cbref_transdate);
                                               SL_NO++;
                                             // max_branch_id++;
                                              ps2.executeUpdate();
                                               xml = xml + "<flag>success</flag>";
                                               System.out.println("here is journaltransaction entry ok");

                                           } catch (Exception e) {
                                               System.out.println("exception in journal entry:" + e);
                        
                                           }
                                  }                    
               
                           
                      } 
            } 
            
            //---------------------checking for issue fresh cheque no condtion------------------
            else {
                       
                       if(txtCheque_DD2.equalsIgnoreCase("N")) {txtAmount2=0;txtCheque_DD2="";txtCheque_DD_NO2=null;txtCheque_DD_date2=null;}
                String sql_fund2 = ("insert into FAS_CHEQUE_DISHONOUR(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH, " +
                                   " DOCUMENT_DATE,DOC_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,REMARKS, " +
                                   "UPDATED_BY_USER_ID,UPDATED_DATE,DOCUMENT_TYPE,CHEQ_DISHONOUR_DATE,CREA_PAY_VOU,RECEIVED_CHEQUE ) " +
                                   " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " ) ;
                try {

                    System.out.println("here begin..add for no condn");
                    ps1 = con.prepareStatement(sql_fund2);
                    ps1.setInt(1, cmbAcc_UnitCode);
                    ps1.setInt(2, cmbOffice_code);
                    ps1.setInt(3, CashBookYear_Cur);
                    ps1.setInt(4, CashBookMonth_Cur);
                    ps1.setDate(5, txtDoc_date);
                    ps1.setInt(6, txtReceipt_No);
                    ps1.setString(7, txtCheque_DD);
                    ps1.setString(8, txtCheque_DD_NO);
                    ps1.setDate(9, che_DD_date);
                    ps1.setDouble(10, txtAmount);
                    ps1.setString(11, txtRemarks);
                  
                    ps1.setString(12, update_user);
                    ps1.setTimestamp(13, ts);
                    ps1.setString(14,doctype);
                    ps1.setDate(15,txtCrea_date);
                    ps1.setString(16,"N");
                    ps1.setString(17,"N");
                    ps1.executeUpdate();
                    xml = xml + "<flag>success</flag>";
                    System.out.println("here is ok");
                sendMessage(response, 
                            " Cross Reference details stored and a GJV created Successfully for the voucher no"+txtReceipt_No, 
                            "ok");
                } catch (Exception e) {

                System.out.println("Exception occur due to " + e);
                
                }

                if (doctype.equalsIgnoreCase("Bank Receipt")) {
                    try {
                             
                    
                           ps2 = con.prepareStatement("UPDATE FAS_RECEIPT_MASTER SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                      " AND RECEIPT_NO=?  and CHEQ_DISHONOUR_STATUS is null");
                             
                           
                           ps2.setString(1,"Y");
                           ps2.setTimestamp(2, ts);
                           ps2.setString(3, update_user);
                           ps2.setInt(4, cmbAcc_UnitCode);
                           ps2.setInt(5, cmbOffice_code);
                           ps2.setInt(6, CashBookYear);
                           ps2.setInt(7, CashBookMonth);
                           ps2.setInt(8, txtVoucher_No);
                         
                             
                           
                           ps2.executeUpdate();
                           ps2.close();
                            xml = xml + "<flag>success</flag>";
                            System.out.println("here update in  Receipt Master Table entry ok");
                         } catch (Exception e) {
                             System.out.println("exception in update Receipt entry:" + e);
                                   }
                                try {
                                         
                                
                                       ps2 = con.prepareStatement("UPDATE FAS_RECEIPT_TRANSACTION SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                                  " AND RECEIPT_NO=? AND CHEQUE_DD_NO=?  and CHEQ_DISHONOUR_STATUS is null");
                                         
                                       
                                       ps2.setString(1,"Y");
                                       ps2.setTimestamp(2, ts);
                                       ps2.setString(3, update_user);
                                       ps2.setInt(4, cmbAcc_UnitCode);
                                       ps2.setInt(5, cmbOffice_code);
                                       ps2.setInt(6, CashBookYear);
                                       ps2.setInt(7, CashBookMonth);
                                       ps2.setInt(8, txtVoucher_No);
                                       ps2.setString(9, txtCheque_DD_NO );
                                     
                                         
                                       
                                       ps2.executeUpdate();
                                       ps2.close();
                                        xml = xml + "<flag>success</flag>";
                                        System.out.println("here update in Receipt Transaction Entry ok");
                                     } catch (Exception e) {
                                         System.out.println("exception in update receipt entry:" + e);
                                               }
                                         
                            }
                            
                else if (doctype.equalsIgnoreCase("Fund Receipt")) {
                    try {
                             
                    
                           ps2 = con.prepareStatement("UPDATE FAS_FUND_RECEIPT_BY_OFFICE SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                      " AND RECEIPT_NO=? and CHEQUE_DD_NO=? and CHEQ_DISHONOUR_STATUS is null ");
                           
                           ps2.setString(1,"Y");
                           ps2.setTimestamp(2, ts);
                           ps2.setString(3, update_user);  
                           ps2.setInt(4, cmbAcc_UnitCode);
                           ps2.setInt(5, cmbOffice_code);
                           ps2.setInt(6, CashBookYear);
                           ps2.setInt(7, CashBookMonth);
                           ps2.setInt(8, txtVoucher_No);
                           ps2.setString(9, txtCheque_DD_NO );
                             
                           
                           ps2.executeUpdate();
                           ps2.close();
                            xml = xml + "<flag>success</flag>";
                            System.out.println("here update in receipt entry ok");
                         } catch (Exception e) {
                             System.out.println("exception in journal entry:" + e);
                                   }
                }
                else if (doctype.equalsIgnoreCase("Fund Receipt HO")) {
                    try {
                             
                    
                           ps2 = con.prepareStatement("UPDATE FAS_FUND_RECEIPT_BY_HO SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                      " AND RECEIPT_NO=? and CHEQUE_DD_NO=? and CHEQ_DISHONOUR_STATUS is null");
                           
                           ps2.setString(1,"Y");
                           ps2.setTimestamp(2, ts);
                           ps2.setString(3, update_user);  
                           ps2.setInt(4, cmbAcc_UnitCode);
                           ps2.setInt(5, cmbOffice_code);
                           ps2.setInt(6, CashBookYear);
                           ps2.setInt(7, CashBookMonth);
                           ps2.setInt(8, txtVoucher_No);
                           ps2.setString(9, txtCheque_DD_NO );
                             
                           
                           ps2.executeUpdate();
                           ps2.close();
                            xml = xml + "<flag>success</flag>";
                            System.out.println("here update in receipt entry ok");
                         } catch (Exception e) {
                             System.out.println("exception in journal entry:" + e);
                                   }
                }
                

            try {
                        ps2 = 
            con.prepareStatement("SELECT coalesce(max(VOUCHER_NO),0)+1 AS br_id FROM FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and " +
                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                        ps2.setInt(1, cmbAcc_UnitCode);
                        ps2.setInt(2, cmbOffice_code);
                        ps2.setInt(3, CashBookYear_Cur);
                        ps2.setInt(4, CashBookMonth_Cur);
                      
                        rs2 = ps2.executeQuery();

                        while (rs2.next()) {
                            max_branch_id = rs2.getInt("br_id");
                        }
                        
                        System.out.println("Maximum Brach ID is ==" + 
                                           max_branch_id);

                    } catch (Exception e) {
                        System.out.println("Failed to Fetch Maximum Branch ID " + 
                                           e);
                    }
                    
                 //--------------for bank recipt type getting values for subledger type,code,accounthead,indicator from reciept transaction and reciept master------------//   
                  ArrayList<String> H_code = new ArrayList();                                         
                  ArrayList<String> SL_code = new ArrayList();                     
                  ArrayList<String> SL_type = new ArrayList();                     
                  ArrayList<String> CR_DR_type = new ArrayList();                     
                  ArrayList<String> Amt_value=new ArrayList();
                if(doctype.equalsIgnoreCase("Bank Receipt")) {
                    try {
                        ps2 = 
            con.prepareStatement("SELECT ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,RECEIPT_NO,AMOUNT FROM FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and " +
                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=? and CHEQUE_DD_NO=?");
                        ps2.setInt(1, cmbAcc_UnitCode);
                        ps2.setInt(2, cmbOffice_code);
                        ps2.setInt(3, CashBookYear);
                        ps2.setInt(4, CashBookMonth);
                        ps2.setInt(5, txtVoucher_No);
                        ps2.setString(6, txtCheque_DD_NO );
                        rs2 = ps2.executeQuery();
                    int k=0;
                    while (rs2.next()) {
                    
                        H_code.add(rs2.getString("ACCOUNT_HEAD_CODE"));
                        SL_type.add(rs2.getString("SUB_LEDGER_TYPE_CODE"));
                        SL_code.add(rs2.getString("SUB_LEDGER_CODE"));
                        CR_DR_type.add(rs2.getString("CR_DR_INDICATOR"));
                        Amt_value.add(rs2.getString("Amount"));
                        System.out.println("H_code ==" + H_code.get(k));
                        System.out.println("SL_code"+SL_code.get(k));
                        System.out.println("SL_type"+SL_type.get(k));
                        System.out.println("CR_DR_type"+CR_DR_type.get(k));  
                        System.out.println("Amt_value"+Amt_value.get(k));
                        k++;
                    }

                    k--;
                    
                    ps2.close();
                    rs2.close();

                    } catch (Exception e) {
                    System.out.println("Failed to Fetch values from payment_transaction-------------->>" + e);
                    }

                   
                    remtype="B";
                    try {
                        ps3 = 
                    con.prepareStatement("SELECT RECEIPT_NO,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,TOTAL_AMOUNT,RECEIPT_DATE,account_no FROM FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and " +
                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=?");
                        ps3.setInt(1, cmbAcc_UnitCode);
                        ps3.setInt(2, cmbOffice_code);
                        ps3.setInt(3, CashBookYear);
                        ps3.setInt(4, CashBookMonth);
                        ps3.setInt(5, txtVoucher_No);
                        rs3 = ps3.executeQuery();

                        while (rs3.next()) 
                        {
                            subledger = rs3.getInt("SUB_LEDGER_CODE");
                            accnthead=rs3.getInt("ACCOUNT_HEAD_CODE");
                            accnthead1=rs3.getString("ACCOUNT_HEAD_CODE");
                            
                            subledgertype=rs3.getInt("SUB_LEDGER_TYPE_CODE");
                            crdrindicator=rs3.getString("CR_DR_INDICATOR");
                            payAmount=rs3.getDouble("TOTAL_AMOUNT");
                            cbrefno=rs3.getInt("RECEIPT_NO");
                            trans_cbrefdate=rs3.getDate("RECEIPT_DATE");
                            /*        NK ON 29SEP20*/
                           // account_no=rs3.getInt("account_no");
                             account_no1=rs3.getBigDecimal("account_no");
                             /*        NK ON 29SEP20*/
                            System.out.println("Account in receipt_master is ==" + account_no1);
                        }


                        System.out.println("subledger in receipt_master is ==" + subledger);
                        System.out.println("Accounthead receipt_master ::::; "+accnthead);
                        System.out.println("Subledger receipt_master::::::::; "+subledgertype);
                        System.out.println("crdrindicator receipt_master :::  "+crdrindicator);
                        ps3.close();
                        rs3.close();

                    } catch (Exception e) {
                        System.out.println("Failed to Fetch values from receipt_master " + 
                                           e);
                    }
                }
                else if (doctype.equalsIgnoreCase("Fund Receipt")){
                
                    try {
                        ps4 = 
                    con.prepareStatement("SELECT CR_ACCOUNT_HEAD_CODE,DR_ACCOUNT_HEAD_CODE,TOTAL_AMOUNT,RECEIPT_NO,RECEIPT_DATE FROM FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and " +
                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=? and CHEQUE_DD_NO=?");
                        ps4.setInt(1, cmbAcc_UnitCode);
                        ps4.setInt(2, cmbOffice_code);
                        ps4.setInt(3, CashBookYear);
                        ps4.setInt(4, CashBookMonth);
                        ps4.setInt(5, txtVoucher_No);
                        ps4.setString(6, txtCheque_DD_NO );
                        rs4 = ps4.executeQuery();

                        while (rs4.next()) {
                       accnthead          = rs4.getInt("CR_ACCOUNT_HEAD_CODE");
                     accnthead_trans        =rs4.getInt("DR_ACCOUNT_HEAD_CODE");
                          
                          payAmount=rs4.getDouble("TOTAL_AMOUNT");
                            txtAmount=rs4.getDouble("TOTAL_AMOUNT");
                            cbrefno=rs4.getInt("RECEIPT_NO");
                              trans_cbrefdate=rs4.getDate("RECEIPT_DATE");
                        }
                    }
                    catch (Exception e) {System.out.println("Failed to Fetch FAS_FUND_RECEIPT_BY_OFFICE details " + e);
                                                        }
                     remtype="F";
                    subledger=0;
                    subledgertype=0;
                    
                }
                else if (doctype.equalsIgnoreCase("Fund Receipt HO")){
                
                    try {
                        ps4 = 
                    con.prepareStatement("SELECT CR_ACCOUNT_HEAD_CODE,DR_ACCOUNT_HEAD_CODE,TOTAL_AMOUNT,RECEIPT_NO,RECEIPT_DATE FROM FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=? and " +
                     "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=? and CHEQUE_DD_NO=?");
                        ps4.setInt(1, cmbAcc_UnitCode);
                        ps4.setInt(2, cmbOffice_code);
                        ps4.setInt(3, CashBookYear);
                        ps4.setInt(4, CashBookMonth);
                        ps4.setInt(5, txtVoucher_No);
                        ps4.setString(6, txtCheque_DD_NO );
                        rs4 = ps4.executeQuery();

                        while (rs4.next()) {
                       accnthead          = rs4.getInt("CR_ACCOUNT_HEAD_CODE");
                     accnthead_trans        =rs4.getInt("DR_ACCOUNT_HEAD_CODE");
                          
                          payAmount=rs4.getDouble("TOTAL_AMOUNT");
                            txtAmount=rs4.getDouble("TOTAL_AMOUNT");
                            cbrefno=rs4.getInt("RECEIPT_NO");
                              trans_cbrefdate=rs4.getDate("RECEIPT_DATE");
                        }
                    }
                    catch (Exception e) {System.out.println("Failed to Fetch FAS_FUND_RECEIPT_BY_HO details " + e);
                                                        }
                     remtype="FH";
                    subledger=0;
                    subledgertype=0;
                    
                }
                if (doctype.equalsIgnoreCase("Bank Receipt")) {
                    try {
                        
                ps4 = con.prepareStatement("select max(SL_NO) AS trn_records  FROM FAS_RECEIPT_TRANSACTION WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=?" +
                                          "AND CASHBOOK_MONTH=? AND RECEIPT_NO=? AND CHEQUE_DD_NO=?");
                    ps4.setInt(1, cmbAcc_UnitCode);
                    ps4.setInt(2, cmbOffice_code);
                    ps4.setInt(3, CashBookYear);
                    ps4.setInt(4, CashBookMonth);
                    ps4.setInt(5, txtVoucher_No);
                    ps4.setString(6, txtCheque_DD_NO );
                    rs4 = ps4.executeQuery();  
                        while (rs4.next()) {
                        transn_records = rs4.getInt("trn_records");
                        System.out.println(transn_records);
                          }
                    }
                    catch (Exception e) {
                      System.out.println("Failed to Fetch trn_records from FAS_RECEIPT_TRANSACTION " +  e);
                                                        }
                }
                else{
                    transn_records=1;
                }
                  String sqljournal_pay= "insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," + 
                  "CASHBOOK_YEAR,CASHBOOK_MONTH, VOUCHER_DATE,VOUCHER_NO,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE, " + 
                  "DEPRECIATION_RATE,CHEQUE_NO,CHEQUE_DATE,CB_REF_TYPE,TOTAL_TRN_RECORDS,REMARKS," + 
                  "MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                  
                  System.out.println("***************adding journal master entry for cheq issue No condition****************");
                 
                  
                  try {
                     ps2 = con.prepareStatement(sqljournal_pay);
                     ps2.setInt(1, cmbAcc_UnitCode);
                     ps2.setInt(2, cmbOffice_code);
                     ps2.setInt(3, CashBookYear_Cur);
                     ps2.setInt(4, CashBookMonth_Cur);
                     ps2.setDate(5,txtCrea_date);
                     ps2.setInt(6, max_branch_id);
                     ps2.setInt(7, 57);
                     ps2.setInt(8,subledger);
                     ps2.setInt(9,subledgertype);
                     ps2.setString(10, txtCheque_DD_NO);
                     ps2.setDate(11, che_DD_date);
                     ps2.setString(12,remtype);
                     ps2.setInt(13,transn_records);
                     ps2.setString(14,"GJV created on Cheque dishonour for cheqno:"+txtCheque_DD_NO+"cheqdate:"+che_DD_date+"ReceiptNo:"+txtVoucher_No+"ReceivedFrom:"+paid_to+"ReceiptDate:"+paydate+"");
                     ps2.setString(15,"A");
                     ps2.setString(16, "GJV");
                     ps2.setString(17, "L");
                     ps2.setString(18, update_user);
                     ps2.setTimestamp(19, ts);
                    
                   //  max_branch_id++;
                      ps2.executeUpdate();
                     xml = xml + "<flag>success</flag>";
                     System.out.println("here is journal master for cheq No Condition --- entry ok");

                  } catch (Exception e) {
                     System.out.println("exception in journal entry:" + e);
                  }
                  System.out.println(sqljournal_pay);
                    int SL_NO=1;
                    try {
                        txtCheque_DD = request.getParameter("txtCheque_DD");
                    } catch (Exception e) {
                        System.out.println("excepion in cheque/dd type :" + e);
                    }
                    String txtCheque_Tran="",txtCheque_Tran1="",txtCheque_Tran2="";
                if (doctype.equalsIgnoreCase("Bank Receipt")) 
                {
                                if (crdrindicator_trans.equalsIgnoreCase("CR")) {txtCheque_Tran2="DR";}//cbrefno=0;cbrefdate="";}
                                else if (crdrindicator_trans.equalsIgnoreCase("DR"))  txtCheque_Tran2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                                if (crdrindicator.equalsIgnoreCase("CR")) {txtCheque_Tran1="DR";}//cbrefno=0;cbrefdate="";}
                                else if (crdrindicator.equalsIgnoreCase("DR"))  txtCheque_Tran1="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                             
                }
                    
                else{
                    System.out.println("inside fund" );
                        txtCheque_Tran1="DR";
                        txtCheque_Tran2="CR";
                        crdrindicator="CR";
                        crdrindicator_trans="DR";
                        subledger=0;
                        subledgertype=0;
                    
                }
                     
                    
                    
              /*  {        System.out.println("crdrindicator=="+crdrindicator);
                    if(doctype.equalsIgnoreCase("Bank Receipt")){
                        if (crdrindicator_trans.equalsIgnoreCase("CR")) {txtCheque_Tran2="DR";}//cbrefno=0;cbrefdate="";}
                        else if (crdrindicator_trans.equalsIgnoreCase("DR"))  txtCheque_Tran2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                        System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                        if (crdrindicator.equalsIgnoreCase("CR")) {txtCheque_Tran1="DR";}//cbrefno=0;cbrefdate="";}
                        else if (crdrindicator.equalsIgnoreCase("DR"))  txtCheque_Tran1="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                        System.out.println("txtCheque_Tran=="+txtCheque_Tran1);
                    }
                    else if(doctype.equalsIgnoreCase("Fund Receipt")){
                    
                        txtCheque_Tran1="CR";
                        txtCheque_Tran2="DR";
                        subledger=0;
                        subledgertype=0;
                    }
                }
            */
         
            
                //-----------journal transaction entry for cheq No condition from receipt master----------
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
                      
                      
// Code modified on 06-12-19 by Kanaga 
//                      acc_head=accnthead1.substring(0,3);
//                       System.out.println("getting the substring from account head**********"+acc_head);
//                       acc_head1=Integer.parseInt(acc_head);
                      
                      boolean acc_headNew=accnthead1.startsWith("82");
                      boolean acc_headNew1=accnthead1.endsWith("01");
                      System.out.println("acc_headNew>>>>"+acc_headNew);  
                      System.out.println("acc_headNew1>>>>"+acc_headNew1);     
                      
                       if(acc_headNew==true && acc_headNew1==true) 
                       {
                    	   /*NK ON 29SEP20*/
                           System.out.println("inside the 820 if condition ******* "+acc_headNew+"account_no*******************"+account_no1);
                           ps2.setInt(9,6);
                           ps2.setFloat(10,account_no1.longValue());
                           System.out.println("inside the 820 if condition ******* "+acc_headNew+"account_no*******************"+account_no1.longValue());
                           /*NK ON 29SEP20*/
                       }
                       else{
                       ps2.setInt(9,subledgertype);
                       ps2.setInt(10,subledger);
                       }
                      
                      ps2.setString(11,txtCheque_DD);
                      ps2.setString(12, txtCheque_DD_NO);
                      ps2.setDate(13, che_DD_date);
                      
                      ps2.setDouble(14,txtAmount);
                      ps2.setString(15,"GJV created on Cheque Dishonour for cheqno:"+txtCheque_DD_NO+"cheqdate:"+che_DD_date+"ReceiptNo:"+txtVoucher_No+"ReceivedFrom:"+paid_to+"ReceiptDate:"+paydate+"");
                     
                      ps2.setString(16, update_user);
                      ps2.setTimestamp(17, ts);
                      
                      ps2.setInt(18,cbrefno);
                      ps2.setDate(19,trans_cbrefdate);
                      SL_NO++;
                     // max_branch_id++;
                       ps2.executeUpdate();
                     
                      xml = xml + "<flag>success</flag>";
                      System.out.println("here is journal transaction entry from payment_master ok");

                  } catch (Exception e) {
                      System.out.println("exception in journal entry:" + e);
                  }
                   
                   ///------------------------
                    if(doctype.equalsIgnoreCase("Bank Receipt")){
                    System.out.println("doctype for journal trns:"+doctype);
                                String rad_sub_CR_DR="";
                                int ahead_trans=0,cmbSL_type=0,cmbSL_Code=0;
                                Double Amt_ind=0.0;
                                
                                 try {
                                    System.out.println("doctype for journal trns:"+doctype);
                                    
                                    System.out.println("H_Code Length ------------------->>>"+H_code.size());                    
                                    
                                    for(int k=0;k<H_code.size();k++)
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
                                         
                                             try{ahead_trans=Integer.parseInt(H_code.get(k));}catch(Exception e){System.out.println("exception in trans "+e);}
                                             try{rad_sub_CR_DR=CR_DR_type.get(k);}catch(Exception e){System.out.println("exception in CR_DR Indicator "+e);}    
                                             try{cmbSL_type=Integer.parseInt(SL_type.get(k));}catch(Exception e){System.out.println("exception in trans "+e);}
                                             try{cmbSL_Code=Integer.parseInt(SL_code.get(k));}catch(Exception e){System.out.println("exception in trans "+e);}
                                             try{Amt_ind=Double.parseDouble(Amt_value.get(k));}catch(Exception e){System.out.println("exception in trans "+e);}
                                             
                                             System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                             System.out.println("AH_code[k] "+ahead_trans);
                                             System.out.println("IND[k] "+rad_sub_CR_DR);
                                             System.out.println("ST[k]"+cmbSL_type);
                                             System.out.println("SL[k]"+cmbSL_Code); 
                                             System.out.println("Amt_ind"+Amt_ind);
                                             System.out.println("------------------------------------------------->>>>>>>>>>>>>>>>>");
                                             
                                             System.out.println("Hiiiiiiiiiiiiiiiiiiiiiiiiii");
                                             String  AHead_trans=H_code.get(k);
//                                             String AHead_trans1=AHead_trans.substring(0,3);
//                                             int AHead_trans2=Integer.parseInt(AHead_trans1);
                                             
                                             boolean AHead_trans1=AHead_trans.startsWith("82");
                                             boolean AHead_trans2=AHead_trans.endsWith("01");
                                             System.out.println("AHead_trans1>>>>"+AHead_trans1); 
                                             System.out.println("AHead_trans2>>>>"+AHead_trans2);  
                                             
                                             
                                             System.out.println("value of first 3 digits of accHead:::::::"+AHead_trans2);
                                             
                                             
                                             String rad_sub_CR_DR2="";
                                             if(doctype.equalsIgnoreCase("Bank Receipt")){
                                                 if (rad_sub_CR_DR.equalsIgnoreCase("CR")) {rad_sub_CR_DR2="DR";}//cbrefno=0;cbrefdate="";}
                                                 else if (rad_sub_CR_DR.equalsIgnoreCase("DR"))  rad_sub_CR_DR2="CR";//cbrefno_trans=cbrefno;cbref_transdate=cbrefdate;
                                                 System.out.println("rad_sub_CR_DR=="+rad_sub_CR_DR2);
                                                 
                                             }
                                             else if(doctype.equalsIgnoreCase("Fund Receipt")){
                                             
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
                                         
                                         // added to check if account head code starts as 820 then the subledgertype is bank as 6 and subledgercode as account no
                                         if(AHead_trans1==true && AHead_trans2==true) 
                                         {
                                        	 /*NK ON 29SEP20*/
                                                 ps2.setInt(9,6);
                                                 ps2.setFloat(10,account_no1.longValue());
                                                 /*NK ON 29SEP20*/
                                         }
                                         else
                                         {
                                             ps2.setInt(9,cmbSL_type);
                                             ps2.setInt(10,cmbSL_Code);
                                         }
                                         
                                         ps2.setString(11,txtCheque_DD);
                                         ps2.setString(12, txtCheque_DD_NO);
                                         ps2.setDate(13, che_DD_date);
                                         ps2.setDouble(14,Amt_ind);    
                                         ps2.setString(15,"GJV created on Cheque Dishonour for cheqno:"+txtCheque_DD_NO+"cheqdate:"+che_DD_date+"ReceiptNo:"+txtVoucher_No+"ReceivedFrom:"+paid_to+"ReceiptDate:"+paydate+"");
                                         ps2.setString(16, update_user);
                                         ps2.setTimestamp(17, ts);                         
                                         ps2.setInt(18,cbrefno_trans);
                                         ps2.setDate(19,cbref_transdate);
                                         
                                         SL_NO++;
                                         ps2.executeUpdate();
                                         
                                         xml = xml + "<flag>success</flag>";
                                         System.out.println("here is journal  ********** transaction entry ok");
                                         
                                         
                                         }
                                     } catch (Exception e) {
                                         System.out.println("exception in journal entry:" + e);
                                     }
                            }
                  else{          
                   System.out.println("doctype for journal trns:"+doctype);
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
                            ps2.setString(8,txtCheque_Tran2);
                            ps2.setInt(9,subledgertype_trans);
                            ps2.setInt(10,subledger_trans);
                            ps2.setString(11,txtCheque_DD);
                            ps2.setString(12, txtCheque_DD_NO);
                            ps2.setDate(13, che_DD_date);
                            
                            ps2.setDouble(14,txtAmount);
                            ps2.setString(15,"GJV created on Cheque Dishonour for cheqno:"+txtCheque_DD_NO+"cheqdate:"+che_DD_date+"ReceiptNo:"+txtVoucher_No+"ReceivedFrom:"+paid_to+"ReceiptDate:"+paydate+"");
                           
                            ps2.setString(16, update_user);
                            ps2.setTimestamp(17, ts);
                            
                            ps2.setInt(18,cbrefno_trans);
                            ps2.setDate(19,cbref_transdate);
                            SL_NO++;
                          // max_branch_id++;
                            ps2.executeUpdate();
                            xml = xml + "<flag>success</flag>";
                            System.out.println("here is journaltransaction entry ok");

                        } catch (Exception e) {
                            System.out.println("exception in journal entry:" + e);
                        }
            }
         
            //******updating remittance status No*************
             if (doctype.equalsIgnoreCase("Fund Receipt")) {
             remtype="F";
             System.out.println("remtype===<<<<====="+remtype);
             }
                if (doctype.equalsIgnoreCase("Fund Receipt HO")) {
                remtype="FH";
                System.out.println("remtype===<<<<====="+remtype);
                }
             else{
                 remtype="B";
                 System.out.println("remtype===<<<<====="+remtype);
             }
             {            
              try {
              
              // System.out.println("received_date " + txtDoc_date);

              if (doctype.equalsIgnoreCase("Fund Receipt")) {
              ps = con.prepareStatement ("select CHALLAN_NO,CHALLAN_DATE,RECEIPT_DATE as paydate, '-' as paid_to from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
              "and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and  RECEIPT_DATE=? and RECEIPT_NO=?");

              } 
              else if (doctype.equalsIgnoreCase("Fund Receipt HO")) {
                 ps = con.prepareStatement ("select CHALLAN_NO,CHALLAN_DATE,RECEIPT_DATE as paydate, '-' as paid_to from FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                 "and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and  RECEIPT_DATE=? and RECEIPT_NO=?");

                 } 
              else if (doctype.equalsIgnoreCase("Bank Receipt")) {
                ps =con.prepareStatement("select CHALLAN_NO,CHALLAN_DATE,RECEIPT_DATE as paydate,RECEIVED_FROM as paid_to from FAS_RECEIPT_MASTER \n" + 
                "where  ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" + 
                "and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_DATE=? and RECEIPT_NO=?");
              /* ps = con.prepareStatement ("select b.CHALLAN_NO,b.CHALLAN_DATE,b.RECEIPT_DATE as paydate, a.RECEIVED_FROM as paid_to from FAS_RECEIPT_TRANSACTION a,FAS_RECEIPT_MASTER b where a.RECEIPT_NO=b.RECEIPT_NO and a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? " +
              "and a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=? and b.RECEIPT_DATE=? and a.RECEIPT_NO=? and a.CHEQUE_DD_NO=?");
                    */
              }

              // ps = con.prepareStatement(sql3);
              ps.setInt(1, cmbAcc_UnitCode);
              ps.setInt(2, cmbOffice_code);
              ps.setInt(3, CashBookYear);
              ps.setInt(4, CashBookMonth);
              ps.setDate(5, txtDoc_date);
              ps.setInt(6, txtVoucher_No);
              //  ps.setInt(7, txtCheque_DD_NO);
              rs = ps.executeQuery();


          /*   if (doctype.equalsIgnoreCase("Fund Receipt")) {
             ps = con.prepareStatement ("select CHALLAN_NO,CHALLAN_DATE from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " + 
              "and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and  RECEIPT_DATE=? and RECEIPT_NO=? and CHEQUE_DD_NO=?");

             } else {
             ps = con.prepareStatement ("select b.CHALLAN_NO,b.CHALLAN_DATE from FAS_RECEIPT_TRANSACTION a,FAS_RECEIPT_MASTER b where a.RECEIPT_NO=b.RECEIPT_NO and a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? " + 
              "and a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=? and b.RECEIPT_DATE=? and a.RECEIPT_NO=? and a.CHEQUE_DD_NO=?");

             }

             // ps = con.prepareStatement(sql3);
             ps.setInt(1, cmbAcc_UnitCode);
             ps.setInt(2, cmbOffice_code);
             ps.setInt(3, CashBookYear);
             ps.setInt(4, CashBookMonth);
             ps.setDate(5, txtDoc_date);
             ps.setInt(6, txtVoucher_No);
             ps.setInt(7, txtCheque_DD_NO);
             rs = ps.executeQuery();*/

             System.out.println("txtCrea_date3 " + txtDoc_date);
             System.out.println("outside if loop");

             while (rs.next()) {

          //   System.out.println("indide if loop");
             challan_no=rs.getInt("CHALLAN_NO");
             challan_date=rs.getDate("CHALLAN_DATE");
                 if((rs2.getString("PAID_TO"))==null){
                     paid_to="";
                 }
                 else{
                 paid_to=rs2.getString("PAID_TO");}
        System.out.println("challan_no"+challan_no);
        System.out.println("challan_date"+challan_date);
             }

             } catch (Exception e) {
             System.out.println("catch..HERE.in failure to retrieve." + e);
             xml = xml + "<flag>failure</flag>";
             }
                 try {
                          System.out.println("CHALLAN_DATE"+challan_date);
                          System.out.println("challan_no"+challan_no);
                          System.out.println("inside ");
                          System.out.println("remtype"+remtype);  
                 
                          ps2 = con.prepareStatement("UPDATE FAS_REMITTANCE SET CHEQ_DISHONOUR_STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? " +
                                                     " AND CHALLAN_NO=? and CHALLAN_DATE=? AND REMITTANCE_TYPE=?  ");
                         
                          ps2.setString(1,"Y");
                          ps2.setTimestamp(2, ts);
                          ps2.setString(3, update_user);
                          ps2.setInt(4, cmbAcc_UnitCode);
                          ps2.setInt(5, cmbOffice_code);
                          ps2.setInt(6, CashBookYear);
                          ps2.setInt(7, CashBookMonth);
                          ps2.setInt(8, challan_no);
                          ps2.setDate(9,challan_date);
                          ps2.setString(10,remtype);
                          System.out.println("UPDATE FAS_REMITTANCE SET CHEQ_DISHONOUR_STATUS='Y',UPDATED_DATE='"+ts+"',UPDATED_BY_USER_ID='"+update_user+"' WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" "+
                                          "AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR="+CashBookYear+" AND CASHBOOK_MONTH="+CashBookMonth+" " +
                                          " AND CHALLAN_NO="+challan_no+"  AND REMITTANCE_TYPE="+remtype+"  ");
                         ps2.executeUpdate();
                         ps2.close();
                         xml = xml + "<flag>success</flag>";
                         System.out.println("here is remitance update ok******");
                      } catch (Exception e) {
                          System.out.println("exception in remitance update:" + e);
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
        } catch (IOException e) {
            System.out.println("error in send msg");
        }
    }
}
