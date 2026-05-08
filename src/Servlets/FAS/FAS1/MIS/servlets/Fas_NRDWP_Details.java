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
public class Fas_NRDWP_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static  String CONTENT_TYPE = 
            "text/xml; charset=windows-1252";
        Connection connection = null;
    public Fas_NRDWP_Details() {
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
         unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        String Re_Type="";
	     try{   
if(command.equalsIgnoreCase("live"))	    	 
{
	
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
    		 head= "Trial Balance Data for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
    	   } if(!f_mnth.equalsIgnoreCase(t_mnth))
    	   {
    		   sup_query= " AND cmonth   between '"+f_mnth+"' and '"+t_mnth+"'   " ;
    		   head= "Trial Balance Data for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
    	   }
    	 
    	   
    	   
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
    	  unit_qry+
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
    	   "ORDER BY aa.categoryid, " +
    	   "  hh.annualgroupingid, " +
    	   "  hh.minorgroupingid" ;
}else if(command.equalsIgnoreCase("fzdlive"))
{
	  unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));

	//EXCEL_R
	//PDF_R
	Re_Type=request.getParameter("FZrad_R");
	   fin_FY=request.getParameter("fzdFY");
       fin_TY=request.getParameter("fzdTY");
       f_mnth=request.getParameter("fromfzdMonth");
       t_mnth=request.getParameter("tofzdMonth");
  
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
            		   head= "Trial Balance Data for the Month Upto  "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
                   }
            	   head= "Trial Balance Data for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
            	   sup_query=" AND Month_Tb BETWEEN '" +f_mnth+"'  AND '" +t_mnth+"' " ;
            	   
                   }else if(f_mnth.equalsIgnoreCase(t_mnth))
                   {
                	  sup_query= " AND  Month_Tb = '" +f_mnth+"' " ;
                	  head= "Trial Balance Data for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
                   }
               qry="SELECT 1 as data_tb,v.account_head_code, " +
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
               "ORDER BY aa.categoryid, " +
               "  hh.annualgroupingid, " +
               "  hh.minorgroupingid";
	     
 }

	    	 System.out.println(Re_Type+">>> .... "+qry);
	    	try{
	    		/*if(chk_cons==0){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/fas_trialDetail_report.jasper"));
	    		}
	    		else{*/
		    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/fas_trialDetail_report_final.jasper"));	    			
	    		//}
	    		//	System.out.println(" Report File >>>  "+reportFile); 
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
    	   if(Type.equalsIgnoreCase("TYL") || Type.equalsIgnoreCase("FYL")|| Type.equalsIgnoreCase("ConFYL"))
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
    	 
    	   
    	   
    	   
    	   
    	   }else if(Type.equalsIgnoreCase("FYFz") || Type.equalsIgnoreCase("TYFz")||Type.equalsIgnoreCase("ConFYFz"))
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
    	// System.out.println(qry);
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
    	   }
    	   
    	   if(Type.equalsIgnoreCase("ConFYL")){
    	   xml+="<type>TYL</type><type1>FYL</type1>";
    	   }else if(Type.equalsIgnoreCase("ConFYFz")){
    		   xml+="<type1>FYFz</type1><type>TYFz</type>";
    	   
    	   }else
    	   {
    		   xml+="<type>"+Type+"</type>";  
    	   }
    		   }
    	   catch (Exception e) {
//System.out.println(e);
    	   }
    	   
    	   
       }xml+="</response>";
    // System.out.println(xml);
       out.write(xml);
       out.close();
        
	}

}