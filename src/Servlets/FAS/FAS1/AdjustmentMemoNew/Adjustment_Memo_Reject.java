package Servlets.FAS.FAS1.AdjustmentMemoNew;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;


import Servlets.Security.classes.UserProfile;
import Servlets.FAS.FAS1.CommonClass.ConvertDate;

/**
 * Servlet implementation class DrawingOfficerDetails
 */
public class Adjustment_Memo_Reject extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
       
     public Adjustment_Memo_Reject() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
			PrintWriter out = response.getWriter();
	   HttpSession session=request.getSession(true);
	    String cmd=request.getParameter("command");
	    ConvertDate cc=new ConvertDate();
	    
	    System.out.println(cmd);
	  
	   
	   
        String xml="";
        try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		
		
		if(cmd.equalsIgnoreCase("add"))
		{
			  
			
			
			xml="<response><command>add</command>";
			
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=cc.ConvertInt(cmbAcc_UnitCode);
			//System.out.println("*********************************************************"+accno);
			String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=cc.ConvertInt(cmbOffice_code);
			//System.out.println("*******************************************************************"+officecode);
			String txtDate[]=request.getParameter("txtDate").split("/");
			int month=cc.ConvertInt(txtDate[1]);
			int year=cc.ConvertInt(txtDate[2]);
			//System.out.println("******************************************************************************"+year);
			String[] cmbAdviceNO=(request.getParameter("cmbAdviceNO")).split("-");
			int no=cc.ConvertInt(cmbAdviceNO[0]);
			int serialNo=cc.ConvertInt(cmbAdviceNO[1]);
			//System.out.println("******************************************************************************"+no);
			String txtReject=request.getParameter("txtReject");
			try
			{   ps1=con.prepareStatement("select ADVICE_TYPE  from FAS_ADJUST_MEMO_MST where CASHBOOK_YEAR='"+year+"' and CASHBOOK_MONTH='"+month+"' and VOUCHER_NO='"+no+"'");
			    rs1=ps1.executeQuery();
			    if(rs1.next())
			    {
			    	
			        if(rs1.getInt("ADVICE_TYPE")==1)
			        {
			    
				ps=con.prepareStatement("update  FAS_ADJUST_MEMO_MST set ACCEPTANCE_STATUS=?,REASON_FOR_REJECT=? where  CASHBOOK_YEAR='"+year+"' and CASHBOOK_MONTH='"+month+"' and VOUCHER_NO='"+no+"' and  FOR_ACCOUNTING_UNIT_ID='"+accno+"'");
				ps.setString(1, "N");
				ps.setString(2,txtReject);
				ps.executeUpdate();
				
				xml=xml+"<flag>success</flag>";
			    }
			        else
			        {
			        	
			        	
						ps=con.prepareStatement("update  FAS_ADJUST_MEMO_TRN set ACCEPTANCE_STATUS=?,REASON_FOR_REJECT=? where  CASHBOOK_YEAR='"+year+"' and CASHBOOK_MONTH='"+month+"' and VOUCHER_NO='"+no+"' and sl_no="+serialNo+" and  FOR_ACCOUNTING_UNIT_ID='"+accno+"'");
						ps.setString(1, "N");
						ps.setString(2,txtReject);
						ps.executeUpdate();
						xml=xml+"<flag>success</flag>";
			        	
			        }
			        
			    }
				
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);	
			
			
		}
		
                
		
		out.write(xml);

}
}
