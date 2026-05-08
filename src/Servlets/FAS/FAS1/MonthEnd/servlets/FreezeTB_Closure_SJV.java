package Servlets.FAS.FAS1.MonthEnd.servlets;
/**
 * Newly Generate java author By siva.N
 * purpose :Freeze TB closusre for supplement
 * Date    :2016-07-22
 */

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

public class FreezeTB_Closure_SJV extends HttpServlet {
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
        int trial_count=0;
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String suppl_no=request.getParameter("txtsupplement_no");
        int CashbookYear = 0;
        int CashbookMonth = 0;
        int supplement_no=0;
        int s_count=0;

        /**
         * Get Parameters
         *
         */
   System.out.println("test for freeze TB in the supplement");
        try {
            CashbookYear = Integer.parseInt(CashBook_Year);
            System.out.println("CashBook_Year After is:" + CashbookYear);
        } catch (Exception e) {
            System.out.println("Exception in Year:" + e);
            CashbookYear = 0;
        }
        try {
            CashbookMonth = Integer.parseInt(CashBook_Month);
            System.out.println("CashBook Month After is:" + CashbookMonth);
        } catch (Exception e) {
            System.out.println("Exception in Month:" + e);
            CashbookMonth = 0;
        }
        try {
        	supplement_no = Integer.parseInt(suppl_no);
            System.out.println("supplement_no is:" + supplement_no);
        } catch (Exception e) {
            System.out.println("Exception in Supplement_no:" + e);
            CashbookMonth = 0;
        }
         /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
              
        if(CashbookMonth==3 &&  supplement_no > 0){
  		  try {
  			int prevyear_trial=CashbookYear-1;
  		  		//int prevmonth_trial=CashbookMonth-1;
  		      PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
  		                        "  from                        \n" +
  		                        "    FAS_SUP_TB_CLOSURE  \n" +
  		                        "  WHERE                       \n" +
  		                      //  "     ACCOUNTING_UNIT_ID=?     \n" +
  		                        "  CASHBOOK_YEAR=?      \n" +
  		                        "  AND CASHBOOK_MONTH=?");
  		      //ps.setInt(1, cmbAcc_UnitCode);
  		      ps.setInt(1, prevyear_trial);
  		      ps.setInt(2, CashbookMonth);
  		      ResultSet res = ps.executeQuery();
  		      if (res.next()) // if the row doesn't return by the query
  		      {
  		         trial_count++;
  		      }
  		      res.close();
  		      ps.close();
  		  } catch (Exception e) {
  		      System.out.println(e);
  		  }
          }
        
        
        if(trial_count==0)
	        {
	        	 sendMessage(response, "Please Do the Closure for Previous Year ", "ok");
	             return;
	        }  
        
        if (supplement_no >1) {
        	System.out.println("suppl_no check");
        	try {    
        		
        		
      		  		int s_no=supplement_no-1;
      		  		System.out.println("s_no="+s_no);
      		  		PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
      		                        "  from                        \n" +
      		                        "    FAS_SUP_TB_CLOSURE  \n" +
      		                        "  WHERE                       \n" +
      		                     
      		                        "  CASHBOOK_YEAR=?      \n" +
      		                        "  AND CASHBOOK_MONTH=? and supplement_no=?");
      		      
      		      ps.setInt(1, CashbookYear);
      		      ps.setInt(2, CashbookMonth);
      		      ps.setInt(3, s_no);	
      		      ResultSet res = ps.executeQuery();
      		      if (res.next()) // if the row doesn't return by the query
      		      {
      		    	  s_count++;
      		    	
      		      }               
      		      res.close();
      		      ps.close();
      		
      		    	if(s_count==0){
                    sendMessage(response, "Please Do the Closure for  Supplement  No "+s_no+"", "ok");
					return ;
      		    	}   
      		      
      		  } catch (Exception e) {
      		      System.out.println(e);
      		  }
			
		} 
        /*else {			
			try {     			
   		      PreparedStatement ps =con.prepareStatement("  select 'X'                  \n" +
    		                        "  from                        \n" +
    		                        "    FAS_SUP_TB_CLOSURE  \n" +
    		                        "  WHERE                       \n" +
    		                     
    		                        "  CASHBOOK_YEAR=?      \n" +
    		                        "  AND CASHBOOK_MONTH=? and supplement_no=?");
    		      
    		      ps.setInt(1, CashbookYear);
    		      ps.setInt(2, CashbookMonth);
    		      ps.setInt(3, supplement_no);	
    		      ResultSet res = ps.executeQuery();
    		      if (res.next()) // if the row doesn't return by the query
    		      {
    		         s_count++;
    		      }
    		      res.close();
    		      ps.close();
    		  } catch (Exception e) {
    		      System.out.println(e);
    		  }
		}
         */
  		  		
  		  /**
         * Check Whether TB already froze or not
         */
        try {
            PreparedStatement ps = null;
            PreparedStatement pss = null;
            String msg = " ";
            ps =
            con.prepareStatement("select sup_TB_STATUS from FAS_SUP_TB_CLOSURE where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and supplement_no=? ");
            ps.setInt(1, CashbookYear);
            ps.setInt(2, CashbookMonth);
            ps.setInt(3, supplement_no);
            ResultSet rs = ps.executeQuery();	

            if (rs.next()) {
                msg = "Supplement Trial Balance Closure has been Frozen Already ";
                sendMessage(response, msg, "ok");
                return;
            } else {
                /**
                     * Insert one record into FAS_SUP_TB_CLOSURE
                     */
                try {
                    PreparedStatement ps2 =
                        con.prepareStatement("insert into FAS_SUP_TB_CLOSURE(CASHBOOK_YEAR,CASHBOOK_MONTH,SUP_TB_STATUS,SUP_TB_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,SUPPLEMENT_NO) values(?,?,?,?,?,?,?)");
                    ps2.setInt(1, CashbookYear);
                    ps2.setInt(2, CashbookMonth);                    
                    ps2.setString(3, "Y");
                    ps2.setTimestamp(4, ts);
                    ps2.setString(5, userid);
                    ps2.setTimestamp(6, ts);
                    ps2.setInt(7, supplement_no);
                    ps2.executeUpdate();
                    ps2.close();
                } catch (Exception e) {
                    System.out.println("exception in fas_tb_clousure" + e);
                }
                /**
                     * Insert one record in fas_sup_tb_closure_log table
                     */
                try {

                    PreparedStatement ps3 =
                        con.prepareStatement("insert into FAS_SUP_TB_CLOSURE_LOG(CASHBOOK_YEAR,CASHBOOK_MONTH,SUP_TB_STATUS,SUP_TB_FREEZE_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,SUPPLEMENT_NO ) values(?,?,?,?,?,?,?)");
                    ps3.setInt(1, CashbookYear);
                    ps3.setInt(2, CashbookMonth);                   
                    ps3.setString(3, "Y");
                    ps3.setTimestamp(4, ts);
                    ps3.setString(5, userid);
                    ps3.setTimestamp(6, ts);
                    ps3.setInt(7, supplement_no);
                    ps3.executeUpdate();
                    ps3.close();
                } catch (Exception e) {
                    System.out.println("exception in fas_sup_tb_closure_log" + e);
                }
            }
            ps.close();
            con.commit();
            con.setAutoCommit(true);

            msg = "Supplement Trial Balance Closure has been Frozen Successfully.......";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("exception in rollback" + e1);
            }
            System.out.println("Exception in Trial balance " + e);
            String msg =
                "Supplement Trial Balance Status Changeing is Unsuccessful.......";
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
            System.out.println(e);
        }
    }

}
