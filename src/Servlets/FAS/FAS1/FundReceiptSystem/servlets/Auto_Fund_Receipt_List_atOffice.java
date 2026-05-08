package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Servlet implementation class Auto_Fund_Receipt_List_atOffice
 */
public class Auto_Fund_Receipt_List_atOffice extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Auto_Fund_Receipt_List_atOffice() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        /**
    	 * Set Content Type
    	 */
        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
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
         * Variables Declaration
         */
        Connection con = null;
        ResultSet rs = null;
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
        }

        /**
                *
                */
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();


        /**
                * Get Command Parameter
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
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;

        /* Get Accounting Unit Code */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /* Get Accounting for Office ID */
        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);


        if (strCommand.equalsIgnoreCase("searchByMonth")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            xml = "<response><command>searchByMonth</command>";


            try {

                String Condition = "";

                if (cmbOffice_code == 5000) {
                    Condition =
                            " and b.TRANSFERED_TO_HO_UNIT_ID=" + cmbAcc_UnitCode;
                }


                /* Get Cashbook Year */
                int year =
                    Integer.parseInt(request.getParameter("txtCB_Year"));
                System.out.println("the year is" + year);

                /* Get Cashbook Month */
                int month =
                    Integer.parseInt(request.getParameter("txtCB_Month"));
                System.out.println("the month is" + month);
                String sql = "";
                /* sql="select \n" +
                             "   fund_type,\n" +
                             "   to_char(date_of_transfer,'DD/MM/YYYY') as date_of_transfer,\n" +
                             "   voucher_no,\n" +
                             "   sl_no,	\n" +
                             "   (		\n" +
                             "     case when ho_ref_no is null then ' ' else ho_ref_no end \n" +
                             "   ) as horef_no,ho_ref_date, \n" +
                             "   cheque_dd_no,\n" +
                             "   cheque_dd_date,\n" +
                             "   trim(to_char(amount,'99999999999999.99')) as amount,\n" +
                             "   particulars,\n" +
                             "   SUBSTR(ACCOUNT_HEAD_CODE, 1, 4) as account_head_code,	                                        \n" +
                             "   office_bank_id,bank_name,										\n" +
                             "   office_branch_id,\n" +
                             "   office_account_no,  \n" +
                             "   trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +
                             "   ho_bank_id,\n" +
                             "   ho_branch_id,\n" +
                             "   ho_account_no,\n" +
                             "   cheque_or_dd \n" +
                             "from    \n" +
                             "  (    \n" +
                             "    select \n" +
                             "        accounting_unit_id,\n" +
                             "        accounting_for_office_id,\n" +
                             "        voucher_no,\n" +
                             "        date_of_transfer,\n" +
                             "        trim(to_char(total_amount,'99999999999999.99')) as total_amount,    \n" +
                             "        cashbook_year,\n" +
                             "        cashbook_month,\n" +
                             "        transfer_status,\n" +
                             "        ho_ref_no,\n" +
                             "        ho_ref_date,  \n" +
                             "        ho_bank_id,\n" +
                             "        ho_branch_id,\n" +
                             "        ho_account_no \n" +
                             "     from \n" +
                             "        fas_fund_trf_from_ho_master    \n" +
                             "  )a    \n" +
                             "  \n" +
                             "  inner join    \n" +
                             "  \n" +
                             "  (    \n" +
                             "    select \n" +
                             "       accounting_unit_id,\n" +
                             "       accounting_for_office_id,\n" +
                             "       transfer_to_office_id,\n" +
                             "       voucher_no as vouch_no,\n" +
                             "       account_head_code,    \n" +
                             "       cashbook_year,\n" +
                             "       cashbook_month,\n" +
                             "       cheque_dd_no,\n" +
                             "       cheque_dd_date,\n" +
                             "       trim(to_char(amount,'99999999999999.99')) as amount,\n" +
                             "       particulars,\n" +
                             "       fund_type,\n" +
                             "       auto_status,\n" +
                             "       sl_no,  \n" +
                             "       office_bank_id, (SELECT BANK_SHORT_NAME FROM FAS_MST_BANKS where BANK_ID = office_bank_id ) as bank_name ,   \n" +
                             "       office_branch_id,\n" +
                             "       office_account_no,\n" +
                             "       cheque_or_dd, TRANSFERED_TO_HO_UNIT_ID \n" +
                             "     from     \n" +
                             "       fas_fund_trf_from_ho_trn     \n" +
                             "  )b    \n" +
                             "  \n" +
                             "  on\n" +
                             "      a.accounting_unit_id=b.accounting_unit_id\n" +
                             "  and a.accounting_for_office_id=b.accounting_for_office_id    \n" +
                             "  and a.voucher_no=b.vouch_no \n" +
                             "  and a.cashbook_year=b.cashbook_year \n" +
                             "  and a.cashbook_month=b.cashbook_month     \n" +
                             "  \n" +
                             "where\n" +
                             "    a.cashbook_month  = " +month+ "\n"+
                             " and a.cashbook_year = " +year+  "\n"+
                             " and b.transfer_to_office_id = " +cmbOffice_code+ "\n"+
                             " and a.transfer_status='L' \n" +
                             " and b.auto_status is null   " ;


                            if( cmbOffice_code == 5000 )
                            {
                               sql=sql+"and b.TRANSFERED_TO_HO_UNIT_ID="+cmbAcc_UnitCode;
                            }*/
/*
 * joe change on 08/04/2014
 * 
 * 
 * 
                sql =
                	"select distinct * from ( SELECT fund_type, " + "  TO_CHAR(date_of_transfer,'DD/MM/YYYY') AS date_of_transfer, " +
   "  voucher_no, " + "  sl_no, " + "  ( " + "  CASE " +
   "    WHEN ho_ref_no IS NULL " + "    THEN ' ' " + "    ELSE ho_ref_no " +
   "  END ) AS horef_no, " + "  ho_ref_date, " + "  cheque_dd_no, " +
   "  cheque_dd_date, " +
   "  trim(TO_CHAR(amount,'99999999999999.99')) AS amount, " +
   "  particulars, " +
   "  SUBSTR(ACCOUNT_HEAD_CODE, 1, 4) AS account_head_code, " +
   "  office_bank_id, " + "  bank_name, " + "  office_branch_id, " +
   "  office_account_no, " +
   "  trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount, " +
   "  ho_bank_id, " + "  ho_branch_id, " + "  ho_account_no, " +
   "  cheque_or_dd, "+" auto_status " + "FROM " + "  (SELECT accounting_unit_id, " +
   "    accounting_for_office_id, " + "    voucher_no, " +
   "    date_of_transfer, " +
   "    trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount, " +
   "    cashbook_year, " + "    cashbook_month, " + "    transfer_status, " +
   "    ho_ref_no, " + "    ho_ref_date, " + "    ho_bank_id, " +
   "    ho_branch_id, " + "    ho_account_no " +
   "  FROM fas_fund_trf_from_ho_master " + "  )a " + "INNER JOIN " +
   "  (SELECT accounting_unit_id, " + "    accounting_for_office_id, " +
   "    transfer_to_office_id, " + "    voucher_no AS vouch_no, " +
   "    account_head_code, " + "    cashbook_year, " + "    cashbook_month, " +
   "    cheque_dd_no, " + "    cheque_dd_date, " +
   "    trim(TO_CHAR(amount,'99999999999999.99')) AS amount, " +
   "    particulars, " + "    fund_type, " + "    auto_status, " +
   "    sl_no, " + "    office_bank_id, " +
   "    (SELECT BANK_SHORT_NAME FROM FAS_MST_BANKS WHERE BANK_ID = office_bank_id " +
   "    ) AS bank_name , " + "    office_branch_id, " +
   "    office_account_no, " + "    cheque_or_dd, " +
   "    TRANSFERED_TO_HO_UNIT_ID " + "  FROM fas_fund_trf_from_ho_trn " +
   "  )b " + "ON a.accounting_unit_id       =b.accounting_unit_id " +
   "AND a.accounting_for_office_id=b.accounting_for_office_id " +
   "AND a.voucher_no              =b.vouch_no " +
   "AND a.cashbook_year           =b.cashbook_year " +
   "AND a.cashbook_month          =b.cashbook_month " +
   "WHERE a.cashbook_month        = " + month + " " +
   "AND a.cashbook_year           = " + year + " " +
   "AND b.transfer_to_office_id   =  " + cmbOffice_code + " " +
   "AND a.transfer_status         ='L' " +
 //  "AND b.auto_status ='Y'  "   + Condition + " ) dd " + 
  Condition + " ) dd " +
   " left outer join(select BANK_AC_NO,BANK_ID,BRANCH_ID,AC_OPERATIONAL_MODE_ID from " +
   " FAS_OFFICE_BANK_AC_CURRENT     " +
   "   WHERE STATUS              ='Y'    " +
   "   AND MODULE_ID             ='MF015'  " +
  // "  AND CR_DR_TYPE            ='CR'     " +//Lakshmi 8Nov13
 //  "   AND SL_NO                 =1    " +
   //" and ACCOUNTING_UNIT_ID=5 " +
   		")e " +
   "  on " +
 "  e.BANK_ID  =dd.office_BANK_ID "+
 "  AND e.BANK_AC_NO =dd.OFFICE_ACCOUNT_NO "+
  " and e.BRANCH_ID=dd.OFFICE_BRANCH_ID ";
   
   //+ " e.BANK_ID=dd.office_bank_id and dd.ho_account_no=e.BANK_AC_NO ";

*
*
*
*
*/

              /// joan added           
          /*      sql =
                    	"select distinct * from ( SELECT fund_type, " + "  TO_CHAR(date_of_transfer,'DD/MM/YYYY') AS date_of_transfer, " +
       "  voucher_no, " + "  sl_no, " + "  ( " + "  CASE " +
       "    WHEN ho_ref_no IS NULL " + "    THEN ' ' " + "    ELSE ho_ref_no " +
       "  END ) AS horef_no, " + "  ho_ref_date, " + "  cheque_dd_no, " +
       "  cheque_dd_date, " +
       "  trim(TO_CHAR(amount,'99999999999999.99')) AS amount, " +
       "  particulars, " +
      // "  SUBSTR(ACCOUNT_HEAD_CODE, 1, 4) AS account_head_code, " +
       "  office_bank_id, " + "  bank_name, " + "  office_branch_id, " +
       "  office_account_no, " +
       "  trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount, " +
       "  ho_bank_id, " + "  ho_branch_id, " + "  ho_account_no, " +
       "  cheque_or_dd, "+" auto_status " + "FROM " + "  (SELECT accounting_unit_id, " +
       "    accounting_for_office_id, " + "    voucher_no, " +
       "    date_of_transfer, " +
       "    trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount, " +
       "    cashbook_year, " + "    cashbook_month, " + "    transfer_status, " +
       "    ho_ref_no, " + "    ho_ref_date, " + "    ho_bank_id, " +
       "    ho_branch_id, " + "    ho_account_no " +
       "  FROM fas_fund_trf_from_ho_master " + "  )a " + "INNER JOIN " +
       "  (SELECT accounting_unit_id, " + "    accounting_for_office_id, " +
       "    transfer_to_office_id, " + "    voucher_no AS vouch_no, " +
       "    account_head_code, " + "    cashbook_year, " + "    cashbook_month, " +
       "    cheque_dd_no, " + "    cheque_dd_date, " +
       "    trim(TO_CHAR(amount,'99999999999999.99')) AS amount, " +
       "    particulars, " + "    fund_type, " + "    auto_status, " +
       "    sl_no, " + "    office_bank_id, " +
       "    (SELECT BANK_SHORT_NAME FROM FAS_MST_BANKS WHERE BANK_ID = office_bank_id " +
       "    ) AS bank_name , " + "    office_branch_id, " +
       "    office_account_no, " + "    cheque_or_dd, " +
       "    TRANSFERED_TO_HO_UNIT_ID " + "  FROM fas_fund_trf_from_ho_trn " +
       "  )b " + "ON a.accounting_unit_id       =b.accounting_unit_id " +
       "AND a.accounting_for_office_id=b.accounting_for_office_id " +
       "AND a.voucher_no              =b.vouch_no " +
       "AND a.cashbook_year           =b.cashbook_year " +
       "AND a.cashbook_month          =b.cashbook_month " +
       "WHERE a.cashbook_month        = " + month + " " +
       "AND a.cashbook_year           = " + year + " " +
       "AND b.transfer_to_office_id   =  " + cmbOffice_code + " " +
       "AND a.transfer_status         ='L' " +
     //  "AND b.auto_status ='Y'  "   + Condition + " ) dd " + 
      Condition + " ) dd " +
       " left outer join(select  SUBSTR(AC_HEAD_CODE, 1, 4) AS account_head_code, BANK_AC_NO,BANK_ID,BRANCH_ID,AC_OPERATIONAL_MODE_ID from " +
       " FAS_OFFICE_BANK_AC_CURRENT     " +
       "   WHERE STATUS              ='Y'    " +
       "   AND MODULE_ID ='MF009'  and cr_dr_type='DR'  " +
      // "  AND CR_DR_TYPE            ='CR'     " +
     //  "   AND SL_NO                 =1    " +
       //" and ACCOUNTING_UNIT_ID=5 " +
       		")e " +
       "  on " +
     "  e.BANK_ID  =dd.office_BANK_ID "+
     "  AND e.BANK_AC_NO =dd.OFFICE_ACCOUNT_NO ";
  //    " and e.BRANCH_ID=dd.OFFICE_BRANCH_ID ";
       
       //+ " e.BANK_ID=dd.office_bank_id and dd.ho_account_no=e.BANK_AC_NO ";
*/                
                sql="SELECT * " +
                "FROM " +
                "  (SELECT DISTINCT * " +
                "  FROM " +
                "    (SELECT fund_type, " +
                "      TO_CHAR(date_of_transfer,'DD/MM/YYYY') AS date_of_transfer, " +
                "      voucher_no, " +
                "      sl_no, " +
                "      ( " +
                "      CASE " +
                "        WHEN ho_ref_no IS NULL " +
                "        THEN ' ' " +
                "        ELSE ho_ref_no " +
                "      END ) AS horef_no, " +
                "      ho_ref_date, " +
                "      cheque_dd_no, " +
                "      cheque_dd_date, " +
                "      trim(TO_CHAR(amount::numeric,'99999999999999.99')) AS amount, " +
                "      particulars, " +
                "      office_bank_id, " +
                "      bank_name, " +
                "      office_branch_id, " +
                "      office_account_no, " +
                "      trim(TO_CHAR(total_amount::numeric,'99999999999999.99')) AS total_amount, " +
                "      ho_bank_id, " +
                "      ho_branch_id, " +
                "      ho_account_no, " +
                "      cheque_or_dd, " +
                "      auto_status " +
                "    FROM " +
                "      (SELECT accounting_unit_id, " +
                "        accounting_for_office_id, " +
                "        voucher_no, " +
                "        date_of_transfer, " +
                "        trim(TO_CHAR(total_amount::numeric,'99999999999999.99')) AS total_amount, " +
                "        cashbook_year, " +
                "        cashbook_month, " +
                "        transfer_status, " +
                "        ho_ref_no, " +
                "        ho_ref_date, " +
                "        ho_bank_id, " +
                "        ho_branch_id, " +
                "        ho_account_no " +
                "      FROM fas_fund_trf_from_ho_master " +
                "      )a " +
                "    INNER JOIN " +
                "      (SELECT accounting_unit_id, " +
                "        accounting_for_office_id, " +
                "        transfer_to_office_id, " +
                "        voucher_no AS vouch_no, " +
                "        account_head_code, " +
                "        cashbook_year, " +
                "        cashbook_month, " +
                "        cheque_dd_no, " +
                "        cheque_dd_date, " +
                "        trim(TO_CHAR(amount::numeric,'99999999999999.99')) AS amount, " +
                "        particulars, " +
                "        fund_type, " +
                "        auto_status, " +
                "        sl_no, " +
                "        office_bank_id, " +
                "        (SELECT BANK_SHORT_NAME FROM FAS_MST_BANKS WHERE BANK_ID = office_bank_id " +
                "        ) AS bank_name , " +
                "        office_branch_id, " +
                "        office_account_no, " +
                "        cheque_or_dd, " +
                "        TRANSFERED_TO_HO_UNIT_ID " +
                "      FROM fas_fund_trf_from_ho_trn " +
                "      )b " +
                "    ON a.accounting_unit_id       =b.accounting_unit_id " +
                "    AND a.accounting_for_office_id=b.accounting_for_office_id " +
                "    AND a.voucher_no              =b.vouch_no " +
                "    AND a.cashbook_year           =b.cashbook_year " +
                "    AND a.cashbook_month          =b.cashbook_month " +
                "    WHERE a.cashbook_month        =  " +month+
                "    AND a.cashbook_year           =  " +year+
                "    AND b.transfer_to_office_id   =  " +cmbOffice_code+
                "    AND a.transfer_status         ='L' " +
                "    ) dd " +
                "  LEFT OUTER JOIN " +
                "    (SELECT SUBSTR(AC_HEAD_CODE::varchar, 1, 4) AS account_head_code, " +
                "      BANK_AC_NO, " +
                "      BANK_ID, " +
                "      BRANCH_ID, " +
                "      AC_OPERATIONAL_MODE_ID " +
                "    FROM FAS_OFFICE_BANK_AC_CURRENT " +
                "    WHERE STATUS          ='Y' " +
                "    AND ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
                "    AND MODULE_ID         ='MF015' " +
                //         "       AND cr_dr_type='CR' " +
                 "       AND cr_dr_type='CR' " +
                "    )e " +
                "  ON e.BANK_ID     =dd.office_BANK_ID " +
                "  AND e.BANK_AC_NO =dd.OFFICE_ACCOUNT_NO " +
                // "  AND e.BRANCH_ID  =dd.OFFICE_BRANCH_ID " +
                "  )fin " +
                "WHERE fin.AC_OPERATIONAL_MODE_ID NOT LIKE '%OPR-%' " +
                "UNION ALL " +
                "SELECT * " +
                "FROM " +
                "  (SELECT DISTINCT * " +
                "  FROM " +
                "    (SELECT fund_type, " +
                "      TO_CHAR(date_of_transfer,'DD/MM/YYYY') AS date_of_transfer, " +
                "      voucher_no, " +
                "      sl_no, " +
                "      ( " +
                "      CASE " +
                "        WHEN ho_ref_no IS NULL " +
                "        THEN ' ' " +
                "        ELSE ho_ref_no " +
                "      END ) AS horef_no, " +
                "      ho_ref_date, " +
                "      cheque_dd_no, " +
                "      cheque_dd_date, " +
                "      trim(TO_CHAR(amount::numeric,'99999999999999.99')) AS amount, " +
                "      particulars, " +
                "      office_bank_id, " +
                "      bank_name, " +
                "      office_branch_id, " +
                "      office_account_no, " +
                "      trim(TO_CHAR(total_amount::numeric,'99999999999999.99')) AS total_amount, " +
                "      ho_bank_id, " +
                "      ho_branch_id, " +
                "      ho_account_no, " +
                "      cheque_or_dd, " +
                "      auto_status " +
                "    FROM " +
                "      (SELECT accounting_unit_id, " +
                "        accounting_for_office_id, " +
                "        voucher_no, " +
                "        date_of_transfer, " +
                "        trim(TO_CHAR(total_amount::numeric,'99999999999999.99')) AS total_amount, " +
                "        cashbook_year, " +
                "        cashbook_month, " +
                "        transfer_status, " +
                "        ho_ref_no, " +
                "        ho_ref_date, " +
                "        ho_bank_id, " +
                "        ho_branch_id, " +
                "        ho_account_no " +
                "      FROM fas_fund_trf_from_ho_master " +
                "      )a " +
                "    INNER JOIN " +
                "      (SELECT accounting_unit_id, " +
                "        accounting_for_office_id, " +
                "        transfer_to_office_id, " +
                "        voucher_no AS vouch_no, " +
                "        account_head_code, " +
                "        cashbook_year, " +
                "        cashbook_month, " +
                "        cheque_dd_no, " +
                "        cheque_dd_date, " +
                "        trim(TO_CHAR(amount::numeric,'99999999999999.99')) AS amount, " +
                "        particulars, " +
                "        fund_type, " +
                "        auto_status, " +
                "        sl_no, " +
                "        office_bank_id, " +
                "        (SELECT BANK_SHORT_NAME FROM FAS_MST_BANKS WHERE BANK_ID = office_bank_id " +
                "        ) AS bank_name , " +
                "        office_branch_id, " +
                "        office_account_no, " +
                "        cheque_or_dd, " +
                "        TRANSFERED_TO_HO_UNIT_ID " +
                "      FROM fas_fund_trf_from_ho_trn " +
                "      )b " +
                "    ON a.accounting_unit_id       =b.accounting_unit_id " +
                "    AND a.accounting_for_office_id=b.accounting_for_office_id " +
                "    AND a.voucher_no              =b.vouch_no " +
                "    AND a.cashbook_year           =b.cashbook_year " +
                "    AND a.cashbook_month          =b.cashbook_month " +
                "    WHERE a.cashbook_month        =  " +month+
                "    AND a.cashbook_year           =  " +year+
                "    AND b.transfer_to_office_id   =  " +cmbOffice_code+
                "    AND a.transfer_status         ='L' " +
                "    ) dd " +
                "  LEFT OUTER JOIN " +
                "    (SELECT SUBSTR(AC_HEAD_CODE::varchar, 1, 4) AS account_head_code, " +
                "      BANK_AC_NO, " +
                "      BANK_ID, " +
                "      BRANCH_ID, " +
                "      AC_OPERATIONAL_MODE_ID " +
                "    FROM FAS_OFFICE_BANK_AC_CURRENT " +
                "    WHERE STATUS          ='Y' " +
                "    AND ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
                "    AND MODULE_ID         ='MF009' " +
                "    AND cr_dr_type        ='DR' " +
                "    )e " +
                "  ON e.BANK_ID     =dd.office_BANK_ID " +
                "  AND e.BANK_AC_NO =dd.OFFICE_ACCOUNT_NO " +
                //"  AND e.BRANCH_ID  =dd.OFFICE_BRANCH_ID " +
                "  )fin " +
                "WHERE fin.AC_OPERATIONAL_MODE_ID LIKE '%OPR-%'" ;
                
                System.out.println(sql);

                ps = con.prepareStatement(sql);


                System.out.println("i am Here 1");
                rs = ps.executeQuery();

                int count = 0;
                while (rs.next()) {
                    String dat_of_trans = "";
                    String fund_type = "";
                    String ho_ref_date = "";
                    String cheque_dd_no = "";
                    String cheque_dd_date = "";
                    String particulars = "";
                    String ho_ref_no = "";
                    String autostatus = "";
                    int voucher_no = 0;
                    int sl_no = 0;
                    int account_head_code = 0;
                    int office_bank_id = 0;
                    int office_branch_id = 0;
                    String office_account_no = "";
                   // float total_amount = 0;
                    BigDecimal total_amount;
                    int ho_bank_id = 0;
                    int ho_branch_id = 0;
                    String ho_account_no = "";
                    String cheque_or_dd = "";
                    String bank_name = "";
                    String AC_OPERATIONAL_MODE_ID1="";
                  //  System.out.println("i am Here");

                    if (rs.getString("date_of_transfer") != null) {
                        // dat_of_trans=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("date_of_transfer"));
                        // System.out.println("date of transfer :"+rs.getDate("date_of_transfer"));
                        dat_of_trans = rs.getString("date_of_transfer");
                    } else {
                        dat_of_trans = "-";
                    }


                    if (rs.getString("fund_type") != null) {
                        fund_type = rs.getString("fund_type");
                       // System.out.println("the fund type is" + fund_type);
                    } else {
                        fund_type = "-";
                    }


                    if (rs.getString("horef_no") != null) {
                        ho_ref_no = rs.getString("horef_no");
                        ho_ref_no = ho_ref_no.replaceAll(" ", "");
                    } else
                        ho_ref_no = "-";
                   // System.out.println("the ho ref number is" + ho_ref_no);

                    if (ho_ref_no.equalsIgnoreCase(""))
                        ho_ref_no = "-";

                    account_head_code = rs.getInt("account_head_code");
                   // System.out.println("the account head code" +account_head_code);


                    office_bank_id = rs.getInt("office_bank_id");
                  //  System.out.println("the office bank id" + office_bank_id);

                    if (rs.getString("bank_name") != null)
                        bank_name = rs.getString("bank_name");
                    else
                        bank_name = "-";
                 //   System.out.println("the office bank name==>" + bank_name);


                    office_branch_id = rs.getInt("office_branch_id");
                //    System.out.println("the office branch id" +office_branch_id);


                    office_account_no = rs.getString("office_account_no");
                   // System.out.println("the office account no" + office_account_no);


                    total_amount = rs.getBigDecimal("total_amount");
                  //  System.out.println("the total amount" + total_amount);


                    ho_bank_id = rs.getInt("ho_bank_id");
                 //   System.out.println("the bank id is" + ho_bank_id);


                    ho_branch_id = rs.getInt("ho_branch_id");
                   // System.out.println("the branch id" + ho_branch_id);


                    ho_account_no = rs.getString("ho_account_no");
                   // System.out.println("the account no is" + ho_account_no);


                    System.out.println("the refno is" + ho_ref_no);
                    if (rs.getDate("ho_ref_date") != null) {
                        ho_ref_date =
                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("ho_ref_date"));
                        System.out.println("date of reference :" +rs.getDate("ho_ref_date"));
                    } else {
                        ho_ref_date = "-";
                    }


                    if (rs.getString("cheque_dd_no") != null) {
                        cheque_dd_no = rs.getString("cheque_dd_no");
                        System.out.println("the cheque dd number is" + cheque_dd_no);
                    } else {
                        cheque_dd_no = "-";
                    }


                    if (rs.getString("cheque_or_dd") != null) {
                        cheque_or_dd = rs.getString("cheque_or_dd");
                        System.out.println("the cheque_or_dd is" +cheque_or_dd);
                    } else {
                        cheque_or_dd = "-";
                    }


                    if (rs.getDate("cheque_dd_date") != null) {
                        cheque_dd_date =
                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("cheque_dd_date"));
                        System.out.println("date of transfer :" + rs.getDate("cheque_dd_date"));
                    } else {
                        cheque_dd_date = "-";
                    }


                    BigDecimal amount ;
                    amount = rs.getBigDecimal("amount");
                    
                    
                    if (rs.getString("auto_status") != null) {
                        autostatus = rs.getString("auto_status");
                        System.out.println("the auto_status is" + autostatus);
                    } else {
                        autostatus = "-";
                    }

                    if (rs.getString("particulars") != null) {
                        particulars = rs.getString("particulars");
                        System.out.println("the particulars is" + particulars);
                    } else {
                        particulars = "-";
                    }
                    //Lakshmi 8Nov13
                    if (rs.getString("AC_OPERATIONAL_MODE_ID") != null) {
                    	AC_OPERATIONAL_MODE_ID1 = rs.getString("AC_OPERATIONAL_MODE_ID");
                        System.out.println("the AC_OPERATIONAL_MODE_ID is" + AC_OPERATIONAL_MODE_ID1);
                    	}
                        else {
                        	AC_OPERATIONAL_MODE_ID1 = "-";
                        }
                   // AC_OPERATIONAL_MODE_ID
                    
                    System.out.println("amount is" + amount);

                    voucher_no = rs.getInt("voucher_no");
                    System.out.println("the voucher number is" + voucher_no);

                    sl_no = rs.getInt("sl_no");
                    System.out.println("the serial number is" + sl_no);
                    xml = xml + "<details>";
                    xml =
 xml + "<fund_type>" + fund_type + "</fund_type><dat_of_trans>" +
   dat_of_trans + "</dat_of_trans><ho_ref_no>" + ho_ref_no +
   "</ho_ref_no><ho_ref_date>" + ho_ref_date + "</ho_ref_date><cheque_dd_no>" +
   cheque_dd_no + "</cheque_dd_no><cheque_dd_date>" + cheque_dd_date +
   "</cheque_dd_date><amount>" + amount + "</amount><particulars>" +
   particulars + "</particulars><voucher_no>" + voucher_no +
   "</voucher_no><sl_no>" + sl_no + "</sl_no><account_head_code>" +
   account_head_code + "</account_head_code><office_bank_id>" +
   office_bank_id + "</office_bank_id><office_branch_id>" + office_branch_id +
   "</office_branch_id><office_account_no>" + office_account_no +
   "</office_account_no><total_amount>" + total_amount +
   "</total_amount><ho_bank_id>" + ho_bank_id + "</ho_bank_id><ho_branch_id>" +
   ho_branch_id + "</ho_branch_id><ho_account_no>" + ho_account_no +
   "</ho_account_no><cheque_or_dd>" + cheque_or_dd + "</cheque_or_dd><AC_OPERATIONAL_MODE_ID>" + AC_OPERATIONAL_MODE_ID1 + "</AC_OPERATIONAL_MODE_ID>";
                    
                    xml = xml + "</details>";
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
                System.out.println(e);
                e.printStackTrace();

                xml = xml + "<flag>failure</flag>";
            }
        }


        else if (strCommand.equalsIgnoreCase("LoadUnitWise_Office")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>" + strCommand + "</command>";


            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }


            try {
                ps =
  con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a," +
                       "COM_MST_OFFICES b where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID and a.ACCOUNTING_UNIT_ID=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                rs = ps.executeQuery();
                int cnt = 0;

                while (rs.next()) {
                    xml =
 xml + "<offid>" + rs.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
                    xml =
 xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
                    cnt++;
                }
                if (cnt != 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
        }


        /**
    * Search By Date
    */
        else if (strCommand.equalsIgnoreCase("searchByDate")) {
            /* Set Content Type */
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            /* Variables Declaration */
            Calendar c;

            /* xml Declaration */
            xml = "<response><command>searchByDate</command>";


            try {
                /* Variables Declaration */
                Date txtFrom_date = null;
                Date txtTo_date = null;
                java.util.Date d = null;
                String sd[] = null;
                int year = 0;
                int month = 0;

                /* Get From Date */
                try {
                    sd = request.getParameter("txtFrom_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtFrom_date = new Date(d.getTime());
                    System.out.println("from_date " + txtFrom_date);
                } catch (Exception e) {
                    System.out.println("Error Getting from Date -->" + e);
                }


                /* Get To Date */
                try {
                    sd = request.getParameter("txtTo_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtTo_date = new Date(d.getTime());
                    System.out.println("txtTo_date " + txtTo_date);
                } catch (Exception e) {
                    System.out.println("Error Getting from Date -->" + e);
                }

                /* Get Year */
                try {
                    year =
Integer.parseInt(request.getParameter("txtCB_Year"));
                    System.out.println("the year is" + year);
                } catch (Exception e) {
                    System.out.println("Error get Year -->" + e);
                }

                /* Get Month */
                try {
                    month =
Integer.parseInt(request.getParameter("txtCB_Month"));
                    System.out.println("the month is" + month);
                } catch (Exception e) {
                    System.out.println("Error Get Month --> " + e);
                }


                /* SQL Query */
                String sql =
                    "" + " select 																	\n" + "   fund_type,																\n" +
                    "   date_of_transfer,														\n" +
                    "   voucher_no,																\n" +
                    "   sl_no,																	\n" +
                    "   ( case when ho_ref_no is null then '-' else ho_ref_no end) as horef_no,	\n" +
                    "   ho_ref_date,   															\n" +
                    "   cheque_dd_no,															\n" +
                    "   cheque_dd_date,															\n" +
                    "   trim(to_char(amount::numeric,'99999999999999.99')) as amount,  	 																	\n" +
                    "   particulars,  															\n" +
                    "   SUBSTR(ACCOUNT_HEAD_CODE::varchar, 1, 4) as account_head_code,					\n" +
                    "   office_bank_id,															\n" +
                    "   office_branch_id,														\n" +
                    "   office_account_no,   													\n" +
                    "   total_amount,															\n" +
                    "   ho_bank_id,																\n" +
                    "   ho_branch_id,															\n" +
                    "   ho_account_no,															\n" +
                    "   cheque_or_dd,auto_status															\n" +
                    "from 																		\n" + "   (   \n" +
                    "       select\n" + "           accounting_unit_id,\n" +
                    "           accounting_for_office_id,\n" +
                    "           voucher_no,\n" +
                    "           date_of_transfer,\n" +
                    "           total_amount,     \n" +
                    "           cashbook_year,\n" +
                    "           cashbook_month,\n" +
                    "           transfer_status,\n" +
                    "           ho_ref_no,\n" +
                    "           ho_ref_date,   \n" +
                    "           ho_bank_id,\n" + "           ho_branch_id,\n" +
                    "           ho_account_no \n" + "       from \n" +
                    "           fas_fund_trf_from_ho_master      \n" +
                    "    )a   \n" + "  \n" + "    inner join   \n" + "  \n" +
                    "    (   \n" + "       select \n" +
                    "          accounting_unit_id,\n" +
                    "          accounting_for_office_id,\n" +
                    "          transfer_to_office_id,\n" +
                    "          voucher_no as vouch_no,\n" +
                    "          account_head_code,     \n" +
                    "          cashbook_year,\n" +
                    "          cashbook_month,\n" +
                    "          cheque_dd_no,\n" +
                    "          cheque_dd_date,\n" + "          amount,\n" +
                    "          particulars,\n" + "          fund_type,\n" +
                    "          auto_status,\n" + "          sl_no,   \n" +
                    "          office_bank_id,\n" +
                    "          office_branch_id,\n" +
                    "          office_account_no,\n" +
                    "          cheque_or_dd\n , TRANSFERED_TO_HO_UNIT_ID " +
                    "       from      \n" +
                    "          fas_fund_trf_from_ho_trn     \n" +
                    "    )b   \n" + "    \n" +
                    " on a.accounting_unit_id = b.accounting_unit_id \n" +
                    "and a.accounting_for_office_id = b.accounting_for_office_id     \n" +
                    "and a.voucher_no = b.vouch_no \n" +
                    "and a.cashbook_year = b.cashbook_year \n" +
                    "and a.cashbook_month = b.cashbook_month  \n" +
                    "where \n" + "    b.transfer_to_office_id = ? \n" +
                    "and a.date_of_transfer between ? and ? \n" +
                    "and a.transfer_status = 'L' \n" +
                    "and b.auto_status='Y'  \n" +
                    "and a.cashbook_month = ? \n" + "and a.cashbook_year = ? ";

                if (cmbOffice_code == 5000) {
                    sql =
 sql + "and b.TRANSFERED_TO_HO_UNIT_ID=" + cmbAcc_UnitCode;
                }

                ps = con.prepareStatement(sql);

                ps.setInt(1, cmbOffice_code);
                ps.setDate(2, txtFrom_date);
                ps.setDate(3, txtTo_date);
                ps.setInt(4, month);
                ps.setInt(5, year);
                rs = ps.executeQuery();

                int count = 0;
                while (rs.next()) {
                    String dat_of_trans = "";
                    String fund_type = "";
                    String ho_ref_date = "";
                    String cheque_dd_no = "";
                    String cheque_dd_date = "";
                    String particulars = "";
                    String ho_ref_no = "";
                    int voucher_no = 0;
                    int sl_no = 0;
                    int account_head_code = 0;
                    int office_bank_id = 0;
                    int office_branch_id = 0;
                    long office_account_no = 0;
                    //float total_amount = 0;
                     BigDecimal total_amount ;
                    int ho_bank_id = 0;
                    int ho_branch_id = 0;
                    int ho_account_no = 0;
                    String cheque_or_dd = "";
                    String autostatus = "";
                    ;

                    if (rs.getDate("date_of_transfer") != null) {
                        dat_of_trans =
                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("date_of_transfer"));
                        System.out.println("date of transfer :" +
                                           rs.getDate("date_of_transfer"));
                    } else {
                        dat_of_trans = "-";
                    }
                    if (rs.getString("fund_type") != null) {
                        fund_type = rs.getString("fund_type");
                        System.out.println("the fund type is" + fund_type);
                    } else {
                        fund_type = "-";
                    }

                    ho_ref_no = rs.getString("horef_no");
                    System.out.println("the ho ref number is" + ho_ref_no);

                    account_head_code = rs.getInt("account_head_code");
                    System.out.println("the account head code" +
                                       account_head_code);

                    office_bank_id = rs.getInt("office_bank_id");
                    System.out.println("the office bank id" + office_bank_id);

                    office_branch_id = rs.getInt("office_branch_id");
                    System.out.println("the office branch id" +
                                       office_branch_id);

                    office_account_no = rs.getLong("office_account_no");
                    System.out.println("the office account no" +
                                       office_account_no);

                    total_amount = rs.getBigDecimal("total_amount");
                    System.out.println("the total amount" + total_amount);

                    ho_bank_id = rs.getInt("ho_bank_id");
                    System.out.println("the bank id is" + ho_bank_id);

                    ho_branch_id = rs.getInt("ho_branch_id");
                    System.out.println("the branch id" + ho_branch_id);

                    ho_account_no = rs.getInt("ho_account_no");
                    System.out.println("the account no is" + ho_account_no);


                    System.out.println("the refno is" + ho_ref_no);
                    if (rs.getDate("ho_ref_date") != null) {
                        ho_ref_date =
                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("ho_ref_date"));
                        System.out.println("date of reference :" +
                                           rs.getDate("ho_ref_date"));
                    } else {
                        ho_ref_date = "-";
                    }

                    if (rs.getString("cheque_dd_no") != null) {
                        cheque_dd_no = rs.getString("cheque_dd_no");
                        System.out.println("the cheque dd number is" +
                                           cheque_dd_no);
                    } else {
                        cheque_dd_no = "-";
                    }

                    if (rs.getString("cheque_or_dd") != null) {
                        cheque_or_dd = rs.getString("cheque_or_dd");
                        System.out.println("the cheque_or_dd is" +
                                           cheque_or_dd);
                    } else {
                        cheque_or_dd = "-";
                    }


                    if (rs.getDate("cheque_dd_date") != null) {
                        cheque_dd_date =
                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("cheque_dd_date"));
                        System.out.println("date of transfer :" +
                                           rs.getDate("cheque_dd_date"));
                    } else {
                        cheque_dd_date = "-";
                    }

                    BigDecimal amount ;
                    amount = rs.getBigDecimal("amount");
                    
                    if (rs.getString("auto_status") != null) {
                        autostatus = rs.getString("auto_status");
                        System.out.println("the autostatus is" + autostatus);
                    } else {
                        autostatus = "-";
                    }
                    

                    if (rs.getString("particulars") != null) {
                        particulars = rs.getString("particulars");
                        System.out.println("the particulars is" + particulars);
                    } else {
                        fund_type = "";
                    }
                    System.out.println("amount is" + amount);

                    voucher_no = rs.getInt("voucher_no");
                    System.out.println("the voucher number is" + voucher_no);

                    sl_no = rs.getInt("sl_no");
                    System.out.println("the serial number is" + sl_no);
                    xml = xml + "<details>";
                    xml =
 xml + "<fund_type>" + fund_type + "</fund_type><dat_of_trans>" +
   dat_of_trans + "</dat_of_trans><ho_ref_no>" + ho_ref_no +
   "</ho_ref_no><ho_ref_date>" + ho_ref_date + "</ho_ref_date><cheque_dd_no>" +
   cheque_dd_no + "</cheque_dd_no><cheque_dd_date>" + cheque_dd_date +
   "</cheque_dd_date><amount>" + amount + "</amount><particulars>" +
   particulars + "</particulars><voucher_no>" + voucher_no +
   "</voucher_no><sl_no>" + sl_no + "</sl_no><account_head_code>" +
   account_head_code + "</account_head_code><office_bank_id>" +
   office_bank_id + "</office_bank_id><office_branch_id>" + office_branch_id +
   "</office_branch_id><office_account_no>" + office_account_no +
   "</office_account_no><total_amount>" + total_amount +
   "</total_amount><ho_bank_id>" + ho_bank_id + "</ho_bank_id><ho_branch_id>" +
   ho_branch_id + "</ho_branch_id><ho_account_no>" + ho_account_no +
   "</ho_account_no><cheque_or_dd>" + cheque_or_dd + "</cheque_or_dd>";
                    xml = xml + "</details>";
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
                System.out.println("catch..HERE.in load valuesssssssss." + e);
                xml = xml + "<flag>failure</flag>";
            }
        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        // TODO Auto-generated method stub
    }

}
