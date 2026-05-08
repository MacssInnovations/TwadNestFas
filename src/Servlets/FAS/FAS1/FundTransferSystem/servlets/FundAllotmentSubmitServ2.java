package Servlets.FAS.FAS1.FundTransferSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class FundAllotmentSubmitServ2 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        String strCommand = "";
        Connection con = null;
        ResultSet rs3 = null;
        ResultSet rs2 = null;
        ResultSet rs4 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps2 = null;
        String xml = "";
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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        out.println("<html>");
        out.println("<head><title>FundAllotmentSubmitServ</title></head>");
        out.println("<body>");
        String accunitid = request.getParameter("cmbAcc_UnitCode");
        System.out.println("unit id" + accunitid);
        String officeid = request.getParameter("cmbOffice_code");
        System.out.println("office_id" + officeid);
        String cashbookyear = request.getParameter("txtCB_Year");
        System.out.println("year" + cashbookyear);
        String cashbookmonth = request.getParameter("txtCB_Month");
        System.out.println("month" + cashbookmonth);


        //to retreive multiple rows in a grid fundtype_hid

        String divnamenew[] = request.getParameterValues("trans_off_hid");
        int divname1[] = new int[divnamenew.length];
        for (int i = 0; i < divnamenew.length; i++) {
            divname1[i] = Integer.parseInt(divnamenew[i]);
        }

        String refno[] = request.getParameterValues("let_no_hid");
        for (int k = 0; k < refno.length; k++)
            System.out.println("refnos       aaaa" + refno[k]);
        String refdate[] = request.getParameterValues("let_date_hid");
        System.out.println("refdate" + refdate);

        String offrefno[] = request.getParameterValues("off_let_no_hid");
        for (int k = 0; k < refno.length; k++)
            System.out.println("offrefnos       aaaa" + offrefno[k]);
        String offrefdate[] = request.getParameterValues("off_let_date_hid");
        System.out.println("offrefdate" + offrefdate);

        System.out.println("offletter date is ------------" + offrefdate);
        String amt[] = request.getParameterValues("alot_amt_hid");
        System.out.println("amount" + amt);
        String reason[] = request.getParameterValues("reason_hid");
        String remarks = request.getParameter("txtRemarks");
        System.out.println("remarks" + remarks);

        String mas_letNo = null;
        mas_letNo = (request.getParameter("txtLetNo"));
        String mas_letDate = null;
        mas_letDate = (request.getParameter("txtLetterDate"));

        System.out.println("Master Letter no and Date :" + mas_letNo + "  " +
                           mas_letDate);
        String[] fundtypenew = request.getParameterValues("fundtype_hid");
        String[] fundtypechar = new String[fundtypenew.length];
        for (int i = 0; i < fundtypenew.length; i++) {
            fundtypenew[i] = fundtypenew[i].trim();
            fundtypechar[i] = fundtypenew[i].substring(0, 1);
            System.out.println("The Fund Type Value = " + fundtypechar[i]);
        }

        String[] reasonnew = request.getParameterValues("reason_hid");
        String[] reasonchar = new String[reasonnew.length];
        for (int i = 0; i < reasonnew.length; i++) {
            reasonnew[i] = reasonnew[i].trim();
            reasonchar[i] = reasonnew[i];
            System.out.println("The reason Value = " + reasonchar[i]);
        }


        int year = Integer.parseInt(cashbookyear);
        int month = Integer.parseInt(cashbookmonth);
        System.out.println("Month:========" + month);
        int accunit = Integer.parseInt(accunitid);
        int office_id = Integer.parseInt(officeid);
        int amtallot[] = new int[amt.length];
        for (int i = 0; i < amt.length; i++) {
            amtallot[i] = Integer.parseInt(amt[i]);
            System.out.println("alloted amount is-------" + amtallot[i]);

        }
        String reqamt[] = request.getParameterValues("req_amt_hid");
        int amtreq[] = new int[reqamt.length];
        for (int i = 0; i < reqamt.length; i++) {
            amtreq[i] = Integer.parseInt(reqamt[i]);
            System.out.println("requested amount is-------" + amtreq[i]);

        }
        int voucherno = 0;

        voucherno = Integer.parseInt(request.getParameter("txtVoucherNo"));
        System.out.println("voucher no is :" + voucherno);


        int slno = 0;
        int bankid = 0, branchid = 0;
        long accno = 0;
        boolean flag = true;
        String cheqOrDd = null;
        String cheqOrDdNo = null;
        java.sql.Date cheqOrDdDate = null;
        try {
            PreparedStatement psx = null;
            ResultSet rsx = null;

            System.out.println("This is between master and transaction........");
            try {
                boolean flag1 = true;
                System.out.println("reference no length is :::::::" +
                                   refno.length);
                for (int j = 0; j < refno.length; j++) {
                    ps3 =
 con.prepareStatement("select * from FUND_ALLOTMENT_TRANSACTION where CASHBOOK_YEAR=? and  cashbook_month=? and office_id=? ");
                    ps3.setInt(1, year);
                    ps3.setInt(2, month);
                    ps3.setInt(3, divname1[j]);
                    rs3 = ps3.executeQuery();
                    if (rs3.next()) {

                        flag1 = false;
                    } else
                        flag1 = true;


                    psx =
 con.prepareStatement("select * from FAS_FUND_TRF_FROM_HO_TRN where CASHBOOK_YEAR=? and  cashbook_month=? and TRANSFER_TO_OFFICE_ID=? ");
                    psx.setInt(1, year);
                    psx.setInt(2, month);
                    psx.setInt(3, divname1[j]);
                    rsx = psx.executeQuery();
                    if (rsx.next()) {
                        cheqOrDd = rsx.getString("CHEQUE_OR_DD");
                        cheqOrDdNo = rsx.getString("CHEQUE_DD_NO");
                        cheqOrDdDate = rsx.getDate("CHEQUE_DD_DATE");
                        //flag1=false;
                    }
                    System.out.println("cheqie dd values " + cheqOrDd + " " +
                                       cheqOrDdNo + " " + cheqOrDdDate);
                    //else
                    //flag1=true;

                    System.out.println("flag value in transaction is:  " +
                                       flag1);
                    if (flag1) {
                        try {
                            System.out.println("here is ok1=========");
                            System.out.println(divname1[j]);
                            System.out.println(year);
                            System.out.println(month);
                            System.out.println(fundtypechar[j]);
                            System.out.println(refno[j]);
                            System.out.println(refdate[j]);
                            System.out.println(amtallot[j]);
                            System.out.println(remarks);
                            System.out.println(amtreq[j]);
                            System.out.println(voucherno);
                            System.out.println(updatedby);
                            System.out.println(sqldt);
                            System.out.println(cheqOrDd);
                            System.out.println(cheqOrDdNo);
                            System.out.println(cheqOrDdDate);
                            System.out.println(offrefno[j]);
                            System.out.println(offrefdate[j]);

                            ps4 =
 con.prepareStatement("insert into FUND_ALLOTMENT_TRANSACTION(SL_NO,ACCOUNTING_UNIT_ID,OFFICE_ID," +
                      "CASHBOOK_YEAR,cashbook_month,FUND_TYPE,REF_NO,REF_DATE,AMOUNT,PARTICULARS,FUND_REQUESTED,voucher_no,updated_by_user_id,updated_date,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,HO_REF_NO,HO_REF_DATE,REASON,LETTER_GEN) values(?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?)");
                            slno += 1;
                            System.out.println(slno);

                            ps4.setInt(1, slno);
                            ps4.setInt(2, accunit);
                            ps4.setInt(3, divname1[j]);
                            ps4.setInt(4, year);
                            ps4.setInt(5, month);
                            ps4.setString(6, fundtypechar[j]);
                            ps4.setString(7, refno[j]);
                            ps4.setString(8, refdate[j]);
                            ps4.setInt(9, amtallot[j]);
                            ps4.setString(10, remarks);
                            ps4.setInt(11, amtreq[j]);
                            ps4.setInt(12, voucherno);
                            ps4.setString(13, updatedby);
                            ps4.setTimestamp(14, sqldt);
                            ps4.setString(15, cheqOrDd);
                            ps4.setString(16, cheqOrDdNo);
                            ps4.setDate(17, cheqOrDdDate);
                            ps4.setString(18, offrefno[j]);
                            ps4.setString(19, offrefdate[j]);
                            ps4.setString(20, reasonchar[j]);
                            ps4.setString(21, "N");

                            System.out.println("OKKKKKKKKKKKKKKKK");
                            int y = ps4.executeUpdate();
                            System.out.println("here is ok2=========");

                        } catch (Exception e) {
                            System.out.println("error in allotment tranctaction" +
                                               e);
                        }
                    } else {

                    }

                    System.out.println("Insertion in transaction has completed....");


                    try {


                        /* psnew=con.prepareStatement("select voucher_no  from fas_fund_trf_from_ho_trn where cashbook_month=? and cashbook_year=? and accounting_unit_id=? and " +
                                      " accounting_for_office_id=? and transfer_to_office_id=?");
                                           psnew.setInt(1,month);
                                           psnew.setInt(2,year);
                                           psnew.setInt(3,accunit);
                                           psnew.setInt(4,office_id);
                                           psnew.setInt(5,divname1[j]);
                                           rsnew=psnew.executeQuery();
                                        if(rsnew.next())   {
                                             voucherno=rsnew.getInt("voucher_no");
                                             System.out.println("Voucher Number is :"+voucherno);
                                         }*/


                        try {
                            ps3 =
 con.prepareStatement("select HO_BANK_ID,HO_BRANCH_ID,HO_ACCOUNT_NO,TOTAL_AMOUNT,HO_REF_NO,HO_REF_DATE from fas_fund_trf_from_ho_master where " +
                      " cashbook_month=? and cashbook_year=? and accounting_unit_id=? and accounting_for_office_id=? and voucher_no=?");
                            ps3.setInt(1, month);
                            ps3.setInt(2, year);
                            ps3.setInt(3, accunit);
                            ps3.setInt(4, office_id);
                            ps3.setInt(5, voucherno);
                            rs3 = ps3.executeQuery();
                            if (rs3.next()) {
                                bankid = rs3.getInt("HO_BANK_ID");
                                branchid = rs3.getInt("HO_BRANCH_ID");
                                accno =
rs3.getLong("HO_ACCOUNT_NO"); //it could be Long value
                                tot_amt = rs3.getInt("TOTAL_AMOUNT");
                                // ho_refno=rs3.getInt("HO_REF_NO");
                                // ho_refdate=rs3.getDate("HO_REF_DATE");
                                System.out.println("bankid" + bankid);
                                System.out.println("branch id" + branchid);
                                System.out.println(accno);
                                System.out.println("total amt from ho master:" +
                                                   tot_amt);
                            }
                            rs3.close();
                            ps3.close();
                        } catch (Exception e) {
                            System.out.println("Error in getttign data from the trn table..." +
                                               e);
                        }

                        //slno+=1;
                        System.out.println("here it comes-----------");
                        ps3 =
 con.prepareStatement("select * from FUND_ALLOTMENT_MASTER where accounting_unit_id=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and office_id=? and voucher_no=?");
                        ps3.setInt(1, accunit);
                        ps3.setInt(2, year);
                        ps3.setInt(3, month);
                        ps3.setInt(4, office_id);
                        ps3.setInt(5, voucherno);
                        rs3 = ps3.executeQuery();

                        if (rs3.next()) {
                            flag = false;
                        } else
                            flag = true;
                        System.out.println("flag value while inserting into master...." +
                                           flag);
                        if (flag) {

                            System.out.println("comes inside flag if part");
                            ps2 =
 con.prepareStatement("insert into FUND_ALLOTMENT_MASTER(ACCOUNTING_UNIT_ID,office_id,CASHBOOK_YEAR,cashbook_month,REF_NO,REF_DATE,Cheque_AMOUNT,HO_BANK_ID,HO_BRANCH_ID,HO_ACCOUNT_NO,voucher_no,remarks,updated_by_user_id,updated_date) values(?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?)");
                            ps2.setInt(1, accunit);
                            ps2.setInt(2, office_id);
                            ps2.setInt(3, year);
                            ps2.setInt(4, month);
                            ps2.setString(5, mas_letNo);
                            ps2.setString(6, mas_letDate);
                            ps2.setInt(7, tot_amt);
                            ps2.setInt(8, bankid);
                            ps2.setInt(9, branchid);
                            ps2.setLong(10, accno);
                            ps2.setInt(11, voucherno);
                            ps2.setString(12, remarks);
                            ps2.setString(13, updatedby);
                            ps2.setTimestamp(14, sqldt);
                            int x = ps2.executeUpdate();
                            System.out.println("x is----" + x);
                            ps2.close();
                            //rs3.close();

                        } else {

                            System.out.println("comes inside flag else part");
                            ps2 =
 con.prepareStatement("update FUND_ALLOTMENT_MASTER set ACCOUNTING_UNIT_ID=?,office_id=?,CASHBOOK_YEAR=?,cashbook_month=?,REF_NO=?,REF_DATE=to_date(?,'dd/mm/yyyy'),Cheque_AMOUNT=?,HO_BANK_ID=?,HO_BRANCH_ID=?,voucher_no=?,remarks=?,updated_by_user_id=?,updated_date=? where HO_ACCOUNT_NO=?");
                            ps2.setInt(1, accunit);
                            ps2.setInt(2, office_id);
                            ps2.setInt(3, year);
                            ps2.setInt(4, month);
                            ps2.setString(5, mas_letNo);
                            ps2.setString(6, mas_letDate);
                            ps2.setInt(7, tot_amt);
                            ps2.setInt(8, bankid);
                            ps2.setInt(9, branchid);
                            ps2.setInt(10, voucherno);
                            ps2.setString(11, remarks);
                            ps2.setString(12, updatedby);
                            ps2.setTimestamp(13, sqldt);
                            ps2.setLong(14, accno);

                            int x = ps2.executeUpdate();
                            System.out.println("x is++++----" + x);
                            ps2.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Error while inserting data into the table" +
                                           e);
                    }


                }

            }

            catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
            }

        }

        catch (Exception e) {
            System.out.println("error" + e);
        }

        sendMessage(response, "The data has been inserted Successfully ",
                    "ok");
        //out.println("<p>The servlet has received a POST. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            System.out.println("Inside.....................");
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("error in messenger" + e);
        }
    }


}
