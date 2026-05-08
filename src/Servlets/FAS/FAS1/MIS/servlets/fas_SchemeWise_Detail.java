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
import com.lowagie.tools.plugins.Txt2Pdf;

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
public class fas_SchemeWise_Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static  String CONTENT_TYPE = 
            "text/xml; charset=windows-1252";
        Connection connection = null;
    public fas_SchemeWise_Detail() {
        super();
        // TODO Auto-generated constructor stub
    }

    
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	System.out.println("Servlet Page ... ");
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
        int sch_id=0;
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
        // unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        String Re_Type="",Second_qry="",cmd_unit="";
	     try{   
if(command.equalsIgnoreCase("live"))	    	 
{
 cmd_unit=request.getParameter("cmd_unit");
 
 

	//WE_REport=Integer.parseInt(request.getParameter("LReport"));
	Re_Type=request.getParameter("rad_R");
	   fin_FY=request.getParameter("liveFY");
    fin_TY=request.getParameter("liveTY");
    f_mnth=request.getParameter("fromMonth");
    t_mnth=request.getParameter("toMonth");
  System.out.println(f_mnth+""+t_mnth);
  /*  if (unit_id==0)unit_qry="";
    if (unit_id!=0)unit_qry=" and   ACCOUNTING_UNIT_ID="+unit_id ; */
    if(f_mnth.equalsIgnoreCase(t_mnth))
 	   {
 		  sup_query=   " AND cmonth          = '"+f_mnth+"' " ;
 		 head= "SchemeWise Trial Balance Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
 	   }else if(!f_mnth.equalsIgnoreCase(t_mnth))
 	   {
 		   System.out.println(" test");
 		  
					if ((!t_mnth.split("-")[1].equalsIgnoreCase("MAR")) && (t_mnth.split("-")[0].equalsIgnoreCase("01"))) {
						if (f_mnth.split("-")[1].equalsIgnoreCase("APR")) {
							head = "SchemeWise Trial Balance Details for the Month  "
								+ f_mnth.split("-")[1]
													+ " - "
													+ f_mnth.split("-")[2]+" to "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2];
						} else {
							head = "SchemeWise Trial Balance Details for the Period From  "
									+ f_mnth.split("-")[1]
									+ " - "
									+ f_mnth.split("-")[2]
									+ " To "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2];
						}
					
					}else if ((t_mnth.split("-")[1].equalsIgnoreCase("MAR"))&& (!t_mnth.split("-")[0].equalsIgnoreCase("01"))) {
						
						if (f_mnth.split("-")[1].equalsIgnoreCase("APR")) {
							head = "SchemeWise Trial Balance Details for the Month   "
								+ f_mnth.split("-")[1]
													+ " - "
													+ f_mnth.split("-")[2]+" to "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2]+" Including Supplement";
						} else {
							head = "SchemeWise Trial Balance Details for the Period From  "
									+ f_mnth.split("-")[1]
									+ " - "
									+ f_mnth.split("-")[2]
									+ " To "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2]+" Including Supplement";;
						}
					}
 		  
 		   
 	/*	   
 		  head = "SchemeWise Trial Balance Details for the Period From  "
				+ f_mnth.split("-")[1]
				+ " - "
				+ f_mnth.split("-")[2]
				+ " To "
				+ t_mnth.split("-")[1]
				+ " - "
				+ t_mnth.split("-")[2];*/
 	   
 		 sup_query= " AND cmonth   between '"+f_mnth+"' and '"+t_mnth+"'   " ;
 	   }
 	   else{
 		   System.out.println("error");
 	   }
 	  	   
if(cmd_unit.equalsIgnoreCase("N")){
	 wE_Type=Integer.parseInt(request.getParameter("cmbScheme_Code"));
	 System.out.println(WE_REport+" >>> "+wE_Type);
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
    	 //  "  nvl(s.sl_codename,'--') AS SLDESC " +
    		"  CASE "+
    		  "   WHEN Sub_Ledger_Type_Code >0 "+
    		  "   AND Sub_Ledger_Type_Code IS NOT NULL "+
    		  "   THEN "+
    		  "     (SELECT s.sl_codename "+
    		  "     FROM sl_type_code_name_view s "+
    		  "     WHERE s.sl_type= t.Sub_Ledger_Type_Code "+
    		  "     AND s.sl_code=t.Sub_Ledger_Code  "+   
    		  "     )"+
    		  "   ELSE '0' "+
    		  " END AS SLDESC "+
    	   " FROM " +
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
    	   "  AND Fh.Schemeid='"+wE_Type+"' " +
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
    	/*   "INNER JOIN sl_type_code_name_view s " +
    	   "ON t.Sub_Ledger_Type_Code=s.sl_type " +
    	   "AND t.Sub_Ledger_Code    =s.sl_code " +*/
    	   "ORDER BY t.Schemeid, " +
    	   "  t.Account_Head_Code, " +
    	   "  t.Sub_Ledger_Type_Code, " +
    	   "  t.Sub_Ledger_Code" ;
              
}else if(cmd_unit.equalsIgnoreCase("SCH_F3")) 
{
	unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	sch_id=Integer.parseInt(request.getParameter("sch_id"));
	String sub_unit="";
	if(unit_id==0)
	{
		sub_unit="";
	}else
	{
		sub_unit="    and ACCOUNTING_UNIT_ID= " +unit_id;
	}
	
/*	qry=	"SELECT t.Data_Tb, " +
	"  t.ACCOUNTING_UNIT_ID, " +
	"  t.unit_name, " +
	"  t.Schemeid, " +
	"  t.Schemename, " +
	"  t.Sub_Ledger_Type_Code, " +
	"  t.Sub_Ledger_Code, " +
	"  t.SUB_LEDGER_TYPE_DESC, " +
	"  t.SLDESC, " +
	"  SUM(SCH20)                                                                                           AS SCH20, " +
	"  SUM(SCH21)                                                                                           AS SCH21, " +
	"  SUM(SCH22)                                                                                           AS SCH22, " +
	"  SUM(SCH23)                                                                                           AS SCH23, " +
	"  SUM(SCH24)                                                                                           AS SCH24, " +
	"  SUM(SCH25)                                                                                           AS SCH25, " +
	"  SUM(SCH26)                                                                                           AS SCH26, " +
	"  SUM(SCH27)                                                                                           AS SCH27, " +
	"  SUM(SCH28)                                                                                           AS SCH28, " +
	"  SUM(SCH29)                                                                                           AS SCH29, " +
	"  SUM(SCH31)                                                                                           AS SCH31, " +
	"  SUM(SCH32)                                                                                           AS SCH32, " +
	"  SUM(SCH37)                                                                                           AS SCH37, " +
	"  SUM(SCH38)                                                                                           AS SCH38, " +
	"  SUM(SCH40)                                                                                           AS SCH40, " +
	"  (SUM(SCH20)+SUM(SCH21)+SUM(SCH22)+SUM(SCH23)+SUM(SCH24)+SUM(SCH25)+SUM(SCH26)+SUM(SCH27)+SUM(SCH28) )AS tot_exp1, " +
	"  SUM(SCH31)+SUM(sch37)                                                      AS tot_exp2 " +
	" FROM " +
	"  (SELECT 1 AS Data_Tb, " +
	"    ACCOUNTING_UNIT_ID, " +
	"    (SELECT trim(u.accounting_unit_name) " +
	"    FROM fas_mst_acct_units u " +
	"    WHERE u.accounting_unit_id=v.ACCOUNTING_UNIT_ID " +
	"    )AS unit_name, " +
	"    Fh.Schemeid, " +
	"    Ps.Schemename, " +
	"    V.Account_Head_Code, " +
	"    (SELECT h.account_head_desc " +
	"    FROM com_mst_account_heads h " +
	"    WHERE h.account_head_code=v.Account_Head_Code " +
	"    )AS head_desc, " +
	"    Fh.Headofaccount, " +
	"    Sub_Ledger_Type_Code, " +
	"    Sub_Ledger_Code, " +
	//"    NVL(s.sl_codename,'--') AS SLDESC , " +
	"  CASE "+
  "   WHEN Sub_Ledger_Type_Code >0 "+
  "   AND Sub_Ledger_Type_Code IS NOT NULL "+
  "   THEN "+
  "     (SELECT s.sl_codename "+
  "     FROM sl_type_code_name_view s "+
  "     WHERE s.sl_type= V.Sub_Ledger_Type_Code "+
  "     AND s.sl_code=V.Sub_Ledger_Code  "+   
  "     )"+
  "   ELSE 'Blank Scheme' "+
  "  END AS SLDESC ,"+
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"20 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH20, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"21 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS sch21, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"22 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH22, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"23 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS sch23, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"24 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH24, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"25 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH25, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"26 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS sch26, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"27 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH27, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"28 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH28, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"29 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH29, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"31 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH31, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"32 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH32, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"37 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH37, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"38 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH38, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"40 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH40, " +
	"    SUB_LEDGER_TYPE_DESC, " +
	"    Fin_Year " +
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
	"  AND fh.Schemeid = '"+sch_id+"' " +
	"  INNER JOIN sl_type_code_name_view s " +
	"  ON V.Sub_Ledger_Type_Code=s.sl_type " +
	"  AND V.Sub_Ledger_Code    =s.sl_code " +
	sup_query+sub_unit+ 
	//"  AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
	"  GROUP BY ACCOUNTING_UNIT_ID, " +
	"    Fh.Schemeid, " +
	"    Ps.Schemename, " +
	"    V.Account_Head_Code, " +
	"    Fh.Headofaccount, " +
	"    Sub_Ledger_Type_Code, " +
	"    Sub_Ledger_Code, " +
	"    SUB_LEDGER_TYPE_DESC, " +
	//"    NVL(s.sl_codename,'--') , " +
	"    Fin_Year " +
	"  )t " +
	" GROUP BY t.Data_Tb, " +
	"  t.ACCOUNTING_UNIT_ID, " +
	"  t.unit_name, " +
	"  t.Schemeid, " +
	"  t.Schemename, " +
	"  t.Sub_Ledger_Type_Code, " +
	"  t.Sub_Ledger_Code, " +
	"  t.SUB_LEDGER_TYPE_DESC, " +
	"  t.SLDESC " +
	" ORDER BY t.ACCOUNTING_UNIT_ID, " +
	"  t.Schemeid";*/
	qry="SELECT t.Data_Tb, " +
			"  t.ACCOUNTING_UNIT_ID, " +
			"  t.unit_name, " +
			"  "+sch_id+"        AS Schemeid, " +
			"  t.head_desc AS Schemename, " +
			"  t.Sub_Ledger_Type_Code, " +
			"  t.Sub_Ledger_Code, " +
			"  t.SUB_LEDGER_TYPE_DESC, " +
			"  t.SLDESC, " +
			"  SUM(SCH20)                                                                                           AS SCH20, " +
			"  SUM(SCH21)                                                                                           AS SCH21, " +
			"  SUM(SCH22)                                                                                           AS SCH22, " +
			"  SUM(SCH23)                                                                                           AS SCH23, " +
			"  SUM(SCH24)                                                                                           AS SCH24, " +
			"  SUM(SCH25)                                                                                           AS SCH25, " +
			"  SUM(SCH26)                                                                                           AS SCH26, " +
			"  SUM(SCH27)                                                                                           AS SCH27, " +
			"  SUM(SCH28)                                                                                           AS SCH28, " +
			"  SUM(SCH29)                                                                                           AS SCH29, " +
			"  SUM(SCH31)                                                                                           AS SCH31, " +
			"  SUM(SCH32)                                                                                           AS SCH32, " +
			"  SUM(SCH37)                                                                                           AS SCH37, " +
			"  SUM(SCH38)                                                                                           AS SCH38, " +
			"  SUM(SCH40)                                                                                           AS SCH40, " +
			"  (SUM(SCH20)+SUM(SCH21)+SUM(SCH22)+SUM(SCH23)+SUM(SCH24)+SUM(SCH25)+SUM(SCH26)+SUM(SCH27)+SUM(SCH28) )AS tot_exp1, " +
			"  SUM(SCH31) +SUM(sch37)                                                                               AS tot_exp2 " +
			"FROM " +
			"  (SELECT 1 AS Data_Tb, " +
			"    ACCOUNTING_UNIT_ID, " +
			"    (SELECT trim(u.accounting_unit_name) " +
			"    FROM fas_mst_acct_units u " +
			"    WHERE u.accounting_unit_id=v.ACCOUNTING_UNIT_ID " +
			"    )AS unit_name, " +
			"    V.Account_Head_Code, " +
			"    (SELECT h.account_head_desc " +
			"    FROM com_mst_account_heads h " +
			"    WHERE h.account_head_code="+sch_id+"31 " +
			"    )AS head_desc, " +
			"    Fh.account_head_code, " +
			"    Sub_Ledger_Type_Code, " +
			"    Sub_Ledger_Code, " +
			"    CASE " +
			"      WHEN Sub_Ledger_Type_Code >0 " +
			"      AND Sub_Ledger_Type_Code IS NOT NULL " +
			"      THEN " +
			"        (SELECT s.sl_codename " +
			"        FROM sl_type_code_name_view s " +
			"        WHERE s.sl_type= V.Sub_Ledger_Type_Code " +
			"        AND s.sl_code  =V.Sub_Ledger_Code " +
			"        ) " +
			"      ELSE 'Blank Scheme' " +
			"    END AS SLDESC , " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"20 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH20, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"21 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS sch21, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"22 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH22, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"23 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS sch23, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"24 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH24, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"25 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH25, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"26 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS sch26, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"27 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH27, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"28 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH28, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"29 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH29, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"31 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH31, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"32 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH32, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"37 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH37, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"38 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH38, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"40 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH40, " +
			"    SUB_LEDGER_TYPE_DESC, " +
			"    Fin_Year " +
			"  FROM Fas_Wrkexp_View V " +
			"  INNER JOIN com_mst_account_heads Fh " +
			"  ON V.Account_Head_Code=Fh.Account_Head_Code " +
			"  AND fh.Account_Head_Code LIKE '"+sch_id+"%' " +
			"  AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
			"  GROUP BY 1, " +
			"    ACCOUNTING_UNIT_ID, " +
			"    V.Account_Head_Code, " +
			"    Fh.account_head_code, " +
			"    Sub_Ledger_Type_Code, " +
			"    Sub_Ledger_Code, " +
			"    SUB_LEDGER_TYPE_DESC, " +
			"    Fin_Year " +
			"  )t " +
			"GROUP BY t.Data_Tb, " +
			"  t.ACCOUNTING_UNIT_ID, " +
			"  t.unit_name, " +
			"  "+sch_id+", " +
			"  t.head_desc, " +
			"  t.Sub_Ledger_Type_Code, " +
			"  t.Sub_Ledger_Code, " +
			"  t.SUB_LEDGER_TYPE_DESC, " +
			"  t.SLDESC " +
			"ORDER BY t.ACCOUNTING_UNIT_ID";
}





else if(cmd_unit.equalsIgnoreCase("SCH_F2")) 
{

	unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	sch_id=Integer.parseInt(request.getParameter("sch_id"));
	String sub_unit="";
	if(unit_id==0)
	{
		sub_unit="";
	}else
	{
		sub_unit="    and ACCOUNTING_UNIT_ID= " +unit_id;
	}
	
	/*qry=" SELECT t.Data_Tb, " +
	"  t.ACCOUNTING_UNIT_ID, " +
	"  t.unit_name, " +
	"  t.Schemeid, " +
	"  t.Schemename, " +
	"  t.Sub_Ledger_Type_Code, " +
	"  t.Sub_Ledger_Code, " +*/
//	"  /*  nvl((SELECT s.sl_codename " +
	//"  FROM sl_type_code_name_view s " +
//	"  WHERE t.Sub_Ledger_Type_Code=s.sl_type " +
//	"  AND t.Sub_Ledger_Code       =s.sl_code " +
//	"  ),'--') AS SLDESC , */ " +
	/*"  t.SUB_LEDGER_TYPE_DESC, " +
	"  t.SLDESC, " +
	"  SUM(SCH20) AS Net_Amt_459020, " +
	"  SUM(SCH31) AS Net_Amt_459029, " +
	"  SUM(SCH37) AS Net_Amt_459031, " +
	"  SUM(SCH41) AS Net_Amt_459037, " +
	"  SUM(SCH47) AS Net_Amt_459038, " +
	"  SUM(SCH51) AS Net_Amt_459040, " +
	"  SUM(SCH57) AS SCH57, " +
	"  SUM(SCH61) AS SCH61, " +
	"  SUM(SCH67) AS SCH67 ," +
	" sum(SCH31)+sum(SCH37)+sum(SCH41)+sum(SCH47)+sum(SCH51)+sum(SCH57)+sum(SCH61)+sum(SCH67) as tot_exp"+
	" FROM " +
	"  (SELECT 1 AS Data_Tb, " +
	"    ACCOUNTING_UNIT_ID, " +
	"    (SELECT trim(u.accounting_unit_name) " +
	"    FROM fas_mst_acct_units u " +
	"    WHERE u.accounting_unit_id=v.ACCOUNTING_UNIT_ID " +
	"    )AS unit_name, " +
	"    Fh.Schemeid, " +
	"    Ps.Schemename, " +
	"    V.Account_Head_Code, " +
	"    (SELECT h.account_head_desc " +
	"    FROM com_mst_account_heads h " +
	"    WHERE h.account_head_code=v.Account_Head_Code " +
	"    )AS head_desc, " +
	"    Fh.Headofaccount, " +
	"    Sub_Ledger_Type_Code, " +
	"    Sub_Ledger_Code, " +
	"  CASE "+
	  "   WHEN Sub_Ledger_Type_Code >0 "+
	  "   AND Sub_Ledger_Type_Code IS NOT NULL "+
	  "   THEN "+
	  "     (SELECT s.sl_codename "+
	  "     FROM sl_type_code_name_view s "+
	  "     WHERE s.sl_type= Sub_Ledger_Type_Code "+
	  "     AND s.sl_code=Sub_Ledger_Code  "+   
	  "     )"+
	  "   ELSE 'Blank Scheme' "+
	  " END AS SLDESC ,"+
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"20 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH20, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"31 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS sch31, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"37 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH37, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"41 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS sch41, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"47 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH47, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"51 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH51, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"57 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS sch57, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"61 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH61, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"67 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS SCH67, " +
	"    SUB_LEDGER_TYPE_DESC, " +
	"    Fin_Year " +
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
	"  AND fh.Schemeid = '"+sch_id+"' " +
	"  INNER JOIN sl_type_code_name_view s " +
	"  ON V.Sub_Ledger_Type_Code=s.sl_type " +
	"  AND V.Sub_Ledger_Code    =s.sl_code " +
	
	sup_query+sub_unit+ 
	//"    --//   AND V.Account_Head_Code IN (459020,459029,459031,459037,459038,459040) " +
	"  GROUP BY ACCOUNTING_UNIT_ID, " +
	"    Fh.Schemeid, " +
	"    Ps.Schemename, " +
	"    V.Account_Head_Code, " +
	"    Fh.Headofaccount, " +
	"    Sub_Ledger_Type_Code, " +
	"    Sub_Ledger_Code, " +
	"    SUB_LEDGER_TYPE_DESC, " +
	//"    NVL(s.sl_codename,'--') , " +
	"    Fin_Year " +
	"  )t " +
	" GROUP BY t.Data_Tb, " +
	"  t.ACCOUNTING_UNIT_ID, " +
	"  t.unit_name, " +
	"  t.Schemeid, " +
	"  t.Schemename, " +
	"  t.Sub_Ledger_Type_Code, " +
	"  t.Sub_Ledger_Code, " +
	"  t.SUB_LEDGER_TYPE_DESC, " +
	"  t.SLDESC " +
	" ORDER BY t.ACCOUNTING_UNIT_ID, " +
	"  t.Schemeid" ;*/
	qry="SELECT t.Data_Tb, " +
			"  t.ACCOUNTING_UNIT_ID, " +
			"  t.unit_name, " +
			"  "+sch_id+"        AS Schemeid, " +
			"  t.head_desc AS Schemename, " +
			"  t.Sub_Ledger_Type_Code, " +
			"  t.Sub_Ledger_Code, " +
			"  t.SUB_LEDGER_TYPE_DESC, " +
			"  t.SLDESC, " +
			"  SUM(SCH20)                                                                              AS Net_Amt_459020, " +
			"  SUM(SCH31)                                                                              AS Net_Amt_459029, " +
			"  SUM(SCH37)                                                                              AS Net_Amt_459031, " +
			"  SUM(SCH41)                                                                              AS Net_Amt_459037, " +
			"  SUM(SCH47)                                                                              AS Net_Amt_459038, " +
			"  SUM(SCH51)                                                                              AS Net_Amt_459040, " +
			"  SUM(SCH57)                                                                              AS SCH57, " +
			"  SUM(SCH61)                                                                              AS SCH61, " +
			"  SUM(SCH67)                                                                              AS SCH67 , " +
			"  SUM(SCH31)+SUM(SCH37)+SUM(SCH41)+SUM(SCH47)+SUM(SCH51)+SUM(SCH57)+SUM(SCH61)+SUM(SCH67) AS tot_exp " +
			"FROM " +
			"  (SELECT 1 AS Data_Tb, " +
			"    ACCOUNTING_UNIT_ID, " +
			"    (SELECT trim(u.accounting_unit_name) " +
			"    FROM fas_mst_acct_units u " +
			"    WHERE u.accounting_unit_id=v.ACCOUNTING_UNIT_ID " +
			"    )AS unit_name, " +
			"    V.Account_Head_Code, " +
			"    (SELECT h.account_head_desc " +
			"    FROM com_mst_account_heads h " +
			"    WHERE h.account_head_code="+sch_id+"31 " +
			"    )AS head_desc, " +
			"    Fh.Account_Head_Code, " +
			"    Sub_Ledger_Type_Code, " +
			"    Sub_Ledger_Code, " +
			"    CASE " +
			"      WHEN Sub_Ledger_Type_Code >0 " +
			"      AND Sub_Ledger_Type_Code IS NOT NULL " +
			"      THEN " +
			"        (SELECT s.sl_codename " +
			"        FROM sl_type_code_name_view s " +
			"        WHERE s.sl_type= Sub_Ledger_Type_Code " +
			"        AND s.sl_code  =Sub_Ledger_Code " +
			"        ) " +
			"      ELSE 'Blank Scheme' " +
			"    END AS SLDESC , " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"20 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH20, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"31 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS sch31, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"37 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH37, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"41 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS sch41, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"47 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH47, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"51 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH51, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"57 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS sch57, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"61 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH61, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"67 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS SCH67, " +
			"    SUB_LEDGER_TYPE_DESC, " +
			"    Fin_Year " +
			"  FROM Fas_Wrkexp_View V " +
			"  INNER JOIN COM_MST_ACCOUNT_HEADS Fh " +
			"  ON V.Account_Head_Code=FH.ACCOUNT_HEAD_CODE " +
			"  AND fh.ACCOUNT_HEAD_CODE LIKE '"+sch_id+"%' " +
	//		"  AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
	sup_query+sub_unit+ 
			"  GROUP BY 1, " +
			"    ACCOUNTING_UNIT_ID, " +
			"    V.Account_Head_Code, " +
			"    Fh.Account_Head_Code, " +
			"    Sub_Ledger_Type_Code, " +
			"    Sub_Ledger_Code, " +
			"    SUB_LEDGER_TYPE_DESC, " +
			"    Fin_Year " +
			"  )t " +
			"GROUP BY t.Data_Tb, " +
			"  t.ACCOUNTING_UNIT_ID, " +
			"  t.unit_name, " +
			"  "+sch_id+", " +
			"  t.head_desc, " +
			"  t.Sub_Ledger_Type_Code, " +
			"  t.Sub_Ledger_Code, " +
			"  t.SUB_LEDGER_TYPE_DESC, " +
			"  t.SLDESC " +
			"ORDER BY t.ACCOUNTING_UNIT_ID";
	
}


else if(cmd_unit.equalsIgnoreCase("SCH_F4"))
{
	
	
	
	
	
	 if(f_mnth.equalsIgnoreCase(t_mnth))
	   {
		  sup_query=   " AND cmonth          = '"+f_mnth+"' " ;
		 head= "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Abstract Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
	   }else if(!f_mnth.equalsIgnoreCase(t_mnth))
	   {
		   System.out.println(" test");
		  
					if ((!t_mnth.split("-")[1].equalsIgnoreCase("MAR")) && (t_mnth.split("-")[0].equalsIgnoreCase("01"))) {
						if (f_mnth.split("-")[1].equalsIgnoreCase("APR")) {
							head = "Unit wise / Schme wise Urban Town Panchayat Schemes MNP and Local Body Abstract Details for the Month  "
								+ f_mnth.split("-")[1]
													+ " - "
													+ f_mnth.split("-")[2]+" to "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2];
						} else {
							head = "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Abstract Details for the Period From  "
									+ f_mnth.split("-")[1]
									+ " - "
									+ f_mnth.split("-")[2]
									+ " To "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2];
						}
					
					}else if ((t_mnth.split("-")[1].equalsIgnoreCase("MAR"))&& (!t_mnth.split("-")[0].equalsIgnoreCase("01"))) {
						
						if (f_mnth.split("-")[1].equalsIgnoreCase("APR")) {
							head = "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Abstract Details for the Month   "
								+ f_mnth.split("-")[1]
													+ " - "
													+ f_mnth.split("-")[2]+" to "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2]+" Including Supplement";
						} else {
							head = "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Abstract Details for the Period From  "
									+ f_mnth.split("-")[1]
									+ " - "
									+ f_mnth.split("-")[2]
									+ " To "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2]+" Including Supplement";;
						}
					}
		  
		   
	/*	   
		  head = "SchemeWise Trial Balance Details for the Period From  "
				+ f_mnth.split("-")[1]
				+ " - "
				+ f_mnth.split("-")[2]
				+ " To "
				+ t_mnth.split("-")[1]
				+ " - "
				+ t_mnth.split("-")[2];*/
	   
		 sup_query= " AND cmonth   between '"+f_mnth+"' and '"+t_mnth+"'   " ;
	   }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	//sch_id=Integer.parseInt(request.getParameter("sch_id"));
	String sub_unit="";
	if(unit_id==0)
	{
		sub_unit="";
	}else
	{
		sub_unit="    and ACCOUNTING_UNIT_ID= " +unit_id;
	}
	
	qry="SELECT fin.ACCOUNTING_UNIT_ID, " +
			"  fin.unit_name, " +
			"  WE_OUT_MNP , " +
			"  CNETT_MNp , " +
			"  NVL(WE_OUT_LB,4839 " +
			"  ||SUBSTR(WE_OUT_MNP,5,1) " +
			"  ||7) AS WE_OUT_LB , " +
			"  NVL(CNETT_LB,4839 " +
			"  ||SUBSTR(WE_OUT_MNP,5,1) " +
			"  ||1)                       AS CNETT_LB , " +
			"  NVL(fin.WE_OUT_MNP_AMT,0)  AS WE_OUT_MNP_AMT , " +
			"  NVL(fin.CNETT_MNp_AMT,0)   AS CNETT_MNp_AMT , " +
			"  NVL(fin.WE_OUT_MNP_AMT1,0) AS WE_OUT_MNP_AMT1 , " +
			"  NVL(fin.CNETT_MNp_AMT1,0)  AS CNETT_MNp_AMT1 , " +
			"  NVL(fin.Fun_REc_amt,0)     AS Fun_REc_amt, " +
			"  fin.head_desc " +
			"FROM " +
			"  (SELECT aa.ACCOUNTING_UNIT_ID, " +
			"    (SELECT u.accounting_unit_name " +
			"    FROM FAS_MST_ACCT_UNITS u " +
			"    WHERE aa.ACCOUNTING_UNIT_ID=u.accounting_unit_id " +
			"    ) AS unit_name, " +
			"    CASE " +
			"      WHEN aa.Account_Head_Code LIKE '%31' " +
			"      THEN 'Regular Population' " +
			"      WHEN aa.Account_Head_Code LIKE '%41' " +
			"      THEN 'SC Population' " +
			"      WHEN aa.Account_Head_Code LIKE '%51' " +
			"      THEN 'ST Population' " +
			"      WHEN aa.Account_Head_Code LIKE '%61' " +
			"      THEN 'Minority Population' " +
			"    END head_desc, " +
			"    aa.Account_Head_Code WE_OUT_MNP, " +
			"    bb.Account_Head_Code CNETT_MNp, " +
			"    cc.Account_Head_Code WE_OUT_LB, " +
			"    dd.Account_Head_Code CNETT_LB, " +
			"    aa.WOut_TP_amt AS WE_OUT_MNP_AMT, " +
			"    bb.WOut_TP_amt AS CNETT_MNp_AMT, " +
			"    cc.WOut_TP_amt AS WE_OUT_MNP_AMT1, " +
			"    dd.WOut_TP_amt AS CNETT_MNp_AMT1, " +
			"    dd.Fun_REc_amt AS Fun_REc_amt " +
			"  FROM " +
			"    (SELECT rownum AS ss, " +
			"      a.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4838%1' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN com_mst_account_heads Fh " +
			"      ON V.Account_Head_Code=Fh.Account_Head_Code " +
		//	"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
		sup_query+sub_unit+ 
			"      AND V.Account_Head_Code LIKE '4838%1' " +
			"      GROUP BY ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        Fin_Year " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )a " +
			"    )aa " +
			"  INNER JOIN " +
			"    (SELECT rownum AS ss1 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4838%7' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN COM_MST_ACCOUNT_HEADS Fh " +
			"      ON V.Account_Head_Code=Fh.Account_Head_Code " +
		///	"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
		sup_query+sub_unit+ 
			"      AND V.Account_Head_Code LIKE '4838%7' " +
			"      GROUP BY ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        Fin_Year " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )bb ON aa.ss           =bb.ss1 " +
			"  AND aa.ACCOUNTING_UNIT_ID=bb.ACCOUNTING_UNIT_ID " +
			"  LEFT OUTER JOIN " +
			"    (SELECT rownum AS ss2 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4839%7' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN com_mst_account_heads Fh " +
			"      ON V.Account_Head_Code=Fh.Account_Head_Code " +
		//	"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
		sup_query+sub_unit+ 
			"      AND V.Account_Head_Code LIKE '4839%7' " +
			"      GROUP BY ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        Fin_Year " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )cc ON aa.ss           =cc.ss2 " +
			"  AND aa.ACCOUNTING_UNIT_ID=cc.ACCOUNTING_UNIT_ID " +
			"  LEFT OUTER JOIN " +
			"    (SELECT rownum AS ss3 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4839%1' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '483920' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS Fun_REc_amt, " +
			"        Fin_Year " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN com_mst_account_heads Fh " +
			"      ON V.Account_Head_Code=Fh.Account_Head_Code " +
			//"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
			sup_query+sub_unit+ 
			"      AND V.Account_Head_Code LIKE '4839%1' " +
			"      GROUP BY ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        Fin_Year " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )dd ON aa.ss           =dd.ss3 " +
			"  AND aa.ACCOUNTING_UNIT_ID=dd.ACCOUNTING_UNIT_ID " +
			"  )fin ORDER BY fin.ACCOUNTING_UNIT_ID " ;
			//"  --ORDER BY fin.ACCOUNTING_UNIT_ID,fin.Account_Head_Code";
	
	/*
	 * 
	 * 
	 * joan Change on 27 Oct 2014
	
	qry ="SELECT fin.ACCOUNTING_UNIT_ID, " +
			"  fin.unit_name, " +
			"  WE_OUT_MNP , " +
			"  CNETT_MNp , " +
			"  NVL(WE_OUT_LB,4839 " +
			"  ||SUBSTR(WE_OUT_MNP,5,1) " +
			"  ||7) AS WE_OUT_LB , " +
			"  NVL(CNETT_LB,4839 " +
			"  ||SUBSTR(WE_OUT_MNP,5,1) " +
			"  ||1)                       AS CNETT_LB , " +
			"  NVL(fin.WE_OUT_MNP_AMT,0)  AS WE_OUT_MNP_AMT , " +
			"  NVL(fin.CNETT_MNp_AMT,0)   AS CNETT_MNp_AMT , " +
			"  NVL(fin.WE_OUT_MNP_AMT1,0) AS WE_OUT_MNP_AMT1 , " +
			"  NVL(fin.CNETT_MNp_AMT1,0)  AS CNETT_MNp_AMT1 , " +
			"  NVL(fin.Fun_REc_amt,0)     AS Fun_REc_amt, " +
			"  fin.Schemeid " +
			"FROM " +
			"  (SELECT aa.ACCOUNTING_UNIT_ID, " +
			"    (SELECT u.accounting_unit_name " +
			"    FROM FAS_MST_ACCT_UNITS u " +
			"    WHERE aa.ACCOUNTING_UNIT_ID=u.accounting_unit_id " +
			"    ) AS unit_name, " +
			"    aa.Schemeid, " +
			"    aa.Account_Head_Code WE_OUT_MNP, " +
			"    bb.Account_Head_Code CNETT_MNp, " +
			"    cc.Account_Head_Code WE_OUT_LB, " +
			"    dd.Account_Head_Code CNETT_LB, " +
			"    aa.WOut_TP_amt AS WE_OUT_MNP_AMT, " +
			"    bb.WOut_TP_amt AS CNETT_MNp_AMT, " +
			"    cc.WOut_TP_amt AS WE_OUT_MNP_AMT1, " +
			"    dd.WOut_TP_amt AS CNETT_MNp_AMT1, " +
			"    dd.Fun_REc_amt AS Fun_REc_amt " +
			"  FROM " +
			"    (SELECT rownum AS ss, " +
			"      a.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        fh.Schemeid, " +
			"        V.Account_Head_Code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4838%1' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year, " +
			"        Ps.Schemename " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN fas_Headofaccounts Fh " +
			"      ON V.Account_Head_Code=Fh.Accountcode " +
		//	"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
		sup_query+sub_unit+ 
			"      AND fh.Schemeid ='4838' " +
			"      AND V.Account_Head_Code LIKE '4838%1' " +
			"      INNER JOIN Schemes Ps " +
			"      ON Fh.Projectid  =Ps.Projectid " +
			"      AND Fh.Programid =Ps.Programid " +
			"      AND fh.Schemeid  =Ps.Schemeid " +
			"      GROUP BY Fh.Accountcode, " +
			"        ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        fh.Schemeid, " +
			"        Fin_Year, " +
			"        Ps.Schemename " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )a " +
			"    )aa " +
			"  INNER JOIN " +
			"    (SELECT rownum AS ss1 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        fh.Schemeid, " +
			"        V.Account_Head_Code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4838%7' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year, " +
			"        Ps.Schemename " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN fas_Headofaccounts Fh " +
			"      ON V.Account_Head_Code=Fh.Accountcode " +
		//	"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
		sup_query+sub_unit+ 
			"      AND fh.Schemeid ='4838' " +
			"      AND V.Account_Head_Code LIKE '4838%7' " +
			"      INNER JOIN Schemes Ps " +
			"      ON Fh.Projectid  =Ps.Projectid " +
			"      AND Fh.Programid =Ps.Programid " +
			"      AND fh.Schemeid  =Ps.Schemeid " +
			"      GROUP BY Fh.Accountcode, " +
			"        ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        fh.Schemeid, " +
			"        Fin_Year, " +
			"        Ps.Schemename " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )bb ON aa.ss           =bb.ss1 " +
			"  AND aa.ACCOUNTING_UNIT_ID=bb.ACCOUNTING_UNIT_ID " +
			"  LEFT OUTER JOIN " +
			"    (SELECT rownum AS ss2 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        fh.Schemeid, " +
			"        V.Account_Head_Code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4839%7' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year, " +
			"        Ps.Schemename " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN fas_Headofaccounts Fh " +
			"      ON V.Account_Head_Code=Fh.Accountcode " +
	//		"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
	sup_query+sub_unit+ 
			"      AND fh.Schemeid ='4839' " +
			"      AND V.Account_Head_Code LIKE '4839%7' " +
			"      INNER JOIN Schemes Ps " +
			"      ON Fh.Projectid  =Ps.Projectid " +
			"      AND Fh.Programid =Ps.Programid " +
			"      AND fh.Schemeid  =Ps.Schemeid " +
			"      GROUP BY Fh.Accountcode, " +
			"        ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        fh.Schemeid, " +
			"        Fin_Year, " +
			"        Ps.Schemename " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )cc ON aa.ss           =cc.ss2 " +
			"  AND aa.ACCOUNTING_UNIT_ID=cc.ACCOUNTING_UNIT_ID " +
			"  LEFT OUTER JOIN " +
			"    (SELECT rownum AS ss3 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        fh.Schemeid, " +
			"        V.Account_Head_Code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4839%1' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '483920' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS Fun_REc_amt, " +
			"        Fin_Year, " +
			"        Ps.Schemename " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN fas_Headofaccounts Fh " +
			"      ON V.Account_Head_Code=Fh.Accountcode " +
//			"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
sup_query+sub_unit+ 
			"      AND fh.Schemeid ='4839' " +
			"      AND V.Account_Head_Code LIKE '4839%1' " +
			"      INNER JOIN Schemes Ps " +
			"      ON Fh.Projectid  =Ps.Projectid " +
			"      AND Fh.Programid =Ps.Programid " +
			"      AND fh.Schemeid  =Ps.Schemeid " +
			"      GROUP BY Fh.Accountcode, " +
			"        ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        fh.Schemeid, " +
			"        Fin_Year, " +
			"        Ps.Schemename " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )dd ON aa.ss           =dd.ss3 " +
			"  AND aa.ACCOUNTING_UNIT_ID=dd.ACCOUNTING_UNIT_ID " +
			"  )fin order by fin.ACCOUNTING_UNIT_ID"
;
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*qry=" SELECT aa.ACCOUNTING_UNIT_ID, " +
"  (SELECT u.accounting_unit_name " +
"  FROM FAS_MST_ACCT_UNITS u " +
"  WHERE aa.ACCOUNTING_UNIT_ID=u.accounting_unit_id " +
"  ) AS unit_name, " +
"  aa.Schemeid, " +
"  aa.Account_Head_Code WE_OUT_MNP, " +
"  bb.Account_Head_Code CNETT_MNp, " +
"  cc.Account_Head_Code WE_OUT_LB, " +
"  dd.Account_Head_Code CNETT_LB, " +
"  aa.WOut_TP_amt AS WE_OUT_MNP_AMT, " +
"  bb.WOut_TP_amt AS CNETT_MNp_AMT, " +
"  cc.WOut_TP_amt AS WE_OUT_MNP_AMT1, " +
"  dd.WOut_TP_amt AS CNETT_MNp_AMT1, " +
"  dd.Fun_REc_amt AS Fun_REc_amt " +
" FROM " +
"  (SELECT rownum AS ss, " +
"    a.* " +
"  FROM " +
"    (SELECT ACCOUNTING_UNIT_ID, " +
"      fh.Schemeid, " +
"      V.Account_Head_Code, " +
"      CASE " +
"        WHEN V.Account_Head_Code LIKE '4838%1' " +
"        THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"        ELSE 0 " +
"      END AS WOut_TP_amt, " +
"      Fin_Year, " +
"      Ps.Schemename " +
"    FROM Fas_Wrkexp_View V " +
"    INNER JOIN fas_Headofaccounts Fh " +
"    ON V.Account_Head_Code=Fh.Accountcode " +
"    AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
"    AND ACCOUNTING_UNIT_ID=38 " +
"    AND fh.Schemeid       ='4838' " +
"    AND V.Account_Head_Code LIKE '4838%1' " +
"    INNER JOIN Schemes Ps " +
"    ON Fh.Projectid  =Ps.Projectid " +
"    AND Fh.Programid =Ps.Programid " +
"    AND fh.Schemeid  =Ps.Schemeid " +
"    GROUP BY Fh.Accountcode, " +
"      ACCOUNTING_UNIT_ID, " +
"      V.Account_Head_Code, " +
"      fh.Schemeid, " +
"      Fin_Year, " +
"      Ps.Schemename " +
"    )a " +
"  )aa " +
" INNER JOIN " +
"  (SELECT rownum AS ss1 , " +
"    b.* " +
"  FROM " +
"    (SELECT ACCOUNTING_UNIT_ID, " +
"      fh.Schemeid, " +
"      V.Account_Head_Code, " +
"      CASE " +
"        WHEN V.Account_Head_Code LIKE '4838%7' " +
"        THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"        ELSE 0 " +
"      END AS WOut_TP_amt, " +
"      Fin_Year, " +
"      Ps.Schemename " +
"    FROM Fas_Wrkexp_View V " +
"    INNER JOIN fas_Headofaccounts Fh " +
"    ON V.Account_Head_Code=Fh.Accountcode " +
"    AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
//"    AND ACCOUNTING_UNIT_ID=38 " +
"    AND fh.Schemeid       ='4838' " +
"    AND V.Account_Head_Code LIKE '4838%7' " +
"    INNER JOIN Schemes Ps " +
"    ON Fh.Projectid  =Ps.Projectid " +
"    AND Fh.Programid =Ps.Programid " +
"    AND fh.Schemeid  =Ps.Schemeid " +
"    GROUP BY Fh.Accountcode, " +
"      ACCOUNTING_UNIT_ID, " +
"      V.Account_Head_Code, " +
"      fh.Schemeid, " +
"      Fin_Year, " +
"      Ps.Schemename " +
"    )b " +
"  )bb ON aa.ss=bb.ss1 " +
" INNER JOIN " +
"  (SELECT rownum AS ss2 , " +
"    b.* " +
"  FROM " +
"    (SELECT ACCOUNTING_UNIT_ID, " +
"      fh.Schemeid, " +
"      V.Account_Head_Code, " +
"      CASE " +
"        WHEN V.Account_Head_Code LIKE '4839%7' " +
"        THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"        ELSE 0 " +
"      END AS WOut_TP_amt, " +
"      Fin_Year, " +
"      Ps.Schemename " +
"    FROM Fas_Wrkexp_View V " +
"    INNER JOIN fas_Headofaccounts Fh " +
"    ON V.Account_Head_Code=Fh.Accountcode " +
//"    AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
//"    AND ACCOUNTING_UNIT_ID=38 " +
sup_query+sub_unit+ 
"    AND fh.Schemeid       ='4839' " +
"    AND V.Account_Head_Code LIKE '4839%7' " +
"    INNER JOIN Schemes Ps " +
"    ON Fh.Projectid  =Ps.Projectid " +
"    AND Fh.Programid =Ps.Programid " +
"    AND fh.Schemeid  =Ps.Schemeid " +
"    GROUP BY Fh.Accountcode, " +
"      ACCOUNTING_UNIT_ID, " +
"      V.Account_Head_Code, " +
"      fh.Schemeid, " +
"      Fin_Year, " +
"      Ps.Schemename " +
"    )b " +
"  )cc ON aa.ss=cc.ss2 " +
" INNER JOIN " +
"  (SELECT rownum AS ss3 , " +
"    b.* " +
"  FROM " +
"    (SELECT ACCOUNTING_UNIT_ID, " +
"      fh.Schemeid, " +
"      V.Account_Head_Code, " +
"      CASE " +
"        WHEN V.Account_Head_Code LIKE '4839%1' " +
"        THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"        ELSE 0 " +
"      END AS WOut_TP_amt, " +
"      CASE " +
"        WHEN V.Account_Head_Code LIKE '483920' " +
"        THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
"        ELSE 0 " +
"      END AS Fun_REc_amt, " +
"      Fin_Year, " +
"      Ps.Schemename " +
"    FROM Fas_Wrkexp_View V " +
"    INNER JOIN fas_Headofaccounts Fh " +
"    ON V.Account_Head_Code=Fh.Accountcode " +
"    AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
"    AND ACCOUNTING_UNIT_ID=38 " +
"    AND fh.Schemeid       ='4839' " +
"    AND V.Account_Head_Code LIKE '4839%1' " +
"    INNER JOIN Schemes Ps " +
"    ON Fh.Projectid  =Ps.Projectid " +
"    AND Fh.Programid =Ps.Programid " +
"    AND fh.Schemeid  =Ps.Schemeid " +
"    GROUP BY Fh.Accountcode, " +
"      ACCOUNTING_UNIT_ID, " +
"      V.Account_Head_Code, " +
"      fh.Schemeid, " +
"      Fin_Year, " +
"      Ps.Schemename " +
"    )b " +
"  )dd ON aa.ss=dd.ss3";*/

}else if(cmd_unit.equalsIgnoreCase("SCH_F41"))
	
{
	 if(f_mnth.equalsIgnoreCase(t_mnth))
	   {
		  sup_query=   " AND cmonth          = '"+f_mnth+"' " ;
		 head= "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
	   }else if(!f_mnth.equalsIgnoreCase(t_mnth))
	   {
		   System.out.println(" test");
		  
					if ((!t_mnth.split("-")[1].equalsIgnoreCase("MAR")) && (t_mnth.split("-")[0].equalsIgnoreCase("01"))) {
						if (f_mnth.split("-")[1].equalsIgnoreCase("APR")) {
							head = "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Abstract for the Month  "
								+ f_mnth.split("-")[1]
													+ " - "
													+ f_mnth.split("-")[2]+" to "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2];
						} else {
							head = "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Abstract for the Period From  "
									+ f_mnth.split("-")[1]
									+ " - "
									+ f_mnth.split("-")[2]
									+ " To "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2];
						}
					
					}else if ((t_mnth.split("-")[1].equalsIgnoreCase("MAR"))&& (!t_mnth.split("-")[0].equalsIgnoreCase("01"))) {
						
						if (f_mnth.split("-")[1].equalsIgnoreCase("APR")) {
							head = "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Abstract for the Month   "
								+ f_mnth.split("-")[1]
													+ " - "
													+ f_mnth.split("-")[2]+" to "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2]+" Including Supplement";
						} else {
							head = "Unit wise / Schme wise Urban Town Panchayat Schemes  MNP and Local Body Abstract for the Period From  "
									+ f_mnth.split("-")[1]
									+ " - "
									+ f_mnth.split("-")[2]
									+ " To "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2]+" Including Supplement";;
						}
					}
		  
		   
	/*	   
		  head = "SchemeWise Trial Balance Details for the Period From  "
				+ f_mnth.split("-")[1]
				+ " - "
				+ f_mnth.split("-")[2]
				+ " To "
				+ t_mnth.split("-")[1]
				+ " - "
				+ t_mnth.split("-")[2];*/
	   
		 sup_query= " AND cmonth   between '"+f_mnth+"' and '"+t_mnth+"'   " ;
	   }
	
	unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	//sch_id=Integer.parseInt(request.getParameter("sch_id"));
	String sub_unit="";
	if(unit_id==0)
	{
		sub_unit="";
	}else
	{
		sub_unit="    and ACCOUNTING_UNIT_ID= " +unit_id;
	}
	
	qry="SELECT fin.ACCOUNTING_UNIT_ID, " +
		//	"  fin.sub_ledger_type_code , " +
		///	"  fin.sub_ledger_code , " +
			//"  vw.sl_codename, " +
			"  fin.unit_name, " +
			"  WE_OUT_MNP , " +
			"  CNETT_MNp , " +
			"  NVL(WE_OUT_LB,4839 " +
			"  ||SUBSTR(WE_OUT_MNP,5,1) " +
			"  ||7) AS WE_OUT_LB , " +
			"  NVL(CNETT_LB,4839 " +
			"  ||SUBSTR(WE_OUT_MNP,5,1) " +
			"  ||1)                       AS CNETT_LB , " +
			"  NVL(fin.WE_OUT_MNP_AMT,0)  AS WE_OUT_MNP_AMT , " +
			"  NVL(fin.CNETT_MNp_AMT,0)   AS CNETT_MNp_AMT , " +
			"  NVL(fin.WE_OUT_MNP_AMT1,0) AS WE_OUT_MNP_AMT1 , " +
			"  NVL(fin.CNETT_MNp_AMT1,0)  AS CNETT_MNp_AMT1 , " +
			"  NVL(fin.Fun_REc_amt,0)     AS Fun_REc_amt, " +
		//	"  vw.sl_codename as head_desc " +
	//	  " fin.sub_ledger_code,fin.sub_ledger_type_code, " +
		 " (SELECT vw.sl_codename " +
		 " FROM SL_TYPE_CODE_NAME_VIEW vw " +
		 " WHERE fin.sub_ledger_type_code = vw.sl_type " +
		 " AND fin.sub_ledger_code        = vw.sl_code " +
		 " ) " +
		 " AS " +
		 "  head_desc"+
			" FROM " +
			"  (SELECT aa.ACCOUNTING_UNIT_ID, " +
			"    aa.sub_ledger_type_code , " +
			"    aa.sub_ledger_code , " +
			"    (SELECT u.accounting_unit_name " +
			"    FROM FAS_MST_ACCT_UNITS u " +
			"    WHERE aa.ACCOUNTING_UNIT_ID=u.accounting_unit_id " +
			"    ) AS unit_name, " +
			"    CASE " +
			"      WHEN aa.Account_Head_Code LIKE '%31' " +
			"      THEN 'Regular Population' " +
			"      WHEN aa.Account_Head_Code LIKE '%41' " +
			"      THEN 'SC Population' " +
			"      WHEN aa.Account_Head_Code LIKE '%51' " +
			"      THEN 'ST Population' " +
			"      WHEN aa.Account_Head_Code LIKE '%61' " +
			"      THEN 'Minority Population' " +
			"    END head_desc, " +
			"    aa.Account_Head_Code WE_OUT_MNP, " +
			"    bb.Account_Head_Code CNETT_MNp, " +
			"    cc.Account_Head_Code WE_OUT_LB, " +
			"    dd.Account_Head_Code CNETT_LB, " +
			"    aa.WOut_TP_amt AS WE_OUT_MNP_AMT, " +
			"    bb.WOut_TP_amt AS CNETT_MNp_AMT, " +
			"    cc.WOut_TP_amt AS WE_OUT_MNP_AMT1, " +
			"    dd.WOut_TP_amt AS CNETT_MNp_AMT1, " +
			"    dd.Fun_REc_amt AS Fun_REc_amt " +
			"  FROM " +
			"    (SELECT rownum AS ss, " +
			"      a.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        v.sub_ledger_type_code , " +
			"        v.sub_ledger_code , " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4838%1' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN com_mst_account_heads Fh " +
			"      ON V.Account_Head_Code=Fh.Account_Head_Code " +
			//"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
			sub_unit+sup_query+
			"      AND V.Account_Head_Code LIKE '4838%1' " +
			"      GROUP BY ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        v.sub_ledger_type_code , " +
			"        v.sub_ledger_code , " +
			"        Fin_Year " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )a " +
			"    )aa " +
			"  INNER JOIN " +
			"    (SELECT rownum AS ss1 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        v.sub_ledger_type_code , " +
			"        v.sub_ledger_code , " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4838%7' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN COM_MST_ACCOUNT_HEADS Fh " +
			"      ON V.Account_Head_Code=Fh.Account_Head_Code " +
		//	"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
		sub_unit+sup_query+
			"      AND V.Account_Head_Code LIKE '4838%7' " +
			"      GROUP BY ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        v.sub_ledger_type_code , " +
			"        v.sub_ledger_code , " +
			"        Fin_Year " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )bb ON aa.ss           =bb.ss1 " +
			"  AND aa.ACCOUNTING_UNIT_ID=bb.ACCOUNTING_UNIT_ID " +
			"  LEFT OUTER JOIN " +
			"    (SELECT rownum AS ss2 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        v.sub_ledger_type_code , " +
			"        v.sub_ledger_code, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4839%7' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        Fin_Year " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN com_mst_account_heads Fh " +
			"      ON V.Account_Head_Code=Fh.Account_Head_Code " +
		//	"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
		sub_unit+sup_query+
			"      AND V.Account_Head_Code LIKE '4839%7' " +
			"      GROUP BY ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        v.sub_ledger_type_code , " +
			"        v.sub_ledger_code , " +
			"        Fin_Year " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )cc ON aa.ss           =cc.ss2 " +
			"  AND aa.ACCOUNTING_UNIT_ID=cc.ACCOUNTING_UNIT_ID " +
			"  LEFT OUTER JOIN " +
			"    (SELECT rownum AS ss3 , " +
			"      b.* " +
			"    FROM " +
			"      (SELECT ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        v.sub_ledger_type_code , " +
			"        v.sub_ledger_code , " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '4839%1' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS WOut_TP_amt, " +
			"        CASE " +
			"          WHEN V.Account_Head_Code LIKE '483920' " +
			"          THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"          ELSE 0 " +
			"        END AS Fun_REc_amt, " +
			"        Fin_Year " +
			"      FROM Fas_Wrkexp_View V " +
			"      INNER JOIN com_mst_account_heads Fh " +
			"      ON V.Account_Head_Code=Fh.Account_Head_Code " +
			//"      AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
			sub_unit+sup_query+
			"      AND V.Account_Head_Code LIKE '4839%1' " +
			"      GROUP BY ACCOUNTING_UNIT_ID, " +
			"        V.Account_Head_Code, " +
			"        v.sub_ledger_type_code, " +
			"        v.sub_ledger_code, " +
			"        Fin_Year " +
			"      ORDER BY ACCOUNTING_UNIT_ID, " +
			"        v.Account_Head_Code " +
			"      )b " +
			"    )dd ON aa.ss           =dd.ss3 " +
			"  AND aa.ACCOUNTING_UNIT_ID=dd.ACCOUNTING_UNIT_ID " +
			"  )fin " +
		//	" INNER JOIN SL_TYPE_CODE_NAME_VIEW vw " +
		//	" ON fin.sub_ledger_type_code = vw.sl_type " +
		//	" AND fin.sub_ledger_code     = vw.sl_code"
		  " order by fin.ACCOUNTING_UNIT_ID ";

	
}
	
	else if(cmd_unit.equalsIgnoreCase("SCH_F5")){
	unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	//sch_id=Integer.parseInt(request.getParameter("sch_id"));
	String sub_unit="";
	if(unit_id==0)
	{
		sub_unit="";
	}else
	{
		sub_unit="    and ACCOUNTING_UNIT_ID= " +unit_id;
	}
	 if(f_mnth.equalsIgnoreCase(t_mnth))
	   {
		  sup_query=   " AND cmonth          = '"+f_mnth+"' " ;
		 head= "IUDM PROJECT FUNDS,EXPENDITURE DETAILS Details for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
	   }else if(!f_mnth.equalsIgnoreCase(t_mnth))
	   {
		   System.out.println(" test");
		  
					if ((!t_mnth.split("-")[1].equalsIgnoreCase("MAR")) && (t_mnth.split("-")[0].equalsIgnoreCase("01"))) {
						if (f_mnth.split("-")[1].equalsIgnoreCase("APR")) {
							head = "IUDM PROJECT FUNDS,EXPENDITURE DETAILS Details for the Month  "
								+ f_mnth.split("-")[1]
													+ " - "
													+ f_mnth.split("-")[2]+" to "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2];
						} else {
							head = "IUDM PROJECT FUNDS,EXPENDITURE DETAILS Details for the Period From  "
									+ f_mnth.split("-")[1]
									+ " - "
									+ f_mnth.split("-")[2]
									+ " To "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2];
						}
					
					}else if ((t_mnth.split("-")[1].equalsIgnoreCase("MAR"))&& (!t_mnth.split("-")[0].equalsIgnoreCase("01"))) {
						
						if (f_mnth.split("-")[1].equalsIgnoreCase("APR")) {
							head = "IUDM PROJECT FUNDS,EXPENDITURE DETAILS Details for the Month   "
								+ f_mnth.split("-")[1]
													+ " - "
													+ f_mnth.split("-")[2]+" to "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2]+" Including Supplement";
						} else {
							head = "IUDM PROJECT FUNDS,EXPENDITURE DETAILS Details for the Period From  "
									+ f_mnth.split("-")[1]
									+ " - "
									+ f_mnth.split("-")[2]
									+ " To "
									+ t_mnth.split("-")[1]
									+ " - "
									+ t_mnth.split("-")[2]+" Including Supplement";;
						}
					}
		  
		   
	/*	   
		  head = "SchemeWise Trial Balance Details for the Period From  "
				+ f_mnth.split("-")[1]
				+ " - "
				+ f_mnth.split("-")[2]
				+ " To "
				+ t_mnth.split("-")[1]
				+ " - "
				+ t_mnth.split("-")[2];*/
	   
		 sup_query= " AND cmonth   between '"+f_mnth+"' and '"+t_mnth+"'   " ;
	   }
	qry="SELECT ss.ACCOUNTING_UNIT_ID, " +
			"  upper(ss.SL_CODENAME) as SL_CODENAME , " +
			"  ss.unit_name, " +
			"  ss.SUB_LEDGER_TYPE_CODE, " +
			"  ss.SUB_LEDGER_CODE, " +
			"  ss.Fin_Year, " +
			"  ss.head_desc, " +
			"  SUM(amt26_20) AS amt26_20, " +
			"  SUM(amt26_21) AS amt26_21, " +
			"  SUM(amt26_31) AS amt26_31, " +
			"  SUM(amt26_37) AS amt26_37, " +
			"  SUM(amt26_41) AS amt26_41, " +
			"  SUM(amt26_47) AS amt26_47 " +
			" FROM " +
			"  (SELECT fin.ACCOUNTING_UNIT_ID, " +
			"    vw.SL_CODENAME, " +
			"    u.accounting_unit_name AS unit_name, " +
			"    fin.Account_Head_Code, " +
			"    fin.SUB_LEDGER_TYPE_CODE, " +
			"    fin.SUB_LEDGER_CODE, " +
			"    fin.amt, " +
			"    fin.Fin_Year, " +
			"    CASE " +
			"      WHEN fin.Account_Head_Code LIKE '4526%' " +
			"      THEN 'IUDM KFW WSS MUNICIPALITIES' " +
			"      WHEN fin.Account_Head_Code LIKE '4527%' " +
			"      THEN 'IUDM JNNURM WSS MUNICIPALITIES' " +
			"      WHEN fin.Account_Head_Code LIKE '4528%' " +
			"      THEN 'IUDM UIDSSMT WSIS MUNICIPALITIES' " +
			"      WHEN fin.Account_Head_Code LIKE '4529%' " +
			"      THEN 'IUDM MNP WSS Town PANCHAYATS' " +
			"      WHEN fin.Account_Head_Code LIKE '4530%' " +
			"      THEN 'IUDM KFW UGSS MUNICIPALITIES' " +
			"      WHEN fin.Account_Head_Code LIKE '4531%' " +
			"      THEN 'IUDM JNNURM UGSS MUNICIPALITIES' " +
			"      WHEN fin.Account_Head_Code LIKE '4532%' " +
			"      THEN 'IUDM UIDSSMT UGSS MUNICIPALITIES' " +
			"      WHEN fin.Account_Head_Code LIKE '4533%' " +
			"      THEN 'IUDM KFW UGSS TOWN PANCHAYATS' " +
			"      WHEN fin.Account_Head_Code LIKE '4534%' " +
			"      THEN 'IUDM JNNURM UGSS TOWN PANCHAYATS' " +
			"      WHEN fin.Account_Head_Code LIKE '4535%' " +
			"      THEN 'IUDM UIDSSMT UGSS TOWN PANCHAYATS' " +
			"    END AS head_desc, " +
			"    CASE " +
			"      WHEN fin.Account_Head_Code LIKE '%20' " +
			"      THEN fin.amt " +
			"      ELSE 0 " +
			"    END AS amt26_20, " +
			"    CASE " +
			"      WHEN fin.Account_Head_Code LIKE '%21' " +
			"      THEN fin.amt " +
			"      ELSE 0 " +
			"    END AS amt26_21, " +
			"    CASE " +
			"      WHEN fin.Account_Head_Code LIKE '%31' " +
			"      THEN fin.amt " +
			"      ELSE 0 " +
			"    END AS amt26_31, " +
			"    CASE " +
			"      WHEN fin.Account_Head_Code LIKE '%37' " +
			"      THEN fin.amt " +
			"      ELSE 0 " +
			"    END AS amt26_37, " +
			"    CASE " +
			"      WHEN fin.Account_Head_Code LIKE '%41' " +
			"      THEN fin.amt " +
			"      ELSE 0 " +
			"    END AS amt26_41, " +
			"    CASE " +
			"      WHEN fin.Account_Head_Code LIKE '%47' " +
			"      THEN fin.amt " +
			"      ELSE 0 " +
			"    END AS amt26_47 " +
			"  FROM " +
			"    (SELECT ACCOUNTING_UNIT_ID, " +
			"      V.Account_Head_Code, " +
			"      V.SUB_LEDGER_TYPE_CODE, " +
			"      V.SUB_LEDGER_CODE, " +
			"      SUM(Dr_Amt)-SUM(Cr_Amt) AS amt, " +
			"      Fin_Year " +
			"    FROM Fas_Wrkexp_View V " +
			"    INNER JOIN com_mst_account_heads Fh " +
			"    ON V.Account_Head_Code=Fh.Account_Head_Code " +
			//"      -- AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
			sub_unit+sup_query+
			"    AND (V.Account_Head_Code LIKE '4526%' " +
			"    OR V.Account_Head_Code LIKE '4527%' " +
			"    OR V.Account_Head_Code LIKE '4528%' " +
			"    OR V.Account_Head_Code LIKE '4529%' " +
			"    OR V.Account_Head_Code LIKE '4530%' " +
			"    OR V.Account_Head_Code LIKE '4531%' " +
			"    OR V.Account_Head_Code LIKE '4532%' " +
			"    OR V.Account_Head_Code LIKE '4533%' " +
			"    OR V.Account_Head_Code LIKE '4534%' " +
			"    OR V.Account_Head_Code LIKE '4535%' ) " +
			"    GROUP BY ACCOUNTING_UNIT_ID, " +
			"      V.Account_Head_Code, " +
			"      V.SUB_LEDGER_TYPE_CODE, " +
			"      V.SUB_LEDGER_CODE, " +
			"      Fin_Year " +
			"    )fin " +
			"  INNER JOIN FAS_MST_ACCT_UNITS u " +
			"  ON fin.ACCOUNTING_UNIT_ID=u.accounting_unit_id " +
			"  INNER JOIN SL_TYPE_CODE_NAME_VIEW vw " +
			"  ON fin.SUB_LEDGER_TYPE_CODE=vw.SL_TYPE " +
			"  AND fin.SUB_LEDGER_CODE    =vw.SL_CODE " +
			"  ORDER BY fin.ACCOUNTING_UNIT_ID, " +
			"    fin.Account_Head_Code " +
			"  )ss " +
			" GROUP BY ss.ACCOUNTING_UNIT_ID, " +
			"  ss.SL_CODENAME, " +
			"  ss.unit_name, " +
			"  ss.SUB_LEDGER_TYPE_CODE, " +
			"  ss.SUB_LEDGER_CODE, " +
			"  ss.Fin_Year, " +
			"  ss.head_desc " +
			" ORDER BY ss.ACCOUNTING_UNIT_ID";
}

else if(cmd_unit.equalsIgnoreCase("SCH"))
{
	unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	sch_id=Integer.parseInt(request.getParameter("sch_id"));
	String sub_unit="";
	if(unit_id==0)
	{
		sub_unit="";
	}else
	{
		sub_unit="    and ACCOUNTING_UNIT_ID= " +unit_id;
	}
	
	/*qry="SELECT t.Data_Tb, " +
	"  t.ACCOUNTING_UNIT_ID, " +
	"  t.unit_name, " +
	"  t.Schemeid, " +
	"  t.Schemename, " +
	"  t.Sub_Ledger_Type_Code, " +
	"  t.Sub_Ledger_Code, " +
	"  nvl((SELECT s.sl_codename " +
	"  FROM sl_type_code_name_view s " +
	"  WHERE t.Sub_Ledger_Type_Code=s.sl_type " +
	"  AND t.Sub_Ledger_Code       =s.sl_code " +
	"  ),'--') AS SLDESC , " +
	"  t.SUB_LEDGER_TYPE_DESC,t.SLDESC, " +
	"  SUM(Net_Amt_459020) AS Net_Amt_459020, " +
	"  SUM(Net_Amt_459029) AS Net_Amt_459029, " +
	"  SUM(Net_Amt_459031) AS Net_Amt_459031, " +
	"  SUM(Net_Amt_459037) AS Net_Amt_459037, " +
	"  SUM(Net_Amt_459038) AS Net_Amt_459038, " +
	"  SUM(Net_Amt_459040) AS Net_Amt_459040 " +
	" FROM " +
	"  (SELECT 1 AS Data_Tb, " +
	"    ACCOUNTING_UNIT_ID, " +
	"    (SELECT trim(u.accounting_unit_name) " +
	"    FROM fas_mst_acct_units u " +
	"    WHERE u.accounting_unit_id=v.ACCOUNTING_UNIT_ID " +
	"    )AS unit_name, " +
	"    Fh.Schemeid, " +
	"    Ps.Schemename, " +
	"    V.Account_Head_Code, " +
	"    (SELECT h.account_head_desc " +
	"    FROM com_mst_account_heads h " +
	"    WHERE h.account_head_code=v.Account_Head_Code " +
	"    )AS head_desc, " +
	"    Fh.Headofaccount, " +
	"    Sub_Ledger_Type_Code, " +
	"    Sub_Ledger_Code," +
//	"NVL(s.sl_codename,'--') AS SLDESC , " +
	"  CASE "+
	  "   WHEN Sub_Ledger_Type_Code >0 "+
	  "   AND Sub_Ledger_Type_Code IS NOT NULL "+
	  "   THEN "+
	  "     (SELECT s.sl_codename "+
	  "     FROM sl_type_code_name_view s "+
	  "     WHERE s.sl_type= Sub_Ledger_Type_Code "+
	  "     AND s.sl_code=Sub_Ledger_Code  "+   
	  "     )"+
	  "   ELSE 'Blank Scheme' "+
	  " END AS SLDESC ,"+
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"20 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459020, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"29 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459029, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"31 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459031, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"37 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459037, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"38 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459038, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code="+sch_id+"40 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459040, " +
	"    SUB_LEDGER_TYPE_DESC, " +
	"    Fin_Year " +
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
	" AND fh.Schemeid = '"+sch_id+"'"+
	" inner join sl_type_code_name_view s "+
	"  on V.Sub_Ledger_Type_Code=s.sl_type "+
	"  AND V.Sub_Ledger_Code       =s.sl_code "+
	sup_query+sub_unit+ 
//	"  AND V.Account_Head_Code IN (459020,459029,459031,459037,459038,459040) " +
	"  GROUP BY ACCOUNTING_UNIT_ID, " +
	"    Fh.Schemeid, " +
	"    Ps.Schemename, " +
	"    V.Account_Head_Code, " +
	"    Fh.Headofaccount, " +
	"    Sub_Ledger_Type_Code, " +
	"    Sub_Ledger_Code, " +
	"    SUB_LEDGER_TYPE_DESC," +
	//" NVL(s.sl_codename,'--') ," +
	"    Fin_Year " +
	"  )t " +
	" GROUP BY t.Data_Tb, " +
	"  t.ACCOUNTING_UNIT_ID, " +
	"  t.unit_name, " +
	"  t.Schemeid, " +
	"  t.Schemename, " +
	"  t.Sub_Ledger_Type_Code, " +
	"  t.Sub_Ledger_Code, " +
	"  t.SUB_LEDGER_TYPE_DESC," +
	" t.SLDESC " +
	"order by  t.ACCOUNTING_UNIT_ID,t.Schemeid ";
	
	*/
	qry="SELECT t.Data_Tb, " +
			"  t.ACCOUNTING_UNIT_ID, " +
			"  t.unit_name, " +
			"  "+sch_id+"        AS Schemeid, " +
			"  t.head_desc AS Schemename, " +
			"  t.Sub_Ledger_Type_Code, " +
			"  t.Sub_Ledger_Code, " +
			"  t.SUB_LEDGER_TYPE_DESC, " +
			"  t.SLDESC, " +
			"  SUM(Net_Amt_459020) AS Net_Amt_459020, " +
			"  SUM(Net_Amt_459029) AS Net_Amt_459029, " +
			"  SUM(Net_Amt_459031) AS Net_Amt_459031, " +
			"  SUM(Net_Amt_459037) AS Net_Amt_459037, " +
			"  SUM(Net_Amt_459038) AS Net_Amt_459038, " +
			"  SUM(Net_Amt_459040) AS Net_Amt_459040 " +
			"FROM " +
			"  (SELECT 1 AS Data_Tb, " +
			"    ACCOUNTING_UNIT_ID, " +
			"    (SELECT trim(u.accounting_unit_name) " +
			"    FROM fas_mst_acct_units u " +
			"    WHERE u.accounting_unit_id=v.ACCOUNTING_UNIT_ID " +
			"    )AS unit_name, " +
			"    V.Account_Head_Code, " +
			"    Fh.Account_Head_Code, " +
			"    (SELECT com.account_head_desc " +
			"    FROM COM_MST_ACCOUNT_HEADS com " +
			"    WHERE com.account_head_code="+sch_id+"31 " +
			"    )AS head_desc, " +
			"    Sub_Ledger_Type_Code, " +
			"    Sub_Ledger_Code, " +
			"    CASE " +
			"      WHEN Sub_Ledger_Type_Code >0 " +
			"      AND Sub_Ledger_Type_Code IS NOT NULL " +
			"      THEN " +
			"        (SELECT s.sl_codename " +
			"        FROM sl_type_code_name_view s " +
			"        WHERE s.sl_type= Sub_Ledger_Type_Code " +
			"        AND s.sl_code  =Sub_Ledger_Code " +
			"        ) " +
			"      ELSE 'Blank Scheme' " +
			"    END AS SLDESC , " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"20 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS Net_Amt_459020, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"29 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS Net_Amt_459029, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"31 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS Net_Amt_459031, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"37 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS Net_Amt_459037, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"38 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS Net_Amt_459038, " +
			"    CASE " +
			"      WHEN v.Account_Head_Code="+sch_id+"40 " +
			"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
			"      ELSE 0 " +
			"    END AS Net_Amt_459040, " +
			"    SUB_LEDGER_TYPE_DESC, " +
			"    Fin_Year " +
			"  FROM Fas_Wrkexp_View V " +
			"  INNER JOIN COM_MST_ACCOUNT_HEADS Fh " +
			"  ON V.Account_Head_Code=fh.account_head_code " +
			"  AND fh.account_head_code LIKE '"+sch_id+"%' " +
		//	"  AND cmonth BETWEEN '01-APR-2013' AND '02-MAR-2014' " +
		sup_query+sub_unit+ 
			"  GROUP BY 1, " +
			"    ACCOUNTING_UNIT_ID, " +
			"    V.Account_Head_Code, " +
			"    Fh.Account_Head_Code, " +
			"    Sub_Ledger_Type_Code, " +
			"    Sub_Ledger_Code, " +
			"    SUB_LEDGER_TYPE_DESC, " +
			"    Fin_Year " +
			"  )t " +
			"GROUP BY t.Data_Tb, " +
			"  t.ACCOUNTING_UNIT_ID, " +
			"  t.unit_name, " +
			"  "+sch_id+", " +
			"  t.head_desc, " +
			"  t.Sub_Ledger_Type_Code, " +
			"  t.Sub_Ledger_Code, " +
			"  t.SUB_LEDGER_TYPE_DESC, " +
			"  t.SLDESC " +
			"ORDER BY t.ACCOUNTING_UNIT_ID " +
			"  --,  t.head_desc" ;
	  /*qry="SELECT t.Data_Tb,t.ACCOUNTING_UNIT_ID,t.unit_name, " +
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
	   "    ACCOUNTING_UNIT_ID, " +
		"    (SELECT trim(u.accounting_unit_name) " +
		"    FROM fas_mst_acct_units u " +
		"    WHERE u.accounting_unit_id=v.ACCOUNTING_UNIT_ID " +
		"    )AS unit_name, " +
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
	   sub_unit+
	   "  AND Fh.Schemeid='"+wE_Type+"' " +
	   "  GROUP BY To_Number (Fh.Projectid), " +
	   "    Pr.Projectname, " +
	   "    Fh.Programid, " +
	   "    Pg.Programname, " +
	   " ACCOUNTING_UNIT_ID, " +
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
	   " INNER JOIN sl_type_code_name_view s " +
	   " ON t.Sub_Ledger_Type_Code=s.sl_type " +
	   " AND t.Sub_Ledger_Code    =s.sl_code " +
	   " ORDER BY t.ACCOUNTING_UNIT_ID,	" +
	   "  t.unit_name,t.Schemeid, " +
	   "  t.Account_Head_Code, " +
	   "  t.Sub_Ledger_Type_Code, " +
	   "  t.Sub_Ledger_Code" ;*/
}
else{
	unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	String sub_unit="";
	if(unit_id==0)
	{
		sub_unit="";
	}else
	{
		sub_unit="    and ACCOUNTING_UNIT_ID= " +unit_id;
	}
	qry="SELECT t.Data_Tb, " +
	"  t.ACCOUNTING_UNIT_ID, " +
	"  t.unit_name, " +
	"  t.Schemeid, " +
	"  t.Schemename, " +
	"  t.Sub_Ledger_Type_Code, " +
	"  t.Sub_Ledger_Code, " +
	"   nvl((SELECT s.sl_codename " +
	"  FROM sl_type_code_name_view s " +
	"  WHERE t.Sub_Ledger_Type_Code=s.sl_type " +
	"  AND t.Sub_Ledger_Code       =s.sl_code " +
	"  ),'--') AS SLDESC , " +
	"  t.SUB_LEDGER_TYPE_DESC, " +
	"  SUM(Net_Amt_459020) AS Net_Amt_459020, " +
	"  SUM(Net_Amt_459029) AS Net_Amt_459029, " +
	"  SUM(Net_Amt_459031) AS Net_Amt_459031, " +
	"  SUM(Net_Amt_459037) AS Net_Amt_459037, " +
	"  SUM(Net_Amt_459038) AS Net_Amt_459038, " +
	"  SUM(Net_Amt_459040) AS Net_Amt_459040 " +
	" FROM " +
	"  (SELECT 1 AS Data_Tb, " +
	"    ACCOUNTING_UNIT_ID, " +
	"    (SELECT trim(u.accounting_unit_name) " +
	"    FROM fas_mst_acct_units u " +
	"    WHERE u.accounting_unit_id=v.ACCOUNTING_UNIT_ID " +
	"    )AS unit_name, " +
	"    Fh.Schemeid, " +
	"    Ps.Schemename, " +
	"    V.Account_Head_Code, " +
	"    (SELECT h.account_head_desc " +
	"    FROM com_mst_account_heads h " +
	"    WHERE h.account_head_code=v.Account_Head_Code " +
	"    )AS head_desc, " +
	"    Fh.Headofaccount, " +
	"    Sub_Ledger_Type_Code, " +
	"    Sub_Ledger_Code, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code=459020 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459020, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code=459029 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459029, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code=459031 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459031, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code=459037 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459037, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code=459038 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459038, " +
	"    CASE " +
	"      WHEN v.Account_Head_Code=459040 " +
	"      THEN SUM(Dr_Amt)-SUM(Cr_Amt) " +
	"      ELSE 0 " +
	"    END AS Net_Amt_459040, " +
	"    SUB_LEDGER_TYPE_DESC, " +
	"    Fin_Year " +
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
	
	sup_query+sub_unit+ 
	"  AND V.Account_Head_Code IN (459020,459029,459031,459037,459038,459040) " +
	"  GROUP BY ACCOUNTING_UNIT_ID, " +
	"    Fh.Schemeid, " +
	"    Ps.Schemename, " +
	"    V.Account_Head_Code, " +
	"    Fh.Headofaccount, " +
	"    Sub_Ledger_Type_Code, " +
	"    Sub_Ledger_Code, " +
	"    SUB_LEDGER_TYPE_DESC, " +
	"    Fin_Year " +
	"  )t " +
	" GROUP BY t.Data_Tb, " +
	"  t.ACCOUNTING_UNIT_ID, " +
	"  t.unit_name, " +
	"  t.Schemeid, " +
	"  t.Schemename, " +
	"  t.Sub_Ledger_Type_Code, " +
	"  t.Sub_Ledger_Code, " +
	"  t.SUB_LEDGER_TYPE_DESC order by  t.ACCOUNTING_UNIT_ID,t.Schemeid ";
}
}	    
System.out.println(Re_Type+">>> .... "+qry);
	    	try{
	    		if(cmd_unit.equalsIgnoreCase("N")){
	      		      reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/fas_schemewise_WXP.jasper"));   
	    		}	else if(cmd_unit.equalsIgnoreCase("SCH")){
	    			 if (Re_Type.equalsIgnoreCase("EXCEL_R"))  {
	    				 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_WXP_EXL.jasper")); 
	    			 }
	    			 else{  //reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/schemewise_WXP.jasper"));
	    			reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_WXP.jasper"));
	    			 }
	    		}	else if(cmd_unit.equalsIgnoreCase("SCH_F2")){
	    			 if (Re_Type.equalsIgnoreCase("EXCEL_R"))  {
	    			 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_Format2_EXL.jasper")); 
	    			 }
	    			 else{  //reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/schemewise_WXP.jasper"));
	    			reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_Format2.jasper"));
	    			 }
	    		}	else if(cmd_unit.equalsIgnoreCase("SCH_F3")){
	    			 if (Re_Type.equalsIgnoreCase("EXCEL_R"))  {
		    			 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_Format3_XL.jasper")); 
		    			 }
		    			 else{  //reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/schemewise_WXP.jasper"));
		    			reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_Format3.jasper"));
		    			 }
		    		}else if(cmd_unit.equalsIgnoreCase("SCH_F4") || cmd_unit.equalsIgnoreCase("SCH_F41")){
		    			 if (Re_Type.equalsIgnoreCase("EXCEL_R"))  {
			    			 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_Format4.jasper")); 
			    			 }
			    			 else{  //reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/schemewise_WXP.jasper"));
			    			reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_Format4.jasper"));
			    			 }
			    		}
		    		else if(cmd_unit.equalsIgnoreCase("SCH_F5")){

		    			 if (Re_Type.equalsIgnoreCase("EXCEL_R"))  {
			    			 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_Format5.jasper")); 
			    			 }
			    			 else{  //reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/schemewise_WXP.jasper"));
			    			reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/Unitschemewise_Format5.jasper"));
			    			 }
			    		
		    		}
	    		else {
	    			 if (Re_Type.equalsIgnoreCase("EXCEL_R"))  {
	    				 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/fas_Unitschemewise_WXP_EXL.jasper")); 
	    			 }else{
	    			
	    			reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Twad_EXP/fas_Unitschemewise_WXP.jasper"));	
	    		}
	    		}
	    			System.out.println(" Report File >>>  "+reportFile); 
	    		  if (!reportFile.exists())
	    		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
	    		    
	    		    JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		    
	    		    Map map = null;
	    		    map = new HashMap();
	    		    map.put("qry", qry);
	    		    map.put("head",head);
	    		    map.put("sch_id", sch_id);
	    		 //   System.out.println("Map va;ue  ... "+map);
	    		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);   
	    		  //
	    			//
	    			
	    	        if (Re_Type.equalsIgnoreCase("HTML_R"))   
	    	        {
	    	            System.out.println("HTML :::::::::::");
	    	                    response.setContentType("text/html");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Unitwise Full Deposit Scheme Report.html\"");
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
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Unitwise Full Deposit Scheme Report.pdf\"");
	    	                    OutputStream out = response.getOutputStream();
	    	                    out.write(buf, 0, buf.length);
	    	                    out.close();
	    	        }  else   if (Re_Type.equalsIgnoreCase("EXCEL_R"))   
	    	        {
	    	        	System.out.println("Excel .... ");
	    	                        response.setContentType("application/vnd.ms-excel");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Unitwise Full Deposit Scheme Report.xls\"");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}