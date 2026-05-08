package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Auto_Fund_Receipt_List_atHO
 */
public class Auto_Fund_Receipt_List_atHO extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Auto_Fund_Receipt_List_atHO() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {

        /** Session Checking */
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


        /** Variables Declaration */
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        ResultSet rs2 = null;
        ResultSet rs3 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;


        /** Database Connection */
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


        /** Set Content Type */
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();


        /** Variables Declaration */
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;
        String remit_type = "";
        String col_unspent = "";
        String xml = "";
        String strCommand = "";
        String displayingOrder = "";
        int txtRegionId = 0;
        int txtBankAccountNo = 0;


        /** Get Command Parameter */
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


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


        /** Account Type */
        try {
            remit_type = request.getParameter("remit_type");
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("remit_type " + remit_type);


        if (remit_type.equalsIgnoreCase("C,U")) {
            remit_type = "C','U";
            col_unspent = "COL','OPR";
        } else if (remit_type.equalsIgnoreCase("U")) {
            col_unspent = "OPR";
        } else if (remit_type.equalsIgnoreCase("C")) {
            col_unspent = "COL";
        }


        /** Get Displaying Order */
        try {
            displayingOrder = request.getParameter("displayingOrder");
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("displayingOrder " + displayingOrder);

        /** Get Region Office ID */
        try {
            txtRegionId =
                    Integer.parseInt(request.getParameter("txtRegionId"));
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtRegionId " + txtRegionId);

        /** Get Bank Account Number */
        try {
            txtBankAccountNo =
                    Integer.parseInt(request.getParameter("txtBankAccountNo"));
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtBankAccountNo " + txtBankAccountNo);


        if (strCommand.equalsIgnoreCase("searchByMonth") ||
            strCommand.equalsIgnoreCase("searchByDate")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            xml = "<response><command>searchByMonth</command>";

            try {
                /* Get Cashbook Year */
                int year =
                    Integer.parseInt(request.getParameter("txtCB_Year"));
                System.out.println("the year is" + year);

                /* Get Cashbook Month */
                int month =
                    Integer.parseInt(request.getParameter("txtCB_Month"));
                System.out.println("the month is" + month);

                /** Date Declaration */
                Date txtFrom_date = null;
                Date txtTo_date = null;

                /** Get From and To Date */

                try {

                    String[] sd =
                        request.getParameter("txtFrom_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    txtFrom_date = new Date(d.getTime());
                    System.out.println("from_date " + txtFrom_date);

                    sd = request.getParameter("txtTo_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtTo_date = new Date(d.getTime());
                    System.out.println("txtTo_date " + txtTo_date);
                } catch (Exception e) {
                    System.out.println("Could not get the Record --> " + e);
                }

                String sql =
                    //     "                                                            (  SELECT     \n" +
                    //   "                                                                curr.AC_HEAD_CODE   \n" +
                    //     "                                                              FROM FAS_OFFICE_BANK_AC_CURRENT curr                       \n" +
                    //    "                                                              WHERE curr.STATUS              ='Y'                        \n" +
                    //     "                                                              AND curr.ACCOUNTING_UNIT_ID    =5                          \n" +
                    //     "                                                              AND curr.MODULE_ID             ='MF009'                    \n" +
                    //     "                                                              AND curr.CR_DR_TYPE            ='DR'                       \n" +
                    //      "                                                              AND curr.SL_NO                 =1                          \n" +
                    //      "                                                              AND curr.AC_OPERATIONAL_MODE_ID in ( '"+col_unspent+"' )               \n" +
                    //    "                                                              and curr.BANK_AC_NO = ho_account_no                        \n" +
                    //     "                                                              and curr.BANK_ID = ho_bank_id                              \n" +
                    /*    "                                                        left outer join             \n" +
                           "                                                        \n" +
                           "                                                         (  \n" +
                           "                                                          SELECT   \n" +
                           "                                                            AC_HEAD_CODE as cr_account_head_code ,  \n" +
                           "                                                            ACCOUNTING_UNIT_ID as unit_id, \n" +
                           "                                                            BANK_AC_NO,\n" +
                           "                                                            BANK_ID\n" +
                           "                                                          FROM FAS_OFFICE_BANK_AC_CURRENT                            \n" +
                           "                                                          WHERE STATUS              ='Y'                             \n" +
                           "                                                          AND MODULE_ID             ='MF009'                         \n" +
                           "                                                          AND CR_DR_TYPE            ='CR'                            \n" +
                           "                                                          AND SL_NO                 =1                               \n" +
                           "                                                          AND AC_OPERATIONAL_MODE_ID in ( '"+col_unspent+"' )        \n" +
                           "                                                      \n" +
                           "                                                         ) b \n" +
                           "                                                         on \n" +
                           "                                                           a.accounting_unit_id = b.unit_id \n" +
                           "                                                           and a.office_account_no = b.BANK_AC_NO\n" +
                           "                                                           and a.office_bank_id = b.BANK_ID\n" + */
                    "                                  select  \n" +
                    "                                   *  \n" +
                    "                                 from  \n" +
                    "                                 ( \n" +
                    "                                             SELECT *   \n" +
                    "                                             FROM   \n" +
                    "                                             (  \n" +
                    "                                             \n" + "\n" +
                    "                                                     \n" +
                    "                                                   select\n" +
                    "                                                   accounting_unit_id,   \n" +
                    "                                                   accounting_for_office_id,   \n" +
                    "                                                   remittance_type,   \n" +
                    "                                                   date_of_transfer,   \n" +
                    "                                                   voucher_no, \n" +
                    "                                                   cheque_or_dd,   \n" +
                    "                                                   cashbook_month,   \n" +
                    "                                                   cashbook_year,   \n" +
                    "                                                   \n" +
                    "                                                   cr_account_head_code,           \n" +
                    "                                                   ( \n" +
                    "                                                    select account_head_desc from com_mst_account_heads\n" +
                    "                                                    where account_head_code= cr_account_head_code\n" +
                    "                                                    )as cr_account_head_code_desc, \n" +
                    "                                                    \n" +
                    "                                                   office_account_no,   \n" +
                    "                                                   office_bank_id,   \n" +
                    "                                                   office_branch_id,    \n" +
                    "                                                   \n" +
                    "                                                   dr_account_head_code,\n" +
                    "                                                   \n" +
                    "                                                   ( \n" +
                    "                                                    select account_head_desc from com_mst_account_heads\n" +
                    "                                                    where account_head_code= dr_account_head_code\n" +
                    "                                                   )as dr_account_head_code_desc, \n" +
                    "                                                    \n" +
                    "                                                   \n" +
                    "                                                   ho_account_no, ho_bank_id,                                                                     \n" +
                    "                                                   ho_account_no_desc,       \n" +
                    "                                                   total_amount,auto_status          \n" +
                    "                                                    \n" +
                    "                                                   from \n" +
                    "                                                   (\n" +
                    "                                                        select \n" +
                    "                                                        * \n" +
                    "                                                        from \n" +
                    "                                                        (               \n" +
                    "                                                           SELECT    \n" +
                    "                                                             accounting_unit_id,   \n" +
                    "                                                             accounting_for_office_id,   \n" +
                    "                                                             remittance_type,   \n" +
                    "                                                             to_char(date_of_transfer,'dd/mm/yyyy') as date_of_transfer ,   \n" +
                    "                                                             voucher_no, \n" +
                    "                                                             cheque_or_dd,   \n" +
                    "                                                             cashbook_month,   \n" +
                    "                                                             cashbook_year,                    \n" +
                    "                                                       \n" +
                    "                                                             office_account_no,   \n" +
                    "                                                             office_bank_id,   \n" +
                    "                                                             office_branch_id,ho_bank_id,                  \n" +
                    "                                                             \n" +
                    "                                                           -- HO Side --    \n" +
                    "                                                           \n" +
                    "                                                            dr_account_head_code, cr_account_head_code,                                 \n" +
                    "                                                            \n" +
                    "                                                         \n" +
                    "                                                             ho_account_no,                                                                      \n" +
                    "                                                             ho_account_no ||'/'|| ho_bank_id ||'/' || ho_branch_id as ho_account_no_desc,       \n" +
                    "                                                             trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount,auto_status                   \n" +
                    "                                                              \n" +
                    "                                                           FROM fas_fund_trf_from_office                        \n" +
                    "                                                           WHERE transfer_status = 'L'                          \n" +
                    "                                                           AND remittance_type  IN ('" +
                    remit_type + "')                        \n" +
                   // "                                                           AND auto_status      IS NULL                         \n" +
                    "                                                                                                                \n" +
                    "                                                           \n" +
                    "                                                           \n" +
                    "                                                        ) a \n" +
                    "                                                        \n" +
                    "                                                   )      \n" +
                    "            \n" + "          \n" +
                    "                                           )a   \n" +
                    "                                            \n" +
                    "                                           LEFT OUTER JOIN    \n" +
                    "                                           (   \n" +
                    "                                             SELECT    \n" +
                    "                                               accounting_unit_id as u_id,                                           \n" +
                    "                                               accounting_unit_name   \n" +
                    "                                             FROM fas_mst_acct_units   \n" +
                    "                                           )b   \n" +
                    "                                           on    \n" +
                    "                                           a.accounting_unit_id = b.u_id    \n" +
                    "                                ) ";


                /** Search By Date */
                if (strCommand.equalsIgnoreCase("searchByDate")) {
                    String search_by_date =
                        " where date_of_transfer between ? and ?  ";
                    sql = sql + search_by_date;
                } else {
                    String search_by_month =
                        " where cashbook_month= " + month +
                        " and cashbook_year= " + year + " ";
                    sql = sql + search_by_month;
                }

                /** SQL for displaying Region wise */
                String sql_Region_Wise = " ";

                if (txtRegionId == 5000) {
                    sql_Region_Wise =
                            " and accounting_for_office_id  in (5000) ";
                } else {
                    sql_Region_Wise =
                            "					                  " + " and accounting_for_office_id  in		                	\n" +
                            "   (							         	\n" +
                            "     select office_id						        \n" +
                            "      from COM_MST_ALL_OFFICES_VIEW			                \n" +
                            "      where region_office_id = " + txtRegionId +
                            ")                     \n" +
                            "									\n";
                }


                /** SQL for displaying Bank wise */
                String sql_Bank_Wise =
                    "and ho_account_no = " + txtBankAccountNo;


                if (displayingOrder.equalsIgnoreCase("RW")) {
                    if (txtRegionId == 101) {
                        /** Display All Units */
                        sql = sql;
                    } else {
                        /** Display Particular Region only */
                        sql = sql + sql_Region_Wise;
                    }
                }


                /** Displaying in Bank wise */
                if (displayingOrder.equalsIgnoreCase("BW")) {
                    sql = sql + sql_Bank_Wise;
                }


                /** Display Order */
                sql =
 sql + "order by accounting_unit_name , date_of_transfer, voucher_no    ";

                System.out.println(sql);


                ps = con.prepareStatement(sql);

                /** Search by Date */
                if (strCommand.equalsIgnoreCase("searchByDate")) {
                    ps.setDate(1, txtFrom_date);
                    ps.setDate(2, txtTo_date);
                }

                /** Query Execution */
                rs = ps.executeQuery();

                System.out.println("sql-->" + sql);


                /** Get All Banking Section Account Numbers */
                String bank_acc_num =
                    "" + "SELECT\n" + "  b.BANK_AC_NO || '/' || b.bank_id || '/' || b.branch_id as ho_bank_ac_no,  \n" +
                    "  c.BANK_SHORT_NAME\n" + "  || '-'\n" +
                    "  || b.BANK_AC_TYPE_ID\n" + "  ||'-'\n" +
                    "  || b.BANK_AC_NO AS bankShow\n" +
                    "FROM FAS_OFFICE_BANK_AC_CURRENT b,\n" +
                    "  FAS_MST_BANKS c\n" +
                    "WHERE b.BANK_ID         =c.BANK_ID\n" +
                    "AND b.ACCOUNTING_UNIT_ID=5 \n" +
                    "AND MODULE_ID           ='MF009'\n" +
                    "AND CR_DR_TYPE          ='DR' \n" +
                    "AND b.STATUS            ='Y'";


                int count = 0;
                while (rs.next()) {
                    xml = xml + "<details>";
                    System.out.println("1");
                    xml =
 xml + "<accounting_unit_id>" + rs.getString("accounting_unit_id") +
   "</accounting_unit_id>";
                    //  System.out.println("2");
                    xml =
 xml + "<accounting_for_office_id>" + rs.getString("accounting_for_office_id") +
   "</accounting_for_office_id>";
                    // System.out.println("3");
                    xml =
 xml + "<remittance_type>" + rs.getString("remittance_type") +
   "</remittance_type>";
                    //  System.out.println("4");
                    xml =
 xml + "<date_of_transfer>" + rs.getString("date_of_transfer") +
   "</date_of_transfer>";
                    //  System.out.println("5");
                    xml =
 xml + "<voucher_no>" + rs.getString("voucher_no") + "</voucher_no>";
                    // System.out.println("6");

                    xml =
 xml + "<cheque_or_dd>" + rs.getString("cheque_or_dd") + "</cheque_or_dd>";
                    //  System.out.println("7");

                    xml =
 xml + "<cashbook_month>" + rs.getString("cashbook_month") +
   "</cashbook_month>";
                    //   System.out.println("8");
                    xml =
 xml + "<cashbook_year>" + rs.getString("cashbook_year") + "</cashbook_year>";
 //added on 18/08/2011
  String auto_status="";
                   if(rs.getString("auto_status")!=null)
                   {
                       auto_status=  rs.getString("auto_status");        
                   }
                   else
                   {
                       auto_status= "-";      
                   
                   }
                   
                   
                    xml =
                    xml + "<autostatus>" + auto_status + "</autostatus>";
                    //  System.out.println("9");
                    /*      xml=xml+"<cr_account_head_code>"+rs.getString("cr_account_head_code")+"</cr_account_head_code>";
                               System.out.println("10");*/


                    String testsqlsec =
                        "  SELECT AC_HEAD_CODE as  cr_account_head_code , \n" +
                        "  ACCOUNTING_UNIT_ID as unit_id, \n" +
                        "  BANK_AC_NO,\n" + "  BANK_ID \n" +
                        " FROM FAS_OFFICE_BANK_AC_CURRENT  \n" +
                        " WHERE STATUS              ='Y' \n" +
                        " AND MODULE_ID             ='MF009' \n" +
                        " AND CR_DR_TYPE            ='CR'   \n" +
                        " AND SL_NO                 =1    \n" +
                        " AND AC_OPERATIONAL_MODE_ID in ( '" + col_unspent +
                        "' ) and ACCOUNTING_UNIT_ID=" +
                        rs.getString("accounting_unit_id") +
                        " and BANK_AC_NO=" +
                        rs.getString("office_account_no") + " and BANK_ID=" +
                        rs.getString("office_bank_id") + " ";

                    System.out.println("office_account_no========>"+rs.getString("office_account_no"));
                    System.out.println("office_bank_id========>"+rs.getString("office_bank_id"));
                    System.out.println("accounting_unit_id========>"+rs.getString("accounting_unit_id"));
                    

                    ps3 = con.prepareStatement(testsqlsec);
                    rs3 = ps3.executeQuery();
                    if (rs3.next()) {
                        xml =
 xml + "<cr_account_head_code>" + rs3.getString("cr_account_head_code") +
   "</cr_account_head_code>";
                        // System.out.println("Success 123"+rs3.getString("cr_account_head_code"));
                    } else {
                        xml =
 xml + "<cr_account_head_code>--</cr_account_head_code>";

                    }


                    xml =
 xml + "<cr_account_head_code_desc>" + rs.getString("cr_account_head_code_desc") +
   "</cr_account_head_code_desc>";
                    // System.out.println("12");

                    xml =
 xml + "<ho_account_no>" + rs.getString("ho_account_no") + "</ho_account_no>";
                    //   System.out.println("13");
                    xml =
 xml + "<ho_account_no_desc>" + rs.getString("ho_account_no_desc") +
   "</ho_account_no_desc>";
                    //  System.out.println("14");
                    xml =
 xml + "<total_amount>" + rs.getString("total_amount") + "</total_amount>";

                    
                    System.out.println("office_account_no========>"+rs.getString("office_account_no"));
                    System.out.println("office_bank_id========>"+rs.getString("office_bank_id"));
                    System.out.println("accounting_unit_id========>"+rs.getString("accounting_unit_id"));
                    
                    
                    String testsql =
                        " SELECT curr.AC_HEAD_CODE as dr_ACCOUNT_HEAD_CODE ,BANK_AC_NO,BANK_ID,BRANCH_ID \n" +
                        " FROM FAS_OFFICE_BANK_AC_CURRENT curr  \n" +
                        " WHERE curr.STATUS              ='Y'  \n" +
                        " AND curr.ACCOUNTING_UNIT_ID    =5   \n" +
                        " AND curr.MODULE_ID             ='MF009' \n" +
                        " AND curr.CR_DR_TYPE            ='DR'  \n" +
                        " AND curr.SL_NO                 =1     \n" +
                        " AND curr.AC_OPERATIONAL_MODE_ID in ( '" +
                        col_unspent + "'  ) and curr.BANK_AC_NO=" +
                        rs.getString("ho_account_no") + " and BANK_ID=" +
                        rs.getInt("ho_bank_id") + " ";


                    if (rs.getString("remittance_type").equalsIgnoreCase("C")) {
                        testsql =
                                testsql + " and curr.ac_head_code like '%3'\n";
                    } else {
                        testsql =
                                testsql + " and curr.ac_head_code like '%2'\n";
                    }
                    
                    System.out.println("testsql--->"+testsql);

                    ps2 = con.prepareStatement(testsql);
                    rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        xml =
 xml + "<dr_account_head_code>" + rs2.getString("dr_ACCOUNT_HEAD_CODE") +
   "</dr_account_head_code>";
                        //System.out.println("Success"+rs2.getString("dr_ACCOUNT_HEAD_CODE"));
                    } else {
                        xml =
 xml + "<dr_account_head_code>--</dr_account_head_code>";
                        System.out.println("and curr.BANK_AC_NO=" +
                                           rs.getString("ho_account_no") +
                                           " and BANK_ID=" +
                                           rs.getInt("ho_bank_id"));
                    }


                    //    System.out.println("15");
                    /*        xml=xml+"<dr_account_head_code>"+rs.getString("dr_account_head_code")+"</dr_account_head_code>";
                               System.out.println("16");*/
                    xml =
 xml + "<dr_account_head_code_desc>" + rs.getString("dr_account_head_code_desc") +
   "</dr_account_head_code_desc>";
                    //  System.out.println("17");


                    xml =
 xml + "<office_account_no>" + rs.getString("office_account_no") +
   "</office_account_no>";
                    //   System.out.println("18");
                    xml =
 xml + "<accounting_unit_name>" + rs.getString("accounting_unit_name") +
   "</accounting_unit_name>";
                    //   System.out.println("19");
                    xml = xml + "</details>";


                    count++;


                }

                ps2 = con.prepareStatement(bank_acc_num);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    xml = xml + "<bank_details>";
                    xml =
 xml + "<ho_bank_ac_no>" + rs2.getString("ho_bank_ac_no") + "</ho_bank_ac_no>";
                    xml =
 xml + "<bankShow>" + rs2.getString("bankShow") + "</bankShow>";
                    xml = xml + "</bank_details>";

                }


                if (count == 0)
                    xml = xml + "<flag>NoRecords</flag>";
                else
                    xml = xml + "<flag>success</flag>";

                ps.close();
                rs.close();

            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Error-->" + e);
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
