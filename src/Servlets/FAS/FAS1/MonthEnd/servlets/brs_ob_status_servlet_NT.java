package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
 * Servlet implementation class brs_ob_status_servlet
 */
public class brs_ob_status_servlet_NT extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

       
    
    public brs_ob_status_servlet_NT() {
        super();
       
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
        {
	   

	    Connection con = null;
	    Statement statement = null;
	    ResultSet rst = null, results = null;
	    String obStatus="",nilObStatus="";
	    try {
	        ResourceBundle rs =
	            ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	            
	        String conString = "";

	        String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
	        String strdsn = rs.getString("Config.DSN");
	        String strhostname = rs.getString("Config.HOST_NAME");
	        String strportno = rs.getString("Config.PORT_NUMBER");
	        String strsid = rs.getString("Config.SID");
	        String strdbusername = rs.getString("Config.USER_NAME");
	        String strdbpassword = rs.getString("Config.PASSWORD");

	        conString =
	                strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
	                ":" + strsid.trim();

	        Class.forName(strDriver.trim());
	        con =
	    DriverManager.getConnection(conString, strdbusername.trim(),
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
	     
	    int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0, OfficeCode =
	        0, achcode = 0;
	   
	    String NFinYear = "";

	    java.sql.Date MaxCRdate = null;
	    java.sql.Date MaxDRdate = null;

	    //Start  3/5/2011
	    int jou_count=0;
	    String jou_vo=" -- NIL",cmbBankAccNo=" --NIL";
	    
	   
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

	    String nill_ob = request.getParameter("nill_ob");
	    System.out.println("nill_ob..." + nill_ob);
	    
	    String userid = (String)session.getAttribute("UserId");

	    /** Get Updated Time */
	    long l = System.currentTimeMillis();
	    Timestamp ts = new Timestamp(l);
	    Boolean flag = false, flag2 = false, flag3 = false;
	    try {
	        PreparedStatement pss =
	            con.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	        pss.setInt(1, cmbAcc_UnitCode);
	        rst = pss.executeQuery();
	        if (rst.next()) {
	            OfficeCode = rst.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	        }
	    } catch (Exception e) {
	        System.out.println("Err in office code selection :: " +
	                           e.getMessage());
	    }


	    int txtCB_Month1 = 0;
	  //  txtCB_Month1 = txtCB_Month + 1;
	    System.out.println("b4 cashbookmont1:" + txtCB_Month1);
	    int txtCB_Year1 = 0;

	    if (txtCB_Month == 12) {
	    	txtCB_Month1 = 1;
	        txtCB_Year1 = txtCB_Year + 1;
	    } else {
	        txtCB_Month1 = txtCB_Month + 1;
	        txtCB_Year1 = txtCB_Year;
	    }


	    System.out.println("cashbookmont1:" + txtCB_Month1);
	    System.out.println("txtCB_Year1:" + txtCB_Year1);

	    /** Check Trial Balance Closure table --'FAS_BRS_OB_STATUS_NT'
	     *  If Record exits in FAS_BRS_OB_STATUS_NT table, You cant allow TB to Freeze
	     */
	    int count_1 = 0;
	    try {
	        PreparedStatement ps3 = null;
	        ResultSet rs3 = null;
	        con.setAutoCommit(false);
	        ps3 = con.prepareStatement("select * from FAS_BRS_OB_STATUS_NT where cashbook_year=?  and  cashbook_month= ? and ACCOUNT_NO=? AND (OB_STATUS      ='Y' or NIL_OB_STATUS='Y') ");
	        ps3.setInt(1, txtCB_Year1);
	        ps3.setInt(2, txtCB_Month1);
	        ps3.setString(3, cmbBankAccNo);
	        rs3 = ps3.executeQuery();
	       
	        if (rs3.next()) {
	         
	            count_1++;
	        }
	        if (count_1 > 0) {
	            sendMessage(response,
	                        "Sorry !  You can't Freeze BRS Non-TWAD OB .  it has already been Frozen",
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
	        con.setAutoCommit(false);
	    } catch (Exception e) {
	        System.out.println("excepton::::"+e);
	    }
	    try {

	    	if(nill_ob.equals("N"))// regular ob means obStatus="Y", else nil ob freeze means nilObStatus="Y"
	    	{
	    		obStatus="Y";
	    		nilObStatus="";
	    	}
	    	else
	    	{
	    		obStatus="";
	    		nilObStatus="Y";
	    	}
	    	
	        PreparedStatement ps1 = con.prepareStatement("insert into FAS_BRS_OB_STATUS_NT(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR," +
                "CASHBOOK_MONTH,ACCOUNT_NO,UPDATED_BY_USER_ID,UPDATED_DATE,OB_STATUS,OB_TYPE,NIL_OB_STATUS) values(?,?,?,?,?,?,?,?,?,?)");
	        ps1.setInt(1, cmbAcc_UnitCode);
	        ps1.setInt(2, 0);
	        ps1.setInt(3, txtCB_Year1);
	        ps1.setInt(4, txtCB_Month1);
	        ps1.setString(5, cmbBankAccNo);
	        ps1.setString(6, userid);
	        ps1.setTimestamp(7, ts);
	        ps1.setString(8, obStatus);
	        ps1.setString(9, "NT");
	        ps1.setString(10,nilObStatus);
	      int k=  ps1.executeUpdate();
              if(k>0) {
                 String msg ="BRS Non-TWAD OB Status is Freezed...";
                  con.commit();
                  con.setAutoCommit(true);
                  msg = msg + "<br><br>";
                  sendMessage(response, msg, "ok");
              }
	      


	    } catch (Exception e) {
	        System.out.println("Exception in Query:" + e);
	        try {
	            con.rollback();
	        } catch (SQLException e1) {
	            System.out.println("catch:" + e1);
	        }
	        String msg = "Error in Generating OB";
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
