package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

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
public class Schedule_Month_Sub_Reports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static final String CONTENT_TYPE = 
            "text/html; charset=windows-1252";
        Connection connection = null;
    public Schedule_Month_Sub_Reports() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}
        
    
        
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        
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
        int pre_year_from=0,pre_Mth_from=0;
        int minor_code=0;
        String command=""; String rtype="";
        String  pre_fin_year="",head="";
        String qry_sub="",major_code="",qry_String="",qry_String1="";
    	String head_Cmd=request.getParameter("yes_hid");
        	txtCB_Year_from=Integer.parseInt(request.getParameter("txtCB_Year_from"));
        	txtCB_Month_from=Integer.parseInt(request.getParameter("txtCB_Month_from"));
        	
        	//changed on 25/05/2018 Left side value for previous month and current CB year value getting from the form....
        	 if(txtCB_Month_from==1)
        	{
        		pre_Mth_from=12;	
        		pre_year_from=txtCB_Year_from-1;
        	}
        	else
        	{
        		pre_Mth_from=txtCB_Month_from-1;
        		pre_year_from=txtCB_Year_from;
        		
        	}
        	
        	
        	 String monthInWords="",monthInWords_pre="";
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
             
             if(pre_Mth_from==1)
            	 monthInWords_pre="January";
             else if(pre_Mth_from==2)
            	 monthInWords_pre="February";
             else if(pre_Mth_from==3)
            	 monthInWords_pre="March";
             else if(pre_Mth_from==4)
            	 monthInWords_pre="April";
             else if(pre_Mth_from==5)
            	 monthInWords_pre="May";
             else if(pre_Mth_from==6)
            	 monthInWords_pre="June";
             else if(pre_Mth_from==7)
            	 monthInWords_pre="July";
             else if(pre_Mth_from==8)
            	 monthInWords_pre="August";
             else if(pre_Mth_from==9)
            	 monthInWords_pre="September";
             else if(pre_Mth_from==10)
            	 monthInWords_pre="October";
             else if(pre_Mth_from==11)
            	 monthInWords_pre="November";
             else if(pre_Mth_from==12)
            	 monthInWords_pre="December";
             major_code=request.getParameter("cmbBudgetGroupMajor");
        	 minor_code=Integer.parseInt(request.getParameter("cmbMinor_Head"));
             System.out.println("Minor_Head >>>> "+minor_code);
        	System.out.println("txtCB_Year_from >>> "+txtCB_Year_from+" >> txtCB_Month_from"+txtCB_Month_from+" >>> pre_year_from >> "+pre_year_from+" >>> ");
        	System.out.println(" >>> txtCB_Month_from "+txtCB_Month_from);
        	System.out.println(" >>> pre_Mth_from "+pre_Mth_from);
      if((pre_Mth_from==3))
      {
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
		"    FROM FAS_TRIAL_BALANCE_SJV_CMP " +
		"    WHERE ACCOUNT_HEAD_CODE IN " +
		"      (SELECT account_head_code " +
		"      FROM com_mst_account_heads " +
		"      WHERE MAJOR_HEAD_CODE= '" +major_code+"'"+
		"      AND MINOR_HEAD_CODE  = " +minor_code+
		"      ) " +
		"    AND Cashbook_Month=3 " +
		"    AND cashbook_year = " +pre_year_from+
		"    ORDER BY accounting_unit_id " +
		"    )a " +
		"  )";
      qry_String1="";
      }
      if((txtCB_Month_from==3))
      {
    	  qry_String="";
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
		"    a.SUPPLEMENT_NO AS supno, " +
		" (select sub.GROUP_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as sub_Grp ,"+
        " (select sub.ORDER_NO from ANNUAL_ACCOUNT_SUB_GROUP sub where sub.MAJOR_HEAD_CODE='"+major_code+"' and sub.MINOR_HEAD_CODE="+minor_code+" )as order_no "+
		"  FROM " +
		"    (SELECT * " +
		"    FROM FAS_TRIAL_BALANCE_SJV_CMP " +
		"    WHERE ACCOUNT_HEAD_CODE IN " +
		"      (SELECT account_head_code " +
		"      FROM com_mst_account_heads " +
		"      WHERE MAJOR_HEAD_CODE= '" +major_code+"'"+
		"      AND MINOR_HEAD_CODE  = " +minor_code+
		"      ) " +
		"    AND Cashbook_Month=3 " +
		"    AND cashbook_year = " +txtCB_Year_from+
		"    ORDER BY accounting_unit_id " +
		"    )a " +
		"  )";
        	
      }
      else{
    	  qry_String="";
    	  qry_String1="";
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
         "(SUM(CURRENT_MONTH_DEBIT) -SUM(CURRENT_MONTH_CREDIT))PRE_ABS, "+
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
     	"    AND Cashbook_Month= " +pre_Mth_from+
		"    AND cashbook_year = " +pre_year_from+
         "      ORDER BY accounting_unit_id " +
         "      )a " +
         "    ) "+qry_String+") " +
         "  GROUP BY headcode,sub_Grp,order_no " +
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
     	"    AND Cashbook_Month= " +txtCB_Month_from+
		"    AND cashbook_year = " +txtCB_Year_from+
         "      ORDER BY accounting_unit_id " +
         "      )a " +
         "    ) "+qry_String1+
         "  ) " +
         "GROUP BY headcode,sub_Grp,order_no)step2 ON step1.headcode=step2.headcode";
       System.out.println(" !!! "+head_Cmd);
         if(head_Cmd.equalsIgnoreCase("No")){
         reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule_sub/Month_Schedule_Detail_trial.jasper"));
         }
       /* else if(head_Cmd.equalsIgnoreCase("Yes")){
             reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/Report_Schedule_Head.jrxml"));
             }
       else if(head_Cmd.equalsIgnoreCase(""))*/
        else{
        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule_sub/Month_Schedule_Head.jasper")); 
         }
         System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
        map.put("year_from",txtCB_Year_from);
        map.put("month_from",txtCB_Month_from);
        map.put("pre_year_from", pre_year_from);
        map.put("head", head);
        map.put("qryString", qry_String);
        map.put("qryString1", qry_String1);
        map.put("pre_Mth_from",pre_Mth_from );
        map.put("minor_code", minor_code);
        map.put("major_code", major_code);
        map.put("month_cur", monthInWords);
        map.put("month_pre", monthInWords_pre);
        map.put("qry", qry);
       
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