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
public class Fas_WorksExpnd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static  String CONTENT_TYPE = 
            "text/xml; charset=windows-1252";
        Connection connection = null;
    public Fas_WorksExpnd() {
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
        
        String opt="";
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
        String head=""; int wE_Type=0;
        int To_year=0;
        int from_Month=0;
        int To_Month=0,sup_no=0,sup_no1=0,unit_id=0;
        String  command="",fin_year="",sup_qry="",sup_query="",f_year="",Str_qry="",xml="";
        String fin_FY="",fin_TY="",f_mnth="",t_mnth="",qry="",unit_qry="";int WE_REport=0;
       int chk_cons=0;
        String year[]=null,a[]=null,b[]=null; 
         command=request.getParameter("cmd");
         unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        String Re_Type="",Second_qry="";
	     try{   
if(command.equalsIgnoreCase("live"))	    	 
{

	wE_Type=Integer.parseInt(request.getParameter("WE_R"));
	System.out.println("WE_R >>> "+wE_Type);
	String WE_R="",head_Qry="";
	WE_REport=Integer.parseInt(request.getParameter("LReport"));
	Re_Type=request.getParameter("rad_R");
	   fin_FY=request.getParameter("liveFY");
       fin_TY=request.getParameter("liveTY");
       f_mnth=request.getParameter("fromMonth");
       t_mnth=request.getParameter("toMonth");
     
       if (unit_id==0)unit_qry="";
       if (unit_id!=0)unit_qry=" and   ACCOUNTING_UNIT_ID="+unit_id ; 
       if(f_mnth.equalsIgnoreCase(t_mnth))
    	   {
    		  sup_query=   " AND cmonth          = '"+f_mnth+"' " ;
    		 head= "Works Expenditure Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
    	   } if(!f_mnth.equalsIgnoreCase(t_mnth))
    	   {
    		  // System.out.println(" >>> "+Integer.parseInt(f_mnth.split("-")[1]));
    		   if(f_mnth.split("-")[1].equalsIgnoreCase("APR"))
    	        {
    	 		   head= "Works Expenditure Details for the Month Upto  "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
    	        }
    		   else{
    		   head= "Works Expenditure Details for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
    		   }
    		   sup_query= " AND cmonth   between '"+f_mnth+"' and '"+t_mnth+"'   " ;
    		   }
    	   if(wE_Type!=1){
    		   WE_R= "  AND Fh.Minorgroupingid='"+wE_Type+"' " ;
    	   }else
    	   {
    		   WE_R="";
    	   }
    	   System.out.println(WE_REport+" >>> "+wE_Type);
    	   if(WE_REport==1){ 
               qry="SELECT t.Data_Tb, " +
               "  t.Schemeid, " +
               "  t.Schemename, " +
               "  SUM(t.DR_AMOUNT)DR_AMOUNT, " +
               "  SUM(t.CR_AMOUNT)CR_AMOUNT, " +
             //  "  SUM(t.Net_Amt)Net_Amt, " +
               "  SUM(t.Funds)Funds, " +
               "  SUM(t.Works_Expenditure)Works_Expenditure, " +
               "  SUM(t.Centage)Centage, " +
               "  SUM(t.Interest_Charged)Interest_Charged, " +
               "  SUM(t.Materials)Materials, " +
               "  SUM(t.Funds)+  SUM(t.Works_Expenditure)+  SUM(t.Centage)+  SUM(t.Interest_Charged)+  SUM(t.Materials) as Net_Amt,  "+
               "  t.Fin_Year " +
               "FROM " +
               "  (SELECT 1                 AS Data_Tb, " +
               "    to_number(Fh.Projectid) AS Projectid, " +
               "    Pr.Projectname, " +
               "    Fh.Programid, " +
               "    Pg.Programname, " +
               "    Fh.Schemeid, " +
               "    Ps.Schemename, " +
               "    V.Account_Head_Code, " +
               "    Fh.Headofaccount, " +
               "    Sub_Ledger_Type_Code, " +
               "    Sub_Ledger_Code, " +
               "    SUM(dr_amt)DR_AMOUNT, " +
               "    SUM(CR_AMT)CR_AMOUNT, " +
               "    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , " +
               "    Fh.Activityid, " +
               "    Ac.Activityname, " +
               "    CASE Fh.activityid " +
               "      WHEN '1' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Funds, " +
               "    CASE Fh.activityid " +
               "      WHEN '2' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Works_Expenditure, " +
               "    CASE Fh.activityid " +
               "      WHEN '3' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Centage, " +
               "    CASE Fh.activityid " +
               "      WHEN '4' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Interest_Charged, " +
               "    CASE Fh.activityid " +
               "      WHEN '15' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Materials, " +
               "    Fin_Year, " +
               "    SUB_LEDGER_TYPE_DESC " +
               "  FROM Fas_Wrkexp_View V " +
               "  INNER JOIN fas_Headofaccounts Fh " +
               "  ON V.Account_Head_Code=Fh.Accountcode " +
               "  INNER JOIN Activity Ac " +
               "  ON Fh.Activityid=Ac.Activityid " +
               "  INNER JOIN Project Pr " +
               "  ON Fh.Projectid=Pr.Projectid " +
               "  INNER JOIN Program Pg " +
               "  ON Fh.Programid=Pg.Programid " +
               "  INNER JOIN Schemes Ps " +
               "  ON Fh.Projectid =Ps.Projectid " +
               "  AND Fh.Programid=Ps.Programid " +
               "  AND fh.Schemeid =Ps.Schemeid " +
               sup_query+
        	   unit_qry+
          //     "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
        	   WE_R+
               "  GROUP BY To_Number (Fh.Projectid), " +
               "    Pr.Projectname, " +
               "    Fh.Programid, " +
               "    Pg.Programname, " +
               "    Fh.Schemeid, " +
               "    Ps.Schemename, " +
               "    V.Account_Head_Code, " +
               "    Fh.Headofaccount, " +
               "    Sub_Ledger_Type_Code, " +
               "    Sub_Ledger_Code, " +
               "    Fh.Activityid, " +
               "    Ac.Activityname, " +
               "    Fin_Year, " +
               "    SUB_LEDGER_TYPE_DESC " +
               "  )t " +
               "GROUP BY t.Data_Tb, " +
               "  t.Schemeid, " +
               "  t.Schemename, " +
               "  t.Fin_Year " +
               "ORDER BY t.Schemeid"
;
              }else if (WE_REport==2)
              {
            	  qry="SELECT t.Data_Tb, " +
            	  "  t.Schemeid, " +
            	  "  t.Schemename, " +
            	  "  t.Account_Head_Code, " +
            	  "  t.Headofaccount, " +
            	  "  SUM(t.DR_AMOUNT)DR_AMOUNT, " +
            	  "  SUM(t.CR_AMOUNT)CR_AMOUNT, " +
            	  "  SUM(t.Net_Amt)Net_Amt, " +
            	  "  SUM(t.Funds)Funds, " +
            	  "  SUM(t.Works_Expenditure)Works_Expenditure, " +
            	  "  SUM(t.Centage)Centage, " +
            	  "  SUM(t.Interest_Charged)Interest_Charged, " +
            	  "  SUM(t.Materials)Materials, " +
            	  "  t.Fin_Year " +
            	  "FROM " +
            	  "  (SELECT 1                 AS Data_Tb, " +
            	  "    to_number(Fh.Projectid) AS Projectid, " +
            	  "    Pr.Projectname, " +
            	  "    Fh.Programid, " +
            	  "    Pg.Programname, " +
            	  "    Fh.Schemeid, " +
            	  "    Ps.Schemename, " +
            	  "    V.Account_Head_Code, " +
            	  "    Fh.Headofaccount, " +
            	  "    Sub_Ledger_Type_Code, " +
            	  "    Sub_Ledger_Code, " +
            	  "    SUM(dr_amt)DR_AMOUNT, " +
            	  "    SUM(CR_AMT)CR_AMOUNT, " +
            	  "    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , " +
            	  "    Fh.Activityid, " +
            	  "    Ac.Activityname, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '1' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Funds, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '2' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Works_Expenditure, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '3' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Centage, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '4' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Interest_Charged, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '15' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Materials, " +
            	  "    Fin_Year, " +
            	  "    SUB_LEDGER_TYPE_DESC " +
            	  "  FROM Fas_Wrkexp_View V " +
            	  "  INNER JOIN fas_Headofaccounts Fh " +
            	  "  ON V.Account_Head_Code=Fh.Accountcode " +
            	  "  INNER JOIN Activity Ac " +
            	  "  ON Fh.Activityid=Ac.Activityid " +
            	  "  INNER JOIN Project Pr " +
            	  "  ON Fh.Projectid=Pr.Projectid " +
            	  "  INNER JOIN Program Pg " +
            	  "  ON Fh.Programid=Pg.Programid " +
            	  "  INNER JOIN Schemes Ps " +
            	  "  ON Fh.Projectid =Ps.Projectid " +
            	  "  AND Fh.Programid=Ps.Programid " +
            	  "  AND fh.Schemeid =Ps.Schemeid " +
            	    sup_query+unit_qry+
                //    "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
            	    WE_R+
            	  "  GROUP BY To_Number (Fh.Projectid), " +
            	  "    Pr.Projectname, " +
            	  "    Fh.Programid, " +
            	  "    Pg.Programname, " +
            	  "    Fh.Schemeid, " +
            	  "    Ps.Schemename, " +
            	  "    V.Account_Head_Code, " +
            	  "    Fh.Headofaccount, " +
            	  "    Sub_Ledger_Type_Code, " +
            	  "    Sub_Ledger_Code, " +
            	  "    Fh.Activityid, " +
            	  "    Ac.Activityname, " +
            	  "    Fin_Year, " +
            	  "    SUB_LEDGER_TYPE_DESC " +
            	  "  )t " +
            	  "GROUP BY t.Data_Tb, " +
            	  "  t.Schemeid, " +
            	  "  t.Schemename, " +
            	  "  t.Account_Head_Code, " +
            	  "  t.Headofaccount, " +
            	  "  t.Fin_Year " +
            	  "ORDER BY t.Schemeid, " +
            	  "  t.Account_Head_Code";
              }else if(WE_REport==3){    	   
    	   qry="SELECT t.Data_Tb, " +
    	   "  t.Projectid, " +
    	   "  t.Projectname, " +
    	   "  t.Programid, " +
    	   "  t.Programname, " +
    	   "  t.Schemeid, " +
    	   "  t.Schemename, " +
    	   "  t.Account_Head_Code, " +
    	   "  t.Headofaccount, " +
    	   "  t.Sub_Ledger_Type_Code, " +
    	   "  t.Sub_Ledger_Code, " +
    	   "  t.DR_AMOUNT, " +
    	   "  t.CR_AMOUNT, " +
    	   "  t.Net_Amt , " +
    	   "  t.Activityid, " +
    	   "  t.Activityname, " +
    	   "  t.Funds, " +
    	   "  t.Works_Expenditure, " +
    	   "  t.Centage, " +
    	   "  t.Interest_Charged, " +
    	   "  t.Materials, " +
    	   "  t.Fin_Year, " +
    	   "  t.SUB_LEDGER_TYPE_DESC, " +
    	   "  s.sl_codename AS SLDESC " +
    	   "FROM " +
    	   "  (SELECT 1                 AS Data_Tb, " +
    	   "    to_number(Fh.Projectid) AS Projectid, " +
    	   "    Pr.Projectname, " +
    	   "    Fh.Programid, " +
    	   "    Pg.Programname, " +
    	   "    Fh.Schemeid, " +
    	   "    Ps.Schemename, " +
    	   "    V.Account_Head_Code, " +
    	   "    Fh.Headofaccount, " +
    	   "    Sub_Ledger_Type_Code, " +
    	   "    Sub_Ledger_Code, " +
    	   "    SUM(dr_amt)DR_AMOUNT, " +
    	   "    SUM(CR_AMT)CR_AMOUNT, " +
    	   "    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , " +
    	   "    Fh.Activityid, " +
    	   "    Ac.Activityname, " +
    	   "    CASE Fh.activityid " +
    	   "      WHEN '1' " +
    	   "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
    	   "      ELSE 0 " +
    	   "    END AS Funds, " +
    	   "    CASE Fh.activityid " +
    	   "      WHEN '2' " +
    	   "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
    	   "      ELSE 0 " +
    	   "    END AS Works_Expenditure, " +
    	   "    CASE Fh.activityid " +
    	   "      WHEN '3' " +
    	   "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
    	   "      ELSE 0 " +
    	   "    END AS Centage, " +
    	   "    CASE Fh.activityid " +
    	   "      WHEN '4' " +
    	   "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
    	   "      ELSE 0 " +
    	   "    END AS Interest_Charged, " +
    	   "    CASE Fh.activityid " +
    	   "      WHEN '15' " +
    	   "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
    	   "      ELSE 0 " +
    	   "    END AS Materials, " +
    	   "    Fin_Year, " +
    	   "    SUB_LEDGER_TYPE_DESC " +
    	   "  FROM Fas_Wrkexp_View V " +
    	   "  INNER JOIN fas_Headofaccounts Fh " +
    	   "  ON V.Account_Head_Code=Fh.Accountcode " +
    	   "  INNER JOIN Activity Ac " +
    	   "  ON Fh.Activityid=Ac.Activityid " +
    	   "  INNER JOIN Project Pr " +
    	   "  ON Fh.Projectid=Pr.Projectid " +
    	   "  INNER JOIN Program Pg " +
    	   "  ON Fh.Programid=Pg.Programid " +
    	   "  INNER JOIN Schemes Ps " +
    	   "  ON Fh.Projectid =Ps.Projectid " +
    	   "  AND Fh.Programid=Ps.Programid " +
    	   "  AND fh.Schemeid =Ps.Schemeid " +
    	   sup_query+
		   unit_qry+
    	 //  "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
		   WE_R+
    	   "  GROUP BY To_Number (Fh.Projectid), " +
    	   "    Pr.Projectname, " +
    	   "    Fh.Programid, " +
    	   "    Pg.Programname, " +
    	   "    Fh.Schemeid, " +
    	   "    Ps.Schemename, " +
    	   "    V.Account_Head_Code, " +
    	   "    Fh.Headofaccount, " +
    	   "    Sub_Ledger_Type_Code, " +
    	   "    Sub_Ledger_Code, " +
    	   "    Fh.Activityid, " +
    	   "    Ac.Activityname, " +
    	   "    Fin_Year, " +
    	   "    SUB_LEDGER_TYPE_DESC " +
    	   "  )t " +
    	   "INNER JOIN sl_type_code_name_view s " +
    	   "ON t.Sub_Ledger_Type_Code=s.sl_type " +
    	   "AND t.Sub_Ledger_Code    =s.sl_code " +
    	   "ORDER BY t.Schemeid, " +
    	   "  t.Account_Head_Code, " +
    	   "  t.Sub_Ledger_Type_Code, " +
    	   "  t.Sub_Ledger_Code" ;
              }else if(WE_REport==5){
            	  
qry="SELECT t.Data_Tb, " +
"  t.Schemeid, " +
"  t.Schemename, " +
"  t.Account_Head_Code, " +
"  t.ACCOUNTING_UNIT_ID, " +
"  (SELECT trim(ACCOUNTING_UNIT_NAME) " +
"  FROM FAS_MST_ACCT_UNITS u " +
"  WHERE u.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID " +
"  ) AS unit_name, " +
"  t.Headofaccount, " +
"  SUM(t.DR_AMOUNT)DR_AMOUNT, " +
"  SUM(t.CR_AMOUNT)CR_AMOUNT, " +
"  SUM(t.Net_Amt)Net_Amt, " +
"  SUM(t.Funds)Funds, " +
"  SUM(t.Works_Expenditure)Works_Expenditure, " +
"  SUM(t.Centage)Centage, " +
"  SUM(t.Interest_Charged)Interest_Charged, " +
"  SUM(t.Materials)Materials, " +
"  t.Fin_Year " +
"FROM " +
"  (SELECT 1 AS Data_Tb, " +

"    to_number(Fh.Projectid) AS Projectid, " +
"    Pr.Projectname, " +
"    Fh.Programid, " +
"    Pg.Programname, " +
"    Fh.Schemeid, " +
"    Ps.Schemename, " +
"    V.Account_Head_Code, " +
"    ACCOUNTING_UNIT_ID, " +
"    Fh.Headofaccount, " +
"    Sub_Ledger_Type_Code, " +
"    Sub_Ledger_Code, " +
"    SUM(dr_amt)DR_AMOUNT, " +
"    SUM(CR_AMT)CR_AMOUNT, " +
"    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , " +
"    Fh.Activityid, " +
"    Ac.Activityname, " +
"    CASE Fh.activityid " +
"      WHEN '1' " +
"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"      ELSE 0 " +
"    END AS Funds, " +
"    CASE Fh.activityid " +
"      WHEN '2' " +
"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"      ELSE 0 " +
"    END AS Works_Expenditure, " +
"    CASE Fh.activityid " +
"      WHEN '3' " +
"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"      ELSE 0 " +
"    END AS Centage, " +
"    CASE Fh.activityid " +
"      WHEN '4' " +
"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"      ELSE 0 " +
"    END AS Interest_Charged, " +
"    CASE Fh.activityid " +
"      WHEN '15' " +
"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"      ELSE 0 " +
"    END AS Materials, " +
"    Fin_Year, " +
"    SUB_LEDGER_TYPE_DESC " +
"  FROM Fas_Wrkexp_View V " +
"  INNER JOIN fas_Headofaccounts Fh " +
"  ON V.Account_Head_Code=Fh.Accountcode " +
"  INNER JOIN Activity Ac " +
"  ON Fh.Activityid=Ac.Activityid " +
"  INNER JOIN Project Pr " +
"  ON Fh.Projectid=Pr.Projectid " +
"  INNER JOIN Program Pg " +
"  ON Fh.Programid=Pg.Programid " +
"  INNER JOIN Schemes Ps " +
"  ON Fh.Projectid       =Ps.Projectid " +
"  AND Fh.Programid      =Ps.Programid " +
"  AND fh.Schemeid       =Ps.Schemeid " +
  sup_query+
     unit_qry+
//  "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
      WE_R+
"  GROUP BY " +
"    To_Number (Fh.Projectid), " +
"    Pr.Projectname, " +
"    Fh.Programid, " +
"    Pg.Programname, " +
"    Fh.Schemeid, " +
"    Ps.Schemename, " +
"    V.Account_Head_Code, ACCOUNTING_UNIT_ID, " +
"    Fh.Headofaccount, " +
"    Sub_Ledger_Type_Code, " +
"    Sub_Ledger_Code, " +
"    Fh.Activityid, " +
"    Ac.Activityname, " +
"    Fin_Year, " +
"    SUB_LEDGER_TYPE_DESC " +
"  )t " +
"GROUP BY t.Data_Tb, " +

"  t.Schemeid, " +
"  t.Schemename, " +
"  t.Account_Head_Code, " +
"  t.ACCOUNTING_UNIT_ID, " +
"  t.Headofaccount, " +
"  t.Fin_Year " +
"ORDER BY t.Schemeid, " +
"  t.Account_Head_Code";
              }else if(WE_REport==4){
              
            	  String head_code=request.getParameter("head_Code");
            	  if(head_code.length()==6)
            	  {
            	head_Qry=" and  V.Account_Head_Code like '"+head_code+"' ";
            	
            	  }else
            	  {
            		  head_Qry=" and  V.Account_Head_Code like '"+head_code+"%' ";  
            	  }
            	  qry="SELECT t.Data_Tb, t.ACCOUNTING_UNIT_ID," +
            	  "  t.Schemeid, " +
            	  "  t.Schemename, " +
            	  "  t.Account_Head_Code, " +
            	  "  t.Headofaccount, " +
            	  "  SUM(t.DR_AMOUNT)DR_AMOUNT, " +
            	  "  SUM(t.CR_AMOUNT)CR_AMOUNT, " +
            	  "  SUM(t.Net_Amt)Net_Amt, " +
            	  "  SUM(t.Funds)Funds, " +
            	  "  SUM(t.Works_Expenditure)Works_Expenditure, " +
            	  "  SUM(t.Centage)Centage, " +
            	  "  SUM(t.Interest_Charged)Interest_Charged, " +
            	  "  SUM(t.Materials)Materials, " +
            	  "  t.Fin_Year " +
            	  "FROM " +
            	  "  (SELECT 1                 AS Data_Tb, ACCOUNTING_UNIT_ID," +
            	  "    to_number(Fh.Projectid) AS Projectid, " +
            	  "    Pr.Projectname, " +
            	  "    Fh.Programid, " +
            	  "    Pg.Programname, " +
            	  "    Fh.Schemeid, " +
            	  "    Ps.Schemename, " +
            	  "    V.Account_Head_Code, " +
            	  "    Fh.Headofaccount, " +
            	  "    Sub_Ledger_Type_Code, " +
            	  "    Sub_Ledger_Code, " +
            	  "    SUM(dr_amt)DR_AMOUNT, " +
            	  "    SUM(CR_AMT)CR_AMOUNT, " +
            	  "    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , " +
            	  "    Fh.Activityid, " +
            	  "    Ac.Activityname, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '1' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Funds, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '2' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Works_Expenditure, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '3' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Centage, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '4' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Interest_Charged, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '15' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Materials, " +
            	  "    Fin_Year, " +
            	  "    SUB_LEDGER_TYPE_DESC " +
            	  "  FROM Fas_Wrkexp_View V " +
            	  "  INNER JOIN fas_Headofaccounts Fh " +
            	  "  ON V.Account_Head_Code=Fh.Accountcode " +
            	  "  INNER JOIN Activity Ac " +
            	  "  ON Fh.Activityid=Ac.Activityid " +
            	  "  INNER JOIN Project Pr " +
            	  "  ON Fh.Projectid=Pr.Projectid " +
            	  "  INNER JOIN Program Pg " +
            	  "  ON Fh.Programid=Pg.Programid " +
            	  "  INNER JOIN Schemes Ps " +
            	  "  ON Fh.Projectid =Ps.Projectid " +
            	  "  AND Fh.Programid=Ps.Programid " +
            	  "  AND fh.Schemeid =Ps.Schemeid " +
            	    sup_query+unit_qry+
            	    
                //    "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
            	    WE_R+head_Qry+
            	  "  GROUP BY ACCOUNTING_UNIT_ID,To_Number (Fh.Projectid), " +
            	  "    Pr.Projectname, " +
            	  "    Fh.Programid, " +
            	  "    Pg.Programname, " +
            	  "    Fh.Schemeid, " +
            	  "    Ps.Schemename, " +
            	  "    V.Account_Head_Code, " +
            	  "    Fh.Headofaccount, " +
            	  "    Sub_Ledger_Type_Code, " +
            	  "    Sub_Ledger_Code, " +
            	  "    Fh.Activityid, " +
            	  "    Ac.Activityname, " +
            	  "    Fin_Year, " +
            	  "    SUB_LEDGER_TYPE_DESC " +
            	  "  )t " +
            	  "GROUP BY t.Data_Tb,t.ACCOUNTING_UNIT_ID, " +
            	  "  t.Schemeid, " +
            	  "  t.Schemename, " +
            	  "  t.Account_Head_Code, " +
            	  "  t.Headofaccount, " +
            	  "  t.Fin_Year " +
            	  "ORDER BY t.Schemeid, " +
            	  "  t.Account_Head_Code";
              
              }else if(WE_REport==6){
              
            	  String head_code=request.getParameter("head_Code");
            	  if(head_code.length()==6)
            	  {
            	head_Qry=" and  V.Account_Head_Code like '"+head_code+"' ";
            	
            	  }else
            	  {
            		  head_Qry=" and  V.Account_Head_Code like '"+head_code+"%' ";  
            	  }
            	  qry="SELECT t.Data_Tb,  " +
            	  "  t.Schemeid, " +
            	  "  t.Schemename, " +
            	  "  t.Account_Head_Code, t.ACCOUNTING_UNIT_ID," +
            	  "  (SELECT trim(ACCOUNTING_UNIT_NAME) " +
            	  "  FROM FAS_MST_ACCT_UNITS u " +
            	  "  WHERE u.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID " +
            	  "  ) AS unit_name, " +
            	  "  t.Headofaccount, " +
            	  "  SUM(t.DR_AMOUNT)DR_AMOUNT, " +
            	  "  SUM(t.CR_AMOUNT)CR_AMOUNT, " +
            	  "  SUM(t.Net_Amt)Net_Amt, " +
            	  "  SUM(t.Funds)Funds, " +
            	  "  SUM(t.Works_Expenditure)Works_Expenditure, " +
            	  "  SUM(t.Centage)Centage, " +
            	  "  SUM(t.Interest_Charged)Interest_Charged, " +
            	  "  SUM(t.Materials)Materials, " +
            	  "  t.Fin_Year " +
            	  "FROM " +
            	  "  (SELECT 1                 AS Data_Tb,  " +
            	  "    to_number(Fh.Projectid) AS Projectid, " +
            	  "    Pr.Projectname, " +
            	  "    Fh.Programid, " +
            	  "    Pg.Programname, " +
            	  "    Fh.Schemeid, " +
            	  "    Ps.Schemename, " +
            	  "    V.Account_Head_Code,ACCOUNTING_UNIT_ID, " +
            	  "    Fh.Headofaccount, " +
            	  "    Sub_Ledger_Type_Code, " +
            	  "    Sub_Ledger_Code, " +
            	  "    SUM(dr_amt)DR_AMOUNT, " +
            	  "    SUM(CR_AMT)CR_AMOUNT, " +
            	  "    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , " +
            	  "    Fh.Activityid, " +
            	  "    Ac.Activityname, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '1' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Funds, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '2' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Works_Expenditure, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '3' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Centage, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '4' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Interest_Charged, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '15' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Materials, " +
            	  "    Fin_Year, " +
            	  "    SUB_LEDGER_TYPE_DESC " +
            	  "  FROM Fas_Wrkexp_View V " +
            	  "  INNER JOIN fas_Headofaccounts Fh " +
            	  "  ON V.Account_Head_Code=Fh.Accountcode " +
            	  "  INNER JOIN Activity Ac " +
            	  "  ON Fh.Activityid=Ac.Activityid " +
            	  "  INNER JOIN Project Pr " +
            	  "  ON Fh.Projectid=Pr.Projectid " +
            	  "  INNER JOIN Program Pg " +
            	  "  ON Fh.Programid=Pg.Programid " +
            	  "  INNER JOIN Schemes Ps " +
            	  "  ON Fh.Projectid =Ps.Projectid " +
            	  "  AND Fh.Programid=Ps.Programid " +
            	  "  AND fh.Schemeid =Ps.Schemeid " +
            	    sup_query+unit_qry+
            	    
                //    "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
            	    WE_R+head_Qry+
            	  "  GROUP BY To_Number (Fh.Projectid), " +
            	  "    Pr.Projectname, " +
            	  "    Fh.Programid, " +
            	  "    Pg.Programname, " +
            	  "    Fh.Schemeid, " +
            	  "    Ps.Schemename, " +
            	  "    V.Account_Head_Code, ACCOUNTING_UNIT_ID," +
            	  "    Fh.Headofaccount, " +
            	  "    Sub_Ledger_Type_Code, " +
            	  "    Sub_Ledger_Code, " +
            	  "    Fh.Activityid, " +
            	  "    Ac.Activityname, " +
            	  "    Fin_Year, " +
            	  "    SUB_LEDGER_TYPE_DESC " +
            	  "  )t " +
            	  "GROUP BY t.Data_Tb,  " +
            	  "  t.Schemeid, " +
            	  "  t.Schemename, " +
            	  "  t.Account_Head_Code,t.ACCOUNTING_UNIT_ID, " +
            	  "  t.Headofaccount, " +
            	  "  t.Fin_Year " +
            	  "ORDER BY t.Schemeid, " +
            	  "  t.Account_Head_Code";
              
              }
}else if(command.equalsIgnoreCase("fzdlive"))
{
	wE_Type=Integer.parseInt(request.getParameter("FzWE_R"));
	Re_Type=request.getParameter("FZrad_R");
	WE_REport=Integer.parseInt(request.getParameter("FzReport"));
	   fin_FY=request.getParameter("fzdFY");
       fin_TY=request.getParameter("fzdTY");
       f_mnth=request.getParameter("fromfzdMonth");
       t_mnth=request.getParameter("tofzdMonth");
  System.out.println(WE_REport+" >>> "+Re_Type);
       if (unit_id==0)unit_qry="";
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
       	 		   head= "Works Expenditure Details for the Month Upto  "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
       	        }else{head= "Works Expenditure Details for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];}
            	   sup_query=" AND Month_Tb BETWEEN '" +f_mnth+"'  AND '" +t_mnth+"' " ;
            	   
                   }else if(f_mnth.equalsIgnoreCase(t_mnth))
                   {
                	  sup_query= " AND  Month_Tb = '" +f_mnth+"' " ;
                	  head= "Works Expenditure Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
                   }
            if(WE_REport==1){ 
               qry="SELECT t.Data_Tb, " +
               "  t.Schemeid, " +
               "  t.Schemename, " +
               "  SUM(t.DR_AMOUNT), " +
               "  SUM(t.CR_AMOUNT), " +
               "  SUM(t.Net_Amt), " +
               "  SUM(t.Funds), " +
               "  SUM(t.Works_Expenditure), " +
               "  SUM(t.Centage), " +
               "  SUM(t.Interest_Charged), " +
               "  SUM(t.Materials), " +
               "  t.Fin_Year " +
               "FROM " +
               "  (SELECT 1                 AS Data_Tb, " +
               "    to_number(Fh.Projectid) AS Projectid, " +
               "    Pr.Projectname, " +
               "    Fh.Programid, " +
               "    Pg.Programname, " +
               "    Fh.Schemeid, " +
               "    Ps.Schemename, " +
               "    V.Account_Head_Code, " +
               "    Fh.Headofaccount, " +
               "    Sub_Ledger_Type_Code, " +
               "    Sub_Ledger_Code, " +
               "    SUM(dr_amt)DR_AMOUNT, " +
               "    SUM(CR_AMT)CR_AMOUNT, " +
               "    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , " +
               "    Fh.Activityid, " +
               "    Ac.Activityname, " +
               "    CASE Fh.activityid " +
               "      WHEN '1' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Funds, " +
               "    CASE Fh.activityid " +
               "      WHEN '2' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Works_Expenditure, " +
               "    CASE Fh.activityid " +
               "      WHEN '3' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Centage, " +
               "    CASE Fh.activityid " +
               "      WHEN '4' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Interest_Charged, " +
               "    CASE Fh.activityid " +
               "      WHEN '15' " +
               "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
               "      ELSE 0 " +
               "    END AS Materials, " +
               "    Fin_Year, " +
               "    SUB_LEDGER_TYPE_DESC " +
               "  FROM Fas_Wrkexp_View V " +
               "  INNER JOIN fas_Headofaccounts Fh " +
               "  ON V.Account_Head_Code=Fh.Accountcode " +
               "  INNER JOIN Activity Ac " +
               "  ON Fh.Activityid=Ac.Activityid " +
               "  INNER JOIN Project Pr " +
               "  ON Fh.Projectid=Pr.Projectid " +
               "  INNER JOIN Program Pg " +
               "  ON Fh.Programid=Pg.Programid " +
               "  INNER JOIN Schemes Ps " +
               "  ON Fh.Projectid =Ps.Projectid " +
               "  AND Fh.Programid=Ps.Programid " +
               "  AND fh.Schemeid =Ps.Schemeid " +
               sup_query+
        	   unit_qry+
               "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
               "  GROUP BY To_Number (Fh.Projectid), " +
               "    Pr.Projectname, " +
               "    Fh.Programid, " +
               "    Pg.Programname, " +
               "    Fh.Schemeid, " +
               "    Ps.Schemename, " +
               "    V.Account_Head_Code, " +
               "    Fh.Headofaccount, " +
               "    Sub_Ledger_Type_Code, " +
               "    Sub_Ledger_Code, " +
               "    Fh.Activityid, " +
               "    Ac.Activityname, " +
               "    Fin_Year, " +
               "    SUB_LEDGER_TYPE_DESC " +
               "  )t " +
               "GROUP BY t.Data_Tb, " +
               "  t.Schemeid, " +
               "  t.Schemename, " +
               "  t.Fin_Year " +
               "ORDER BY t.Schemeid"
;
              }else if (WE_REport==2)
              {
            	  qry="SELECT t.Data_Tb, " +
            	  "  t.Schemeid, " +
            	  "  t.Schemename, " +
            	  "  t.Account_Head_Code, " +
            	  "  t.Headofaccount, " +
            	  "  SUM(t.DR_AMOUNT)DR_AMOUNT, " +
            	  "  SUM(t.CR_AMOUNT)CR_AMOUNT, " +
            	  "  SUM(t.Net_Amt)Net_Amt, " +
            	  "  SUM(t.Funds)Funds, " +
            	  "  SUM(t.Works_Expenditure)Works_Expenditure, " +
            	  "  SUM(t.Centage)Centage, " +
            	  "  SUM(t.Interest_Charged)Interest_Charged, " +
            	  "  SUM(t.Materials)Materials, " +
            	  "  t.Fin_Year " +
            	  "FROM " +
            	  "  (SELECT 1                 AS Data_Tb, " +
            	  "    to_number(Fh.Projectid) AS Projectid, " +
            	  "    Pr.Projectname, " +
            	  "    Fh.Programid, " +
            	  "    Pg.Programname, " +
            	  "    Fh.Schemeid, " +
            	  "    Ps.Schemename, " +
            	  "    V.Account_Head_Code, " +
            	  "    Fh.Headofaccount, " +
            	  "    Sub_Ledger_Type_Code, " +
            	  "    Sub_Ledger_Code, " +
            	  "    SUM(dr_amt)DR_AMOUNT, " +
            	  "    SUM(CR_AMT)CR_AMOUNT, " +
            	  "    SUM(Dr_Amt)-SUM(Cr_Amt) AS Net_Amt , " +
            	  "    Fh.Activityid, " +
            	  "    Ac.Activityname, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '1' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Funds, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '2' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Works_Expenditure, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '3' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Centage, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '4' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Interest_Charged, " +
            	  "    CASE Fh.activityid " +
            	  "      WHEN '15' " +
            	  "      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
            	  "      ELSE 0 " +
            	  "    END AS Materials, " +
            	  "    Fin_Year, " +
            	  "    SUB_LEDGER_TYPE_DESC " +
            	  "  FROM Fas_Wrkexp_View V " +
            	  "  INNER JOIN fas_Headofaccounts Fh " +
            	  "  ON V.Account_Head_Code=Fh.Accountcode " +
            	  "  INNER JOIN Activity Ac " +
            	  "  ON Fh.Activityid=Ac.Activityid " +
            	  "  INNER JOIN Project Pr " +
            	  "  ON Fh.Projectid=Pr.Projectid " +
            	  "  INNER JOIN Program Pg " +
            	  "  ON Fh.Programid=Pg.Programid " +
            	  "  INNER JOIN Schemes Ps " +
            	  "  ON Fh.Projectid =Ps.Projectid " +
            	  "  AND Fh.Programid=Ps.Programid " +
            	  "  AND fh.Schemeid =Ps.Schemeid " +
            	    sup_query+unit_qry+
                    "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
            	  "  GROUP BY To_Number (Fh.Projectid), " +
            	  "    Pr.Projectname, " +
            	  "    Fh.Programid, " +
            	  "    Pg.Programname, " +
            	  "    Fh.Schemeid, " +
            	  "    Ps.Schemename, " +
            	  "    V.Account_Head_Code, " +
            	  "    Fh.Headofaccount, " +
            	  "    Sub_Ledger_Type_Code, " +
            	  "    Sub_Ledger_Code, " +
            	  "    Fh.Activityid, " +
            	  "    Ac.Activityname, " +
            	  "    Fin_Year, " +
            	  "    SUB_LEDGER_TYPE_DESC " +
            	  "  )t " +
            	  "GROUP BY t.Data_Tb, " +
            	  "  t.Schemeid, " +
            	  "  t.Schemename, " +
            	  "  t.Account_Head_Code, " +
            	  "  t.Headofaccount, " +
            	  "  t.Fin_Year " +
            	  "ORDER BY t.Schemeid, " +
            	  "  t.Account_Head_Code";
              }
              else if (WE_REport==3){
               qry="SELECT a.Data_Tb, " +
               "  a.activityid, " +
               "  CASE a.activityid " +
               "    WHEN '1' " +
               "    THEN Net_Amt " +
               "  END AS Funds, " +
               "  CASE a.activityid " +
               "    WHEN '2' " +
               "    THEN net_amt " +
               "  END AS works_expenditure, " +
               "  CASE a.activityid " +
               "    WHEN '3' " +
               "    THEN net_amt " +
               "  END AS centage, " +
               "  CASE a.activityid " +
               "    WHEN '4' " +
               "    THEN net_amt " +
               "  END AS interest_charged, " +
               "  CASE a.activityid " +
               "    WHEN '15' " +
               "    THEN net_amt " +
               "  END AS Materials, " +
               "  a.annualgroupingid, " +
               "  a.minorgroupingid, " +
               "  ac.activityname, " +
               "  min.minorgroupingname, " +
               "  a.account_head_code, " +
               "  hd.account_head_desc, " +
               "  a.Programid, " +
               "  a.Programname, " +
               "  SUM(A.Dr_Amount)Dr_Amount, " +
               "  SUM(A.Cr_Amount) Cr_Amount, " +
               "  SUM(A.Net_Amt) Net_Amt, " +
               "  a. Fin_Year " +
               "FROM " +
               "  (SELECT 1 AS data_tb, " +
             //  "    --  Fh.Projectid, " +
             //  "    --   Pr.Projectname, " +
               "    fh.activityid, " +
               "    fh.annualgroupingid, " +
               "    fh.minorgroupingid, " +
               "    CASE " +
               "      WHEN LENGTH(Fh.Programid)<2 " +
               "      THEN '0' " +
               "        ||Fh.Programid " +
               "      ELSE Fh.Programid " +
               "    END Programid, " +
               "    Pg.Programname, " +
               "    v.account_head_code, " +
               "    SUM(DR_AMOUNT)DR_AMOUNT, " +
               "    SUM(CR_AMOUNT)CR_AMOUNT, " +
               "    SUM(DR_AMOUNT)-SUM(CR_AMOUNT) AS net_amt , " +
               "    Fin_Year " +
               "  FROM( " +
               "    (SELECT a.ACCOUNTING_UNIT_ID, " +
               "      a.account_head_code, " +
               "      SUM( current_month_debit )                               AS DR_AMOUNT, " +
               "      SUM( current_month_credit)                               AS CR_AMOUNT, " +
               "      (SUM( current_month_debit )- SUM( Current_Month_Credit)) AS Net_Amt, " +
               "      0                                                        AS Sup_No, " +
               "      0                                                        AS Fin_Year , " +
               "      Cashbook_Year, " +
               "      Cashbook_Month, " +
               "      To_date(Cashbook_Month " +
               "      ||'-' " +
               "      ||Cashbook_Year,'MM-yyyy') Month_Tb " +
               "    FROM FAS_TRIAL_BALANCE a " +
               "    WHERE (current_month_debit!=0 " +
               "    OR current_month_credit!   =0) " +
               "    GROUP BY a.ACCOUNTING_UNIT_ID, " +
               "      a.account_head_code, " +
               "      0, " +
               "      0, " +
               "      Cashbook_Year, " +
               "      Cashbook_Month, " +
               "      To_date(Cashbook_Month " +
               "      ||'-' " +
               "      ||Cashbook_Year,'MM-yyyy') " +
               "    ) " +
               "  UNION ALL " +
               "    (SELECT a.ACCOUNTING_UNIT_ID, " +
               "      a.account_head_code, " +
               "      SUM( current_month_debit )                               AS debit, " +
               "      SUM( current_month_credit)                               AS credit, " +
               "      (SUM( current_month_debit )- SUM( Current_Month_Credit)) AS Net, " +
               "      Supplement_No                                            AS Sup_No, " +
               "      0                                                        AS Fin_Year , " +
               "      Cashbook_Year, " +
               "      Cashbook_Month, " +
               "      To_date(Cashbook_Month " +
               "      ||'-' " +
               "      ||Cashbook_Year,'MM-yyyy')+Supplement_No month_TB " +
               "    FROM Fas_Trial_Balance_Supplement A " +
               "    WHERE (current_month_debit!=0 " +
               "    OR current_month_credit!   =0) " +
               "    GROUP BY a.ACCOUNTING_UNIT_ID, " +
               "      a.account_head_code, " +
               "      Supplement_No, " +
               "      0, " +
               "      Cashbook_Year, " +
               "      Cashbook_Month, " +
               "      To_date(Cashbook_Month " +
               "      ||'-' " +
               "      ||Cashbook_Year,'MM-yyyy')+Supplement_No " +
               "    ) )V " +
               "  INNER JOIN Headofaccounts Fh " +
               "  ON V.Account_Head_Code=Fh.Accountcode " +
               "  INNER JOIN Project Pr " +
               "  ON Fh.Projectid=Pr.Projectid " +
               "  INNER JOIN Program Pg " +
               "  ON fh.programid =pg.programid " +
               sup_query+unit_qry+
               "  AND Fh.Minorgroupingid='"+wE_Type+"' " +
               "  GROUP BY 1, " +
               "    fh.activityid, " +
               "    fh.annualgroupingid, " +
               "    fh.minorgroupingid, " +
               "    CASE " +
               "      WHEN LENGTH(Fh.Programid)<2 " +
               "      THEN '0' " +
               "        ||Fh.Programid " +
               "      ELSE Fh.Programid " +
               "    END, " +
               "    pg.programname, " +
               "    v.account_head_code, " +
               "    fin_year " +
               "  )a " +
               "INNER JOIN activity ac " +
               "ON a.activityid=ac.activityid " +
               "INNER JOIN minorgrouping MIN " +
               "ON a.minorgroupingid=min.minorgroupingid " +
               "INNER JOIN com_mst_account_heads hd " +
               "ON a.account_head_code=hd.account_head_code " +
               "GROUP BY a.Data_Tb, " +
               "  a.activityid, " +
               "  CASE a.activityid " +
               "    WHEN '1' " +
               "    THEN Net_Amt " +
               "  END, " +
               "  CASE a.activityid " +
               "    WHEN '2' " +
               "    THEN net_amt " +
               "  END, " +
               "  CASE a.activityid " +
               "    WHEN '3' " +
               "    THEN net_amt " +
               "  END, " +
               "  CASE a.activityid " +
               "    WHEN '4' " +
               "    THEN net_amt " +
               "  END, " +
               "  CASE a.activityid " +
               "    WHEN '15' " +
               "    THEN net_amt " +
               "  END, " +
               "  a.annualgroupingid, " +
               "  a.minorgroupingid, " +
               "  ac.activityname, " +
               "  min.minorgroupingname, " +
               "  a.account_head_code, " +
               "  hd.account_head_desc, " +
               "  a.Programid, " +
               "  a.Programname, " +
               "  a. Fin_Year " +
               "   ORDER BY a.account_head_code";
             //  "  --          a.Programid";
              }
 }
	    	 System.out.println(Re_Type+">>> .... "+qry);
	    	try{
	    		 if(WE_REport==1)
	      	   {
	    			  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Fas_wrkexp_sum.jasper")); 
	      	   }else if (WE_REport==2 || WE_REport==4)
	      	   {
	      		      reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Fas_wrkexp_Abs.jasper"));  
	      	   }else if (WE_REport==3)
	      	   {
	      		      reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/fas_WrkExp_Report.jasper"));   
	      	   }else if (WE_REport==5 || WE_REport==6)
	      	   {
	      		      reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Fas_wrkexp_AbsUnit.jasper"));  
	      	   }
		    			    			
	    		//}
	    			System.out.println(" Report File >>>  "+reportFile); 
	    		  if (!reportFile.exists())
	    		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
	    		    
	    		    JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		    
	    		    Map map = null;
	    		    map = new HashMap();
	    		    map.put("qry", qry);
	    		    map.put("head",head);
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
		  
    //	System.out.println("Servlet Page ... ");
    	CallableStatement stmt=null; 
    	Connection connection=null;
    	PreparedStatement ps=null;
    	ResultSet rset=null;
    	PrintWriter out=response.getWriter();
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
       
        CONTENT_TYPE="text/xml; charset=windows-1252";
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
       String command=request.getParameter("command");
       int from_year=0;
       int To_year=0;
       int from_Month=0;
       int To_Month=0;
       String fin_year="",Type="",qry="",xml="";
       Type=request.getParameter("Type");
       if(command.equalsIgnoreCase("loadCmb"))
       {
    	   xml="<response><command>"+command+"</command>";
    	   fin_year=request.getParameter("Fin_year");
    	 //  System.out.println(fin_year+">>"+Type);
    	//  xml+="<type>"+Type+"</type>";
    	   if(Type.equalsIgnoreCase("TYL") || Type.equalsIgnoreCase("FYL"))
    	   {
    		   qry=" select distinct cmonth,CASHBOOK_YEAR, "+
                   " case      when cashbook_month=4   "+
    		       " then 'Apr '       ||cashbook_year     when cashbook_month=5     then 'May '       ||cashbook_year   "+
    		       " when cashbook_month=6     then 'Jun '       ||cashbook_year     when cashbook_month=7     then 'Jul '       ||cashbook_year   "+
    		       " when cashbook_month=8     then 'Aug '       ||cashbook_year     when cashbook_month=9     then 'Sep '       ||cashbook_year   "+
    		       " when cashbook_month=10     then 'Oct '       ||cashbook_year     when cashbook_month=11     then 'Nov '       ||cashbook_year   "+
    		       " when cashbook_month=12     then 'Dec '       ||cashbook_year     when cashbook_month=1     then 'Jan '       ||cashbook_year   "+
    		       " when cashbook_month=2     then 'Feb '       ||cashbook_year     "+ 
    		       " when cashbook_month=3  and sup_no=0   then 'Mar '       ||cashbook_year  "+
    		       "  when Cashbook_Month=3  AND SUP_NO!=0   then 'Sup '       ||SUP_NO    "+                 
    		       " end MONTH   "+
    		       " FROM  VIEW_FAS_TRIAL_CP WHERE Fin_Year='"+fin_year+"' ORDER BY CMONTH";
    	 
    	   
    	   
    	   
    	   
    	   }else if(Type.equalsIgnoreCase("FYFz") || Type.equalsIgnoreCase("TYFz"))
    	   {
    		 qry = "select * from (SELECT DISTINCT Cashbook_Year,To_date(Cashbook_Month||'-'||Cashbook_Year,'MM-yyyy') CMonth,"+
    			   "  CASE Cashbook_Month  WHEN 4  THEN 'Apr ' ||Cashbook_Year  WHEN 5 THEN 'May '||Cashbook_Year  WHEN 6 THEN 'Jun '"+
    			   "  ||Cashbook_Year   WHEN 7   THEN 'Jul '  ||Cashbook_Year  WHEN 8 THEN 'Aug ' ||Cashbook_Year  WHEN 9  THEN 'Sep '"+
    			   "  ||Cashbook_Year  WHEN 10  THEN 'Oct '  ||Cashbook_Year  WHEN 11  THEN 'Nov ' ||Cashbook_Year WHEN 12 THEN 'Dec '"+
    			   "  ||Cashbook_Year   WHEN 1 THEN 'Jan '  ||Cashbook_Year  WHEN 2  THEN 'Feb ' ||Cashbook_Year  WHEN 3  THEN 'Mar '"+
    			   "  ||Cashbook_Year  END MONTH, CASE  WHEN cashbook_month<=3  THEN (cashbook_year-1)  ||'-'  ||cashbook_year WHEN cashbook_month>=4"+
    			   "  THEN cashbook_year ||'-' ||(Cashbook_Year+1)   END Fin_Year FROM FAS_TRIAL_BALANCE_CMP "+
    			   " UNION ALL " +
    			   " SELECT DISTINCT Cashbook_Year, " +
    			   "  To_date(Cashbook_Month " +
    			   "  ||'-' " +
    			   "  ||Cashbook_Year,'MM-yyyy')+SUPPLEMENT_NO Cmonth, " +
    			   "  'Sup ' " +
    			   "  ||SUPPLEMENT_NO AS MONTH, " +
    			   "  CASE " +
    			   "    WHEN cashbook_month<=3 " +
    			   "    THEN (cashbook_year-1) " +
    			   "      ||'-' " +
    			   "      ||cashbook_year " +
    			   "    WHEN cashbook_month>=4 " +
    			   "    THEN cashbook_year " +
    			   "      ||'-' " +
    			   "      ||(Cashbook_Year+1) " +
    			   "  END Fin_Year " +
    			   " FROM Fas_Trial_Balance_Supplement ) WHERE Fin_Year='"+fin_year+"'  ORDER BY Cashbook_Year, CMonth, MONTH";
  
    	   }
    	   qry="SELECT q.Cashbook_Year, " +
    	   "  To_Date(q.Cashbook_Month " +
    	   "  ||'-' " +
    	   "  ||q.Cashbook_Year,'MM-yyyy')+Sup_No Cmonth, " +
    	   "  q.MONTH, " +
    	   "  q.Fin_Year " +
    	   "FROM " +
    	   "  (SELECT DISTINCT M.Cashbook_Year, " +
    	   "    M.Cashbook_Month, " +
    	   "    DECODE(m.Cashbook_Month,3,M.Supplement_No,0)Sup_No, " +
    	   "    To_Date(m.Cashbook_Month " +
    	   "    ||'-' " +
    	   "    ||m.Cashbook_Year,'MM-yyyy')+DECODE(m.Cashbook_Month,3,m.Supplement_No,0) Cmonth, " +
    	   "    CASE " +
    	   "      WHEN m.cashbook_month=4 " +
    	   "      THEN 'Apr ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=5 " +
    	   "      THEN 'May ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=6 " +
    	   "      THEN 'Jun ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=7 " +
    	   "      THEN 'Jul ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=8 " +
    	   "      THEN 'Aug ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=9 " +
    	   "      THEN 'Sep ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=10 " +
    	   "      THEN 'Oct ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=11 " +
    	   "      THEN 'Nov ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=12 " +
    	   "      THEN 'Dec ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=1 " +
    	   "      THEN 'Jan ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN m.cashbook_month=2 " +
    	   "      THEN 'Feb ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN M.Cashbook_Month =3 " +
    	   "      AND m.Supplement_No   =0 " +
    	   "      THEN 'Mar ' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN M.Cashbook_Month =3 " +
    	   "      AND m.Supplement_No!  =0 " +
    	   "      THEN 'Sup ' " +
    	   "        ||m.Supplement_No " +
    	   "    END MONTH , " +
    	   "    CASE " +
    	   "      WHEN M.Cashbook_Month<=3 " +
    	   "      THEN (m.cashbook_year-1) " +
    	   "        ||'-' " +
    	   "        ||M.Cashbook_Year " +
    	   "      WHEN M.Cashbook_Month>=4 " +
    	   "      THEN m.cashbook_year " +
    	   "        ||'-' " +
    	   "        ||(m.cashbook_Year+1) " +
    	   "    END Fin_Year " +
    	   "  FROM Fas_Journal_Master M " +
    	   "  JOIN Fas_Journal_Transaction T " +
    	   "  ON M.Accounting_Unit_Id       =T.Accounting_Unit_Id " +
    	   "  AND M.Accounting_For_Office_Id=T.Accounting_For_Office_Id " +
    	   "  AND M.Voucher_No              =t.voucher_no " +
    	   "  AND M.Cashbook_Year           =T.Cashbook_Year " +
    	   "  AND M.Cashbook_Month          =T.Cashbook_Month " +
    	   "  AND m.cashbook_year          IN ("+fin_year.split("-")[0]+","+fin_year.split("-")[1]+") " +
    	   "  )q " +
    	   "WHERE Fin_Year='"+fin_year+"' " +
    	   "ORDER BY Cmonth";
    	   //System.out.println(qry);
    		   try{    	  
    	   
    	   ps=connection.prepareStatement(qry);
    	   rset=ps.executeQuery();
    	   String ChequeDate="";
    	   while(rset.next())
    	   {
    		   Date d_val=rset.getDate("cmonth");
    			String Stringdate = d_val.toString();

				String[] ddd = Stringdate.split("-");

				int day = Integer.parseInt(ddd[2]);
				int month = Integer.parseInt(ddd[1]);
				int year = Integer.parseInt(ddd[0]);
				String month_Str="";
				if(month==1)month_Str="JAN";
				else if (month==2)month_Str="FEB";
				else if (month==3)month_Str="MAR";
				else if (month==4)month_Str="APR";
				else if (month==5)month_Str="MAY";
				else if (month==6)month_Str="JUN";
				else if (month==7)month_Str="JUL";
				else if (month==8)month_Str="AUG";
				else if (month==9)month_Str="SEP";
				else if (month==10)month_Str="OCT";
				else if (month==11)month_Str="NOV";
				else if (month==12)month_Str="DEC";
				if (day >= 10) {
					ChequeDate = (day + "-" + month_Str + "-" + year);  
				} else {
					ChequeDate = ("0"+day + "-" + month_Str + "-" + year);  
				}
    		
    		   xml+="<year>"+rset.getInt("CASHBOOK_YEAR")+"</year>";
    		   xml+="<month>"+ChequeDate+"</month>";
    		   xml+="<month_desc>"+rset.getString("MONTH")+"</month_desc>";
    	   }xml+="<type>"+Type+"</type>";
    		   }
    	   catch (Exception e) {
//System.out.println(e);
    	   }
    	   
    	   
       }xml+="</response>";
       //System.out.println(xml);
       out.write(xml);
       out.close();
        
	}

}