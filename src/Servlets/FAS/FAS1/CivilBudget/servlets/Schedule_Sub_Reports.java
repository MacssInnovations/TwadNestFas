package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
public class Schedule_Sub_Reports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static final String CONTENT_TYPE = 
            "text/html; charset=windows-1252";
        Connection connection = null;
    public Schedule_Sub_Reports() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}
        
    
        
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  PreparedStatement ps_st=null;
		   ResultSet rs_st=null;    
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
        
        try
        {String fin_year="";
        String[] val=null;
        int txtCB_Year_from=0;
        int txtCB_Month_from=0;
        int txtCB_Year_to=0;
        int txtCB_Month_to=0,pre_year_from=0,pre_year_to=0;
        int minor_code=0;
        String command=""; String rtype="";String minor_desx="",minorHeading="";
        String minor_qry="";
        String  pre_fin_year="";
        String head="",major_code="",qry_String="",qry_String1="";
        	String Command=request.getParameter("hid_text");
        	String supp_Cmd=request.getParameter("hid_cmd");
        	String head_Cmd=request.getParameter("yes_hid");
        	major_code=request.getParameter("cmbBudgetGroupMajor");
        	 minor_code=Integer.parseInt(request.getParameter("cmbMinor_Head"));
             System.out.println("Minor_Head >>>> "+minor_code);
        	System.out.println(" >>> "+Command+" >> Supp Cmd"+supp_Cmd+" >>> Head_Cmd >> "+head_Cmd);
       
        	try{
        		minor_qry="SELECT MINOR_HEAD_DESC FROM COM_MST_MINOR_HEADS m WHERE m.Major_HEAD_CODE='"+major_code+"' and  m.MINOR_HEAD_CODE= "+minor_code;
        	ps_st=connection.prepareStatement(minor_qry);
        	rs_st=ps_st.executeQuery();
        	while(rs_st.next()){
        		minor_desx=rs_st.getString("MINOR_HEAD_DESC");
        	}
        	}catch (Exception e) {
				System.out.println(e);
			}
        	
        	if(Command.equalsIgnoreCase("Full")){
        	
        	fin_year=request.getParameter("fin_year");
        	 val=fin_year.split("-");
        	System.out.println(val[0]+" >>> "+val[1]);
        	minorHeading=minor_desx+" for "+fin_year;
        	txtCB_Year_from=Integer.parseInt(val[0]);
             txtCB_Month_from=4;
             txtCB_Year_to=Integer.parseInt(val[1]);
             txtCB_Month_to=3;
            pre_fin_year=(txtCB_Year_from-1)+"-"+(txtCB_Year_to-1);
            System.out.println("txtCB_Year_from >>>> "+txtCB_Year_from);
            System.out.println("txtCB_Month_from >>>> "+txtCB_Month_from);
            System.out.println("txtCB_Year_to >>>> "+txtCB_Year_to);
            System.out.println("txtCB_Month_to >>>> "+txtCB_Month_to);
       pre_year_from=txtCB_Year_from-1; 
      pre_year_to=txtCB_Year_to-1;
      qry_String="UNION ALL " +
		"  (SELECT a.ACCOUNTING_UNIT_ID, " +
		"    a.ACCOUNTING_FOR_OFFICE_ID, " +
		"    a.CASHBOOK_YEAR, " +
		"    a.CASHBOOK_MONTH, " +
		"    a.ACCOUNT_HEAD_CODE AS headcode, " +
		"    a.DEBIT_OPENING_BALANCE, " +
		"    a.CREDIT_OPENING_BALANCE, " +
		"    a.CURRENT_MONTH_DEBIT, " +
		"    a.CURRENT_MONTH_CREDIT, " +
		"    a.DEBIT_CLOSING_BALANCE, " +
		"    a.CREDIT_CLOSING_BALANCE, " +
		"    a.UPDATED_BY_USER_ID, " +
		"    a.UPDATED_DATE, " +
		"    a.SUPPLEMENT_NO AS supno ," +
		" (select sub.GROUP_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as sub_Grp ,"+
        " (select sub.ORDER_NO from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as order_no "+
		"  FROM " +
		"    (SELECT * " +
		"    FROM FAS_TRIAL_BALANCE_SUPPLEMENT " +
		"    WHERE ACCOUNT_HEAD_CODE IN " +
		"      (SELECT account_head_code " +
		"      FROM com_mst_account_heads " +
		"      WHERE MAJOR_HEAD_CODE= '" +major_code+"'"+
		"      AND MINOR_HEAD_CODE  = " +minor_code+
		"      ) " +
		"    AND Cashbook_Month=3 " +
		"    AND cashbook_year = " +pre_year_to+
		"    ORDER BY accounting_unit_id " +
		"    )a " +
		"  )";
		qry_String1="UNION ALL " +
		"  (SELECT a.ACCOUNTING_UNIT_ID, " +
		"    a.ACCOUNTING_FOR_OFFICE_ID, " +
		"    a.CASHBOOK_YEAR, " +
		"    a.CASHBOOK_MONTH, " +
		"    a.ACCOUNT_HEAD_CODE AS headcode, " +
		"    a.DEBIT_OPENING_BALANCE, " +
		"    a.CREDIT_OPENING_BALANCE, " +
		"    a.CURRENT_MONTH_DEBIT, " +
		"    a.CURRENT_MONTH_CREDIT, " +
		"    a.DEBIT_CLOSING_BALANCE, " +
		"    a.CREDIT_CLOSING_BALANCE, " +
		"    a.UPDATED_BY_USER_ID, " +
		"    a.UPDATED_DATE, " +
		"    a.SUPPLEMENT_NO AS supno ," +
		" (select sub.GROUP_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as sub_Grp ,"+
        " (select sub.ORDER_NO from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as order_no "+
		"  FROM " +
		"    (SELECT * " +
		"    FROM FAS_TRIAL_BALANCE_SUPPLEMENT " +
		"    WHERE ACCOUNT_HEAD_CODE IN " +
		"      (SELECT account_head_code " +
		"      FROM com_mst_account_heads " +
		"      WHERE MAJOR_HEAD_CODE= '" +major_code+"'"+
		"      AND MINOR_HEAD_CODE  = " +minor_code+
		"      ) " +
		"    AND Cashbook_Month=3 " +
		"    AND cashbook_year = " +txtCB_Year_to+
		"    ORDER BY accounting_unit_id " +
		"    )a " +
		"  )";
        	}
        	if(Command.equalsIgnoreCase("notFull")){
        		  txtCB_Year_from=Integer.parseInt(request.getParameter("txtCB_Year_from"));
                 txtCB_Month_from=Integer.parseInt(request.getParameter("txtCB_Month_from"));
                 txtCB_Year_to=Integer.parseInt(request.getParameter("txtCB_Year_to"));
                txtCB_Month_to=Integer.parseInt(request.getParameter("txtCB_Month_to"));
                String month = null,month1="";
        		if(txtCB_Month_from == 1){
        			month = "Jan";
        		}else if(txtCB_Month_from == 2){
        			month = "Feb";
        		}else if(txtCB_Month_from == 3){
        			month = "Mar";
        		}else if(txtCB_Month_from == 4){
        			month = "Apr";
        		}else if(txtCB_Month_from == 5){
        			month = "May";
        		}else if(txtCB_Month_from == 6){
        			month = "Jun";
        		}else if(txtCB_Month_from == 7){
        			month = "Jul";
        		}else if(txtCB_Month_from == 8){
        			month = "Aug";
        		}else if(txtCB_Month_from == 9){
        			month = "Sep";
        		}else if(txtCB_Month_from == 10){
        			month = "Oct";
        		}else if(txtCB_Month_from == 11){
        			month = "Nov";
        		}else if(txtCB_Month_from == 12){
        			month = "Dec";
        		}
        		if(txtCB_Month_to == 1){
        			month1 = "Jan";
        		}else if(txtCB_Month_to == 2){
        			month1 = "Feb";
        		}else if(txtCB_Month_to == 3){
        			month1 = "Mar";
        		}else if(txtCB_Month_to == 4){
        			month1 = "Apr";
        		}else if(txtCB_Month_to == 5){
        			month1 = "May";
        		}else if(txtCB_Month_to == 6){
        			month1 = "Jun";
        		}else if(txtCB_Month_to == 7){
        			month1 = "Jul";
        		}else if(txtCB_Month_to == 8){
        			month1 = "Aug";
        		}else if(txtCB_Month_to == 9){
        			month1 = "Sep";
        		}else if(txtCB_Month_to == 10){
        			month1 = "Oct";
        		}else if(txtCB_Month_to == 11){
        			month1 = "Nov";
        		}else if(txtCB_Month_to == 12){
        			month1 = "Dec";
        		}
        		
               if(txtCB_Year_from==txtCB_Year_to){
                if(txtCB_Month_from==txtCB_Month_to)
                {  if(month==month1)
                	minorHeading=minor_desx+" for "+month+" - "+txtCB_Year_from;
                }
                
               }else{
               	minorHeading=minor_desx+" for the period from "+month+" - "+txtCB_Year_from+" to "+month1+" - "+txtCB_Year_to;
               }
               
                fin_year=request.getParameter("fin_year");
           	 val=fin_year.split("-");
               pre_fin_year=(txtCB_Year_from-1)+"-"+(txtCB_Year_to-1);
        	pre_year_from=txtCB_Year_from-1;
        	pre_year_to=txtCB_Year_to-1;
        	 pre_year_from=txtCB_Year_from-1; 
             pre_year_to=txtCB_Year_to-1;
                System.out.println(txtCB_Year_from+">>"+pre_year_from+" pre_year_from "+">>"+txtCB_Month_from+" >> "+txtCB_Month_to+">> supp_Cmd >> "+supp_Cmd);
                if(supp_Cmd.equalsIgnoreCase("noSupp")){
        		qry_String="";
        		qry_String1="";
        	}
        	if(supp_Cmd.equalsIgnoreCase("Supp")){
        		qry_String="UNION ALL " +
        		"  (SELECT a.ACCOUNTING_UNIT_ID, " +
        		"    a.ACCOUNTING_FOR_OFFICE_ID, " +
        		"    a.CASHBOOK_YEAR, " +
        		"    a.CASHBOOK_MONTH, " +
        		"    a.ACCOUNT_HEAD_CODE AS headcode, " +
        		"    a.DEBIT_OPENING_BALANCE, " +
        		"    a.CREDIT_OPENING_BALANCE, " +
        		"    a.CURRENT_MONTH_DEBIT, " +
        		"    a.CURRENT_MONTH_CREDIT, " +
        		"    a.DEBIT_CLOSING_BALANCE, " +
        		"    a.CREDIT_CLOSING_BALANCE, " +
        		"    a.UPDATED_BY_USER_ID, " +
        		"    a.UPDATED_DATE, " +
        		"    a.SUPPLEMENT_NO AS supno, " +
        		" (select sub.GROUP_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as sub_Grp ,"+
                " (select sub.ORDER_NO from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as order_no "+
        		"  FROM " +
        		"    (SELECT * " +
        		"    FROM FAS_TRIAL_BALANCE_SUPPLEMENT " +
        		"    WHERE ACCOUNT_HEAD_CODE IN " +
        		"      (SELECT account_head_code " +
        		"      FROM com_mst_account_heads " +
        		"      WHERE MAJOR_HEAD_CODE= '" +major_code+"'"+
        		"      AND MINOR_HEAD_CODE  = " +minor_code+
        		"      ) " +
        		"    AND Cashbook_Month=3 " +
        		"    AND cashbook_year = " +pre_year_to+
        		"    ORDER BY accounting_unit_id " +
        		"    )a " +
        		"  )";
        		qry_String1="UNION ALL " +
        		"  (SELECT a.ACCOUNTING_UNIT_ID, " +
        		"    a.ACCOUNTING_FOR_OFFICE_ID, " +
        		"    a.CASHBOOK_YEAR, " +
        		"    a.CASHBOOK_MONTH, " +
        		"    a.ACCOUNT_HEAD_CODE AS headcode, " +
        		"    a.DEBIT_OPENING_BALANCE, " +
        		"    a.CREDIT_OPENING_BALANCE, " +
        		"    a.CURRENT_MONTH_DEBIT, " +
        		"    a.CURRENT_MONTH_CREDIT, " +
        		"    a.DEBIT_CLOSING_BALANCE, " +
        		"    a.CREDIT_CLOSING_BALANCE, " +
        		"    a.UPDATED_BY_USER_ID, " +
        		"    a.UPDATED_DATE, " +
        		"    a.SUPPLEMENT_NO AS supno ," +
        		" (select sub.GROUP_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as sub_Grp ,"+
                " (select sub.ORDER_NO from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as order_no "+
        		"  FROM " +
        		"    (SELECT * " +
        		"    FROM FAS_TRIAL_BALANCE_SUPPLEMENT " +
        		"    WHERE ACCOUNT_HEAD_CODE IN " +
        		"      (SELECT account_head_code " +
        		"      FROM com_mst_account_heads " +
        		"      WHERE MAJOR_HEAD_CODE= '" +major_code+"'"+
        		"      AND MINOR_HEAD_CODE  = " +minor_code+
        		"      ) " +
        		"    AND Cashbook_Month=3 " +
        		"    AND cashbook_year = " +txtCB_Year_to+
        		"    ORDER BY accounting_unit_id " +
        		"    )a " +
        		"  )";
        	}
        	}
        command=request.getParameter("cmbMinor_Head");
        rtype= request.getParameter("txtoption");
        if(command.equalsIgnoreCase("110"))
        {   System.out.println(command);
        	head=" SCHEDULE - 1.1 ";
      //  minor_code=110; 
        major_code="I";
        } 
        if(command.equalsIgnoreCase("Schedule2.1(AE)"))
        {
        	head=" SCHEDULE - 2.1 ";
        	
        //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule2.1(AE).jasper")); 
        }
         if(command.equalsIgnoreCase("Schedule2.2(FC)"))
        {
        	 head=" SCHEDULE - 2.2 ";
       // reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule2.2(FC).jasper")); 
        }
         if(command.equalsIgnoreCase("Schedule3.2(Investment)"))
         {
        	 head=" SCHEDULE - 3.2 ";
        // reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule3.2(INVEST).jasper")); 
         }
         if(command.equalsIgnoreCase("Schedule3.3(BB)"))
         {
        	 head=" SCHEDULE - 3.3 ";
         //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule3.3(BB).jasper")); 
         }
         if(command.equalsIgnoreCase("Schedule3.4(AdvRec)"))
         {
        	 head=" SCHEDULE - 3.4 ";
         //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule3.4(AdvRec).jasper")); 
         }
           if(command.equalsIgnoreCase("Schedule4.1(UL)"))
         {
        	   head=" SCHEDULE - 4.1 ";
        // reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule4.1(Unsec_Loan).jasper")); 
         }
         if(command.equalsIgnoreCase("Schedule4.2(sunCred)"))
         {
        	 head=" SCHEDULE - 4.2 ";
        // reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule4.4(SunCred).jasper")); 
         }
         if(command.equalsIgnoreCase("Schedule1.2.1"))
         {
         //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Shedule1.2.1.jasper")); 
         }
         if(command.equalsIgnoreCase("Schedule1.2.1(A)"))
         {
        	 head=" SCHEDULE - 1.2.1(A) ";
         //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Shedule1.2.1(A).jasper")); 
         }
         if(command.equalsIgnoreCase("Schedule1.2.1(B)"))
         {
        	 head=" SCHEDULE - 1.2.1(B) ";
         //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Shedule1.2.1(B).jasper")); 
         }
         System.out.println("********************************************************");
         System.out.println("qry String >>"+qry_String);
         System.out.println("********************************************************");
         System.out.println("qry String >>"+qry_String1);
         String qry="SELECT step1.*, " +
         "  step2.*, step2.GROUP_HEAD_DESC AS Grp_Desc," +
         " step2.headcode as hd_Code,step2.head_desc as hd_Desc,step2.sub_Grp       AS sub_Grp_head,"+
         "  (SELECT MINOR_HEAD_DESC " +
         "  FROM COM_MST_MINOR_HEADS m " +
         "  WHERE m.MINOR_HEAD_CODE=step2.minor_head_code " +
         "  )AS minor_head_desc " +
         "FROM " +
         "  (SELECT headcode, " +
         "    (SELECT s.account_head_desc " +
         "    FROM com_mst_account_heads s " +
         "    WHERE s.account_head_code=headcode " +
         "    )AS head_desc, " +
         "    CASE " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))<0 " +
         "      THEN (SUM(CURRENT_MONTH_CREDIT)-SUM(CURRENT_MONTH_DEBIT)) " +
         "        ||' CR' " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT)-SUM(CURRENT_MONTH_CREDIT))>0 " +
         "      THEN (SUM(CURRENT_MONTH_DEBIT)-SUM(CURRENT_MONTH_CREDIT)) " +
         "        ||' DR' " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT)-SUM(CURRENT_MONTH_CREDIT))=0 " +
         "      THEN (SUM(CURRENT_MONTH_DEBIT)-SUM(CURRENT_MONTH_CREDIT)) " +
         "        ||'' " +
         "    END AS PRE_TOT, " +
         "    CASE " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))<0 " +
         "      THEN (SUM(CURRENT_MONTH_CREDIT)-SUM(CURRENT_MONTH_DEBIT)) " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))>0 " +
         "      THEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT)) " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))=0 " +
         "      THEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT)) " +
         "    END AS PREVIOUS_YR ," +
         "(SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))PRE_ABS,"+
         " sub_Grp,(select GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER gp where gp.group_head_code=sub_Grp)as GROUP_HEAD_DESC,order_no"+
         "  FROM ( " +
         "    (SELECT a.ACCOUNTING_UNIT_ID, " +
         "      a.ACCOUNTING_FOR_OFFICE_ID, " +
         "      a.CASHBOOK_YEAR, " +
         "      a.CASHBOOK_MONTH, " +
         "      a.ACCOUNT_HEAD_CODE AS headcode, " +
         "      a.DEBIT_OPENING_BALANCE, " +
         "      a.CREDIT_OPENING_BALANCE, " +
         "      a.CURRENT_MONTH_DEBIT, " +
         "      a.CURRENT_MONTH_CREDIT, " +
         "      a.DEBIT_CLOSING_BALANCE, " +
         "      a.CREDIT_CLOSING_BALANCE, " +
         "      a.UPDATED_BY_USER_ID, " +
         "      a.UPDATED_DATE, " +
         "      0 AS supno ," +
         " (select sub.GROUP_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as sub_Grp ,"+
         " (select sub.ORDER_NO from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as order_no "+
         "    FROM " +
         "      (SELECT * " +
         "      FROM FAS_TRIAL_BALANCE_CMP " +
         "      WHERE ACCOUNT_HEAD_CODE IN " +
         "        (SELECT account_head_code " +
         "        FROM com_mst_account_heads " +
         "        WHERE MAJOR_HEAD_CODE= '" +major_code+"'"+
         "        AND MINOR_HEAD_CODE  = " +minor_code+
         "        ) " +
         "      AND To_Date((Cashbook_Month " +
         "        ||'-' " +
         "        || Cashbook_Year),'mm-yyyy') BETWEEN To_Date( " +txtCB_Month_from+
         "        ||'-' " +
         "        ||"+pre_year_from+",'mm-yyyy') " +
         "      AND to_date( " +txtCB_Month_to+
         "        ||'-' " +
         "        ||"+pre_year_to+",'mm-yyyy') " +
         "      ORDER BY accounting_unit_id " +
         "      )a " +
         "    ) "+qry_String+") " +
         "  GROUP BY headcode,sub_Grp,order_no "+
         "  )step1 " +
         " right JOIN " +
         "  (SELECT headcode, " +
         "    (SELECT s.account_head_desc " +
         "    FROM com_mst_account_heads s " +
         "    WHERE s.account_head_code=headcode " +
         "    )AS head_desc, " +
         "    (SELECT MINOR_HEAD_CODE " +
         "    FROM COM_MST_ACCOUNT_HEADS minor " +
         "    WHERE minor.account_head_code=headcode " +
         "    )AS minor_head_code, " +
         "    CASE " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))<0 " +
         "      THEN (SUM(CURRENT_MONTH_CREDIT)-SUM(CURRENT_MONTH_DEBIT)) " +
         "        ||' CR' " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT)-SUM(CURRENT_MONTH_CREDIT))>0 " +
         "      THEN (SUM(CURRENT_MONTH_DEBIT)-SUM(CURRENT_MONTH_CREDIT)) " +
         "        ||' DR' " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT)-SUM(CURRENT_MONTH_CREDIT))=0 " +
         "      THEN (SUM(CURRENT_MONTH_DEBIT)-SUM(CURRENT_MONTH_CREDIT)) " +
         "        ||'' " +
         "    END AS CURRENT_TOT, " +
         "    CASE " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))<0 " +
         "      THEN (SUM(CURRENT_MONTH_CREDIT)-SUM(CURRENT_MONTH_DEBIT)) " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))>0 " +
         "      THEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT)) " +
         "      WHEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))=0 " +
         "      THEN (SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT)) " +
         "    END AS NEXT_YR, " +
         "(SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))NEXT_ABS ," +
         "sub_Grp, (select GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER gp where gp.group_head_code=sub_Grp)as GROUP_HEAD_DESC,order_no"+
         "  FROM ( " +
         "    (SELECT a.ACCOUNTING_UNIT_ID, " +
         "      a.ACCOUNTING_FOR_OFFICE_ID, " +
         "      a.CASHBOOK_YEAR, " +
         "      a.CASHBOOK_MONTH, " +
         "      a.ACCOUNT_HEAD_CODE AS headcode, " +
         "      a.DEBIT_OPENING_BALANCE, " +
         "      a.CREDIT_OPENING_BALANCE, " +
         "      a.CURRENT_MONTH_DEBIT, " +
         "      a.CURRENT_MONTH_CREDIT, " +
         "      a.DEBIT_CLOSING_BALANCE, " +
         "      a.CREDIT_CLOSING_BALANCE, " +
         "      a.UPDATED_BY_USER_ID, " +
         "      a.UPDATED_DATE, " +
         "      0 AS supno, " +
         " (select sub.GROUP_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as sub_Grp ,"+
         " (select sub.ORDER_NO from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as order_no "+
         "    FROM " +
         "      (SELECT * " +
         "      FROM FAS_TRIAL_BALANCE_CMP " +
         "      WHERE ACCOUNT_HEAD_CODE IN " +
         "        (SELECT account_head_code " +
         "        FROM com_mst_account_heads " +
         "        WHERE MAJOR_HEAD_CODE= '" +major_code+"'"+
         "        AND MINOR_HEAD_CODE  = " +minor_code+
         "        ) " +
         "      AND To_Date((Cashbook_Month " +
         "        ||'-' " +
         "        || Cashbook_Year),'mm-yyyy') BETWEEN To_Date( " +txtCB_Month_from+
         "        ||'-' " +
         "        ||"+txtCB_Year_from+",'mm-yyyy') " +
         "      AND to_date( " +txtCB_Month_to+
         "        ||'-' " +
         "        ||"+txtCB_Year_to+",'mm-yyyy') " +
         "      ORDER BY accounting_unit_id " +
         "      )a " +
         "    ) "+qry_String1+
         "  ) " +
         "GROUP BY headcode,sub_Grp,order_no)step2 ON step1.headcode=step2.headcode";
         System.out.println(" !!! "+head_Cmd);
         if(head_Cmd.equalsIgnoreCase("No")){
         reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule_sub/Report_Schedule_Detail_trial.jasper"));
         }
     /*    else if(head_Cmd.equalsIgnoreCase("Yes")){
             reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule_Head.jrxml"));
             }*/
      /*   else if(head_Cmd.equalsIgnoreCase(""))*/
         else{
        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule_sub/Report_Schedule_Head.jasper")); 
         }
         System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
        map.put("year_from",txtCB_Year_from);
        map.put("year_to",txtCB_Year_to);
        map.put("month_to",txtCB_Month_to);
        map.put("month_from",txtCB_Month_from);
        map.put("pre_year_from", pre_year_from);
        map.put("pre_year_to", pre_year_to);
        map.put("head", head);
        map.put("qryString", qry_String);
        map.put("qryString1", qry_String1);
        map.put("heading_pre",pre_fin_year );
        map.put("heading_cur", fin_year);
        map.put("minor_code", minor_code);
        map.put("major_code", major_code);
        map.put("qry", qry);
        map.put("minorHeading", minorHeading);
       
        System.out.println(map);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
        if (rtype.equalsIgnoreCase("HTML"))   
        {
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
        else      if (rtype.equalsIgnoreCase("PDF"))   
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
        else      if (rtype.equalsIgnoreCase("EXCEL"))   
        {

       
        	response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\""+head+".xls\"");
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
        else      if (rtype.equalsIgnoreCase("TXT"))   
        {
        
                        response.setContentType("text/plain");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"Schedule4.4.txt\"");
                             
                        JRTextExporter exporter = new JRTextExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
                        exporter.exportReport(); 
                        
                    byte []bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

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

}