package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Sub_Ledger_Type_Applicable extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        /**
        * Set Content Type
        */
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);


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
        * Variables Declaration
        */
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;


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
        * Get Account Head Code
        */
        int Account_Head_Code =
            Integer.parseInt(request.getParameter("Account_Head_Code"));
        System.out.println("Account_Head_Code -->" + Account_Head_Code);


        String xml = "";
        String sql = "";
        xml = "<response><command>check_cheque_no</command>";

        sql =
 " select account_head_code from com_mst_account_heads  where sub_ledger_type_applicable ='Y' and SL_MANDATORY='Y' and account_head_code= ? ";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Account_Head_Code);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                i++;
            }

            if (i >= 1) {
                xml = xml + "<flag>Success</flag>";
            } else {
                xml = xml + "<flag>Failure</flag>";
            }

        } catch (Exception e) {
            System.out.println("Failed to Fetch Cheque/DD Details" + e);
            xml = xml + "<flag>Failure</flag>";
        }

        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
        out.close();

    }
}
