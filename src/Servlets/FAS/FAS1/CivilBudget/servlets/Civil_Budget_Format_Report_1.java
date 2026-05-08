package Servlets.FAS.FAS1.CivilBudget.servlets;

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
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Servlets.PMS.PMS1.ContractorsInfoSys.servlets.newRegnServlet;

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

/**
 * Servlet implementation class Civil_Budget_Format_Report_1
 */
public class Civil_Budget_Format_Report_1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Civil_Budget_Format_Report_1() {
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
		
		
		Connection con = null;
		String qry="",qry1="",Format="",Format1="",Main_desc="",Sub_type="",type_no="",grp_id="",grp_name="",group="";
		String heading="",title="",l1="",l2="",l3="",l4="",l5="",l6="",l7="",l8="",xml="",unit_description="";
		int count=0,count1=0,kk=0;
		//PreparedStatement ps=null;
	//	ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		StringBuilder sb1=new StringBuilder();
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
		
		
		 String formattyp=request.getParameter("format_type");
		 String rtype= request.getParameter("txtoption");
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		/*int cboCashBook_Year = Integer.parseInt(request
				.getParameter("cboCashBook_Year"));
		int cboCashBook_Year2 = Integer.parseInt(request
				.getParameter("cboCashBook_Year2"));*/
	String y1=request.getParameter("hid");
	String y2=request.getParameter("hid1");
	 l1=request.getParameter("l1");
	 l2=request.getParameter("l2");
	 l3=request.getParameter("l3");
	l4=request.getParameter("l4");
	l5=request.getParameter("l5");
	 l6=request.getParameter("l6");
	
	String y3=y2.substring(2);	
	String yeR_Value=y1+"-"+y3;
	String Rmn="";
	int val=Integer.parseInt(formattyp);
	int yearr1=Integer.parseInt(y1)-1;
	int year2=Integer.parseInt(y2)-1;
	//System.out.println(val);
	if(val==1)
		Rmn="I";
	else if(val==2)
	    Rmn="II";
	else if(val==3)
	    Rmn="III";
	else if(val==4)
		Rmn="IV";
	else if(val==5)
		Rmn="V";
	else if(val==6)
		Rmn="VI";
	else if(val==7)
		Rmn="VII";
	else if(val==8)
		Rmn="VIII";
	else if(val==9)
		Rmn="IX";
	else if(val==10)
		Rmn="X";
	else if(val==11)
		Rmn="XI";
	else if(val==12)
		Rmn="XII";
	else if(val==13)
		Rmn="XIII";
		String fin_year=request.getParameter("cmbFinancialYear");
		
		//System.out.println("fin_year:::"+fin_year);
		
		//System.out.println("formattyp:::"+formattyp);
		//System.out.println("rtype::::"+rtype);
		//System.out.println("cboAcc_UnitCode:::::::"+cboAcc_UnitCode);
		//System.out.println("cboOffice_code:::::::"+cboOffice_code);
		String detail=request.getParameter("r1");
		String detail1=request.getParameter("r11");
		System.out.println("detail1 "+detail1);
		String year = null;
		
			year = fin_year;
		//year=y
			//System.out.println("Rmn::::"+Rmn);
			String acc_unit_Name="",acc_unit_No="";
			 try {
				 PreparedStatement ps_st=null;
				 
					ResultSet rset=null;
				 unit_description="Select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where Accounting_Unit_Id="+cboAcc_UnitCode; 
				 ps_st=con.prepareStatement(unit_description);
				 rset=ps_st.executeQuery();
				while(rset.next())
				{
					acc_unit_Name=rset.getString("ACCOUNTING_UNIT_NAME");
				//	acc_unit_No=rset.getString("Accounting_Unit_Id");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String Acc_unit_Name="Unit Name : "+acc_unit_Name;
			 try {
				 PreparedStatement ps=null;
					ResultSet rs=null;
				 Format="select FORMAT_NO,FORMAT_DESC_MAIN,FORMAT_DESC_SUB from " +
				 		" fas_format_master where FORMAT_NO="+val; 
				ps=con.prepareStatement(Format);
				rs=ps.executeQuery();
				System.out.println("Format"+Format);
				while(rs.next())
				{
					type_no=rs.getString("FORMAT_NO");
					Main_desc=rs.getString("FORMAT_DESC_MAIN");
					Sub_type=rs.getString("FORMAT_DESC_SUB");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 try {
				 PreparedStatement ps1=null;
					ResultSet rs1=null;
				 Format1="SELECT budger_g_id, BUDGET_GROUP_MAJOR FROM " +
				 		"(SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id "
                         +"  FROM FAS_BUDGET_AC_HEADS_MAP WHERE FORMAT_NO="+val+" " +
                         		"ORDER BY BUDGET_GROUP_ID )a JOIN(SELECT BUDGET_GROUP_ID,"
                         +"  BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER " +
                         		"ORDER BY BUDGET_GROUP_ID)b ON budger_g_id =BUDGET_GROUP_ID"; 
				ps1=con.prepareStatement(Format1);
				rs1=ps1.executeQuery();
				System.out.println("Format1"+Format1);
				while(rs1.next())
				{
					grp_id=rs1.getString("budger_g_id");
					grp_name=rs1.getString("BUDGET_GROUP_MAJOR");
					
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("grp_id:::::::"+grp_id);
			//System.out.println("grp_name:::::::"+grp_name);
			if(grp_name==" ")
				grp_name="";	
			
			File reportFile = null;
			if(formattyp.equalsIgnoreCase("1"))
			{
				if(detail.equalsIgnoreCase("Detail"))
				{	
					
					
					
					//append before
					/*" select t.budger_g_id,t.BUDGET_GROUP_MAJOR, "+
					" c.* from "+
					" (SELECT budger_g_id, "+
					" BUDGET_GROUP_MAJOR, "+
					" ACC_HEAD_CODE "+
					" FROM "+
					" (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					" ACC_HEAD_CODE "+
					" FROM FAS_BUDGET_AC_HEADS_MAP "+
					" WHERE FORMAT_NO=1 "+
					" ORDER BY BUDGET_GROUP_ID "+
					" )a "+
					" left outer join"+
					" (SELECT BUDGET_GROUP_ID, "+
					" BUDGET_GROUP_MAJOR "+
					" FROM FAS_BUDGET_GROUP_MASTER "+
					" ORDER BY BUDGET_GROUP_ID "+
					"  )b "+
					"   ON a.budger_g_id =b.BUDGET_GROUP_ID)t "+
					"   inner join "+
					"   ( "+
					"  select head_of_account," +
					" (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS "+
					"   where ACCOUNT_HEAD_CODE=head_of_account) as head, "+
					"actuals_for_last_year AS Ac_fr_lst_yr, "+
					"   be_for_the_year, "+
					"   actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
				  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR, "+
					" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR, "+
					" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE, "+
					"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID, "+
					"  SL_NO from FAS_BUDGET_FORMAT_1 WHERE ACCOUNTING_UNIT_ID    = "+cboAcc_UnitCode+
					" AND ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
					" AND FINANCIAL_YEAR          = "+"'"+year+"'"+
					" )c "+
					"   on t.ACC_HEAD_CODE=c.head_of_account "+
					"  order by c.head_of_account "*/
					
					
				sb.append("select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr,  "+
						  " be_for_the_year,   "+
							" (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS "+
							"   where ACCOUNT_HEAD_CODE=head_of_account) as head, "+
							 "  actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov,   "+
							  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,   "+
							" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,  "+ 
							" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE,  "+ 
							"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,BUDGET_GROUP_ID as budger_g_id,   "+
							 "(SELECT BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER m   "+
							  " where m.BUDGET_GROUP_ID=f.BUDGET_GROUP_ID) as BUDGET_GROUP_MAJOR,  "+
							"  SL_NO from FAS_BUDGET_FORMAT_1  f  "+
							 " WHERE " +
							 " ACCOUNTING_UNIT_ID    ="+cboAcc_UnitCode+
							" AND " +
							" ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
							" AND FINANCIAL_YEAR          =  "+"'"+year+"'"+ 
							" --and ALLOCATION_TYPE='H' " );
				/*sb.append("select a.SL_NO," +
					" (select b.ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS b " +
					" where b.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID) as orgname,"+
                    " a.HEAD_OF_ACCOUNT,a.ACTUALS_FOR_LAST_YEAR,a.BE_FOR_THE_YEAR," +
                    " a.ACTUALS_FOR_PERIOD_APR_TO_NOV,"+
                    " a.ANTICIPATED_FR_PERIOD_DEC_MAR,a.RE_FOR_YEAR,a.VARIATION_BETWEN_BE_RE," +
                    " a.REASON_FOR_VARIATION,a.BE_FOR_NEXT_YEAR,"+
                    " a.VARIATION_BTWN_REYR_AND_NXTYR from FAS_BUDGET_FORMAT_1 a where " +
                    " a.ACCOUNTING_UNIT_ID= "+cboAcc_UnitCode +
                    " and a.ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+
                    " and a.FINANCIAL_YEAR="+"'"+year+"'");*/
				
			}
				else if(detail.equalsIgnoreCase("Abstract"))
				{
					System.out.println("Abstarct:::"+detail);
					
					
					/*"select Budger_G_Id,Budget_Group_Major,  "+
					"	Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
						" Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
				" Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
				" 	Sum(Re_For_Year)As Re_For_Year, "+
				" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
				" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
				" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
				" Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
						" from "+
				" (SELECT t.budger_g_id, "+
				" 	  t.BUDGET_GROUP_MAJOR, "+
				" 	  c.* "+
				" 	FROM "+
				" 	  (SELECT budger_g_id, "+
				" 	    BUDGET_GROUP_MAJOR, "+
				" 	    ACC_HEAD_CODE "+
				" 	  FROM "+
				" 	    (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
				" 	      ACC_HEAD_CODE "+
				" 	    FROM FAS_BUDGET_AC_HEADS_MAP "+
				" 	    WHERE FORMAT_NO=1 "+
				" 	    ORDER BY BUDGET_GROUP_ID "+
				" 	    )a "+
				" 	  LEFT OUTER JOIN "+
				" 	    (SELECT BUDGET_GROUP_ID, "+
				" 	      BUDGET_GROUP_MAJOR "+
				" 	    FROM FAS_BUDGET_GROUP_MASTER "+
				" 	    ORDER BY BUDGET_GROUP_ID "+
				" 	    )b "+
				" 	  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
				" 	  )t "+
				" 	INNER JOIN "+
				" 	  (SELECT head_of_account, "+
				" 	    actuals_for_last_year AS Ac_fr_lst_yr, "+
				" 	    be_for_the_year, "+
				" 	    actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
				" 	    ANTICIPATED_FR_PERIOD_DEC_MAR, "+
				" 	    RE_FOR_YEAR, "+
				" 	    VARIATION_BETWEN_BE_RE, "+
				" 	    REASON_FOR_VARIATION, "+
				" 	    BE_FOR_NEXT_YEAR, "+
				" 	    VARIATION_BTWN_REYR_AND_NXTYR, "+
				" 	    UPDATED_BY_USERID, "+
				" 	    UPDATED_DATE, "+
				" 	    DIVISION, "+
				" 	    CIRCLE, "+
				" 	    REGION, "+
				" 	    HEAD_OFFICE, "+
				" 	    OFFICE_LEVEL_ID, "+
				" 	    SL_NO "+
				" 	  From Fas_Budget_Format_1 "+
				" 	  Where Accounting_Unit_Id    = "+cboAcc_UnitCode+
				" 	  And Accounting_For_Office_Id= "+cboOffice_code+
				" 	  AND FINANCIAL_YEAR          = "+"'"+year+"'"+
				" 	  )c "+
				" 	On T.Acc_Head_Code=C.Head_Of_Account "+
				" 	Order By C.Head_Of_Account) "+
				" 	group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id "*/
					
					sb.append(" select Budger_G_Id,Budget_Group_Major, "+
							" Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
							"  Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
							"  Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
							" 	Sum(Re_For_Year)As Re_For_Year, "+
							" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
							" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
							" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
							"  Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
							" 		 from "+
									
							" 	  (SELECT BUDGET_GROUP_ID as budger_g_id,head_of_account, "+
							" (SELECT      BUDGET_GROUP_MAJOR "+
							"    FROM FAS_BUDGET_GROUP_MASTER f "+
							"    where f.BUDGET_GROUP_ID=f1.BUDGET_GROUP_ID ) as BUDGET_GROUP_MAJOR,  "+
							"     actuals_for_last_year AS Ac_fr_lst_yr, "+
							"     be_for_the_year, "+
							"     actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
							"     ANTICIPATED_FR_PERIOD_DEC_MAR, "+
							"     RE_FOR_YEAR, "+
							"     VARIATION_BETWEN_BE_RE, "+
							"     REASON_FOR_VARIATION, "+
							"     BE_FOR_NEXT_YEAR, "+
							"     VARIATION_BTWN_REYR_AND_NXTYR, "+
							"     UPDATED_BY_USERID, "+
							"     UPDATED_DATE, "+
							"   DIVISION, "+
					 	   "    CIRCLE, "+
					 	  "   REGION, "+
					 	 "   HEAD_OFFICE, "+
					 	"    OFFICE_LEVEL_ID, "+
					 	"    SL_NO "+
					 	"   From Fas_Budget_Format_1 f1 "+
					 	" 	  Where Accounting_Unit_Id    = "+cboAcc_UnitCode+
						" 	  And Accounting_For_Office_Id= "+cboOffice_code+
						" 	  AND FINANCIAL_YEAR          = "+"'"+year+"'"+
					 	" 	Order By head_of_account  "+
					" ) group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id ");
					
				}
				qry=sb.toString();
				heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
			//	title="Financial Year :"+yeR_Value;
				title="Financial Year :"+fin_year;
				//fin_year
				group=grp_name;
			}
			else if(formattyp.equalsIgnoreCase("2"))
			{
				
				if(detail.equalsIgnoreCase("Detail")){
					
					
						
						
						/*"select t.budger_g_id,t.BUDGET_GROUP_MAJOR, "+
						" c.* from "+
						" (SELECT budger_g_id, "+
						" BUDGET_GROUP_MAJOR, "+
						" head_of_account "+
						" FROM "+
						" (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
						" ACC_HEAD_CODE as head_of_account"+
						" FROM FAS_BUDGET_AC_HEADS_MAP "+
						" WHERE FORMAT_NO=2 "+
						" ORDER BY BUDGET_GROUP_ID "+
						" )a "+
						" left outer join"+
						" (SELECT BUDGET_GROUP_ID, "+
						" BUDGET_GROUP_MAJOR "+
						" FROM FAS_BUDGET_GROUP_MASTER "+
						" ORDER BY BUDGET_GROUP_ID "+
						"  )b "+
						"   ON a.budger_g_id =b.BUDGET_GROUP_ID)t "+
						"   inner join "+
						"   ( "+
						"  select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr, "+
						"   be_for_the_year as BE_FR_YR, "+
						"   actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
					  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR, "+
						" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR, "+
						" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE, "+
						"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID, "+
						"  SL_NO from fas_budget_format_2 WHERE ACCOUNTING_UNIT_ID    = "+cboAcc_UnitCode+
						" AND ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
						" AND FINANCIAL_YEAR          = "+"'"+year+"'"+
						" )c "+
						"   on t.head_of_account=c.head_of_account "+
						"  order by c.head_of_account "*/
					sb1.append(	"select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr,  "+
							  " be_for_the_year as BE_FR_YR,   "+
								" (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS "+
								"   where ACCOUNT_HEAD_CODE=head_of_account) as head, "+
								 "  actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov,   "+
								  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,   "+
								" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,  "+ 
								" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE,  "+ 
								"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,BUDGET_GROUP_ID as budger_g_id,   "+
								 "(SELECT BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER m   "+
								  " where m.BUDGET_GROUP_ID=f.BUDGET_GROUP_ID) as BUDGET_GROUP_MAJOR,  "+
								"  SL_NO from FAS_BUDGET_FORMAT_2  f  "+
								 " WHERE " +
								 " ACCOUNTING_UNIT_ID    ="+cboAcc_UnitCode+
								" AND " +
								" ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
								" AND FINANCIAL_YEAR          =  "+"'"+year+"'"+ 
								" --and ALLOCATION_TYPE='H' "
								);
					qry=sb1.toString();
					//System.out.println("test 1..."+qry);
					try {
					PreparedStatement	ps1=con.prepareStatement(qry);
						ResultSet rs1=ps1.executeQuery();
						
					while (rs1.next()){
						xml+="<test>"+rs1.getInt("budger_g_id")+"</test>";
						count++;
						
					}//System.out.println("count value........"+count);
					}
					catch (Exception e) {
						System.out.println(e);}
					if(count>0)
					{
						
						qry=sb1.toString();
					}
					/*else{
						//System.out.println("count value........"+count);
					sb.append( "SELECT budger_g_id,  "+
					 " BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE, "+
						" SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
						" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
						" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT budger_g_id, "+
						"  BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE, "+
						" DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
						" DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
						" DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT rownum AS slno1, "+
						" budger_g_id,BUDGET_GROUP_MAJOR,ACC_HEAD_CODE "+
						"  FROM "+
						"   (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
						"    ACC_HEAD_CODE "+
						"   FROM FAS_BUDGET_AC_HEADS_MAP "+
						"   WHERE FORMAT_NO=2 "+
						"   ORDER BY BUDGET_GROUP_ID "+
						"   )a "+
						"  LEFT OUTER JOIN "+
						"   (SELECT BUDGET_GROUP_ID, "+
						"     BUDGET_GROUP_MAJOR "+
						"   FROM FAS_BUDGET_GROUP_MASTER "+
						"   ORDER BY BUDGET_GROUP_ID "+
						"   )b "+
						"  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
						"  )X "+
						" LEFT OUTER JOIN "+
						" ( "+
						" SELECT ACCOUNT_HEAD_CODE_XX, "+
						"  (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
						"  FROM "+
						"  (SELECT ACCOUNT_HEAD_CODE1                                                            AS ACCOUNT_HEAD_CODE_XX, "+
						"    DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
						"  FROM "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr1, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
						"     FROM FAS_TRIAL_BALANCE "+
						"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"     AND CASHBOOK_YEAR       = "+yearr1+
						"    AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
						"    GROUP BY ACCOUNT_HEAD_CODE "+
						"    )a "+
						"  LEFT OUTER JOIN "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE "+
						"   WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"   AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"   )b "+
						" ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
						" )XX "+
						"  LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE2                                                           AS ACCOUNT_HEAD_CODE_YY, "+
						"     Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
						"    FROM "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       =  "+year2+
						"   And Cashbook_Month Between 1 And 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE) "+
						"  )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						" )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
						" LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
						"    DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
						"  FROM "+
						"   (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
						"    ACCOUNT_HEAD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
						"  FROM COM_BUDGET_DETAILS "+
						"  Where Accounting_Unit_Id="+cboAcc_UnitCode+
						"   AND FINANCIAL_YEAR      ='"+year+"'"+
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"    ) "+
						"  )Z "+
						" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
						"  LEFT OUTER JOIN "+
						"   ( "+
						"  SELECT ACCOUNT_HEAD_CODE_XX  AS ACCOUNT_HEAD_CODE_KK, "+
						"   (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
						"  FROM "+
						"  (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
						"    ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
						"   FROM FAS_TRIAL_BALANCE "+
						"  WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						" GROUP BY ACCOUNT_HEAD_CODE "+
						"   )XX "+
						"  Left Outer Join "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
						"   ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID= "+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						"  GROUP BY ACCOUNT_HEAD_CODE "+
						"   )Yy "+
						"  ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						"  )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
						" ) "+
						" GROUP BY budger_g_id, "+
						"  BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE "+
						" ORDER BY budger_g_id, "+
						"  ACC_HEAD_CODE ");
			
					qry=sb.toString();
					System.out.println("qry test...."+qry);
					}*/
					
			heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
			//title="Financial Year :"+yeR_Value;
			title="Financial Year :"+fin_year;
			group=grp_name;
			     
				}
				else if(detail.equalsIgnoreCase("Abstract")){
					System.out.println("detail"+detail);
					/*sb1.append("Select Budger_G_Id,Budget_Group_Major,  "+
				"	Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
					" Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
			" Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
			" 	Sum(Re_For_Year)As Re_For_Year, "+
			" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
			" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
			" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
			" Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
					" from "+
			" (SELECT t.budger_g_id, "+
			" 	  t.BUDGET_GROUP_MAJOR, "+
			" 	  c.* "+
			" 	FROM "+
			" 	  (SELECT budger_g_id, "+
			" 	    BUDGET_GROUP_MAJOR, "+
			" 	    ACC_HEAD_CODE "+
			" 	  FROM "+
			" 	    (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
			" 	      ACC_HEAD_CODE "+
			" 	    FROM FAS_BUDGET_AC_HEADS_MAP "+
			" 	    WHERE FORMAT_NO=1 "+
			" 	    ORDER BY BUDGET_GROUP_ID "+
			" 	    )a "+
			" 	  LEFT OUTER JOIN "+
			" 	    (SELECT BUDGET_GROUP_ID, "+
			" 	      BUDGET_GROUP_MAJOR "+
			" 	    FROM FAS_BUDGET_GROUP_MASTER "+
			" 	    ORDER BY BUDGET_GROUP_ID "+
			" 	    )b "+
			" 	  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
			" 	  )t "+
			" 	INNER JOIN "+
			" 	  (SELECT head_of_account, "+
			" 	    actuals_for_last_year AS Ac_fr_lst_yr, "+
			" 	    be_for_the_year, "+
			" 	    actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
			" 	    ANTICIPATED_FR_PERIOD_DEC_MAR, "+
			" 	    RE_FOR_YEAR, "+
			" 	    VARIATION_BETWEN_BE_RE, "+
			" 	    REASON_FOR_VARIATION, "+
			" 	    BE_FOR_NEXT_YEAR, "+
			" 	    VARIATION_BTWN_REYR_AND_NXTYR, "+
			" 	    UPDATED_BY_USERID, "+
			" 	    UPDATED_DATE, "+
			" 	    DIVISION, "+
			" 	    CIRCLE, "+
			" 	    REGION, "+
			" 	    HEAD_OFFICE, "+
			" 	    OFFICE_LEVEL_ID, "+
			" 	    SL_NO "+
			" 	  From Fas_Budget_Format_2 "+
			" 	  Where Accounting_Unit_Id    = "+cboAcc_UnitCode+
			" 	  And Accounting_For_Office_Id= "+cboOffice_code+
			" 	  AND FINANCIAL_YEAR          ="+"'" + year + "'"+
			" 	  )c "+
			" 	On T.Acc_Head_Code=C.Head_Of_Account "+
			" 	Order By C.Head_Of_Account) "+
			" 	group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id");*/
					
					sb1.append(" select Budger_G_Id,Budget_Group_Major, "+
					" Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
					"  Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
					"  Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
					" 	Sum(Re_For_Year)As Re_For_Year, "+
					" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
					" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
					" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
					"  Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
					" 		 from "+
							
					" 	  (SELECT BUDGET_GROUP_ID as budger_g_id,head_of_account, "+
					" (SELECT      BUDGET_GROUP_MAJOR "+
					"    FROM FAS_BUDGET_GROUP_MASTER f "+
					"    where f.BUDGET_GROUP_ID=f1.BUDGET_GROUP_ID ) as BUDGET_GROUP_MAJOR,  "+
					"     actuals_for_last_year AS Ac_fr_lst_yr, "+
					"     be_for_the_year, "+
					"     actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
					"     ANTICIPATED_FR_PERIOD_DEC_MAR, "+
					"     RE_FOR_YEAR, "+
					"     VARIATION_BETWEN_BE_RE, "+
					"     REASON_FOR_VARIATION, "+
					"     BE_FOR_NEXT_YEAR, "+
					"     VARIATION_BTWN_REYR_AND_NXTYR, "+
					"     UPDATED_BY_USERID, "+
					"     UPDATED_DATE, "+
					"   DIVISION, "+
			 	   "    CIRCLE, "+
			 	  "   REGION, "+
			 	 "   HEAD_OFFICE, "+
			 	"    OFFICE_LEVEL_ID, "+
			 	"    SL_NO "+
			 	"   From Fas_Budget_Format_2 f1 "+
			 	" 	  Where Accounting_Unit_Id    = "+cboAcc_UnitCode+
				" 	  And Accounting_For_Office_Id= "+cboOffice_code+
				" 	  AND FINANCIAL_YEAR          = "+"'"+year+"'"+
			 	" 	Order By head_of_account  "+
			" ) group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id ");
					qry=sb1.toString();
				//	System.out.println("test 1..."+qry);
					try {
					PreparedStatement	ps1=con.prepareStatement(qry);
						ResultSet rs1=ps1.executeQuery();
						
					while (rs1.next()){
						xml+="<test>"+rs1.getInt("Budger_G_Id")+"</test>";
						count++;
						
					}//System.out.println("count value........"+count);
					}
					catch (Exception e) {
						System.out.println(e);}
				if (count > 0) {

					qry = sb1.toString();
				}/* else {
					sb.append(" SELECT budger_g_id, "+
					 " BUDGET_GROUP_MAJOR, "+
					"  SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
					" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
					" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					" (SELECT budger_g_id,BUDGET_GROUP_MAJOR, "+
					"   DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
					"   DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
					"   DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					"   (SELECT rownum AS slno1,budger_g_id, "+
					"     BUDGET_GROUP_MAJOR,ACC_HEAD_CODE "+
					"   FROM "+
					"     (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					"       ACC_HEAD_CODE FROM FAS_BUDGET_AC_HEADS_MAP "+
					"     WHERE FORMAT_NO=2 ORDER BY BUDGET_GROUP_ID "+
					"     )a "+
					"   LEFT OUTER JOIN "+
					"     (SELECT BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR "+
					"     FROM FAS_BUDGET_GROUP_MASTER "+
					"     ORDER BY BUDGET_GROUP_ID "+
					"     )b "+
					"   ON a.budger_g_id =b.BUDGET_GROUP_ID "+
					"   )X "+
					" LEFT OUTER JOIN "+
					"   ( "+
					"   SELECT ACCOUNT_HEAD_CODE_XX, "+
					"     (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
					"   FROM "+
					"     (SELECT ACCOUNT_HEAD_CODE1                                                           AS ACCOUNT_HEAD_CODE_XX, "+
					"       DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
					"     FROM "+
					"        (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"          ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+yearr1+
					"       AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )a "+
					"     LEFT OUTER JOIN "+
					"       (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+year2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )b "+
					"     ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT ACCOUNT_HEAD_CODE2                                   AS ACCOUNT_HEAD_CODE_YY,"+
					"      Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
					"     FROM "+
					"         (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+year2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       ) "+
					"     )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
					"     DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
					"   FROM "+
					"     (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
					"       ACCOUNT_HEAD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM COM_BUDGET_DETAILS "+
					"     Where Accounting_Unit_Id="+cboAcc_UnitCode+
					"     AND FINANCIAL_YEAR      ='"+year+"'"+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     ) "+
					"   )Z "+
					" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX              AS ACCOUNT_HEAD_CODE_KK, "+
					"     (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
					"   FROM "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM FAS_TRIAL_BALANCE "+
					"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"     AND CASHBOOK_YEAR       ="+year2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
					"     FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"     AND CASHBOOK_YEAR       ="+year2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )YY "+
					"   ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
					" ) "+
					" GROUP BY budger_g_id, "+
					" BUDGET_GROUP_MAJOR "+
					" ORDER BY budger_g_id ");
				}*/
				qry = sb.toString();
				heading = "REPORT FOR " + Main_desc + " - " + Sub_type;
				//title = "Financial Year :" + yeR_Value;
				title="Financial Year :"+fin_year;
				group = grp_name;

			  } 
			}
			else if(formattyp.equalsIgnoreCase("3"))
			{
			sb.append("SELECT ACCOUNTING_UNIT_ID, "
					+ " ACCOUNTING_FOR_OFFICE_ID, FINANCIAL_YEAR, SL_NO, NAME_OF_CATEGORY, TIME_SCALE_OF_PAY, NO_OF_SANCTIONED_POSTS, NO_OF_INCUMBENTS_IN_ROLL, "
					+ " NO_OF_VACANT_POSTS, BEGINING_OF_THE_YEAR_BASIC_PAY, BEGINING_OF_THE_YEAR_50_BP,DECODE(INCREMENT_DATE,NULL,'-',TO_CHAR(INCREMENT_DATE,'DD/MM/YYYY'))AS INCREMENT_DATE, INCREMENT_AMOUNT, "
					+ "  AFTER_INCREMENT_BASIC_PAY, AFTER_INCREMENT_50_BP, BASIC_PAY_50_DEARNESS_PAY, "
					+ "  DA_32_ON_COL_NO_13, OTHER_ALLOWANCES, TOTAL,DECODE(DATE_OF_RETIREMENT,NULL,'-',TO_CHAR(DATE_OF_RETIREMENT,'DD/MM/YYYY'))AS DATE_OF_RETIREMENT, DIVISION, CIRCLE,REGION, HEAD_OFFICE, "
					+ " (SELECT OFFICE_NAME FROM COM_MST_ALL_OFFICES_VIEW WHERE OFFICE_ID=HEAD_OFFICE) AS head, "
					+ "  OFFICE_LEVEL_ID,  "
					+ " HRA_FOR_12_MONTHS, MA_FOR_12_MONTHS, WA_FOR_12_MONTHS, DA_PERCENT "
					+ " FROM FAS_BUDGET_FORMAT_3 a WHERE a.accounting_unit_id   = "
								+ cboAcc_UnitCode
								+ " and a.ACCOUNTING_FOR_OFFICE_ID="
								+ cboOffice_code
								+ " and a.FINANCIAL_YEAR="
								+ "'" + year + "'	order by a.sl_no");
			qry=sb.toString();
			heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
			//title="Financial Year :"+yeR_Value;
			title="Financial Year :"+fin_year;
			group=grp_name;
			
			}
			else if(formattyp.equalsIgnoreCase("4"))
			{
			sb.append("SELECT SL_NO, " +
					"  NAME_OF_EMPLOYEE, " +
					"  DESIGNATION, ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR, " +
					" (select b.ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS b " +
					" where b.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID) as orgname, "+
					"  DECODE(S_DATE_OF_RETIREMENT,NULL,'-',TO_CHAR(S_DATE_OF_RETIREMENT,'DD/MM/YYYY'))AS DATE_OF_RETIREMENT, " +
					"  S_AMT_PAID_UPTO_NOV_E_OF_LS, " +
					"  S_AMT_PID_UPTO_NOV_C_OF_PNSN, " +
					"  S_AMT_PAID_UPTO_NOV_GRATUITY, " +
					"  VR_AMT_PAID_UPTO_NOV_E_OF_LS, " +
					"  VR_AMT_PAID_UPTO_NOV_C_OF_PNSN, " +
					"  VR_AMT_PAID_UPTO_NOV_GRATUITY, " +
					"  S_ANTICIPATED_AMT_E_OF_LS, " +
					"  S_ANTICIPATED_AMT_C_OF_PNSN, " +
					"  S_ANTICIPATED_AMT_GRATUITY, " +
					"  VR_ANTICIPATED_AMT_E_OF_LS, " +
					"  VR_ANTICIPATED_AMT_C_OF_PNSN, " +
					"  DECODE(VRS_DATE_OF_RETIREMENT,NULL,'-',TO_CHAR(VRS_DATE_OF_RETIREMENT,'DD/MM/YYYY'))AS VRS_DATE_OF_RETIREMENT, " +
					"  VR_ANTICIPATED_AMT_GRATUITY " +
					" FROM FAS_BUDGET_FORMAT_4 a" +
					" WHERE ACCOUNTING_UNIT_ID    =" +cboAcc_UnitCode+
					" AND ACCOUNTING_FOR_OFFICE_ID= " +cboOffice_code+
					" AND FINANCIAL_YEAR          ='"+year+"'");
			qry=sb.toString();
			heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
			//title="Financial Year :"+yeR_Value;
			title="Financial Year :"+fin_year;
			group=grp_name;
			}
			else if(formattyp.equalsIgnoreCase("5"))
			{
			sb.append("SELECT SL_NO, "
					+ "  EMP_ID, "
					+ "  EMPLOYEE_NAME, "
					+ "  DESC_ID, "
					+ "  designation, "
					+ "  DATE_OF_RETIREMENT, "
					+ "  DATE_OF_RETIREMENT1, "
					+ "  SA_ENCASHMENT_OF_LS, "
					+ "  SA_COMMUTATION_OF_PENSION, "
					+ "  SA_GRATUITY, "
					+ "  VR_ENCASHMENT_OF_LS, "
					+ "  VR_COMMUTATION_OF_PENSION, "
					+ "  VR_GRATUITY "
					+ "FROM "
					+ "  (SELECT SL_NO, "
					+ "    EMP_ID, "
					+ "    DESC_ID, "
					+ "    DECODE(DATE_OF_RETIREMENT,NULL,'--',TO_CHAR(DATE_OF_RETIREMENT,'DD/MM/YYYY') ) DATE_OF_RETIREMENT, "
					+ "    SA_ENCASHMENT_OF_LS, "
					+ "    SA_COMMUTATION_OF_PENSION, "
					+ "    SA_GRATUITY, "
					+ "    DECODE(DATE_OF_RETIREMENT1,NULL,'--',TO_CHAR(DATE_OF_RETIREMENT1,'DD/MM/YYYY') ) DATE_OF_RETIREMENT1, "
					+ "    VR_ENCASHMENT_OF_LS, "
					+ "    VR_COMMUTATION_OF_PENSION, "
					+ "    VR_GRATUITY "
					+ "  FROM FAS_BUDGET_FORMAT_5 "
					+ "  WHERE ACCOUNTING_UNIT_ID    ="+cboAcc_UnitCode
					+ "  AND ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code
					+ "  AND FINANCIAL_YEAR          ='"+year+"' "
					+ "  )x "
					+ "LEFT OUTER JOIN "
					+ "  (SELECT EMPLOYEE_ID,EMPLOYEE_NAME FROM HRM_MST_EMPLOYEES "
					+ "  )y "
					+ "ON x.EMP_ID =y.EMPLOYEE_ID "
					+ "LEFT OUTER JOIN "
					+ "  (SELECT designation_id,designation FROM hrm_mst_designations "
					+ "  )z " + "ON x.DESC_ID =z.designation_id "
					+ "ORDER BY SL_NO");
			qry=sb.toString();
			heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
			//title="Financial Year :"+yeR_Value;
			title="Financial Year :"+fin_year;
			group=grp_name;
			}
			else if(formattyp.equalsIgnoreCase("6")){
				
				if(detail1.equalsIgnoreCase("6a"))
				{
				sb.append("SELECT SL_NO,"+
								 " CATEGORY,"+
								  "NO_OF_PENSIONERS,"+
								  "TOTAL_BASIC_PENSION_UPTO_11Y,"+
								  "TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1,"+
								  "TOTAL_D_A_UPTO_11Y,"+
								  "TOTAL_D_A_ANTICIPATED_12Y_3Y1,"+
								  "TOTAL_OTHER_PAYMENT_UPTO_11Y,"+
								  "TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1,"+
								  "TOTAL_UPTO_11Y,"+
								  "TOTAL_ANTICIPATED_12Y_3Y1,"+
								  "NO_OF_PENSIONERS1,"+
								  "TOTAL_BASIC_PENSION,"+
								  "TOTAL_D_A,"+
								  "TOTAL_OTHER_PAYMENT,"+
								  "TOTAL,PENSIONER_TYPE "+
								"FROM FAS_BUDGET_FORMAT_6 "+
						" WHERE ACCOUNTING_UNIT_ID    =" +cboAcc_UnitCode+
						" AND ACCOUNTING_FOR_OFFICE_ID= " +cboOffice_code+
						" AND FINANCIAL_YEAR          ='"+year+"'" +
						" AND PENSIONER_TYPE='P' "+
								" order by PENSIONER_TYPE");
				qry=sb.toString();
				
				heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
				//title="Financial Year :"+yeR_Value;
				title="Financial Year :"+fin_year;
				group=grp_name;
				
				}
				else if(detail1.equalsIgnoreCase("6b"))
				{
					sb.append("SELECT SL_NO,"+
							 " CATEGORY,"+
							  "NO_OF_PENSIONERS,"+
							  "TOTAL_BASIC_PENSION_UPTO_11Y,"+
							  "TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1,"+
							  "TOTAL_D_A_UPTO_11Y,"+
							  "TOTAL_D_A_ANTICIPATED_12Y_3Y1,"+
							  "TOTAL_OTHER_PAYMENT_UPTO_11Y,"+
							  "TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1,"+
							  "TOTAL_UPTO_11Y,"+
							  "TOTAL_ANTICIPATED_12Y_3Y1,"+
							  "NO_OF_PENSIONERS1,"+
							  "TOTAL_BASIC_PENSION,"+
							  "TOTAL_D_A,"+
							  "TOTAL_OTHER_PAYMENT,"+
							  "TOTAL,PENSIONER_TYPE "+
							"FROM FAS_BUDGET_FORMAT_6 "+
					" WHERE ACCOUNTING_UNIT_ID    =" +cboAcc_UnitCode+
					" AND ACCOUNTING_FOR_OFFICE_ID= " +cboOffice_code+
					" AND FINANCIAL_YEAR          ='"+year+"'" +
					" AND PENSIONER_TYPE='F' "+
							" order by PENSIONER_TYPE");
			qry=sb.toString();
			
			heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
			//title="Financial Year :"+yeR_Value;
			title="Financial Year :"+fin_year;
			group=grp_name;
				}
			}
			else if(formattyp.equalsIgnoreCase("7")){
				sb.append("SELECT SL_NO, " +
						"  POST_RANK_ID, " +
						"  post_rank_name, " +
						"  SANCTIONED_POST, " +
						"  DIVERSION_TO_OTHERS, " +
						"  DIVERSION_FROM_OTHERS, " +
						"  TOTAL, " +
						"  UTILISED, " +
						"  VACANT " +
						"FROM " +
						"  (SELECT SL_NO, " +
						"    POST_RANK_ID, " +
						"    SANCTIONED_POST, " +
						"    DIVERSION_TO_OTHERS, " +
						"    DIVERSION_FROM_OTHERS, " +
						"    TOTAL, " +
						"    UTILISED, " +
						"    VACANT " +
						"  FROM FAS_BUDGET_FORMAT_7 " +
						"  WHERE ACCOUNTING_UNIT_ID    = " +cboAcc_UnitCode+
						"  AND ACCOUNTING_FOR_OFFICE_ID=" +cboOffice_code+
						"  AND FINANCIAL_YEAR          ='" +year+"'"+
						"  )x " +
						"LEFT OUTER JOIN " +
						"  (SELECT post_rank_id AS psid, post_rank_name FROM hrm_mst_post_ranks " +
						"  )y " +
						"ON x.POST_RANK_ID =y.psid " +
						"ORDER BY SL_NO" );
				qry=sb.toString();
				heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
				//title="Financial Year :"+yeR_Value;
				title="Financial Year :"+fin_year;
				group=grp_name;
				}
				else if(formattyp.equalsIgnoreCase("8")){
					sb.append("SELECT SL_NO, " +
							"  HEAD_OF_ACCOUNT, " +
							"  BE_FOR_THE_YEAR, " +
							"  ANTICIPATED_DEC_TO_END_OF_CY, " +
							"  TOTAL, " +
							"  NEXT_YEAR, " +
							"  DECODE(BUDGET_GROUP_MAJOR,NULL,'-',BUDGET_GROUP_MAJOR) AS BUDGET_GROUP_MAJOR " +
							"FROM " +
							"  (SELECT SL_NO, " +
							"    HEAD_OF_ACCOUNT, " +
							"    BE_FOR_THE_YEAR, " +
							"    ANTICIPATED_DEC_TO_END_OF_CY, " +
							"    TOTAL, " +
							"    NEXT_YEAR " +
							"  FROM FAS_BUDGET_FORMAT_8 " +
							"  WHERE ACCOUNTING_UNIT_ID    = " +cboAcc_UnitCode+
							"  AND ACCOUNTING_FOR_OFFICE_ID=" +cboOffice_code+
							"  AND FINANCIAL_YEAR          ='" +year+"'"+
							"  )a " +
							"LEFT OUTER JOIN " +
							"  (SELECT BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER " +
							"  )b " +
							"ON a.HEAD_OF_ACCOUNT =b.BUDGET_GROUP_ID " +
							"ORDER BY SL_NO" );
					qry=sb.toString();
					heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
				//	title="Financial Year :"+yeR_Value;
					title="Financial Year :"+fin_year;
					group=grp_name;
					}
			else if(formattyp.equalsIgnoreCase("9"))
			{
				if(detail.equalsIgnoreCase("Detail")){
					
					/*
					"select t.budger_g_id,t.BUDGET_GROUP_MAJOR, "+
					" c.* from "+
					" (SELECT budger_g_id, "+
					" BUDGET_GROUP_MAJOR, "+
					" ACC_HEAD_CODE "+
					" FROM "+
					" (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					" ACC_HEAD_CODE "+
					" FROM FAS_BUDGET_AC_HEADS_MAP "+
					" WHERE FORMAT_NO=9 "+
					" ORDER BY BUDGET_GROUP_ID "+
					" )a "+
					" left outer join"+
					" (SELECT BUDGET_GROUP_ID, "+
					" BUDGET_GROUP_MAJOR "+
					" FROM FAS_BUDGET_GROUP_MASTER "+
					" ORDER BY BUDGET_GROUP_ID "+
					"  )b "+
					"   ON a.budger_g_id =b.BUDGET_GROUP_ID)t "+
					"   inner join "+
					"   ( "+
					"  select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr, "+
					"   be_for_the_year, "+
					"   actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
				  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR, "+
					" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR, "+
					" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE, "+
					"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID, "+
					"  SL_NO from FAS_BUDGET_FORMAT_9 WHERE ACCOUNTING_UNIT_ID    = "+cboAcc_UnitCode +
					" AND ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
					" AND FINANCIAL_YEAR          = '"+year+"'"+
					" )c "+
					"   on t.ACC_HEAD_CODE=c.head_of_account "+
					"  order by c.head_of_account "*/
	
						
				sb1.append("select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr,  "+
						  " be_for_the_year,   "+
							" (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS "+
							"   where ACCOUNT_HEAD_CODE=head_of_account) as head, "+
							 "  actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov,   "+
							  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,   "+
							" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,  "+ 
							" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE,  "+ 
							"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,BUDGET_GROUP_ID as budger_g_id,   "+
							 "(SELECT BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER m   "+
							  " where m.BUDGET_GROUP_ID=f.BUDGET_GROUP_ID) as BUDGET_GROUP_MAJOR,  "+
							"  SL_NO from FAS_BUDGET_FORMAT_9  f  "+
							 " WHERE " +
							 " ACCOUNTING_UNIT_ID    ="+cboAcc_UnitCode+
							" AND " +
							" ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
							" AND FINANCIAL_YEAR          =  "+"'"+year+"'"+ 
							" --and ALLOCATION_TYPE='H' ");
				qry=sb1.toString();
				//System.out.println("test 1..."+qry);
				try {
				PreparedStatement	ps1=con.prepareStatement(qry);
					ResultSet rs1=ps1.executeQuery();
					
				while (rs1.next()){
					xml+="<test>"+rs1.getInt("Budger_G_Id")+"</test>";
					count++;
					
				}//System.out.println("count value........"+count);
				}
				catch (Exception e) {
					System.out.println(e);}
				if(count>0){
					qry=sb1.toString();
				}/*else{
				sb.append("SELECT budger_g_id,  "+
					 " BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE, "+
						" SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
						" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
						" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT budger_g_id, "+
						"  BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE, "+
						" DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
						" DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
						" DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT rownum AS slno1, "+
						" budger_g_id,BUDGET_GROUP_MAJOR,ACC_HEAD_CODE "+
						"  FROM "+
						"   (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
						"    ACC_HEAD_CODE "+
						"   FROM FAS_BUDGET_AC_HEADS_MAP "+
						"   WHERE FORMAT_NO=9 "+
						"   ORDER BY BUDGET_GROUP_ID "+
						"   )a "+
						"  LEFT OUTER JOIN "+
						"   (SELECT BUDGET_GROUP_ID, "+
						"     BUDGET_GROUP_MAJOR "+
						"   FROM FAS_BUDGET_GROUP_MASTER "+
						"   ORDER BY BUDGET_GROUP_ID "+
						"   )b "+
						"  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
						"  )X "+
						" LEFT OUTER JOIN "+
						" ( "+
						" SELECT ACCOUNT_HEAD_CODE_XX, "+
						"  (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
						"  FROM "+
						"  (SELECT ACCOUNT_HEAD_CODE1                                                            AS ACCOUNT_HEAD_CODE_XX, "+
						"    DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
						"  FROM "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr1, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
						"     FROM FAS_TRIAL_BALANCE "+
						"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"     AND CASHBOOK_YEAR       = "+yearr1+
						"    AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
						"    GROUP BY ACCOUNT_HEAD_CODE "+
						"    )a "+
						"  LEFT OUTER JOIN "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE "+
						"   WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"   AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"   )b "+
						" ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
						" )XX "+
						"  LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE2                                                           AS ACCOUNT_HEAD_CODE_YY, "+
						"     Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
						"    FROM "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       =  "+year2+
						"   And Cashbook_Month Between 1 And 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE) "+
						"  )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						" )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
						" LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
						"    DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
						"  FROM "+
						"   (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
						"    ACCOUNT_HEAD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
						"  FROM COM_BUDGET_DETAILS "+
						"  Where Accounting_Unit_Id="+cboAcc_UnitCode+
						"   AND FINANCIAL_YEAR      = '"+year+"'"+
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"    ) "+
						"  )Z "+
						" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
						"  LEFT OUTER JOIN "+
						"   ( "+
						"  SELECT ACCOUNT_HEAD_CODE_XX  AS ACCOUNT_HEAD_CODE_KK, "+
						"   (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
						"  FROM "+
						"  (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
						"    ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
						"   FROM FAS_TRIAL_BALANCE "+
						"  WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						" GROUP BY ACCOUNT_HEAD_CODE "+
						"   )XX "+
						"  Left Outer Join "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
						"   ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID= "+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						"  GROUP BY ACCOUNT_HEAD_CODE "+
						"   )Yy "+
						"  ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						"  )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
						" ) "+
						" GROUP BY budger_g_id, "+
						"  BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE "+
						" ORDER BY budger_g_id, "+
						"  ACC_HEAD_CODE ");
				qry=sb.toString();
				}*/
				
			
			}
				else if(detail.equalsIgnoreCase("Abstract")){
					
				/*sb1.append("Select Budger_G_Id,Budget_Group_Major,  "+
						"	Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
							" Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
					" Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
					" 	Sum(Re_For_Year)As Re_For_Year, "+
					" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
					" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
					" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
					" Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
							" from "+
					" (SELECT t.budger_g_id, "+
					" 	  t.BUDGET_GROUP_MAJOR, "+
					" 	  c.* "+
					" 	FROM "+
					" 	  (SELECT budger_g_id, "+
					" 	    BUDGET_GROUP_MAJOR, "+
					" 	    ACC_HEAD_CODE "+
					" 	  FROM "+
					" 	    (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					" 	      ACC_HEAD_CODE "+
					" 	    FROM FAS_BUDGET_AC_HEADS_MAP "+
					" 	    WHERE FORMAT_NO=9 "+
					" 	    ORDER BY BUDGET_GROUP_ID "+
					" 	    )a "+
					" 	  LEFT OUTER JOIN "+
					" 	    (SELECT BUDGET_GROUP_ID, "+
					" 	      BUDGET_GROUP_MAJOR "+
					" 	    FROM FAS_BUDGET_GROUP_MASTER "+
					" 	    ORDER BY BUDGET_GROUP_ID "+
					" 	    )b "+
					" 	  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
					" 	  )t "+
					" 	INNER JOIN "+
					" 	  (SELECT head_of_account, "+
					" 	    actuals_for_last_year AS Ac_fr_lst_yr, "+
					" 	    be_for_the_year, "+
					" 	    actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
					" 	    ANTICIPATED_FR_PERIOD_DEC_MAR, "+
					" 	    RE_FOR_YEAR, "+
					" 	    VARIATION_BETWEN_BE_RE, "+
					" 	    REASON_FOR_VARIATION, "+
					" 	    BE_FOR_NEXT_YEAR, "+
					" 	    VARIATION_BTWN_REYR_AND_NXTYR, "+
					" 	    UPDATED_BY_USERID, "+
					" 	    UPDATED_DATE, "+
					" 	    DIVISION, "+
					" 	    CIRCLE, "+
					" 	    REGION, "+
					" 	    HEAD_OFFICE, "+
					" 	    OFFICE_LEVEL_ID, "+
					" 	    SL_NO "+
					" 	  From Fas_Budget_Format_9 "+
					" 	  Where Accounting_Unit_Id    = "+cboAcc_UnitCode+
					" 	  And Accounting_For_Office_Id= "+cboOffice_code+
					" 	  AND FINANCIAL_YEAR          ='"+year+"'"+
					" 	  )c "+
					" 	On T.Acc_Head_Code=C.Head_Of_Account "+
					" 	Order By C.Head_Of_Account) "+
					" 	group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id");*/
					
					sb1.append(" select Budger_G_Id,Budget_Group_Major, "+
							" Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
							"  Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
							"  Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
							" 	Sum(Re_For_Year)As Re_For_Year, "+
							" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
							" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
							" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
							"  Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
							" 		 from "+
									
							" 	  (SELECT BUDGET_GROUP_ID as budger_g_id,head_of_account, "+
							" (SELECT      BUDGET_GROUP_MAJOR "+
							"    FROM FAS_BUDGET_GROUP_MASTER f "+
							"    where f.BUDGET_GROUP_ID=f1.BUDGET_GROUP_ID ) as BUDGET_GROUP_MAJOR,  "+
							"     actuals_for_last_year AS Ac_fr_lst_yr, "+
							"     be_for_the_year, "+
							"     actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
							"     ANTICIPATED_FR_PERIOD_DEC_MAR, "+
							"     RE_FOR_YEAR, "+
							"     VARIATION_BETWEN_BE_RE, "+
							"     REASON_FOR_VARIATION, "+
							"     BE_FOR_NEXT_YEAR, "+
							"     VARIATION_BTWN_REYR_AND_NXTYR, "+
							"     UPDATED_BY_USERID, "+
							"     UPDATED_DATE, "+
							"   DIVISION, "+
					 	   "    CIRCLE, "+
					 	  "   REGION, "+
					 	 "   HEAD_OFFICE, "+
					 	"    OFFICE_LEVEL_ID, "+
					 	"    SL_NO "+
					 	"   From Fas_Budget_Format_9 f1 "+
					 	" 	  Where Accounting_Unit_Id    = "+cboAcc_UnitCode+
						" 	  And Accounting_For_Office_Id= "+cboOffice_code+
						" 	  AND FINANCIAL_YEAR          = "+"'"+year+"'"+
					 	" 	Order By head_of_account  "+
					" ) group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id ");
					
				
				qry=sb1.toString();
				System.out.println("test 1..."+qry);
				try {
				PreparedStatement	ps1=con.prepareStatement(qry);
					ResultSet rs1=ps1.executeQuery();
					
				while (rs1.next()){
					xml+="<test>"+rs1.getInt("Budger_G_Id")+"</test>";
					count++;
					
				}System.out.println("count value........"+count);
				}
				catch (Exception e) {
					System.out.println(e);}
				if(count>0){
					qry=sb1.toString();
				}/*else{
					sb.append("SELECT budger_g_id, "+
					 " BUDGET_GROUP_MAJOR, "+
					"  SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
					" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
					" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					" (SELECT budger_g_id,BUDGET_GROUP_MAJOR, "+
					"   DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
					"   DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
					"   DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					"   (SELECT rownum AS slno1,budger_g_id, "+
					"     BUDGET_GROUP_MAJOR,ACC_HEAD_CODE "+
					"   FROM "+
					"     (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					"       ACC_HEAD_CODE FROM FAS_BUDGET_AC_HEADS_MAP "+
					"     WHERE FORMAT_NO=9 ORDER BY BUDGET_GROUP_ID "+
					"     )a "+
					"   LEFT OUTER JOIN "+
					"     (SELECT BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR "+
					"     FROM FAS_BUDGET_GROUP_MASTER "+
					"     ORDER BY BUDGET_GROUP_ID "+
					"     )b "+
					"   ON a.budger_g_id =b.BUDGET_GROUP_ID "+
					"   )X "+
					" LEFT OUTER JOIN "+
					"   ( "+
					"   SELECT ACCOUNT_HEAD_CODE_XX, "+
					"     (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
					"   FROM "+
					"     (SELECT ACCOUNT_HEAD_CODE1                                                           AS ACCOUNT_HEAD_CODE_XX, "+
					"       DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
					"     FROM "+
					"        (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"          ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode	+
					"       AND CASHBOOK_YEAR       = "+yearr1+
					"       AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )a "+
					"     LEFT OUTER JOIN "+
					"       (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode	+
					"       AND CASHBOOK_YEAR       = "+year2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )b "+
					"     ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT ACCOUNT_HEAD_CODE2                                   AS ACCOUNT_HEAD_CODE_YY,"+
					"      Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
					"     FROM "+
					"         (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode	+
					"       AND CASHBOOK_YEAR       = "+year2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       ) "+
					"     )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
					"     DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
					"   FROM "+
					"     (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
					"       ACCOUNT_HEAD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM COM_BUDGET_DETAILS "+
	
					"     Where Accounting_Unit_Id="+cboAcc_UnitCode	+
					"     AND FINANCIAL_YEAR      = '"+year+"'"+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     ) "+
					"   )Z "+
					" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX              AS ACCOUNT_HEAD_CODE_KK, "+
					"     (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
					"   FROM "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM FAS_TRIAL_BALANCE "+
					"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode	+
					"     AND CASHBOOK_YEAR       ="+year2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
					"     FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode	+
					"     AND CASHBOOK_YEAR       ="+year2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )YY "+
					"   ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
					" ) "+
					" GROUP BY budger_g_id, "+
					" BUDGET_GROUP_MAJOR "+
					" ORDER BY budger_g_id  ");
					
					
					
					qry=sb.toString();
				}*/
				
		}heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
		//title="Financial Year :"+yeR_Value;
		title="Financial Year :"+fin_year;
		group=grp_name;	
			}
			else if(formattyp.equalsIgnoreCase("10"))
			{
				if(detail.equalsIgnoreCase("Detail")){
					
					
						
						
						/*" select t.budger_g_id,t.BUDGET_GROUP_MAJOR, "+
						" c.* from "+
						" (SELECT budger_g_id, "+
						" BUDGET_GROUP_MAJOR, "+
						" ACC_HEAD_CODE "+
						" FROM "+
						" (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
						" ACC_HEAD_CODE "+
						" FROM FAS_BUDGET_AC_HEADS_MAP "+
						" WHERE FORMAT_NO=10 "+
						" ORDER BY BUDGET_GROUP_ID "+
						" )a "+
						" left outer join"+
						" (SELECT BUDGET_GROUP_ID, "+
						" BUDGET_GROUP_MAJOR "+
						" FROM FAS_BUDGET_GROUP_MASTER "+
						" ORDER BY BUDGET_GROUP_ID "+
						"  )b "+
						"   ON a.budger_g_id =b.BUDGET_GROUP_ID)t "+
						"   inner join "+
						"   ( "+
						"  select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr, "+
						"   be_for_the_year, "+
						"   actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
					  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR, "+
						" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR, "+
						" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE, "+
						"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID, "+
						"  SL_NO from FAS_BUDGET_FORMAT_10 WHERE ACCOUNTING_UNIT_ID    = "+cboAcc_UnitCode+
						" AND ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
						" AND FINANCIAL_YEAR          ='"+year+"'"+
						" )c "+
						"   on t.ACC_HEAD_CODE=c.head_of_account "+
						"  order by c.head_of_account "*/
				sb1.append("select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr,  "+
						  " be_for_the_year,   "+
							" (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS "+
							"   where ACCOUNT_HEAD_CODE=head_of_account) as head, "+
							 "  actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov,   "+
							  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,   "+
							" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,  "+ 
							" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE,  "+ 
							"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,BUDGET_GROUP_ID as budger_g_id,   "+
							 "(SELECT BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER m   "+
							  " where m.BUDGET_GROUP_ID=f.BUDGET_GROUP_ID) as BUDGET_GROUP_MAJOR,  "+
							"  SL_NO from FAS_BUDGET_FORMAT_10  f  "+
							 " WHERE " +
							 " ACCOUNTING_UNIT_ID    ="+cboAcc_UnitCode+
							 " AND " +
							" ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+
							" AND FINANCIAL_YEAR          =  "+"'"+year+"'"+ 
							" --and ALLOCATION_TYPE='H' ");
				qry=sb1.toString();
			
				System.out.println("test 1..."+qry);
				try {
				PreparedStatement	ps1=con.prepareStatement(qry);
					ResultSet rs1=ps1.executeQuery();
					
				while (rs1.next()){
					xml+="<test>"+rs1.getInt("Budger_G_Id")+"</test>";
					count++;
					
				}System.out.println("count value........"+count);
				}
				catch (Exception e) {
					System.out.println(e);}
				if(count>0){
					qry=sb1.toString();
				}/*else{
					sb.append("SELECT budger_g_id,  "+
					 " BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE, "+
						" SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
						" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
						" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT budger_g_id, "+
						"  BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE, "+
						" DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
						" DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
						" DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT rownum AS slno1, "+
						" budger_g_id,BUDGET_GROUP_MAJOR,ACC_HEAD_CODE "+
						"  FROM "+
						"   (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
						"    ACC_HEAD_CODE "+
						"   FROM FAS_BUDGET_AC_HEADS_MAP "+
						"   WHERE FORMAT_NO=10 "+
						"   ORDER BY BUDGET_GROUP_ID "+
						"   )a "+
						"  LEFT OUTER JOIN "+
						"   (SELECT BUDGET_GROUP_ID, "+
						"     BUDGET_GROUP_MAJOR "+
						"   FROM FAS_BUDGET_GROUP_MASTER "+
						"   ORDER BY BUDGET_GROUP_ID "+
						"   )b "+
						"  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
						"  )X "+
						" LEFT OUTER JOIN "+
						" ( "+
						" SELECT ACCOUNT_HEAD_CODE_XX, "+
						"  (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
						"  FROM "+
						"  (SELECT ACCOUNT_HEAD_CODE1                                                            AS ACCOUNT_HEAD_CODE_XX, "+
						"    DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
						"  FROM "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr1, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
						"     FROM FAS_TRIAL_BALANCE "+
						"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"     AND CASHBOOK_YEAR       = "+yearr1+
						"    AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
						"    GROUP BY ACCOUNT_HEAD_CODE "+
						"    )a "+
						"  LEFT OUTER JOIN "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE "+
						"   WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"   AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"   )b "+
						" ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
						" )XX "+
						"  LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE2                                                           AS ACCOUNT_HEAD_CODE_YY, "+
						"     Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
						"    FROM "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       =  "+year2+
						"   And Cashbook_Month Between 1 And 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE) "+
						"  )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						" )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
						" LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
						"    DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
						"  FROM "+
						"   (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
						"    ACCOUNT_HEAD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
						"  FROM COM_BUDGET_DETAILS "+
						"  Where Accounting_Unit_Id="+cboAcc_UnitCode+
						"   AND FINANCIAL_YEAR      ='"+year+"'"+
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"    ) "+
						"  )Z "+
						" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
						"  LEFT OUTER JOIN "+
						"   ( "+
						"  SELECT ACCOUNT_HEAD_CODE_XX  AS ACCOUNT_HEAD_CODE_KK, "+
						"   (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
						"  FROM "+
						"  (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
						"    ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
						"   FROM FAS_TRIAL_BALANCE "+
						"  WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						" GROUP BY ACCOUNT_HEAD_CODE "+
						"   )XX "+
						"  Left Outer Join "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
						"   ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID= "+cboAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+year2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						"  GROUP BY ACCOUNT_HEAD_CODE "+
						"   )Yy "+
						"  ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						"  )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
						" ) "+
						" GROUP BY budger_g_id, "+
						"  BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE "+
						" ORDER BY budger_g_id, "+
						"  ACC_HEAD_CODE ");
					
					qry=sb.toString();
					
				}*/
				}
				
				else if(detail.equalsIgnoreCase("Abstract"))
				{
					/*sb1.append("Select Budger_G_Id,Budget_Group_Major,  "+
						"	Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
							" Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
					" Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
					" 	Sum(Re_For_Year)As Re_For_Year, "+
					" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
					" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
					" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
					" Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
							" from "+
					" (SELECT t.budger_g_id, "+
					" 	  t.BUDGET_GROUP_MAJOR, "+
					" 	  c.* "+
					" 	FROM "+
					" 	  (SELECT budger_g_id, "+
					" 	    BUDGET_GROUP_MAJOR, "+
					" 	    ACC_HEAD_CODE "+
					" 	  FROM "+
					" 	    (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					" 	      ACC_HEAD_CODE "+
					" 	    FROM FAS_BUDGET_AC_HEADS_MAP "+
					" 	    WHERE FORMAT_NO=10 "+
					" 	    ORDER BY BUDGET_GROUP_ID "+
					" 	    )a "+
					" 	  LEFT OUTER JOIN "+
					" 	    (SELECT BUDGET_GROUP_ID, "+
					" 	      BUDGET_GROUP_MAJOR "+
					" 	    FROM FAS_BUDGET_GROUP_MASTER "+
					" 	    ORDER BY BUDGET_GROUP_ID "+
					" 	    )b "+
					" 	  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
					" 	  )t "+
					" 	INNER JOIN "+
					" 	  (SELECT head_of_account, "+
					" 	    actuals_for_last_year AS Ac_fr_lst_yr, "+
					" 	    be_for_the_year, "+
					" 	    actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
					" 	    ANTICIPATED_FR_PERIOD_DEC_MAR, "+
					" 	    RE_FOR_YEAR, "+
					" 	    VARIATION_BETWEN_BE_RE, "+
					" 	    REASON_FOR_VARIATION, "+
					" 	    BE_FOR_NEXT_YEAR, "+
					" 	    VARIATION_BTWN_REYR_AND_NXTYR, "+
					" 	    UPDATED_BY_USERID, "+
					" 	    UPDATED_DATE, "+
					" 	    DIVISION, "+
					" 	    CIRCLE, "+
					" 	    REGION, "+
					" 	    HEAD_OFFICE, "+
					" 	    OFFICE_LEVEL_ID, "+
					" 	    SL_NO "+
					" 	  From FAS_BUDGET_FORMAT_10 "+
					" 	  Where Accounting_Unit_Id    = "+cboAcc_UnitCode+
					" 	  And Accounting_For_Office_Id= "+cboOffice_code+
					" 	  AND FINANCIAL_YEAR          ='"+year+"'"+
					" 	  )c "+
					" 	On T.Acc_Head_Code=C.Head_Of_Account "+
					" 	Order By C.Head_Of_Account) "+
					" 	group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id");*/
					
					sb1.append(" select Budger_G_Id,Budget_Group_Major, "+
							" Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
							"  Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
							"  Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
							" 	Sum(Re_For_Year)As Re_For_Year, "+
							" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
							" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
							" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
							"  Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
							" 		 from "+
									
							" 	  (SELECT BUDGET_GROUP_ID as budger_g_id,head_of_account, "+
							" (SELECT      BUDGET_GROUP_MAJOR "+
							"    FROM FAS_BUDGET_GROUP_MASTER f "+
							"    where f.BUDGET_GROUP_ID=f1.BUDGET_GROUP_ID ) as BUDGET_GROUP_MAJOR,  "+
							"     actuals_for_last_year AS Ac_fr_lst_yr, "+
							"     be_for_the_year, "+
							"     actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
							"     ANTICIPATED_FR_PERIOD_DEC_MAR, "+
							"     RE_FOR_YEAR, "+
							"     VARIATION_BETWEN_BE_RE, "+
							"     REASON_FOR_VARIATION, "+
							"     BE_FOR_NEXT_YEAR, "+
							"     VARIATION_BTWN_REYR_AND_NXTYR, "+
							"     UPDATED_BY_USERID, "+
							"     UPDATED_DATE, "+
							"   DIVISION, "+
					 	   "    CIRCLE, "+
					 	  "   REGION, "+
					 	 "   HEAD_OFFICE, "+
					 	"    OFFICE_LEVEL_ID, "+
					 	"    SL_NO "+
					 	"   From Fas_Budget_Format_10 f1 "+
					 	" 	  Where Accounting_Unit_Id    = "+cboAcc_UnitCode+
						" 	  And Accounting_For_Office_Id= "+cboOffice_code+
						" 	  AND FINANCIAL_YEAR          = "+"'"+year+"'"+
					 	" 	Order By head_of_account  "+
					" ) group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id ");
					qry=sb1.toString();
					
					System.out.println("test 1..."+qry);
					try {
					PreparedStatement	ps1=con.prepareStatement(qry);
						ResultSet rs1=ps1.executeQuery();
						
					while (rs1.next()){
						xml+="<test>"+rs1.getInt("Budger_G_Id")+"</test>";
						count++;
						
					}System.out.println("count value........"+count);
					}
					catch (Exception e) {
						System.out.println(e);}
					if(count>0){
						qry=sb1.toString();
					}/*else{
						sb.append(" SELECT budger_g_id, "+
					 " BUDGET_GROUP_MAJOR, "+
					"  SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
					" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
					" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					" (SELECT budger_g_id,BUDGET_GROUP_MAJOR, "+
					"   DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
					"   DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
					"   DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					"   (SELECT rownum AS slno1,budger_g_id, "+
					"     BUDGET_GROUP_MAJOR,ACC_HEAD_CODE "+
					"   FROM "+
					"     (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					"       ACC_HEAD_CODE FROM FAS_BUDGET_AC_HEADS_MAP "+
					"     WHERE FORMAT_NO=10 ORDER BY BUDGET_GROUP_ID "+
					"     )a "+
					"   LEFT OUTER JOIN "+
					"     (SELECT BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR "+
					"     FROM FAS_BUDGET_GROUP_MASTER "+
					"     ORDER BY BUDGET_GROUP_ID "+
					"     )b "+
					"   ON a.budger_g_id =b.BUDGET_GROUP_ID "+
					"   )X "+
					" LEFT OUTER JOIN "+
					"   ( "+
					"   SELECT ACCOUNT_HEAD_CODE_XX, "+
					"     (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
					"   FROM "+
					"     (SELECT ACCOUNT_HEAD_CODE1                                                           AS ACCOUNT_HEAD_CODE_XX, "+
					"       DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
					"     FROM "+
					"        (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"          ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+yearr1+
					"       AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )a "+
					"     LEFT OUTER JOIN "+
					"       (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+year2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )b "+
					"     ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT ACCOUNT_HEAD_CODE2                                   AS ACCOUNT_HEAD_CODE_YY,"+
					"      Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
					"     FROM "+
					"         (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"       WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+year2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       ) "+
					"     )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
					"     DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
					"   FROM "+
					"     (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
					"       ACCOUNT_HEAD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM COM_BUDGET_DETAILS "+
					"     Where Accounting_Unit_Id="+cboAcc_UnitCode+
					"     AND FINANCIAL_YEAR      = '"+year+"'"+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     ) "+
					"   )Z "+
					" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX              AS ACCOUNT_HEAD_CODE_KK, "+
					"     (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
					"   FROM "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM FAS_TRIAL_BALANCE "+
					"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"     AND CASHBOOK_YEAR       ="+year2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
					"     FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"     WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
					"     AND CASHBOOK_YEAR       ="+year2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )YY "+
					"   ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
					" ) "+
					" GROUP BY budger_g_id, "+
					" BUDGET_GROUP_MAJOR "+
					" ORDER BY budger_g_id  ");
					qry=sb.toString();
				}*/
				}
			heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
			//title="Financial Year :"+yeR_Value;
			title="Financial Year :"+fin_year;
			group=grp_name;
			}
			else if(formattyp.equalsIgnoreCase("11")){
				sb.append("SELECT ACCOUNTING_UNIT_ID, " +
						"  ACCOUNTING_FOR_OFFICE_ID, " +
						"  FINANCIAL_YEAR, " +
						"  SL_NO, " +
						"  DIVN_CODE, " +
						"  TYPE_OF_VEHICLE, " +
						"  REGN_NO, " +
						"  TO_CHAR(DATE_OF_PURCHASE,'DD/MM/YYYY') AS DATE_OF_PURCHASE, " +
						"  NO_OF_KMS_DONE, " +
						"  AGE_OF_VEHICLE, " +
						"  TO_CHAR(DATE_OF_CONDEMNATION_FIT,'DD/MM/YYYY')     AS DATE_OF_CONDEMNATION_FIT, " +
						"  TO_CHAR(DATE_OF_CONDEMNATION_NOT_FIT,'DD/MM/YYYY') AS DATE_OF_CONDEMNATION_NOT_FIT, " +
						"  SALARY_LY, " +
						"  FUEL_MATERIA_LY, " +
						"  ORDINARY_REPAIRS_LY, " +
						"  SPECIAL_REPAIRS_LY, " +
						"  TOTAL_COST_LY, " +
						"  SALARY_CY, " +
						"  FUEL_MATERIA_CY, " +
						"  ORDINARY_REPAIRS_CY, " +
						"  SPECIAL_REPAIRS_CY, " +
						"  TOTAL_COST_CY, " +
						"  SALARY_NY, " +
						"  FUEL_MATERIA_NY, " +
						"  ORDINARY_REPAIRS_NY, " +
						"  SPECIAL_REPAIRS_NY, " +
						"  TOTAL_COST_NY " +
						" FROM FAS_BUDGET_FORMAT_11 " +
						" WHERE ACCOUNTING_UNIT_ID    = " +cboAcc_UnitCode+
						" AND ACCOUNTING_FOR_OFFICE_ID= " +cboOffice_code+
						" AND FINANCIAL_YEAR          ='"+year+"'");
				qry=sb.toString();
				heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
				//title="Financial Year :"+yeR_Value;
				title="Financial Year :"+fin_year;
				group=grp_name;
				}
			else if(formattyp.equalsIgnoreCase("12")) {
				//System.out.println("122uiuiuy2");
				sb.append("SELECT SL_NO,VEHICLES,"+	
						"JOBS_PENDING,"+
						"PRE_YR_JOBS_ENTRUSTED,"+
						"CUR_YR_JOBS_ENTRUSTED,"+
						"PRE_YR_JOBS_COMPLITED,"+
						"CUR_YR_JOBS_COMPLITED,"+
						"PRE_YR_DIRECT_LABOUR,"+
						"CUR_YR_DIRECT_LABOUR,"+
						"PRE_YR_INDIRECT_LABOUR,"+
						"CUR_YR_INDIRECT_LABOUR,"+
						"PRE_YR_VALUE_OF_WORK,"+
						"CUR_YR_VALUE_OF_WORK,"+
						"PRE_YR_COST_OF_SPARES,"+
						"CUR_YR_COST_OF_SPARES,"+
						"PRE_YR_EXPENDITURE_OF_OTHERS,"+
						"CUR_YR_EXPENDITURE_OF_OTHERS,"+
						"PRE_YR_TOTAL_COST,"+
						"CUR_YR_TOTAL_COST from FAS_BUDGET_FORMAT_12 "+  						
				" WHERE ACCOUNTING_UNIT_ID    =" +cboAcc_UnitCode+
				" AND ACCOUNTING_FOR_OFFICE_ID= " +cboOffice_code+
				" AND FINANCIAL_YEAR          ='"+year+"'");
		qry=sb.toString();
		
		heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
		//title="Financial Year :"+yeR_Value;
		title="Financial Year :"+fin_year;
		group=grp_name;
				
			}
			else if(formattyp.equalsIgnoreCase("13")) {
				System.out.println("12A");
				sb.append("SELECT SL_NO,"+
						"VEHICLES, "+
						"PENDING_ON_BEGIN_OF_THE_YR_1A,"+
						"PENDING_ON_BEGIN_OF_THE_YR_1B,"+
						"TDA_RAISED_2A,"+
						"TDA_RAISED_2B,"+
						"TDA_RAISED_3A,"+
						"TDA_RAISED_3B,"+
						"TDA_ACCEPTED_AND_ADJUSTED_4A,"+
						"TDA_ACCEPTED_AND_ADJUSTED_4B,"+
						"TDA_ACCEPTED_AND_ADJUSTED_5A,"+
						"TDA_ACCEPTED_AND_ADJUSTED_5B,"+
						"VALUE_OF_JOBS_IN_PROGRESS_6A,"+
						"VALUE_OF_JOBS_IN_PROGRESS_6B,"+
						"VALUE_OF_JOBS_IN_PROGRESS_7A "+
						" from FAS_BUDGET_FORMAT_12A "+  						
				" WHERE ACCOUNTING_UNIT_ID    =" +cboAcc_UnitCode+
				" AND ACCOUNTING_FOR_OFFICE_ID= " +cboOffice_code+
				" AND FINANCIAL_YEAR          ='"+year+"'");
		qry=sb.toString();
		
		heading="REPORT FOR "+Main_desc+" - "+Sub_type;	
		//title="Financial Year :"+yeR_Value;
		title="Financial Year :"+fin_year;
		group=grp_name;
				
			}
			if(formattyp.equalsIgnoreCase("6")){

				// Date date; // your date
				    Calendar cal = Calendar.getInstance();
				    //cal.setTime(date);
				    int year0 = cal.get(Calendar.YEAR);
				    int month = cal.get(Calendar.MONTH);
				    int day = cal.get(Calendar.DAY_OF_MONTH);
				//Date today = new Date();
				//var day = today.getDate();
				//var month = today.getMonth();
				month = month + 1;
				//var year = today.getYear();
				int year1 = 0;
				int financialyear = 0;
				int financialyear1 = 0;
				int financialyear2 = 0;
				if (year0 < 1900)
					year0 += 1900;
				if (month < 4) {
					year1 = year0 - 1;
				} else {
					year1 = year0 + 1;
				}

				if (month < 4) {
					/*financialyear = year1 + "-" + year;
					financialyear1 = year1-1 + "-" + year-1;
					financialyear2 = year1-2 + "-" + (parseInt(year)-2);*/
					l1="upto  11/"+year1;
					l2="Anticipated  12/"+year1+" to 3/"+year0;
					l3="upto 11/"+year1;
					l4="Anticipated  12/"+year1+" to 3/"+year0;
					l5="upto  11/"+year1;
					l6="Anticipated  12/"+year1+" to 3/"+year0;
					l7="upto  11/"+year1+" (4+6+8)";
					l8="Anticipated  12/"+year1+" to 3/"+year0+" (5+7+9)";
				} else {
				/*	financialyear = year + "-" + year1;
					financialyear1 = (parseInt(year)-1) + "-" + (parseInt(year1)-1);
					financialyear2 = (parseInt(year)-2) + "-" + (parseInt(year1)-2);*/
					l1="upto 11/"+year0;
					l2="Anticipated 12/"+year1+" to 3/"+year0;
					l3="upto  11/"+year1;
					l4="Anticipated   12/"+year1+" to 3/"+year0;
					l5="upto  11/"+year1;
					l6="Anticipated   12/"+year1+" to 3/"+year0;
					l7="upto  11/"+year1+" (4+6+8)";
					l8="Anticipated  12/"+year1+" to 3/"+year0+" (5+7+9)";
					
					
				}
				System.out.println("l1"+l1+"l2"+l2+"l3"+l3+"l4"+l4+"l5"+l5+"l6"+l6+"l7"+l7+"l8"+l8);
			}
			else{
			 l1="Actuals for the Last Year       ("+l1+")";
			 l2="BE for the Year ( "+l2+" )";
			 l3="Actuals for the Period Apr to Nov ( "+l3 +")";
			 l4="Anticipated for the Period Dec to Mar ( "+l4 +")";
			l5="RE for the Year ( "+l5+")";
			l6="BE for Next Year ( "+l6+")";
			}
		/*
			//qry=sb.toString();
			 l1="Actuals for the Last Year       ("+l1+")";
			 l2="BE for the Year ( "+l2+" )";
			 l3="Actuals for the Period Apr to Nov ( "+l3 +")";
			 l4="Anticipated for the Period Dec to Mar  ( "+l4 +")";
			l5="RE for the Year ( "+l5+")";
			l6="BE for Next Year ( "+l6+")";*/
			System.out.println("qry:::"+qry);
			/*System.out.println("l1:::"+l1);
			System.out.println("l2:::"+l2);
			System.out.println("l3:::"+l3);
			System.out.println("l4:::"+l4);
			System.out.println("l5:::"+l5);
			System.out.println("l6:::"+l6);*/
			try {
				
				
				 if(formattyp.equalsIgnoreCase("1")  )
				{
					if(detail.equalsIgnoreCase("Detail")){
					
					System.out.println("calling format 1 servlet...");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/DetailsFormaT_Report_1.jasper"));
					}
					else if(detail.equalsIgnoreCase("Abstract")){
						System.out.println("calling format 1 servlet...");
						reportFile = new File(getServletContext().getRealPath(
								"/org/FAS/FAS1/CivilBudget/jasper/AbstractFormaT_Report_1.jasper"));	
					}
				}
										
				else if(formattyp.equalsIgnoreCase("2") )
				{
					
				if (detail.equalsIgnoreCase("Detail")) {
					//System.out.println("calling servlet.Detail..");
					reportFile = new File(
							getServletContext()
									.getRealPath(
											"/org/FAS/FAS1/CivilBudget/jasper/Civil_Budget_Format_Report_2.jasper"));

				} else if (detail.equalsIgnoreCase("Abstract")) {

					//System.out.println("calling servlet Abstract...");
					reportFile = new File(
							getServletContext()
									.getRealPath(
											"/org/FAS/FAS1/CivilBudget/jasper/Abstract_Format_Report_2.jasper"));
				}

				}
				else if(formattyp.equalsIgnoreCase("3")){
					//System.out.println("calling servlet...");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/format_3_Report.jasper"));
				}
				else if(formattyp.equalsIgnoreCase("4")){
					//System.out.println("calling servlet...");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/Format_Report_4.jasper"));	
				}
				else if(formattyp.equalsIgnoreCase("5")){
					//System.out.println("calling servlet...");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/Format_REport_5.jasper"));	
				}
				else if(formattyp.equalsIgnoreCase("6")){
					//System.out.println("calling servlet...in format 6---");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/CivilBudget_Format_Report_6.jasper"));	
				}
				else if(formattyp.equalsIgnoreCase("7")){
					//System.out.println("calling servlet...");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/Format_Report_7.jasper"));	
				
			}
			else if(formattyp.equalsIgnoreCase("8")){
				//System.out.println("calling servlet...");
				reportFile = new File(getServletContext().getRealPath(
						"/org/FAS/FAS1/CivilBudget/jasper/Report_Format_8 .jasper"));	
			
		}
				else if(formattyp.equalsIgnoreCase("9")){
					
			 if(detail.equalsIgnoreCase("Detail")){
						//System.out.println("calling servlet Deatils 9...");
						reportFile = new File(getServletContext().getRealPath(
								"/org/FAS/FAS1/CivilBudget/jasper/DetailsFormaT_Report_9.jasper"));	
					}
			 else if(detail.equalsIgnoreCase("Abstract")){
				//	System.out.println("calling servlet Abstract 9...");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/AbstractFormaT_Report_9.jasper"));}
				}
				/*else if(formattyp.equalsIgnoreCase("9"))
				{
					if(detail.equalsIgnoreCase("Detail")){
						
						System.out.println("calling format 1 servlet...");
						reportFile = new File(getServletContext().getRealPath(
								"/org/FAS/FAS1/CivilBudget/jasper/DetailsFormaT_Report_1.jasper"));
						}
						else if(detail.equalsIgnoreCase("Abstract")){
							System.out.println("calling format 1 servlet...");
							reportFile = new File(getServletContext().getRealPath(
									"/org/FAS/FAS1/CivilBudget/jasper/AbstractFormaT_Report_.jasper"));	
						}
				}*/
				else if(formattyp.equalsIgnoreCase("10"))
				{
					if(detail.equalsIgnoreCase("Detail")){
						
						//System.out.println("calling format 10 servlet..."+detail);
						reportFile = new File(getServletContext().getRealPath(
								"/org/FAS/FAS1/CivilBudget/jasper/DetailsFormaT_Report_10.jasper"));
						}
						else if(detail.equalsIgnoreCase("Abstract")){
						//	System.out.println("calling format 10 servlet..."+detail);
							reportFile = new File(getServletContext().getRealPath(
									"/org/FAS/FAS1/CivilBudget/jasper/AbstractFormaT_Report_10.jasper"));	
						}
				}
			
			else if(formattyp.equalsIgnoreCase("11")){
				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/CivilBudget/jasper/Report_11_Format.jasper"));
			}
				else if(formattyp.equalsIgnoreCase("12")){
				//	System.out.println("calling servlet...in format 12---");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/CivilBudget_Format_Report_12.jasper"));	
				}
				else if(formattyp.equalsIgnoreCase("13")){
				//	System.out.println("calling servlet...in format 12A---");
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/CivilBudget_Format_Report_12A.jasper"));	
				}
				System.out.println("formattyp:::"+formattyp);
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();
				
				map.put("qry", qry);
				map.put("heading", heading);
				map.put("year", year);
				map.put("title",title);
				map.put("group",group);
				map.put("l1", l1);
				map.put("l2",l2);
				map.put("l3", l3);
				map.put("l4", l4);
				map.put("l5", l5);
				map.put("l6", l6);
				map.put("l7", l7);
				map.put("l8", l8);
				map.put("unit_description", Acc_unit_Name);
									
				System.out.println("reportFile "+reportFile);
				//System.out.println("qry"+qry);
				//System.out.println("acc_unit_Name "+Acc_unit_Name);
				//System.out.println("heading  "+heading);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				//System.out.println("upto");
				
				//String rtype = "PDF";// request.getParameter("cmbReportType");
				//System.out.println(rtype);
				if (rtype.equalsIgnoreCase("HTML")) {
					response.setContentType("text/html");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"Civil_Budget_Format_Report.html\"");
					PrintWriter out = response.getWriter();
					JRHtmlExporter exporter = new JRHtmlExporter();
					// File f=new
					// File(getServletContext().getRealPath("/WEB-INF/Report/"));
					// exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
					// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
					// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
					exporter
							.setParameter(
									JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
									false);
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,
							jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
					exporter.exportReport();
					out.flush();
					out.close();
				} else if (rtype.equalsIgnoreCase("PDF")) {
					System.out.println(rtype);
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				//	System.out.println("Length  " + buf.length);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					// response.setHeader("content-disposition",
					// "inline;filename=OpenActionItems.pdf");
					// response.setContentType("application/force-download");

					response.setHeader("Content-Disposition",
							"attachment;filename=\"Civil_Budget_Format_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				}
				 else if (rtype.equalsIgnoreCase("EXCEL")) {        	   
	             	   System.out.println("test rtype"+rtype);             	   
	             	   try{
	            			response.setContentType("application/vnd.ms-excel");
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"Civil_Budget_Format_Report.xls\"");
	            		//String filename="c:/hello.xls" ;
	            		HSSFWorkbook hwb=new HSSFWorkbook();
	            		HSSFSheet sheet =  hwb.createSheet("new sheet");

	            		HSSFRow rowheading=   sheet.createRow((short)0);
	            		rowheading.createCell((short) 6).setCellValue(heading);
	            		HSSFRow rowhead1=   sheet.createRow((short)1);
	            		//rowhead1.createCell((short) 1).setCellValue("Unit Name");
	            		rowhead1.createCell((short) 1).setCellValue(Acc_unit_Name);
	            		rowhead1.createCell((short) 7).setCellValue("Financial Year : ");
	            		rowhead1.createCell((short) 9).setCellValue(fin_year);
	            		HSSFRow rowhead=   sheet.createRow((short)2);
	            		//Lakshmi------
	            		 if(formattyp.equalsIgnoreCase("1")){
	            			 if(detail.equalsIgnoreCase("Detail")){   	
	     	            		rowhead.createCell((short) 0).setCellValue("Sl.No");
	     	            		rowhead.createCell((short) 1).setCellValue("Head Description");
	     	            		rowhead.createCell((short) 2).setCellValue("Group");
	     	            		rowhead.createCell((short) 3).setCellValue(l1);
	     	            		rowhead.createCell((short) 4).setCellValue(l2);
	     	            		rowhead.createCell((short) 5).setCellValue(l3);
	     	            		rowhead.createCell((short) 6).setCellValue(l4);
	     	            		rowhead.createCell((short) 7).setCellValue(l5);
	     	            		rowhead.createCell((short) 8).setCellValue("Variation between BE and RE");
	     	            		rowhead.createCell((short) 9).setCellValue("Reason for Variation");
	     	            		rowhead.createCell((short) 10).setCellValue(l6);
	     	            		rowhead.createCell((short) 11).setCellValue("Reason for Variation if any between RE for the Year and the next Year");
	     	            		
	            			 }else  if(detail.equalsIgnoreCase("Abstract")){
	            				 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Group");
		     	            		rowhead.createCell((short) 2).setCellValue(l1);
		     	            		rowhead.createCell((short) 3).setCellValue(l2);
		     	            		rowhead.createCell((short) 4).setCellValue(l3);
		     	            		rowhead.createCell((short) 5).setCellValue(l4);
		     	            		rowhead.createCell((short) 6).setCellValue(l5);
		     	            		rowhead.createCell((short) 7).setCellValue("Variation between BE and RE");
		     	            		rowhead.createCell((short) 8).setCellValue("Reason for Variation");
		     	            		rowhead.createCell((short) 9).setCellValue(l6);
		     	            		rowhead.createCell((short) 10).setCellValue("Reason for Variation if any between RE for the Year and the next Year");
	            			 }
	             	   
	            		 } if(formattyp.equalsIgnoreCase("2")){

	            			 if(detail.equalsIgnoreCase("Detail")){
	            			 rowhead.createCell((short) 0).setCellValue("Sl.No");
	     	            		rowhead.createCell((short) 1).setCellValue("Account Head code");
	     	            		rowhead.createCell((short) 2).setCellValue("Group");
	     	            		rowhead.createCell((short) 3).setCellValue("Actuals for the Last Year");
	     	            		rowhead.createCell((short) 4).setCellValue("BE for the Year");
	     	            		rowhead.createCell((short) 5).setCellValue("Actuals for the Period Apr to Nov");
	     	            		rowhead.createCell((short) 6).setCellValue("Anticipated for the period Dec to Mar");
	     	            		rowhead.createCell((short) 7).setCellValue("RE for the Year");
	     	            		rowhead.createCell((short) 8).setCellValue("Variation Between BE and RE");
	     	            		rowhead.createCell((short) 9).setCellValue("Reson for variation");
	     	            		rowhead.createCell((short) 10).setCellValue("BE for Next Year");
	     	            		rowhead.createCell((short) 11).setCellValue("Variation Between RE and BE");
     	
	            			 }else if(detail.equalsIgnoreCase("Abstract")){
	            				 
	            				 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Group");
		     	            		rowhead.createCell((short) 2).setCellValue("Actuals for the Last Year");
		     	            		rowhead.createCell((short) 3).setCellValue("BE for the Year");
		     	            		rowhead.createCell((short) 4).setCellValue("Actuals for the Period Apr to Nov");
		     	            		rowhead.createCell((short) 5).setCellValue("Anticipated for the period Dec to Mar");
		     	            		rowhead.createCell((short) 6).setCellValue("RE for the Year");
		     	            		rowhead.createCell((short) 7).setCellValue("Variation Between BE and RE");
		     	            		rowhead.createCell((short) 8).setCellValue("Reson for variation");
		     	            		rowhead.createCell((short) 9).setCellValue("BE for Next Year");
		     	            		rowhead.createCell((short) 10).setCellValue("Variation Between RE and BE");
	            			 } 
	            		 }
	            		 else if(formattyp.equalsIgnoreCase("3")){
	            			 rowhead.createCell((short) 0).setCellValue("Sl.No");
	     	            		rowhead.createCell((short) 1).setCellValue("Name of Category");
	     	            		rowhead.createCell((short) 2).setCellValue("Time Scale of Pay (Specify O.G/S.G/Spl.Gr");
	     	            		rowhead.createCell((short) 3).setCellValue("No of Sanctioned Posts");
	     	            		rowhead.createCell((short) 4).setCellValue("No of Incumbents in Roll");
	     	            		rowhead.createCell((short) 5).setCellValue("No of Vacant Posts");
	     	            		rowhead.createCell((short) 6).setCellValue("As on Begining of the Year  Basic Pay");
	     	            		rowhead.createCell((short) 7).setCellValue("As on Begining of the Year 50% B.P");
	     	            		rowhead.createCell((short) 8).setCellValue("Increment Date");
	     	            		rowhead.createCell((short) 9).setCellValue(" Increment Amount");
	     	            		rowhead.createCell((short) 10).setCellValue("Pay After Increment Basic Pay");
	     	            		rowhead.createCell((short) 11).setCellValue("Pay After Increment 50% B.P");
	     	            		rowhead.createCell((short) 12).setCellValue("Total Basic Pay plus 50% DR pay for 12 Months During the Year");
	     	            		rowhead.createCell((short) 13).setCellValue("D.A @ 32% on Col.No.13");
	     	            		rowhead.createCell((short) 14).setCellValue("Other Allowances for 12 Months ");
	     	            		rowhead.createCell((short) 15).setCellValue("Total ");
	     	            		rowhead.createCell((short) 16).setCellValue("Date of Retirement During the Year");
	     	            		
								            		 }
							 else if(formattyp.equalsIgnoreCase("4")){
								 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Name of Employee");
		     	            		rowhead.createCell((short) 2).setCellValue("Designation ");
		     	            		rowhead.createCell((short) 3).setCellValue("Superannuation Amt Paid Date of Retirement");
		     	            		rowhead.createCell((short) 4).setCellValue("Superannuation Amt Paid Encashment of L.S ");
		     	            		rowhead.createCell((short) 5).setCellValue("Superannuation Amt Paid Commutation of Pension ");
		     	            		rowhead.createCell((short) 6).setCellValue("Superannuation Amt Paid Gratuity");
		     	            		rowhead.createCell((short) 7).setCellValue("Voluntary Retirement Amt Paid Date of Retirement");
		     	            		rowhead.createCell((short) 8).setCellValue("Voluntary Retirement Amt Paid Encashment of L.S ");
		     	            		rowhead.createCell((short) 9).setCellValue("Voluntary Retirement Amt Paid Commutation of Pension ");
		     	            		rowhead.createCell((short) 10).setCellValue("Voluntary Retirement Amt Paid Gratuity");
		     	            		rowhead.createCell((short) 11).setCellValue("Superannuation Amt Not Paid Encashment of L.S ");
		     	            		rowhead.createCell((short) 12).setCellValue("Superannuation Amt Not Paid Commutation of Pension ");
		     	            		rowhead.createCell((short) 13).setCellValue("Superannuation Amt Not Paid Gratuity");
		     	            		rowhead.createCell((short) 14).setCellValue("Voluntary Retirement Amt Not Paid Encashment of L.S ");
		     	            		rowhead.createCell((short) 15).setCellValue("Voluntary Retirement Amt Not Paid Commutation of Pension ");
		     	            		rowhead.createCell((short) 16).setCellValue("Voluntary Retirement Amt Not Paid Gratuity");       			 
								            		 }
							 else if(formattyp.equalsIgnoreCase("5")){
								 
								 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Name of Employee");
		     	            		rowhead.createCell((short) 2).setCellValue("Designation ");
		     	            		rowhead.createCell((short) 3).setCellValue("Date of Retirement");
		     	            		rowhead.createCell((short) 4).setCellValue("Superannuation Amt Paid Encashment of L.S ");
		     	            		rowhead.createCell((short) 5).setCellValue("Superannuation Amt Paid Commutation of Pension ");
		     	            		rowhead.createCell((short) 6).setCellValue("Superannuation Amt Paid Gratuity");
		     	            		rowhead.createCell((short) 7).setCellValue("Voluntary Retirement Amt Paid Date of Retirement");
		     	            		rowhead.createCell((short) 8).setCellValue("Voluntary Retirement Amt Paid Encashment of L.S ");
		     	            		rowhead.createCell((short) 9).setCellValue("Voluntary Retirement Amt Paid Commutation of Pension ");
		     	            		rowhead.createCell((short) 10).setCellValue("Voluntary Retirement Amt Paid Gratuity");
		    							 
								 
							 }
							 else if(formattyp.equalsIgnoreCase("6")){
								 
								 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Category");
		     	            		rowhead.createCell((short) 2).setCellValue("No of Pensioners");
		     	            		rowhead.createCell((short) 3).setCellValue("Total Basic Pension "+l1);
		     	            		rowhead.createCell((short) 4).setCellValue("Total Basic Pension "+l2);
		     	            		rowhead.createCell((short) 5).setCellValue("Total D.A "+l3);
		     	            		rowhead.createCell((short) 6).setCellValue("Total D.A "+l4);
		     	            		rowhead.createCell((short) 7).setCellValue("Total Other Payment"+l5);
		     	            		rowhead.createCell((short) 8).setCellValue("Total Other Payment"+l6);
		     	            		rowhead.createCell((short) 9).setCellValue("Total "+l7);
		     	            		rowhead.createCell((short) 10).setCellValue("Total "+l8);
		     	            		rowhead.createCell((short) 11).setCellValue("No of Pensioners");
		     	            		rowhead.createCell((short) 12).setCellValue("Total Basic Pension");
		     	            		rowhead.createCell((short) 13).setCellValue("Total D.A");
		     	            		rowhead.createCell((short) 14).setCellValue("Total Other Payment");
		     	            		rowhead.createCell((short) 15).setCellValue("Total");
		     									 
							 }
							 else if(formattyp.equalsIgnoreCase("7")){
								 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Post Rank Name");
		     	            		rowhead.createCell((short) 2).setCellValue("Sanctioned Post (Upto the Year ) ");
		     	            		rowhead.createCell((short) 3).setCellValue("Diversion to Others(-)");
		     	            		rowhead.createCell((short) 4).setCellValue("Diversion from Others(+)");
		     	            		rowhead.createCell((short) 5).setCellValue("Total (Upto to the Year) ");
		     	            		rowhead.createCell((short) 6).setCellValue("Utilised (Upto Nov)");
		     	            		rowhead.createCell((short) 7).setCellValue("Vacant as on I st Dec");	 
								 
							 }
							 else if(formattyp.equalsIgnoreCase("8")){
								 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Nature of Expendidure ");
		     	            		rowhead.createCell((short) 2).setCellValue("Upto Nov CY");
		     	            		rowhead.createCell((short) 3).setCellValue("Anticipated Dec to End of CY ");
		     	            		rowhead.createCell((short) 4).setCellValue("Total");
		     	            		rowhead.createCell((short) 5).setCellValue("Next Year"); 
							 }else if(formattyp.equalsIgnoreCase("9"))
								{
									if(detail.equalsIgnoreCase("Detail")){
										
										rowhead.createCell((short) 0).setCellValue("Sl.No");
			     	            		rowhead.createCell((short) 1).setCellValue("Group");
			     	            		rowhead.createCell((short) 2).setCellValue(l1);
			     	            		rowhead.createCell((short) 3).setCellValue(l2);
			     	            		rowhead.createCell((short) 4).setCellValue(l3);
			     	            		rowhead.createCell((short) 5).setCellValue(l4);
			     	            		rowhead.createCell((short) 6).setCellValue(l5);
			     	            		rowhead.createCell((short) 7).setCellValue("Variation between BE and RE");
			     	            		rowhead.createCell((short) 8).setCellValue("Reason for Variation");
			     	            		rowhead.createCell((short) 9).setCellValue(l6);
			     	            		rowhead.createCell((short) 10).setCellValue("Reason for Variation if any between RE for the Year and the next Year");
									}else if(detail.equalsIgnoreCase("Abstract")){
										rowhead.createCell((short) 0).setCellValue("Sl.No");
			     	            		rowhead.createCell((short) 1).setCellValue("Group");
			     	            		rowhead.createCell((short) 2).setCellValue(l1);
			     	            		rowhead.createCell((short) 3).setCellValue(l2);
			     	            		rowhead.createCell((short) 4).setCellValue(l3);
			     	            		rowhead.createCell((short) 5).setCellValue(l4);
			     	            		rowhead.createCell((short) 6).setCellValue(l5);
			     	            		rowhead.createCell((short) 7).setCellValue("Variation between BE and RE");
			     	            		rowhead.createCell((short) 8).setCellValue("Reason for Variation");
			     	            		rowhead.createCell((short) 9).setCellValue(l6);
			     	            		rowhead.createCell((short) 10).setCellValue("Reason for Variation if any between RE for the Year and the next Year");	
									}
								}
							 else if(formattyp.equalsIgnoreCase("10"))
								{
									if(detail.equalsIgnoreCase("Detail")){
										
										rowhead.createCell((short) 0).setCellValue("Sl.No");
			     	            		rowhead.createCell((short) 1).setCellValue("Head Description");
			     	            		rowhead.createCell((short) 2).setCellValue("Group");
			     	            		rowhead.createCell((short) 3).setCellValue(l1);
			     	            		rowhead.createCell((short) 4).setCellValue(l2);
			     	            		rowhead.createCell((short) 5).setCellValue(l3);
			     	            		rowhead.createCell((short) 6).setCellValue(l4);
			     	            		rowhead.createCell((short) 7).setCellValue(l5);
			     	            		rowhead.createCell((short) 8).setCellValue("Variation between BE and RE");
			     	            		rowhead.createCell((short) 9).setCellValue("Reason for Variation");
			     	            		rowhead.createCell((short) 10).setCellValue(l6);
			     	            		rowhead.createCell((short) 11).setCellValue("Reason for Variation if any between RE for the Year and the next Year");
										
									}else if(detail.equalsIgnoreCase("Abstract")){
										rowhead.createCell((short) 0).setCellValue("Sl.No");
			     	            		rowhead.createCell((short) 1).setCellValue("Group");
			     	            		rowhead.createCell((short) 2).setCellValue(l1);
			     	            		rowhead.createCell((short) 3).setCellValue(l2);
			     	            		rowhead.createCell((short) 4).setCellValue(l3);
			     	            		rowhead.createCell((short) 5).setCellValue(l4);
			     	            		rowhead.createCell((short) 6).setCellValue(l5);
			     	            		rowhead.createCell((short) 7).setCellValue("Variation between BE and RE");
			     	            		rowhead.createCell((short) 8).setCellValue("Reason for Variation");
			     	            		rowhead.createCell((short) 9).setCellValue(l6);
			     	            		rowhead.createCell((short) 10).setCellValue("Reason for Variation if any between RE for the Year and the next Year");
									}
								}
							 else if(formattyp.equalsIgnoreCase("11"))
								{
								 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Divn Code");
		     	            		rowhead.createCell((short) 2).setCellValue("Type of Vehicle");
		     	            		rowhead.createCell((short) 3).setCellValue("Regn No");
		     	            		rowhead.createCell((short) 4).setCellValue("No of Kms Done");
		     	            		rowhead.createCell((short) 5).setCellValue("Age of Vehicle");
		     	            		rowhead.createCell((short) 6).setCellValue("Whether Fit for Condemnation now,Probable Date of Condemnation");
		     	            		rowhead.createCell((short) 7).setCellValue("If not Condemnation now,Probable Date of Condemnation");
		     	            		rowhead.createCell((short) 8).setCellValue("Last Year Salary");
		     	            		rowhead.createCell((short) 9).setCellValue("Last Year Fuel / Material");
		     	            		rowhead.createCell((short) 10).setCellValue("Last Year Ordinary Repairs");
		     	            		rowhead.createCell((short) 11).setCellValue("Last Year Special Repairs");
		     	            		rowhead.createCell((short) 12).setCellValue("Last Year Total Cost");
		     	            		rowhead.createCell((short) 13).setCellValue("Current Year Salary");
		     	            		rowhead.createCell((short) 14).setCellValue("Current Year Fuel / Material");
		     	            		rowhead.createCell((short) 15).setCellValue("Current Year Ordinary Repairs");
		     	            		rowhead.createCell((short) 16).setCellValue("Current Year Special Repairs");
		     	            		rowhead.createCell((short) 17).setCellValue("Current Year Total Cost");
		     	            		rowhead.createCell((short) 18).setCellValue("Next Year Salary");
		     	            		rowhead.createCell((short) 19).setCellValue("Next Year Fuel / Material");
		     	            		rowhead.createCell((short) 20).setCellValue("Next Year Ordinary Repairs");
		     	            		rowhead.createCell((short) 21).setCellValue("Next Year Special Repairs");
		     	            		rowhead.createCell((short) 22).setCellValue("Next Year Total Cost");
								}
							 else if(formattyp.equalsIgnoreCase("12"))
								{
								 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Vehicles");
		     	            		rowhead.createCell((short) 2).setCellValue("Jobs Pending");
		     	            		rowhead.createCell((short) 3).setCellValue("Jobs Entrusted Previous Year");
		     	            		rowhead.createCell((short) 4).setCellValue("Jobs Entrusted Current Year");
		     	            		rowhead.createCell((short) 5).setCellValue("Jobs Complited Previous Year");
		     	            		rowhead.createCell((short) 6).setCellValue("Jobs Complited Current Year");
		     	            		rowhead.createCell((short) 7).setCellValue("Direct Labour (Cost)Previous Year");
		     	            		rowhead.createCell((short) 8).setCellValue("Direct Labour (Cost)Current Year");
		     	            		rowhead.createCell((short) 9).setCellValue("InDirect Labour (Cost)Previous Year");
		     	            		rowhead.createCell((short) 10).setCellValue("InDirect Labour (Cost)Current Year");
		     	            		rowhead.createCell((short) 11).setCellValue("Work Turned Previous Year");
		     	            		rowhead.createCell((short) 12).setCellValue("Work Turned Current Year");
		     	            		rowhead.createCell((short) 13).setCellValue("Cost of Spares Previous Year");
		     	            		rowhead.createCell((short) 14).setCellValue("Cost of Spares Current Year");
		     	            		rowhead.createCell((short) 15).setCellValue("Expenditure of Others Previous Year");
		     	            		rowhead.createCell((short) 16).setCellValue("Expenditure of Others Current Year");
		     	            		rowhead.createCell((short) 17).setCellValue("Total Cost Previous Year");
		     	            		rowhead.createCell((short) 18).setCellValue("Total Cost Current Year");	
								}
							 else if(formattyp.equalsIgnoreCase("13"))
								{
								 rowhead.createCell((short) 0).setCellValue("Sl.No");
		     	            		rowhead.createCell((short) 1).setCellValue("Vehicles");
		     	            		rowhead.createCell((short) 2).setCellValue("Pending No. 1a");
		     	            		rowhead.createCell((short) 3).setCellValue("Pending Value. 1b");
		     	            		rowhead.createCell((short) 4).setCellValue("TDA Raised No.2a");
		     	            		rowhead.createCell((short) 5).setCellValue("TDA Raised Value 2b");
		     	            		rowhead.createCell((short) 6).setCellValue("TDA Raised No. 3a");
		     	            		rowhead.createCell((short) 7).setCellValue("TDA Raised Value 4b");
		     	            		rowhead.createCell((short) 8).setCellValue("TDA Accepted and Adjusted No.4a");
		     	            		rowhead.createCell((short) 9).setCellValue("TDA Accepted and Adjusted Value 4b");
		     	            		rowhead.createCell((short) 10).setCellValue("TDA Accepted and Adjusted No.5a");
		     	            		rowhead.createCell((short) 11).setCellValue("TDA Accepted and Adjusted Value 5b");
		     	            		rowhead.createCell((short) 12).setCellValue("Value of Jobs in Progress No.6a");
		     	            		rowhead.createCell((short) 13).setCellValue("Value of Jobs in Progress Value 6b");
		     	            		rowhead.createCell((short) 14).setCellValue("Value of Jobs in Progress Expected Date 7");
								}
 
	            		ServletOutputStream fileOut=null;
	            		  System.out.println("test rtype >>>>>>>>>>"+qry);      
	                PreparedStatement ps2=con.prepareStatement(qry);
	              ResultSet rs2=ps2.executeQuery();
	              System.out.println("test excel repot type >>>>>>>>>>"+rs2);    
	              int jj=1;
	               int j=3;
	               
	               double actfirst=0,beyr=0,actualfr=0,anti=0,recurrect=0, varia=0, benext=0;
	                while(rs2.next())
	                {
	             	 
	            		HSSFRow row=   sheet.createRow((short)j);
	            		 if(formattyp.equalsIgnoreCase("1")){
	            			 if(detail.equalsIgnoreCase("Detail")){
	            		row.createCell((short) 0).setCellValue(jj);
	            		row.createCell((short) 1).setCellValue(rs2.getString("head"));
	            		row.createCell((short) 2).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
	            		actfirst=actfirst+rs2.getDouble("AC_FR_LST_YR");
	            		beyr=beyr+rs2.getDouble("BE_FOR_THE_YEAR");
	            		actualfr=actualfr+rs2.getDouble("ACTUAL_FR_PERIOD_APR_NOV");
	            		anti=anti+rs2.getDouble("ANTICIPATED_FR_PERIOD_DEC_MAR");
	            		recurrect=recurrect+rs2.getDouble("RE_FOR_YEAR");
	            		 varia=varia+rs2.getDouble("VARIATION_BETWEN_BE_RE");
	            		 benext=benext+rs2.getDouble("BE_FOR_NEXT_YEAR");
	            		row.createCell((short) 3).setCellValue(rs2.getDouble("AC_FR_LST_YR"));
	            		row.createCell((short) 4).setCellValue(rs2.getDouble("BE_FOR_THE_YEAR"));
	            		row.createCell((short) 5).setCellValue(rs2.getDouble("ACTUAL_FR_PERIOD_APR_NOV"));
	            		row.createCell((short) 6).setCellValue(rs2.getDouble("ANTICIPATED_FR_PERIOD_DEC_MAR"));
	            		row.createCell((short) 7).setCellValue(rs2.getDouble("RE_FOR_YEAR"));
	            		row.createCell((short) 8).setCellValue(rs2.getDouble("VARIATION_BETWEN_BE_RE"));
	            		row.createCell((short) 9).setCellValue(rs2.getString("REASON_FOR_VARIATION"));
	            		row.createCell((short) 10).setCellValue(rs2.getDouble("BE_FOR_NEXT_YEAR"));
	            		row.createCell((short) 11).setCellValue(rs2.getString("VARIATION_BTWN_REYR_AND_NXTYR"));	            		
	            		 }
	            		else  if(detail.equalsIgnoreCase("Abstract")){
	            			row.createCell((short) 0).setCellValue(jj);
		            		row.createCell((short) 1).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
		            		row.createCell((short) 2).setCellValue(rs2.getDouble("AC_FR_LST_YR"));
		            		row.createCell((short) 3).setCellValue(rs2.getDouble("BE_FOR_THE_YEAR"));
		            		row.createCell((short) 4).setCellValue(rs2.getDouble("ACTUAL_FR_PERIOD_APR_NOV"));
		            		row.createCell((short) 5).setCellValue(rs2.getDouble("ANTICIPATED_FR_PERIOD_DEC_MAR"));
		            		row.createCell((short) 6).setCellValue(rs2.getDouble("RE_FOR_YEAR"));
		            		row.createCell((short) 7).setCellValue(rs2.getDouble("VARIATION_BETWEN_BE_RE"));
		            		row.createCell((short) 8).setCellValue(rs2.getString("REASON_FOR_VARIATION"));
		            		row.createCell((short) 9).setCellValue(rs2.getDouble("BE_FOR_NEXT_YEAR"));
		            		row.createCell((short) 10).setCellValue(rs2.getString("VARIATION_BTWN_REYR_AND_NXTYR"));	 	 
	            				 
	            			 } 
	            		 }else if(formattyp.equalsIgnoreCase("2")){
	            			 if(detail.equalsIgnoreCase("Detail")){
	     	            		row.createCell((short) 0).setCellValue(jj);
	     	            		actfirst=actfirst+rs2.getDouble("AC_FR_LST_YR");
	    	            		beyr=beyr+rs2.getDouble("BE_fr_Yr");
	    	            		actualfr=actualfr+rs2.getDouble("ACTUAL_FR_PERIOD_APR_NOV");
	    	            		/*anti=anti+rs2.getDouble("ANTICIPATED_FR_PERIOD_DEC_MAR");
	    	            		recurrect=recurrect+rs2.getDouble("RE_FOR_YEAR");
	    	            		 varia=varia+rs2.getDouble("VARIATION_BETWEN_BE_RE");
	    	            		 benext=benext+rs2.getDouble("BE_FOR_NEXT_YEAR");*/
	     	            		row.createCell((short) 1).setCellValue(rs2.getString("head_of_account"));
	     	            		row.createCell((short) 2).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
	     	            		row.createCell((short) 3).setCellValue(rs2.getDouble("Ac_fr_lst_yr"));
	     	            		row.createCell((short) 4).setCellValue(rs2.getDouble("BE_fr_Yr"));
	     	            		row.createCell((short) 5).setCellValue(rs2.getDouble("Actual_fr_Period_Apr_Nov"));
	     	            		row.createCell((short) 6).setCellValue(0.00);
	     	            		row.createCell((short) 7).setCellValue(0.00);
	     	            		row.createCell((short) 8).setCellValue(0);
	     	            		row.createCell((short) 9).setCellValue("-");
	     	            		row.createCell((short) 10).setCellValue(0.00);
	     	            		row.createCell((short) 11).setCellValue("-");            		
	     	            		 }
	     	            		else if(detail.equalsIgnoreCase("Abstract")){
	     	            			row.createCell((short) 0).setCellValue(jj);
	     		            		row.createCell((short) 1).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
	     		            		row.createCell((short) 2).setCellValue(rs2.getDouble("AC_FR_LST_YR"));
	     		            		row.createCell((short) 3).setCellValue(rs2.getDouble("BE_fr_Yr"));
	     		            		row.createCell((short) 4).setCellValue(rs2.getDouble("Actual_fr_Period_Apr_Nov"));
	     		            		row.createCell((short) 5).setCellValue(0.00);
	     		            		row.createCell((short) 6).setCellValue(0.00);
	     		            		row.createCell((short) 7).setCellValue(0);
	     		            		row.createCell((short) 8).setCellValue("-");
	     		            		row.createCell((short) 9).setCellValue(0.00);
	     		            		row.createCell((short) 10).setCellValue("-");	 	 
	     	            				 
	     	            			 } 
	     	            		 }
	            		 else if(formattyp.equalsIgnoreCase("3")){
	            			// System.out.println("inside threee  ");
	            			 row.createCell((short) 0).setCellValue(jj);
			            		row.createCell((short) 1).setCellValue(rs2.getString("NAME_OF_CATEGORY"));
			            		row.createCell((short) 2).setCellValue(rs2.getString("TIME_SCALE_OF_PAY"));
			            		row.createCell((short) 3).setCellValue(rs2.getInt("NO_OF_SANCTIONED_POSTS"));
			            		row.createCell((short) 4).setCellValue(rs2.getInt("NO_OF_INCUMBENTS_IN_ROLL"));
			            		row.createCell((short) 5).setCellValue(rs2.getInt("NO_OF_VACANT_POSTS"));
			            		row.createCell((short) 6).setCellValue(rs2.getDouble("BEGINING_OF_THE_YEAR_BASIC_PAY"));
			            		row.createCell((short) 7).setCellValue(rs2.getDouble("AFTER_INCREMENT_50_BP"));
			            		row.createCell((short) 8).setCellValue(rs2.getString("INCREMENT_DATE"));
			            		row.createCell((short) 9).setCellValue(rs2.getDouble("INCREMENT_AMOUNT"));
			            		row.createCell((short) 10).setCellValue(rs2.getDouble("AFTER_INCREMENT_BASIC_PAY"));
			            		row.createCell((short) 11).setCellValue(rs2.getDouble("AFTER_INCREMENT_50_BP"));
			            		row.createCell((short) 12).setCellValue(rs2.getDouble("BASIC_PAY_50_DEARNESS_PAY"));
			            		row.createCell((short) 13).setCellValue(rs2.getDouble("DA_32_ON_COL_NO_13"));
			            		row.createCell((short) 14).setCellValue(rs2.getDouble("OTHER_ALLOWANCES"));
			            		row.createCell((short) 15).setCellValue(rs2.getDouble("TOTAL"));
			            		row.createCell((short) 16).setCellValue(rs2.getString("DATE_OF_RETIREMENT"));	
	            			 
			            		 }
	            		 else if(formattyp.equalsIgnoreCase("4")){
			            			 row.createCell((short) 0).setCellValue(jj);
					            		row.createCell((short) 1).setCellValue(rs2.getString("NAME_OF_EMPLOYEE"));
					            		row.createCell((short) 2).setCellValue(rs2.getString("DESIGNATION"));
					            		row.createCell((short) 3).setCellValue(rs2.getString("DATE_OF_RETIREMENT"));
					            		row.createCell((short) 4).setCellValue(rs2.getDouble("S_AMT_PAID_UPTO_NOV_E_OF_LS"));
					            		row.createCell((short) 5).setCellValue(rs2.getDouble("S_AMT_PID_UPTO_NOV_C_OF_PNSN"));
					            		row.createCell((short) 6).setCellValue(rs2.getDouble("S_AMT_PAID_UPTO_NOV_GRATUITY"));
					            		row.createCell((short) 7).setCellValue(rs2.getString("VRS_DATE_OF_RETIREMENT"));
					            		row.createCell((short) 8).setCellValue(rs2.getDouble("VR_AMT_PAID_UPTO_NOV_E_OF_LS"));
					            		row.createCell((short) 9).setCellValue(rs2.getDouble("VR_AMT_PAID_UPTO_NOV_C_OF_PNSN"));
					            		row.createCell((short) 10).setCellValue(rs2.getDouble("VR_AMT_PAID_UPTO_NOV_GRATUITY"));
					            		row.createCell((short) 11).setCellValue(rs2.getDouble("S_ANTICIPATED_AMT_E_OF_LS"));
					            		row.createCell((short) 12).setCellValue(rs2.getDouble("S_ANTICIPATED_AMT_C_OF_PNSN"));
					            		row.createCell((short) 13).setCellValue(rs2.getDouble("S_ANTICIPATED_AMT_GRATUITY"));
					            		row.createCell((short) 14).setCellValue(rs2.getDouble("VR_ANTICIPATED_AMT_E_OF_LS"));
					            		row.createCell((short) 15).setCellValue(rs2.getDouble("VR_ANTICIPATED_AMT_C_OF_PNSN"));
					            		row.createCell((short) 16).setCellValue(rs2.getDouble("VR_ANTICIPATED_AMT_GRATUITY"));	 		 
			            			 
			            			 
			            		 }
							 else if(formattyp.equalsIgnoreCase("5")){
								 row.createCell((short) 0).setCellValue(jj);
				            		row.createCell((short) 1).setCellValue(rs2.getString("EMPLOYEE_NAME"));
				            		row.createCell((short) 2).setCellValue(rs2.getString("DESIGNATION"));
				            		row.createCell((short) 3).setCellValue(rs2.getString("DATE_OF_RETIREMENT"));
				            		row.createCell((short) 4).setCellValue(rs2.getDouble("SA_ENCASHMENT_OF_LS"));
				            		row.createCell((short) 5).setCellValue(rs2.getDouble("SA_COMMUTATION_OF_PENSION"));
				            		row.createCell((short) 6).setCellValue(rs2.getDouble("SA_GRATUITY"));
				            		row.createCell((short) 7).setCellValue(rs2.getString("DATE_OF_RETIREMENT1"));
				            		row.createCell((short) 8).setCellValue(rs2.getDouble("VR_ENCASHMENT_OF_LS"));
				            		row.createCell((short) 9).setCellValue(rs2.getDouble("VR_COMMUTATION_OF_PENSION"));
				            		row.createCell((short) 10).setCellValue(rs2.getDouble("VR_GRATUITY"));

							 }
							 else if(formattyp.equalsIgnoreCase("6")){
								 row.createCell((short) 0).setCellValue(jj);
				            		row.createCell((short) 1).setCellValue(rs2.getString("CATEGORY"));
				            		row.createCell((short) 2).setCellValue(rs2.getInt("NO_OF_PENSIONERS"));
				            		row.createCell((short) 3).setCellValue(rs2.getDouble("TOTAL_BASIC_PENSION_UPTO_11Y"));
				            		row.createCell((short) 4).setCellValue(rs2.getDouble("TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1"));
				            		row.createCell((short) 5).setCellValue(rs2.getDouble("TOTAL_D_A_UPTO_11Y"));
				            		row.createCell((short) 6).setCellValue(rs2.getDouble("TOTAL_D_A_ANTICIPATED_12Y_3Y1"));
				            		row.createCell((short) 7).setCellValue(rs2.getDouble("TOTAL_OTHER_PAYMENT_UPTO_11Y"));
				            		row.createCell((short) 8).setCellValue(rs2.getDouble("TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1"));
				            		row.createCell((short) 9).setCellValue(rs2.getDouble("TOTAL_UPTO_11Y"));
				            		row.createCell((short) 10).setCellValue(rs2.getDouble("TOTAL_ANTICIPATED_12Y_3Y1"));
				            		row.createCell((short) 11).setCellValue(rs2.getInt("NO_OF_PENSIONERS1"));
				            		row.createCell((short) 12).setCellValue(rs2.getString("TOTAL_BASIC_PENSION"));
				            		row.createCell((short) 13).setCellValue(rs2.getDouble("TOTAL_D_A"));
				            		row.createCell((short) 14).setCellValue(rs2.getDouble("TOTAL_OTHER_PAYMENT"));
				            		row.createCell((short) 15).setCellValue(rs2.getDouble("TOTAL"));
							 }
							 else if(formattyp.equalsIgnoreCase("7")){
								 row.createCell((short) 0).setCellValue(jj);
				            		row.createCell((short) 1).setCellValue(rs2.getString("POST_RANK_NAME"));
				            		row.createCell((short) 2).setCellValue(rs2.getInt("SANCTIONED_POST"));
				            		row.createCell((short) 3).setCellValue(rs2.getInt("DIVERSION_TO_OTHERS"));
				            		row.createCell((short) 4).setCellValue(rs2.getInt("DIVERSION_FROM_OTHERS"));
				            		row.createCell((short) 5).setCellValue(rs2.getInt("TOTAL"));
				            		row.createCell((short) 6).setCellValue(rs2.getInt("UTILISED"));
				            		row.createCell((short) 7).setCellValue(rs2.getInt("VACANT")); 
							 }
							 else if(formattyp.equalsIgnoreCase("8")){
								 row.createCell((short) 0).setCellValue(jj);
				            		row.createCell((short) 1).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
				            		row.createCell((short) 2).setCellValue(rs2.getDouble("BE_FOR_THE_YEAR"));
				            		row.createCell((short) 3).setCellValue(rs2.getDouble("ANTICIPATED_DEC_TO_END_OF_CY"));
				            		row.createCell((short) 4).setCellValue(rs2.getDouble("TOTAL"));
				            		row.createCell((short) 5).setCellValue(rs2.getDouble("NEXT_YEAR"));	 
							 }else if(formattyp.equalsIgnoreCase("9"))
								{
									if(detail.equalsIgnoreCase("Detail")){
										row.createCell((short) 0).setCellValue(jj);
										actfirst=actfirst+rs2.getDouble("AC_FR_LST_YR");
			    	            		beyr=beyr+rs2.getDouble("BE_fr_Yr");
			    	            		actualfr=actualfr+rs2.getDouble("ACTUAL_FR_PERIOD_APR_NOV");
			     	            		row.createCell((short) 1).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
			     	            		row.createCell((short) 2).setCellValue(rs2.getDouble("Ac_fr_lst_yr"));
			     	            		row.createCell((short) 3).setCellValue(rs2.getDouble("BE_fr_Yr"));
			     	            		row.createCell((short) 4).setCellValue(rs2.getDouble("Actual_fr_Period_Apr_Nov"));
			     	            		row.createCell((short) 5).setCellValue(0.00);
			     	            		row.createCell((short) 6).setCellValue(0.00);
			     	            		row.createCell((short) 7).setCellValue(0);
			     	            		row.createCell((short) 8).setCellValue("-");
			     	            		row.createCell((short) 9).setCellValue(0.00);
			     	            		row.createCell((short) 10).setCellValue("-");      
									}else if(detail.equalsIgnoreCase("Abstract")){
										row.createCell((short) 0).setCellValue(jj);
			     	            		row.createCell((short) 1).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
			     	            		actfirst=actfirst+rs2.getDouble("AC_FR_LST_YR");
			    	            		beyr=beyr+rs2.getDouble("BE_fr_Yr");
			    	            		actualfr=actualfr+rs2.getDouble("ACTUAL_FR_PERIOD_APR_NOV");
			     	            		row.createCell((short) 2).setCellValue(rs2.getDouble("Ac_fr_lst_yr"));
			     	            		row.createCell((short) 3).setCellValue(rs2.getDouble("BE_fr_Yr"));
			     	            		row.createCell((short) 4).setCellValue(rs2.getDouble("Actual_fr_Period_Apr_Nov"));
			     	            		row.createCell((short) 5).setCellValue(0.00);
			     	            		row.createCell((short) 6).setCellValue(0.00);
			     	            		row.createCell((short) 7).setCellValue(0);
			     	            		row.createCell((short) 8).setCellValue("-");
			     	            		row.createCell((short) 9).setCellValue(0.00);
			     	            		row.createCell((short) 10).setCellValue("-");  
									}
								}
							 else if(formattyp.equalsIgnoreCase("10"))
								{
									if(detail.equalsIgnoreCase("Detail")){
										row.createCell((short) 0).setCellValue(jj);
										actfirst=actfirst+rs2.getDouble("AC_FR_LST_YR");
			    	            		beyr=beyr+rs2.getDouble("BE_fr_Yr");
			    	            		actualfr=actualfr+rs2.getDouble("ACTUAL_FR_PERIOD_APR_NOV");
			     	            		row.createCell((short) 1).setCellValue(rs2.getString("ACC_HEAD_CODE"));
			     	            		row.createCell((short) 2).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
			     	            		row.createCell((short) 3).setCellValue(rs2.getDouble("Ac_fr_lst_yr"));
			     	            		row.createCell((short) 4).setCellValue(rs2.getDouble("BE_fr_Yr"));
			     	            		row.createCell((short) 5).setCellValue(rs2.getDouble("Actual_fr_Period_Apr_Nov"));
			     	            		row.createCell((short) 6).setCellValue(0.00);
			     	            		row.createCell((short) 7).setCellValue(0.00);
			     	            		row.createCell((short) 8).setCellValue(0);
			     	            		row.createCell((short) 9).setCellValue("-");
			     	            		row.createCell((short) 10).setCellValue(0.00);
			     	            		row.createCell((short) 11).setCellValue("-");  
									}else if(detail.equalsIgnoreCase("Abstract")){
										row.createCell((short) 0).setCellValue(jj);
			     	            		row.createCell((short) 1).setCellValue(rs2.getString("BUDGET_GROUP_MAJOR"));
			     	            		row.createCell((short) 2).setCellValue(rs2.getDouble("Ac_fr_lst_yr"));
			     	            		row.createCell((short) 3).setCellValue(rs2.getDouble("BE_fr_Yr"));
			     	            		row.createCell((short) 4).setCellValue(rs2.getDouble("Actual_fr_Period_Apr_Nov"));
			     	            		row.createCell((short) 5).setCellValue(0.00);
			     	            		row.createCell((short) 6).setCellValue(0.00);
			     	            		row.createCell((short) 7).setCellValue(0);
			     	            		row.createCell((short) 8).setCellValue("-");
			     	            		row.createCell((short) 9).setCellValue(0.00);
			     	            		row.createCell((short) 10).setCellValue("-");  
									}
								}
							 else if(formattyp.equalsIgnoreCase("11"))
								{
								 row.createCell((short) 0).setCellValue(jj);
				            		row.createCell((short) 1).setCellValue(rs2.getInt("DIVN_CODE"));
				            		row.createCell((short) 2).setCellValue(rs2.getString("TYPE_OF_VEHICLE"));
				            		row.createCell((short) 3).setCellValue(rs2.getString("REGN_NO"));
				            		row.createCell((short) 4).setCellValue(rs2.getString("NO_OF_KMS_DONE"));
				            		row.createCell((short) 5).setCellValue(rs2.getString("AGE_OF_VEHICLE"));
				            		row.createCell((short) 6).setCellValue(rs2.getString("DATE_OF_CONDEMNATION_FIT"));
				            		row.createCell((short) 7).setCellValue(rs2.getString("DATE_OF_CONDEMNATION_NOT_FIT"));
				            		row.createCell((short) 8).setCellValue(rs2.getDouble("SALARY_LY"));
				            		row.createCell((short) 9).setCellValue(rs2.getString("FUEL_MATERIA_LY"));
				            		row.createCell((short) 10).setCellValue(rs2.getString("ORDINARY_REPAIRS_LY"));
				            		row.createCell((short) 11).setCellValue(rs2.getString("SPECIAL_REPAIRS_LY"));
				            		row.createCell((short) 12).setCellValue(rs2.getDouble("TOTAL_COST_LY"));
				            		row.createCell((short) 13).setCellValue(rs2.getDouble("SALARY_CY"));
				            		row.createCell((short) 14).setCellValue(rs2.getString("FUEL_MATERIA_CY"));
				            		row.createCell((short) 15).setCellValue(rs2.getString("ORDINARY_REPAIRS_CY"));
				            		row.createCell((short) 16).setCellValue(rs2.getString("SPECIAL_REPAIRS_CY"));
				            		row.createCell((short) 17).setCellValue(rs2.getDouble("TOTAL_COST_CY"));
				            		row.createCell((short) 18).setCellValue(rs2.getDouble("SALARY_NY"));
				            		row.createCell((short) 19).setCellValue(rs2.getString("FUEL_MATERIA_NY"));
				            		row.createCell((short) 20).setCellValue(rs2.getString("ORDINARY_REPAIRS_NY"));
				            		row.createCell((short) 21).setCellValue(rs2.getString("SPECIAL_REPAIRS_NY"));
				            		row.createCell((short) 22).setCellValue(rs2.getDouble("TOTAL_COST_NY"));
								}
							 else if(formattyp.equalsIgnoreCase("12"))
								{
								 row.createCell((short) 0).setCellValue(jj);
				            		row.createCell((short) 1).setCellValue(rs2.getString("VEHICLES"));
				            		row.createCell((short) 2).setCellValue(rs2.getString("JOBS_PENDING"));
				            		row.createCell((short) 3).setCellValue(rs2.getInt("PRE_YR_JOBS_ENTRUSTED"));
				            		row.createCell((short) 4).setCellValue(rs2.getInt("CUR_YR_JOBS_ENTRUSTED"));
				            		row.createCell((short) 5).setCellValue(rs2.getInt("PRE_YR_JOBS_COMPLITED"));
				            		row.createCell((short) 6).setCellValue(rs2.getInt("CUR_YR_JOBS_COMPLITED"));
				            		row.createCell((short) 7).setCellValue(rs2.getDouble("PRE_YR_DIRECT_LABOUR"));
				            		row.createCell((short) 8).setCellValue(rs2.getDouble("CUR_YR_DIRECT_LABOUR"));
				            		row.createCell((short) 9).setCellValue(rs2.getDouble("PRE_YR_INDIRECT_LABOUR"));
				            		row.createCell((short) 10).setCellValue(rs2.getDouble("CUR_YR_INDIRECT_LABOUR"));
				            		row.createCell((short) 11).setCellValue(rs2.getDouble("PRE_YR_VALUE_OF_WORK"));
				            		row.createCell((short) 12).setCellValue(rs2.getDouble("CUR_YR_VALUE_OF_WORK"));
				            		row.createCell((short) 13).setCellValue(rs2.getDouble("PRE_YR_COST_OF_SPARES"));
				            		row.createCell((short) 14).setCellValue(rs2.getDouble("CUR_YR_COST_OF_SPARES"));
				            		row.createCell((short) 15).setCellValue(rs2.getDouble("PRE_YR_EXPENDITURE_OF_OTHERS"));
				            		row.createCell((short) 16).setCellValue(rs2.getDouble("CUR_YR_EXPENDITURE_OF_OTHERS"));
				            		row.createCell((short) 17).setCellValue(rs2.getDouble("PRE_YR_TOTAL_COST"));
				            		row.createCell((short) 18).setCellValue(rs2.getDouble("CUR_YR_TOTAL_COST"));
								}
							 else if(formattyp.equalsIgnoreCase("13"))
								{
								 row.createCell((short) 0).setCellValue(jj);
				            		row.createCell((short) 1).setCellValue(rs2.getString("VEHICLES"));
				            		row.createCell((short) 2).setCellValue(rs2.getInt("PENDING_ON_BEGIN_OF_THE_YR_1A"));
				            		row.createCell((short) 3).setCellValue(rs2.getDouble("PENDING_ON_BEGIN_OF_THE_YR_1B"));
				            		row.createCell((short) 4).setCellValue(rs2.getInt("TDA_RAISED_2A"));
				            		row.createCell((short) 5).setCellValue(rs2.getDouble("TDA_RAISED_2B"));
				            		row.createCell((short) 6).setCellValue(rs2.getInt("TDA_RAISED_3A"));
				            		row.createCell((short) 7).setCellValue(rs2.getDouble("TDA_RAISED_3B"));
				            		row.createCell((short) 8).setCellValue(rs2.getInt("TDA_ACCEPTED_AND_ADJUSTED_4A"));
				            		row.createCell((short) 9).setCellValue(rs2.getDouble("TDA_ACCEPTED_AND_ADJUSTED_4B"));
				            		row.createCell((short) 10).setCellValue(rs2.getInt("TDA_ACCEPTED_AND_ADJUSTED_5A"));
				            		row.createCell((short) 11).setCellValue(rs2.getDouble("TDA_ACCEPTED_AND_ADJUSTED_5B"));
				            		row.createCell((short) 12).setCellValue(rs2.getInt("VALUE_OF_JOBS_IN_PROGRESS_6A"));
				            		row.createCell((short) 13).setCellValue(rs2.getDouble("VALUE_OF_JOBS_IN_PROGRESS_6B"));
				            		row.createCell((short) 14).setCellValue(rs2.getString("VALUE_OF_JOBS_IN_PROGRESS_7A"));
								}
	            		 kk++;
	            		 j++;
	            	jj++;
	            	 }
	                
	                HSSFRow rowlast=   sheet.createRow((short)j);
           		 if(formattyp.equalsIgnoreCase("1")){
           			 if(detail.equalsIgnoreCase("Detail")){
	            		rowlast.createCell((short) 2).setCellValue("Grand Total");
	            		rowlast.createCell((short) 3).setCellValue(actfirst);
	            		rowlast.createCell((short) 4).setCellValue(beyr);
	            		rowlast.createCell((short) 5).setCellValue(actualfr);
	            		rowlast.createCell((short) 6).setCellValue(anti);
	            		rowlast.createCell((short) 7).setCellValue(recurrect);
	            		rowlast.createCell((short) 8).setCellValue(varia);
	            		rowlast.createCell((short) 10).setCellValue(benext);

           			 }
           		 }else if(formattyp.equalsIgnoreCase("2")){
           			 if(detail.equalsIgnoreCase("Detail")){
 	            		rowlast.createCell((short) 2).setCellValue("Grand Total");
 	            		rowlast.createCell((short) 3).setCellValue(actfirst);
 	            		rowlast.createCell((short) 4).setCellValue(beyr);
 	            		rowlast.createCell((short) 5).setCellValue(actualfr);
 	            		rowlast.createCell((short) 6).setCellValue(anti);
 	            		rowlast.createCell((short) 7).setCellValue(recurrect);
 	            		rowlast.createCell((short) 8).setCellValue(varia);
 	            		rowlast.createCell((short) 10).setCellValue(benext);

            			 }
            		 }else if(formattyp.equalsIgnoreCase("9")){
               			 if(detail.equalsIgnoreCase("Detail")){
  	            		rowlast.createCell((short) 1).setCellValue("Grand Total");
  	            		rowlast.createCell((short) 2).setCellValue(actfirst);
  	            		rowlast.createCell((short) 3).setCellValue(beyr);
  	            		rowlast.createCell((short) 4).setCellValue(actualfr);
  	            		rowlast.createCell((short) 5).setCellValue(anti);
  	            		rowlast.createCell((short) 6).setCellValue(recurrect);
  	            		rowlast.createCell((short) 7).setCellValue(varia);
  	            		rowlast.createCell((short) 9).setCellValue(benext);

             			 }else if(detail.equalsIgnoreCase("Abstract")){
             				rowlast.createCell((short) 1).setCellValue("Grand Total");
      	            		rowlast.createCell((short) 2).setCellValue(actfirst);
      	            		rowlast.createCell((short) 3).setCellValue(beyr);
      	            		rowlast.createCell((short) 4).setCellValue(actualfr);
      	            		rowlast.createCell((short) 5).setCellValue(anti);
      	            		rowlast.createCell((short) 6).setCellValue(recurrect);
      	            		rowlast.createCell((short) 7).setCellValue(varia);
      	            		rowlast.createCell((short) 9).setCellValue(benext);
             				 
             			 }
             		 }else if(formattyp.equalsIgnoreCase("10")){
               			 if(detail.equalsIgnoreCase("Detail")){
  	            		rowlast.createCell((short) 2).setCellValue("Grand Total");
  	            		rowlast.createCell((short) 3).setCellValue(actfirst);
  	            		rowlast.createCell((short) 4).setCellValue(beyr);
  	            		rowlast.createCell((short) 5).setCellValue(actualfr);
  	            		rowlast.createCell((short) 6).setCellValue(anti);
  	            		rowlast.createCell((short) 7).setCellValue(recurrect);
  	            		rowlast.createCell((short) 8).setCellValue(varia);
  	            		rowlast.createCell((short) 10).setCellValue(benext);

             			 }
             		 }
	                fileOut = response.getOutputStream();
	                hwb.write(fileOut);
	           		fileOut.close();
	            		} catch ( Exception ex ) {
	            		    System.out.println(ex);

	            		}

	        		}
					

					else if (rtype.equalsIgnoreCase("HTML")) {
		                response.setContentType("text/html");

		                response.setHeader("Content-Disposition",
		                                   "attachment;filename=\"Civil_Budget_Format_Report.html\"");
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
		            } 
			/*			
				else if (rtype.equalsIgnoreCase("TXT")) {

					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"Civil_Budget_Format_Report.txt\"");

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

				}*/
			} catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println(connectMsg);
			}
		
	}

}