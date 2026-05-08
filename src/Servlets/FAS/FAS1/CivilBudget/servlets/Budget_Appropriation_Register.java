package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Budget_Appropriation_Register
 */
public class Budget_Appropriation_Register extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Budget_Appropriation_Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;

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
		      
		HttpSession session = request.getSession(false);

		try {

		if (session == null) {
		System.out.println(request.getContextPath() + "/index.jsp");
		response.sendRedirect(request.getContextPath() + "/index.jsp");

		}

		} catch (Exception e) {
		System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try {
		if (session == null) {
		System.out.println(request.getContextPath() + "/index.jsp");
		response.sendRedirect(request.getContextPath() + "/index.jsp");

		}
		System.out.println(session);

		} catch (Exception e) {
		System.out.println("Redirect Error :" + e);
		}


		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		         JasperDesign jasperDesign = null;
		       File reportFile = null;
		       try {
		           System.out.println("++++++++Apppppp calling report*****");
		           String cmd="";
		         //  String rtype="PDF" ;
		          String fyear="";
		           try{
		         //  fyear=request.getParameter("cmbFinancialYear");
		           cmd=request.getParameter("command");
		             }catch(Exception e){
		            System.out.println("input get from jsp---"+e);
		           
		           }
		             System.out.println("cmmmd "+cmd);
		             int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		             int txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
		            String fin_year=request.getParameter("fin_year");
		            String qry="SELECT tb.accounting_unit_id,u.accounting_unit_name,h.account_head_desc,"+
		            " Tb.Account_Head_Code,"+
		            "  bud.CURRENT_YEAR_BUDGET_ALLOTTED AS oB,"+
		            " current_month_debit-current_month_credit AS cur_exp,"+
		            " tb.cashbook_year,  to_char(to_date(tb.cashbook_month"+
		            " ||'-'"+
		            " ||tb.cashbook_year,'mm-yyyy'),'Mon')mon_des,"+
		            " tb.cashbook_month "+ 
		           " FROM fas_trial_balance tb "+
		          " INNER JOIN Com_Mst_Account_Heads H "+
		          " ON Tb.Account_Head_Code=H.Account_Head_Code "+
		          " INNER JOIN Fas_Mst_Acct_Units U "+
		          " On Tb.Accounting_Unit_Id =U.Accounting_Unit_Id"+
		          " Inner Join COM_BUDGET_DETAILS Bud"+
		          " On Tb.Accounting_Unit_Id =Bud.Accounting_Unit_Id And"+
		          " Tb.Account_Head_Code=Bud.Account_Head_Code"+
		          " and bud.FINANCIAL_YEAR='"+fin_year.split("-")[0]+"-"+fin_year.split("-")[1].substring(2)+
		          "' AND bud.account_head_code ='"+txtAcc_HeadCode+
		          "' AND tb.accounting_unit_id="+cmbAcc_UnitCode+
		          " AND to_date(tb.cashbook_month"+
		           " ||'-'"+
		          " ||tb.cashbook_year,'mm-yyyy') BETWEEN to_date(4"+
		          "   ||'-'"+
		          " ||"+fin_year.split("-")[0]+",'mm-yyyy')"+
		          " AND to_date(3"+
		          " ||'-'"+
		          "  ||"+fin_year.split("-")[1]+",'mm-yyyy') "+
		          " Order By Tb.Cashbook_Year,"+
		          " Tb.Cashbook_Month";

		             reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/Budget_apprppriation_register.jasper")); 
		           
		         
		         
		           if (!reportFile.exists())
		               throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		           JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());

		          Map map = new HashMap();
		           map.put("from_year",fin_year.split("-")[0]);
		           map.put("to_year",fin_year.split("-")[1]);
		           map.put("unit_id",cmbAcc_UnitCode);
		           map.put("head_code", txtAcc_HeadCode);
		           map.put("fin_year", fin_year.split("-")[0]+"-"+fin_year.split("-")[1].substring(2));
		           
		           map.put("qry", qry);
		           System.out.println(map);
		           JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);


		           //else if (rtype.equalsIgnoreCase("PDF")) {
		           
		            //System.out.println("the option chosen is :::::"+rtype);
		               byte buf[] =
		                   JasperExportManager.exportReportToPdf(jasperPrint);
		               response.setContentType("application/pdf");
		               response.setContentLength(buf.length);
		             
		            //   if(cmd.equalsIgnoreCase("Report")){
		                response.setHeader("Content-Disposition","attachment;filename=\"Civil_Budget_AccountHeadMapping.pdf\"");
		            // }else if(cmd.equalsIgnoreCase("A52UnVerified")){
		                /*  response.setHeader("Content-Disposition","attachment;filename=\"A52_UnVerify.pdf\"");
		             }else if(cmd.equalsIgnoreCase("A52QtyFreezeStatus")){
		             response.setHeader("Content-Disposition","attachment;filename=\"A52QtyFreezeStatus.pdf\"");
		             }*/
		               
		              
		               OutputStream out = response.getOutputStream();
		               out.write(buf, 0, buf.length);
		               //System.out.println("testing***"+jasperPrint);
		               out.close();
		           //}  

		       } catch (Exception ex) {
		           String connectMsg =
		               "Could not create the report " + ex.getMessage() + " " +
		               ex.getLocalizedMessage();
		           System.out.println(connectMsg);
		       }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
