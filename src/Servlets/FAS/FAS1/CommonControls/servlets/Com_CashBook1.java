package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Com_CashBook1 extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.close();
    }


    public String cb_date(String date_cb) {

        System.out.println("_______________________________________________");
        System.out.println("FINDING CASH BOOK YEAR AND MONTH STARTS........");
        System.out.println("_______________________________________________");

        /**
         * Database Connection
         */
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
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
        }


        /**
         * Variables Declaration
         */

        int txtCash_Month_hid = 0;
        int txtCash_year = 0;
        Date txtCrea_date = null;
        Calendar c;
        int check_financeyear = 0;

        /**
         * Getting Parameters
         */

        /** Get Date from User Interface and store it in string array sp[ ] */
        String[] sd = date_cb.split("/");
        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
        java.util.Date d = c.getTime();
        txtCrea_date = new Date(d.getTime());


        System.out.println("txtCrea_date " + txtCrea_date);
        System.out.println("b4 getting month and year");


        /** Get Year seperately from Input Date */
        try {
            txtCash_year = Integer.parseInt(sd[2]);
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtCash_year " + txtCash_year);


        /** Get Month seperately from Input Date */
        try {
            txtCash_Month_hid = Integer.parseInt(sd[1]);
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtCash_Month_hid " + txtCash_Month_hid);


        String[] sp = date_cb.split("/");
        System.out.println(sp[0] + " " + sp[1] + " " + sp[2]);

        int check_year = Integer.parseInt(sp[2]);
        int check_day = Integer.parseInt(sp[0]);

        System.out.println(Integer.parseInt(sp[2]));
        System.out.println("here" + check_year);
        String check_date = date_cb;

        /** Get Date from Interface */
        sp = date_cb.split("/");


        /** Convert Date in yyyy/mm/dd format */
        check_date = sp[2] + "/" + sp[1] + "/" + sp[0];

        System.out.println(check_date);


        try {
            String sql1 =
                "select FINANCIAL_YEAR," + "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ," +
                "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
                "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  " +
                "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";

            ps = con.prepareStatement(sql1);
            rs = ps.executeQuery();
            int Begin_yr, End_yr;


            while (rs.next()) {
                String[] yr = rs.getString("FINANCIAL_YEAR").split("-");
                Begin_yr = Integer.parseInt(yr[0]);
                End_yr = Integer.parseInt(yr[1]);
                System.out.println("while");
                System.out.println(Begin_yr + " " + End_yr);
                System.out.println(rs.getString("mar_beg") + " " +
                                   rs.getString("mar_end"));

                if (check_year == Begin_yr) {
                    if (txtCash_Month_hid >= 4 && txtCash_Month_hid <= 12) {
                        check_financeyear++;

                        System.out.println("if 4");
                        if ((check_date.compareToIgnoreCase(rs.getString("mar_beg")) >=
                             0) &&
                            (check_date.compareToIgnoreCase(rs.getString("mar_end")) <=
                             0)) {
                            txtCash_Month_hid = 03;
                            System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg")) +
                                               "mar" + txtCash_Month_hid);
                        } else if ((check_date.compareToIgnoreCase(rs.getString("apr_beg")) >=
                                    0) &&
                                   (check_date.compareToIgnoreCase(rs.getString("apr_end")) <=
                                    0)) {
                            txtCash_Month_hid = 04;
                            System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg")) +
                                               "apr" + txtCash_Month_hid);
                        } else if (check_day >=
                                   rs.getInt("CB_FROM_DATE_FOR_OTH")) {
                            txtCash_Month_hid = txtCash_Month_hid + 1;
                            if (txtCash_Month_hid > 12) {
                                txtCash_Month_hid = 1;
                                txtCash_year = txtCash_year + 1;
                                System.out.println("hello" + txtCash_year);
                            }
                            System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH") +
                                               "oth1 " + txtCash_Month_hid);
                        } else if (check_day <=
                                   rs.getInt("CB_TO_DATE_FOR_OTH")) {
                            System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH") +
                                               "oth2 " + txtCash_Month_hid);
                        }
                    }

                } else if (check_year == End_yr) {
                    if (txtCash_Month_hid >= 1 && txtCash_Month_hid <= 3) {
                        check_financeyear++;

                        txtCash_year = End_yr;
                        System.out.println("if 3");
                        if ((check_date.compareToIgnoreCase(rs.getString("mar_beg")) >=
                             0) &&
                            (check_date.compareToIgnoreCase(rs.getString("mar_end")) <=
                             0)) {
                            txtCash_Month_hid = 03;
                            System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg")) +
                                               "mar" + txtCash_Month_hid);
                        } else if ((check_date.compareToIgnoreCase(rs.getString("apr_beg")) >=
                                    0) &&
                                   (check_date.compareToIgnoreCase(rs.getString("apr_end")) <=
                                    0)) {
                            txtCash_Month_hid = 04;
                            System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg")) +
                                               "apr" + txtCash_Month_hid);
                        } else if (check_day >=
                                   rs.getInt("CB_FROM_DATE_FOR_OTH")) {
                            txtCash_Month_hid = txtCash_Month_hid + 1;
                            if (txtCash_Month_hid > 12) {
                                txtCash_Month_hid = 1;
                                txtCash_year = txtCash_year + 1;
                                System.out.println("hello" + txtCash_year);
                            }
                            System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH") +
                                               "oth1 " + txtCash_Month_hid);
                        } else if (check_day <=
                                   rs.getInt("CB_TO_DATE_FOR_OTH")) {
                            System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH") +
                                               "oth2 " + txtCash_Month_hid);
                        }
                    }
                }
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("exception" + e);
        }


        /**
       *   Get Date from User Interface for Finding Cash book year and Month
       *   If the input date is less than '26-August-2007' Find Cash Book month and year
       *   else follow the ordinary calendar year and month as cash book month and year
       */


        String[] input_date = date_cb.split("/");
        int yer = Integer.parseInt(input_date[2]);
        int mn = Integer.parseInt(input_date[1]);
        int dt = Integer.parseInt(input_date[0]);


        if ((dt <= 25) && (mn <= 8) && (yer <= 2007)) {
            System.out.println("Old Cash Book Year is " + txtCash_year);
            System.out.println("Old Cash Book Month is " + txtCash_Month_hid);
            System.out.println("______________________________________________________");
            return (txtCash_year + "/" + txtCash_Month_hid + "/" +
                    check_financeyear);
        }

        else if ((dt <= 31) && (mn <= 8) && (yer <= 2007)) {
            int cash_book_month = (Integer.parseInt(input_date[1]) + 1);

            System.out.println("26 to 31 august Cash Book Year is " +
                               Integer.parseInt(input_date[2]));
            System.out.println("26 to 31 august Cash Book Month is " +
                               cash_book_month);
            System.out.println("______________________________________________________");
            return (Integer.parseInt(input_date[2]) + "/" + cash_book_month +
                    "/" + check_financeyear);

        } else {
            System.out.println("New Cash Book Year is " +
                               Integer.parseInt(input_date[2]));
            System.out.println("New Cash Book Month is " +
                               Integer.parseInt(input_date[1]));
            System.out.println("______________________________________________________");
            return (Integer.parseInt(input_date[2]) + "/" +
                    Integer.parseInt(input_date[1]) + "/" + check_financeyear);

        }


    } // End of cb_date function

}  // End of Main Function 