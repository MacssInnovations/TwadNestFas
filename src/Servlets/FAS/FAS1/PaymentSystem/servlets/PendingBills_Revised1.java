
package Servlets.FAS.FAS1.PaymentSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS_PAYMENT;

public class PendingBills_Revised1 extends HttpServlet {
/**
*
*/
private static final long serialVersionUID = 1L;
private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

public void init(ServletConfig config) throws ServletException {
super.init(config);
}

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

/**
* Session Checking
*/

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

/**
* Variables Declaration
*/

Connection con = null;
ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null, rsbank = null;
PreparedStatement ps = null, ps2 = null, ps3 = null,ps4 = null, psbank = null,psamt = null;
String xml = "<response>";
response.setContentType(CONTENT_TYPE);
response.setHeader("Cache-Control", "no-cache");
PrintWriter out = response.getWriter();
String strCommand = "";
int cmbAcc_HeadCode = 0, cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0 ,
selvouNo = 0,selsNo = 0,selslType = 0,selslCode = 0,selslAmt = 0,balAmt = 0,rowId = 0;
Calendar c;
Date txtCrea_date = null, frmDate = null, toDate = null,selvouDate = null;

/**
* Database Connection
*/

try {
ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
String ConnectionString = "";

String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
String strdsn = rs1.getString("Config.DSN");
String strhostname = rs1.getString("Config.HOST_NAME");
String strportno = rs1.getString("Config.PORT_NUMBER");
String strsid = rs1.getString("Config.SID");
String strdbusername = rs1.getString("Config.USER_NAME");
String strdbpassword = rs1.getString("Config.PASSWORD");
ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection Class.forName(strDriver.trim());
con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
} catch (Exception e) {
System.out.println("Exception in opening connection :" + e);

}


try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }



if(strCommand.equalsIgnoreCase("load_journalAmt"))
        {
String CONTENT_TYPE = "text/xml; charset=windows-1252";
response.setContentType(CONTENT_TYPE);
xml=xml+"<command>load_journalAmt</command>";
//String selvouDate = request.getParameter("selvouDate");
String[] sd1 = request.getParameter("selvouDate").split("/");
c = new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
java.util.Date d1 = c.getTime();
selvouDate = new Date(d1.getTime());
selvouNo =Integer.parseInt(request.getParameter("selvouNo"));
selsNo=Integer.parseInt(request.getParameter("selsNo"));
selslType=Integer.parseInt(request.getParameter("selslType"));
    selslCode=Integer.parseInt(request.getParameter("selslCode"));
    double dob = Double.parseDouble(request.getParameter("selslAmt"));
    selslAmt=(int) dob;
    rowId=Integer.parseInt(request.getParameter("row_id"));
    //rowId=rowId+1;
   

try {
cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
} catch (NumberFormatException e) {
System.out.println("exception" + e);
}
// System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

/** Get Accounting Office ID */
try {
cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
} catch (NumberFormatException e) {
System.out.println("exception" + e);
}

String sql="SELECT t.voucher_no,\r\n" +
"  m.payment_date,\r\n" +
"  t.amount\r\n" +
"FROM fas_payment_master m,\r\n" +
"  fas_payment_transaction t\r\n" +
"WHERE m.accounting_unit_id    =t.accounting_unit_id\r\n" +
"AND m.accounting_for_office_id=t.accounting_for_office_id\r\n" +
"AND m.cashbook_year           =t.cashbook_year\r\n" +
"AND m.cashbook_month          =t.cashbook_month\r\n" +
"AND m.voucher_no              =t.voucher_no\r\n" +
"AND m.payment_status          ='L' " +
"AND t.accounting_unit_id      =? "  +
"AND t.accounting_for_office_id=? "  +
" AND t.payable_voucher_no     =? " +
" AND t.payable_voucher_date   =? " +
" AND t.payable_voucher_slno  =? " +
" AND t.sub_ledger_type_code =? " +
" AND t.sub_ledger_code  =?  " ;
/* " AND t.amount      =? " ;*/
System.out.println("Query" + sql);
try {
psamt = con.prepareStatement(sql);
psamt.setInt(1, cmbAcc_UnitCode);
psamt.setInt(2, cmbOffice_code);
psamt.setInt(3, selvouNo);
psamt.setDate(4, selvouDate);
psamt.setInt(5, selsNo);
psamt.setInt(6, selslType);
psamt.setInt(7, selslCode);
/*psamt.setInt(8, selslAmt);*/
rs = psamt.executeQuery();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

try {
int[] getAmt =new int[10];
int count=0 ,sumgetAmt=0;

while (rs.next()) {
getAmt[count] = rs.getInt("amount");
count++;
}
for (int i = 0; i < count; i++) {
sumgetAmt = getAmt[i] + sumgetAmt;
}
if (!(sumgetAmt == 0)) {
if (selslAmt == sumgetAmt) {
xml = xml + "<status>fullpmt</status><rowid>" + rowId + "</rowid>";
} else if (selslAmt > sumgetAmt) {
balAmt = selslAmt - sumgetAmt;
xml = xml + "<status>partpmt</status><rowid>" + rowId + "</rowid>";
xml = xml + "<paidedAmt>" + balAmt + "</paidedAmt>";

}
else if (selslAmt < sumgetAmt) {
// balAmt = selslAmt - sumgetAmt;
xml = xml + "<status>excesspmt</status><rowid>" + rowId + "</rowid>";
/* xml = xml + "<paidedAmt>" + balAmt + "</paidedAmt>";*/
}
} else if (sumgetAmt == 0) {
xml = xml + "<status>notpaid</status><rowid>" + rowId + "</rowid>";
}

} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
finally
{
try {
rs.close();
psamt.close();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

}


        }

else if(strCommand.equalsIgnoreCase("get_Voucher")){

        xml = "<response><command>get_Voucher</command>";

/** Get Accounting Unit ID */

try {
cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
} catch (NumberFormatException e) {
System.out.println("exception" + e);
}
// System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

/** Get Accounting Office ID */
try {
cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
} catch (NumberFormatException e) {
System.out.println("exception" + e);
}
// System.out.println("cmbOffice_code " + cmbOffice_code);

/** GEt Data */
String[] sd = request.getParameter("txtCrea_date").split("/");
c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
java.util.Date d = c.getTime();
txtCrea_date = new Date(d.getTime());
System.out.println("txtCrea_date " + txtCrea_date);

/** Find Year and month from date */
// System.out.println("b4 getting month and year");
try {
txtCash_year = Integer.parseInt(sd[2]);
} catch (Exception e) {
System.out.println("exception" + e);
}
// System.out.println("txtCash_year " + txtCash_year);
try {
txtCash_Month_hid = Integer.parseInt(sd[1]);
} catch (Exception e) {
System.out.println("exception" + e);
}
// System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

// int row=0;
try {

String CONTENT_TYPE = "text/xml; charset=windows-1252";
response.setContentType(CONTENT_TYPE);
int cmbMas_SL_type = 0, cmbMaS_SL_code = 0;

try {
cmbMas_SL_type = Integer.parseInt(request.getParameter("cmbMas_SL_type"));
} catch (NumberFormatException e) {
System.out.println("exception" + e);
}
// System.out.println("cmbMas_SL_type " + cmbMas_SL_type);

try {
cmbMaS_SL_code = Integer.parseInt(request.getParameter("cmbMaS_SL_code"));
} catch (NumberFormatException e) {
System.out.println("exception" + e);
}
// System.out.println("cmbMaS_SL_code " + cmbMaS_SL_code);
String[] sd1 = request.getParameter("frmDate").split("/");
c = new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
java.util.Date d1 = c.getTime();
frmDate = new Date(d1.getTime());
System.out.println("frmDate " + frmDate);

String[] sd2 = request.getParameter("toDate").split("/");
c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1, Integer.parseInt(sd2[0]));
java.util.Date d2 = c.getTime();
toDate = new Date(d2.getTime());
System.out.println("toDate " + toDate);
// for pending bills for all sltype the office id combination with
// accounting unit is to be removed
// under the KT 29Nov2017
if (cmbMas_SL_type == 11|| cmbMas_SL_type == 7 || cmbMas_SL_type == 2 || cmbMas_SL_type == 9
|| cmbMas_SL_type == 96 || cmbMas_SL_type == 1 || cmbMas_SL_type == 84 ) // on
// 29th
// jun
// (if

// 11-Contractors
// 7-Employee Journal
// 2-Firms
// 9-Other Departments
// 96-Rent Journal
// 1-Supplier
// 84-TWAD CPS Journal)
// 17-Miscellaneous

if(cmbMas_SL_type==96)cmbMas_SL_type=17;
{
System.out.println("cmbMas_SL_type1 " + cmbMas_SL_type);
/*Developed by Nanda Kumar*/
String query =  
"select * from (SELECT fin.VOUCHER_NO, "
+ "  fin.SL_NO, "
+ "  fin.office_id, "
+ "  fin.CB_REF_DATE, "
+ "  fin.VOUCHER_DATE, "
+ "  fin.vou_date, "
+ "  fin.vouType, "
+ "  fin.ACCOUNT_HEAD_CODE , "
+ "  fin.cr_dr_indicator , "
+ "  fin.SUB_LEDGER_TYPE_CODE , "
+ "  fin.SUB_LEDGER_CODE, "
+ "  fin.BILL_NO, "
+ "  fin.BILL_TYPE, "
+ "  fin.AGREEMENT_NO, "
+ "  fin.agree_date, "
+ "  fin.b_date , "
+ "  trim(TO_CHAR(fin.AMOUNT::NUMERIC,'99999999999999D99')::VARCHAR)         AS AMOUNT , "
+ "  trim(TO_CHAR((AMOUNT::NUMERIC - pay_amt)::NUMERIC,'99999999999999.99')::VARCHAR) AS vrAMOUNT , "
+ "  fin.JOURNAL_TYPE_CODE, "
+ "  fin.PARTICULARS, "
+ "  fin.MULTIPLE_PVR_DETAILS "
+ "FROM ( "
+ "  (SELECT chk.VOUCHER_NO, "
+ "    chk.SL_NO, "
+ "    chk.office_id, "
+ "    chk.CB_REF_DATE, "
+ "    chk.VOUCHER_DATE, "
+ "    chk.vou_date, "
+ "    chk.vouType, "
+ "    chk.ACCOUNT_HEAD_CODE , "
+ "    chk.cr_dr_indicator , "
+ "    chk.SUB_LEDGER_TYPE_CODE , "
+ "    chk.SUB_LEDGER_CODE, "
+ "    chk.BILL_NO, "
+ "    chk.BILL_TYPE, "
+ "    chk.AGREEMENT_NO, "
+ "    chk.agree_date, "
+ "    chk.b_date , "
+ "    chk.AMOUNT, "
+ "    chk.JOURNAL_TYPE_CODE, "
+ "    chk.PARTICULARS, "
+ "    chk.MULTIPLE_PVR_DETAILS, "
+ "    p.payable_voucher_no    AS payable_voucher_no, "
+ "    SUM(chk.PARTIAL_AMOUNT) AS PAY_AMT, "
+ "    chk.CHEQUE_DD_NO, "
+ "    chk.CB_REF_NO "
+ "  FROM "
+ "    (SELECT  trs.VOUCHER_NO, "
+ "      trs.SL_NO, "
+ "  mas.accounting_for_office_id as office_id, "
+ "      pt.amount       AS PARTIAL_AMOUNT, "
+ "      pm.payment_date AS TRANSACTION_DATE, "
+ "      mas.VOUCHER_DATE, "
+ "      TO_CHAR(mas.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date, "
+ "      mas.CREATED_BY_MODULE                  AS vouType, "
+ "      trs.ACCOUNT_HEAD_CODE , "
+ "      CASE WHEN (trs.CR_DR_INDICATOR='DR') THEN 'CR'WHEN (trs.CR_DR_INDICATOR='CR') THEN 'DR' END  AS cr_dr_indicator ,  "
+ "      trs.SUB_LEDGER_TYPE_CODE , "
+ "      trs.SUB_LEDGER_CODE, "
+ "      trs.BILL_NO, "
+ "      trs.BILL_TYPE, "
+ "      trs.AGREEMENT_NO, "
+ "      TO_CHAR(trs.AGREEMENT_DATE,'DD/MM/YYYY')      AS agree_date, "
+ "      TO_CHAR(trs.BILL_DATE,'DD/MM/YYYY')           AS b_date , "
+ "      trim(TO_CHAR(trs.AMOUNT,'99999999999999.99')) AS AMOUNT, "
+ "      mas.JOURNAL_TYPE_CODE, "
+ "      trs.PARTICULARS , "
+ "      trs.CHEQUE_DD_NO, "
+ "      trs.CB_REF_NO, "
+ "      TO_CHAR( trs.CB_REF_DATE,'DD/MM/YYYY') AS CB_REF_DATE, "
+ "      trs.MULTIPLE_PVR_DETAILS "
+ "    FROM FAS_JOURNAL_MASTER mas, "
+ "      FAS_JOURNAL_TRANSACTION trs, "
+ "     fas_payment_master pm, "
+ "      fas_payment_transaction pt "
+ "   WHERE mas.voucher_DATE BETWEEN ? AND ? "
+ "    AND trs.ACCOUNTING_UNIT_ID   =? "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =? "
+ "    AND trs.SUB_LEDGER_CODE      =? "
+ "     "
+ "    AND mas.ACCOUNTING_UNIT_ID   =trs.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =trs.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =trs.CASHBOOK_MONTH "
+ "    AND mas.VOUCHER_NO           =trs.VOUCHER_NO "
+ "   "
+ "   "
+ "    AND pm.ACCOUNTING_UNIT_ID    =pt.ACCOUNTING_UNIT_ID "
+ "    AND pm.CASHBOOK_YEAR         =pt.CASHBOOK_YEAR "
+ "    AND pm.CASHBOOK_MONTH        =pt.CASHBOOK_MONTH "
+ "    and pm.voucher_no            =  pt.voucher_no "
+ " "
+ "    AND trs.ACCOUNTING_UNIT_ID    =pt.ACCOUNTING_UNIT_ID "
// + "    AND trs.CASHBOOK_YEAR         =pt.CASHBOOK_YEAR "
// + "    AND trs.CASHBOOK_MONTH        =pt.CASHBOOK_MONTH "
+ " "
+ " "
+ "     "
+ "    AND mas.ACCOUNTING_UNIT_ID   =pt.ACCOUNTING_UNIT_ID "
+ "   AND mas.VOUCHER_NO           =pt.payable_voucher_no "
+ "    AND mas.VOUCHER_DAte         =pt.payable_voucher_date "
+ "     "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =pt.SUB_LEDGER_TYPE_CODE "
+ "    AND trs.SUB_LEDGER_CODE      =pt.SUB_LEDGER_CODE "
+ "    AND trs.SL_NO                =pt.PAYABLE_VOUCHER_SLNO "
+ "    AND mas.JOURNAL_STATUS!='C' "
+ "    AND trs.MULTIPLE_PVRS        ='Y' "
+ "     "
+ "    AND pm.payment_status        ='L' "
+ "    AND pm.PAYABLE_VOUCHER_TYPE  ='J' "
+ "    AND pt.payable_voucher_no not in ( "
+ "    select distinct  payable_voucher_No from "
+ "   ( SELECT  distinct p.payable_voucher_no,p.payable_voucher_slno  FROM FAS_PAYMENT_TRANSACTION P,fas_payment_master m WHERE "
+ "    p.payable_voucher_date BETWEEN  ? AND ? "
+ "    and  P.ACCOUNTING_UNIT_ID   =? "
+ "    and  p.SUB_LEDGER_TYPE_CODE =? "
+ "    AND p.SUB_LEDGER_CODE      =? "
+ "    and(p.payable_voucher_slno is  null or p.payable_voucher_slno = 0) "
+ "      AND m.ACCOUNTING_UNIT_ID    =p.ACCOUNTING_UNIT_ID "
+ "    AND m.CASHBOOK_YEAR         =p.CASHBOOK_YEAR "
+ "    AND m.CASHBOOK_MONTH        =p.CASHBOOK_MONTH "
+ "    AND m.voucher_no            = p.voucher_no "
+ "     AND m.payment_status        ='L' "
+ ")kout "
+ "where kout.payable_voucher_slno is null or kout.payable_voucher_slno = 0) "
+ "    )chk "
+ "  LEFT OUTER JOIN "
+ "    (SELECT ms.ACCOUNTING_UNIT_ID, "
+ "      tr.SUB_LEDGER_TYPE_CODE, "
+ "      tr.SUB_LEDGER_CODE, "
+ "      tr.payable_voucher_no AS payable_voucher_no, "
+ "       tr.PAYABLE_VOUCHER_SLNO, "
+ "      tr.PAYABLE_VOUCHER_DATE, "
+ "      tr.ACCOUNT_HEAD_CODE, "
+ "      SUM(tr.AMOUNT) AS AMOUNT "
+ "      "
+ "    FROM fas_payment_transaction tr, "
+ "      fas_payment_master ms "
+ "    WHERE ms.accounting_unit_id = tr.accounting_unit_id "
+ "    AND ms.cashbook_month       = tr.cashbook_month "
+ "    AND ms.cashbook_year        =tr.cashbook_year "
+ "    AND ms.voucher_no           =tr.voucher_no "
+ "    AND ms.payment_status       ='L' "
+ "    AND ms.PAYABLE_VOUCHER_TYPE ='J' "
+ "    AND ms.ACCOUNTING_UNIT_ID   =? "
+ "    GROUP BY ms.ACCOUNTING_UNIT_ID, "
+ "      tr.SUB_LEDGER_TYPE_CODE, "
+ "      tr.SUB_LEDGER_CODE, "
+ "      tr.payable_voucher_no, "
+ "      tr.PAYABLE_VOUCHER_DATE, "
+ "      tr.ACCOUNT_HEAD_CODE, "
+ "      tr.PAYABLE_VOUCHER_SLNO "
+ "       "
+ "    )p "
+ "  ON chk.VOUCHER_NO            =p .payable_voucher_no "
+ "  AND VOUCHER_DATE             =p.PAYABLE_VOUCHER_DATE "
+ "  AND chk.SUB_LEDGER_TYPE_CODE =p.SUB_LEDGER_TYPE_CODE "
+ "  AND chk.SUB_LEDGER_CODE      =p.SUB_LEDGER_CODE "
+ "  AND chk.ACCOUNT_HEAD_CODE    =p.ACCOUNT_HEAD_CODE "
+ "  AND chk.SL_NO                =p.PAYABLE_VOUCHER_SLNO "
+ "   "
+ "  GROUP BY chk.VOUCHER_NO, "
+ "    chk.SL_NO, "
+ "    chk.VOUCHER_DATE, "
+ "    chk.vou_date, "
+ "    chk.vouType, "
+ "    chk.ACCOUNT_HEAD_CODE , "
+ "    chk.cr_dr_indicator , "
+ "    chk.SUB_LEDGER_TYPE_CODE , "
+ "    chk.SUB_LEDGER_CODE, "
+ "    chk.BILL_NO, "
+ "    chk.BILL_TYPE, "
+ "    chk.AGREEMENT_NO, "
+ "    chk.agree_date, "
+ "    chk.b_date , "
+ "    chk.AMOUNT, "
+ "    chk.JOURNAL_TYPE_CODE, "
+ "    chk.PARTICULARS, "
+ "    chk.MULTIPLE_PVR_DETAILS, "
+ "    p.payable_voucher_no , "
+ "    chk.CHEQUE_DD_NO, "
+ "    chk.CB_REF_NO, "
+ "    CHK.CB_REF_DATE, "
+ "    CHK.office_id "
+ "  ) "
+ "UNION ALL "
+ "  (SELECT chk.VOUCHER_NO, "
+ "    chk.SL_NO, "
+ "  chk.office_id, "
+ "   chk.CB_REF_DATE, "
+ "    chk.VOUCHER_DATE, "
+ "    chk.vou_date, "
+ "    chk.vouType, "
+ "    chk.ACCOUNT_HEAD_CODE , "
+ "    chk.cr_dr_indicator , "
+ "    chk.SUB_LEDGER_TYPE_CODE , "
+ "    chk.SUB_LEDGER_CODE, "
+ "    chk.BILL_NO, "
+ "    chk.BILL_TYPE, "
+ "    chk.AGREEMENT_NO, "
+ "    chk.agree_date, "
+ "    chk.b_date , "
+ "    chk.AMOUNT, "
+ "    chk.JOURNAL_TYPE_CODE, "
+ "    chk.PARTICULARS, "
+ "    chk.MULTIPLE_PVR_DETAILS, "
+ "    NULL AS PAYABLE_VOUCHER_DATE, "
+ "    0    AS pay_amt, "
+ "    chk.CHEQUE_DD_NO, "
+ "    chk.CB_REF_NO "
+ "  FROM "
+ "    (SELECT trs.VOUCHER_NO, "
+ "      trs.SL_NO, "
+ "  mas.accounting_for_office_id as office_id, "
+ "      TO_CHAR( trs.CB_REF_DATE,'DD/MM/YYYY') AS CB_REF_DATE, "
+ "      mas.VOUCHER_DATE, "
+ "      TO_CHAR(mas.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date, "
+ "      'LJV'                                  AS vouType, "
+ "      trs.ACCOUNT_HEAD_CODE , "
+ "  CASE WHEN (trs.CR_DR_INDICATOR='DR') THEN 'CR'WHEN (trs.CR_DR_INDICATOR='CR') THEN 'DR' END  AS cr_dr_indicator , "
+ "      trs.SUB_LEDGER_TYPE_CODE , "
+ "      trs.SUB_LEDGER_CODE, "
+ "      trs.BILL_NO, "
+ "      trs.BILL_TYPE, "
+ "      trs.AGREEMENT_NO, "
+ "      TO_CHAR(trs.AGREEMENT_DATE,'DD/MM/YYYY')      AS agree_date, "
+ "      TO_CHAR(trs.BILL_DATE,'DD/MM/YYYY')           AS b_date , "
+ "      trim(TO_CHAR(trs.AMOUNT,'99999999999999.99')) AS AMOUNT, "
+ "      mas.JOURNAL_TYPE_CODE, "
+ "      trs.PARTICULARS, "
+ "      trs.CHEQUE_DD_NO, "
+ "      trs.CB_REF_NO, "
+ "      trs.MULTIPLE_PVR_DETAILS "
+ "    FROM FAS_JOURNAL_MASTER mas, "
+ "      FAS_JOURNAL_TRANSACTION trs "
+ "   WHERE mas.voucher_DATE BETWEEN ? AND ? "
+ "    AND trs.ACCOUNTING_UNIT_ID   =? "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =? "
+ "    AND trs.SUB_LEDGER_CODE      =? "
+ "    AND mas.ACCOUNTING_UNIT_ID   =trs.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =trs.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =trs.CASHBOOK_MONTH "
+ "    AND mas.VOUCHER_NO           =trs.VOUCHER_NO "
+ "    AND mas.JOURNAL_STATUS!='C' "
+ "    AND ((mas.CREATED_BY_MODULE  ='LJV' "
//+ "    AND mas.supplement_no        =0"
+ ") "
+ "    OR ((mas.journal_type_code  IN (105,61,66,53) "
+ "    AND mas.CREATED_BY_MODULE    ='SJV' "
+ "    AND mas.supplement_no        >0 ) "
+ "    OR ( mas.CREATED_BY_MODULE   ='SJV' "
+ "    AND trs.CB_tdca_REF_NO      IS NULL "
+ "    AND trs.CB_tdca_REF_DATE    IS NULL "
+ "    AND mas.JOURNAL_TYPE_CODE   IN (62)) ) ) "
+ "    AND (trs.CB_REF_NO           is null or trs.CB_REF_NO=0) "
+ "    AND trs.CB_REF_DATE         IS NULL"
+ "    AND (trs.MULTIPLE_PVRS       IS NULL  "
+ " or trs.MULTIPLE_PVRS!='Y') "
+ "    UNION ALL "
+ "    SELECT trs.VOUCHER_NO, "
+ "      trs.SL_NO, "
+ "  mas.accounting_for_office_id as office_id, "
+ "      TO_CHAR( trs.CB_REF_DATE,'DD/MM/YYYY') AS CB_REF_DATE, "
+ "      mas.VOUCHER_DATE, "
+ "      TO_CHAR(mas.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date, "
+ "      'GJV'                                  AS vouType, "
+ "      trs.ACCOUNT_HEAD_CODE , "
+ "     CASE WHEN (trs.CR_DR_INDICATOR='DR') THEN 'CR'WHEN (trs.CR_DR_INDICATOR='CR') THEN 'DR' END  AS cr_dr_indicator ,"
+ "      trs.SUB_LEDGER_TYPE_CODE , "
+ "      trs.SUB_LEDGER_CODE, "
+ "      trs.BILL_NO, "
+ "      trs.BILL_TYPE, "
+ "      trs.AGREEMENT_NO, "
+ "      TO_CHAR(trs.AGREEMENT_DATE,'DD/MM/YYYY')      AS agree_date, "
+ "      TO_CHAR(trs.BILL_DATE,'DD/MM/YYYY')           AS b_date , "
+ "      trim(TO_CHAR(trs.AMOUNT,'99999999999999.99')) AS AMOUNT, "
+ "      mas.JOURNAL_TYPE_CODE, "
+ "      trs.PARTICULARS , "
+ "      trs.CHEQUE_DD_NO, "
+ "      trs.CB_REF_NO, "
+ "      trs.MULTIPLE_PVR_DETAILS "
+ "    FROM FAS_JOURNAL_MASTER mas, "
+ "      FAS_JOURNAL_TRANSACTION trs "
+ "    WHERE mas.voucher_DATE BETWEEN ? AND ? "
+ "    AND trs.ACCOUNTING_UNIT_ID   =? "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =? "
+ "    AND trs.SUB_LEDGER_CODE      =? "
+ "    AND mas.ACCOUNTING_UNIT_ID   =trs.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =trs.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =trs.CASHBOOK_MONTH "
+ "    AND mas.VOUCHER_NO           =trs.VOUCHER_NO "
+ "    AND mas.JOURNAL_STATUS!='C' "
+ "    AND mas.CREATED_BY_MODULE    ='GJV' "
+ "    AND ( trs.CB_REF_NO in (SELECT op.m_voucher_no"
+ " from\r\n"
+ " ( (\r\n"
+ " select\r\n"
+ " mst.accounting_unit_id as ms_accounting_unit_id ,\r\n"
+ " mst.accounting_for_office_id as ms_accounting_for_office_id,\r\n"
+ " mst.voucher_no as m_voucher_no,cashbook_month,cashbook_year,voucher_date as ms_voucher_date\r\n"
+ " from\r\n"
+ " fas_adjust_memo_mst mst)op1\r\n"
+ " left join (\r\n"
+ " select\r\n"
+ " trn.accounting_unit_id as tr_accounting_unit_id,\r\n"
+ " trn.accounting_for_office_id as tr_accounting_for_office_id,\r\n"
+ " trn.voucher_no,\r\n"
+ " trn.accounting_for_office_id,\r\n"
+ " trn.for_accounting_unit_id,\r\n"
+ " trn.accept_voucher_date as voucher_date,cashbook_year,cashbook_month\r\n"
+ " from\r\n"
+ " fas_adjust_memo_trn trn)op2 on\r\n"
+ " op1.ms_accounting_for_office_id = op2.tr_accounting_for_office_id\r\n"
+ " and op1.ms_accounting_unit_id = op2.tr_accounting_unit_id\r\n"
+ " and op1.cashbook_year=op2.cashbook_year\r\n"
+ " and op1.cashbook_month=op2.cashbook_month\r\n"
+ " and op1.m_voucher_no = op2.voucher_no )op\r\n"
+ " where "
+ "op.ms_accounting_for_office_id = 5000 "
+ " AND op.for_accounting_unit_id = ? "
+ " AND op.voucher_date BETWEEN ? "
+ " AND ? ) ) "
+ "    AND (trs.CB_REF_DATE  in (SELECT op.ms_voucher_date\r\n"
+ ""
+ " from\r\n"
+ " ( (\r\n"
+ " select\r\n"
+ " mst.accounting_unit_id as ms_accounting_unit_id ,\r\n"
+ " mst.accounting_for_office_id as ms_accounting_for_office_id,\r\n"
+ " mst.voucher_no as m_voucher_no,cashbook_month,cashbook_year,voucher_date as ms_voucher_date\r\n"
+ " from\r\n"
+ " fas_adjust_memo_mst mst)op1\r\n"
+ " left join (\r\n"
+ " select\r\n"
+ " trn.accounting_unit_id as tr_accounting_unit_id,\r\n"
+ " trn.accounting_for_office_id as tr_accounting_for_office_id,\r\n"
+ " trn.voucher_no,\r\n"
+ " trn.accounting_for_office_id,\r\n"
+ " trn.for_accounting_unit_id,\r\n"
+ " trn.accept_voucher_date as voucher_date,cashbook_year,cashbook_month\r\n"
+ " from\r\n"
+ " fas_adjust_memo_trn trn)op2 on\r\n"
+ " op1.ms_accounting_for_office_id = op2.tr_accounting_for_office_id\r\n"
+ " and op1.ms_accounting_unit_id = op2.tr_accounting_unit_id\r\n"
+ " and op1.cashbook_year=op2.cashbook_year\r\n"
+ " and op1.cashbook_month=op2.cashbook_month\r\n"
+ " and op1.m_voucher_no = op2.voucher_no )op\r\n"
+ " where "
+ "op.ms_accounting_for_office_id = 5000 "
+ " AND op.for_accounting_unit_id = ? "
+ " AND op.voucher_date BETWEEN ? "
+ " AND ? ) ) "
+ "    AND mas.JOURNAL_TYPE_CODE   IN (53,54,61,62,66,70,79,75,110) "/*@NK INCLUDE TYPE_CODE 70 ON 10JUL21 FOR CGST,SGST PAYMENT*/
+ "    UNION ALL "
+ "    SELECT trs.VOUCHER_NO, "
+ "      trs.SL_NO, "
+ "  mas.accounting_for_office_id as office_id, "
+ "      TO_CHAR( trs.CB_REF_DATE,'DD/MM/YYYY') AS CB_REF_DATE, "
+ "      mas.VOUCHER_DATE, "
+ "      TO_CHAR(mas.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date, "
+ "      'TJV'                                  AS vouType, "
+ "      trs.ACCOUNT_HEAD_CODE , "
+ "      CASE WHEN (trs.CR_DR_INDICATOR='DR') THEN 'CR'WHEN (trs.CR_DR_INDICATOR='CR') THEN 'DR' END  AS cr_dr_indicator , "
+ "      trs.SUB_LEDGER_TYPE_CODE , "
+ "      trs.SUB_LEDGER_CODE, "
+ "      trs.BILL_NO, "
+ "      trs.BILL_TYPE, "
+ "      trs.AGREEMENT_NO, "
+ "      TO_CHAR(trs.AGREEMENT_DATE,'DD/MM/YYYY')      AS agree_date, "
+ "      TO_CHAR(trs.BILL_DATE,'DD/MM/YYYY')           AS b_date , "
+ "      trim(TO_CHAR(trs.AMOUNT,'99999999999999.99')) AS AMOUNT, "
+ "      mas.JOURNAL_TYPE_CODE, "
+ "      trs.PARTICULARS , "
+ "      trs.CHEQUE_DD_NO, "
+ "      trs.CB_REF_NO, "
+ "      trs.MULTIPLE_PVR_DETAILS "
+ "    FROM FAS_JOURNAL_MASTER mas, "
+ "      FAS_JOURNAL_TRANSACTION trs, "
+ "      FAS_TDA_TCA_RAISED_TRN tda "
+ "     WHERE mas.voucher_DATE BETWEEN ? AND ? "
+ "    AND trs.ACCOUNTING_UNIT_ID   =? "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =?"
+ "    AND trs.SUB_LEDGER_CODE      =? "
+ "    AND mas.ACCOUNTING_UNIT_ID   =trs.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =trs.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =trs.CASHBOOK_MONTH "
+ "    AND mas.VOUCHER_NO           =trs.VOUCHER_NO "
+ "    AND mas.ACCOUNTING_UNIT_ID   =tda.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =tda.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =tda.CASHBOOK_MONTH "
+ "    AND trs.CB_TDCA_REF_NO       =tda.VOUCHER_NO "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =tda.SUB_LEDGER_TYPE_CODE "
+ "    AND trs.SUB_LEDGER_CODE      =tda.SUB_LEDGER_CODE "
+ "    AND mas.JOURNAL_STATUS!='C' "
+ "    AND ( mas.CREATED_BY_MODULE  ='GJV' "
+ "    OR (MAS.CREATED_BY_MODULE    ='SJV' "
+ "    AND mas.supplement_no        >0) ) "
+ "    AND mas.JOURNAL_TYPE_CODE    =62 "
+ "    AND (trs.CB_REF_NO           is null or trs.CB_REF_NO=0) "
+ "    AND trs.CB_REF_DATE         IS NULL "
+ "    AND trs.CHEQUE_OR_DD        IS NULL "
+ "    AND trs.CHEQUE_DD_NO        IS NULL "
+ "    AND trs.CHEQUE_DD_DATE      IS NULL "
+ "    AND TRS.CB_TDCA_REF_NO      IS NOT NULL "
+ "    AND TRS.CB_TDCA_REF_DATE    IS NOT NULL "
+ "    UNION ALL "
+ "    SELECT trs.VOUCHER_NO, "
+ "      trs.SL_NO, "
+ "  mas.accounting_for_office_id as office_id, "
+ "      TO_CHAR( trs.CB_REF_DATE,'DD/MM/YYYY') AS CB_REF_DATE, "
+ "      mas.VOUCHER_DATE, "
+ "      TO_CHAR(mas.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date, "
+ "      'TPJ'                                  AS vouType, "
+ "      trs.ACCOUNT_HEAD_CODE , "
+ "  CASE WHEN (trs.CR_DR_INDICATOR='DR') THEN 'CR'WHEN (trs.CR_DR_INDICATOR='CR') THEN 'DR' END  AS cr_dr_indicator , "
+ "      trs.SUB_LEDGER_TYPE_CODE , "
+ "      trs.SUB_LEDGER_CODE, "
+ "      trs.BILL_NO, "
+ "      trs.BILL_TYPE, "
+ "      trs.AGREEMENT_NO, "
+ "      TO_CHAR(trs.AGREEMENT_DATE,'DD/MM/YYYY')      AS agree_date, "
+ "      TO_CHAR(trs.BILL_DATE,'DD/MM/YYYY')           AS b_date , "
+ "      trim(TO_CHAR(trs.AMOUNT,'99999999999999.99')) AS AMOUNT, "
+ "      mas.JOURNAL_TYPE_CODE, "
+ "      trs.PARTICULARS , "
+ "      trs.CHEQUE_DD_NO, "
+ "      trs.CB_REF_NO, "
+ "      trs.MULTIPLE_PVR_DETAILS "
+ "    FROM FAS_JOURNAL_MASTER mas, "
+ "      FAS_JOURNAL_TRANSACTION trs, "
+ "      FAS_TPA_TRANSACTION tda "
+ "    WHERE mas.voucher_DATE BETWEEN ? AND ? "
+ "    AND trs.ACCOUNTING_UNIT_ID   =? "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =?"
+ "    AND trs.SUB_LEDGER_CODE      =? "
+ "    AND mas.ACCOUNTING_UNIT_ID   =trs.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =trs.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =trs.CASHBOOK_MONTH "
+ "    AND mas.VOUCHER_NO           =trs.VOUCHER_NO "
+ "    AND mas.ACCOUNTING_UNIT_ID   =tda.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =tda.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =tda.CASHBOOK_MONTH "
+ "    AND trs.CB_REF_NO            =tda.VOUCHER_NO "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =tda.SUB_LEDGER_TYPE_CODE "
+ "    AND trs.SUB_LEDGER_CODE      =tda.SUB_LEDGER_CODE "
+ "    AND TRS.ACCOUNT_HEAD_CODE    =TDA.ACCOUNT_HEAD_CODE "
+ "    AND mas.JOURNAL_STATUS!='C' "
+ "    AND( mas.CREATED_BY_MODULE   ='GJV' "
+ "    OR (MAS.CREATED_BY_MODULE    ='SJV' "
+ "    AND mas.supplement_no        >0) ) "
+ "    AND mas.JOURNAL_TYPE_CODE    =79 "
+ "    AND (trs.CB_REF_NO            is null or trs.CB_REF_NO=0) "
+ "    AND trs.CB_REF_DATE         IS NULL "
+ "    AND trs.CHEQUE_OR_DD        IS NULL "
+ "    AND trs.CHEQUE_DD_NO        IS NULL "
+ "    AND trs.CHEQUE_DD_DATE      IS NULL "
+ "    AND TRS.CB_TPA_REF_NO       IS NOT NULL "
+ "    AND TRS.CB_TPA_REF_DATE     IS NOT NULL "
+ "    UNION ALL "
+ "    SELECT trs.RECEIPT_NO AS VOUCHER_NO, "
+ "      trs.SL_NO, "
+ "  mas.accounting_for_office_id as office_id, "
+ "      TO_CHAR( trs.CB_REF_DATE,'DD/MM/YYYY') AS CB_REF_DATE, "
+ "      mas.RECEIPT_DATE                       AS VOUCHER_DATE, "
+ "      TO_CHAR(mas.RECEIPT_DATE,'DD/MM/YYYY') AS vou_date, "
+ "      'R'                                    AS vouType, "
+ "      trs.ACCOUNT_HEAD_CODE , "
+ "      CASE WHEN (trs.CR_DR_INDICATOR='DR') THEN 'CR'WHEN (trs.CR_DR_INDICATOR='CR') THEN 'DR' END  AS cr_dr_indicator  , "
+ "      trs.SUB_LEDGER_TYPE_CODE , "
+ "      trs.SUB_LEDGER_CODE, "
+ "      NULL                                          AS BILL_NO, "
+ "      NULL                                          AS BILL_TYPE, "
+ "      NULL                                          AS AGREEMENT_NO, "
+ "      NULL                                          AS agree_date, "
+ "      NULL                                          AS b_date, "
+ "      trim(TO_CHAR(trs.AMOUNT,'99999999999999.99')) AS AMOUNT, "
+ "      mas.JOURNAL_TYPE_CODE::NUMERIC              AS JOURNAL_TYPE_CODE, "
+ "      trs.PARTICULARS, "
+ "      trs.CHEQUE_DD_NO, "
+ "      trs.CB_REF_NO, "
+ "      trs.MULTIPLE_PVR_DETAILS "
+ "    FROM FAS_RECEIPT_MASTER mas, "
+ "      FAS_RECEIPT_TRANSACTION trs "
+ "    WHERE mas.RECEIPT_DATE BETWEEN ? AND ? "
+ "    AND trs.ACCOUNTING_UNIT_ID   =? "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =?"
+ "    AND trs.SUB_LEDGER_CODE      =? "
+ "    AND mas.ACCOUNTING_UNIT_ID   =trs.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =trs.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =trs.CASHBOOK_MONTH "
+ "    AND mas.RECEIPT_NO           =trs.RECEIPT_NO "
+ "    AND mas.RECEIPT_STATUS!='C' "
+ "    AND (mas.CREATED_BY_MODULE  IN ('CR','BR')) "
+ "    AND (trs.CB_REF_NO           is null "
+ "    OR trs.CB_REF_NO           = 0) "
+ "    AND trs.CB_REF_DATE         IS NULL "
+ "    AND trs.MULTIPLE_PVRS       IS NULL"
+ "    )chk "
+ "  ) )fin "
+ "UNION ALL "
+ "SELECT fin.VOUCHER_NO, "
+ "  fin.SL_NO, "
+ "  fin.office_id, "
+ "  fin.CB_REF_DATE, "
+ "  fin.VOUCHER_DATE, "
+ "  fin.vou_date, "
+ "  fin.vouType, "
+ "  fin.ACCOUNT_HEAD_CODE , "
+ "  fin.cr_dr_indicator , "
+ "  fin.SUB_LEDGER_TYPE_CODE , "
+ "  fin.SUB_LEDGER_CODE, "
+ "  fin.BILL_NO, "
+ "  fin.BILL_TYPE, "
+ "  fin.AGREEMENT_NO, "
+ "  fin.agree_date, "
+ "  fin.b_date , "
+ "  trim(TO_CHAR(fin.AMOUNT::NUMERIC,'99999999999999.99'))  AS AMOUNT, "
+ "  trim(TO_CHAR((AMOUNT::NUMERIC - pay_amt),'99999999999999.99')) AS vrAMOUNT, "
+ "  fin.JOURNAL_TYPE_CODE, "
+ "  fin.PARTICULARS, "
+ "  fin.MULTIPLE_PVR_DETAILS "
+ "FROM "
+ "  (SELECT chk.VOUCHER_NO, "
+ "    chk.SL_NO, "
+ "  chk.office_id, "
+ "    chk.CB_REF_DATE, "
+ "    chk.VOUCHER_DATE, "
+ "    chk.vou_date, "
+ "    chk.vouType, "
+ "    chk.ACCOUNT_HEAD_CODE , "
+ "    chk.cr_dr_indicator , "
+ "    chk.SUB_LEDGER_TYPE_CODE , "
+ "    chk.SUB_LEDGER_CODE, "
+ "    chk.BILL_NO, "
+ "    chk.BILL_TYPE, "
+ "    chk.AGREEMENT_NO, "
+ "    chk.agree_date, "
+ "    chk.b_date , "
+ "    chk.AMOUNT, "
+ "    chk.JOURNAL_TYPE_CODE, "
+ "    chk.PARTICULARS, "
+ "    chk.MULTIPLE_PVR_DETAILS, "
+ "    p.payable_voucher_no    AS payable_voucher_no, "
+ "    SUM(chk.PARTIAL_AMOUNT) AS PAY_AMT, "
+ "    chk.CHEQUE_DD_NO, "
+ "    chk.CB_REF_NO "
+ "  FROM "
+ "    (SELECT trs.RECEIPT_NO AS VOUCHER_NO, "
+ "      trs.SL_NO, "
+ "  mas.accounting_for_office_id as office_id, "
+ "      TO_CHAR( trs.CB_REF_DATE,'DD/MM/YYYY') AS CB_REF_DATE, "
+ "      pt.amount                              AS PARTIAL_AMOUNT, "
+ "      pm.payment_date                        AS TRANSACTION_DATE, "
+ "      mas.RECEIPT_DATE                       AS VOUCHER_DATE, "
+ "      TO_CHAR(mas.RECEIPT_DATE,'DD/MM/YYYY') AS vou_date, "
+ "      'R'                                    AS vouType, "
+ "      trs.ACCOUNT_HEAD_CODE , "
+ "      CASE WHEN (trs.CR_DR_INDICATOR='DR') THEN 'CR'WHEN (trs.CR_DR_INDICATOR='CR') THEN 'DR' END  AS cr_dr_indicator, "
+ "      trs.SUB_LEDGER_TYPE_CODE , "
+ "      trs.SUB_LEDGER_CODE, "
+ "      NULL                                          AS BILL_NO, "
+ "      NULL                                          AS BILL_TYPE, "
+ "      NULL                                          AS AGREEMENT_NO, "
+ "      NULL                                          AS agree_date, "
+ "      NULL                                          AS b_date, "
+ "      trim(TO_CHAR(trs.AMOUNT,'99999999999999.99')) AS AMOUNT, "
+ "      mas.JOURNAL_TYPE_CODE::NUMERIC              AS JOURNAL_TYPE_CODE, "
+ "      trs.PARTICULARS, "
+ "      trs.CHEQUE_DD_NO, "
+ "      trs.CB_REF_NO, "
+ "      trs.MULTIPLE_PVR_DETAILS "
+ "    FROM FAS_RECEIPT_MASTER mas, "
+ "      FAS_RECEIPT_TRANSACTION trs, "
+ "      fas_payment_master pm, "
+ "      fas_payment_transaction pt "
+ "    WHERE mas.RECEIPT_DATE BETWEEN ? AND ? "
+ "    AND trs.ACCOUNTING_UNIT_ID   =? "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =?"
+ "    AND trs.SUB_LEDGER_CODE      =? "
+ "    AND mas.ACCOUNTING_UNIT_ID   =trs.ACCOUNTING_UNIT_ID "
+ "    AND mas.CASHBOOK_YEAR        =trs.CASHBOOK_YEAR "
+ "    AND mas.CASHBOOK_MONTH       =trs.CASHBOOK_MONTH "
+ "    AND mas.RECEIPT_NO           =trs.RECEIPT_NO "
+ "    "
+ "     "
+ "    AND pm.ACCOUNTING_UNIT_ID    =pt.ACCOUNTING_UNIT_ID "
+ "    AND pm.CASHBOOK_YEAR         =pt.CASHBOOK_YEAR "
+ "    AND pm.CASHBOOK_MONTH        =pt.CASHBOOK_MONTH "
+ "    AND pm.VOUCHER_NO            =pt.VOUCHER_NO "
+ "     "
+ "    AND mas.ACCOUNTING_UNIT_ID   =pt.ACCOUNTING_UNIT_ID "
+ "    AND mas.receipt_no           =pt.payable_voucher_no "
+ "    AND mas.receipt_date         =pt.payable_voucher_date "
+ "     AND trs.ACCOUNTING_UNIT_ID    =pt.ACCOUNTING_UNIT_ID "
// + "    AND trs.CASHBOOK_YEAR         =pt.CASHBOOK_YEAR "
// + "    AND trs.CASHBOOK_MONTH        =pt.CASHBOOK_MONTH "
+ "    AND trs.SUB_LEDGER_TYPE_CODE =pt.SUB_LEDGER_TYPE_CODE "
+ "    AND trs.SUB_LEDGER_CODE      =pt.SUB_LEDGER_CODE "
+ "    AND trs.SL_NO                =pt.PAYABLE_VOUCHER_SLNO "
+ "    AND mas.RECEIPT_STATUS!='C' "
+ "    AND trs.MULTIPLE_PVRS        ='Y' "
+ "    AND pm.payment_status        ='L' "
+ "     AND pm.payable_voucher_type  = 'R' "
+ "   AND pt.payable_voucher_no not in ( "
+ "    select distinct  payable_voucher_No from "
+ "   ( SELECT  distinct p.payable_voucher_no,p.payable_voucher_slno  FROM FAS_PAYMENT_TRANSACTION P,fas_payment_master m WHERE "
+ "    p.payable_voucher_date BETWEEN  ? AND ? "
+ "    and  P.ACCOUNTING_UNIT_ID   =? "
+ "    and  p.SUB_LEDGER_TYPE_CODE =? "
+ "    AND p.SUB_LEDGER_CODE      =? "
+ "    and(p.payable_voucher_slno is  null or p.payable_voucher_slno = 0) "
+ "      AND m.ACCOUNTING_UNIT_ID    =p.ACCOUNTING_UNIT_ID "
+ "    AND m.CASHBOOK_YEAR         =p.CASHBOOK_YEAR "
+ "    AND m.CASHBOOK_MONTH        =p.CASHBOOK_MONTH "
+ "    AND m.voucher_no            = p.voucher_no "
+ "     AND m.payment_status        ='L' "
+ ")kout "
+ "where kout.payable_voucher_slno is null or kout.payable_voucher_slno = 0) "
+ "    )chk "
+ "  LEFT OUTER JOIN "
+ "    (SELECT ms.ACCOUNTING_UNIT_ID, "
+ "      tr.SUB_LEDGER_TYPE_CODE, "
+ "      tr.SUB_LEDGER_CODE, "
+ "      TR.VOUCHER_NO, "
+ "     tr.SL_NO, "
+ "      tr.payable_voucher_no AS payable_voucher_no, "
+ "      tr.PAYABLE_VOUCHER_DATE, "
+ "      tr.ACCOUNT_HEAD_CODE, "
+ "      SUM(tr.AMOUNT) AS AMOUNT, "
+ "      tr.PAYABLE_VOUCHER_SLNO "
+ "    FROM fas_payment_transaction tr, "
+ "      fas_payment_master ms "
+ "    WHERE ms.accounting_unit_id = tr.accounting_unit_id "
+ "    AND ms.cashbook_month       = tr.cashbook_month "
+ "    AND ms.cashbook_year        =tr.cashbook_year "
+ "    AND ms.voucher_no           =tr.voucher_no "
+ "    AND ms.payment_status       ='L' AND ms.payable_voucher_type = 'J' "
+ "    AND ms.ACCOUNTING_UNIT_ID   =? "
+ "    GROUP BY ms.ACCOUNTING_UNIT_ID, "
+ "      tr.SUB_LEDGER_TYPE_CODE, "
+ "      tr.SUB_LEDGER_CODE, "
+ "      tr.payable_voucher_no, "
+ "      tr.PAYABLE_VOUCHER_DATE, "
+ "      tr.ACCOUNT_HEAD_CODE, "
+ "      tr.PAYABLE_VOUCHER_SLNO, "
+ "     tr.SL_NO, "
+ "      tr.VOUCHER_NO "
+ "    )p "
+ "  ON chk.VOUCHER_NO            =p.payable_voucher_no "
+ "  AND VOUCHER_DATE             =p.PAYABLE_VOUCHER_DATE "
+ "  AND chk.SUB_LEDGER_TYPE_CODE =p.SUB_LEDGER_TYPE_CODE "
+ "  AND chk.SUB_LEDGER_CODE      =p.SUB_LEDGER_CODE "
+ "  AND chk.ACCOUNT_HEAD_CODE    =p.ACCOUNT_HEAD_CODE "
+ "  AND chk.SL_NO                =p.PAYABLE_VOUCHER_SLNO "
+ "  AND chk.CB_REF_NO            =p.VOUCHER_NO "
+ "  AND chk.SL_NO                =p.SL_NO "
+ "   "
+ "  GROUP BY chk.VOUCHER_NO, "
+ "    chk.SL_NO, "
+ "    chk.VOUCHER_DATE, "
+ "    chk.vou_date, "
+ "    chk.vouType, "
+ "    chk.ACCOUNT_HEAD_CODE , "
+ "    chk.cr_dr_indicator , "
+ "    chk.SUB_LEDGER_TYPE_CODE , "
+ "    chk.SUB_LEDGER_CODE, "
+ "    chk.BILL_NO, "
+ "    chk.BILL_TYPE, "
+ "    chk.AGREEMENT_NO, "
+ "    chk.agree_date, "
+ "    chk.b_date , "
+ "    chk.AMOUNT, "
+ "    chk.JOURNAL_TYPE_CODE, "
+ "    chk.PARTICULARS, "
+ "    chk.MULTIPLE_PVR_DETAILS, "
+ "    p.payable_voucher_no , "
+ "    chk.CHEQUE_DD_NO, "
+ "    chk.CB_REF_NO, "
+ "     CHK.CB_REF_DATE, "
+ "     CHK.office_id "
+ "  )fin "
+ "WHERE (AMOUNT::NUMERIC - pay_amt) > 0 )op1 where op1.voutype ='GJV' "
+ "ORDER BY VOUCHER_DATE DESC";
 
   
/* Included by NK for receipt part payment on 18062019 */

System.out.println("CommonQuery >>66 " + query);
ps2 = con.prepareStatement(query);
ps2.setDate(1, frmDate);
ps2.setDate(2, toDate);
ps2.setInt(3, cmbAcc_UnitCode);
// ps2.setInt(4, cmbOffice_code);
ps2.setInt(4, cmbMas_SL_type);
ps2.setInt(5, cmbMaS_SL_code);

ps2.setDate(6, frmDate);
ps2.setDate(7, toDate);
ps2.setInt(8, cmbAcc_UnitCode);
// ps2.setInt(11, cmbOffice_code);
ps2.setInt(9, cmbMas_SL_type);
ps2.setInt(10, cmbMaS_SL_code);
ps2.setInt(11, cmbAcc_UnitCode);

ps2.setDate(12, frmDate);
ps2.setDate(13, toDate);
ps2.setInt(14, cmbAcc_UnitCode);
// ps2.setInt(11, cmbOffice_code);
ps2.setInt(15, cmbMas_SL_type);
ps2.setInt(16, cmbMaS_SL_code);

ps2.setDate(17, frmDate);
ps2.setDate(18, toDate);
ps2.setInt(19, cmbAcc_UnitCode);
// ps2.setInt(17, cmbOffice_code);
ps2.setInt(20, cmbMas_SL_type);
ps2.setInt(21, cmbMaS_SL_code);
ps2.setInt(22, cmbAcc_UnitCode);
ps2.setDate(23, frmDate);
ps2.setDate(24, toDate);
ps2.setInt(25, cmbAcc_UnitCode);
ps2.setDate(26, frmDate);
ps2.setDate(27, toDate);
ps2.setDate(28, frmDate);
ps2.setDate(29, toDate);
ps2.setInt(30, cmbAcc_UnitCode);
// ps2.setInt(23, cmbOffice_code);
ps2.setInt(31, cmbMas_SL_type);
ps2.setInt(32, cmbMaS_SL_code);

ps2.setDate(33, frmDate);
ps2.setDate(34, toDate);
ps2.setInt(35, cmbAcc_UnitCode);
// ps2.setInt(29, cmbOffice_code);
ps2.setInt(36, cmbMas_SL_type);
ps2.setInt(37, cmbMaS_SL_code);

ps2.setDate(38, frmDate);
ps2.setDate(39, toDate);
ps2.setInt(40, cmbAcc_UnitCode);
// ps2.setInt(35, cmbOffice_code);
ps2.setInt(41, cmbMas_SL_type);
ps2.setInt(42, cmbMaS_SL_code);

/* @NK On 18062019 */
ps2.setDate(43, frmDate);
ps2.setDate(44, toDate);
ps2.setInt(45, cmbAcc_UnitCode);
// ps2.setInt(4, cmbOffice_code);
ps2.setInt(46, cmbMas_SL_type);
ps2.setInt(47, cmbMaS_SL_code);

ps2.setDate(48, frmDate);
ps2.setDate(49, toDate);
ps2.setInt(50, cmbAcc_UnitCode);
// ps2.setInt(4, cmbOffice_code);
ps2.setInt(51, cmbMas_SL_type);
ps2.setInt(52, cmbMaS_SL_code);
ps2.setInt(53, cmbAcc_UnitCode);
/* @NK On 18062019 */
System.out.println("CommonQuery >>66 " + ps2.toString());

}

// System.out.println("hai start");
try {
rs2 = ps2.executeQuery();
} catch (Exception e) {
System.out.println("Exceptipon raised here .... ");
e.printStackTrace();
}
// System.out.println("hai stop");

int cnt = 0;
while (rs2.next()) {
// System.out.println("while start");
/*@NK on 19sep2019 to list only Liability(L) records*/
ps4 = con.prepareStatement(
"select MAJOR_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
ps4.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
rs4 = ps4.executeQuery();
String majorHeadCode = null;
if (rs4.next()) {
majorHeadCode=rs4.getString("MAJOR_HEAD_CODE");
ps4.close();
rs4.close();
}

/*@NK on 19sep2019 to list only Liability(L) records*/
if(majorHeadCode.equalsIgnoreCase("L"))
{
xml = xml + "<VOUCHER_NO>" + rs2.getInt("VOUCHER_NO") + "</VOUCHER_NO>";
xml = xml + "<SL_NO>" + rs2.getInt("SL_NO") + "</SL_NO>";
xml = xml + "<OFFICE_ID>" + rs2.getInt("office_id") + "</OFFICE_ID>";
xml = xml + "<payment_date>" + rs2.getString("CB_REF_DATE") + "</payment_date>";
xml = xml + "<vou_date>" + rs2.getString("vou_date") + "</vou_date>";
xml = xml + "<JOURNAL_TYPE>" + rs2.getString("vouType") + "</JOURNAL_TYPE>";
xml += "<JOURNAL_TYPE_CODE>" + rs2.getString("JOURNAL_TYPE_CODE") + "</JOURNAL_TYPE_CODE>";
// xml=xml+"<PARTICULARS>"+rs2.getString("PARTICULARS")+"</PARTICULARS>";
xml += "<MULTIPLE_PVR_DETAILS>" + rs2.getString("MULTIPLE_PVR_DETAILS") + "</MULTIPLE_PVR_DETAILS>";
xml = xml + "<AHcode>" + rs2.getInt("ACCOUNT_HEAD_CODE") + "</AHcode>";
ps3 = con.prepareStatement(
"select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
rs3 = ps3.executeQuery();
if (rs3.next())
xml = xml + "<AHdesc>" + rs3.getString("ACCOUNT_HEAD_DESC") + "</AHdesc>";
ps3.close();
rs3.close();
xml = xml + "<CR_DR_ind>" + rs2.getString("CR_DR_INDICATOR") + "</CR_DR_ind><SL_Type>"
+ rs2.getInt("SUB_LEDGER_TYPE_CODE") + "</SL_Type>";
// System.out.println("hai stop 5");
if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
// System.out.println("take SL DESC");
ps3 = con.prepareStatement(
"select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
ps3.setInt(1, rs2.getInt("SUB_LEDGER_TYPE_CODE"));
rs3 = ps3.executeQuery();
if (rs3.next())
xml = xml + "<SL_Desc>" + rs3.getString("SUB_LEDGER_TYPE_DESC") + "</SL_Desc>";
} else {
xml = xml + "<SL_Desc>" + null + "</SL_Desc>"; // it also
// return
// null
// value
// System.out.println("else part 23");
}

rs3.close();
ps3.close();

xml = xml + "<SL_Code>" + rs2.getInt("SUB_LEDGER_CODE") + "</SL_Code>";

// System.out.println("b4 desc type ");
// Start of fetching sub-Ledger , here u r passing parameters to
// the function getResult_General which is inside the class
// SL_TYPE_CODE_NAME_GENERAL
if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
SL_TYPE_CODE_NAME_DETAILS_PAYMENT obj_det = new SL_TYPE_CODE_NAME_DETAILS_PAYMENT();
ResultSet rs_det = obj_det.getResult_Details(cmbAcc_UnitCode, cmbOffice_code,
rs2.getInt("SUB_LEDGER_TYPE_CODE"), rs2.getString("SUB_LEDGER_CODE"), 0);
if (rs_det != null) {
//rs_det.beforeFirst();
if (rs_det.next()) {
// System.out.println(rs_det.getString("cid"));
// System.out.println(rs_det.getString("cname"));
xml = xml + "<desc_type><![CDATA[" + rs_det.getString("cname") + "]]></desc_type>";
}

/*@NK on 30Apr2020*/
else {

ps = con.prepareStatement(""
                            + "SELECT e.EMPLOYEE_ID AS cid , "
                            + "  e.EMPLOYEE_NAME "
                            + "  || '.' "
                            + "  || e.employee_initial  AS cname "
                            + "from hrm_mst_employees e "
                            + "where "
                            + " e.EMPLOYEE_ID    =? "
                            + "ORDER BY e.EMPLOYEE_NAME");
int cmbSL_code=Integer.parseInt(rs2.getString("SUB_LEDGER_CODE"));
ps.setInt(1,cmbSL_code);

rs = ps.executeQuery();
if (rs.next())
xml = xml + "<desc_type><![CDATA[" + rs.getString("cname") + "]]></desc_type>";
rs.close();
ps.close();
//xml = xml + "<desc_type>" + null + "</desc_type>";
}

/*@NK on 30Apr2020*/

rs_det.close();
} else
System.out.println("null result set");
} else {
xml = xml + "<desc_type>" + null + "</desc_type>";
}
// End of fetching sub-Ledger

// System.out.println("ha........stop 7");
xml = xml + "<Bill_NO>" + rs2.getString("BILL_NO") + "</Bill_NO>" + "<Bill_date>"
+ rs2.getString("b_date") + "</Bill_date>" + "<Bill_type>" + rs2.getString("BILL_TYPE")
+ "</Bill_type>" + "<Agree_No>" + rs2.getString("AGREEMENT_NO") + "</Agree_No>" + "<Agree_date>"
+ rs2.getString("agree_date") + "</Agree_date>" + "<sub_amount>" + rs2.getString("AMOUNT")
+ "</sub_amount><vrAMOUNT>" + rs2.getString("vrAMOUNT") + "</vrAMOUNT><sub_part><![CDATA["
+ rs2.getString("PARTICULARS") + "]]></sub_part>";
// System.out.println("hai stop 8");
cnt++;

}



}
if (cnt == 0)
xml = xml + "<flag>failure</flag>";
else
xml = xml + "<cnt>" + cnt + "</cnt><flag>success</flag>";

} catch (Exception e) {
System.out.println("catch..HERE.in load head code." + e);
xml = xml + "<flag>failure</flag>";
}

        }
// try
// {




// }catch(Exception e)
// {
// System.out.println("catch..HERE...in load jrnl amt."+e);
// }

//        }




xml = xml + "</response>";
System.out.println(xml);
out.println(xml);
}
}

