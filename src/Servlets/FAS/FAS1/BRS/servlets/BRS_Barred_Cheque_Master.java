package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class BRS_Barred_Cheque_Master
 */
public class BRS_Barred_Cheque_Master extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BRS_Barred_Cheque_Master() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs3 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps3 = null;

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

		String strCommand = "";
		String xml = "";
                    int count_brs=0,count_list=0;
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
                int c_tes=0;
		if (strCommand.equalsIgnoreCase("LoadDocNo")) {
			xml = xml + "<response><command>LoadDocNo</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			String cmbDoc_Type = request.getParameter("cmbDoc_Type");
			int cmbCheque_No = Integer.parseInt(request
					.getParameter("cmbCheque_No"));
			try {
				if (cmbDoc_Type.equals("Receipt")) {
					ps = connection.prepareStatement("select RECEIPT_NO from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHEQUE_DD_NO=? group by RECEIPT_NO");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setInt(5, cmbCheque_No);
					rs = ps.executeQuery();
					xml = xml + "<flag>success</flag>";
					while (rs.next()) 
                                        {

						xml = xml + "<DocNo>" + rs.getInt("RECEIPT_NO")
								+ "</DocNo>";
                                                                count_brs++;
					    ps3 = connection.prepareStatement("select DOC_NO from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHEQUE_NO=? group by DOC_NO");
					    ps3.setInt(1, cmbAcc_UnitCode);
					    ps3.setInt(2, cmbOffice_code);
					    ps3.setInt(3, txtCB_Year);
					    ps3.setInt(4, txtCB_Month);
					    ps3.setInt(5, cmbCheque_No);
					    rs3 = ps3.executeQuery();
					    while(rs3.next())
					    {
                                            c_tes++;
					    xml = xml + "<flag_l>noLoading</flag_l>";
					    }
                                            if(c_tes==0) {
                                                xml = xml + "<flag_l>loading</flag_l>";
                                            }
					}
                                        if(count_brs==0) {
                                            xml = xml + "<flag_l>notexist</flag_l>";
                                            
                                        }
                                       
				} else if (cmbDoc_Type.equals("Payment")) {
					ps = connection
							.prepareStatement("select VOUCHER_NO from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and CHEQUE_DD_NO=? order by VOUCHER_NO");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setLong(5, cmbBankAccNo);
					ps.setInt(6, cmbCheque_No);
					rs = ps.executeQuery();
					xml = xml + "<flag>success</flag>";
					while (rs.next()) {
						xml = xml + "<DocNo>" + rs.getInt("VOUCHER_NO")
								+ "</DocNo>";
					    count_brs++;
					    ps3 = connection.prepareStatement("select DOC_NO from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHEQUE_NO=? group by DOC_NO");
					    ps3.setInt(1, cmbAcc_UnitCode);
					    ps3.setInt(2, cmbOffice_code);
					    ps3.setInt(3, txtCB_Year);
					    ps3.setInt(4, txtCB_Month);
					    ps3.setInt(5, cmbCheque_No);
					    rs3 = ps3.executeQuery();
					    while(rs3.next())
					    {
					    c_tes++;
					    xml = xml + "<flag_l>noLoading</flag_l>";
					    }
					    if(c_tes==0) {
					    xml = xml + "<flag_l>loading</flag_l>";
					    }
					}
				    if(count_brs==0) {
				        xml = xml + "<flag_l>notexist</flag_l>";
				        
				    }
				}

			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} 
                else if (strCommand.equalsIgnoreCase("LoadDocNo_2006")) {
			xml = xml + "<response><command>LoadDocNo_2006</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			String cmbDoc_Type = request.getParameter("cmbDoc_Type");
			int cmbCheque_No = Integer.parseInt(request
					.getParameter("cmbCheque_No"));
			try {
				if (cmbDoc_Type.equals("Receipt")) {
					ps = connection
							.prepareStatement("select RECEIPT_NO from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and CHEQUE_DD_NO=? group by RECEIPT_NO");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setInt(5, cmbCheque_No);
					rs = ps.executeQuery();
					xml = xml + "<flag>success</flag>";
					while (rs.next()) {

						xml = xml + "<DocNo>" + rs.getInt("RECEIPT_NO")
								+ "</DocNo>";
                                                                count_brs++;
                                                                System.out.println("dataaaaaaaaaaaaaa");
					}
                                        if(count_brs>0) {
                                            xml = xml + "<flag_l>noLoading</flag_l>";
                                        }
                                        else {
                                            xml = xml + "<flag_l>loading</flag_l>";
                                        }
				} else if (cmbDoc_Type.equals("Payment")) {
					ps = connection
							.prepareStatement("select VOUCHER_NO from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and CHEQUE_DD_NO=? order by VOUCHER_NO");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setLong(5, cmbBankAccNo);
					ps.setInt(6, cmbCheque_No);
					rs = ps.executeQuery();
					xml = xml + "<flag>success</flag>";
					while (rs.next()) {
						xml = xml + "<DocNo>" + rs.getInt("VOUCHER_NO")
								+ "</DocNo>";
					    count_brs++;
					}
				    if(count_brs>0) {
				        xml = xml + "<flag_l>noLoading</flag_l>";
				    }
				    else {
				        xml = xml + "<flag_l>loading</flag_l>";
				    }
				}

			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
                
                else if (strCommand.equalsIgnoreCase("LoadChequeNoDetails")) {
			xml = xml + "<response><command>LoadChequeNoDetails</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			String cmbDoc_Type = request.getParameter("cmbDoc_Type");
			int txtDoc_No = Integer.parseInt(request.getParameter("txtDoc_No"));
			try {
				if (cmbDoc_Type.equals("Receipt")) {
					ps = connection
							.prepareStatement("SELECT RECEIPT_DATE,  TOTAL_AMOUNT,  CHEQUE_DD_DATE FROM   "
									+ "( SELECT RECEIPT_NO,     RECEIPT_DATE,    ACCOUNTING_UNIT_ID, "
									+ "ACCOUNTING_FOR_OFFICE_ID,    CASHBOOK_YEAR,    CASHBOOK_MONTH,    "
									+ "TOTAL_AMOUNT  FROM FAS_RECEIPT_MASTER  WHERE ACCOUNTING_UNIT_ID    =?  "
									+ "AND ACCOUNTING_FOR_OFFICE_ID=?  AND CASHBOOK_YEAR           =?  "
									+ "AND CASHBOOK_MONTH          =?  AND ACCOUNT_NO              =?  AND "
									+ "RECEIPT_NO              =?  )X LEFT OUTER JOIN  (SELECT RECEIPT_NO AS rno,"
									+ "    CHEQUE_DD_DATE ,    ACCOUNTING_UNIT_ID       AS accuid,    "
									+ "ACCOUNTING_FOR_OFFICE_ID AS accoffid,    CASHBOOK_YEAR            AS cby,"
									+ "    CASHBOOK_MONTH           AS cbm  FROM FAS_RECEIPT_TRANSACTION  "
									+ "WHERE ACCOUNTING_UNIT_ID    =?  AND ACCOUNTING_FOR_OFFICE_ID=?  "
									+ "AND CASHBOOK_YEAR           =?  AND CASHBOOK_MONTH          =?  "
									+ "AND RECEIPT_NO              =?  GROUP BY RECEIPT_NO,    CHEQUE_DD_DATE , "
									+ "   ACCOUNTING_UNIT_ID,    ACCOUNTING_FOR_OFFICE_ID,    CASHBOOK_YEAR,  "
									+ "  CASHBOOK_MONTH  )Y ON X.RECEIPT_NO                = Y.rno "
									+ "AND X.ACCOUNTING_UNIT_ID       = Y.accuid "
									+ "AND X.ACCOUNTING_FOR_OFFICE_ID = Y.accoffid AND "
									+ "X.CASHBOOK_YEAR            = Y.cby "
									+ "AND X.CASHBOOK_MONTH           = Y.cbm ");

					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setLong(5, cmbBankAccNo);
					ps.setInt(6, txtDoc_No);
					ps.setInt(7, cmbAcc_UnitCode);
					ps.setInt(8, cmbOffice_code);
					ps.setInt(9, txtCB_Year);
					ps.setInt(10, txtCB_Month);
					ps.setInt(11, txtDoc_No);
					rs = ps.executeQuery();
				} else if (cmbDoc_Type.equals("Payment")) {
					ps = connection
							.prepareStatement("SELECT PAYMENT_DATE,  TOTAL_AMOUNT,  CHEQUE_DD_DATE FROM  "
									+ "( SELECT VOUCHER_NO,    PAYMENT_DATE,    ACCOUNTING_UNIT_ID,    "
									+ "ACCOUNTING_FOR_OFFICE_ID,    CASHBOOK_YEAR,    CASHBOOK_MONTH,    "
									+ "TOTAL_AMOUNT,ACCOUNT_NO  FROM FAS_PAYMENT_MASTER  WHERE ACCOUNTING_UNIT_ID "
									+ "   =?  AND ACCOUNTING_FOR_OFFICE_ID=?  AND CASHBOOK_YEAR           =? "
									+ " AND CASHBOOK_MONTH          =?  AND ACCOUNT_NO              =?  AND VOUCHER_NO              =?  )X"
									+ " LEFT OUTER JOIN   ( SELECT VOUCHER_NO AS vno,    CHEQUE_DD_DATE ,     ACCOUNT_NO as accno,    "
									+ "ACCOUNTING_UNIT_ID       AS accuid,    ACCOUNTING_FOR_OFFICE_ID AS accoffid,    CASHBOOK_YEAR  "
									+ "          AS cby,    CASHBOOK_MONTH           AS cbm  FROM FAS_PAYMENT_TRANSACTION  "
									+ "WHERE ACCOUNTING_UNIT_ID    =?  AND ACCOUNTING_FOR_OFFICE_ID=?  AND CASHBOOK_YEAR           =?"
									+ "  AND CASHBOOK_MONTH          =?   AND ACCOUNT_NO              =?  AND VOUCHER_NO              =?"
									+ "  GROUP BY VOUCHER_NO,    CHEQUE_DD_DATE ,    ACCOUNTING_UNIT_ID,    ACCOUNTING_FOR_OFFICE_ID,  "
									+ "  CASHBOOK_YEAR,    CASHBOOK_MONTH,ACCOUNT_NO  )Y ON X.VOUCHER_NO                = Y.vno "
									+ "AND X.ACCOUNTING_UNIT_ID       = Y.accuid AND X.ACCOUNTING_FOR_OFFICE_ID = Y.accoffid AND "
									+ "X.CASHBOOK_YEAR            = Y.cby AND X.CASHBOOK_MONTH           = Y.cbm  "
									+ "AND X.ACCOUNT_NO           = Y.accno ");

					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setLong(5, cmbBankAccNo);
					ps.setInt(6, txtDoc_No);
					ps.setInt(7, cmbAcc_UnitCode);
					ps.setInt(8, cmbOffice_code);
					ps.setInt(9, txtCB_Year);
					ps.setInt(10, txtCB_Month);
					ps.setLong(11, cmbBankAccNo);
					ps.setInt(12, txtDoc_No);
					rs = ps.executeQuery();
				}

				xml = xml + "<flag>success</flag>";
				String ChequeDate = null;
				String DocDate = null;
				int count = 0;
				while (rs.next()) {
					if (cmbDoc_Type.equals("Receipt")) {
						Date ChequeDate1 = rs.getDate("RECEIPT_DATE");

						String Stringdate = ChequeDate1.toString();

						String[] ddd = Stringdate.split("-");

						int day = Integer.parseInt(ddd[2]);
						int month = Integer.parseInt(ddd[1]);
						int year = Integer.parseInt(ddd[0]);

						if (month >= 10) {
							ChequeDate = (day + "/" + month + "/" + year);
						} else {
							ChequeDate = (day + "/0" + month + "/" + year);
						}

						Date DocDate1 = rs.getDate("CHEQUE_DD_DATE");

						String Stringdate1 = DocDate1.toString();

						String[] ddd1 = Stringdate1.split("-");

						int day1 = Integer.parseInt(ddd1[2]);
						int month1 = Integer.parseInt(ddd1[1]);
						int year1 = Integer.parseInt(ddd1[0]);

						if (month1 >= 10) {
							DocDate = (day1 + "/" + month1 + "/" + year1);
						} else {
							DocDate = (day1 + "/0" + month1 + "/" + year1);
						}

						xml = xml + "<ChequeDate>" + ChequeDate
								+ "</ChequeDate>";
						xml = xml + "<DocDate>" + DocDate + "</DocDate>";
						xml = xml + "<ChequeAmount>"
								+ rs.getInt("TOTAL_AMOUNT") + "</ChequeAmount>";
					} else if (cmbDoc_Type.equals("Payment")) {
						Date ChequeDate1 = rs.getDate("PAYMENT_DATE");

						String Stringdate = ChequeDate1.toString();

						String[] ddd = Stringdate.split("-");

						int day = Integer.parseInt(ddd[2]);
						int month = Integer.parseInt(ddd[1]);
						int year = Integer.parseInt(ddd[0]);

						if (month >= 10) {
							ChequeDate = (day + "/" + month + "/" + year);
						} else {
							ChequeDate = (day + "/0" + month + "/" + year);
						}

						Date DocDate1 = rs.getDate("CHEQUE_DD_DATE");

						String Stringdate1 = DocDate1.toString();

						String[] ddd1 = Stringdate1.split("-");

						int day1 = Integer.parseInt(ddd1[2]);
						int month1 = Integer.parseInt(ddd1[1]);
						int year1 = Integer.parseInt(ddd1[0]);

						if (month1 >= 10) {
							DocDate = (day1 + "/" + month1 + "/" + year1);
						} else {
							DocDate = (day1 + "/0" + month1 + "/" + year1);
						}

						xml = xml + "<ChequeDate>" + ChequeDate
								+ "</ChequeDate>";
						xml = xml + "<DocDate>" + DocDate + "</DocDate>";
						xml = xml + "<ChequeAmount>"
								+ rs.getInt("TOTAL_AMOUNT") + "</ChequeAmount>";
					}
					count = count + 1;
				}
				xml = xml + "<count>" + count + "</count>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("Add")) {
			xml = xml + "<response><command>Add</command>";
			java.sql.Date date = null;
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			String cmbDoc_Type = request.getParameter("cmbDoc_Type");
			int cmbCheque_No = Integer.parseInt(request
					.getParameter("cmbCheque_No"));
			int txtDoc_No = Integer.parseInt(request.getParameter("txtDoc_No"));
			java.sql.Date txtDoc_Date = null;
			java.sql.Date txtCheque_Date = null;
			Float txtCheque_Amount = Float.parseFloat(request
					.getParameter("txtCheque_Amount"));

			java.sql.Date txtCheque_Valid_Upto = null;

			java.util.GregorianCalendar c;

			String[] sd = request.getParameter("txtDoc_Date").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtDoc_Date = new Date(d.getTime());

			String[] sd1 = request.getParameter("txtCheque_Date").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd1[2]),
					Integer.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
			java.util.Date d1 = c.getTime();
			txtCheque_Date = new Date(d1.getTime());

			String[] sd2 = request.getParameter("txtCheque_Valid_Upto").split(
					"/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd2[2]),
					Integer.parseInt(sd2[1]) - 1, Integer.parseInt(sd2[0]));
			java.util.Date d2 = c.getTime();
			txtCheque_Valid_Upto = new Date(d2.getTime());

			String mtxtFollowup_Action1 = request
					.getParameter("mtxtFollowup_Action");
			String mtxtFollowup_Action = null;
			if (mtxtFollowup_Action1 == null || mtxtFollowup_Action1.equals("")) {
				mtxtFollowup_Action = "";
			} else {
				mtxtFollowup_Action = mtxtFollowup_Action1;
			}

			String txtCleared_Entries1 = request
					.getParameter("txtCleared_Entries");
			String txtCleared_Entries = null;
			if (txtCleared_Entries1 == null || txtCleared_Entries1.equals("")) {
				txtCleared_Entries = "";
			} else {
				txtCleared_Entries = txtCleared_Entries1;
			}

			java.sql.Date txtCleared_Date = null;
			String txtCleared_Date1 = null;
			try {
				txtCleared_Date1 = request.getParameter("txtCleared_Date");
				if (txtCleared_Date1 != null) {
					if (txtCleared_Date1.equals("")) {
						txtCleared_Date=date.valueOf("0000-00-00");
					} else {
						String[] sd3 = txtCleared_Date1.split("/");
						c = new java.util.GregorianCalendar(Integer
								.parseInt(sd3[2]),
								Integer.parseInt(sd3[1]) - 1, Integer
										.parseInt(sd3[0]));
						java.util.Date d3 = c.getTime();
						txtCleared_Date = new Date(d3.getTime());
					}
				} else {
					txtCleared_Date=date.valueOf("0000-00-00");
				}
			} catch (Exception e) {
				e.printStackTrace();				
			}

			try {

				ps1 = connection
						.prepareStatement("select CHEQUE_NO from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and CHEQUE_NO=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, txtCB_Year);
				ps1.setInt(4, txtCB_Month);
				ps1.setLong(5, cmbBankAccNo);
				ps1.setInt(6, cmbCheque_No);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_BRS_BARRED_CHEQUE_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_NO,DOC_TYPE,CHEQUE_NO,DOC_NO,DOC_DATE,CHEQUE_DATE,CHEQUE_AMOUNT,CHEQUE_VALID_UPTO,UPDATED_BY_USERID,UPDATED_DATE,FOLLOWUP_ACTION,CLEARED_ENTRIES,CLEARED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setLong(5, cmbBankAccNo);
					ps.setString(6, cmbDoc_Type);
					ps.setInt(7, cmbCheque_No);
					ps.setInt(8, txtDoc_No);
					ps.setDate(9, txtDoc_Date);
					ps.setDate(10, txtCheque_Date);
					ps.setFloat(11, txtCheque_Amount);
					ps.setDate(12, txtCheque_Valid_Upto);
					ps.setString(13, userid);
					ps.setTimestamp(14, ts);
					ps.setString(15, mtxtFollowup_Action);
					ps.setString(16, txtCleared_Entries);
					ps.setDate(17, txtCleared_Date);
					int k = ps.executeUpdate();
					if (k > 0) {
						xml = xml + "<flag>success</flag>";
					} else {
						xml = xml + "<flag>failure</flag>";
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("List")) {
			xml = xml + "<response><command>List</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			try {

				ps = connection
						.prepareStatement("select * from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");

				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				rs = ps.executeQuery();
				
				String ChequeDate = null;
				String DocDate = null;
				String CheckValidUpto = null;
				String ClearedDate = null;
				while (rs.next()) {
                                System.out.println("while");
                                count_list++;
				    xml = xml + "<flag>success</flag>";
					Date ChequeDate1 = rs.getDate("CHEQUE_DATE");
					Date DocDate1 = rs.getDate("DOC_DATE");
					Date CheckValidUpto1 = rs.getDate("CHEQUE_VALID_UPTO");
					Date ClearedDate1 = rs.getDate("CLEARED_DATE");
                                        System.out.println("ClearedDate1::::"+ClearedDate1);
                                       String ce=rs.getString("CLEARED_ENTRIES");
                                       
					String Stringdate = ChequeDate1.toString();
					String Stringdate1 = DocDate1.toString();
					String Stringdate2 = CheckValidUpto1.toString();
					

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
				    if(ce.equalsIgnoreCase("Y")) {
				        String Stringdate3 = ClearedDate1.toString();
				        String[] ddd3 = Stringdate3.split("-");
				        int day3 = Integer.parseInt(ddd3[2]);
				        int month3 = Integer.parseInt(ddd3[1]);
				        int year3 = Integer.parseInt(ddd3[0]);
				        if (month3 >= 10) {
				                ClearedDate = (day3 + "/" + month3 + "/" + year3);
				        } else {
				                ClearedDate = (day3 + "/0" + month3 + "/" + year3);
				        }
				    }
					

					if (month >= 10) {
						ChequeDate = (day + "/" + month + "/" + year);
					} else {
						ChequeDate = (day + "/0" + month + "/" + year);
					}

					if (month1 >= 10) {
						DocDate = (day1 + "/" + month1 + "/" + year1);
					} else {
						DocDate = (day1 + "/0" + month1 + "/" + year1);
					}

					if (month2 >= 10) {
						CheckValidUpto = (day2 + "/" + month2 + "/" + year2);
					} else {
						CheckValidUpto = (day2 + "/0" + month2 + "/" + year2);
					}

					
					xml = xml + "<Acc_Unit_id>"
							+ rs.getInt("ACCOUNTING_UNIT_ID")
							+ "</Acc_Unit_id>";
					xml = xml + "<Acc_Office_id>"
							+ rs.getInt("ACCOUNTING_FOR_OFFICE_ID")
							+ "</Acc_Office_id>";
					xml = xml + "<Cb_year>" + rs.getInt("CASHBOOK_YEAR")
							+ "</Cb_year>";
					xml = xml + "<Cb_Month>" + rs.getInt("CASHBOOK_MONTH")
							+ "</Cb_Month>";
                                        System.out.println("xml:"+xml);
					xml = xml + "<Acc_no>" + rs.getInt("ACCOUNT_NO")
							+ "</Acc_no>";
					xml = xml + "<Doc_Type>" + rs.getString("DOC_TYPE")
							+ "</Doc_Type>";
				    System.out.println("xml:::::"+xml);
					xml = xml + "<Cheque_No>" + rs.getInt("CHEQUE_NO")
							+ "</Cheque_No>";
					xml = xml + "<Doc_No>" + rs.getInt("DOC_NO") + "</Doc_No>";
					xml = xml + "<ChequeDate>" + ChequeDate + "</ChequeDate>";
					xml = xml + "<DocDate>" + DocDate + "</DocDate>";
					xml = xml + "<ChequeAmount>" + rs.getFloat("CHEQUE_AMOUNT")
							+ "</ChequeAmount>";
					xml = xml + "<CheckValidUpto>" + CheckValidUpto
							+ "</CheckValidUpto>";
					xml = xml + "<Follow_Up>" + rs.getString("FOLLOWUP_ACTION")
							+ "</Follow_Up>";
					xml = xml + "<Cleared_Entries>"
							+ rs.getString("CLEARED_ENTRIES")
							+ "</Cleared_Entries>";
					xml = xml + "<ClearedDate>" + ClearedDate
							+ "</ClearedDate>";
				}
                                if(count_list>0) {
                                    xml = xml + "<flagdat>Data</flagdat>";
                                }
                                else {
                                    xml = xml + "<flagdat>noData</flagdat>";
                                }
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("Update")) {
			xml = xml + "<response><command>Update</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			String cmbDoc_Type = request.getParameter("cmbDoc_Type");
			int cmbCheque_No = Integer.parseInt(request
					.getParameter("cmbCheque_No"));
			int txtDoc_No = Integer.parseInt(request.getParameter("txtDoc_No"));
			java.sql.Date txtDoc_Date = null;
			java.sql.Date txtCheque_Date = null;
			Float txtCheque_Amount = Float.parseFloat(request
					.getParameter("txtCheque_Amount"));
			java.sql.Date txtCheque_Valid_Upto = null;

			java.util.GregorianCalendar c;
			String[] sd = request.getParameter("txtDoc_Date").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtDoc_Date = new Date(d.getTime());

			String[] sd1 = request.getParameter("txtCheque_Date").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd1[2]),
					Integer.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
			java.util.Date d1 = c.getTime();
			txtCheque_Date = new Date(d1.getTime());

			String[] sd2 = request.getParameter("txtCheque_Valid_Upto").split(
					"/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd2[2]),
					Integer.parseInt(sd2[1]) - 1, Integer.parseInt(sd2[0]));
			java.util.Date d2 = c.getTime();
			txtCheque_Valid_Upto = new Date(d2.getTime());

			String mtxtFollowup_Action1 = request
					.getParameter("mtxtFollowup_Action");
			String mtxtFollowup_Action = null;
			if (mtxtFollowup_Action1 == null || mtxtFollowup_Action1.equals("")) {
				mtxtFollowup_Action = "";
			} else {
				mtxtFollowup_Action = mtxtFollowup_Action1;
			}

			String txtCleared_Entries1 = request
					.getParameter("txtCleared_Entries");
			String txtCleared_Entries = null;
			if (txtCleared_Entries1 == null || txtCleared_Entries1.equals("")) {
				txtCleared_Entries = "";
			} else {
				txtCleared_Entries = txtCleared_Entries1;
			}

			java.sql.Date txtCleared_Date = null;
			String txtCleared_Date1 = null;
			try {
				txtCleared_Date1 = request.getParameter("txtCleared_Date");
				if (txtCleared_Date1 != null) {
					if (txtCleared_Date1.equals("")) {
						txtCleared_Date1 = "00/00/0000";
						String[] sd3 = txtCleared_Date1.split("/");
						c = new java.util.GregorianCalendar(Integer
								.parseInt(sd3[2]),
								Integer.parseInt(sd3[1]) - 1, Integer
										.parseInt(sd3[0]));
						java.util.Date d3 = c.getTime();
						txtCleared_Date = new Date(d3.getTime());
					} else {
						String[] sd3 = txtCleared_Date1.split("/");
						c = new java.util.GregorianCalendar(Integer
								.parseInt(sd3[2]),
								Integer.parseInt(sd3[1]) - 1, Integer
										.parseInt(sd3[0]));
						java.util.Date d3 = c.getTime();
						txtCleared_Date = new Date(d3.getTime());
					}
				} else {
					txtCleared_Date1 = "00/00/0000";
					String[] sd3 = txtCleared_Date1.split("/");
					c = new java.util.GregorianCalendar(Integer
							.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,
							Integer.parseInt(sd3[0]));
					java.util.Date d3 = c.getTime();
					txtCleared_Date = new Date(d3.getTime());
				}
			} catch (Exception e) {
				e.printStackTrace();
				txtCleared_Date1 = "00/00/0000";
				String[] sd3 = txtCleared_Date1.split("/");
				c = new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),
						Integer.parseInt(sd3[1]) - 1, Integer.parseInt(sd3[0]));
				java.util.Date d3 = c.getTime();
				txtCleared_Date = new Date(d3.getTime());
			}
			try {

				ps1 = connection
						.prepareStatement("select CHEQUE_NO from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and CHEQUE_NO=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, txtCB_Year);
				ps1.setInt(4, txtCB_Month);
				ps1.setLong(5, cmbBankAccNo);
				ps1.setInt(6, cmbCheque_No);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("update FAS_BRS_BARRED_CHEQUE_MASTER set DOC_NO=?,DOC_DATE=?,CHEQUE_DATE=?,CHEQUE_AMOUNT=?,CHEQUE_VALID_UPTO=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,FOLLOWUP_ACTION=?,CLEARED_ENTRIES=?,CLEARED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and DOC_TYPE=? and CHEQUE_NO=?");
					ps.setInt(1, txtDoc_No);
					ps.setDate(2, txtDoc_Date);
					ps.setDate(3, txtCheque_Date);
					ps.setFloat(4, txtCheque_Amount);
					ps.setDate(5, txtCheque_Valid_Upto);
					ps.setString(6, userid);
					ps.setTimestamp(7, ts);
					ps.setString(8, mtxtFollowup_Action);
					ps.setString(9, txtCleared_Entries);
					ps.setDate(10, txtCleared_Date);
					ps.setInt(11, cmbAcc_UnitCode);
					ps.setInt(12, cmbOffice_code);
					ps.setInt(13, txtCB_Year);
					ps.setInt(14, txtCB_Month);
					ps.setLong(15, cmbBankAccNo);
					ps.setString(16, cmbDoc_Type);
					ps.setInt(17, cmbCheque_No);

					int k = ps.executeUpdate();
					if (k > 0) {
						xml = xml + "<flag>success</flag>";
					} else {
						xml = xml + "<flag>failure</flag>";
					}

				} else {

					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("Delete")) {
			xml = xml + "<response><command>Delete</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			String cmbDoc_Type = request.getParameter("cmbDoc_Type");
			int cmbCheque_No = Integer.parseInt(request
					.getParameter("cmbCheque_No"));
			try {

				ps1 = connection
						.prepareStatement("select CHEQUE_NO from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and CHEQUE_NO=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, txtCB_Year);
				ps1.setInt(4, txtCB_Month);
				ps1.setLong(5, cmbBankAccNo);
				ps1.setInt(6, cmbCheque_No);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and CHEQUE_NO=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setLong(5, cmbBankAccNo);
					ps.setInt(6, cmbCheque_No);
					int k = ps.executeUpdate();
					if (k > 0) {
						xml = xml + "<flag>success</flag>";
					} else {
						xml = xml + "<flag>failure</flag>";
					}

				} else {

					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}else if (strCommand.equalsIgnoreCase("ParentDrawing")) {
			xml = xml + "<response><command>ParentDrawing</command>";
			int v1 = Integer.parseInt(request
					.getParameter("v1"));
			int v2 = Integer.parseInt(request
					.getParameter("v2"));
			int v3 = Integer.parseInt(request
					.getParameter("v3"));
			int v4 = Integer.parseInt(request
					.getParameter("v4"));			
			String v5 = request.getParameter("v5");
			String v6 = request.getParameter("v6");
			String v7 = request.getParameter("v7");
			String v8 = request.getParameter("v8");
			String v9 = request.getParameter("v9");
			String v10 = request.getParameter("v10");
			String v11 = request.getParameter("v11");
			String v12 = request.getParameter("v12");
			
			String v13 = request.getParameter("v13");
			if(v13.equals(""))
			{
				v13=null;
			}
			String v14 = request.getParameter("v14");
			if(v14.equals(""))
			{
				v14=null;
			}
			String v15 = request.getParameter("v15");
			if(v15.equals(""))
			{
				v15=null;
			}
			xml = xml + "<v1>" + v1 + "</v1>";
			xml = xml + "<v2>" + v2 + "</v2>";
			xml = xml + "<v3>" + v3 + "</v3>";
			xml = xml + "<v4>" + v4 + "</v4>";
			xml = xml + "<v5>" + v5 + "</v5>";
			xml = xml + "<v6>" + v6 + "</v6>";
			xml = xml + "<v7>" + v7 + "</v7>";
			xml = xml + "<v8>" + v8 + "</v8>";
			xml = xml + "<v9>" + v9 + "</v9>";
			xml = xml + "<v10>" + v10 + "</v10>";
			xml = xml + "<v11>" + v11 + "</v11>";
			xml = xml + "<v12>" + v12 + "</v12>";
			xml = xml + "<v13>" + v13 + "</v13>";
			xml = xml + "<v14>" + v14 + "</v14>";
			xml = xml + "<v15>" + v15 + "</v15>";						
			
			try {
				ps = connection
				.prepareStatement("select DOC_NO from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
		ps.setInt(1, v1);
		ps.setInt(2, v2);
		ps.setInt(3, v3);
		ps.setInt(4, v4);		
		rs = ps.executeQuery();
		xml = xml + "<flag>success</flag>";
		while (rs.next()) {

			xml = xml + "<DocNo>" + rs.getInt("DOC_NO")
					+ "</DocNo>";
		}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}

		xml = xml + "</response>";
		out.write(xml);
		System.out.println("xml::::"+xml);
	}

}
