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


public class ListVerifiedGLSL extends HttpServlet 
{
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

       
        int CashBookYear = 0;
        int CashBookMonth = 0;
        String Command = null;
        String xml = null;

        try {

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
        /**
         * Variables Declaration
         */
            ResultSet rs = null;
            PreparedStatement ps = null;

        if (Command.equalsIgnoreCase("loadVerifiedGL")) {

           /* String sql ="select a.accounting_unit_id ,to_char(a.gl_date,'dd/mm/yyyy' )as gl_date,"+
            			"(select accounting_unit_name from fas_mst_acct_units " +
            				"where accounting_unit_id=a.accounting_unit_id) as accounting_unit_name "+
            					" from fas_gl_cb_status a "+
            					" where cashbook_year=? "+
            					" and cashbook_month =? order by a.accounting_unit_id";*/
        	String sql="SELECT a.accounting_unit_id ,"+
							 " TO_CHAR(a.gl_date,'dd/mm/yyyy' )AS gl_date,"+
							 " (SELECT accounting_unit_name "+
							 " FROM fas_mst_acct_units "+
							 " WHERE accounting_unit_id=a.accounting_unit_id "+
							 " ) as accounting_unit_name, "+
							"  sum(b.MONTH_CLOSING_BALANCE) as MONTH_CLOSING_BALANCE "+
							" FROM fas_gl_cb_status a, "+
							" fas_general_ledger_cb b "+
							" where a.accounting_unit_id=b.accounting_unit_id "+
							" and a.cashbook_year=b.year "+
							" and a.cashbook_month=b.month "+
							" and a.GL_STATUS='Y' "+
							" and cashbook_year= ? "+
							" and cashbook_month =? "+ 
							" group by a.accounting_unit_id,a.gl_date "+
							" order by a.accounting_unit_id";
            
            xml = "<response><command>loadVerifiedGL</command>";

            try {
                System.out.println("sql:::::::"+sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, CashBookYear);
                ps.setInt(2, CashBookMonth);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
 xml + "<accunitid>" + rs.getString("accounting_unit_id") +
   "</accunitid>";
                    xml =
 xml + "<accunitname>" + rs.getString("accounting_unit_name") +
   "</accunitname>";
                    xml =
 xml + "<gldate>" + rs.getString("gl_date") +
   "</gldate>";
                    xml =
                    	 xml + "<moncb>" + rs.getString("MONTH_CLOSING_BALANCE") +
                    	   "</moncb>";
              }
                xml = xml + "<flag>Success</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>Failure</flag>";
                System.out.println(e);
            }

            xml = xml + "</response>";
        }
        else if (Command.equalsIgnoreCase("loadVerifiedSL")) {

            String sql ="SELECT a.accounting_unit_id,"+
							  " TO_CHAR(a.sl_date,'dd/mm/yyyy' )AS sl_date, "+
							  " (SELECT accounting_unit_name "+
							  " FROM fas_mst_acct_units "+
							  " WHERE accounting_unit_id=a.accounting_unit_id "+
							  " )                            AS accounting_unit_name, "+
							  " SUM(b.month_closing_balance) AS month_closing_balance "+
							  " FROM fas_sl_cb_status a, "+
								  " FAS_SUB_LEDGER_MASTER_CB b "+
								" WHERE a.accounting_unit_id=b.accounting_unit_id "+
								" AND a.cashbook_year       =b.year "+
								" AND a.cashbook_month      =b.month "+
								" AND a.sl_status           ='Y' "+
								" AND cashbook_year         = ? "+
								" AND cashbook_month        =? "+
								" GROUP BY a.accounting_unit_id, "+
								  " a.sl_date "+
								" ORDER BY a.accounting_unit_id";
            
            xml = "<response><command>loadVerifiedSL</command>";

            try {
                System.out.println("sql sl*******"+sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, CashBookYear);
                ps.setInt(2, CashBookMonth);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
 xml + "<accunitid>" + rs.getString("accounting_unit_id") +
   "</accunitid>";
                    xml =
 xml + "<accunitname>" + rs.getString("accounting_unit_name") +
   "</accunitname>";
                    xml =
 xml + "<sldate>" + rs.getString("sl_date") +
   "</sldate>";
                    xml =
                    	 xml + "<moncb>" + rs.getString("MONTH_CLOSING_BALANCE") +
                    	   "</moncb>";
              }
                xml = xml + "<flag>Success</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>Failure</flag>";
                System.out.println(e);
            }

            xml = xml + "</response>";
        }
        System.out.println(xml);

        out.println(xml);
        out.close();
    }
}
