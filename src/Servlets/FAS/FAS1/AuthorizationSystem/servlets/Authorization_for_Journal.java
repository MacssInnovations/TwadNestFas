package Servlets.FAS.FAS1.AuthorizationSystem.servlets;



//import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook_for_Journal;

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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Authorization_for_Journal extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


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
        *  Variables Declaration
        */

        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs1 = null;
        PreparedStatement ps = null;
        String xml = "";
        String strCommand = "";


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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

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
         * If Command is ADD
         */

        if (strCommand.equalsIgnoreCase("Add")) {

            /** Set Content Type */
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";

            /** Varialbles Declaration */
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            Date txtCrea_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            /** Get Accounting Unit Id */
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** Get Accounting Office ID */
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

            /** Get Voucher Number */
            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);


            /** Find Cashbook Month and Year */
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


            try {
                con.clearWarnings();
                con.setAutoCommit(false);

                String txtReferNO_edit = "", txtRemak_edit =
                    "", cmbSubSystemType = "", txtRefdate = "";
                Date txtReferDate_edit = null;
                String radAuth_MC = "";
                int txtAuth_By = 0;


                UserProfile empProfile =
                    (UserProfile)session.getAttribute("UserProfile");
                txtAuth_By = empProfile.getEmployeeId();
System.out.println("***************************************************");
                cmbSubSystemType = request.getParameter("cmbSubSystemType");
                System.out.println("cmbSubSystemType.. " + cmbSubSystemType);

                if (cmbSubSystemType.equalsIgnoreCase("BR_S"))
                    cmbSubSystemType = "BR";
                if (cmbSubSystemType.equalsIgnoreCase("CR_S"))
                    cmbSubSystemType = "CR";
                
                if (cmbSubSystemType.equalsIgnoreCase("TDAOS"))
                {  cmbSubSystemType = "TDAO";}
                if (cmbSubSystemType.equalsIgnoreCase("TCAOS"))
                {  cmbSubSystemType = "TCAO";}
                if (cmbSubSystemType.equalsIgnoreCase("TDAAS"))
                {  cmbSubSystemType = "TDAA";}
                if (cmbSubSystemType.equalsIgnoreCase("TCAAS"))
                {  cmbSubSystemType = "TCAA";}
                if (cmbSubSystemType.equalsIgnoreCase("TCAABS"))
                {  cmbSubSystemType = "TCAA";}
                if (cmbSubSystemType.equalsIgnoreCase("TDAABS"))
                {  cmbSubSystemType = "TDAA";}
              
                if(cmbSubSystemType.equalsIgnoreCase("TDCP")) 
                {
                    cmbSubSystemType="GJV";
                }
                System.out.println("cmbSubSystemType:********************"+cmbSubSystemType);
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
                //cs1.setString(16,radAuth_MC);
                cs1.execute(); // insertion into cross reference table
                //int errcode = cs1.getInt(13);
                int errcode = cs1.getBigDecimal(13).intValue();
                System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                System.out.println("cmbOffice_code " + cmbOffice_code);
                System.out.println("txtCrea_date " + txtCrea_date);
                System.out.println("txtCash_year " + txtCash_year);
                System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
                System.out.println("txtVoucher_No" + txtVoucher_No);
                System.out.println("cmbSubSystemType" + cmbSubSystemType);
                System.out.println("txtReferNO_edit" + txtReferNO_edit);
                System.out.println("txtReferDate_edit" + txtReferDate_edit);
                System.out.println("txtRemak_edit" + txtRemak_edit);
                System.out.println("txtAuth_By" + txtAuth_By);
                System.out.println("radAuth_MC" + radAuth_MC);

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

    /**
     * GET Method
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public

    void doGet(HttpServletRequest request,
               HttpServletResponse response) throws ServletException,
                                                    IOException {

        /** Session Checking  */
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

        /** Database Connection  */
        Connection con = null;
        ResultSet rs = null,rs2=null,rs4=null;
        PreparedStatement ps = null,ps1=null,ps3=null;
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

        /** Receiving Command  */
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        /** Variables Declaration  */
        String xml = "", cmbSubSystemType = "";
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;
        Calendar c;int count = 0;

        /** Get Accounting Unit ID  */
      

        /** Get Date */
    /*  
*/

        /** Load Voucher Number for particular Date  */
        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** Get Accounting Office ID  */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            /** Get Sub System Type */
            cmbSubSystemType = request.getParameter("cmbSubSystemType");
            System.out.println("cmbSubSystemType.. " + cmbSubSystemType);
            
            System.out.println("Date==>"+request.getParameter("txtCrea_date"));
            
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
            System.out.println("Date=1=>"+txtCrea_date);
            
            xml = "<response><command>load_Voucher_No</command>";
            String QueryType = "";

            
            if (cmbSubSystemType.equalsIgnoreCase("GJV"))
            { 
                    //   QueryType =  "select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='GJV' and JOURNAL_STATUS!='C' and MODE_OF_CREATION ='M' ";
            
            		/* QueryType =   "SELECT a.voucher_no " +
            "FROM fas_journal_master a , " +
            "  fas_journal_transaction b " +
            "WHERE a.accounting_unit_id    =b.accounting_unit_id " +
            "AND a.accounting_for_office_id=b.accounting_for_office_id " +
            "AND a.cashbook_year           =b.cashbook_year " +
            "AND a.cashbook_month          =b.cashbook_month " +
            "AND a.voucher_no              =b.voucher_no " +
            "AND a.accounting_unit_id      =? " +
            "AND a.accounting_for_office_id=? " +
            "AND a.CREATED_BY_MODULE       ='GJV' " +
            "AND a.MODE_OF_CREATION        ='M' " +
            "AND a.JOURNAL_STATUS!         ='C' " +
            "AND a.VOUCHER_DATE            =? " +
            "AND a.voucher_no NOT         IN " +
            "  (SELECT aa.voucher_no " +
            "  FROM fas_journal_transaction aa " +
            "  WHERE aa.accounting_unit_id      = " +cmbAcc_UnitCode+
            "  AND aa.accounting_for_office_id= " +cmbOffice_code+
            "  AND aa.cashbook_year           =extract(YEAR FROM a.VOUCHER_DATE) " +
            "  AND aa.cashbook_month          =extract(MONTH FROM a.VOUCHER_DATE) " +
            "  AND aa.CHEQUE_OR_DD           IS NOT NULL " +
            "  AND aa.CHEQUE_DD_NO            > 0 " +
            "  AND aa.CHEQUE_DD_DATE         IS NOT NULL " +
            "  AND CB_REF_NO                  > 0 " +
            "  AND CB_REF_DATE               IS NOT NULL " +
            "  ) " +
            "GROUP BY a.voucher_no " ;*/
           // "ORDER BY a.voucher_no";
            		 
            		 //siva changed on 2016-03-08	 allow fo athorization
            		 
            		 
            		 QueryType =   "SELECT a.voucher_no " +
            		            "FROM fas_journal_master a , " +
            		            "  fas_journal_transaction b " +
            		            "WHERE a.accounting_unit_id    =b.accounting_unit_id " +
            		            "AND a.accounting_for_office_id=b.accounting_for_office_id " +
            		            "AND a.cashbook_year           =b.cashbook_year " +
            		            "AND a.cashbook_month          =b.cashbook_month " +
            		            "AND a.voucher_no              =b.voucher_no " +
            		            "AND a.accounting_unit_id      =? " +
            		            "AND a.accounting_for_office_id=? " +
            		            "AND trim(a.CREATED_BY_MODULE)     ='GJV' " +
            		            "AND trim(a.MODE_OF_CREATION )      ='M' " +
            		            "AND a.JOURNAL_STATUS!='C' " +
            		            "AND a.VOUCHER_DATE            =? " +
            		            "AND a.voucher_no NOT         IN " +
            		            "  (SELECT aa.voucher_no " +
            		            "  FROM fas_journal_transaction aa " +
            		            "  WHERE aa.accounting_unit_id      = " +cmbAcc_UnitCode+
            		            "  AND aa.accounting_for_office_id= " +cmbOffice_code+
            		            "  AND aa.cashbook_year           =extract(YEAR FROM a.VOUCHER_DATE) " +
            		            "  AND aa.cashbook_month          =extract(MONTH FROM a.VOUCHER_DATE) " +
            		            "  AND aa.CHEQUE_OR_DD           IS NULL " +
            		            "  AND trim(aa.CHEQUE_DD_NO)::numeric            >=0 " +
            		            "  AND aa.CHEQUE_DD_DATE         IS  NULL " +
            		            "  AND CB_REF_NO                  >=0 " +
            		            "  AND CB_REF_DATE               IS  NULL " +
            		            "  ) " +
            		            "GROUP BY a.voucher_no " ;
            		 
            		 
            		 
            }else if (cmbSubSystemType.equalsIgnoreCase("LJV")){ // Once payment done for any of the transaction journal, CB_REF_TYPE is set to 'P'.. so after that you can't do any changes in that journal voucher
                //  QueryType="select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='LJV' and CB_REF_TYPE is null and JOURNAL_STATUS!='C'";
               /*
                * Joan added on 13 August 15 for not allowed Pending bill generated Jrnl
                *  
                *  QueryType =
                        "select                         \n" + "       a.voucher_no                      \n" +
                        "from                                     \n" +
                        "       fas_journal_master a ,            \n" +
                        "       fas_journal_transaction b         \n" +
                        "where                                    \n" +
                        "      a.accounting_unit_id=b.accounting_unit_id and             \n" +
                        "      a.accounting_for_office_id=b.accounting_for_office_id and \n" +
                        "      a.cashbook_year=b.cashbook_year and               \n" +
                        "      a.cashbook_month=b.cashbook_month and             \n" +
                        "      a.voucher_no=b.voucher_no and                     \n" +
                        "                                                        \n" +
                        "      a.accounting_unit_id=? and                        \n" +
                        "      a.accounting_for_office_id=? and                  \n" +
                        "      a.CREATED_BY_MODULE='LJV' and                     \n" +
                        "      a.JOURNAL_STATUS!='C' and                         \n" +
                        "      a.VOUCHER_DATE=?  and                             \n" +
                        "      b.CB_REF_NO=0  group by a.voucher_no    ";*/
            	
            	
            	  
            	  /*
            	   *  Siva added on 26-02-2016 to allow the pending bills generated Jrnl
            	   *  
            	   * QueryType="SELECT a.voucher_no " +
            			" FROM fas_journal_master a , " +
            			"  fas_journal_transaction b " +
            			" WHERE a.accounting_unit_id    =b.accounting_unit_id " +
            			" AND a.accounting_for_office_id=b.accounting_for_office_id " +
            			" AND a.cashbook_year           =b.cashbook_year " +
            			" AND a.cashbook_month          =b.cashbook_month " +
            			" AND a.voucher_no              =b.voucher_no " +
            			" AND a.accounting_unit_id      =? " +
            			" AND a.accounting_for_office_id=? " +
            			" AND a.CREATED_BY_MODULE       ='LJV' " +
            			" AND a.JOURNAL_STATUS!         ='C' " +
            			" AND a.VOUCHER_DATE            =? " +
            			" AND a.voucher_no NOT         IN " +
            			"  (SELECT aa.voucher_no " +
            			"  FROM fas_journal_transaction aa " +
            			"  WHERE aa.accounting_unit_id    = " +cmbAcc_UnitCode+
            			"  AND aa.accounting_for_office_id= " +cmbOffice_code+
            			"  AND aa.cashbook_year           =extract(YEAR FROM a.VOUCHER_DATE) " +
            			"  AND aa.cashbook_month          =extract(MONTH FROM a.VOUCHER_DATE) " +
            			"  AND aa.CHEQUE_OR_DD           IS NOT NULL " +
            			"  AND aa.CHEQUE_DD_NO            > 0 " +
            			"  AND aa.CHEQUE_DD_DATE         IS NOT NULL " +
            			"  AND CB_REF_NO                  > 0 " +
            			"  AND CB_REF_DATE               IS NOT NULL " +
            			"  ) " +
            			" GROUP BY a.voucher_no " ;*/
            
            
            
         
              
            QueryType="SELECT a.voucher_no " +
        			" FROM fas_journal_master a , " +
        			"  fas_journal_transaction b " +
        			" WHERE a.accounting_unit_id    =b.accounting_unit_id " +
        			" AND a.accounting_for_office_id=b.accounting_for_office_id " +
        			" AND a.cashbook_year           =b.cashbook_year " +
        			" AND a.cashbook_month          =b.cashbook_month " +
        			" AND a.voucher_no              =b.voucher_no " +
        			" AND a.accounting_unit_id      =? " +
        			" AND a.accounting_for_office_id=? " +
        			" AND trim(a.CREATED_BY_MODULE)      ='LJV' " +
        			" AND a.JOURNAL_STATUS!='C' " +
        			" AND a.VOUCHER_DATE            =? " +
        			" AND a.voucher_no NOT         IN " +
        			"  (SELECT aa.voucher_no " +
        			"  FROM fas_journal_transaction aa " +
        			"  WHERE aa.accounting_unit_id    = " +cmbAcc_UnitCode+
        			"  AND aa.accounting_for_office_id= " +cmbOffice_code+
        			"  AND aa.cashbook_year           =extract(YEAR FROM a.VOUCHER_DATE) " +
        			"  AND aa.cashbook_month          =extract(MONTH FROM a.VOUCHER_DATE) " +
        			"  AND aa.CHEQUE_OR_DD           IS  NULL " +
        			"  AND trim(aa.CHEQUE_DD_NO)::numeric >= 0 " +
        			"  AND aa.CHEQUE_DD_DATE         IS  NULL " +
        			"  AND CB_REF_NO                  >= 0 " +
        			"  AND CB_REF_DATE               IS  NULL " +
        			"  ) " +
        			" GROUP BY a.voucher_no " ;
            
            
           
            
            
            }

            //commanded on 25-07-2018 issue 28926
            
//            else if (cmbSubSystemType.equalsIgnoreCase("SJV"))
//                QueryType =
//                        "select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='SJV' and CB_REF_TYPE is null and JOURNAL_STATUS!='C'";
            
            else if (cmbSubSystemType.equalsIgnoreCase("SJV"))
            {
            QueryType="SELECT a.voucher_no " +
        			" FROM fas_journal_master a , " +
        			"  fas_journal_transaction b " +
        			" WHERE a.accounting_unit_id    =b.accounting_unit_id " +
        			" AND a.accounting_for_office_id=b.accounting_for_office_id " +
        			" AND a.cashbook_year           =b.cashbook_year " +
        			" AND a.cashbook_month          =b.cashbook_month " +
        			" AND a.voucher_no              =b.voucher_no " +
        			" AND a.accounting_unit_id      =? " +
        			" AND a.accounting_for_office_id=? " +
        			" AND trim(a.CREATED_BY_MODULE)      ='SJV' " +
        			" AND a.JOURNAL_STATUS!='C' " +
        			" AND a.VOUCHER_DATE            =? " +
        			" AND a.voucher_no NOT         IN " +
        			"  (SELECT aa.voucher_no " +
        			"  FROM fas_journal_transaction aa " +
        			"  WHERE aa.accounting_unit_id    = " +cmbAcc_UnitCode+
        			"  AND aa.accounting_for_office_id= " +cmbOffice_code+
        			"  AND aa.cashbook_year           =extract(YEAR FROM a.VOUCHER_DATE) " +
        			"  AND aa.cashbook_month          =extract(MONTH FROM a.VOUCHER_DATE) " +
        			"  AND aa.CHEQUE_OR_DD           IS  NULL " +
        			"  AND trim(aa.CHEQUE_DD_NO)::numeric          >= 0 " +
        			"  AND aa.CHEQUE_DD_DATE         IS  NULL " +
        			"  AND CB_REF_NO                  >= 0 " +
        			"  AND CB_REF_DATE               IS  NULL " +
        			"  ) " +
        			" GROUP BY a.voucher_no " ;
            }
            
            else if(cmbSubSystemType.equalsIgnoreCase("IJV"))
                              QueryType="select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='GJV' and MODE_OF_CREATION='I' and CB_REF_TYPE is null and JOURNAL_STATUS!='C'";   
                          else if(cmbSubSystemType.equalsIgnoreCase("TJV"))
                              QueryType="select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='GJV' and MODE_OF_CREATION='T' and CB_REF_TYPE is null and JOURNAL_STATUS!='C'";
           //added on 22Mar2011
            else if(cmbSubSystemType.equalsIgnoreCase("RJV")){
                              QueryType="select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='GJV' and JOURNAL_STATUS!='C' and MODE_OF_CREATION in('M','A') and JOURNAL_TYPE_CODE in(75,82) ";
                      }
            
  //----------------------------------------------------------          
            
            
            else if(cmbSubSystemType.equalsIgnoreCase("TDCP")){
                QueryType="select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='GJV' and CB_REF_TYPE is not null and JOURNAL_STATUS!='C' and MODE_OF_CREATION ='M'  ";
        }
          
          //added on 23apr2012 for supplement TDA
            else if(cmbSubSystemType.equalsIgnoreCase("TDAOS")||cmbSubSystemType.equalsIgnoreCase("TCAOS"))
            {
            if(cmbSubSystemType.equalsIgnoreCase("TDAOS")){
//                QueryType="select * from\n" + 
//                "(\n" + 
//                "    select \n" + 
//                "        VOUCHER_NO,\n" + 
//                "        VOUCHER_DATE,\n" + 
//                "        TRF_ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
//                "        ACCEPTING_SLNO,\n" + 
//                "        ACCEPTING_DATE\n" + 
//                "    from\n" + 
//                "        FAS_TDA_TCA_RAISED_MST \n" + 
//                "    where\n" + 
//                "        ACCOUNTING_UNIT_ID=? and\n" + 
//                "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
//                "        VOUCHER_DATE=? AND\n" + 
//                "       ( TDA_OR_TCA=? or TDA_OR_TCA='TDACB') and\n" + 
//                "        STATUS='L' and \n" +
//               //DHANA ADDED FOR TDAO 4TH_MAR_2011
//             //   "        ADVICE_TYPE='J' and \n" +
//                "        (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='Y')" + 
//                ")mst_originat\n" + 
//                "where\n" + 
//                "ACCEPTING_SLNO not in \n" + 
//                "(\n" + 
//                "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and to_date(VOUCHER_DATE,'dd-mm-yyyy')=to_date(mst_originat.ACCEPTING_DATE,'dd-mm-yyyy') and STATUS='L'\n" + 
//                ")";
            	QueryType="select * from\n" + 
                        "(\n" + 
                        "    select \n" + 
                        "        VOUCHER_NO,\n" + 
                        "        VOUCHER_DATE,\n" + 
                        "        TRF_ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
                        "        ACCEPTING_SLNO,\n" + 
                        "        ACCEPTING_DATE\n" + 
                        "    from\n" + 
                        "        FAS_TDA_TCA_RAISED_MST \n" + 
                        "    where\n" + 
                        "        ACCOUNTING_UNIT_ID=? and\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                        "        VOUCHER_DATE=? AND\n" + 
                        "       ( TDA_OR_TCA=? or TDA_OR_TCA='TDACB') and\n" + 
                        "        STATUS='L' and \n" +
                       //DHANA ADDED FOR TDAO 4TH_MAR_2011
                     //   "        ADVICE_TYPE='J' and \n" +
                        "        (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='Y') and \n" + 
                        " SUPPLEMENT_NO IS NOT NULL  and SUPPLEMENT_NO!=0"+
                        ")mst_originat\n" + 
                        "where\n" + 
                        "ACCEPTING_SLNO not in \n" + 
                        "(\n" + 
                        "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and to_date(VOUCHER_DATE,'dd-mm-yyyy')=to_date(mst_originat.ACCEPTING_DATE,'dd-mm-yyyy') and STATUS='L'\n" + 
                        ")";
            }
          
            else if(cmbSubSystemType.equalsIgnoreCase("TCAOS")){
//                QueryType="select * from\n" + 
//                "(\n" + 
//                "    select \n" + 
//                "        VOUCHER_NO,\n" + 
//                "        VOUCHER_DATE,\n" + 
//                "        TRF_ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
//                "        ACCEPTING_SLNO,\n" + 
//                "        ACCEPTING_DATE\n" + 
//                "    from\n" + 
//                "        FAS_TDA_TCA_RAISED_MST \n" + 
//                "    where\n" + 
//                "        ACCOUNTING_UNIT_ID=? and\n" + 
//                "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
//                "        VOUCHER_DATE=? AND\n" + 
//                "       ( TDA_OR_TCA=? or TDA_OR_TCA='TCACB') and\n" + 
//                "        STATUS='L' and \n" +
//               //DHANA ADDED FOR TDAO 4TH_MAR_2011
//             //   "        ADVICE_TYPE='J' and \n" +
//                "        (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='Y')" + 
//                ")mst_originat\n" + 
//                "where\n" + 
//                "ACCEPTING_SLNO not in \n" + 
//                "(\n" + 
//                "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and to_date(VOUCHER_DATE,'dd-mm-yyyy')=to_date(mst_originat.ACCEPTING_DATE,'dd-mm-yyyy') and STATUS='L'\n" + 
//                ")";
            	QueryType="select * from\n" + 
                        "(\n" + 
                        "    select \n" + 
                        "        VOUCHER_NO,\n" + 
                        "        VOUCHER_DATE,\n" + 
                        "        TRF_ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
                        "        ACCEPTING_SLNO,\n" + 
                        "        ACCEPTING_DATE\n" + 
                        "    from\n" + 
                        "        FAS_TDA_TCA_RAISED_MST \n" + 
                        "    where\n" + 
                        "        ACCOUNTING_UNIT_ID=? and\n" + 
                        "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                        "        VOUCHER_DATE=? AND\n" + 
                        "       ( TDA_OR_TCA=? or TDA_OR_TCA='TCACB') and\n" + 
                        "        STATUS='L' and \n" +
                       //DHANA ADDED FOR TDAO 4TH_MAR_2011
                     //   "        ADVICE_TYPE='J' and \n" +
                        "        (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='Y') and\n" + 
                        " SUPPLEMENT_NO IS NOT NULL and SUPPLEMENT_NO!=0"+

                        ")mst_originat\n" + 
                        "where\n" + 
                        "ACCEPTING_SLNO not in \n" + 
                        "(\n" + 
                        "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and to_date(VOUCHER_DATE,'dd-mm-yyyy')=to_date(mst_originat.ACCEPTING_DATE,'dd-mm-yyyy') and STATUS='L'\n" + 
                        ")";
            	
            }
            }
            else if(cmbSubSystemType.equalsIgnoreCase("TDAAS")||cmbSubSystemType.equalsIgnoreCase("TCAAS"))
            {
//                QueryType="select * from\n" + 
//                "(\n" + 
//                "    select   \n" +
//                "      a.VOUCHER_NO," + 
//                "      b.ACCOUNTING_UNIT_ID,\n" + 
//                "      b.ACCOUNTING_FOR_OFFICE_ID,      \n" + 
//                "      a.RESPONDING_JVR_NO, \n" + 
//                "      a.RESPONDING_JVR_DATE \n" + 
//                "    from   \n" + 
//                "      FAS_TDA_TCA_RAISED_MST a, FAS_TDA_TCA_RAISED_MST b   \n" + 
//                "    where  \n" + 
//                "      a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and\n" + 
//                "      a.VOUCHER_NO=b.ACCEPTING_SLNO and\n" + 
//                "      a.VOUCHER_DATE=b.ACCEPTING_DATE and\n" + 
//                "      a.ACCOUNTING_UNIT_ID=? and   \n" + 
//                "      a.ACCOUNTING_FOR_OFFICE_ID=? and    \n" + 
//                "      a.VOUCHER_DATE=? AND   \n" + 
//                "      a.TDA_OR_TCA=? and   \n" + 
//                "      a.STATUS='L' and a.accepting_jvr_no is not null \n" + 
//                ")aa \n" + 
//                "where aa.RESPONDING_JVR_NO not in\n" + 
//                "(\n" + 
//                "    select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID " +
//                "and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and VOUCHER_NO=aa.RESPONDING_JVR_NO and to_date(VOUCHER_DATE,'dd-mm-yy')=to_date(aa.RESPONDING_JVR_DATE,'dd-mm-yy') and JOURNAL_STATUS='L'\n" + 
//                ")";
            	
            	QueryType="select * from\n" + 
                        "(\n" + 
                        "    select   \n" +
                        "      a.VOUCHER_NO," + 
                        "      b.ACCOUNTING_UNIT_ID,\n" + 
                        "      b.ACCOUNTING_FOR_OFFICE_ID,      \n" + 
                        "      a.RESPONDING_JVR_NO, \n" + 
                        "      a.RESPONDING_JVR_DATE \n" + 
                        "    from   \n" + 
                        "      FAS_TDA_TCA_RAISED_MST a, FAS_TDA_TCA_RAISED_MST b   \n" + 
                        "    where  \n" + 
                        "      a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and\n" + 
                        "      a.VOUCHER_NO=b.ACCEPTING_SLNO and\n" + 
                        "      a.VOUCHER_DATE=b.ACCEPTING_DATE and\n" + 
                        "      a.ACCOUNTING_UNIT_ID=? and   \n" + 
                        "      a.ACCOUNTING_FOR_OFFICE_ID=? and    \n" + 
                        "      a.VOUCHER_DATE=? AND   \n" + 
                        "      a.TDA_OR_TCA=? and   \n" + 
                        "      a.STATUS='L' and a.accepting_jvr_no is not null  and \n" + 
                        "      a.SUPPLEMENT_NO IS NOT NULL  and a.SUPPLEMENT_NO!=0"+
                        ")aa \n" + 
                        "where aa.RESPONDING_JVR_NO not in\n" + 
                        "(\n" + 
                        "    select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID " +
                        "and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and VOUCHER_NO=aa.RESPONDING_JVR_NO and to_date(VOUCHER_DATE,'dd-mm-yy')=to_date(aa.RESPONDING_JVR_DATE,'dd-mm-yy') and JOURNAL_STATUS='L'\n" + 
                        ")";
            	
            }
            else if(cmbSubSystemType.equalsIgnoreCase("TDAABS")||cmbSubSystemType.equalsIgnoreCase("TCAABS"))
            {
//                QueryType="select * from\n" + 
//                "(\n" + 
//                "    select   \n" +
//                "      a.VOUCHER_NO," + 
//                "      b.ACCOUNTING_UNIT_ID,\n" + 
//                "      b.ACCOUNTING_FOR_OFFICE_ID,      \n" + 
//                "      a.ACCEPTING_JVR_NO, \n" + 
//                "      a.ACCEPTING_JVR_DATE \n" + 
//                "    from   \n" + 
//                "      FAS_TDA_TCA_RAISED_MST a, FAS_TDA_TCA_RAISED_MST b   \n" + 
//                "    where  \n" + 
//                "      a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and\n" + 
//                "      a.VOUCHER_NO=b.ACCEPTING_SLNO and\n" + 
//                "      a.VOUCHER_DATE=b.ACCEPTING_DATE and\n" + 
//                "      a.ACCOUNTING_UNIT_ID=? and   \n" + 
//                "      a.ACCOUNTING_FOR_OFFICE_ID=? and    \n" + 
//                "      a.VOUCHER_DATE=? AND   \n" + 
//                "      a.TDA_OR_TCA=? and   \n" + 
//                "      a.STATUS='L' \n" + 
//                ")aa \n" + 
//                "where aa.ACCEPTING_JVR_NO IS NULL";
            	
            	QueryType="select * from\n" + 
                        "(\n" + 
                        "    select   \n" +
                        "      a.VOUCHER_NO," + 
                        "      b.ACCOUNTING_UNIT_ID,\n" + 
                        "      b.ACCOUNTING_FOR_OFFICE_ID,      \n" + 
                        "      a.ACCEPTING_JVR_NO, \n" + 
                        "      a.ACCEPTING_JVR_DATE \n" + 
                        "    from   \n" + 
                        "      FAS_TDA_TCA_RAISED_MST a, FAS_TDA_TCA_RAISED_MST b   \n" + 
                        "    where  \n" + 
                        "      a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and\n" + 
                        "      a.VOUCHER_NO=b.ACCEPTING_SLNO and\n" + 
                        "      a.VOUCHER_DATE=b.ACCEPTING_DATE and\n" + 
                        "      a.ACCOUNTING_UNIT_ID=? and   \n" + 
                        "      a.ACCOUNTING_FOR_OFFICE_ID=? and    \n" + 
                        "      a.VOUCHER_DATE=? AND   \n" + 
                        "      a.TDA_OR_TCA=? and   \n" + 
                        "      a.STATUS='L'  and \n" + 
                        "      a.SUPPLEMENT_NO IS NOT NULL  and a.SUPPLEMENT_NO!=0"+
                        ")aa \n" + 
                        "where aa.ACCEPTING_JVR_NO IS NULL";
                
            }
            else if(cmbSubSystemType.equalsIgnoreCase("TDARS")||cmbSubSystemType.equalsIgnoreCase("TCARS"))
            {
                  if(cmbSubSystemType.equalsIgnoreCase("TDARS")){
//              QueryType="SELECT VOUCHER_NO,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID AS acct_unit_id,ACCEPTING_SLNO," +
//                            "ACCEPTING_DATE FROM FAS_TDA_TCA_RAISED_MST WHERE ACCOUNTING_UNIT_ID =?  AND " +
//                            "ACCOUNTING_FOR_OFFICE_ID=? AND Responding_Jvr_Date=? AND (TDA_OR_TCA ='TDAO' or TDA_OR_TCA='TDACB') AND" +
//                            " STATUS ='L' AND RESPONDING_JVR_NO >0";
                	  
                	  QueryType="SELECT VOUCHER_NO,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID AS acct_unit_id,ACCEPTING_SLNO," +
                              "ACCEPTING_DATE FROM FAS_TDA_TCA_RAISED_MST WHERE ACCOUNTING_UNIT_ID =?  AND " +
                              "ACCOUNTING_FOR_OFFICE_ID=? AND Responding_Jvr_Date=? AND (TDA_OR_TCA ='TDAO' or TDA_OR_TCA='TDACB') AND" +
                              " STATUS ='L' AND RESPONDING_JVR_NO >0 AND SUPPLEMENT_NO IS NOT NULL and SUPPLEMENT_NO!=0";
                	  
                  }
                  else 
                  if(cmbSubSystemType.equalsIgnoreCase("TCARS")){
//              QueryType="SELECT VOUCHER_NO,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID AS acct_unit_id,ACCEPTING_SLNO," +
//                            "ACCEPTING_DATE FROM FAS_TDA_TCA_RAISED_MST WHERE ACCOUNTING_UNIT_ID =?  AND " +
//                            "ACCOUNTING_FOR_OFFICE_ID=? AND RESPONDING_JVR_DATE=? AND (TDA_OR_TCA ='TCAO' or TDA_OR_TCA='TCACB') AND" +
//                            " STATUS ='L' AND RESPONDING_JVR_NO >0";
                	  QueryType="SELECT VOUCHER_NO,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID AS acct_unit_id,ACCEPTING_SLNO," +
                              "ACCEPTING_DATE FROM FAS_TDA_TCA_RAISED_MST WHERE ACCOUNTING_UNIT_ID =?  AND " +
                              "ACCOUNTING_FOR_OFFICE_ID=? AND RESPONDING_JVR_DATE=? AND (TDA_OR_TCA ='TCAO' or TDA_OR_TCA='TCACB') AND" +
                              " STATUS ='L' AND RESPONDING_JVR_NO >0 AND SUPPLEMENT_NO IS NOT NULL and SUPPLEMENT_NO!=0";
                	  
                	  
                  }
            }
     //--------------------------------------------------------       
            
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
                if(cmbSubSystemType.equalsIgnoreCase("TDAOS"))
                    ps.setString(4,"TDAO");
                else if(cmbSubSystemType.equalsIgnoreCase("TCAOS"))
                    ps.setString(4,"TCAO");
                else if(cmbSubSystemType.equalsIgnoreCase("TDAAS"))
                {
                    System.out.println("inside TDAA");
                    ps.setString(4,"TDAA");
                }
                else if(cmbSubSystemType.equalsIgnoreCase("TCAAS"))
                    ps.setString(4,"TCAA");
                else if(cmbSubSystemType.equalsIgnoreCase("TCAABS"))
                    ps.setString(4,"TCAA");
                else if(cmbSubSystemType.equalsIgnoreCase("TDAABS"))
                    ps.setString(4,"TDAA");
                rs = ps.executeQuery();

                 count = 0;
                while (rs.next()) {

                    xml =
                    xml + "<Rec_No>" + rs.getInt("VOUCHER_NO") + "</Rec_No>";
                   
                 /*  QueryType="Select A.Voucher_No  From Fas_Journal_Transaction A join Fas_Journal_Master B On  A.Voucher_No=B.Voucher_No Where A.Voucher_No='"+ rs.getInt("VOUCHER_NO")+"' And A.Cashbook_Year =extract(YEAR FROM VOUCHER_DATE) AND A.Cashbook_Month =extract(MONTH FROM B.VOUCHER_DATE) AND A.Accounting_Unit_Id=? And A.Accounting_For_Office_Id =? And B.Voucher_Date=? AND A.CB_REF_NO <>0";
                    
                	ps = con.prepareStatement(QueryType);
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setDate(3, txtCrea_date);

                    rs = ps.executeQuery();

                     count = 0;
                     while (rs.next()) {

                         xml = xml + "<Edit>true</Edit>";
                         count++;
                     }
                     xml = xml + "<Edit>false</Edit>";*/
                     count++;
                    
                }
               /*
                * siva added on 26-02-2016 flag set for pending bill generated jrnl  
                *  if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                	
                    xml = xml + "<flag>success</flag>";
                */
                
                if (count == 0){
                    xml = xml + "<flag>failure</flag>";
               
                }
                else{
                	/*QueryType="select Voucher_No from fas_journal_transaction where voucher_no='21' and cashbook_year='2016' and cashbook_month='2' and ACCOUNTING_UNIT_ID='33' and ACCOUNTING_FOR_OFFICE_ID='5052'  and CB_REF_NO<>0";
                	ps = con.prepareStatement(QueryType);
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setDate(3, txtCrea_date);

                    rs = ps.executeQuery();

                     count = 0;
                     while (rs.next()) {

                         xml = xml + "<Edit>true</Edit>";
                         count++;
                     }
                     xml= xml + "<Edit>false</Edit>";*/
                	
                    xml = xml + "<flag>success</flag>";
                }
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
        
        else if (strCommand.equalsIgnoreCase("voucherLoad"))
        {
            xml = "<response><command>voucherLoad</command>";
            int ss=0,counted=0;
            
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** Get Accounting Office ID  */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            /** Get Sub System Type */
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
           if (cmbSubSystemType.equalsIgnoreCase("SJV"))
            {
            	try {
                    ss = Integer.parseInt(request.getParameter("ss"));
                } catch (NumberFormatException e) {
                    System.out.println("exception" + e);
                } 
            	//siva query  on 06-06-2016 in the purpose of authorization of vocher_no
            	
            	String sql11="SELECT a.VOUCHER_NO FROM fas_journal_master a ,   fas_journal_transaction b "
            			+ " WHERE a.accounting_unit_id    =b.accounting_unit_id AND "
            			+ " a.accounting_for_office_id=b.accounting_for_office_id "
            			+ " AND a.cashbook_year           =b.cashbook_year "
            			+ " AND a.cashbook_month          =b.cashbook_month "
            			+ " AND A.VOUCHER_NO              =B.VOUCHER_NO "
            			+ " and a.ACCOUNTING_UNIT_ID    =? "
            			+ " AND a.ACCOUNTING_FOR_OFFICE_ID=? "
            			+ " AND A.VOUCHER_DATE            =?"
            			+ " AND a.CREATED_BY_MODULE       ='SJV' "            			
            			+ " AND A.JOURNAL_STATUS!='C' "
            			+ " AND A.MODE_OF_CREATION!       ='A' "
            			+ " AND A.SUPPLEMENT_NO           =?"
            			+ " AND A.VOUCHER_NO NOT IN ( "
            			+ " SELECT aa.voucher_no    FROM fas_journal_transaction aa   "
            			+ " WHERE aa.accounting_unit_id    ="+cmbAcc_UnitCode
            			+ " AND aa.accounting_for_office_id= "+cmbOffice_code
            			+ "  AND aa.cashbook_year           =extract(YEAR FROM a.VOUCHER_DATE) "
     		            +"  AND aa.cashbook_month          =extract(MONTH FROM a.VOUCHER_DATE) "
            			+ " AND aa.CHEQUE_OR_DD           IS NULL   "
            			+ " AND aa.CHEQUE_DD_NO           >=0   "
            			+ " AND aa.CHEQUE_DD_DATE         IS NULL  "
            			+ " AND CB_REF_NO                 >=0   "
            			+ " AND CB_REF_DATE               IS NULL ) GROUP BY A.VOUCHER_NO ORDER BY 1";
            	
            	
            	/* siva query  on 06-06-2016 in the purpose of authorization of vocher_no
            	 * 
            	 * String sql11 ="select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                                    "and VOUCHER_DATE=?  and CREATED_BY_MODULE='SJV' and CB_REF_TYPE is null and JOURNAL_STATUS!='C' and mode_of_creation!='A' and SUPPLEMENT_NO=?";*/
                           try{  
                           System.out.println("sql11 in sjv:"+sql11);
                        ps3 = con.prepareStatement(sql11);
                        ps3.setInt(1, cmbAcc_UnitCode);
                        ps3.setInt(2, cmbOffice_code);
                        ps3.setDate(3, txtCrea_date);System.out.println(":::: "+txtCrea_date);
                        ps3.setInt(4, ss);
        
                        rs4 = ps3.executeQuery();
                        
                               while(rs4.next()) 
                               {
                                xml = xml + "<voucherNo>"+rs4.getInt("VOUCHER_NO")+"</voucherNo>";
                                   counted++;                                                           
                               }
                               if(counted>0){
                                   System.out.println("rs4>>>"+xml);
                                   xml = xml + "<flag>success</flag>";
                               }
                               else
                               xml = xml + "<flag>failure</flag>";                      
                        
                           }
                    catch (Exception e) {
                                    System.out.println("catch..HERE.in load SJV." + e);
                                    xml = xml + "<flag>failure</flag>";
                                }
                xml = xml + "</response>";
                System.out.println("last xml in auth::::"+xml);
                out.println(xml);
            }
        }
        else if(strCommand.equalsIgnoreCase("loadsupplement_sjv")){
        
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int txtCash_year=0,txtCash_Month_hid=0;
            String xml2="";
            String[] sd=null;
            String cash_year=null,cash_year1=null;
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** Get Accounting Office ID  */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            /** Get Sub System Type */
            cmbSubSystemType = request.getParameter("cmbSubSystemType");
            System.out.println("cmbSubSystemType.. " + cmbSubSystemType);
     	   xml2 = "<response><command>loadsupplement_sjv</command>";
            try{
      	  PreparedStatement ps_dte=con.prepareStatement("SELECT MAX(CASHBOOK_YEAR), " +
				  "    CASE WHEN MAX(CASHBOOK_YEAR) = " +
						  "        (SELECT MAX(CASHBOOK_YEAR) FROM FAS_SUPPLEMENT_GJV WHERE STATUS ='L' group by STATUS " +
								  "        ) " +
								  "      THEN  " +
								  "     '31/03/'|| extract(YEAR from now()) " +
								  "      ELSE  " +
								  "      '31/03/'||(SELECT MAX(SUP.CASHBOOK_YEAR) FROM FAS_SUPPLEMENT_GJV SUP WHERE STATUS ='L') " +
							      "     END AS CASHBOOK_YEAR " +
								  "    FROM FAS_TRIAL_BALANCE_STATUS_SJV  " +
								  "    WHERE ACCOUNTING_UNIT_ID =" +cmbAcc_UnitCode+" group by ACCOUNTING_UNIT_ID ");
      	  
System.out.println("SELECT MAX(CASHBOOK_YEAR), " +
				  "    CASE WHEN MAX(CASHBOOK_YEAR) = " +
						  "        (SELECT MAX(CASHBOOK_YEAR) FROM FAS_SUPPLEMENT_GJV WHERE STATUS ='L' group by STATUS " +
								  "        ) " +
								  "      THEN  " +
								  "     '31/03/'|| extract(YEAR from now()) " +
								  "      ELSE  " +
								  "      '31/03/'||(SELECT MAX(SUP.CASHBOOK_YEAR) FROM FAS_SUPPLEMENT_GJV SUP WHERE STATUS ='L') " +
							      "     END AS CASHBOOK_YEAR " +
								  "    FROM FAS_TRIAL_BALANCE_STATUS_SJV  " +
								  "    WHERE ACCOUNTING_UNIT_ID =" +cmbAcc_UnitCode+" group by ACCOUNTING_UNIT_ID ");



  ResultSet rs_dte=ps_dte.executeQuery();
  while(rs_dte.next()){
	 cash_year=rs_dte.getString("CASHBOOK_YEAR");
	
  }
  }catch (Exception e){
	  e.printStackTrace();
  }
          System.out.println("cash_year "+cash_year);
          
          String sjvdate=request.getParameter("txtCrea_date");
          
          if(cash_year==null ||cash_year=="null")
          {
        	 try
        	 {
        	  PreparedStatement ps11=con.prepareStatement("SELECT '31/03/'||MAX(CASHBOOK_YEAR)as year FROM FAS_SUPPLEMENT_GJV WHERE STATUS ='L'");
        	  ResultSet rs_dte=ps11.executeQuery();
        	  while(rs_dte.next()){
        		 cash_year1=rs_dte.getString("year");
        		System.out.println("cash_year1==>"+cash_year1);
        	  }
        	  try {
                  sd = cash_year1.split("/");
                   c =  new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                Integer.parseInt(sd[0]));
                   java.util.Date d = c.getTime();
                   txtCrea_date = new Date(d.getTime());
                   System.out.println("txtCrea_date " + txtCrea_date);
               } catch (NumberFormatException e) {
                   System.out.println("exception" + e);
               }
               try {
                   txtCash_year = Integer.parseInt(sd[2]);
                   System.out.println("txtCash_year " + txtCash_year);
               } catch (Exception e) {
                   System.out.println("exception" + e);
               }
               try {
                   txtCash_Month_hid = Integer.parseInt(sd[1]);
                   System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
               } catch (Exception e) {
                   System.out.println("exception" + e);
               }
        	  
        	 }
        	 catch(Exception e)
        	 {
        		 
        	 }
        	  
        	 
//        	  try {
//                 sd = request.getParameter("txtCrea_date").split("/");
//                  c =  new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
//                               Integer.parseInt(sd[0]));
//                  java.util.Date d = c.getTime();
//                  txtCrea_date = new Date(d.getTime());
//                  System.out.println("txtCrea_date " + txtCrea_date);
//              } catch (NumberFormatException e) {
//                  System.out.println("exception" + e);
//              }
//              try {
//            	  txtCash_year = Integer.parseInt(sd[2]);
//              } catch (Exception e) {
//                  System.out.println("exception" + e);
//              }
//              try {
//                  txtCash_Month_hid = Integer.parseInt(sd[1]);
//              } catch (Exception e) {
//                  System.out.println("exception" + e);
//              }
          }
          else
          {
                    try {
                       sd = cash_year.split("/");
                        c =  new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                     Integer.parseInt(sd[0]));
                        java.util.Date d = c.getTime();
                        txtCrea_date = new Date(d.getTime());
                        System.out.println("txtCrea_date " + txtCrea_date);
                    } catch (NumberFormatException e) {
                        System.out.println("exception" + e);
                    }
                    try {
                        txtCash_year = Integer.parseInt(sd[2]);
                    } catch (Exception e) {
                        System.out.println("exception" + e);
                    }
                    try {
                        txtCash_Month_hid = Integer.parseInt(sd[1]);
                    } catch (Exception e) {
                        System.out.println("exception" + e);
                    }
                   
          }
            
                    String sql2="SELECT SUPPLEMENT_NO FROM FAS_SUPPLEMENT_GJV WHERE status ='L' " +
                    " AND cashbook_year     ="+txtCash_year+" AND " +
                    " cashbook_month  ="+txtCash_Month_hid+" AND SUPPLEMENT_NO not in (SELECT supplement_no " +
                    " FROM fas_trial_balance_status_sjv WHERE accounting_unit_id ="+cmbAcc_UnitCode+" AND " +
                    " cashbook_month  ="+txtCash_Month_hid+" AND cashbook_year=  "+txtCash_year+" AND tb_status ='Y') order by SUPPLEMENT_NO  ";
                    try{
                    System.out.println("sql2>>>>>"+sql2);
                        ps1 = con.prepareStatement(sql2);
                        rs2=ps1.executeQuery();
                        while(rs2.next()) 
                        {
                         xml2 = xml2 + "<supnumber>"+rs2.getInt("SUPPLEMENT_NO")+"</supnumber>";
                            count++;                                                           
                        }
                        if(count>0){
                            System.out.println("rs2>>>"+xml2);
                           
                            if(cash_year==null ||cash_year=="null")
                            {
                            xml2 = xml2 + "<cb_dte>"+cash_year1+"</cb_dte><flag>supsuccess</flag>";
                            }
                            else
                            {
                            	xml2 = xml2 + "<cb_dte>"+cash_year+"</cb_dte><flag>supsuccess</flag>";	
                            }
                        }
                        else
                        xml2 = xml2 + "<cb_dte>"+cash_year+"</cb_dte><flag>supfailure</flag>";
                      
                    }
                    catch(Exception e1) {
                        System.out.println("Exception in 2nd query"+e1);
                    }
                    
                    xml2 = xml2 + "</response>";
                    System.out.println("last xml in auth::::"+xml2);
                    out.println(xml2);
            
        
        }
        else if (strCommand.equalsIgnoreCase("loadsupplement"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** Get Accounting Office ID  */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            /** Get Sub System Type */
            cmbSubSystemType = request.getParameter("cmbSubSystemType");
            System.out.println("cmbSubSystemType.. " + cmbSubSystemType);
                int txtCash_year=0,txtCash_Month_hid=0;
                String xml2="";
                String[] sd=null;
                    try {
                       sd = request.getParameter("txtCrea_date").split("/");
                        c =  new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                     Integer.parseInt(sd[0]));
                        java.util.Date d = c.getTime();
                        txtCrea_date = new Date(d.getTime());
                        System.out.println("txtCrea_date " + txtCrea_date);
                    } catch (NumberFormatException e) {
                        System.out.println("exception" + e);
                    }
                    try {
                        txtCash_year = Integer.parseInt(sd[2]);
                    } catch (Exception e) {
                        System.out.println("exception" + e);
                    }
                    try {
                        txtCash_Month_hid = Integer.parseInt(sd[1]);
                    } catch (Exception e) {
                        System.out.println("exception" + e);
                    }
                   
            xml2 = "<response><command>loadsupplement</command>";
                    String sql2="SELECT SUPPLEMENT_NO FROM FAS_SUPPLEMENT_GJV WHERE status ='L' " +
                    " AND cashbook_year     ="+txtCash_year+" AND " +
                    " cashbook_month  ="+txtCash_Month_hid+" AND SUPPLEMENT_NO not in (SELECT supplement_no " +
                    " FROM fas_trial_balance_status_sjv WHERE accounting_unit_id ="+cmbAcc_UnitCode+" AND " +
                    " cashbook_month  ="+txtCash_Month_hid+" AND cashbook_year=  "+txtCash_year+" AND tb_status ='Y') order by SUPPLEMENT_NO  ";
                    try{
                    System.out.println("sql2>>>>>"+sql2);
                        ps1 = con.prepareStatement(sql2);
                        rs2=ps1.executeQuery();
                        while(rs2.next()) 
                        {
                         xml2 = xml2 + "<supnumber>"+rs2.getInt("SUPPLEMENT_NO")+"</supnumber>";
                            count++;                                                           
                        }
                        if(count>0){
                            System.out.println("rs2>>>"+xml2);
                            xml2 = xml2 + "<flag>supsuccess</flag>";
                        }
                        else
                        xml2 = xml2 + "<flag>supfailure</flag>";
                      
                    }
                    catch(Exception e1) {
                        System.out.println("Exception in 2nd query"+e1);
                    }
                    
                    xml2 = xml2 + "</response>";
                    System.out.println("last xml in auth::::"+xml2);
                    out.println(xml2);
            
        }
        else if (strCommand.equalsIgnoreCase("load_Voucher_details")) {
        	System.out.println("++++ load_Voucher_details ++++ ");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int txtVoucher_No = 0;
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** Get Accounting Office ID  */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            /** Get Sub System Type */
            cmbSubSystemType = request.getParameter("cmbSubSystemType");
            System.out.println("cmbSubSystemType.. " + cmbSubSystemType);
            /** Get Voucher Number  */
            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            try {
               String[] sd = request.getParameter("txtCrea_date").split("/");
                 c =  new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                              Integer.parseInt(sd[0]));
                 java.util.Date d = c.getTime();
                 txtCrea_date = new Date(d.getTime());
                 System.out.println("txtCrea_date " + txtCrea_date);
             } catch (NumberFormatException e) {
                 System.out.println("exception" + e);
             }
            System.out.println("txtVoucher_No " + txtVoucher_No);
            System.out.println("txtCrea_date.. " + txtCrea_date);
            xml = "<response><command>load_Voucher_details</command>";

            String QueryType = "";

            if (cmbSubSystemType.equalsIgnoreCase("GJV"))
                QueryType =
                        "select e.JOURNAL_TYPE_DESC as com_value,(select  coalesce(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                        " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                        " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt,  e.DISPLAY_RESTRICTED from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                        " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='GJV' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE ";
            else if (cmbSubSystemType.equalsIgnoreCase("LJV"))
                QueryType =
                        "select m.VOUCHER_NO,e.JOURNAL_TYPE_DESC as com_value,e.DISPLAY_RESTRICTED,(select Coalesce(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                        " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                        " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt, e.DISPLAY_RESTRICTED  from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                        " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='LJV' and trim(m.JOURNAL_STATUS)!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";

            else if (cmbSubSystemType.equalsIgnoreCase("SJV"))
                QueryType =
                        "select e.JOURNAL_TYPE_DESC as com_value,(select  coalesce(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                        " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                        " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt, e.DISPLAY_RESTRICTED  from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                        " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='SJV' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";

            else if(cmbSubSystemType.equalsIgnoreCase("IJV"))
                               QueryType="select e.JOURNAL_TYPE_DESC as com_value,(select  trim(nvl(to_char(sum(AMOUNT),'99999999999999.99'),'0')) from FAS_JOURNAL_TRANSACTION where " +
                               " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                               " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt, e.DISPLAY_RESTRICTED  from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                               " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='GJV' and MODE_OF_CREATION='I' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";
            else if(cmbSubSystemType.equalsIgnoreCase("TJV"))
                               QueryType="select e.JOURNAL_TYPE_DESC as com_value,(select  trim(nvl(to_char(sum(AMOUNT),'99999999999999.99'),'0')) from FAS_JOURNAL_TRANSACTION where " +
                               " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                               " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt, e.DISPLAY_RESTRICTED  from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                               " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='GJV' and MODE_OF_CREATION='T' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";
            else if (cmbSubSystemType.equalsIgnoreCase("TDCP"))
                QueryType =
                        "select e.JOURNAL_TYPE_DESC as com_value,(select  nvl(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                        " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                        " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt,  e.DISPLAY_RESTRICTED from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                        " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='GJV' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE ";
            
            else if(cmbSubSystemType.equalsIgnoreCase("RJV"))
                               QueryType="select e.JOURNAL_TYPE_DESC as com_value,(select  nvl(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                               " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                               " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt,e.JOURNAL_TYPE_CODE from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                               " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='GJV' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";
            else if(cmbSubSystemType.equalsIgnoreCase("TDAOS")||cmbSubSystemType.equalsIgnoreCase("TCAOS")||cmbSubSystemType.equalsIgnoreCase("TDAAS")||cmbSubSystemType.equalsIgnoreCase("TCAAS")||cmbSubSystemType.equalsIgnoreCase("TCAABS")||cmbSubSystemType.equalsIgnoreCase("TDAABS"))    
            			QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_TDA_TCA_RAISED_MST mst,FAS_MST_ACCT_UNITS acc_mst where mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=? and mst.STATUS!='C'and SUPPLEMENT_NO IS NOT NULL  and SUPPLEMENT_NO!=0";
            else if(cmbSubSystemType.equalsIgnoreCase("TDARS")||cmbSubSystemType.equalsIgnoreCase("TCARS"))
                QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_TDA_TCA_RAISED_MST mst,FAS_MST_ACCT_UNITS acc_mst where mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RESPONDING_JVR_DATE=? and VOUCHER_NO=? and mst.STATUS!='C' and SUPPLEMENT_NO IS NOT NULL  and SUPPLEMENT_NO!=0";
            
            
            System.out.println("QueryType...." + QueryType);
            System.out.println("Create Date:===>"+txtCrea_date);
            
            try {
                ps = con.prepareStatement(QueryType);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtVoucher_No);
                rs = ps.executeQuery();
System.out.println("QueryType >> "+QueryType);
System.out.println("cmbSubSystemType >> "+cmbSubSystemType);
                int typeCode=0;
                while(rs.next()) {
                	//System.out.println("rs.getString(DISPLAY_RESTRICTED).equalsIgnoreCase(Y)"+rs.getString("DISPLAY_RESTRICTED"));
                	   xml = xml + "<com_value>" + rs.getString("com_value") + "</com_value>";
                       xml = xml + "<amt>" + rs.getString("amt") + "</amt>";
                      
                	xml = xml + "<cmbSubSystemType>"+cmbSubSystemType+"</cmbSubSystemType>";
                	if(cmbSubSystemType.equalsIgnoreCase("RJV"))
                     {
                         //System.out.println("JournalTypeCode===>"+rs.getInt("JOURNAL_TYPE_CODE"));
                		  
                		  typeCode=rs.getInt("JOURNAL_TYPE_CODE");
                		  System.out.println("~~~~~~~~~~~~`"+typeCode);
                         xml = xml + "<typeCode>"+typeCode+"</typeCode>";
                     }
                    if (cmbSubSystemType.equalsIgnoreCase("GJV") && rs.getString("DISPLAY_RESTRICTED").equalsIgnoreCase("Y") )
                    {
                        xml = xml + "<flagName>notAuthorize</flagName>";
                        /*QueryType="Select A.Voucher_No  From Fas_Journal_Transaction A join Fas_Journal_Master B On  A.Voucher_No=B.Voucher_No Where A.Voucher_No='"+txtVoucher_No+"' And A.Cashbook_Year =extract(YEAR FROM VOUCHER_DATE) AND A.Cashbook_Month =extract(MONTH FROM B.VOUCHER_DATE) AND A.Accounting_Unit_Id=? And A.Accounting_For_Office_Id =? And B.Voucher_Date=? AND A.CB_REF_NO <>0";
                        
                     	ps = con.prepareStatement(QueryType);
                         ps.setInt(1, cmbAcc_UnitCode);
                         ps.setInt(2, cmbOffice_code);
                         ps.setDate(3, txtCrea_date);

                         rs = ps.executeQuery();

                          count = 0;
                          while (rs.next()) {

                              xml = xml + "<Edit>true</Edit>";
                              
                          }
                          xml = xml + "<Edit>false</Edit>";*/
                    }
                    else if (cmbSubSystemType.equalsIgnoreCase("LJV"))
                    {
                      QueryType="Select A.Voucher_No  From Fas_Journal_Transaction A join Fas_Journal_Master B On  A.Voucher_No=B.Voucher_No Where A.Voucher_No='"+txtVoucher_No+"' And A.Cashbook_Year =extract(YEAR FROM VOUCHER_DATE) AND A.Cashbook_Month =extract(MONTH FROM B.VOUCHER_DATE) AND A.Accounting_Unit_Id=? And A.Accounting_For_Office_Id =? And B.Voucher_Date=? AND A.CB_REF_NO <>0";
                         
                     	ps = con.prepareStatement(QueryType);
                         ps.setInt(1, cmbAcc_UnitCode);
                         ps.setInt(2, cmbOffice_code);
                         ps.setDate(3, txtCrea_date);

                         rs = ps.executeQuery();

                          count = 0;
                          while (rs.next()) {

                              xml = xml + "<Edit>true</Edit>";
                              
                          }
                          xml = xml + "<Edit>false</Edit>";
                       
                    }
                    else if (cmbSubSystemType.equalsIgnoreCase("SJV"))
                    {
                      QueryType="Select A.Voucher_No  From Fas_Journal_Transaction A join Fas_Journal_Master B On  A.Voucher_No=B.Voucher_No Where A.Voucher_No='"+txtVoucher_No+"' And A.Cashbook_Year =extract(YEAR FROM VOUCHER_DATE) AND A.Cashbook_Month =extract(MONTH FROM B.VOUCHER_DATE) AND A.Accounting_Unit_Id=? And A.Accounting_For_Office_Id =? And B.Voucher_Date=? AND A.CB_REF_NO <>0";
                         
                      System.out.println("sjv authorization"+QueryType);
                     	ps = con.prepareStatement(QueryType);
                         ps.setInt(1, cmbAcc_UnitCode);
                         ps.setInt(2, cmbOffice_code);
                         ps.setDate(3, txtCrea_date);

                         rs = ps.executeQuery();

                          count = 0;
                          while (rs.next()) {

                              xml = xml + "<Edit>true</Edit>";
                              
                          }
                          xml = xml + "<Edit>false</Edit>";
                       
                    }
                    count++;
                 
                }
                System.out.println("count   count count"+count);
                if (count == 0)
                {
                    xml = xml + "<flag>noData</flag>";
                xml = xml + "<flagName>auth</flagName>";
                }
                else{
                    if (cmbSubSystemType.equalsIgnoreCase("GJV")) {
                    	
               QueryType="Select A.Voucher_No  From Fas_Journal_Transaction A join Fas_Journal_Master B On  A.Voucher_No=B.Voucher_No Where A.Voucher_No='"+txtVoucher_No+"' And A.Cashbook_Year =extract(YEAR FROM VOUCHER_DATE) AND A.Cashbook_Month =extract(MONTH FROM B.VOUCHER_DATE) AND A.Accounting_Unit_Id=? And A.Accounting_For_Office_Id =? And B.Voucher_Date=? AND A.CB_REF_NO <>0";
                        
                     	ps = con.prepareStatement(QueryType);
                         ps.setInt(1, cmbAcc_UnitCode);
                         ps.setInt(2, cmbOffice_code);
                         ps.setDate(3, txtCrea_date);

                         rs = ps.executeQuery();

                          count = 0;
                          while (rs.next()) {

                              xml = xml + "<Edit>true</Edit>";
                              
                          }
                          xml = xml + "<Edit>false</Edit>";
                        xml = xml + "<flagName>auth</flagName>";
                        xml = xml + "<flag>success</flag>";
                    }
                    else if(cmbSubSystemType.equalsIgnoreCase("RJV")) {
                    //    xml = xml + "<typeCode>"+typeCode+"</typeCode>";
                        xml = xml + "<flag>success</flag>";
                    }
                    else
                    {
                    xml = xml + "<flagName>auth</flagName>";
                    xml = xml + "<flag>success</flag>";
                    }
                }
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
