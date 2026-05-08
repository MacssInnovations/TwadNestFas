package Servlets.FAS.FAS1.TPA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TPA_Freeze_System
 */
public class TPA_Freeze_System extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TPA_Freeze_System() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;

		try {

			ResourceBundle rs1 = ResourceBundle
			.getBundle("Servlets.Security.servlets.Config");
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
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in connection...." + e);
		}
		ResultSet rs = null, rs1 = null, rs2 = null, rs4 = null;
		CallableStatement cs = null;
		PreparedStatement ps = null, ps1 = null, ps2 = null;
		String xml = "";

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				xml = "<response><command>sessionout</command><flag>sessionout</flag></response>";
				out.println(xml);
				System.out.println(xml);
				out.close();
				return;

			}
			// System.out.println(session);

		} catch (Exception e) {
			// System.out.println("Redirect Error :"+e);
		}
		System.out.println("java");
		String command;
		command = request.getParameter("command");

		session = request.getSession(false);
		String updatedby = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(l);
		System.out.println("got");
		System.out.println("command" + command);
		String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);
		DecimalFormat df=new DecimalFormat("#0.00");
		if (command.equalsIgnoreCase("get")) {

			// String xml="";
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
			int cmbSL_type = 0;
			int addtional_field_value = 0;
			int y = 0,z=0;
			xml = "<response><command>" + command + "</command>";
			
			
				int cmbAcc_UnitCode1 = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				int cashMonth = Integer.parseInt(request.getParameter("cashmonth"));
				int cashYear = Integer.parseInt(request.getParameter("cashyear"));
				
				System.out.println("cashMonth===>"+cashMonth);
				System.out.println("cashYear===>"+cashYear);
				System.out.println("cmbAcc_UnitCode1===>"+cmbAcc_UnitCode1);
				try {
					
					String sql="select * from( (select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,voucher_no,to_char(voucher_date,'dd/MM/yyyy') as voucher_date,trf_accounting_unit_id,tpa_type,reason_for_transfer,AUDIT_VERIFY,to_char(AUDIT_VERIFIED_DATE,'dd/MM/yyyy') as AUDIT_VERIFIED_DATE from fas_tpa_master where (acceptance_status is null or acceptance_status='N') and VERIFY='Y'and AUDIT_VERIFY='Y' and ACCOUNTING_UNIT_ID=? and cashbook_month=? and cashbook_year=? and status='L' ) aa \n"+
						"	left outer join \n"+
						"	(select accounting_unit_id,accounting_unit_name as org_name from \n"+
						"	FAS_MST_ACCT_UNITS) b \n"+
						"	on aa.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID  \n"+
						"	left outer join \n"+
						"	(select accounting_unit_id,accounting_unit_name as trf_name from \n"+
						"	FAS_MST_ACCT_UNITS) c \n"+
						"	on aa.trf_accounting_unit_id=c.ACCOUNTING_UNIT_ID )";
					System.out.println("sql::"+sql);
					ps = con.prepareStatement(sql);
					ps.setInt(1,cmbAcc_UnitCode1);
					ps.setInt(2, cashMonth);
					ps.setInt(3, cashYear);
					rs = ps.executeQuery();


					while (rs.next()) {
						int voucherNo=rs.getInt("VOUCHER_NO");
						
						
						String tpaType="";
						if(rs.getString("tpa_type").equalsIgnoreCase("TPAOC"))
							tpaType="CR";
						else if(rs.getString("tpa_type").equalsIgnoreCase("TPAOD"))		
							tpaType="DR";
						
						System.out.println();
						
						ps2 = con.prepareStatement("select  *  from FAS_TPA_STATUS where ACCOUNTING_UNIT_ID=?  and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE=? and REASON_FOR_TRANSFER=? and TPA_VR_NO=?");
						ps2.setInt(1, rs.getInt("ACCOUNTING_UNIT_ID"));
						
						ps2.setInt(2, cashYear);
						ps2.setInt(3, cashMonth);
						ps2.setString(4, tpaType);
						ps2.setString(5, rs.getString("REASON_FOR_TRANSFER"));
						ps2.setInt(6,voucherNo);
						
						rs2=ps2.executeQuery();
						if(!rs2.next())
						{
							System.out.println(xml);
						xml = xml + "<vno>" +voucherNo + "</vno>";
						xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
						xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
						xml = xml + "<org_name>" + rs.getString("org_name")+ "</org_name>";
						xml = xml + "<org_unitid>" + rs.getInt("ACCOUNTING_UNIT_ID")+ "</org_unitid>";
						xml = xml + "<org_officeid>" + rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+ "</org_officeid>";
						xml = xml + "<trf_name>" + rs.getString("trf_name")+ "</trf_name>";
						xml = xml + "<tpatype>" +tpaType+ "</tpatype>";
						xml = xml + "<audit_verify>" + rs.getString("AUDIT_VERIFY")+ "</audit_verify>";
						xml = xml + "<audit_verified_date>" + rs.getString("AUDIT_VERIFIED_DATE")+ "</audit_verified_date>";
						
						
						ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=?  and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
						ps1.setInt(1, rs.getInt("ACCOUNTING_UNIT_ID"));
						
						ps1.setInt(2, cashYear);
						ps1.setInt(3, cashMonth);
						ps1.setInt(4, voucherNo);
						rs1=ps1.executeQuery();
						rs1.next();
						xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";
						
						y++;
						
						}
						
						
						

						
					}
					ps.close();
					rs.close();
					
					
					
					
					if (y != 0 ) {
						xml = xml + "<flag>success</flag>";
					
					}else
						xml = xml + "<flag>failure</flag>";

					ps.close();
					rs.close();
				} catch (Exception e) {
					System.out.println("catch..HERE.in load supplier." + e);
					xml = xml + "<flag>failure</flag>";
				}

				xml = xml + "</response>";
				System.out.println(xml);
				out.println(xml);

			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 /**
	       * Session Checking 
	      */
		 PrintWriter out = response.getWriter();
	      HttpSession session=request.getSession(false);
	      try
	      {	           
              if(session==null)
              {
                       System.out.println(request.getContextPath()+"/index.jsp");
                       response.sendRedirect(request.getContextPath()+"/index.jsp");
                       return;
              }
              System.out.println(session);
	               
	      }catch(Exception e)
	      {
              System.out.println("Redirect Error :"+e);
	      }
	      
	     
	      /**
	       * Variables Declaration 
	      */		        
	      Connection con=null;
	      PreparedStatement ps2=null,ps=null;        
	      ResultSet rs2=null,rs=null;
	      String sql=null;      
	      /**
	       * Database Connection 
	      */
	      try
	      {
              ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
              String ConnectionString="";
              String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
              String strdsn=rs1.getString("Config.DSN");
              String strhostname=rs1.getString("Config.HOST_NAME");
              String strportno=rs1.getString("Config.PORT_NUMBER");
              String strsid=rs1.getString("Config.SID");
              String strdbusername=rs1.getString("Config.USER_NAME");
              String strdbpassword=rs1.getString("Config.PASSWORD");
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
      }
      catch(Exception e)
      {
          	  System.out.println("Exception in opening connection :"+e);
      }
      response.setContentType(CONTENT_TYPE);
      int cmbAcc_UnitCode=0,cmbOffice_code=0,count=0,authorisedUnit=0,cashYear=0,cashMonth=0;
      String particulars=null;
     
      String update_user=(String)session.getAttribute("UserId");
      long l=System.currentTimeMillis();
      Timestamp ts=new Timestamp(l);                      
  	String seq_no[]=null;  
      
      
      

		/* Get User ID */ 
		



		try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
		System.out.println("cashbookYear "+cashYear);

		try{cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
		System.out.println("cashbookMonth "+cashMonth);

		String verifyDate=request.getParameter("tpadate");

		//----------------------------------------------------------------------------------------------//
		/* Get voucher Number from Selected Check Box */         


		/* Get Office Accounting Unit ID */          
		try {
			seq_no = request.getParameterValues("chckparameter");  
		}
		catch (Exception e) {
			System.out.println("Error getting Accounting Unit ID "+e);
		}
		
		String Grid_H_code[] = request.getParameterValues("org_unit");
		String Grid_H_office[] = request.getParameterValues("officeid");
		String Grid_crdr[] = request.getParameterValues("crdr");
		String Grid_reason[] = request.getParameterValues("reason");
		String vr_no[] = request.getParameterValues("vouchno");
		String vr_date[] = request.getParameterValues("vrdate");
     
   String xml="<response>";
   xml=xml+"<command>Add</command>";
      
   
   
   
   
   int k=0;
    
   int j=0;
   try{
	/** Iterate selected Records and make status true if it matches */
	for(k=0;k<seq_no.length;k++)
	{
	j=Integer.parseInt(seq_no[k]);
		System.out.print("Voucher No"+seq_no[k]);
		
			 
			 
		     ps=con.prepareStatement("insert into FAS_TPA_STATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,TPA_STATUS,TPA_FREEZE_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,TPA_TYPE,REASON_FOR_TRANSFER,TPA_VR_NO,TPA_VR_DATE) values(?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,to_date(?,'dd/MM/yyyy')) ") ;
		      ps.setInt(1, Integer.parseInt(Grid_H_code[j]));
		      ps.setInt(2, Integer.parseInt(Grid_H_office[j]));
		      ps.setInt(3, cashYear);
		      ps.setInt(4, cashMonth);
		      ps.setString(5, "Y");
		      ps.setString(6, verifyDate);
		      ps.setString(7, update_user);
		      ps.setTimestamp(8, ts);
		      ps.setString(9, Grid_crdr[j]);
		      ps.setString(10, Grid_reason[j]);
		      ps.setInt(11, Integer.parseInt(vr_no[j]));
		      ps.setString(12, vr_date[j]);
		    
		      ps.execute();
		     
		     
		      
		     


	} 	
	 xml+="<flag>success</flag>";
   
   }catch(Exception e)
   {
   	System.out.print("Exce"+e);
    sendMessage(response,"TPA Freezed Failed ", "ok");
   	 xml+="<flag>error</flag>";
   }
   
   sendMessage(response,"TPA Freezed Successfully ", "ok");
	
     
      
      
      xml=xml+"</response>";
      System.out.println(xml);
      out.println(xml);
      out.close(); 
      
      
	}

	private void sendMessage(HttpServletResponse response, String msg,String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
		}
	}

}
