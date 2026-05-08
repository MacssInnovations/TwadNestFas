package Servlets.FAS.FAS1.AuthorizationSystem.servlets;

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
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Authorization_JAO_Create_old extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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
        CallableStatement cs1 = null;
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
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            Date txtCrea_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

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

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

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

            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);


            String[] sp = request.getParameter("txtCrea_date").split("/");
            System.out.println(sp[0] + " " + sp[1] + " " + sp[2]);
            int check_year = Integer.parseInt(sp[2]); // to check in while loop
            int check_day = Integer.parseInt(sp[0]); // to check in while loop
            System.out.println(Integer.parseInt(sp[2]));
            System.out.println("here" + check_year);

            String check_date = request.getParameter("txtCrea_date");
            sp = request.getParameter("txtCrea_date").split("/");
            check_date = sp[2] + "/" + sp[1] + "/" + sp[0];

            System.out.println(check_date); // to check in while loop with d/b date it converted to yyyy/mm/dd form
            try {
                String sql1 =
                    "select FINANCIAL_YEAR," + "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ," +
                    "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
                    "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  " +
                    "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";

                // date is taken as string from database in above format for checking with receipt date variable ( check_date is string type)
                // checking of dates performed in form of String checking
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

                    if (check_year ==
                        Begin_yr) //   to check which financial year it belongs
                    {
                        if (txtCash_Month_hid >= 4 &&
                            txtCash_Month_hid <= 12) {
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
                                                   "oth1 " +
                                                   txtCash_Month_hid);
                            } else if (check_day <=
                                       rs.getInt("CB_TO_DATE_FOR_OTH")) {
                                //txtCash_Month_hid=txtCash_Month_hid;
                                System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH") +
                                                   "oth2 " +
                                                   txtCash_Month_hid);
                            }
                        }

                    } else if (check_year == End_yr) {
                        if (txtCash_Month_hid >= 1 && txtCash_Month_hid <= 3) {
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
                                if (txtCash_Month_hid >
                                    12) // No chance for this condition
                                {
                                    txtCash_Month_hid = 1;
                                    txtCash_year = txtCash_year + 1;
                                    System.out.println("hello" + txtCash_year);
                                }
                                System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH") +
                                                   "oth1 " +
                                                   txtCash_Month_hid);
                            } else if (check_day <=
                                       rs.getInt("CB_TO_DATE_FOR_OTH")) {
                                //txtCash_Month_hid=txtCash_Month_hid;
                                System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH") +
                                                   "oth2 " +
                                                   txtCash_Month_hid);
                            }
                        }
                    }
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                sendMessage(response,
                            "The Authorization has failed to find cash book month",
                            "ok");
                System.out.println("exception" + e);
            }


            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                String txtReferNO_edit = "", txtRemak_edit =
                    "", cmbSubSystemType = "", txtRefdate =
                    ""; //for cross reference
                Date txtReferDate_edit = null;
                String radAuth_MC = "";
                int txtAuth_By = 0;
                UserProfile empProfile =
                    (UserProfile)session.getAttribute("UserProfile");

                txtAuth_By = empProfile.getEmployeeId();

                cmbSubSystemType = request.getParameter("cmbSubSystemType");
                System.out.println("cmbSubSystemType.. " + cmbSubSystemType);

                if (cmbSubSystemType.equalsIgnoreCase("BR_S")) ///    **********************
                    cmbSubSystemType = "BR";
                if (cmbSubSystemType.equalsIgnoreCase("CR_S"))
                    cmbSubSystemType = "CR";
                txtReferNO_edit = request.getParameter("txtReferNO_edit");
                System.out.println("txtReferNO_edit  " + txtReferNO_edit);

                txtRemak_edit = request.getParameter("txtRemak_edit");
                System.out.println("txtRemak_edit  " + txtRemak_edit);

                txtRefdate = request.getParameter("txtReferDate_edit");

                if (!txtRefdate.equalsIgnoreCase("")) {
                    sd = request.getParameter("txtReferDate_edit").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtReferDate_edit = new Date(d.getTime());
                }

                radAuth_MC = request.getParameter("radAuth_MC");
                System.out.println("radAuth_MC.. " + radAuth_MC);

                cs1 =
 con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
                cs1.setInt(1, cmbAcc_UnitCode);
                cs1.setInt(2, txtCash_year);
                cs1.setInt(3, txtCash_Month_hid);
                cs1.setInt(4, txtVoucher_No);
                cs1.setInt(5, cmbOffice_code);
                cs1.setDate(6, txtCrea_date);
                cs1.setString(7, cmbSubSystemType);
                cs1.setString(8, txtReferNO_edit);
                cs1.setDate(9, txtReferDate_edit);
                cs1.setString(10, txtRemak_edit);
                cs1.setInt(11, txtAuth_By);
                cs1.setString(12, "allow_modify");
                cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
                cs1.setNull(13,java.sql.Types.NUMERIC);
                cs1.setString(14, update_user);
                cs1.setTimestamp(15, ts);
                cs1.setString(16, radAuth_MC);
                cs1.setString(16, radAuth_MC);
                cs1.execute(); // insertion into cross reference table
                int errcode = cs1.getInt(13);
                System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                System.out.println("cmbOffice_code " + cmbOffice_code);
                System.out.println("txtCrea_date " + txtCrea_date);
                System.out.println("txtCash_year " + txtCash_year);
                System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    con.rollback();
                    sendMessage(response, "Authorization has failed ", "ok");
                    xml = xml + "<flag>failure</flag>";
                    return;
                }
                con.commit();
                if (radAuth_MC.equalsIgnoreCase("M"))
                    sendMessage(response,
                                "The Modification has allowed to the voucher number '" +
                                txtVoucher_No + "' ", "ok");
                else
                    sendMessage(response,
                                "The Cancellation has allowed to the voucher number '" +
                                txtVoucher_No + "' ", "ok");
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
                sendMessage(response, "The Authorization has failed ", "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
            }

        }
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
        PreparedStatement ps = null;
        //String xml="";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
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

        String xml = "", cmbSubSystemType = "";
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;
        Calendar c;

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

        cmbSubSystemType = request.getParameter("cmbSubSystemType");
        System.out.println("cmbSubSystemType.. " + cmbSubSystemType);


        try {
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }


        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            xml = "<response><command>load_Voucher_No</command>";
            String QueryType = "";
            if (cmbSubSystemType.equalsIgnoreCase("CR"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='M' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("BR"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='M' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("CR_S")) // cash Reclassification
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='S' and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("BR_S")) // Bank Reclassification
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='S' and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("GJV"))
                QueryType =
                        "select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='GJV' and CB_REF_TYPE is null and JOURNAL_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("LJV")) // Once payment done for any of the transaction journal, CB_REF_TYPE is set to 'P'.. so after that you can't do any changes in that journal voucher
                QueryType =
                        "select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='LJV' and CB_REF_TYPE is null and JOURNAL_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("BPP"))
                QueryType =
                        "select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPP' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("BPF"))
                QueryType =
                        "select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPF' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("NP"))
                QueryType =
                        "select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='NP' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("FTH"))
                QueryType =
                        "select VOUCHER_NO from FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("FTO"))
                QueryType =
                        "select VOUCHER_NO from FAS_FUND_TRF_FROM_OFFICE where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("FRH"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_STATUS!='C' and REMITTANCE_STATUS!='Y' and  CHALLAN_NO=0 and CHALLAN_DATE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("FRO"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_STATUS!='C' and and REMITTANCE_STATUS!='Y' and  CHALLAN_NO=0 and CHALLAN_DATE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("IBT"))
                QueryType =
                        "select VOUCHER_NO from FAS_INTER_BANK_TRF_AT_HO where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("SC"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='SC' and RECEIPT_STATUS!='C' and CHALLAN_NO=0 and CHALLAN_DATE is null";


            QueryType =
                    QueryType + " order by 1"; // here order by specified with column number

            System.out.println("QueryType...." + QueryType);

            try {
                //ps=con.prepareStatement("select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPF' and PAYMENT_STATUS!='C'");
                /*
                              ps=con.prepareStatement("select i.VOUCHER_NO from FAS_PAYMENT_MASTER i,FAS_CROSS_REFERENCE c where " +
                             " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.PAYMENT_DATE=? and PAYMENT_TYPE='B' and i.PAYMENT_STATUS!='C'  and CREATED_BY_MODULE='BPF' " +
                             " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                             " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.VOUCHER_NO=c.VOUCHER_NO " +
                             " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE='BPF'");
                             */
                ps = con.prepareStatement(QueryType);
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

        } else if (strCommand.equalsIgnoreCase("load_Voucher_details")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int txtVoucher_No = 0;
            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }

            System.out.println("txtVoucher_No " + txtVoucher_No);

            xml = "<response><command>load_Voucher_details</command>";

            String QueryType = "";
            if (cmbSubSystemType.equalsIgnoreCase("CR"))
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and CHALLAN_NO=0 and CHALLAN_DATE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("BR"))
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and CHALLAN_NO=0 and CHALLAN_DATE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("CR_S")) // CASH Reclassification
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='S'";
            else if (cmbSubSystemType.equalsIgnoreCase("BR_S")) // BANK Reclassification
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='S'";
            else if (cmbSubSystemType.equalsIgnoreCase("GJV"))
                QueryType =
                        "select e.JOURNAL_TYPE_DESC as com_value,(select  nvl(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                        " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                        " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                        " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='GJV' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";
            else if (cmbSubSystemType.equalsIgnoreCase("LJV"))
                QueryType =
                        "select m.VOUCHER_NO,e.JOURNAL_TYPE_DESC as com_value,(select nvl(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                        " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                        " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                        " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='LJV' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";
            else if (cmbSubSystemType.equalsIgnoreCase("BPP"))
                QueryType =
                        "select PAID_TO as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=?  and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPP' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("BPF"))
                QueryType =
                        "select PAID_TO as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPF' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("NP"))
                QueryType =
                        "select PAID_TO as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='NP' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("FTH"))
                //QueryType="select VOUCHER_NO,,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'";
                QueryType =
                        "select (select o.OFFICE_NAME from FAS_FUND_TRF_FROM_HO_TRN t,COM_MST_OFFICES o  " +
                        " where t.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID and t.ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID " +
                        " and t.CASHBOOK_YEAR=a.CASHBOOK_YEAR and t.CASHBOOK_MONTH=a.CASHBOOK_MONTH and t.VOUCHER_NO=a.VOUCHER_NO " +
                        " and t.TRANSFER_TO_OFFICE_ID=o.OFFICE_ID and t.SL_NO=1) || ' and ..'  as com_value,trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as amt " +
                        " from FAS_FUND_TRF_FROM_HO_MASTER a where a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and " +
                        " a.DATE_OF_TRANSFER=? and a.VOUCHER_NO=? and a.TRANSFER_STATUS!='C' ";
            // In the above query i used SL_NO=1 to get only one office from transaction , else it retrieve more than one row,hence it say error
            else if (cmbSubSystemType.equalsIgnoreCase("FTO"))
                QueryType =
                        "select 'HEAD OFFICE' as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_FUND_TRF_FROM_OFFICE where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and VOUCHER_NO=? and TRANSFER_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("FRH"))
                //QueryType="select receipt_no as VOUCHER_NO,,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_STATUS!='C'";
                QueryType =
                        "select b.OFFICE_NAME as com_value,trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as amt from FAS_FUND_RECEIPT_BY_HO a,COM_MST_OFFICES b where a.ACCOUNTING_UNIT_ID=?  and a.ACCOUNTING_FOR_OFFICE_ID=? and a.RECEIPT_DATE=? and RECEIPT_NO=? and a.RECEIPT_STATUS!='C' " +
                        " and a.RECEIVED_FROM_OFFICE_ID=b.OFFICE_ID ";
            else if (cmbSubSystemType.equalsIgnoreCase("FRO"))
                QueryType =
                        "select 'HEAD OFFICE' as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("IBT"))
                QueryType =
                        "select 'HEAD OFFICE' as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_INTER_BANK_TRF_AT_HO where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and VOUCHER_NO=? and TRANSFER_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("SC"))
                QueryType =
                        "select  'cheque No: ' || t.CHEQUE_DD_NO || '  --  Date : ' || to_char(t.CHEQUE_DD_DATE,'DD/MM/YYYY') as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_RECEIPT_MASTER m,FAS_RECEIPT_TRANSACTION t" +
                        " where m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH and m.RECEIPT_NO=t.RECEIPT_NO" +
                        " and m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.RECEIPT_DATE=? and m.RECEIPT_NO=? and m.RECEIPT_TYPE='C' " +
                        " and m.CREATED_BY_MODULE='SC' and m.RECEIPT_STATUS!='C' and m.CHALLAN_NO=0 and m.CHALLAN_DATE is null";


            System.out.println("QueryType...." + QueryType);
            try {
                ps = con.prepareStatement(QueryType);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtVoucher_No);
                rs = ps.executeQuery();

                int count = 0;
                if (rs.next()) {

                    xml =
 xml + "<com_value>" + rs.getString("com_value") + "</com_value>";
                    //System.out.println("...rs.getDouble(amt)"+rs.getDouble("amt"));
                    // If you use getDouble it'll return in terms of 2.2323232232323E23.. Note 'E' in the number
                    // So i used getString to avoid 'E' power

                    xml = xml + "<amt>" + rs.getString("amt") + "</amt>";
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
