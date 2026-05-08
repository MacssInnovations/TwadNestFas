package Servlets.FAS.FAS1.MIS.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class Twad_OpeningBalance extends HttpServlet {
	/**
	 * ECode_for_NMRs.pdf
	 */
	private static final long serialVersionUID = 1L;
	private static String CONTENT_TYPE = "text/xml; charset=windows-1252";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);

		
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

		String userid = (String) session.getAttribute("UserId");
		String empid = userid.substring(4, userid.length());
		System.out.println("Empid -------------->" + empid);

		/**
		 * Variables Declaration
		 */
		Connection con = null;

		/** Combo Loading */
		PreparedStatement ps = null, ps_category = null;
		ResultSet rs = null, rs_category = null;
		int c = 1;float sum_cre=0,sum_deb=0,sum_net=0;

		/**
		 * Database Connection
		 */
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
			System.out.println("Exception in opening connection :" + e);
		}

		/** xml */
		String xml = "", cat_id = "";

		String Command = request.getParameter("Command");
		System.out
				.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++="
						+ Command);

		String fin_year = request.getParameter("fin_year");
		String[] year = fin_year.split("-");
		int yearFrom = Integer.parseInt(year[0]);
		int yearTo = Integer.parseInt(year[1]);
	      int yearFrom_Pre=yearFrom-1;
	      String year_val=year[1].substring(2);
	      int pre_Toyear=Integer.parseInt(year_val)-1;
	    String fin_Preyear=yearFrom_Pre+"-"+pre_Toyear;
	      System.out.println("Previous year value : "+pre_Toyear);
		if (Command.equalsIgnoreCase("viewGrid")) {
			xml = "<response><command>viewGrid</command>";
			System.out.println("all units::::::");
			try {
				String qry_str ="SELECT sec_fin.FIN_YEAR, " +
				"  sec_fin.MINOR_ID, " +
				"  (SELECT MINORGROUPINGNAME " +
				"  FROM MINORGROUPING " +
				"  WHERE MINORGROUPINGID= sec_fin.MINOR_ID " +
				"  )Minor_Dsec, " +
				"  sec_fin1.Addition as Addition , " +
				"  sec_fin1.Deletion as Deletion , " +
				"  sec_fin1.Depreciation as Depreciation, " +
				"  sec_fin1.Depreciation_YY as Depreciation_YY, " +
				"  sec_fin1.Discarder_Asset as Discarder_Asset, " +
				"  sec_fin1.others as others, " +
				"  sec_fin.op_bal_5 as Grd_TOT_5, " +
				"  sec_fin.op_bal_10 as Dep_upto_Date10, " +
				"  sec_fin.op_bal_14 as Dis_Asset_14, " +
				"  sec_fin.op_bal_19 Apportionment_Net " +
				" FROM " +
				"  (SELECT fin.FIN_YEAR, " +
				"    fin.MINOR_ID, " +
				"    SUM(fin.op_bal_5)op_bal_5, " +
				"    SUM(fin.op_bal_10)op_bal_10, " +
				"    SUM(fin.op_bal_14)op_bal_14, " +
				"    SUM(fin.op_bal_19)op_bal_19 " +
				"  FROM " +
				"    (SELECT ID, " +
				"      FIN_YEAR, " +
				"      MINOR_ID, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN ID=5 " +
				"        THEN SUM(AMOUNT) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN ID=5 " +
				"        THEN SUM(AMOUNT) " +
				"      END)) AS op_bal_5, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN ID=10 " +
				"        THEN SUM(AMOUNT) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN ID=10 " +
				"        THEN SUM(AMOUNT) " +
				"      END)) AS op_bal_10, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN ID=14 " +
				"        THEN SUM(AMOUNT) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN ID=14 " +
				"        THEN SUM(AMOUNT) " +
				"      END)) AS op_bal_14, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN ID=19 " +
				"        THEN SUM(AMOUNT) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN ID=19 " +
				"        THEN SUM(AMOUNT) " +
				"      END)) AS op_bal_19, " +
				"      AMOUNT " +
				"    FROM TWAD_CONSOLIDATED_OB_DATA " +
				"    WHERE FIN_YEAR='"+fin_Preyear+"' " +
				"    GROUP BY ID, " +
				"      FIN_YEAR, " +
				"      MINOR_ID, " +
				"      AMOUNT " +
				"    )fin " +
				"  GROUP BY fin.FIN_YEAR, " +
				"    fin.MINOR_ID " +
				"  ORDER BY fin.MINOR_ID " +
				"  )sec_fin " +
				" JOIN " +
				"  (SELECT xyz.ANNUALGROUPINGID, " +
				"    xyz.MINORGROUPINGID, " +
				"    SUM(xyz.Addition)Addition, " +
				"    SUM(xyz.Deletion)Deletion, " +
				"    SUM(xyz.Depreciation)Depreciation, " +
				"    SUM(xyz.Depreciation_YY)Depreciation_YY, " +
				"    SUM(xyz.Discarder_Asset)Discarder_Asset, " +
				"    SUM(xyz.others)OTHERS " +
				"  FROM " +
				"    (SELECT sec1.ANNUALGROUPINGID, " +
				"      sec1.MINORGROUPINGID, " +
				"      sec1. ACTIVITYID, " +
				"      sec2.net, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=10 " +
				"        THEN SUM(sec2.net) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=10 " +
				"        THEN SUM(sec2.net) " +
				"      END)) AS Addition, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=11 " +
				"        THEN SUM(sec2.net) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=11 " +
				"        THEN SUM(sec2.net) " +
				"      END)) AS Deletion, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=12 " +
				"        THEN SUM(sec2.net) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=12 " +
				"        THEN SUM(sec2.net) " +
				"      END)) AS Depreciation, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=17 " +
				"        THEN SUM(sec2.net) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=17 " +
				"        THEN SUM(sec2.net) " +
				"      END)) AS Depreciation_YY, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=18 " +
				"        THEN SUM(sec2.net) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID=18 " +
				"        THEN SUM(sec2.net) " +
				"      END)) AS Discarder_Asset, " +
				"      DECODE(( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID NOT IN (10,11,12,17,18) " +
				"        THEN SUM(sec2.net) " +
				"      END),NULL,0,( " +
				"      CASE " +
				"        WHEN sec1. ACTIVITYID NOT IN (10,11,12,17,18) " +
				"        THEN SUM(sec2.net) " +
				"      END)) AS OTHERS " +
				"    FROM " +
				"      (SELECT ACCOUNTCODE, " +
				"        ANNUALGROUPINGID, " +
				"        MINORGROUPINGID , " +
				"        ACTIVITYID " +
				"      FROM HEADOFACCOUNTS " +
				"      )sec1 " +
				"    JOIN " +
				"      (SELECT " +
				"        (SELECT MAJOR_HEAD_DESC " +
				"        FROM COM_MST_MAJOR_HEADS " +
				"        WHERE MAJOR_HEAD_CODE=aa.major_code " +
				"        )AS major_desc, " +
				"        aa.* " +
				"      FROM " +
				"        (SELECT a.ACCOUNT_HEAD_CODE AS head_code, " +
				"          (SELECT MAJOR_HEAD_CODE " +
				"          FROM COM_MST_ACCOUNT_HEADS " +
				"          WHERE ACCOUNT_HEAD_CODE= a.ACCOUNT_HEAD_CODE " +
				"          ) AS major_code, " +
				"          (SELECT ACCOUNT_HEAD_DESC " +
				"          FROM COM_MST_ACCOUNT_HEADS " +
				"          WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
				"          )                AS ACCOUNT_HEADS, " +
				"          SUM(Debit)       AS Debit, " +
				"          SUM(Credit)      AS Credit, " +
				"          SUM(Debit-Credit)AS net " +
				"        FROM " +
				"          (SELECT ACCOUNT_HEAD_CODE, " +
				"            CURRENT_MONTH_DEBIT  AS Debit, " +
				"            CURRENT_MONTH_CREDIT AS Credit " +
				"          FROM FAS_TRIAL_BALANCE " +
				"          WHERE  to_date((cashbook_month " +
					"      ||'-' " +
					"      ||cashbook_year),'mm-yyyy') BETWEEN to_date(4"+
					"      ||'-' " +
					"      ||"+yearFrom+",'mm-yyyy') " +
					"    AND to_date(3"+
					"      ||'-' " +
					"      ||"+yearTo+",'mm-yyyy') " +
				"          ) a " +
				"        GROUP BY a.ACCOUNT_HEAD_CODE " +
				"        )aa " +
				"      ORDER BY aa.major_code, " +
				"        aa.head_code " +
				"      )sec2 " +
				"    ON sec1.ACCOUNTCODE=sec2.head_code " +
				"    GROUP BY sec1.ANNUALGROUPINGID, " +
				"      sec1.MINORGROUPINGID, " +
				"      sec1. ACTIVITYID, " +
				"      sec2.net " +
				"    )xyz " +
				"  GROUP BY xyz.ANNUALGROUPINGID, " +
				"    xyz.MINORGROUPINGID " +
				"  ) sec_fin1 ON sec_fin.MINOR_ID=sec_fin1.MINORGROUPINGID " +
				" ORDER BY sec_fin.MINOR_ID";
				
						System.out.println("...  ..."+qry_str);
						ps = con.prepareStatement(qry_str);
						rs = ps.executeQuery();

						while (rs.next()) {
						
							c++;
								xml += "<MINOR_ID>" + rs.getInt("MINOR_ID")
							+ "</MINOR_ID>";
					xml +=   "<Addition>" + rs.getDouble("Addition")
							+ "</Addition>";
					xml += "<Deletion>" + rs.getDouble("Deletion")
							+ "</Deletion>";
					xml += "<Depreciation>" + rs.getDouble("Depreciation") + "</Depreciation>";
					xml += "<Depreciation_YY>" + rs.getDouble("Depreciation_YY")
							+ "</Depreciation_YY>";
					xml += "<Discarder_Asset>" + rs.getDouble("Discarder_Asset")
							+ "</Discarder_Asset>";
					xml += "<others>" + rs.getDouble("others")
							+ "</others>";
					xml += "<Grd_TOT>" + rs.getDouble("Grd_TOT_5")
							+ "</Grd_TOT>";
					xml += "<Dep_upto_Date>" + rs.getDouble("Dep_upto_Date10")
							+ "</Dep_upto_Date>";
					xml += "<Dis_Asset>" + rs.getDouble("Dis_Asset_14")
							+ "</Dis_Asset>";
					xml += "<App_Net>" + rs.getDouble("Apportionment_Net")
							+ "</App_Net>";
					xml += "<Minor_Dsec><![CDATA["
							+ rs.getString("Minor_Dsec")
							+ "]]></Minor_Dsec>";
						}
				 
					if(c>0)
					{
						
							xml = xml + "<flag>Success</flag>";						
					}
					else{
						xml = xml + "<flag>Failure</flag>";
						}
				}
		

			 catch (Exception e) {
				 System.out.println("exception::::");
				xml = xml + "<flag1>Failure</flag1>";
				System.out.println("fail:::::"+e.getMessage());
			}

		}
		xml = xml + "</response>";
		out.write(xml);
		out.close();
		System.out.println(xml);

	}


	private String parseString(int year_val) {
		// TODO Auto-generated method stub
		return null;
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
