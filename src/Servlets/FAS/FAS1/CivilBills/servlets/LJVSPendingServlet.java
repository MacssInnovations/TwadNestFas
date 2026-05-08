package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class LJVSPendingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		String CONTENT_TYPE = "text/xml";
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String strType = "";
		String xml = "<response>";

		/*-------------------------- Session Checking -----------------------------*/

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

		/*---------------------------------------------------------------------------*/
		/*------------------------- Variables Declaration--------------------------- */

		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		/*----------------------------------------------------------------------------*/
		/*--------------------------------- Database Connection------------------------*/

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
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
		}

		/*-----------------------------------------------------------------------------*/

		/* Get Command Parameter */
		try {
			strType = request.getParameter("Command");
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* Variables Declaration */
		int txtCB_Year = 0;
		int txtCB_Month = 0;
		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		Date txtFrom_date = null;
		Date txtTo_date = null;
		Calendar c;
		String sql = "";

		String cmbStatus = "";

		/* Accounting Unit ID */

		if (strType.equalsIgnoreCase("searchByMonth"))

		{
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

			/* Accounting For Office ID */
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbOffice_code " + cmbOffice_code);

			/* Get Cashbook Month and Year */
			txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));

			System.out.println("year..." + txtCB_Year);
			System.out.println("Month..." + txtCB_Month);

			/* Get Voucher Status */
			cmbStatus = request.getParameter("cmbStatus");
			System.out.println("cmbStatus.." + cmbStatus);

			xml = "<response><command>searchByMonth</command>";
			
			
			sql = "SELECT Jm.Accounting_Unit_Id AS Unit_Id , " +
					"  jm.CASHBOOK_YEAR, " +
					"  jm.CASHBOOK_MONTH, " +
					"  TO_CHAR(JM.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, " +
					"  JT.ACCOUNT_HEAD_CODE , " +
					"  JM.VOUCHER_NO AS VOC_NO , " +
					"  JM.REMARKS, " +
					"  JT.AMOUNT AS AMOUNT1 , " +
					"  JT.CR_DR_INDICATOR, " +
					"  Jm.Journal_Type_Code, " +
					"  (SELECT JOURNAL_TYPE_DESC " +
					"  FROM Fas_Mst_Journal_Type " +
					"  WHERE Journal_Type_Code = jM.Journal_Type_Code " +
					"  )                    AS JOURNAL_TYPE_DESC, " +
					"  Pt.Amount            AS amount2, " +
					"  JT.AMOUNT- Pt.Amount AS pendingAmt " +
					"FROM FAS_JOURNAL_MASTER JM " +
					"INNER JOIN FAS_JOURNAL_TRANSACTION JT " +
					"ON JM.ACCOUNTING_UNIT_ID       =JT.ACCOUNTING_UNIT_ID " +
					"AND JM.ACCOUNTING_FOR_OFFICE_ID=JT.ACCOUNTING_FOR_OFFICE_ID " +
					"AND JM.CASHBOOK_YEAR           =JT.CASHBOOK_YEAR " +
					"AND JM.CASHBOOK_MONTH          =JT.CASHBOOK_MONTH " +
					"AND JM.VOUCHER_NO              =JT.VOUCHER_NO " +
					"INNER JOIN FAS_PAYMENT_MASTER PM " +
					"ON JM.ACCOUNTING_UNIT_ID       =PM.ACCOUNTING_UNIT_ID " +
					"AND JM.ACCOUNTING_FOR_OFFICE_ID=PM.ACCOUNTING_FOR_OFFICE_ID " +
					"INNER JOIN FAS_PAYMENT_TRANSACTION PT " +
					"ON pM.ACCOUNTING_UNIT_ID       =Pt.ACCOUNTING_UNIT_ID " +
					"AND PM.ACCOUNTING_FOR_OFFICE_ID=PT.ACCOUNTING_FOR_OFFICE_ID " +
					"AND PM.CASHBOOK_YEAR           =PT.CASHBOOK_YEAR " +
					"AND PM.CASHBOOK_MONTH          =pT.CASHBOOK_MONTH " +
					"AND PM.VOUCHER_NO              =PT.VOUCHER_NO " +
					"AND JT.CB_REF_DATE             =PM.PAYMENT_DATE " +
					"AND JT.CB_REF_NO               =PM.VOUCHER_NO " +
					"AND JT.CHEQUE_DD_NO            =PT.CHEQUE_DD_NO " +
					"AND PT.PAYABLE_VOUCHER_NO      =JT.VOUCHER_NO " +
					"AND PT.PAYABLE_VOUCHER_DATE    =JM.VOUCHER_DATE " +
					"AND PM.PAYMENT_STATUS          ='L' " +
					"AND (JT.ACCOUNT_HEAD_CODE      =550102 " +
					"OR JT.ACCOUNT_HEAD_CODE        =550602) " +
					"AND JT.CB_REF_DATE            IS NOT NULL " +
					"AND JT.CB_REF_NO              <> 0 " +
					"AND Jm.Accounting_Unit_Id      =? " +
					"AND Jm.Accounting_For_Office_Id=? " +
					"AND Jm.Cashbook_Year           =? " +
					"AND jm.cashbook_month          =? " +
					"AND JM.JOURNAL_STATUS          ='L' " +
					"AND JM.CREATED_BY_MODULE       ='LJV' " +
					"ORDER BY JM.VOUCHER_DATE, " +
					"  JM.VOUCHER_NO";

			System.out.println("sql:::" + sql);
			try {
				int count = 0;
				ps = con.prepareStatement(sql);
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);

				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);

				rs = ps.executeQuery();
				System.out.println(rs.next());
				int cno = 0;
				System.out.println(rs);
				while (rs.next()) {

					xml = xml + "<VOUCHER_NO>" + rs.getInt("VOC_NO")
							+ "</VOUCHER_NO>";
					xml = xml + "<VOUCHER_DATE>" + rs.getString("VOUCHER_DATE")
							+ "</VOUCHER_DATE>";
					xml = xml + "<VOUCHER_TYPE>"
							+ rs.getString("journal_type_desc")
							+ "</VOUCHER_TYPE>";
					xml = xml + "<PARTICULARS>" + rs.getString("REMARKS")
							+ "</PARTICULARS>";
					xml = xml + "<TOTAL_AMOUNT>" + rs.getString("AMOUNT1")
							+ "</TOTAL_AMOUNT>";
					xml = xml + "<pending_AMOUNT>" + rs.getString("pendingAmt")
							+ "</pending_AMOUNT>";
					xml = xml + "<amount2>" + rs.getString("amount2")
							+ "</amount2>";

					cno++;
				}
				if (cno > 0) {
					xml = xml + "<flag>success</flag>";
				} else {
					xml = xml + "<flag>failure</flag>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.println(xml);
			System.out.println(xml);
		}

		else if (strType.equalsIgnoreCase("searchByDate")) {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			cmbStatus = request.getParameter("cmbStatus");
			// String txtFrom_date1=request.getParameter("txtFrom_date");
			// String txtTo_date1=request.getParameter("txtTo_date");
			xml = "<response><command>searchByDate</command>";

			System.out.println("here "
					+ strType.equalsIgnoreCase("searchByDate"));

			// Get From Date
			String[] sd = request.getParameter("txtFrom_date").split("/");
			System.out.println("sd " + sd);
			c = new GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			System.out.println("c " + c);
			java.util.Date d = c.getTime();
			txtFrom_date = new Date(d.getTime());
			System.out.println("from_date " + txtFrom_date);

			// Get To Date
			sd = request.getParameter("txtTo_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			d = c.getTime();
			txtTo_date = new Date(d.getTime());
			System.out.println("txtTo_date " + txtTo_date);
			
			
			

			sql = "SELECT Jm.Accounting_Unit_Id AS Unit_Id , "
					+ "  jm.CASHBOOK_YEAR, "
					+ "  jm.CASHBOOK_MONTH, "
					+ "  TO_CHAR(JM.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "
					+ "  JT.ACCOUNT_HEAD_CODE , "
					+ "  JM.VOUCHER_NO AS VOC_NO , "
					+ "  JM.REMARKS, "
					+ "  JT.AMOUNT AS AMOUNT1 , "
					+ "  JT.CR_DR_INDICATOR, "
					+ "  Jm.Journal_Type_Code, "
					+ "  (SELECT JOURNAL_TYPE_DESC "
					+ "  FROM Fas_Mst_Journal_Type "
					+ "  WHERE Journal_Type_Code = jM.Journal_Type_Code "
					+ "  )AS journal_type_desc, "
					+ "  Pt.Amount as amount2 , "
					+ "  JT.AMOUNT- Pt.Amount AS pendingAmt "
					+ "FROM FAS_JOURNAL_MASTER JM "
					+ "INNER JOIN FAS_JOURNAL_TRANSACTION JT "
					+ "ON JM.ACCOUNTING_UNIT_ID       =JT.ACCOUNTING_UNIT_ID "
					+ "AND JM.ACCOUNTING_FOR_OFFICE_ID=JT.ACCOUNTING_FOR_OFFICE_ID "
					+ "AND JM.CASHBOOK_YEAR           =JT.CASHBOOK_YEAR "
					+ "AND JM.CASHBOOK_MONTH          =JT.CASHBOOK_MONTH "
					+ "AND JM.VOUCHER_NO              =JT.VOUCHER_NO "
					+ "INNER JOIN FAS_PAYMENT_MASTER PM "
					+ "ON JM.ACCOUNTING_UNIT_ID       =PM.ACCOUNTING_UNIT_ID "
					+ "AND JM.ACCOUNTING_FOR_OFFICE_ID=PM.ACCOUNTING_FOR_OFFICE_ID "
					+ "INNER JOIN FAS_PAYMENT_TRANSACTION PT "
					+ "ON pM.ACCOUNTING_UNIT_ID       =Pt.ACCOUNTING_UNIT_ID "
					+ "AND PM.ACCOUNTING_FOR_OFFICE_ID=PT.ACCOUNTING_FOR_OFFICE_ID "
					+ "AND PM.CASHBOOK_YEAR           =PT.CASHBOOK_YEAR "
					+ "AND PM.CASHBOOK_MONTH          =pT.CASHBOOK_MONTH "
					+ "AND PM.VOUCHER_NO              =PT.VOUCHER_NO "
					+ "AND JT.CB_REF_DATE             =PM.PAYMENT_DATE "
					+ "AND JT.CB_REF_NO               =PM.VOUCHER_NO "
					+ "AND JT.CHEQUE_DD_NO            =PT.CHEQUE_DD_NO "
					+ "AND PT.PAYABLE_VOUCHER_NO      =JT.VOUCHER_NO "
					+ "AND PT.PAYABLE_VOUCHER_DATE    =JM.VOUCHER_DATE "
					+ "AND PM.PAYMENT_STATUS          ='L' "
					+ "AND (JT.ACCOUNT_HEAD_CODE      =550102 "
					+ "OR JT.ACCOUNT_HEAD_CODE        =550602) "
					+ "AND JT.CB_REF_DATE            IS NOT NULL "
					+ "AND JT.CB_REF_NO              <> 0 "
					+ "AND Jm.Accounting_Unit_Id      ="
					+ cmbAcc_UnitCode
					+ " "
					+
					// "AND Jm.Accounting_For_Office_Id="++" " +
					"AND jM.Voucher_Date BETWEEN to_date('" + txtFrom_date
					+ "','YYYY-MM-DD') AND to_date('" + txtTo_date
					+ "','YYYY-MM-DD') "
					+ "AND JM.JOURNAL_STATUS          ='L' "
					+ "AND JM.CREATED_BY_MODULE       ='LJV' "
					+ "ORDER BY JM.VOUCHER_DATE, " + "  JM.VOUCHER_NO";
			System.out.println("sql:::" + sql);
			try {
				int count = 0;
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				System.out.println(rs.next());
				int cno = 0;
				System.out.println(rs);
				while (rs.next()) {

					xml = xml + "<VOUCHER_NO>" + rs.getInt("VOC_NO")
							+ "</VOUCHER_NO>";
					xml = xml + "<VOUCHER_DATE>" + rs.getString("VOUCHER_DATE")
							+ "</VOUCHER_DATE>";
					xml = xml + "<VOUCHER_TYPE>"
							+ rs.getString("journal_type_desc")
							+ "</VOUCHER_TYPE>";
					xml = xml + "<PARTICULARS>" + rs.getString("REMARKS")
							+ "</PARTICULARS>";
					xml = xml + "<TOTAL_AMOUNT>" + rs.getString("AMOUNT1")
							+ "</TOTAL_AMOUNT>";
					xml = xml + "<amount2>" + rs.getString("amount2")
							+ "</amount2>";
					
					xml = xml + "<pending_AMOUNT>" + rs.getString("pendingAmt")
							+ "</pending_AMOUNT>";

					cno++;
				}
				if (cno > 0) {
					xml = xml + "<flag>success</flag>";
				} else {
					xml = xml + "<flag>failure</flag>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.println(xml);
			System.out.println(xml);
		}

		else if (strType.equalsIgnoreCase("add")) {
			// xml=xml+"<response><command>"+strType+"</command>";
			xml = "<response><command>add</command>";

			String VOCHER_NO[] = request.getParameterValues("VOUCHER_NO");
			System.out.println("vocherno" + VOCHER_NO.length);

			String VOCHER_DATE[] = request.getParameterValues("VOUCHER_DATE");
			// System.out.println(request.getParameterValues("VOUCHER_DATE"));
			String VOCHER_TYPE[] = request.getParameterValues("VOUCHER_TYPE");
			String REMARKS[] = request.getParameterValues("PARTICULARS");
			String am[] = request.getParameterValues("TOTAL_AMOUNT");
			String am1[] = request.getParameterValues("TOTAL_AMOUNT1");
			String NOT_CLEARED[] = request.getParameterValues("cleared");
			String PARTIALLY_CLEARED[] = request
					.getParameterValues("partlycleared");

			String PENDING_AMOUNT[] = request
					.getParameterValues("pending_AMOUNT");
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

			/* Accounting For Office ID */
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbOffice_code " + cmbOffice_code);

			/* Get Cashbook Month and Year */
			txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
			String sql1 = "";
			try {
				int VOCHER_NO_value = 0;
				Date VOCHER_DATE1 = null;
				int i = 0;
				int amount = 0;
				int amount1 = 0;
				int amount2 = 0;
				int i1 = 0;
				for (int k = 0; k < VOCHER_NO.length; k++) {
				amount1 = Integer.parseInt(PENDING_AMOUNT[k]);
				// System.out.println(" integer : " + amount1[i]);
				String[] sd = VOCHER_DATE[k].split("/");
				c = new GregorianCalendar(Integer.parseInt(sd[2]),
						Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
				java.util.Date d = c.getTime();
				VOCHER_DATE1 = new Date(d.getTime());
				System.out.println("from_date " + VOCHER_DATE1);
				amount = Integer.parseInt(am[k]);
				System.out.println("amount"+amount);
				amount2 = Integer.parseInt(am1[k]);
				System.out.println("amount1"+amount2);
				
					try {
						VOCHER_NO_value = Integer.parseInt(VOCHER_NO[k]);
					} catch (Exception e) {
					}
					
					if (Integer.parseInt(PENDING_AMOUNT[k]) > 0)
						sql1 = "insert into LJV_PENDING_PAYMENT values(?,?,?,?,?,?,?,?,?,'N','Y',?)";
					else
						sql1 = "insert into LJV_PENDING_PAYMENT values(?,?,?,?,?,?,?,?,?,'Y','N',?,?)";
					ps = con.prepareStatement(sql1);
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setInt(5, VOCHER_NO_value);
					ps.setDate(6, VOCHER_DATE1);
					ps.setString(7, VOCHER_TYPE[k]);
					ps.setString(8, REMARKS[k]);
					ps.setInt(9, amount);
					ps.setInt(10, amount1);
					ps.setInt(11, amount2);
					i1 = ps.executeUpdate();
				}
				// System.out.println(i1);
				ps.close();
				if(i1==0)
				{  con.rollback();
				sendMessage(response, "Insertion Failed...", "OK");}
				else{
					con.commit();
					sendMessage(response, "Successfully Inserted...", "OK");}
			}catch(Exception e)
			{	sendMessage(response, "Already data available...", "OK");
				//con.rollback();
				e.printStackTrace();
			}
		}

	}
	public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException {
		
		
		try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String opt = "";
        Connection connection = null;
        response.setContentType(CONTENT_TYPE);
        try {


            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile = null;

        try {
            System.out.println("calling LJV Pending for Payment Report");
            int txtCB_Year =
                    Integer.parseInt(request.getParameter("txtCB_Year"));
                int txtCB_Month =
                    Integer.parseInt(request.getParameter("txtCB_Month"));
                String fromdate = request.getParameter("txtfromdate");
                String todate = request.getParameter("txttodate");
                String rtype = request.getParameter("txtoption");
                String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
                String cmbOffice_code = request.getParameter("cmbOffice_code");
                int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
                int accountingoffice = Integer.parseInt(cmbOffice_code);
                java.util.Date d = null;
                java.util.Date d1 = null;
                System.out.println("here i come");
                //Date Conversion for Date
                if (!fromdate.equalsIgnoreCase("") &&
                    !todate.equalsIgnoreCase("")) {
                    java.sql.Date dateOfAttachment = null;
                    System.out.println("before converting date");
                    String dateString = fromdate;
                    SimpleDateFormat dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy");

                    d = dateFormat.parse(fromdate.trim());
                    System.out.println("util date is:" + d);
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString = dateFormat.format(d);
                    dateOfAttachment = java.sql.Date.valueOf(dateString);

                    java.sql.Date dateto = null;
                    System.out.println("before converting date");
                    String dateString1 = todate;
                    SimpleDateFormat dateFormat1 =
                        new SimpleDateFormat("dd/MM/yyyy");

                    d1 = dateFormat1.parse(todate.trim());
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    dateString1 = dateFormat1.format(d1);
                    dateto = java.sql.Date.valueOf(dateString1);

                    System.out.println("fromdate" + dateOfAttachment);
                    System.out.println("todate" + dateto);
                    
                    System.out.println("fromdate  :::: dddd" + d);
                    System.out.println("todate ::::: d1d1d1" + d1);
                }
                String monthInWords = "";
                if (txtCB_Month == 1)
                    monthInWords = "January";
                else if (txtCB_Month == 2)
                    monthInWords = "February";
                else if (txtCB_Month == 3)
                    monthInWords = "March";
                else if (txtCB_Month == 4)
                    monthInWords = "April";
                else if (txtCB_Month == 5)
                    monthInWords = "May";
                else if (txtCB_Month == 6)
                    monthInWords = "June";
                else if (txtCB_Month == 7)
                    monthInWords = "July";
                else if (txtCB_Month == 8)
                    monthInWords = "August";
                else if (txtCB_Month == 9)
                    monthInWords = "September";
                else if (txtCB_Month == 10)
                    monthInWords = "October";
                else if (txtCB_Month == 11)
                    monthInWords = "November";
                else if (txtCB_Month == 12)
                    monthInWords = "December";


                System.out.println("fromdate" + fromdate);
                System.out.println("todate" + todate);
                if (fromdate.equalsIgnoreCase("") ||
                    todate.equalsIgnoreCase("")) // if any date is empty , then list based on cash book month
                {
                	reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/LJV_Pending_for_Payment_Rpt.jasper"));
                    System.out.println("/org/FAS/FAS1/Reports/JournalSystem/jasper/LJV_Pending_for_Payment_Rpt.jasper");
                }else // if date is not empty , then list based on date also
                {
               	 reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/LJV_Pending_for_Payment_Rpt_Period.jasper"));
               	System.out.println("/org/FAS/FAS1/Reports/JournalSystem/jasper/LJV_Pending_for_Payment_Rpt_Period.jasper");
               }
                
                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = new HashMap();

                String acc_unit_name = "";
                String acc_unit_office_name = "";
                PreparedStatement ps =
                    connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                ps.setInt(1, accountingoffice);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    acc_unit_name = rs.getString("ACCOUNTING_UNIT_NAME");
                ps.close();
                rs.close();

                ps =
      connection.prepareStatement("select OFFICE_NAME from com_mst_offices where OFFICE_ID=?");
                ps.setInt(1, accountingoffice);
                rs = ps.executeQuery();
                if (rs.next())
                    acc_unit_office_name = rs.getString("OFFICE_NAME");
                ps.close();
                rs.close();

                //map.put("accountingunitName", acc_unit_name);
               // map.put("accountofficeName", acc_unit_office_name);
                map.put("accountingunitid", accountingunit);
                map.put("accountofficeid", accountingoffice);
                map.put("txtCB_Year", txtCB_Year);
                map.put("txtCB_Month", txtCB_Month);
                map.put("txtfrom", fromdate);
                map.put("txtto", todate);
                map.put("monthInWords", monthInWords);

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map, connection);

                System.out.println("opt::******" + map);

                if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"LJV_Pending_for_Payment.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
                    //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
                    //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
                    //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                          false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
                } else if (rtype.equalsIgnoreCase("PDF")) {
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"LJV_Pending_for_Payment.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (rtype.equalsIgnoreCase("EXCEL")) {

                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"LJV_Pending_for_Payment.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                             jasperPrint);

                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                             xlsReport);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                             Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                             Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                             Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                             Boolean.TRUE);
                    exporterXLS.exportReport();
                    byte[] bytes;
                    bytes = xlsReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                } else if (rtype.equalsIgnoreCase("TXT")) {

                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"LJV_Pending_for_Payment.txt\"");

                    JRTextExporter exporter = new JRTextExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          txtReport);
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                          new Integer(200));
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                          new Integer(50));
                    exporter.exportReport();

                    byte[] bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                }


            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage() + " " +
                    ex.getLocalizedMessage();
                System.out.println(connectMsg);
            }
	}
	
	private void sendMessage(HttpServletResponse response,String msg,String bType)
	{
		try
		{
			String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
			response.sendRedirect(url);
		}
		catch(Exception e)
		{
			System.out.println("error in messenger"+e);
		}
	}
}