package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * Servlet implementation class BRS_List
 */
public class BRS_List extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_List() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
	    NumberFormat obj= new DecimalFormat("#0.00");
		
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
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}

		System.out.println("chk 2");

		String strCommand = "";
		String xml = "";

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
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try {
			System.out.println("chk 3");
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("command");
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		if (strCommand.equalsIgnoreCase("ListAll")) {
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();

			xml = xml + "<response><command>ListAll</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			String RemitanceDate=null;
			String WithdrawlDate=null;
			String EntryDate=null;
			String Stringdate=null;
			String Stringdate1=null;
			String Stringdate2=null;
			String T_or_NT = null;			
			int rsndiff1 = 0;
			String rsndif = null;
			int tnscde1 = 0;
			String tnscde = null;
			double crAmount = 0;
			double drAmount = 0;
			double crAmount_bank = 0;
			double drAmount_bank = 0;
			try {
				ps = connection.prepareStatement("select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? And Extract(Month From Passbook_Date)="+txtCB_Month+" And Extract(year From Passbook_Date)="+txtCB_Year+" and DOC_TYPE!='J' order by PASSBOOK_DATE,CHEQUE_DD_NO");
				ps.setInt(1,cmbAcc_UnitCode );
				ps.setInt(2,cmbOffice_code );
				ps.setInt(3,txtCB_Year );
				ps.setInt(4,txtCB_Month );
				ps.setLong(5,cmbBankAccNo );				
				results = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (results.next()) {

					Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
					Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
					Date EntryDate1=results.getDate("PASSBOOK_DATE");					
				try {
					Stringdate = RemitanceDate1.toString();
				} catch (Exception e) {
					Stringdate = "0000-00-00";
				}
				try {
					Stringdate1 = WithdrawlDate1.toString();
				} catch (Exception e) {
					Stringdate1 = "0000-00-00";
				}
				try {
					Stringdate2 = EntryDate1.toString();
				} catch (Exception e) {
					Stringdate2 = "0000-00-00";
				}

					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					String[] ddd2 = Stringdate2.split("-");
										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
					int day2 =Integer.parseInt(ddd2[2]);
					int month2 =Integer.parseInt(ddd2[1]);
					int year2 = Integer.parseInt(ddd2[0]);

					
					if(month>=10)
			        {
						RemitanceDate=(day+"/"+month+"/"+year);
			        }
			        else
			        {
			        	RemitanceDate=(day+"/0"+month+"/"+year);	
			        }
					
					if(month1>=10)
			        {
						WithdrawlDate=(day1+"/"+month1+"/"+year1);
			        }
			        else
			        {
			        	WithdrawlDate=(day1+"/0"+month1+"/"+year1);	
			        }
					if(month2>=10)
			        {
						EntryDate=(day2+"/"+month2+"/"+year2);
			        }
			        else
			        {
			        	EntryDate=(day2+"/0"+month2+"/"+year2);	
			        }
					
					T_or_NT = results.getString("TWAD_OR_NON_TWAD");
					
					if(T_or_NT.equals("T"))
					{
						xml = xml + "<RemitanceDate>" + RemitanceDate
								+ "</RemitanceDate>";
						xml = xml + "<WithdrawlDate>" + WithdrawlDate
								+ "</WithdrawlDate>";
						xml = xml + "<Voucher_or_ChallanNo>"
								+ results.getInt("VOUCHER_OR_CHALLAN_NO")
								+ "</Voucher_or_ChallanNo>";
						xml = xml + "<Cheqe_or_DDNo>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo>";
						
						if(results.getString("DOC_TYPE").equals("SC"))
						{
							 xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
                             drAmount += results.getDouble("CR_AMOUNT");
                             xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
						}
						else
						{
							xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                            drAmount += results.getDouble("DR_AMOUNT");
                            xml = xml + "<CRAmount>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount>";
                            crAmount += results.getDouble("CR_AMOUNT");
						}
					    /*  if(results.getFloat("DR_AMOUNT")==0 ||results.getDouble("DR_AMOUNT")==0) {
                               xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
                                drAmount += results.getDouble("CR_AMOUNT");
                                xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                            }
                            else{
                              //  System.out.println("else looppppppppp");
                            		xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                                drAmount += results.getDouble("DR_AMOUNT");
                                xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                            } */
						//crAmount += results.getDouble("CR_AMOUNT");
						
						xml = xml + "<EntryFoundInPassBook>"
								+ results.getString("ENTRY_FOUND_IN_PASSBOOK")
								+ "</EntryFoundInPassBook>";
						xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
						xml = xml + "<Amount>"
								+ results.getInt("AMOUNT_IN_PASSBOOK")
								+ "</Amount>";
						xml = xml + "<Difference>"
								+ results.getBigDecimal("DIFFERENCE")
								+ "</Difference>";	
						xml = xml + "<doc_no>" + results.getInt("DOC_NO") + "</doc_no>";
						xml = xml + "<doc_type>" + results.getString("DOC_TYPE")
								+ "</doc_type>";
						rsndif = results.getString("REASON_FOR_DIFFERENCE");
						
						xml = xml + "<FollowUpAction>"
								+ results.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction>";
						xml = xml + "<Clearance>"
								+ results.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance>";	
						
						try {
							ps1 = connection.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
							ps1.setInt(1, rsndiff1);
							rs = ps1.executeQuery();
							if (rs.next()) {
								xml = xml + "<RsnForDiff>"
								+ rs.getString("REASON_SHORT_DESC")
								+ "</RsnForDiff>";																
							}
							else
							{
								xml = xml + "<RsnForDiff>null</RsnForDiff>";								
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}else
					{
						xml = xml + "<PassbookDate1>" + EntryDate
								+ "</PassbookDate1>";
						xml = xml + "<Particulars1>"
								+ results.getString("PARTICULARS")
								+ "</Particulars1>";
						xml = xml + "<Cheqe_or_DDNo1>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo1>";
						xml = xml + "<Details1>" + results.getString("DETAILS")
								+ "</Details1>";
						 xml = xml + "<CRAmount1>"
								+ results.getBigDecimal("CR_AMOUNT")
								+ "</CRAmount1>";
						xml = xml + "<DRAmount1>"
								+ results.getBigDecimal("DR_AMOUNT")
								+ "</DRAmount1>";	 
                                                                
                                                
						crAmount_bank += results.getDouble("CR_AMOUNT");
						drAmount_bank += results.getDouble("DR_AMOUNT");  
						
					/*	if(results.getString("DOC_TYPE").equals("SC"))
						{
							 xml = xml + "<DRAmount1>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
							 drAmount_bank += results.getDouble("CR_AMOUNT");
                             xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
						}
						else
						{
							xml = xml + "<DRAmount1>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
							drAmount_bank += results.getDouble("DR_AMOUNT");
                            xml = xml + "<CRAmount1>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
                            crAmount_bank += results.getDouble("CR_AMOUNT");
						}  */
						
						    
						xml = xml+ "<FollowUpAction1>" + results.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction1>";
						xml = xml
								+ "<Clearance1>"
								+ results
										.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance1>";	
						xml = xml + "<doc_no1>" + results.getInt("DOC_NO") + "</doc_no1>";
						xml = xml + "<doc_type1>" + results.getString("DOC_TYPE")
								+ "</doc_type1>";
						tnscde = results.getString("TRANSACTION_TYPE");
						
						if(tnscde == null)
						{
							tnscde1 = 0;
						}
						else
						{
							tnscde1 = Integer.parseInt(tnscde);
						}
						
						try {
							ps1 = connection.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
							ps1.setInt(1, tnscde1);
							rs = ps1.executeQuery();
							if (rs.next()) {
								System.out.println("RsnForDiff"+rs.getString("TRANS_SHORT_DESC"));
								xml = xml + "<RsnForDiff1>"
								+ rs.getString("TRANS_SHORT_DESC")
								+ "</RsnForDiff1>";																
							}
							else
							{
								xml = xml + "<RsnForDiff>null</RsnForDiff>";								
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}	
				}
				xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
				xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
				xml = xml + "<crAmount_bank>"+obj.format(crAmount_bank)+"</crAmount_bank>";
				xml = xml + "<drAmount_bank>"+obj.format(drAmount_bank)+"</drAmount_bank>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		}
            
		if (strCommand.equalsIgnoreCase("ListAllBrs")) {

			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();

			xml = xml + "<response><command>ListAllBrs</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo = Long.parseLong(request
					.getParameter("cmbBankAccNo"));

			String RemitanceDate = null;
			String WithdrawlDate = null;
			String EntryDate = null;
			String Stringdate = null;
			String Stringdate1 = null;
			String Stringdate2 = null;
			String T_or_NT = null;
			int rsndiff1 = 0, count = 0;
			String rsndif = null;
			int tnscde1 = 0;
			String tnscde = null, qry = "";
			double crAmount = 0;
			double drAmount = 0;
			double crAmount_bank = 0;
			double drAmount_bank = 0;
			try {
				qry = "select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="
						+ cmbAcc_UnitCode
						+ " and ACCOUNTING_FOR_OFFICE_ID="
						+ cmbOffice_code
						+ " and CASHBOOK_YEAR="
						+ txtCB_Year
						+ " and CASHBOOK_MONTH="
						+ txtCB_Month
						+ " and ACCOUNT_NO="
						+ cmbBankAccNo
						+ " And Extract(Month From Passbook_Date)="
						+ txtCB_Month
						+ " And Extract(year From Passbook_Date)="
						+ txtCB_Year
						+ " and DOC_TYPE='J' order by PASSBOOK_DATE,CHEQUE_DD_NO";
				System.out.println(">>>>>> " + qry);
				ps = connection
						.prepareStatement(qry);
			/*	ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				ps.setLong(5, cmbBankAccNo);*/
				results = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (results.next()) {

					Date RemitanceDate1 = results.getDate("REMITTANCE_DATE");
					Date WithdrawlDate1 = results.getDate("WITHDRAWAL_DATE");
					Date EntryDate1 = results.getDate("PASSBOOK_DATE");
					try {
						Stringdate = RemitanceDate1.toString();
					} catch (Exception e) {
						Stringdate = "0000-00-00";
					}
					try {
						Stringdate1 = WithdrawlDate1.toString();
					} catch (Exception e) {
						Stringdate1 = "0000-00-00";
					}
					try {
						Stringdate2 = EntryDate1.toString();
					} catch (Exception e) {
						Stringdate2 = "0000-00-00";
					}

					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					String[] ddd2 = Stringdate2.split("-");

					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);

					int day1 = Integer.parseInt(ddd1[2]);
					int month1 = Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);

					int day2 = Integer.parseInt(ddd2[2]);
					int month2 = Integer.parseInt(ddd2[1]);
					int year2 = Integer.parseInt(ddd2[0]);

					if (month >= 10) {
						RemitanceDate = (day + "/" + month + "/" + year);
					} else {
						RemitanceDate = (day + "/0" + month + "/" + year);
					}

					if (month1 >= 10) {
						WithdrawlDate = (day1 + "/" + month1 + "/" + year1);
					} else {
						WithdrawlDate = (day1 + "/0" + month1 + "/" + year1);
					}
					if (month2 >= 10) {
						EntryDate = (day2 + "/" + month2 + "/" + year2);
					} else {
						EntryDate = (day2 + "/0" + month2 + "/" + year2);
					}

					T_or_NT = results.getString("TWAD_OR_NON_TWAD");
					System.out.println("tornt value is>>" + T_or_NT);

					if (T_or_NT.equals("T")) {
						xml = xml + "<RemitanceDate>" + RemitanceDate
								+ "</RemitanceDate>";
						xml = xml + "<WithdrawlDate>" + WithdrawlDate
								+ "</WithdrawlDate>";
						xml = xml + "<Voucher_or_ChallanNo>"
								+ results.getInt("VOUCHER_OR_CHALLAN_NO")
								+ "</Voucher_or_ChallanNo>";
						xml = xml + "<Cheqe_or_DDNo>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo>";

						if (results.getString("DOC_TYPE").equals("SC")) {
							xml = xml + "<DRAmount>"
									+ results.getBigDecimal("CR_AMOUNT")
									+ "</DRAmount>";
							drAmount += results.getDouble("CR_AMOUNT");
							xml = xml + "<CRAmount>" + 0 + "</CRAmount>";
						} else {
							xml = xml + "<DRAmount>"
									+ results.getBigDecimal("DR_AMOUNT")
									+ "</DRAmount>";
							drAmount += results.getDouble("DR_AMOUNT");
							xml = xml + "<CRAmount>"
									+ results.getBigDecimal("CR_AMOUNT")
									+ "</CRAmount>";
							crAmount += results.getDouble("CR_AMOUNT");
						}
						/*
						 * if(results.getFloat("DR_AMOUNT")==0
						 * ||results.getDouble("DR_AMOUNT")==0) { xml = xml +
						 * "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+
						 * "</DRAmount>"; drAmount +=
						 * results.getDouble("CR_AMOUNT"); xml = xml +
						 * "<CRAmount>" + 0+ "</CRAmount>"; } else{ //
						 * System.out.println("else looppppppppp"); xml = xml +
						 * "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+
						 * "</DRAmount>"; drAmount +=
						 * results.getDouble("DR_AMOUNT"); xml = xml +
						 * "<CRAmount>" + 0+ "</CRAmount>"; }
						 */
						// crAmount += results.getDouble("CR_AMOUNT");

						xml = xml + "<EntryFoundInPassBook>"
								+ results.getString("ENTRY_FOUND_IN_PASSBOOK")
								+ "</EntryFoundInPassBook>";
						xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
						xml = xml + "<Amount>"
								+ results.getInt("AMOUNT_IN_PASSBOOK")
								+ "</Amount>";
						xml = xml + "<Difference>"
								+ results.getBigDecimal("DIFFERENCE")
								+ "</Difference>";
						xml = xml + "<doc_no>" + results.getInt("DOC_NO")
								+ "</doc_no>";
						xml = xml + "<doc_type>"
								+ results.getString("DOC_TYPE") + "</doc_type>";
						rsndif = results.getString("REASON_FOR_DIFFERENCE");

						xml = xml
								+ "<FollowUpAction>"
								+ results
										.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction>";
						xml = xml
								+ "<Clearance>"
								+ results
										.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance>";

						try {
							ps1 = connection
									.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
							ps1.setInt(1, rsndiff1);
							rs = ps1.executeQuery();
							if (rs.next()) {
								xml = xml + "<RsnForDiff>"
										+ rs.getString("REASON_SHORT_DESC")
										+ "</RsnForDiff>";
							} else {
								xml = xml + "<RsnForDiff>null</RsnForDiff>";
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					} else {
						xml = xml + "<PassbookDate1>" + EntryDate
								+ "</PassbookDate1>";
						xml = xml + "<Particulars1>"
								+ results.getString("PARTICULARS")
								+ "</Particulars1>";
						xml = xml + "<Cheqe_or_DDNo1>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo1>";
						xml = xml + "<Details1>" + results.getString("DETAILS")
								+ "</Details1>";
						xml = xml + "<CRAmount1>"
								+ results.getBigDecimal("CR_AMOUNT")
								+ "</CRAmount1>";
						xml = xml + "<DRAmount1>"
								+ results.getBigDecimal("DR_AMOUNT")
								+ "</DRAmount1>";

						crAmount_bank += results.getDouble("CR_AMOUNT");
						drAmount_bank += results.getDouble("DR_AMOUNT");

						/*
						 * if(results.getString("DOC_TYPE").equals("SC")) { xml
						 * = xml + "<DRAmount1>" +
						 * results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
						 * drAmount_bank += results.getDouble("CR_AMOUNT"); xml
						 * = xml + "<CRAmount1>" + 0+ "</CRAmount1>"; } else {
						 * xml = xml + "<DRAmount1>"+
						 * results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
						 * drAmount_bank += results.getDouble("DR_AMOUNT"); xml
						 * = xml + "<CRAmount1>"
						 * +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
						 * crAmount_bank += results.getDouble("CR_AMOUNT"); }
						 */

						xml = xml
								+ "<FollowUpAction1>"
								+ results
										.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction1>";
						xml = xml
								+ "<Clearance1>"
								+ results
										.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance1>";
						xml = xml + "<doc_no1>" + results.getInt("DOC_NO")
								+ "</doc_no1>";
						xml = xml + "<doc_type1>"
								+ results.getString("DOC_TYPE")
								+ "</doc_type1>";
						tnscde = results.getString("TRANSACTION_TYPE");

						if (tnscde == null) {
							tnscde1 = 0;
						} else {
							tnscde1 = Integer.parseInt(tnscde);
						}

						try {
							ps1 = connection
									.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
							ps1.setInt(1, tnscde1);
							rs = ps1.executeQuery();
							if (rs.next()) {
								xml = xml + "<RsnForDiff>"
										+ rs.getString("TRANS_SHORT_DESC")
										+ "</RsnForDiff>";
							} else {
								xml = xml + "<RsnForDiff>null</RsnForDiff>";
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}
					count++;
				}
				xml = xml + "<crAmount>" + obj.format(crAmount) + "</crAmount>";
				xml = xml + "<drAmount>" + obj.format(drAmount) + "</drAmount>";
				xml = xml + "<crAmount_bank>" + obj.format(crAmount_bank)
						+ "</crAmount_bank>";
				xml = xml + "<drAmount_bank>" + obj.format(drAmount_bank)
						+ "</drAmount_bank>";

				xml += "<count>" + count + "</count>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		}
		else if(strCommand.equalsIgnoreCase("ListAllCashBrs"))
		{
			 	response.setContentType(CONTENT_TYPE);
					PrintWriter out = response.getWriter();

		         xml = xml + "<response><command>ListAllCashBrs</command>";

		         int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		         int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		         int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
		         int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		         long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
		         
		         String RemitanceDate=null;
		         String WithdrawlDate=null;
		         String EntryDate=null;
		         String Stringdate=null;
		         String Stringdate1=null;
		         String Stringdate2=null;
		         String T_or_NT = null;                  
		         int rsndiff1 = 0,count=0;
		         String rsndif = null;
		         int tnscde1 = 0;
		         String tnscde = null,sql="";
		         double crAmount = 0;
		       
		         double drAmount = 0;
		         double crAmount_bank = 0;
		         double drAmount_bank = 0;
		         try {
						sql = "select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="
								+ cmbAcc_UnitCode
								+ " and ACCOUNTING_FOR_OFFICE_ID="
								+ cmbOffice_code
								+ " and CASHBOOK_YEAR="
								+ txtCB_Year
								+ " and CASHBOOK_MONTH="
								+ txtCB_Month
								+ " and ACCOUNT_NO="
								+ cmbBankAccNo
								+ " and DOC_TYPE='J'"
								+ " order by PASSBOOK_DATE,CHEQUE_DD_NO";
						System.out.println(">>> " + sql);
						ps = connection.prepareStatement(sql);
						/*
						 * ps.setInt(1,cmbAcc_UnitCode ); ps.setInt(2,cmbOffice_code );
						 * ps.setInt(3,txtCB_Year ); ps.setInt(4,txtCB_Month );
						 * ps.setLong(5,cmbBankAccNo );
						 */                          
		                 results = ps.executeQuery();
		                 xml = xml + "<flag>success</flag>";
		                 while (results.next()) {

		                         Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
		                         Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
		                         Date EntryDate1=results.getDate("PASSBOOK_DATE");                                       
		                 try {
		                         Stringdate = RemitanceDate1.toString();
		                 } catch (Exception e) {
		                         Stringdate = "0000-00-00";
		                 }
		                 try {
		                         Stringdate1 = WithdrawlDate1.toString();
		                 } catch (Exception e) {
		                         Stringdate1 = "0000-00-00";
		                 }
		                 try {
		                         Stringdate2 = EntryDate1.toString();
		                 } catch (Exception e) {
		                         Stringdate2 = "0000-00-00";
		                 }

		                         String[] ddd = Stringdate.split("-");
		                         String[] ddd1 = Stringdate1.split("-");
		                         String[] ddd2 = Stringdate2.split("-");
		                                                                 
		                         int day =Integer.parseInt(ddd[2]);
		                         int month =Integer.parseInt(ddd[1]);
		                         int year = Integer.parseInt(ddd[0]);
		                         
		                         int day1 =Integer.parseInt(ddd1[2]);
		                         int month1 =Integer.parseInt(ddd1[1]);
		                         int year1 = Integer.parseInt(ddd1[0]);
		                         
		                         int day2 =Integer.parseInt(ddd2[2]);
		                         int month2 =Integer.parseInt(ddd2[1]);
		                         int year2 = Integer.parseInt(ddd2[0]);

		                         
		                         if(month>=10)
		                 {
		                                 RemitanceDate=(day+"/"+month+"/"+year);
		                 }
		                 else
		                 {
		                         RemitanceDate=(day+"/0"+month+"/"+year);        
		                 }
		                         
		                         if(month1>=10)
		                 {
		                                 WithdrawlDate=(day1+"/"+month1+"/"+year1);
		                 }
		                 else
		                 {
		                         WithdrawlDate=(day1+"/0"+month1+"/"+year1);     
		                 }
		                         if(month2>=10)
		                 {
		                                 EntryDate=(day2+"/"+month2+"/"+year2);
		                 }
		                 else
		                 {
		                         EntryDate=(day2+"/0"+month2+"/"+year2); 
		                 }
		                         
		                         T_or_NT = results.getString("TWAD_OR_NON_TWAD");
		                         
		                         if(T_or_NT.equals("T"))
		                         {
		                                 xml = xml + "<RemitanceDate>" + RemitanceDate
		                                                 + "</RemitanceDate>";
		                                 xml = xml + "<WithdrawlDate>" + WithdrawlDate
		                                                 + "</WithdrawlDate>";
		                                 xml = xml + "<Voucher_or_ChallanNo>"
		                                                 + results.getInt("VOUCHER_OR_CHALLAN_NO")
		                                                 + "</Voucher_or_ChallanNo>";
		                                 xml = xml + "<Cheqe_or_DDNo>"
		                                                 + results.getInt("CHEQUE_DD_NO")
		                                                 + "</Cheqe_or_DDNo>";
		                                 if(results.getString("DOC_TYPE").equals("SC"))
		         						{
		         							 xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
		                                      drAmount += results.getDouble("CR_AMOUNT");
		                                      xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
		         						}
		         						else
		         						{
		         							xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
		                                     drAmount += results.getDouble("DR_AMOUNT");
		                                     xml = xml + "<CRAmount>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount>";
		                                     crAmount += results.getDouble("CR_AMOUNT");
		         						}
		                              /*  if(results.getFloat("DR_AMOUNT")==0 ||results.getDouble("DR_AMOUNT")==0) {
		                                    xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
		                                     drAmount += results.getDouble("CR_AMOUNT");
		                                     xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
		                                 }
		                                 else{
		                                   //  System.out.println("else looppppppppp");
		                                 xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
		                                     drAmount += results.getDouble("DR_AMOUNT");
		                                     xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
		                                 }  */
		                                 
		                                 xml = xml + "<EntryFoundInPassBook>"
		                                                 + results.getString("ENTRY_FOUND_IN_PASSBOOK")
		                                                 + "</EntryFoundInPassBook>";
		                                 xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
		                                 xml = xml + "<Amount>"
		                                                 + results.getInt("AMOUNT_IN_PASSBOOK")
		                                                 + "</Amount>";
		                                 xml = xml + "<Difference>"
		                                                 + results.getBigDecimal("DIFFERENCE")
		                                                 + "</Difference>";      
		                                 xml = xml + "<doc_no>" + results.getInt("DOC_NO") + "</doc_no>";
		                                 xml = xml + "<doc_type>" + results.getString("DOC_TYPE")
		                                                 + "</doc_type>";
		                                 rsndif = results.getString("REASON_FOR_DIFFERENCE");
		                                 
		                                 xml = xml + "<FollowUpAction>"
		                                                 + results.getString("FOLLOW_UP_ACTION_REQUIRED")
		                                                 + "</FollowUpAction>";
		                                 xml = xml + "<Clearance>"
		                                                 + results.getString("CLEARED_BASED_ON_FOLLOWUP")
		                                                 + "</Clearance>";       
		                                 
		                                 try {
		                                         ps1 = connection.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
		                                         ps1.setInt(1, rsndiff1);
		                                         rs = ps1.executeQuery();
		                                         if (rs.next()) {
		                                                 xml = xml + "<RsnForDiff>"
		                                                 + rs.getString("REASON_SHORT_DESC")
		                                                 + "</RsnForDiff>";                                                                                                                              
		                                         }
		                                         else
		                                         {
		                                                 xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
		                                         }
		                                 } catch (Exception e) {
		                                         System.out.println(e);
		                                 }
		                         }else
		                         {
		                                 xml = xml + "<PassbookDate1>" + EntryDate
		                                                 + "</PassbookDate1>";
		                                 xml = xml + "<Particulars1>"
		                                                 + results.getString("PARTICULARS")
		                                                 + "</Particulars1>";
		                                 xml = xml + "<Cheqe_or_DDNo1>"
		                                                 + results.getInt("CHEQUE_DD_NO")
		                                                 + "</Cheqe_or_DDNo1>";
		                                 xml = xml + "<Details1>" + results.getString("DETAILS")
		                                                 + "</Details1>";
		                                /* xml = xml + "<CRAmount1>"
		                                                 + results.getBigDecimal("CR_AMOUNT")
		                                                 + "</CRAmount1>";
		                                 xml = xml + "<DRAmount1>"
		                                                 + results.getBigDecimal("DR_AMOUNT")
		                                                 + "</DRAmount1>";       
		                                 crAmount_bank += results.getDouble("CR_AMOUNT");
		                                 drAmount_bank += results.getDouble("DR_AMOUNT");  */
		                                 if(results.getString("DOC_TYPE").equals("SC"))
		         						{
		         							 xml = xml + "<DRAmount1>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
		         							 drAmount_bank += results.getDouble("CR_AMOUNT");
		                                      xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
		         						}
		         						else
		         						{
		         							xml = xml + "<DRAmount1>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
		         							drAmount_bank += results.getDouble("DR_AMOUNT");
		                                     xml = xml + "<CRAmount1>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
		                                     crAmount_bank += results.getDouble("CR_AMOUNT");
		         						}
		                                 xml = xml
		                                                 + "<FollowUpAction1>"
		                                                 + results
		                                                                 .getString("FOLLOW_UP_ACTION_REQUIRED")
		                                                 + "</FollowUpAction1>";
		                                 xml = xml
		                                                 + "<Clearance1>"
		                                                 + results
		                                                                 .getString("CLEARED_BASED_ON_FOLLOWUP")
		                                                 + "</Clearance1>";      
		                                 xml = xml + "<doc_no1>" + results.getInt("DOC_NO") + "</doc_no1>";
		                                 xml = xml + "<doc_type1>" + results.getString("DOC_TYPE")
		                                                 + "</doc_type1>";
		                                 tnscde = results.getString("TRANSACTION_TYPE");
		                                 if(tnscde == null)
		                                 {
		                                         tnscde1 = 0;
		                                 }
		                                 else
		                                 {
		                                         tnscde1 = Integer.parseInt(tnscde);
		                                 }
		                                 
		                                 try {
		                                         ps1 = connection.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
		                                         ps1.setInt(1, tnscde1);
		                                         rs = ps1.executeQuery();
		                                         if (rs.next()) {
		                                                 xml = xml + "<RsnForDiff>"
		                                                 + rs.getString("TRANS_SHORT_DESC")
		                                                 + "</RsnForDiff>";                                                                                                                              
		                                         }
		                                         else
		                                         {
		                                                 xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
		                                         }
		                                 } catch (Exception e) {
		                                         System.out.println(e);
		                                 }
		                         }   count++;    
		                 }
		                 xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
		                 xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
		                 xml = xml + "<crAmount_bank>"+obj.format(crAmount_bank)+"</crAmount_bank>";
		                 xml = xml + "<drAmount_bank>"+obj.format(drAmount_bank)+"</drAmount_bank>";
		                 xml+="<count>"+count+"</count>";
		         } catch (Exception e) {
		                 xml = xml + "<flag>failure</flag>";
		                 e.printStackTrace();
		         } 
		         xml = xml + "</response>";
		 		out.write(xml);
		 		System.out.println(xml);
			}
				
		else if(strCommand.equalsIgnoreCase("ListAllCashBrs"))
		{
			 	response.setContentType(CONTENT_TYPE);
					PrintWriter out = response.getWriter();

		         xml = xml + "<response><command>ListAllCashBrs</command>";

		         int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		         int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		         int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
		         int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		         long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
		         
		         String RemitanceDate=null;
		         String WithdrawlDate=null;
		         String EntryDate=null;
		         String Stringdate=null;
		         String Stringdate1=null;
		         String Stringdate2=null;
		         String T_or_NT = null;                  
		         int rsndiff1 = 0,count=0;
		         String rsndif = null;
		         int tnscde1 = 0;
		         String tnscde = null,sql="";
		         double crAmount = 0;
		       
		         double drAmount = 0;
		         double crAmount_bank = 0;
		         double drAmount_bank = 0;
		         try {
						sql = "select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="
								+ cmbAcc_UnitCode
								+ " and ACCOUNTING_FOR_OFFICE_ID="
								+ cmbOffice_code
								+ " and CASHBOOK_YEAR="
								+ txtCB_Year
								+ " and CASHBOOK_MONTH="
								+ txtCB_Month
								+ " and ACCOUNT_NO="
								+ cmbBankAccNo
								+ " and DOC_TYPE='J'"
								+ " order by PASSBOOK_DATE,CHEQUE_DD_NO";
						System.out.println(">>> " + sql);
						ps = connection.prepareStatement(sql);
						/*
						 * ps.setInt(1,cmbAcc_UnitCode ); ps.setInt(2,cmbOffice_code );
						 * ps.setInt(3,txtCB_Year ); ps.setInt(4,txtCB_Month );
						 * ps.setLong(5,cmbBankAccNo );
						 */                          
		                 results = ps.executeQuery();
		                 xml = xml + "<flag>success</flag>";
		                 while (results.next()) {

		                         Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
		                         Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
		                         Date EntryDate1=results.getDate("PASSBOOK_DATE");                                       
		                 try {
		                         Stringdate = RemitanceDate1.toString();
		                 } catch (Exception e) {
		                         Stringdate = "0000-00-00";
		                 }
		                 try {
		                         Stringdate1 = WithdrawlDate1.toString();
		                 } catch (Exception e) {
		                         Stringdate1 = "0000-00-00";
		                 }
		                 try {
		                         Stringdate2 = EntryDate1.toString();
		                 } catch (Exception e) {
		                         Stringdate2 = "0000-00-00";
		                 }

		                         String[] ddd = Stringdate.split("-");
		                         String[] ddd1 = Stringdate1.split("-");
		                         String[] ddd2 = Stringdate2.split("-");
		                                                                 
		                         int day =Integer.parseInt(ddd[2]);
		                         int month =Integer.parseInt(ddd[1]);
		                         int year = Integer.parseInt(ddd[0]);
		                         
		                         int day1 =Integer.parseInt(ddd1[2]);
		                         int month1 =Integer.parseInt(ddd1[1]);
		                         int year1 = Integer.parseInt(ddd1[0]);
		                         
		                         int day2 =Integer.parseInt(ddd2[2]);
		                         int month2 =Integer.parseInt(ddd2[1]);
		                         int year2 = Integer.parseInt(ddd2[0]);

		                         
		                         if(month>=10)
		                 {
		                                 RemitanceDate=(day+"/"+month+"/"+year);
		                 }
		                 else
		                 {
		                         RemitanceDate=(day+"/0"+month+"/"+year);        
		                 }
		                         
		                         if(month1>=10)
		                 {
		                                 WithdrawlDate=(day1+"/"+month1+"/"+year1);
		                 }
		                 else
		                 {
		                         WithdrawlDate=(day1+"/0"+month1+"/"+year1);     
		                 }
		                         if(month2>=10)
		                 {
		                                 EntryDate=(day2+"/"+month2+"/"+year2);
		                 }
		                 else
		                 {
		                         EntryDate=(day2+"/0"+month2+"/"+year2); 
		                 }
		                         
		                         T_or_NT = results.getString("TWAD_OR_NON_TWAD");
		                         
		                         if(T_or_NT.equals("T"))
		                         {
		                                 xml = xml + "<RemitanceDate>" + RemitanceDate
		                                                 + "</RemitanceDate>";
		                                 xml = xml + "<WithdrawlDate>" + WithdrawlDate
		                                                 + "</WithdrawlDate>";
		                                 xml = xml + "<Voucher_or_ChallanNo>"
		                                                 + results.getInt("VOUCHER_OR_CHALLAN_NO")
		                                                 + "</Voucher_or_ChallanNo>";
		                                 xml = xml + "<Cheqe_or_DDNo>"
		                                                 + results.getInt("CHEQUE_DD_NO")
		                                                 + "</Cheqe_or_DDNo>";
		                                 if(results.getString("DOC_TYPE").equals("SC"))
		         						{
		         							 xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
		                                      drAmount += results.getDouble("CR_AMOUNT");
		                                      xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
		         						}
		         						else
		         						{
		         							xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
		                                     drAmount += results.getDouble("DR_AMOUNT");
		                                     xml = xml + "<CRAmount>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount>";
		                                     crAmount += results.getDouble("CR_AMOUNT");
		         						}
		                              /*  if(results.getFloat("DR_AMOUNT")==0 ||results.getDouble("DR_AMOUNT")==0) {
		                                    xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
		                                     drAmount += results.getDouble("CR_AMOUNT");
		                                     xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
		                                 }
		                                 else{
		                                   //  System.out.println("else looppppppppp");
		                                 xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
		                                     drAmount += results.getDouble("DR_AMOUNT");
		                                     xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
		                                 }  */
		                                 
		                                 xml = xml + "<EntryFoundInPassBook>"
		                                                 + results.getString("ENTRY_FOUND_IN_PASSBOOK")
		                                                 + "</EntryFoundInPassBook>";
		                                 xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
		                                 xml = xml + "<Amount>"
		                                                 + results.getInt("AMOUNT_IN_PASSBOOK")
		                                                 + "</Amount>";
		                                 xml = xml + "<Difference>"
		                                                 + results.getBigDecimal("DIFFERENCE")
		                                                 + "</Difference>";      
		                                 xml = xml + "<doc_no>" + results.getInt("DOC_NO") + "</doc_no>";
		                                 xml = xml + "<doc_type>" + results.getString("DOC_TYPE")
		                                                 + "</doc_type>";
		                                 rsndif = results.getString("REASON_FOR_DIFFERENCE");
		                                 
		                                 xml = xml + "<FollowUpAction>"
		                                                 + results.getString("FOLLOW_UP_ACTION_REQUIRED")
		                                                 + "</FollowUpAction>";
		                                 xml = xml + "<Clearance>"
		                                                 + results.getString("CLEARED_BASED_ON_FOLLOWUP")
		                                                 + "</Clearance>";       
		                                 
		                                 try {
		                                         ps1 = connection.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
		                                         ps1.setInt(1, rsndiff1);
		                                         rs = ps1.executeQuery();
		                                         if (rs.next()) {
		                                                 xml = xml + "<RsnForDiff>"
		                                                 + rs.getString("REASON_SHORT_DESC")
		                                                 + "</RsnForDiff>";                                                                                                                              
		                                         }
		                                         else
		                                         {
		                                                 xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
		                                         }
		                                 } catch (Exception e) {
		                                         System.out.println(e);
		                                 }
		                         }else
		                         {
		                                 xml = xml + "<PassbookDate1>" + EntryDate
		                                                 + "</PassbookDate1>";
		                                 xml = xml + "<Particulars1>"
		                                                 + results.getString("PARTICULARS")
		                                                 + "</Particulars1>";
		                                 xml = xml + "<Cheqe_or_DDNo1>"
		                                                 + results.getInt("CHEQUE_DD_NO")
		                                                 + "</Cheqe_or_DDNo1>";
		                                 xml = xml + "<Details1>" + results.getString("DETAILS")
		                                                 + "</Details1>";
		                                /* xml = xml + "<CRAmount1>"
		                                                 + results.getBigDecimal("CR_AMOUNT")
		                                                 + "</CRAmount1>";
		                                 xml = xml + "<DRAmount1>"
		                                                 + results.getBigDecimal("DR_AMOUNT")
		                                                 + "</DRAmount1>";       
		                                 crAmount_bank += results.getDouble("CR_AMOUNT");
		                                 drAmount_bank += results.getDouble("DR_AMOUNT");  */
		                                 if(results.getString("DOC_TYPE").equals("SC"))
		         						{
		         							 xml = xml + "<DRAmount1>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
		         							 drAmount_bank += results.getDouble("CR_AMOUNT");
		                                      xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
		         						}
		         						else
		         						{
		         							xml = xml + "<DRAmount1>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
		         							drAmount_bank += results.getDouble("DR_AMOUNT");
		                                     xml = xml + "<CRAmount1>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
		                                     crAmount_bank += results.getDouble("CR_AMOUNT");
		         						}
		                                 xml = xml
		                                                 + "<FollowUpAction1>"
		                                                 + results
		                                                                 .getString("FOLLOW_UP_ACTION_REQUIRED")
		                                                 + "</FollowUpAction1>";
		                                 xml = xml
		                                                 + "<Clearance1>"
		                                                 + results
		                                                                 .getString("CLEARED_BASED_ON_FOLLOWUP")
		                                                 + "</Clearance1>";      
		                                 xml = xml + "<doc_no1>" + results.getInt("DOC_NO") + "</doc_no1>";
		                                 xml = xml + "<doc_type1>" + results.getString("DOC_TYPE")
		                                                 + "</doc_type1>";
		                                 tnscde = results.getString("TRANSACTION_TYPE");
		                                 if(tnscde == null)
		                                 {
		                                         tnscde1 = 0;
		                                 }
		                                 else
		                                 {
		                                         tnscde1 = Integer.parseInt(tnscde);
		                                 }
		                                 
		                                 try {
		                                         ps1 = connection.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
		                                         ps1.setInt(1, tnscde1);
		                                         rs = ps1.executeQuery();
		                                         if (rs.next()) {
		                                                 xml = xml + "<RsnForDiff>"
		                                                 + rs.getString("TRANS_SHORT_DESC")
		                                                 + "</RsnForDiff>";                                                                                                                              
		                                         }
		                                         else
		                                         {
		                                                 xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
		                                         }
		                                 } catch (Exception e) {
		                                         System.out.println(e);
		                                 }
		                         }   count++;    
		                 }
		                 xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
		                 xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
		                 xml = xml + "<crAmount_bank>"+obj.format(crAmount_bank)+"</crAmount_bank>";
		                 xml = xml + "<drAmount_bank>"+obj.format(drAmount_bank)+"</drAmount_bank>";
		                 xml+="<count>"+count+"</count>";
		         } catch (Exception e) {
		                 xml = xml + "<flag>failure</flag>";
		                 e.printStackTrace();
		         } 
		         xml = xml + "</response>";
		 		out.write(xml);
		 		System.out.println(xml);
			}
				
		  else if (strCommand.equalsIgnoreCase("ListAllPassSheetBrs")) {
          	response.setContentType(CONTENT_TYPE);
      		PrintWriter out = response.getWriter();

		xml = xml + "<response><command>ListAllPassSheetBrs</command>";

		int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
		int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
		
		String RemitanceDate=null;
		String WithdrawlDate=null;
		String EntryDate=null;
		String Stringdate=null;
		String Stringdate1=null;
		String Stringdate2=null;
		String T_or_NT = null;			
		int rsndiff1 = 0,count=0;
		String rsndif = null,qry="";
		int tnscde1 = 0;
		String tnscde = null;
		double crAmount = 0;
                
		double drAmount = 0;
		double crAmount_bank = 0;
		double drAmount_bank = 0;
		try {
			qry="select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and extract (year from PASSBOOK_DATE)="+txtCB_Year+" and extract (month from PASSBOOK_DATE)="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo+" and DOC_TYPE='J' order by PASSBOOK_DATE,CHEQUE_DD_NO";
			System.out.println(">>>>"+qry);
			ps = connection.prepareStatement(qry);
		/*	ps.setInt(1,cmbAcc_UnitCode );
			ps.setInt(2,cmbOffice_code );
			ps.setInt(3,txtCB_Year );
			ps.setInt(4,txtCB_Month );
			ps.setLong(5,cmbBankAccNo );	*/			
			results = ps.executeQuery();
			xml = xml + "<flag>success</flag>";
			while (results.next()) {

				Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
				Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
				Date EntryDate1=results.getDate("PASSBOOK_DATE");					
			try {
				Stringdate = RemitanceDate1.toString();
			} catch (Exception e) {
				Stringdate = "0000-00-00";
			}
			try {
				Stringdate1 = WithdrawlDate1.toString();
			} catch (Exception e) {
				Stringdate1 = "0000-00-00";
			}
			try {
				Stringdate2 = EntryDate1.toString();
			} catch (Exception e) {
				Stringdate2 = "0000-00-00";
			}

				String[] ddd = Stringdate.split("-");
				String[] ddd1 = Stringdate1.split("-");
				String[] ddd2 = Stringdate2.split("-");
									
				int day =Integer.parseInt(ddd[2]);
				int month =Integer.parseInt(ddd[1]);
				int year = Integer.parseInt(ddd[0]);
				
				int day1 =Integer.parseInt(ddd1[2]);
				int month1 =Integer.parseInt(ddd1[1]);
				int year1 = Integer.parseInt(ddd1[0]);
				
				int day2 =Integer.parseInt(ddd2[2]);
				int month2 =Integer.parseInt(ddd2[1]);
				int year2 = Integer.parseInt(ddd2[0]);

				
				if(month>=10)
		        {
					RemitanceDate=(day+"/"+month+"/"+year);
		        }
		        else
		        {
		        	RemitanceDate=(day+"/0"+month+"/"+year);	
		        }
				
				if(month1>=10)
		        {
					WithdrawlDate=(day1+"/"+month1+"/"+year1);
		        }
		        else
		        {
		        	WithdrawlDate=(day1+"/0"+month1+"/"+year1);	
		        }
				if(month2>=10)
		        {
					EntryDate=(day2+"/"+month2+"/"+year2);
		        }
		        else
		        {
		        	EntryDate=(day2+"/0"+month2+"/"+year2);	
		        }
				
				T_or_NT = results.getString("TWAD_OR_NON_TWAD");
				
				if(T_or_NT.equals("T"))
				{
					xml = xml + "<RemitanceDate>" + RemitanceDate
							+ "</RemitanceDate>";
					xml = xml + "<WithdrawlDate>" + WithdrawlDate
							+ "</WithdrawlDate>";
					xml = xml + "<Voucher_or_ChallanNo>"
							+ results.getInt("VOUCHER_OR_CHALLAN_NO")
							+ "</Voucher_or_ChallanNo>";
					xml = xml + "<Cheqe_or_DDNo>"
							+ results.getInt("CHEQUE_DD_NO")
							+ "</Cheqe_or_DDNo>";
					
                                          //dhana
                /*   if(results.getFloat("DR_AMOUNT")==0 ||results.getDouble("DR_AMOUNT")==0) {
                       xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
                        drAmount += results.getDouble("CR_AMOUNT");
                        xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                    }
                    else{
                      //  System.out.println("else looppppppppp");
                    xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                        drAmount += results.getDouble("DR_AMOUNT");
                        xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                    }  */
					
					if(results.getString("DOC_TYPE").equals("SC"))
					{
						 xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
                       drAmount += results.getDouble("CR_AMOUNT");
                       xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
					}
					else
					{
						xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                      drAmount += results.getDouble("DR_AMOUNT");
                      xml = xml + "<CRAmount>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount>";
                      crAmount += results.getDouble("CR_AMOUNT");
					}
					xml = xml + "<EntryFoundInPassBook>"
							+ results.getString("ENTRY_FOUND_IN_PASSBOOK")
							+ "</EntryFoundInPassBook>";
					xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
					xml = xml + "<Amount>"
							+ results.getInt("AMOUNT_IN_PASSBOOK")
							+ "</Amount>";
					xml = xml + "<Difference>"
							+ results.getBigDecimal("DIFFERENCE")
							+ "</Difference>";	
					xml = xml + "<doc_no>" + results.getInt("DOC_NO") + "</doc_no>";
					xml = xml + "<doc_type>" + results.getString("DOC_TYPE")
							+ "</doc_type>";
					rsndif = results.getString("REASON_FOR_DIFFERENCE");
					
					xml = xml + "<FollowUpAction>"
							+ results.getString("FOLLOW_UP_ACTION_REQUIRED")
							+ "</FollowUpAction>";
					xml = xml + "<Clearance>"
							+ results.getString("CLEARED_BASED_ON_FOLLOWUP")
							+ "</Clearance>";	
					
					try {
						ps1 = connection.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
						ps1.setInt(1, rsndiff1);
						rs = ps1.executeQuery();
						if (rs.next()) {
							xml = xml + "<RsnForDiff>"
							+ rs.getString("REASON_SHORT_DESC")
							+ "</RsnForDiff>";																
						}
						else
						{
							xml = xml + "<RsnForDiff>null</RsnForDiff>";								
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}else
				{
					xml = xml + "<PassbookDate1>" + EntryDate
							+ "</PassbookDate1>";
					xml = xml + "<Particulars1>"
							+ results.getString("PARTICULARS")
							+ "</Particulars1>";
					xml = xml + "<Cheqe_or_DDNo1>"
							+ results.getInt("CHEQUE_DD_NO")
							+ "</Cheqe_or_DDNo1>";
					xml = xml + "<Details1>" + results.getString("DETAILS")
							+ "</Details1>";
					/* xml = xml + "<CRAmount1>"
							+ results.getBigDecimal("CR_AMOUNT")
							+ "</CRAmount1>";
					xml = xml + "<DRAmount1>"
							+ results.getBigDecimal("DR_AMOUNT")
							+ "</DRAmount1>";	
					crAmount_bank += results.getDouble("CR_AMOUNT");
					drAmount_bank += results.getDouble("DR_AMOUNT");  */
					if(results.getString("DOC_TYPE").equals("SC"))
					{
						 xml = xml + "<DRAmount1>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
						 drAmount_bank += results.getDouble("CR_AMOUNT");
                       xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
					}
					else
					{
						xml = xml + "<DRAmount1>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
						drAmount_bank += results.getDouble("DR_AMOUNT");
                      xml = xml + "<CRAmount1>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
                      crAmount_bank += results.getDouble("CR_AMOUNT");
					}
					xml = xml
							+ "<FollowUpAction1>"
							+ results
									.getString("FOLLOW_UP_ACTION_REQUIRED")
							+ "</FollowUpAction1>";
					xml = xml
							+ "<Clearance1>"
							+ results
									.getString("CLEARED_BASED_ON_FOLLOWUP")
							+ "</Clearance1>";	
					xml = xml + "<doc_no1>" + results.getInt("DOC_NO") + "</doc_no1>";
					xml = xml + "<doc_type1>" + results.getString("DOC_TYPE")
							+ "</doc_type1>";
					tnscde = results.getString("TRANSACTION_TYPE");
					if(tnscde == null)
					{
						tnscde1 = 0;
					}
					else
					{
						tnscde1 = Integer.parseInt(tnscde);
					}
					
					try {
						ps1 = connection.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
						ps1.setInt(1, tnscde1);
						rs = ps1.executeQuery();
						if (rs.next()) {
							xml = xml + "<RsnForDiff>"
							+ rs.getString("TRANS_SHORT_DESC")
							+ "</RsnForDiff>";																
						}
						else
						{
							xml = xml + "<RsnForDiff>null</RsnForDiff>";								
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}	count++;
			}
			xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
			xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
			xml = xml + "<crAmount_bank>"+obj.format(crAmount_bank)+"</crAmount_bank>";
			xml = xml + "<drAmount_bank>"+obj.format(drAmount_bank)+"</drAmount_bank>";
			xml+="<count>"+count+"</count>";
		} catch (Exception e) {
			xml = xml + "<flag>failure</flag>";
			e.printStackTrace();
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
          }
          else if(strCommand.equalsIgnoreCase("ListAll_acknowledged_Brs"))
          {

  			response.setContentType(CONTENT_TYPE);
  			PrintWriter out = response.getWriter();

  	            xml = xml + "<response><command>ListAll_acknowledged_Brs</command>";

  	            int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
  	            int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
  	            int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
  	            int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
  	            long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
  	            
  	            String RemitanceDate=null;
  	            String WithdrawlDate=null;
  	            String EntryDate=null;
  	            String Stringdate=null;
  	            String Stringdate1=null;
  	            String Stringdate2=null;
  	            String T_or_NT = null;                  
  	            int rsndiff1 = 0;
  	            String rsndif = null,qry="";
  	            int tnscde1 = 0,count=0;
  	            String tnscde = null;
  	            double crAmount = 0;
  	            double drAmount = 0;
  	            double crAmount_bank = 0;
  	            double drAmount_bank = 0;
  	            try {
  	            	qry="select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and extract (year from PASSBOOK_DATE)="+txtCB_Year+" and extract (month from PASSBOOK_DATE)="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo+" And (Cashbook_Year                    !="+txtCB_Year+" or CASHBOOK_MONTH                    !="+txtCB_Month+" ) and DOC_TYPE='J'  order by PASSBOOK_DATE,CHEQUE_DD_NO";
  	                   
  	            	System.out.println(">>>>> "+qry);
  	            	ps = connection.prepareStatement(qry);
  	                   /* ps.setInt(1,cmbAcc_UnitCode );
  	                    ps.setInt(2,cmbOffice_code );
  	                    ps.setInt(3,txtCB_Year );
  	                    ps.setInt(4,txtCB_Month );
  	                    ps.setLong(5,cmbBankAccNo );    */                         
  	                    results = ps.executeQuery();
  	                    xml = xml + "<flag>success</flag>";
  	                    while (results.next()) {

  	                            Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
  	                            Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
  	                            Date EntryDate1=results.getDate("PASSBOOK_DATE");                                       
  	                    try {
  	                            Stringdate = RemitanceDate1.toString();
  	                    } catch (Exception e) {
  	                            Stringdate = "0000-00-00";
  	                    }
  	                    try {
  	                            Stringdate1 = WithdrawlDate1.toString();
  	                    } catch (Exception e) {
  	                            Stringdate1 = "0000-00-00";
  	                    }
  	                    try {
  	                            Stringdate2 = EntryDate1.toString();
  	                    } catch (Exception e) {
  	                            Stringdate2 = "0000-00-00";
  	                    }

  	                            String[] ddd = Stringdate.split("-");
  	                            String[] ddd1 = Stringdate1.split("-");
  	                            String[] ddd2 = Stringdate2.split("-");
  	                                                                    
  	                            int day =Integer.parseInt(ddd[2]);
  	                            int month =Integer.parseInt(ddd[1]);
  	                            int year = Integer.parseInt(ddd[0]);
  	                            
  	                            int day1 =Integer.parseInt(ddd1[2]);
  	                            int month1 =Integer.parseInt(ddd1[1]);
  	                            int year1 = Integer.parseInt(ddd1[0]);
  	                            
  	                            int day2 =Integer.parseInt(ddd2[2]);
  	                            int month2 =Integer.parseInt(ddd2[1]);
  	                            int year2 = Integer.parseInt(ddd2[0]);

  	                            
  	                            if(month>=10)
  	                    {
  	                                    RemitanceDate=(day+"/"+month+"/"+year);
  	                    }
  	                    else
  	                    {
  	                            RemitanceDate=(day+"/0"+month+"/"+year);        
  	                    }
  	                            
  	                            if(month1>=10)
  	                    {
  	                                    WithdrawlDate=(day1+"/"+month1+"/"+year1);
  	                    }
  	                    else
  	                    {
  	                            WithdrawlDate=(day1+"/0"+month1+"/"+year1);     
  	                    }
  	                            if(month2>=10)
  	                    {
  	                                    EntryDate=(day2+"/"+month2+"/"+year2);
  	                    }
  	                    else
  	                    {
  	                            EntryDate=(day2+"/0"+month2+"/"+year2); 
  	                    }
  	                            
  	                            T_or_NT = results.getString("TWAD_OR_NON_TWAD");
  	                            
  	                            if(T_or_NT.equals("T"))
  	                            {
  	                                    xml = xml + "<RemitanceDate>" + RemitanceDate
  	                                                    + "</RemitanceDate>";
  	                                    xml = xml + "<WithdrawlDate>" + WithdrawlDate
  	                                                    + "</WithdrawlDate>";
  	                                    xml = xml + "<Voucher_or_ChallanNo>"
  	                                                    + results.getInt("VOUCHER_OR_CHALLAN_NO")
  	                                                    + "</Voucher_or_ChallanNo>";
  	                                    xml = xml + "<Cheqe_or_DDNo>"
  	                                                    + results.getInt("CHEQUE_DD_NO")
  	                                                    + "</Cheqe_or_DDNo>";
  	                                    if(results.getString("DOC_TYPE").equals("SC"))
  	            						{
  	            							 xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
  	                                         drAmount += results.getDouble("CR_AMOUNT");
  	                                         xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
  	            						}
  	            						else
  	            						{
  	            							xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
  	                                        drAmount += results.getDouble("DR_AMOUNT");
  	                                        xml = xml + "<CRAmount>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount>";
  	                                        crAmount += results.getDouble("CR_AMOUNT");
  	            						}
  	                                 /*  if(results.getFloat("DR_AMOUNT")==0 ||results.getDouble("DR_AMOUNT")==0) {
  	                                       xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
  	                                        drAmount += results.getDouble("CR_AMOUNT");
  	                                        xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
  	                                    }
  	                                    else{
  	                                      //  System.out.println("else looppppppppp");
  	                                    xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
  	                                        drAmount += results.getDouble("DR_AMOUNT");
  	                                        xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
  	                                    }  */
  	                                    xml = xml + "<EntryFoundInPassBook>"
  	                                                    + results.getString("ENTRY_FOUND_IN_PASSBOOK")
  	                                                    + "</EntryFoundInPassBook>";
  	                                    xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
  	                                    xml = xml + "<Amount>"
  	                                                    + results.getInt("AMOUNT_IN_PASSBOOK")
  	                                                    + "</Amount>";
  	                                    xml = xml + "<Difference>"
  	                                                    + results.getBigDecimal("DIFFERENCE")
  	                                                    + "</Difference>";      
  	                                    xml = xml + "<doc_no>" + results.getInt("DOC_NO") + "</doc_no>";
  	                                    xml = xml + "<doc_type>" + results.getString("DOC_TYPE")
  	                                                    + "</doc_type>";
  	                                    rsndif = results.getString("REASON_FOR_DIFFERENCE");
  	                                    
  	                                    xml = xml + "<FollowUpAction>"
  	                                                    + results.getString("FOLLOW_UP_ACTION_REQUIRED")
  	                                                    + "</FollowUpAction>";
  	                                    xml = xml + "<Clearance>"
  	                                                    + results.getString("CLEARED_BASED_ON_FOLLOWUP")
  	                                                    + "</Clearance>";       
  	                                    
  	                                    try {
  	                                            ps1 = connection.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
  	                                            ps1.setInt(1, rsndiff1);
  	                                            rs = ps1.executeQuery();
  	                                            if (rs.next()) {
  	                                                    xml = xml + "<RsnForDiff>"
  	                                                    + rs.getString("REASON_SHORT_DESC")
  	                                                    + "</RsnForDiff>";                                                                                                                              
  	                                            }
  	                                            else
  	                                            {
  	                                                    xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
  	                                            }
  	                                    } catch (Exception e) {
  	                                            System.out.println(e);
  	                                    }
  	                            }else
  	                            {
  	                                    xml = xml + "<PassbookDate1>" + EntryDate
  	                                                    + "</PassbookDate1>";
  	                                    xml = xml + "<Particulars1>"
  	                                                    + results.getString("PARTICULARS")
  	                                                    + "</Particulars1>";
  	                                    xml = xml + "<Cheqe_or_DDNo1>"
  	                                                    + results.getInt("CHEQUE_DD_NO")
  	                                                    + "</Cheqe_or_DDNo1>";
  	                                    xml = xml + "<Details1>" + results.getString("DETAILS")
  	                                                    + "</Details1>";
  	                                  /*  xml = xml + "<CRAmount1>"
  	                                                    + results.getBigDecimal("CR_AMOUNT")
  	                                                    + "</CRAmount1>";
  	                                    xml = xml + "<DRAmount1>"
  	                                                    + results.getBigDecimal("DR_AMOUNT")
  	                                                    + "</DRAmount1>";       
  	                                    crAmount_bank += results.getDouble("CR_AMOUNT");
  	                                    drAmount_bank += results.getDouble("DR_AMOUNT");  */
  	                                    if(results.getString("DOC_TYPE").equals("SC"))
  	            						{
  	            							 xml = xml + "<DRAmount1>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
  	            							 drAmount_bank += results.getDouble("CR_AMOUNT");
  	                                         xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
  	            						}
  	            						else
  	            						{
  	            							xml = xml + "<DRAmount1>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
  	            							drAmount_bank += results.getDouble("DR_AMOUNT");
  	                                        xml = xml + "<CRAmount1>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
  	                                        crAmount_bank += results.getDouble("CR_AMOUNT");
  	            						}
  	                                    xml = xml
  	                                                    + "<FollowUpAction1>"
  	                                                    + results
  	                                                                    .getString("FOLLOW_UP_ACTION_REQUIRED")
  	                                                    + "</FollowUpAction1>";
  	                                    xml = xml
  	                                                    + "<Clearance1>"
  	                                                    + results
  	                                                                    .getString("CLEARED_BASED_ON_FOLLOWUP")
  	                                                    + "</Clearance1>";      
  	                                    xml = xml + "<doc_no1>" + results.getInt("DOC_NO") + "</doc_no1>";
  	                                    xml = xml + "<doc_type1>" + results.getString("DOC_TYPE")
  	                                                    + "</doc_type1>";
  	                                    tnscde = results.getString("TRANSACTION_TYPE");
  	                                    if(tnscde == null)
  	                                    {
  	                                            tnscde1 = 0;
  	                                    }
  	                                    else
  	                                    {
  	                                            tnscde1 = Integer.parseInt(tnscde);
  	                                    }
  	                                    
  	                                    try {
  	                                            ps1 = connection.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
  	                                            ps1.setInt(1, tnscde1);
  	                                            rs = ps1.executeQuery();
  	                                            if (rs.next()) {
  	                                                    xml = xml + "<RsnForDiff>"
  	                                                    + rs.getString("TRANS_SHORT_DESC")
  	                                                    + "</RsnForDiff>";                                                                                                                              
  	                                            }
  	                                            else
  	                                            {
  	                                                    xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
  	                                            }
  	                                    } catch (Exception e) {
  	                                            System.out.println(e);
  	                                    }
  	                            } count++;      
  	                    }
                           //   obj.format()
  	                    xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
  	                    xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
  	                    xml = xml + "<crAmount_bank>"+obj.format(crAmount_bank)+"</crAmount_bank>";
  	                    xml = xml + "<drAmount_bank>"+obj.format(drAmount_bank)+"</drAmount_bank>";
  	                	xml+="<count>"+count+"</count>";
  	            } catch (Exception e) {
  	                    xml = xml + "<flag>failure</flag>";
  	                    e.printStackTrace();
  	            }
  	            xml = xml + "</response>";
  	    		out.write(xml);
  	    		System.out.println(xml);
  	    
		
		
		
	} 
		
		//oly for cashbook Year & Month
                
                 else if (strCommand.equalsIgnoreCase("ListAllCash")) {
                	 	response.setContentType(CONTENT_TYPE);
             			PrintWriter out = response.getWriter();

                         xml = xml + "<response><command>ListAllCash</command>";

                         int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                         int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                         int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                         int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                         long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
                         
                         String RemitanceDate=null;
                         String WithdrawlDate=null;
                         String EntryDate=null;
                         String Stringdate=null;
                         String Stringdate1=null;
                         String Stringdate2=null;
                         String T_or_NT = null;                  
                         int rsndiff1 = 0;
                         String rsndif = null;
                         int tnscde1 = 0;
                         String tnscde = null;
                         double crAmount = 0;
                       
                         double drAmount = 0;
                         double crAmount_bank = 0;
                         double drAmount_bank = 0;
                         try {
                           //dhana modifications
                             ps = connection.prepareStatement("select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and doc_type!='J' and ACCOUNT_NO=? order by PASSBOOK_DATE,CHEQUE_DD_NO");
                                 ps.setInt(1,cmbAcc_UnitCode );
                                 ps.setInt(2,cmbOffice_code );
                                 ps.setInt(3,txtCB_Year );
                                 ps.setInt(4,txtCB_Month );
                                 ps.setLong(5,cmbBankAccNo );                             
                                 results = ps.executeQuery();
                                 xml = xml + "<flag>success</flag>";
                                 while (results.next()) {

                                         Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
                                         Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
                                         Date EntryDate1=results.getDate("PASSBOOK_DATE");  
                                         System.out.println("EntryDate1 >>>      "+EntryDate1);
                                 try {
                                         Stringdate = RemitanceDate1.toString();
                                 } catch (Exception e) {
                                         Stringdate = "0000-00-00";
                                 }
                                 try {
                                         Stringdate1 = WithdrawlDate1.toString();
                                 } catch (Exception e) {
                                         Stringdate1 = "0000-00-00";
                                 }
                                 try {
                                         Stringdate2 = EntryDate1.toString();
                                 } catch (Exception e) {
                                         Stringdate2 = "0000-00-00";
                                 }

                                         String[] ddd = Stringdate.split("-");
                                         String[] ddd1 = Stringdate1.split("-");
                                         String[] ddd2 = Stringdate2.split("-");
                                         System.out.println("ddd >>>      "+ddd);                                      
                                         int day =Integer.parseInt(ddd[2]);
                                         int month =Integer.parseInt(ddd[1]);
                                         int year = Integer.parseInt(ddd[0]);
                                         System.out.println("ddd1 >>>      "+ddd1);    
                                         int day1 =Integer.parseInt(ddd1[2]);
                                         int month1 =Integer.parseInt(ddd1[1]);
                                         int year1 = Integer.parseInt(ddd1[0]);
                                         System.out.println("ddd2 >>>      "+ddd2);    
                                         int day2 =Integer.parseInt(ddd2[2]);
                                         int month2 =Integer.parseInt(ddd2[1]);
                                         int year2 = Integer.parseInt(ddd2[0]);

                                         
                                         if(month>=10)
                                 {
                                                 RemitanceDate=(day+"/"+month+"/"+year);
                                 }
                                 else
                                 {
                                         RemitanceDate=(day+"/0"+month+"/"+year);        
                                 }
                                         
                                         if(month1>=10)
                                 {
                                                 WithdrawlDate=(day1+"/"+month1+"/"+year1);
                                 }
                                 else
                                 {
                                         WithdrawlDate=(day1+"/0"+month1+"/"+year1);     
                                 }
                                         if(month2>=10)
                                 {
                                                 EntryDate=(day2+"/"+month2+"/"+year2);
                                 }
                                 else
                                 {
                                         EntryDate=(day2+"/0"+month2+"/"+year2); 
                                 }
                                        
                                         T_or_NT = results.getString("TWAD_OR_NON_TWAD");
                                         System.out.println("T_or_NT >>>      "+T_or_NT);     
                                         if(T_or_NT.equals("T"))
                                         {
                                                 xml = xml + "<RemitanceDate>" + RemitanceDate
                                                                 + "</RemitanceDate>";
                                                 xml = xml + "<WithdrawlDate>" + WithdrawlDate
                                                                 + "</WithdrawlDate>";
                                                 xml = xml + "<Voucher_or_ChallanNo>"
                                                                 + results.getInt("VOUCHER_OR_CHALLAN_NO")
                                                                 + "</Voucher_or_ChallanNo>";
                                                 xml = xml + "<Cheqe_or_DDNo>"
                                                                 + results.getInt("CHEQUE_DD_NO")
                                                                 + "</Cheqe_or_DDNo>";
                                                 if(results.getString("DOC_TYPE").equals("SC"))
                         						{
                         							 xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
                                                      drAmount += results.getDouble("CR_AMOUNT");
                                                      xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                         						}
                         						else
                         						{
                         							xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                                                     drAmount += results.getDouble("DR_AMOUNT");
                                                     xml = xml + "<CRAmount>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount>";
                                                     crAmount += results.getDouble("CR_AMOUNT");
                         						}
                                              /*  if(results.getFloat("DR_AMOUNT")==0 ||results.getDouble("DR_AMOUNT")==0) {
                                                    xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
                                                     drAmount += results.getDouble("CR_AMOUNT");
                                                     xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                                                 }
                                                 else{
                                                   //  System.out.println("else looppppppppp");
                                                 xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                                                     drAmount += results.getDouble("DR_AMOUNT");
                                                     xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                                                 }  */
                                                 System.out.println("drAmount >>>      "+drAmount);     
                                                 System.out.println("crAmount >>>      "+crAmount);     
                                                 xml = xml + "<EntryFoundInPassBook>"
                                                                 + results.getString("ENTRY_FOUND_IN_PASSBOOK")
                                                                 + "</EntryFoundInPassBook>";
                                                 xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
                                                 xml = xml + "<Amount>"
                                                                 + results.getInt("AMOUNT_IN_PASSBOOK")
                                                                 + "</Amount>";
                                                 xml = xml + "<Difference>"
                                                                 + results.getBigDecimal("DIFFERENCE")
                                                                 + "</Difference>";      
                                                 xml = xml + "<doc_no>" + results.getInt("DOC_NO") + "</doc_no>";
                                                 xml = xml + "<doc_type>" + results.getString("DOC_TYPE")
                                                                 + "</doc_type>";
                                                 rsndif = results.getString("REASON_FOR_DIFFERENCE");
                                                 
                                                 xml = xml + "<FollowUpAction>"
                                                                 + results.getString("FOLLOW_UP_ACTION_REQUIRED")
                                                                 + "</FollowUpAction>";
                                                 xml = xml + "<Clearance>"
                                                                 + results.getString("CLEARED_BASED_ON_FOLLOWUP")
                                                                 + "</Clearance>";       
                                                 
                                                 try {
                                                         ps1 = connection.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
                                                         ps1.setInt(1, rsndiff1);
                                                         rs = ps1.executeQuery();
                                                         if (rs.next()) {
                                                                 xml = xml + "<RsnForDiff>"
                                                                 + rs.getString("REASON_SHORT_DESC")
                                                                 + "</RsnForDiff>";                                                                                                                              
                                                         }
                                                         else
                                                         {
                                                                 xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
                                                         }
                                                 } catch (Exception e) {
                                                         System.out.println(e);
                                                 }
                                         }else
                                         {
                                                 xml = xml + "<PassbookDate1>" + EntryDate
                                                                 + "</PassbookDate1>";
                                                 xml = xml + "<Particulars1>"
                                                                 + results.getString("PARTICULARS")
                                                                 + "</Particulars1>";
                                                 xml = xml + "<Cheqe_or_DDNo1>"
                                                                 + results.getInt("CHEQUE_DD_NO")
                                                                 + "</Cheqe_or_DDNo1>";
                                                 xml = xml + "<Details1>" + results.getString("DETAILS")
                                                                 + "</Details1>";
                                                /* xml = xml + "<CRAmount1>"
                                                                 + results.getBigDecimal("CR_AMOUNT")
                                                                 + "</CRAmount1>";
                                                 xml = xml + "<DRAmount1>"
                                                                 + results.getBigDecimal("DR_AMOUNT")
                                                                 + "</DRAmount1>";       
                                                 crAmount_bank += results.getDouble("CR_AMOUNT");
                                                 drAmount_bank += results.getDouble("DR_AMOUNT");  */
                                                 if(results.getString("DOC_TYPE").equals("SC"))
                         						{
                         							 xml = xml + "<DRAmount1>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
                         							 drAmount_bank += results.getDouble("CR_AMOUNT");
                                                      xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
                         						}
                         						else
                         						{
                         							xml = xml + "<DRAmount1>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
                         							drAmount_bank += results.getDouble("DR_AMOUNT");
                                                     xml = xml + "<CRAmount1>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
                                                     crAmount_bank += results.getDouble("CR_AMOUNT");
                         						}
                                                 xml = xml
                                                                 + "<FollowUpAction1>"
                                                                 + results
                                                                                 .getString("FOLLOW_UP_ACTION_REQUIRED")
                                                                 + "</FollowUpAction1>";
                                                 xml = xml
                                                                 + "<Clearance1>"
                                                                 + results
                                                                                 .getString("CLEARED_BASED_ON_FOLLOWUP")
                                                                 + "</Clearance1>";      
                                                 xml = xml + "<doc_no1>" + results.getInt("DOC_NO") + "</doc_no1>";
                                                 xml = xml + "<doc_type1>" + results.getString("DOC_TYPE")
                                                                 + "</doc_type1>";
                                                 tnscde = results.getString("TRANSACTION_TYPE");
                                                 if(tnscde == null)
                                                 {
                                                         tnscde1 = 0;
                                                 }
                                                 else
                                                 {
                                                         tnscde1 = Integer.parseInt(tnscde);
                                                 }
                                                 
                                                 try {
                                                         ps1 = connection.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
                                                         ps1.setInt(1, tnscde1);
                                                         rs = ps1.executeQuery();
                                                         if (rs.next()) {
                                                                 xml = xml + "<RsnForDiff>"
                                                                 + rs.getString("TRANS_SHORT_DESC")
                                                                 + "</RsnForDiff>";                                                                                                                              
                                                         }
                                                         else
                                                         {
                                                                 xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
                                                         }
                                                 } catch (Exception e) {
                                                         System.out.println(e);
                                                 }
                                         }       
                                 }
                                 xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
                                 xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
                                 xml = xml + "<crAmount_bank>"+obj.format(crAmount_bank)+"</crAmount_bank>";
                                 xml = xml + "<drAmount_bank>"+obj.format(drAmount_bank)+"</drAmount_bank>";
                         } catch (Exception e) {
                                 xml = xml + "<flag>failure</flag>";
                                 e.printStackTrace();
                         } 
                         xml = xml + "</response>";
                 		out.write(xml);
                 		System.out.println(xml);
                 }
                
                //oly for passh Sheet
                else if (strCommand.equalsIgnoreCase("ListAllPassSheet")) {
                	response.setContentType(CONTENT_TYPE);
            		PrintWriter out = response.getWriter();

			xml = xml + "<response><command>ListAllPassSheet</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			String RemitanceDate=null;
			String WithdrawlDate=null;
			String EntryDate=null;
			String Stringdate=null;
			String Stringdate1=null;
			String Stringdate2=null;
			String T_or_NT = null;			
			int rsndiff1 = 0;
			String rsndif = null;
			int tnscde1 = 0;
			String tnscde = null;
			double crAmount = 0;
                      
			double drAmount = 0;
			double crAmount_bank = 0;
			double drAmount_bank = 0;
			try {
		
			
				//joan change on 13 Jan 2015 for IBT 
				//	ps = connection.prepareStatement("select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and extract (year from PASSBOOK_DATE)=? and extract (month from PASSBOOK_DATE)=? and ACCOUNT_NO=? and DOC_TYPE!='J' order by PASSBOOK_DATE,CHEQUE_DD_NO");
				ps = connection.prepareStatement("SELECT TWAD_OR_NON_TWAD, " +
"  REMITTANCE_DATE, " +
"  WITHDRAWAL_DATE, " +
"  VOUCHER_OR_CHALLAN_NO, " +
"  CHEQUE_DD_NO, " +
//"  CASE " +
//"    WHEN DOC_TYPE='IBT' " +
//"    THEN DRAMT " +
//"    ELSE CRAMT " +
//"  END AS CR_AMOUNT, " +
//"  CASE " +
//"    WHEN DOC_TYPE='IBT' " +
//"    THEN cRAMT " +
//"    ELSE dRamt " +
//"  END AS DR_AMOUNT, " +
"  CRAMT AS CR_AMOUNT, " +
"  DRAMT AS DR_AMOUNT, " +
"  ENTRY_FOUND_IN_PASSBOOK, " +
"  PASSBOOK_DATE, " +
"  AMOUNT_IN_PASSBOOK, " +
"  DIFFERENCE, " +
"  REASON_FOR_DIFFERENCE, " +
"  FOLLOW_UP_ACTION_REQUIRED, " +
"  CLEARED_BASED_ON_FOLLOWUP, " +
"  PARTICULARS, " +
"  DETAILS, " +
"  TRANSACTION_TYPE, " +
"  DOC_NO, " +
"  DOC_TYPE " +
" FROM " +
"  (SELECT TWAD_OR_NON_TWAD, " +
"    REMITTANCE_DATE, " +
"    WITHDRAWAL_DATE, " +
"    VOUCHER_OR_CHALLAN_NO, " +
"    CHEQUE_DD_NO, " +
"    CR_AMOUNT AS CRAMT, " +
"    DR_AMOUNT AS dramt, " +
"    ENTRY_FOUND_IN_PASSBOOK, " +
"    PASSBOOK_DATE, " +
"    AMOUNT_IN_PASSBOOK, " +
"    DIFFERENCE, " +
"    REASON_FOR_DIFFERENCE, " +
"    FOLLOW_UP_ACTION_REQUIRED, " +
"    CLEARED_BASED_ON_FOLLOWUP, " +
"    PARTICULARS, " +
"    DETAILS, " +
"    TRANSACTION_TYPE, " +
"    DOC_NO, " +
"    DOC_TYPE " +
"  FROM FAS_BRS_TRANSACTION " +
"  WHERE ACCOUNTING_UNIT_ID              =? " +
"  AND ACCOUNTING_FOR_OFFICE_ID          =? " +
"  AND EXTRACT (YEAR FROM PASSBOOK_DATE) =? " +
"  AND EXTRACT (MONTH FROM PASSBOOK_DATE)=? " +
"  AND ACCOUNT_NO                        =? " +
"  AND DOC_TYPE!                         ='J' " +
"  ORDER BY PASSBOOK_DATE, " +
"    CHEQUE_DD_NO " +
"  )s");

				ps.setInt(1,cmbAcc_UnitCode );
				ps.setInt(2,cmbOffice_code );
				ps.setInt(3,txtCB_Year );
				ps.setInt(4,txtCB_Month );
				ps.setLong(5,cmbBankAccNo );				
				results = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (results.next()) {
 
					Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
					Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
					Date EntryDate1=results.getDate("PASSBOOK_DATE");					
				try {
					Stringdate = RemitanceDate1.toString();
				} catch (Exception e) {
					Stringdate = "0000-00-00";
				}
				try {
					Stringdate1 = WithdrawlDate1.toString();
				} catch (Exception e) {
					Stringdate1 = "0000-00-00";
				}
				try {
					Stringdate2 = EntryDate1.toString();
				} catch (Exception e) {
					Stringdate2 = "0000-00-00";
				}

					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					String[] ddd2 = Stringdate2.split("-");
										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
					int day2 =Integer.parseInt(ddd2[2]);
					int month2 =Integer.parseInt(ddd2[1]);
					int year2 = Integer.parseInt(ddd2[0]);

					
					if(month>=10)
			        {
						RemitanceDate=(day+"/"+month+"/"+year);
			        }
			        else
			        {
			        	RemitanceDate=(day+"/0"+month+"/"+year);	
			        }
					
					if(month1>=10)
			        {
						WithdrawlDate=(day1+"/"+month1+"/"+year1);
			        }
			        else
			        {
			        	WithdrawlDate=(day1+"/0"+month1+"/"+year1);	
			        }
					if(month2>=10)
			        {
						EntryDate=(day2+"/"+month2+"/"+year2);
			        }
			        else
			        {
			        	EntryDate=(day2+"/0"+month2+"/"+year2);	
			        }
					
					T_or_NT = results.getString("TWAD_OR_NON_TWAD");
					
					if(T_or_NT.equals("T"))
					{
						xml = xml + "<RemitanceDate>" + RemitanceDate
								+ "</RemitanceDate>";
						xml = xml + "<WithdrawlDate>" + WithdrawlDate
								+ "</WithdrawlDate>";
						xml = xml + "<Voucher_or_ChallanNo>"
								+ results.getInt("VOUCHER_OR_CHALLAN_NO")
								+ "</Voucher_or_ChallanNo>";
						xml = xml + "<Cheqe_or_DDNo>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo>";
						
                                                //dhana
                      /*   if(results.getFloat("DR_AMOUNT")==0 ||results.getDouble("DR_AMOUNT")==0) {
                             xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
                              drAmount += results.getDouble("CR_AMOUNT");
                              xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                          }
                          else{
                            //  System.out.println("else looppppppppp");
                          xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                              drAmount += results.getDouble("DR_AMOUNT");
                              xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
                          }  */
						
						if(results.getString("DOC_TYPE").equals("SC"))
						{
//							 xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
//                             drAmount += results.getDouble("CR_AMOUNT");
//                             xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
							
							xml = xml + "<DRAmount>" + results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                            drAmount += results.getDouble("DR_AMOUNT");
                            xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
							
						}
						else
						{
							xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
                            drAmount += results.getDouble("DR_AMOUNT");
                            xml = xml + "<CRAmount>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount>";
                            crAmount += results.getDouble("CR_AMOUNT");
						}
						xml = xml + "<EntryFoundInPassBook>"
								+ results.getString("ENTRY_FOUND_IN_PASSBOOK")
								+ "</EntryFoundInPassBook>";
						xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
						xml = xml + "<Amount>"
								+ results.getInt("AMOUNT_IN_PASSBOOK")
								+ "</Amount>";
						xml = xml + "<Difference>"
								+ results.getBigDecimal("DIFFERENCE")
								+ "</Difference>";	
						xml = xml + "<doc_no>" + results.getInt("DOC_NO") + "</doc_no>";
						xml = xml + "<doc_type>" + results.getString("DOC_TYPE")
								+ "</doc_type>";
						rsndif = results.getString("REASON_FOR_DIFFERENCE");
						
						xml = xml + "<FollowUpAction>"
								+ results.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction>";
						xml = xml + "<Clearance>"
								+ results.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance>";	
						
						try {
							ps1 = connection.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
							ps1.setInt(1, rsndiff1);
							rs = ps1.executeQuery();
							if (rs.next()) {
								xml = xml + "<RsnForDiff>"
								+ rs.getString("REASON_SHORT_DESC")
								+ "</RsnForDiff>";																
							}
							else
							{
								xml = xml + "<RsnForDiff>null</RsnForDiff>";								
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}else
					{
						xml = xml + "<PassbookDate1>" + EntryDate
								+ "</PassbookDate1>";
						xml = xml + "<Particulars1>"
								+ results.getString("PARTICULARS")
								+ "</Particulars1>";
						xml = xml + "<Cheqe_or_DDNo1>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo1>";
						xml = xml + "<Details1>" + results.getString("DETAILS")
								+ "</Details1>";
						/* xml = xml + "<CRAmount1>"
								+ results.getBigDecimal("CR_AMOUNT")
								+ "</CRAmount1>";
						xml = xml + "<DRAmount1>"
								+ results.getBigDecimal("DR_AMOUNT")
								+ "</DRAmount1>";	
						crAmount_bank += results.getDouble("CR_AMOUNT");
						drAmount_bank += results.getDouble("DR_AMOUNT");  */
						if(results.getString("DOC_TYPE").equals("SC"))
						{
//							 xml = xml + "<DRAmount1>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
//							 drAmount_bank += results.getDouble("CR_AMOUNT");
//                             xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
							
							
							 xml = xml + "<DRAmount1>" + results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
							 drAmount_bank += results.getDouble("DR_AMOUNT");
                             xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
							
						}
						else
						{
							xml = xml + "<DRAmount1>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
							drAmount_bank += results.getDouble("DR_AMOUNT");
                            xml = xml + "<CRAmount1>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
                            crAmount_bank += results.getDouble("CR_AMOUNT");
						}
						xml = xml
								+ "<FollowUpAction1>"
								+ results
										.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction1>";
						xml = xml
								+ "<Clearance1>"
								+ results
										.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance1>";	
						xml = xml + "<doc_no1>" + results.getInt("DOC_NO") + "</doc_no1>";
						xml = xml + "<doc_type1>" + results.getString("DOC_TYPE")
								+ "</doc_type1>";
						tnscde = results.getString("TRANSACTION_TYPE");
						if(tnscde == null)
						{
							tnscde1 = 0;
						}
						else
						{
							tnscde1 = Integer.parseInt(tnscde);
						}
						
						try {
							ps1 = connection.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
							ps1.setInt(1, tnscde1);
							rs = ps1.executeQuery();
							if (rs.next()) {
								xml = xml + "<RsnForDiff1>"
								+ rs.getString("TRANS_SHORT_DESC")
								+ "</RsnForDiff1>";																
							}
							else
							{
								xml = xml + "<RsnForDiff1>null</RsnForDiff1>";								
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}	
				}
				xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
				xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
				xml = xml + "<crAmount_bank>"+obj.format(crAmount_bank)+"</crAmount_bank>";
				xml = xml + "<drAmount_bank>"+obj.format(drAmount_bank)+"</drAmount_bank>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		} else if (strCommand.equalsIgnoreCase("ListAll_acknowledged")) {
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();

	            xml = xml + "<response><command>ListAll_acknowledged</command>";

	            int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	            int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
	            int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
	            int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
	            long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
	            
	            String RemitanceDate=null;
	            String WithdrawlDate=null;
	            String EntryDate=null;
	            String Stringdate=null;
	            String Stringdate1=null;
	            String Stringdate2=null;
	            String T_or_NT = null;                  
	            int rsndiff1 = 0;
	            String rsndif = null;
	            int tnscde1 = 0;
	            String tnscde = null;
	            double crAmount = 0;
	            double drAmount = 0;
	            double crAmount_bank = 0;
	            double drAmount_bank = 0;
	            try {
	                    ps = connection.prepareStatement("select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and extract (year from PASSBOOK_DATE)=? and extract (month from PASSBOOK_DATE)=? and ACCOUNT_NO=? And (Cashbook_Year                    !="+txtCB_Year+" or CASHBOOK_MONTH                    !="+txtCB_Month+" ) and DOC_TYPE!='J'  order by PASSBOOK_DATE,CHEQUE_DD_NO");
	                    ps.setInt(1,cmbAcc_UnitCode );
	                    ps.setInt(2,cmbOffice_code );
	                    ps.setInt(3,txtCB_Year );
	                    ps.setInt(4,txtCB_Month );
	                    ps.setLong(5,cmbBankAccNo );                             
	                    results = ps.executeQuery();
	                    xml = xml + "<flag>success</flag>";
	                    while (results.next()) {

	                            Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
	                            Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
	                            Date EntryDate1=results.getDate("PASSBOOK_DATE");                                       
	                    try {
	                            Stringdate = RemitanceDate1.toString();
	                    } catch (Exception e) {
	                            Stringdate = "0000-00-00";
	                    }
	                    try {
	                            Stringdate1 = WithdrawlDate1.toString();
	                    } catch (Exception e) {
	                            Stringdate1 = "0000-00-00";
	                    }
	                    try {
	                            Stringdate2 = EntryDate1.toString();
	                    } catch (Exception e) {
	                            Stringdate2 = "0000-00-00";
	                    }

	                            String[] ddd = Stringdate.split("-");
	                            String[] ddd1 = Stringdate1.split("-");
	                            String[] ddd2 = Stringdate2.split("-");
	                                                                    
	                            int day =Integer.parseInt(ddd[2]);
	                            int month =Integer.parseInt(ddd[1]);
	                            int year = Integer.parseInt(ddd[0]);
	                            
	                            int day1 =Integer.parseInt(ddd1[2]);
	                            int month1 =Integer.parseInt(ddd1[1]);
	                            int year1 = Integer.parseInt(ddd1[0]);
	                            
	                            int day2 =Integer.parseInt(ddd2[2]);
	                            int month2 =Integer.parseInt(ddd2[1]);
	                            int year2 = Integer.parseInt(ddd2[0]);

	                            
	                            if(month>=10)
	                    {
	                                    RemitanceDate=(day+"/"+month+"/"+year);
	                    }
	                    else
	                    {
	                            RemitanceDate=(day+"/0"+month+"/"+year);        
	                    }
	                            
	                            if(month1>=10)
	                    {
	                                    WithdrawlDate=(day1+"/"+month1+"/"+year1);
	                    }
	                    else
	                    {
	                            WithdrawlDate=(day1+"/0"+month1+"/"+year1);     
	                    }
	                            if(month2>=10)
	                    {
	                                    EntryDate=(day2+"/"+month2+"/"+year2);
	                    }
	                    else
	                    {
	                            EntryDate=(day2+"/0"+month2+"/"+year2); 
	                    }
	                            
	                            T_or_NT = results.getString("TWAD_OR_NON_TWAD");
	                            
	                            if(T_or_NT.equals("T"))
	                            {
	                                    xml = xml + "<RemitanceDate>" + RemitanceDate
	                                                    + "</RemitanceDate>";
	                                    xml = xml + "<WithdrawlDate>" + WithdrawlDate
	                                                    + "</WithdrawlDate>";
	                                    xml = xml + "<Voucher_or_ChallanNo>"
	                                                    + results.getInt("VOUCHER_OR_CHALLAN_NO")
	                                                    + "</Voucher_or_ChallanNo>";
	                                    xml = xml + "<Cheqe_or_DDNo>"
	                                                    + results.getInt("CHEQUE_DD_NO")
	                                                    + "</Cheqe_or_DDNo>";
	                                    if(results.getString("DOC_TYPE").equals("SC"))
	            						{
	            							 xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
	                                         drAmount += results.getDouble("CR_AMOUNT");
	                                         xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
	            						}
	            						else
	            						{
	            							xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
	                                        drAmount += results.getDouble("DR_AMOUNT");
	                                        xml = xml + "<CRAmount>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount>";
	                                        crAmount += results.getDouble("CR_AMOUNT");
	            						}
	                                 /*  if(results.getFloat("DR_AMOUNT")==0 ||results.getDouble("DR_AMOUNT")==0) {
	                                       xml = xml + "<DRAmount>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount>";
	                                        drAmount += results.getDouble("CR_AMOUNT");
	                                        xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
	                                    }
	                                    else{
	                                      //  System.out.println("else looppppppppp");
	                                    xml = xml + "<DRAmount>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount>";
	                                        drAmount += results.getDouble("DR_AMOUNT");
	                                        xml = xml + "<CRAmount>" + 0+ "</CRAmount>";
	                                    }  */
	                                    xml = xml + "<EntryFoundInPassBook>"
	                                                    + results.getString("ENTRY_FOUND_IN_PASSBOOK")
	                                                    + "</EntryFoundInPassBook>";
	                                    xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
	                                    xml = xml + "<Amount>"
	                                                    + results.getInt("AMOUNT_IN_PASSBOOK")
	                                                    + "</Amount>";
	                                    xml = xml + "<Difference>"
	                                                    + results.getBigDecimal("DIFFERENCE")
	                                                    + "</Difference>";      
	                                    xml = xml + "<doc_no>" + results.getInt("DOC_NO") + "</doc_no>";
	                                    xml = xml + "<doc_type>" + results.getString("DOC_TYPE")
	                                                    + "</doc_type>";
	                                    rsndif = results.getString("REASON_FOR_DIFFERENCE");
	                                    
	                                    xml = xml + "<FollowUpAction>"
	                                                    + results.getString("FOLLOW_UP_ACTION_REQUIRED")
	                                                    + "</FollowUpAction>";
	                                    xml = xml + "<Clearance>"
	                                                    + results.getString("CLEARED_BASED_ON_FOLLOWUP")
	                                                    + "</Clearance>";       
	                                    
	                                    try {
	                                            ps1 = connection.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
	                                            ps1.setInt(1, rsndiff1);
	                                            rs = ps1.executeQuery();
	                                            if (rs.next()) {
	                                                    xml = xml + "<RsnForDiff>"
	                                                    + rs.getString("REASON_SHORT_DESC")
	                                                    + "</RsnForDiff>";                                                                                                                              
	                                            }
	                                            else
	                                            {
	                                                    xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
	                                            }
	                                    } catch (Exception e) {
	                                            System.out.println(e);
	                                    }
	                            }else
	                            {
	                                    xml = xml + "<PassbookDate1>" + EntryDate
	                                                    + "</PassbookDate1>";
	                                    xml = xml + "<Particulars1>"
	                                                    + results.getString("PARTICULARS")
	                                                    + "</Particulars1>";
	                                    xml = xml + "<Cheqe_or_DDNo1>"
	                                                    + results.getInt("CHEQUE_DD_NO")
	                                                    + "</Cheqe_or_DDNo1>";
	                                    xml = xml + "<Details1>" + results.getString("DETAILS")
	                                                    + "</Details1>";
	                                  /*  xml = xml + "<CRAmount1>"
	                                                    + results.getBigDecimal("CR_AMOUNT")
	                                                    + "</CRAmount1>";
	                                    xml = xml + "<DRAmount1>"
	                                                    + results.getBigDecimal("DR_AMOUNT")
	                                                    + "</DRAmount1>";       
	                                    crAmount_bank += results.getDouble("CR_AMOUNT");
	                                    drAmount_bank += results.getDouble("DR_AMOUNT");  */
	                                    if(results.getString("DOC_TYPE").equals("SC"))
	            						{
	            							 xml = xml + "<DRAmount1>" + results.getBigDecimal("CR_AMOUNT")+ "</DRAmount1>";
	            							 drAmount_bank += results.getDouble("CR_AMOUNT");
	                                         xml = xml + "<CRAmount1>" + 0+ "</CRAmount1>";
	            						}
	            						else
	            						{
	            							xml = xml + "<DRAmount1>"+ results.getBigDecimal("DR_AMOUNT")+ "</DRAmount1>";
	            							drAmount_bank += results.getDouble("DR_AMOUNT");
	                                        xml = xml + "<CRAmount1>" +results.getBigDecimal("CR_AMOUNT")+ "</CRAmount1>";
	                                        crAmount_bank += results.getDouble("CR_AMOUNT");
	            						}
	                                    xml = xml
	                                                    + "<FollowUpAction1>"
	                                                    + results
	                                                                    .getString("FOLLOW_UP_ACTION_REQUIRED")
	                                                    + "</FollowUpAction1>";
	                                    xml = xml
	                                                    + "<Clearance1>"
	                                                    + results
	                                                                    .getString("CLEARED_BASED_ON_FOLLOWUP")
	                                                    + "</Clearance1>";      
	                                    xml = xml + "<doc_no1>" + results.getInt("DOC_NO") + "</doc_no1>";
	                                    xml = xml + "<doc_type1>" + results.getString("DOC_TYPE")
	                                                    + "</doc_type1>";
	                                    tnscde = results.getString("TRANSACTION_TYPE");
	                                    if(tnscde == null)
	                                    {
	                                            tnscde1 = 0;
	                                    }
	                                    else
	                                    {
	                                            tnscde1 = Integer.parseInt(tnscde);
	                                    }
	                                    
	                                    try {
	                                            ps1 = connection.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
	                                            ps1.setInt(1, tnscde1);
	                                            rs = ps1.executeQuery();
	                                            if (rs.next()) {
	                                                    xml = xml + "<RsnForDiff>"
	                                                    + rs.getString("TRANS_SHORT_DESC")
	                                                    + "</RsnForDiff>";                                                                                                                              
	                                            }
	                                            else
	                                            {
	                                                    xml = xml + "<RsnForDiff>null</RsnForDiff>";                                                            
	                                            }
	                                    } catch (Exception e) {
	                                            System.out.println(e);
	                                    }
	                            }       
	                    }
                         //   obj.format()
	                    xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
	                    xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
	                    xml = xml + "<crAmount_bank>"+obj.format(crAmount_bank)+"</crAmount_bank>";
	                    xml = xml + "<drAmount_bank>"+obj.format(drAmount_bank)+"</drAmount_bank>";
	            } catch (Exception e) {
	                    xml = xml + "<flag>failure</flag>";
	                    e.printStackTrace();
	            }
	            xml = xml + "</response>";
	    		out.write(xml);
	    		System.out.println(xml);
	    } else if(strCommand.equalsIgnoreCase("printAll")){
	    	try{
        		String s = "",myQry="",setQury="",AccType="";
        		int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
    			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
    			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
    			long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
    			String[] accNo_text=(request.getParameter("cmbBankAccNo_text")).split("-");
    			
    			Map map = new HashMap();
            	Map<Integer,String> monthlist = new HashMap<Integer,String>();
            	monthlist.put(1,"January");
            	monthlist.put(2,"February");
            	monthlist.put(3,"March");
            	monthlist.put(4,"April");
            	monthlist.put(5,"May");
            	monthlist.put(6,"June");
            	monthlist.put(7,"July");
            	monthlist.put(8,"August");
            	monthlist.put(9,"September");
            	monthlist.put(10,"October");
            	monthlist.put(11,"November");
            	monthlist.put(12,"December");
            	map.put("year_from", txtCB_Year);
            	
        		map.put("month_from", monthlist.get(txtCB_Month));
        		if(accNo_text[0].equalsIgnoreCase("COL"))
        		{
        			AccType=accNo_text[2]+" For Collection A/c :"+cmbBankAccNo;
        		}
        		else
        		{
        			AccType=accNo_text[2]+" For Operation A/c :"+cmbBankAccNo;
        		}
        		map.put("AccType", AccType);
        		//map.put("bankname", accNo_text[2]);
        	
        		
    			
    			String listType = request.getParameter("typelist");
    			if(listType.equalsIgnoreCase("list1")){
    				setQury=" WHERE ACCOUNTING_UNIT_ID    ='"+cmbAcc_UnitCode+"' " +
            		"AND ACCOUNTING_FOR_OFFICE_ID         ='"+cmbOffice_code+"' " +
            		"AND CASHBOOK_YEAR                    ='"+txtCB_Year+"' " +
            		"AND CASHBOOK_MONTH                   ='"+txtCB_Month+"' " +
            		"AND ACCOUNT_NO                       ='"+cmbBankAccNo+"' " +
            		"AND Extract(MONTH FROM Passbook_Date)='"+txtCB_Month+"' " +
            		"AND Extract(YEAR FROM Passbook_Date) ='"+txtCB_Year+"' ";
    			}else if(listType.equalsIgnoreCase("list2")){
    				setQury=" WHERE ACCOUNTING_UNIT_ID          ='"+cmbAcc_UnitCode+"' " +
    						"AND ACCOUNTING_FOR_OFFICE_ID         ='"+cmbOffice_code+"' " +
    						"AND Extract(MONTH FROM Passbook_Date)='"+txtCB_Month+"' " +
    						"AND Extract(YEAR FROM Passbook_Date) ='"+txtCB_Year+"' " +
    						"AND ACCOUNT_NO                       ='"+cmbBankAccNo+"' " +
    						"AND (Cashbook_Year                   !='"+txtCB_Year+"' OR CASHBOOK_MONTH !='"+txtCB_Month+"' )";    						
    			}else if(listType.equalsIgnoreCase("list3")){
    				setQury=" WHERE ACCOUNTING_UNIT_ID    ='"+cmbAcc_UnitCode+"' " +
            		"AND ACCOUNTING_FOR_OFFICE_ID         ='"+cmbOffice_code+"' " +
            		"AND ACCOUNT_NO                       ='"+cmbBankAccNo+"' " +
            		"AND Extract(MONTH FROM Passbook_Date)='"+txtCB_Month+"' " +
            		"AND Extract(YEAR FROM Passbook_Date) ='"+txtCB_Year+"' ";
    			}
    			if(request.getParameter("trans").equalsIgnoreCase("T")){
    				setQury+=" AND TWAD_OR_NON_TWAD ='T' ";
    			}else{
    				setQury+=" AND TWAD_OR_NON_TWAD NOT IN ('T') ";
    			}
        		myQry="SELECT a.TWAD_OR_NON_TWAD     AS TWAD_OR_NON_TWAD, " +
        		"  a.ACCOUNTING_UNIT_ID        AS ACCOUNTING_UNIT_ID, " +
        		"  a.ACCOUNTING_FOR_OFFICE_ID  AS ACCOUNTING_FOR_OFFICE_ID, " +
        		"  DECODE(a.REMITTANCE_DATE,NULL,'-',a.REMITTANCE_DATE) AS REMITTANCE_DATE, " +
        		"  DECODE(a.WITHDRAWAL_DATE,NULL,'-',a.WITHDRAWAL_DATE) AS WITHDRAWAL_DATE, " +
        		"  a.VOUCHER_OR_CHALLAN_NO     AS VOUCHER_OR_CHALLAN_NO, " +
        		"  a.CHEQUE_DD_NO              AS CHEQUE_DD_NO, " +
        		"  a.CR_AMOUNT                 AS CR_AMOUNT, " +
        		"  a.DR_AMOUNT                 AS DR_AMOUNT, " +
        		"  a.ENTRY_FOUND_IN_PASSBOOK   AS ENTRY_FOUND_IN_PASSBOOK, " +
        		"  DECODE(a.PASSBOOK_DATE,NULL,'-',a.PASSBOOK_DATE) AS PASSBOOK_DATE, " +
        		"  a.AMOUNT_IN_PASSBOOK        AS AMOUNT_IN_PASSBOOK, " +
        		"  a.DIFFERENCE                AS DIFFERENCE, " +
        		"  a.REASON_FOR_DIFFERENCE1    AS REASON_FOR_DIFFERENCE1, " +
        		"  DECODE(a.FOLLOW_UP_ACTION_REQUIRED,NULL,'-',a.FOLLOW_UP_ACTION_REQUIRED) AS FOLLOW_UP_ACTION_REQUIRED, " +
        		"  DECODE(a.CLEARED_BASED_ON_FOLLOWUP,NULL,'-',a.CLEARED_BASED_ON_FOLLOWUP) AS CLEARED_BASED_ON_FOLLOWUP, " +
        		"  a.PARTICULARS               AS PARTICULARS, " +
        		"  a.DETAILS                   AS DETAILS, " +
        		"  a.TRANSACTION_TYPE          AS TRANSACTION_TYPE, " +
        		"  a.DOC_NO                    AS DOC_NO, " +
        		"  a.DOC_TYPE                  AS DOC_TYPE, " +
        		"  b.accounting_unit_name      AS accounting_unit_name, " +
        		"  c.OFFICE_NAME               AS OFFICE_NAME, " +
        		"  d.TRANS_SHORT_DESC          AS TRANS_SHORT_DESC " +
        		"FROM " +
        		"  (SELECT TWAD_OR_NON_TWAD, " +
        		"    ACCOUNTING_UNIT_ID, " +
        		"    ACCOUNTING_FOR_OFFICE_ID, " +
        		"    TO_CHAR(REMITTANCE_DATE,'dd/MM/yyyy')            AS REMITTANCE_DATE, " +
        		"    TO_CHAR(WITHDRAWAL_DATE,'dd/MM/yyyy')            AS WITHDRAWAL_DATE, " +
        		"    VOUCHER_OR_CHALLAN_NO, " +
        		"    CHEQUE_DD_NO, " +
        		"    CR_AMOUNT, " +
        		"    DR_AMOUNT, " +
        		"    ENTRY_FOUND_IN_PASSBOOK, " +
        		"    TO_CHAR(PASSBOOK_DATE,'dd/MM/yyyy')            AS PASSBOOK_DATE, " +
        		"    AMOUNT_IN_PASSBOOK, " +
        		"    DIFFERENCE, " +
        		"    DECODE(REASON_FOR_DIFFERENCE,NULL,'-',REASON_FOR_DIFFERENCE) AS REASON_FOR_DIFFERENCE1, " +
        		"    FOLLOW_UP_ACTION_REQUIRED, " +
        		"    CLEARED_BASED_ON_FOLLOWUP, " +
        		"    PARTICULARS, " +
        		"    DETAILS, " +
        		"    TRANSACTION_TYPE, " +
        		"    DOC_NO, " +
        		"    DOC_TYPE " +
        		"  FROM FAS_BRS_TRANSACTION " +        		
        			setQury +
        		"  )a " +
        		"LEFT OUTER JOIN " +
        		"  (SELECT ACCOUNTING_UNIT_ID, accounting_unit_name FROM fas_mst_acct_units " +
        		"  ) b " +
        		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
        		"LEFT OUTER JOIN " +
        		"  ( SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
        		"  )c " +
        		"ON a.ACCOUNTING_FOR_OFFICE_ID=c.OFFICE_ID " +
        		"LEFT OUTER JOIN " +
        		"  ( SELECT TRANS_CODE,TRANS_SHORT_DESC FROM FAS_BRS_TRANSACTION_TYPE " +
        		"  )d " +
        		"ON a.TRANSACTION_TYPE=d.TRANS_CODE " +
        		"ORDER BY a.PASSBOOK_DATE";
        		if(request.getParameter("trans").equalsIgnoreCase("T")){
        			s=request.getRealPath("/org/FAS/FAS1/BRS/jaspers/BrsList.jrxml");
    			}else{
    				s=request.getRealPath("/org/FAS/FAS1/BRS/jaspers/BRSListBankTrans.jrxml");
    			}        		
        		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
        		JRDesignQuery query=new JRDesignQuery();
        		query.setText(myQry);
        		jasperDesign.setQuery(query);
        		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
        		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
        		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		        OutputStream os=response.getOutputStream();
		        response.setContentType("application/pdf");
		        response.setHeader ("Content-Disposition", "attachment;filename=\"Brs_List.pdf\"");
		        os.write(JasperManager.printReportToPdf(jasperPrint));
		        os.close();
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
	    }	
	    else if(strCommand.equalsIgnoreCase("printAllBRS")){
	    	try{
        		String s = "",myQry="",setQury="",AccType="";
        		int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
    			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
    			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
    			long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
    			String[] accNo_text=(request.getParameter("cmbBankAccNo_text")).split("-");
    			int txtCB_Year_one=Integer.parseInt(request.getParameter("txtCB_Year_one"));
    			int txtCB_Month_one=Integer.parseInt(request.getParameter("txtCB_Month_one"));
    			Map map = new HashMap();
            	Map<Integer,String> monthlist = new HashMap<Integer,String>();
            	monthlist.put(1,"January");
            	monthlist.put(2,"February");
            	monthlist.put(3,"March");
            	monthlist.put(4,"April");
            	monthlist.put(5,"May");
            	monthlist.put(6,"June");
            	monthlist.put(7,"July");
            	monthlist.put(8,"August");
            	monthlist.put(9,"September");
            	monthlist.put(10,"October");
            	monthlist.put(11,"November");
            	monthlist.put(12,"December");
            	map.put("year_from", txtCB_Year);
        		map.put("month_from", monthlist.get(txtCB_Month));
        		map.put("year_from1", txtCB_Year_one);
        		map.put("month_from1", monthlist.get(txtCB_Month_one));
        		if(accNo_text[0].equalsIgnoreCase("COL"))
        		{
        			AccType=accNo_text[2]+" For Collection A/c :"+cmbBankAccNo;
        		}
        		else
        		{
        			AccType=accNo_text[2]+" For Operation A/c :"+cmbBankAccNo;
        		}
        		map.put("AccType", AccType);
        		//map.put("bankname", accNo_text[2]);
        	
        		
    			
    			String listType = request.getParameter("typelist");
    			if(listType.equalsIgnoreCase("list1")){
    				setQury=" WHERE ACCOUNTING_UNIT_ID    ='"+cmbAcc_UnitCode+"' " +
            		" AND ACCOUNTING_FOR_OFFICE_ID         ='"+cmbOffice_code+"' " +
            		" AND CASHBOOK_YEAR                    ='"+txtCB_Year+"' " +
            		" AND CASHBOOK_MONTH                   ='"+txtCB_Month+"' " +
            		" AND ACCOUNT_NO                       ='"+cmbBankAccNo+"' " +
            		" AND Extract(MONTH FROM Passbook_Date)='"+txtCB_Month+"' " +
            		" AND Extract(YEAR FROM Passbook_Date) ='"+txtCB_Year+"' "+
            		" AND DOC_TYPE='J' ";
    			}else if(listType.equalsIgnoreCase("list2")){
    				setQury=" WHERE ACCOUNTING_UNIT_ID          ='"+cmbAcc_UnitCode+"' " +
    						" AND ACCOUNTING_FOR_OFFICE_ID         ='"+cmbOffice_code+"' " +
    						" AND Extract(MONTH FROM Passbook_Date)='"+txtCB_Month+"' " +
    						" AND Extract(YEAR FROM Passbook_Date) ='"+txtCB_Year+"' " +
    						" AND ACCOUNT_NO                       ='"+cmbBankAccNo+"' " +
    						" AND (Cashbook_Year                   !='"+txtCB_Year+
    						"' OR CASHBOOK_MONTH !='"+txtCB_Month+"' )"+
    						" AND DOC_TYPE='J' ";
    			}else if(listType.equalsIgnoreCase("list3")){
    				setQury=" WHERE ACCOUNTING_UNIT_ID    ='"+cmbAcc_UnitCode+"' " +
            		" AND ACCOUNTING_FOR_OFFICE_ID         ='"+cmbOffice_code+"' " +
            		" AND ACCOUNT_NO                       ='"+cmbBankAccNo+"' " +
            		" AND Extract(MONTH FROM Passbook_Date)='"+txtCB_Month+"' " +
            		" AND Extract(YEAR FROM Passbook_Date) ='"+txtCB_Year+"' "+
            		" AND DOC_TYPE='J' ";
    			}
    			else if(listType.equalsIgnoreCase("list4")){
    				setQury=" WHERE ACCOUNTING_UNIT_ID    ='"+cmbAcc_UnitCode+"' " +
            		" AND ACCOUNTING_FOR_OFFICE_ID         ='"+cmbOffice_code+"' " +
            		" AND CASHBOOK_YEAR                    ='"+txtCB_Year_one+"' " +
            		" AND CASHBOOK_MONTH                   ='"+txtCB_Month_one+"' " +
            		" AND ACCOUNT_NO                       ='"+cmbBankAccNo+"' " +
            		" AND DOC_TYPE='J' ";
    				System.out.println("list4"+setQury);
    			}
    			if(request.getParameter("trans").equalsIgnoreCase("T")){
    				setQury+=" AND TWAD_OR_NON_TWAD ='T' ";
    			}else{
    				setQury+=" AND TWAD_OR_NON_TWAD NOT IN ('T') ";
    			}
        		myQry="SELECT a.TWAD_OR_NON_TWAD     AS TWAD_OR_NON_TWAD, " +
        		"  a.ACCOUNTING_UNIT_ID        AS ACCOUNTING_UNIT_ID, " +
        		"  a.ACCOUNTING_FOR_OFFICE_ID  AS ACCOUNTING_FOR_OFFICE_ID, " +
        		"  DECODE(a.REMITTANCE_DATE,NULL,'-',a.REMITTANCE_DATE) AS REMITTANCE_DATE, " +
        		"  DECODE(a.WITHDRAWAL_DATE,NULL,'-',a.WITHDRAWAL_DATE) AS WITHDRAWAL_DATE, " +
        		"  a.VOUCHER_OR_CHALLAN_NO     AS VOUCHER_OR_CHALLAN_NO, " +
        		"  a.CHEQUE_DD_NO              AS CHEQUE_DD_NO, " +
        		"  a.CR_AMOUNT                 AS CR_AMOUNT, " +
        		"  a.DR_AMOUNT                 AS DR_AMOUNT, " +
        		"  a.ENTRY_FOUND_IN_PASSBOOK   AS ENTRY_FOUND_IN_PASSBOOK, " +
        		"  DECODE(a.PASSBOOK_DATE,NULL,'-',a.PASSBOOK_DATE) AS PASSBOOK_DATE, " +
        		"  a.AMOUNT_IN_PASSBOOK        AS AMOUNT_IN_PASSBOOK, " +
        		"  a.DIFFERENCE                AS DIFFERENCE, " +
        		"  a.REASON_FOR_DIFFERENCE1    AS REASON_FOR_DIFFERENCE1, " +
        		"  DECODE(a.FOLLOW_UP_ACTION_REQUIRED,NULL,'-',a.FOLLOW_UP_ACTION_REQUIRED) AS FOLLOW_UP_ACTION_REQUIRED, " +
        		"  DECODE(a.CLEARED_BASED_ON_FOLLOWUP,NULL,'-',a.CLEARED_BASED_ON_FOLLOWUP) AS CLEARED_BASED_ON_FOLLOWUP, " +
        		"  a.PARTICULARS               AS PARTICULARS, " +
        		"  a.DETAILS                   AS DETAILS, " +
        		"  a.TRANSACTION_TYPE          AS TRANSACTION_TYPE, " +
        		"  a.DOC_NO                    AS DOC_NO, " +
        		"  a.DOC_TYPE                  AS DOC_TYPE, " +
        		"  b.accounting_unit_name      AS accounting_unit_name, " +
        		"  c.OFFICE_NAME               AS OFFICE_NAME, " +
        		"  d.TRANS_SHORT_DESC          AS TRANS_SHORT_DESC " +
        		"FROM " +
        		"  (SELECT TWAD_OR_NON_TWAD, " +
        		"    ACCOUNTING_UNIT_ID, " +
        		"    ACCOUNTING_FOR_OFFICE_ID, " +
        		"    TO_CHAR(REMITTANCE_DATE,'dd/MM/yyyy')            AS REMITTANCE_DATE, " +
        		"    TO_CHAR(WITHDRAWAL_DATE,'dd/MM/yyyy')            AS WITHDRAWAL_DATE, " +
        		"    VOUCHER_OR_CHALLAN_NO, " +
        		"    CHEQUE_DD_NO, " +
        		"    CR_AMOUNT, " +
        		"    DR_AMOUNT, " +
        		"    ENTRY_FOUND_IN_PASSBOOK, " +
        		"    TO_CHAR(PASSBOOK_DATE,'dd/MM/yyyy')            AS PASSBOOK_DATE, " +
        		"    AMOUNT_IN_PASSBOOK, " +
        		"    DIFFERENCE, " +
        		"    DECODE(REASON_FOR_DIFFERENCE,NULL,'-',REASON_FOR_DIFFERENCE) AS REASON_FOR_DIFFERENCE1, " +
        		"    FOLLOW_UP_ACTION_REQUIRED, " +
        		"    CLEARED_BASED_ON_FOLLOWUP, " +
        		"    PARTICULARS, " +
        		"    DETAILS, " +
        		"    TRANSACTION_TYPE, " +
        		"    DOC_NO, " +
        		"    DOC_TYPE " +
        		"  FROM FAS_BRS_TRANSACTION " +        		
        			setQury +
        		"  )a " +
        		"LEFT OUTER JOIN " +
        		"  (SELECT ACCOUNTING_UNIT_ID, accounting_unit_name FROM fas_mst_acct_units " +
        		"  ) b " +
        		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
        		"LEFT OUTER JOIN " +
        		"  ( SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
        		"  )c " +
        		"ON a.ACCOUNTING_FOR_OFFICE_ID=c.OFFICE_ID " +
        		"LEFT OUTER JOIN " +
        		"  ( SELECT TRANS_CODE,TRANS_SHORT_DESC FROM FAS_BRS_TRANSACTION_TYPE " +
        		"  )d " +
        		"ON a.TRANSACTION_TYPE=d.TRANS_CODE " +
        		"ORDER BY a.PASSBOOK_DATE";
        		System.out.println(myQry);
        		if(request.getParameter("trans").equalsIgnoreCase("T")){
        			s=request.getRealPath("/org/FAS/FAS1/BRS/jaspers/BrsList.jrxml");
    			}else{
    				s=request.getRealPath("/org/FAS/FAS1/BRS/jaspers/BRSListBankTrans.jrxml");
    			}        		
        		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
        		JRDesignQuery query=new JRDesignQuery();
        		query.setText(myQry);
        		jasperDesign.setQuery(query);
        		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
        		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
        		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		        OutputStream os=response.getOutputStream();
		        response.setContentType("application/pdf");
		        response.setHeader ("Content-Disposition", "attachment;filename=\"Brs_List.pdf\"");
		        os.write(JasperManager.printReportToPdf(jasperPrint));
		        os.close();
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
	    }		
		
	}

}
