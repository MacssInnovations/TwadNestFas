package Servlets.FAS.FAS1.FundTransferSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class FundAllotmentSubmitServ extends HttpServlet {
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
        String divnamestr = request.getParameter("txtDivname");
        System.out.println("division name:" + divnamestr);
        int divname = Integer.parseInt(divnamestr);

        //      String fundtype1[]=request.getParameterValues("work_type_hid");
        //      String refno1[]=request.getParameterValues("let_no_hid");
        //      String refdate1[]=request.getParameterValues("let_date_hid");
        //      String amtallot1[]=request.getParameterValues("alot_amt_hid");
        //       String remarks1[]=request.getParameterValues("remarks_hid");

        /* to retreive a single row
 String fundtype=request.getParameter("work_type_hid");
 System.out.println("fund type"+fundtype);
 String refno=request.getParameter("let_no_hid");
 System.out.println("refno"+refno);
 String refdate=request.getParameter("let_date_hid");
 System.out.println("refdate"+refdate);
 //  System.out.println(to_date(refdate,'dd/mm,yyyy'))
 System.out.println("letter date is ------------"+refdate);
 String amt=request.getParameter("alot_amt_hid");
 System.out.println("amount"+amt);
 String remarks=request.getParameter("remarks_hid");
 System.out.println("remarks"+remarks);
 int year=Integer.parseInt(cashbookyear);
 int month=Integer.parseInt(cashbookmonth);
 System.out.println("Month:========"+month);
 int accunit=Integer.parseInt(accunitid);
 int office_id=Integer.parseInt(officeid);
 int amtallot=Integer.parseInt(amt); */


        //to retreive multiple rows in a grid

        String divnamenew[] = request.getParameterValues("officeid_hid");
        int divname1[] = new int[divnamenew.length];
        for (int i = 0; i < divnamenew.length; i++) {
            divname1[i] = Integer.parseInt(divnamenew[i]);
        }
        String fundtype[] = request.getParameterValues("work_type_hid");
        //System.out.println("fund type"+fundtype);
        String refno[] = request.getParameterValues("let_no_hid");
        for (int k = 0; k < refno.length; k++)
            System.out.println("refnos       aaaa" + refno[k]);
        String refdate[] = request.getParameterValues("let_date_hid");
        //System.out.println("refdate"+refdate);

        //System.out.println("letter date is ------------"+refdate);
        String amt[] = request.getParameterValues("alot_amt_hid");
        //System.out.println("amount"+amt);
        String remarks[] = request.getParameterValues("remarks_hid");
        //System.out.println("remarks"+remarks);

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

        /*  String fundtype=request.getParameter("worktype");
        System.out.println("fund type"+fundtype);
        String refno=request.getParameter("LetNo");
        System.out.println("refno"+refno);
        String refdate=request.getParameter("LetterDate");
        System.out.println("refdate"+refdate);
      //  System.out.println(to_date(refdate,'dd/mm,yyyy'))
        System.out.println("letter date is ------------"+refdate);
        String amt=request.getParameter("alotAmt");
        System.out.println("amount"+amt);
        String remarks=request.getParameter("txtRemarks");
        System.out.println("remarks"+remarks);
        int year=Integer.parseInt(cashbookyear);
        int month=Integer.parseInt(cashbookmonth);
        System.out.println("Month:========"+month);
        int accunit=Integer.parseInt(accunitid);
        int office_id=Integer.parseInt(officeid);
        int amtallot=Integer.parseInt(amt);*/
        int slno = 0;
        int bankid = 0, branchid = 0, accno = 0;
        boolean flag = true;
        try {

            try {
                ps3 =
 con.prepareStatement("select HO_BANK_ID,HO_BRANCH_ID,HO_ACCOUNT_NO,TOTAL_AMOUNT,HO_REF_NO,HO_REF_DATE from fas_fund_trf_from_ho_master where " +
                      "cashbook_month=? and cashbook_year=? and accounting_unit_id=? and accounting_for_office_id=? ");
                ps3.setInt(1, month);
                ps3.setInt(2, year);
                ps3.setInt(3, accunit);
                ps3.setInt(4, office_id);
                rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    bankid = rs3.getInt("HO_BANK_ID");
                    branchid = rs3.getInt("HO_BRANCH_ID");
                    accno = rs3.getInt("HO_ACCOUNT_NO");
                    tot_amt = rs3.getInt("TOTAL_AMOUNT");
                    ho_refno = rs3.getInt("HO_REF_NO");
                    ho_refdate = rs3.getDate("HO_REF_DATE");
                    System.out.println("bankid" + bankid);
                    System.out.println("branch id" + branchid);
                    System.out.println(accno);
                    System.out.println("total amt from ho master:" + tot_amt);
                }
                rs3.close();
                ps3.close();

                //slno+=1;
                System.out.println("here it comes-----------");
                ps3 =
 con.prepareStatement("select * from FUND_ALLOTMENT_MASTER where HO_ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                ps3.setInt(1, accno);
                ps3.setInt(2, year);
                ps3.setInt(3, month);
                rs3 = ps3.executeQuery();

                while (rs3.next()) {
                    flag = false;
                }
                //else
                //  flag=true;


                System.out.println("flag value is :" + flag);
                if (flag) {
                    ps2 =
 con.prepareStatement("insert into FUND_ALLOTMENT_MASTER(ACCOUNTING_UNIT_ID,office_id,CASHBOOK_YEAR,cashbook_month,REF_NO,REF_DATE,Cheque_AMOUNT,HO_BANK_ID,HO_BRANCH_ID,HO_ACCOUNT_NO) values(?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?)");
                    ps2.setInt(1, accunit);
                    ps2.setInt(2, office_id);
                    ps2.setInt(3, year);
                    ps2.setInt(4, month);
                    ps2.setInt(5, ho_refno);
                    ps2.setDate(6, ho_refdate);
                    ps2.setInt(7, tot_amt);
                    ps2.setInt(8, bankid);
                    ps2.setInt(9, branchid);
                    ps2.setInt(10, accno);

                    int x = ps2.executeUpdate();
                    System.out.println("x is----" + x);
                    ps2.close();
                    //rs3.close();

                } else {
                    ps2 =
 con.prepareStatement("update FUND_ALLOTMENT_MASTER set ACCOUNTING_UNIT_ID=?,office_id=?,CASHBOOK_YEAR=?,cashbook_month=?,REF_NO=?,REF_DATE=to_date(?,'dd/mm/yyyy'),Cheque_AMOUNT=?,HO_BANK_ID=?,HO_BRANCH_ID=? where HO_ACCOUNT_NO=?");
                    ps2.setInt(1, accunit);
                    ps2.setInt(2, office_id);
                    ps2.setInt(3, year);
                    ps2.setInt(4, month);
                    ps2.setInt(5, ho_refno);
                    ps2.setDate(6, ho_refdate);
                    ps2.setInt(7, tot_amt);
                    ps2.setInt(8, bankid);
                    ps2.setInt(9, branchid);
                    ps2.setInt(10, accno);

                    int x = ps2.executeUpdate();
                    System.out.println("x is++++----" + x);
                    ps2.close();
                }
            } catch (SQLException e) {
                System.out.println("Error while inserting data into the table" +
                                   e);
            }
            System.out.println("This is between master and transaction........");
            try {
                boolean flag1 = true;

                for (int j = 0; j < refno.length; j++) {
                    System.out.println("divname1  values     :" + divname1[j]);
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

                    System.out.println("flag value in transaction is:  " +
                                       flag1);
                    if (flag1) {
                        try {
                            System.out.println("here is ok1=========");


                            System.out.println(slno);
                            System.out.println(accunit);
                            System.out.println(office_id);
                            System.out.println(year);
                            System.out.println(month);
                            //   System.out.println(fundtype);
                            //   System.out.println(refno);
                            //   System.out.println(refdate);
                            //   System.out.println(amtallot);
                            //   System.out.println(remarks);
                            System.out.println(bankid);
                            System.out.println(branchid);
                            System.out.println(accno);

                            //  {
                            ps4 =
 con.prepareStatement("insert into FUND_ALLOTMENT_TRANSACTION(SL_NO,ACCOUNTING_UNIT_ID,OFFICE_ID," +
                      "CASHBOOK_YEAR,cashbook_month,FUND_TYPE,REF_NO,REF_DATE,AMOUNT,PARTICULARS,FUND_REQUESTED) values(?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?)");
                            slno += 1;


                            ps4.setInt(1, slno);
                            ps4.setInt(2, accunit);
                            ps4.setInt(3, divname1[j]);
                            ps4.setInt(4, year);
                            ps4.setInt(5, month);

                            ps4.setString(6, fundtype[j]);
                            ps4.setString(7, refno[j]);
                            ps4.setString(8, refdate[j]);
                            ps4.setInt(9, amtallot[j]);
                            ps4.setString(10, remarks[j]);
                            ps4.setInt(11, amtreq[j]);

                            /*    ps4.setString(6,fundtype1[k]);
                                    ps4.setString(7,refno1[k]);
                                    ps4.setString(8,refdate1[k]);
                                    ps4.setInt(9,Integer.parseInt(amtallot1[k]));
                                    ps4.setString(10,remarks1[k]);*/
                            // ps4.setInt(11,bankid);
                            //  ps4.setInt(12,branchid);
                            // ps4.setInt(13,accno);
                            System.out.println("OKKKKKKKKKKKKKKKK");
                            int y = ps4.executeUpdate();
                            System.out.println("here is ok2=========");
                            //    }
                            //   }
                            /*else {
                                       ps4=con.prepareStatement("update FUND_ALLOTMENT_TRANSACTION SL_NO=?,ACCOUNTING_UNIT_ID=?,OFFICE_ID=?," +
                                       "CASHBOOK_YEAR=?,cashbook_month=?,FUND_TYPE=?,REF_NO=?,REF_DATE=to_date(?,'dd/mm/yyyy',AMOUNT=?,PARTICULARS=?,FUND_REQUESTED=?");
                                       slno+=1;


                                       ps4.setInt(1,slno);
                                       ps4.setInt(2,accunit);
                                       ps4.setInt(3,divname1[j]);
                                       ps4.setInt(4,year);
                                       ps4.setInt(5,month);

                                           ps4.setString(6,fundtype[j]);
                                           ps4.setString(7,refno[j]);
                                           ps4.setString(8,refdate[j]);
                                           ps4.setInt(9,amtallot[j]);
                                           ps4.setString(10,remarks[j]);
                                           ps4.setInt(11,amtreq[j]);


                                       // ps4.setInt(11,bankid);
                                       //  ps4.setInt(12,branchid);
                                       // ps4.setInt(13,accno);
                                       System.out.println("OKKKKKKKKKKKKKKKK");
                                       int y=ps4.executeUpdate();
                                       System.out.println("here is ok2=========");
                                       //    }
                                   }*/
                        } catch (Exception e) {
                            System.out.println("error");
                        }
                    } else {

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
