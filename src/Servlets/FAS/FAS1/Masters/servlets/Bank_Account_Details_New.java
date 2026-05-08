package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Bank_Account_Details_New extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        /** Session Checking */

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


        /** Variables Declaration */
        Connection con = null;
        ResultSet rs = null, rs2 = null;
        PreparedStatement ps = null;
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";

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

        int txtBankId = 0;
        int cmbAcc_UnitCode = 0;
        int txtBranchId = 0;
        String txtOperation_mode = "";
        String txtBankAcc_type = "";
        long txtBankAccountNo = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;


        /** Get Accounting Unit ID */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbAcc_UnitCode ");
        }

        /** Get Cashbook Year */
        String CashBook_Year = request.getParameter("txtCB_Year");

        /** Get Cashbook Month */
        String CashBook_Month = request.getParameter("txtCB_Month");

        long l = System.currentTimeMillis();


        /** Convert String to Interger -- Cashbook Year */
        try {
            CashBookYear = Integer.parseInt(CashBook_Year);
        } catch (Exception e) {
            System.out.println("Exception in Year:" + e);
            CashBookYear = 0;
        }

        /** Convert String to Interger -- Cashbook Month */
        try {
            CashBookMonth = Integer.parseInt(CashBook_Month);
        } catch (Exception e) {
            System.out.println("Exception in Month:" + e);
            CashBookMonth = 0;
        }

        /** Get Bank Id */
        try {
            txtBankId = Integer.parseInt(request.getParameter("txtBankId"));
        } catch (Exception e) {
            System.out.println("Exception to catch bank id ");
        }


        /** Get Branch ID */
        try {
            txtBranchId =
                    Integer.parseInt(request.getParameter("txtBranchId"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtBranchId ");
        }


        /** Get Operational Mode */
        txtOperation_mode = request.getParameter("txtOperation_mode");


        /** Get Bank Account Type */
        txtBankAcc_type = request.getParameter("txtBankAcc_type");


        /** Get Bank Account Number */
        try {
            txtBankAccountNo =
                    Long.parseLong(request.getParameter("txtBankAccountNo"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtBankAccountNo ");
        }


        int cashbookmonth1 = 0;
        cashbookmonth1 = CashBookMonth - 1;

        /** Load Bank Details */

        if (strCommand.equalsIgnoreCase("loadbankdeatils")) {
            /** Set Content Type */
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            /** XML Declaration */
            String xml = "";
            xml = "<response><command>loadbankdeatils</command>";

            /** Variables Declaration */
            int bani_id_temp = 0;
            String operation_mode_temp = "";
            String dispdate1 = "";
            int ACCHEAD = 0, INIT_AMT = 0;


            try {
            	String qry="         " + "select                                     \n" +
                "  distinct bb.BANK_ID,                     \n" +
                "  bb.BRANCH_ID,                            \n" +
                "  bk.BANK_NAME,\n" +
                "  br.BRANCH_NAME ||'-'||coalesce ( br.CITY_TOWN_NAME,'') AS BRANCH_CITY,\n" +
                "  bb.BANK_AC_TYPE_ID,\n" +
                "  bb.AC_OPERATIONAL_MODE_ID,\n" + "  bb.REMARKS,\n" +
                "  bb.AC_OPENING_DATE,\n" +
                "  bb.INITIAL_DEPOSIT_AMT,\n" +
                "  bb.OPENING_BALANCE,\n" + "  bb.BALANCE_DATE,\n" +
                "  t.ACCOUNT_TYPE,\n" + "  m.AC_OPERATIONAL_MODE,\n" +
                "  N.AC_HEAD_CODE\n" + "from \n" +
                "  FAS_MST_BANK_BALANCE bb,\n" +
                "  FAS_MST_BANKS bk,\n" +
                "  FAS_MST_BANK_BRANCHES br,\n" +
                "  FAS_MST_BANK_AC_TYPES t,\n" +
                "  FAS_MST_AC_OPER_MODES m,\n" +
                "  fas_office_bank_ac_current N \n" + "where \n" +
                "    bb.ACCOUNTING_UNIT_ID=?\n" +
                "and bb.BANK_AC_NO=? \n" +
                "and t.ACCOUNT_TYPE_ID=bb.BANK_AC_TYPE_ID \n" +
                "and bb.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID \n" +
                "and bb.BANK_ID=br.BANK_ID \n" +
                "and bb.BRANCH_ID=br.BRANCH_ID\n" +
                "and bb.BANK_ID=bk.BANK_ID \n" +
                "AND N.ACCOUNTING_UNIT_ID=? \n" +
                "AND bb.bank_id=N.bank_id \n" +
                "AND bb.branch_id=N.branch_id \n" +
                "AND bb.BANK_AC_TYPE_ID= N.BANK_AC_TYPE_ID \n" +
                "and bb.BANK_AC_NO=? \n" +
                "and bk.bank_id=N.bank_id\n" +
                "";
                //   "and m.AC_OPERATIONAL_MODE_ID in ('COL','OPR')");
            	System.out.println("Bank qry >>> "+qry);
                ps =
  con.prepareStatement(qry);
              


                ps.setInt(1, cmbAcc_UnitCode);
                ps.setLong(2, txtBankAccountNo);
                ps.setInt(3, cmbAcc_UnitCode);
                ps.setLong(4, txtBankAccountNo);

                rs = ps.executeQuery();

                while (rs.next()) {
                    try {
                        if (rs.getDate("AC_OPENING_DATE") == null)
                            dispdate1 = "";
                        else {
                            java.sql.Date dd = rs.getDate("AC_OPENING_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            dispdate1 = sdf.format(dd);
                            System.out.println("date1 is" + dispdate1);
                        }


                        if (rs.getInt("AC_HEAD_CODE") == 0) {
                            ACCHEAD = 0;
                        } else {
                            ACCHEAD = rs.getInt("AC_HEAD_CODE");
                        }
                        System.out.println(rs.getInt("AC_HEAD_CODE"));
                        xml =
 xml + "<ACCHEAD>" + rs.getInt("AC_HEAD_CODE") + "</ACCHEAD>";


                        if (rs.getInt("INITIAL_DEPOSIT_AMT") == 0) {
                            INIT_AMT = 0;
                        } else {
                            INIT_AMT = rs.getInt("INITIAL_DEPOSIT_AMT");
                        }
                        System.out.println(rs.getInt("INITIAL_DEPOSIT_AMT"));
                        xml =
 xml + "<INIT_AMT>" + rs.getInt("INITIAL_DEPOSIT_AMT") + "</INIT_AMT>";
                    } catch (Exception e) {
                        System.out.println("Error in getting date values");
                    }


                    xml = xml + "<flag>success</flag>";

                    bani_id_temp = rs.getInt("BANK_ID");
                    operation_mode_temp =
                            rs.getString("AC_OPERATIONAL_MODE_ID");


                    xml =
 xml + "<BANK_ID>" + rs.getInt("BANK_ID") + "</BANK_ID>";
                    xml =
 xml + "<BANK_NAME>" + rs.getString("BANK_NAME") + "</BANK_NAME>";
                    xml =
 xml + "<BRANCH_ID>" + rs.getInt("BRANCH_ID") + "</BRANCH_ID>";
                    xml =
 xml + "<BRANCH_CITY>" + rs.getString("BRANCH_CITY") + "</BRANCH_CITY>";
                    xml =
 xml + "<BANK_AC_TYPE_ID>" + rs.getString("BANK_AC_TYPE_ID") +
   "</BANK_AC_TYPE_ID>";
                    xml =
 xml + "<ACCOUNT_TYPE>" + rs.getString("ACCOUNT_TYPE") + "</ACCOUNT_TYPE>";
                    xml =
 xml + "<AC_OPERATIONAL_MODE_ID>" + rs.getString("AC_OPERATIONAL_MODE_ID") +
   "</AC_OPERATIONAL_MODE_ID>";
                    xml =
 xml + "<AC_OPERATIONAL_MODE>" + rs.getString("AC_OPERATIONAL_MODE") +
   "</AC_OPERATIONAL_MODE>";
System.out.println("A/C opening Date ::::::"+dispdate1);
                    if (dispdate1 == null || dispdate1.equalsIgnoreCase(""))
                    {
                    	dispdate1 = "-";
                    }
                    xml = xml + "<OPEN_DATE>" + dispdate1 + "</OPEN_DATE>";
                    xml = xml + "<INIT_AMT>" + INIT_AMT + "</INIT_AMT>";
                    xml =
 xml + "<ACCHEAD>" + rs.getInt("AC_HEAD_CODE") + "</ACCHEAD>";
                    xml =
 xml + "<REMARK>" + rs.getString("REMARKS") + "</REMARK>";
                }

                xml = xml + "<flag>failure</flag>";
                ps.close();
                rs.close();


            } catch (Exception e) {
                System.out.println("Finding Branch failed due to exception" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    }
}
