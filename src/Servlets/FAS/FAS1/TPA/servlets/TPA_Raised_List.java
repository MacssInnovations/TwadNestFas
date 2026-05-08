package Servlets.FAS.FAS1.TPA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TPA_Raised_List
 */
public class TPA_Raised_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TPA_Raised_List() {
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
				int y = 0;
				xml = "<response><command>" + command + "</command>";
				
				try {
					cmbAcc_UnitCode = Integer.parseInt(request
							.getParameter("cmbAcc_UnitCode"));
				} catch (Exception e) {
					System.out.println("error get acc unit code");
				}
				try {
					cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
				} catch (Exception e) {
					System.out.println("error get office id");
				}
				
				String option=request.getParameter("searchby");
				
				String listType=request.getParameter("listType");
				
				if(option.equalsIgnoreCase("yearmonth")){
				String status_type=null;
				int cashMonth = Integer.parseInt(request.getParameter("cashmonth"));
				int cashYear = Integer.parseInt(request.getParameter("cashyear"));
				String status=request.getParameter("status");
				String type=request.getParameter("proformatype");
				if(type.equals("TPAOC"))
				{
					status_type="CR";
				}
				else
				{
					status_type="DR";
				}
				try {
					System.out.println("listType:::"+listType);
					
					if(listType.equals("or")){
					ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE=? and STATUS=?");
					}
					else if(listType.equals("uv")){
						
						ps = con.prepareStatement("select VOUCHER_NO,to_char(VERIFIED_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE=? and STATUS=? and VERIFY='Y'");
					}
					else if(listType.equals("av")){
					
						ps = con.prepareStatement("select VOUCHER_NO,to_char(AUDIT_VERIFIED_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE=? and STATUS=? and AUDIT_VERIFY='Y' order by VOUCHER_NO");
					}
					else
					{
//						ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS " +
//								"from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?" +
//								" and CASHBOOK_MONTH=? and TPA_TYPE=? and STATUS=? " +
//								"and AUDIT_VERIFY='Y' And Reason_For_Transfer In (Select Reason_For_Transfer From Fas_Tpa_Status " +
//								" Where Accounting_Unit_Id    ="+cmbAcc_UnitCode+" And Cashbook_Year           ="+cashYear+" And Cashbook_Month          ="+cashMonth+" and TPA_TYPE='"+status_type+"' )order by VOUCHER_NO");
						
						ps=con.prepareStatement(" SELECT A.VOUCHER_NO, " +
								"  TO_CHAR(b.TPA_FREEZE_DATE,'dd/MM/yyyy') AS VOUCHER_DATE, " +
								"  A.REASON_FOR_TRANSFER, " +
								"  a.PARTICULARS           " +
								" FROM FAS_TPA_MASTER A, " +
								" Fas_Tpa_Status b " +
								" WHERE  " +
								" A.ACCOUNTING_UNIT_ID = B.ACCOUNTING_UNIT_ID  " +
								" AND A.ACCOUNTING_FOR_OFFICE_ID = B.ACCOUNTING_FOR_OFFICE_ID " +
								" AND A.VOUCHER_NO=B.TPA_VR_NO " +
								" AND A.CASHBOOK_YEAR=B.CASHBOOK_YEAR " +
								" AND A.CASHBOOK_MONTH=B.CASHBOOK_MONTH " +
								" AND A.ACCOUNTING_UNIT_ID    =? " +
								" AND A.ACCOUNTING_FOR_OFFICE_ID=? " +
								" AND A.CASHBOOK_YEAR           =?         " +
								" AND A.CASHBOOK_MONTH          =? " +
								" AND A.TPA_TYPE                =? " +
								" AND A.STATUS                  =?   " +
								" AND A.AUDIT_VERIFY            ='Y' " +
								" And a.Reason_For_Transfer In (Select Reason_For_Transfer From Fas_Tpa_Status " +
								" Where Accounting_Unit_Id    ="+cmbAcc_UnitCode + 
								" And Cashbook_Year           ="+cashYear+
								" And Cashbook_Month          ="+cashMonth+ 
								" and TPA_TYPE='"+status_type+"' )order by VOUCHER_NO");
						
						
						
					}
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, cashYear);
					ps.setInt(4, cashMonth);
					ps.setString(5, type);
					ps.setString(6, status);
					
					rs = ps.executeQuery();
					
					xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
					xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
					xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
					xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";
					
					while (rs.next()) {
						int voucherNo=rs.getInt("VOUCHER_NO");
						xml = xml + "<vno>" +voucherNo + "</vno>";
						xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
						xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
						xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
						ps1 = con.prepareStatement("select  amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301) and sl_no=1");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setInt(3, cashYear);
						ps1.setInt(4, cashMonth);
						ps1.setInt(5, voucherNo);
						rs1=ps1.executeQuery();
						rs1.next();
						xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";
								
						y++;
					}
					if (y != 0) {
						xml = xml + "<flag>success</flag>";
					} else
						xml = xml + "<flag>failure</flag>";

					ps.close();
					rs.close();
				} catch (Exception e) {
					System.out.println("catch..HERE.in load supplier." + e);
					xml = xml + "<flag>failure</flag>";
				}
					
				xml = xml + "</response>";
				 //System.out.println(xml);
				out.println(xml);
			
			}else if(option.equalsIgnoreCase("date"))
					{
				
				
				
				
//				String[] sd=request.getParameter("fromdate").split("/");
//	             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
//	             java.util.Date d=c.getTime();
//	             fromDate=new Date(d.getTime());
//	             System.out.println("from_date "+fromDate);
//	             
//	             sd=request.getParameter("todate").split("/");
//	             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
//	             d=c.getTime();
//	             toDate=new Date(d.getTime());
//	             System.out.println("toDate "+toDate);
	             
	             

	            
				String fromDate = request.getParameter("fromdate");
				System.out.println("fromDate===>"+fromDate);
				
				int  frommonth=Integer.parseInt(fromDate.split("/")[1]);
				System.out.println("frommonth"+frommonth);
				
				int fromyear=Integer.parseInt(fromDate.split("/")[2]);
				System.out.println("fromyear"+fromyear);
				
				String toDate = request.getParameter("todate");
				System.out.println("toDate===>"+toDate);
				
				int tomonth=Integer.parseInt(toDate.split("/")[1]);
				System.out.println("tomonth===>"+tomonth);
				
				int toyear=Integer.parseInt(toDate.split("/")[2]);
				System.out.println("toyear===>"+toyear);
				
				int cashMonth = Integer.parseInt(request.getParameter("cashmonth"));
				System.out.println("cashMonth===>"+cashMonth);
				int cashYear = Integer.parseInt(request.getParameter("cashyear"));
				System.out.println("cashYear===>"+cashYear);
				String status=request.getParameter("status");
				System.out.println("status===>"+status);
				String type=request.getParameter("proformatype");
				System.out.println("type===>"+type);
				
				
				
				
				try {
//changed on 28-12-2017 for datewise data was not loaded 
					//					ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and (VOUCHER_DATE between to_date(?,'dd/MM/yyyy') and to_date(?,'dd/MM/yyyy')) and TPA_TYPE=? and STATUS=?");
				
					ps = con.prepareStatement("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"  and VOUCHER_DATE>=? and VOUCHER_DATE<=? and TPA_TYPE='"+type+"' and STATUS='"+status+"'");
					System.out.println("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"   and VOUCHER_DATE>=? and VOUCHER_DATE<=? and TPA_TYPE='"+type+"' and STATUS='"+status+"'");

					
					//System.out.println("select VOUCHER_NO,to_char(VOUCHER_DATE,'dd/MM/yyyy') as VOUCHER_DATE,REASON_FOR_TRANSFER,PARTICULARS from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"  and (VOUCHER_DATE between to_char('"+fromDate+"' ,'dd/MM/yyyy') and to_char('"+toDate+"','dd/MM/yyyy')) and TPA_TYPE='"+type+"' and STATUS='"+status+"'");
//					ps.setInt(1, cmbAcc_UnitCode);
//					ps.setInt(2, cmbOffice_code);
//					//ps.setInt(3, cashYear);
//					//ps.setInt(4, cashMonth);
					ps.setString(1, fromDate);
					ps.setString(2, toDate);
//					ps.setString(5, type);
//					ps.setString(6, status);
					rs = ps.executeQuery();
					xml = xml + "<unitid>" +cmbAcc_UnitCode+ "</unitid>";
					xml = xml + "<officeid>" + cmbOffice_code+ "</officeid>";
					xml = xml + "<cashyear>" + cashYear+ "</cashyear>";
					xml = xml + "<cashmonth>" +cashMonth+ "</cashmonth>";
					
					
					
					
					
					while (rs.next()) {
						System.out.println("Voucher_No--->"+rs.getInt("VOUCHER_NO"));
						int voucherNo=rs.getInt("VOUCHER_NO");
						xml = xml + "<vno>" +voucherNo + "</vno>";
						xml = xml + "<vdate>" + rs.getString("VOUCHER_DATE")+ "</vdate>";
						xml = xml + "<reason>" + rs.getString("REASON_FOR_TRANSFER")+ "</reason>";
						xml = xml + "<particular>" + rs.getString("PARTICULARS")+ "</particular>";
//						ps1 = con.prepareStatement("select sum(AMOUNT) as amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? "
//								+ " and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301)");
						
						ps1 = con.prepareStatement("select sum(AMOUNT) as amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? "
								+ " and To_Date((CASHBOOK_MONTH "+
            	
            			" ||'-' "+
            			" || CASHBOOK_YEAR),'mm-yyyy') BETWEEN To_Date("+frommonth +
            			" ||'-' "+
            			" ||"+fromyear+",'mm-yyyy')"+
            			" AND to_date("+tomonth +
            			" ||'-' "+
            			" ||"+toyear+",'mm-yyyy')"
            			+ "  and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301)");
						
						///sysout
						
						
						System.out.println("select sum(AMOUNT) as amount from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? "
								+ " and To_Date((CASHBOOK_MONTH "+
            	
            			" ||'-' "+
            			" || CASHBOOK_YEAR),'mm-yyyy') BETWEEN To_Date("+frommonth +
            			" ||'-' "+
            			" ||"+fromyear+",'mm-yyyy')"+
            			" AND to_date("+tomonth +
            			" ||'-' "+
            			" ||"+toyear+",'mm-yyyy')"
            			+ "  and VOUCHER_NO=? and ACCOUNT_HEAD_CODE in (620101,900301)");
						
						
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						
//						ps1.setInt(3, cashYear);
//						ps1.setInt(4, cashMonth);
						ps1.setInt(3, voucherNo);
						rs1=ps1.executeQuery();
						if(rs1.next())
						{
						xml = xml + "<amount>" +df.format(rs1.getBigDecimal("amount"))+ "</amount>";
								
						y++;
						}
						else
						{
							y=0;
						}
					}
					if (y != 0) {
						xml = xml + "<flag>success</flag>";
					} else
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
