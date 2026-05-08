package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import java.lang.String;

import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

public class Load_Bank_Details_for_FR_ByHO extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        /**
        * Session Cehcking
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
        Statement st = null;
        String xml = "";
        String strCommand = "";
        int cmbAcc_UnitCode = 0;
        String AC_Head_Sub = "";
        String AC_Head_Sub2 = "";

        String BANK_ID = "";
        String BRANCH_ID = "";
        String BANK_AC_NO = "";
        String AC_HEAD_CODE = "";
        String bk_br_city = "";
        String unspent_OR_col = "";

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
              * Get Paramter Values
              */
        try {
            /** Get Command Parameter */
            strCommand = request.getParameter("Command");

            /**Get Accounting Unit ID */
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));

            /** Get Account Head code Middle Value */
            AC_Head_Sub = request.getParameter("AC_Head_Sub");

            System.out.println("AC_Head_Sub" + AC_Head_Sub);
            System.out.println("assign..here command..." + strCommand);

            /**Get unspent OR collection type */
            unspent_OR_col = request.getParameter("unspent_OR_col");
            System.out.println("unspent_OR_col----><" + unspent_OR_col);

        } catch (Exception e) {
            System.out.println("Can't Retrieve Values " + e);
        }


        /**
             *  Load Debit Account Head Code Details
             */


        /**
             * SQL Query Execution
             */

        xml = "<response><command>LoadBankDetails</command>";

        try {
            st = con.createStatement();
            System.out.println("before exe");


            if (unspent_OR_col.equalsIgnoreCase("COL")) {
                AC_Head_Sub2 = "3";
            } else if (unspent_OR_col.equalsIgnoreCase("OPR")) {
                AC_Head_Sub2 = "2";
            }


            String sql_dr = "";
            sql_dr =
                    " select                     \n" + "       curr.BANK_ID,        \n" +
                    "       curr.BRANCH_ID,      \n" +
                    "       curr.BANK_AC_NO,     \n" +
                    "       curr.AC_HEAD_CODE,   \n" +
                    "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || br.CITY_TOWN_NAME as bk_br_city       \n" +
                    "                            \n" +
                    "from                        \n" +
                    "       FAS_OFFICE_BANK_AC_CURRENT curr,         \n" +
                    "       FAS_MST_BANK_BRANCHES br ,   \n" +
                    "       FAS_MST_BANKS bk             \n" +
                    "                                    \n" +
                    "where                               \n" +
                    "        curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=" +
                    cmbAcc_UnitCode +
                    "        and                             \n" +
                    "        curr.MODULE_ID='MF009' and      \n" +
                    "        curr.CR_DR_TYPE='DR'   and      \n" +
                    "        curr.BANK_ID=br.BANK_ID and     \n" +
                    "        curr.BRANCH_ID=br.BRANCH_ID and \n" +
                    "        br.BANK_ID=bk.BANK_ID and       \n" +
                    "        curr.AC_OPERATIONAL_MODE_ID='" + unspent_OR_col +
                    "' and   \n" + "        curr.ac_head_code like '__" +
                    AC_Head_Sub + "_" + AC_Head_Sub2 + "'  and curr.sl_no=1 ";

            String sql_cr = "";
            sql_cr =
                    " select                     \n" + "       curr.BANK_ID,        \n" +
                    "       curr.BRANCH_ID,      \n" +
                    "       curr.BANK_AC_NO,     \n" +
                    "       curr.AC_HEAD_CODE,   \n" +
                    "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || br.CITY_TOWN_NAME as bk_br_city       \n" +
                    "                            \n" +
                    "from                        \n" +
                    "       FAS_OFFICE_BANK_AC_CURRENT curr,         \n" +
                    "       FAS_MST_BANK_BRANCHES br ,   \n" +
                    "       FAS_MST_BANKS bk             \n" +
                    "                                    \n" +
                    "where                               \n" +
                    "        curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID in ( select accounting_unit_id from fas_mst_acct_units where accounting_unit_office_id =" +
                    cmbAcc_UnitCode + ")" +
                    "        and                             \n" +
                    "        curr.MODULE_ID='MF009' and      \n" +
                    "        curr.CR_DR_TYPE='CR'   and      \n" +
                    "        curr.BANK_ID=br.BANK_ID and     \n" +
                    "        curr.BRANCH_ID=br.BRANCH_ID and \n" +
                    "        br.BANK_ID=bk.BANK_ID and       \n" +
                    "        curr.AC_OPERATIONAL_MODE_ID='" + unspent_OR_col +
                    "' and   \n" + "        curr.ac_head_code like '__" +
                    AC_Head_Sub + "__'   ";


            String CR_DR_Type = null;
            CR_DR_Type = request.getParameter("CR_DR_Type");

            if (CR_DR_Type.equalsIgnoreCase("Credit_Load")) {
                System.out.println(sql_cr);
                rs = st.executeQuery(sql_cr);
            } else {
                System.out.println(sql_dr);
                rs = st.executeQuery(sql_dr);
            }

            System.out.println(rs);
            System.out.println("after exe");

            int cnt = 0;

            while (rs.next()) {


                BANK_ID = rs.getString("BANK_ID");
                BRANCH_ID = rs.getString("BRANCH_ID");
                BANK_AC_NO = rs.getString("BANK_AC_NO");
                AC_HEAD_CODE = rs.getString("AC_HEAD_CODE");
                bk_br_city = rs.getString("bk_br_city");

                xml = xml + "<BANK_ID>" + BANK_ID + "</BANK_ID>";
                xml = xml + "<BRANCH_ID>" + BRANCH_ID + "</BRANCH_ID>";
                xml = xml + "<BANK_AC_NO>" + BANK_AC_NO + "</BANK_AC_NO>";
                xml =
 xml + "<AC_HEAD_CODE>" + AC_HEAD_CODE + "</AC_HEAD_CODE>";
                xml = xml + "<bk_br_city>" + bk_br_city + "</bk_br_city>";
                cnt++;

            }

            if (cnt == 0) {
                xml = xml + "<flag>InvalidBank</flag>";
            } else {
                xml = xml + "<flag>success</flag>";
            }

        } catch (Exception e) {
            System.out.println("Can't Execute SQL Query " + e);
            xml = xml + "<flag>failure</flag>";
        }


        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
        out.close();
    }
}
