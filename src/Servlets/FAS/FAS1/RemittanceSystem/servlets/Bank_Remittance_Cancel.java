package Servlets.FAS.FAS1.RemittanceSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import Servlets.Security.classes.UserProfile;

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

public class Bank_Remittance_Cancel extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * POST Method
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         *  Variables Declaration
         */
        String strCommand = "";
        Connection con = null;


        /**
         *  Session Checking
         */
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


        /**
         * Database Connection
         */
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
         * Getting Command Parameter Value
         */
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /**
         * If Command is 'ADD'
         */
        if (strCommand.equalsIgnoreCase("Add")) {
            /** Set Servlet Content Type */
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            /** Variable Declaration */
            PreparedStatement ps = null;
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            ResultSet rs = null;
            ResultSet rs1 = null;
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
            int txtChallan_No = 0;
            Date txtChallan_Date = null, txtCrea_date = null;

            /** Get Account Unit ID */
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** Get Account office code */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            /** Get Date */
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);


            /** Update Remittance Table */
            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                String sql_update =
                    "update FAS_REMITTANCE set STATUS='N' where  ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and CHALLAN_NO=? and CHALLAN_DATE=?";
                String sql_update1 =
                    "update FAS_RECEIPT_MASTER set CHALLAN_NO=0 , CHALLAN_DATE=null , REMITTANCE_STATUS='N'  where ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and CHALLAN_NO=? and CHALLAN_DATE=?  and RECEIPT_TYPE='B' ";

                ps = con.prepareStatement(sql_update);
                ps1 = con.prepareStatement(sql_update1);


                /**
             *   Getting Grid Values such as Challan Number and Challan Date
             */
                String Challan_no[] = request.getParameterValues("Challan_no");
                String challan_date[] =
                    request.getParameterValues("challan_date");
                String cancel_select[] =
                    request.getParameterValues("cancel_select");


                System.out.println("after array declaratration");

                for (int k = 0; k < Challan_no.length; k++) {

                    /** Check Whether Checkbox is checked or not */
                    if (cancel_select[k].equals("CHECKED")) {

                        /** Get Challan Number */
                        try {
                            txtChallan_No = Integer.parseInt(Challan_no[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        /** Get Challan Date */
                        if (!challan_date[k].equalsIgnoreCase("")) {
                            sd = challan_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtChallan_Date = new Date(d.getTime());
                        }

                        /** For Changing status = N in fas_remittance table */
                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setInt(2, cmbOffice_code);
                        ps.setInt(3, txtChallan_No);
                        ps.setDate(4, txtChallan_Date);

                        /** Execute the Query */
                        ps.executeUpdate();

                        /** For changing challan_no = 0 , challan_date = null and remittance_status = c in FAS_Receipt_master table */
                        ps1.setInt(1, cmbAcc_UnitCode);
                        ps1.setInt(2, cmbOffice_code);
                        ps1.setInt(3, txtChallan_No);
                        ps1.setDate(4, txtChallan_Date);

                        /** Execute the Query */
                        ps1.executeUpdate();

                        /** Make Challan Number and Challan Date are 0 and null */
                        txtChallan_No = 0;
                        txtChallan_Date = null;

                        System.out.println("last ");
                        con.commit();
                        sendMessage(response,
                                    "The Bank Remittance Cancelled Successfully ",
                                    "ok");

                    }
                }
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
                sendMessage(response, "The Bank Remittance Cancel failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done here");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
            }
        }
    }

    /**
     * GET Method
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        /**
        * Variables Declaration
        */
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        int empid = 0;
        int txtCash_year = 0;
        int txtCash_Month_hid = 0;

        /**
         *  Session Checking
         */
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");
            System.out.println("user id::" + empProfile.getEmployeeId());
            empid = empProfile.getEmployeeId();
        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        /**
         *  Database Connection
         */

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
         * Set Content Type
         */
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";

        /**
         *  Get Command Variable
         */
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        /**
         * Variables Declaration
         */
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;

        /**
         *  Load Pending Remittance for Cancellation
         */
        if (strCommand.equalsIgnoreCase("loadPendingRemittance")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";

            /** Get Accounting Unit Id  */
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** Get Accounting Office Id */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            /** Initial XML definition */
            xml = "<response><command>loadPendingRemittance</command>";

            /** Get Date */
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            /** Find Cashbook Month and Year */
            System.out.println("b4 getting month and year");
            try {
                txtCash_year = Integer.parseInt(sd[2]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }

            System.out.println("txtCash_year " + txtCash_year);

            try {
                txtCash_Month_hid = Integer.parseInt(sd[1]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);


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

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /*
           String[] sp=request.getParameter("txtCrea_date").split("/");
           System.out.println(sp[0]+" "+sp[1]+" "+sp[2]);

           int check_year=Integer.parseInt(sp[2]);                 // to check in while loop
           int check_day=Integer.parseInt(sp[0]);                  // to check in while loop

           System.out.println(Integer.parseInt(sp[2]));
           System.out.println("here"+check_year);

            String check_date=request.getParameter("txtCrea_date");
            sp=request.getParameter("txtCrea_date").split("/");
            check_date=sp[2]+"/"+sp[1]+"/"+sp[0];

            System.out.println(check_date); // to check in while loop with d/b date it converted to yyyy/mm/dd form


            try
            {
                String sql1="select FINANCIAL_YEAR," +
                "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ," +
                "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
                "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  " +
                "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";

             // date is taken as string from database in above format for checking with receipt date variable ( check_date is string type)
             // checking of dates performed in form of String checking
             ps=con.prepareStatement(sql1);
             rs=ps.executeQuery();
             int Begin_yr,End_yr;
            while(rs.next())
            {
                String[] yr=rs.getString("FINANCIAL_YEAR").split("-");
                 Begin_yr=Integer.parseInt(yr[0]);
                 End_yr=Integer.parseInt(yr[1]);
                                 System.out.println("while");
                                 System.out.println(Begin_yr+ " "+End_yr);
                                 System.out.println(rs.getString("mar_beg")+" "+rs.getString("mar_end"));

                if(check_year==Begin_yr)          //   to check which financial year it belongs
                {
                    if(txtCash_Month_hid>=4 && txtCash_Month_hid<=12)
                    {
                             System.out.println("if 4");
                             if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                             {
                                 txtCash_Month_hid=03;
                             System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                             }
                             else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                             {
                                 txtCash_Month_hid=04;
                             System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                             }
                             else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                             {
                                 txtCash_Month_hid=txtCash_Month_hid+1;
                                 if(txtCash_Month_hid>12)
                                     {
                                     txtCash_Month_hid=1;
                                     txtCash_year=txtCash_year+1;
                                     System.out.println("hello"+txtCash_year);
                                     }
                                 System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                             }
                             else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                             {
                                //txtCash_Month_hid=txtCash_Month_hid;
                                System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                             }
                    }

                }
                else  if(check_year==End_yr)
                {
                    if(txtCash_Month_hid>=1 && txtCash_Month_hid<=3)
                    {
                        txtCash_year=End_yr;System.out.println("if 3");
                        if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                         {
                             txtCash_Month_hid=03;
                         System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                         }
                         else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                         {
                             txtCash_Month_hid=04;
                         System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                         }
                         else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                         {
                             txtCash_Month_hid=txtCash_Month_hid+1;
                             if(txtCash_Month_hid>12)
                             {
                             txtCash_Month_hid=1;
                             txtCash_year=txtCash_year+1;
                             System.out.println("hello"+txtCash_year);
                             }
                             System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                         }
                        else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                        {
                            //txtCash_Month_hid=txtCash_Month_hid;
                            System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                        }
                    }
                }
            }
            ps.close();
            rs.close();
            }
            catch(Exception e)
            {
            sendMessage(response,"The Cash Receipt Creation Failed ","ok");
            System.out.println("exception"+e);
            }

            System.out.println("Cash Book Year is is "+txtCash_year);
            System.out.println("Cash Book Month is is "+txtCash_Month_hid);

          */


            /** Retrieve Data from FAS CROSS REFERRENCE table */

            try {
                ps2 =
 con.prepareStatement("select * from  FAS_CROSS_REFERENCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ORIGINAL_DATE=? and DOC_TYPE='BRM' ");
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                ps2.setInt(3, txtCash_year);
                ps2.setInt(4, txtCash_Month_hid);
                ps2.setDate(5, txtCrea_date);
                System.out.println("before execution ");
                rs2 = ps2.executeQuery();
                System.out.println("after execution ");
                int challan_no = 0;
                int cnt = 0;

                while (rs2.next()) {
                    challan_no = rs2.getInt("VOUCHER_NO");
                    ps =
  con.prepareStatement("select CHALLAN_NO, to_char(REMITTANCE_DATE,'DD/MM/YYYY') as rem_date , REMARKS, AMOUNT_REMITTED from FAS_REMITTANCE  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHALLAN_DATE=? and CHALLAN_NO=? and STATUS is null ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setInt(3, txtCash_year);
                    ps.setInt(4, txtCash_Month_hid);
                    ps.setDate(5, txtCrea_date);
                    ps.setInt(6, challan_no);

                    rs = ps.executeQuery();

                    System.out.println("Executed sucessfully ----------------");

                    xml = xml + "<flag>success</flag>";

                    while (rs.next()) {
                        xml =
 xml + "<Challan_no>" + rs.getInt("CHALLAN_NO") + "</Challan_no>" +
   "<challan_date>" + rs.getString("rem_date") + "</challan_date>" +
   "<Amount>" + rs.getString("AMOUNT_REMITTED") + "</Amount>" + "<remarks>" +
   rs.getString("REMARKS") + "</remarks>";
                        cnt++;
                    }


                }
                if (cnt == 0)
                    xml =
 "<response><command>loadPendingRemittance</command>" + "<flag>failure</flag>";

            } catch (Exception e) {
                System.out.println("Fail to Retrieve Data from FAS CROSS REFERRENCE table " +
                                   e);
                xml =
 "<response><command>loadPendingRemittance</command>" + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
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
            System.out.println("Excep" + e);
        }
    }
}
