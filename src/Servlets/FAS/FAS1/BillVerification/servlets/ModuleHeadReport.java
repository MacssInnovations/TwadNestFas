package Servlets.FAS.FAS1.BillVerification.servlets;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


public class ModuleHeadReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static  String CONTENT_TYPE = "text/html; charset=windows-1252";
	
    public ModuleHeadReport() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("Servlet Call ModuleHeadReport.java PDF");
		String Command=request.getParameter("Command");
		System.out.println("Command"+Command);
		CallableStatement stmt=null; 
    	Connection connection=null;
    	PreparedStatement ps=null;
    	ResultSet rset=null;
    	//PrintWriter out=response.getWriter();
        try
        {
                
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
      
        response.setContentType("text/html; charset=windows-1252");
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
        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection        Class.forName(strDriver.trim());
        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception ex) {
        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
        ex.getLocalizedMessage();
        //System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        String qry="",head="",hid="";
        String cmbOprMode="",cmbAccNo="";
        int cmbAcc_UnitCode=0,cmbOffice_code=0;
        try{
        	hid=request.getParameter("hid");
        	
        }catch (Exception e) {
        	e.printStackTrace();
        }
        if(hid.equalsIgnoreCase("module")){
        	
        	cmbOprMode=request.getParameter("cmbOprMode");
        	//cmbAccNo=request.getParameter("cmbAccNo");
        	
        	
        	
        	
        	qry="SELECT Accounting_Unit_Id, " +
        	"  (SELECT U.Accounting_Unit_Name " +
        	"  FROM Fas_Mst_Acct_Units U " +
        	"  WHERE U.Accounting_Unit_Id = Curr.Accounting_Unit_Id " +
        	"  ) unit_name, " +
        	"  Bank_Id, " +
        	"  (SELECT Bb.Bank_Name FROM fas_mst_banks bb WHERE curr.bank_id=bb.bank_id " +
        	"  ) bank_name, " +
        	"  Branch_Id, " +
        	"  (SELECT BR.BRANCH_NAME " +
        	"  FROM Fas_Mst_Bank_Branches br " +
        	"  WHERE curr.bank_id=br.bank_id " +
        	"  AND curr.branch_id=br.branch_id " +
        	"  ) branch_name, " +
        	"  Bank_Ac_No, " +
        	"  Bank_Ac_Type_Id, " +
        	"  (SELECT at.ACCOUNT_TYPE " +
        	"  FROM FAS_MST_BANK_AC_TYPES at " +
        	"  WHERE curr.Bank_Ac_Type_Id=at.ACCOUNT_TYPE_ID " +
        	"  )acc_type, " +
        	"  Ac_Operational_Mode_Id, " +
        	"  (SELECT om.AC_OPERATIONAL_MODE " +
        	"  FROM FAS_MST_AC_OPER_MODES om " +
        	"  WHERE curr.AC_OPERATIONAL_MODE_ID =om.AC_OPERATIONAL_MODE_ID " +
        	"  )mode_desc, " +
        	"  Ac_Head_Code, " +
        	"  REMARKS, " +
        	"  Sl_No, " +
        	"  Module_Id, " +
        	"  (SELECT mt.MODULE_DESC " +
        	"  FROM Fas_Module_Heads_Template Mt " +
        	" where  trim(mt.Bank_Ac_Type_Id) = trim(curr.Bank_Ac_Type_Id) "+
        	"  AND trim(mt.Module_Id)         = trim(curr.Module_Id)"+
        	"  AND trim(mt.CR_DR_TYPE)        = trim(curr.CR_DR_TYPE) "+
            "  and trim(mt.AC_OPERATIONAL_MODE_ID) = trim(curr.AC_OPERATIONAL_MODE_ID) " +
        	"  )module_desc, " +
        	"  Cr_Dr_Type, " +
        	"  Status " +
        	"FROM Fas_Office_Bank_Ac_Current Curr " +
        	"WHERE Curr.Module_Id='"+cmbOprMode+"' " +
        //	"AND curr.bank_ac_no ='"+cmbAccNo+"' " +
        	"ORDER BY curr.AC_OPERATIONAL_MODE_ID,Curr.Accounting_Unit_Id, " +
        	"  Curr.Bank_Ac_No";

        }else  if(hid.equalsIgnoreCase("office")){
        	cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
        	
        	qry="SELECT Accounting_Unit_Id, " +
        	"  (SELECT U.Accounting_Unit_Name " +
        	"  FROM Fas_Mst_Acct_Units U " +
        	"  WHERE U.Accounting_Unit_Id = Curr.Accounting_Unit_Id " +
        	"  ) unit_name, " +
        	"  Bank_Id, " +
        	"  (SELECT Bb.Bank_Name FROM fas_mst_banks bb WHERE curr.bank_id=bb.bank_id " +
        	"  ) bank_name, " +
        	"  Branch_Id, " +
        	"  (SELECT BR.BRANCH_NAME " +
        	"  FROM Fas_Mst_Bank_Branches br " +
        	"  WHERE curr.bank_id=br.bank_id " +
        	"  AND curr.branch_id=br.branch_id " +
        	"  ) branch_name, " +
        	"  Bank_Ac_No, " +
        	"  Bank_Ac_Type_Id, " +
        	"  (SELECT at.ACCOUNT_TYPE " +
        	"  FROM FAS_MST_BANK_AC_TYPES at " +
        	"  WHERE curr.Bank_Ac_Type_Id=at.ACCOUNT_TYPE_ID " +
        	"  )acc_type, " +
        	"  Ac_Operational_Mode_Id, " +
        	"  (SELECT om.AC_OPERATIONAL_MODE " +
        	"  FROM FAS_MST_AC_OPER_MODES om " +
        	"  WHERE curr.AC_OPERATIONAL_MODE_ID =om.AC_OPERATIONAL_MODE_ID " +
        	"  )mode_desc, " +
        	"  Ac_Head_Code, " +
        	"  REMARKS, " +
        	"  Sl_No, " +
        	"  Module_Id, " +
        	"  (SELECT mt.MODULE_DESC " +
        	"  FROM Fas_Module_Heads_Template Mt " +
        	" where  mt.Bank_Ac_Type_Id = curr.Bank_Ac_Type_Id "+
        	"  AND mt.Module_Id         =curr.Module_Id"+
        	"  AND mt.CR_DR_TYPE        = curr.CR_DR_TYPE "+
            "  and mt.AC_OPERATIONAL_MODE_ID = curr.AC_OPERATIONAL_MODE_ID " +
        	"  )module_desc, " +
        	"  Cr_Dr_Type, " +
        	"  Status " +
        	" FROM Fas_Office_Bank_Ac_Current Curr " +
        	" WHERE Curr.Accounting_Unit_Id= " +cmbAcc_UnitCode+
        	" ORDER BY Curr.Accounting_Unit_Id, " +
        	"  curr.bank_ac_no"
;
        }
        System.out.println(qry);
		try{
    		
    	    Map map = null;
    	    map = new HashMap();
    	    if(hid.equalsIgnoreCase("module")){
    	    	head="Modulewise Account Head Details";
    	    	reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Fas_Module_Modulewise.jasper"));
    	    }
    	    else  if(hid.equalsIgnoreCase("office")){
    	    	head="Officewise Account Head Details";
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Fas_Module_Office.jasper"));	    			
    	    }
    		  
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		  map.put("qry", qry);
	    		    map.put("heading",head);
	    		    JasperPrint jasperPrint =
		                JasperFillManager.fillReport(jasperReport, map, connection);
		            	String rtype="PDF";
		            	//System.out.println("map"+map );
			
		      
		            	System.out.println("the option chosen is :::::"+rtype);
		                byte buf[] =
		                    JasperExportManager.exportReportToPdf(jasperPrint);
		                response.setContentType("application/pdf");
		                response.setContentLength(buf.length);
		                response.setHeader("Content-Disposition",
		                                   "attachment;filename=\""+head+".pdf\"");
		                OutputStream out = response.getOutputStream();
		                out.write(buf, 0, buf.length);
		                System.out.println("testing***"+jasperPrint);
		                out.close();
		            
				    

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet Call ModuleHeadReport.java ");
		PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
		
  HttpSession session=request.getSession(true);

   String billno="";
   Connection con=null;
  
 
  
  
   String xml="";
   try {
		ResourceBundle rsb = ResourceBundle
				.getBundle("Servlets.Security.servlets.Config");
		String ConnectionString = "";

		String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
		String strdsn = rsb.getString("Config.DSN");
		String strhostname = rsb.getString("Config.HOST_NAME");
		String strportno = rsb.getString("Config.PORT_NUMBER");
		String strsid = rsb.getString("Config.SID");
		String strdbusername = rsb.getString("Config.USER_NAME");
		String strdbpassword = rsb.getString("Config.PASSWORD");

		ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

		Class.forName(strDriver.trim());
		con = DriverManager.getConnection(ConnectionString,
				strdbusername.trim(), strdbpassword.trim());
		
	} catch (Exception e) {
		System.out.println("Exception in openeing connection:" + e);
	}
	int c=0; String qry="";
	   String cmd=request.getParameter("command");
	   String rep_type=request.getParameter("rep_type");
	   int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	   System.out.println("Command >>> "+cmd);
	   if(cmd.equalsIgnoreCase("getAccNo")){
		   xml+="<response><command>getAccNo</command>";
		   if(rep_type.equalsIgnoreCase("module")){
			   String OprMode=request.getParameter("OprMode") ;
		    qry="select distinct to_number(bank_ac_no)  as bank_ac_no from fas_office_bank_ac_current c where c.module_id='"+OprMode+"' order by bank_ac_no";
		
		   }
		   else{
			   qry="select distinct to_number(bank_ac_no)  as bank_ac_no from fas_office_bank_ac_current c where  c.accounting_unit_id="+cmbAcc_UnitCode+" order by bank_ac_no";
			   
		   }
		   
		   try{
			   PreparedStatement ps=con.prepareStatement(qry);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next()){
				   xml+="<bank_ac_no>"+rs.getString("bank_ac_no")+"</bank_ac_no>";
				 
				   c++;
			   }
			   if(c!=0){
				   xml+="<flag>success</flag>";
			   }else{
				   xml+="<flag>failure</flag>";
			   }
		   }catch (Exception e) {
			   xml+="<flag>failure</flag>";
			 e.printStackTrace();  
		}
		   xml+="</response>";
		   System.out.println("xml >>> "+xml);
		   out.println(xml);
		   out.close();
	   }
	   
	   
	 
	}

}
