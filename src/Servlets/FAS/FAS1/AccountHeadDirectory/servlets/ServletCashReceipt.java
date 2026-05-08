package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.lang.IllegalArgumentException;

import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.lang.IllegalArgumentException.*;

import java.util.*;

import java.text.DateFormat;


public class ServletCashReceipt extends HttpServlet {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


        // opening connection to the database
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connection = DriverManager.getConnection("Jdbc:Odbc:fas");
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(true);


        String command = "";
        System.out.println("servlet calledddddddddddddd");


        String strOffCode = " ";
        String strAcctUnitCode = " ";
        Date strDate_Creation = null;
        int strCashBkYear = 0;
        int strCashBkMonth = 0;

        String strFund_transf_flag = " ";
        String Receipt_type = " ";
        int receiptNo = 0;
        String strCash_Acct_Head_Code = " ";
        String strCD = " ";
        String strBank_Acct_No = " ";
        String strReceivedFrom = " ";
        String strRef_No = " ";
        Date strRef_Date = null;
        int strTotal_Amt = 0;
        String strRemarks = " ";
        int date = 0;


        try {

            System.out.println("good");


            strOffCode = request.getParameter("offcode");
            System.out.println(strOffCode);
            strAcctUnitCode = request.getParameter("accountingUnitCode");
            System.out.println(strAcctUnitCode);


            strCashBkYear =
                    Integer.parseInt(request.getParameter("cashBkYear"));
            System.out.println(strCashBkYear);

            strCashBkMonth =
                    Integer.parseInt(request.getParameter("cashBkMonth"));
            System.out.println(strCashBkMonth);
            strFund_transf_flag = "N";
            System.out.println(strFund_transf_flag);
            Receipt_type = "C";
            System.out.println(Receipt_type);

            strCash_Acct_Head_Code =
                    request.getParameter("cash_accounting code");
            System.out.println(strCash_Acct_Head_Code);

            strCD = request.getParameter("cr_db_Indicator");
            System.out.println(strCD);

            strBank_Acct_No = "nil";
            System.out.println(strBank_Acct_No);

            strReceivedFrom = request.getParameter("received_from");
            System.out.println(strReceivedFrom);

            strRef_No = request.getParameter("ref_num");
            System.out.println(strRef_No);
            strTotal_Amt = Integer.parseInt(request.getParameter("total_amt"));
            System.out.println(strTotal_Amt);
            strRemarks = request.getParameter("remarks");
            System.out.println(strRemarks);
        } catch (Exception e) {
            System.out.println("exce **** " + e);
        }


        // insert values into the table(the destination table is Receipt_Master)

        String sql =
            "insert into RECEIPT_MASTER(OFFICE_CODE,ACCOUNTING_UNIT_CODE,RECEIPT_DATE,CASHBOOK_YEAR,";
        sql =
 sql + "CASHBOOK_MONTH,FUND_TRANSFER_FLAG,RECEIPT_TYPE,RECEIPT_NO,";
        sql =
 sql + "ACCOUNT_HEAD_CODE,CR_DB_INDICATOR,BANK_AC_NO,RECEIVED_FROM,";
        sql =
 sql + "REF_NO,REF_DATE,TOTAL_AMOUNT,REMARKS)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //for date functionality
            //first date field for Date_creation field in the form

            String dateString1 = request.getParameter("date_creation");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1;
            try {
                d1 = dateFormat.parse(dateString1);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat.format(d1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
            System.out.println(date1);


            java.sql.Date date2 = null;


            //for second date field ie.Ref Date field in the form

            String dateString2 = request.getParameter("ref_date");
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d2;
            try {
                d2 = dateFormat.parse(dateString2);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString2 = dateFormat.format(d2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            date2 = java.sql.Date.valueOf(dateString2);
            System.out.println(date2);


            try {

                //coding for generating Receipt_Number according to the date and month criteria.

                //String dateString="01/26/2006";  // user entered date
                //We have already obtained the user entered date and it is in dateString1

                String userdate = request.getParameter("date_creation");
                System.out.println(userdate);

                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date correct_date;
                String correct_date_s = "";
                try {
                    correct_date = dateFormat.parse(userdate);
                    System.out.println("b4 formatting:" + correct_date);
                    correct_date_s =
                            DateFormat.getInstance().format(correct_date);
                    //dateFormat.applyLocalizedPattern("MM/dd/yyyy");

                    System.out.println("the correct date is:" +
                                       correct_date_s);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //date2 = java.sql.Date.valueOf(dateString2);
                //System.out.println(date2);


                java.util.Date da1 = new java.util.Date(correct_date_s);
                System.out.println(da1);
                Date da = new Date(da1.getTime());
                System.out.println(da);

                Date startDate = da;
                Date endDate = da;
                int id = da.getDate();
                int im = da.getMonth() + 1;
                int iy = da1.getYear();
                iy = iy + 1900;

                System.out.println("date : " + id + "/" + im + "/" + iy);

                java.util.Date t1 = new java.util.Date("02/26/" + iy);
                Date c1S = new Date(t1.getTime());
                t1 = new java.util.Date("03/31/" + iy);
                Date c1E = new Date(t1.getTime());

                t1 = new java.util.Date("04/01/" + iy);
                Date c2S = new Date(t1.getTime());
                t1 = new java.util.Date("04/25/" + iy);
                Date c2E = new Date(t1.getTime());
                System.out.println("Checking for : " + da1);
                if ((da.after(c1S) || da.equals(c1S)) &&
                    (da.before(c1E) || da.equals(c1E))) {
                    System.out.println("with in the feb to march");
                    startDate = c1S;
                    endDate = c1E;

                } else if ((da.after(c2S) || da.equals(c2S)) &&
                           (da.before(c2E) || da.equals(c2E))) {
                    System.out.println("with in the april");
                    startDate = c2S;
                    endDate = c2E;
                } else {
                    int idate = da1.getDate();
                    int imonth1 = da1.getMonth();
                    System.out.println("month is : " + imonth1);
                    int imonth2 = imonth1 + 1;
                    if (imonth1 == 11) {
                        if (idate >= 26) {
                            imonth1 = 12;
                            imonth2 = 1;
                            t1 = new java.util.Date(imonth1 + "/26/" + iy);
                            startDate = new Date(t1.getTime());
                            t1 =
  new java.util.Date((imonth2) + "/25/" + (iy + 1));
                            endDate = new Date(t1.getTime());
                        } else {
                            imonth1 = 11;
                            imonth2 = 12;
                            t1 = new java.util.Date(imonth1 + "/26/" + iy);
                            startDate = new Date(t1.getTime());
                            t1 = new java.util.Date((imonth2) + "/25/" + iy);
                            endDate = new Date(t1.getTime());
                        }
                    } else if (imonth1 == 0) {
                        if (idate <= 25) {
                            imonth1 = 12;
                            imonth2 = 1;
                            t1 =
  new java.util.Date(imonth1 + "/26/" + (iy - 1));
                            startDate = new Date(t1.getTime());
                            t1 = new java.util.Date((imonth2) + "/25/" + iy);
                            endDate = new Date(t1.getTime());
                        } else {
                            imonth1 = imonth1 + 1;
                            imonth2 = imonth1 + 1;
                            t1 = new java.util.Date(imonth1 + "/26/" + iy);
                            startDate = new Date(t1.getTime());
                            t1 = new java.util.Date((imonth2) + "/25/" + iy);
                            endDate = new Date(t1.getTime());
                        }
                    } else {
                        if (id >= 26) {
                            t1 =
  new java.util.Date((imonth1 + 1) + "/26/" + iy);
                            startDate = new Date(t1.getTime());
                            t1 =
  new java.util.Date((imonth2 + 1) + "/25/" + iy);
                            endDate = new Date(t1.getTime());
                        } else {
                            t1 = new java.util.Date(imonth1 + "/26/" + iy);
                            startDate = new Date(t1.getTime());
                            t1 = new java.util.Date(imonth2 + "/25/" + iy);
                            endDate = new Date(t1.getTime());
                        }
                    }
                }

                System.out.println("dates to be compared : " + startDate +
                                   " : " + endDate);
                String sql1 =
                    "SELECT max(RECEIPT_NO) AS maximum FROM RECEIPT_MASTER WHERE RECEIPT_DATE>=? and RECEIPT_DATE<=?";

                PreparedStatement ps = connection.prepareStatement(sql1);
                ps.setDate(1, startDate);
                ps.setDate(2, endDate);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    receiptNo = rs.getInt("maximum") + 1;

                    System.out.println(receiptNo);
                }

            } catch (Exception eeee) {
                System.out.println(eeee);
            }


            //for inserting values in the destination table Receipt_Master
            ps = connection.prepareStatement(sql);

            ps.setString(1, strOffCode);
            ps.setString(2, strAcctUnitCode);
            ps.setDate(3, date1);
            ps.setInt(4, strCashBkYear);
            ps.setInt(5, strCashBkMonth);
            ps.setString(6, strFund_transf_flag);
            ps.setString(7, Receipt_type);
            ps.setInt(8, receiptNo);
            ps.setString(9, strCash_Acct_Head_Code);
            ps.setString(10, strCD);
            ps.setString(11, strBank_Acct_No);
            ps.setString(12, strReceivedFrom);
            ps.setString(13, strRef_No);
            ps.setDate(14, date2);
            ps.setInt(15, strTotal_Amt);
            ps.setString(16, strRemarks);

            System.out.println("before update");
            ps.executeUpdate();
            System.out.println("afetre update");
            ps.close();


            //transaction part(ie.destination table is Receipt_Transaction)


            String sltypeCode[] = request.getParameterValues("HSLType");
            String recFrom[] = request.getParameterValues("HRecFrom");
            String Particulars[] = request.getParameterValues("HParticulars");
            String chq_dd = "nil";

            String chq_dd_no = "nil";
            System.out.println(chq_dd_no);
            Date chq_dd_Date = null;
            String Bank_name = "nil";
            System.out.println(Bank_name);
            String Drawee_Branch = "nil";
            System.out.println(Drawee_Branch);
            String Bank_Micr_code = "nil";
            System.out.println(Bank_Micr_code);

            String serno[] = request.getParameterValues("HSerNo");
            String ahc[] = request.getParameterValues("HAhc");
            String slc[] = request.getParameterValues("HSlc");
            String cd[] = request.getParameterValues("HCD");
            //String amount[]=request.getParameterValues("HAmt");
            String am[] = request.getParameterValues("HAmt");
            int len = ahc.length;
            int amount[] = new int[am.length];
            for (int i = 0; i < len; i++) {
                try {
                    System.out.println(" string : " + am[i]);
                    amount[i] = Integer.parseInt(am[i]);
                    System.out.println(" integer : " + amount[i]);
                } catch (Exception e) {
                    System.out.println("exception occured : " + e);
                }

            }
            System.out.println("after amt");


            //to insert values to the destination table Receipt_Transaction
            PreparedStatement ps1 =
                connection.prepareStatement("insert into RECEIPT_TRANSACTION(OFFICE_CODE,ACCOUNTING_UNIT_CODE,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DB_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,RECEIVED_FROM,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,BANK_NAME,DRAWEE_BRANCH,BANK_MICR_CODE,AMOUNT,PARTICULARS)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            System.out.println("length :" + serno.length);
            for (int i = 0; i < serno.length; i++) {
                System.out.println("value " + serno[i]);
            }

            //just like that

            //for second date field ie.Ref Date field in the form
            java.sql.Date date3 = null;

            //to insert the values from the grid in the form to the destination table -- Receipt_Transaction"


            for (int i = 0; i < serno.length; i++) {
                System.out.println("inside for");
                ps1.setString(1, strOffCode);
                ps1.setString(2, strAcctUnitCode);
                ps1.setInt(3, strCashBkYear);
                ps1.setInt(4, strCashBkMonth);
                ps1.setInt(5, receiptNo);
                ps1.setString(6, serno[i]);
                ps1.setString(7, ahc[i]);
                ps1.setString(8, cd[i]);
                ps1.setString(9, sltypeCode[i]);
                ps1.setString(10, slc[i]);
                ps1.setString(11, recFrom[i]);
                ps1.setString(12, chq_dd);
                ps1.setString(13, chq_dd_no);
                ps1.setDate(14, chq_dd_Date);
                ps1.setString(15, Bank_name);
                ps1.setString(16, Drawee_Branch);
                ps1.setString(17, Bank_Micr_code);
                ps1.setInt(18, amount[i]);
                ps1.setString(19, Particulars[i]);


                ps1.executeUpdate();
            }
            ps1.close();


            System.out.println("success");
            pw.write("<html>");
            pw.write("<body>");
            pw.write("Record inserted successfully<br>");
            pw.write("The Receipt Number Generated is:" + receiptNo +
                     "</body></html>");

        } catch (Exception s) {
            System.out.println("IAE:" + s);

            pw.write("<html>");
            pw.write("<body>");
            pw.write("Sorry Record Not Inserted</body></html>");

        }
    }
}
