package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class SL_GL_CB_Freeze extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        /**
         * Variable Declaration
         */

        int AccountUnitCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        String Command = null;
        String xml = null;

        try {

            AccountUnitCode =
                    Integer.parseInt(request.getParameter("accounting_unit_id"));
            CashBookYear =
                    Integer.parseInt(request.getParameter("cashbook_year"));
            CashBookMonth =
                    Integer.parseInt(request.getParameter("cashbook_month"));
            Command = request.getParameter("Command");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        /**
         * Database Connection
         */

        Connection con = null;
        Statement statement = null;

        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                statement = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing con:" + e);
        }


        /**
         * Content Type Setting
         */

        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
         */
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }


        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String update_user = (String)session.getAttribute("UserId");

        if (Command.equalsIgnoreCase("Ledger_Details")) {

            String sql =
                "           select \n" + "              gl_account_head_code, \n" +
                "              gl_month_closing_balance, \n" +
                "              gl_month_closing_bal_dr_cr_ind, \n" +
                "              sl_account_head_code, \n" +
                "              sl_month_closing_balance, \n" +
                "              sl_month_closing_bal_dr_cr_ind, \n" +
                "              account_head_code, \n" +
                "              account_head_desc,              \n" +
                "              sl_month_closing_balance_nn,              \n" +
                "            case \n" +
                "               when gl_month_closing_balance >=0 and sl_month_closing_balance >=0 and gl_month_closing_balance != sl_month_closing_balance then \n" +
                "                'RED'\n" +
                "               when sl_month_closing_balance_nn ='NO AMOUNT'  then \n" +
                "                'BLACK'\n" + "               else \n" +
                "                'GREEN'\n" + "                \n" +
                "            end as tally_status\n" + "              \n" +
                "            from \n" + "            (\n" +
                "            select \n" +
                "              gl_account_head_code, \n" +
                "              gl_month_closing_balance, \n" +
                "              gl_month_closing_bal_dr_cr_ind, \n" +
                "              sl_account_head_code, \n" +
                "              sl_month_closing_balance, \n" +
                "              sl_month_closing_bal_dr_cr_ind, \n" +
                "              account_head_code, \n" +
                "              account_head_desc,              \n" +
                "              nvl(to_char(sl_month_closing_balance),'NO AMOUNT') sl_month_closing_balance_nn \n" +
                "              \n" + "            from \n" +
                "            (\n" + "            \n" +
                "             select                \n" +
                "              *                    \n" +
                "             from                  \n" +
                "             (                     \n" +
                "             select                \n" +
                "                account_head_code as  gl_account_head_code,                    \n" +
                "                month_closing_balance as gl_month_closing_balance,             \n" +
                "                month_closing_bal_dr_cr_ind  as gl_month_closing_bal_dr_cr_ind \n" +
                "             from                                                              \n" +
                "                 fas_general_ledger_cb              \n" +
                "             where                                  \n" +
                "                 accounting_unit_id=?               \n" +
                "             and month=?                               \n" +
                "             and year=?             \n" +
                "             ) a                    \n" +
                "                                    \n" +
                "             left outer join        \n" +
                "                             \n" +
                "             (               \n" +
                "             select          \n" +
                "             account_head_code as sl_account_head_code,        \n" +
                "             case when (cr_amt - dr_amt ) < 0 then             \n" +
                "               (cr_amt - dr_amt ) * -1                         \n" +
                "             else                                              \n" +
                "               (cr_amt - dr_amt )                              \n" +
                "             end as  sl_month_closing_balance ,                \n" +
                "                                                               \n" +
                "             case when (cr_amt - dr_amt ) < 0 then             \n" +
                "               'DR'                                  \n" +
                "             else                                    \n" +
                "               'CR'                                  \n" +
                "             end as sl_month_closing_bal_dr_cr_ind   \n" +
                "                                                     \n" +
                "             from                 \n" +
                "             (                    \n" +
                "             select               \n" +
                "                   account_head_code, \n" +
                "                   sum(case when month_closing_bal_dr_cr_ind='CR' then month_closing_balance else 0 end ) as cr_amt, \n" +
                "                   sum(case when month_closing_bal_dr_cr_ind='DR' then month_closing_balance else 0 end ) as dr_amt   \n" +
                "             from                            \n" +
                "                   fas_sub_ledger_master_cb  \n" +
                "             where                           \n" +
                "                 accounting_unit_id=?        \n" +
                "             and month=?                     \n" +
                "             and year=?                    \n" +
                "             group by account_head_code      \n" +
                "             )        \n" + "             )b       \n" +
                "             on       \n" +
                "              a.gl_account_head_code=b.sl_account_head_code           \n" +
                "               left outer join             \n" +
                "               (\n" +
                "                 select account_head_code , account_head_desc from com_mst_account_heads\n" +
                "               )c\n" + "                \n" +
                "               on a.gl_account_head_code=c.account_head_code    \n" +
                "             order by a.gl_account_head_code , b.sl_account_head_code  \n" +
                "             \n" + "          )\n" + "        )\n" +
                "          \n" + "          \n" + "              ";


            /**
         * Variables Declaration
         */
            ResultSet rs = null;
            PreparedStatement ps = null;
            int reccount=0;

            xml = "<response><command>Disp_Ledger_Details</command>";

            try {
                System.out.println("sql:::::::"+sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, AccountUnitCode);
                ps.setInt(2, CashBookMonth);
                ps.setInt(3, CashBookYear);
                ps.setInt(4, AccountUnitCode);
                ps.setInt(5, CashBookMonth);
                ps.setInt(6, CashBookYear);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
 xml + "<gl_account_head_code>" + rs.getString("gl_account_head_code") +
   "</gl_account_head_code>";
                    xml =
 xml + "<account_head_desc>" + rs.getString("account_head_desc") +
   "</account_head_desc>";
                    xml =
 xml + "<gl_month_closing_balance>" + rs.getString("gl_month_closing_balance") +
   "</gl_month_closing_balance>";
                    xml =
 xml + "<gl_month_closing_bal_dr_cr_ind>" + rs.getString("gl_month_closing_bal_dr_cr_ind") +
   "</gl_month_closing_bal_dr_cr_ind>";
                    xml =
 xml + "<sl_account_head_code>" + rs.getString("sl_account_head_code") +
   "</sl_account_head_code>";
                    xml =
 xml + "<sl_month_closing_balance>" + rs.getString("sl_month_closing_balance") +
   "</sl_month_closing_balance>";
                    xml =
 xml + "<sl_month_closing_bal_dr_cr_ind>" + rs.getString("sl_month_closing_bal_dr_cr_ind") +
   "</sl_month_closing_bal_dr_cr_ind>";
                    xml =
 xml + "<tally_status>" + rs.getString("tally_status") + "</tally_status>";

                    reccount++;
                }
             //  xml = xml + "<flag>Success</flag>";
                if(reccount>1)
                     xml = xml + "<flag>Success</flag>";
                else if(reccount<1)
                xml = xml + "<flag>NoData</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>Failure</flag>";
                System.out.println(e);
            }

            xml = xml + "</response>";


        }
        
        //dhana changes for super user role only
        if (Command.equalsIgnoreCase("Ledger_Details_superUser_role")) {

            String sql ="    select  "+
            "  gl_account_head_code, "+ 
            	" gl_month_closing_balance,  "+
            	" gl_month_closing_bal_dr_cr_ind,  "+
            	" sl_account_head_code,  "+
            	" sl_month_closing_balance,  "+
            	" sl_month_closing_bal_dr_cr_ind,  "+
            	" Account_Head_Code,  "+
            	" Case When Account_Head_Desc Is Null Then Desc1 Else Account_Head_Desc End As Account_Head_Desc, "+
            	"   sl_month_closing_balance_nn,            "+   
            	" case  "+
            	" when gl_month_closing_balance >=0 and sl_month_closing_balance >=0 and gl_month_closing_balance != sl_month_closing_balance then "+ 
            	" 'RED' "+
            	" when sl_month_closing_balance_nn ='NO AMOUNT'  then  "+
            	" 'BLACK' When gl_month_closing_balance is null and sl_month_closing_balance>=0 then 'RED' "+
            	" else  "+
            	" 'GREEN' "+
            	"  end as tally_status "+
            	"  from  "+
            	" ( "+
            	"   select  "+
            	"    gl_account_head_code,  "+
            	"   gl_month_closing_balance,  "+
            	" gl_month_closing_bal_dr_cr_ind,  "+
            	"    sl_account_head_code,  "+
            	"    sl_month_closing_balance,  "+
            	"    sl_month_closing_bal_dr_cr_ind,  "+
            	"   Account_Head_Code,  "+
            	"    account_head_desc, desc1,     "+         
            	"   nvl(to_char(sl_month_closing_balance),'NO AMOUNT') sl_month_closing_balance_nn  "+
            	"   from  "+
            	" ( "+
            	"  select           "+      
            	"   *              "+       
            	"   from              "+     
            	"  (                     "+ 
            	"  select                 "+
            	"  account_head_code as  gl_account_head_code, "+                    
            	"  month_closing_balance as gl_month_closing_balance, "+             
            	"   month_closing_bal_dr_cr_ind  as gl_month_closing_bal_dr_cr_ind "+ 
            	"  from                                                               "+
            	"     fas_general_ledger_cb               "+
            	"  Where                                   "+
            	"      Accounting_Unit_Id=   ?             "+
            	"  And Month=        ?                       "+
            	"  and year=     ?         "+
            	"  ) a                     "+
              "  full outer join         "+
            	"  (                "+
            	"  select           "+
            	" account_head_code as sl_account_head_code, "+        
            	"  case when (cr_amt - dr_amt ) < 0 then        "+      
            	"    (cr_amt - dr_amt ) * -1                       "+   
            	"  else                                               "+
            	"    (cr_amt - dr_amt )                               "+
            	"  end as  sl_month_closing_balance ,                 "+
              "  case when (cr_amt - dr_amt ) < 0 then   "+           
            	"    'DR'                     "+              
            	"  else                    "+                 
            	"    'CR'                                   "+
            	"  end as sl_month_closing_bal_dr_cr_ind,desc1 "+
            	"  from                  "+
            	"  (                     "+
            	"  select                "+
            	"    Account_Head_Code, "+
            	"     (select h.account_head_desc from com_mst_account_heads h where h.account_head_code=cc.account_head_code)as desc1, "+
            	"    sum(case when month_closing_bal_dr_cr_ind='CR' then month_closing_balance else 0 end ) as cr_amt,  "+
            	"    sum(case when month_closing_bal_dr_cr_ind='DR' then month_closing_balance else 0 end ) as dr_amt    "+
            	"  From    "+                         
            	"        fas_sub_ledger_master_cb  cc "+
            	"  Where                        "+    
            	"      Accounting_Unit_Id=?     "+    
            	"  And Month=         ? "+            
            	"  and year=       ?"+              
            	"  group by Account_Head_Code "+
            	"   )         "+
            	"   )b        "+
            	"  on        "+
            	"  a.gl_account_head_code=b.sl_account_head_code      "+      
            	"   left outer join              "+
             "   ( "+
            	"    select account_head_code , account_head_desc from com_mst_account_heads "+
            	"   )c "+
            	"   on a.gl_account_head_code=c.account_head_code     "+
            	"  order by a.gl_account_head_code , b.sl_account_head_code   "+
            	"  ) "+
            	" )   ";


            /**
         * Variables Declaration
         */
            ResultSet rs = null;
            PreparedStatement ps = null;
            int reccount=0;

            xml = "<response><command>Ledger_Details_superUser_role</command>";

            try {
                System.out.println("sql super user role:::"+sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, AccountUnitCode);
                ps.setInt(2, CashBookMonth);
                ps.setInt(3, CashBookYear);
                ps.setInt(4, AccountUnitCode);
                ps.setInt(5, CashBookMonth);
                ps.setInt(6, CashBookYear);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
 xml + "<gl_account_head_code>" + rs.getString("gl_account_head_code") +
   "</gl_account_head_code>";
                    xml =
 xml + "<account_head_desc>" + rs.getString("account_head_desc") +
   "</account_head_desc>";
                    xml =
 xml + "<gl_month_closing_balance>" + rs.getString("gl_month_closing_balance") +
   "</gl_month_closing_balance>";
                    xml =
 xml + "<gl_month_closing_bal_dr_cr_ind>" + rs.getString("gl_month_closing_bal_dr_cr_ind") +
   "</gl_month_closing_bal_dr_cr_ind>";
                    xml =
 xml + "<sl_account_head_code>" + rs.getString("sl_account_head_code") +
   "</sl_account_head_code>";
                    xml =
 xml + "<sl_month_closing_balance>" + rs.getString("sl_month_closing_balance") +
   "</sl_month_closing_balance>";
                    xml =
 xml + "<sl_month_closing_bal_dr_cr_ind>" + rs.getString("sl_month_closing_bal_dr_cr_ind") +
   "</sl_month_closing_bal_dr_cr_ind>";
                    xml =
 xml + "<tally_status>" + rs.getString("tally_status") + "</tally_status>";

                    reccount++;
                }
             //  xml = xml + "<flag>Success</flag>";
                if(reccount>1)
                     xml = xml + "<flag>Success</flag>";
                else if(reccount<1)
                xml = xml + "<flag>NoData</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>Failure</flag>";
                System.out.println(e);
            }

            xml = xml + "</response>";


        }
        

        if (Command.equalsIgnoreCase("SL_Freeze")) {

            /**
         * Variables Declaration
         */

            CallableStatement cs2 = null;
            xml = "<response><command>SL_Freeze</command>";
            try {

                cs2 =
 con.prepareCall("{ call FAS_SL_GL_CB_FREEZE_UNFREEZE(?,?,?,?,?,?)}");
                cs2.setInt(1, AccountUnitCode);
                cs2.setInt(2, CashBookYear);
                cs2.setInt(3, CashBookMonth);
                cs2.setString(4, "SL_Freeze");
                cs2.setString(5, update_user);
                cs2.registerOutParameter(6, java.sql.Types.NUMERIC);
                cs2.execute();
                int errcode = cs2.getInt(6);
                if (errcode == 0) {
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>Failure</flag>";
            }

            xml = xml + "</response>";
        }


        if (Command.equalsIgnoreCase("GL_Freeze")) {

            /**
              * Variables Declaration
              */

            CallableStatement cs2 = null;
            xml = "<response><command>GL_Freeze</command>";
            try {

                cs2 =
 con.prepareCall("{ call FAS_SL_GL_CB_FREEZE_UNFREEZE(?,?,?,?,?,?)}");
                cs2.setInt(1, AccountUnitCode);
                cs2.setInt(2, CashBookYear);
                cs2.setInt(3, CashBookMonth);
                cs2.setString(4, "GL_Freeze");
                cs2.setString(5, update_user);
                cs2.registerOutParameter(6, java.sql.Types.NUMERIC);
                cs2.execute();
                int errcode = cs2.getInt(6);
                if (errcode == 0) {
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>Failure</flag>";
            }

            xml = xml + "</response>";
        }


        if (Command.equalsIgnoreCase("Status_Check")) {

            /**
                   * Variables Declaration
                   */

            PreparedStatement ps1 = null;
            ResultSet rs1 = null;
            PreparedStatement ps2 = null;
            ResultSet rs2 = null;

            xml = "<response><command>Status_Check</command>";
            try {


                String sl_sql =
                    " " + "select 'SL'  as status_check from fas_sl_cb_status        \n" +
                    "where                                                     \n" +
                    "    accounting_unit_id=?                                  \n" +
                    "and cashbook_month=?                                      \n" +
                    "and cashbook_year=?                                       \n" +
                    "and sl_status='Y'                                         \n ";

                ps1 = con.prepareStatement(sl_sql);
                ps1.setInt(1, AccountUnitCode);
                ps1.setInt(2, CashBookMonth);
                ps1.setInt(3, CashBookYear);
                rs1 = ps1.executeQuery();
                String SL_Status = "";
                while (rs1.next()) {
                    SL_Status = rs1.getString("status_check");
                }

                if (SL_Status.equalsIgnoreCase("SL")) {
                    xml = xml + "<SL_Status>AlreadyFreeze</SL_Status>";
                } else {
                    xml = xml + "<SL_Status>NotYetFreeze</SL_Status>";
                }


                String gl_sql =
                    "" + "select 'GL' as status_check from fas_gl_cb_status         \n" +
                    "where                                                     \n" +
                    "    accounting_unit_id=?                                  \n" +
                    "and cashbook_month=?                                      \n" +
                    "and cashbook_year=?                                       \n" +
                    "and gl_status='Y'                                         \n";

                ps2 = con.prepareStatement(gl_sql);
                ps2.setInt(1, AccountUnitCode);
                ps2.setInt(2, CashBookMonth);
                ps2.setInt(3, CashBookYear);
                rs2 = ps2.executeQuery();
                String GL_Status = "";
                while (rs2.next()) {
                    GL_Status = rs2.getString("status_check");
                }

                if (GL_Status.equalsIgnoreCase("GL")) {
                    xml = xml + "<GL_Status>AlreadyFreeze</GL_Status>";
                } else {
                    xml = xml + "<GL_Status>NotYetFreeze</GL_Status>";
                }

                xml = xml + "<flag>Success</flag>";

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>Failure</flag>";
            }

            xml = xml + "</response>";
        }


        System.out.println(xml);

        out.println(xml);
        out.close();

    }
}


