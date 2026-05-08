package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/**
 * Servlet implementation class TB_Freeze_Status_Rpt_Yearwise
 */
public class TB_Freeze_Status_Rpt_Yearwise extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE =
	        "text/html; charset=windows-1252";     
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TB_Freeze_Status_Rpt_Yearwise() {
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
		
		System.out.println("Welcome to TB_Freeze_Status_Rpt Servlet!............");
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
       
        String fin_year=request.getParameter("fin_year");
        System.out.println("fin_year===>"+fin_year);
        String rtype = request.getParameter("txtoption");
        System.out.println("rtype===>"+rtype);

        String Uname="",offname="";
        int offid=0;
        
        
        String qry="select distinct x.*,y.MAR_SUP,y.cnt_MAR_SUP,y.NIL_TB_STATUS_Sup,y.cnt_NIL_TB_STATUS_Sup from( SELECT ACCOUNTING_UNIT_ID,\n" + 
        		"  UNAME,\n" + 
        		"  APR,  MAY,  JUN,  JUL,  AUG,  SEP,  OCT,  NOV,  DEC,  JAN,FEB, MAR ,NIL_TB_STATUS_Reg,\n" + 
        		"  COUNT(APR) AS CNT_APR,\n" + 
        		"  COUNT(MAY) AS CNT_MAY,\n" + 
        		"  COUNT(JUN) AS CNT_JUN,\n" + 
        		"  COUNT(JUL) AS CNT_JUL,\n" + 
        		"  COUNT(AUG) AS CNT_AUG,\n" + 
        		"  COUNT(SEP) AS CNT_SEP,\n" + 
        		"  COUNT(OCT) AS CNT_OCT,\n" + 
        		"  COUNT(NOV) AS CNT_NOV,\n" + 
        		"  COUNT(DEC) as CNT_DEC,\n" +
        		"  COUNT(JAN) as CNT_JAN,\n" +
        		"  COUNT(FEB) as CNT_FEB,\n" +
        		"  COUNT(MAR) as CNT_MAR,\n" +
        		"  COUNT(NIL_TB_STATUS_Reg) as CNT_NIL_TB_STATUS_Reg " +
        		"  FROM\n" + 
        		"  (SELECT b.ACCOUNTING_UNIT_ID,\n" + 
        		"  b.ACCOUNTING_UNIT_NAME as uname,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=4\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS APR,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=5\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS MAY,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=6\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS JUN,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=7\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS Jul,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=8\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS AUG,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN CASHBOOK_MONTH=9\n" + 
        		"    THEN TB_STATUS\n" + 
        		"  END )AS SEP,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=10\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS OCT,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=11\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS NOV,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=12\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS DEC,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=1\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS JAN,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=2\n" + 
        		"    THEN a.TB_STATUS\n" + 
        		"  END )AS FEB,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=3\n" + 
        		"    THEN A.TB_STATUS\n" + 
        		"  END )AS MAR,\n" + 
        		"  a.NIL_TB_STATUS as NIL_TB_STATUS_Reg\n " +
        		" \n" + 
        		"FROM\n" + 
        		//commanded on 30-11-19 for closed office updated only in FAS_MST_ACCT_UNIT_OFFICES
        		
//        		"  (SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS ACC)B\n" + 
//        		" (SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME " +
//        	    " FROM FAS_MST_ACCT_UNITS ACC " +
//        	    " WHERE ACC.STATUS IS NOT NULL " +
//        	    " and EXTRACT(YEAR FROM (ACC.DATE_OF_CLOSURE)) BETWEEN "+fin_year.split("-")[0]+" AND "+fin_year.split("-")[1] +
//        	    
//        	    " UNION ALL " +
//
//        	    " SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME " +
//        	    " FROM FAS_MST_ACCT_UNITS ACC " +
//        	    " WHERE ACC.STATUS is null )B " +        	

        		"(SELECT offc.ACCOUNTING_UNIT_ID,\n" + 
        		"        acc.accounting_unit_name\n" + 
        		"      from FAS_MST_ACCT_UNIT_OFFICES offc,\n" + 
        		"      fas_mst_acct_units acc      \n" + 
        		"      where offc.CLOSED is not null\n" + 
        		"      and offc.accounting_unit_id=acc.accounting_unit_id\n" + 
    //    		"      and extract(year from (offc.date_of_closure)) BETWEEN "+fin_year.split("-")[0]+" AND "+fin_year.split("-")[1] +
        	    "   and extract(year from (offc.date_of_closure)) BETWEEN (split_part('"+fin_year+"','-',1))::numeric AND (split_part('"+fin_year+"','-',2))::numeric"+
            	
        		"      UNION ALL\n" + 
        		"      SELECT offc.ACCOUNTING_UNIT_ID,\n" + 
        		"        acc.accounting_unit_name\n" + 
        		"      from FAS_MST_ACCT_UNIT_OFFICES offc,\n" + 
        		"      fas_mst_acct_units acc      \n" + 
        		"      where offc.CLOSED is null\n" + 
        		"      and offc.accounting_unit_id=acc.accounting_unit_id\n" + 
        		"      )B " +       		
        		"  LEFT OUTER JOIN \n" + 
        		"  (SELECT ACCOUNTING_UNIT_ID,\n" + 
        		"    CASHBOOK_YEAR,\n" + 
        		"    CASHBOOK_MONTH,\n" + 
        		"    TO_CHAR(to_date(cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
        		"    TB_STATUS,NIL_TB_STATUS\n" + 
        		"    \n" + 
        		"  FROM FAS_TRIAL_BALANCE_STATUS \n" + 
        		"  WHERE to_date(cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
        		"    ||'-'\n" + 
    //    		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
        		 " ||split_part('"+fin_year+"','-',1),'mm-yyyy')\n"+  
        		"  AND to_date(3\n" + 
        		"    ||'-'\n" + 
    //    		"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
        		 " ||split_part('"+fin_year+"','-',2),'mm-yyyy')\n"+  
        		"  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        		"    CASHBOOK_YEAR,\n" + 
        		"    CASHBOOK_MONTH,\n" + 
        		"    TO_CHAR(to_date(cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon'),\n" + 
        		"    TB_STATUS,NIL_TB_STATUS\n" + 
        		"  )A\n" + 
        		"  ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
        		"  GROUP BY B.ACCOUNTING_UNIT_ID, B.ACCOUNTING_UNIT_NAME,A.NIL_TB_STATUS\n" + 
        		"ORDER BY B.ACCOUNTING_UNIT_ID) as opt1 GROUP BY ACCOUNTING_UNIT_ID, UNAME, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC, JAN, FEB, MAR,NIL_TB_STATUS_Reg)X\n" + 
        		"FULL JOIN\n" + 
        		"(select ACCOUNTING_UNIT_ID,UNAME,MAR_SUP,NIL_TB_STATUS_Sup,count(MAR_SUP) as cnt_MAR_SUP,count(NIL_TB_STATUS_Sup) as cnt_NIL_TB_STATUS_Sup from (SELECT b.ACCOUNTING_UNIT_ID,\n" + 
        		"  B.ACCOUNTING_UNIT_NAME AS UNAME,\n" + 
        		"  MAX(\n" + 
        		"  CASE\n" + 
        		"    WHEN A.CASHBOOK_MONTH=3\n" + 
        		"    THEN A.TB_STATUS\n" + 
        		"  END )AS MAR_SUP,\n" + 
        		" NIL_TB_STATUS AS NIL_TB_STATUS_Sup\n " +
        		"FROM\n" + 
//        		"  (SELECT ACC.ACCOUNTING_UNIT_ID,\n" + 
//        		"    ACC.ACCOUNTING_UNIT_NAME\n" + 
//        		"  FROM FAS_MST_ACCT_UNITS ACC\n" + 
//        		"  )B\n" +

//commanded on 30-11-19 for closed office updated only in FAS_MST_ACCT_UNIT_OFFICES

//				" (SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME " +
//				" FROM FAS_MST_ACCT_UNITS ACC " +
//				" WHERE ACC.STATUS IS NOT NULL " +
//				" and EXTRACT(YEAR FROM (ACC.DATE_OF_CLOSURE)) BETWEEN "+fin_year.split("-")[0]+" AND "+fin_year.split("-")[1] +
//				" UNION ALL " +
//				" SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME " +
//				" FROM FAS_MST_ACCT_UNITS ACC " +
//				" WHERE ACC.STATUS is null )B " +  

				"(SELECT offc.ACCOUNTING_UNIT_ID,\n" + 
				"        acc.accounting_unit_name\n" + 
				"      from FAS_MST_ACCT_UNIT_OFFICES offc,\n" + 
				"      fas_mst_acct_units acc      \n" + 
				"      where offc.CLOSED is not null\n" + 
				"      and offc.accounting_unit_id=acc.accounting_unit_id\n" + 
//				"      and extract(year from (offc.date_of_closure)) BETWEEN "+fin_year.split("-")[0]+" AND "+fin_year.split("-")[1] +
			    "   and extract(year from (offc.date_of_closure)) BETWEEN (split_part('"+fin_year+"','-',1))::numeric AND (split_part('"+fin_year+"','-',2))::numeric"+
		           
				"      UNION ALL\n" + 
				"      SELECT offc.ACCOUNTING_UNIT_ID,\n" + 
				"        acc.accounting_unit_name\n" + 
				"      from FAS_MST_ACCT_UNIT_OFFICES offc,\n" + 
				"      fas_mst_acct_units acc      \n" + 
				"      where offc.CLOSED is null\n" + 
				"      and offc.accounting_unit_id=acc.accounting_unit_id\n" + 
				"      )B " +     
        		
        		"LEFT OUTER JOIN\n" + 
        		"  (SELECT ACCOUNTING_UNIT_ID,\n" + 
        		"    CASHBOOK_YEAR,\n" + 
        		"    CASHBOOK_MONTH,\n" + 
        		"    TO_CHAR(to_date(cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
        		"    TB_STATUS,coalesce(nullif(NIL_TB_STATUS,'')) as NIL_TB_STATUS,\n" + 
        		"    coalesce(SUPPLEMENT_NO,0,'0',SUPPLEMENT_NO) AS SUPPLEMENT_NO\n" + 
        		"  FROM FAS_TRIAL_BALANCE_STATUS_SJV\n" + 
        		"  WHERE to_date(cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
        		"    ||'-'\n" + 
       // 		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
        		 " ||split_part('"+fin_year+"','-',1),'mm-yyyy')\n"+  
        		"  AND to_date(3\n" + 
        		"    ||'-'\n" + 
       // 		"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
        		 " ||split_part('"+fin_year+"','-',2),'mm-yyyy')\n"+  
        		"  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        		"    CASHBOOK_YEAR,\n" + 
        		"    CASHBOOK_MONTH,\n" + 
        		"    TO_CHAR(to_date(cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon'),\n" + 
        		"    TB_STATUS,NIL_TB_STATUS,\n" + 
        		"    SUPPLEMENT_NO\n" + 
        		"  )A\n" + 
        		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
        		"GROUP BY B.ACCOUNTING_UNIT_ID, B.ACCOUNTING_UNIT_NAME, A.SUPPLEMENT_NO,NIL_TB_STATUS\n" + 
        		"ORDER BY B.ACCOUNTING_UNIT_ID) as opt2 GROUP BY ACCOUNTING_UNIT_ID, UNAME, MAR_SUP, NIL_TB_STATUS_Sup)Y\n" + 
        		"on x.ACCOUNTING_UNIT_ID=y.ACCOUNTING_UNIT_ID order by x.ACCOUNTING_UNIT_ID";
        
        
        
        //Commended on 24-07-2019 for column count 
        
//        String qry="select x.*,y.MAR_SUP,y.NIL_TB_STATUS_Sup from(SELECT b.ACCOUNTING_UNIT_ID,\n" + 
//        		"b.ACCOUNTING_UNIT_NAME as uname,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=4\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS APR,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=5\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS MAY,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=6\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS JUN,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=7\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS Jul,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=8\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS AUG,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN CASHBOOK_MONTH=9\n" + 
//        		"    THEN TB_STATUS\n" + 
//        		"  END )AS SEP,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=10\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS OCT,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=11\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS NOV,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=12\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS DEC,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=1\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS JAN,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=2\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS FEB,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=3\n" + 
//        		"    THEN A.TB_STATUS\n" + 
//        		"  END )AS MAR,\n" + 
//        		"  a.NIL_TB_STATUS as NIL_TB_STATUS_Reg\n " +
//        		" \n" + 
//        		"FROM\n" + 
//        		//"  (SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS ACC)B\n" + 
//        		" (SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME " +
//        	    " FROM FAS_MST_ACCT_UNITS ACC " +
//        	    " WHERE ACC.STATUS IS NOT NULL " +
//        	    " and EXTRACT(YEAR FROM (ACC.DATE_OF_CLOSURE)) BETWEEN "+fin_year.split("-")[0]+" AND "+fin_year.split("-")[1] +
//        	    
//        	    " UNION ALL " +
//
//        	    " SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME " +
//        	    " FROM FAS_MST_ACCT_UNITS ACC " +
//        	    " WHERE ACC.STATUS is null )B " +        		
//        		"  LEFT OUTER JOIN \n" + 
//        		"  (SELECT ACCOUNTING_UNIT_ID,\n" + 
//        		"    CASHBOOK_YEAR,\n" + 
//        		"    CASHBOOK_MONTH,\n" + 
//        		"    TO_CHAR(to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
//        		"    TB_STATUS,NIL_TB_STATUS\n" + 
//        		"    \n" + 
//        		"  FROM FAS_TRIAL_BALANCE_STATUS \n" + 
//        		"  WHERE to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
//        		"    ||'-'\n" + 
//        		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
//        		"  AND to_date(3\n" + 
//        		"    ||'-'\n" + 
//        		"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
//        		"  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
//        		"    CASHBOOK_YEAR,\n" + 
//        		"    CASHBOOK_MONTH,\n" + 
//        		"    TO_CHAR(to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon'),\n" + 
//        		"    TB_STATUS,NIL_TB_STATUS\n" + 
//        		"  )A\n" + 
//        		"  ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
//        		"  GROUP BY B.ACCOUNTING_UNIT_ID, B.ACCOUNTING_UNIT_NAME,A.NIL_TB_STATUS\n" + 
//        		"ORDER BY B.ACCOUNTING_UNIT_ID)X\n" + 
//        		"FULL JOIN\n" + 
//        		"(SELECT b.ACCOUNTING_UNIT_ID,\n" + 
//        		"  B.ACCOUNTING_UNIT_NAME AS UNAME,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=3\n" + 
//        		"    THEN A.TB_STATUS\n" + 
//        		"  END )AS MAR_SUP,\n" + 
//        		" NIL_TB_STATUS AS NIL_TB_STATUS_Sup\n " +
//        		"FROM\n" + 
////        		"  (SELECT ACC.ACCOUNTING_UNIT_ID,\n" + 
////        		"    ACC.ACCOUNTING_UNIT_NAME\n" + 
////        		"  FROM FAS_MST_ACCT_UNITS ACC\n" + 
////        		"  )B\n" + 
//				" (SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME " +
//				" FROM FAS_MST_ACCT_UNITS ACC " +
//				" WHERE ACC.STATUS IS NOT NULL " +
//				" and EXTRACT(YEAR FROM (ACC.DATE_OF_CLOSURE)) BETWEEN "+fin_year.split("-")[0]+" AND "+fin_year.split("-")[1] +
//				" UNION ALL " +
//				" SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME " +
//				" FROM FAS_MST_ACCT_UNITS ACC " +
//				" WHERE ACC.STATUS is null )B " +  
//
//        		
//        		"LEFT OUTER JOIN\n" + 
//        		"  (SELECT ACCOUNTING_UNIT_ID,\n" + 
//        		"    CASHBOOK_YEAR,\n" + 
//        		"    CASHBOOK_MONTH,\n" + 
//        		"    TO_CHAR(to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
//        		"    TB_STATUS,NIL_TB_STATUS,\n" + 
//        		"    DECODE(SUPPLEMENT_NO,0,'0',SUPPLEMENT_NO) AS SUPPLEMENT_NO\n" + 
//        		"  FROM FAS_TRIAL_BALANCE_STATUS_SJV\n" + 
//        		"  WHERE to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
//        		"    ||'-'\n" + 
//        		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
//        		"  AND to_date(3\n" + 
//        		"    ||'-'\n" + 
//        		"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
//        		"  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
//        		"    CASHBOOK_YEAR,\n" + 
//        		"    CASHBOOK_MONTH,\n" + 
//        		"    TO_CHAR(to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon'),\n" + 
//        		"    TB_STATUS,NIL_TB_STATUS,\n" + 
//        		"    SUPPLEMENT_NO\n" + 
//        		"  )A\n" + 
//        		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
//        		"GROUP BY B.ACCOUNTING_UNIT_ID, B.ACCOUNTING_UNIT_NAME, A.SUPPLEMENT_NO,NIL_TB_STATUS\n" + 
//        		"ORDER BY B.ACCOUNTING_UNIT_ID)Y\n" + 
//        		"on x.ACCOUNTING_UNIT_ID=y.ACCOUNTING_UNIT_ID";
        
       
//        String qry="SELECT b.ACCOUNTING_UNIT_ID,\n" + 
//        		"b.ACCOUNTING_UNIT_NAME as uname,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=4\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS APR,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=5\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS MAY,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=6\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS JUN,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=7\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS Jul,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=8\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS AUG,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN CASHBOOK_MONTH=9\n" + 
//        		"    THEN TB_STATUS\n" + 
//        		"  END )AS SEP,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=10\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS OCT,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=11\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS NOV,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=12\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS DEC,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=1\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS JAN,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=2\n" + 
//        		"    THEN a.TB_STATUS\n" + 
//        		"  END )AS FEB,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=3\n" + 
//        		"    THEN A.TB_STATUS\n" + 
//        		"  END )AS MAR,\n" + 
//        		"  MAX(\n" + 
//        		"  CASE\n" + 
//        		"    WHEN A.CASHBOOK_MONTH=3\n" + 
//        		"    THEN A.TB_STATUS\n" + 
//        		"  END )AS MAR_SUP\n" + 
//        		"FROM\n" +        		
//        		"  (SELECT ACC.ACCOUNTING_UNIT_ID,ACC.ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS ACC where acc.STATUS is null)B\n" + 
//        		"  LEFT OUTER JOIN \n" + 
//        		"  (SELECT ACCOUNTING_UNIT_ID,\n" + 
//        		"    CASHBOOK_YEAR,\n" + 
//        		"    CASHBOOK_MONTH,\n" + 
//        		"    TO_CHAR(to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
//        		"    TB_STATUS\n" + 
//        		"    \n" + 
//        		"  FROM FAS_TRIAL_BALANCE_STATUS \n" + 
//        		"  WHERE to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
//        		"    ||'-'\n" + 
//        		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
//        		"  AND to_date(3\n" + 
//        		"    ||'-'\n" + 
//        		"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
//        		"  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
//        		"    CASHBOOK_YEAR,\n" + 
//        		"    CASHBOOK_MONTH,\n" + 
//        		"    TO_CHAR(to_date(cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||CASHBOOK_YEAR,'mm-yyyy'),'Mon'),\n" + 
//        		"    TB_STATUS\n" + 
//        		"  )A\n" + 
//        		
//        		"  ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
//        		"  GROUP BY b.ACCOUNTING_UNIT_ID, B.ACCOUNTING_UNIT_NAME\n" + 
//        		"ORDER BY b.ACCOUNTING_UNIT_ID ";
        

        
        reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Freeze_Status_Rpt_Yearwise.jasper"));
        System.out.println("reportFile:" + reportFile);
        if (!reportFile.exists())
        	throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
        
        Map map = new HashMap();
        map.put("from_year",fin_year.split("-")[0]);
        map.put("to_year",fin_year.split("-")[1]);
       // map.put("unit_id",cmbAcc_UnitCode);
        map.put("fin_year", fin_year.split("-")[0]+"-"+fin_year.split("-")[1].substring(2));
        map.put("qry",qry);
       // map.put("Uname",Uname);
       // map.put("offname",offname);
       // map.put("offid",cmbOffice_code);
        
        System.out.println(map);
        JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);     
        if (rtype.equalsIgnoreCase("HTML")) {
            response.setContentType("text/html");
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"ViewReceipt_Count.html\"");
            PrintWriter out = response.getWriter();
            JRHtmlExporter exporter = new JRHtmlExporter();
            
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
          

            response.setHeader("Content-Disposition",
                               "attachment;filename=\"ViewReceipt_Count.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();
        } else if (rtype.equalsIgnoreCase("EXCEL")) {

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"ViewReceipt_Count.xls\"");
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
                               "attachment;filename=\"ViewReceipt_Count.txt\"");

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
            
        }
        catch(Exception e)
        {
        	System.out.println("Major try block Exception is===>"+e);
        }
        
		
		
	}

}
