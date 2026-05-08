package Servlets.FAS.FAS1.YourSelfCheque.servlets;


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
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class YourSelfCheque_Create extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
    	  * Variables Declaration
    	  */
        String strCommand = "";
        Connection con = null;
        CallableStatement cs = null;
        PreparedStatement ps = null;


        /**
         * Session Checking
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
        }


        /**
         * Get Command Parameter
         */
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /**
         * Record Addition
         */
        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            Calendar c;

            int cmbAcc_UnitCode = 0;
            int cmbOffice_code = 0;
            int txtCash_Month_hid = 0;
            int txtCash_year = 0;

            Date txtCrea_date = null;
            String txtRemarks = "";
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            String txtDDObtained = null;
            long cmbBankAccNo = 0;
            Date txtChequeDate = null;
            int txtChequeNo = 0;
            int txtChequeAmt = 0;
            int txtRefNo = 0;
            Date txtRefDate = null;

            /** Get Accounting Unit ID */
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


            /** Get Accounting for Office ID */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);


            String sd[] = null;
            java.util.Date d = null;

            /**  Get Creation Date */
            try {
                sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);
            } catch (Exception e) {
                System.out.println("Error Getting Date -->" + e);
            }

            /** Calcualte Cashbook Year */
            try {
                txtCash_year = Integer.parseInt(sd[2]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_year " + txtCash_year);

            /** Calcualte Cashbook Month */
            try {
                txtCash_Month_hid = Integer.parseInt(sd[1]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

           
            /** Get Bank Account Number */
            try {
                cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
                    
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbBankAccNo " + cmbBankAccNo);

            /** Get DD Obtained or NOT  */
            try {
                txtDDObtained = (request.getParameter("txtDDObtained"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtDDObtained " + txtDDObtained);

            /**  Get Cheque Date  */
            try {
                String[] sd1 =
                    request.getParameter("txtChequeDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) -
                         1, Integer.parseInt(sd1[0]));
                java.util.Date d1 = c.getTime();
                txtChequeDate = new Date(d1.getTime());
                System.out.println("txtChequeDate " + txtChequeDate);
            } catch (Exception e) {
                System.out.println("Cheque Date -->" + e);
            }

            /** Get Cheque Number */
            try {
                txtChequeNo =
                        Integer.parseInt((request.getParameter("txtChequeNo")));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtChequeNo " + txtChequeNo);


            /** Get Cheque Amount  */
            try {
                txtChequeAmt =
                        Integer.parseInt((request.getParameter("txtChequeAmt")));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtChequeAmt " + txtChequeAmt);


            /** Get Ref No */
            try {
                txtRefNo =
                        Integer.parseInt((request.getParameter("txtRefNo")));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtRefNo " + txtRefNo);

            /**  Get Ref Date  */
            try {
                String[] sd2 = request.getParameter("txtRefDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) -
                         1, Integer.parseInt(sd2[0]));
                java.util.Date d2 = c.getTime();
                txtRefDate = new Date(d2.getTime());
                System.out.println("txtRefDate " + txtRefDate);
            } catch (Exception e) {
                System.out.println("Error to get Ref Date --> " + e);
            }

            /** Get Remarks */
            try {
                txtRemarks = (request.getParameter("txtRemarks"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtRemarks " + txtRemarks);

            String userid = (String)session.getAttribute("UserId");

            int voucher_no = 0;

            try {
                con.clearWarnings();
                con.setAutoCommit(false);


//                cs =
//  con.prepareCall("{call FAS_YOURSELF_CHEQUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                cs.setInt(1, cmbAcc_UnitCode);
//                cs.setInt(2, cmbOffice_code);
//                cs.setDate(3, txtCrea_date);
//                cs.setInt(4, txtCash_year);
//                cs.setInt(5, txtCash_Month_hid);
//                cs.setLong(6, cmbBankAccNo);
//                cs.setString(7, txtDDObtained);
//                cs.setDate(8, txtChequeDate);
//                cs.setInt(9, txtChequeNo);
//                cs.setInt(10, txtChequeAmt);
//                cs.setInt(11, txtRefNo);
//                cs.setDate(12, txtRefDate);
//                cs.setString(13, txtRemarks);
//                cs.setString(14, "insert");
//                cs.setString(15, userid);
//                cs.registerOutParameter(16, java.sql.Types.NUMERIC);
//                cs.registerOutParameter(17, java.sql.Types.NUMERIC);
//
//                System.out.println("before--->");
//                cs.execute();
//                System.out.println("after--->");
//
//                int errcode = cs.getInt(16);
//                voucher_no = cs.getInt(17);
                cs =
                		  con.prepareCall("call FAS_YOURSELF_CHEQUE(?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?,?,?,?,?::numeric,?::numeric)");
                		                cs.setInt(1, cmbAcc_UnitCode);
                		                cs.setInt(2, cmbOffice_code);
                		                cs.setDate(3, txtCrea_date);
                		                cs.setInt(4, txtCash_year);
                		                cs.setInt(5, txtCash_Month_hid);
                		                cs.setLong(6, cmbBankAccNo);
                		                cs.setString(7, txtDDObtained);
                		                cs.setDate(8, txtChequeDate);
                		                cs.setInt(9, txtChequeNo);
                		                cs.setInt(10, txtChequeAmt);
                		                cs.setInt(11, txtRefNo);
                		                cs.setDate(12, txtRefDate);
                		                cs.setString(13, txtRemarks);
                		                cs.setString(14, "update");
                		                cs.setString(15, userid);
                		                cs.registerOutParameter(16, java.sql.Types.NUMERIC);
                		                cs.setNull(16, java.sql.Types.NUMERIC);
                		                cs.setInt(17, voucher_no);
                		                cs.registerOutParameter(17, java.sql.Types.NUMERIC);

                		                System.out.println("before--->");
                		                cs.execute();
                		                System.out.println("after--->");

//                		                int errcode = cs.getInt(16);
//                		                voucher_no = cs.getInt(17);
                		                int errcode = cs.getBigDecimal(16).intValue();
                		                voucher_no = cs.getBigDecimal(17).intValue();
                System.out.println("SQLCODE:::" + errcode);

                if (errcode != 0) {
                    sendMessage(response, "The Voucher Creation Failed ",
                                "ok");
                } else {

                    System.out.println(request.getParameter("RecordCount"));

                    int RecordCount =
                        Integer.parseInt(request.getParameter("RecordCount"));

                    String txtDDNumber[] = new String[RecordCount];
                    String txtDDDate[] = new String[RecordCount];
                    String txtDDAmount[] = new String[RecordCount];
                    String txtDDFavourOf[] = new String[RecordCount];
                    String txtCommissionCharge[] = new String[RecordCount];

                    for (int k = 0; k < RecordCount; k++) {
                        System.out.println("inside" + k);
                        txtDDNumber[k] =
                                request.getParameter("txtDDNumber" + k);
                        txtDDDate[k] = request.getParameter("txtDDDate" + k);
                        txtDDAmount[k] =
                                request.getParameter("txtDDAmount" + k);
                        txtDDFavourOf[k] =
                                request.getParameter("txtDDFavourOf" + k);
                        txtCommissionCharge[k] =
                                request.getParameter("txtCommissionCharge" +
                                                     k);

                        System.out.println(txtDDNumber[k]);
                        System.out.println(txtDDDate[k]);
                        System.out.println(txtDDAmount[k]);
                        System.out.println(txtDDFavourOf[k]);
                        System.out.println(txtCommissionCharge[k]);

                    }


                    String sql =
                        " insert into fas_yourself_cheque_trans    " + " ( 									    " +
                        " 	accounting_unit_id,					    " +
                        "	accounting_for_office_id,				" +
                        "	cashbook_year,							" + "	cashbook_month,							" +
                        "	voucher_no,								" + "	slno,									" +
                        "	dd_number,								" + "	dd_date,								" +
                        "	dd_amount,								" + "	dd_in_favour_of,						" +
                        "	commission_charges,						" +
                        "	updated_by_user_id,						" + "	updated_date							" +
                        "	)										" + "values									" +
                        "( ?,?,?,?,?,?,?,?,?,?,?,?,? ) 			";

                    int txtDDNumber2 = 0;
                    Date txtDDDate2 = null;
                    int txtDDAmount2 = 0;
                    int SL_NO = 1;
                    String txtDDFavourOf2 = null;
                    String txtCommissionCharge2 = null;

                    ps = con.prepareStatement(sql);


                    for (int k = 0; k < RecordCount; k++) {
                        /** Convert Sting into interger values */

                        /** DD Number */
                        try {
                            System.out.println("DD Number ---> " +
                                               txtDDNumber[k]);
                            txtDDNumber2 = Integer.parseInt(txtDDNumber[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }


                        /** DD Date */
                        try {
                            if (!txtDDDate[k].equalsIgnoreCase("")) {
                                sd = txtDDDate[k].split("/");
                                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                                d = c.getTime();
                                txtDDDate2 = new Date(d.getTime());
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }


                        /** DD Amount */
                        try {
                            System.out.println("DD Amount ---> " +
                                               txtDDAmount[k]);
                            txtDDAmount2 = Integer.parseInt(txtDDAmount[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        txtDDFavourOf2 = txtDDFavourOf[k];
                        txtCommissionCharge2 = txtCommissionCharge[k];


                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setInt(2, cmbOffice_code);
                        ps.setInt(3, txtCash_year);
                        ps.setInt(4, txtCash_Month_hid);
                        ps.setInt(5, voucher_no);
                        ps.setInt(6, SL_NO);
                        ps.setInt(7, txtDDNumber2);
                        ps.setDate(8, txtDDDate2);
                        ps.setInt(9, txtDDAmount2);
                        ps.setString(10, txtDDFavourOf2);
                        ps.setString(11, txtCommissionCharge2);
                        ps.setString(12, userid);
                        ps.setTimestamp(13, ts);

                        SL_NO++;

                        ps.executeUpdate();

                        /** Reset Variables */
                        txtDDNumber2 = 0;
                        txtDDDate2 = null;
                        txtDDAmount2 = 0;
                        txtDDFavourOf2 = "";
                        txtCommissionCharge2 = "";

                    }
                    ps.close();
                    con.commit();
                    sendMessage(response,
                                "The Voucher Number '" + voucher_no + "' has been Created Successfully ",
                                "ok");
                }

            }

            catch (Exception e) {
                System.out.println("Error ---> " + e);
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("exception in rollback " + sqle);
                }
                sendMessage(response, "The Voucher Creation Failed ", "ok");

            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                }
            }

        }
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        /**
         *  Varialbes Declaration
         */
        Connection con = null;
        ResultSet rs2 = null;
        PreparedStatement ps2 = null;
        String xml = "";

        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);


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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
        }

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

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        /**
         *  Set Servlet Content Type
         */
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        int cmbAcc_UnitCode = 0,office_id=0,cmbBankAccNo=0;
        String Cmd=request.getParameter("command");

        System.out.println("************************************************************"+Cmd);



        try {
        
           
        
        if(Cmd.equals("LoadChequeNumber"))
        {
        
            xml = "";
            int month=0;
            int year=0;
            /** Get Cheque Number */
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                        
                        System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                        
                office_id= Integer.parseInt(request.getParameter("cmbOffice_code"));
                
                cmbBankAccNo= Integer.parseInt(request.getParameter("cmbBankAccNo"));
                
               String txtCrea_date[] = request.getParameter("txtCrea_date").split("/");
                
                 month =Integer.parseInt(txtCrea_date[1]);
                 year=Integer.parseInt(txtCrea_date[2]);
                
                System.out.println("office_id"+office_id);
                System.out.println("office_id"+txtCrea_date[2]);
                System.out.println("office_id"+txtCrea_date[1]);
                System.out.println("office_id"+month);
                System.out.println("office_id"+year);
                
            } catch (Exception e) {
                System.out.println(e);
            }

            /** SQL Query for Reterving Records from table */
            String sql =" Select CHEQUE_DD_NO from FAS_PAYMENT_TRANSACTION t where t.ACCOUNTING_UNIT_ID=? and t.ACCOUNTING_FOR_OFFICE_ID=? and t.CASHBOOK_YEAR=? and t.CASHBOOK_MONTH=? and  ACCOUNT_NO=? and t.cheque_dd_no  is not null and t.VOUCHER_NO in (select  VOUCHER_NO from FAS_PAYMENT_MASTER m where m. ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and  m.PAYMENT_STATUS='L' )";
                                         
            System.out.println("SQL ::: " + sql);
            ps2 = con.prepareStatement(sql);
            ps2.setInt(1, cmbAcc_UnitCode);
            ps2.setInt(2, office_id);
            ps2.setInt(3, year);
            ps2.setInt(4, month);
            ps2.setInt(5, cmbBankAccNo);
            ps2.setInt(6, cmbAcc_UnitCode);
            ps2.setInt(7, office_id);
            rs2 = ps2.executeQuery();
            int count1 = 0;

            xml = "<response><command>LoadChequeNumber</command>";

            /** Count How many Records are availabel  */
            while (rs2.next()) {
                xml =xml + "<acc_no1>" + rs2.getString("CHEQUE_DD_NO") + "</acc_no1>";
                System.out.println("while xml........."+xml);
                count1++;
            }

            if (count1 == 0) {
                xml = xml + "<flag>NoData</flag></response>";
                
            } else {
                xml = xml + "<flag>success</flag></response>";
                System.out.println("xml........."+xml);
            }
            System.out.println("xml-----> " + xml);
            out.println(xml);
        }
        
        else {
            /** Get Accouting UNit ID */
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println(e);
            }

            /** SQL Query for Reterving Records from table */
            String sql =
                "   select *                                                                     " + "  from                                                                                             " +
                "       (                                                                                                        " + "          select                                                                                   " +
                "                       bank_id,                                                                         " + "                  BRANCH_ID,                                                                       " +
                "                       bank_ac_no,                                                      " +
                "                       AC_OPERATIONAL_MODE_ID ||'-'||  bank_ac_no as acc_no  " +
                "               from                                                                                     " + "                  FAS_MST_BANK_BALANCE                                                     " +
                "               where                                                                                    " + "                  accounting_unit_id = ?                                           " +
                "       )X                                                                                                       " + "  left outer join                                                                          " +
                "       (                                                                                                        " +
                "               select bank_id as bid , bank_name from FAS_MST_BANKS            " +
                "       )Y                                                                                              " +
                "       on X.bank_id =Y.bid                                                             ";
            System.out.println("SQL ::: " + sql);
            ps2 = con.prepareStatement(sql);
            ps2.setInt(1, cmbAcc_UnitCode);
            rs2 = ps2.executeQuery();
            int count = 0;

            xml = "<response><command>LoadBankAccountNumber</command>";

            /** Count How many Records are availabel  */
            while (rs2.next()) {
                xml =
            xml + "<acc_no>" + rs2.getString("bank_ac_no") + "</acc_no>";
                xml =
            xml + "<acc_desc>" + rs2.getString("acc_no") + "-" + rs2.getString("bank_name") +
            "</acc_desc>";

                count++;
            }

            if (count == 0) {
                xml = xml + "<flag>NoData</flag></response>";
            } else {
                xml = xml + "<flag>success</flag></response>";
            }
            System.out.println("xml-----> " + xml);
            out.println(xml);
        }
        
        }catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
            xml = xml + "<flag>failure</flag></response>";
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
        }
    }
}

