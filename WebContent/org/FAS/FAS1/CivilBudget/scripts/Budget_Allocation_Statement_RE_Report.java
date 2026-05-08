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
import java.sql.Timestamp;
import java.text.ParseException;
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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
public class Budget_Allocation_Statement_RE_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Budget_Allocation_Statement_RE_Report() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String que="",que2="",todate="",dateString="",head_qry="";
		int cashYear=0,cashMonth=0,office_id=0;
		Calendar c; File reportFile = null;
		String hid="";
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
//			ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
//					+ strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);

		}
		
       //String RegionId="";
       String command=request.getParameter("command");
        hid=request.getParameter("old");
       
        
        System.out.println("command >>  "+command);
       System.out.println("old >>  "+hid);
		int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
		int cmbAcc_OfficeCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
		String rad_val=request.getParameter("tpe_wise");
		String grp_val=request.getParameter("statementGp");
		 System.out.println("grp_val"+grp_val); 
		if(grp_val=="")grp_val="0";
		int statement_group=Integer.parseInt(grp_val);
		 System.out.println("statement_group"+statement_group); 
        	//RegionId= request.getParameter("txtRegionId");
          //  System.out.println("RegionId"+RegionId); 
        
        String fin_year=request.getParameter("cmbFinancialYear");
	    String Statement_no=request.getParameter("cmbStatementName");
	  
	    int statement_no=Integer.parseInt(Statement_no);
	    String statementGp=request.getParameter("statementGp");
	    if(statementGp=="")statementGp="1";
	    int statementGp_no=Integer.parseInt(statementGp);
	    System.out.println("no"+Statement_no);
	    String statement="";
	    String val="";
	    try{
	    	PreparedStatement ps=null;
	    	ResultSet rs=null;
	    	statement="select STATEMENT_DESC from FAS_STATEMENT_MASTER where STATEMENT_NO='"+Statement_no+"'";
ps=con.prepareStatement(statement);
rs=ps.executeQuery();
while (rs.next()) {
	val=rs.getString("STATEMENT_DESC");
                   }
           }
	catch (Exception e) {
			System.out.println(e);
		}
	 String val_gp="",statement_grp="";
	    try{
	    	PreparedStatement ps1=null;
	    	ResultSet rs1=null;
	    	statement_grp="Select STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME="+statement_no+"  and Statement_Group_No="+statementGp_no+" order by Statement_Group_No";
ps1=con.prepareStatement(statement_grp);
rs1=ps1.executeQuery();
while (rs1.next()) {
	val_gp=rs1.getString("STATEMENT_GROUP_DESC");
                }
        }
	catch (Exception e) {
			System.out.println(e);
		}
	    System.out.println(statement_grp+"val"+val_gp);

	    
	   
	    System.out.println("fin_year"+fin_year);	 
	    String heading="REPORT FOR "+val;
	    String grp_name=val_gp;
	    String heading1="IN RESPECT OF "+val;
	    String heading2="STATEMENT SHOWING THE BUDGET PROVISION FOR THE YEAR  "+fin_year;
	    String qry="";
	if(rad_val.equalsIgnoreCase("GRP")){    
		 qry = "SELECT r.ACCOUNTING_FOR_OFFICE_ID, " +
		"  r.Region_Office_Id, " +
		//"  (SELECT o.Office_Name " +
		"(SELECT r.ACCOUNTING_FOR_OFFICE_ID||'('||o.Office_Name||')' " +
		"  FROM COM_MST_ALL_OFFICES_VIEW o " +
		"  WHERE o.Office_Id=r.ACCOUNTING_FOR_OFFICE_ID " +
		"  )AS Office_Name, " +
		//"  decode(ACCOUNT_HEAD_CODE,'0','--','null','--',Account_Head_Code)as ACCOUNT_HEAD_CODE, Statement_Group_No,Statement_No," +
		" ACCOUNT_HEAD_CODE, Statement_Group_No,Statement_No," +
		"  (Select Statement_Group_Desc "+
		 " From Fas_Statement_Group_Master Mst "+
		  " Where Mst.Statement_Name  =R.Statement_No "+
		  " AND mst.Statement_Group_No=r.Statement_Group_No "+
		   " )as Statement_Group_Desc,"+
		"  (SELECT re.Office_Name " +
		"  FROM COM_MST_ALL_OFFICES_VIEW re " +
		"  WHERE re.Office_Id=r.Region_Office_Id " +
		"  )AS RE_Office_Name,decode(UNIT_ALLOCATION,'L','( Rs . In lakhs ) ','T','( Rs . In thousands ) ')as ALLOCATION, " +
		 " AMOUNT as all_amount,UNIT_ALLOCATION,"+
		  " case when UNIT_ALLOCATION='L' then AMOUNT*100000"+
		    " when UNIT_ALLOCATION='T' then AMOUNT*1000"+
		    " when UNIT_ALLOCATION='R' then AMOUNT end Amount"+
		" FROM Fas_Statement_By_Region r " +
		" WHERE Statement_No    = " +statement_no+
		" AND Statement_Group_No= " +statement_group+
		" AND Financial_Year    ='"+fin_year+"'" +
		" AND Region_Office_Id  =  " +cmbAcc_OfficeCode+
	//	" ORDER BY ACCOUNT_HEAD_CODE";
		" ORDER BY Statement_Group_No,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE";
	}
	if(rad_val.equalsIgnoreCase("ALL"))
	{
	qry="SELECT r.ACCOUNTING_FOR_OFFICE_ID, " +
		"  r.Statement_Group_No, " +
		"  r.Statement_No, " +
		"  (SELECT STATEMENT_DESC " +
		"  FROM FAS_STATEMENT_MASTER " +
		"  WHERE STATEMENT_NO=r.statement_no " +
		"  )AS state_name,r.Statement_Group_No||','|| " +
		"  (SELECT STATEMENT_GROUP_DESC " +
		"  FROM FAS_STATEMENT_GROUP_MASTER " +
		"  WHERE STATEMENT_NAME  =r.Statement_No " +
		"  AND Statement_Group_No=r.statement_group_no " +
		"  )AS grp_name, " +
		"  r.Region_Office_Id, " +
		//"  (SELECT o.Office_Name " +
		"(SELECT r.ACCOUNTING_FOR_OFFICE_ID||'-'||o.Office_Name " +
		"  FROM COM_MST_ALL_OFFICES_VIEW o " +
		"  WHERE o.Office_Id=r.ACCOUNTING_FOR_OFFICE_ID" +
		"  )AS Office_Name,ACCOUNT_HEAD_CODE, " +
		//" decode(ACCOUNT_HEAD_CODE,'0','--','null','--',Account_Head_Code)as ACCOUNT_HEAD_CODE,"+
		"  (SELECT re.Office_Name " +
		"  FROM COM_MST_ALL_OFFICES_VIEW re " +
		"  WHERE re.Office_Id=r.Region_Office_Id " +
		"  )AS RE_Office_Name, " +
		 " AMOUNT as all_amount,UNIT_ALLOCATION,"+
		  " case when UNIT_ALLOCATION='L' then AMOUNT*100000"+
		    " when UNIT_ALLOCATION='T' then AMOUNT*1000"+
		    " when UNIT_ALLOCATION='R' then AMOUNT end Amount"+
		" FROM Fas_Statement_By_Region r " +
		" WHERE Statement_No   =  " +statement_no+
		" AND Financial_Year   ='"+fin_year +"' " +
		" AND Region_Office_Id = " +cmbAcc_OfficeCode+
		" ORDER BY r.Statement_Group_No,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE";
	}
				System.out.println("testing"+qry);
			try {
				System.out.println("calling servlet...");
			/*	if(rad_val.equalsIgnoreCase("GRP")){
				reportFile = new File(getServletContext().getRealPath(
						"/org/FAS/FAS1/CivilBudget/jasper/Statement_REgion_Report.jasper"));
				System.out.println("upto"+reportFile);}
				else if(rad_val.equalsIgnoreCase("ALL")){
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/Budget_Allocation_All.jasper"));
					System.out.println("upto"+reportFile);}*/
				Map map = null;
				map = new HashMap();
if(rad_val.equalsIgnoreCase("GRP")){
					
					int count=0;
					try{
						
						PreparedStatement ps1=null;
						ResultSet rs1=null;	
					head_qry="SELECT ACCOUNT_HEAD_CODE " +
					" FROM Fas_Statement_By_Region r " +
					" WHERE Statement_No    =  " +statement_no+
					" and Statement_Group_No= "+statement_group+
					" AND ACCOUNT_HEAD_CODE!='0' " +
					" AND Financial_Year    ='" +fin_year+"'"+
					" AND Region_Office_Id  = "+cmbAcc_OfficeCode;
					
					ps1=con.prepareStatement(head_qry);
					rs1=ps1.executeQuery();
					while(rs1.next()){
						
						count++;
					}
						
						
					}catch (Exception e) {
						System.out.println(e);
					}
					
					
					System.out.println("count value....in single..."+count);
					if(count==0){
						System.out.println("Statement_no::::"+Statement_no);
						map.put("statement_group", statement_group);
						map.put("Statement_no", Statement_no);
						map.put("officeid",cmbAcc_OfficeCode);
						reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/Statement_REgion_Report_HeadLess.jasper"));
				}else if(count>0)
				{
					map.put("statement_group", statement_group);
					map.put("Statement_no", Statement_no);
					map.put("officeid",cmbAcc_OfficeCode);
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/Statement_REgion_Report.jasper"));
				System.out.println("upto"+reportFile);
				}
				}	
				else if(rad_val.equalsIgnoreCase("ALL")){
					System.out.println("alllllllllllllll");
					int count=0;
					try{
						
						PreparedStatement ps1=null;
						ResultSet rs1=null;	
					head_qry="SELECT ACCOUNT_HEAD_CODE " +
					" FROM Fas_Statement_By_Region r " +
					" WHERE Statement_No    =  " +statement_no+
					" AND ACCOUNT_HEAD_CODE!='0' " +
					" AND Financial_Year    ='" +fin_year+"'"+
					" AND Region_Office_Id  = "+cmbAcc_OfficeCode;
					
					ps1=con.prepareStatement(head_qry);
					System.out.println("query count value......."+head_qry);
					rs1=ps1.executeQuery();
					System.out.println("ksjdfhdkjfkd"+rs1.next());
					while(rs1.next()){
						
						count++;
					}
						
						
					}catch (Exception e) {
						System.out.println(e);
					}
					
					
					System.out.println("count value......."+count);
					if(count==0){
					reportFile = new File(getServletContext().getRealPath(
							"/org/FAS/FAS1/CivilBudget/jasper/Budget_Allocation_All_subreport_NoHead.jasper"));
					//Budget_Allocation_All_subreport_NoHead.jasper
					
					}
					
					
					
					else if(count>0){
						if(hid.equalsIgnoreCase("old")){
						reportFile = new File(getServletContext().getRealPath(
						"/org/FAS/FAS1/CivilBudget/jasper/Budget_Allocation_All_subreport0.jasper"));}
						
						
						else{
							new File(getServletContext().getRealPath(
									"/org/FAS/FAS1/CivilBudget/jasper/CivilBudget.jasper"));
						}
						//Budget_Allocation_All_subreport0.jasper
					}
					System.out.println("upto"+reportFile);
					
				}
				
/*
if(command.equalsIgnoreCase("load")){

	System.out.println("bbbbbbbbbbbbbbbbbbbbbbb");
try{
	
reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/CivilBudget.jasper"));

	
if (!reportFile.exists())
	throw new JRRuntimeException("File J not found. The report design must be compiled first.");

JasperReport jasperReport = (JasperReport) JRLoader
		.loadObject(reportFile.getPath());


//map.put("UnitId", cmbAcc_UnitCode);
map.put("OfficeId", cmbAcc_OfficeCode);
map.put("year", fin_year);
//map.put("Statement_name",Statement_no);

//map.put("tpe_wise",rad_val);

    

JasperPrint jasperPrint = JasperFillManager.fillReport(
		jasperReport, map, con);

String rtype = "PDF";

 if (rtype.equalsIgnoreCase("PDF")) {
	System.out.println(rtype);
	byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
	System.out.println("Length  " + buf.length);
	response.setContentType("application/pdf");
	response.setContentLength(buf.length);
	
	response.setHeader("Content-Disposition",
			"attachment;filename=\"civibudget.pdf\"");
	OutputStream out = response.getOutputStream();
	out.write(buf, 0, buf.length);
	out.close();
}
}
catch (Exception ex) {
	String connectMsg = "Could not create the report "
			+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
	System.out.println(connectMsg);
}





}


*/


				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				System.out.println(fin_year);
				map.put("year", fin_year);
				map.put("qry",qry );
				map.put("heading", heading);
				map.put("heading1", heading1);
				map.put("heading2", heading2);
				map.put("grp_name", grp_name);
				map.put("off_id", cmbAcc_OfficeCode);
			System.out.println("test::::"+qry);
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					//System.out.println("test");
					response.setHeader("Content-Disposition",
							"attachment;filename=\""+val+"_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					//System.out.println("testing");
					out.write(buf, 0, buf.length);
					out.close();
	} 
				
			 catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println("connectMsg"+connectMsg);
			 }
			}
			
			
}