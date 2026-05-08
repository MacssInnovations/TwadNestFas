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


public class YourSelfCheque_Cancel extends HttpServlet {
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
            int cmbBankAccNo = 0;
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


            /**
             * Get Creation Date
             */

            String sd[] = null;
            java.util.Date d = null;

            try {
                sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);
            } catch (Exception e) {
                System.out.println(e);
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
                cmbBankAccNo =
                        Integer.parseInt((request.getParameter("cmbBankAccNo")));
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
                System.out.println(e);
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
                System.out.println(e);
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

            /** Get Voucher No */
            try {
                voucher_no =
                        Integer.parseInt((request.getParameter("txtVoucher_No")));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("voucher_no " + voucher_no);
              
                PreparedStatement ps2 = null;
            
            try {
            
                String sql ="update FAS_YOURSELF_CHEQUE_MST set VOUCHER_STATUS =? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?";
                                             
                System.out.println("SQL ::: " + sql);
                ps2 = con.prepareStatement(sql);
                ps2.setString(1, "C");
                ps2.setInt(2, cmbAcc_UnitCode);
                ps2.setInt(3, cmbOffice_code);
                ps2.setInt(4, txtCash_year);
                ps2.setInt(5, txtCash_Month_hid);
              
                ps2.setInt(6, voucher_no);
              int j= ps2.executeUpdate();
            
             if(j>0) {
                 sendMessage(response, "The Voucher Successfully Updated ", "ok");
             }
            
            
            
            
              
            }

            catch (Exception e) {
                System.out.println("Error ---> " + e);
                    sendMessage(response, "The Voucher Updation Failed ", "ok");
               
                }
            

             
            }

        }
    


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("I am in Servler Get Method ----------");

        /**
         *  Varialbes Declaration
         */
        Connection con = null;

        ResultSet rs = null;
        ResultSet rs2 = null;

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;

        String xml = "";
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;

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
        PrintWriter out = response.getWriter();

        String Command = null;
        try {
            Command = request.getParameter("Command");
        } catch (Exception e) {
            System.out.println(e);
        }

        /** Get Accounting UNit ID */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            System.out.println(e);
        }

        /** Get Accounting for Office ID */
        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);

        /* Bank Account Number Loading */
        if (Command.equalsIgnoreCase("LoadBankAccounts")) {

            try {


                /** SQL Query for Retrieving Records from table */
                String sql =
                    "   select *                							 " + "	from 												 " +
                    "	(													 " + "		select											 " +
                    "			bank_id,									 " + "			BRANCH_ID,									 " +
                    "			bank_ac_no, 					     		 " +
                    "			AC_OPERATIONAL_MODE_ID ||'-'||  bank_ac_no as acc_no  " +
                    "		from											 " + "			fas_mst_bank_balance							 " +
                    "		where											 " +
                    "			accounting_unit_id = ?   					 " +
                    "	)X													 " + "	left outer join										 " +
                    "	(													 " +
                    "		select bank_id as bid , bank_name from fas_bank_list		" +
                    "	)Y						    					 	" +
                    "	on X.bank_id =Y.bid 								";

                ps2 = con.prepareStatement(sql);
                ps2.setInt(1, cmbAcc_UnitCode);
                rs2 = ps2.executeQuery();
                int count = 0;

                xml = "<response><command>LoadBankAccountNumber</command>";

                /** Count How many Records are available  */
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
            } catch (Exception e) {
                System.out.println("Exception in assigning..." + e);
                xml = xml + "<flag>failure</flag></response>";
            }
        }

        /* Load Voucher Number */
        else if (Command.equalsIgnoreCase("load_Voucher_No")) {
            Calendar c;
            xml = "<response><command>load_Voucher_No</command>";
            Date txtCrea_date = null;

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            try {
                ps =
  con.prepareStatement("									      " + " SELECT i.voucher_no 				  						  " +
                       " FROM 														  " +
                       "    fas_yourself_cheque_mst i,								  " +
                       "    fas_cross_reference c 									  " +
                       " WHERE  													  " +
                       "     i.accounting_unit_id = ?								  " +
                       " AND i.accounting_for_office_id = ?						  " +
                       " AND i.voucher_date = ?									  " +
                       " AND i.voucher_status != 'C'								  " +
                       " AND i.accounting_unit_id = c.accounting_unit_id			  " +
                       " AND i.accounting_for_office_id = c.accounting_for_office_id " +
                       " AND i.cashbook_year = c.cashbook_year						  " +
                       " AND i.cashbook_month = c.cashbook_month					  " +
                       " AND i.voucher_no = c.voucher_no							  " +
                       " AND c.change_no = 0										  " +
                       " AND c.authorized_to = 'C'									  " +
                       " AND c.doc_type = 'YC' 									  ");


                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                rs = ps.executeQuery();

                int count = 0;
                while (rs.next()) {
                    xml =
 xml + "<Voc_No>" + rs.getInt("voucher_no") + "</Voc_No>";
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
                xml = xml + "<flag>failure</flag>";
                System.out.println(e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }
        /* Load Voucher Details */
        else if (Command.equalsIgnoreCase("load_Voucher_Details")) {
            Calendar c;
            xml = "<response><command>load_Voucher_Details</command>";

            int txtVoucher_No = 0;
            int CashbookMonth = 0;
            int CashbookYear = 0;
            Date txtCrea_date = null;


            /* Get Receipt Number */
            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtReceipt_No " + txtVoucher_No);

            /* Get Creation Date */
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            /* SQL for fetching Data from Master Table */
            try {
                ps =
  con.prepareStatement("SELECT     				               						 " +
                       "      cashbook_year,  									     " +
                       "		 cashbook_month,  										 " +
                       "		 to_char(VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE,  	 " +
                       " 	 BANK_ACCOUNT_NO,  										 " +
                       "		 WHEATHER_DD_OBTAINED,   								 " +
                       " 	 to_char(CHEQUE_DATE,'DD/MM/YYYY') AS CHEQUE_DATE,   	 " +
                       "		 CHEQUE_NUMBER,   										 " +
                       "		 CHEQUE_AMOUNT,   										 " +
                       "		 REF_NO,   												 " +
                       "		 to_char(ref_date,'DD/MM/YYYY') AS ref_date,   			 " +
                       "		 REMARKS   												 " +
                       "FROM fas_yourself_cheque_mst   								 " +
                       "WHERE accounting_unit_id = ?   								 " +
                       "	AND accounting_for_office_id = ?   							 " +
                       " AND voucher_status = 'L'   									 " +
                       " AND voucher_date = ?  										 " +
                       "  AND voucher_no = ?  										 ");

                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtVoucher_No);

                rs = ps.executeQuery();

                if (rs.next()) {
                    CashbookMonth = rs.getInt("cashbook_month");
                    CashbookYear = rs.getInt("cashbook_year");
                    xml =
 xml + "<BANK_ACCOUNT_NO>" + rs.getString("BANK_ACCOUNT_NO") +
   "</BANK_ACCOUNT_NO>";
                    xml =
 xml + "<WHEATHER_DD_OBTAINED>" + rs.getString("WHEATHER_DD_OBTAINED") +
   "</WHEATHER_DD_OBTAINED>";
                    xml =
 xml + "<CHEQUE_DATE>" + rs.getString("CHEQUE_DATE") + "</CHEQUE_DATE>";
                    xml =
 xml + "<CHEQUE_NUMBER>" + rs.getInt("CHEQUE_NUMBER") + "</CHEQUE_NUMBER>";
                    xml =
 xml + "<CHEQUE_AMOUNT>" + rs.getString("CHEQUE_AMOUNT") + "</CHEQUE_AMOUNT>";
                    xml =
 xml + "<REF_NO>" + rs.getString("REF_NO") + "</REF_NO>";
                    xml =
 xml + "<ref_date>" + rs.getString("ref_date") + "</ref_date>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                }


                String sql =
                    "select 						 " + "   DD_NUMBER,					 " + "   to_char(DD_DATE,'DD/MM/YYYY') AS DD_DATE,		 " +
                    "   DD_AMOUNT,					 " + "   DD_IN_FAVOUR_OF,			 " +
                    "   COMMISSION_CHARGES			 " + "from 							 " +
                    "  fas_yourself_cheque_trans	 " + "where 							 " +
                    "     accounting_unit_id = ?	 " +
                    " AND accounting_for_office_id=? " +
                    " AND cashbook_year = ?			 " +
                    " AND cashbook_month = ?		 " + " AND voucher_no = ? 			 " +
                    "order by slno 					 ";

                ps2 = con.prepareStatement(sql);
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                ps2.setInt(3, CashbookYear);
                ps2.setInt(4, CashbookMonth);
                ps2.setInt(5, txtVoucher_No);

                rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    xml = xml + "<trans_pair>";
                    xml =
 xml + "<DD_NUMBER>" + rs2.getString("DD_NUMBER") + "</DD_NUMBER>";
                    xml =
 xml + "<DD_DATE>" + rs2.getString("DD_DATE") + "</DD_DATE>";
                    xml =
 xml + "<DD_AMOUNT>" + rs2.getString("DD_AMOUNT") + "</DD_AMOUNT>";
                    xml =
 xml + "<DD_IN_FAVOUR_OF>" + rs2.getString("DD_IN_FAVOUR_OF") +
   "</DD_IN_FAVOUR_OF>";
                    xml =
 xml + "<COMMISSION_CHARGES>" + rs2.getString("COMMISSION_CHARGES") +
   "</COMMISSION_CHARGES>";
                    xml = xml + "</trans_pair>";
                }
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println(e);
                xml =
 "<response><command>load_Receipt_Details</command><flag>failure</flag>";
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
        }
    }
}

