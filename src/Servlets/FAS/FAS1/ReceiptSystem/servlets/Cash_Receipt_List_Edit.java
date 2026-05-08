package Servlets.FAS.FAS1.ReceiptSystem.servlets;

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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Cash_Receipt_List_Edit extends HttpServlet {
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
        ResultSet rs = null;
        Statement stmt = null;
        PreparedStatement ps = null, ps2 = null;
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
        System.out.println("servlet called");
        
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("cache-control","no-cache");
        
    
        
        String strType = "", xml = "";
        try {
            strType = request.getParameter("Command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0;
        Date txtFrom_date = null, txtTo_date = null;
        Calendar c;
        String sql = "", txtReceipt_type = "", cmbStatus = "";


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
        System.out.println("strtype  " + strType);
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);
        txtReceipt_type = request.getParameter("txtReceipt_type");
        // after receipt status
        cmbStatus = request.getParameter("cmbStatus");

        System.out.println("cmbStatus.." + cmbStatus);
        if (strType.equalsIgnoreCase("searchByMonth")) {
            xml = "<response><command>searchByMonth</command>";

            /*
             sql="select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,REMARKS," +
            "TOTAL_AMOUNT from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and  " +
            "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_TYPE=? and RECEIPT_STATUS!='C'";
            */
            if (cmbStatus.equalsIgnoreCase("Y")) {
                System.out.println("Inside cheque cancelled");
                sql="select distinct 	  	  a.RECEIPT_NO, a.RECEIVED_FROM,  	  to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,"+  
   	  " trim(to_char(a.total_amount,'99999999999999.99')) as total_amount,a.ACCOUNT_HEAD_CODE,  "+ 
   	 " b.particulars as remarks, "+  
   	  " trim(to_char(b.amount,'99999999999999.99')) as cheque_amount,  "+ 
   	  " d.voucher_no as GJV_No,  "+ 
   	  " to_char(c.voucher_date,'DD/MM/YYYY') as gjv_date   from "+   
   	  " fas_receipt_master a,   	  fas_receipt_transaction b,  "+ 
   	  " fas_journal_master c,   	  fas_journal_transaction d    where"+   
   	  " a.accounting_unit_id=b.accounting_unit_id and  "+ 
   	  " a.accounting_for_office_id=b.accounting_for_office_id and"+   
   	  " a.cashbook_year=b.cashbook_year and  "+ 
   	  " a.cashbook_month=b.cashbook_month and  "+ 
   	  " a.RECEIPT_NO=b.RECEIPT_NO and  "+ 
   	  " a.accounting_unit_id=d.accounting_unit_id and"+   
   	  " a.accounting_for_office_id=d.accounting_for_office_id and"+   
   	  " a.RECEIPT_NO=d.cb_ref_no and  "+ 
   	  " a.RECEIPT_DATE=d.cb_ref_date and "+  
   	 " d.accounting_unit_id=c.accounting_unit_id and"+   
   	  " d.accounting_for_office_id=c.accounting_for_office_id and"+   
   	  " d.cashbook_year=c.cashbook_year and  "+ 
   	  " d.cashbook_month=c.cashbook_month and  "+ 
   	 " d.voucher_no=c.voucher_no and "+ 
     " a.accounting_unit_id="+cmbAcc_UnitCode+" and   "+ 
   	  " a.accounting_for_office_id="+cmbOffice_code+" and   	  a.cashbook_year="+txtCB_Year+" and"+   
   	 " a.cashbook_month="+txtCB_Month+"  and b.CHEQ_DISHONOUR_STATUS='"+cmbStatus+"' and  a.created_by_module='"+txtReceipt_type+"' AND a.SUB_LEDGER_TYPE_CODE!=14";
           /* sql="select distinct 	  	  a.RECEIPT_NO, a.RECEIVED_FROM,a.account_no,to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,  "+
            "  trim(to_char(a.total_amount,'99999999999999.99')) as total_amount,a.ACCOUNT_HEAD_CODE,   "+
             	  "  b.particulars as remarks,   "+
             	 "  trim(to_char(b.amount,'99999999999999.99')) as cheque_amount,   "+
             	  "  d.voucher_no as GJV_No,   "+
             	  "  to_char(c.voucher_date,'DD/MM/YYYY') as gjv_date, fm.AC_OPERATIONAL_MODE_ID   from    "+
             	  "  fas_receipt_master a,   	  fas_receipt_transaction b,   "+
             	  "  fas_journal_master c,   	  fas_journal_transaction d,fas_cheque_dishonour e,FAS_OFFICE_BANK_AC_CURRENT fm  where   "+
             	  "  a.accounting_unit_id=b.accounting_unit_id and   "+
             	  "   a.accounting_for_office_id=b.accounting_for_office_id and "+  
             	  "   a.cashbook_year=b.cashbook_year and   "+
             	  "  a.cashbook_month=b.cashbook_month and   "+
             	  "  a.RECEIPT_NO=b.RECEIPT_NO and   "+
             	  "  a.accounting_unit_id=d.accounting_unit_id and   "+
             	  "  a.accounting_for_office_id=d.accounting_for_office_id and  "+ 
             	  "  a.RECEIPT_NO=d.cb_ref_no and   "+
             	  " a.RECEIPT_DATE=d.cb_ref_date and   "+
             	  "  d.accounting_unit_id=c.accounting_unit_id and   "+
             	 "  d.accounting_for_office_id=c.accounting_for_office_id and  "+ 
             	  "  d.cashbook_year=c.cashbook_year and  "+ 
             	  "  d.cashbook_month=c.cashbook_month and  "+ 
             	  "   d.voucher_no=c.voucher_no and "+
             	 "    a.accounting_unit_id=e.accounting_unit_id "+
                 " AND a.accounting_for_office_id=e.accounting_for_office_id "+
                 "   AND a.receipt_no              =e.DOC_NO  "+
                 "  AND a.receipt_date            =e.document_date "+
                 "  and b.cheque_dd_no            =e.cheque_dd_no "+
                 "  and a.total_amount            =e.amount "+
                 " and a.account_head_code=fm.ac_head_code " +
                 " and a.bank_id=fm.bank_id " +
                 "  and a.branch_id=fm.branch_id " +
                 " and a.account_no=fm.bank_ac_no " +
                 " and a.accounting_unit_id=fm.accounting_unit_id " +
                 " and fm.module_id='MF004' " +
                 "  and a.accounting_unit_id=? and    "+
                 "    a.accounting_for_office_id=? and  "+
             	 "   a.cashbook_year=?  and         	 a.cashbook_month=? and "+
                 " 	  a.created_by_module=?  and  a.CHEQ_DISHONOUR_STATUS=?  AND a.SUB_LEDGER_TYPE_CODE!=14";
            */
            
            }else {
            	
            
            /*sql =
 "select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,RECEIVED_FROM," +
 " trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,ACCOUNT_HEAD_CODE from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  " +
 " ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and " +
 " CASHBOOK_MONTH="+txtCB_Month+" and CREATED_BY_MODULE='"+txtReceipt_type+"' and RECEIPT_STATUS='"+cmbStatus+"' " +
 " AND SUB_LEDGER_TYPE_CODE!=14";
            */
            
           /* sql =
            	 "select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,RECEIVED_FROM," +
            	 " trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,ACCOUNT_HEAD_CODE," +
            	 "account_no,fm.AC_OPERATIONAL_MODE_ID from FAS_RECEIPT_MASTER m," +
            	 "FAS_OFFICE_BANK_AC_CURRENT fm " +
            	 "where " +
            	 " m.account_head_code=fm.ac_head_code "+
            	 "  and m.bank_id=fm.bank_id "+
            	 " and m.branch_id=fm.branch_id "+
            	 " and m.account_no=fm.bank_ac_no "+
            	 " and m.accounting_unit_id=fm.accounting_unit_id "+
            	 "  and fm.module_id='MF004' "+
            	 " and m.ACCOUNTING_UNIT_ID=? and  " +
            	 " m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and " +
            	 " m.CASHBOOK_MONTH=? and m.CREATED_BY_MODULE=? and m.RECEIPT_STATUS=? " +
            	
            	 " AND m.SUB_LEDGER_TYPE_CODE!=14";*/
            	sql =
               	 "select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,RECEIVED_FROM," +
               	 " trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,ACCOUNT_HEAD_CODE," +
               	 "account_no,REMITTANCE_IN_CURR_MONTH from FAS_RECEIPT_MASTER m " +
               	
               	 " where " +
               	
               	 "  m.ACCOUNTING_UNIT_ID=? and  " +
               	 " m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and " +
               	 " m.CASHBOOK_MONTH=? and m.CREATED_BY_MODULE=? and m.RECEIPT_STATUS=? and m.SUB_LEDGER_TYPE_CODE!=14 " ;
               	
               	 //" AND m.SUB_LEDGER_TYPE_CODE!=14";
        }
System.out.println("sql:::"+sql);
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, txtReceipt_type);
                ps.setString(6, cmbStatus); 
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =xml + "<leng>";
                    xml =xml + "<Rec_no>" + rs.getInt("RECEIPT_NO") + "</Rec_no>";
                    xml =xml + "<Rec_Date>" + rs.getString("rec_date") + "</Rec_Date>";
                  //  xml =xml + "<accno>" + rs.getLong("account_no") +"-"+rs.getString("AC_OPERATIONAL_MODE_ID") +"</accno>";
                    xml =xml + "<accno>" + rs.getLong("account_no") +"</accno>";
                    xml=xml+"<Remak><![CDATA["+rs.getString("RECEIVED_FROM")+"]]></Remak>";
                    xml =xml + "<Tot_Amt>" + rs.getString("TOTAL_AMOUNT") + "</Tot_Amt>";
                    xml =xml + "<achead>" + rs.getInt("ACCOUNT_HEAD_CODE") + "</achead>";
                    xml=xml+"<curr_month><![CDATA["+rs.getString("REMITTANCE_IN_CURR_MONTH")+"]]></curr_month>";
                    if (cmbStatus.equalsIgnoreCase("Y")) {
                        xml = xml + "<cheque_amt>" + rs.getString("cheque_amount") + "</cheque_amt>";
                        xml = xml + "<GJV_No>" + rs.getInt("GJV_No") + "</GJV_No>";
                        xml = xml + "<GJV_Date>" + rs.getString("GJV_Date") + "</GJV_Date>";
                    }
                    xml =xml + "</leng>";   
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
            }

        }
      //  System.out.println("here " + strType.equalsIgnoreCase("searchByDate"));
        if (strType.equalsIgnoreCase("searchByDate")) {
            xml = "<response><command>searchByDate</command>";
            System.out.println("here " +
                               strType.equalsIgnoreCase("searchByDate"));

            String[] sd = request.getParameter("txtFrom_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtFrom_date = new Date(d.getTime());
            System.out.println("from_date " + txtFrom_date);

            sd = request.getParameter("txtTo_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            txtTo_date = new Date(d.getTime());
            System.out.println("txtTo_date " + txtTo_date);
            //sql="select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date,REMARKS, TOTAL_AMOUNT from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and  " +
            //"ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_DATE>=? and RECEIPT_DATE<=? and RECEIPT_TYPE=? and RECEIPT_STATUS!='C'";
            if (cmbStatus.equalsIgnoreCase("Y")) {
                System.out.println("Inside cheque cancelled");
                
                /*sql="select distinct 	  	  a.RECEIPT_NO, a.RECEIVED_FROM,  	  to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,"+  
             	  " trim(to_char(a.total_amount,'99999999999999.99')) as total_amount,a.ACCOUNT_HEAD_CODE,  "+ 
             	 " b.particulars as remarks, "+  
             	  " trim(to_char(b.amount,'99999999999999.99')) as cheque_amount,  "+ 
             	  " d.voucher_no as GJV_No,  "+ 
             	  " to_char(c.voucher_date,'DD/MM/YYYY') as gjv_date   from "+   
             	  " fas_receipt_master a,   	  fas_receipt_transaction b,  "+ 
             	  " fas_journal_master c,   	  fas_journal_transaction d    where"+   
             	  " a.accounting_unit_id=b.accounting_unit_id and  "+ 
             	  " a.accounting_for_office_id=b.accounting_for_office_id and"+   
             	  " a.cashbook_year=b.cashbook_year and  "+ 
             	  " a.cashbook_month=b.cashbook_month and  "+ 
             	  " a.RECEIPT_NO=b.RECEIPT_NO and  "+ 
             	  " a.accounting_unit_id=d.accounting_unit_id and"+   
             	  " a.accounting_for_office_id=d.accounting_for_office_id and"+   
             	  " a.RECEIPT_NO=d.cb_ref_no and  "+ 
             	  " a.RECEIPT_DATE=d.cb_ref_date and "+  
             	 " d.accounting_unit_id=c.accounting_unit_id and"+   
             	  " d.accounting_for_office_id=c.accounting_for_office_id and"+   
             	  " d.cashbook_year=c.cashbook_year and  "+ 
             	  " d.cashbook_month=c.cashbook_month and  "+ 
             	 " d.voucher_no=c.voucher_no and "+ 
               " a.accounting_unit_id=? and   "+ 
             	  " a.accounting_for_office_id=? and  a.RECEIPT_DATE>=? and a.RECEIPT_DATE<=? AND	"+   
             	 " a.created_by_module=?  and  b.CHEQ_DISHONOUR_STATUS=?  AND a.SUB_LEDGER_TYPE_CODE!=14";  
                */
                /*  sql="select distinct 	  	  a.RECEIPT_NO, a.RECEIVED_FROM,a.account_no,to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date , "+ 
                "   trim(to_char(a.total_amount,'99999999999999.99')) as total_amount,a.ACCOUNT_HEAD_CODE,   "+
             	  "  b.particulars as remarks,   "+
             	 "   trim(to_char(b.amount,'99999999999999.99')) as cheque_amount,   "+
             	  "   d.voucher_no as GJV_No,   "+
             	  "    to_char(c.voucher_date,'DD/MM/YYYY') as gjv_date, fm.AC_OPERATIONAL_MODE_ID   from    "+
             	  "   fas_receipt_master a,   	  fas_receipt_transaction b,   "+
             	  "  fas_journal_master c,   	  fas_journal_transaction d,fas_cheque_dishonour e,FAS_OFFICE_BANK_AC_CURRENT fm  where  "+ 
             	  "  a.accounting_unit_id=b.accounting_unit_id and   "+
             	  "  a.accounting_for_office_id=b.accounting_for_office_id and   "+
             	  "  a.cashbook_year=b.cashbook_year and   "+
             	  "  a.cashbook_month=b.cashbook_month and   "+
             	  "  a.RECEIPT_NO=b.RECEIPT_NO and   "+
             	  "  a.accounting_unit_id=d.accounting_unit_id and   "+
             	  " a.accounting_for_office_id=d.accounting_for_office_id and   "+
             	  " a.RECEIPT_NO=d.cb_ref_no and   "+
             	  "   a.RECEIPT_DATE=d.cb_ref_date and   "+
             	  "   d.accounting_unit_id=c.accounting_unit_id and   "+
             	 "   d.accounting_for_office_id=c.accounting_for_office_id and   "+
             	  "  d.cashbook_year=c.cashbook_year and  "+ 
             	  "   d.cashbook_month=c.cashbook_month and  "+ 
             	  " 	  d.voucher_no=c.voucher_no and "+
             	 "        a.accounting_unit_id=e.accounting_unit_id "+
                 " AND a.accounting_for_office_id=e.accounting_for_office_id "+
 "  AND a.receipt_no              =e.doc_no  "+
    "  AND a.receipt_date            =e.document_date "+
   "  and b.cheque_dd_no            =e.cheque_dd_no "+
   "  and a.total_amount            =e.amount "+
   " and a.account_head_code=fm.ac_head_code " +
   " and a.bank_id=fm.bank_id " +
   "  and a.branch_id=fm.branch_id " +
   " and a.account_no=fm.bank_ac_no " +
   " and a.accounting_unit_id=fm.accounting_unit_id " +
   " and fm.module_id='MF004' " +
   "  and         a.accounting_unit_id=? and    "+
   " 	   a.accounting_for_office_id=? and  "+
   "     a.receipt_date>=? and a.receipt_date<=? and	  "+ 
             	"  a.created_by_module=?  and  a.CHEQ_DISHONOUR_STATUS=?  AND a.SUB_LEDGER_TYPE_CODE!=14";
                */
                sql="select distinct 	  	  a.RECEIPT_NO, a.RECEIVED_FROM,a.account_no,to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date , "+ 
                "   trim(to_char(a.total_amount,'99999999999999.99')) as total_amount,a.ACCOUNT_HEAD_CODE,   "+
             	  "  b.particulars as remarks,   "+
             	 "   trim(to_char(b.amount,'99999999999999.99')) as cheque_amount,   "+
             	  "   d.voucher_no as GJV_No,   "+
             	  "    to_char(c.voucher_date,'DD/MM/YYYY') as gjv_date   from    "+
             	  "   fas_receipt_master a,   	  fas_receipt_transaction b,   "+
             	  "  fas_journal_master c,   	  fas_journal_transaction d,fas_cheque_dishonour e  where  "+ 
             	  "  a.accounting_unit_id=b.accounting_unit_id and   "+
             	  "  a.accounting_for_office_id=b.accounting_for_office_id and   "+
             	  "  a.cashbook_year=b.cashbook_year and   "+
             	  "  a.cashbook_month=b.cashbook_month and   "+
             	  "  a.RECEIPT_NO=b.RECEIPT_NO and   "+
             	  "  a.accounting_unit_id=d.accounting_unit_id and   "+
             	  " a.accounting_for_office_id=d.accounting_for_office_id and   "+
             	  " a.RECEIPT_NO=d.cb_ref_no and   "+
             	  "   a.RECEIPT_DATE=d.cb_ref_date and   "+
             	  "   d.accounting_unit_id=c.accounting_unit_id and   "+
             	 "   d.accounting_for_office_id=c.accounting_for_office_id and   "+
             	  "  d.cashbook_year=c.cashbook_year and  "+ 
             	  "   d.cashbook_month=c.cashbook_month and  "+ 
             	  " 	  d.voucher_no=c.voucher_no and "+
             	 "        a.accounting_unit_id=e.accounting_unit_id "+
                 " AND a.accounting_for_office_id=e.accounting_for_office_id "+
 "  AND a.receipt_no              =e.doc_no  "+
    "  AND a.receipt_date            =e.document_date "+
   "  and b.cheque_dd_no            =e.cheque_dd_no "+
   "  and a.total_amount            =e.amount "+
  
   "  and         a.accounting_unit_id=? and    "+
   " 	   a.accounting_for_office_id=? and  "+
   "     a.receipt_date>=? and a.receipt_date<=? and	  "+ 
             	"  a.created_by_module=?  and  a.CHEQ_DISHONOUR_STATUS=?  AND a.SUB_LEDGER_TYPE_CODE!=14";
                
            }else{
            sql =
 "select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date,RECEIVED_FROM, " +
 "trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,ACCOUNT_HEAD_CODE,account_no,REMITTANCE_IN_CURR_MONTH " +
 
 " from FAS_RECEIPT_MASTER m where " +
 " m.ACCOUNTING_UNIT_ID=? and  " +
   "m.ACCOUNTING_FOR_OFFICE_ID=? " +
   "and m.RECEIPT_DATE>=? and m.RECEIPT_DATE<=? " +
   "and m.CREATED_BY_MODULE=? and m.RECEIPT_STATUS=? " +
   "AND m.SUB_LEDGER_TYPE_CODE!=14";
            }
            System.out.println("sql::::"+sql);
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
              //  ps.setInt(3, txtCB_Year);
              //  ps.setInt(4, txtCB_Month);
                ps.setDate(3, txtFrom_date);
                ps.setDate(4, txtTo_date);
                ps.setString(5, txtReceipt_type);
                ps.setString(6, cmbStatus);
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml = xml + "<Rec_no>" + rs.getInt("RECEIPT_NO") + "</Rec_no>";
                    xml = xml + "<Rec_Date>" + rs.getString("rec_date") + "</Rec_Date>";
                  //  xml =xml + "<accno>" + rs.getLong("account_no") +"-"+rs.getString("AC_OPERATIONAL_MODE_ID")+ "</accno>";
                    xml =xml + "<accno>" + rs.getLong("account_no") +"</accno>";
                    xml=xml+"<Remak><![CDATA["+rs.getString("RECEIVED_FROM")+"]]></Remak>";
                    xml =xml + "<achead>" + rs.getInt("ACCOUNT_HEAD_CODE") + "</achead>";
                    xml = xml + "<Tot_Amt>" + rs.getString("TOTAL_AMOUNT") + "</Tot_Amt>";
                    xml=xml+"<curr_month><![CDATA["+rs.getString("REMITTANCE_IN_CURR_MONTH")+"]]></curr_month>";
                    if (cmbStatus.equalsIgnoreCase("Y")) {
                        xml = xml + "<cheque_amt>" + rs.getString("cheque_amount") + "</cheque_amt>";
                        xml = xml + "<GJV_No>" + rs.getInt("GJV_No") + "</GJV_No>";
                        xml = xml + "<GJV_Date>" + rs.getString("GJV_Date") + "</GJV_Date>";
                    }
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
            }
        }
        xml = xml + "</response>";
        out.write(xml);
        System.out.println(xml);
    }
}
