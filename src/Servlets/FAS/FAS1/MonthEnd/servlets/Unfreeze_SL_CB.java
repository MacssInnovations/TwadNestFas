package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;

import java.sql.Types;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Unfreeze_SL_CB extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);

        PrintWriter out = response.getWriter();

        /**
         * Database Connection
         */

        Connection con = null;
        Statement statement = null;
        String msg = null;

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
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Variables Declaration
         */
        int cmbAcc_UnitCode = 0;
        int txtCB_Year = 0;
        int txtCB_Month = 0;


        /**
         * Get Parameters
         */

        /** Get Accounting Unit Id */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /** Get Cash Book Year */
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

        /** Get Cash Book Month */
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /**
         * Procedure Calling
         */

        CallableStatement cs = null;
        try {
            cs =
  con.prepareCall("call FAS_SL_GL_CB_FREEZE_UNFREEZE (?::numeric,?::numeric,?::numeric,?,?,?::numeric) ");
            cs.setInt(1, cmbAcc_UnitCode);
            cs.setInt(2, txtCB_Year);
            cs.setInt(3, txtCB_Month);
            cs.setString(4, "Unfreeze_SL");
            cs.setString(5, userid);
            cs.registerOutParameter(6, Types.NUMERIC);
            cs.setNull(6, java.sql.Types.NUMERIC);
            cs.execute();
            //int error_code = cs.getInt(6);
            int error_code = cs.getBigDecimal(6).intValue();     
            if (error_code == 0) {
            	
            	//dhana changes on march2012
            	//while on unfreezed CB,PB also unfreezed
            	int prevYr=txtCB_Year-1;
            	int nextyr=txtCB_Year+1;
                PreparedStatement ps1 =con.prepareStatement("delete from FAS_SL_PBSTATUS "+
					"    where  "+
                		"         ACCOUNTING_UNIT_ID = ? "+
                		"   and To_Date((Cashbook_Month "+
                		" ||'-' "+
                		" || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 "+
                		" ||'-' "+
                		" ||?,'mm-yyyy') "+
                		" AND to_date(3 "+
                		" ||'-' "+
                		" ||?,'mm-yyyy')");
                
                ps1.setInt(1, cmbAcc_UnitCode);
                ps1.setInt(2, txtCB_Year);
                ps1.setInt(3, nextyr);
               int s= ps1.executeUpdate();
                if(s>0)
                {
                	 sendMessage(response,"Sub Ledger CB and PB has been Unfreezed Successfully",
                             "ok");
                }
                else
                {
                		sendMessage(response,
                "Sub Ledger Closing Balance has been Unfrozen Successfully But PB is Not Unfreezed",
                "ok");	
                }
                ps1.close();
            	
            	
            	
            	
            }
            else
            {
            	System.out.println("error");
            	sendMessage(response, "SL Unfreeze Failed ", "ok");
            }
        } catch (Exception e) {
            System.out.println(e);
            sendMessage(response, "Unfreeze Failed  " + e, "ok");
        }


    }


    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
