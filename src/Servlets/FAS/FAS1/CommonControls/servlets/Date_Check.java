package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.Date;

import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.*;


public class Date_Check extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Calendar c;
        String doc_date = null;

        /**
         * Get Parameter
         */
        String check_date[] = request.getParameter("_date").split("/");
        System.out.println("servlet date is " + check_date[0]);
        System.out.println("servlet date is " + check_date[1]);
        System.out.println("servlet date is " + check_date[2]);


        /**
         * Get Receipt Data
         */

        int date_value = 0;
        int mm = 0;
        String month_value = null;
        int year_value = 0;

        String[] sd = request.getParameter("doc_date").split("/");
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


        System.out.println("doc Date --->" + doc_date);


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
        Statement st = null;
        ResultSet rs1 = null;
        String ora_dt = null;
        String ora_mn = null;
        String ora_yr = null;


        /**
         * Get Date which is 180 days ( 6 months ) less than current date from oracle
         */
        String sql =
            "SELECT to_char(to_date('" + doc_date + "','dd-mon-yyyy') -180,   'dd') AS dt, to_char(to_date('" +
            doc_date + "','dd-mon-yyyy') -180,   'mm') AS mn, to_char(to_date('" + doc_date +
            "','dd-mon-yyyy') -180,   'yyyy') AS yr  ";
        
        
        
       

        System.out.println(sql);

        try {
            st = con.createStatement();
            rs1 = st.executeQuery(sql);
            while (rs1.next()) {
                ora_dt = rs1.getString("dt");
                ora_mn = rs1.getString("mn");
                ora_yr = rs1.getString("yr");
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        System.out.println(ora_dt);
        System.out.println(ora_mn);
        System.out.println(ora_yr);


        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        /**
         * Cheque Date
         */
        int ch_year1 = Integer.parseInt(check_date[2]);
        int ch_month1 = Integer.parseInt(check_date[1]);
        int ch_day1 = Integer.parseInt(check_date[0]);


        /**
         * Oracle Date ( Current Date - 180 days )
         */
        int ora_year2 = Integer.parseInt(ora_yr);
        int ora_month2 = Integer.parseInt(ora_mn);
        int ora_day2 = Integer.parseInt(ora_dt);


        cal1.set(ch_year1, ch_month1, ch_day1);
        cal2.set(ora_year2, ora_month2, ora_day2);

        String ch_date1 = "" + ch_year1 + "//" + ch_month1 + "//" + ch_day1;
        String ora_date2 =
            "" + ora_year2 + "//" + ora_month2 + "//" + ora_day2;


        System.out.println("date 1 -" + ch_date1);
        System.out.println("date 2 -" + ora_date2);

        String xml = "<response><command>cheque_verification</command>";
        if (cal1.after(cal2) || cal1.equals(cal2)) {
            xml = xml + "<check>valid</check>";
            System.out.println("Valid Cheque");
        } else if (cal1.before(cal2)) {
            xml = xml + "<check>invalid</check>";
            System.out.println("Invalid Cheque");
        }

        xml = xml + "<flag>success</flag>";
        xml = xml + "</response>";


        /*
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat monthFormat = new SimpleDateFormat("MM");
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date dt = new Date();
        out.println(dateFormat.format(dt));
        out.println(monthFormat.format(dt));
        out.println(yearFormat.format(dt));

       */

        out.println(xml);
        System.out.println(xml);
        out.close();
    }
}
