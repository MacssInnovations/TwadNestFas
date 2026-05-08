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

public class TwadMIS_Report extends HttpServlet {
	/**
	 * ECode_for_NMRs.pdf
	 */
	private static final long serialVersionUID = 1L;
	private static String CONTENT_TYPE = "text/xml; charset=windows-1252";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("test servlet");
		// response.setContentType(CONTENT_TYPE);

		/**
		 * Set Content Type
		 */
		PrintWriter out = response.getWriter();
		CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);

		/**
		 * Session Checking
		 */
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

		// ** Get User ID *//*
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
		int CmbFrom_Month = Integer.parseInt(request
				.getParameter("CmbFrom_Month"));
		int CmbTo_Month = Integer.parseInt(request.getParameter("CmbTo_Month"));
		System.out.println("fin_year" + fin_year + "... monthfrom >> "
				+ CmbFrom_Month + ".... month To >> " + CmbTo_Month);
		if (Command.equalsIgnoreCase("All")) {
			xml = "<response><command>All</command>";
			System.out.println("all units::::::");
			try {
				String qry_str ="";
				
				
					qry_str="SELECT " +
					"  (SELECT MAJOR_HEAD_DESC " +
					"  FROM COM_MST_MAJOR_HEADS " +
					"  WHERE MAJOR_HEAD_CODE=aa.major_code " +
					"  )AS head_desc, " +
					"  aa.* " +
					"FROM " +
					"  (SELECT a.ACCOUNT_HEAD_CODE AS head_code, " +
					"    (SELECT MAJOR_HEAD_CODE " +
					"    FROM COM_MST_ACCOUNT_HEADS " +
					"    WHERE ACCOUNT_HEAD_CODE= a.ACCOUNT_HEAD_CODE " +
					"    ) AS major_code, " +
					"    (SELECT ACCOUNT_HEAD_DESC " +
					"    FROM COM_MST_ACCOUNT_HEADS " +
					"    WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
					"    )                AS ACCOUNT_HEADS, " +
					"    SUM(Debit)       AS Debit, " +
					"    SUM(Credit)      AS Credit, " +
					"    SUM(Debit-Credit)AS net " +
					"  FROM " +
					"    (SELECT ACCOUNT_HEAD_CODE, " +
					"      CURRENT_MONTH_DEBIT  AS Debit, " +
					"      CURRENT_MONTH_CREDIT AS Credit " +
					"    FROM FAS_TRIAL_BALANCE " +
					"    WHERE to_date((cashbook_month " +
					"      ||'-' " +
					"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
					"      ||'-' " +
					"      ||"+yearFrom+",'mm-yyyy') " +
					"    AND to_date("+CmbTo_Month +
					"      ||'-' " +
					"      ||"+yearTo+",'mm-yyyy') " +
				/*	"    AND ACCOUNT_HEAD_CODE IN " +
					"      (SELECT ACCOUNT_HEAD_CODE " +
					"      FROM COM_MST_ACCOUNT_HEADS " +
					"      WHERE MAJOR_HEAD_CODE='"+cat_id+"' " +
					"      ) " +*/
					"    ) a " +
					"  GROUP BY a.ACCOUNT_HEAD_CODE " +
					"  ORDER BY a.ACCOUNT_HEAD_CODE " +
					"  )aa";
					

						System.out.println("... "+cat_id+" ..."+qry_str);
						ps = con.prepareStatement(qry_str);
						rs = ps.executeQuery();

						while (rs.next()) {
						
							c++;
							xml = xml + "<head_code>" + rs.getInt("head_code")
									+ "</head_code>";
							xml = xml + "<Debit>" + rs.getLong("Debit")
									+ "</Debit>";
							xml = xml + "<Credit>" + rs.getLong("Credit")
									+ "</Credit>";
							xml = xml + "<NET>" + rs.getLong("NET") + "</NET>";
							xml = xml + "<head_desc><![CDATA["
									+ rs.getString("head_desc")
									+ "]]></head_desc>";
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
		else if(Command.equalsIgnoreCase("All_x"))
		{

			xml = "<response><command>All</command>";
			c=0;
			try {
				String qry_str ="";
				
				xml += "<count>";
					qry_str="SELECT " +
					"  (SELECT MAJOR_HEAD_DESC " +
					"  FROM COM_MST_MAJOR_HEADS " +
					"  WHERE MAJOR_HEAD_CODE=aa.major_code " +
					"  )AS Major_desc, " +
					"  aa.* " +
					"FROM " +
					"  (SELECT a.ACCOUNT_HEAD_CODE AS head_code, " +
					"    (SELECT MAJOR_HEAD_CODE " +
					"    FROM COM_MST_ACCOUNT_HEADS " +
					"    WHERE ACCOUNT_HEAD_CODE= a.ACCOUNT_HEAD_CODE " +
					"    ) AS major_code, " +
					"    (SELECT ACCOUNT_HEAD_DESC " +
					"    FROM COM_MST_ACCOUNT_HEADS " +
					"    WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
					"    )                AS ACCOUNT_HEADS, " +
					"    SUM(Debit)       AS Debit, " +
					"    SUM(Credit)      AS Credit, " +
					"    SUM(Debit-Credit)AS net " +
					"  FROM " +
					"    (SELECT ACCOUNT_HEAD_CODE, " +
					"      CURRENT_MONTH_DEBIT  AS Debit, " +
					"      CURRENT_MONTH_CREDIT AS Credit " +
					"    FROM FAS_TRIAL_BALANCE " +
					"    WHERE to_date((cashbook_month " +
					"      ||'-' " +
					"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
					"      ||'-' " +
					"      ||"+yearFrom+",'mm-yyyy') " +
					"    AND to_date("+CmbTo_Month +
					"      ||'-' " +
					"      ||"+yearTo+",'mm-yyyy') " +
					"    ) a " +
					"  GROUP BY a.ACCOUNT_HEAD_CODE " +
					"  ORDER BY a.ACCOUNT_HEAD_CODE " +
					"  )aa";
						System.out.println(" ..."+qry_str);
						ps = con.prepareStatement(qry_str);
						rs = ps.executeQuery();
System.out.println("ps  ... "+ps+"rs ...."+rs);
						while (rs.next()) {
						
						/*	xml = xml + "<major_code>" + rs.getString("major_code")
							+ "</major_code>";
						xml = xml + "<head_code>" + rs.getInt("head_code")
									+ "</head_code>";*/
							xml = xml + "<Debit>" + rs.getDouble("Debit")
									+ "</Debit>";
							xml = xml + "<Credit>" + rs.getDouble("Credit")
									+ "</Credit>";
							/*	xml = xml + "<NET>" + rs.getLong("NET") + "</NET>";
							xml = xml + "<major_desc><![CDATA["
									+ rs.getString("Major_desc")
									+ "]]></major_desc>";*/
							
						}
						c++;
					xml+="<c>"+c+"</c></count>";
				}

			 catch (Exception e) {
				xml = xml + "<flag>Failure</flag>";
				e.printStackTrace();
				System.out.println(e);
			}

		
		}
		
		else if(Command.equalsIgnoreCase("catagory_wise"))
		{
			xml = "<response><command>catagory_wise</command>";
			try {
				cat_id=request.getParameter("cat_id");
				String cat_desc=request.getParameter("cat_desc");
			//	System.out.println("cat_id   ..... " +cat_id+cat_desc);
				xml+="<cat_id>"+cat_id+"</cat_id><cat_desc>"+cat_desc+"</cat_desc>";
				xml += "<count>";
				String qry_str= "SELECT bb.categry_id AS categry_id, " +
				"  (SELECT CATEGORYNAME FROM CATEGORY WHERE CATEGORYID='0'||bb.categry_id " +
				"  )            AS category_name, " +
				"  aa.head_code AS head_code, " +
				"  AA.Debit     AS Debit, " +
				"  AA.Credit    AS Credit, " +
				"  aa. NET      AS NET, " +
				"  bb.*, " +
				"  (SELECT mst.ACCOUNT_HEAD_DESC " +
				"  FROM COM_MST_ACCOUNT_HEADS mst " +
				"  WHERE mst.ACCOUNT_HEAD_CODE=aa.head_code " +
				"  )AS head_desc, " +
				"  (SELECT MAJOR_HEAD_DESC " +
				"  FROM COM_MST_MAJOR_HEADS " +
				"  WHERE MAJOR_HEAD_CODE IN " +
				"    (SELECT MAJOR_HEAD_CODE " +
				"    FROM COM_MST_ACCOUNT_HEADS " +
				"    WHERE ACCOUNT_HEAD_CODE=aa.head_code " +
				"    ) " +
				"  )AS major " +
				"FROM " +
				"  (SELECT a.head_code, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNT_HEAD_CODE AS head_code, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM FAS_JOURNAL_TRANSACTION " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  WHERE a.head_code IN " +
				"    (SELECT ACCOUNT_HEAD_CODE " +
				"    FROM COM_MST_ACCOUNT_HEADS " +
				"    WHERE MAJOR_HEAD_CODE= '"+cat_id+"'" +
				"    ) " +
				"  GROUP BY a.head_code " +
				"  UNION ALL " +
				"  SELECT a.head_code, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNT_HEAD_CODE AS head_code, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM fas_payment_transaction " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  WHERE a.head_code IN " +
				"    (SELECT ACCOUNT_HEAD_CODE " +
				"    FROM COM_MST_ACCOUNT_HEADS " +
				"    WHERE MAJOR_HEAD_CODE='"+cat_id+"'" + 
				"    ) " +
				"  GROUP BY a.head_code " +
				"  UNION ALL " +
				"  SELECT a.head_code, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNT_HEAD_CODE AS head_code, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM fas_receipt_transaction " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  WHERE a.head_code IN " +
				"    (SELECT ACCOUNT_HEAD_CODE " +
				"    FROM COM_MST_ACCOUNT_HEADS " +
				"    WHERE MAJOR_HEAD_CODE='"+cat_id+"'" +
				"    ) " +
				"  GROUP BY a.head_code " +
				"  )aa " +
				"LEFT JOIN " +
				"  (SELECT head.ACCOUNTCODE, " +
				"    head.HEADOFACCOUNT, " +
				"    head.annualgroupingid, " +
				"    (SELECT ANNUALGROUPINGNAME " +
				"    FROM ANNUALGROUPING " +
				"    WHERE ANNUALGROUPINGID=head.annualgroupingid " +
				"    )AS annualgrp_name, " +
				"    (SELECT CATEGORYID " +
				"    FROM ANNUALGROUPING " +
				"    WHERE ANNUALGROUPINGID=head.annualgroupingid " +
				"    )AS categry_id, " +
				"    head.MONTHLYGROUPINGID, " +
				"    (SELECT monthlygroupingname " +
				"    FROM MONTHLYGROUPING " +
				"    WHERE monthlygroupingid=head.MONTHLYGROUPINGID " +
				"    )AS mnthlyGid_name, " +
				"    head.SCHEMEID, " +
				"    (SELECT schemename FROM SCHEMES WHERE schemeid=head.SCHEMEID " +
				"    )AS scheme_Name, " +
				"    head.PROJECTID, " +
				"    (SELECT PROJECTNAME FROM PROJECT WHERE PROJECTID=head.PROJECTID " +
				"    )AS project_name, " +
				"    head.PROGRAMID, " +
				"    (SELECT PROGRAMNAME FROM PROGRAM WHERE PROGRAMID=head.programid " +
				"    )AS progarm_name " +
				"  FROM headofaccountcp head " +
				"  )bb " +
				"ON aa.head_code=bb.ACCOUNTCODE " +
				"ORDER BY bb.categry_id" ;
				//System.out.println("... "+cat_id+" ..."+qry_str);
				ps = con.prepareStatement(qry_str);
				rs = ps.executeQuery();

				while (rs.next()) {
				
					/*
					 * xml=xml+"<categry_id>"+rs.getInt("categry_id")+"</categry_id>"
					 * ;xml=xml+"<category_name>"+rs.getString(
					 * "category_name")+"</category_name>";
					 */
					
					sum_cre+=rs.getFloat("Credit");
					sum_deb+=rs.getFloat("Debit");
				//	sum_net+=(sum_deb-sum_cre);
				

					xml = xml + "<head_code"+c+">" + rs.getInt("head_code")
							+ "</head_code"+c+">";
					xml = xml + "<Debit"+c+">" + rs.getString("Debit")
							+ "</Debit"+c+">";
					xml = xml + "<Credit"+c+">" + rs.getString("Credit")
							+ "</Credit"+c+">";
					xml = xml + "<NET"+c+">" + rs.getInt("NET") + "</NET"+c+">";
					xml = xml + "<head_desc"+c+"><![CDATA["
							+ rs.getString("head_desc")
							+ "]]></head_desc"+c+">";
				}
		    sum_net=sum_deb-sum_cre;
			xml +="</count><sum_deb>"+sum_deb+"</sum_deb>";
			xml+="<sum_cre>"+sum_cre+"</sum_cre>";
			xml+="<sum_net>"+sum_net+"</sum_net>";
			c++;	
			}
			catch (Exception e) {
				xml = xml + "<flag>Failure</flag>";
				System.out.println(e);
			}
		}
		
		else if(Command.equalsIgnoreCase("All_test"))
		{
			xml = "<response><command>All_TEST</command>";
			try {
				String qry_str ="";
				/*
				 * "  --, aa.accounting_unit_id, " +
				 * "  --  aa.ACCOUNTING_FOR_OFFICE_ID, " + "  --  aa.head_code";
				 */
				String qry_category = "select MAJOR_HEAD_DESC,MAJOR_HEAD_CODE from COM_MST_MAJOR_HEADS order by MAJOR_HEAD_CODE";
				System.out.println(qry_category);
				ps_category = con.prepareStatement(qry_category);
				rs_category = ps_category.executeQuery();
				while (rs_category.next()) {
					cat_id = rs_category.getString("MAJOR_HEAD_CODE");
				//	System.out.println(cat_id);
					xml += "<count>";
					xml += "<Category_ID>"
							+ rs_category.getString("MAJOR_HEAD_CODE")
							+ "</Category_ID>";
					xml += "<Category_DESC>"
							+ rs_category.getString("MAJOR_HEAD_DESC")
							+ "</Category_DESC>";

					
				
qry_str= "SELECT aa.category, " +
"  SUM(AA.Debit)/10000 AS Debit, " +
"  SUM(AA.Credit)/10000 AS Credit, " +
"  SUM(AA. NET)/10000 AS NET " +
"FROM " +
"  (SELECT a.major AS category, " +
"    SUM(a.Debit)  AS Debit, " +
"    SUM(a.Credit) AS Credit, " +
"    (SUM(a.Debit)-SUM(a.Credit))AS NET " +
"  FROM " +
"    (SELECT " +
"      (SELECT MAJOR_HEAD_DESC " +
"      FROM COM_MST_MAJOR_HEADS " +
"      WHERE MAJOR_HEAD_CODE IN " +
"        (SELECT MAJOR_HEAD_CODE FROM COM_MST_ACCOUNT_HEADS WHERE MAJOR_HEAD_CODE='"+cat_id+"' " +
"        ) " +
"      )AS major , " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'DR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Debit, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'CR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Credit " +
"    FROM FAS_JOURNAL_TRANSACTION " +
"    WHERE to_date((cashbook_month " +
"      ||'-' " +
"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
"      ||'-' " +
"      ||"+yearFrom+",'mm-yyyy') " +
"    AND to_date("+CmbTo_Month +
"      ||'-' " +
"      ||"+yearTo+",'mm-yyyy') " +
"    AND ACCOUNT_HEAD_CODE IN " +
"      (SELECT ACCOUNT_HEAD_CODE " +
"      FROM COM_MST_ACCOUNT_HEADS " +
"      WHERE MAJOR_HEAD_CODE='"+cat_id+"' " +
"      ) " +
"    ) a " +
"  GROUP BY a.major " +
"  UNION ALL " +
"  SELECT a.major AS category, " +
"    SUM(a.Debit) AS Debit, " +
"    SUM(a.Credit)AS Credit, " +
"    (SUM(a.Debit)-SUM(a.Credit))AS NET " +
"  FROM " +
"    (SELECT " +
"      (SELECT MAJOR_HEAD_DESC " +
"      FROM COM_MST_MAJOR_HEADS " +
"      WHERE MAJOR_HEAD_CODE IN " +
"        (SELECT MAJOR_HEAD_CODE FROM COM_MST_ACCOUNT_HEADS WHERE MAJOR_HEAD_CODE='"+cat_id+"' " +
"        ) " +
"      )AS major, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'DR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Debit, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'CR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Credit " +
"    FROM fas_payment_transaction " +
"    WHERE to_date((cashbook_month " +
"      ||'-' " +
"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
"      ||'-' " +
"      ||"+yearFrom+",'mm-yyyy') " +
"    AND to_date("+CmbTo_Month +
"      ||'-' " +
"      ||"+yearTo+",'mm-yyyy') " +
"    AND ACCOUNT_HEAD_CODE IN " +
"      (SELECT ACCOUNT_HEAD_CODE " +
"      FROM COM_MST_ACCOUNT_HEADS " +
"      WHERE MAJOR_HEAD_CODE='"+cat_id+"' " +
"      ) " +
"    ) a " +
"  GROUP BY a.major " +
"  UNION ALL " +
"  SELECT a.major AS category, " +
"    SUM(a.Debit) AS Debit, " +
"    SUM(a.Credit)AS Credit, " +
"    (SUM(a.Debit)-SUM(a.Credit))AS NET " +
"  FROM " +
"    (SELECT " +
"      (SELECT MAJOR_HEAD_DESC " +
"      FROM COM_MST_MAJOR_HEADS " +
"      WHERE MAJOR_HEAD_CODE IN " +
"        (SELECT MAJOR_HEAD_CODE FROM COM_MST_ACCOUNT_HEADS WHERE MAJOR_HEAD_CODE='"+cat_id+"' " +
"        ) " +
"      )AS major , " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'DR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Debit, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'CR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Credit " +
"    FROM fas_receipt_transaction " +
"    WHERE to_date((cashbook_month " +
"      ||'-' " +
"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
"      ||'-' " +
"      ||"+yearFrom+",'mm-yyyy') " +
"    AND to_date("+CmbTo_Month +
"      ||'-' " +
"      ||"+yearTo+",'mm-yyyy') " +
"    AND ACCOUNT_HEAD_CODE IN " +
"      (SELECT ACCOUNT_HEAD_CODE " +
"      FROM COM_MST_ACCOUNT_HEADS " +
"      WHERE MAJOR_HEAD_CODE='"+cat_id+"' " +
"      ) " +
"    ) a " +
"  GROUP BY a.major " +
"  )aa " +
"GROUP BY aa.category";
						//System.out.println("... "+cat_id+" ..."+qry_str);
						ps = con.prepareStatement(qry_str);
						rs = ps.executeQuery();

						while (rs.next()) {
						
							/*
							 * xml=xml+"<categry_id>"+rs.getInt("categry_id")+"</categry_id>"
							 * ;xml=xml+"<category_name>"+rs.getString(
							 * "category_name")+"</category_name>";
							 */
							
						//System.out.println(c);
						
							/*xml = xml + "<head_code"+c+">" + rs.getInt("head_code")
									+ "</head_code"+c+">";*/
							xml = xml + "<Debit"+c+">" + rs.getString("Debit")
									+ "</Debit"+c+">";
							xml = xml + "<Credit"+c+">" + rs.getString("Credit")
									+ "</Credit"+c+">";
							xml = xml + "<NET"+c+">" + rs.getInt("NET") + "</NET"+c+">";
							/*xml = xml + "<head_desc"+c+"><![CDATA["
									+ rs.getString("head_desc")
									+ "]]></head_desc"+c+">";*/
						}
				   
					xml +="</count>";
					c++;
				}

			} catch (Exception e) {
				xml = xml + "<flag>Failure</flag>";
				System.out.println(e);
			}

		}if(Command.equalsIgnoreCase("fin_year"))
		{
			xml = "<response><command>FIN_YEAR</command>";
			 
			
			String qry_str="";
			try{
				qry_str="SELECT a.CASHBOOK_YEAR AS YEAR, " +
				"  a.CASHBOOK_MONTH AS MONTH, " +
				"  SUM(Debit)       AS Debit, " +
				"  SUM(Credit)      AS Credit, " +
				"  SUM(Debit-Credit)AS net " +
				"FROM " +
				"  (SELECT CASHBOOK_YEAR, " +
				"    CASHBOOK_MONTH, " +
				"    CURRENT_MONTH_DEBIT  AS Debit, " +
				"    CURRENT_MONTH_CREDIT AS Credit " +
				"  FROM FAS_TRIAL_BALANCE " +
				"  WHERE to_date((cashbook_month " +
				"    ||'-' " +
				"    ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"    ||'-' " +
				"    ||"+yearFrom+",'mm-yyyy') " +
				"  AND to_date("+CmbTo_Month +
				"    ||'-' " +
				"    ||"+yearTo+",'mm-yyyy') " +
				"  ) a " +
				"GROUP BY a.CASHBOOK_YEAR, " +
				"  a.CASHBOOK_MONTH " +
				"ORDER BY a.CASHBOOK_YEAR, " +
				"  a.CASHBOOK_MONTH";
				System.out.println("... ..."+qry_str);
				ps = con.prepareStatement(qry_str);
				rs = ps.executeQuery();

				while (rs.next()) {
					xml = xml + "<YEAR>" + rs.getString("YEAR")
					+ "</YEAR>";
					xml = xml + "<MONTH>" + rs.getString("MONTH")
					+ "</MONTH>";
					xml = xml + "<Debit>" + rs.getString("Debit")
							+ "</Debit>";
					xml = xml + "<Credit>" + rs.getString("Credit")
							+ "</Credit>";
					xml = xml + "<NET>" + rs.getInt("NET") + "</NET>";
					
				}
		
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		else if (Command.equalsIgnoreCase("unitwise"))
				{

			xml = "<response><command>unitwise</command>";
			 cat_id=request.getParameter("unit");
			 int cat_id1=Integer.parseInt(cat_id);
			try {
				String qry_str ="";
				
				
				qry_str= "SELECT aa.ACCOUNTING_UNIT_ID AS unit_id, " +
				"  aa.head_code               AS head_code, " +
				"  AA.Debit                   AS Debit, " +
				"  AA.Credit                  AS Credit, " +
				"  aa. NET                    AS NET, " +
				"  bb.*, " +
				"  (SELECT mst.ACCOUNT_HEAD_DESC " +
				"  FROM COM_MST_ACCOUNT_HEADS mst " +
				"  WHERE mst.ACCOUNT_HEAD_CODE=aa.head_code " +
				"  )AS head_desc, " +
				"  (SELECT MAJOR_HEAD_DESC " +
				"  FROM COM_MST_MAJOR_HEADS " +
				"  WHERE MAJOR_HEAD_CODE IN " +
				"    (SELECT MAJOR_HEAD_CODE " +
				"    FROM COM_MST_ACCOUNT_HEADS " +
				"    WHERE ACCOUNT_HEAD_CODE=aa.head_code " +
				"    ) " +
				"  )AS major " +
				"FROM " +
				"  (SELECT a.ACCOUNTING_UNIT_ID, " +
				"    a.head_code, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNTING_UNIT_ID, " +
				"      ACCOUNT_HEAD_CODE AS head_code, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM FAS_JOURNAL_TRANSACTION " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  WHERE a.ACCOUNTING_UNIT_ID= " +cat_id1+
				"  GROUP BY a.ACCOUNTING_UNIT_ID, " +
				"    a.head_code " +
				"  UNION ALL " +
				"  SELECT a.ACCOUNTING_UNIT_ID, " +
				"    a.head_code, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNTING_UNIT_ID, " +
				"      ACCOUNT_HEAD_CODE AS head_code, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM fas_payment_transaction " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  WHERE a.ACCOUNTING_UNIT_ID= " +cat_id1+
				"  GROUP BY a.ACCOUNTING_UNIT_ID, " +
				"    a.head_code " +
				"  UNION ALL " +
				"  SELECT a.ACCOUNTING_UNIT_ID, " +
				"    a.head_code, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNTING_UNIT_ID, " +
				"      ACCOUNT_HEAD_CODE AS head_code, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM fas_receipt_transaction " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  WHERE a.ACCOUNTING_UNIT_ID= " +cat_id1+
				"  GROUP BY a.ACCOUNTING_UNIT_ID, " +
				"    a.head_code " +
				"  )aa " +
				"LEFT JOIN " +
				"  (SELECT head.ACCOUNTCODE, " +
				"    head.HEADOFACCOUNT, " +
				"    head.annualgroupingid, " +
				"    (SELECT ANNUALGROUPINGNAME " +
				"    FROM ANNUALGROUPING " +
				"    WHERE ANNUALGROUPINGID=head.annualgroupingid " +
				"    )AS annualgrp_name, " +
				"    (SELECT CATEGORYID " +
				"    FROM ANNUALGROUPING " +
				"    WHERE ANNUALGROUPINGID=head.annualgroupingid " +
				"    )AS categry_id, " +
				"    head.MONTHLYGROUPINGID, " +
				"    (SELECT monthlygroupingname " +
				"    FROM MONTHLYGROUPING " +
				"    WHERE monthlygroupingid=head.MONTHLYGROUPINGID " +
				"    )AS mnthlyGid_name, " +
				"    head.SCHEMEID, " +
				"    (SELECT schemename FROM SCHEMES WHERE schemeid=head.SCHEMEID " +
				"    )AS scheme_Name, " +
				"    head.PROJECTID, " +
				"    (SELECT PROJECTNAME FROM PROJECT WHERE PROJECTID=head.PROJECTID " +
				"    )AS project_name, " +
				"    head.PROGRAMID, " +
				"    (SELECT PROGRAMNAME FROM PROGRAM WHERE PROGRAMID=head.programid " +
				"    )AS progarm_name " +
				"  FROM headofaccountcp head " +
				"  )bb " +
				"ON aa.head_code=bb.ACCOUNTCODE " +
				"ORDER BY bb.categry_id" ;
										System.out.println("... "+cat_id+" ..."+qry_str);
										ps = con.prepareStatement(qry_str);
										rs = ps.executeQuery();

										while (rs.next()) {
										
											/*
											 * xml=xml+"<categry_id>"+rs.getInt("categry_id")+"</categry_id>"
											 * ;xml=xml+"<category_name>"+rs.getString(
											 * "category_name")+"</category_name>";
											 */
											
											
										
											xml = xml + "<head_code>" + rs.getInt("head_code")
													+ "</head_code>";
											xml = xml + "<Debit>" + rs.getString("Debit")
													+ "</Debit>";
											xml = xml + "<Credit>" + rs.getString("Credit")
													+ "</Credit>";
											xml = xml + "<NET>" + rs.getInt("NET") + "</NET>";
											xml = xml + "<head_desc><![CDATA["
													+ rs.getString("head_desc")
													+ "]]></head_desc>";
										}
								
			} catch (Exception e) {
				xml = xml + "<flag>Failure</flag>";
				System.out.println(e);
			}
				}
		else if (Command.equalsIgnoreCase("unitAll")) {

			xml = "<response><command>All</command>";
			
			try {
				String qry_str ="";
				/*
				 * "  --, aa.accounting_unit_id, " +
				 * "  --  aa.ACCOUNTING_FOR_OFFICE_ID, " + "  --  aa.head_code";
				 */
				String qry_category = "SELECT aa.ACCOUNTING_UNIT_ID AS unit_id, " +
				"  (SELECT ACCOUNTING_UNIT_NAME " +
				"  FROM FAS_MST_ACCT_UNITS " +
				"  WHERE ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID " +
				"  )AS unit_name " +
				"FROM " +
				"  (SELECT a.ACCOUNTING_UNIT_ID, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNTING_UNIT_ID, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM FAS_JOURNAL_TRANSACTION " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  GROUP BY a.ACCOUNTING_UNIT_ID " +
				"  UNION ALL " +
				"  SELECT a.ACCOUNTING_UNIT_ID, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNTING_UNIT_ID, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM fas_payment_transaction " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  GROUP BY a.ACCOUNTING_UNIT_ID " +
				"  UNION ALL " +
				"  SELECT a.ACCOUNTING_UNIT_ID, " +
				"    SUM(a.Debit) AS Debit, " +
				"    SUM(a.Credit)AS Credit, " +
				"    CASE " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
				"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
				"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
				"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
				"    END AS NET " +
				"  FROM " +
				"    (SELECT ACCOUNTING_UNIT_ID, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'DR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Debit, " +
				"      CASE CR_DR_INDICATOR " +
				"        WHEN 'CR' " +
				"        THEN amount " +
				"        ELSE 0 " +
				"      END AS Credit " +
				"    FROM fas_receipt_transaction " +
				"    WHERE to_date((cashbook_month " +
				"      ||'-' " +
				"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
				"      ||'-' " +
				"      ||"+yearFrom+",'mm-yyyy') " +
				"    AND to_date("+CmbTo_Month +
				"      ||'-' " +
				"      ||"+yearTo+",'mm-yyyy') " +
				"    ) a " +
				"  GROUP BY a.ACCOUNTING_UNIT_ID " +
				"  )aa";
				System.out.println(qry_category);
				ps_category = con.prepareStatement(qry_category);
				rs_category = ps_category.executeQuery();
				while (rs_category.next()) {
					cat_id = rs_category.getString("unit_id");
					sum_cre=0;sum_deb=0;sum_net=0;
					xml += "<count>";
					xml += "<unit_id>"
							+ rs_category.getString("unit_id")
							+ "</unit_id>";
					xml += "<unit_name><![CDATA["
							+ rs_category.getString("unit_name").trim()
							+ "]]></unit_name>";

					
				
qry_str= "SELECT aa.ACCOUNTING_UNIT_ID AS unit_id, " +
"  aa.head_code               AS head_code, " +
"  AA.Debit                   AS Debit, " +
"  AA.Credit                  AS Credit, " +
"  aa. NET                    AS NET, " +
"  bb.*, " +
"  (SELECT mst.ACCOUNT_HEAD_DESC " +
"  FROM COM_MST_ACCOUNT_HEADS mst " +
"  WHERE mst.ACCOUNT_HEAD_CODE=aa.head_code " +
"  )AS head_desc, " +
"  (SELECT MAJOR_HEAD_DESC " +
"  FROM COM_MST_MAJOR_HEADS " +
"  WHERE MAJOR_HEAD_CODE IN " +
"    (SELECT MAJOR_HEAD_CODE " +
"    FROM COM_MST_ACCOUNT_HEADS " +
"    WHERE ACCOUNT_HEAD_CODE=aa.head_code " +
"    ) " +
"  )AS major " +
"FROM " +
"  (SELECT a.ACCOUNTING_UNIT_ID, " +
"    a.head_code, " +
"    SUM(a.Debit) AS Debit, " +
"    SUM(a.Credit)AS Credit, " +
"    CASE " +
"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
"    END AS NET " +
"  FROM " +
"    (SELECT ACCOUNTING_UNIT_ID, " +
"      ACCOUNT_HEAD_CODE AS head_code, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'DR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Debit, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'CR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Credit " +
"    FROM FAS_JOURNAL_TRANSACTION " +
"    WHERE to_date((cashbook_month " +
"      ||'-' " +
"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
"      ||'-' " +
"      ||"+yearFrom+",'mm-yyyy') " +
"    AND to_date("+CmbTo_Month +
"      ||'-' " +
"      ||"+yearTo+",'mm-yyyy') " +
"    ) a " +
"  WHERE a.ACCOUNTING_UNIT_ID= " +cat_id+
"  GROUP BY a.ACCOUNTING_UNIT_ID, " +
"    a.head_code " +
"  UNION ALL " +
"  SELECT a.ACCOUNTING_UNIT_ID, " +
"    a.head_code, " +
"    SUM(a.Debit) AS Debit, " +
"    SUM(a.Credit)AS Credit, " +
"    CASE " +
"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
"    END AS NET " +
"  FROM " +
"    (SELECT ACCOUNTING_UNIT_ID, " +
"      ACCOUNT_HEAD_CODE AS head_code, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'DR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Debit, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'CR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Credit " +
"    FROM fas_payment_transaction " +
"    WHERE to_date((cashbook_month " +
"      ||'-' " +
"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
"      ||'-' " +
"      ||"+yearFrom+",'mm-yyyy') " +
"    AND to_date("+CmbTo_Month +
"      ||'-' " +
"      ||"+yearTo+",'mm-yyyy') " +
"    ) a " +
"  WHERE a.ACCOUNTING_UNIT_ID= " +cat_id+
"  GROUP BY a.ACCOUNTING_UNIT_ID, " +
"    a.head_code " +
"  UNION ALL " +
"  SELECT a.ACCOUNTING_UNIT_ID, " +
"    a.head_code, " +
"    SUM(a.Debit) AS Debit, " +
"    SUM(a.Credit)AS Credit, " +
"    CASE " +
"      WHEN (SUM(a.Debit) -SUM(a.Credit))>=0 " +
"      THEN (SUM(a.Debit) -SUM(a.Credit)) " +
"      WHEN (SUM(a.Debit) -SUM(a.Credit))<0 " +
"      THEN (SUM(a.Credit)-SUM(a.Debit)) " +
"    END AS NET " +
"  FROM " +
"    (SELECT ACCOUNTING_UNIT_ID, " +
"      ACCOUNT_HEAD_CODE AS head_code, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'DR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Debit, " +
"      CASE CR_DR_INDICATOR " +
"        WHEN 'CR' " +
"        THEN amount " +
"        ELSE 0 " +
"      END AS Credit " +
"    FROM fas_receipt_transaction " +
"    WHERE to_date((cashbook_month " +
"      ||'-' " +
"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
"      ||'-' " +
"      ||"+yearFrom+",'mm-yyyy') " +
"    AND to_date("+CmbTo_Month +
"      ||'-' " +
"      ||"+yearTo+",'mm-yyyy') " +
"    ) a " +
"  WHERE a.ACCOUNTING_UNIT_ID= " +cat_id+
"  GROUP BY a.ACCOUNTING_UNIT_ID, " +
"    a.head_code " +
"  )aa " +
"LEFT JOIN " +
"  (SELECT head.ACCOUNTCODE, " +
"    head.HEADOFACCOUNT, " +
"    head.annualgroupingid, " +
"    (SELECT ANNUALGROUPINGNAME " +
"    FROM ANNUALGROUPING " +
"    WHERE ANNUALGROUPINGID=head.annualgroupingid " +
"    )AS annualgrp_name, " +
"    (SELECT CATEGORYID " +
"    FROM ANNUALGROUPING " +
"    WHERE ANNUALGROUPINGID=head.annualgroupingid " +
"    )AS categry_id, " +
"    head.MONTHLYGROUPINGID, " +
"    (SELECT monthlygroupingname " +
"    FROM MONTHLYGROUPING " +
"    WHERE monthlygroupingid=head.MONTHLYGROUPINGID " +
"    )AS mnthlyGid_name, " +
"    head.SCHEMEID, " +
"    (SELECT schemename FROM SCHEMES WHERE schemeid=head.SCHEMEID " +
"    )AS scheme_Name, " +
"    head.PROJECTID, " +
"    (SELECT PROJECTNAME FROM PROJECT WHERE PROJECTID=head.PROJECTID " +
"    )AS project_name, " +
"    head.PROGRAMID, " +
"    (SELECT PROGRAMNAME FROM PROGRAM WHERE PROGRAMID=head.programid " +
"    )AS progarm_name " +
"  FROM headofaccountcp head " +
"  )bb " +
"ON aa.head_code=bb.ACCOUNTCODE " +
"ORDER BY bb.categry_id" ;
						System.out.println("... "+cat_id+" ..."+qry_str);
						ps = con.prepareStatement(qry_str);
						rs = ps.executeQuery();

						while (rs.next()) {
						
							/*
							 * xml=xml+"<categry_id>"+rs.getInt("categry_id")+"</categry_id>"
							 * ;xml=xml+"<category_name>"+rs.getString(
							 * "category_name")+"</category_name>";
							 */
							sum_cre+=rs.getFloat("Credit");
							sum_deb+=rs.getFloat("Debit");
						//	sum_net+=(sum_deb-sum_cre);
							
						
							xml = xml + "<head_code"+c+">" + rs.getInt("head_code")
									+ "</head_code"+c+">";
							xml = xml + "<Debit"+c+">" + rs.getString("Debit")
									+ "</Debit"+c+">";
							xml = xml + "<Credit"+c+">" + rs.getString("Credit")
									+ "</Credit"+c+">";
							xml = xml + "<NET"+c+">" + rs.getInt("NET") + "</NET"+c+">";
							xml = xml + "<head_desc"+c+"><![CDATA["
									+ rs.getString("head_desc")
									+ "]]></head_desc"+c+">";
						}
				
						xml +="</count><sum_deb>"+sum_deb+"</sum_deb>";
						xml+="<sum_cre>"+sum_cre+"</sum_cre>";
						xml+="<sum_net>"+sum_net+"</sum_net>";
					c++;
				}

			} catch (Exception e) {
				xml = xml + "<flag>Failure</flag>";
				System.out.println(e);
			}

		
		} else {
			xml = xml + "<flag>Failure</flag>";

		}

		xml = xml + "</response>";
		out.write(xml);
		out.close();
		System.out.println(xml);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
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
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		String qry="",xml="",sub_Qry="";
		String Command = request.getParameter("Command");
		
	
			String fin_year = request.getParameter("fin_year");
			String[] year = fin_year.split("-");
			int yearFrom = Integer.parseInt(year[0]);
			int yearTo = Integer.parseInt(year[1]);

			int CmbFrom_Month = Integer.parseInt(request
					.getParameter("CmbFrom_Month"));
			int CmbTo_Month = Integer.parseInt(request
					.getParameter("CmbTo_Month"));

			File reportFile = null;
			try {
				if (Command.equalsIgnoreCase("PDF")) {
					sub_Qry="";
				}else
				{
					sub_Qry=   "AND ACCOUNT_HEAD_CODE IN " +
					"(SELECT ACCOUNT_HEAD_CODE " +
					"FROM COM_MST_ACCOUNT_HEADS " +
					"WHERE MAJOR_HEAD_CODE='"+Command+"' " +
					")";
				}
		
				System.out.println("calling servlet..."+sub_Qry);
				reportFile = new File(getServletContext().getRealPath(
						"/org/FAS/FAS1/MIS/jaspers/twadFas/TwadReport_All.jasper"));
				System.out.println("..." + reportFile);
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();
				String monthInWords = "", monthInWords1 = "";

				if (CmbFrom_Month == 1)
					monthInWords = "January";
				else if (CmbFrom_Month == 2)
					monthInWords = "February";
				else if (CmbFrom_Month == 3)
					monthInWords = "March";
				else if (CmbFrom_Month == 4)
					monthInWords = "April";
				else if (CmbFrom_Month == 5)
					monthInWords = "May";
				else if (CmbFrom_Month == 6)
					monthInWords = "June";
				else if (CmbFrom_Month == 7)
					monthInWords = "July";
				else if (CmbFrom_Month == 8)
					monthInWords = "August";
				else if (CmbFrom_Month == 9)
					monthInWords = "September";
				else if (CmbFrom_Month == 10)
					monthInWords = "October";
				else if (CmbFrom_Month == 11)
					monthInWords = "November";
				else if (CmbFrom_Month == 12)
					monthInWords = "December";

				if (CmbTo_Month == 1)
					monthInWords1 = "January";
				else if (CmbTo_Month == 2)
					monthInWords1 = "February";
				else if (CmbTo_Month == 3)
					monthInWords1 = "March";
				else if (CmbTo_Month == 4)
					monthInWords1 = "April";
				else if (CmbTo_Month == 5)
					monthInWords1 = "May";
				else if (CmbTo_Month == 6)
					monthInWords1 = "June";
				else if (CmbTo_Month == 7)
					monthInWords1 = "July";
				else if (CmbTo_Month == 8)
					monthInWords1 = "August";
				else if (CmbTo_Month == 9)
					monthInWords1 = "September";
				else if (CmbTo_Month == 10)
					monthInWords1 = "October";
				else if (CmbTo_Month == 11)
					monthInWords1 = "November";
				else if (CmbTo_Month == 12)
					monthInWords1 = "December";

				map.put("yearFrom", yearFrom);
				map.put("yearTo", yearTo);
				map.put("CmbFrom_Month", CmbFrom_Month);
				map.put("CmbTo_Month", CmbTo_Month);
				map.put("monthInWords", monthInWords);
				map.put("monthInWords1", monthInWords1);
				map.put("sub_Qry", sub_Qry);

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				System.out.println("upto");

				
					System.out.println(Command);
					byte buf[] = JasperExportManager
							.exportReportToPdf(jasperPrint);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					// response.setHeader("content-disposition",
					// "inline;filename=OpenActionItems.pdf");
					// response.setContentType("application/force-download");

					response.setHeader("Content-Disposition",
							"attachment;filename=\"TB_Twad.pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				
			} catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println(connectMsg);
			}
		
	}
}
