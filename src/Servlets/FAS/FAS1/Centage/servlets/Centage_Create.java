package Servlets.FAS.FAS1.Centage.servlets;

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

import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.ibm.icu.text.DateFormat;
//import com.ibm.icu.text.SimpleDateFormat;


public class Centage_Create extends HttpServlet
{

    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strCommand = "";
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;
        int txtCB_Month = 0;
        int txtCB_Year = 0;
        String xml = null;
        String sql = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HttpSession session = request.getSession(false);
        try
        {
            if(session == null)
            {
                System.out.println((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                response.sendRedirect((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                return;
            }
            System.out.println(session);
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Redirect Error :").append(e).toString());
        }
        try
        {
            ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in opening connection :").append(e).toString());
        }
        try
        {
            strCommand = request.getParameter("Command");
            System.out.println((new StringBuilder()).append("assign..here command...").append(strCommand).toString());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in assigning...").append(e).toString());
        }
        try
        {
            cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("cmbAcc_UnitCode ").append(cmbAcc_UnitCode).toString());
        try
        {
            cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("cmbOffice_code ").append(cmbOffice_code).toString());
        try
        {
            txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("txtCB_Year ").append(txtCB_Year).toString());
        try
        {
            txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("txtCB_Month ").append(txtCB_Month).toString());
        xml = "<response><command>PendingJournals</command>";
        if(strCommand.equalsIgnoreCase("PendingJournals"))
        {
            try
            {
                sql = "select                                   \n  " +
                      "  accounting_unit_id ,             " +
				"        \n    accounting_for_office_id,                \n    acc_code,          " +
				"                      \n   ( select account_head_desc from com_mst_account_heads" +
				" where account_head_code = acc_code ) as acc_code_desc, \n    project_sl_code,  " +
				"                       \n    cat_code,                                \n    cr_a" +
				"ccount_head_code,                    \n    ( select account_head_desc from com_m" +
				"st_account_heads where account_head_code = cr_account_head_code ) as cr_account_" +
				"head_code_desc, \n    dr_account_head_code,                    \n    ( select ac" +
				"count_head_desc from com_mst_account_heads where account_head_code = dr_account_" +
				"head_code ) as dr_account_head_code_desc, \n    category_desc,                  " +
				"         \n    centage_rate,                            \n    voucher_no,       " +
				"                       \n    to_char(voucher_date,'DD-MON-YY') as voucher_date ," +
				"                                  \n    amount,                                 " +
				"                                             \n    trim(to_char(( amount ),'9999" +
				"9999999999.99')) as centage_amount,                     \n    sl_type_code,     " +
				"                                                                   \n    (select" +
				" sub_ledger_type_desc from com_mst_sl_types where sub_ledger_type_code = sl_type" +
				"_code) as sl_type_code_desc,       \n    sl_code,                               " +
				"         \n                                                    \n               " +
				"-- Supplier 1                        \n               case when sl_type_code = 1" +
				" then      \n               (                                    \n             " +
				"     select                            \n                      SUPPLIER_NAME    " +
				"             \n                  from                              \n           " +
				"           COM_SUPPLIER_SL_MST           \n                  where              " +
				"               \n                       ACCOUNTING_UNIT_ID= ?        \n         " +
				"          and ACCOUNTING_FOR_OFFICE_ID=?   \n                   and supplier_id " +
				"= sl_code        \n               )                                    \n       " +
				"        else                                 \n               (     --- Firms 2 " +
				"                   \n                     case when sl_type_code=2 then  \n     " +
				"                (                              \n                        select " +
				"                     \n                           FIRMS_NAME               \n   " +
				"                     from                        \n                           CO" +
				"M_FIRMS_SL_MST         \n                        where                       \n " +
				"                           ACCOUNTING_UNIT_ID=?    \n                        and" +
				" ACCOUNTING_FOR_OFFICE_ID=?            \n                        and firms_id=sl" +
				"_code                      \n                     )                             " +
				"               \n                     else                                      " +
				"   \n                     (      --- Assests 3                         \n       " +
				"                     case when sl_type_code=3 then         \n                   " +
				"         (                                     \n                               " +
				" select                            \n                                    ASSET_D" +
				"ESCRIPTION             \n                                from                   " +
				"           \n                                    COM_MST_ASSETS_SL             \n" +
				"                                where                             \n            " +
				"                        ACCOUNTING_UNIT_ID=?          \n                        " +
				"        and ACCOUNTING_FOR_OFFICE_ID=?    \n                                and " +
				"asset_code = sl_code          \n                            )                   " +
				"                  \n                            else                            " +
				"      \n                            (    --- Offices 5                    \n    " +
				"                             case when sl_type_code = 5 then  \n                " +
				"                 (                                \n                            " +
				"        select                        \n                                        " +
				"OFFICE_NAME               \n                                    from            " +
				"              \n                                        COM_MST_OFFICES         " +
				"  \n                                    where OFFICE_ID=sl_code       \n        " +
				"                         )                                \n                    " +
				"             else                             \n                                " +
				" (    -- Bank 6                   \n                                      case w" +
				"hen sl_type_code = 6 then    \n                                      (          " +
				"                 \n                                            select           " +
				"     \n                                              to_char(bank_ac_no) \n     " +
				"                                       from                  \n                 " +
				"                               fas_mst_bank_balance     \n                      " +
				"                      where                        \n                           " +
				"                     ACCOUNTING_UNIT_ID=?     \n                                " +
				"            and BANK_AC_NO_ALIAS_CODE = sl_code  \n                             " +
				"                                            \n                                  " +
				"     )                                 \n                                       " +
				"else                              \n                                       (   -" +
				"- Employees 7                \n                                            case " +
				"when sl_type_code= 7 then    \n                                           (     " +
				"                        \n                                              select  " +
				"                   \n                                                    e.EMPLO" +
				"YEE_NAME||'.'||e.EMPLOYEE_INITIAL ||'-'|| d.DESIGNATION as ENAME     \n         " +
				"                                     from                                    \n " +
				"                                                   HRM_MST_EMPLOYEES e,         " +
				"     \n                                                    HRM_EMP_CURRENT_POSTI" +
				"NG c,        \n                                                    HRM_MST_DESIG" +
				"NATIONS d            \n                                               where     " +
				"                             \n                                                 " +
				"   c.DESIGNATION_ID=d.DESIGNATION_ID \n                                         " +
				"       and c.EMPLOYEE_ID=e.EMPLOYEE_ID       \n                                 " +
				"               and c.OFFICE_ID=?                     \n                         " +
				"                       and e.EMPLOYEE_ID=sl_code             \n                 " +
				"                           )                                         \n         " +
				"                                   else                                      \n " +
				"                                           (                                    " +
				"     \n                                                -- OTHER DEPARTMENTS     " +
				"             \n                                                case when sl_type" +
				"_code= 9 then        \n                                                (        " +
				"                             \n                                                 " +
				" select                              \n                                         " +
				"              dep.OTHER_DEPT_NAME || '-' || off.OTHER_DEPT_OFFICE_NAME as OTHER_" +
				"DEPT_OFF_NAME\n                                                  from           " +
				"                     \n                                                       HR" +
				"M_MST_OTHER_DEPTS dep,       \n                                                 " +
				"      HRM_MST_OTHER_DEPT_OFFICES off \n                                         " +
				"         where                               \n                                 " +
				"                      dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID        \n             " +
				"                                      and off.other_dept_office_alias_id= sl_cod" +
				"e    \n                                                )                        " +
				"                         \n                                                else " +
				"                                             \n                                 " +
				"               (                                                 \n             " +
				"                                      -- PROJECT                                " +
				"     \n                                                   case when sl_type_code" +
				"=10 then                 \n                                                   ( " +
				"                                             \n                                 " +
				"                       select                                    \n             " +
				"                                              PROJECT_NAME                      " +
				"     \n                                                        from             " +
				"                         \n                                                     " +
				"      PMS_MST_PROJECTS_VIEW                  \n                                 " +
				"                       where                                     \n             " +
				"                                               OFFICE_ID=?                      " +
				"     \n                                                        and PROJECT_ID= s" +
				"l_code                   \n                                                   ) " +
				"                                             \n                                 " +
				"                  else                                           \n             " +
				"                                      (                                         " +
				"     \n                                                     -- CONTRACTORS      " +
				"                         \n                                                     " +
				"case when sl_type_code=11 then               \n                                 " +
				"                    (                                            \n             " +
				"                                          select                                " +
				"     \n                                                          CONTRACTOR_NAME" +
				"                         \n                                                     " +
				"  from                                       \n                                 " +
				"                         PMS_MST_CONTRACTORS_VIEW                \n             " +
				"                                          where                                 " +
				"     \n                                                          (OFFICE_ID=? or" +
				" OFFICE_ID in            \n                                                     " +
				"     (select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where office_id=?   " +
				"     \n                                                           union all     " +
				"                                                                 \n             " +
				"                                              select CIRCLE_OFFICE_ID from COM_M" +
				"ST_ALL_OFFICES_VIEW where office_id=?        \n                                 " +
				"                          )) and                                                " +
				"                         \n                                                     " +
				"      CONTRACTOR_ID= sl_code                                                    " +
				"     \n                                                      )end               " +
				"                                                                 \n             " +
				"                                      \n                                        " +
				"           )end \n                                                \n            " +
				"                                    ) end \n                                    " +
				"           \n                                            )end \n                " +
				"                              \n                                            \n  " +
				"                                     )end \n                                  \n" +
				"                                  )end \n                            \n         " +
				"                   )end  \n                            \n                     ) " +
				"end \n               \n               ) end as sl_code_name \n    \nfrom        " +
				"                                \n(                                            \n" +
				"select                                       \n *                               " +
				"            \nfrom                                         \n(                  " +
				"                          \nselect                                       \n  ACC" +
				"OUNTING_UNIT_ID,                        \n  ACCOUNTING_FOR_OFFICE_ID,           " +
				"       \n  WEXP_ACCOUNT_HEAD_CODE,                    \n  PROJECT_SL_CODE,      " +
				"                     \n  cat_code,                                  \n  CR_ACCOU" +
				"NT_HEAD_CODE,                      \n  DR_ACCOUNT_HEAD_CODE,                    " +
				"  \n  CATEGORY_DESC,                             \n  ( select centage_rate from " +
				"fas_centage_rates where CATEGORY_CODE = cat_code  and ACCOUNTING_UNIT_ID = ?  ) as centage_rate              " +
				"        \nfrom                                         \n(                      " +
				"                      \nselect                                       \n spe.ACCO" +
				"UNTING_UNIT_ID,                     \n spe.ACCOUNTING_FOR_OFFICE_ID,            " +
				"   \n spe.WEXP_ACCOUNT_HEAD_CODE,                 \n spe.PROJECT_SL_CODE,       " +
				"                 \n spe.DEFAULT_CENTAGE_CATEGORY as cat_code,   \n spe.CR_ACCOUN" +
				"T_HEAD_CODE,                   \n spe.DR_ACCOUNT_HEAD_CODE ,                  \n" +
				" cat.category_desc                           \nfrom                             " +
				"            \n  fas_speci_centage_ac_heads spe,            \n  FAS_CENTAGE_CATEG" +
				"ORY_HO_MASTER cat         \nwhere                                        \n  spe" +
				".accounting_unit_id = cat.ACCOUNTING_UNIT_ID and                    \n  spe.ACCO" +
				"UNTING_FOR_OFFICE_ID = cat.ACCOUNTING_FOR_OFFICE_ID and        \n  spe.DEFAULT_C" +
				"ENTAGE_CATEGORY = cat.CATEGORY_CODE and                   \n  spe.accounting_uni" +
				"t_id= ? and                                          \n  spe.ACCOUNTING_FOR_OFFI" +
				"CE_ID= ?                                        \n)                             " +
				"               \n                                             \n)a              " +
				"                             \n                                             \n i" +
				"nner join                                  \n                                   " +
				"          \n(                                            \nselect               " +
				"                        \n  m.accounting_unit_id as unit_id,           \n  m.ACC" +
				"OUNTING_FOR_OFFICE_ID as office_id,   \n  m.CASHBOOK_MONTH,                     " +
				"     \n  m.CASHBOOK_YEAR,                           \n  m.VOUCHER_NO,           " +
				"                   \n  m.VOUCHER_DATE,                            \n  t.AMOUNT, " +
				"                                 \n  t.SUB_LEDGER_TYPE_CODE as sl_type_code,    " +
				"           \n  t.SUB_LEDGER_CODE as sl_code,                         \n  t.ACCOU" +
				"NT_HEAD_CODE as acc_code                       \nfrom                           " +
				"              \n  fas_journal_master m ,                     \n  fas_journal_tra" +
				"nsaction t                  \nwhere                                        \n  m" +
				".CREATED_BY_MODULE='LJV'  and             \n  m.JOURNAL_STATUS='L'  and         " +
				"         \n  m.CENTAGE_AVAIL is null and                \n  m.ACCOUNTING_UNIT_ID" +
				"= ? and                \n  m.ACCOUNTING_FOR_OFFICE_ID = ? and         \n  m.CASH" +
				"BOOK_MONTH = ? and                   \n  m.CASHBOOK_YEAR = ?  and               " +
				"    \n                                             \n  m.ACCOUNTING_UNIT_ID=t.AC" +
				"COUNTING_UNIT_ID and                  \n  m.ACCOUNTING_FOR_OFFICE_ID = t.ACCOUNT" +
				"ING_FOR_OFFICE_ID and    \n  m.CASHBOOK_MONTH = t.CASHBOOK_MONTH and            " +
				"            \n  m.CASHBOOK_YEAR = t.CASHBOOK_YEAR and                          \n" +
				"  m.VOUCHER_NO = t.VOUCHER_NO and                                \n  t.centage_c" +
				"reated is null and                                  \n  t.CR_DR_INDICATOR='DR' a" +
				"nd                                     \n  t.ACCOUNT_HEAD_CODE in               " +
				"                          \n  (                                                 " +
				"             \n    select                                                       " +
				"\n         account_head_code                                       \n    from co" +
				"m_mst_account_heads                                   \n    where               " +
				"                                         \n        major_head_code = 'L'        " +
				"                            \n    and minor_head_code = 183                     " +
				"               \n  )                                                            " +
				"  \n) b                                                              \non       " +
				"                                                        \n  a.ACCOUNTING_UNIT_ID" +
				" = b.unit_id and                           \n  a.ACCOUNTING_FOR_OFFICE_ID = b.OF" +
				"FICE_ID and                   \n  a.WEXP_ACCOUNT_HEAD_CODE = b.acc_code and     " +
				"         \n  a.PROJECT_SL_CODE = b.sl_code                          \n)         " +
				"                                   "
				;
                System.out.println("sql in 1:::::::;"+sql);
                ps = con.prepareStatement(sql);
               // System.out.println((new StringBuilder()).append("Spec").append(sql).toString());
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbAcc_UnitCode);
                ps.setInt(4, cmbOffice_code);
                ps.setInt(5, cmbAcc_UnitCode);
                ps.setInt(6, cmbOffice_code);
                ps.setInt(7, cmbAcc_UnitCode);
                ps.setInt(8, cmbOffice_code);
                ps.setInt(9, cmbOffice_code);
                ps.setInt(10, cmbOffice_code);
                ps.setInt(11, cmbOffice_code);
                ps.setInt(12, cmbOffice_code);
                
                ps.setInt(13, cmbAcc_UnitCode);
                
                ps.setInt(14, cmbAcc_UnitCode);
                ps.setInt(15, cmbOffice_code);
                ps.setInt(16, cmbAcc_UnitCode);
                ps.setInt(17, cmbOffice_code);
                ps.setInt(18, txtCB_Month);
                ps.setInt(19, txtCB_Year);
                
                rs = ps.executeQuery();
                int cnt = 0;
                xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                // for single grid::
//                while(rs.next()) 
//                {
//                    xml = (new StringBuilder()).append(xml).append("<option>").append("<voucher_no>").append(rs.getInt("voucher_no")).append("</voucher_no>").append("<voucher_date>").append(rs.getString("voucher_date")).append("</voucher_date>").append("<account_head_code>").append(rs.getInt("acc_code")).append("</account_head_code>").append("<account_head_code_desc>").append(rs.getString("acc_code_desc")).append("</account_head_code_desc>").append("<sub_ledger_type_code>").append(rs.getInt("sl_type_code")).append("</sub_ledger_type_code>").append("<sub_ledger_type_code_desc>").append(rs.getString("sl_type_code_desc")).append("</sub_ledger_type_code_desc>").append("<project_sl_code>").append(rs.getInt("sl_code")).append("</project_sl_code>").append("<project_sl_code_desc>").append(rs.getString("sl_code_name")).append("</project_sl_code_desc>").append("<cat_code>").append(rs.getInt("cat_code")).append("</cat_code>").append("<category_desc>").append(rs.getString("category_desc")).append("</category_desc>").append("<centage_rate>").append(rs.getDouble("centage_rate")).append("</centage_rate>").append("<centage_amount>").append(rs.getString("centage_amount")).append("</centage_amount>").append("<dr_account_head_code>").append(rs.getInt("dr_account_head_code")).append("</dr_account_head_code>").append("<dr_account_head_code_desc>").append(rs.getString("dr_account_head_code_desc")).append("</dr_account_head_code_desc>").append("<cr_account_head_code_desc>").append(rs.getString("cr_account_head_code_desc")).append("</cr_account_head_code_desc>").append("<cr_account_head_code>").append(rs.getInt("cr_account_head_code")).append("</cr_account_head_code></option>").toString();
//                    cnt++;
//                }
                
                System.out.println((new StringBuilder()).append("Specific AC head code Table count--> ").append(cnt).toString());
                
                
               // if(cnt == 0)
                {
                    sql ="                                 select \n" + 
                    "                                * \n" + 
                    "                                from \n" + 
                    "                                (\n" + 
                    "                                  select \n" + 
                    "                                    accounting_unit_id ,\n" + 
                    "                                    accounting_for_office_id,\n" + 
                    "                                    \n" + 
                    "                                    acc_code,\n" + 
                    "                                    \n" + 
                    "                                    (SELECT account_head_desc\n" + 
                    "                                    FROM com_mst_account_heads\n" + 
                    "                                    WHERE account_head_code= acc_code\n" + 
                    "                                    ) AS acc_code_desc,\n" + 
                    "                                    \n" + 
                    "                                    \n" + 
                    "                                    project_sl_code,  \n" + 
                    "                                    cat_code,  \n" + 
                    "                                    \n" + 
                    "                                    cr_account_head_code,\n" + 
                    "                                    (SELECT account_head_desc\n" + 
                    "                                    FROM com_mst_account_heads\n" + 
                    "                                    WHERE account_head_code= cr_account_head_code\n" + 
                    "                                    ) AS cr_account_head_code_desc,\n" + 
                    "                                    \n" + 
                    "                                    \n" + 
                    "                                    dr_account_head_code,\n" + 
                    "                                    (SELECT account_head_desc\n" + 
                    "                                    FROM com_mst_account_heads\n" + 
                    "                                    WHERE account_head_code= dr_account_head_code\n" + 
                    "                                    ) AS dr_account_head_code_desc,\n" + 
                    "                                    \n" + 
                    "                                    category_desc,\n" + 
                    "                                    centage_rate,\n" + 
                    "                                    voucher_no,\n" + 
                    "                                    TO_CHAR(voucher_date,'DD-MON-YY') AS voucher_date ,\n" + 
                    "                                    amount,\n" + 
                    "                                    trim(TO_CHAR(( amount ),'99999999999999.99')) AS centage_amount,\n" + 
                    "                                    sl_type_code,\n" + 
                    "                                    (SELECT sub_ledger_type_desc\n" + 
                    "                                    FROM com_mst_sl_types\n" + 
                    "                                    WHERE sub_ledger_type_code = sl_type_code\n" + 
                    "                                    ) AS sl_type_code_desc,\n" + 
                    "                                    \n" + 
                    "                                    j_sl_code\n" + 
                    "                                    \n" + 
                    "                                  from \n" + 
                    "                                   (\n" + 
                    "                                    select                                     \n" + 
                    "                                    *                                          \n" + 
                    "                                    from                                       \n" + 
                    "                                    (                                          \n" + 
                    "                                \n" + 
                    "                                                  select                                     \n" + 
                    "                                                    ACCOUNTING_UNIT_ID,                      \n" + 
                    "                                                    ACCOUNTING_FOR_OFFICE_ID,                \n" + 
                    "                                                    WEXP_ACCOUNT_HEAD_CODE,                  \n" + 
                    "                                                    0 as PROJECT_SL_CODE,                    \n" + 
                    "                                                    cat_code,                                       \n" + 
                    "                                                    DEFAULT_CENTAGE_CATEGORY_TYPE,\n" + 
                    "                                                    CR_ACCOUNT_HEAD_CODE,                    \n" + 
                    "                                                    DR_ACCOUNT_HEAD_CODE,                    \n" + 
                    "                                                    CATEGORY_DESC, \n" + 
                    "                                                    centage_rate\n" + 
                    "                                                    \n" + 
                    "                                                  from                                       \n" + 
                    "                                                  (                               \n" + 
                    "                                                    \n" + 
                    "                                                      \n" + 
                    "                                                    select \n" + 
                    "                                                    *\n" + 
                    "                                                    from \n" + 
                    "                                                    (                                   \n" + 
                    "                                                      select                                     \n" + 
                    "                                                       ind.ACCOUNTING_UNIT_ID,                   \n" + 
                    "                                                       ind.ACCOUNTING_FOR_OFFICE_ID,             \n" + 
                    "                                                       ind.WEXP_ACCOUNT_HEAD_CODE,               \n" + 
                    "                                                       ind.DEFAULT_CENTAGE_CATEGORY as cat_code,  \n" + 
                    "                                                       ind.DEFAULT_CENTAGE_CATEGORY_TYPE,\n" + 
                    "                                                       ind.CR_ACCOUNT_HEAD_CODE,                  \n" + 
                    "                                                       ind.DR_ACCOUNT_HEAD_CODE\n" + 
                    "                                                      from                                        \n" + 
                    "                                                        fas_inde_centage_ac_heads ind\n" + 
                    "                                                      where                                                                            \n" + 
                    "                                                        ind.accounting_unit_id= ? and                                      \n" + 
                    "                                                        ind.ACCOUNTING_FOR_OFFICE_ID = ?  \n" + 
                    "                                                    )x \n" + 
                    "                                                    \n" + 
                    "                                                    left outer join \n" + 
                    "                                                    \n" + 
                    "                                                   ( \n" + 
                    "                                                   \n" + 
                    "                                                  ----------------------------------------- \n" + 
                    "                                                  \n" + 
                    "                                                  select \n" + 
                    "                                                   * \n" + 
                    "                                                  from \n" + 
                    "                                                  (\n" + 
                    "                                                    SELECT \n" + 
                    "                                                      CATEGORY_CODE, \n" + 
                    "                                                      CATEGORY_DESC, \n" + 
                    "                                                      CATEGORY_TYPE, \n" + 
                    "                                                      ( category_type || category_code ) AS cat_code_type \n" + 
                    "                                                    FROM FAS_CENTAGE_CATEGORY_HO_MASTER \n" + 
                    "                                                    WHERE ACCOUNTING_UNIT_ID     = ? \n" + 
                    "                                                    AND ACCOUNTING_FOR_OFFICE_ID = ? \n" + 
                    "                                                    AND CATEGORY_TYPE            ='S' \n" + 
                    "                                                     \n" + 
                    "                                                    UNION ALL \n" + 
                    "                                                     \n" + 
                    "                                                    SELECT CATEGORY_CODE, \n" + 
                    "                                                      CATEGORY_DESC, \n" + 
                    "                                                      CATEGORY_TYPE, \n" + 
                    "                                                      (category_type || category_code ) AS cat_code_type \n" + 
                    "                                                    FROM FAS_CENTAGE_CATEGORY_HO_MASTER \n" + 
                    "                                                    WHERE CATEGORY_TYPE='C' \n" + 
                    "                                                  )m\n" + 
                    "                                                  \n" + 
                    "                                                  left  outer join \n" + 
                    "                                                  \n" + 
                    "                                                  (\n" + 
                    "                                                    select \n" + 
                    "                                                      category_code as rate_cat_code,\n" + 
                    "                                                      centage_rate,\n" + 
                    "                                                      DEFAULT_CENTAGE_CATEGORY_TYPE as rate_cat_type\n" + 
                    "                                                    from\n" + 
                    "                                                      fas_centage_rates \n" + 
                    "                                                    WHERE ACCOUNTING_UNIT_ID     = ? \n" + 
                    "                                                       AND ACCOUNTING_FOR_OFFICE_ID = ?\n" + 
                    "                                                       AND DEFAULT_CENTAGE_CATEGORY_TYPE ='S'\n" + 
                    "                                                   \n" + 
                    "                                                    union all \n" + 
                    "                                                    \n" + 
                    "                                                   select \n" + 
                    "                                                      category_code as rate_cat_code ,\n" + 
                    "                                                      centage_rate, \n" + 
                    "                                                      DEFAULT_CENTAGE_CATEGORY_TYPE as rate_cat_type\n" + 
                    "                                                    from\n" + 
                    "                                                      fas_centage_rates \n" + 
                    "                                                    where  DEFAULT_CENTAGE_CATEGORY_TYPE ='C'\n" + 
                    "                                                  )n\n" + 
                    "                                                  on m.category_code = n.rate_cat_code and \n" + 
                    "                                                     m.category_type = n.rate_cat_type \n" + 
                    "                                                  \n" + 
                    "                                                  ----------------------------------------                                \n" + 
                    "                                                  )y\n" + 
                    "                                                       \n" + 
                    "                                                  ON x.cat_code  = y.CATEGORY_CODE\n" + 
                    "                                                 AND x.DEFAULT_CENTAGE_CATEGORY_type  = y.CATEGORY_TYPE\n" + 
                    "                                               )       \n" + 
                    "                                                     \n" + 
                    "                                   \n" + 
                    "                                   \n" + 
                    "                                   \n" + 
                    "                                    )a                                     \n" + 
                    "                                                                           \n" + 
                    "                                    inner join                            \n" + 
                    "                                                                           \n" + 
                    "                                    (    \n" + 
                    "                                    \n" + 
                    "                                    select                                 \n" + 
                    "                                      m.accounting_unit_id as unit_id,         \n" + 
                    "                                      m.ACCOUNTING_FOR_OFFICE_ID as office_id, \n" + 
                    "                                      m.CASHBOOK_MONTH,                        \n" + 
                    "                                      m.CASHBOOK_YEAR,                         \n" + 
                    "                                      m.VOUCHER_NO,                            \n" + 
                    "                                      m.VOUCHER_DATE,                          \n" + 
                    "                                      t.AMOUNT,                                \n" + 
                    "                                      t.SUB_LEDGER_TYPE_CODE as sl_type_code,                  \n" + 
                    "                                      t.SUB_LEDGER_CODE as j_sl_code,                       \n" + 
                    "                                      t.ACCOUNT_HEAD_CODE as acc_code                      \n" + 
                    "                                    from                                       \n" + 
                    "                                      fas_journal_master m ,                   \n" + 
                    "                                      fas_journal_transaction t                \n" + 
                    "                                    where                                      \n" + 
                    "                                      m.CREATED_BY_MODULE='LJV'  and          \n" + 
                    "                                      m.JOURNAL_STATUS='L'  and               \n" + 
                    "                                                                              \n" + 
                    "                                      m.ACCOUNTING_UNIT_ID= ? and             \n" + 
                    "                                      m.ACCOUNTING_FOR_OFFICE_ID = ? and      \n" + 
                    "                                      m.CASHBOOK_MONTH = ? and                \n" + 
                    "                                      m.CASHBOOK_YEAR = ? and                 \n" + 
                    "                                      m.CENTAGE_AVAIL is null and             \n" + 
                    "                                      m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID and  \n" + 
                    "                                      m.ACCOUNTING_FOR_OFFICE_ID = t.ACCOUNTING_FOR_OFFICE_ID and    \n" + 
                    "                                      m.CASHBOOK_MONTH = t.CASHBOOK_MONTH and                        \n" + 
                    "                                      m.CASHBOOK_YEAR = t.CASHBOOK_YEAR and                          \n" + 
                    "                                      m.VOUCHER_NO = t.VOUCHER_NO and                                \n" + 
                    "                                      t.centage_created is null and                                  \n" + 
                    "                                      t.CR_DR_INDICATOR='DR' and                                     \n" + 
                    "                                      t.ACCOUNT_HEAD_CODE in                                         \n" + 
                    "                                      (                                                              \n" + 
                    "                                        select                                                \n" + 
                    "                                             account_head_code                                \n" + 
                    "                                        from com_mst_account_heads                            \n" + 
                    "                                        where                                                 \n" + 
                    "                                            major_head_code = 'L'                             \n" + 
                    "                                        and minor_head_code = 183                             \n" + 
                    "                                      )\n" + 
                    "                                      \n" + 
                    "                                    ) b                                                         \n" + 
                    "                                    on                                                          \n" + 
                    "                                      a.ACCOUNTING_UNIT_ID = b.unit_id and                      \n" + 
                    "                                      a.ACCOUNTING_FOR_OFFICE_ID = b.OFFICE_ID and              \n" + 
                    "                                      a.WEXP_ACCOUNT_HEAD_CODE = b.acc_code    \n" + 
                    "                              )\n" + 
                    "                            )i\n" + 
                    "                            left outer join                                                         \n" + 
                    "                            ( \n" + 
                    "                                select \n" + 
                    "                                   SL_CODENAME as sl_code_name ,\n" + 
                    "                                   sl_code,\n" + 
                    "                                   sl_type \n" + 
                    "                                from \n" + 
                    "                                  sl_type_code_name_view                                 \n" + 
                    "                            )j\n" + 
                    "                            on i.j_sl_code = j.sl_code and \n" + 
                    "                               i.sl_type_code = sl_type \n" + 
                    "                               \n" + 
                    "                             \n" + 
                    "                            \n" + 
                    "                             \n" + 
                    "                              ";
                    
                    System.out.println("sql in 2***********"+sql); 
                    ps = con.prepareStatement(sql);
                //    System.out.println((new StringBuilder()).append("Inde").append(sql).toString());
                    
                    ps.setInt(1, cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);System.out.println(cmbOffice_code);
                    
                    ps.setInt(3, cmbAcc_UnitCode);System.out.println(cmbAcc_UnitCode);
                    ps.setInt(4, cmbOffice_code);System.out.println(cmbOffice_code);
                    
                    ps.setInt(5, cmbAcc_UnitCode);System.out.println(cmbAcc_UnitCode);
                    ps.setInt(6, cmbOffice_code);System.out.println(cmbOffice_code);
                    
                    ps.setInt(7, cmbAcc_UnitCode);System.out.println(cmbAcc_UnitCode);
                    ps.setInt(8, cmbOffice_code);System.out.println(cmbOffice_code);
                    ps.setInt(9, txtCB_Month);System.out.println(txtCB_Month);
                    ps.setInt(10, txtCB_Year);System.out.println(txtCB_Year);
                    
                    System.out.println("1");
                    rs = ps.executeQuery();
                    System.out.println("2");
                    cnt = 0;
                    xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                    while(rs.next()) 
                    {
                        xml = (new StringBuilder()).append(xml).append("<option>").append("<voucher_no>").append(rs.getInt("voucher_no")).append("</voucher_no>").append("<voucher_date>").append(rs.getString("voucher_date")).append("</voucher_date>").append("<account_head_code>").append(rs.getInt("acc_code")).append("</account_head_code>").append("<account_head_code_desc>").append(rs.getString("acc_code_desc")).append("</account_head_code_desc>").append("<sub_ledger_type_code>").append(rs.getInt("sl_type_code")).append("</sub_ledger_type_code>").append("<sub_ledger_type_code_desc>").append(rs.getString("sl_type_code_desc")).append("</sub_ledger_type_code_desc>").append("<project_sl_code>").append(rs.getInt("sl_code")).append("</project_sl_code>").append("<project_sl_code_desc>").append(rs.getString("sl_code_name")).append("</project_sl_code_desc>").append("<cat_code>").append(rs.getInt("cat_code")).append("</cat_code>").append("<category_desc>").append(rs.getString("category_desc")).append("</category_desc>").append("<centage_rate>").append(rs.getDouble("centage_rate")).append("</centage_rate>").append("<centage_amount>").append(rs.getString("centage_amount")).append("</centage_amount>").append("<dr_account_head_code>").append(rs.getInt("dr_account_head_code")).append("</dr_account_head_code>").append("<dr_account_head_code_desc>").append(rs.getString("dr_account_head_code_desc")).append("</dr_account_head_code_desc>").append("<cr_account_head_code_desc>").append(rs.getString("cr_account_head_code_desc")).append("</cr_account_head_code_desc>").append("<cr_account_head_code>").append(rs.getInt("cr_account_head_code")).append("</cr_account_head_code></option>").toString();
                        cnt++;
                    }
                    System.out.println("Ide here--->"+xml);
                    System.out.println((new StringBuilder()).append("Indepe AC head code Table count--> ").append(cnt).toString());
                    if(cnt == 0)
                    {
                    	System.out.println("count is zero");
                        xml = "<response><command>PendingJournals</command><flag>failure</flag>";
                    }
                }
                
                
                
                sql = " SELECT\n" + 
                "  t.category_code,\n" + 
                "  t.category_desc,\n" + 
                "  m.centage_rate, \n" + 
                "  t.CATEGORY_TYPE as cat_type, \n" + 
                "  (t.category_type || t.category_code ) as cat_code_type\n" + 
                "FROM \n" + 
                "  fas_centage_category_ho_master t,\n" + 
                "  fas_centage_rates m\n" + 
                "WHERE t.CATEGORY_CODE          = m.CATEGORY_CODE\n" + 
                "AND t.accounting_unit_id       = m.accounting_unit_id\n" + 
                "AND t.ACCOUNTING_FOR_OFFICE_ID =m.ACCOUNTING_FOR_OFFICE_ID\n" + 
                "AND t.accounting_unit_id       = ?\n" + 
                "AND t.accounting_for_office_id=? \n" + 
                "and t.CATEGORY_TYPE = 'S'\n" + 
                "and t.CATEGORY_TYPE = m.DEFAULT_CENTAGE_CATEGORY_TYPE\n" + 
                "\n" + 
                "union all \n" + 
                "\n" + 
                "SELECT\n" + 
                "  t.category_code,\n" + 
                "  t.category_desc,\n" + 
                "  m.centage_rate, \n" + 
                "  t.CATEGORY_TYPE as cat_type, \n" + 
                "  (t.category_type || t.category_code ) as cat_code_type \n" + 
                "FROM \n" + 
                "  fas_centage_category_ho_master t,\n" + 
                "  fas_centage_rates m\n" + 
                "WHERE t.CATEGORY_CODE          = m.CATEGORY_CODE\n" + 
                "AND t.accounting_unit_id       = m.accounting_unit_id\n" + 
                "AND t.ACCOUNTING_FOR_OFFICE_ID =m.ACCOUNTING_FOR_OFFICE_ID\n" + 
                "and t.CATEGORY_TYPE = 'C'\n" + 
                "and t.CATEGORY_TYPE = m.DEFAULT_CENTAGE_CATEGORY_TYPE   ";
                
                System.out.println("sql in 3--------:"+sql); 
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                for(rs = ps.executeQuery(); rs.next();)
                {
                	System.out.println("rs:"+rs);
                    xml = (new StringBuilder()).append(xml).append("<mst_option>").append("<category_code>").append(rs.getString("cat_code_type")).append("</category_code>").append("<category_desc>").append(rs.getString("category_desc")).append("</category_desc>").append("<centage_rate>").append(rs.getString("centage_rate")).append("</centage_rate>").append("</mst_option>").toString();
                }
                System.out.println("combo data xml---->"+xml);

            }
            catch(Exception e)
            {
                System.out.println((new StringBuilder()).append("Error ").append(e).toString());
                xml = "<response><command>PendingJournals</command><flag>failure</flag>";
            }
            xml = (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        String CONTENT_TYPE = "text/html; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        CallableStatement cs = null;
        PreparedStatement ps=null;
        Statement st=null;
        ResultSet rs=null;
        String strCommand = "";
        int TotalRecords=0;
        
        
        HttpSession session = request.getSession(false);
        try
        {
            if(session == null)
            {
                System.out.println((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                response.sendRedirect((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                return;
            }
            System.out.println(session);
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Redirect Error :").append(e).toString());
        }
        String update_user = (String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        Date ctdate = new java.sql.Date(ts.getTime());  
        System.out.println("date***********************************"+ctdate);
        
        try
        {
            ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            st=con.createStatement();
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in opening connection :").append(e).toString());
        }
        int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        System.out.println((new StringBuilder()).append("cmbAcc_UnitCode-->").append(cmbAcc_UnitCode).toString());
        int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        System.out.println((new StringBuilder()).append("cmbOffice_code-->").append(cmbOffice_code).toString());
        int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        System.out.println((new StringBuilder()).append("txtCB_Year-->").append(txtCB_Year).toString());
        int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println((new StringBuilder()).append("txtCB_Month-->").append(txtCB_Month).toString());
        
        
        try {
            TotalRecords = Integer.parseInt(request.getParameter("TotalRecords"));
        }
        catch (Exception e) {
            System.out.println("Error getting TotalRecords "+e);
        }
        
        
        String sel[] = request.getParameterValues("sel");
        String voucher_no[] = request.getParameterValues("voucher_no");
        String account_head_code[] = request.getParameterValues("account_head_code");
        String project_sl_code[] = request.getParameterValues("project_sl_code");
        String category_desc[] = request.getParameterValues("category_desc");
        String centage_rate[] = request.getParameterValues("centage_rate");
        String dr_account_head_code[] = request.getParameterValues("dr_account_head_code");
        String cr_account_head_code[] = request.getParameterValues("cr_account_head_code");
        String centage_amount[] = request.getParameterValues("centage_amount");
        String remarks[] = request.getParameterValues("remarks");
        String sub_ledger_type_code[] = request.getParameterValues("sub_ledger_type_code");
        String cmbCategoryCode1[] = request.getParameterValues("cmbCategoryCode");
        
        
        int _voucher_no = 0;
        int _account_head_code = 0;
        int _project_sl_code = 0;
        String _cat_code = "";
        String _category_desc = null;
        double _centage_rate = 0.0D;
        int _dr_account_head_code = 0;
        int _cr_account_head_code = 0;
        double _centage_amount = 0.0D;
        String _remarks = null;
        int _sub_ledger_type_code = 0;
        try
        {
            con.setAutoCommit(false);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
      
        int count = 0;
        for(int k = 0; k < TotalRecords; k++)
        {
           System.out.println("the value of K -->"+k);
           System.out.println("TotalRecords"+TotalRecords);
           boolean flag =false;
           for(int i=0;i<sel.length;i++)
           {
             System.out.println("The value of i -->"+i);
             if (k == Integer.parseInt(sel[i]) ) 
             {
                flag=true; 
             }
           }
           
           System.out.println("Flag Status -->"+flag);
           
           if(flag)
           {
                    try
                    {
                        _voucher_no = Integer.parseInt(voucher_no[k]);
                        _account_head_code = Integer.parseInt(account_head_code[k]);
                        _project_sl_code = Integer.parseInt(project_sl_code[k]);
                        _cat_code = cmbCategoryCode1[k];
                        _category_desc = category_desc[k];
                        _centage_rate = Double.parseDouble(centage_rate[k]);
                        _dr_account_head_code = Integer.parseInt(dr_account_head_code[k]);
                        _cr_account_head_code = Integer.parseInt(cr_account_head_code[k]);
                        _centage_amount = Double.parseDouble(centage_amount[k]);
                        _remarks = remarks[k];
                        _sub_ledger_type_code = Integer.parseInt(sub_ledger_type_code[k]);
                        System.out.println((new StringBuilder()).append("_voucher_no -->").append(_voucher_no).toString());
                        System.out.println((new StringBuilder()).append("_account_head_code--->").append(_account_head_code).toString());
                        System.out.println((new StringBuilder()).append("_project_sl_code--->").append(_project_sl_code).toString());
                        System.out.println((new StringBuilder()).append("_cat_code--->").append(_cat_code).toString());
                        System.out.println((new StringBuilder()).append("_account_head_code-->").append(_account_head_code).toString());
                        System.out.println((new StringBuilder()).append("_dr_account_head_code-->").append(_dr_account_head_code).toString());
                        System.out.println((new StringBuilder()).append("_cr_account_head_code-->").append(_cr_account_head_code).toString());
                        System.out.println((new StringBuilder()).append("_centage_amount-->").append(_centage_amount).toString());
                    }
                    catch(Exception e)
                    {
                        System.out.println((new StringBuilder()).append("Error in Data Conversion ").append(e).toString());
                    }
                    
                    int txtVoucher_No=0;
    	            try
    	            {
    	            	rs=st.executeQuery("SELECT VOUCHER_NO FROM FAS_CENTAGE_BEFORE_POST_MST GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_CENTAGE_BEFORE_POST_MST where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+")");
    	                if(rs.next()) 
    	                {
    	                	txtVoucher_No = rs.getInt("VOUCHER_NO");                		                   
    	                }
    	                txtVoucher_No=txtVoucher_No+1;
    	            }
    	            catch(Exception e){System.out.println("exception"+e );}
                    
                    try 
                    {
                        cs = con.prepareCall("call FAS_CENTAGE_CREATE( ?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?,?,?,?,?) ");
                        cs.setInt(1, cmbAcc_UnitCode);
                        cs.setInt(2, cmbOffice_code);
                        cs.setInt(3, txtCB_Month);
                        cs.setInt(4, txtCB_Year);
                        cs.setInt(5, txtVoucher_No);
                        cs.setInt(6, _account_head_code);
                        cs.setString(7, "58");
                        cs.setString(8, _cat_code);
                        cs.setString(9, _category_desc);
                        cs.setDouble(10, _centage_rate);
                        cs.setInt(11, _dr_account_head_code);
                        cs.setDouble(12, _centage_amount);
                        cs.setInt(13, _cr_account_head_code);
                        cs.setString(14, _remarks);
                        cs.setString(15, update_user);
                        cs.registerOutParameter(16, 2);
                        cs.setNull(16,java.sql.Types.NUMERIC);
                        cs.setInt(17, _project_sl_code);//_sub_ledger_type_code
                        cs.setString(18, "2");
                        cs.setString(19, "A");
                        cs.setString(20, "GJV");
                        cs.setString(21, "L");
                        cs.setDate(22, ctdate);
                        cs.setTimestamp(23, ts);
                        cs.execute();
                        //int error_code = cs.getInt(16);
                        int error_code = cs.getBigDecimal(16).intValue();
                        System.out.println((new StringBuilder()).append("Error Code ---->").append(error_code).toString());
                        count += error_code;
                        if(error_code!=0)
                        {         
    	                       System.out.println("redirect");
    	                       sendMessage(response,"The Centage Before Post Master Failed ","ok");
    	                                            
                        }
                       else
                        {  
    	                       double tranAmount=0;                                  
    	                       String cr_dr="",remark="";
    	                       int drAccountHCode=0,subledgerCode=0,subledgerTypeCode=0,crAccountHCode=0,SL_NO=0,vouchNo=0;
    	                    
//                               for(int m=0;m<1;m++) 
//                               {
                            	  
                            		   System.out.println("tis is first grid voucher_no.length::::"+voucher_no.length);
                            		   SL_NO=1;
                                       try{drAccountHCode=Integer.parseInt(dr_account_head_code[k]);}
                                       catch(Exception e){System.out.println("exception in trans "+e);}
                                       cr_dr="DR";
                                       
                                       try{subledgerTypeCode=Integer.parseInt(sub_ledger_type_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                       try{subledgerCode=Integer.parseInt(project_sl_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                       try{tranAmount=Integer.parseInt(centage_amount[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                       remark=remarks[k];
                                                                         
                                       String sql="insert into FAS_CENTAGE_BEFORE_POST_TRN(ACCOUNTING_UNIT_ID, " +
   				                    "   ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
   				                    "   CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, " +
   				                    "   AMOUNT, PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) "+
   				                    "   values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                                   
                                       System.out.println("sql in first"+sql);
                            		   ps=con.prepareStatement(sql);
                                       ps.setInt(1,cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                                       ps.setInt(2,cmbOffice_code);System.out.println(cmbOffice_code);
                                       ps.setInt(3,txtCB_Year);System.out.println(txtCB_Year);
                                       ps.setInt(4,txtCB_Month);System.out.println(txtCB_Month);
                                       ps.setInt(5,txtVoucher_No);System.out.println("txtVoucher_No"+txtVoucher_No);
                                       ps.setInt(6,SL_NO);System.out.println(SL_NO);
                                       ps.setInt(7,drAccountHCode);System.out.println(drAccountHCode);
                                       ps.setString(8,cr_dr);System.out.println(cr_dr);
                                       ps.setInt(9,subledgerTypeCode);System.out.println(subledgerTypeCode);
                                       ps.setInt(10,subledgerCode);System.out.println(subledgerCode);
                                       ps.setDouble(11,tranAmount);System.out.println(tranAmount);
                                       ps.setString(12,remark);System.out.println(remark);
                                       ps.setString(13,update_user);System.out.println(update_user);
                                       ps.setTimestamp(14,ts);System.out.println(ts); 
                                     
                                       ps.executeUpdate(); 
                                   
                                       ps.close();
                            	
                                    	   System.out.println("tis is second grid");
                                    	   SL_NO=2;
                            			   cr_dr="CR";
                            			   try{crAccountHCode=Integer.parseInt(cr_account_head_code[k]);}
                            			   catch(Exception e){System.out.println("exception in trans "+e);}
                            			   try{subledgerTypeCode=Integer.parseInt(sub_ledger_type_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                           try{subledgerCode=Integer.parseInt(project_sl_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                           try{tranAmount=Integer.parseInt(centage_amount[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                           remark=remarks[k];
                                         //  try{vouchNo=Integer.parseInt(voucher_no[m]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                           subledgerTypeCode=0;
                                           subledgerCode=0;
                                           
                                           sql="insert into FAS_CENTAGE_BEFORE_POST_TRN(ACCOUNTING_UNIT_ID, " +
       				                    "   ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
       				                    "   CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, " +
       				                    "   AMOUNT, PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE ) "+
       				                    "   values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                                           
                                           System.out.println("sql in second"+sql);
                                		   ps=con.prepareStatement(sql);
                                           ps.setInt(1,cmbAcc_UnitCode);
                                           ps.setInt(2,cmbOffice_code);
                                           ps.setInt(3,txtCB_Year);
                                           ps.setInt(4,txtCB_Month);
                                           ps.setInt(5,txtVoucher_No);
                                           ps.setInt(6,SL_NO);
                                           ps.setInt(7,crAccountHCode);
                                           ps.setString(8,cr_dr);
                                           ps.setInt(9,subledgerTypeCode);
                                           ps.setInt(10,subledgerCode);
                                           ps.setDouble(11,tranAmount);
                                           ps.setString(12,remark);
                                           ps.setString(13,update_user);
                                           ps.setTimestamp(14,ts);
                                           ps.executeUpdate();
                                         
                                           ps.close();
                            		
                               //}                              
                               System.out.println("b4 commit");
                               con.commit();
                                  }
                        
                        //end of trans
                    }
                    catch(Exception e)
                    {
                        try
                        {
                            con.rollback();
                        }
                        catch(SQLException ee)
                        {
                            System.out.println(ee);
                        }
                        System.out.println((new StringBuilder()).append("I cant insert data").append(e).toString());
                        sendMessage(response, "Centage Creation Failed ", "OK");
                    }
          } 
        }

        try
        {
            if(count == 0)
            {
                con.commit();
                con.setAutoCommit(true);
                sendMessage(response, "Centage Created Sucessfully", "OK");
            } else
            {
                con.rollback();
                sendMessage(response, "Centage Creation Failed ", "OK");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    private void sendMessage(HttpServletResponse response, String msg, String bType)
    {
        try
        {
            System.out.println("inside send msg");
            String url = (new StringBuilder()).append("org/Library/jsps/MessengerOkBack.jsp?message=").append(msg).append("&button=").append(bType).toString();
            response.sendRedirect(url);
        }
        catch(IOException e)
        {
            System.out.println((new StringBuilder()).append("Excep").append(e).toString());
        }
    }

    public Centage_Create()
    {
    }
}
