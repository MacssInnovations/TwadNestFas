package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.FAS.FAS1.CommonClass.ConvertDate;
import Servlets.Security.classes.UserProfile;

public class Asset_transfer_A52_to_AA52 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml charset=windows-1521";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String xml = "";
		String user_id = (String) session.getAttribute("UserId");
		UserProfile empProfile = (UserProfile) session
				.getAttribute("UserProfile");
		int employeeId = empProfile.getEmployeeId();
		System.out.println("employeeId :: " + employeeId);
		String cmd = request.getParameter("command");
		int unit_id = 0;
		int office_id = 0;
		int assetmajor = 0;
		String financial_year = null;

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		/*
		 * try { unit_id = Integer.parseInt(request.getParameter("unit_id"));
		 * //System.out.println("accounting_unit_id : " + unit_id); }
		 * catch(Exception e) { System.out.println(
		 * "IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " +
		 * e); }
		 * 
		 * try { office_id =
		 * Integer.parseInt(request.getParameter("office_id"));
		 * //System.out.println("accounting_unit_office_id : " + office_id); }
		 * catch(Exception e) { System.out.println(
		 * "IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> "
		 * + e); } try { financial_year =
		 * request.getParameter("financial_year");
		 * //System.out.println("financial_year : " + financial_year); }
		 * catch(Exception e) { System.out.println(
		 * "IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
		 * }
		 * 
		 * 
		 * try { assetmajor =
		 * Integer.parseInt(request.getParameter("assetmajor"));
		 * 
		 * } catch(Exception e) { System.out.println(
		 * "IGNORABLE Exception getting 'assetmajor' parameter ===> " + e); }
		 */
		Connection con = null;
		Statement statement = null;
		PreparedStatement ps = null;
		ResultSet rs1 = null;
		try {
			ResourceBundle rs = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			try {
				statement = con.createStatement();
				con.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		response.setContentType(CONTENT_TYPE);

		if (cmd.equals("checkStatus")) {
			int count = 0;
			System.out
					.println("\n*************\ncheckStatus \n**************\n");
			xml = "<response><command>checkStatus</command>";
			try {
				ResultSet result = statement
						.executeQuery("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,A52_STATUS from FAS_A52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID="
								+ office_id
								+ "  and FINANCIAL_YEAR='"
								+ financial_year + "'");
				// ACCOUNTING_UNIT_ID="+unit_id+" and

				try {
					xml = xml + "<flag>success</flag>";
					String valExists = "No";
					while (result.next()) {
						valExists = "Yes";
						xml += "<ACCOUNTING_UNIT_ID>"
								+ result.getInt("ACCOUNTING_UNIT_ID")
								+ "</ACCOUNTING_UNIT_ID>";
						xml += "<A52_STATUS>" + result.getString("A52_STATUS")
								+ "</A52_STATUS>";
					}

					xml += "<exists>" + valExists + "</exists>";
				} catch (Exception e) {
					System.out
							.println("Exception in getting values from  a52 freeze verification : "
									+ e);
				}

				result.close();
				// response.setHeader("cache-control","no-cache");

			} catch (Exception e1) {
				System.out.println("Exception is in Get" + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";

		}
		if (cmd.equals("loadMajor")) {
			System.out.println("\n*************\nloadMajor\n**************\n");
			xml = "<response><command>loadMajor</command>";
			try {
				ResultSet result = statement
						.executeQuery("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE");
				try {
					xml = xml + "<flag>success</flag>";
					String valExists = "No";
					while (result.next()) {
						valExists = "Yes";
						xml += "<ASSET_MAJOR_CLASS_CODE>"
								+ result.getInt("ASSET_MAJOR_CLASS_CODE")
								+ "</ASSET_MAJOR_CLASS_CODE>";
						xml += "<ASSET_MAJOR_CLASS_DESC><![CDATA["
								+ result.getString("ASSET_MAJOR_CLASS_DESC")
								+ "]]></ASSET_MAJOR_CLASS_DESC>";
					}

					xml += "<exists>" + valExists + "</exists>";
				} catch (Exception e) {
					System.out
							.println("Exception in getting values from ASSET_MAJOR_CLASS_CODE: "
									+ e);
				}

				result.close();
				// response.setHeader("cache-control","no-cache");

			} catch (Exception e1) {
				System.out.println("Exception is in Get" + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}

		// Joan Change on 20Jan 2015 for 2012-13 record Transfer from A52 to
		// AA52
		// submit
		/*
		 * if(cmd.equalsIgnoreCase("subm")) {
		 * System.out.println("***inside submit***");
		 * xml=xml+"<response><command>subm</command>"; String
		 * asscode=request.getParameter("asset_code");
		 * System.out.println("===asset code==="+asscode); String[]
		 * assetCodeF=asscode.split(","); String[] asset=null; String
		 * asset_code=""; String acc_unitid=""; String acc_offid=""; String
		 * fin_yr=""; String app_grant=""; String clos_bal=""; String
		 * dep_cost=""; String asset_maj_code="";
		 * 
		 * int acc_uid=0; int acc_ofid=0; int ass_code=0; int bal=0; int
		 * grant=0; int depre=0; int ass_maj_code=0;
		 * 
		 * System.out.println("assetCodeF.length "+assetCodeF.length); for(int
		 * i=0; i<assetCodeF.length; i++){
		 * System.out.println("assetCodeF[i] "+assetCodeF[i]); asset =
		 * assetCodeF[i].split("/");
		 * System.out.println("asset len "+asset.length);
		 * System.out.println("asset "+asset); for(int j=0; j<asset.length;
		 * j++){ asset_code=asset[0]; acc_unitid=asset[1]; acc_offid=asset[2];
		 * fin_yr=asset[3]; app_grant=asset[4]; clos_bal=asset[5];
		 * dep_cost=asset[6]; asset_maj_code = asset[7]; }
		 * 
		 * 
		 * 
		 * acc_uid=Integer.parseInt(acc_unitid);
		 * acc_ofid=Integer.parseInt(acc_offid);
		 * ass_code=Integer.parseInt(asset_code);
		 * bal=Integer.parseInt(clos_bal); grant=Integer.parseInt(app_grant);
		 * depre=Integer.parseInt(dep_cost);
		 * ass_maj_code=Integer.parseInt(asset_maj_code);
		 * 
		 * System.out.println("grant:::"+grant);
		 * 
		 * 
		 * try { ps=con.prepareStatement(
		 * "update FAS_A52_REGISTER set MOVED_TO_AA52='Y' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and FINANCIAL_YEAR=?"
		 * );
		 * 
		 * ps.setInt(1, acc_uid ); ps.setInt(2, acc_ofid ); ps.setString(3,
		 * fin_yr );
		 * 
		 * ps.executeUpdate();
		 * 
		 * xml = xml + "<flag>success</flag>"; xml = xml + "< acc_unitid>"+
		 * acc_uid+"</acc_unitid>"; xml = xml + "< acc_offid>"+
		 * acc_ofid+"</acc_offid>"; xml = xml + "< fin_yr>"+ fin_yr+"</fin_yr>";
		 * 
		 * System.out.println("A52 updated"); } catch(Exception e) {
		 * e.getStackTrace(); xml=xml+"<flag>failure1</flag>"; }
		 * 
		 * try { ps=con.prepareStatement(
		 * "insert into  FAS_AA52REGISTER ( ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,FINANCIAL_YEAR,ASSET_CODE,BOOKVALUE,APPOR_GRANT,DEP_DEBIT,ASSET_MAJOR_CLASS_CODE) values (?,?,?,?,?,?,?,?) "
		 * );
		 * 
		 * ps.setInt(1, acc_uid ); ps.setInt(2, acc_ofid ); ps.setString(3,
		 * fin_yr ); ps.setInt(4, ass_code); ps.setInt(5, bal); ps.setInt(6,
		 * grant); ps.setInt(7, depre); ps.setInt(8, ass_maj_code);
		 * ps.executeUpdate();
		 * 
		 * xml = xml + "<flag>success</flag>"; xml = xml + "< acc_unitid>"+
		 * acc_uid+"</acc_unitid>"; xml = xml + "< acc_offid>"+
		 * acc_ofid+"</acc_offid>"; xml = xml + "< fin_yr>"+ fin_yr+"</fin_yr>";
		 * xml = xml + "< asset_code>"+ ass_code+"</asset_code>"; xml = xml +
		 * "< clos_bal>"+ bal+"</clos_bal>"; xml = xml + "< app_grant>"+
		 * grant+"</app_grant>"; xml = xml + "< dep_cost>"+ depre+"</dep_cost>";
		 * xml = xml + "< asset_maj_code>"+ ass_maj_code +"</asset_maj_code>";
		 * System.out.println("Value moved to AA52"); } catch(Exception e) {
		 * e.getStackTrace(); xml=xml+"<flag>failure</flag>"; } // }
		 * xml=xml+"</response>"; }
		 */

		if (cmd.equalsIgnoreCase("subm")) {
			System.out.println("***inside submit***");
			xml = xml + "<response><command>subm</command>";
			String asscode = request.getParameter("asset_code");
			System.out.println("===asset code===" + asscode);
			String[] assetCodeF = asscode.split(",");
			String[] asset = null;
			String asset_code = "";
			String acc_unitid = "";
			String acc_offid = "";
			String fin_yr = "";
			String app_grant = "";
			String clos_bal = "";
			String dep_cost = "";
			String asset_maj_code = "", asset_min_code = "";

			int acc_uid = 0;
			int acc_ofid = 0;
			int ass_code = 0;
			int bal = 0;
			int grant = 0;
			int depre = 0;
			int ass_maj_code = 0, ass_min_code = 0;

			System.out.println("assetCodeF.length " + assetCodeF.length);
			for (int i = 0; i < assetCodeF.length; i++) {
				System.out.println("assetCodeF[i] " + assetCodeF[i]);
				asset = assetCodeF[i].split("/");
				System.out.println("asset len " + asset.length);
				System.out.println("asset " + asset);
				for (int j = 0; j < asset.length; j++) {
					asset_code = asset[0];
					acc_unitid = asset[2];
					acc_offid = asset[3];
					fin_yr = asset[4];
					app_grant = asset[5];
					clos_bal = asset[6];
					dep_cost = asset[7];
					asset_maj_code = asset[8];
					asset_min_code = asset[1];
				}

				acc_uid = Integer.parseInt(acc_unitid);
				acc_ofid = Integer.parseInt(acc_offid);
				ass_code = Integer.parseInt(asset_code);
				bal = Integer.parseInt(clos_bal);
				grant = Integer.parseInt(app_grant);
				depre = Integer.parseInt(dep_cost);
				ass_maj_code = Integer.parseInt(asset_maj_code);
				ass_min_code = Integer.parseInt(asset_min_code);
				System.out.println("grant:::" + grant);

				try {

					PreparedStatement ps_log = con
							.prepareStatement("INSERT "
									+ " INTO FAS_A52_REGISTER_EDIT_LOG "
									+ "  (SELECT ACCOUNTING_UNIT_ID, "
									+ "      ACCOUNTING_UNIT_OFFICE_ID, "
									+ "      FINANCIAL_YEAR, "
									+ "      ASSET_MAJOR_CLASS_CODE, "
									+ "      ASSET_CODE, "
									+ "      HEAD_OF_ACCOUNT, "
									+ "      DEPRECIATION, "
									+ "      APPORTIONMENT, "
									+ "      OPEN_BAL_QTY, "
									+ "      OPENING_BAL_VALUE, "
									+ "      RECIEPTS_YEAR_DR_QTY, "
									+ "      RECIEPTS_YEAR_CR_QTY, "
									+ "      RECIEPTS_YR_DR_VALUE, "
									+ "      RECIEPTS_YR_CR_VALUE, "
									+ "      ISSUES_YEAR_DR_QTY, "
									+ "      ISSUES_YEAR_CR_QTY, "
									+ "      ISSUES_YR_DR_VALUE, "
									+ "      ISSUES_YR_CR_VALUE, "
									+ "      DEP_PREV_YEAR, "
									+ "      APP_PRE_YR, "
									+ "      DEPRE_REC_AC, "
									+ "      APP_REC_AC, "
									+ "      DEPRE_ALLOWED_YR_DR, "
									+ "      DEPRE_ALLOWED_YR_CR, "
									+ "      APPRO_ALLOWED_YR_DR, "
									+ "      APPRO_ALLOWED_YR_CR, "
									+ "      DEPRE_TR_AC, "
									+ "      APPRO_TR_AC, "
									+ "      DEPRE_UPTO_DATE, "
									+ "      APPRO_UPTO_DATE, "
									+ "      NET_DEPRE_COST, "
									+ "      REMARKS, "
									+ "      UPDATED_BY_USERID, "
									+ "      UPDATED_DATE, "
									+ "      ASSET_MINOR_CLASS_CODE, "
									+ "      OFFICE_WING_SINO, "
									+ "      'Y', "
									+ "      DATE_OF_TRANSFER, "
									+ "      DATE_OF_CLOSURE, "
									+ "      TRANSFER_REMARKS "
									+ "    FROM FAS_A52_REGISTER_EDIT "
									+ "    WHERE ACCOUNTING_UNIT_ID     =? "
									+ "    AND ACCOUNTING_UNIT_OFFICE_ID=?  "
									+ "    AND FINANCIAL_YEAR           =? and ASSET_CODE=? and ASSET_MAJOR_CLASS_CODE=? and ASSET_MINOR_CLASS_CODE =? and TRANSFER_REMARKS = 'PV' "
									+ "  )");
					ps_log.setInt(1, acc_uid);
					System.out.println("... " + acc_uid);
					ps_log.setInt(2, acc_ofid);
					System.out.println("... " + acc_ofid);
					ps_log.setString(3, fin_yr);
					System.out.println("... " + fin_yr);
					ps_log.setInt(4, ass_code);
					System.out.println("... " + ass_code);
					ps_log.setInt(5, ass_maj_code);
					System.out.println("... " + ass_maj_code);
					ps_log.setInt(6, ass_min_code);
					System.out.println("... " + ass_min_code);
					int k = ps_log.executeUpdate();
					System.out.println("LOG INSERT ... " + k);
					if (k > 0) {

						ps = con.prepareStatement("delete from FAS_A52_REGISTER_EDIT  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and FINANCIAL_YEAR=? and ASSET_CODE=? and ASSET_MAJOR_CLASS_CODE=? and ASSET_MINOR_CLASS_CODE =? and TRANSFER_REMARKS = 'PV'");

						ps.setInt(1, acc_uid);
						ps.setInt(2, acc_ofid);
						ps.setString(3, fin_yr);
						ps.setInt(4, ass_code);
						ps.setInt(5, ass_maj_code);
						ps.setInt(6, ass_min_code);

						int k1 = ps.executeUpdate();
						if (k1 > 0) {
							System.out
									.println("DELETE REGISTER EDIT ... " + k1);
							try {
								PreparedStatement ps_ins = con
										.prepareStatement("insert into  FAS_AA52REGISTER ( ACCOUNTING_UNIT_ID,"
												+ "ACCOUNTING_UNIT_OFFICE_ID,"
												+ "FINANCIAL_YEAR,"
												+ "ASSET_CODE,"
												+ "BOOKVALUE,"
												+ "APPOR_GRANT,"
												+ "DEP_DEBIT,UPDATED_BY_USERID,UPDATED_DATE, "
												+ "ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE,QUANTITY) "
												+ " (SELECT ACCOUNTING_UNIT_ID, "
												+ " ACCOUNTING_UNIT_OFFICE_ID,"
												+ " FINANCIAL_YEAR,"
												+ " ASSET_CODE,"
												+ "	NET_DEPRE_COST,"
												+ "	APPRO_UPTO_DATE,"
												+ "DEPRE_UPTO_DATE,?,?, "
												+ "ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE, "
												+ "	(OPEN_BAL_QTY+(RECIEPTS_YEAR_DR_QTY-RECIEPTS_YEAR_CR_QTY)-(ISSUES_YEAR_DR_QTY-ISSUES_YEAR_CR_QTY)) from FAS_A52_REGISTER_EDIT_LOG "
												+ "    WHERE ACCOUNTING_UNIT_ID     =? "
												+ "    AND ACCOUNTING_UNIT_OFFICE_ID=? and ASSET_CODE=?"
												+ "    AND FINANCIAL_YEAR           =? and ASSET_MAJOR_CLASS_CODE=? and ASSET_MINOR_CLASS_CODE =? and TRANSFER_REMARKS = 'PV'"
												+ ")");
								ps_ins.setString(1, user_id);
								ps_ins.setTimestamp(2, ts);
								ps_ins.setInt(3, acc_uid);
								ps_ins.setInt(4, acc_ofid);
								ps_ins.setInt(5, ass_code);
								ps_ins.setString(6, fin_yr);
								ps_ins.setInt(7, ass_maj_code);
								ps_ins.setInt(8, ass_min_code);
								/*
								 * ps.setInt(1, acc_uid ); ps.setInt(2, acc_ofid
								 * ); ps.setString(3, fin_yr ); ps.setInt(4,
								 * ass_code); ps.setInt(5, bal); ps.setInt(6,
								 * grant); ps.setInt(7, depre); ps.setInt(8,
								 * ass_maj_code);
								 */
								int jj = ps_ins.executeUpdate();
								if (jj > 0) {
									System.out.println("TRANSFER SUCCESS .. ");
									xml = xml + "<flag>success</flag>";
									xml = xml + "< acc_unitid>" + acc_uid
											+ "</acc_unitid>";
									xml = xml + "< acc_offid>" + acc_ofid
											+ "</acc_offid>";
									xml = xml + "< fin_yr>" + fin_yr
											+ "</fin_yr>";
									xml = xml + "< asset_code>" + ass_code
											+ "</asset_code>";
									xml = xml + "< clos_bal>" + bal
											+ "</clos_bal>";
									xml = xml + "< app_grant>" + grant
											+ "</app_grant>";
									xml = xml + "< dep_cost>" + depre
											+ "</dep_cost>";
									xml = xml + "< asset_maj_code>"
											+ ass_maj_code
											+ "</asset_maj_code>";
									System.out.println("Value moved to AA52");
									con.commit();
								} else {
									con.rollback();
									xml = xml + "<flag>failure</flag>";
								}
							} catch (Exception e) {
								e.getStackTrace();
								con.rollback();
								xml = xml + "<flag>failure</flag>";
							}

							xml = xml + "< acc_unitid>" + acc_uid
									+ "</acc_unitid>";
							xml = xml + "< acc_offid>" + acc_ofid
									+ "</acc_offid>";
							xml = xml + "< fin_yr>" + fin_yr + "</fin_yr>";
						} else {
							con.rollback();
							xml = xml + "<flag>failure</flag>";
						}
					} else {
						con.rollback();
						xml = xml + "<flag>failure</flag>";
					}
				} catch (Exception e) {
					
					e.getStackTrace();
					try {
						con.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					xml = xml + "<flag>failure</flag>";
				}

				//
			}
			xml = xml + "</response>";
		}
		// display

		if (cmd.equalsIgnoreCase("dis")) {
			System.out.println("===dis func====");
			xml = xml + "<response><command>dis</command>";
			int count = 0;

			try {

				String sql = " SELECT UNIQUE(aa.asset_code), "
						+ " ss.office_id,"
						+ " tt.office_name,"
						+ " TO_CHAR(aa.VERIFIEDON,'dd/MM/yyyy') AS VERIFIEDON,"
						+ " aa.QTY_TO_BE_CONDEMNED,"
						+
						// " d.NET_DEPRE_COST,"+
						// " d.TOTAL_DEPRE,"+
						/*
						 * " d.DEPRE_UPTO_DATE,"+ " d.DEPRE_ALLOWED_YR,"+
						 * " d.DEP_PREV_YEAR,"+
						 */
						" DECODE(d.DEPRE_UPTO_DATE,NULL,0,d.DEPRE_UPTO_DATE)   AS DEPRE_UPTO_DATE, "
						+ "   DECODE(d.DEPRE_ALLOWED_YR,NULL,0,d.DEPRE_ALLOWED_YR)   AS DEPRE_ALLOWED_YR, "
						+ "    DECODE(d.DEP_PREV_YEAR,NULL,0,d.DEP_PREV_YEAR)   AS DEP_PREV_YEAR, "
						+ "    DECODE(d.NET_DEPRE_COST,NULL,0,d.NET_DEPRE_COST)   AS NET_DEPRE_COST, "
						+ " d.accunitId,"
						+ " d.ACCOUNTING_UNIT_OFFICE_ID,"
						+ " d.ASSET_MAJOR_CLASS_CODE,d.ASSET_MINOR_CLASS_CODE,d.remarks,"
						+ " d.FINANCIAL_YEAR"
						+
						// " d.TOT_APP_GRANT"+
						" FROM "
						+ " (SELECT OFFICE_ID,"
						+ " EMPLOYEE_STATUS_ID,"
						+ "  EMPLOYEE_ID"
						+ " FROM HRM_EMP_CURRENT_POSTING"
						+ " WHERE EMPLOYEE_STATUS_ID='WKG'"
						+ " AND EMPLOYEE_ID= ?"
						+ " )ss "
						+ " LEFT OUTER JOIN"
						+ " ( SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES"
						+ " )tt"
						+ " ON ss.OFFICE_ID=tt.OFFICE_ID"
						+ " LEFT OUTER JOIN"
						+ " (SELECT UNIQUE(asset_code) AS asset_code,"
						+ "  ACCOUNTING_UNIT_OFFICE_ID,"
						+ " VERIFIEDON,"
						+ " QTY_TO_BE_CONDEMNED"
						+ "  FROM A52_verifacation_details"
						+
						// " WHERE TO_BE_CONDEMNED='Y'"+
						" WHERE QTY_TO_BE_CONDEMNED >0 "
						+ " )aa"
						+ " ON ss.OFFICE_ID=aa.ACCOUNTING_UNIT_OFFICE_ID"
						+ " LEFT OUTER JOIN"
						+ " (SELECT ACCOUNTING_UNIT_OFFICE_ID,ASSET_CODE FROM fas_asset_val_ac_details"
						+ " )bb"
						+ " ON ss.OFFICE_ID  =bb.ACCOUNTING_UNIT_OFFICE_ID"
						+ " AND aa.asset_code=bb.asset_code"
						+ " INNER JOIN"
						+ " (SELECT ACCOUNTING_UNIT_OFFICE_ID,"
						+ "  DEP_PREV_YEAR,"
						+ "  DEPRE_UPTO_DATE,"
						+ "  NET_DEPRE_COST,"
						+
						// "  TOTAL_DEPRE,"+
						" nvl(DEPRE_ALLOWED_YR_CR,0)-  nvl(DEPRE_ALLOWED_YR_DR,0) as DEPRE_ALLOWED_YR,"
						+ "  ACCOUNTING_UNIT_ID AS accunitId,"
						+ "  FINANCIAL_YEAR,"
						+
						// "  TOT_APP_GRANT,"+
						" ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE, "
						+ "  ASSET_CODE,remarks "
						+ "  FROM FAS_A52_REGISTER_EDIT"
						+ " where TRANSFER_REMARKS ='PV' AND ( MOVED_TO_AA52= 'N' OR MOVED_TO_AA52 IS NULL ) "
						+ " )d"
						+ " ON ss.OFFICE_ID  =d.ACCOUNTING_UNIT_OFFICE_ID"
						+ " AND aa.ASSET_CODE=d.ASSET_CODE "
						+ " order by aa.asset_code";
				// 8April2014
				/*
				 * String sql= " SELECT UNIQUE(aa.asset_code), " +
				 * " ss.office_id,"+ " tt.office_name,"+
				 * " TO_CHAR(aa.VERIFIEDON,'dd/MM/yyyy') AS VERIFIEDON,"+
				 * " aa.QTY_TO_BE_CONDEMNED,"+ " d.NET_DEPRE_COST,"+
				 * //" d.TOTAL_DEPRE,"+ " d.DEPRE_UPTO_DATE,"+
				 * " d.DEPRE_ALLOWED_YR,"+ " d.DEP_PREV_YEAR,"+ " d.accunitId,"+
				 * " d.ACCOUNTING_UNIT_OFFICE_ID,"+
				 * " d.ASSET_MAJOR_CLASS_CODE,"+ " d.FINANCIAL_YEAR"+
				 * //" d.TOT_APP_GRANT"+ " FROM "+ " (SELECT OFFICE_ID,"+
				 * " EMPLOYEE_STATUS_ID,"+ "  EMPLOYEE_ID"+
				 * " FROM HRM_EMP_CURRENT_POSTING"+
				 * " WHERE EMPLOYEE_STATUS_ID='WKG'"+ " AND EMPLOYEE_ID= ?"+
				 * " )ss "+ " LEFT OUTER JOIN"+
				 * " ( SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES"+
				 * " )tt"+ " ON ss.OFFICE_ID=tt.OFFICE_ID"+ " LEFT OUTER JOIN"+
				 * " (SELECT UNIQUE(asset_code) AS asset_code,"+
				 * "  ACCOUNTING_UNIT_OFFICE_ID,"+ " VERIFIEDON,"+
				 * " QTY_TO_BE_CONDEMNED"+ "  FROM A52_verifacation_details"+
				 * //" WHERE TO_BE_CONDEMNED='Y'"+
				 * " WHERE QTY_TO_BE_CONDEMNED >0 "+ " )aa"+
				 * " ON ss.OFFICE_ID=aa.ACCOUNTING_UNIT_OFFICE_ID"+
				 * " LEFT OUTER JOIN"+
				 * " (SELECT ACCOUNTING_UNIT_OFFICE_ID,ASSET_CODE FROM fas_asset_val_ac_details"
				 * + " )bb"+ " ON ss.OFFICE_ID  =bb.ACCOUNTING_UNIT_OFFICE_ID"+
				 * " AND aa.asset_code=bb.asset_code"+ " INNER JOIN"+
				 * " (SELECT ACCOUNTING_UNIT_OFFICE_ID,"+ "  DEP_PREV_YEAR,"+
				 * "  DEPRE_UPTO_DATE,"+ "  NET_DEPRE_COST,"+
				 * //"  TOTAL_DEPRE,"+
				 * "  (DEPRE_ALLOWED_YR_DR+DEPRE_ALLOWED_YR_CR)as DEPRE_ALLOWED_YR,"
				 * + "  ACCOUNTING_UNIT_ID AS accunitId,"+ "  FINANCIAL_YEAR,"+
				 * //"  TOT_APP_GRANT,"+ " ASSET_MAJOR_CLASS_CODE, "+
				 * "  ASSET_CODE"+ "  FROM FAS_A52_REGISTER_EDIT " +
				 * " where MOVED_TO_AA52= 'N' OR MOVED_TO_AA52 IS NULL"+ " )d"+
				 * " ON ss.OFFICE_ID  =d.ACCOUNTING_UNIT_OFFICE_ID"+
				 * " AND aa.ASSET_CODE=d.ASSET_CODE " +
				 * " order by aa.asset_code";
				 */

				System.out.println("sql " + sql);

				ps = con.prepareStatement(sql);
				ps.setInt(1, employeeId);

				rs1 = ps.executeQuery();

				int depCost = 0, ClosingBalance = 0;
				while (rs1.next()) {

					// depCost =
					// rs1.getInt("TOTAL_DEPRE")-rs1.getInt("DEP_PREV_YEAR");
					depCost = rs1.getInt("NET_DEPRE_COST");
					System.out.println(" depCost " + depCost);
					int dd = Integer.parseInt(rs1.getString("DEPRE_UPTO_DATE"));
					// ClosingBalance = depCost+dd;
					System.out.println(" dd " + dd);
					ClosingBalance = depCost
							+ Integer
									.parseInt(rs1.getString("DEPRE_UPTO_DATE"));
					System.out
							.println("ClosingBalance Value calculated********"
									+ ClosingBalance);
					xml = xml + "<asset_code>" + rs1.getInt("asset_code")
							+ "</asset_code>";
					xml = xml + "<phy_loc>" + rs1.getString("office_name")
							+ "</phy_loc>";
					xml = xml + "<phy_loc_verdat>"
							+ rs1.getString("VERIFIEDON") + "</phy_loc_verdat>";
					xml = xml + "<obs_item>"
							+ rs1.getInt("QTY_TO_BE_CONDEMNED") + "</obs_item>";
					xml = xml + "<dep_dat>" + rs1.getString("DEPRE_UPTO_DATE")
							+ "</dep_dat>";
					xml = xml + "<pro_dep_dat>"
							+ rs1.getInt("DEPRE_ALLOWED_YR") + "</pro_dep_dat>";

					xml = xml + "<remarks>" + rs1.getString("remarks")
							+ "</remarks>";
					xml = xml + "<dep_cost>" + depCost + "</dep_cost>";
					// xml=xml+"<pro_dep_cost>"+depCost+"</pro_dep_cost>";
					xml = xml + "<clos_bal>" + ClosingBalance + "</clos_bal>";
					xml = xml + "<minor_code>"
							+ rs1.getInt("ASSET_MINOR_CLASS_CODE")
							+ "</minor_code>";

					xml = xml + "<asset_maj_code>"
							+ rs1.getInt("ASSET_MAJOR_CLASS_CODE")
							+ "</asset_maj_code>";
					xml = xml + "<acc_unitid>" + rs1.getInt("accunitId")
							+ "</acc_unitid>";
					xml = xml + "<acc_offid>"
							+ rs1.getInt("ACCOUNTING_UNIT_OFFICE_ID")
							+ "</acc_offid>";
					xml = xml + "<fin_yr>" + rs1.getString("FINANCIAL_YEAR")
							+ "</fin_yr>";
					// xml=xml+"<app_grant>"+rs1.getInt("TOT_APP_GRANT")+"</app_grant>";

					count++;
				}
				if (count > 0) {
					xml = xml + "<flag>success</flag>";
				} else {
					xml = xml + "<flag>nodata</flag>";
				}
				rs1.close();
				ps.close();
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}

		System.out.println("XML is:" + xml);
		out.write(xml);
		out.close();

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
