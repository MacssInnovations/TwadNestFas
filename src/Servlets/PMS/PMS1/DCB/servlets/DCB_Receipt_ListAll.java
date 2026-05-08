package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DCB_Receipt_ListAll
 */
public class DCB_Receipt_ListAll extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DCB_Receipt_ListAll() {
        super();
        //   Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
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
//            ConnectionString =strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strType = "", xml = "<response>";
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
                
              /*  sql="select distinct 	  	  a.RECEIPT_NO, a.RECEIVED_FROM, a.REMARKS, 	  to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,"+  
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
             	  " a.accounting_for_office_id=?  and   	  a.cashbook_year=? and "+   
             	 " a.cashbook_month=? and  a.created_by_module=? and  b.CHEQ_DISHONOUR_STATUS=? AND a.SUB_LEDGER_TYPE_CODE=14";*/
                
                
                sql="select distinct to_char(r.RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,r.receipt_no,r.received_from,r.total_amount, trim(to_char(r.amount,'99999999999999.99')) as cheque_amount,j.gjv_no,j.gjv_date from "+
                " (select f1.accounting_unit_id,f1.accounting_for_office_id,f1.RECEIPT_DATE,f1.receipt_no,f1.received_from,f1.TOTAL_AMOUNT,f1.amount,f2.CHEQ_DISHONOUR_DATE,f2.DOCUMENT_TYPE from "+
		" (SELECT a.accounting_unit_id, "+
		"  a.accounting_for_office_id,  "+
      "  a.receipt_no, "+
      " a.RECEIPT_DATE, "+
      " a.cashbook_year, "+
      "  a.cashbook_month, "+
      "  a.account_head_code, "+
      "  a.received_from, "+
      "   a.TOTAL_AMOUNT, "+
      "   b.CHEQUE_DD_NO, "+
      "   b.cheq_dishonour_status, "+
      "  b.amount, "+
      "  a.created_by_module "+
      "  FROM fas_receipt_master a, "+
    "  fas_receipt_transaction b "+
      "  WHERE a.accounting_unit_id    =b.accounting_unit_id "+
    "  AND a.accounting_for_office_id=b.accounting_for_office_id "+
    " AND a.cashbook_year           =b.cashbook_year "+
    "  AND a.cashbook_month          =b.cashbook_month "+
    "  AND a.receipt_no              =b.receipt_no "+
    " and a.receipt_status='L' "+
    	"  and a.accounting_unit_id=? " +
    	" and a.accounting_for_office_id=?"+
    "  and a.cashbook_year=? "+
    "  and a.cashbook_month=? "+
    "  and a.created_by_module=? "+
    	" and a.SUB_LEDGER_TYPE_CODE=14 "+
    " )f1 "+
  "  inner join "+
    "   ( select ACCOUNTING_UNIT_ID, "+
    		" ACCOUNTING_FOR_OFFICE_ID, "+
" CASHBOOK_YEAR, "+
" CASHBOOK_MONTH, "+
" DOCUMENT_DATE, "+
" DOCUMENT_NO, "+
" DOCUMENT_TYPE, "+
" CHEQUE_DD_NO, "+
" CHEQUE_DD_DATE, "+
" AMOUNT, "+
" CHEQ_DISHONOUR_DATE from fas_cheque_dishonour)f2 "+
" on f1.accounting_unit_id=f2.accounting_unit_id "+
" AND f1.accounting_for_office_id=f2.accounting_for_office_id "+
  "  AND f1.receipt_no              =f2.document_no " +
  " AND f1.receipt_date            =f2.document_date "+
  " AND f1.cheque_dd_no            =f2.cheque_dd_no "+
  " AND f1.total_amount            =f2.amount "+
  " AND f1.cheq_dishonour_status   =?)r "+
  "  left outer join "+
  "  (SELECT j1.accounting_for_office_id, "+
		  " j1.accounting_unit_id, "+
    " j1.cashbook_month, "+
    " j1.cashbook_year, "+
    "  j2.amount, "+
    "  j2.cb_ref_date, "+
    "  j2.cb_ref_no, "+
    "  j2.voucher_no as gjv_no, "+
    "  to_char(j1.voucher_date,'DD/MM/YYYY') as gjv_date "+
    " FROM fas_journal_master j1, "+
  "   fas_journal_transaction j2 "+
    "  WHERE j2.accounting_for_office_id = j1.accounting_for_office_id "+
  " AND j2.accounting_unit_id         = j1.accounting_unit_id "+
  " AND j2.cashbook_month             = j1.cashbook_month "+
  " AND j2.cashbook_year              = j1.cashbook_year "+
  " AND j2.voucher_no                 = j1.voucher_no "+
  " and j1.journal_status='L' "+
	  " )j "+
  " on  r.accounting_unit_id    =j.accounting_unit_id "+
  " AND r.accounting_for_office_id=j.accounting_for_office_id "+
" AND r.receipt_no              =j.cb_ref_no "+
" AND r.receipt_date            =j.cb_ref_date "+
" AND r.total_amount            =j.amount ";
            }
            else{
            	
           
            sql =
 "select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,COALESCE(NULLIF(REMARKS, ''), NULL) AS REMARKS,RECEIVED_FROM," +
   "trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,REMITTANCE_IN_CURR_MONTH from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and  " +
   "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CREATED_BY_MODULE=? and RECEIPT_STATUS=? AND SUB_LEDGER_TYPE_CODE=14";
            }
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, txtReceipt_type);
                ps.setString(6, cmbStatus);
                xml = xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                int i=1;
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml = xml + "<Rec_no>" + rs.getInt("RECEIPT_NO") + "</Rec_no>";
                    xml = xml + "<Rec_Date>" + rs.getString("rec_date") + "</Rec_Date>";
                    xml = xml + "<Remak>" + rs.getString("RECEIVED_FROM").replace(" ", "-") + "</Remak>";
                    xml = xml + "<Tot_Amt>" + rs.getString("TOTAL_AMOUNT") + "</Tot_Amt>";
                    xml=xml+"<curr_month><![CDATA["+rs.getString("REMITTANCE_IN_CURR_MONTH")+"]]></curr_month>";
                    if (cmbStatus.equalsIgnoreCase("Y")) {
                    	System.out.println("inside "+cmbStatus);
                        xml = xml + "<cheque_amt>" + rs.getString("cheque_amount") + "</cheque_amt>";
                        xml = xml + "<GJV_No>" + rs.getInt("GJV_No") + "</GJV_No>";
                        xml = xml + "<GJV_Date>" + rs.getString("GJV_Date") + "</GJV_Date>";
                    }
                    xml = xml + "</leng>";
                    System.out.println(i+" - "+cmbStatus);
                    i++;
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml = "<response><command>searchByMonth</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml = "<response><command>searchByMonth</command><flag>failure</flag>";
            }

        }
        System.out.println("here " + strType.equalsIgnoreCase("searchByDate"));
        if (strType.equalsIgnoreCase("searchByDate")) {
            xml = "<response><command>searchByDate</command>";
            System.out.println("here " +
                               strType.equalsIgnoreCase("searchByDate"));

            String[] sd = request.getParameter("txtFrom_date").split("/");
            c =   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtFrom_date = new Date(d.getTime());
            System.out.println("from_date " + txtFrom_date);

            sd = request.getParameter("txtTo_date").split("/");
            c =   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            txtTo_date = new Date(d.getTime());
            System.out.println("txtTo_date " + txtTo_date);
            //sql="select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date,REMARKS, TOTAL_AMOUNT from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and  " +
            //"ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_DATE>=? and RECEIPT_DATE<=? and RECEIPT_TYPE=? and RECEIPT_STATUS!='C'";
            if (cmbStatus.equalsIgnoreCase("Y")) {
                System.out.println("Inside cheque cancelled");
                
               /* sql="select distinct 	  	  a.RECEIPT_NO, a.RECEIVED_FROM, a.REMARKS, 	  to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,"+  
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
             	  " a.accounting_for_office_id=?  and   	  a.cashbook_year=? and "+   
             	 " a.cashbook_month=? and a.RECEIPT_DATE>=? and a.RECEIPT_DATE<=? and  a.created_by_module=? and  b.CHEQ_DISHONOUR_STATUS=? AND a.SUB_LEDGER_TYPE_CODE=14";*/
                sql="select distinct to_char(r.RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,r.receipt_no,r.received_from,r.total_amount, trim(to_char(r.amount,'99999999999999.99')) as cheque_amount,j.gjv_no,j.gjv_date from "+
                " (select f1.accounting_unit_id,f1.accounting_for_office_id,f1.RECEIPT_DATE,f1.receipt_no,f1.received_from,f1.TOTAL_AMOUNT,f1.amount,f2.CHEQ_DISHONOUR_DATE,f2.DOCUMENT_TYPE from "+
		" (SELECT a.accounting_unit_id, "+
		"  a.accounting_for_office_id,  "+
      "  a.receipt_no, "+
      " a.RECEIPT_DATE, "+
      " a.cashbook_year, "+
      "  a.cashbook_month, "+
      "  a.account_head_code, "+
      "  a.received_from, "+
      "   a.TOTAL_AMOUNT, "+
      "   b.CHEQUE_DD_NO, "+
      "   b.cheq_dishonour_status, "+
      "  b.amount, "+
      "  a.created_by_module "+
      "  FROM fas_receipt_master a, "+
    "  fas_receipt_transaction b "+
      "  WHERE a.accounting_unit_id    =b.accounting_unit_id "+
    "  AND a.accounting_for_office_id=b.accounting_for_office_id "+
    " AND a.cashbook_year           =b.cashbook_year "+
    "  AND a.cashbook_month          =b.cashbook_month "+
    "  AND a.receipt_no              =b.receipt_no "+
    " and a.receipt_status='L' "+
    	"  and a.accounting_unit_id= ?" +//cmbAcc_UnitCode+
    	" and a.accounting_for_office_id=?"+//cmbOffice_code+
   // "  and a.cashbook_year= ?"+//txtCB_Year+
   // "  and a.cashbook_month=? " +//txtCB_Month+
    " and  a.receipt_date>= ?"+//txtFrom_date+"'"+
    " and  a.receipt_date<= ?"+//txtTo_date+"'"+
    "  and a.created_by_module= ?"+//txtReceipt_type+"'"+
    	" and a.SUB_LEDGER_TYPE_CODE=14 "+
    " )f1 "+
  "  inner join "+
    "   ( select ACCOUNTING_UNIT_ID, "+
    		" ACCOUNTING_FOR_OFFICE_ID, "+
" CASHBOOK_YEAR, "+
" CASHBOOK_MONTH, "+
" DOCUMENT_DATE, "+
" DOCUMENT_NO, "+
" DOCUMENT_TYPE, "+
" CHEQUE_DD_NO, "+
" CHEQUE_DD_DATE, "+
" AMOUNT, "+
" CHEQ_DISHONOUR_DATE from fas_cheque_dishonour)f2 "+
" on f1.accounting_unit_id=f2.accounting_unit_id "+
" AND f1.accounting_for_office_id=f2.accounting_for_office_id "+
  "  AND f1.receipt_no              =f2.document_no " +
  " AND f1.receipt_date            =f2.document_date "+
  " AND f1.cheque_dd_no            =f2.cheque_dd_no "+
  " AND f1.total_amount            =f2.amount "+
  " AND f1.cheq_dishonour_status   =?)r "+
  "  left outer join "+
  "  (SELECT j1.accounting_for_office_id, "+
		  " j1.accounting_unit_id, "+
    " j1.cashbook_month, "+
    " j1.cashbook_year, "+
    "  j2.amount, "+
    "  j2.cb_ref_date, "+
    "  j2.cb_ref_no, "+
    "  j2.voucher_no as gjv_no, "+
    "  to_char(j1.voucher_date,'DD/MM/YYYY') as gjv_date "+
    " FROM fas_journal_master j1, "+
  "   fas_journal_transaction j2 "+
    "  WHERE j2.accounting_for_office_id = j1.accounting_for_office_id "+
  " AND j2.accounting_unit_id         = j1.accounting_unit_id "+
  " AND j2.cashbook_month             = j1.cashbook_month "+
  " AND j2.cashbook_year              = j1.cashbook_year "+
  " AND j2.voucher_no                 = j1.voucher_no "+
  " and j1.journal_status='L' "+
	  " )j "+
  " on  r.accounting_unit_id    =j.accounting_unit_id "+
  " AND r.accounting_for_office_id=j.accounting_for_office_id "+
" AND r.receipt_no              =j.cb_ref_no "+
" AND r.receipt_date            =j.cb_ref_date "+
" AND r.total_amount            =j.amount ";
            }
            else{
            sql =
 "select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date,REMARKS,RECEIVED_FROM, trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,REMITTANCE_IN_CURR_MONTH from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and  " +
   "ACCOUNTING_FOR_OFFICE_ID=? " +
   //"and CASHBOOK_YEAR=? " +
   //"and CASHBOOK_MONTH=? " +
   "and RECEIPT_DATE>=? and RECEIPT_DATE<=? and CREATED_BY_MODULE=? and RECEIPT_STATUS=? AND SUB_LEDGER_TYPE_CODE=14";
            }
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
                xml = xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml = xml + "<Rec_no>" + rs.getInt("RECEIPT_NO") + "</Rec_no>";
                    xml = xml + "<Rec_Date>" + rs.getString("rec_date") + "</Rec_Date>";
                    xml = xml + "<Remak>" + rs.getString("RECEIVED_FROM") + "</Remak>";
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
                    xml = "<response><command>searchByDate</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml = "<response><command>searchByDate</command><flag>failure</flag>";
            }
        }
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        //   Auto-generated method stub
    }

}
