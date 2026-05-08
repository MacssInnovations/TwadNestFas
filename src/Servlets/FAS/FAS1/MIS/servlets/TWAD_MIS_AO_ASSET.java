package Servlets.FAS.FAS1.MIS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TWAD_MIS_AO_ASSET extends HttpServlet {
	private static String CONTENT_TYPE="text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;  
    public TWAD_MIS_AO_ASSET() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		Connection con = null;
		PreparedStatement ps = null, ps_category = null,ps_AnnualGrp=null,ps_Minor=null;
		ResultSet rs = null, rs_category = null,rs_AnnualGrp=null,rs_Minor=null;
		int c = 1;
		String annual_id="",MinorGrp_id="";float sum_cre=0,sum_deb=0,sum_net=0;
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

		String xml = "", cat_id = "",qry_str="";

		String Command = request.getParameter("Command");
		System.out.println("command ..... "+ Command);
		String fin_year = request.getParameter("fin_year");
		String[] year = fin_year.split("-");
		int yearFrom = Integer.parseInt(year[0]);
		int yearTo = Integer.parseInt(year[1]);
		int CmbFrom_Month = Integer.parseInt(request
				.getParameter("CmbFrom_Month"));
		int CmbTo_Month = Integer.parseInt(request.getParameter("CmbTo_Month"));
		System.out.println("fin_year" + fin_year + "... monthfrom >> "
				+ CmbFrom_Month + ".... month To >> " + CmbTo_Month);
		if(Command.equalsIgnoreCase("All"))
		{
			xml = "<response><command>Load_Grid</command>";
			try {
				String qry_category = "SELECT aa.minor_code as minor_code, " +
				"  (SELECT MINOR_HEAD_DESC " +
				"  FROM COM_MST_MINOR_HEADS " +
				"  WHERE MAJOR_HEAD_CODE='A' " +
				"  AND MINOR_HEAD_CODE  =aa.minor_code " +
				"  )AS minor_desc , sum(aa.OB_Amt)as OB " +
				" FROM " +
				"  (SELECT a.ACCOUNT_HEAD_CODE AS head_code, " +
				"    (SELECT MINOR_HEAD_CODE " +
				"    FROM COM_MST_ACCOUNT_HEADS " +
				"    WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
				"    AND MAJOR_HEAD_CODE    ='A' " +
				"    )minor_code, " +
				"    (SELECT MAJOR_HEAD_CODE " +
				"    FROM COM_MST_ACCOUNT_HEADS " +
				"    WHERE ACCOUNT_HEAD_CODE= a.ACCOUNT_HEAD_CODE " +
				"    ) AS major_code , sum(a.DEBIT_OPENING_BALANCE-a.CREDIT_OPENING_BALANCE)as OB_Amt " +
				"  FROM " +
				"    (SELECT ACCOUNT_HEAD_CODE,DEBIT_OPENING_BALANCE,CREDIT_OPENING_BALANCE " +
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
				"  WHERE a.ACCOUNT_HEAD_CODE IN " +
				"    (SELECT ACCOUNT_HEAD_CODE " +
				"    FROM COM_MST_ACCOUNT_HEADS " +
				"    WHERE MAJOR_HEAD_CODE ='A' " +
				"    ) " +
				"  GROUP BY a.ACCOUNT_HEAD_CODE " +
				"  ORDER BY a.ACCOUNT_HEAD_CODE " +
				"  )aa " +
				"GROUP BY aa.minor_code " +
				"ORDER BY aa.minor_code";
				System.out.println(qry_category);
				ps_category = con.prepareStatement(qry_category);
				rs_category = ps_category.executeQuery();
				while (rs_category.next()) {
					cat_id = rs_category.getString("minor_code");
				
					xml += "<count>";
					xml += "<minor_code>"
							+ rs_category.getString("minor_code")
							+ "</minor_code>";
					xml += "<minor_desc>"
							+ rs_category.getString("minor_desc")
							+ "</minor_desc>";
					xml+="<OB>"+rs_category.getDouble("OB")+"</OB>";
qry_str= "SELECT " +
"  (SELECT MINOR_HEAD_DESC " +
"  FROM COM_MST_MINOR_HEADS " +
"  WHERE MAJOR_HEAD_CODE='A' " +
"  AND MINOR_HEAD_CODE  =aa.minor_code " +
"  )AS minor_desc, " +
/*"  case " +
" when head_desc like '%ADDITION%' or head_desc like '%Addition%' then 1 " +
" when  head_desc like '%DELETION%' or head_desc like '%Deletio%'  then 2 " +
" when  head_desc like '%DEPRECIATIO%' or  head_desc like '%epreciati%' or head_desc like '%DEPRICIATED'  then 3   " + 
" when  head_desc like '%Yester%' or  head_desc like '%YY%'  then 4   " +  
" when  head_desc like '%Discarded Assets' then 5  " +
" when  head_desc like '%Apportionment of G%' then 6 " + 
" end as flag,"+*/
"  aa.* " +
" FROM " +
"  (SELECT a.ACCOUNT_HEAD_CODE AS head_code, " +
"    (SELECT MINOR_HEAD_CODE " +
"    FROM COM_MST_ACCOUNT_HEADS " +
"    WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
"    AND MAJOR_HEAD_CODE    ='A' " +
"    )minor_code, " +
"    (SELECT MAJOR_HEAD_CODE " +
"    FROM COM_MST_ACCOUNT_HEADS " +
"    WHERE ACCOUNT_HEAD_CODE= a.ACCOUNT_HEAD_CODE " +
"    ) AS major_code, " +
"    (SELECT ACCOUNT_HEAD_DESC " +
"    FROM COM_MST_ACCOUNT_HEADS " +
"    WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
"    )                AS head_desc, " +
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
"  )aa " +
" WHERE aa.major_code='A' " +
" AND aa.minor_code  = "+cat_id +
" ORDER BY aa.major_code, " +
"  aa.head_code";
						ps = con.prepareStatement(qry_str);
						rs = ps.executeQuery();

						while (rs.next()) {
							sum_cre+=rs.getDouble("Credit");
							sum_deb+=rs.getDouble("Debit");
							xml = xml + "<head_code"+c+">" + rs.getInt("head_code")
							+ "</head_code"+c+">";
							xml = xml + "<Debit"+c+">" + rs.getDouble("Debit")
									+ "</Debit"+c+">";
							xml = xml + "<Credit"+c+">" + rs.getDouble("Credit")
									+ "</Credit"+c+">";
							xml = xml + "<NET"+c+">" + rs.getDouble("NET") + "</NET"+c+">";
							xml = xml + "<head_desc"+c+"><![CDATA["
							+ rs.getString("head_desc")
							+ "]]></head_desc"+c+">";
						}
				   
					xml +="</count><sum_deb>"+sum_deb+"</sum_deb>";
			xml+="<sum_cre>"+sum_cre+"</sum_cre>";
					c++;
				}
if(c==0){
	xml = xml + "<flag>Failure</flag>";	
}else{
	xml = xml + "<flag>Success</flag>";}
			} catch (Exception e) {
				xml = xml + "<flag>Failure</flag>";
				System.out.println(e);
			}
	
		}if(Command.equalsIgnoreCase("TB_Detail"))
		{
			xml = "<response><command>TB_Detail</command>";
			try {
				String qry_category = "SELECT CATEGORYID,CATEGORYNAME from  CATEGORY where CATEGORYID=3 ";
				ps_category = con.prepareStatement(qry_category);
				rs_category = ps_category.executeQuery();
				while (rs_category.next()) {
					cat_id = rs_category.getString("CATEGORYID");
				
					xml += "<count>";
					xml += "<CATEGORYID>"
							+ rs_category.getInt("CATEGORYID")
							+ "</CATEGORYID>";
					xml += "<CATEGORYNAME>"
							+ rs_category.getString("CATEGORYNAME")
							+ "</CATEGORYNAME>";
					
					String annualGrp_id="SELECT ANNUALGROUPINGID from HEADOFACCOUNTS where ANNUALGROUPINGID like 'B%',ANNUALGROUPINGNAME from ANNUALGROUPING where CATEGORYID="+cat_id;
					//System.out.println("annualGrp_id .. "+annualGrp_id);
					 List<String> results = new ArrayList<String>();
					 List<String> results1 = new ArrayList<String>();
					 List<String> res_minor = new ArrayList<String>();
					 List<String> res_minor1 = new ArrayList<String>();
					try{
						ps_AnnualGrp = con.prepareStatement(annualGrp_id);
						rs_AnnualGrp = ps_AnnualGrp.executeQuery();
						while (rs_AnnualGrp.next()) {
							annual_id = rs_AnnualGrp.getString("ANNUALGROUPINGID");
						
							results.add(rs_AnnualGrp.getString("ANNUALGROUPINGID"));
							results1.add(rs_AnnualGrp.getString("ANNUALGROUPINGNAME"));
						}
						}catch (Exception e) {
	                         System.out.println(" Secound Try Block >> "+e);
						}
						int result_Size=results.size();
						if(result_Size!=0){
						for(int kk=1;kk<=result_Size;kk++)
						{
							
						xml+="<Annula_Group"+kk+">";
						xml += "<ANNUALGROUPINGID>"
							+ results.get(kk)
							+ "</ANNUALGROUPINGID>";
					xml += "<ANNUALGROUPINGNAME>"
							+ results1.get(kk)
							+ "</ANNUALGROUPINGNAME>";
					String Ann_Group=results.get(kk);
					System.out.println(" size ... "+Ann_Group);
						String no=Ann_Group.substring(1);
						System.out.println(" no ... "+no);
						String Minor_id="SELECT MINORGROUPINGID,MINORGROUPINGNAME from MINORGROUPING where MINORGROUPINGID="+no+" and  CATEGORYID="+cat_id;
			    System.out.println(Minor_id);
						try{
						ps_Minor=con.prepareStatement(Minor_id);
						rs_Minor=ps_Minor.executeQuery();
						while(rs_Minor.next())
						{
							res_minor.add(rs_Minor.getString("MINORGROUPINGID"));
							res_minor1.add(rs_Minor.getString("MINORGROUPINGNAME"));
						}
						}catch (Exception e) {
							System.out.println("Third Exception "+e);
						}
						int res_minor_Size=res_minor.size();
						System.out.println("res_minor_Size >>> "+res_minor);
						if(res_minor_Size!=0){
						for(int jj=1;jj<=res_minor_Size;jj++)
						{
							xml+="<Minor_Group"+jj+">";  
							xml += "<MINORGROUPINGID>"
								+ res_minor.get(jj)
								+ "</MINORGROUPINGID>";
						xml += "<MINORGROUPINGNAME>"
								+ res_minor1.get(jj)
								+ "</MINORGROUPINGNAME>";
						String Minor_Group=res_minor.get(jj);
						System.out.println(res_minor_Size+" size ... "+Minor_Group);
						String Head_id="SELECT " +
						"  (SELECT CATEGORYID " +
						"  FROM ANNUALGROUPING " +
						"  WHERE ANNUALGROUPINGID=j1.ANNUALGROUPINGID " +
						"  )AS category, " +
						"  (SELECT ANNUALGROUPINGNAME " +
						"  FROM ANNUALGROUPING " +
						"  WHERE ANNUALGROUPINGID=j1.ANNUALGROUPINGID " +
						"  )AS Annual_desc, " +
						"  (SELECT MINORGROUPINGNAME " +
						"  FROM MINORGROUPING " +
						"  WHERE MINORGROUPINGID=j1.MINORGROUPINGID " +
						"  AND CATEGORYID       = " +
						"    (SELECT CATEGORYID " +
						"    FROM ANNUALGROUPING " +
						"    WHERE ANNUALGROUPINGID=j1.ANNUALGROUPINGID " +
						"    ) " +
						"  )AS minor_grp, " +
						"  j1. *, " +
						"  j2.* " +
						"FROM " +
						"  (SELECT ANNUALGROUPINGID,MINORGROUPINGID,ACCOUNTCODE FROM HEADOFACCOUNTS " +
						"  )j1 " +
						"JOIN " +
						"  (SELECT " +
						"    (SELECT MINOR_HEAD_DESC " +
						"    FROM COM_MST_MINOR_HEADS " +
						"    WHERE MAJOR_HEAD_CODE='A' " +
						"    AND MINOR_HEAD_CODE  =aa.minor_code " +
						"    )AS minor_desc, " +
						"    aa.* " +
						"  FROM " +
						"    (SELECT a.ACCOUNT_HEAD_CODE AS head_code, " +
						"      (SELECT MINOR_HEAD_CODE " +
						"      FROM COM_MST_ACCOUNT_HEADS " +
						"      WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
						"      AND MAJOR_HEAD_CODE    ='A' " +
						"      )minor_code, " +
						"      (SELECT MAJOR_HEAD_CODE " +
						"      FROM COM_MST_ACCOUNT_HEADS " +
						"      WHERE ACCOUNT_HEAD_CODE= a.ACCOUNT_HEAD_CODE " +
						"      ) AS major_code, " +
						"      (SELECT ACCOUNT_HEAD_DESC " +
						"      FROM COM_MST_ACCOUNT_HEADS " +
						"      WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
						"      )                AS head_desc, " +
						"      SUM(Debit)       AS Debit, " +
						"      SUM(Credit)      AS Credit, " +
						"      SUM(Debit-Credit)AS net " +
						"    FROM " +
						"      (SELECT ACCOUNT_HEAD_CODE, " +
						"        CURRENT_MONTH_DEBIT  AS Debit, " +
						"        CURRENT_MONTH_CREDIT AS Credit " +
						"      FROM FAS_TRIAL_BALANCE " +
						"      WHERE to_date((cashbook_month " +
						"        ||'-' " +
						"        ||cashbook_year),'mm-yyyy') BETWEEN to_date(4 " +
						"        ||'-' " +
						"        ||2011,'mm-yyyy') " +
						"      AND to_date(3 " +
						"        ||'-' " +
						"        ||2012,'mm-yyyy') " +
						"      ) a " +
						"    GROUP BY a.ACCOUNT_HEAD_CODE " +
						"    )aa " +
						"  WHERE aa.major_code='A' " +
						"  ORDER BY aa.major_code, " +
						"    aa.head_code " +
						"  )j2 " +
						" ON j1.ACCOUNTCODE=j2.head_code " +
						"  where  j1.MINORGROUPINGID="+Minor_Group+"";
						System.out.println(Head_id);
						ps = con.prepareStatement(Head_id);
						rs = ps.executeQuery();
c=0;
						while (rs.next()) {
							
							sum_cre+=rs.getDouble("Credit");
							sum_deb+=rs.getDouble("Debit");
							xml = xml + "<head_code"+c+">" + rs.getInt("head_code")
							+ "</head_code"+c+">";
							xml = xml + "<Debit"+c+">" + rs.getDouble("Debit")
									+ "</Debit"+c+">";
							xml = xml + "<Credit"+c+">" + rs.getDouble("Credit")
									+ "</Credit"+c+">";
							xml = xml + "<NET"+c+">" + rs.getDouble("NET") + "</NET"+c+">";
							xml = xml + "<head_desc"+c+"><![CDATA["
							+ rs.getString("head_desc")
							+ "]]></head_desc"+c+">";
							c++;
						}
						if(c==0){
							xml = xml + "<flag>Failure</flag>";	
						}else{
							xml+="</Minor_Group"+jj+">";    
						    xml +="<sum_deb>"+sum_deb+"</sum_deb>";
				            xml+="<sum_cre>"+sum_cre+"</sum_cre>";
							xml = xml + "<flag>Success</flag>";}
							
						}
					
					
						
						}
						xml+="</Annula_Group"+kk+">";
				
				 
						}
				}
					
					
					xml += "</count>";
					
					
					
			}
					
			}catch (Exception e) {
				System.out.println(" First Try Block >> "+e);
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		out.close();
		System.out.println(xml);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
