package Servlets.FAS.FAS1.TDA.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

public class TDA_Reports_HO_only extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null;

   
    public TDA_Reports_HO_only() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
        Statement statement = null;
		 try {
	            ResourceBundle rs =
	                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                
	            String conString = "";

	            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
	            String strdsn = rs.getString("Config.DSN");
	            String strhostname = rs.getString("Config.HOST_NAME");
	            String strportno = rs.getString("Config.PORT_NUMBER");
	            String strsid = rs.getString("Config.SID");
	            String strdbusername = rs.getString("Config.USER_NAME");
	            String strdbpassword = rs.getString("Config.PASSWORD");

//	            conString =
//	                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//	                    ":" + strsid.trim();
	            conString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

	            

	            Class.forName(strDriver.trim());
	            con =
	 DriverManager.getConnection(conString, strdbusername.trim(),
	                             strdbpassword.trim());
	            try {
	                statement = con.createStatement();
	                con.clearWarnings();
	            } catch (SQLException e) {
	                System.out.println("Exception in creating statement:" + e);
	            }
	        } catch (Exception e) {
	            System.out.println("Exception in openeing con:" + e);
	        }
		
		try
	    {
			System.out.println("this is starting::::::::::");
	        HttpSession session=request.getSession(false);
	        if(session==null)
	        {
	            System.out.println(request.getContextPath()+"/index.jsp");
	            response.sendRedirect(request.getContextPath()+"/index.jsp");
	        
	        }
	        System.out.println(session);
	            
	    }catch(Exception e)
	    {
	    	System.out.println("Redirect Error :"+e);
	    }
		
	      //  Connection con=
	    String opt="",orgUnitName="",hcode="",heading="",heading_one="";
	    response.setContentType(CONTENT_TYPE);
	    try 
	    {
	    	ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
	        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
	    } catch (Exception ex) {
	        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
	        ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	    }
	    JasperDesign jasperDesign = null;
	    String HEad_Str="";
	    File reportFile=null;
		int cmbOffice_code=0,cashYear=0,cashMonth=0,off_id=0;
		String type_prf=null,txtoption=null,officeCode="";
		JasperPrint jasperPrint = null;
		JasperReport jasperReport =null;
     int txtStatus=Integer.parseInt(request.getParameter("txtStatus"));
     System.out.println("Status===>"+txtStatus);
     String Sub_Jour="",Sub_Resp="",Sub_Accept="";
     Map map=new HashMap();
     String statusText="";
     String sub_q="";
     PreparedStatement ps2 = null;
     System.out.println("Status==1==>"+txtStatus);
	    try 
	    {
	        System.out.println("inside GET servlet>>>>>superrrrrrrrrrrrr>>>>>..");
	        String displayOrder=request.getParameter("displayingOrder");
	        type_prf=request.getParameter("proformatype");
	        if (txtStatus==1){
             	statusText="Acceptance Pending  "+type_prf;
             	Sub_Jour=" where f.ORGINATING_JVR_NO is not null "+
             	" and  f.RESPONDING_JVR_NO is null "+
             	" and f.ACCEPTING_JVR_NO is null and (f.acceptance_status is null or f.acceptance_status='-')"+
             	" order by f.trf_accounting_unit_id,f.voucher_date ";
             	//" f.acceptance_status <> 'R' ";
             	
             }else if(txtStatus==2){
             	statusText="Suspense Head Clearance Pending  "+type_prf;
             	Sub_Jour=" where f.ORGINATING_JVR_NO is not null "+
             	" and  f.RESPONDING_JVR_NO is null " +
             	"  and  f.ACCEPTING_JVR_NO is not null "+
             	" ORDER BY f.ACCOUNTING_UNIT_ID,f.VOUCHER_DATE ";
             	
             }else if(txtStatus==3){
             	statusText="Rejected Items - RJV made  "+type_prf;
             	Sub_Jour=" where f.ORGINATING_JVR_NO is not null "+
             	" and f.RESPONDING_JVR_NO is not null "+
             	"  and f.acceptance_status='R' "+
             	" ORDER BY f.ACCOUNTING_UNIT_ID,f.VOUCHER_DATE ";
             	
             }else if(txtStatus==4){
             	statusText="Rejected items - Pending RJV  "+type_prf;
             	sub_q=" and   mst.RESPONDING_JVR_NO is null "+
                " and  mst.RESPONDING_JVR_DATE is null";
             	Sub_Jour="  where f.acceptance_status='R' and f.ACCEPTING_JVR_DATE is null and f.ACCEPTING_JVR_NO is null   "+
             			" ORDER BY f.ACCOUNTING_UNIT_ID,f.VOUCHER_DATE ";
             }else{
             	System.out.println("Error in Status...... ");
             }
	        
	        System.out.println("txtStatus...... "+txtStatus);
        	System.out.println("sub_q...... "+sub_q);
         	
	        if(request.getParameter("month_year").equalsIgnoreCase("particular_cb"))
	        {
	        	 try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));
	           //  type_prf=request.getParameter("proformatype");
	             txtoption=request.getParameter("txtoption");//PDF OR EXCEL
	             
	             
	           /*  if (txtStatus==1){
	             	statusText="Acceptance Pending  "+type_prf;
	             	Sub_Jour=" where f.ORGINATING_JVR_NO is not null "+
	             	" and  f.RESPONDING_JVR_NO is null "+
	             	"  and f.ACCEPTING_JVR_NO is null and f.acceptance_status <> 'R' ";
	             	
	             }else if(txtStatus==2){
	             	statusText="Suspense Head Clearance Pending  "+type_prf;
	             	Sub_Jour=" where f.ORGINATING_JVR_NO is not null "+
	             	" and  f.RESPONDING_JVR_NO is null " +
	             	"  and  f.ACCEPTING_JVR_NO is not null ";
	             	
	             }else if(txtStatus==3){
	             	statusText="Rejected Items - RJV made  "+type_prf;
	             	Sub_Jour=" where f.ORGINATING_JVR_NO is not null "+
	             	" and f.RESPONDING_JVR_NO is not null "+
	             	"  and f.acceptance_status='R' ";
	             	
	             }else if(txtStatus==4){
	             	statusText="Rejected items - Pending RJV  "+type_prf;
	             	Sub_Jour="  where f.acceptance_status='R' and f.ACCEPTING_JVR_DATE is null and f.ACCEPTING_JVR_NO is null   ";
	             }else{
	             	System.out.println("Error in Status...... ");
	             }*/
	             
	        String monthInWords="";
	        if(cashMonth==1)
	            monthInWords="January";
	        else if(cashMonth==2)
	            monthInWords="February";
	        else if(cashMonth==3)
	            monthInWords="March";
	        else if(cashMonth==4)
	            monthInWords="April";
	        else if(cashMonth==5)
	            monthInWords="May";
	        else if(cashMonth==6)
	            monthInWords="June";
	        else if(cashMonth==7)
	            monthInWords="July";
	        else if(cashMonth==8)
	            monthInWords="August";
	        else if(cashMonth==9)
	            monthInWords="September";
	        else if(cashMonth==10)
	            monthInWords="October";
	        else if(cashMonth==11)
	            monthInWords="November";
	        else if(cashMonth==12)
	            monthInWords="December";
	            
	        if(displayOrder.equalsIgnoreCase("RW") ||displayOrder.equalsIgnoreCase("OW"))
	        {
	        	
	        	if(displayOrder.equalsIgnoreCase("RW"))
	            {
	            	cmbOffice_code=Integer.parseInt(request.getParameter("txtRegionId"));
	            	
	            
	        	
	        	 try {
	        	        PreparedStatement ps=null;
	        	        ResultSet rs=null;
	        	       
	        	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	        	        ps.setInt(1,cmbOffice_code);
	        	        rs=ps.executeQuery();
	        	        if(rs.next()) {
	        	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	        	        }
	        	        }
	        	        catch (SQLException e) {
	        	            System.out.println("SQL Exception -->"+e);
	        	        }
	        	
	        	if(type_prf.equals("TDA"))
		        {
	        		System.out.println("TDA1");
	        		if (txtoption.equalsIgnoreCase("EXCEL")) {
			        	 try
	                       {
			        		  
	                    	   String sql_HOA_NCR= "select * from(SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
	                    	   		+ "\r\n"
	                    	   		+ "(SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
	                    	   		+ "    )As Orgname,\r\n"
	                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
	                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
	                    	   		+ "    )AS acceptedName,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_NO,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_DATE,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_NO,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_DATE,\r\n"
	                    	   		+ "    Mst.Status,\r\n"
	                    	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO,mst.acceptance_status\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAO','TDACB')\r\n"
	                    	   		+ "  And Mst.Cashbook_Year=?\r\n"
	                    	   		+ "  And Mst.Cashbook_Month=?\r\n"
	                    	   		+ "  and mst.accounting_for_office_id in (SELECT office_id\r\n"
	                    	   		+ "FROM COM_MST_ALL_OFFICES_VIEW\r\n"
	                    	   		+ "WHERE region_office_id =?)\r\n"
	                    	   		+ ")A\r\n"
	                    	   		+ "  Left Outer JOIN\r\n"
	                    	   		+ "    \r\n"
	                    	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
	                    	   		+ "    mst.STATUS\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAA')\r\n"
	                    	   		+ ")B\r\n"
	                    	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
	                    	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
	                    	   		+ "  order by a.Orgname,a.VOUCHER_NO)f"+Sub_Jour;
	                       	   ps2=con.prepareStatement(sql_HOA_NCR);
	                       
	                           ps2.setInt(1,cashYear);
	                           ps2.setInt(2,cashMonth);
	                           ps2.setInt(3,off_id);
	                           //System.out.println("before execution HOA Balance DR type check");
	                           ResultSet rs1=ps2.executeQuery();
	                           generateExcelFile(rs1,response,"TDA_LIST_RW_ALL");
	                       }
	                       
	                       catch(Exception e)
	                       {
	                    	   System.out.println("Error in Report**"+e);
	                       }}
			        	 else {
		        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/TDA_List_All_rw.jasper"));
		        }
		        }
		        else
		        {
		        	if (txtoption.equalsIgnoreCase("EXCEL")) {
			        	 try
	                       {
			        		  
	                    	   String sql_HOA_NCR= "select * from (SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
	                    	   		+ "\r\n"
	                    	   		+ "(SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
	                    	   		+ "    )As Orgname,\r\n"
	                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
	                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
	                    	   		+ "    )AS acceptedName,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_NO,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_DATE,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_NO,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_DATE,\r\n"
	                    	   		+ "    Mst.Status,\r\n"
	                    	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO,mst.acceptance_status\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TCAO','TCACB')\r\n"
	                    	   		+ "  And Mst.Cashbook_Year=? \r\n"
	                    	   		+ "  And Mst.Cashbook_Month=? \r\n"
	                    	   		+ " and mst.accounting_for_office_id in (SELECT office_id\r\n"
	                    	   		+ "FROM COM_MST_ALL_OFFICES_VIEW\r\n"
	                    	   		+ "WHERE region_office_id =?)\r\n"
	                    	   		+ ")A\r\n"
	                    	   		+ "  Left Outer JOIN\r\n"
	                    	   		+ "    \r\n"
	                    	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
	                    	   		+ "    mst.STATUS\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TCAA')\r\n"
	                    	   		+ ")B\r\n"
	                    	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
	                    	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
	                    	   		+ "  order by a.Orgname,a.VOUCHER_NO)f"+Sub_Jour;
	                       	   ps2=con.prepareStatement(sql_HOA_NCR);
	                       
	                           ps2.setInt(1,cashYear);
	                           ps2.setInt(2,cashMonth);
	                           ps2.setInt(3,off_id);
	                           //System.out.println("before execution HOA Balance DR type check");
	                           ResultSet rs1=ps2.executeQuery();
	                           generateExcelFile(rs1,response,"TCA_LIST_RW_ALL");
	                       }
	                       
	                       catch(Exception e)
	                       {
	                    	   System.out.println("Error in Report**"+e);
	                       }}
		        	else {
		        	
		        		reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/TCA_List_All_rw.jasper"));
		        }
		        }
	       
	        		map.put("cashyear",cashYear);     
		        	 map.put("cashmonth",cashMonth);
		             map.put("monthinwords",monthInWords);
		             map.put("officeCode",cmbOffice_code);
		             map.put("statusHeading", statusText);
		             map.put("Sub_Jour", Sub_Jour);
	            HEad_Str="TDA_ALL";
	        
	          }
	        	else if(displayOrder.equalsIgnoreCase("OW"))
	            {
	            	cmbOffice_code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	            	try {
	        	        PreparedStatement ps=null;
	        	        ResultSet rs=null;
	        	       
	        	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	        	        ps.setInt(1,cmbOffice_code);
	        	        rs=ps.executeQuery();
	        	        if(rs.next()) {
	        	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	        	        }
	        	        }
	        	        catch (SQLException e) {
	        	            System.out.println("SQL Exception -->"+e);
	        	        }
	        	
	        	if(type_prf.equals("TDA"))
		        {
	        		if (txtoption.equalsIgnoreCase("EXCEL")) {
			        	 try
	                       {
			        		  
	                    	   String sql_HOA_NCR= "select * from (SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
	                    	   		+ "\r\n"
	                    	   		+ "(SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
	                    	   		+ "    )As Orgname,\r\n"
	                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
	                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
	                    	   		+ "    )AS acceptedName,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_NO,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_DATE,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_NO,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_DATE,\r\n"
	                    	   		+ "    Mst.Status,\r\n"
	                    	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO,mst.acceptance_status\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAO','TDACB')\r\n"
	                    	   		+ "  And Mst.Cashbook_Year=?\r\n"
	                    	   		+ "  And Mst.Cashbook_Month=?\r\n"
	                    	   		+ "    and mst.accounting_for_office_id=?\r\n"
	                    	   		+ ")A\r\n"
	                    	   		+ "  Left Outer JOIN\r\n"
	                    	   		+ "    \r\n"
	                    	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
	                    	   		+ "    mst.STATUS\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAA')\r\n"
	                    	   		+ ")B\r\n"
	                    	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
	                    	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
	                    	   		+ "  order by a.Orgname,a.VOUCHER_NO)f"+Sub_Jour;
	                       	   ps2=con.prepareStatement(sql_HOA_NCR);
	                       
	                           ps2.setInt(1,cashYear);
	                           ps2.setInt(2,cashMonth);
	                           ps2.setInt(3,off_id);
	                           //System.out.println("before execution HOA Balance DR type check");
	                           ResultSet rs1=ps2.executeQuery();
	                           generateExcelFile(rs1,response,"TDA_LIST_OW_ALL");
	                       }
	                       
	                       catch(Exception e)
	                       {
	                    	   System.out.println("Error in Report**"+e);
	                       }}
	        		else {
			        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/TDA_List_All_rw_ow.jasper"));

	        		}
		        
			        	 }
			        	 
		        else
		        {
		        	if (txtoption.equalsIgnoreCase("EXCEL")) {
			        	 try
	                       {
			        		  
	                    	   String sql_HOA_NCR= "select * from(SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
	                    	   		+ "(SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
	                    	   		+ "    )As Orgname,\r\n"
	                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
	                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
	                    	   		+ "    )AS acceptedName,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_NO,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_DATE,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_NO,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_DATE,\r\n"
	                    	   		+ "    Mst.Status,\r\n"
	                    	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO,mst.acceptance_status\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TCAO','TCACB')\r\n"
	                    	   		+ "  And Mst.Cashbook_Year=? \r\n"
	                    	   		+ "  And Mst.Cashbook_Month=? \r\n"
	                    	   		+ "    and mst.accounting_for_office_id=? \r\n"
	                    	   		+ ")A\r\n"
	                    	   		+ "  Left Outer JOIN\r\n"
	                    	   		+ "    \r\n"
	                    	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
	                    	   		+ "    mst.STATUS\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TCAA')\r\n"
	                    	   		+ ")B\r\n"
	                    	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
	                    	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
	                    	   		+ "  order by a.Orgname,a.VOUCHER_NO)f"+Sub_Jour;
	                       	   ps2=con.prepareStatement(sql_HOA_NCR);
	                       
	                           ps2.setInt(1,cashYear);
	                           ps2.setInt(2,cashMonth);
	                           ps2.setInt(3,off_id);
	                           //System.out.println("before execution HOA Balance DR type check");
	                           ResultSet rs1=ps2.executeQuery();
	                           generateExcelFile(rs1,response,"TCA_LIST_OW_ALL");
	                       }
	                       
	                       catch(Exception e)
	                       {
	                    	   System.out.println("Error in Report**"+e);
	                       }}
		        	else {
			        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/TCA_List_All_rw_ow.jasper"));

		        	}
		        }
	        	
	        	
	        		map.put("cashyear",cashYear);     
		        	 map.put("cashmonth",cashMonth);
		             map.put("monthinwords",monthInWords);
		             map.put("officeCode",off_id);
		             map.put("statusHeading", statusText);
		             map.put("Sub_Jour", Sub_Jour);
	        	
	            HEad_Str="TDA_ALL";
	            }
	        
	        }
	        else{
	        	
//	        	Connection con = null;
	        	System.out.println("all starts:");
			        if(type_prf.equals("TDA"))
			        {
			        	System.out.println("TDA22");
			        	 if (txtoption.equalsIgnoreCase("EXCEL")) {
			        	 try
	                       {
			        		  
	                    	   String sql_HOA_NCR= "select * from (SELECT A.*,A.VOUCHER_NO ,B.ACCEPTING_JVR_NO ,B.ACCEPTING_JVR_DATE  FROM\r\n"
	                    	   		+ "\r\n"
	                    	   		+ "(SELECT mst.VOUCHER_DATE ,\r\n"
	                    	   		+ "    mst.VOUCHER_NO ,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA as \"TDA_OR_TCA\",\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
	                    	   		+ "    ) As ORGNAME,\r\n"
	                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
	                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                    	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
	                    	   		+ "    )AS \"ACCEPTEDNAME\",\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT as \"TOTAL_AMOUNT\",\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_NO ,\r\n"
	                    	   		+ "    mst.ORGINATING_JVR_DATE ,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_NO ,\r\n"
	                    	   		+ "    mst.RESPONDING_JVR_DATE ,\r\n"
	                    	   		+ "    Mst.Status,\r\n"
	                    	   		+ "    mst.PARTICULARS ,MST.ACCEPTING_SLNO,mst.acceptance_status ,\r\n"
	                    	   		+ "    MST.SUPPLEMENT_NO\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAO','TDACB')\r\n"
	                    	   		+ " And Mst.Cashbook_Year=?::int\r\n"
	                    	   		+ "  And Mst.Cashbook_Month=?::int)A\r\n"
	                    	   		+ "  \r\n"
	                    	   		+ "  Left Outer JOIN\r\n"
	                    	   		+ "    \r\n"
	                    	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
	                    	   		+ "    mst.VOUCHER_NO ,\r\n"
	                    	   		+ "    mst.TDA_OR_TCA,\r\n"
	                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
	                    	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
	                    	   		+ "    mst.STATUS,\r\n"
	                    	   		+ "    MST.SUPPLEMENT_NO\r\n"
	                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                    	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
	                    	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
	                    	   		+ "  And Mst.Status                                  ='L'\r\n"
	                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAA')\r\n"
	                    	   		+ ")B\r\n"
	                    	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
	                    	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
	                    	   		+ "  order by a.Orgname,a.VOUCHER_NO)f"+Sub_Jour;
	                       	   ps2=con.prepareStatement(sql_HOA_NCR);
	                       
	                           ps2.setInt(1,cashYear);
	                           ps2.setInt(2,cashMonth);
	                           //System.out.println("before execution HOA Balance DR type check");
	                           ResultSet rs1=ps2.executeQuery();
	                           generateExcelFile(rs1,response,"TDA_LIST_ALL");
	                       }
	                       
	                       catch(Exception e)
	                       {
	                    	   System.out.println("Error in Report**"+e);
	                       }}
			        	 else {
				        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/TDA_List_All.jasper"));

			        	 }
			        }
			        else
			        {
			        	 if (txtoption.equalsIgnoreCase("EXCEL")) {
				        	 try
		                       {
				        		  
		                    	   String sql= "select * from (SELECT A.*,B.ACCEPTING_JVR_NO ,B.ACCEPTING_JVR_DATE  FROM\r\n"
		                    	   		+ "\r\n"
		                    	   		+ "(SELECT mst.VOUCHER_DATE  ,\r\n"
		                    	   		+ "    mst.VOUCHER_NO ,\r\n"
		                    	   		+ "    mst.TDA_OR_TCA as \"TDA_OR_TCA\",\r\n"
		                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
		                    	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
		                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
		                    	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
		                    	   		+ "    )As Orgname,\r\n"
		                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
		                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
		                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
		                    	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
		                    	   		+ "    )AS \"ACCEPTEDNAME\",\r\n"
		                    	   		+ "    mst.TOTAL_AMOUNT as \"TOTAL_AMOUNT\",\r\n"
		                    	   		+ "    mst.ORGINATING_JVR_NO ,\r\n"
		                    	   		+ "    mst.ORGINATING_JVR_DATE ,\r\n"
		                    	   		+ "    mst.RESPONDING_JVR_NO ,\r\n"
		                    	   		+ "    mst.RESPONDING_JVR_DATE ,\r\n"
		                    	   		+ "    Mst.Status ,\r\n"
		                    	   		+ "    mst.PARTICULARS as \"PARTICULARS\",MST.ACCEPTING_SLNO,mst.acceptance_status ,\r\n"
		                    	   		+ "    MST.SUPPLEMENT_NO\r\n"
		                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
		                    	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
		                    	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
		                    	   		+ "  And Mst.Status                                  ='L'\r\n"
		                    	   		+ "  AND mst.TDA_OR_TCA             in ('TCAO','TCACB')\r\n"
		                    	   		+ " And Mst.Cashbook_Year=?::int\r\n"
		                    	   		+ "  And Mst.Cashbook_Month=?::int)A\r\n"
		                    	   		+ " \r\n"
		                    	   		+ "  Left Outer JOIN\r\n"
		                    	   		+ "    \r\n"
		                    	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
		                    	   		+ "    mst.VOUCHER_NO,\r\n"
		                    	   		+ "    mst.TDA_OR_TCA,\r\n"
		                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
		                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
		                    	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
		                    	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
		                    	   		+ "    mst.STATUS,\r\n"
		                    	   		+ "    MST.SUPPLEMENT_NO\r\n"
		                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
		                    	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
		                    	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
		                    	   		+ "  And Mst.Status                                  ='L'\r\n"
		                    	   		+ "  AND mst.TDA_OR_TCA             in ('TCAA')\r\n"
		                    	   		+ ")B\r\n"
		                    	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
		                    	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
		                    	   		+ "  order by a.Orgname,a.VOUCHER_NO)f"+Sub_Jour;
		                       	   ps2=con.prepareStatement(sql);
		                       
		                           ps2.setInt(1,cashYear);
		                           ps2.setInt(2,cashMonth);
		                           //System.out.println("before execution HOA Balance DR type check");
		                           ResultSet rs1=ps2.executeQuery();
		                           generateExcelFile(rs1,response,"TCA_LIST_ALL");
		                       }
		                       
		                       catch(Exception e)
		                       {
		                    	   System.out.println("Error in Report**"+e);
		                       }}
				        	 else {
			        	
				        	 }reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/TCA_List_All.jasper"));
			        }
			        map.put("cashyear",cashYear);     
		        	 map.put("cashmonth",cashMonth);
		             map.put("monthinwords",monthInWords);
		             map.put("statusHeading", statusText);
		             map.put("sub_q", Sub_Jour);
	        }
	        HEad_Str="TDA_ALL";
	    }
	        
	        
	        
//-------------------------------------------------------Date wise------------------------------------------------------------------------------------------	        
	        
	        
	        else  if(request.getParameter("month_year").equalsIgnoreCase("more_cb"))
	        {
	        	int txtCB_Month_to=0,txtCB_Year_from=0,txtCB_Year_to=0,txtCB_Month_from=0;
	        	
	       	 try{txtCB_Year_from=Integer.parseInt(request.getParameter("txtCB_Year_from"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{txtCB_Year_to=Integer.parseInt(request.getParameter("txtCB_Year_to"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	           
	            txtCB_Month_from=Integer.parseInt(request.getParameter("txtCB_Month_from"));
	            txtCB_Month_to=Integer.parseInt(request.getParameter("txtCB_Month_to"));
	            
	           // type_prf=request.getParameter("proformatype");
	            txtoption=request.getParameter("txtoption");//PDF OR EXCEL
	            
	            
	            
	            
	            
	            
	               
	       String monthInWords="";
	       if(txtCB_Month_from==1)
	           monthInWords="January";
	       else if(txtCB_Month_from==2)
	           monthInWords="February";
	       else if(txtCB_Month_from==3)
	           monthInWords="March";
	       else if(txtCB_Month_from==4)
	           monthInWords="April";
	       else if(txtCB_Month_from==5)
	           monthInWords="May";
	       else if(txtCB_Month_from==6)
	           monthInWords="June";
	       else if(txtCB_Month_from==7)
	           monthInWords="July";
	       else if(txtCB_Month_from==8)
	           monthInWords="August";
	       else if(txtCB_Month_from==9)
	           monthInWords="September";
	       else if(txtCB_Month_from==10)
	           monthInWords="October";
	       else if(txtCB_Month_from==11)
	           monthInWords="November";
	       else if(txtCB_Month_from==12)
	           monthInWords="December";
	       
	       String monthInWords_to="";
	       if(txtCB_Month_to==1)
	    	   monthInWords_to="January";
	       else if(txtCB_Month_to==2)
	    	   monthInWords_to="February";
	       else if(txtCB_Month_to==3)
	    	   monthInWords_to="March";
	       else if(txtCB_Month_to==4)
	    	   monthInWords_to="April";
	       else if(txtCB_Month_to==5)
	    	   monthInWords_to="May";
	       else if(txtCB_Month_to==6)
	    	   monthInWords_to="June";
	       else if(txtCB_Month_to==7)
	    	   monthInWords_to="July";
	       else if(txtCB_Month_to==8)
	    	   monthInWords_to="August";
	       else if(txtCB_Month_to==9)
	    	   monthInWords_to="September";
	       else if(txtCB_Month_to==10)
	    	   monthInWords_to="October";
	       else if(txtCB_Month_to==11)
	    	   monthInWords_to="November";
	       else if(txtCB_Month_to==12)
	    	   monthInWords_to="December";
	       
	       String from_to="From "+monthInWords+" "+txtCB_Year_from+" to "+monthInWords_to+" "+txtCB_Year_to;
	           System.out.println("from_to:::"+from_to);
	       if(displayOrder.equalsIgnoreCase("RW") ||displayOrder.equalsIgnoreCase("OW"))
	       {
	       	
	       	if(displayOrder.equalsIgnoreCase("RW"))
	           {
	           	cmbOffice_code=Integer.parseInt(request.getParameter("txtRegionId"));
	           	
	          
	       	
	       	 try {
	       	        PreparedStatement ps=null;
	       	        ResultSet rs=null;
	       	       
	       	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	       	        ps.setInt(1,cmbOffice_code);
	       	        rs=ps.executeQuery();
	       	        if(rs.next()) {
	       	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	       	        }
	       	        }
	       	        catch (SQLException e) {
	       	            System.out.println("SQL Exception -->"+e);
	       	        }
	       	
	       	if(type_prf.equals("TDA"))
		        {
	       		if (txtoption.equalsIgnoreCase("EXCEL")) {
	       		 try
                 {
	        		  
              	   String sql= "select * from(SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
              	   		+ "\r\n"
              	   		+ "(SELECT mst.VOUCHER_DATE ,\r\n"
              	   		+ "    mst.VOUCHER_NO ,\r\n"
              	   		+ "    mst.TDA_OR_TCA,\r\n"
              	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
              	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
              	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
              	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
              	   		+ "    )As orgname,\r\n"
              	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
              	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
              	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
              	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
              	   		+ "    )AS ACCEPTEDNAME,\r\n"
              	   		+ "    mst.TOTAL_AMOUNT ,\r\n"
              	   		+ "    mst.ORGINATING_JVR_NO ,\r\n"
              	   		+ "    mst.ORGINATING_JVR_DATE,\r\n"
              	   		+ "    mst.RESPONDING_JVR_NO ,\r\n"
              	   		+ "    mst.RESPONDING_JVR_DATE ,\r\n"
              	   		+ "    Mst.Status,\r\n"
              	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO, \r\n"
              	   		+ "coalesce(mst.ACCEPTANCE_STATUS,NULL,'-',mst.ACCEPTANCE_STATUS) AS ACCEPTANCE_STATUS\r\n"
              	   		+ "----case when mst.ACCEPTANCE_STATUS,NULL\r\n"
              	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
              	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
              	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
              	   		+ "  And Mst.Status                                  ='L'\r\n"
              	   		+ "  AND mst.TDA_OR_TCA             in ('TDAO','TDACB')\r\n"
              	   		+ "   And To_Date((mst.Cashbook_Month\r\n"
              	   		+ "  ||'-'\r\n"
              	   		+ "  || mst.Cashbook_Year),'mm-yyyy') BETWEEN To_Date(?::int  \r\n"
              	   		+ "  ||'-'\r\n"
              	   		+ "  ||?,'mm-yyyy')\r\n"
              	   		+ "AND to_date(?::int  \r\n"
              	   		+ "  ||'-'\r\n"
              	   		+ "  ||?::int,'mm-yyyy')   \r\n"
              	   
              	   		+ "    and mst.accounting_for_office_id in (SELECT office_id  \r\n"
              	   		+ "FROM COM_MST_ALL_OFFICES_VIEW    \r\n"
              	   		+ "WHERE region_office_id =?::int)  \r\n"
              	   		+ ")A\r\n"
              	   		+ "  Left Outer JOIN\r\n"
              	   		+ "    \r\n"
              	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
              	   		+ "    mst.VOUCHER_NO,\r\n"
              	   		+ "    mst.TDA_OR_TCA,\r\n"
              	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
              	   		+ "    mst.TOTAL_AMOUNT,\r\n"
              	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
              	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
              	   		+ "    mst.STATUS\r\n"
              	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
              	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
              	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
              	   		+ "  And Mst.Status                                  ='L'\r\n"
              	   		+ "  AND mst.TDA_OR_TCA             in ('TDAA')\r\n"
              	   		+ ")B\r\n"
              	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
              	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO   \r\n"
              	   		+ "  order by a.Orgname,a.VOUCHER_NO)f" +Sub_Jour;
                 	   ps2=con.prepareStatement(sql);
                 
                     ps2.setInt(1,txtCB_Month_from);
                     ps2.setInt(2,txtCB_Year_from);
                     ps2.setInt(3,txtCB_Month_to);
                     ps2.setInt(4,txtCB_Year_to );
                     ps2.setInt(5,cmbOffice_code);
                     if(!sub_q.isEmpty()) {
                  	   ps2.setString(5, sub_q);
                     }
                     
               
                     ResultSet rs1=ps2.executeQuery();
                     generateExcelFile(rs1,response,"TDA_LIST_RegionWise_DATE");
                 }
                 
                 catch(Exception e)
                 {
              	   System.out.println("Error in Report**"+e);
                 }
	       			
	       		}
	       		else {
		        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/date_wise/TDA_List_All_rw_date.jasper"));

	       		}
		        
		        }
		        else
		        {
		        	if (txtoption.equalsIgnoreCase("EXCEL")) {
			       		 try
		                 {
			        		  
		              	   String sql= "select * from (SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
		              	   		+ "(SELECT mst.VOUCHER_DATE ,\r\n"
		              	   		+ "    mst.VOUCHER_NO  ,\r\n"
		              	   		+ "    mst.TDA_OR_TCA ,\r\n"
		              	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
		              	   		+ "   (SELECT ACCOUNTING_UNIT_NAME \r\n"
		              	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
		              	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
		              	   		+ "    )As Orgname,\r\n"
		              	   		+ "      Mst.Trf_Accounting_Unit_Id, \r\n"
		              	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
		              	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
		              	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
		              	   		+ "    ) as ACCEPTEDNAME,\r\n"
		              	   		+ "    mst.TOTAL_AMOUNT,\r\n"
		              	   		+ "    mst.ORGINATING_JVR_NO ,\r\n"
		              	   		+ "    mst.ORGINATING_JVR_DATE ,\r\n"
		              	   		+ "    mst.RESPONDING_JVR_NO ,\r\n"
		              	   		+ "    mst.RESPONDING_JVR_DATE,\r\n"
		              	   		+ "    Mst.Status,\r\n"
		              	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO,\r\n"
		              	   		+ " coalesce(mst.ACCEPTANCE_STATUS,NULL,'-',mst.ACCEPTANCE_STATUS) AS ACCEPTANCE_STATUS\r\n"
		              	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
		              	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
		              	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
		              	   		+ "  And Mst.Status                                  ='L'\r\n"
		              	   		+ "  AND mst.TDA_OR_TCA             in ('TCAO','TCACB')\r\n"
		              	   		+ "   And To_Date((mst.Cashbook_Month\r\n"
		              	   		+ "  ||'-'\r\n"
		              	   		+ "  || mst.Cashbook_Year),'mm-yyyy') BETWEEN To_Date(?::int\r\n"
		              	   		+ "  ||'-'\r\n"
		              	   		+ "  ||?,'mm-yyyy')\r\n"
		              	   		+ "AND to_date(?::Int\r\n"
		              	   		+ "  ||'-'\r\n"
		              	   		+ "  ||?::int,'mm-yyyy') \r\n"
		              	   		+ " -----$P!{sub_q}\r\n"
		              	   		+ "  and mst.accounting_for_office_id in (SELECT office_id\r\n"
		              	   		+ "FROM COM_MST_ALL_OFFICES_VIEW\r\n"
		              	   		+ "WHERE region_office_id =?::int)\r\n"
		              	   		+ ")A\r\n"
		              	   		+ "  Left Outer JOIN\r\n"
		              	   		+ "    \r\n"
		              	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
		              	   		+ "    mst.VOUCHER_NO,\r\n"
		              	   		+ "    mst.TDA_OR_TCA,\r\n"
		              	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
		              	   		+ "    mst.TOTAL_AMOUNT,\r\n"
		              	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
		              	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
		              	   		+ "    mst.STATUS\r\n"
		              	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
		              	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
		              	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
		              	   		+ "  And Mst.Status                                  ='L'\r\n"
		              	   		+ "  AND mst.TDA_OR_TCA             in ('TCAA')\r\n"
		              	   		+ ")B\r\n"
		              	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
		              	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
		              	   		+ "  order by a.Orgname,a.VOUCHER_NO)f" +Sub_Jour;
		                 	   ps2=con.prepareStatement(sql);
		                 
		                     ps2.setInt(1,txtCB_Month_from);
		                     ps2.setInt(2,txtCB_Year_from);
		                     ps2.setInt(3,txtCB_Month_to);
		                     ps2.setInt(4,txtCB_Year_to );
		                     ps2.setInt(5,cmbOffice_code);
		                     if(!sub_q.isEmpty()) {
		                  	   ps2.setString(5, sub_q);
		                     }
		                     
		               
		                     ResultSet rs1=ps2.executeQuery();
		                     generateExcelFile(rs1,response,"TCA_LIST_RegionWise_DATE");
		                 }
		                 
		                 catch(Exception e)
		                 {
		              	   System.out.println("Error in Report**"+e);
		                 }
			       			
			       		}
		        	else {
			        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/date_wise/TCA_List_All_rw_date.jasper"));

		        	}
		        }
	      
	      
	         			 map.put("txtCB_Year_from",txtCB_Year_from);     
		        	 map.put("txtCB_Year_to",txtCB_Year_to);
		        	 
		        	 map.put("txtCB_Month_from",txtCB_Month_from);     
		        	 map.put("txtCB_Month_to",txtCB_Month_to);
		               
		             map.put("from_to",from_to);
		             map.put("officeCode",cmbOffice_code);
		             map.put("statusHeading", statusText);
		             map.put("sub_q", sub_q);
		                 HEad_Str="TDA_ALL_date";
	       	
	       }
	        else if(displayOrder.equalsIgnoreCase("OW"))
	        {
	        	cmbOffice_code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	        	try {
	       	        PreparedStatement ps=null;
	       	        ResultSet rs=null;
	       	       
	       	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	       	        ps.setInt(1,cmbOffice_code);
	       	        rs=ps.executeQuery();
	       	        if(rs.next()) {
	       	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	       	        }
	       	        }
	       	        catch (SQLException e) {
	       	            System.out.println("SQL Exception -->"+e);
	       	        }
	       	
	       	if(type_prf.equals("TDA"))
		        {
		        
	       		if (txtoption.equalsIgnoreCase("EXCEL")) {
		        	 try
                      {
		        		  
                   	   String sql= "select * from(SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
                   	   		+ "(SELECT mst.VOUCHER_DATE,\r\n"
                   	   		+ "    mst.VOUCHER_NO,\r\n"
                   	   		+ "    mst.TDA_OR_TCA,\r\n"
                   	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
                   	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
                   	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n" 
                   	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
                   	   		+ "    )As Orgname,\r\n"
                   	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
                   	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
                   	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
                   	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
                   	   		+ "    )AS acceptedName,\r\n"
                   	   		+ "    mst.TOTAL_AMOUNT,\r\n"
                   	   		+ "    mst.ORGINATING_JVR_NO,\r\n"
                   	   		+ "    mst.ORGINATING_JVR_DATE,\r\n"
                   	   		+ "    mst.RESPONDING_JVR_NO,\r\n"
                   	   		+ "    mst.RESPONDING_JVR_DATE,\r\n"
                   	   		+ "    Mst.Status,\r\n"
                   	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO,mst.ACCEPTANCE_STATUS\r\n"
                   	   		+ " \r\n"
                   	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
                   	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
                   	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
                   	   		+ "  And Mst.Status                                  ='L'\r\n"
                   	   		+ "  AND mst.TDA_OR_TCA             in ('TDAO','TDACB')\r\n"
                   	   		+ "   And To_Date((mst.Cashbook_Month\r\n"
                   	   		+ "  ||'-'\r\n"
                   	   		+ "  || mst.Cashbook_Year),'mm-yyyy') BETWEEN To_Date(?\r\n"
                   	   		+ "  ||'-'\r\n"
                   	   		+ "  ||?,'mm-yyyy')\r\n"
                   	   		+ "AND to_date(?\r\n"
                   	   		+ "  ||'-'\r\n"
                   	   		+ "  ||?,'mm-yyyy') \r\n"
                   	   		+ "    and mst.accounting_for_office_id in (?)\r\n"
                   	   		+ ")A\r\n"
                   	   		+ "  Left Outer JOIN\r\n"
                   	   		+ "    \r\n"
                   	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
                   	   		+ "    mst.VOUCHER_NO,\r\n"
                   	   		+ "    mst.TDA_OR_TCA,\r\n"
                   	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
                   	   		+ "    mst.TOTAL_AMOUNT,\r\n"
                   	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
                   	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
                   	   		+ "    mst.STATUS\r\n"
                   	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
                   	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
                   	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
                   	   		+ "  And Mst.Status                                  ='L'\r\n"
                   	   		+ "  AND mst.TDA_OR_TCA             in ('TDAA')\r\n"
                   	   		+ ")B\r\n"
                   	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
                   	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
                   	   		+ "  order by a.Orgname,a.VOUCHER_NO)f  " +Sub_Jour;
                      	   ps2=con.prepareStatement(sql);
                      
                          ps2.setInt(1,txtCB_Month_from);
                          ps2.setInt(2,txtCB_Year_from);
                          ps2.setInt(3,txtCB_Month_to);
                          ps2.setInt(4,txtCB_Year_to );
                          ps2.setInt(5,off_id);
                          if(!sub_q.isEmpty()) {
                       	   ps2.setString(5, sub_q);
                          }
                          
                    
                          ResultSet rs1=ps2.executeQuery();
                          generateExcelFile(rs1,response,"TDA_LIST_OfficeWise_DATE");
                      }
                      
                      catch(Exception e)
                      {
                   	   System.out.println("Error in Report**"+e);
                      }
	       		}
		        	 else {
			        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/date_wise/TDA_List_All_rw_ow_date.jasper"));

		        	 }
		        }
		        else
		        {
		        	if (txtoption.equalsIgnoreCase("EXCEL")) {
			        	 try
	                      {
			        		  
	                   	   String sql= "select * from(SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
	                   	   		+ "(SELECT mst.VOUCHER_DATE,\r\n"
	                   	   		+ "    mst.VOUCHER_NO,\r\n"
	                   	   		+ "    mst.TDA_OR_TCA,\r\n"
	                   	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                   	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                   	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                   	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
	                   	   		+ "    )As Orgname,\r\n"
	                   	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
	                   	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
	                   	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
	                   	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
	                   	   		+ "    )AS acceptedName,\r\n"
	                   	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                   	   		+ "    mst.ORGINATING_JVR_NO,\r\n"
	                   	   		+ "    mst.ORGINATING_JVR_DATE,\r\n"
	                   	   		+ "    mst.RESPONDING_JVR_NO,\r\n"
	                   	   		+ "    mst.RESPONDING_JVR_DATE,\r\n"
	                   	   		+ "    Mst.Status,\r\n"
	                   	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO,\r\n"
	                   	   		+ " coalesce(mst.ACCEPTANCE_STATUS,NULL,'-',mst.ACCEPTANCE_STATUS) AS ACCEPTANCE_STATUS\r\n"
	                   	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                   	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
	                   	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
	                   	   		+ "  And Mst.Status                                  ='L'\r\n"
	                   	   		+ "  AND mst.TDA_OR_TCA             in ('TCAO','TCACB')\r\n"
	                   	   		+ "   And To_Date((mst.Cashbook_Month\r\n"
	                   	   		+ "  ||'-'\r\n"
	                   	   		+ "  || mst.Cashbook_Year),'mm-yyyy') BETWEEN To_Date(?\r\n"
	                   	   		+ "  ||'-'\r\n"
	                   	   		+ "  ||?,'mm-yyyy')\r\n"
	                   	   		+ "AND to_date(?\r\n"
	                   	   		+ "  ||'-'\r\n"
	                   	   		+ "  ||?,'mm-yyyy')  \r\n"
	                   	   		+ " ----$P!{sub_q}\r\n"
	                   	   		+ "  and mst.accounting_for_office_id in (?::int) \r\n"
	                   	   		+ ")A\r\n"
	                   	   		+ "  Left Outer JOIN \r\n"
	                   	   		+ "    \r\n"
	                   	   		+ "  (SELECT mst.VOUCHER_DATE, \r\n"
	                   	   		+ "    mst.VOUCHER_NO,\r\n"
	                   	   		+ "    mst.TDA_OR_TCA,\r\n"
	                   	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
	                   	   		+ "    mst.TOTAL_AMOUNT,\r\n"
	                   	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
	                   	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
	                   	   		+ "    mst.STATUS\r\n"
	                   	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
	                   	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
	                   	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
	                   	   		+ "  And Mst.Status                                  ='L'\r\n"
	                   	   		+ "  AND mst.TDA_OR_TCA             in ('TCAA')\r\n"
	                   	   		+ ")B\r\n"
	                   	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
	                   	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO \r\n"
	                   	   		+ "  order by a.Orgname,a.VOUCHER_NO)f " +Sub_Jour;
	                      	   ps2=con.prepareStatement(sql);
	                      
	                          ps2.setInt(1,txtCB_Month_from);
	                          ps2.setInt(2,txtCB_Year_from);
	                          ps2.setInt(3,txtCB_Month_to);
	                          ps2.setInt(4,txtCB_Year_to );
	                          ps2.setInt(5,off_id);
	                          if(!sub_q.isEmpty()) {
	                       	   ps2.setString(5, sub_q);
	                          }
	                          
	                    
	                          ResultSet rs1=ps2.executeQuery();
	                          generateExcelFile(rs1,response,"TCA_LIST_OfficeWise_DATE");
	                      }
	                      
	                      catch(Exception e)
	                      {
	                   	   System.out.println("Error in Report**"+e);
	                      }
		       		}
		        	else {
			        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/date_wise/TCA_List_All_rw_ow_date.jasper"));

		        	}
		        }
	      
	       			 map.put("txtCB_Year_from",txtCB_Year_from);     
		        	 map.put("txtCB_Year_to",txtCB_Year_to);
		        	 
		        	 map.put("txtCB_Month_from",txtCB_Month_from);     
		        	 map.put("txtCB_Month_to",txtCB_Month_to);
		               
		             map.put("from_to",from_to);
		             map.put("officeCode",off_id);
		             map.put("statusHeading", statusText);
		             map.put("sub_q", sub_q);
		                 HEad_Str="TDA_ALL_date";
	        }
	       }
	       
	       //---ALL----
	       else{
	       	System.out.println("all starts from datewise:");
			        if(type_prf.equals("TDA"))
			        {
			        	if (txtoption.equalsIgnoreCase("EXCEL")) {
				        	 try
		                       {
				        		  
		                    	   String sql= "select * from(SELECT A.*,B.ACCEPTING_JVR_NO,B.ACCEPTING_JVR_DATE FROM\r\n"
		                    	   		+ "\r\n"
		                    	   		+ "(SELECT mst.VOUCHER_DATE,\r\n"
		                    	   		+ "    mst.VOUCHER_NO,\r\n"
		                    	   		+ "    mst.TDA_OR_TCA,\r\n"
		                    	   		+ "   case when Mst.Acceptance_Status='R' then Mst.Tda_Or_Tca||'(Rejected)' else Mst.Tda_Or_Tca end as type_one,\r\n"
		                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
		                    	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
		                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
		                    	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
		                    	   		+ "    )As Orgname,\r\n"
		                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
		                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
		                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
		                    	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
		                    	   		+ "    )AS acceptedName,\r\n"
		                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
		                    	   		+ "    mst.ORGINATING_JVR_NO,\r\n"
		                    	   		+ "    mst.ORGINATING_JVR_DATE,\r\n"
		                    	   		+ "    mst.RESPONDING_JVR_NO,\r\n"
		                    	   		+ "    mst.RESPONDING_JVR_DATE,\r\n"
		                    	   		+ "    Mst.Status,\r\n"
		                    	   		+ "    mst.PARTICULARS,MST.ACCEPTING_SLNO,\r\n"
		                    	   		+ " case when mst.ACCEPTANCE_STATUS is NULL then '-' else  mst.ACCEPTANCE_STATUS end as acceptance_status\r\n"
		                    	   		+ "  --mst.ACCEPTANCE_STATUS\r\n"
		                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
		                    	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
		                    	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
		                    	   		+ "  And Mst.Status                                  ='L'\r\n"
		                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAO','TDACB')\r\n"
		                    	   		+ "   And To_Date((mst.Cashbook_Month\r\n"
		                    	   		+ "  ||'-'\r\n"
		                    	   		+ "  || mst.Cashbook_Year),'mm-yyyy') BETWEEN To_Date(? \r\n"
		                    	   		+ "  ||'-'\r\n"
		                    	   		+ "  ||?,'mm-yyyy')\r\n"
		                    	   		+ "AND to_date(?\r\n"
		                    	   		+ "  ||'-'\r\n"
		                    	   		+ "  ||?,'mm-yyyy')  \r\n"
		                    	   
		                    	   		+ ")A\r\n"
		                    	   		+ "  Left Outer JOIN\r\n"
		                    	   		+ "    \r\n"
		                    	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
		                    	   		+ "    mst.VOUCHER_NO,\r\n"
		                    	   		+ "    mst.TDA_OR_TCA,\r\n"
		                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
		                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
		                    	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
		                    	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
		                    	   		+ "    mst.STATUS\r\n"
		                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
		                    	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
		                    	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
		                    	   		+ "  And Mst.Status                                  ='L'\r\n"
		                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAA')\r\n"
		                    	   		+ ")B\r\n"
		                    	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
		                    	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
		                    	   		+ "  order by a.Orgname,a.VOUCHER_NO)f " +Sub_Jour;
		                       	   ps2=con.prepareStatement(sql);
		                       
		                           ps2.setInt(1,txtCB_Month_from);
		                           ps2.setInt(2,txtCB_Year_from);
		                           ps2.setInt(3,txtCB_Month_to);
		                           ps2.setInt(4,txtCB_Year_to );
//		                           ps2.setString(5,Sub_Jour);
		                           if(!sub_q.isEmpty()) {
		                        	   ps2.setString(5, sub_q);
		                           }
		                           
		                     
		                           ResultSet rs1=ps2.executeQuery();
		                           generateExcelFile(rs1,response,"TDA_LIST_ALL_DATE");
		                       }
		                       
		                       catch(Exception e)
		                       {
		                    	   System.out.println("Error in Report**"+e);
		                       }}
				        	 else {
			        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/date_wise/TDA_List_All_date.jasper"));
				        	 }
				        	 }
			        else
			        {
			        	if (txtoption.equalsIgnoreCase("EXCEL")) {
			        		 try
		                       {
				        		  
		                    	   String sql= "SELECT *\r\n"
		                    	   		+ "FROM\r\n"
		                    	   		+ "  (SELECT A.*,\r\n"
		                    	   		+ "    B.ACCEPTING_JVR_NO,\r\n"
		                    	   		+ "    B.ACCEPTING_JVR_DATE\r\n"
		                    	   		+ "  FROM\r\n"
		                    	   		+ "    (SELECT mst.VOUCHER_DATE,\r\n"
		                    	   		+ "      mst.VOUCHER_NO,\r\n"
		                    	   		+ "      mst.TDA_OR_TCA,\r\n"
		                    	   		+ "      CASE\r\n"
		                    	   		+ "        WHEN Mst.Acceptance_Status='R'\r\n"
		                    	   		+ "        THEN Mst.Tda_Or_Tca\r\n"
		                    	   		+ "          ||'(Rejected)'\r\n"
		                    	   		+ "        ELSE Mst.Tda_Or_Tca\r\n"
		                    	   		+ "      END AS type_one,\r\n"
		                    	   		+ "      mst.ACCOUNTING_UNIT_ID,\r\n"
		                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
		                    	   		+ "      FROM FAS_MST_ACCT_UNITS unit\r\n"
		                    	   		+ "      WHERE Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
		                    	   		+ "      )AS Orgname,\r\n"
		                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
		                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
		                    	   		+ "      FROM FAS_MST_ACCT_UNITS unit\r\n"
		                    	   		+ "      WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
		                    	   		+ "      )AS acceptedName,\r\n"
		                    	   		+ "      mst.TOTAL_AMOUNT,\r\n"
		                    	   		+ "      mst.ORGINATING_JVR_NO,\r\n"
		                    	   		+ "      mst.ORGINATING_JVR_DATE,\r\n"
		                    	   		+ "      mst.RESPONDING_JVR_NO,\r\n"
		                    	   		+ "      mst.RESPONDING_JVR_DATE,\r\n"
		                    	   		+ "      Mst.Status,\r\n"
		                    	   		+ "      mst.PARTICULARS,\r\n"
		                    	   		+ "      Mst.Accepting_Slno,\r\n"
		                    	   		+ "     -- Decode(Mst.Acceptance_Status,Null,'-',Mst.Acceptance_Status) As Acceptance_Status\r\n"
		                    	   		+ "	case when Mst.Acceptance_Status is Null then '-' else Mst.Acceptance_Status end as Acceptance_Status\r\n"
		                    	   		+ "      -- mst.ACCEPTANCE_STATUS\r\n"
		                    	   		+ "    FROM Fas_Tda_Tca_Raised_Mst Mst\r\n"
		                    	   		+ "    WHERE mst.ORGINATING_JVR_NO IS NOT NULL\r\n"
		                    	   		+ "    AND mst.orginating_jvr_date IS NOT NULL\r\n"
		                    	   		+ "    AND Mst.Status               ='L'\r\n"
		                    	   		+ "    AND mst.TDA_OR_TCA          IN ('TCAO','TCACB')\r\n"
		                    	   		+ "    AND To_Date((mst.Cashbook_Month\r\n"
		                    	   		+ "      ||'-'\r\n"
		                    	   		+ "      || mst.Cashbook_Year),'mm-yyyy') BETWEEN To_Date(?\r\n"
		                    	   		+ "      ||'-'\r\n"
		                    	   		+ "      ||?,'mm-yyyy')\r\n"
		                    	   		+ "    AND to_date(?\r\n"
		                    	   		+ "      ||'-'\r\n"
		                    	   		+ "      ||?,'mm-yyyy') \r\n"
		                    	   		
		                    	   		+ "    )A\r\n"
		                    	   		+ "  LEFT OUTER JOIN\r\n"
		                    	   		+ "    (SELECT mst.VOUCHER_DATE,\r\n"
		                    	   		+ "      mst.VOUCHER_NO,\r\n"
		                    	   		+ "      mst.TDA_OR_TCA,\r\n"
		                    	   		+ "      mst.ACCOUNTING_UNIT_ID,\r\n"
		                    	   		+ "      mst.TOTAL_AMOUNT,\r\n"
		                    	   		+ "      mst.ACCEPTING_JVR_NO,\r\n"
		                    	   		+ "      mst.ACCEPTING_JVR_DATE,\r\n"
		                    	   		+ "      mst.STATUS\r\n"
		                    	   		+ "    FROM Fas_Tda_Tca_Raised_Mst Mst\r\n"
		                    	   		+ "    WHERE mst.ACCEPTING_JVR_NO IS NOT NULL\r\n"
		                    	   		+ "    AND mst.ACCEPTING_JVR_DATE IS NOT NULL\r\n"
		                    	   		+ "    AND Mst.Status              ='L'\r\n"
		                    	   		+ "    AND mst.TDA_OR_TCA         IN ('TCAA')\r\n"
		                    	   		+ "    )B\r\n"
		                    	   		+ "  ON A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
		                    	   		+ "  AND A.Accepting_Slno       =B.Voucher_No\r\n"
		                    	   		+ "  ORDER BY a.Orgname,\r\n"
		                    	   		+ "    a.VOUCHER_NO\r\n"
		                    	   		+ "  )f" +Sub_Jour;
		                       	   ps2=con.prepareStatement(sql);
		                       
		                           ps2.setInt(1,txtCB_Month_from);
		                           ps2.setInt(2,txtCB_Year_from);
		                           ps2.setInt(3,txtCB_Month_to);
		                           ps2.setInt(4,txtCB_Year_to );
//		                           ps2.setString(5,Sub_Jour);
		                           if(!sub_q.isEmpty()) {
		                        	   ps2.setString(5, sub_q);
		                           }
		                           
		                     
		                           ResultSet rs1=ps2.executeQuery();
		                           generateExcelFile(rs1,response,"TCA_LIST_ALL_DATE");
		                       }
		                       
		                       catch(Exception e)
		                       {
		                    	   System.out.println("Error in Report**"+e);
		                       }
			        		
			        	}
			        	else {
				        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA(TCA_super_user/date_wise/TCA_List_All_date.jasper"));

			        	}
			        }
			     map.put("txtCB_Year_from",txtCB_Year_from);     
		        	 map.put("txtCB_Year_to",txtCB_Year_to);
		        	 map.put("txtCB_Month_from",txtCB_Month_from);     
		        	 map.put("txtCB_Month_to",txtCB_Month_to);
		             map.put("from_to",from_to);
		             map.put("statusHeading", statusText);
		             map.put("sub_q", sub_q);
	       }
	       HEad_Str="TDA_ALL_date";
	        }
	
	        if (!reportFile.exists())
	            throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	             jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	      //  map.put("Sub_Accept",Sub_Accept);     
       	     map.put("Sub_Jour",Sub_Jour);
          //  map.put("Sub_Resp",Sub_Resp);
      // 	 System.out.println("statusText "+statusText);
            System.out.println("MAP >> "+map);
            System.out.println("reportFile >>  gettt "+reportFile);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);   
	        if (txtoption.equalsIgnoreCase("PDF"))   
            {
            		System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\""+HEad_Str+".pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        	}
//	        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
//	        {
//                	response.setContentType("application/vnd.ms-excel");
//                    response.setHeader ("Content-Disposition", "attachment;filename=\""+HEad_Str+".xls\"");
//                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
//                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
//                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
//                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
//                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
//                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
//                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
//                    exporterXLS.exportReport(); 
//                    byte []bytes;
//                    bytes = xlsReport.toByteArray();
//                    ServletOutputStream ouputStream = response.getOutputStream();
//                    ouputStream.write(bytes, 0, bytes.length);
//                    ouputStream.flush();
//                    ouputStream.close();
//	        }
//	        else if (txtoption.equalsIgnoreCase("EXCEL")) {
//	            response.setContentType("application/vnd.ms-excel");
//	            response.setHeader("Content-Disposition", "attachment;filename=\"" + HEad_Str + ".xls\"");
//	            
//	            JRXlsExporter exporterXLS = new JRXlsExporter();
//	            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//	            
//	            ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//	            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
//	            
//	            // Adjust these parameters for better formatting
//	            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE); // or FALSE based on your requirement
//	            exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.FALSE);
//	            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//	            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//	            
//	            exporterXLS.exportReport();
//	            
//	            byte[] bytes = xlsReport.toByteArray();
//	            ServletOutputStream outputStream = response.getOutputStream();
//	            outputStream.write(bytes, 0, bytes.length);
//	            outputStream.flush();
//	            outputStream.close();
//	        }

	    } 
	    catch (Exception ex) 
	    {
	        String connectMsg = 
	        "Could not create the report " + ex.getMessage() + " " + 
	        ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	    }
	    System.out.println("Report File--->"+reportFile);
	}
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	try
    {
		System.out.println("this is starting::::::::::post ");
        HttpSession session=request.getSession(false);
        if(session==null)
        {
            System.out.println(request.getContextPath()+"/index.jsp");
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        
        }
        System.out.println(session);
            
    }catch(Exception e)
    {
    	System.out.println("Redirect Error :"+e);
    }
	
      //  Connection con=
    String opt="",orgUnitName="",hcode="",heading="",heading_one="";
    response.setContentType(CONTENT_TYPE);
    try 
    {
    	ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
    } catch (Exception ex) {
        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
        ex.getLocalizedMessage();
        System.out.println(connectMsg);
    }
    JasperDesign jasperDesign = null;
    File reportFile=null;
	int cmbOffice_code=0,cashYear=0,cashMonth=0,off_id=0;
	String type_prf=null,txtoption=null,officeCode="";
    try 
    {
        System.out.println("inside servlet>>>>>superrrrrrrrrrrrr>>>>>..");
        
        String displayOrder=request.getParameter("displayingOrder");

        
        if(request.getParameter("month_year").equalsIgnoreCase("particular_cb"))
        {
        	
        	
        	 try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
            
             cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));
             type_prf=request.getParameter("proformatype");
             txtoption=request.getParameter("txtoption");//PDF OR EXCEL
             
                
        String monthInWords="";
        if(cashMonth==1)
            monthInWords="January";
        else if(cashMonth==2)
            monthInWords="February";
        else if(cashMonth==3)
            monthInWords="March";
        else if(cashMonth==4)
            monthInWords="April";
        else if(cashMonth==5)
            monthInWords="May";
        else if(cashMonth==6)
            monthInWords="June";
        else if(cashMonth==7)
            monthInWords="July";
        else if(cashMonth==8)
            monthInWords="August";
        else if(cashMonth==9)
            monthInWords="September";
        else if(cashMonth==10)
            monthInWords="October";
        else if(cashMonth==11)
            monthInWords="November";
        else if(cashMonth==12)
            monthInWords="December";
            
        if(displayOrder.equalsIgnoreCase("RW") ||displayOrder.equalsIgnoreCase("OW"))
        {
        	
        	if(displayOrder.equalsIgnoreCase("RW"))
            {
            	cmbOffice_code=Integer.parseInt(request.getParameter("txtRegionId"));
            	
            
        	
        	 try {
        	        PreparedStatement ps=null;
        	        ResultSet rs=null;
        	       
        	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
        	        ps.setInt(1,cmbOffice_code);
        	        rs=ps.executeQuery();
        	        if(rs.next()) {
        	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
        	        }
        	        }
        	        catch (SQLException e) {
        	            System.out.println("SQL Exception -->"+e);
        	        }
        	
        	if(type_prf.equals("TDA"))
	        {
	        
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/TDA_List_All_rw.jasper"));
	        }
	        else
	        {
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/TCA_List_All_rw.jasper"));
	        }
       
       
         System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
       System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
       System.out.println("displayOrder:for :::"+displayOrder);
        System.out.println("off_id:::"+off_id);
        		map.put("cashyear",cashYear);     
	        	 map.put("cashmonth",cashMonth);
	               
	             map.put("monthinwords",monthInWords);
	             map.put("officeCode",cmbOffice_code);
        	
        	
        
       
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
        
        System.out.println("jasperPrint:"+jasperPrint);
        
            if (txtoption.equalsIgnoreCase("PDF"))   
            {
            		System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        	}
	        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
	        {

                	response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
                 
                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
                    exporterXLS.exportReport(); 
                    byte []bytes;
                    bytes = xlsReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

	        }
        
          }
        	else if(displayOrder.equalsIgnoreCase("OW"))
            {
            	cmbOffice_code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            	try {
        	        PreparedStatement ps=null;
        	        ResultSet rs=null;
        	       
        	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
        	        ps.setInt(1,cmbOffice_code);
        	        rs=ps.executeQuery();
        	        if(rs.next()) {
        	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
        	        }
        	        }
        	        catch (SQLException e) {
        	            System.out.println("SQL Exception -->"+e);
        	        }
        	
        	if(type_prf.equals("TDA"))
	        {
	        
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/TDA_List_All_rw_ow.jasper"));
	        }
	        else
	        {
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/TCA_List_All_rw_ow.jasper"));
	        }
       
       
         System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
       System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
       System.out.println("displayOrder:for :::"+displayOrder);
        System.out.println("off_id:::"+off_id);
        		map.put("cashyear",cashYear);     
	        	 map.put("cashmonth",cashMonth);
	               
	             map.put("monthinwords",monthInWords);
	             map.put("officeCode",off_id);
        	
        	
        
       
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
        
        System.out.println("jasperPrint:"+jasperPrint);
        
            if (txtoption.equalsIgnoreCase("PDF"))   
            {
            		System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        	}
	        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
	        {

                	response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
                 
                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
                    exporterXLS.exportReport(); 
                    byte []bytes;
                    bytes = xlsReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

	        }
            }
        
        }
        else{
        	PreparedStatement ps2 = null;
        	Connection con = null;
        	System.out.println("all starts:");
		        if(type_prf.equals("TDA"))
		        {
		        	System.out.println("TDA33");
		        //	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/TDA_List_All_1.jasper"));
		        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/TDA_List_All.jasper"));
//		        	   try
//                       {
//		        		   if (txtoption.equalsIgnoreCase("EXCEL")) {
//                    	   String sql_HOA_NCR= "SELECT A.*,A.VOUCHER_NO as \"VOUCHER_NO\",B.ACCEPTING_JVR_NO as \"ACCEPTING_JVR_NO\",B.ACCEPTING_JVR_DATE as \"ACCEPTING_JVR_DATE\" FROM\r\n"
//                    	   		+ "\r\n"
//                    	   		+ "(SELECT mst.VOUCHER_DATE as \"VOUCHER_DATE\",\r\n"
//                    	   		+ "    mst.VOUCHER_NO ,\r\n"
//                    	   		+ "    mst.TDA_OR_TCA as \"TDA_OR_TCA\",\r\n"
//                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
//                    	   		+ "   (SELECT ACCOUNTING_UNIT_NAME\r\n"
//                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
//                    	   		+ "    Where Unit.Accounting_Unit_Id=Mst.ACCOUNTING_UNIT_ID\r\n"
//                    	   		+ "    ) As ORGNAME,\r\n"
//                    	   		+ "      Mst.Trf_Accounting_Unit_Id,\r\n"
//                    	   		+ "      (SELECT ACCOUNTING_UNIT_NAME\r\n"
//                    	   		+ "    FROM FAS_MST_ACCT_UNITS unit\r\n"
//                    	   		+ "    WHERE unit.ACCOUNTING_UNIT_ID=mst.Trf_Accounting_Unit_Id\r\n"
//                    	   		+ "    )AS \"ACCEPTEDNAME\",\r\n"
//                    	   		+ "    mst.TOTAL_AMOUNT as \"TOTAL_AMOUNT\",\r\n"
//                    	   		+ "    mst.ORGINATING_JVR_NO as \"ORGINATING_JVR_NO\",\r\n"
//                    	   		+ "    mst.ORGINATING_JVR_DATE as \"ORGINATING_JVR_DATE\",\r\n"
//                    	   		+ "    mst.RESPONDING_JVR_NO As \"RESPONDING_JVR_NO\",\r\n"
//                    	   		+ "    mst.RESPONDING_JVR_DATE as \"RESPONDING_JVR_DATE\",\r\n"
//                    	   		+ "    Mst.Status,\r\n"
//                    	   		+ "    mst.PARTICULARS as \"PARTICULARS\",MST.ACCEPTING_SLNO,mst.acceptance_status as \"ACCEPTANCE_STATUS\",\r\n"
//                    	   		+ "    MST.SUPPLEMENT_NO\r\n"
//                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
//                    	   		+ "  WHERE mst.ORGINATING_JVR_NO                      IS NOT NULL\r\n"
//                    	   		+ "  AND mst.orginating_jvr_date                    IS NOT NULL\r\n"
//                    	   		+ "  And Mst.Status                                  ='L'\r\n"
//                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAO','TDACB')\r\n"
//                    	   		+ " And Mst.Cashbook_Year=$P{cashyear}::int\r\n"
//                    	   		+ "  And Mst.Cashbook_Month=$P{cashmonth}::int)A\r\n"
//                    	   		+ "  \r\n"
//                    	   		+ "  Left Outer JOIN\r\n"
//                    	   		+ "    \r\n"
//                    	   		+ "  (SELECT mst.VOUCHER_DATE,\r\n"
//                    	   		+ "    mst.VOUCHER_NO ,\r\n"
//                    	   		+ "    mst.TDA_OR_TCA,\r\n"
//                    	   		+ "    mst.ACCOUNTING_UNIT_ID,\r\n"
//                    	   		+ "    mst.TOTAL_AMOUNT,\r\n"
//                    	   		+ "    mst.ACCEPTING_JVR_NO,\r\n"
//                    	   		+ "    mst.ACCEPTING_JVR_DATE,\r\n"
//                    	   		+ "    mst.STATUS,\r\n"
//                    	   		+ "    MST.SUPPLEMENT_NO\r\n"
//                    	   		+ "  From Fas_Tda_Tca_Raised_Mst Mst\r\n"
//                    	   		+ "  WHERE mst.ACCEPTING_JVR_NO                      IS NOT NULL\r\n"
//                    	   		+ "  AND mst.ACCEPTING_JVR_DATE                    IS NOT NULL\r\n"
//                    	   		+ "  And Mst.Status                                  ='L'\r\n"
//                    	   		+ "  AND mst.TDA_OR_TCA             in ('TDAA')\r\n"
//                    	   		+ ")B\r\n"
//                    	   		+ "  On A.TRF_ACCOUNTING_UNIT_ID=B.Accounting_Unit_Id\r\n"
//                    	   		+ "  AND A.ACCEPTING_SLNO=B.VOUCHER_NO\r\n"
//                    	   		+ "  order by a.Orgname,a.VOUCHER_NO";
//                       	   ps2=con.prepareStatement(sql_HOA_NCR);
//                       
//                           ps2.setInt(2,cashYear);
//                           ps2.setInt(3,cashMonth);
//                           //System.out.println("before execution HOA Balance DR type check");
//                           ResultSet rs1=ps2.executeQuery();
//                           generateExcelFile(rs1,response,"TDA_LIST_ALL");
//                       }
//                       }
//                       catch(Exception e)
//                       {
//                    	   System.out.println("Error in HOA balance type and amount**"+e);
//                       }
				        
		        }
		        else
		        {
		        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/TCA_List_All.jasper"));
		        }
		        System.out.println("reportFile for office"+reportFile);   
		        if (!reportFile.exists())
		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		        
		        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		      
		        System.out.println("cashMonth::"+cashMonth);
		        System.out.println("cashYear::"+cashYear);
		        
		        Map map=new HashMap();
		       
		        map.put("cashyear",cashYear);     
	        	 map.put("cashmonth",cashMonth);
	               
	             map.put("monthinwords",monthInWords);
	       System.out.println("map::::"+map);
	       
	        
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
	            if (txtoption.equalsIgnoreCase("PDF"))   
	            {
	            		System.out.println("PDF:::::::::::");
	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL1.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        	}
//		        else if (txtoption.equalsIgnoreCase("EXCEL"))   
//		        {
//
//	                	response.setContentType("application/vnd.ms-excel");
//	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL.xls\"");
//	                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
//	                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
//	                 
//	                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//	                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
//	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
//	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
//	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
//	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
//	                    exporterXLS.exportReport(); 
//	                    byte []bytes;
//	                    bytes = xlsReport.toByteArray();
//	                    ServletOutputStream ouputStream = response.getOutputStream();
//	                    ouputStream.write(bytes, 0, bytes.length);
//	                    ouputStream.flush();
//	                    ouputStream.close();
//
//		        }
        }
        
    }
        else  if(request.getParameter("month_year").equalsIgnoreCase("more_cb"))
        {
        	int txtCB_Month_to=0,txtCB_Year_from=0,txtCB_Year_to=0,txtCB_Month_from=0;
        	
       	 try{txtCB_Year_from=Integer.parseInt(request.getParameter("txtCB_Year_from"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            try{txtCB_Year_to=Integer.parseInt(request.getParameter("txtCB_Year_to"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
           
            txtCB_Month_from=Integer.parseInt(request.getParameter("txtCB_Month_from"));
            txtCB_Month_to=Integer.parseInt(request.getParameter("txtCB_Month_to"));
            
            type_prf=request.getParameter("proformatype");
            txtoption=request.getParameter("txtoption");//PDF OR EXCEL
            
               
       String monthInWords="";
       if(txtCB_Month_from==1)
           monthInWords="January";
       else if(txtCB_Month_from==2)
           monthInWords="February";
       else if(txtCB_Month_from==3)
           monthInWords="March";
       else if(txtCB_Month_from==4)
           monthInWords="April";
       else if(txtCB_Month_from==5)
           monthInWords="May";
       else if(txtCB_Month_from==6)
           monthInWords="June";
       else if(txtCB_Month_from==7)
           monthInWords="July";
       else if(txtCB_Month_from==8)
           monthInWords="August";
       else if(txtCB_Month_from==9)
           monthInWords="September";
       else if(txtCB_Month_from==10)
           monthInWords="October";
       else if(txtCB_Month_from==11)
           monthInWords="November";
       else if(txtCB_Month_from==12)
           monthInWords="December";
       
       String monthInWords_to="";
       if(txtCB_Month_to==1)
    	   monthInWords_to="January";
       else if(txtCB_Month_to==2)
    	   monthInWords_to="February";
       else if(txtCB_Month_to==3)
    	   monthInWords_to="March";
       else if(txtCB_Month_to==4)
    	   monthInWords_to="April";
       else if(txtCB_Month_to==5)
    	   monthInWords_to="May";
       else if(txtCB_Month_to==6)
    	   monthInWords_to="June";
       else if(txtCB_Month_to==7)
    	   monthInWords_to="July";
       else if(txtCB_Month_to==8)
    	   monthInWords_to="August";
       else if(txtCB_Month_to==9)
    	   monthInWords_to="September";
       else if(txtCB_Month_to==10)
    	   monthInWords_to="October";
       else if(txtCB_Month_to==11)
    	   monthInWords_to="November";
       else if(txtCB_Month_to==12)
    	   monthInWords_to="December";
       
       String from_to="From "+monthInWords+" "+txtCB_Year_from+" to "+monthInWords_to+" "+txtCB_Year_to;
           System.out.println("from_to:::"+from_to);
       if(displayOrder.equalsIgnoreCase("RW") ||displayOrder.equalsIgnoreCase("OW"))
       {
       	
       	if(displayOrder.equalsIgnoreCase("RW"))
           {
           	cmbOffice_code=Integer.parseInt(request.getParameter("txtRegionId"));
           	
          
       	
       	 try {
       	        PreparedStatement ps=null;
       	        ResultSet rs=null;
       	       
       	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
       	        ps.setInt(1,cmbOffice_code);
       	        rs=ps.executeQuery();
       	        if(rs.next()) {
       	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
       	        }
       	        }
       	        catch (SQLException e) {
       	            System.out.println("SQL Exception -->"+e);
       	        }
       	 
       	 
       	
       	if(type_prf.equals("TDA"))
	        {
	        
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/date_wise/TDA_List_All_rw_date.jasper"));
	        }
	        else
	        {
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/date_wise/TCA_List_All_rw_date.jasper"));
	        }
      
      
        System.out.println("reportFile"+reportFile);   
       if (!reportFile.exists())
       throw new JRRuntimeException("File J not found. The report design must be compiled first.");
       
       JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
      System.out.println("jasperReport"+jasperReport);
       Map map=new HashMap();
      System.out.println("displayOrder:for :::"+displayOrder);
       System.out.println("off_id:::"+off_id);
       System.out.println("cmbOffice_code for region:::"+cmbOffice_code);
       			 map.put("txtCB_Year_from",txtCB_Year_from);     
	        	 map.put("txtCB_Year_to",txtCB_Year_to);
	        	 
	        	 map.put("txtCB_Month_from",txtCB_Month_from);     
	        	 map.put("txtCB_Month_to",txtCB_Month_to);
	               
	             map.put("from_to",from_to);
	             map.put("officeCode",cmbOffice_code);
	             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
	             
	             System.out.println("jasperPrint:"+jasperPrint);
	             
	                 if (txtoption.equalsIgnoreCase("PDF"))   
	                 {
	                 		System.out.println("PDF:::::::::::");
	                         byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                         response.setContentType("application/pdf");
	                         response.setContentLength(buf.length);
	                         response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL_date.pdf\"");
	                         OutputStream out = response.getOutputStream();
	                         out.write(buf, 0, buf.length);
	                         out.close();
	             	}
	      	        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
	      	        {

	                     	response.setContentType("application/vnd.ms-excel");
	                         response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL_date.xls\"");
	                         JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                      
	                         ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                         exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                         exporterXLS.exportReport(); 
	                         byte []bytes;
	                         bytes = xlsReport.toByteArray();
	                         ServletOutputStream ouputStream = response.getOutputStream();
	                         ouputStream.write(bytes, 0, bytes.length);
	                         ouputStream.flush();
	                         ouputStream.close();

	      	        }
       	
       }
        else if(displayOrder.equalsIgnoreCase("OW"))
        {
        	cmbOffice_code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	try {
       	        PreparedStatement ps=null;
       	        ResultSet rs=null;
       	       
       	        ps=connection.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
       	        ps.setInt(1,cmbOffice_code);
       	        rs=ps.executeQuery();
       	        if(rs.next()) {
       	            off_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
       	        }
       	        }
       	        catch (SQLException e) {
       	            System.out.println("SQL Exception -->"+e);
       	        }
       	
       	if(type_prf.equals("TDA"))
	        {
	        
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/date_wise/TDA_List_All_rw_ow_date.jasper"));
	        }
	        else
	        {
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/date_wise/TCA_List_All_rw_ow_date.jasper"));
	        }
      
      
        System.out.println("reportFile"+reportFile);   
       if (!reportFile.exists())
       throw new JRRuntimeException("File J not found. The report design must be compiled first.");
       
       JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
      System.out.println("jasperReport"+jasperReport);
       Map map=new HashMap();
      System.out.println("displayOrder:for :::"+displayOrder);
       System.out.println("off_id:::"+off_id);
      
       			 map.put("txtCB_Year_from",txtCB_Year_from);     
	        	 map.put("txtCB_Year_to",txtCB_Year_to);
	        	 
	        	 map.put("txtCB_Month_from",txtCB_Month_from);     
	        	 map.put("txtCB_Month_to",txtCB_Month_to);
	               
	             map.put("from_to",from_to);
	             map.put("officeCode",off_id);
	             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
	             
	             System.out.println("jasperPrint:"+jasperPrint);
	             
	                 if (txtoption.equalsIgnoreCase("PDF"))   
	                 {
	                 		System.out.println("PDF:::::::::::");
	                         byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                         response.setContentType("application/pdf");
	                         response.setContentLength(buf.length);
	                         response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL_date.pdf\"");
	                         OutputStream out = response.getOutputStream();
	                         out.write(buf, 0, buf.length);
	                         out.close();
	             	}
	      	        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
	      	        {

	                     	response.setContentType("application/vnd.ms-excel");
	                         response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL_date.xls\"");
	                         JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                      
	                         ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                         exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                         exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                         exporterXLS.exportReport(); 
	                         byte []bytes;
	                         bytes = xlsReport.toByteArray();
	                         ServletOutputStream ouputStream = response.getOutputStream();
	                         ouputStream.write(bytes, 0, bytes.length);
	                         ouputStream.flush();
	                         ouputStream.close();

	      	        }
        }
      
       
       }
       else{
       	System.out.println("all starts from datewise:");
		        if(type_prf.equals("TDA"))
		        {
		        
		        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/date_wise/TDA_List_All_date.jasper"));
		        }
		        else
		        {
		        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/only_super_user/date_wise/TCA_List_All_date.jasper"));
		        }
		        System.out.println("reportFile for office"+reportFile);   
		        if (!reportFile.exists())
		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		        
		        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		      
		        System.out.println("cashMonth::"+cashMonth);
		        System.out.println("cashYear::"+cashYear);
		        
		        Map map=new HashMap();
		       
		        map.put("txtCB_Year_from",txtCB_Year_from);     
	        	 map.put("txtCB_Year_to",txtCB_Year_to);
	        	 
	        	 map.put("txtCB_Month_from",txtCB_Month_from);     
	        	 map.put("txtCB_Month_to",txtCB_Month_to);
	             map.put("from_to",from_to);
	       System.out.println("map::::"+map);
	       
	        
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
	            if (txtoption.equalsIgnoreCase("PDF"))   
	            {
	            		System.out.println("PDF:::::::::::");
	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL_date.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        	}
		        else      if (txtoption.equalsIgnoreCase("EXCEL"))   
		        {

	                	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA_ALL_date.xls\"");
	                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                 
	                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                    exporterXLS.exportReport(); 
	                    byte []bytes;
	                    bytes = xlsReport.toByteArray();
	                    ServletOutputStream ouputStream = response.getOutputStream();
	                    ouputStream.write(bytes, 0, bytes.length);
	                    ouputStream.flush();
	                    ouputStream.close();

		        }
       }
        }
 
    } 
    catch (Exception ex) 
    {
        String connectMsg = 
        "Could not create the report " + ex.getMessage() + " " + 
        ex.getLocalizedMessage();
        System.out.println(connectMsg);
    }
	
	
	
	}

public static void generateExcelFile(ResultSet resultSet, HttpServletResponse response, String filename) {
	try {

		// Create a new workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sheet 1");
		
		// Create header row
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		HSSFRow headerRow = sheet.createRow(0);
		for (int i = 1; i <= columnCount; i++) {
			HSSFCell cell = headerRow.createCell((short) (i - 1));
			cell.setCellValue(metaData.getColumnName(i).toUpperCase());
		}

		// Create data rows
		int rowNum = 1;
		while (resultSet.next()) {
			HSSFRow row = sheet.createRow(rowNum++);
			for (int i = 1; i <= columnCount; i++) {
				HSSFCell cell = row.createCell((short) (i - 1));

				int columnType = metaData.getColumnType(i);

				// Handle different data types
				switch (columnType) {
				case 2:
					cell.setCellValue(resultSet.getLong(i));
					break;
				case java.sql.Types.DOUBLE:
					cell.setCellValue(resultSet.getDouble(i));
					break;
				default:
					// Default to treating other types as strings
					cell.setCellValue(resultSet.getString(i));
				}
			}

		}

		// Auto-size columns for better readability
		for (int i = 0; i < columnCount; i++) {
			sheet.autoSizeColumn((short) i);
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls");
		
		workbook.write(response.getOutputStream());

		System.out.println("Excel file generated successfully");
		
				
	} catch (Exception e) {
		e.printStackTrace();
	}
}



}
