package Servlets.FAS.FAS1.MIS.servlets;

import java.io.ByteArrayOutputStream;
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
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowagie.text.RomanList;

import jxl.biff.Type;
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
 * Servlet implementation class Annual_acc_Report
 */
public class Fas_ConsolidateTBAcc_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static  String CONTENT_TYPE = 
            "text/xml; charset=windows-1252";
        Connection connection = null;
    public Fas_ConsolidateTBAcc_Details() {
        super();
        // TODO Auto-generated constructor stub
    }

    
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	//System.out.println("Servlet Page ... ");
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
        
        String opt="",Report_For="";
        int errcode=0;
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
        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection        Class.forName(strDriver.trim());
        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception ex) {
        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
        ex.getLocalizedMessage();
        //System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        int from_year=0;
        String head="";
        int To_year=0;
        int from_Month=0;
        int To_Month=0,sup_no=0,sup_no1=0,unit_id=0;
        String  command="",fin_year="",sup_qry="",sup_query="",f_year="",Str_qry="",xml="";
        String fin_FY="",fin_TY="",f_mnth="",t_mnth="",qry="",unit_qry="";
       int chk_cons=0;
        String year[]=null,a[]=null,b[]=null; 
         command=request.getParameter("cmd");
       
        String Re_Type="";
	     try{   

 if(command.equalsIgnoreCase("live_Report"))
{
	Report_For=request.getParameter("Report_For");
	Re_Type=request.getParameter("rad_R");
	fin_FY=request.getParameter("liveFY");  
    f_mnth=request.getParameter("fromMonth");
    t_mnth=request.getParameter("toMonth");
    

	if (Report_For.equalsIgnoreCase("Con_TB"))
	{
	    if(!f_mnth.equalsIgnoreCase(t_mnth))
        {
 	   
 	   if(Integer.parseInt(f_mnth.split("-")[2])!=Integer.parseInt(t_mnth.split("-")[2]))
 	   {
 		   sup_qry=" AND cashbook_year BETWEEN "+Integer.parseInt(f_mnth.split("-")[2])+"  AND  "+Integer.parseInt(t_mnth.split("-")[2])+"  AND ";
 	   }else if(Integer.parseInt(f_mnth.split("-")[2])==Integer.parseInt(t_mnth.split("-")[2]))
 	   {
 		   sup_qry=" AND cashbook_year= "+Integer.parseInt(f_mnth.split("-")[2])+" AND ";
 		   
 	   }
 	   if(f_mnth.split("-")[1].equalsIgnoreCase("APR"))
        {
 		   head= "Consolidated Trial Balance Details for the Month Upto  "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
        }
 	   head= "Consolidated Trial Balance Details  for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
 	   sup_query=" AND CMONTH BETWEEN '" +f_mnth+"'  AND '" +t_mnth+"' " ;
 	   
        }else if(f_mnth.equalsIgnoreCase(t_mnth))
        {
     	  sup_query= " AND  CMONTH = '" +f_mnth+"' " ;
     	  head= "Consolidated Trial Balance Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
        }
		  unit_id=0;
		  qry ="SELECT 1 as data_tb, v.account_head_code, " +
   	   "  c.account_head_desc, " +
   	   "  hh.annualgroupingid, " +
   	   "  aa.annualgroupingname, " +
   	   "  aa.categoryid, " +
   	   "  ccc.categoryname, " +
   	   "  hh.minorgroupingid, " +
   	   "  mm.minorgroupingname, " +
   	   "  SUM(dr_amt)DR_AMOUNT, " +
   	   "  SUM(CR_AMT)CR_AMOUNT, " +
   	   "  SUM(dr_amt)-SUM(cr_amt) AS net_amt , " +
   	   "  fin_year " +
   	   " FROM VIEW_FAS_TRIAL_CP v " +
   	   " INNER JOIN com_mst_account_heads c " +
   	   " ON v.account_head_code=c.account_head_code " +
   	   " INNER JOIN FAS_HEADOFACCOUNTS hh " +
   	   " ON v.account_head_code=hh.accountcode " +
   	   " INNER JOIN annualgrouping aa " +
   	   " ON hh.annualgroupingid=aa.annualgroupingid " +
   	   " INNER JOIN minorgrouping mm " +
   	   " ON hh.minorgroupingid=mm.minorgroupingid " +
   	   " INNER JOIN category ccc " +
   	   " ON aa.categoryid=ccc.categoryid " +
   	  sup_query+
   	
   	   " and (dr_amt>0 or CR_AMT>0) "+
   	   " GROUP BY v.account_head_code, " +
   	   "  c.account_head_desc, " +
   	   "  hh.annualgroupingid, " +
   	   "  aa.annualgroupingname, " +
   	   "  aa.categoryid, " +
   	   "  ccc.categoryname, " +
   	   "  hh.minorgroupingid, " +
   	   "  mm.minorgroupingname, " +
   	   "  fin_year " +
   	  // "ORDER BY aa.categoryid, " +
   	 "ORDER BY  " +
   	   "  hh.annualgroupingid, " +
   	   "  hh.minorgroupingid" ;
		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/fas_trialDetail_report_final.jasper"));
		  
	}else if(Report_For.equalsIgnoreCase("Con_Ac")){
		unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		 if (unit_id!=0)unit_qry="  AND  ACCOUNTING_UNIT_ID="+unit_id ;
	    if(!f_mnth.equalsIgnoreCase(t_mnth))
        {
 	   
 	   if(Integer.parseInt(f_mnth.split("-")[2])!=Integer.parseInt(t_mnth.split("-")[2]))
 	   {
 		   sup_qry=" AND cashbook_year BETWEEN "+Integer.parseInt(f_mnth.split("-")[2])+"  AND  "+Integer.parseInt(t_mnth.split("-")[2])+"  AND ";
 	   }else if(Integer.parseInt(f_mnth.split("-")[2])==Integer.parseInt(t_mnth.split("-")[2]))
 	   {
 		   sup_qry=" AND cashbook_year= "+Integer.parseInt(f_mnth.split("-")[2])+" AND ";
 		   
 	   }
 	  if(f_mnth.split("-")[1].equalsIgnoreCase("APR"))
        {
 		   head= "Consolidated Accounts Details for the Month Upto  "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
        }
 	   head= "Consolidated Accounts Details for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
 	   sup_query=" AND CMONTH BETWEEN '" +f_mnth+"'  AND '" +t_mnth+"' " ;
 	   
        }else if(f_mnth.equalsIgnoreCase(t_mnth))
        {
     	  sup_query= " AND  CMONTH = '" +f_mnth+"' " ;
     	  head= "Consolidated Accounts Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
        }
	    qry ="SELECT 1 AS data_tb, " +
	    "  v.ACCOUNTING_UNIT_ID, " +
	    "  unit.accounting_unit_name, " +
	    "  v.account_head_code, " +
	    "  c.account_head_desc, " +
	    "  hh.annualgroupingid, " +
	    "  aa.annualgroupingname, " +
	    "  aa.categoryid, " +
	    "  ccc.categoryname, " +
	    "  hh.minorgroupingid, " +
	    "  mm.minorgroupingname, " +
	    "  SUM(dr_amt)DR_AMOUNT, " +
	    "  SUM(CR_AMT)CR_AMOUNT, " +
	    "  SUM(dr_amt)-SUM(cr_amt) AS net_amt , " +
	    "  fin_year " +
	    "FROM VIEW_FAS_TRIAL_CP v " +
	    "INNER JOIN com_mst_account_heads c " +
	    "ON v.account_head_code=c.account_head_code " +
	    "INNER JOIN FAS_HEADOFACCOUNTS hh " +
	    "ON v.account_head_code=hh.accountcode " +
	    "INNER JOIN fas_mst_acct_units unit " +
	    "ON unit.accounting_unit_id=v.accounting_unit_id " +
	    "INNER JOIN annualgrouping aa " +
	    "ON hh.annualgroupingid=aa.annualgroupingid " +
	    "INNER JOIN minorgrouping mm " +
	    "ON hh.minorgroupingid=mm.minorgroupingid " +
	    "INNER JOIN category ccc " +
	    "ON aa.categoryid=ccc.categoryid " +
	    sup_query+
	    unit_qry+	    
	    "AND (dr_amt     >0 " +
	    "OR CR_AMT       >0) " +
	    "GROUP BY v.ACCOUNTING_UNIT_ID, " +
	    "  v.account_head_code, " +
	    "  unit.accounting_unit_name, " +
	    "  c.account_head_desc, " +
	    "  hh.annualgroupingid, " +
	    "  aa.annualgroupingname, " +
	    "  aa.categoryid, " +
	    "  ccc.categoryname, " +
	    "  hh.minorgroupingid, " +
	    "  mm.minorgroupingname, " +
	    "  fin_year " +
	   // "ORDER BY aa.categoryid, " +
	    "ORDER BY  " +
	    "  v.ACCOUNTING_UNIT_ID, " +
	    "  v.account_head_code, " +
	    "  hh.annualgroupingid, " +
	    "  hh.minorgroupingid" ;
	    reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/fas_trialDetail_report_Unit.jasper"));
	}
	
}else if(command.equalsIgnoreCase("fzdlive_Report")){

	Report_For=request.getParameter("Report_For");
	Re_Type=request.getParameter("FZrad_R");
	fin_FY=request.getParameter("fzdFY");  
    f_mnth=request.getParameter("fromfzdMonth");
    t_mnth=request.getParameter("tofzdMonth");
    

	if (Report_For.equalsIgnoreCase("Con_TB"))
	{
	    if(!f_mnth.equalsIgnoreCase(t_mnth))
        {
 	   
 	   if(Integer.parseInt(f_mnth.split("-")[2])!=Integer.parseInt(t_mnth.split("-")[2]))
 	   {
 		   sup_qry=" AND cashbook_year BETWEEN "+Integer.parseInt(f_mnth.split("-")[2])+"  AND  "+Integer.parseInt(t_mnth.split("-")[2])+"  AND ";
 	   }else if(Integer.parseInt(f_mnth.split("-")[2])==Integer.parseInt(t_mnth.split("-")[2]))
 	   {
 		   sup_qry=" AND cashbook_year= "+Integer.parseInt(f_mnth.split("-")[2])+" AND ";
 		   
 	   }
 	   if(f_mnth.split("-")[1].equalsIgnoreCase("APR"))
        {
 		   head= "Consolidated Trial Balance Details for the Month Upto  "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
        }
 	   head= "Consolidated Trial Balance Details  for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
 	   sup_query=" AND month_TB BETWEEN '" +f_mnth+"'  AND '" +t_mnth+"' " ;
 	   
        }else if(f_mnth.equalsIgnoreCase(t_mnth))
        {
     	  sup_query= " AND  month_TB = '" +f_mnth+"' " ;
     	  head= "Consolidated Trial Balance Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
        }
		 
		 qry ="SELECT 1 as data_tb," +
		 		"v.account_head_code, " +
          "  c.account_head_desc, " +
          "  hh.annualgroupingid, " +
          "  aa.annualgroupingname, " +
          "  aa.categoryid, " +
          "  ccc.categoryname, " +
          "  hh.minorgroupingid, " +
          "  mm.minorgroupingname, " +
          "  SUM(dr_amount)dr_amount, " +
          "  SUM(CR_AMOUNT)CR_AMOUNT, " +
          "  SUM(Net_Amt) Net_Amt, " +
          "  fin_year " +
          "FROM ( " +
          "  (SELECT a.ACCOUNTING_UNIT_ID, " +
          "    a.account_head_code, " +
          "    SUM( current_month_debit )                               AS DR_AMOUNT, " +
          "    SUM( current_month_credit)                               AS CR_AMOUNT, " +
          "    (SUM( current_month_debit )- SUM( Current_Month_Credit)) AS Net_Amt, " +
          "    0                                                        AS Sup_No, " +
          "    0                                                        AS Fin_Year , " +
          "    Cashbook_Year, " +
          "    Cashbook_Month, " +
          "    To_date(Cashbook_Month " +
          "    ||'-' " +
          "    ||Cashbook_Year,'MM-yyyy') Month_Tb " +
          "  FROM FAS_TRIAL_BALANCE a " +
          "  WHERE (current_month_debit!=0 " +
          "  OR current_month_credit!   =0) " +
          "  GROUP BY a.ACCOUNTING_UNIT_ID, " +
          "    a.account_head_code, " +
          "    0, " +
          "    0, " +
          "    Cashbook_Year, " +
          "    Cashbook_Month, " +
          "    To_date(Cashbook_Month " +
          "    ||'-' " +
          "    ||Cashbook_Year,'MM-yyyy') " +
          "  ) " +
          "UNION ALL " +
          "  (SELECT a.ACCOUNTING_UNIT_ID, " +
          "    a.account_head_code, " +
          "    SUM( current_month_debit )                               AS debit, " +
          "    SUM( current_month_credit)                               AS credit, " +
          "    (SUM( current_month_debit )- SUM( Current_Month_Credit)) AS Net, " +
          "    Supplement_No                                            AS Sup_No, " +
          "    0                                                        AS Fin_Year , " +
          "    Cashbook_Year, " +
          "    Cashbook_Month, " +
          "    To_date(Cashbook_Month " +
          "    ||'-' " +
          "    ||Cashbook_Year,'MM-yyyy')+Supplement_No month_TB " +
          "  FROM Fas_Trial_Balance_Supplement A " +
          "  WHERE (current_month_debit!=0 " +
          "  OR current_month_credit!   =0) " +
          "  GROUP BY a.ACCOUNTING_UNIT_ID, " +
          "    a.account_head_code, " +
          "    Supplement_No, " +
          "    0, " +
          "    Cashbook_Year, " +
          "    Cashbook_Month, " +
          "    To_date(Cashbook_Month " +
          "    ||'-' " +
          "    ||cashbook_year,'MM-yyyy')+supplement_no " +
          "  ) )v " +
          "INNER JOIN com_mst_account_heads c " +
          "ON v.account_head_code=c.account_head_code " +
          "INNER JOIN FAS_HEADOFACCOUNTS hh " +
          "ON v.account_head_code=hh.accountcode " +
          "INNER JOIN annualgrouping aa " +
          "ON hh.annualgroupingid=aa.annualgroupingid " +
          "INNER JOIN minorgrouping mm " +
          "ON hh.minorgroupingid=mm.minorgroupingid " +
          "INNER JOIN category ccc " +
          "ON aa.categoryid=ccc.categoryid " +
          sup_query +
          unit_qry+
          "GROUP BY v.account_head_code, " +
          "  c.account_head_desc, " +
          "  hh.annualgroupingid, " +
          "  aa.annualgroupingname, " +
          "  aa.categoryid, " +
          "  ccc.categoryname, " +
          "  hh.minorgroupingid, " +
          "  mm.minorgroupingname, " +
          "  fin_year " +
          ///"ORDER BY aa.categoryid, " +
          "ORDER BY " +
          "  hh.annualgroupingid, " +
          "  hh.minorgroupingid" ; 
		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/fas_trialDetail_report_final.jasper"));
		  
	}else if(Report_For.equalsIgnoreCase("Con_Ac")){
		unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		 if (unit_id!=0)unit_qry="  AND  ACCOUNTING_UNIT_ID="+unit_id ;
	    if(!f_mnth.equalsIgnoreCase(t_mnth))
        {
 	   
 	   if(Integer.parseInt(f_mnth.split("-")[2])!=Integer.parseInt(t_mnth.split("-")[2]))
 	   {
 		   sup_qry=" AND cashbook_year BETWEEN "+Integer.parseInt(f_mnth.split("-")[2])+"  AND  "+Integer.parseInt(t_mnth.split("-")[2])+"  AND ";
 	   }else if(Integer.parseInt(f_mnth.split("-")[2])==Integer.parseInt(t_mnth.split("-")[2]))
 	   {
 		   sup_qry=" AND cashbook_year= "+Integer.parseInt(f_mnth.split("-")[2])+" AND ";
 		   
 	   }
 	   System.out.println(Integer.parseInt(f_mnth.split("-")[1]));
 	   if(f_mnth.split("-")[1].equalsIgnoreCase("APR"))
        {
 		   head= "Consolidated Accounts Details for the Month Upto  "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
        }
 	   head= "Consolidated Accounts Details for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
 	   sup_query=" AND Month_Tb BETWEEN '" +f_mnth+"'  AND '" +t_mnth+"' " ;
 	   
        }else if(f_mnth.equalsIgnoreCase(t_mnth))
        {
     	  sup_query= " AND  Month_Tb = '" +f_mnth+"' " ;
     	  head= "Consolidated Accounts Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
        }
	    qry ="SELECT 1 as data_tb," +
	    "  v.ACCOUNTING_UNIT_ID, " +
	    "  unit.accounting_unit_name, " +
 		"v.account_head_code, " +
  "  c.account_head_desc, " +
  "  hh.annualgroupingid, " +
  "  aa.annualgroupingname, " +
  "  aa.categoryid, " +
  "  ccc.categoryname, " +
  "  hh.minorgroupingid, " +
  "  mm.minorgroupingname, " +
  "  SUM(dr_amount)dr_amount, " +
  "  SUM(CR_AMOUNT)CR_AMOUNT, " +
  "  SUM(Net_Amt) Net_Amt, " +
  "  fin_year " +
  "FROM ( " +
  "  (SELECT a.ACCOUNTING_UNIT_ID, " +
  "    a.account_head_code, " +
  "    SUM( current_month_debit )                               AS DR_AMOUNT, " +
  "    SUM( current_month_credit)                               AS CR_AMOUNT, " +
  "    (SUM( current_month_debit )- SUM( Current_Month_Credit)) AS Net_Amt, " +
  "    0                                                        AS Sup_No, " +
  "    0                                                        AS Fin_Year , " +
  "    Cashbook_Year, " +
  "    Cashbook_Month, " +
  "    To_date(Cashbook_Month " +
  "    ||'-' " +
  "    ||Cashbook_Year,'MM-yyyy') Month_Tb " +
  "  FROM FAS_TRIAL_BALANCE a " +
  "  WHERE (current_month_debit!=0 " +
  "  OR current_month_credit!   =0) " +
  "  GROUP BY a.ACCOUNTING_UNIT_ID, " +
  "    a.account_head_code, " +
  "    0, " +
  "    0, " +
  "    Cashbook_Year, " +
  "    Cashbook_Month, " +
  "    To_date(Cashbook_Month " +
  "    ||'-' " +
  "    ||Cashbook_Year,'MM-yyyy') " +
  "  ) " +
  "UNION ALL " +
  "  (SELECT a.ACCOUNTING_UNIT_ID, " +
  "    a.account_head_code, " +
  "    SUM( current_month_debit )                               AS debit, " +
  "    SUM( current_month_credit)                               AS credit, " +
  "    (SUM( current_month_debit )- SUM( Current_Month_Credit)) AS Net, " +
  "    Supplement_No                                            AS Sup_No, " +
  "    0                                                        AS Fin_Year , " +
  "    Cashbook_Year, " +
  "    Cashbook_Month, " +
  "    To_date(Cashbook_Month " +
  "    ||'-' " +
  "    ||Cashbook_Year,'MM-yyyy')+Supplement_No month_TB " +
  "  FROM Fas_Trial_Balance_Supplement A " +
  "  WHERE (current_month_debit!=0 " +
  "  OR current_month_credit!   =0) " +
  "  GROUP BY a.ACCOUNTING_UNIT_ID, " +
  "    a.account_head_code, " +
  "    Supplement_No, " +
  "    0, " +
  "    Cashbook_Year, " +
  "    Cashbook_Month, " +
  "    To_date(Cashbook_Month " +
  "    ||'-' " +
  "    ||cashbook_year,'MM-yyyy')+supplement_no " +
  "  ) )v " +
  "INNER JOIN com_mst_account_heads c " +
  "ON v.account_head_code=c.account_head_code " +
  "INNER JOIN FAS_HEADOFACCOUNTS hh " +
  "ON v.account_head_code=hh.accountcode " +
  "INNER JOIN fas_mst_acct_units unit " +
  "ON unit.accounting_unit_id=v.accounting_unit_id " +
  "INNER JOIN annualgrouping aa " +
  "ON hh.annualgroupingid=aa.annualgroupingid " +
  "INNER JOIN minorgrouping mm " +
  "ON hh.minorgroupingid=mm.minorgroupingid " +
  "INNER JOIN category ccc " +
  "ON aa.categoryid=ccc.categoryid " +
  sup_query +
  unit_qry+
  "GROUP BY " +
  "  v.ACCOUNTING_UNIT_ID, " +
  "  unit.accounting_unit_name, " +
  "v.account_head_code, " +
  "  c.account_head_desc, " +
  "  hh.annualgroupingid, " +
  "  aa.annualgroupingname, " +
  "  aa.categoryid, " +
  "  ccc.categoryname, " +
  "  hh.minorgroupingid, " +
  "  mm.minorgroupingname, " +
  "  fin_year " +
 // "ORDER BY aa.categoryid, " +
  " ORDER BY " +
  "  v.ACCOUNTING_UNIT_ID, " +
  "  hh.annualgroupingid, " +
  "  hh.minorgroupingid" ; 
	    reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/fas_trialDetail_report_Unit.jasper"));
	}
	


}
	    	 //System.out.println(Re_Type+">>> .... "+qry);
	    	 System.out.println("reportFile >> "+reportFile +">>> .... ");
	    	try{
	    		
	    		  if (!reportFile.exists())
	    		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
	    		    
	    		    JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		    
	    		    Map map = null;
	    		    map = new HashMap();
	    		    map.put("qry", qry);
	    		    map.put("heading",head);
	    		 //   System.out.println("Map va;ue  ... "+map);
	    		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);   
	    		  //
	    			//
	    			
	    	        if (Re_Type.equalsIgnoreCase("HTML_R"))   
	    	        {
	    	            System.out.println("HTML :::::::::::");
	    	                    response.setContentType("text/html");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\""+head+".html\"");
	    	                    PrintWriter out = response.getWriter();
	    	                    JRHtmlExporter exporter = new JRHtmlExporter();
	    	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	    	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	    	                    exporter.exportReport();
	    	                    out.flush();
	    	                    out.close();
	    	        }
	    	        else if (Re_Type.equalsIgnoreCase("PDF_R"))   
	    	        {
	    	                System.out.println("PDF:::::::::::");
	    	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	    	                    response.setContentType("application/pdf");
	    	                    response.setContentLength(buf.length);
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\""+head+".pdf\"");
	    	                    OutputStream out = response.getOutputStream();
	    	                    out.write(buf, 0, buf.length);
	    	                    out.close();
	    	        }
	    		}  catch (Exception ex) {
	    		    String connectMsg ="Could not create the report " + ex.getMessage(); 
	    		    String con_err ="Could not create the report " + ex; 
	    		  //  System.out.println(con_err);
	    		    System.out.println(connectMsg);
	    		}
	      }
	  
	  
	  catch (Exception e) 
      {
         			e.printStackTrace();
		}
         
      
      	
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0, txtVoucher_No = 0;
       String headwise="";
        ;
        String sql = "", txtCreat_By_Module = "";
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);

        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        headwise = request.getParameter("headwise");
        String rptsel = request.getParameter("rptsel");
        int sup = Integer.parseInt(request.getParameter("supno"));
    	System.out.println("sup===>"+sup);
        
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);

        /* txtCreat_By_Module=request.getParameter("txtCreat_By_Module");
        //txtReceipt_type="CR";
        System.out.println(txtCreat_By_Module);
        //txtReceipt_No=4;
                           try{txtVoucher_No=Integer.parseInt(request.getParameter("txtVoucher_No"));}
                            catch(NumberFormatException e){System.out.println("exception"+e );}
                            System.out.println("txtVoucher_No "+txtVoucher_No);

     */
        System.out.println("calling servlet...");
        File reportFile = null;
        try {
        	 String rtype = request.getParameter("txtoption");
             System.out.println(rtype);
             String sub_qry="";
             System.out.println("headwise------>"+headwise);
    if(headwise.equalsIgnoreCase("unitwise")){
    	sub_qry=	" ORDER BY t.accounting_unit_id, " +
				"  t.account_head_code , " +
				"  t.cashbook_year, " +
				"  t.cashbook_month";
            
    	
    	
    	if (rtype.equalsIgnoreCase("EXCEL")){
            	reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/fas_trialDetail_report_Unit.jasper"));
                
                }else{
                	
                	
                	 reportFile =
                             new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/PDFfas_trialDetail_report.jasper"));  	
                	
                }
           }else if(headwise.equalsIgnoreCase("headwise")){
        	   sub_qry=	" ORDER BY t.account_head_code ," +
       				"   t.accounting_unit_id," +
       				"  t.cashbook_year, " +
       				"  t.cashbook_month";
                	   if (rtype.equalsIgnoreCase("EXCEL")){
                       	reportFile =
                                   new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/fas_trialDetail_report_Unit1.jasper"));
                           
                           }else{
                           	
                           	
                           	 reportFile =
                                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/PDFfas_trialDetail_reportnew.jasper"));  	
                           	
                           }
                }
           else
           {
        	   sub_qry=	" ORDER BY t.accounting_unit_id, " +
       				"  t.account_head_code , " +
       				"  t.cashbook_year, " +
       				"  t.cashbook_month";
        	   
        	   
//        	   if(txtCB_Month!=3)
//        	   {        	   
        	   if (rtype.equalsIgnoreCase("EXCEL"))
        	   {
        		   reportFile =
                           new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/PDFfas_trialAbstract1.jasper"));  
        	   }
        	   
        	   else{
        	   reportFile =
                       new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/PDFfas_trialAbstract.jasper"));
        	   }
//        	   }
        	   
//        	   else
//        	   {
//        		   String rptsel = request.getParameter("rptsel"); 
//        		   if(rptsel.equalsIgnoreCase("Regular"))
//        		   {
//        			   if (rtype.equalsIgnoreCase("EXCEL"))
//                	   {
//                		   reportFile =
//                                   new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/PDFfas_trialAbstract1.jasper"));  
//                	   }
//                	   
//                	   else{
//                	   reportFile =
//                               new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/PDFfas_trialAbstract.jasper"));
//                	   }  
//        			   
//        		   }
//        		   else
//        		   {
//        			   
//        			   if (rtype.equalsIgnoreCase("EXCEL"))
//                	   {
//                		   reportFile =
//                                   new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/PDFfas_trialAbstract1_Sup.jasper"));  
//                	   }
//                	   
//                	   else{
//                	   reportFile =
//                               new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/PDFfas_trialAbstract_Sup.jasper"));
//                	   }
//        		   }
//        	   }
           }
        //    reportFile =
          //          new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TrialBalance/jasper/fas_trialDetail_report_Unit.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
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
            String sql_Qry="";
            if(cmbAcc_UnitCode!=0){
 
            	
            	
        	if(!headwise.equalsIgnoreCase("unitwise_Abstract"))
            	{    	
            	
        sql_Qry="SELECT 1 AS data_tb, " +
		"  t.accounting_unit_id, " +
		"  u.ACCOUNTING_UNIT_NAME, " +
		"  t.account_head_code , " +
		"  c.ACCOUNT_HEAD_DESC, " +
		"  t.cashbook_year, " +
		"  t.cashbook_month, " +
		"  SUM(t.dr_amt) AS dr_amount, " +
		"  SUM(t.cr_amt) AS cr_amount " +
		"FROM ( " +
		"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
		"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
		"when a.sub_ledger_type_code=5 then 'Offices'"+
		"when a.sub_ledger_type_code=6 then'Bank'"+
		"when a.sub_ledger_type_code=7 then'Employees'"+
		"when a.sub_ledger_type_code=8 then '-'"+
		"when a.sub_ledger_type_code=9 then 'Other Departments'"+
		"when a.sub_ledger_type_code=10 then 'Project'"+
		"when a.sub_ledger_type_code=11 then'Contractors'"+
		"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
		"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
			 " else '-'  end AS sub_ledger_type_desc, " +			"    a.accounting_unit_id, " +
		"    b.paid_to AS paid_to, " +
		"    a.sub_ledger_type_code, " +
		"    a.SUB_LEDGER_CODE, " +
		"    a.cashbook_year, " +
		"    a.cashbook_month, " +
		"    b.payment_date AS datevalue, " +
		"    a.ACCOUNT_HEAD_CODE, " +
		"    b.voucher_no AS voucher_no, " +
		"    'P'          AS document_type, " +
		"    a.CR_DR_INDICATOR, " +
		"    CASE " +
		"      WHEN a.cr_dr_indicator = 'DR' " +
		"      THEN a.amount " +
		"      ELSE 0 " +
		"    END AS dr_amt, " +
		"    CASE " +
		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
		"      THEN a.amount " +
		"      ELSE 0 " +
		"    END      AS cr_amt, " +
		"    a.amount AS amount, " +
		"    a.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN a.cashbook_month<=3 " +
		"      THEN (a.cashbook_year-1) " +
		"        ||'-' " +
		"        ||a.cashbook_year " +
		"      WHEN a.cashbook_month>=4 " +
		"      THEN a.cashbook_year " +
		"        ||'-' " +
		"        ||(a.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_payment_transaction a, " +
		"    fas_payment_master b " +
		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
		"  AND a.cashbook_year            = b.cashbook_year " +
		"  AND a.cashbook_month           = b.cashbook_month " +
		"  AND a.voucher_no               = b.voucher_no " +
		"  AND b.payment_status          <>'C' " +
		"  AND a.cashbook_year            ="+txtCB_Year+
		"  AND a.cashbook_month           ="+txtCB_Month+
		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
		"  ) " +
		"UNION ALL " +
		"  (SELECT case when b.sub_ledger_type_code=1 then'Supplier' when b.sub_ledger_type_code= 2 then 'Firms' when b.sub_ledger_type_code=10 then 'Assets'"+
		"when b.sub_ledger_type_code=4 then 'Cheque Books'"+
		"when b.sub_ledger_type_code=5 then 'Offices'"+
		"when b.sub_ledger_type_code=6 then'Bank'"+
		"when b.sub_ledger_type_code=7 then'Employees'"+
		"when b.sub_ledger_type_code=8 then '-'"+
		"when b.sub_ledger_type_code=9 then 'Other Departments'"+
		"when b.sub_ledger_type_code=10 then 'Project'"+
		"when b.sub_ledger_type_code=11 then'Contractors'"+
		"when b.sub_ledger_type_code=12 then 'Scheme Owner'"+
		"when b.sub_ledger_type_code=13 then 'Beneficiary'"+
			 " else '-'  end AS sub_ledger_type_desc, " +			"    b.accounting_unit_id, " +
		"    b.paid_to AS paid_to, " +
		"    b.sub_ledger_type_code, " +
		"    b.sub_ledger_code, " +
		"    B.cashbook_year, " +
		"    B.cashbook_month, " +
		"    b.payment_date AS datevalue, " +
		"    b.Account_Head_Code, " +
		"    b.voucher_no AS voucher_no, " +
		"    'P'          AS Document_Type, " +
		"    'CR'         AS Cr_Dr_Indicator, " +
		"    CASE " +
		"      WHEN CR_DR_INDICATOR = 'DR' " +
		"      THEN B.TOTAL_AMOUNT " +
		"      ELSE 0 " +
		"    END AS dr_amt, " +
		"    CASE " +
		"      WHEN CR_DR_INDICATOR = 'CR' " +
		"      THEN B.TOTAL_AMOUNT " +
		"      ELSE 0 " +
		"    END            AS Cr_Amt, " +
		"    b.total_amount AS amount, " +
		"    b.remarks      AS particulars, " +
		"    0              AS sup_no, " +
		"    CASE " +
		"      WHEN b.cashbook_month<=3 " +
		"      THEN (b.cashbook_year-1) " +
		"        ||'-' " +
		"        ||b.cashbook_year " +
		"      WHEN b.cashbook_month>=4 " +
		"      THEN b.cashbook_year " +
		"        ||'-' " +
		"        ||(b.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_payment_master b " +
		"  WHERE b.payment_status  <>'C' " +
		"  AND b.cashbook_year      = "+txtCB_Year+
		"  AND b.cashbook_month     ="+txtCB_Month+
		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		"UNION ALL " +
		"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
		"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
		"when a.sub_ledger_type_code=5 then 'Offices'"+
		"when a.sub_ledger_type_code=6 then'Bank'"+
		"when a.sub_ledger_type_code=7 then'Employees'"+
		"when a.sub_ledger_type_code=8 then '-'"+
		"when a.sub_ledger_type_code=9 then 'Other Departments'"+
		"when a.sub_ledger_type_code=10 then 'Project'"+
		"when a.sub_ledger_type_code=11 then'Contractors'"+
		"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
		"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
			 " else '-'  end AS sub_ledger_type_desc, " +			"    a.accounting_unit_id, " +
		"    b.received_from AS paid_to, " +
		"    a.sub_ledger_type_code, " +
		"    a.sub_ledger_code, " +
		"    a.cashbook_year, " +
		"    a.cashbook_month, " +
		"    b.receipt_date AS datevalue, " +
		"    a.ACCOUNT_HEAD_CODE, " +
		"    b.receipt_no AS voucher_no , " +
		"    'R'          AS document_type, " +
		"    a.CR_DR_INDICATOR, " +
		"    CASE " +
		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
		"      THEN a.amount " +
		"      ELSE 0 " +
		"    END AS dr_amt, " +
		"    CASE " +
		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
		"      THEN a.amount " +
		"      ELSE 0 " +
		"    END      AS cr_amt, " +
		"    a.amount AS amount, " +
		"    a.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN a.cashbook_month<=3 " +
		"      THEN (a.cashbook_year-1) " +
		"        ||'-' " +
		"        ||a.cashbook_year " +
		"      WHEN a.cashbook_month>=4 " +
		"      THEN a.cashbook_year " +
		"        ||'-' " +
		"        ||(a.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_receipt_transaction a, " +
		"    fas_receipt_master b " +
		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
		"  AND a.cashbook_year            = b.cashbook_year " +
		"  AND a.cashbook_month           = b.cashbook_month " +
		"  AND a.receipt_no               = b.receipt_no " +
		"  AND b.receipt_status          <>'C' " +
		"  AND a.cashbook_year            ="+txtCB_Year+
		"  AND a.cashbook_month           ="+txtCB_Month+
		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
		"  ) " +
		"UNION ALL " +
		"  (SELECT case when b.sub_ledger_type_code=1 then'Supplier' when b.sub_ledger_type_code= 2 then 'Firms' when b.sub_ledger_type_code=10 then 'Assets'"+
		"when b.sub_ledger_type_code=4 then 'Cheque Books'"+
		"when b.sub_ledger_type_code=5 then 'Offices'"+
		"when b.sub_ledger_type_code=6 then'Bank'"+
		"when b.sub_ledger_type_code=7 then'Employees'"+
		"when b.sub_ledger_type_code=8 then '-'"+
		"when b.sub_ledger_type_code=9 then 'Other Departments'"+
		"when b.sub_ledger_type_code=10 then 'Project'"+
		"when b.sub_ledger_type_code=11 then'Contractors'"+
		"when b.sub_ledger_type_code=12 then 'Scheme Owner'"+
		"when b.sub_ledger_type_code=13 then 'Beneficiary'"+
			 " else '-'  end AS sub_ledger_type_desc, " +			"    b.accounting_unit_id, " +
		"    b.received_from AS paid_to, " +
		"    b.sub_ledger_type_code, " +
		"    b.SUB_LEDGER_CODE, " +
		"    B.cashbook_year, " +
		"    b.cashbook_month, " +
		"    b.receipt_date AS datevalue, " +
		"    b.account_head_code , " +
		"    b.receipt_no AS voucher_no , " +
		"    'R'          AS document_type, " +
		"    b.CR_DR_INDICATOR, " +
		"    CASE " +
		"      WHEN b.CR_DR_INDICATOR = 'DR' " +
		"      THEN b.TOTAL_AMOUNT " +
		"      ELSE 0 " +
		"    END AS dr_amt, " +
		"    CASE " +
		"      WHEN b.CR_DR_INDICATOR = 'CR' " +
		"      THEN b.TOTAL_AMOUNT " +
		"      ELSE 0 " +
		"    END            AS cr_amt, " +
		"    B.Total_Amount AS Amount, " +
		"    b.remarks      AS particulars, " +
		"    0              AS sup_no, " +
		"    CASE " +
		"      WHEN b.cashbook_month<=3 " +
		"      THEN (b.cashbook_year-1) " +
		"        ||'-' " +
		"        ||b.cashbook_year " +
		"      WHEN b.cashbook_month>=4 " +
		"      THEN b.cashbook_year " +
		"        ||'-' " +
		"        ||(b.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_receipt_master b " +
		"  WHERE b.receipt_status  <>'C' " +
		"  AND b.cashbook_year      ="+txtCB_Year+
		"  AND b.cashbook_month     ="+txtCB_Month+
		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		"UNION ALL " +
		"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
		"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
		"when a.sub_ledger_type_code=5 then 'Offices'"+
		"when a.sub_ledger_type_code=6 then'Bank'"+
		"when a.sub_ledger_type_code=7 then'Employees'"+
		"when a.sub_ledger_type_code=8 then '-'"+
		"when a.sub_ledger_type_code=9 then 'Other Departments'"+
		"when a.sub_ledger_type_code=10 then 'Project'"+
		"when a.sub_ledger_type_code=11 then'Contractors'"+
		"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
		"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
			 " else '-'  end AS sub_ledger_type_desc, " +			"    a.accounting_unit_id, " +
		"    ' ' AS paid_to, " +
		"    a.sub_ledger_type_code, " +
		"    a.SUB_LEDGER_CODE, " +
		"    a.cashbook_year, " +
		"    a.cashbook_month, " +
		"    b.voucher_date AS datevalue, " +
		"    a.account_head_code , " +
		"    b.voucher_no AS voucher_no , " +
		"    'J'          AS document_type, " +
		"    a.CR_DR_INDICATOR, " +
		"    CASE " +
		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
		"      THEN a.amount " +
		"      ELSE 0 " +
		"    END AS dr_amt, " +
		"    CASE " +
		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
		"      THEN a.amount " +
		"      ELSE 0 " +
		"    END      AS cr_amt, " +
		"    a.amount AS amount, " +
		"    a.particulars, " +
		"    coalesce (b.supplement_no,0) AS sup_no, " +
		"    CASE " +
		"      WHEN a.cashbook_month<=3 " +
		"      THEN (a.cashbook_year-1) " +
		"        ||'-' " +
		"        ||a.cashbook_year " +
		"      WHEN a.cashbook_month>=4 " +
		"      THEN a.cashbook_year " +
		"        ||'-' " +
		"        ||(a.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_journal_transaction a, " +
		"    fas_journal_master b " +
		"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
		"  AND a.cashbook_year            = b.cashbook_year " +
		"  AND a.cashbook_month           = b.cashbook_month " +
		"  AND a.voucher_no               = b.voucher_no " +
		"  AND b.journal_status          <>'C' " +
		"  AND a.cashbook_year            ="+txtCB_Year+
		"  AND a.cashbook_month           ="+txtCB_Month+
		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT '' AS sub_ledger_type_desc, " +
		"    accounting_unit_id, " +
		"    '' AS Paid_To, " +
		"    0  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
		"    cashbook_year, " +
		"    cashbook_month, " +
		"    Date_Of_Transfer     AS Datevalue, " +
		"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
		"    voucher_no           AS voucher_no, " +
		"    'IBT1'               AS document_type, " +
		"    cr_dr_indicator, " +
		"    CASE " +
		"      WHEN cr_dr_indicator = 'DR' " +
		"      THEN amount " +
		"      ELSE 0 " +
		"    END AS dr_amt, " +
		"    CASE " +
		"      WHEN cr_dr_indicator = 'CR' " +
		"      THEN amount " +
		"      ELSE 0 " +
		"    END AS cr_amt, " +
		"    amount, " +
		"    particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN cashbook_month<=3 " +
		"      THEN (cashbook_year-1) " +
		"        ||'-' " +
		"        ||cashbook_year " +
		"      WHEN cashbook_month>=4 " +
		"      THEN cashbook_year " +
		"        ||'-' " +
		"        ||(cashbook_year+1) " +
		"    END fin_year " +
		"  FROM " +
		"    (SELECT a.accounting_unit_id, " +
		"      '' AS sub_ledger_type_desc, " +
		"      A.CR_ACCOUNT_HEAD_CODE, " +
		"      Date_Of_Transfer, " +
		"      '' AS paid_received, " +
		"      a.cashbook_year, " +
		"      a.cashbook_month, " +
		"      Date_Of_Transfer AS Datevalue, " +
		"      a.VOUCHER_NO     AS voucher_no, " +
		"      'IBT'            AS document_type, " +
		"      'CR'             AS cr_dr_indicator, " +
		"      total_amount     AS amount, " +
		"      particulars " +
		"    FROM fas_inter_bank_trf_at_ho a " +
		"    WHERE a.TRANSFER_STATUS <>'C' " +
		"    AND a.cashbook_year      ="+txtCB_Year+
		"    AND a.cashbook_month     ="+txtCB_Month+
		"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
		"    ) " +
		"  ) " +
		" UNION ALL " +
		"  (SELECT '' AS sub_ledger_type_desc, " +
		"    a.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    0  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
		"    a.cashbook_year, " +
		"    a.cashbook_month, " +
		"    a.DATE_OF_TRANSFER     AS datevalue, " +
		"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
		"    a.voucher_no           AS voucher_no, " +
		"    'IBT'                  AS document_type, " +
		"    'DR'                   AS CR_DR_INDICATOR, " +
		"    a.TOTAL_AMOUNT         AS dr_amt, " +
		"    0                      AS cr_amt, " +
		"    a.total_amount         AS amount, " +
		"    a.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN a.cashbook_month<=3 " +
		"      THEN (a.cashbook_year-1) " +
		"        ||'-' " +
		"        ||a.cashbook_year " +
		"      WHEN a.cashbook_month>=4 " +
		"      THEN a.cashbook_year " +
		"        ||'-' " +
		"        ||(a.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_inter_bank_trf_at_ho a " +
		"  WHERE a.transfer_status  ='L' " +
		"  AND a.cashbook_year      ="+txtCB_Year+
		"  AND a.cashbook_month     ="+txtCB_Month+
		"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
		"    b.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    6  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
		//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
		"    b.cashbook_year, " +
		"    b.cashbook_month, " +
		"    b.RECEIPT_DATE         AS datevalue, " +
		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
		"    b.RECEIPT_NO           AS voucher_no, " +
		"    'FR'                   AS document_type, " +
		"    'CR'                   AS CR_DR_INDICATOR, " +
		"    0                      AS dr_amt, " +
		"    b.total_amount         AS cr_amt, " +
		"    b.total_amount         AS amount, " +
		"    b.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN b.cashbook_month<=3 " +
		"      THEN (b.cashbook_year-1) " +
		"        ||'-' " +
		"        ||b.cashbook_year " +
		"      WHEN b.cashbook_month>=4 " +
		"      THEN b.cashbook_year " +
		"        ||'-' " +
		"        ||(b.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_fund_receipt_by_office b " +
		"  WHERE b.receipt_status  <>'C' " +
		"  AND b.cashbook_year      ="+txtCB_Year+
		"  AND b.cashbook_month     ="+txtCB_Month+
		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
		"    b.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    6  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
		"    b.cashbook_year, " +
		"    b.cashbook_month, " +
		"    b.RECEIPT_DATE         AS datevalue, " +
		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
		"    b.RECEIPT_NO           AS voucher_no, " +
		"    'FR'                   AS document_type, " +
		"    'DR'                   AS CR_DR_INDICATOR, " +
		"    b.total_amount         AS dr_amt, " +
		"    0                      AS cr_amt, " +
		"    b.total_amount         AS amount, " +
		"    b.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN b.cashbook_month<=3 " +
		"      THEN (b.cashbook_year-1) " +
		"        ||'-' " +
		"        ||b.cashbook_year " +
		"      WHEN b.cashbook_month>=4 " +
		"      THEN b.cashbook_year " +
		"        ||'-' " +
		"        ||(b.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_fund_receipt_by_office b " +
		"  WHERE b.receipt_status  <>'C' " +
		"  AND b.cashbook_year      ="+txtCB_Year+
		"  AND b.cashbook_month     ="+txtCB_Month+
		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
		"    b.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    6  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
		"    b.cashbook_year, " +
		"    b.cashbook_month, " +
		"    b.DATE_OF_TRANSFER     AS datevalue, " +
		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
		"    b.VOUCHER_NO           AS voucher_no, " +
		"    'FT'                   AS document_type, " +
		"    'DR'                   AS CR_DR_INDICATOR, " +
		"    b.total_amount         AS dr_amt, " +
		"    0                      AS cr_amt, " +
		"    b.total_amount         AS amount, " +
		"    b.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN b.cashbook_month<=3 " +
		"      THEN (b.cashbook_year-1) " +
		"        ||'-' " +
		"        ||b.cashbook_year " +
		"      WHEN b.cashbook_month>=4 " +
		"      THEN b.cashbook_year " +
		"        ||'-' " +
		"        ||(b.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_fund_trf_from_office b " +
		"  WHERE b.transfer_status <>'C' " +
		"  AND b.cashbook_year      ="+txtCB_Year+
		"  AND b.cashbook_month     ="+txtCB_Month+
		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
		"    b.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    6  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
		"    b.cashbook_year, " +
		"    b.cashbook_month, " +
		"    b.DATE_OF_TRANSFER     AS datevalue, " +
		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
		"    b.VOUCHER_NO           AS voucher_no, " +
		"    'FT'                   AS document_type, " +
		"    'CR'                   AS CR_DR_INDICATOR, " +
		"    0                      AS dr_amt, " +
		"    b.total_amount         AS cr_amt, " +
		"    b.total_amount         AS amount, " +
		"    b.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN b.cashbook_month<=3 " +
		"      THEN (b.cashbook_year-1) " +
		"        ||'-' " +
		"        ||b.cashbook_year " +
		"      WHEN b.cashbook_month>=4 " +
		"      THEN b.cashbook_year " +
		"        ||'-' " +
		"        ||(b.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_fund_trf_from_office b " +
		"  WHERE b.transfer_status <>'C' " +
		"  AND b.cashbook_year      ="+txtCB_Year+
		"  AND b.cashbook_month     ="+txtCB_Month+
		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
		"    b.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    6  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
		"    b.cashbook_year, " +
		"    b.cashbook_month, " +
		"    b.RECEIPT_DATE         AS datevalue, " +
		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
		"    b.RECEIPT_NO           AS voucher_no, " +
		"    'FT'                   AS document_type, " +
		"    'CR'                   AS Cr_Dr_Indicator, " +
		"    0                      AS dr_amt, " +
		"    b.total_amount         AS cr_amt, " +
		"    b.total_amount         AS amount, " +
		"    b.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN b.cashbook_month<=3 " +
		"      THEN (b.cashbook_year-1) " +
		"        ||'-' " +
		"        ||b.cashbook_year " +
		"      WHEN b.cashbook_month>=4 " +
		"      THEN b.cashbook_year " +
		"        ||'-' " +
		"        ||(b.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_fund_receipt_by_ho b " +
		"  WHERE b.receipt_status  <>'C' " +
		"  AND b.cashbook_year      ="+txtCB_Year+
		"  AND b.cashbook_month     ="+txtCB_Month+
		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
		"    b.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    6  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
		//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
		"    b.cashbook_year, " +
		"    b.cashbook_month, " +
		"    b.RECEIPT_DATE         AS datevalue, " +
		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
		"    B.Receipt_No           AS Voucher_No, " +
		"    'FRH'                  AS Document_Type, " +
		"    'DR'                   AS Cr_Dr_Indicator, " +
		"    B.Total_Amount         AS Dr_Amt, " +
		"    0                      AS cr_amt, " +
		"    b.total_amount         AS amount, " +
		"    b.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN b.cashbook_month<=3 " +
		"      THEN (b.cashbook_year-1) " +
		"        ||'-' " +
		"        ||b.cashbook_year " +
		"      WHEN b.cashbook_month>=4 " +
		"      THEN b.cashbook_year " +
		"        ||'-' " +
		"        ||(b.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_fund_receipt_by_ho b " +
		"  WHERE b.receipt_status  <>'C' " +
		"  AND b.cashbook_year      ="+txtCB_Year+
		"  AND b.cashbook_month     ="+txtCB_Month+
		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
		"    a.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    6  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
		"    a.cashbook_year, " +
		"    a.cashbook_month, " +
		"    b.date_of_transfer AS datevalue, " +
		"    a.ACCOUNT_HEAD_CODE, " +
		"    B.Voucher_No AS Voucher_No, " +
		"    'FTH'        AS Document_Type, " +
		"    'DR'         AS CR_DR_INDICATOR, " +
		"    CASE " +
		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
		"      THEN a.amount " +
		"      ELSE 0 " +
		"    END AS dr_amt, " +
		"    CASE " +
		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
		"      THEN a.amount " +
		"      ELSE 0 " +
		"    END      AS cr_amt, " +
		"    a.amount AS amount, " +
		"    a.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN a.cashbook_month<=3 " +
		"      THEN (a.cashbook_year-1) " +
		"        ||'-' " +
		"        ||a.cashbook_year " +
		"      WHEN a.cashbook_month>=4 " +
		"      THEN a.cashbook_year " +
		"        ||'-' " +
		"        ||(a.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_fund_trf_from_ho_trn a, " +
		"    fas_fund_trf_from_ho_master b " +
		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
		"  AND a.cashbook_year            = b.cashbook_year " +
		"  AND a.cashbook_month           = b.cashbook_month " +
		"  AND a.voucher_no               = b.voucher_no " +
		"  AND b.TRANSFER_STATUS         <>'C' " +
		"  AND a.cashbook_year            ="+txtCB_Year+
		"  AND a.cashbook_month           ="+txtCB_Month+
		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
		"  ) " +
		" UNION ALL " +
		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
		"    a.accounting_unit_id, " +
		"    '' AS paid_to, " +
		"    6  AS sub_ledger_type_code, " +
		"    0  AS SUB_LEDGER_CODE, " +
	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
		"    a.cashbook_year, " +
		"    a.cashbook_month, " +
		"    b.date_of_transfer AS datevalue, " +
		"    b.account_head_code , " +
		"    B.Voucher_No AS Voucher_No, " +
		"    'FTH'        AS Document_Type, " +
		"    'CR'         AS CR_DR_INDICATOR, " +
		"    0            AS dr_amt, " +
		"    a.amount     AS cr_amt, " +
		"    a.amount     AS amount, " +
		"    a.particulars, " +
		"    0 AS sup_no, " +
		"    CASE " +
		"      WHEN a.cashbook_month<=3 " +
		"      THEN (a.cashbook_year-1) " +
		"        ||'-' " +
		"        ||a.cashbook_year " +
		"      WHEN a.cashbook_month>=4 " +
		"      THEN a.cashbook_year " +
		"        ||'-' " +
		"        ||(a.cashbook_year+1) " +
		"    END fin_year " +
		"  FROM fas_fund_trf_from_ho_trn a, " +
		"    fas_fund_trf_from_ho_master b " +
		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
		"  AND a.cashbook_year            = b.cashbook_year " +
		"  AND a.cashbook_month           = b.cashbook_month " +
		"  AND a.voucher_no               = b.voucher_no " +
		"  AND b.TRANSFER_STATUS         <>'C' " +
		"  AND a.cashbook_year            ="+txtCB_Year+
		"  AND a.cashbook_month           ="+txtCB_Month+
		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
		"  ))t " +
		" INNER JOIN FAS_MST_ACCT_UNITS u " +
		" ON t.accounting_unit_id=u.accounting_unit_id " +
		" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
		" ON t.account_head_code=c.account_head_code " +
		" GROUP BY t.accounting_unit_id, " +
		"  u.ACCOUNTING_UNIT_NAME, " +
		"  t.account_head_code, " +
		"  c.ACCOUNT_HEAD_DESC, " +
		"  t.cashbook_year, " +
		"  t.cashbook_month " +sub_qry;
	}
   if ((txtCB_Month==3) && (rptsel.equalsIgnoreCase("Regular"))&&
			headwise.equalsIgnoreCase("unitwise_Abstract"))     	
   {    	
   	
sql_Qry="SELECT 1 AS data_tb, " +
"  t.accounting_unit_id, " +
"  u.ACCOUNTING_UNIT_NAME, " +
"  t.account_head_code , " +
"  c.ACCOUNT_HEAD_DESC, " +
"  t.cashbook_year, " +
"  t.cashbook_month, " +
"  SUM(t.dr_amt) AS dr_amount, " +
"  SUM(t.cr_amt) AS cr_amount " +
"FROM ( " +
"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
"when a.sub_ledger_type_code=5 then 'Offices'"+
"when a.sub_ledger_type_code=6 then'Bank'"+
"when a.sub_ledger_type_code=7 then'Employees'"+
"when a.sub_ledger_type_code=8 then '-'"+
"when a.sub_ledger_type_code=9 then 'Other Departments'"+
"when a.sub_ledger_type_code=10 then 'Project'"+
"when a.sub_ledger_type_code=11 then'Contractors'"+
"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
	 " else '-'  end AS sub_ledger_type_desc, " +	"    a.accounting_unit_id, " +
"    b.paid_to AS paid_to, " +
"    a.sub_ledger_type_code, " +
"    a.SUB_LEDGER_CODE, " +
"    a.cashbook_year, " +
"    a.cashbook_month, " +
"    b.payment_date AS datevalue, " +
"    a.ACCOUNT_HEAD_CODE, " +
"    b.voucher_no AS voucher_no, " +
"    'P'          AS document_type, " +
"    a.CR_DR_INDICATOR, " +
"    CASE " +
"      WHEN a.cr_dr_indicator = 'DR' " +
"      THEN a.amount " +
"      ELSE 0 " +
"    END AS dr_amt, " +
"    CASE " +
"      WHEN a.CR_DR_INDICATOR = 'CR' " +
"      THEN a.amount " +
"      ELSE 0 " +
"    END      AS cr_amt, " +
"    a.amount AS amount, " +
"    a.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN a.cashbook_month<=3 " +
"      THEN (a.cashbook_year-1) " +
"        ||'-' " +
"        ||a.cashbook_year " +
"      WHEN a.cashbook_month>=4 " +
"      THEN a.cashbook_year " +
"        ||'-' " +
"        ||(a.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_payment_transaction a, " +
"    fas_payment_master b " +
"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
"  AND a.cashbook_year            = b.cashbook_year " +
"  AND a.cashbook_month           = b.cashbook_month " +
"  AND a.voucher_no               = b.voucher_no " +
"  AND b.payment_status          <>'C' " +
"  AND a.cashbook_year            ="+txtCB_Year+
"  AND a.cashbook_month           ="+txtCB_Month+
"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
"  ) " +
"UNION ALL " +
"  (SELECT case when b.sub_ledger_type_code=1 then'Supplier' when b.sub_ledger_type_code= 2 then 'Firms' when b.sub_ledger_type_code=10 then 'Assets'"+
"when b.sub_ledger_type_code=4 then 'Cheque Books'"+
"when b.sub_ledger_type_code=5 then 'Offices'"+
"when b.sub_ledger_type_code=6 then'Bank'"+
"when b.sub_ledger_type_code=7 then'Employees'"+
"when b.sub_ledger_type_code=8 then '-'"+
"when b.sub_ledger_type_code=9 then 'Other Departments'"+
"when b.sub_ledger_type_code=10 then 'Project'"+
"when b.sub_ledger_type_code=11 then'Contractors'"+
"when b.sub_ledger_type_code=12 then 'Scheme Owner'"+
"when b.sub_ledger_type_code=13 then 'Beneficiary'"+
	 " else '-'  end AS sub_ledger_type_desc, " +	"    b.accounting_unit_id, " +
"    b.paid_to AS paid_to, " +
"    b.sub_ledger_type_code, " +
"    b.sub_ledger_code, " +
"    B.cashbook_year, " +
"    B.cashbook_month, " +
"    b.payment_date AS datevalue, " +
"    b.Account_Head_Code, " +
"    b.voucher_no AS voucher_no, " +
"    'P'          AS Document_Type, " +
"    'CR'         AS Cr_Dr_Indicator, " +
"    CASE " +
"      WHEN CR_DR_INDICATOR = 'DR' " +
"      THEN B.TOTAL_AMOUNT " +
"      ELSE 0 " +
"    END AS dr_amt, " +
"    CASE " +
"      WHEN CR_DR_INDICATOR = 'CR' " +
"      THEN B.TOTAL_AMOUNT " +
"      ELSE 0 " +
"    END            AS Cr_Amt, " +
"    b.total_amount AS amount, " +
"    b.remarks      AS particulars, " +
"    0              AS sup_no, " +
"    CASE " +
"      WHEN b.cashbook_month<=3 " +
"      THEN (b.cashbook_year-1) " +
"        ||'-' " +
"        ||b.cashbook_year " +
"      WHEN b.cashbook_month>=4 " +
"      THEN b.cashbook_year " +
"        ||'-' " +
"        ||(b.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_payment_master b " +
"  WHERE b.payment_status  <>'C' " +
"  AND b.cashbook_year      = "+txtCB_Year+
"  AND b.cashbook_month     ="+txtCB_Month+
"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
"UNION ALL " +
"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
"when a.sub_ledger_type_code=5 then 'Offices'"+
"when a.sub_ledger_type_code=6 then'Bank'"+
"when a.sub_ledger_type_code=7 then'Employees'"+
"when a.sub_ledger_type_code=8 then '-'"+
"when a.sub_ledger_type_code=9 then 'Other Departments'"+
"when a.sub_ledger_type_code=10 then 'Project'"+
"when a.sub_ledger_type_code=11 then'Contractors'"+
"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
	 " else '-'  end AS sub_ledger_type_desc, " +	"    a.accounting_unit_id, " +
"    b.received_from AS paid_to, " +
"    a.sub_ledger_type_code, " +
"    a.sub_ledger_code, " +
"    a.cashbook_year, " +
"    a.cashbook_month, " +
"    b.receipt_date AS datevalue, " +
"    a.ACCOUNT_HEAD_CODE, " +
"    b.receipt_no AS voucher_no , " +
"    'R'          AS document_type, " +
"    a.CR_DR_INDICATOR, " +
"    CASE " +
"      WHEN a.CR_DR_INDICATOR = 'DR' " +
"      THEN a.amount " +
"      ELSE 0 " +
"    END AS dr_amt, " +
"    CASE " +
"      WHEN a.CR_DR_INDICATOR = 'CR' " +
"      THEN a.amount " +
"      ELSE 0 " +
"    END      AS cr_amt, " +
"    a.amount AS amount, " +
"    a.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN a.cashbook_month<=3 " +
"      THEN (a.cashbook_year-1) " +
"        ||'-' " +
"        ||a.cashbook_year " +
"      WHEN a.cashbook_month>=4 " +
"      THEN a.cashbook_year " +
"        ||'-' " +
"        ||(a.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_receipt_transaction a, " +
"    fas_receipt_master b " +
"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
"  AND a.cashbook_year            = b.cashbook_year " +
"  AND a.cashbook_month           = b.cashbook_month " +
"  AND a.receipt_no               = b.receipt_no " +
"  AND b.receipt_status          <>'C' " +
"  AND a.cashbook_year            ="+txtCB_Year+
"  AND a.cashbook_month           ="+txtCB_Month+
"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
"  ) " +
"UNION ALL " +
"  (SELECT case when b.sub_ledger_type_code=1 then'Supplier' when b.sub_ledger_type_code= 2 then 'Firms' when b.sub_ledger_type_code=10 then 'Assets'"+
"when b.sub_ledger_type_code=4 then 'Cheque Books'"+
"when b.sub_ledger_type_code=5 then 'Offices'"+
"when b.sub_ledger_type_code=6 then'Bank'"+
"when b.sub_ledger_type_code=7 then'Employees'"+
"when b.sub_ledger_type_code=8 then '-'"+
"when b.sub_ledger_type_code=9 then 'Other Departments'"+
"when b.sub_ledger_type_code=10 then 'Project'"+
"when b.sub_ledger_type_code=11 then'Contractors'"+
"when b.sub_ledger_type_code=12 then 'Scheme Owner'"+
"when b.sub_ledger_type_code=13 then 'Beneficiary'"+
	 " else '-'  end AS sub_ledger_type_desc, " +	"    b.accounting_unit_id, " +
"    b.received_from AS paid_to, " +
"    b.sub_ledger_type_code, " +
"    b.SUB_LEDGER_CODE, " +
"    B.cashbook_year, " +
"    b.cashbook_month, " +
"    b.receipt_date AS datevalue, " +
"    b.account_head_code , " +
"    b.receipt_no AS voucher_no , " +
"    'R'          AS document_type, " +
"    b.CR_DR_INDICATOR, " +
"    CASE " +
"      WHEN b.CR_DR_INDICATOR = 'DR' " +
"      THEN b.TOTAL_AMOUNT " +
"      ELSE 0 " +
"    END AS dr_amt, " +
"    CASE " +
"      WHEN b.CR_DR_INDICATOR = 'CR' " +
"      THEN b.TOTAL_AMOUNT " +
"      ELSE 0 " +
"    END            AS cr_amt, " +
"    B.Total_Amount AS Amount, " +
"    b.remarks      AS particulars, " +
"    0              AS sup_no, " +
"    CASE " +
"      WHEN b.cashbook_month<=3 " +
"      THEN (b.cashbook_year-1) " +
"        ||'-' " +
"        ||b.cashbook_year " +
"      WHEN b.cashbook_month>=4 " +
"      THEN b.cashbook_year " +
"        ||'-' " +
"        ||(b.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_receipt_master b " +
"  WHERE b.receipt_status  <>'C' " +
"  AND b.cashbook_year      ="+txtCB_Year+
"  AND b.cashbook_month     ="+txtCB_Month+
"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
"UNION ALL " +
"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
"when a.sub_ledger_type_code=5 then 'Offices'"+
"when a.sub_ledger_type_code=6 then'Bank'"+
"when a.sub_ledger_type_code=7 then'Employees'"+
"when a.sub_ledger_type_code=8 then '-'"+
"when a.sub_ledger_type_code=9 then 'Other Departments'"+
"when a.sub_ledger_type_code=10 then 'Project'"+
"when a.sub_ledger_type_code=11 then'Contractors'"+
"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
	 " else '-'  end AS sub_ledger_type_desc, " +	"    a.accounting_unit_id, " +
"    ' ' AS paid_to, " +
"    a.sub_ledger_type_code, " +
"    a.SUB_LEDGER_CODE, " +
"    a.cashbook_year, " +
"    a.cashbook_month, " +
"    b.voucher_date AS datevalue, " +
"    a.account_head_code , " +
"    b.voucher_no AS voucher_no , " +
"    'J'          AS document_type, " +
"    a.CR_DR_INDICATOR, " +
"    CASE " +
"      WHEN a.CR_DR_INDICATOR = 'DR' " +
"      THEN a.amount " +
"      ELSE 0 " +
"    END AS dr_amt, " +
"    CASE " +
"      WHEN a.CR_DR_INDICATOR = 'CR' " +
"      THEN a.amount " +
"      ELSE 0 " +
"    END      AS cr_amt, " +
"    a.amount AS amount, " +
"    a.particulars, " +
"    coalesce (b.supplement_no,0) AS sup_no, " +
"    CASE " +
"      WHEN a.cashbook_month<=3 " +
"      THEN (a.cashbook_year-1) " +
"        ||'-' " +
"        ||a.cashbook_year " +
"      WHEN a.cashbook_month>=4 " +
"      THEN a.cashbook_year " +
"        ||'-' " +
"        ||(a.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_journal_transaction a, " +
"    fas_journal_master b " +
"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
"  AND a.cashbook_year            = b.cashbook_year " +
"  AND a.cashbook_month           = b.cashbook_month " +
"  AND a.voucher_no               = b.voucher_no " +
"  AND b.journal_status          <>'C' " +
"  AND a.cashbook_year            ="+txtCB_Year+
"  AND a.cashbook_month           ="+txtCB_Month+
"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT '' AS sub_ledger_type_desc, " +
"    accounting_unit_id, " +
"    '' AS Paid_To, " +
"    0  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
"    cashbook_year, " +
"    cashbook_month, " +
"    Date_Of_Transfer     AS Datevalue, " +
"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
"    voucher_no           AS voucher_no, " +
"    'IBT1'               AS document_type, " +
"    cr_dr_indicator, " +
"    CASE " +
"      WHEN cr_dr_indicator = 'DR' " +
"      THEN amount " +
"      ELSE 0 " +
"    END AS dr_amt, " +
"    CASE " +
"      WHEN cr_dr_indicator = 'CR' " +
"      THEN amount " +
"      ELSE 0 " +
"    END AS cr_amt, " +
"    amount, " +
"    particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN cashbook_month<=3 " +
"      THEN (cashbook_year-1) " +
"        ||'-' " +
"        ||cashbook_year " +
"      WHEN cashbook_month>=4 " +
"      THEN cashbook_year " +
"        ||'-' " +
"        ||(cashbook_year+1) " +
"    END fin_year " +
"  FROM " +
"    (SELECT a.accounting_unit_id, " +
"      '' AS sub_ledger_type_desc, " +
"      A.CR_ACCOUNT_HEAD_CODE, " +
"      Date_Of_Transfer, " +
"      '' AS paid_received, " +
"      a.cashbook_year, " +
"      a.cashbook_month, " +
"      Date_Of_Transfer AS Datevalue, " +
"      a.VOUCHER_NO     AS voucher_no, " +
"      'IBT'            AS document_type, " +
"      'CR'             AS cr_dr_indicator, " +
"      total_amount     AS amount, " +
"      particulars " +
"    FROM fas_inter_bank_trf_at_ho a " +
"    WHERE a.TRANSFER_STATUS <>'C' " +
"    AND a.cashbook_year      ="+txtCB_Year+
"    AND a.cashbook_month     ="+txtCB_Month+
"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
"    ) " +
"  ) " +
" UNION ALL " +
"  (SELECT '' AS sub_ledger_type_desc, " +
"    a.accounting_unit_id, " +
"    '' AS paid_to, " +
"    0  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
"    a.cashbook_year, " +
"    a.cashbook_month, " +
"    a.DATE_OF_TRANSFER     AS datevalue, " +
"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
"    a.voucher_no           AS voucher_no, " +
"    'IBT'                  AS document_type, " +
"    'DR'                   AS CR_DR_INDICATOR, " +
"    a.TOTAL_AMOUNT         AS dr_amt, " +
"    0                      AS cr_amt, " +
"    a.total_amount         AS amount, " +
"    a.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN a.cashbook_month<=3 " +
"      THEN (a.cashbook_year-1) " +
"        ||'-' " +
"        ||a.cashbook_year " +
"      WHEN a.cashbook_month>=4 " +
"      THEN a.cashbook_year " +
"        ||'-' " +
"        ||(a.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_inter_bank_trf_at_ho a " +
"  WHERE a.transfer_status  ='L' " +
"  AND a.cashbook_year      ="+txtCB_Year+
"  AND a.cashbook_month     ="+txtCB_Month+
"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
"    b.accounting_unit_id, " +
"    '' AS paid_to, " +
"    6  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
"    b.cashbook_year, " +
"    b.cashbook_month, " +
"    b.RECEIPT_DATE         AS datevalue, " +
"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
"    b.RECEIPT_NO           AS voucher_no, " +
"    'FR'                   AS document_type, " +
"    'CR'                   AS CR_DR_INDICATOR, " +
"    0                      AS dr_amt, " +
"    b.total_amount         AS cr_amt, " +
"    b.total_amount         AS amount, " +
"    b.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN b.cashbook_month<=3 " +
"      THEN (b.cashbook_year-1) " +
"        ||'-' " +
"        ||b.cashbook_year " +
"      WHEN b.cashbook_month>=4 " +
"      THEN b.cashbook_year " +
"        ||'-' " +
"        ||(b.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_fund_receipt_by_office b " +
"  WHERE b.receipt_status  <>'C' " +
"  AND b.cashbook_year      ="+txtCB_Year+
"  AND b.cashbook_month     ="+txtCB_Month+
"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
"    b.accounting_unit_id, " +
"    '' AS paid_to, " +
"    6  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
"    b.cashbook_year, " +
"    b.cashbook_month, " +
"    b.RECEIPT_DATE         AS datevalue, " +
"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
"    b.RECEIPT_NO           AS voucher_no, " +
"    'FR'                   AS document_type, " +
"    'DR'                   AS CR_DR_INDICATOR, " +
"    b.total_amount         AS dr_amt, " +
"    0                      AS cr_amt, " +
"    b.total_amount         AS amount, " +
"    b.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN b.cashbook_month<=3 " +
"      THEN (b.cashbook_year-1) " +
"        ||'-' " +
"        ||b.cashbook_year " +
"      WHEN b.cashbook_month>=4 " +
"      THEN b.cashbook_year " +
"        ||'-' " +
"        ||(b.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_fund_receipt_by_office b " +
"  WHERE b.receipt_status  <>'C' " +
"  AND b.cashbook_year      ="+txtCB_Year+
"  AND b.cashbook_month     ="+txtCB_Month+
"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
"    b.accounting_unit_id, " +
"    '' AS paid_to, " +
"    6  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
"    b.cashbook_year, " +
"    b.cashbook_month, " +
"    b.DATE_OF_TRANSFER     AS datevalue, " +
"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
"    b.VOUCHER_NO           AS voucher_no, " +
"    'FT'                   AS document_type, " +
"    'DR'                   AS CR_DR_INDICATOR, " +
"    b.total_amount         AS dr_amt, " +
"    0                      AS cr_amt, " +
"    b.total_amount         AS amount, " +
"    b.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN b.cashbook_month<=3 " +
"      THEN (b.cashbook_year-1) " +
"        ||'-' " +
"        ||b.cashbook_year " +
"      WHEN b.cashbook_month>=4 " +
"      THEN b.cashbook_year " +
"        ||'-' " +
"        ||(b.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_fund_trf_from_office b " +
"  WHERE b.transfer_status <>'C' " +
"  AND b.cashbook_year      ="+txtCB_Year+
"  AND b.cashbook_month     ="+txtCB_Month+
"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
"    b.accounting_unit_id, " +
"    '' AS paid_to, " +
"    6  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
"    b.cashbook_year, " +
"    b.cashbook_month, " +
"    b.DATE_OF_TRANSFER     AS datevalue, " +
"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
"    b.VOUCHER_NO           AS voucher_no, " +
"    'FT'                   AS document_type, " +
"    'CR'                   AS CR_DR_INDICATOR, " +
"    0                      AS dr_amt, " +
"    b.total_amount         AS cr_amt, " +
"    b.total_amount         AS amount, " +
"    b.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN b.cashbook_month<=3 " +
"      THEN (b.cashbook_year-1) " +
"        ||'-' " +
"        ||b.cashbook_year " +
"      WHEN b.cashbook_month>=4 " +
"      THEN b.cashbook_year " +
"        ||'-' " +
"        ||(b.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_fund_trf_from_office b " +
"  WHERE b.transfer_status <>'C' " +
"  AND b.cashbook_year      ="+txtCB_Year+
"  AND b.cashbook_month     ="+txtCB_Month+
"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
"    b.accounting_unit_id, " +
"    '' AS paid_to, " +
"    6  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
"    b.cashbook_year, " +
"    b.cashbook_month, " +
"    b.RECEIPT_DATE         AS datevalue, " +
"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
"    b.RECEIPT_NO           AS voucher_no, " +
"    'FT'                   AS document_type, " +
"    'CR'                   AS Cr_Dr_Indicator, " +
"    0                      AS dr_amt, " +
"    b.total_amount         AS cr_amt, " +
"    b.total_amount         AS amount, " +
"    b.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN b.cashbook_month<=3 " +
"      THEN (b.cashbook_year-1) " +
"        ||'-' " +
"        ||b.cashbook_year " +
"      WHEN b.cashbook_month>=4 " +
"      THEN b.cashbook_year " +
"        ||'-' " +
"        ||(b.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_fund_receipt_by_ho b " +
"  WHERE b.receipt_status  <>'C' " +
"  AND b.cashbook_year      ="+txtCB_Year+
"  AND b.cashbook_month     ="+txtCB_Month+
"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
"    b.accounting_unit_id, " +
"    '' AS paid_to, " +
"    6  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
"    b.cashbook_year, " +
"    b.cashbook_month, " +
"    b.RECEIPT_DATE         AS datevalue, " +
"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
"    B.Receipt_No           AS Voucher_No, " +
"    'FRH'                  AS Document_Type, " +
"    'DR'                   AS Cr_Dr_Indicator, " +
"    B.Total_Amount         AS Dr_Amt, " +
"    0                      AS cr_amt, " +
"    b.total_amount         AS amount, " +
"    b.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN b.cashbook_month<=3 " +
"      THEN (b.cashbook_year-1) " +
"        ||'-' " +
"        ||b.cashbook_year " +
"      WHEN b.cashbook_month>=4 " +
"      THEN b.cashbook_year " +
"        ||'-' " +
"        ||(b.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_fund_receipt_by_ho b " +
"  WHERE b.receipt_status  <>'C' " +
"  AND b.cashbook_year      ="+txtCB_Year+
"  AND b.cashbook_month     ="+txtCB_Month+
"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
"    a.accounting_unit_id, " +
"    '' AS paid_to, " +
"    6  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
"    a.cashbook_year, " +
"    a.cashbook_month, " +
"    b.date_of_transfer AS datevalue, " +
"    a.ACCOUNT_HEAD_CODE, " +
"    B.Voucher_No AS Voucher_No, " +
"    'FTH'        AS Document_Type, " +
"    'DR'         AS CR_DR_INDICATOR, " +
"    CASE " +
"      WHEN a.CR_DR_INDICATOR = 'DR' " +
"      THEN a.amount " +
"      ELSE 0 " +
"    END AS dr_amt, " +
"    CASE " +
"      WHEN a.CR_DR_INDICATOR = 'CR' " +
"      THEN a.amount " +
"      ELSE 0 " +
"    END      AS cr_amt, " +
"    a.amount AS amount, " +
"    a.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN a.cashbook_month<=3 " +
"      THEN (a.cashbook_year-1) " +
"        ||'-' " +
"        ||a.cashbook_year " +
"      WHEN a.cashbook_month>=4 " +
"      THEN a.cashbook_year " +
"        ||'-' " +
"        ||(a.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_fund_trf_from_ho_trn a, " +
"    fas_fund_trf_from_ho_master b " +
"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
"  AND a.cashbook_year            = b.cashbook_year " +
"  AND a.cashbook_month           = b.cashbook_month " +
"  AND a.voucher_no               = b.voucher_no " +
"  AND b.TRANSFER_STATUS         <>'C' " +
"  AND a.cashbook_year            ="+txtCB_Year+
"  AND a.cashbook_month           ="+txtCB_Month+
"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
"  ) " +
" UNION ALL " +
"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
"    a.accounting_unit_id, " +
"    '' AS paid_to, " +
"    6  AS sub_ledger_type_code, " +
"    0  AS SUB_LEDGER_CODE, " +
//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
"    a.cashbook_year, " +
"    a.cashbook_month, " +
"    b.date_of_transfer AS datevalue, " +
"    b.account_head_code , " +
"    B.Voucher_No AS Voucher_No, " +
"    'FTH'        AS Document_Type, " +
"    'CR'         AS CR_DR_INDICATOR, " +
"    0            AS dr_amt, " +
"    a.amount     AS cr_amt, " +
"    a.amount     AS amount, " +
"    a.particulars, " +
"    0 AS sup_no, " +
"    CASE " +
"      WHEN a.cashbook_month<=3 " +
"      THEN (a.cashbook_year-1) " +
"        ||'-' " +
"        ||a.cashbook_year " +
"      WHEN a.cashbook_month>=4 " +
"      THEN a.cashbook_year " +
"        ||'-' " +
"        ||(a.cashbook_year+1) " +
"    END fin_year " +
"  FROM fas_fund_trf_from_ho_trn a, " +
"    fas_fund_trf_from_ho_master b " +
"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
"  AND a.cashbook_year            = b.cashbook_year " +
"  AND a.cashbook_month           = b.cashbook_month " +
"  AND a.voucher_no               = b.voucher_no " +
"  AND b.TRANSFER_STATUS         <>'C' " +
"  AND a.cashbook_year            ="+txtCB_Year+
"  AND a.cashbook_month           ="+txtCB_Month+
"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
"  ))t " +
" INNER JOIN FAS_MST_ACCT_UNITS u " +
" ON t.accounting_unit_id=u.accounting_unit_id " +
" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
" ON t.account_head_code=c.account_head_code " +
" GROUP BY t.accounting_unit_id, " +
"  u.ACCOUNTING_UNIT_NAME, " +
"  t.account_head_code, " +
"  c.ACCOUNT_HEAD_DESC, " +
"  t.cashbook_year, " +
"  t.cashbook_month " +sub_qry;
}
        	
        	 if ((txtCB_Month==3) && (rptsel.equalsIgnoreCase("Supplement"))&&
        			headwise.equalsIgnoreCase("unitwise_Abstract"))
        	{
            	
        		 sql_Qry="SELECT 1 AS data_tb, " +
        	        		"  t.accounting_unit_id, " +
        	        		"  u.ACCOUNTING_UNIT_NAME, " +
        	        		"  t.account_head_code , " +
        	        		"  c.ACCOUNT_HEAD_DESC, " +
        	        		"  t.cashbook_year, " +
        	        		"  t.cashbook_month, " +
        	        		"  SUM(t.dr_amt) AS dr_amount, " +
        	        		"  SUM(t.cr_amt) AS cr_amount " +
        	        		"FROM ( " +
        	        		
"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
"when a.sub_ledger_type_code=5 then 'Offices'"+
"when a.sub_ledger_type_code=6 then'Bank'"+
"when a.sub_ledger_type_code=7 then'Employees'"+
"when a.sub_ledger_type_code=8 then '-'"+
"when a.sub_ledger_type_code=9 then 'Other Departments'"+
"when a.sub_ledger_type_code=10 then 'Project'"+
"when a.sub_ledger_type_code=11 then'Contractors'"+
"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
	 " else '-'  end AS sub_ledger_type_desc, " +	        	        		"    a.accounting_unit_id, " +
        	        		"    ' ' AS paid_to, " +
        	        		"    a.sub_ledger_type_code, " +
        	        		"    a.SUB_LEDGER_CODE, " +
        	        		"    a.cashbook_year, " +
        	        		"    a.cashbook_month, " +
        	        		"    b.voucher_date AS datevalue, " +
        	        		"    a.account_head_code , " +
        	        		"    b.voucher_no AS voucher_no , " +
        	        		"    'J'          AS document_type, " +
        	        		"    a.CR_DR_INDICATOR, " +
        	        		"    CASE " +
        	        		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
        	        		"      THEN a.amount " +
        	        		"      ELSE 0 " +
        	        		"    END AS dr_amt, " +
        	        		"    CASE " +
        	        		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
        	        		"      THEN a.amount " +
        	        		"      ELSE 0 " +
        	        		"    END      AS cr_amt, " +
        	        		"    a.amount AS amount, " +
        	        		"    a.particulars, " +
        	        		"    coalesce (b.supplement_no,0) AS sup_no, " +
        	        		"    CASE " +
        	        		"      WHEN a.cashbook_month<=3 " +
        	        		"      THEN (a.cashbook_year-1) " +
        	        		"        ||'-' " +
        	        		"        ||a.cashbook_year " +
        	        		"      WHEN a.cashbook_month>=4 " +
        	        		"      THEN a.cashbook_year " +
        	        		"        ||'-' " +
        	        		"        ||(a.cashbook_year+1) " +
        	        		"    END fin_year " +
        	        		"  FROM fas_journal_transaction a, " +
        	        		"    fas_journal_master b " +
        	        		"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
        	        		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
        	        		"  AND a.cashbook_year            = b.cashbook_year " +
        	        		"  AND a.cashbook_month           = b.cashbook_month " +
        	        		"  AND a.voucher_no               = b.voucher_no " +
        	        		"  AND b.journal_status          <>'C' " +
        	        		"  AND a.cashbook_year            ="+txtCB_Year+
        	        		"  AND a.cashbook_month           ="+txtCB_Month+
        	        		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
        	        		"  AND b.SUPPLEMENT_NO            ="+ sup +
        	        		"  ) " +
        	        		" )t " +
        	        		" INNER JOIN FAS_MST_ACCT_UNITS u " +
        	        		" ON t.accounting_unit_id=u.accounting_unit_id " +
        	        		" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
        	        		" ON t.account_head_code=c.account_head_code " +
        	        		" GROUP BY t.accounting_unit_id, " +
        	        		"  u.ACCOUNTING_UNIT_NAME, " +
        	        		"  t.account_head_code, " +
        	        		"  c.ACCOUNT_HEAD_DESC, " +
        	        		"  t.cashbook_year, " +
        	        		"  t.cashbook_month " +sub_qry;
        		 
        		 
            	
//                sql_Qry="SELECT 1 AS data_tb, " +
//        		"  t.accounting_unit_id, " +
//        		"  u.ACCOUNTING_UNIT_NAME, " +
//        		"  t.account_head_code , " +
//        		"  c.ACCOUNT_HEAD_DESC, " +
//        		"  t.cashbook_year, " +
//        		"  t.cashbook_month, " +
//        		"  SUM(t.dr_amt) AS dr_amount, " +
//        		"  SUM(t.cr_amt) AS cr_amount " +
//        		"FROM ( " +
//        		"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//        		"    a.accounting_unit_id, " +
//        		"    b.paid_to AS paid_to, " +
//        		"    a.sub_ledger_type_code, " +
//        		"    a.SUB_LEDGER_CODE, " +
//        		"    a.cashbook_year, " +
//        		"    a.cashbook_month, " +
//        		"    b.payment_date AS datevalue, " +
//        		"    a.ACCOUNT_HEAD_CODE, " +
//        		"    b.voucher_no AS voucher_no, " +
//        		"    'P'          AS document_type, " +
//        		"    a.CR_DR_INDICATOR, " +
//        		"    CASE " +
//        		"      WHEN a.cr_dr_indicator = 'DR' " +
//        		"      THEN a.amount " +
//        		"      ELSE 0 " +
//        		"    END AS dr_amt, " +
//        		"    CASE " +
//        		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//        		"      THEN a.amount " +
//        		"      ELSE 0 " +
//        		"    END      AS cr_amt, " +
//        		"    a.amount AS amount, " +
//        		"    a.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN a.cashbook_month<=3 " +
//        		"      THEN (a.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||a.cashbook_year " +
//        		"      WHEN a.cashbook_month>=4 " +
//        		"      THEN a.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(a.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_payment_transaction a, " +
//        		"    fas_payment_master b " +
//        		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//        		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//        		"  AND a.cashbook_year            = b.cashbook_year " +
//        		"  AND a.cashbook_month           = b.cashbook_month " +
//        		"  AND a.voucher_no               = b.voucher_no " +
//        		"  AND b.payment_status          <>'C' " +
//        		"  AND a.cashbook_year            ="+txtCB_Year+
//        		"  AND a.cashbook_month           ="+txtCB_Month+
//        		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		"UNION ALL " +
//        		"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//        		"    b.accounting_unit_id, " +
//        		"    b.paid_to AS paid_to, " +
//        		"    b.sub_ledger_type_code, " +
//        		"    b.sub_ledger_code, " +
//        		"    B.cashbook_year, " +
//        		"    B.cashbook_month, " +
//        		"    b.payment_date AS datevalue, " +
//        		"    b.Account_Head_Code, " +
//        		"    b.voucher_no AS voucher_no, " +
//        		"    'P'          AS Document_Type, " +
//        		"    'CR'         AS Cr_Dr_Indicator, " +
//        		"    CASE " +
//        		"      WHEN CR_DR_INDICATOR = 'DR' " +
//        		"      THEN B.TOTAL_AMOUNT " +
//        		"      ELSE 0 " +
//        		"    END AS dr_amt, " +
//        		"    CASE " +
//        		"      WHEN CR_DR_INDICATOR = 'CR' " +
//        		"      THEN B.TOTAL_AMOUNT " +
//        		"      ELSE 0 " +
//        		"    END            AS Cr_Amt, " +
//        		"    b.total_amount AS amount, " +
//        		"    b.remarks      AS particulars, " +
//        		"    0              AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN b.cashbook_month<=3 " +
//        		"      THEN (b.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||b.cashbook_year " +
//        		"      WHEN b.cashbook_month>=4 " +
//        		"      THEN b.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(b.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_payment_master b " +
//        		"  WHERE b.payment_status  <>'C' " +
//        		"  AND b.cashbook_year      = "+txtCB_Year+
//        		"  AND b.cashbook_month     ="+txtCB_Month+
//        		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		"UNION ALL " +
//        		"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//        		"    a.accounting_unit_id, " +
//        		"    b.received_from AS paid_to, " +
//        		"    a.sub_ledger_type_code, " +
//        		"    a.sub_ledger_code, " +
//        		"    a.cashbook_year, " +
//        		"    a.cashbook_month, " +
//        		"    b.receipt_date AS datevalue, " +
//        		"    a.ACCOUNT_HEAD_CODE, " +
//        		"    b.receipt_no AS voucher_no , " +
//        		"    'R'          AS document_type, " +
//        		"    a.CR_DR_INDICATOR, " +
//        		"    CASE " +
//        		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//        		"      THEN a.amount " +
//        		"      ELSE 0 " +
//        		"    END AS dr_amt, " +
//        		"    CASE " +
//        		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//        		"      THEN a.amount " +
//        		"      ELSE 0 " +
//        		"    END      AS cr_amt, " +
//        		"    a.amount AS amount, " +
//        		"    a.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN a.cashbook_month<=3 " +
//        		"      THEN (a.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||a.cashbook_year " +
//        		"      WHEN a.cashbook_month>=4 " +
//        		"      THEN a.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(a.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_receipt_transaction a, " +
//        		"    fas_receipt_master b " +
//        		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//        		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//        		"  AND a.cashbook_year            = b.cashbook_year " +
//        		"  AND a.cashbook_month           = b.cashbook_month " +
//        		"  AND a.receipt_no               = b.receipt_no " +
//        		"  AND b.receipt_status          <>'C' " +
//        		"  AND a.cashbook_year            ="+txtCB_Year+
//        		"  AND a.cashbook_month           ="+txtCB_Month+
//        		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		"UNION ALL " +
//        		"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//        		"    b.accounting_unit_id, " +
//        		"    b.received_from AS paid_to, " +
//        		"    b.sub_ledger_type_code, " +
//        		"    b.SUB_LEDGER_CODE, " +
//        		"    B.cashbook_year, " +
//        		"    b.cashbook_month, " +
//        		"    b.receipt_date AS datevalue, " +
//        		"    b.account_head_code , " +
//        		"    b.receipt_no AS voucher_no , " +
//        		"    'R'          AS document_type, " +
//        		"    b.CR_DR_INDICATOR, " +
//        		"    CASE " +
//        		"      WHEN b.CR_DR_INDICATOR = 'DR' " +
//        		"      THEN b.TOTAL_AMOUNT " +
//        		"      ELSE 0 " +
//        		"    END AS dr_amt, " +
//        		"    CASE " +
//        		"      WHEN b.CR_DR_INDICATOR = 'CR' " +
//        		"      THEN b.TOTAL_AMOUNT " +
//        		"      ELSE 0 " +
//        		"    END            AS cr_amt, " +
//        		"    B.Total_Amount AS Amount, " +
//        		"    b.remarks      AS particulars, " +
//        		"    0              AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN b.cashbook_month<=3 " +
//        		"      THEN (b.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||b.cashbook_year " +
//        		"      WHEN b.cashbook_month>=4 " +
//        		"      THEN b.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(b.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_receipt_master b " +
//        		"  WHERE b.receipt_status  <>'C' " +
//        		"  AND b.cashbook_year      ="+txtCB_Year+
//        		"  AND b.cashbook_month     ="+txtCB_Month+
//        		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		"UNION ALL " +
//        		"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//        		"    a.accounting_unit_id, " +
//        		"    ' ' AS paid_to, " +
//        		"    a.sub_ledger_type_code, " +
//        		"    a.SUB_LEDGER_CODE, " +
//        		"    a.cashbook_year, " +
//        		"    a.cashbook_month, " +
//        		"    b.voucher_date AS datevalue, " +
//        		"    a.account_head_code , " +
//        		"    b.voucher_no AS voucher_no , " +
//        		"    'J'          AS document_type, " +
//        		"    a.CR_DR_INDICATOR, " +
//        		"    CASE " +
//        		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//        		"      THEN a.amount " +
//        		"      ELSE 0 " +
//        		"    END AS dr_amt, " +
//        		"    CASE " +
//        		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//        		"      THEN a.amount " +
//        		"      ELSE 0 " +
//        		"    END      AS cr_amt, " +
//        		"    a.amount AS amount, " +
//        		"    a.particulars, " +
//        		"    NVL(b.supplement_no,0) AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN a.cashbook_month<=3 " +
//        		"      THEN (a.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||a.cashbook_year " +
//        		"      WHEN a.cashbook_month>=4 " +
//        		"      THEN a.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(a.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_journal_transaction a, " +
//        		"    fas_journal_master b " +
//        		"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
//        		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//        		"  AND a.cashbook_year            = b.cashbook_year " +
//        		"  AND a.cashbook_month           = b.cashbook_month " +
//        		"  AND a.voucher_no               = b.voucher_no " +
//        		"  AND b.journal_status          <>'C' " +
//        		"  AND a.cashbook_year            ="+txtCB_Year+
//        		"  AND a.cashbook_month           ="+txtCB_Month+
//        		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//        		"  AND b.SUPPLEMENT_NO            ="+ sup +
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT '' AS sub_ledger_type_desc, " +
//        		"    accounting_unit_id, " +
//        		"    '' AS Paid_To, " +
//        		"    0  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        		"    cashbook_year, " +
//        		"    cashbook_month, " +
//        		"    Date_Of_Transfer     AS Datevalue, " +
//        		"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//        		"    voucher_no           AS voucher_no, " +
//        		"    'IBT1'               AS document_type, " +
//        		"    cr_dr_indicator, " +
//        		"    CASE " +
//        		"      WHEN cr_dr_indicator = 'DR' " +
//        		"      THEN amount " +
//        		"      ELSE 0 " +
//        		"    END AS dr_amt, " +
//        		"    CASE " +
//        		"      WHEN cr_dr_indicator = 'CR' " +
//        		"      THEN amount " +
//        		"      ELSE 0 " +
//        		"    END AS cr_amt, " +
//        		"    amount, " +
//        		"    particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN cashbook_month<=3 " +
//        		"      THEN (cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||cashbook_year " +
//        		"      WHEN cashbook_month>=4 " +
//        		"      THEN cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM " +
//        		"    (SELECT a.accounting_unit_id, " +
//        		"      '' AS sub_ledger_type_desc, " +
//        		"      A.CR_ACCOUNT_HEAD_CODE, " +
//        		"      Date_Of_Transfer, " +
//        		"      '' AS paid_received, " +
//        		"      a.cashbook_year, " +
//        		"      a.cashbook_month, " +
//        		"      Date_Of_Transfer AS Datevalue, " +
//        		"      a.VOUCHER_NO     AS voucher_no, " +
//        		"      'IBT'            AS document_type, " +
//        		"      'CR'             AS cr_dr_indicator, " +
//        		"      total_amount     AS amount, " +
//        		"      particulars " +
//        		"    FROM fas_inter_bank_trf_at_ho a " +
//        		"    WHERE a.TRANSFER_STATUS <>'C' " +
//        		"    AND a.cashbook_year      ="+txtCB_Year+
//        		"    AND a.cashbook_month     ="+txtCB_Month+
//        		"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"    ) " +
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT '' AS sub_ledger_type_desc, " +
//        		"    a.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    0  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        		"    a.cashbook_year, " +
//        		"    a.cashbook_month, " +
//        		"    a.DATE_OF_TRANSFER     AS datevalue, " +
//        		"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//        		"    a.voucher_no           AS voucher_no, " +
//        		"    'IBT'                  AS document_type, " +
//        		"    'DR'                   AS CR_DR_INDICATOR, " +
//        		"    a.TOTAL_AMOUNT         AS dr_amt, " +
//        		"    0                      AS cr_amt, " +
//        		"    a.total_amount         AS amount, " +
//        		"    a.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN a.cashbook_month<=3 " +
//        		"      THEN (a.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||a.cashbook_year " +
//        		"      WHEN a.cashbook_month>=4 " +
//        		"      THEN a.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(a.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_inter_bank_trf_at_ho a " +
//        		"  WHERE a.transfer_status  ='L' " +
//        		"  AND a.cashbook_year      ="+txtCB_Year+
//        		"  AND a.cashbook_month     ="+txtCB_Month+
//        		"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//        		"    b.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    6  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        		//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//        		"    b.cashbook_year, " +
//        		"    b.cashbook_month, " +
//        		"    b.RECEIPT_DATE         AS datevalue, " +
//        		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//        		"    b.RECEIPT_NO           AS voucher_no, " +
//        		"    'FR'                   AS document_type, " +
//        		"    'CR'                   AS CR_DR_INDICATOR, " +
//        		"    0                      AS dr_amt, " +
//        		"    b.total_amount         AS cr_amt, " +
//        		"    b.total_amount         AS amount, " +
//        		"    b.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN b.cashbook_month<=3 " +
//        		"      THEN (b.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||b.cashbook_year " +
//        		"      WHEN b.cashbook_month>=4 " +
//        		"      THEN b.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(b.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_fund_receipt_by_office b " +
//        		"  WHERE b.receipt_status  <>'C' " +
//        		"  AND b.cashbook_year      ="+txtCB_Year+
//        		"  AND b.cashbook_month     ="+txtCB_Month+
//        		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//        		"    b.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    6  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        		"    b.cashbook_year, " +
//        		"    b.cashbook_month, " +
//        		"    b.RECEIPT_DATE         AS datevalue, " +
//        		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//        		"    b.RECEIPT_NO           AS voucher_no, " +
//        		"    'FR'                   AS document_type, " +
//        		"    'DR'                   AS CR_DR_INDICATOR, " +
//        		"    b.total_amount         AS dr_amt, " +
//        		"    0                      AS cr_amt, " +
//        		"    b.total_amount         AS amount, " +
//        		"    b.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN b.cashbook_month<=3 " +
//        		"      THEN (b.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||b.cashbook_year " +
//        		"      WHEN b.cashbook_month>=4 " +
//        		"      THEN b.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(b.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_fund_receipt_by_office b " +
//        		"  WHERE b.receipt_status  <>'C' " +
//        		"  AND b.cashbook_year      ="+txtCB_Year+
//        		"  AND b.cashbook_month     ="+txtCB_Month+
//        		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//        		"    b.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    6  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        		"    b.cashbook_year, " +
//        		"    b.cashbook_month, " +
//        		"    b.DATE_OF_TRANSFER     AS datevalue, " +
//        		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//        		"    b.VOUCHER_NO           AS voucher_no, " +
//        		"    'FT'                   AS document_type, " +
//        		"    'DR'                   AS CR_DR_INDICATOR, " +
//        		"    b.total_amount         AS dr_amt, " +
//        		"    0                      AS cr_amt, " +
//        		"    b.total_amount         AS amount, " +
//        		"    b.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN b.cashbook_month<=3 " +
//        		"      THEN (b.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||b.cashbook_year " +
//        		"      WHEN b.cashbook_month>=4 " +
//        		"      THEN b.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(b.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_fund_trf_from_office b " +
//        		"  WHERE b.transfer_status <>'C' " +
//        		"  AND b.cashbook_year      ="+txtCB_Year+
//        		"  AND b.cashbook_month     ="+txtCB_Month+
//        		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//        		"    b.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    6  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//        		"    b.cashbook_year, " +
//        		"    b.cashbook_month, " +
//        		"    b.DATE_OF_TRANSFER     AS datevalue, " +
//        		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//        		"    b.VOUCHER_NO           AS voucher_no, " +
//        		"    'FT'                   AS document_type, " +
//        		"    'CR'                   AS CR_DR_INDICATOR, " +
//        		"    0                      AS dr_amt, " +
//        		"    b.total_amount         AS cr_amt, " +
//        		"    b.total_amount         AS amount, " +
//        		"    b.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN b.cashbook_month<=3 " +
//        		"      THEN (b.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||b.cashbook_year " +
//        		"      WHEN b.cashbook_month>=4 " +
//        		"      THEN b.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(b.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_fund_trf_from_office b " +
//        		"  WHERE b.transfer_status <>'C' " +
//        		"  AND b.cashbook_year      ="+txtCB_Year+
//        		"  AND b.cashbook_month     ="+txtCB_Month+
//        		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//        		"    b.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    6  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//        		"    b.cashbook_year, " +
//        		"    b.cashbook_month, " +
//        		"    b.RECEIPT_DATE         AS datevalue, " +
//        		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//        		"    b.RECEIPT_NO           AS voucher_no, " +
//        		"    'FT'                   AS document_type, " +
//        		"    'CR'                   AS Cr_Dr_Indicator, " +
//        		"    0                      AS dr_amt, " +
//        		"    b.total_amount         AS cr_amt, " +
//        		"    b.total_amount         AS amount, " +
//        		"    b.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN b.cashbook_month<=3 " +
//        		"      THEN (b.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||b.cashbook_year " +
//        		"      WHEN b.cashbook_month>=4 " +
//        		"      THEN b.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(b.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_fund_receipt_by_ho b " +
//        		"  WHERE b.receipt_status  <>'C' " +
//        		"  AND b.cashbook_year      ="+txtCB_Year+
//        		"  AND b.cashbook_month     ="+txtCB_Month+
//        		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//        		"    b.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    6  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        		//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//        		"    b.cashbook_year, " +
//        		"    b.cashbook_month, " +
//        		"    b.RECEIPT_DATE         AS datevalue, " +
//        		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//        		"    B.Receipt_No           AS Voucher_No, " +
//        		"    'FRH'                  AS Document_Type, " +
//        		"    'DR'                   AS Cr_Dr_Indicator, " +
//        		"    B.Total_Amount         AS Dr_Amt, " +
//        		"    0                      AS cr_amt, " +
//        		"    b.total_amount         AS amount, " +
//        		"    b.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN b.cashbook_month<=3 " +
//        		"      THEN (b.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||b.cashbook_year " +
//        		"      WHEN b.cashbook_month>=4 " +
//        		"      THEN b.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(b.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_fund_receipt_by_ho b " +
//        		"  WHERE b.receipt_status  <>'C' " +
//        		"  AND b.cashbook_year      ="+txtCB_Year+
//        		"  AND b.cashbook_month     ="+txtCB_Month+
//        		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//        		"    a.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    6  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//        		"    a.cashbook_year, " +
//        		"    a.cashbook_month, " +
//        		"    b.date_of_transfer AS datevalue, " +
//        		"    a.ACCOUNT_HEAD_CODE, " +
//        		"    B.Voucher_No AS Voucher_No, " +
//        		"    'FTH'        AS Document_Type, " +
//        		"    'DR'         AS CR_DR_INDICATOR, " +
//        		"    CASE " +
//        		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//        		"      THEN a.amount " +
//        		"      ELSE 0 " +
//        		"    END AS dr_amt, " +
//        		"    CASE " +
//        		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//        		"      THEN a.amount " +
//        		"      ELSE 0 " +
//        		"    END      AS cr_amt, " +
//        		"    a.amount AS amount, " +
//        		"    a.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN a.cashbook_month<=3 " +
//        		"      THEN (a.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||a.cashbook_year " +
//        		"      WHEN a.cashbook_month>=4 " +
//        		"      THEN a.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(a.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_fund_trf_from_ho_trn a, " +
//        		"    fas_fund_trf_from_ho_master b " +
//        		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//        		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//        		"  AND a.cashbook_year            = b.cashbook_year " +
//        		"  AND a.cashbook_month           = b.cashbook_month " +
//        		"  AND a.voucher_no               = b.voucher_no " +
//        		"  AND b.TRANSFER_STATUS         <>'C' " +
//        		"  AND a.cashbook_year            ="+txtCB_Year+
//        		"  AND a.cashbook_month           ="+txtCB_Month+
//        		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//        		"  ) " +
//        		" UNION ALL " +
//        		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//        		"    a.accounting_unit_id, " +
//        		"    '' AS paid_to, " +
//        		"    6  AS sub_ledger_type_code, " +
//        		"    0  AS SUB_LEDGER_CODE, " +
//        	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//        		"    a.cashbook_year, " +
//        		"    a.cashbook_month, " +
//        		"    b.date_of_transfer AS datevalue, " +
//        		"    b.account_head_code , " +
//        		"    B.Voucher_No AS Voucher_No, " +
//        		"    'FTH'        AS Document_Type, " +
//        		"    'CR'         AS CR_DR_INDICATOR, " +
//        		"    0            AS dr_amt, " +
//        		"    a.amount     AS cr_amt, " +
//        		"    a.amount     AS amount, " +
//        		"    a.particulars, " +
//        		"    0 AS sup_no, " +
//        		"    CASE " +
//        		"      WHEN a.cashbook_month<=3 " +
//        		"      THEN (a.cashbook_year-1) " +
//        		"        ||'-' " +
//        		"        ||a.cashbook_year " +
//        		"      WHEN a.cashbook_month>=4 " +
//        		"      THEN a.cashbook_year " +
//        		"        ||'-' " +
//        		"        ||(a.cashbook_year+1) " +
//        		"    END fin_year " +
//        		"  FROM fas_fund_trf_from_ho_trn a, " +
//        		"    fas_fund_trf_from_ho_master b " +
//        		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//        		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//        		"  AND a.cashbook_year            = b.cashbook_year " +
//        		"  AND a.cashbook_month           = b.cashbook_month " +
//        		"  AND a.voucher_no               = b.voucher_no " +
//        		"  AND b.TRANSFER_STATUS         <>'C' " +
//        		"  AND a.cashbook_year            ="+txtCB_Year+
//        		"  AND a.cashbook_month           ="+txtCB_Month+
//        		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//        		"  ))t " +
//        		" INNER JOIN FAS_MST_ACCT_UNITS u " +
//        		" ON t.accounting_unit_id=u.accounting_unit_id " +
//        		" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
//        		" ON t.account_head_code=c.account_head_code " +
//        		" GROUP BY t.accounting_unit_id, " +
//        		"  u.ACCOUNTING_UNIT_NAME, " +
//        		"  t.account_head_code, " +
//        		"  c.ACCOUNT_HEAD_DESC, " +
//        		"  t.cashbook_year, " +
//        		"  t.cashbook_month " +sub_qry;
        		
        	}
            }
            else if (cmbAcc_UnitCode==0)
            {

            	 
            	
            	
            	if(!headwise.equalsIgnoreCase("unitwise_Abstract"))
                	{    	
                	
            sql_Qry="SELECT 1 AS data_tb, " +
    		"  t.accounting_unit_id, " +
    		"  u.ACCOUNTING_UNIT_NAME, " +
    		"  t.account_head_code , " +
    		"  c.ACCOUNT_HEAD_DESC, " +
    		"  t.cashbook_year, " +
    		"  t.cashbook_month, " +
    		"  SUM(t.dr_amt) AS dr_amount, " +
    		"  SUM(t.cr_amt) AS cr_amount " +
    		"FROM ( " +
    		"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
    		"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
    		"when a.sub_ledger_type_code=5 then 'Offices'"+
    		"when a.sub_ledger_type_code=6 then'Bank'"+
    		"when a.sub_ledger_type_code=7 then'Employees'"+
    		"when a.sub_ledger_type_code=8 then '-'"+
    		"when a.sub_ledger_type_code=9 then 'Other Departments'"+
    		"when a.sub_ledger_type_code=10 then 'Project'"+
    		"when a.sub_ledger_type_code=11 then'Contractors'"+
    		"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
    		"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
    			 " else '-'  end AS sub_ledger_type_desc, " +	    		"    a.accounting_unit_id, " +
    		"    b.paid_to AS paid_to, " +
    		"    a.sub_ledger_type_code, " +
    		"    a.SUB_LEDGER_CODE, " +
    		"    a.cashbook_year, " +
    		"    a.cashbook_month, " +
    		"    b.payment_date AS datevalue, " +
    		"    a.ACCOUNT_HEAD_CODE, " +
    		"    b.voucher_no AS voucher_no, " +
    		"    'P'          AS document_type, " +
    		"    a.CR_DR_INDICATOR, " +
    		"    CASE " +
    		"      WHEN a.cr_dr_indicator = 'DR' " +
    		"      THEN a.amount " +
    		"      ELSE 0 " +
    		"    END AS dr_amt, " +
    		"    CASE " +
    		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
    		"      THEN a.amount " +
    		"      ELSE 0 " +
    		"    END      AS cr_amt, " +
    		"    a.amount AS amount, " +
    		"    a.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN a.cashbook_month<=3 " +
    		"      THEN (a.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||a.cashbook_year " +
    		"      WHEN a.cashbook_month>=4 " +
    		"      THEN a.cashbook_year " +
    		"        ||'-' " +
    		"        ||(a.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_payment_transaction a, " +
    		"    fas_payment_master b " +
    		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
    		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
    		"  AND a.cashbook_year            = b.cashbook_year " +
    		"  AND a.cashbook_month           = b.cashbook_month " +
    		"  AND a.voucher_no               = b.voucher_no " +
    		"  AND b.payment_status          <>'C' " +
    		"  AND a.cashbook_year            ="+txtCB_Year+
    		"  AND a.cashbook_month           ="+txtCB_Month+
//    		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
    		"  ) " +
    		"UNION ALL " +
    		"  (SELECT case when b.sub_ledger_type_code=1 then'Supplier' when b.sub_ledger_type_code= 2 then 'Firms' when b.sub_ledger_type_code=10 then 'Assets'"+
    		"when b.sub_ledger_type_code=4 then 'Cheque Books'"+
    		"when b.sub_ledger_type_code=5 then 'Offices'"+
    		"when b.sub_ledger_type_code=6 then'Bank'"+
    		"when b.sub_ledger_type_code=7 then'Employees'"+
    		"when b.sub_ledger_type_code=8 then '-'"+
    		"when b.sub_ledger_type_code=9 then 'Other Departments'"+
    		"when b.sub_ledger_type_code=10 then 'Project'"+
    		"when b.sub_ledger_type_code=11 then'Contractors'"+
    		"when b.sub_ledger_type_code=12 then 'Scheme Owner'"+
    		"when b.sub_ledger_type_code=13 then 'Beneficiary'"+
    			 " else '-'  end AS sub_ledger_type_desc, " +	    		"    b.accounting_unit_id, " +
    		"    b.paid_to AS paid_to, " +
    		"    b.sub_ledger_type_code, " +
    		"    b.sub_ledger_code, " +
    		"    B.cashbook_year, " +
    		"    B.cashbook_month, " +
    		"    b.payment_date AS datevalue, " +
    		"    b.Account_Head_Code, " +
    		"    b.voucher_no AS voucher_no, " +
    		"    'P'          AS Document_Type, " +
    		"    'CR'         AS Cr_Dr_Indicator, " +
    		"    CASE " +
    		"      WHEN CR_DR_INDICATOR = 'DR' " +
    		"      THEN B.TOTAL_AMOUNT " +
    		"      ELSE 0 " +
    		"    END AS dr_amt, " +
    		"    CASE " +
    		"      WHEN CR_DR_INDICATOR = 'CR' " +
    		"      THEN B.TOTAL_AMOUNT " +
    		"      ELSE 0 " +
    		"    END            AS Cr_Amt, " +
    		"    b.total_amount AS amount, " +
    		"    b.remarks      AS particulars, " +
    		"    0              AS sup_no, " +
    		"    CASE " +
    		"      WHEN b.cashbook_month<=3 " +
    		"      THEN (b.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||b.cashbook_year " +
    		"      WHEN b.cashbook_month>=4 " +
    		"      THEN b.cashbook_year " +
    		"        ||'-' " +
    		"        ||(b.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_payment_master b " +
    		"  WHERE b.payment_status  <>'C' " +
    		"  AND b.cashbook_year      = "+txtCB_Year+
    		"  AND b.cashbook_month     ="+txtCB_Month+
//    		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		"UNION ALL " +
    		"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
    		"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
    		"when a.sub_ledger_type_code=5 then 'Offices'"+
    		"when a.sub_ledger_type_code=6 then'Bank'"+
    		"when a.sub_ledger_type_code=7 then'Employees'"+
    		"when a.sub_ledger_type_code=8 then '-'"+
    		"when a.sub_ledger_type_code=9 then 'Other Departments'"+
    		"when a.sub_ledger_type_code=10 then 'Project'"+
    		"when a.sub_ledger_type_code=11 then'Contractors'"+
    		"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
    		"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
    			 " else '-'  end AS sub_ledger_type_desc, " +	    		"    a.accounting_unit_id, " +
    		"    b.received_from AS paid_to, " +
    		"    a.sub_ledger_type_code, " +
    		"    a.sub_ledger_code, " +
    		"    a.cashbook_year, " +
    		"    a.cashbook_month, " +
    		"    b.receipt_date AS datevalue, " +
    		"    a.ACCOUNT_HEAD_CODE, " +
    		"    b.receipt_no AS voucher_no , " +
    		"    'R'          AS document_type, " +
    		"    a.CR_DR_INDICATOR, " +
    		"    CASE " +
    		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
    		"      THEN a.amount " +
    		"      ELSE 0 " +
    		"    END AS dr_amt, " +
    		"    CASE " +
    		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
    		"      THEN a.amount " +
    		"      ELSE 0 " +
    		"    END      AS cr_amt, " +
    		"    a.amount AS amount, " +
    		"    a.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN a.cashbook_month<=3 " +
    		"      THEN (a.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||a.cashbook_year " +
    		"      WHEN a.cashbook_month>=4 " +
    		"      THEN a.cashbook_year " +
    		"        ||'-' " +
    		"        ||(a.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_receipt_transaction a, " +
    		"    fas_receipt_master b " +
    		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
    		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
    		"  AND a.cashbook_year            = b.cashbook_year " +
    		"  AND a.cashbook_month           = b.cashbook_month " +
    		"  AND a.receipt_no               = b.receipt_no " +
    		"  AND b.receipt_status          <>'C' " +
    		"  AND a.cashbook_year            ="+txtCB_Year+
    		"  AND a.cashbook_month           ="+txtCB_Month+
//    		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
    		"  ) " +
    		"UNION ALL " +
    		"  (SELECT case when b.sub_ledger_type_code=1 then'Supplier' when b.sub_ledger_type_code= 2 then 'Firms' when b.sub_ledger_type_code=10 then 'Assets'"+
    		"when b.sub_ledger_type_code=4 then 'Cheque Books'"+
    		"when b.sub_ledger_type_code=5 then 'Offices'"+
    		"when b.sub_ledger_type_code=6 then'Bank'"+
    		"when b.sub_ledger_type_code=7 then'Employees'"+
    		"when b.sub_ledger_type_code=8 then '-'"+
    		"when b.sub_ledger_type_code=9 then 'Other Departments'"+
    		"when b.sub_ledger_type_code=10 then 'Project'"+
    		"when b.sub_ledger_type_code=11 then'Contractors'"+
    		"when b.sub_ledger_type_code=12 then 'Scheme Owner'"+
    		"when b.sub_ledger_type_code=13 then 'Beneficiary'"+
    			 " else '-'  end AS sub_ledger_type_desc, " +	    		"    b.accounting_unit_id, " +
    		"    b.received_from AS paid_to, " +
    		"    b.sub_ledger_type_code, " +
    		"    b.SUB_LEDGER_CODE, " +
    		"    B.cashbook_year, " +
    		"    b.cashbook_month, " +
    		"    b.receipt_date AS datevalue, " +
    		"    b.account_head_code , " +
    		"    b.receipt_no AS voucher_no , " +
    		"    'R'          AS document_type, " +
    		"    b.CR_DR_INDICATOR, " +
    		"    CASE " +
    		"      WHEN b.CR_DR_INDICATOR = 'DR' " +
    		"      THEN b.TOTAL_AMOUNT " +
    		"      ELSE 0 " +
    		"    END AS dr_amt, " +
    		"    CASE " +
    		"      WHEN b.CR_DR_INDICATOR = 'CR' " +
    		"      THEN b.TOTAL_AMOUNT " +
    		"      ELSE 0 " +
    		"    END            AS cr_amt, " +
    		"    B.Total_Amount AS Amount, " +
    		"    b.remarks      AS particulars, " +
    		"    0              AS sup_no, " +
    		"    CASE " +
    		"      WHEN b.cashbook_month<=3 " +
    		"      THEN (b.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||b.cashbook_year " +
    		"      WHEN b.cashbook_month>=4 " +
    		"      THEN b.cashbook_year " +
    		"        ||'-' " +
    		"        ||(b.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_receipt_master b " +
    		"  WHERE b.receipt_status  <>'C' " +
    		"  AND b.cashbook_year      ="+txtCB_Year+
    		"  AND b.cashbook_month     ="+txtCB_Month+
//    		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		"UNION ALL " +
    		"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
    		"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
    		"when a.sub_ledger_type_code=5 then 'Offices'"+
    		"when a.sub_ledger_type_code=6 then'Bank'"+
    		"when a.sub_ledger_type_code=7 then'Employees'"+
    		"when a.sub_ledger_type_code=8 then '-'"+
    		"when a.sub_ledger_type_code=9 then 'Other Departments'"+
    		"when a.sub_ledger_type_code=10 then 'Project'"+
    		"when a.sub_ledger_type_code=11 then'Contractors'"+
    		"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
    		"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
    			 " else '-'  end AS sub_ledger_type_desc, " +	    		"    a.accounting_unit_id, " +
    		"    ' ' AS paid_to, " +
    		"    a.sub_ledger_type_code, " +
    		"    a.SUB_LEDGER_CODE, " +
    		"    a.cashbook_year, " +
    		"    a.cashbook_month, " +
    		"    b.voucher_date AS datevalue, " +
    		"    a.account_head_code , " +
    		"    b.voucher_no AS voucher_no , " +
    		"    'J'          AS document_type, " +
    		"    a.CR_DR_INDICATOR, " +
    		"    CASE " +
    		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
    		"      THEN a.amount " +
    		"      ELSE 0 " +
    		"    END AS dr_amt, " +
    		"    CASE " +
    		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
    		"      THEN a.amount " +
    		"      ELSE 0 " +
    		"    END      AS cr_amt, " +
    		"    a.amount AS amount, " +
    		"    a.particulars, " +
    		"    coalesce (b.supplement_no,0) AS sup_no, " +
    		"    CASE " +
    		"      WHEN a.cashbook_month<=3 " +
    		"      THEN (a.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||a.cashbook_year " +
    		"      WHEN a.cashbook_month>=4 " +
    		"      THEN a.cashbook_year " +
    		"        ||'-' " +
    		"        ||(a.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_journal_transaction a, " +
    		"    fas_journal_master b " +
    		"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
    		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
    		"  AND a.cashbook_year            = b.cashbook_year " +
    		"  AND a.cashbook_month           = b.cashbook_month " +
    		"  AND a.voucher_no               = b.voucher_no " +
    		"  AND b.journal_status          <>'C' " +
    		"  AND a.cashbook_year            ="+txtCB_Year+
    		"  AND a.cashbook_month           ="+txtCB_Month+
//    		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT '' AS sub_ledger_type_desc, " +
    		"    accounting_unit_id, " +
    		"    '' AS Paid_To, " +
    		"    0  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    		"    cashbook_year, " +
    		"    cashbook_month, " +
    		"    Date_Of_Transfer     AS Datevalue, " +
    		"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
    		"    voucher_no           AS voucher_no, " +
    		"    'IBT1'               AS document_type, " +
    		"    cr_dr_indicator, " +
    		"    CASE " +
    		"      WHEN cr_dr_indicator = 'DR' " +
    		"      THEN amount " +
    		"      ELSE 0 " +
    		"    END AS dr_amt, " +
    		"    CASE " +
    		"      WHEN cr_dr_indicator = 'CR' " +
    		"      THEN amount " +
    		"      ELSE 0 " +
    		"    END AS cr_amt, " +
    		"    amount, " +
    		"    particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN cashbook_month<=3 " +
    		"      THEN (cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||cashbook_year " +
    		"      WHEN cashbook_month>=4 " +
    		"      THEN cashbook_year " +
    		"        ||'-' " +
    		"        ||(cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM " +
    		"    (SELECT a.accounting_unit_id, " +
    		"      '' AS sub_ledger_type_desc, " +
    		"      A.CR_ACCOUNT_HEAD_CODE, " +
    		"      Date_Of_Transfer, " +
    		"      '' AS paid_received, " +
    		"      a.cashbook_year, " +
    		"      a.cashbook_month, " +
    		"      Date_Of_Transfer AS Datevalue, " +
    		"      a.VOUCHER_NO     AS voucher_no, " +
    		"      'IBT'            AS document_type, " +
    		"      'CR'             AS cr_dr_indicator, " +
    		"      total_amount     AS amount, " +
    		"      particulars " +
    		"    FROM fas_inter_bank_trf_at_ho a " +
    		"    WHERE a.TRANSFER_STATUS <>'C' " +
    		"    AND a.cashbook_year      ="+txtCB_Year+
    		"    AND a.cashbook_month     ="+txtCB_Month+
//    		"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
    		"    )s " +
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT '' AS sub_ledger_type_desc, " +
    		"    a.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    0  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    		"    a.cashbook_year, " +
    		"    a.cashbook_month, " +
    		"    a.DATE_OF_TRANSFER     AS datevalue, " +
    		"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
    		"    a.voucher_no           AS voucher_no, " +
    		"    'IBT'                  AS document_type, " +
    		"    'DR'                   AS CR_DR_INDICATOR, " +
    		"    a.TOTAL_AMOUNT         AS dr_amt, " +
    		"    0                      AS cr_amt, " +
    		"    a.total_amount         AS amount, " +
    		"    a.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN a.cashbook_month<=3 " +
    		"      THEN (a.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||a.cashbook_year " +
    		"      WHEN a.cashbook_month>=4 " +
    		"      THEN a.cashbook_year " +
    		"        ||'-' " +
    		"        ||(a.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_inter_bank_trf_at_ho a " +
    		"  WHERE a.transfer_status  ='L' " +
    		"  AND a.cashbook_year      ="+txtCB_Year+
    		"  AND a.cashbook_month     ="+txtCB_Month+
//    		"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
    		"    b.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    6  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    		//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
    		"    b.cashbook_year, " +
    		"    b.cashbook_month, " +
    		"    b.RECEIPT_DATE         AS datevalue, " +
    		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
    		"    b.RECEIPT_NO           AS voucher_no, " +
    		"    'FR'                   AS document_type, " +
    		"    'CR'                   AS CR_DR_INDICATOR, " +
    		"    0                      AS dr_amt, " +
    		"    b.total_amount         AS cr_amt, " +
    		"    b.total_amount         AS amount, " +
    		"    b.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN b.cashbook_month<=3 " +
    		"      THEN (b.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||b.cashbook_year " +
    		"      WHEN b.cashbook_month>=4 " +
    		"      THEN b.cashbook_year " +
    		"        ||'-' " +
    		"        ||(b.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_fund_receipt_by_office b " +
    		"  WHERE b.receipt_status  <>'C' " +
    		"  AND b.cashbook_year      ="+txtCB_Year+
    		"  AND b.cashbook_month     ="+txtCB_Month+
//    		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
    		"    b.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    6  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    		"    b.cashbook_year, " +
    		"    b.cashbook_month, " +
    		"    b.RECEIPT_DATE         AS datevalue, " +
    		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
    		"    b.RECEIPT_NO           AS voucher_no, " +
    		"    'FR'                   AS document_type, " +
    		"    'DR'                   AS CR_DR_INDICATOR, " +
    		"    b.total_amount         AS dr_amt, " +
    		"    0                      AS cr_amt, " +
    		"    b.total_amount         AS amount, " +
    		"    b.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN b.cashbook_month<=3 " +
    		"      THEN (b.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||b.cashbook_year " +
    		"      WHEN b.cashbook_month>=4 " +
    		"      THEN b.cashbook_year " +
    		"        ||'-' " +
    		"        ||(b.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_fund_receipt_by_office b " +
    		"  WHERE b.receipt_status  <>'C' " +
    		"  AND b.cashbook_year      ="+txtCB_Year+
    		"  AND b.cashbook_month     ="+txtCB_Month+
//    		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
    		"    b.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    6  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    		"    b.cashbook_year, " +
    		"    b.cashbook_month, " +
    		"    b.DATE_OF_TRANSFER     AS datevalue, " +
    		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
    		"    b.VOUCHER_NO           AS voucher_no, " +
    		"    'FT'                   AS document_type, " +
    		"    'DR'                   AS CR_DR_INDICATOR, " +
    		"    b.total_amount         AS dr_amt, " +
    		"    0                      AS cr_amt, " +
    		"    b.total_amount         AS amount, " +
    		"    b.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN b.cashbook_month<=3 " +
    		"      THEN (b.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||b.cashbook_year " +
    		"      WHEN b.cashbook_month>=4 " +
    		"      THEN b.cashbook_year " +
    		"        ||'-' " +
    		"        ||(b.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_fund_trf_from_office b " +
    		"  WHERE b.transfer_status <>'C' " +
    		"  AND b.cashbook_year      ="+txtCB_Year+
    		"  AND b.cashbook_month     ="+txtCB_Month+
//    		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
    		"    b.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    6  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
    		"    b.cashbook_year, " +
    		"    b.cashbook_month, " +
    		"    b.DATE_OF_TRANSFER     AS datevalue, " +
    		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
    		"    b.VOUCHER_NO           AS voucher_no, " +
    		"    'FT'                   AS document_type, " +
    		"    'CR'                   AS CR_DR_INDICATOR, " +
    		"    0                      AS dr_amt, " +
    		"    b.total_amount         AS cr_amt, " +
    		"    b.total_amount         AS amount, " +
    		"    b.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN b.cashbook_month<=3 " +
    		"      THEN (b.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||b.cashbook_year " +
    		"      WHEN b.cashbook_month>=4 " +
    		"      THEN b.cashbook_year " +
    		"        ||'-' " +
    		"        ||(b.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_fund_trf_from_office b " +
    		"  WHERE b.transfer_status <>'C' " +
    		"  AND b.cashbook_year      ="+txtCB_Year+
    		"  AND b.cashbook_month     ="+txtCB_Month+
//    		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
    		"    b.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    6  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
    		"    b.cashbook_year, " +
    		"    b.cashbook_month, " +
    		"    b.RECEIPT_DATE         AS datevalue, " +
    		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
    		"    b.RECEIPT_NO           AS voucher_no, " +
    		"    'FT'                   AS document_type, " +
    		"    'CR'                   AS Cr_Dr_Indicator, " +
    		"    0                      AS dr_amt, " +
    		"    b.total_amount         AS cr_amt, " +
    		"    b.total_amount         AS amount, " +
    		"    b.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN b.cashbook_month<=3 " +
    		"      THEN (b.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||b.cashbook_year " +
    		"      WHEN b.cashbook_month>=4 " +
    		"      THEN b.cashbook_year " +
    		"        ||'-' " +
    		"        ||(b.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_fund_receipt_by_ho b " +
    		"  WHERE b.receipt_status  <>'C' " +
    		"  AND b.cashbook_year      ="+txtCB_Year+
    		"  AND b.cashbook_month     ="+txtCB_Month+
//    		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
    		"    b.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    6  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    		//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
    		"    b.cashbook_year, " +
    		"    b.cashbook_month, " +
    		"    b.RECEIPT_DATE         AS datevalue, " +
    		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
    		"    B.Receipt_No           AS Voucher_No, " +
    		"    'FRH'                  AS Document_Type, " +
    		"    'DR'                   AS Cr_Dr_Indicator, " +
    		"    B.Total_Amount         AS Dr_Amt, " +
    		"    0                      AS cr_amt, " +
    		"    b.total_amount         AS amount, " +
    		"    b.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN b.cashbook_month<=3 " +
    		"      THEN (b.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||b.cashbook_year " +
    		"      WHEN b.cashbook_month>=4 " +
    		"      THEN b.cashbook_year " +
    		"        ||'-' " +
    		"        ||(b.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_fund_receipt_by_ho b " +
    		"  WHERE b.receipt_status  <>'C' " +
    		"  AND b.cashbook_year      ="+txtCB_Year+
    		"  AND b.cashbook_month     ="+txtCB_Month+
//    		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
    		"    a.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    6  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
    		"    a.cashbook_year, " +
    		"    a.cashbook_month, " +
    		"    b.date_of_transfer AS datevalue, " +
    		"    a.ACCOUNT_HEAD_CODE, " +
    		"    B.Voucher_No AS Voucher_No, " +
    		"    'FTH'        AS Document_Type, " +
    		"    'DR'         AS CR_DR_INDICATOR, " +
    		"    CASE " +
    		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
    		"      THEN a.amount " +
    		"      ELSE 0 " +
    		"    END AS dr_amt, " +
    		"    CASE " +
    		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
    		"      THEN a.amount " +
    		"      ELSE 0 " +
    		"    END      AS cr_amt, " +
    		"    a.amount AS amount, " +
    		"    a.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN a.cashbook_month<=3 " +
    		"      THEN (a.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||a.cashbook_year " +
    		"      WHEN a.cashbook_month>=4 " +
    		"      THEN a.cashbook_year " +
    		"        ||'-' " +
    		"        ||(a.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_fund_trf_from_ho_trn a, " +
    		"    fas_fund_trf_from_ho_master b " +
    		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
    		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
    		"  AND a.cashbook_year            = b.cashbook_year " +
    		"  AND a.cashbook_month           = b.cashbook_month " +
    		"  AND a.voucher_no               = b.voucher_no " +
    		"  AND b.TRANSFER_STATUS         <>'C' " +
    		"  AND a.cashbook_year            ="+txtCB_Year+
    		"  AND a.cashbook_month           ="+txtCB_Month+
//    		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
    		"  ) " +
    		" UNION ALL " +
    		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
    		"    a.accounting_unit_id, " +
    		"    '' AS paid_to, " +
    		"    6  AS sub_ledger_type_code, " +
    		"    0  AS SUB_LEDGER_CODE, " +
    	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
    		"    a.cashbook_year, " +
    		"    a.cashbook_month, " +
    		"    b.date_of_transfer AS datevalue, " +
    		"    b.account_head_code , " +
    		"    B.Voucher_No AS Voucher_No, " +
    		"    'FTH'        AS Document_Type, " +
    		"    'CR'         AS CR_DR_INDICATOR, " +
    		"    0            AS dr_amt, " +
    		"    a.amount     AS cr_amt, " +
    		"    a.amount     AS amount, " +
    		"    a.particulars, " +
    		"    0 AS sup_no, " +
    		"    CASE " +
    		"      WHEN a.cashbook_month<=3 " +
    		"      THEN (a.cashbook_year-1) " +
    		"        ||'-' " +
    		"        ||a.cashbook_year " +
    		"      WHEN a.cashbook_month>=4 " +
    		"      THEN a.cashbook_year " +
    		"        ||'-' " +
    		"        ||(a.cashbook_year+1) " +
    		"    END fin_year " +
    		"  FROM fas_fund_trf_from_ho_trn a, " +
    		"    fas_fund_trf_from_ho_master b " +
    		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
    		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
    		"  AND a.cashbook_year            = b.cashbook_year " +
    		"  AND a.cashbook_month           = b.cashbook_month " +
    		"  AND a.voucher_no               = b.voucher_no " +
    		"  AND b.TRANSFER_STATUS         <>'C' " +
    		"  AND a.cashbook_year            ="+txtCB_Year+
    		"  AND a.cashbook_month           ="+txtCB_Month+
//    		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
    		"  ))t " +
    		" INNER JOIN FAS_MST_ACCT_UNITS u " +
    		" ON t.accounting_unit_id=u.accounting_unit_id " +
    		" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
    		" ON t.account_head_code=c.account_head_code " +
    		" GROUP BY t.accounting_unit_id, " +
    		"  u.ACCOUNTING_UNIT_NAME, " +
    		"  t.account_head_code, " +
    		"  c.ACCOUNT_HEAD_DESC, " +
    		"  t.cashbook_year, " +
    		"  t.cashbook_month " +sub_qry;
    	}
       if ((txtCB_Month==3) && (rptsel.equalsIgnoreCase("Regular"))&&
    			headwise.equalsIgnoreCase("unitwise_Abstract"))     	
       {    	
       	
   sql_Qry="SELECT 1 AS data_tb, " +
	"  t.accounting_unit_id, " +
	"  u.ACCOUNTING_UNIT_NAME, " +
	"  t.account_head_code , " +
	"  c.ACCOUNT_HEAD_DESC, " +
	"  t.cashbook_year, " +
	"  t.cashbook_month, " +
	"  SUM(t.dr_amt) AS dr_amount, " +
	"  SUM(t.cr_amt) AS cr_amount " +
	"FROM ( " +
	"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
	"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
	"when a.sub_ledger_type_code=5 then 'Offices'"+
	"when a.sub_ledger_type_code=6 then'Bank'"+
	"when a.sub_ledger_type_code=7 then'Employees'"+
	"when a.sub_ledger_type_code=8 then '-'"+
	"when a.sub_ledger_type_code=9 then 'Other Departments'"+
	"when a.sub_ledger_type_code=10 then 'Project'"+
	"when a.sub_ledger_type_code=11 then'Contractors'"+
	"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
	"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
		 " else '-'  end AS sub_ledger_type_desc, " +	
		  "    a.accounting_unit_id, " +
	"    b.paid_to AS paid_to, " +
	"    a.sub_ledger_type_code, " +
	"    a.SUB_LEDGER_CODE, " +
	"    a.cashbook_year, " +
	"    a.cashbook_month, " +
	"    b.payment_date AS datevalue, " +
	"    a.ACCOUNT_HEAD_CODE, " +
	"    b.voucher_no AS voucher_no, " +
	"    'P'          AS document_type, " +
	"    a.CR_DR_INDICATOR, " +
	"    CASE " +
	"      WHEN a.cr_dr_indicator = 'DR' " +
	"      THEN a.amount " +
	"      ELSE 0 " +
	"    END AS dr_amt, " +
	"    CASE " +
	"      WHEN a.CR_DR_INDICATOR = 'CR' " +
	"      THEN a.amount " +
	"      ELSE 0 " +
	"    END      AS cr_amt, " +
	"    a.amount AS amount, " +
	"    a.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN a.cashbook_month<=3 " +
	"      THEN (a.cashbook_year-1) " +
	"        ||'-' " +
	"        ||a.cashbook_year " +
	"      WHEN a.cashbook_month>=4 " +
	"      THEN a.cashbook_year " +
	"        ||'-' " +
	"        ||(a.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_payment_transaction a, " +
	"    fas_payment_master b " +
	"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
	"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
	"  AND a.cashbook_year            = b.cashbook_year " +
	"  AND a.cashbook_month           = b.cashbook_month " +
	"  AND a.voucher_no               = b.voucher_no " +
	"  AND b.payment_status          <>'C' " +
	"  AND a.cashbook_year            ="+txtCB_Year+
	"  AND a.cashbook_month           ="+txtCB_Month+
//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
	"  ) " +
	"UNION ALL " +
	"  (SELECT case when b.sub_ledger_type_code=1 then'Supplier' when b.sub_ledger_type_code= 2 then 'Firms' when b.sub_ledger_type_code=10 then 'Assets'"+
	"when b.sub_ledger_type_code=4 then 'Cheque Books'"+
	"when b.sub_ledger_type_code=5 then 'Offices'"+
	"when b.sub_ledger_type_code=6 then'Bank'"+
	"when b.sub_ledger_type_code=7 then'Employees'"+
	"when b.sub_ledger_type_code=8 then '-'"+
	"when b.sub_ledger_type_code=9 then 'Other Departments'"+
	"when b.sub_ledger_type_code=10 then 'Project'"+
	"when b.sub_ledger_type_code=11 then'Contractors'"+
	"when b.sub_ledger_type_code=12 then 'Scheme Owner'"+
	"when b.sub_ledger_type_code=13 then 'Beneficiary'"+
		 " else '-'  end AS sub_ledger_type_desc, " +	
	"    b.accounting_unit_id, " +
	"    b.paid_to AS paid_to, " +
	"    b.sub_ledger_type_code, " +
	"    b.sub_ledger_code, " +
	"    B.cashbook_year, " +
	"    B.cashbook_month, " +
	"    b.payment_date AS datevalue, " +
	"    b.Account_Head_Code, " +
	"    b.voucher_no AS voucher_no, " +
	"    'P'          AS Document_Type, " +
	"    'CR'         AS Cr_Dr_Indicator, " +
	"    CASE " +
	"      WHEN CR_DR_INDICATOR = 'DR' " +
	"      THEN B.TOTAL_AMOUNT " +
	"      ELSE 0 " +
	"    END AS dr_amt, " +
	"    CASE " +
	"      WHEN CR_DR_INDICATOR = 'CR' " +
	"      THEN B.TOTAL_AMOUNT " +
	"      ELSE 0 " +
	"    END            AS Cr_Amt, " +
	"    b.total_amount AS amount, " +
	"    b.remarks      AS particulars, " +
	"    0              AS sup_no, " +
	"    CASE " +
	"      WHEN b.cashbook_month<=3 " +
	"      THEN (b.cashbook_year-1) " +
	"        ||'-' " +
	"        ||b.cashbook_year " +
	"      WHEN b.cashbook_month>=4 " +
	"      THEN b.cashbook_year " +
	"        ||'-' " +
	"        ||(b.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_payment_master b " +
	"  WHERE b.payment_status  <>'C' " +
	"  AND b.cashbook_year      = "+txtCB_Year+
	"  AND b.cashbook_month     ="+txtCB_Month+
//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	"UNION ALL " +
	"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
	"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
	"when a.sub_ledger_type_code=5 then 'Offices'"+
	"when a.sub_ledger_type_code=6 then'Bank'"+
	"when a.sub_ledger_type_code=7 then'Employees'"+
	"when a.sub_ledger_type_code=8 then '-'"+
	"when a.sub_ledger_type_code=9 then 'Other Departments'"+
	"when a.sub_ledger_type_code=10 then 'Project'"+
	"when a.sub_ledger_type_code=11 then'Contractors'"+
	"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
	"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
		 " else '-'  end AS sub_ledger_type_desc, " +		"    a.accounting_unit_id, " +
	"    b.received_from AS paid_to, " +
	"    a.sub_ledger_type_code, " +
	"    a.sub_ledger_code, " +
	"    a.cashbook_year, " +
	"    a.cashbook_month, " +
	"    b.receipt_date AS datevalue, " +
	"    a.ACCOUNT_HEAD_CODE, " +
	"    b.receipt_no AS voucher_no , " +
	"    'R'          AS document_type, " +
	"    a.CR_DR_INDICATOR, " +
	"    CASE " +
	"      WHEN a.CR_DR_INDICATOR = 'DR' " +
	"      THEN a.amount " +
	"      ELSE 0 " +
	"    END AS dr_amt, " +
	"    CASE " +
	"      WHEN a.CR_DR_INDICATOR = 'CR' " +
	"      THEN a.amount " +
	"      ELSE 0 " +
	"    END      AS cr_amt, " +
	"    a.amount AS amount, " +
	"    a.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN a.cashbook_month<=3 " +
	"      THEN (a.cashbook_year-1) " +
	"        ||'-' " +
	"        ||a.cashbook_year " +
	"      WHEN a.cashbook_month>=4 " +
	"      THEN a.cashbook_year " +
	"        ||'-' " +
	"        ||(a.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_receipt_transaction a, " +
	"    fas_receipt_master b " +
	"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
	"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
	"  AND a.cashbook_year            = b.cashbook_year " +
	"  AND a.cashbook_month           = b.cashbook_month " +
	"  AND a.receipt_no               = b.receipt_no " +
	"  AND b.receipt_status          <>'C' " +
	"  AND a.cashbook_year            ="+txtCB_Year+
	"  AND a.cashbook_month           ="+txtCB_Month+
//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
	"  ) " +
	"UNION ALL " +
	"  (SELECT case when b.sub_ledger_type_code=1 then'Supplier' when b.sub_ledger_type_code= 2 then 'Firms' when b.sub_ledger_type_code=10 then 'Assets'"+
	"when b.sub_ledger_type_code=4 then 'Cheque Books'"+
	"when b.sub_ledger_type_code=5 then 'Offices'"+
	"when b.sub_ledger_type_code=6 then'Bank'"+
	"when b.sub_ledger_type_code=7 then'Employees'"+
	"when b.sub_ledger_type_code=8 then '-'"+
	"when b.sub_ledger_type_code=9 then 'Other Departments'"+
	"when b.sub_ledger_type_code=10 then 'Project'"+
	"when b.sub_ledger_type_code=11 then'Contractors'"+
	"when b.sub_ledger_type_code=12 then 'Scheme Owner'"+
	"when b.sub_ledger_type_code=13 then 'Beneficiary'"+
		 " else '-'  end AS sub_ledger_type_desc, " +		"    b.accounting_unit_id, " +
	"    b.received_from AS paid_to, " +
	"    b.sub_ledger_type_code, " +
	"    b.SUB_LEDGER_CODE, " +
	"    B.cashbook_year, " +
	"    b.cashbook_month, " +
	"    b.receipt_date AS datevalue, " +
	"    b.account_head_code , " +
	"    b.receipt_no AS voucher_no , " +
	"    'R'          AS document_type, " +
	"    b.CR_DR_INDICATOR, " +
	"    CASE " +
	"      WHEN b.CR_DR_INDICATOR = 'DR' " +
	"      THEN b.TOTAL_AMOUNT " +
	"      ELSE 0 " +
	"    END AS dr_amt, " +
	"    CASE " +
	"      WHEN b.CR_DR_INDICATOR = 'CR' " +
	"      THEN b.TOTAL_AMOUNT " +
	"      ELSE 0 " +
	"    END            AS cr_amt, " +
	"    B.Total_Amount AS Amount, " +
	"    b.remarks      AS particulars, " +
	"    0              AS sup_no, " +
	"    CASE " +
	"      WHEN b.cashbook_month<=3 " +
	"      THEN (b.cashbook_year-1) " +
	"        ||'-' " +
	"        ||b.cashbook_year " +
	"      WHEN b.cashbook_month>=4 " +
	"      THEN b.cashbook_year " +
	"        ||'-' " +
	"        ||(b.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_receipt_master b " +
	"  WHERE b.receipt_status  <>'C' " +
	"  AND b.cashbook_year      ="+txtCB_Year+
	"  AND b.cashbook_month     ="+txtCB_Month+
//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	"UNION ALL " +
	"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
	"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
	"when a.sub_ledger_type_code=5 then 'Offices'"+
	"when a.sub_ledger_type_code=6 then'Bank'"+
	"when a.sub_ledger_type_code=7 then'Employees'"+
	"when a.sub_ledger_type_code=8 then '-'"+
	"when a.sub_ledger_type_code=9 then 'Other Departments'"+
	"when a.sub_ledger_type_code=10 then 'Project'"+
	"when a.sub_ledger_type_code=11 then'Contractors'"+
	"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
	"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
		 " else '-'  end AS sub_ledger_type_desc, " +		"    a.accounting_unit_id, " +
	"    ' ' AS paid_to, " +
	"    a.sub_ledger_type_code, " +
	"    a.SUB_LEDGER_CODE, " +
	"    a.cashbook_year, " +
	"    a.cashbook_month, " +
	"    b.voucher_date AS datevalue, " +
	"    a.account_head_code , " +
	"    b.voucher_no AS voucher_no , " +
	"    'J'          AS document_type, " +
	"    a.CR_DR_INDICATOR, " +
	"    CASE " +
	"      WHEN a.CR_DR_INDICATOR = 'DR' " +
	"      THEN a.amount " +
	"      ELSE 0 " +
	"    END AS dr_amt, " +
	"    CASE " +
	"      WHEN a.CR_DR_INDICATOR = 'CR' " +
	"      THEN a.amount " +
	"      ELSE 0 " +
	"    END      AS cr_amt, " +
	"    a.amount AS amount, " +
	"    a.particulars, " +
	"    coalesce (b.supplement_no,0) AS sup_no, " +
	"    CASE " +
	"      WHEN a.cashbook_month<=3 " +
	"      THEN (a.cashbook_year-1) " +
	"        ||'-' " +
	"        ||a.cashbook_year " +
	"      WHEN a.cashbook_month>=4 " +
	"      THEN a.cashbook_year " +
	"        ||'-' " +
	"        ||(a.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_journal_transaction a, " +
	"    fas_journal_master b " +
	"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
	"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
	"  AND a.cashbook_year            = b.cashbook_year " +
	"  AND a.cashbook_month           = b.cashbook_month " +
	"  AND a.voucher_no               = b.voucher_no " +
	"  AND b.journal_status          <>'C' " +
	"  AND a.cashbook_year            ="+txtCB_Year+
	"  AND a.cashbook_month           ="+txtCB_Month+
	"  And (Supplement_No is null or Supplement_No=0) " +
//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT '' AS sub_ledger_type_desc, " +
	"    accounting_unit_id, " +
	"    '' AS Paid_To, " +
	"    0  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
	"    cashbook_year, " +
	"    cashbook_month, " +
	"    Date_Of_Transfer     AS Datevalue, " +
	"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
	"    voucher_no           AS voucher_no, " +
	"    'IBT1'               AS document_type, " +
	"    cr_dr_indicator, " +
	"    CASE " +
	"      WHEN cr_dr_indicator = 'DR' " +
	"      THEN amount " +
	"      ELSE 0 " +
	"    END AS dr_amt, " +
	"    CASE " +
	"      WHEN cr_dr_indicator = 'CR' " +
	"      THEN amount " +
	"      ELSE 0 " +
	"    END AS cr_amt, " +
	"    amount, " +
	"    particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN cashbook_month<=3 " +
	"      THEN (cashbook_year-1) " +
	"        ||'-' " +
	"        ||cashbook_year " +
	"      WHEN cashbook_month>=4 " +
	"      THEN cashbook_year " +
	"        ||'-' " +
	"        ||(cashbook_year+1) " +
	"    END fin_year " +
	"  FROM " +
	"    (SELECT a.accounting_unit_id, " +
	"      '' AS sub_ledger_type_desc, " +
	"      A.CR_ACCOUNT_HEAD_CODE, " +
	"      Date_Of_Transfer, " +
	"      '' AS paid_received, " +
	"      a.cashbook_year, " +
	"      a.cashbook_month, " +
	"      Date_Of_Transfer AS Datevalue, " +
	"      a.VOUCHER_NO     AS voucher_no, " +
	"      'IBT'            AS document_type, " +
	"      'CR'             AS cr_dr_indicator, " +
	"      total_amount     AS amount, " +
	"      particulars " +
	"    FROM fas_inter_bank_trf_at_ho a " +
	"    WHERE a.TRANSFER_STATUS <>'C' " +
	"    AND a.cashbook_year      ="+txtCB_Year+
	"    AND a.cashbook_month     ="+txtCB_Month+
//	"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
	"    ) " +
	"  ) " +
	" UNION ALL " +
	"  (SELECT '' AS sub_ledger_type_desc, " +
	"    a.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    0  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
	"    a.cashbook_year, " +
	"    a.cashbook_month, " +
	"    a.DATE_OF_TRANSFER     AS datevalue, " +
	"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
	"    a.voucher_no           AS voucher_no, " +
	"    'IBT'                  AS document_type, " +
	"    'DR'                   AS CR_DR_INDICATOR, " +
	"    a.TOTAL_AMOUNT         AS dr_amt, " +
	"    0                      AS cr_amt, " +
	"    a.total_amount         AS amount, " +
	"    a.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN a.cashbook_month<=3 " +
	"      THEN (a.cashbook_year-1) " +
	"        ||'-' " +
	"        ||a.cashbook_year " +
	"      WHEN a.cashbook_month>=4 " +
	"      THEN a.cashbook_year " +
	"        ||'-' " +
	"        ||(a.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_inter_bank_trf_at_ho a " +
	"  WHERE a.transfer_status  ='L' " +
	"  AND a.cashbook_year      ="+txtCB_Year+
	"  AND a.cashbook_month     ="+txtCB_Month+
//	"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
	"    b.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    6  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
	//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
	"    b.cashbook_year, " +
	"    b.cashbook_month, " +
	"    b.RECEIPT_DATE         AS datevalue, " +
	"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
	"    b.RECEIPT_NO           AS voucher_no, " +
	"    'FR'                   AS document_type, " +
	"    'CR'                   AS CR_DR_INDICATOR, " +
	"    0                      AS dr_amt, " +
	"    b.total_amount         AS cr_amt, " +
	"    b.total_amount         AS amount, " +
	"    b.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN b.cashbook_month<=3 " +
	"      THEN (b.cashbook_year-1) " +
	"        ||'-' " +
	"        ||b.cashbook_year " +
	"      WHEN b.cashbook_month>=4 " +
	"      THEN b.cashbook_year " +
	"        ||'-' " +
	"        ||(b.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_fund_receipt_by_office b " +
	"  WHERE b.receipt_status  <>'C' " +
	"  AND b.cashbook_year      ="+txtCB_Year+
	"  AND b.cashbook_month     ="+txtCB_Month+
//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
	"    b.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    6  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
	"    b.cashbook_year, " +
	"    b.cashbook_month, " +
	"    b.RECEIPT_DATE         AS datevalue, " +
	"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
	"    b.RECEIPT_NO           AS voucher_no, " +
	"    'FR'                   AS document_type, " +
	"    'DR'                   AS CR_DR_INDICATOR, " +
	"    b.total_amount         AS dr_amt, " +
	"    0                      AS cr_amt, " +
	"    b.total_amount         AS amount, " +
	"    b.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN b.cashbook_month<=3 " +
	"      THEN (b.cashbook_year-1) " +
	"        ||'-' " +
	"        ||b.cashbook_year " +
	"      WHEN b.cashbook_month>=4 " +
	"      THEN b.cashbook_year " +
	"        ||'-' " +
	"        ||(b.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_fund_receipt_by_office b " +
	"  WHERE b.receipt_status  <>'C' " +
	"  AND b.cashbook_year      ="+txtCB_Year+
	"  AND b.cashbook_month     ="+txtCB_Month+
//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
	"    b.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    6  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
	"    b.cashbook_year, " +
	"    b.cashbook_month, " +
	"    b.DATE_OF_TRANSFER     AS datevalue, " +
	"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
	"    b.VOUCHER_NO           AS voucher_no, " +
	"    'FT'                   AS document_type, " +
	"    'DR'                   AS CR_DR_INDICATOR, " +
	"    b.total_amount         AS dr_amt, " +
	"    0                      AS cr_amt, " +
	"    b.total_amount         AS amount, " +
	"    b.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN b.cashbook_month<=3 " +
	"      THEN (b.cashbook_year-1) " +
	"        ||'-' " +
	"        ||b.cashbook_year " +
	"      WHEN b.cashbook_month>=4 " +
	"      THEN b.cashbook_year " +
	"        ||'-' " +
	"        ||(b.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_fund_trf_from_office b " +
	"  WHERE b.transfer_status <>'C' " +
	"  AND b.cashbook_year      ="+txtCB_Year+
	"  AND b.cashbook_month     ="+txtCB_Month+
//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
	"    b.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    6  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
	"    b.cashbook_year, " +
	"    b.cashbook_month, " +
	"    b.DATE_OF_TRANSFER     AS datevalue, " +
	"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
	"    b.VOUCHER_NO           AS voucher_no, " +
	"    'FT'                   AS document_type, " +
	"    'CR'                   AS CR_DR_INDICATOR, " +
	"    0                      AS dr_amt, " +
	"    b.total_amount         AS cr_amt, " +
	"    b.total_amount         AS amount, " +
	"    b.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN b.cashbook_month<=3 " +
	"      THEN (b.cashbook_year-1) " +
	"        ||'-' " +
	"        ||b.cashbook_year " +
	"      WHEN b.cashbook_month>=4 " +
	"      THEN b.cashbook_year " +
	"        ||'-' " +
	"        ||(b.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_fund_trf_from_office b " +
	"  WHERE b.transfer_status <>'C' " +
	"  AND b.cashbook_year      ="+txtCB_Year+
	"  AND b.cashbook_month     ="+txtCB_Month+
//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
	"    b.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    6  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
	"    b.cashbook_year, " +
	"    b.cashbook_month, " +
	"    b.RECEIPT_DATE         AS datevalue, " +
	"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
	"    b.RECEIPT_NO           AS voucher_no, " +
	"    'FT'                   AS document_type, " +
	"    'CR'                   AS Cr_Dr_Indicator, " +
	"    0                      AS dr_amt, " +
	"    b.total_amount         AS cr_amt, " +
	"    b.total_amount         AS amount, " +
	"    b.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN b.cashbook_month<=3 " +
	"      THEN (b.cashbook_year-1) " +
	"        ||'-' " +
	"        ||b.cashbook_year " +
	"      WHEN b.cashbook_month>=4 " +
	"      THEN b.cashbook_year " +
	"        ||'-' " +
	"        ||(b.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_fund_receipt_by_ho b " +
	"  WHERE b.receipt_status  <>'C' " +
	"  AND b.cashbook_year      ="+txtCB_Year+
	"  AND b.cashbook_month     ="+txtCB_Month+
//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
	"    b.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    6  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
	//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
	"    b.cashbook_year, " +
	"    b.cashbook_month, " +
	"    b.RECEIPT_DATE         AS datevalue, " +
	"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
	"    B.Receipt_No           AS Voucher_No, " +
	"    'FRH'                  AS Document_Type, " +
	"    'DR'                   AS Cr_Dr_Indicator, " +
	"    B.Total_Amount         AS Dr_Amt, " +
	"    0                      AS cr_amt, " +
	"    b.total_amount         AS amount, " +
	"    b.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN b.cashbook_month<=3 " +
	"      THEN (b.cashbook_year-1) " +
	"        ||'-' " +
	"        ||b.cashbook_year " +
	"      WHEN b.cashbook_month>=4 " +
	"      THEN b.cashbook_year " +
	"        ||'-' " +
	"        ||(b.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_fund_receipt_by_ho b " +
	"  WHERE b.receipt_status  <>'C' " +
	"  AND b.cashbook_year      ="+txtCB_Year+
	"  AND b.cashbook_month     ="+txtCB_Month+
//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
	"    a.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    6  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
	"    a.cashbook_year, " +
	"    a.cashbook_month, " +
	"    b.date_of_transfer AS datevalue, " +
	"    a.ACCOUNT_HEAD_CODE, " +
	"    B.Voucher_No AS Voucher_No, " +
	"    'FTH'        AS Document_Type, " +
	"    'DR'         AS CR_DR_INDICATOR, " +
	"    CASE " +
	"      WHEN a.CR_DR_INDICATOR = 'DR' " +
	"      THEN a.amount " +
	"      ELSE 0 " +
	"    END AS dr_amt, " +
	"    CASE " +
	"      WHEN a.CR_DR_INDICATOR = 'CR' " +
	"      THEN a.amount " +
	"      ELSE 0 " +
	"    END      AS cr_amt, " +
	"    a.amount AS amount, " +
	"    a.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN a.cashbook_month<=3 " +
	"      THEN (a.cashbook_year-1) " +
	"        ||'-' " +
	"        ||a.cashbook_year " +
	"      WHEN a.cashbook_month>=4 " +
	"      THEN a.cashbook_year " +
	"        ||'-' " +
	"        ||(a.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_fund_trf_from_ho_trn a, " +
	"    fas_fund_trf_from_ho_master b " +
	"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
	"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
	"  AND a.cashbook_year            = b.cashbook_year " +
	"  AND a.cashbook_month           = b.cashbook_month " +
	"  AND a.voucher_no               = b.voucher_no " +
	"  AND b.TRANSFER_STATUS         <>'C' " +
	"  AND a.cashbook_year            ="+txtCB_Year+
	"  AND a.cashbook_month           ="+txtCB_Month+
//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
	"  ) " +
	" UNION ALL " +
	"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
	"    a.accounting_unit_id, " +
	"    '' AS paid_to, " +
	"    6  AS sub_ledger_type_code, " +
	"    0  AS SUB_LEDGER_CODE, " +
//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
	"    a.cashbook_year, " +
	"    a.cashbook_month, " +
	"    b.date_of_transfer AS datevalue, " +
	"    b.account_head_code , " +
	"    B.Voucher_No AS Voucher_No, " +
	"    'FTH'        AS Document_Type, " +
	"    'CR'         AS CR_DR_INDICATOR, " +
	"    0            AS dr_amt, " +
	"    a.amount     AS cr_amt, " +
	"    a.amount     AS amount, " +
	"    a.particulars, " +
	"    0 AS sup_no, " +
	"    CASE " +
	"      WHEN a.cashbook_month<=3 " +
	"      THEN (a.cashbook_year-1) " +
	"        ||'-' " +
	"        ||a.cashbook_year " +
	"      WHEN a.cashbook_month>=4 " +
	"      THEN a.cashbook_year " +
	"        ||'-' " +
	"        ||(a.cashbook_year+1) " +
	"    END fin_year " +
	"  FROM fas_fund_trf_from_ho_trn a, " +
	"    fas_fund_trf_from_ho_master b " +
	"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
	"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
	"  AND a.cashbook_year            = b.cashbook_year " +
	"  AND a.cashbook_month           = b.cashbook_month " +
	"  AND a.voucher_no               = b.voucher_no " +
	"  AND b.TRANSFER_STATUS         <>'C' " +
	"  AND a.cashbook_year            ="+txtCB_Year+
	"  AND a.cashbook_month           ="+txtCB_Month+
//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
	"  ))t " +
	" INNER JOIN FAS_MST_ACCT_UNITS u " +
	" ON t.accounting_unit_id=u.accounting_unit_id " +
	" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
	" ON t.account_head_code=c.account_head_code " +
	" GROUP BY t.accounting_unit_id, " +
	"  u.ACCOUNTING_UNIT_NAME, " +
	"  t.account_head_code, " +
	"  c.ACCOUNT_HEAD_DESC, " +
	"  t.cashbook_year, " +
	"  t.cashbook_month " +sub_qry;
}
            	
            	 if ((txtCB_Month==3) && (rptsel.equalsIgnoreCase("Supplement"))&&
            			headwise.equalsIgnoreCase("unitwise_Abstract"))
            		 
            	 {
            		 sql_Qry="SELECT 1 AS data_tb, " +
         	        		"  t.accounting_unit_id, " +
         	        		"  u.ACCOUNTING_UNIT_NAME, " +
         	        		"  t.account_head_code , " +
         	        		"  c.ACCOUNT_HEAD_DESC, " +
         	        		"  t.cashbook_year, " +
         	        		"  t.cashbook_month, " +
         	        		"  SUM(t.dr_amt) AS dr_amount, " +
         	        		"  SUM(t.cr_amt) AS cr_amount " +
         	        		"FROM ( " +
         	        		
"  (SELECT case when a.sub_ledger_type_code=1 then'Supplier' when a.sub_ledger_type_code= 2 then 'Firms' when a.sub_ledger_type_code=10 then 'Assets'"+
"when a.sub_ledger_type_code=4 then 'Cheque Books'"+
"when a.sub_ledger_type_code=5 then 'Offices'"+
"when a.sub_ledger_type_code=6 then'Bank'"+
"when a.sub_ledger_type_code=7 then'Employees'"+
"when a.sub_ledger_type_code=8 then '-'"+
"when a.sub_ledger_type_code=9 then 'Other Departments'"+
"when a.sub_ledger_type_code=10 then 'Project'"+
"when a.sub_ledger_type_code=11 then'Contractors'"+
"when a.sub_ledger_type_code=12 then 'Scheme Owner'"+
"when a.sub_ledger_type_code=13 then 'Beneficiary'"+
	 " else '-'  end AS sub_ledger_type_desc, " +	         	        		"    a.accounting_unit_id, " +
         	        		"    ' ' AS paid_to, " +
         	        		"    a.sub_ledger_type_code, " +
         	        		"    a.SUB_LEDGER_CODE, " +
         	        		"    a.cashbook_year, " +
         	        		"    a.cashbook_month, " +
         	        		"    b.voucher_date AS datevalue, " +
         	        		"    a.account_head_code , " +
         	        		"    b.voucher_no AS voucher_no , " +
         	        		"    'J'          AS document_type, " +
         	        		"    a.CR_DR_INDICATOR, " +
         	        		"    CASE " +
         	        		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
         	        		"      THEN a.amount " +
         	        		"      ELSE 0 " +
         	        		"    END AS dr_amt, " +
         	        		"    CASE " +
         	        		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
         	        		"      THEN a.amount " +
         	        		"      ELSE 0 " +
         	        		"    END      AS cr_amt, " +
         	        		"    a.amount AS amount, " +
         	        		"    a.particulars, " +
         	        		"    coalesce (b.supplement_no,0) AS sup_no, " +
         	        		"    CASE " +
         	        		"      WHEN a.cashbook_month<=3 " +
         	        		"      THEN (a.cashbook_year-1) " +
         	        		"        ||'-' " +
         	        		"        ||a.cashbook_year " +
         	        		"      WHEN a.cashbook_month>=4 " +
         	        		"      THEN a.cashbook_year " +
         	        		"        ||'-' " +
         	        		"        ||(a.cashbook_year+1) " +
         	        		"    END fin_year " +
         	        		"  FROM fas_journal_transaction a, " +
         	        		"    fas_journal_master b " +
         	        		"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
         	        		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
         	        		"  AND a.cashbook_year            = b.cashbook_year " +
         	        		"  AND a.cashbook_month           = b.cashbook_month " +
         	        		"  AND a.voucher_no               = b.voucher_no " +
         	        		"  AND b.journal_status          <>'C' " +
         	        		"  AND a.cashbook_year            ="+txtCB_Year+
         	        		"  AND a.cashbook_month           ="+txtCB_Month+
         	        		"  AND b.SUPPLEMENT_NO            ="+ sup +
         	        		"  ) " +
         	        		" )t " +
         	        		" INNER JOIN FAS_MST_ACCT_UNITS u " +
         	        		" ON t.accounting_unit_id=u.accounting_unit_id " +
         	        		" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
         	        		" ON t.account_head_code=c.account_head_code " +
         	        		" GROUP BY t.accounting_unit_id, " +
         	        		"  u.ACCOUNTING_UNIT_NAME, " +
         	        		"  t.account_head_code, " +
         	        		"  c.ACCOUNT_HEAD_DESC, " +
         	        		"  t.cashbook_year, " +
         	        		"  t.cashbook_month " +sub_qry;
//            	{
//                	
//                	
//                    sql_Qry="SELECT 1 AS data_tb, " +
//            		"  t.accounting_unit_id, " +
//            		"  u.ACCOUNTING_UNIT_NAME, " +
//            		"  t.account_head_code , " +
//            		"  c.ACCOUNT_HEAD_DESC, " +
//            		"  t.cashbook_year, " +
//            		"  t.cashbook_month, " +
//            		"  SUM(t.dr_amt) AS dr_amount, " +
//            		"  SUM(t.cr_amt) AS cr_amount " +
//            		"FROM ( " +
//            		"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//            		"    a.accounting_unit_id, " +
//            		"    b.paid_to AS paid_to, " +
//            		"    a.sub_ledger_type_code, " +
//            		"    a.SUB_LEDGER_CODE, " +
//            		"    a.cashbook_year, " +
//            		"    a.cashbook_month, " +
//            		"    b.payment_date AS datevalue, " +
//            		"    a.ACCOUNT_HEAD_CODE, " +
//            		"    b.voucher_no AS voucher_no, " +
//            		"    'P'          AS document_type, " +
//            		"    a.CR_DR_INDICATOR, " +
//            		"    CASE " +
//            		"      WHEN a.cr_dr_indicator = 'DR' " +
//            		"      THEN a.amount " +
//            		"      ELSE 0 " +
//            		"    END AS dr_amt, " +
//            		"    CASE " +
//            		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//            		"      THEN a.amount " +
//            		"      ELSE 0 " +
//            		"    END      AS cr_amt, " +
//            		"    a.amount AS amount, " +
//            		"    a.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN a.cashbook_month<=3 " +
//            		"      THEN (a.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||a.cashbook_year " +
//            		"      WHEN a.cashbook_month>=4 " +
//            		"      THEN a.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(a.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_payment_transaction a, " +
//            		"    fas_payment_master b " +
//            		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//            		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//            		"  AND a.cashbook_year            = b.cashbook_year " +
//            		"  AND a.cashbook_month           = b.cashbook_month " +
//            		"  AND a.voucher_no               = b.voucher_no " +
//            		"  AND b.payment_status          <>'C' " +
//            		"  AND a.cashbook_year            ="+txtCB_Year+
//            		"  AND a.cashbook_month           ="+txtCB_Month+
////            		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		"UNION ALL " +
//            		"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//            		"    b.accounting_unit_id, " +
//            		"    b.paid_to AS paid_to, " +
//            		"    b.sub_ledger_type_code, " +
//            		"    b.sub_ledger_code, " +
//            		"    B.cashbook_year, " +
//            		"    B.cashbook_month, " +
//            		"    b.payment_date AS datevalue, " +
//            		"    b.Account_Head_Code, " +
//            		"    b.voucher_no AS voucher_no, " +
//            		"    'P'          AS Document_Type, " +
//            		"    'CR'         AS Cr_Dr_Indicator, " +
//            		"    CASE " +
//            		"      WHEN CR_DR_INDICATOR = 'DR' " +
//            		"      THEN B.TOTAL_AMOUNT " +
//            		"      ELSE 0 " +
//            		"    END AS dr_amt, " +
//            		"    CASE " +
//            		"      WHEN CR_DR_INDICATOR = 'CR' " +
//            		"      THEN B.TOTAL_AMOUNT " +
//            		"      ELSE 0 " +
//            		"    END            AS Cr_Amt, " +
//            		"    b.total_amount AS amount, " +
//            		"    b.remarks      AS particulars, " +
//            		"    0              AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN b.cashbook_month<=3 " +
//            		"      THEN (b.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||b.cashbook_year " +
//            		"      WHEN b.cashbook_month>=4 " +
//            		"      THEN b.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(b.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_payment_master b " +
//            		"  WHERE b.payment_status  <>'C' " +
//            		"  AND b.cashbook_year      = "+txtCB_Year+
//            		"  AND b.cashbook_month     ="+txtCB_Month+
////            		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		"UNION ALL " +
//            		"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//            		"    a.accounting_unit_id, " +
//            		"    b.received_from AS paid_to, " +
//            		"    a.sub_ledger_type_code, " +
//            		"    a.sub_ledger_code, " +
//            		"    a.cashbook_year, " +
//            		"    a.cashbook_month, " +
//            		"    b.receipt_date AS datevalue, " +
//            		"    a.ACCOUNT_HEAD_CODE, " +
//            		"    b.receipt_no AS voucher_no , " +
//            		"    'R'          AS document_type, " +
//            		"    a.CR_DR_INDICATOR, " +
//            		"    CASE " +
//            		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//            		"      THEN a.amount " +
//            		"      ELSE 0 " +
//            		"    END AS dr_amt, " +
//            		"    CASE " +
//            		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//            		"      THEN a.amount " +
//            		"      ELSE 0 " +
//            		"    END      AS cr_amt, " +
//            		"    a.amount AS amount, " +
//            		"    a.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN a.cashbook_month<=3 " +
//            		"      THEN (a.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||a.cashbook_year " +
//            		"      WHEN a.cashbook_month>=4 " +
//            		"      THEN a.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(a.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_receipt_transaction a, " +
//            		"    fas_receipt_master b " +
//            		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//            		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//            		"  AND a.cashbook_year            = b.cashbook_year " +
//            		"  AND a.cashbook_month           = b.cashbook_month " +
//            		"  AND a.receipt_no               = b.receipt_no " +
//            		"  AND b.receipt_status          <>'C' " +
//            		"  AND a.cashbook_year            ="+txtCB_Year+
//            		"  AND a.cashbook_month           ="+txtCB_Month+
////            		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		"UNION ALL " +
//            		"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//            		"    b.accounting_unit_id, " +
//            		"    b.received_from AS paid_to, " +
//            		"    b.sub_ledger_type_code, " +
//            		"    b.SUB_LEDGER_CODE, " +
//            		"    B.cashbook_year, " +
//            		"    b.cashbook_month, " +
//            		"    b.receipt_date AS datevalue, " +
//            		"    b.account_head_code , " +
//            		"    b.receipt_no AS voucher_no , " +
//            		"    'R'          AS document_type, " +
//            		"    b.CR_DR_INDICATOR, " +
//            		"    CASE " +
//            		"      WHEN b.CR_DR_INDICATOR = 'DR' " +
//            		"      THEN b.TOTAL_AMOUNT " +
//            		"      ELSE 0 " +
//            		"    END AS dr_amt, " +
//            		"    CASE " +
//            		"      WHEN b.CR_DR_INDICATOR = 'CR' " +
//            		"      THEN b.TOTAL_AMOUNT " +
//            		"      ELSE 0 " +
//            		"    END            AS cr_amt, " +
//            		"    B.Total_Amount AS Amount, " +
//            		"    b.remarks      AS particulars, " +
//            		"    0              AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN b.cashbook_month<=3 " +
//            		"      THEN (b.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||b.cashbook_year " +
//            		"      WHEN b.cashbook_month>=4 " +
//            		"      THEN b.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(b.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_receipt_master b " +
//            		"  WHERE b.receipt_status  <>'C' " +
//            		"  AND b.cashbook_year      ="+txtCB_Year+
//            		"  AND b.cashbook_month     ="+txtCB_Month+
////            		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		"UNION ALL " +
//            		"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//            		"    a.accounting_unit_id, " +
//            		"    ' ' AS paid_to, " +
//            		"    a.sub_ledger_type_code, " +
//            		"    a.SUB_LEDGER_CODE, " +
//            		"    a.cashbook_year, " +
//            		"    a.cashbook_month, " +
//            		"    b.voucher_date AS datevalue, " +
//            		"    a.account_head_code , " +
//            		"    b.voucher_no AS voucher_no , " +
//            		"    'J'          AS document_type, " +
//            		"    a.CR_DR_INDICATOR, " +
//            		"    CASE " +
//            		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//            		"      THEN a.amount " +
//            		"      ELSE 0 " +
//            		"    END AS dr_amt, " +
//            		"    CASE " +
//            		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//            		"      THEN a.amount " +
//            		"      ELSE 0 " +
//            		"    END      AS cr_amt, " +
//            		"    a.amount AS amount, " +
//            		"    a.particulars, " +
//            		"    NVL(b.supplement_no,0) AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN a.cashbook_month<=3 " +
//            		"      THEN (a.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||a.cashbook_year " +
//            		"      WHEN a.cashbook_month>=4 " +
//            		"      THEN a.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(a.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_journal_transaction a, " +
//            		"    fas_journal_master b " +
//            		"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
//            		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//            		"  AND a.cashbook_year            = b.cashbook_year " +
//            		"  AND a.cashbook_month           = b.cashbook_month " +
//            		"  AND a.voucher_no               = b.voucher_no " +
//            		"  AND b.journal_status          <>'C' " +
//            		"  AND a.cashbook_year            ="+txtCB_Year+
//            		"  AND a.cashbook_month           ="+txtCB_Month+
////            		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//            		"  AND b.SUPPLEMENT_NO            ="+ sup +
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT '' AS sub_ledger_type_desc, " +
//            		"    accounting_unit_id, " +
//            		"    '' AS Paid_To, " +
//            		"    0  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            		"    cashbook_year, " +
//            		"    cashbook_month, " +
//            		"    Date_Of_Transfer     AS Datevalue, " +
//            		"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//            		"    voucher_no           AS voucher_no, " +
//            		"    'IBT1'               AS document_type, " +
//            		"    cr_dr_indicator, " +
//            		"    CASE " +
//            		"      WHEN cr_dr_indicator = 'DR' " +
//            		"      THEN amount " +
//            		"      ELSE 0 " +
//            		"    END AS dr_amt, " +
//            		"    CASE " +
//            		"      WHEN cr_dr_indicator = 'CR' " +
//            		"      THEN amount " +
//            		"      ELSE 0 " +
//            		"    END AS cr_amt, " +
//            		"    amount, " +
//            		"    particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN cashbook_month<=3 " +
//            		"      THEN (cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||cashbook_year " +
//            		"      WHEN cashbook_month>=4 " +
//            		"      THEN cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM " +
//            		"    (SELECT a.accounting_unit_id, " +
//            		"      '' AS sub_ledger_type_desc, " +
//            		"      A.CR_ACCOUNT_HEAD_CODE, " +
//            		"      Date_Of_Transfer, " +
//            		"      '' AS paid_received, " +
//            		"      a.cashbook_year, " +
//            		"      a.cashbook_month, " +
//            		"      Date_Of_Transfer AS Datevalue, " +
//            		"      a.VOUCHER_NO     AS voucher_no, " +
//            		"      'IBT'            AS document_type, " +
//            		"      'CR'             AS cr_dr_indicator, " +
//            		"      total_amount     AS amount, " +
//            		"      particulars " +
//            		"    FROM fas_inter_bank_trf_at_ho a " +
//            		"    WHERE a.TRANSFER_STATUS <>'C' " +
//            		"    AND a.cashbook_year      ="+txtCB_Year+
//            		"    AND a.cashbook_month     ="+txtCB_Month+
////            		"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"    ) " +
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT '' AS sub_ledger_type_desc, " +
//            		"    a.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    0  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            		"    a.cashbook_year, " +
//            		"    a.cashbook_month, " +
//            		"    a.DATE_OF_TRANSFER     AS datevalue, " +
//            		"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//            		"    a.voucher_no           AS voucher_no, " +
//            		"    'IBT'                  AS document_type, " +
//            		"    'DR'                   AS CR_DR_INDICATOR, " +
//            		"    a.TOTAL_AMOUNT         AS dr_amt, " +
//            		"    0                      AS cr_amt, " +
//            		"    a.total_amount         AS amount, " +
//            		"    a.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN a.cashbook_month<=3 " +
//            		"      THEN (a.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||a.cashbook_year " +
//            		"      WHEN a.cashbook_month>=4 " +
//            		"      THEN a.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(a.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_inter_bank_trf_at_ho a " +
//            		"  WHERE a.transfer_status  ='L' " +
//            		"  AND a.cashbook_year      ="+txtCB_Year+
//            		"  AND a.cashbook_month     ="+txtCB_Month+
////            		"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//            		"    b.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    6  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            		//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//            		"    b.cashbook_year, " +
//            		"    b.cashbook_month, " +
//            		"    b.RECEIPT_DATE         AS datevalue, " +
//            		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//            		"    b.RECEIPT_NO           AS voucher_no, " +
//            		"    'FR'                   AS document_type, " +
//            		"    'CR'                   AS CR_DR_INDICATOR, " +
//            		"    0                      AS dr_amt, " +
//            		"    b.total_amount         AS cr_amt, " +
//            		"    b.total_amount         AS amount, " +
//            		"    b.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN b.cashbook_month<=3 " +
//            		"      THEN (b.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||b.cashbook_year " +
//            		"      WHEN b.cashbook_month>=4 " +
//            		"      THEN b.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(b.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_fund_receipt_by_office b " +
//            		"  WHERE b.receipt_status  <>'C' " +
//            		"  AND b.cashbook_year      ="+txtCB_Year+
//            		"  AND b.cashbook_month     ="+txtCB_Month+
////            		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//            		"    b.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    6  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            		"    b.cashbook_year, " +
//            		"    b.cashbook_month, " +
//            		"    b.RECEIPT_DATE         AS datevalue, " +
//            		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//            		"    b.RECEIPT_NO           AS voucher_no, " +
//            		"    'FR'                   AS document_type, " +
//            		"    'DR'                   AS CR_DR_INDICATOR, " +
//            		"    b.total_amount         AS dr_amt, " +
//            		"    0                      AS cr_amt, " +
//            		"    b.total_amount         AS amount, " +
//            		"    b.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN b.cashbook_month<=3 " +
//            		"      THEN (b.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||b.cashbook_year " +
//            		"      WHEN b.cashbook_month>=4 " +
//            		"      THEN b.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(b.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_fund_receipt_by_office b " +
//            		"  WHERE b.receipt_status  <>'C' " +
//            		"  AND b.cashbook_year      ="+txtCB_Year+
//            		"  AND b.cashbook_month     ="+txtCB_Month+
////            		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//            		"    b.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    6  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            		"    b.cashbook_year, " +
//            		"    b.cashbook_month, " +
//            		"    b.DATE_OF_TRANSFER     AS datevalue, " +
//            		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//            		"    b.VOUCHER_NO           AS voucher_no, " +
//            		"    'FT'                   AS document_type, " +
//            		"    'DR'                   AS CR_DR_INDICATOR, " +
//            		"    b.total_amount         AS dr_amt, " +
//            		"    0                      AS cr_amt, " +
//            		"    b.total_amount         AS amount, " +
//            		"    b.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN b.cashbook_month<=3 " +
//            		"      THEN (b.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||b.cashbook_year " +
//            		"      WHEN b.cashbook_month>=4 " +
//            		"      THEN b.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(b.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_fund_trf_from_office b " +
//            		"  WHERE b.transfer_status <>'C' " +
//            		"  AND b.cashbook_year      ="+txtCB_Year+
//            		"  AND b.cashbook_month     ="+txtCB_Month+
////            		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//            		"    b.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    6  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//            		"    b.cashbook_year, " +
//            		"    b.cashbook_month, " +
//            		"    b.DATE_OF_TRANSFER     AS datevalue, " +
//            		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//            		"    b.VOUCHER_NO           AS voucher_no, " +
//            		"    'FT'                   AS document_type, " +
//            		"    'CR'                   AS CR_DR_INDICATOR, " +
//            		"    0                      AS dr_amt, " +
//            		"    b.total_amount         AS cr_amt, " +
//            		"    b.total_amount         AS amount, " +
//            		"    b.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN b.cashbook_month<=3 " +
//            		"      THEN (b.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||b.cashbook_year " +
//            		"      WHEN b.cashbook_month>=4 " +
//            		"      THEN b.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(b.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_fund_trf_from_office b " +
//            		"  WHERE b.transfer_status <>'C' " +
//            		"  AND b.cashbook_year      ="+txtCB_Year+
//            		"  AND b.cashbook_month     ="+txtCB_Month+
////            		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//            		"    b.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    6  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//            		"    b.cashbook_year, " +
//            		"    b.cashbook_month, " +
//            		"    b.RECEIPT_DATE         AS datevalue, " +
//            		"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//            		"    b.RECEIPT_NO           AS voucher_no, " +
//            		"    'FT'                   AS document_type, " +
//            		"    'CR'                   AS Cr_Dr_Indicator, " +
//            		"    0                      AS dr_amt, " +
//            		"    b.total_amount         AS cr_amt, " +
//            		"    b.total_amount         AS amount, " +
//            		"    b.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN b.cashbook_month<=3 " +
//            		"      THEN (b.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||b.cashbook_year " +
//            		"      WHEN b.cashbook_month>=4 " +
//            		"      THEN b.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(b.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_fund_receipt_by_ho b " +
//            		"  WHERE b.receipt_status  <>'C' " +
//            		"  AND b.cashbook_year      ="+txtCB_Year+
//            		"  AND b.cashbook_month     ="+txtCB_Month+
////            		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//            		"    b.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    6  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            		//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//            		"    b.cashbook_year, " +
//            		"    b.cashbook_month, " +
//            		"    b.RECEIPT_DATE         AS datevalue, " +
//            		"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//            		"    B.Receipt_No           AS Voucher_No, " +
//            		"    'FRH'                  AS Document_Type, " +
//            		"    'DR'                   AS Cr_Dr_Indicator, " +
//            		"    B.Total_Amount         AS Dr_Amt, " +
//            		"    0                      AS cr_amt, " +
//            		"    b.total_amount         AS amount, " +
//            		"    b.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN b.cashbook_month<=3 " +
//            		"      THEN (b.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||b.cashbook_year " +
//            		"      WHEN b.cashbook_month>=4 " +
//            		"      THEN b.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(b.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_fund_receipt_by_ho b " +
//            		"  WHERE b.receipt_status  <>'C' " +
//            		"  AND b.cashbook_year      ="+txtCB_Year+
//            		"  AND b.cashbook_month     ="+txtCB_Month+
////            		"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//            		"    a.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    6  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//            		"    a.cashbook_year, " +
//            		"    a.cashbook_month, " +
//            		"    b.date_of_transfer AS datevalue, " +
//            		"    a.ACCOUNT_HEAD_CODE, " +
//            		"    B.Voucher_No AS Voucher_No, " +
//            		"    'FTH'        AS Document_Type, " +
//            		"    'DR'         AS CR_DR_INDICATOR, " +
//            		"    CASE " +
//            		"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//            		"      THEN a.amount " +
//            		"      ELSE 0 " +
//            		"    END AS dr_amt, " +
//            		"    CASE " +
//            		"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//            		"      THEN a.amount " +
//            		"      ELSE 0 " +
//            		"    END      AS cr_amt, " +
//            		"    a.amount AS amount, " +
//            		"    a.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN a.cashbook_month<=3 " +
//            		"      THEN (a.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||a.cashbook_year " +
//            		"      WHEN a.cashbook_month>=4 " +
//            		"      THEN a.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(a.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_fund_trf_from_ho_trn a, " +
//            		"    fas_fund_trf_from_ho_master b " +
//            		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//            		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//            		"  AND a.cashbook_year            = b.cashbook_year " +
//            		"  AND a.cashbook_month           = b.cashbook_month " +
//            		"  AND a.voucher_no               = b.voucher_no " +
//            		"  AND b.TRANSFER_STATUS         <>'C' " +
//            		"  AND a.cashbook_year            ="+txtCB_Year+
//            		"  AND a.cashbook_month           ="+txtCB_Month+
////            		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//            		"  ) " +
//            		" UNION ALL " +
//            		"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//            		"    a.accounting_unit_id, " +
//            		"    '' AS paid_to, " +
//            		"    6  AS sub_ledger_type_code, " +
//            		"    0  AS SUB_LEDGER_CODE, " +
//            	//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//            		"    a.cashbook_year, " +
//            		"    a.cashbook_month, " +
//            		"    b.date_of_transfer AS datevalue, " +
//            		"    b.account_head_code , " +
//            		"    B.Voucher_No AS Voucher_No, " +
//            		"    'FTH'        AS Document_Type, " +
//            		"    'CR'         AS CR_DR_INDICATOR, " +
//            		"    0            AS dr_amt, " +
//            		"    a.amount     AS cr_amt, " +
//            		"    a.amount     AS amount, " +
//            		"    a.particulars, " +
//            		"    0 AS sup_no, " +
//            		"    CASE " +
//            		"      WHEN a.cashbook_month<=3 " +
//            		"      THEN (a.cashbook_year-1) " +
//            		"        ||'-' " +
//            		"        ||a.cashbook_year " +
//            		"      WHEN a.cashbook_month>=4 " +
//            		"      THEN a.cashbook_year " +
//            		"        ||'-' " +
//            		"        ||(a.cashbook_year+1) " +
//            		"    END fin_year " +
//            		"  FROM fas_fund_trf_from_ho_trn a, " +
//            		"    fas_fund_trf_from_ho_master b " +
//            		"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//            		"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//            		"  AND a.cashbook_year            = b.cashbook_year " +
//            		"  AND a.cashbook_month           = b.cashbook_month " +
//            		"  AND a.voucher_no               = b.voucher_no " +
//            		"  AND b.TRANSFER_STATUS         <>'C' " +
//            		"  AND a.cashbook_year            ="+txtCB_Year+
//            		"  AND a.cashbook_month           ="+txtCB_Month+
////            		"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//            		"  ))t " +
//            		" INNER JOIN FAS_MST_ACCT_UNITS u " +
//            		" ON t.accounting_unit_id=u.accounting_unit_id " +
//            		" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
//            		" ON t.account_head_code=c.account_head_code " +
//            		" GROUP BY t.accounting_unit_id, " +
//            		"  u.ACCOUNTING_UNIT_NAME, " +
//            		"  t.account_head_code, " +
//            		"  c.ACCOUNT_HEAD_DESC, " +
//            		"  t.cashbook_year, " +
//            		"  t.cashbook_month " +sub_qry;
//            		
            	}
                
            }
            	
            
            
            
//            else if(cmbAcc_UnitCode==0){
//			
//            	if(headwise.equalsIgnoreCase("unitwise_Abstract"))
//            	{
//            	
//            	if((txtCB_Month!=3) ||(rptsel.equalsIgnoreCase("Regular")))
//            	{
//            	
//            	sql_Qry="SELECT 1 AS data_tb, " +
//					"  t.accounting_unit_id, " +
//					"  u.ACCOUNTING_UNIT_NAME, " +
//					"  t.account_head_code , " +
//					"  c.ACCOUNT_HEAD_DESC, " +
//					"  t.cashbook_year, " +
//					"  t.cashbook_month, " +
//					"  SUM(t.dr_amt) AS dr_amount, " +
//					"  SUM(t.cr_amt) AS cr_amount " +
//					"FROM ( " +
//					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//					"    a.accounting_unit_id, " +
//					"    b.paid_to AS paid_to, " +
//					"    a.sub_ledger_type_code, " +
//					"    a.SUB_LEDGER_CODE, " +
//					"    a.cashbook_year, " +
//					"    a.cashbook_month, " +
//					"    b.payment_date AS datevalue, " +
//					"    a.ACCOUNT_HEAD_CODE, " +
//					"    b.voucher_no AS voucher_no, " +
//					"    'P'          AS document_type, " +
//					"    a.CR_DR_INDICATOR, " +
//					"    CASE " +
//					"      WHEN a.cr_dr_indicator = 'DR' " +
//					"      THEN a.amount " +
//					"      ELSE 0 " +
//					"    END AS dr_amt, " +
//					"    CASE " +
//					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//					"      THEN a.amount " +
//					"      ELSE 0 " +
//					"    END      AS cr_amt, " +
//					"    a.amount AS amount, " +
//					"    a.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN a.cashbook_month<=3 " +
//					"      THEN (a.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||a.cashbook_year " +
//					"      WHEN a.cashbook_month>=4 " +
//					"      THEN a.cashbook_year " +
//					"        ||'-' " +
//					"        ||(a.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_payment_transaction a, " +
//					"    fas_payment_master b " +
//					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//					"  AND a.cashbook_year            = b.cashbook_year " +
//					"  AND a.cashbook_month           = b.cashbook_month " +
//					"  AND a.voucher_no               = b.voucher_no " +
//					"  AND b.payment_status          <>'C' " +
//					"  AND a.cashbook_year            ="+txtCB_Year+
//					"  AND a.cashbook_month           ="+txtCB_Month+
//				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//					"  ) " +
//					"UNION ALL " +
//					"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//					"    b.accounting_unit_id, " +
//					"    b.paid_to AS paid_to, " +
//					"    b.sub_ledger_type_code, " +
//					"    b.sub_ledger_code, " +
//					"    B.cashbook_year, " +
//					"    B.cashbook_month, " +
//					"    b.payment_date AS datevalue, " +
//					"    b.Account_Head_Code, " +
//					"    b.voucher_no AS voucher_no, " +
//					"    'P'          AS Document_Type, " +
//					"    'CR'         AS Cr_Dr_Indicator, " +
//					"    CASE " +
//					"      WHEN CR_DR_INDICATOR = 'DR' " +
//					"      THEN B.TOTAL_AMOUNT " +
//					"      ELSE 0 " +
//					"    END AS dr_amt, " +
//					"    CASE " +
//					"      WHEN CR_DR_INDICATOR = 'CR' " +
//					"      THEN B.TOTAL_AMOUNT " +
//					"      ELSE 0 " +
//					"    END            AS Cr_Amt, " +
//					"    b.total_amount AS amount, " +
//					"    b.remarks      AS particulars, " +
//					"    0              AS sup_no, " +
//					"    CASE " +
//					"      WHEN b.cashbook_month<=3 " +
//					"      THEN (b.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||b.cashbook_year " +
//					"      WHEN b.cashbook_month>=4 " +
//					"      THEN b.cashbook_year " +
//					"        ||'-' " +
//					"        ||(b.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_payment_master b " +
//					"  WHERE b.payment_status  <>'C' " +
//					"  AND b.cashbook_year      = "+txtCB_Year+
//					"  AND b.cashbook_month     ="+txtCB_Month+
//				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					"UNION ALL " +
//					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//					"    a.accounting_unit_id, " +
//					"    b.received_from AS paid_to, " +
//					"    a.sub_ledger_type_code, " +
//					"    a.sub_ledger_code, " +
//					"    a.cashbook_year, " +
//					"    a.cashbook_month, " +
//					"    b.receipt_date AS datevalue, " +
//					"    a.ACCOUNT_HEAD_CODE, " +
//					"    b.receipt_no AS voucher_no , " +
//					"    'R'          AS document_type, " +
//					"    a.CR_DR_INDICATOR, " +
//					"    CASE " +
//					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//					"      THEN a.amount " +
//					"      ELSE 0 " +
//					"    END AS dr_amt, " +
//					"    CASE " +
//					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//					"      THEN a.amount " +
//					"      ELSE 0 " +
//					"    END      AS cr_amt, " +
//					"    a.amount AS amount, " +
//					"    a.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN a.cashbook_month<=3 " +
//					"      THEN (a.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||a.cashbook_year " +
//					"      WHEN a.cashbook_month>=4 " +
//					"      THEN a.cashbook_year " +
//					"        ||'-' " +
//					"        ||(a.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_receipt_transaction a, " +
//					"    fas_receipt_master b " +
//					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//					"  AND a.cashbook_year            = b.cashbook_year " +
//					"  AND a.cashbook_month           = b.cashbook_month " +
//					"  AND a.receipt_no               = b.receipt_no " +
//					"  AND b.receipt_status          <>'C' " +
//					"  AND a.cashbook_year            ="+txtCB_Year+
//					"  AND a.cashbook_month           ="+txtCB_Month+
//				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//					"  ) " +
//					"UNION ALL " +
//					"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//					"    b.accounting_unit_id, " +
//					"    b.received_from AS paid_to, " +
//					"    b.sub_ledger_type_code, " +
//					"    b.SUB_LEDGER_CODE, " +
//					"    B.cashbook_year, " +
//					"    b.cashbook_month, " +
//					"    b.receipt_date AS datevalue, " +
//					"    b.account_head_code , " +
//					"    b.receipt_no AS voucher_no , " +
//					"    'R'          AS document_type, " +
//					"    b.CR_DR_INDICATOR, " +
//					"    CASE " +
//					"      WHEN b.CR_DR_INDICATOR = 'DR' " +
//					"      THEN b.TOTAL_AMOUNT " +
//					"      ELSE 0 " +
//					"    END AS dr_amt, " +
//					"    CASE " +
//					"      WHEN b.CR_DR_INDICATOR = 'CR' " +
//					"      THEN b.TOTAL_AMOUNT " +
//					"      ELSE 0 " +
//					"    END            AS cr_amt, " +
//					"    B.Total_Amount AS Amount, " +
//					"    b.remarks      AS particulars, " +
//					"    0              AS sup_no, " +
//					"    CASE " +
//					"      WHEN b.cashbook_month<=3 " +
//					"      THEN (b.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||b.cashbook_year " +
//					"      WHEN b.cashbook_month>=4 " +
//					"      THEN b.cashbook_year " +
//					"        ||'-' " +
//					"        ||(b.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_receipt_master b " +
//					"  WHERE b.receipt_status  <>'C' " +
//					"  AND b.cashbook_year      ="+txtCB_Year+
//					"  AND b.cashbook_month     ="+txtCB_Month+
//				///	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					"UNION ALL " +
//					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//					"    a.accounting_unit_id, " +
//					"    ' ' AS paid_to, " +
//					"    a.sub_ledger_type_code, " +
//					"    a.SUB_LEDGER_CODE, " +
//					"    a.cashbook_year, " +
//					"    a.cashbook_month, " +
//					"    b.voucher_date AS datevalue, " +
//					"    a.account_head_code , " +
//					"    b.voucher_no AS voucher_no , " +
//					"    'J'          AS document_type, " +
//					"    a.CR_DR_INDICATOR, " +
//					"    CASE " +
//					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//					"      THEN a.amount " +
//					"      ELSE 0 " +
//					"    END AS dr_amt, " +
//					"    CASE " +
//					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//					"      THEN a.amount " +
//					"      ELSE 0 " +
//					"    END      AS cr_amt, " +
//					"    a.amount AS amount, " +
//					"    a.particulars, " +
//					"    NVL(b.supplement_no,0) AS sup_no, " +
//					"    CASE " +
//					"      WHEN a.cashbook_month<=3 " +
//					"      THEN (a.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||a.cashbook_year " +
//					"      WHEN a.cashbook_month>=4 " +
//					"      THEN a.cashbook_year " +
//					"        ||'-' " +
//					"        ||(a.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_journal_transaction a, " +
//					"    fas_journal_master b " +
//					"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
//					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//					"  AND a.cashbook_year            = b.cashbook_year " +
//					"  AND a.cashbook_month           = b.cashbook_month " +
//					"  AND a.voucher_no               = b.voucher_no " +
//					"  AND b.journal_status          <>'C' " +
//					"  AND a.cashbook_year            ="+txtCB_Year+
//					"  AND a.cashbook_month           ="+txtCB_Month+
//					
//				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT '' AS sub_ledger_type_desc, " +
//					"    accounting_unit_id, " +
//					"    '' AS Paid_To, " +
//					"    0  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//					"    cashbook_year, " +
//					"    cashbook_month, " +
//					"    Date_Of_Transfer     AS Datevalue, " +
//					"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//					"    voucher_no           AS voucher_no, " +
//					"    'IBT1'               AS document_type, " +
//					"    cr_dr_indicator, " +
//					"    CASE " +
//					"      WHEN cr_dr_indicator = 'DR' " +
//					"      THEN amount " +
//					"      ELSE 0 " +
//					"    END AS dr_amt, " +
//					"    CASE " +
//					"      WHEN cr_dr_indicator = 'CR' " +
//					"      THEN amount " +
//					"      ELSE 0 " +
//					"    END AS cr_amt, " +
//					"    amount, " +
//					"    particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN cashbook_month<=3 " +
//					"      THEN (cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||cashbook_year " +
//					"      WHEN cashbook_month>=4 " +
//					"      THEN cashbook_year " +
//					"        ||'-' " +
//					"        ||(cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM " +
//					"    (SELECT a.accounting_unit_id, " +
//					"      '' AS sub_ledger_type_desc, " +
//					"      A.CR_ACCOUNT_HEAD_CODE, " +
//					"      Date_Of_Transfer, " +
//					"      '' AS paid_received, " +
//					"      a.cashbook_year, " +
//					"      a.cashbook_month, " +
//					"      Date_Of_Transfer AS Datevalue, " +
//					"      a.VOUCHER_NO     AS voucher_no, " +
//					"      'IBT'            AS document_type, " +
//					"      'CR'             AS cr_dr_indicator, " +
//					"      total_amount     AS amount, " +
//					"      particulars " +
//					"    FROM fas_inter_bank_trf_at_ho a " +
//					"    WHERE a.TRANSFER_STATUS <>'C' " +
//					"    AND a.cashbook_year      ="+txtCB_Year+
//					"    AND a.cashbook_month     ="+txtCB_Month+
//				//	"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//					"    ) " +
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT '' AS sub_ledger_type_desc, " +
//					"    a.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    0  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//					"    a.cashbook_year, " +
//					"    a.cashbook_month, " +
//					"    a.DATE_OF_TRANSFER     AS datevalue, " +
//					"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//					"    a.voucher_no           AS voucher_no, " +
//					"    'IBT'                  AS document_type, " +
//					"    'DR'                   AS CR_DR_INDICATOR, " +
//					"    a.TOTAL_AMOUNT         AS dr_amt, " +
//					"    0                      AS cr_amt, " +
//					"    a.total_amount         AS amount, " +
//					"    a.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN a.cashbook_month<=3 " +
//					"      THEN (a.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||a.cashbook_year " +
//					"      WHEN a.cashbook_month>=4 " +
//					"      THEN a.cashbook_year " +
//					"        ||'-' " +
//					"        ||(a.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_inter_bank_trf_at_ho a " +
//					"  WHERE a.transfer_status  ='L' " +
//					"  AND a.cashbook_year      ="+txtCB_Year+
//					"  AND a.cashbook_month     ="+txtCB_Month+
//				//	"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//					"    b.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    6  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//					//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//					"    b.cashbook_year, " +
//					"    b.cashbook_month, " +
//					"    b.RECEIPT_DATE         AS datevalue, " +
//					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//					"    b.RECEIPT_NO           AS voucher_no, " +
//					"    'FR'                   AS document_type, " +
//					"    'CR'                   AS CR_DR_INDICATOR, " +
//					"    0                      AS dr_amt, " +
//					"    b.total_amount         AS cr_amt, " +
//					"    b.total_amount         AS amount, " +
//					"    b.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN b.cashbook_month<=3 " +
//					"      THEN (b.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||b.cashbook_year " +
//					"      WHEN b.cashbook_month>=4 " +
//					"      THEN b.cashbook_year " +
//					"        ||'-' " +
//					"        ||(b.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_fund_receipt_by_office b " +
//					"  WHERE b.receipt_status  <>'C' " +
//					"  AND b.cashbook_year      ="+txtCB_Year+
//					"  AND b.cashbook_month     ="+txtCB_Month+
//					//"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//					"    b.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    6  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//					"    b.cashbook_year, " +
//					"    b.cashbook_month, " +
//					"    b.RECEIPT_DATE         AS datevalue, " +
//					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//					"    b.RECEIPT_NO           AS voucher_no, " +
//					"    'FR'                   AS document_type, " +
//					"    'DR'                   AS CR_DR_INDICATOR, " +
//					"    b.total_amount         AS dr_amt, " +
//					"    0                      AS cr_amt, " +
//					"    b.total_amount         AS amount, " +
//					"    b.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN b.cashbook_month<=3 " +
//					"      THEN (b.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||b.cashbook_year " +
//					"      WHEN b.cashbook_month>=4 " +
//					"      THEN b.cashbook_year " +
//					"        ||'-' " +
//					"        ||(b.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_fund_receipt_by_office b " +
//					"  WHERE b.receipt_status  <>'C' " +
//					"  AND b.cashbook_year      ="+txtCB_Year+
//					"  AND b.cashbook_month     ="+txtCB_Month+
//				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//					"    b.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    6  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//					"    b.cashbook_year, " +
//					"    b.cashbook_month, " +
//					"    b.DATE_OF_TRANSFER     AS datevalue, " +
//					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//					"    b.VOUCHER_NO           AS voucher_no, " +
//					"    'FT'                   AS document_type, " +
//					"    'DR'                   AS CR_DR_INDICATOR, " +
//					"    b.total_amount         AS dr_amt, " +
//					"    0                      AS cr_amt, " +
//					"    b.total_amount         AS amount, " +
//					"    b.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN b.cashbook_month<=3 " +
//					"      THEN (b.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||b.cashbook_year " +
//					"      WHEN b.cashbook_month>=4 " +
//					"      THEN b.cashbook_year " +
//					"        ||'-' " +
//					"        ||(b.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_fund_trf_from_office b " +
//					"  WHERE b.transfer_status <>'C' " +
//					"  AND b.cashbook_year      ="+txtCB_Year+
//					"  AND b.cashbook_month     ="+txtCB_Month+
//				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//					"    b.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    6  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//					"    b.cashbook_year, " +
//					"    b.cashbook_month, " +
//					"    b.DATE_OF_TRANSFER     AS datevalue, " +
//					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//					"    b.VOUCHER_NO           AS voucher_no, " +
//					"    'FT'                   AS document_type, " +
//					"    'CR'                   AS CR_DR_INDICATOR, " +
//					"    0                      AS dr_amt, " +
//					"    b.total_amount         AS cr_amt, " +
//					"    b.total_amount         AS amount, " +
//					"    b.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN b.cashbook_month<=3 " +
//					"      THEN (b.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||b.cashbook_year " +
//					"      WHEN b.cashbook_month>=4 " +
//					"      THEN b.cashbook_year " +
//					"        ||'-' " +
//					"        ||(b.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_fund_trf_from_office b " +
//					"  WHERE b.transfer_status <>'C' " +
//					"  AND b.cashbook_year      ="+txtCB_Year+
//					"  AND b.cashbook_month     ="+txtCB_Month+
//				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//					"    b.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    6  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//					"    b.cashbook_year, " +
//					"    b.cashbook_month, " +
//					"    b.RECEIPT_DATE         AS datevalue, " +
//					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//					"    b.RECEIPT_NO           AS voucher_no, " +
//					"    'FT'                   AS document_type, " +
//					"    'CR'                   AS Cr_Dr_Indicator, " +
//					"    0                      AS dr_amt, " +
//					"    b.total_amount         AS cr_amt, " +
//					"    b.total_amount         AS amount, " +
//					"    b.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN b.cashbook_month<=3 " +
//					"      THEN (b.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||b.cashbook_year " +
//					"      WHEN b.cashbook_month>=4 " +
//					"      THEN b.cashbook_year " +
//					"        ||'-' " +
//					"        ||(b.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_fund_receipt_by_ho b " +
//					"  WHERE b.receipt_status  <>'C' " +
//					"  AND b.cashbook_year      ="+txtCB_Year+
//					"  AND b.cashbook_month     ="+txtCB_Month+
//				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//					"    b.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    6  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//					//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//					"    b.cashbook_year, " +
//					"    b.cashbook_month, " +
//					"    b.RECEIPT_DATE         AS datevalue, " +
//					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//					"    B.Receipt_No           AS Voucher_No, " +
//					"    'FRH'                  AS Document_Type, " +
//					"    'DR'                   AS Cr_Dr_Indicator, " +
//					"    B.Total_Amount         AS Dr_Amt, " +
//					"    0                      AS cr_amt, " +
//					"    b.total_amount         AS amount, " +
//					"    b.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN b.cashbook_month<=3 " +
//					"      THEN (b.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||b.cashbook_year " +
//					"      WHEN b.cashbook_month>=4 " +
//					"      THEN b.cashbook_year " +
//					"        ||'-' " +
//					"        ||(b.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_fund_receipt_by_ho b " +
//					"  WHERE b.receipt_status  <>'C' " +
//					"  AND b.cashbook_year      ="+txtCB_Year+
//					"  AND b.cashbook_month     ="+txtCB_Month+
//					//"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//					"    a.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    6  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//					"    a.cashbook_year, " +
//					"    a.cashbook_month, " +
//					"    b.date_of_transfer AS datevalue, " +
//					"    a.ACCOUNT_HEAD_CODE, " +
//					"    B.Voucher_No AS Voucher_No, " +
//					"    'FTH'        AS Document_Type, " +
//					"    'DR'         AS CR_DR_INDICATOR, " +
//					"    CASE " +
//					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//					"      THEN a.amount " +
//					"      ELSE 0 " +
//					"    END AS dr_amt, " +
//					"    CASE " +
//					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//					"      THEN a.amount " +
//					"      ELSE 0 " +
//					"    END      AS cr_amt, " +
//					"    a.amount AS amount, " +
//					"    a.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN a.cashbook_month<=3 " +
//					"      THEN (a.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||a.cashbook_year " +
//					"      WHEN a.cashbook_month>=4 " +
//					"      THEN a.cashbook_year " +
//					"        ||'-' " +
//					"        ||(a.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_fund_trf_from_ho_trn a, " +
//					"    fas_fund_trf_from_ho_master b " +
//					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//					"  AND a.cashbook_year            = b.cashbook_year " +
//					"  AND a.cashbook_month           = b.cashbook_month " +
//					"  AND a.voucher_no               = b.voucher_no " +
//					"  AND b.TRANSFER_STATUS         <>'C' " +
//					"  AND a.cashbook_year            ="+txtCB_Year+
//					"  AND a.cashbook_month           ="+txtCB_Month+
//					//"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//					"  ) " +
//					" UNION ALL " +
//					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//					"    a.accounting_unit_id, " +
//					"    '' AS paid_to, " +
//					"    6  AS sub_ledger_type_code, " +
//					"    0  AS SUB_LEDGER_CODE, " +
//				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//					"    a.cashbook_year, " +
//					"    a.cashbook_month, " +
//					"    b.date_of_transfer AS datevalue, " +
//					"    b.account_head_code , " +
//					"    B.Voucher_No AS Voucher_No, " +
//					"    'FTH'        AS Document_Type, " +
//					"    'CR'         AS CR_DR_INDICATOR, " +
//					"    0            AS dr_amt, " +
//					"    a.amount     AS cr_amt, " +
//					"    a.amount     AS amount, " +
//					"    a.particulars, " +
//					"    0 AS sup_no, " +
//					"    CASE " +
//					"      WHEN a.cashbook_month<=3 " +
//					"      THEN (a.cashbook_year-1) " +
//					"        ||'-' " +
//					"        ||a.cashbook_year " +
//					"      WHEN a.cashbook_month>=4 " +
//					"      THEN a.cashbook_year " +
//					"        ||'-' " +
//					"        ||(a.cashbook_year+1) " +
//					"    END fin_year " +
//					"  FROM fas_fund_trf_from_ho_trn a, " +
//					"    fas_fund_trf_from_ho_master b " +
//					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//					"  AND a.cashbook_year            = b.cashbook_year " +
//					"  AND a.cashbook_month           = b.cashbook_month " +
//					"  AND a.voucher_no               = b.voucher_no " +
//					"  AND b.TRANSFER_STATUS         <>'C' " +
//					"  AND a.cashbook_year            ="+txtCB_Year+
//					"  AND a.cashbook_month           ="+txtCB_Month+
//					//"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//					"  ))t " +
//					" INNER JOIN FAS_MST_ACCT_UNITS u " +
//					" ON t.accounting_unit_id=u.accounting_unit_id " +
//					" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
//					" ON t.account_head_code=c.account_head_code " +
//					" GROUP BY t.accounting_unit_id, " +
//					"  u.ACCOUNTING_UNIT_NAME, " +
//					"  t.account_head_code, " +
//					"  c.ACCOUNT_HEAD_DESC, " +
//					"  t.cashbook_year, " +
//					"  t.cashbook_month " 
//				+sub_qry;
//            	
//            	}
//            	else if((txtCB_Month==3) && (rptsel.equalsIgnoreCase("Supplement")))
//            	{
//
//                	sql_Qry="SELECT 1 AS data_tb, " +
//    					"  t.accounting_unit_id, " +
//    					"  u.ACCOUNTING_UNIT_NAME, " +
//    					"  t.account_head_code , " +
//    					"  c.ACCOUNT_HEAD_DESC, " +
//    					"  t.cashbook_year, " +
//    					"  t.cashbook_month, " +
//    					"  SUM(t.dr_amt) AS dr_amount, " +
//    					"  SUM(t.cr_amt) AS cr_amount " +
//    					"FROM ( " +
//    					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    b.paid_to AS paid_to, " +
//    					"    a.sub_ledger_type_code, " +
//    					"    a.SUB_LEDGER_CODE, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.payment_date AS datevalue, " +
//    					"    a.ACCOUNT_HEAD_CODE, " +
//    					"    b.voucher_no AS voucher_no, " +
//    					"    'P'          AS document_type, " +
//    					"    a.CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN a.cr_dr_indicator = 'DR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END      AS cr_amt, " +
//    					"    a.amount AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_payment_transaction a, " +
//    					"    fas_payment_master b " +
//    					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.voucher_no               = b.voucher_no " +
//    					"  AND b.payment_status          <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					"UNION ALL " +
//    					"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    b.paid_to AS paid_to, " +
//    					"    b.sub_ledger_type_code, " +
//    					"    b.sub_ledger_code, " +
//    					"    B.cashbook_year, " +
//    					"    B.cashbook_month, " +
//    					"    b.payment_date AS datevalue, " +
//    					"    b.Account_Head_Code, " +
//    					"    b.voucher_no AS voucher_no, " +
//    					"    'P'          AS Document_Type, " +
//    					"    'CR'         AS Cr_Dr_Indicator, " +
//    					"    CASE " +
//    					"      WHEN CR_DR_INDICATOR = 'DR' " +
//    					"      THEN B.TOTAL_AMOUNT " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN CR_DR_INDICATOR = 'CR' " +
//    					"      THEN B.TOTAL_AMOUNT " +
//    					"      ELSE 0 " +
//    					"    END            AS Cr_Amt, " +
//    					"    b.total_amount AS amount, " +
//    					"    b.remarks      AS particulars, " +
//    					"    0              AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_payment_master b " +
//    					"  WHERE b.payment_status  <>'C' " +
//    					"  AND b.cashbook_year      = "+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					"UNION ALL " +
//    					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    b.received_from AS paid_to, " +
//    					"    a.sub_ledger_type_code, " +
//    					"    a.sub_ledger_code, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.receipt_date AS datevalue, " +
//    					"    a.ACCOUNT_HEAD_CODE, " +
//    					"    b.receipt_no AS voucher_no , " +
//    					"    'R'          AS document_type, " +
//    					"    a.CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END      AS cr_amt, " +
//    					"    a.amount AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_receipt_transaction a, " +
//    					"    fas_receipt_master b " +
//    					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.receipt_no               = b.receipt_no " +
//    					"  AND b.receipt_status          <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					"UNION ALL " +
//    					"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    b.received_from AS paid_to, " +
//    					"    b.sub_ledger_type_code, " +
//    					"    b.SUB_LEDGER_CODE, " +
//    					"    B.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.receipt_date AS datevalue, " +
//    					"    b.account_head_code , " +
//    					"    b.receipt_no AS voucher_no , " +
//    					"    'R'          AS document_type, " +
//    					"    b.CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN b.CR_DR_INDICATOR = 'DR' " +
//    					"      THEN b.TOTAL_AMOUNT " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN b.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN b.TOTAL_AMOUNT " +
//    					"      ELSE 0 " +
//    					"    END            AS cr_amt, " +
//    					"    B.Total_Amount AS Amount, " +
//    					"    b.remarks      AS particulars, " +
//    					"    0              AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_receipt_master b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				///	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					"UNION ALL " +
//    					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    ' ' AS paid_to, " +
//    					"    a.sub_ledger_type_code, " +
//    					"    a.SUB_LEDGER_CODE, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.voucher_date AS datevalue, " +
//    					"    a.account_head_code , " +
//    					"    b.voucher_no AS voucher_no , " +
//    					"    'J'          AS document_type, " +
//    					"    a.CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END      AS cr_amt, " +
//    					"    a.amount AS amount, " +
//    					"    a.particulars, " +
//    					"    NVL(b.supplement_no,0) AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_journal_transaction a, " +
//    					"    fas_journal_master b " +
//    					"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.voucher_no               = b.voucher_no " +
//    					"  AND b.journal_status          <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    					" and b.SUPPLEMENT_NO            = "+sup+ 
//    				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT '' AS sub_ledger_type_desc, " +
//    					"    accounting_unit_id, " +
//    					"    '' AS Paid_To, " +
//    					"    0  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					"    cashbook_year, " +
//    					"    cashbook_month, " +
//    					"    Date_Of_Transfer     AS Datevalue, " +
//    					"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    voucher_no           AS voucher_no, " +
//    					"    'IBT1'               AS document_type, " +
//    					"    cr_dr_indicator, " +
//    					"    CASE " +
//    					"      WHEN cr_dr_indicator = 'DR' " +
//    					"      THEN amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN cr_dr_indicator = 'CR' " +
//    					"      THEN amount " +
//    					"      ELSE 0 " +
//    					"    END AS cr_amt, " +
//    					"    amount, " +
//    					"    particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN cashbook_month<=3 " +
//    					"      THEN (cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||cashbook_year " +
//    					"      WHEN cashbook_month>=4 " +
//    					"      THEN cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM " +
//    					"    (SELECT a.accounting_unit_id, " +
//    					"      '' AS sub_ledger_type_desc, " +
//    					"      A.CR_ACCOUNT_HEAD_CODE, " +
//    					"      Date_Of_Transfer, " +
//    					"      '' AS paid_received, " +
//    					"      a.cashbook_year, " +
//    					"      a.cashbook_month, " +
//    					"      Date_Of_Transfer AS Datevalue, " +
//    					"      a.VOUCHER_NO     AS voucher_no, " +
//    					"      'IBT'            AS document_type, " +
//    					"      'CR'             AS cr_dr_indicator, " +
//    					"      total_amount     AS amount, " +
//    					"      particulars " +
//    					"    FROM fas_inter_bank_trf_at_ho a " +
//    					"    WHERE a.TRANSFER_STATUS <>'C' " +
//    					"    AND a.cashbook_year      ="+txtCB_Year+
//    					"    AND a.cashbook_month     ="+txtCB_Month+
//    				//	"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"    ) " +
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT '' AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    0  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    a.DATE_OF_TRANSFER     AS datevalue, " +
//    					"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    a.voucher_no           AS voucher_no, " +
//    					"    'IBT'                  AS document_type, " +
//    					"    'DR'                   AS CR_DR_INDICATOR, " +
//    					"    a.TOTAL_AMOUNT         AS dr_amt, " +
//    					"    0                      AS cr_amt, " +
//    					"    a.total_amount         AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_inter_bank_trf_at_ho a " +
//    					"  WHERE a.transfer_status  ='L' " +
//    					"  AND a.cashbook_year      ="+txtCB_Year+
//    					"  AND a.cashbook_month     ="+txtCB_Month+
//    				//	"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.RECEIPT_DATE         AS datevalue, " +
//    					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.RECEIPT_NO           AS voucher_no, " +
//    					"    'FR'                   AS document_type, " +
//    					"    'CR'                   AS CR_DR_INDICATOR, " +
//    					"    0                      AS dr_amt, " +
//    					"    b.total_amount         AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_receipt_by_office b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    					//"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.RECEIPT_DATE         AS datevalue, " +
//    					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.RECEIPT_NO           AS voucher_no, " +
//    					"    'FR'                   AS document_type, " +
//    					"    'DR'                   AS CR_DR_INDICATOR, " +
//    					"    b.total_amount         AS dr_amt, " +
//    					"    0                      AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_receipt_by_office b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.DATE_OF_TRANSFER     AS datevalue, " +
//    					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.VOUCHER_NO           AS voucher_no, " +
//    					"    'FT'                   AS document_type, " +
//    					"    'DR'                   AS CR_DR_INDICATOR, " +
//    					"    b.total_amount         AS dr_amt, " +
//    					"    0                      AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_trf_from_office b " +
//    					"  WHERE b.transfer_status <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.DATE_OF_TRANSFER     AS datevalue, " +
//    					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.VOUCHER_NO           AS voucher_no, " +
//    					"    'FT'                   AS document_type, " +
//    					"    'CR'                   AS CR_DR_INDICATOR, " +
//    					"    0                      AS dr_amt, " +
//    					"    b.total_amount         AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_trf_from_office b " +
//    					"  WHERE b.transfer_status <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.RECEIPT_DATE         AS datevalue, " +
//    					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.RECEIPT_NO           AS voucher_no, " +
//    					"    'FT'                   AS document_type, " +
//    					"    'CR'                   AS Cr_Dr_Indicator, " +
//    					"    0                      AS dr_amt, " +
//    					"    b.total_amount         AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_receipt_by_ho b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.RECEIPT_DATE         AS datevalue, " +
//    					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    B.Receipt_No           AS Voucher_No, " +
//    					"    'FRH'                  AS Document_Type, " +
//    					"    'DR'                   AS Cr_Dr_Indicator, " +
//    					"    B.Total_Amount         AS Dr_Amt, " +
//    					"    0                      AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_receipt_by_ho b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    					//"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.date_of_transfer AS datevalue, " +
//    					"    a.ACCOUNT_HEAD_CODE, " +
//    					"    B.Voucher_No AS Voucher_No, " +
//    					"    'FTH'        AS Document_Type, " +
//    					"    'DR'         AS CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END      AS cr_amt, " +
//    					"    a.amount AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_trf_from_ho_trn a, " +
//    					"    fas_fund_trf_from_ho_master b " +
//    					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.voucher_no               = b.voucher_no " +
//    					"  AND b.TRANSFER_STATUS         <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    					//"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.date_of_transfer AS datevalue, " +
//    					"    b.account_head_code , " +
//    					"    B.Voucher_No AS Voucher_No, " +
//    					"    'FTH'        AS Document_Type, " +
//    					"    'CR'         AS CR_DR_INDICATOR, " +
//    					"    0            AS dr_amt, " +
//    					"    a.amount     AS cr_amt, " +
//    					"    a.amount     AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_trf_from_ho_trn a, " +
//    					"    fas_fund_trf_from_ho_master b " +
//    					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.voucher_no               = b.voucher_no " +
//    					"  AND b.TRANSFER_STATUS         <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    					//"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ))t " +
//    					" INNER JOIN FAS_MST_ACCT_UNITS u " +
//    					" ON t.accounting_unit_id=u.accounting_unit_id " +
//    					" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
//    					" ON t.account_head_code=c.account_head_code " +
//    					" GROUP BY t.accounting_unit_id, " +
//    					"  u.ACCOUNTING_UNIT_NAME, " +
//    					"  t.account_head_code, " +
//    					"  c.ACCOUNT_HEAD_DESC, " +
//    					"  t.cashbook_year, " +
//    					"  t.cashbook_month " 
//    				+sub_qry;
//                	
//            	}
//            }
//            	else
//            	{
//
//                	
//                	sql_Qry="SELECT 1 AS data_tb, " +
//    					"  t.accounting_unit_id, " +
//    					"  u.ACCOUNTING_UNIT_NAME, " +
//    					"  t.account_head_code , " +
//    					"  c.ACCOUNT_HEAD_DESC, " +
//    					"  t.cashbook_year, " +
//    					"  t.cashbook_month, " +
//    					"  SUM(t.dr_amt) AS dr_amount, " +
//    					"  SUM(t.cr_amt) AS cr_amount " +
//    					"FROM ( " +
//    					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    b.paid_to AS paid_to, " +
//    					"    a.sub_ledger_type_code, " +
//    					"    a.SUB_LEDGER_CODE, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.payment_date AS datevalue, " +
//    					"    a.ACCOUNT_HEAD_CODE, " +
//    					"    b.voucher_no AS voucher_no, " +
//    					"    'P'          AS document_type, " +
//    					"    a.CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN a.cr_dr_indicator = 'DR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END      AS cr_amt, " +
//    					"    a.amount AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_payment_transaction a, " +
//    					"    fas_payment_master b " +
//    					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.voucher_no               = b.voucher_no " +
//    					"  AND b.payment_status          <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					"UNION ALL " +
//    					"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-') AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    b.paid_to AS paid_to, " +
//    					"    b.sub_ledger_type_code, " +
//    					"    b.sub_ledger_code, " +
//    					"    B.cashbook_year, " +
//    					"    B.cashbook_month, " +
//    					"    b.payment_date AS datevalue, " +
//    					"    b.Account_Head_Code, " +
//    					"    b.voucher_no AS voucher_no, " +
//    					"    'P'          AS Document_Type, " +
//    					"    'CR'         AS Cr_Dr_Indicator, " +
//    					"    CASE " +
//    					"      WHEN CR_DR_INDICATOR = 'DR' " +
//    					"      THEN B.TOTAL_AMOUNT " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN CR_DR_INDICATOR = 'CR' " +
//    					"      THEN B.TOTAL_AMOUNT " +
//    					"      ELSE 0 " +
//    					"    END            AS Cr_Amt, " +
//    					"    b.total_amount AS amount, " +
//    					"    b.remarks      AS particulars, " +
//    					"    0              AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_payment_master b " +
//    					"  WHERE b.payment_status  <>'C' " +
//    					"  AND b.cashbook_year      = "+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					"UNION ALL " +
//    					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    b.received_from AS paid_to, " +
//    					"    a.sub_ledger_type_code, " +
//    					"    a.sub_ledger_code, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.receipt_date AS datevalue, " +
//    					"    a.ACCOUNT_HEAD_CODE, " +
//    					"    b.receipt_no AS voucher_no , " +
//    					"    'R'          AS document_type, " +
//    					"    a.CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END      AS cr_amt, " +
//    					"    a.amount AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_receipt_transaction a, " +
//    					"    fas_receipt_master b " +
//    					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.receipt_no               = b.receipt_no " +
//    					"  AND b.receipt_status          <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					"UNION ALL " +
//    					"  (SELECT DECODE(b.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    b.received_from AS paid_to, " +
//    					"    b.sub_ledger_type_code, " +
//    					"    b.SUB_LEDGER_CODE, " +
//    					"    B.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.receipt_date AS datevalue, " +
//    					"    b.account_head_code , " +
//    					"    b.receipt_no AS voucher_no , " +
//    					"    'R'          AS document_type, " +
//    					"    b.CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN b.CR_DR_INDICATOR = 'DR' " +
//    					"      THEN b.TOTAL_AMOUNT " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN b.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN b.TOTAL_AMOUNT " +
//    					"      ELSE 0 " +
//    					"    END            AS cr_amt, " +
//    					"    B.Total_Amount AS Amount, " +
//    					"    b.remarks      AS particulars, " +
//    					"    0              AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_receipt_master b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				///	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					"UNION ALL " +
//    					"  (SELECT DECODE(a.sub_ledger_type_code,1,'Supplier',2,'Firms',10,'Assets',4,'Cheque Books',5,'Offices',6,'Bank',7,'Employees',8,'-',9,'Other Departments',10,'Project',11,'Contractors',12,'Scheme Owner',13,'Beneficiary','-')AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    ' ' AS paid_to, " +
//    					"    a.sub_ledger_type_code, " +
//    					"    a.SUB_LEDGER_CODE, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.voucher_date AS datevalue, " +
//    					"    a.account_head_code , " +
//    					"    b.voucher_no AS voucher_no , " +
//    					"    'J'          AS document_type, " +
//    					"    a.CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END      AS cr_amt, " +
//    					"    a.amount AS amount, " +
//    					"    a.particulars, " +
//    					"    NVL(b.supplement_no,0) AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_journal_transaction a, " +
//    					"    fas_journal_master b " +
//    					"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.voucher_no               = b.voucher_no " +
//    					"  AND b.journal_status          <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    				//	"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT '' AS sub_ledger_type_desc, " +
//    					"    accounting_unit_id, " +
//    					"    '' AS Paid_To, " +
//    					"    0  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					"    cashbook_year, " +
//    					"    cashbook_month, " +
//    					"    Date_Of_Transfer     AS Datevalue, " +
//    					"    CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    voucher_no           AS voucher_no, " +
//    					"    'IBT1'               AS document_type, " +
//    					"    cr_dr_indicator, " +
//    					"    CASE " +
//    					"      WHEN cr_dr_indicator = 'DR' " +
//    					"      THEN amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN cr_dr_indicator = 'CR' " +
//    					"      THEN amount " +
//    					"      ELSE 0 " +
//    					"    END AS cr_amt, " +
//    					"    amount, " +
//    					"    particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN cashbook_month<=3 " +
//    					"      THEN (cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||cashbook_year " +
//    					"      WHEN cashbook_month>=4 " +
//    					"      THEN cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM " +
//    					"    (SELECT a.accounting_unit_id, " +
//    					"      '' AS sub_ledger_type_desc, " +
//    					"      A.CR_ACCOUNT_HEAD_CODE, " +
//    					"      Date_Of_Transfer, " +
//    					"      '' AS paid_received, " +
//    					"      a.cashbook_year, " +
//    					"      a.cashbook_month, " +
//    					"      Date_Of_Transfer AS Datevalue, " +
//    					"      a.VOUCHER_NO     AS voucher_no, " +
//    					"      'IBT'            AS document_type, " +
//    					"      'CR'             AS cr_dr_indicator, " +
//    					"      total_amount     AS amount, " +
//    					"      particulars " +
//    					"    FROM fas_inter_bank_trf_at_ho a " +
//    					"    WHERE a.TRANSFER_STATUS <>'C' " +
//    					"    AND a.cashbook_year      ="+txtCB_Year+
//    					"    AND a.cashbook_month     ="+txtCB_Month+
//    				//	"    AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"    ) " +
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT '' AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    0  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    a.DATE_OF_TRANSFER     AS datevalue, " +
//    					"    a.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    a.voucher_no           AS voucher_no, " +
//    					"    'IBT'                  AS document_type, " +
//    					"    'DR'                   AS CR_DR_INDICATOR, " +
//    					"    a.TOTAL_AMOUNT         AS dr_amt, " +
//    					"    0                      AS cr_amt, " +
//    					"    a.total_amount         AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_inter_bank_trf_at_ho a " +
//    					"  WHERE a.transfer_status  ='L' " +
//    					"  AND a.cashbook_year      ="+txtCB_Year+
//    					"  AND a.cashbook_month     ="+txtCB_Month+
//    				//	"  AND a.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.RECEIPT_DATE         AS datevalue, " +
//    					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.RECEIPT_NO           AS voucher_no, " +
//    					"    'FR'                   AS document_type, " +
//    					"    'CR'                   AS CR_DR_INDICATOR, " +
//    					"    0                      AS dr_amt, " +
//    					"    b.total_amount         AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_receipt_by_office b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    					//"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.RECEIPT_DATE         AS datevalue, " +
//    					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.RECEIPT_NO           AS voucher_no, " +
//    					"    'FR'                   AS document_type, " +
//    					"    'DR'                   AS CR_DR_INDICATOR, " +
//    					"    b.total_amount         AS dr_amt, " +
//    					"    0                      AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_receipt_by_office b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.DATE_OF_TRANSFER     AS datevalue, " +
//    					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.VOUCHER_NO           AS voucher_no, " +
//    					"    'FT'                   AS document_type, " +
//    					"    'DR'                   AS CR_DR_INDICATOR, " +
//    					"    b.total_amount         AS dr_amt, " +
//    					"    0                      AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_trf_from_office b " +
//    					"  WHERE b.transfer_status <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.DATE_OF_TRANSFER     AS datevalue, " +
//    					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.VOUCHER_NO           AS voucher_no, " +
//    					"    'FT'                   AS document_type, " +
//    					"    'CR'                   AS CR_DR_INDICATOR, " +
//    					"    0                      AS dr_amt, " +
//    					"    b.total_amount         AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_trf_from_office b " +
//    					"  WHERE b.transfer_status <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.RECEIPT_DATE         AS datevalue, " +
//    					"    b.CR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    b.RECEIPT_NO           AS voucher_no, " +
//    					"    'FT'                   AS document_type, " +
//    					"    'CR'                   AS Cr_Dr_Indicator, " +
//    					"    0                      AS dr_amt, " +
//    					"    b.total_amount         AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_receipt_by_ho b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    				//	"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    b.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    					//"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    b.cashbook_year, " +
//    					"    b.cashbook_month, " +
//    					"    b.RECEIPT_DATE         AS datevalue, " +
//    					"    b.DR_ACCOUNT_HEAD_CODE AS account_head_code, " +
//    					"    B.Receipt_No           AS Voucher_No, " +
//    					"    'FRH'                  AS Document_Type, " +
//    					"    'DR'                   AS Cr_Dr_Indicator, " +
//    					"    B.Total_Amount         AS Dr_Amt, " +
//    					"    0                      AS cr_amt, " +
//    					"    b.total_amount         AS amount, " +
//    					"    b.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN b.cashbook_month<=3 " +
//    					"      THEN (b.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||b.cashbook_year " +
//    					"      WHEN b.cashbook_month>=4 " +
//    					"      THEN b.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(b.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_receipt_by_ho b " +
//    					"  WHERE b.receipt_status  <>'C' " +
//    					"  AND b.cashbook_year      ="+txtCB_Year+
//    					"  AND b.cashbook_month     ="+txtCB_Month+
//    					//"  AND b.accounting_unit_id ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.date_of_transfer AS datevalue, " +
//    					"    a.ACCOUNT_HEAD_CODE, " +
//    					"    B.Voucher_No AS Voucher_No, " +
//    					"    'FTH'        AS Document_Type, " +
//    					"    'DR'         AS CR_DR_INDICATOR, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'DR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END AS dr_amt, " +
//    					"    CASE " +
//    					"      WHEN a.CR_DR_INDICATOR = 'CR' " +
//    					"      THEN a.amount " +
//    					"      ELSE 0 " +
//    					"    END      AS cr_amt, " +
//    					"    a.amount AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_trf_from_ho_trn a, " +
//    					"    fas_fund_trf_from_ho_master b " +
//    					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.voucher_no               = b.voucher_no " +
//    					"  AND b.TRANSFER_STATUS         <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    					//"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ) " +
//    					" UNION ALL " +
//    					"  (SELECT 'Bank' AS sub_ledger_type_desc, " +
//    					"    a.accounting_unit_id, " +
//    					"    '' AS paid_to, " +
//    					"    6  AS sub_ledger_type_code, " +
//    					"    0  AS SUB_LEDGER_CODE, " +
//    				//	"    -- b.OFFICE_ACCOUNT_NO as sldesc, " +
//    					"    a.cashbook_year, " +
//    					"    a.cashbook_month, " +
//    					"    b.date_of_transfer AS datevalue, " +
//    					"    b.account_head_code , " +
//    					"    B.Voucher_No AS Voucher_No, " +
//    					"    'FTH'        AS Document_Type, " +
//    					"    'CR'         AS CR_DR_INDICATOR, " +
//    					"    0            AS dr_amt, " +
//    					"    a.amount     AS cr_amt, " +
//    					"    a.amount     AS amount, " +
//    					"    a.particulars, " +
//    					"    0 AS sup_no, " +
//    					"    CASE " +
//    					"      WHEN a.cashbook_month<=3 " +
//    					"      THEN (a.cashbook_year-1) " +
//    					"        ||'-' " +
//    					"        ||a.cashbook_year " +
//    					"      WHEN a.cashbook_month>=4 " +
//    					"      THEN a.cashbook_year " +
//    					"        ||'-' " +
//    					"        ||(a.cashbook_year+1) " +
//    					"    END fin_year " +
//    					"  FROM fas_fund_trf_from_ho_trn a, " +
//    					"    fas_fund_trf_from_ho_master b " +
//    					"  WHERE a.accounting_unit_id     = b.accounting_unit_id " +
//    					"  AND a.accounting_for_office_id = b.accounting_for_office_id " +
//    					"  AND a.cashbook_year            = b.cashbook_year " +
//    					"  AND a.cashbook_month           = b.cashbook_month " +
//    					"  AND a.voucher_no               = b.voucher_no " +
//    					"  AND b.TRANSFER_STATUS         <>'C' " +
//    					"  AND a.cashbook_year            ="+txtCB_Year+
//    					"  AND a.cashbook_month           ="+txtCB_Month+
//    					//"  AND a.accounting_unit_id       ="+cmbAcc_UnitCode+
//    					"  ))t " +
//    					" INNER JOIN FAS_MST_ACCT_UNITS u " +
//    					" ON t.accounting_unit_id=u.accounting_unit_id " +
//    					" INNER JOIN COM_MST_ACCOUNT_HEADS c " +
//    					" ON t.account_head_code=c.account_head_code " +
//    					" GROUP BY t.accounting_unit_id, " +
//    					"  u.ACCOUNTING_UNIT_NAME, " +
//    					"  t.account_head_code, " +
//    					"  c.ACCOUNT_HEAD_DESC, " +
//    					"  t.cashbook_year, " +
//    					"  t.cashbook_month " 
//    				+sub_qry;
//                	
//                	
//            	}
//            	
//            	
//            	
//		}
            System.out.println("reportFile >> "+reportFile);
System.out.println("sql_Qry >> "+sql_Qry);
            map.put("qry", sql_Qry);
            map.put("acc_unit_id", cmbAcc_UnitCode);
            map.put("off_id", cmbOffice_code);
            map.put("yr", txtCB_Year);
            map.put("mon", txtCB_Month);
            map.put("monthInWords", monthInWords);
            
            if ((txtCB_Month==3) && (rptsel.equalsIgnoreCase("Supplement")) &&
        			headwise.equalsIgnoreCase("unitwise_Abstract"))
            {
            	map.put("heading", "Trial Balance Supplement Data for the Month of "+monthInWords+" "+txtCB_Year);
            }
            else
            {
            map.put("heading", "Trial Balance Data for the Month of "+monthInWords+" "+txtCB_Year);
            }
            // map.put("vouType",txtCreat_By_Module);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, con);
            System.out.println("upto");
           
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"TrialBalance.html\"");
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
                System.out.println(rtype);
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"TrialBalance.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"TrialBalance.xls\"");
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
                                   "attachment;filename=\"Journal_forCashBookMonth.txt\"");

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
                "Could not create the report " + ex.getMessage() + " uu " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }
}