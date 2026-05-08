package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class FreezeSJVNilTB extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

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
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Variables Declaration
         */

        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0;
        String radTB_status = "";


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

        /** Get YES or NO Status */
        radTB_status = request.getParameter("radTB_status");
        System.out.println("radTB_status..." + radTB_status);

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        /** Get Supplement Number */
        int Supplement_No = 0;
        try {
            Supplement_No =
                    Integer.parseInt(request.getParameter("txtsupplement_no"));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {


            con.setAutoCommit(false);
            PreparedStatement ps = null;
            String msg = " ";
            int count = 0;

            /** For Yes Condition */

            if (radTB_status.equalsIgnoreCase("Y")) {

            	ps =con.prepareStatement("select 'X' from FAS_TRIAL_BALANCE_SUPPLEMENT " +
            			                       "WHERE ACCOUNTING_UNIT_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? and SUPPLEMENT_NO = ?" +
            			                       "HAVING SUM(CURRENT_MONTH_DEBIT)!=SUM(CURRENT_MONTH_CREDIT) AND SUM(CURRENT_MONTH_DEBIT) >0 " +
            			                       "AND SUM(CURRENT_MONTH_CREDIT)>0");
            			                    ps.setInt(1, cmbAcc_UnitCode);
            			                    ps.setInt(2, txtCB_Year);
            			                    ps.setInt(3, txtCB_Month);
            			                    ps.setInt(4, Supplement_No);
            			                    
            			                    ResultSet res = ps.executeQuery();
            			                    if (res.next()) // if the row doesn't return by the query
            			                    {
            			                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%3");
            			                        sendMessage(response, "Trial Balance doesn't Tally",
            			                                    "ok");
            			                        System.out.println("Trial Balance doesn't Tally");
            			                        return;
            			                    }
            			                    res.close();
            			                    ps.close();
            	
            	
            	
            	
                ps =
  con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS_SJV where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?  and SUPPLEMENT_NO = ? and NIL_TB_STATUS='Y' ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, txtCB_Year);
                ps.setInt(3, txtCB_Month);
                ps.setInt(4, Supplement_No);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    msg = "Nil Supplement Trial Balance Arleady Froze";
                } else {

                	PreparedStatement pss= con.prepareStatement("SELECT COUNT(*) AS jrl_count FROM FAS_JOURNAL_MASTER WHERE ACCOUNTING_UNIT_ID=? AND CASHBOOK_YEAR=? and CASHBOOK_MONTH=? AND SUPPLEMENT_NO=? AND JOURNAL_STATUS='L' AND CREATED_BY_MODULE ='SJV'");
                	pss.setInt(1, cmbAcc_UnitCode);
                	pss.setInt(2, txtCB_Year);
                    pss.setInt(3, txtCB_Month);
                    pss.setInt(4, Supplement_No);
                    ResultSet rs_trs = pss.executeQuery();
                	
                	if(rs_trs.next()) {
                	
                		count = rs_trs.getInt("jrl_count");
                			}
                	if(count==0)
                	{
                		PreparedStatement ps1 =
                                con.prepareStatement("insert into FAS_TRIAL_BALANCE_STATUS_SJV(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,TB_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,SUPPLEMENT_NO, NIL_TB_STATUS) values(?,?,?,?,?,?,?,?,?)");
                            ps1.setInt(1, cmbAcc_UnitCode);
                            ps1.setInt(2, 0);
                            ps1.setInt(3, txtCB_Year);
                            ps1.setInt(4, txtCB_Month);
                           // ps1.setString(5, "Y");
                            ps1.setTimestamp(5, ts);
                            ps1.setString(6, userid);
                            ps1.setTimestamp(7, ts);
                            ps1.setInt(8, Supplement_No);
                            ps1.setString(9, "Y");

                            ps1.executeUpdate();

                            ps1.close();
                        	
                            msg ="Nil Supplement Trial Balance has been Frozen Successfully.......";
                	}
                	else
                	{
                		msg="Nil_SJV Freeze cannot be done as you have some SJV Entries";
                	}
                }

                ps.close();

            }

            con.commit();
            con.setAutoCommit(true);
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("exception in rollback" + e1);
            }
            String msg =
                "Nil Supplement Trial Balance Status Changeing is Unsuccessful.......";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

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
        }
    }

}
