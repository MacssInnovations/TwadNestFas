package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Accept_Report_Freeze
 */
public class Accept_Report_Freeze extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1,result = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
	
    public Accept_Report_Freeze() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		


		PrintWriter out = response.getWriter();
    
	String cmd=request.getParameter("command");
    String xml="";
    int count=0;
    int cmbAcc_UnitCode = 0,cmbOffice_code=0;
	
    
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
	try
    {
         cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    } 
    catch (Exception e) 
    {
        System.out.println("Exception to catch cmbAcc_UnitCode ");
    }
    try
    {
        cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
    }
    catch (Exception e) 
    {
        System.out.println("Exception to catch cmbOffice_code ");
    } 
    int year=Integer.parseInt(request.getParameter("txtCB_Year"));
	int month=Integer.parseInt(request.getParameter("txtCB_Month"));System.out.println("cash book month------"+month);
	
	 if(cmd.equalsIgnoreCase("check"))
	{
		xml="<response><command>check</command>";
		
		try
					{
								
						
							ps=con.prepareStatement("SELECT t.VOUCHER_NO,T.SL_NO "+
										"	FROM FAS_ADJUST_MEMO_MST M,FAS_ADJUST_MEMO_TRN T"+
									 	"	WHERE M.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID"+
										" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID"+
										" AND M.CASHBOOK_YEAR=T.CASHBOOK_YEAR"+
										" 	AND M.CASHBOOK_MONTH=T.CASHBOOK_MONTH"+
										" 	AND M.VOUCHER_NO=T.VOUCHER_NO"+
										" 	AND M.MEMO_STATUS='L'"+
										" 	AND T.CASHBOOK_YEAR        ="+year+
										" 	AND T.CASHBOOK_MONTH         ="+month+
										" 	AND T.FOR_ACCOUNTING_UNIT_ID ="+cmbAcc_UnitCode+
										" 	AND T.ADVICE_TRANSFER_FREEZE='N' "+
										" 	AND t.ACCEPT_VOUCHER_NO     IS NOT NULL");
							result=ps.executeQuery();
							while(result.next())
							{
								System.out.println("inside transac");
								xml=xml+"<memono>"+result.getInt("VOUCHER_NO")+"</memono>";	
								xml=xml+"<slno>"+result.getInt("SL_NO")+"</slno>";
								count++;
						   	}
							
							if(count>0)
								xml = xml + "<flag>success</flag>";	
							else
							{
								xml = xml + "<flag>Nodata</flag>";
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
	 else if(cmd.equalsIgnoreCase("updated"))
{
		 xml="<response><command>updated</command>"; 
			try
			{
			 
		     int adv=Integer.parseInt(request.getParameter("vou_no"));
		     int slno=Integer.parseInt(request.getParameter("slno"));
		     System.out.println("next updation");                                                                                                                       
		
			      ps=con.prepareStatement("update FAS_ADJUST_MEMO_TRN set ADVICE_TRANSFER_FREEZE='Y' where CASHBOOK_YEAR='"+year+"' and " +
			      	" CASHBOOK_MONTH='"+month+"' and FOR_ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and ADVICE_TRANSFER_FREEZE='N' " +
			      	" and ACCEPT_VOUCHER_NO is not null and VOUCHER_NO='"+adv+"' and SL_NO='"+slno+"'");
			      int upd=ps.executeUpdate();                
		          
				   	if(upd>0)     					
				   		xml = xml + "<flag>success</flag>";
				   	else
				   		xml = xml + "<flag>failure</flag>";
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
    out.close();

	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
