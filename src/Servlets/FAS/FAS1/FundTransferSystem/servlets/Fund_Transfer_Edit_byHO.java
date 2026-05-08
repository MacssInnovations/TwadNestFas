package Servlets.FAS.FAS1.FundTransferSystem.servlets;

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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Fund_Transfer_Edit_byHO extends HttpServlet {
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
        PreparedStatement ps = null, ps2 = null, ps3 = null;
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
        Date txtCrea_date = null;
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

        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            xml = "<response><command>load_Voucher_No</command>";

            try {
                String[] sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);

                //ps=con.prepareStatement("select VOUCHER_NO from FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'");
                ps =
  con.prepareStatement("select i.VOUCHER_NO from FAS_FUND_TRF_FROM_HO_MASTER i,FAS_CROSS_REFERENCE c where " +
                       " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.DATE_OF_TRANSFER=? and i.TRANSFER_STATUS!='C' " +
                       " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                       " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.VOUCHER_NO=c.VOUCHER_NO " +
                       " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='M' and DOC_TYPE='FTH'");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                rs = ps.executeQuery();

                int count = 0;
                while (rs.next()) {

                    xml =
 xml + "<Rec_No>" + rs.getInt("VOUCHER_NO") + "</Rec_No>";
                    count++;
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (strCommand.equalsIgnoreCase("load_Voucher_Details")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            xml = "<response><command>load_Voucher_Details</command>";
            int txtVoucher_No = 0;
            // Date txtCrea_date;

            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);


            try {
                String[] sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);

                ps =
  con.prepareStatement("select CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE,HO_BANK_ID," +
                       "HO_BRANCH_ID,HO_ACCOUNT_NO,trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,TOTAL_TRN_RECORDS,REMARKS,HO_REF_NO,to_char(HO_REF_DATE,'DD/MM/YYYY') as referDate  " +
                       "from  FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and DATE_OF_TRANSFER=? and VOUCHER_NO=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtVoucher_No);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<MasHeadCode>" + rs.getString("ACCOUNT_HEAD_CODE").trim() +
   "</MasHeadCode>";
                    xml =
 xml + "<accNo>" + rs.getString("HO_ACCOUNT_NO").trim() + "</accNo>";

                    ps3 =
 con.prepareStatement("select bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||coalesce(br.CITY_TOWN_NAME,'') as bankNAME" +
                      " from FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where br.BANK_ID=? and br.BRANCH_ID=? and br.BANK_ID=bk.BANK_ID");
                    ps3.setInt(1, rs.getInt("HO_BANK_ID"));
                    ps3.setInt(2, rs.getInt("HO_BRANCH_ID"));

                    rs3 = ps3.executeQuery();
                    if (rs3.next()) {
                        xml =
 xml + "<bk_name>" + rs3.getString("bankNAME") + "</bk_name>";
                        rs3.close();
                        ps3.close();
                    }


                    xml =
                         xml + "<bk_id>" + rs.getInt("HO_BANK_ID") + "</bk_id><br_id>" +
                           rs.getInt("HO_BRANCH_ID") + "</br_id><Total_amt>" +
                           rs.getString("TOTAL_AMOUNT") + "</Total_amt><No_TRN_Rec>" +
                           rs.getInt("TOTAL_TRN_RECORDS") + "</No_TRN_Rec><Remak>" +
                           rs.getString("REMARKS").replace("", "-") + "</Remak><REF_NO>" + rs.getString("HO_REF_NO").replace("", "-") +
                           "</REF_NO><referDate>" + rs.getString("referDate") + "</referDate>";
                }


                System.out.println("in b/w here exection");

                ps2 =
 con.prepareStatement("select trs.TRANSFER_TO_OFFICE_ID,off.OFFICE_NAME,trs.TRANSFERED_TO_HO_UNIT_ID," +
                      "bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||coalesce(br.CITY_TOWN_NAME,'') as bankNAME_trs," +
                      "trs.ACCOUNT_HEAD_CODE,trs.OFFICE_BANK_ID,trs.OFFICE_BRANCH_ID,trs.OFFICE_ACCOUNT_NO," +
                      "trs.CHEQUE_OR_DD ,trs.CHEQUE_DD_NO ,to_char(trs.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date ," +
                      "trim(to_char(trs.AMOUNT,'99999999999999.99')) as  AMOUNT,trs.PARTICULARS, trs.FUND_TYPE,trs.AUTO_STATUS from FAS_FUND_TRF_FROM_HO_TRN trs,COM_MST_OFFICES Off,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where " +
                      "  trs.TRANSFER_TO_OFFICE_ID=off.OFFICE_ID and bk.BANK_ID=trs.OFFICE_BANK_ID and " +
                      " br.BRANCH_ID=trs.OFFICE_BRANCH_ID and br.BANK_ID=trs.OFFICE_BANK_ID and " +
                      " trs.ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and  trs.CASHBOOK_YEAR=?::numeric and trs.CASHBOOK_MONTH=?::numeric and trs.VOUCHER_NO=?");
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                ps2.setString(3, rs.getString("CASHBOOK_YEAR"));
                ps2.setInt(4, rs.getInt("CASHBOOK_MONTH"));
                ps2.setInt(5, txtVoucher_No);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    xml =
 xml + "<Office_id>" + rs2.getInt("TRANSFER_TO_OFFICE_ID") +
   "</Office_id><Office_name>" + rs2.getString("OFFICE_NAME") +
   "</Office_name><AHcode>" + rs2.getInt("ACCOUNT_HEAD_CODE") + "</AHcode>";
                    ps3 =
 con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
 xml + "<AHdesc>" + rs3.getString("ACCOUNT_HEAD_DESC") + "</AHdesc>";
                    ps3.close();
                    rs3.close();

                    xml =
 xml + "<HO_acc_unitid>" + rs2.getInt("TRANSFERED_TO_HO_UNIT_ID") +
   "</HO_acc_unitid>";
                    ps3 =
 con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                    ps3.setInt(1, rs2.getInt("TRANSFERED_TO_HO_UNIT_ID"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
 xml + "<HO_acc_unitName>" + rs3.getString("ACCOUNTING_UNIT_NAME") +
   "</HO_acc_unitName>";
                    else
                        xml =
 xml + "<HO_acc_unitName>" + null + "</HO_acc_unitName>";
                    ps3.close();
                    rs3.close();

                    xml =
 xml + "<off_bank_id>" + rs2.getInt("OFFICE_BANK_ID") +
   "</off_bank_id><off_branch_id>" + rs2.getInt("OFFICE_BRANCH_ID") +
   "</off_branch_id>";
                    xml =
 xml + "<off_bank_name>" + rs2.getString("bankNAME_trs") + "</off_bank_name>";
                    xml =
 xml + "<che_or_DD>" + rs2.getString("CHEQUE_OR_DD") + "</che_or_DD>" +
   "<che_DD_no>" + rs2.getString("CHEQUE_DD_NO") + "</che_DD_no>" +
   "<che_DD_date>" + rs2.getString("cheq_dd_date") + "</che_DD_date>" +
   "<off_bankAccNo>" + rs2.getString("OFFICE_ACCOUNT_NO").trim() +
   "</off_bankAccNo>" + "<sub_amount>" + rs2.getString("AMOUNT") +
   "</sub_amount><sub_part>" + rs2.getString("PARTICULARS").replaceAll(" ", "") +
   "</sub_part><fund_type>" + rs2.getString("FUND_TYPE") + "</fund_type><autostatus>"+rs2.getString("AUTO_STATUS")+"</autostatus>";


                }

            } catch (Exception e) {
                System.out.println("catch..HERE.in failure to retrieve." + e);
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

        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null, cs1 = null;
        PreparedStatement ps = null;
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
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            Calendar c;
            //  General details
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId =
                0, Total_TRN_Rec = 0;
            long txtBankAccountNo = 0;
            double txtAmount = 0;
            String txtCR_DB = "";
            String txtRemarks = "";
            Date txtCrea_date = null, txtReferenceDate = null;
            String txtReferenceNo = "";
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            try {
                txtBankId =
                        Integer.parseInt(request.getParameter("txtBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankId " + txtBankId);

            try {
                txtBranchId =
                        Integer.parseInt(request.getParameter("txtBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBranchId " + txtBranchId);

            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);


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
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtCR_DB = request.getParameter("txtCR_DB");
            System.out.println("txtCR_DB " + txtCR_DB);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);

            txtReferenceNo = request.getParameter("txtReferenceNo");
            System.out.println("txtReferenceNo " + txtReferenceNo);

            if (!request.getParameter("txtReferenceDate").equalsIgnoreCase("")) {
                sd = request.getParameter("txtReferenceDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtReferenceDate = new Date(d.getTime());
            }
            System.out.println("txtReferenceDate " + txtReferenceDate);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);

           

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");
                String No_TRN_Rec[] =
                    request.getParameterValues("Sub_Acc_Head_Code");
                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                Total_TRN_Rec =
                        No_TRN_Rec.length; //Integer.parseInt(No_TRN_Rec);
                System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
                cs =
 con.prepareCall("call FAS_FUND_TRF_FRM_HO_MAS_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?,?,?,?::numeric,?,?,?::numeric)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setDate(3, txtCrea_date);
                cs.setInt(4, txtCash_year);
                cs.setInt(5, txtCash_Month_hid);
                cs.setInt(6, txtVoucher_No);
                cs.setString(7, txtCR_DB);
                cs.setInt(8, txtCash_Acc_code);
                cs.setInt(9, txtBankId);
                cs.setInt(10, txtBranchId);
                cs.setLong(11, txtBankAccountNo);
                cs.setString(12, txtRemarks);
                cs.setDouble(13, txtAmount);
                cs.setString(14, txtReferenceNo);
                cs.setDate(15, txtReferenceDate);
                cs.setString(16, "update");
                cs.registerOutParameter(17, java.sql.Types.NUMERIC);
                cs.registerOutParameter(6, java.sql.Types.NUMERIC);
                cs.setNull(17, java.sql.Types.NUMERIC);
                //cs.setNull(6, java.sql.Types.NUMERIC);
                cs.setString(18, update_user);
                cs.setTimestamp(19, ts); 
                cs.setInt(20, Total_TRN_Rec);

                System.out.println("b4 exe ");
                cs.execute();
//                txtVoucher_No = cs.getInt(6);
//                int errcode = cs.getInt(17);
                txtVoucher_No = cs.getBigDecimal(6).intValue();
                int errcode = cs.getBigDecimal(17).intValue();
                System.out.println("voucher number" + txtVoucher_No);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The Fund Transfer Transaction failed ", "ok");
                    return;
                } else {
                	System.out.println("comes for delete:::");
                    String sql_del =
                        "delete from FAS_FUND_TRF_FROM_HO_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and " +
                        "CASHBOOK_MONTH =? and VOUCHER_NO=?";
                    ps = con.prepareStatement(sql_del);
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setInt(3, txtCash_year);
                    ps.setInt(4, txtCash_Month_hid);
                    ps.setInt(5, txtVoucher_No);
                    ps.executeUpdate(); // deletion from transaction table
                    ps.close();

                    String Grid_Sub_Office_code[] =
                        request.getParameterValues("Sub_Office_code");
                    String Grid_H_code[] =
                        request.getParameterValues("Sub_Acc_Head_Code");
                    String Grid_HO_acc_unitid[] =
                        request.getParameterValues("HO_Unit_ID");
                    String Grid_Sub_Bank_Acc_No[] =
                        request.getParameterValues("Sub_Bank_Acc_No");
                    String Grid_Sub_Bank_Id[] =
                        request.getParameterValues("Sub_Bank_Id");
                    String Grid_Sub_Branch_Id[] =
                        request.getParameterValues("Sub_Branch_Id");
                    String Grid_particular[] =
                        request.getParameterValues("particular");
                    String Grid_sl_amt[] =
                        request.getParameterValues("sl_amt");

                    String Grid_Cheque_DD[] =
                        request.getParameterValues("Cheque_DD");
                    String Grid_Cheque_DD_NO[] =
                        request.getParameterValues("Cheque_DD_NO");
                    String Grid_Cheque_DD_date[] =
                        request.getParameterValues("Cheque_DD_date");
                    String Grid_edit[] =request.getParameterValues("edit_test");
                    System.out.println("autostatus::::"+request.getParameterValues("edit_test"));

                    String sql =
                        "insert into FAS_FUND_TRF_FROM_HO_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,TRANSFER_TO_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO," +
                        "ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,OFFICE_BANK_ID,OFFICE_BRANCH_ID,OFFICE_ACCOUNT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE," +
                        "AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,TRANSFERED_TO_HO_UNIT_ID,FUND_TYPE,AUTO_STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    int SL_NO = 1, txtSubAcc_HeadCode = 0, txtSubOffice_code =
                        0, txtSubBankId = 0, txtSubBranchId = 0;
                    int txtHO_acc_unitid = 0;
                    long txtSubBankAccountNo = 0;
                    Date txtCheque_DD_date = null;
                    double txtsub_Amount = 0;
                    String rad_sub_CR_DR = "", txtParticular = "";
                    String txtCheque_DD = "", txtCheque_DD_NO = "";
                    String grid_status="";

                    /**
                         * Get Fund Type eg: C-Civil or W-Work
                         */

                    String fund_type = null;
                    try {
                        fund_type = request.getParameter("fund_type");
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    ps = con.prepareStatement(sql);
                    System.out.println("length of grid:::"+Grid_H_code.length);
                    for (int k = 0; k < Grid_H_code.length; k++) {
                    	if(Grid_edit[k].equals("Y"))
                    	{
                    		System.out.println("yyyyyyyyyyyyyyy::::::::");
                    		grid_status="Y";
                    	}
                    	else if(Grid_edit[k].equals("N"))
                    	{
                    		System.out.println("NNNNNN::::::::");
                    		grid_status=null;
                    	}
                    	else
                    	{
                    		System.out.println("else::::::::");
                    		grid_status=null;
                    	}
                    		
                        try {
                            txtSubAcc_HeadCode =
                                    Integer.parseInt(Grid_H_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in txtSubAcc_HeadCode " + e);
                        }
                        rad_sub_CR_DR = "DR";

                        try {
                            txtSubOffice_code =
                                    Integer.parseInt(Grid_Sub_Office_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in txtSubOffice_code " + e);
                        }
                        try {
                            txtHO_acc_unitid =
                                    Integer.parseInt(Grid_HO_acc_unitid[k]);
                        } catch (Exception e) {
                            System.out.println("exception in txtHO_acc_unitid " + e);
                        }
                        // System.out.println("k value.."+k);
                        System.out.println("Grid_HO_acc_unitid[k]..." +
                                           Grid_HO_acc_unitid[k]);
                        System.out.println("k value.." + k);
                        try {
                            txtSubBankAccountNo =
                                    Long.parseLong(Grid_Sub_Bank_Acc_No[k]);
                        } catch (Exception e) {
                            System.out.println("exception in txtSubBankAccountNo " + e);
                        }
                        try {
                            txtSubBankId =
                                    Integer.parseInt(Grid_Sub_Bank_Id[k]);
                        } catch (Exception e) {
                            System.out.println("exception in txtSubBankId " + e);
                        }
                        try {
                            txtSubBranchId =
                                    Integer.parseInt(Grid_Sub_Branch_Id[k]);
                        } catch (Exception e) {
                            System.out.println("exception in txtSubBranchId " + e);
                        }
                        txtParticular = Grid_particular[k];
                        try {
                            txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                        } catch (Exception e) {
                            System.out.println("exception in txtsub_Amount " + e);
                        }

                        txtCheque_DD = Grid_Cheque_DD[k];
                        txtCheque_DD_NO = Grid_Cheque_DD_NO[k];
                        if (!Grid_Cheque_DD_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Cheque_DD_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtCheque_DD_date = new Date(d.getTime());
                        }

                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setInt(2, cmbOffice_code);
                        ps.setInt(3, txtSubOffice_code);
                        ps.setInt(4, txtCash_year);
                        ps.setInt(5, txtCash_Month_hid);
                        ps.setInt(6, txtVoucher_No);
                        ps.setInt(7, SL_NO);
                        ps.setInt(8, txtSubAcc_HeadCode);
                        ps.setString(9, rad_sub_CR_DR);
                        ps.setInt(10, txtSubBankId);
                        ps.setInt(11, txtSubBranchId);
                        ps.setLong(12, txtSubBankAccountNo);
                        ps.setString(13, txtCheque_DD);
                        ps.setString(14, txtCheque_DD_NO);
                        ps.setDate(15, txtCheque_DD_date);
                        ps.setDouble(16, txtsub_Amount);
                        ps.setString(17, txtParticular);
                        ps.setString(18, update_user);
                        ps.setTimestamp(19, ts);
                        ps.setInt(20, txtHO_acc_unitid);
                        ps.setString(21, fund_type);
                        ps.setString(22, grid_status);
                        

                        SL_NO++;
                        int kj=ps.executeUpdate();
                        System.out.println("count:::::"+kj);

                        System.out.println("txtSubOffice_code.." +
                                           txtSubOffice_code);
                        System.out.println("txtHO_acc_unitid.." +
                                           txtHO_acc_unitid);

                        txtSubOffice_code = 0;
                        txtSubAcc_HeadCode = 0;
                        txtSubBankId = 0;
                        txtSubBranchId = 0;
                        txtSubBankAccountNo = 0;
                        txtCheque_DD = "";
                        txtCheque_DD_NO = "";
                        txtCheque_DD_date = null;
                        txtsub_Amount = 0;
                        txtParticular = "";
                        txtHO_acc_unitid = 0;
                        System.out.println("txtSubOffice_code.." +
                                           txtSubOffice_code);
                        System.out.println("txtHO_acc_unitid.." +
                                           txtHO_acc_unitid);

                    }
                    ps.close();
                    String txtReferNO_edit = "", txtRemak_edit =
                        "", txtRefdate = ""; // for cross reference
                    Date txtReferDate_edit = null;
                    String radAuth_MC = "";
                    int txtAuth_By = 0;

                    System.out.println("txtReferDate_edit " +
                                       txtReferDate_edit);
                    cs1 =
 con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
                    cs1.setInt(1, cmbAcc_UnitCode);
                    cs1.setInt(2, txtCash_year);
                    cs1.setInt(3, txtCash_Month_hid);
                    cs1.setInt(4, txtVoucher_No);
                    cs1.setInt(5,
                               cmbOffice_code); // This operation is only related with ?Head Office so here it's '5000'
                    cs1.setDate(6, txtCrea_date);
                    cs1.setString(7, "FTH"); // Fund transfer from head office
                    cs1.setString(8, txtReferNO_edit);
                    cs1.setDate(9, txtReferDate_edit);
                    cs1.setString(10, txtRemak_edit);
                    cs1.setInt(11, txtAuth_By);
                    cs1.setString(12, "insert");
                    cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
                    cs1.setNull(13,java.sql.Types.NUMERIC);
                    cs1.setString(14, update_user);
                    cs1.setTimestamp(15, ts);
                    cs1.setString(16, radAuth_MC);
                    cs1.execute(); // insertion into cross reference table
                    //errcode = cs1.getInt(13);
                     errcode = cs1.getBigDecimal(13).intValue();
                    System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                    //System.out.println("cmbOffice_code "+cmbOffice_code);
                    System.out.println("txtCrea_date " + txtCrea_date);
                    System.out.println("txtCash_year " + txtCash_year);
                    System.out.println("txtCash_Month_hid " +
                                       txtCash_Month_hid);
                    System.out.println("SQLCODE:::" + errcode);
                    if (errcode != 0) {
                        con.rollback();
                        sendMessage(response,
                                    "The Fund Transfer Transaction Failed ",
                                    "ok");
                        xml = xml + "<flag>failure</flag>";
                    }
                    System.out.println("b4 commit");
                    con.commit();
                    sendMessage(response,
                                "The Fund Transfer Transaction Voucher Number '" +
                                txtVoucher_No +
                                "' has been Modified Successfully ", "ok");
                }

            }

            catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("exception in rollback;");
                }
                sendMessage(response, "The Fund Transfer Transaction Failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("exception in finally");
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
            System.out.println("error in send message");
        }
    }
}

