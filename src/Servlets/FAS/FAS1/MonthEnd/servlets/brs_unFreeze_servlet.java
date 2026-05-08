package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class brs_unFreeze_servlet
 */
public class brs_unFreeze_servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
       
    
    public brs_unFreeze_servlet() {
        super();
       
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
        {
	
	    /**
	     * Variables Declaration
	     */
	    Connection con = null;
	    Statement statement = null;
	    PreparedStatement ps3 = null,ps4=null;
	    ResultSet rs3 = null,rs4=null;
            int count_one=0;

	    /**
	     * Database Connection
	     */
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
	     * Session Checking
	     */

	    response.setContentType(CONTENT_TYPE);
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
	    String sub_q="";
	    int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0;
	    String radTB_status = "",cmbBankAccNo="";
	    if(request.getParameter("hid").equalsIgnoreCase("NonTwad")){
	    	sub_q="FAS_BRS_OB_STATUS_NT";
	    }else if(request.getParameter("hid").equalsIgnoreCase("Twad")){
	    	sub_q="FAS_BRS_OB_STATUS";
	    }
System.out.println(" sub_q    :: "+sub_q);
	    /**
	     * Get Accounting Unit ID
	     */
	    try {
	        cmbAcc_UnitCode =
	                Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    } catch (NumberFormatException e) {
	        System.out.println("exception" + e);
	    }
	    System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


	    txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
	    txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
	    System.out.println("year..." + txtCB_Year);
	    System.out.println("Month..." + txtCB_Month);
	    cmbBankAccNo = request.getParameter("cmbBankAccNo");
	    System.out.println("cmbBankAccNo..." + cmbBankAccNo);
	    
	    String userid = (String)session.getAttribute("UserId");

	    
	    if(txtCB_Month==12)
	    {
	    	txtCB_Year=txtCB_Year+1;
	    	txtCB_Month=1;
	    }
	    else
	    {
	    	txtCB_Year=txtCB_Year;
	    	txtCB_Month=txtCB_Month+1;
	    }

	    
	    long l = System.currentTimeMillis();
	    Timestamp ts = new Timestamp(l);
            try{
                ps4 = con.prepareStatement("select * from FAS_BRS_MONTHLY_CLOSURE where cashbook_year=?  and  cashbook_month= ? and ACCOUNTING_UNIT_ID=? and ACCOUNT_NO=? ");
                ps4.setInt(1, txtCB_Year);
                ps4.setInt(2, txtCB_Month);
                ps4.setInt(3, cmbAcc_UnitCode);
                ps4.setLong(4, Long.parseLong(cmbBankAccNo));
                rs4 = ps4.executeQuery();
             //   System.out.println("success1***********************************************************************");
                if (rs4.next()) {
                    System.out.println("FAS_BRS_MONTHLY_CLOSURE have records,unfreeze brs first::: ");
                    count_one++;
                }
                
            }
            catch(Exception eee) {
                System.out.println("exception::::::::"+eee);
            }
            if(count_one>0) {
                sendMessage(response, "Sorry !  You can't unFreeze OB . BRS Monthly Closure is not unFreezed","ok");
                
                return;
            }
            else
            {
	    int count_1 = 0;
	    try {
	       // RECORDS NOT IN FAS_BRS_MONTHLY_CLOSURE
	        con.setAutoCommit(false);
	        ps3 = con.prepareStatement("select ACCOUNT_NO from "+sub_q+" where cashbook_year="+txtCB_Year+"  and  cashbook_month= "+txtCB_Month+" and ACCOUNT_NO="+Long.parseLong(cmbBankAccNo)+"  and  (OB_STATUS='Y' OR NIL_OB_STATUS='Y')  ");
	       System.out.println("select ACCOUNT_NO from "+sub_q+" where cashbook_year="+txtCB_Year+"  and  cashbook_month= "+txtCB_Month+" and ACCOUNT_NO="+Long.parseLong(cmbBankAccNo)+"  and  (OB_STATUS='Y' OR NIL_OB_STATUS='Y')  ");
	     /*   ps3.setInt(1, txtCB_Year);
	        ps3.setInt(2, txtCB_Month);
	        ps3.setLong(3, Long.parseLong(cmbBankAccNo));*/
	        rs3 = ps3.executeQuery();
	        System.out.println("test ***********************************************************************");
	      while (rs3.next()) {
	    	  
	            System.out.println("3mmmmm >> "+rs3.getLong(1));
	            count_1++;
	        }
	      System.out.println("Count_1"+count_1);
	        if (count_1== 0) {
	            sendMessage(response,
	                        "Sorry !  You can't unFreeze OB .  it has already been unFreezed",
	                        "ok");
	            ps3.close();
	            rs3.close();
	            return;
	        }
	        ps3.close();
	        rs3.close();
	    } catch (Exception e) {
	        System.out.println("Error in BRS OB " + e);
	    }



	    try {

	        /** Variables Declaration */
	        PreparedStatement ps = null;
	        PreparedStatement ps2 = null;
	        String msg = " ";
	    System.out.println("TEST HERE ................................ ");
	        ps =
	    con.prepareStatement("delete from "+sub_q+" where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? ");
	        ps2 =
	    con.prepareStatement("insert into FAS_BRS_OB_STATUS_LOG ( ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR,  CASHBOOK_MONTH , OB_STATUS, OB_UNFREEZE_DATE ,  UPDATED_BY_USER_ID , UPDATED_DATE,ACCOUNT_NO ) values(?,?,?,?,?,?,?,?,?)");
	    
	        ps.setInt(1, cmbAcc_UnitCode);
	        ps.setInt(2, txtCB_Year);
	        ps.setInt(3, txtCB_Month);
	        ps.setString(4, cmbBankAccNo);
	        ps.executeUpdate();
	        ps.close();


	        ps2.setInt(1, cmbAcc_UnitCode);
	        ps2.setInt(2, 0);
	        ps2.setInt(3, txtCB_Year);
	        ps2.setInt(4, txtCB_Month);
	        ps2.setString(5, "Y");
	       // ps2.setTimestamp(6, ts);
	        ps2.setTimestamp(6, ts);
	        ps2.setString(7, userid);
	        ps2.setTimestamp(8, ts);
	        ps2.setString(9, cmbBankAccNo);
	        ps2.executeUpdate();
	        ps2.close();

	        msg =
	    "BRS OB Froze Status is Removed...  ";


	        /** Commit the database */
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
	        System.out.println("Exception in unfreeze " + e);
	        e.printStackTrace();
	        String msg ="UnFreeze OB is Unsuccessful.......";
	        msg = msg + "<br><br>";
	        sendMessage(response, msg, "ok");

	    }

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
