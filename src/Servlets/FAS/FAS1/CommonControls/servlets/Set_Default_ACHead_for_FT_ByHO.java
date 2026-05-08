package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Set_Default_ACHead_for_FT_ByHO extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

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
        Statement st = null;
        Statement st1 = null;

        int Acc_UnitCode = 0;
        String Module_Type = "MF015";
        String cr_dr_indi = "CR";
        int Acc_Head_Code = 0;

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
               * Get Parameter Values
               */
        try {
            /**Get Accounting Unit ID */
            Acc_UnitCode =
                    Integer.parseInt(request.getParameter("Acc_UnitCode"));
            System.out.println("Set as Default venkat  ---->" + Acc_UnitCode);

            /** Get Account Head Code */
            Acc_Head_Code =
                    Integer.parseInt(request.getParameter("Acc_Head_Code"));
            System.out.println("Account Head code -->" + Acc_Head_Code);

        } catch (Exception e) {
            System.out.println("Can't Retrieve Values " + e);
        }


        /**
               * SQL Query Execution
               */
        try {

            String sql1 =
                "update FAS_OFFICE_BANK_AC_CURRENT set sl_no=0 where CR_DR_TYPE='CR'  and MODULE_ID='MF015' and sl_no=1 and ACCOUNTING_UNIT_ID=" +
                Acc_UnitCode;
            String sql2 =
                "update FAS_OFFICE_BANK_AC_CURRENT set sl_no=1 where ACCOUNTING_UNIT_ID=" +
                Acc_UnitCode +
                " and CR_DR_TYPE='CR' and MODULE_ID='MF015'  and ac_head_code=" +
                Acc_Head_Code;

            System.out.println(sql1);
            System.out.println(sql2);

            con.clearWarnings();
            con.setAutoCommit(false);

            st = con.createStatement();
            System.out.println("Execution Result 1 == " + st);
            st.execute(sql1);

            st1 = con.createStatement();
            System.out.println("Execution Result 2 == " + st1);
            st1.execute(sql2);

            con.commit();
            con.setAutoCommit(true);

        } catch (Exception e) {
            System.out.println("Cant Execute SQL Query " + e);
        }

        out.close();
    }
}
