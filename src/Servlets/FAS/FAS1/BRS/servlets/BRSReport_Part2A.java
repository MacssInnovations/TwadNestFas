package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.math.BigDecimal;
import java.text.DecimalFormat;

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
/**
 * Servlet implementation class BRSReport_Part2A
 */
public class BRSReport_Part2A extends HttpServlet {
	
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRSReport_Part2A() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    PrintWriter out = response.getWriter();
	     response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
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
	            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

	            Class.forName(strDriver.trim());
	            con = DriverManager.getConnection(ConnectionString, strdbusername
	                            .trim(), strdbpassword.trim());
	    } catch (Exception e) {
	            System.out.println("Exception in opening connection :" + e);
	            // sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	    } 
	    HttpSession session=request.getSession(false);
        try
        {
            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        } 
	    String update_user=(String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
	    PreparedStatement ps2=null,ps=null,ps1=null;
	    ResultSet rs2=null,rs=null,rs1=null;
	    String cmd="",xml=null;
	    int count=0;
	    int cmbAcc_UnitCode=0;
	    try
	    {
	    cmd=request.getParameter("command");
	    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    
	    }catch (Exception e) {
	            e.printStackTrace();
	    }
	    System.out.println("cmd---------"+cmd);
	    if(cmd.equalsIgnoreCase("LoadBankAccountNumber"))
	    {
	            String sql =
	            "       select *                                                                                                                \n"+                                            
	            "               from                                                                                                                                    \n"+    
	            "               (                                                                                                                                               \n"+    
	            "                       select                                                                                                                          \n"+
	            "                               bank_id,                                                                                                                \n"+
	            "                               BRANCH_ID,                                                                                                              \n"+
	            "                               bank_ac_no,                                                                                                     \n"+
	            "                               AC_OPERATIONAL_MODE_ID,                                         \n"+
	            "                               trim(AC_OPERATIONAL_MODE_ID)||'-'||trim(bank_ac_no) as acc_no                       \n"+  
	            "                       from                                                                                                                            \n"+
	            "                               fas_mst_bank_balance                                                                                                \n"+
	            "                       where                                                                                                                           \n"+            
	       //     "                               accounting_unit_id = ? and AC_OPERATIONAL_MODE_ID in ('OPR','FDW')         \n"+
	            "                               accounting_unit_id = ?   and status='Y'  		\n"+
	            "               )X                                                                                                                                              \n"+                    
	            "               left outer join                                                                                                                 \n"+
	            "               (                                                                                                                                               \n"+             
	            "                               select bank_id as y_bank_id ,trim(bank_name) as y_bank_name from fas_bank_list  \n"+    
	            "               )Y                                                                                                                                              \n"+
	            "    on                                                                                                                                                 \n"+
	            "      X.bank_id  = Y.y_bank_id                                                                                                 \n"+
	            "    left outer join                                                                                                                    \n"+
	            "    (                                                                                                                                                  \n"+
	            "      select  BANK_ID as z_bank_id, BRANCH_ID as z_BRANCH_ID ,trim(BRANCH_NAME) as z_BRANCH_NAME from fas_mst_bank_branches  \n"+                   
	            "    )Z                                                                                                                 \n"+
	            "        on                                                                                                                                             \n"+
	            "      X.bank_id  = Z.z_bank_id  and                                                                                    \n"+ 
	            "      X.BRANCH_ID = Z.z_branch_id                                                                                              \n"+
	            "                                                                                                                                                             ";
	            
	    //   System.out.println("sql:::"+sql);
	    
	        try
	        {
	                  ps2=con.prepareStatement(sql);
	                  ps2.setInt(1,cmbAcc_UnitCode);
	                  rs2=ps2.executeQuery();
	               
	                  
	                  xml="<response><command>LoadBankAccountNumber</command>";
	                  
	                  /** Count How many Records are available  */
	                  while (rs2.next()) 
	                  {
	                     xml=xml+ "<acc_no>"+ rs2.getString("bank_ac_no") +"</acc_no>";  
	                     xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
	                     xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
	                     xml=xml+ "<opr_mode>"+ rs2.getString("AC_OPERATIONAL_MODE_ID") +"</opr_mode>";                 
	                     xml=xml+ "<acc_desc>"+ rs2.getString("acc_no") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
	                     xml=xml+ "<bank_name>"+ rs2.getString("y_bank_name") +"</bank_name>";                                           
	                     xml=xml+ "<branch_name>"+ rs2.getString("z_BRANCH_NAME") +"</branch_name>";
	                     count++;
	                  }
	                  
	                  if(count==0) {
	                      xml=xml+"<flag>NoData</flag>";
	                  }
	                  else{                
	                      xml=xml+"<flag>success</flag>";
	                  }              
	       }
	           catch(Exception e) 
	           {
	                  System.out.println("Exception in assigning..."+e);
	                  xml=xml+"<flag>failure</flag>";
	           }
	           xml = xml + "</response>";
	                    System.out.println(xml);
	                    out.println(xml);
	    }
	    
		else if(cmd.equalsIgnoreCase("chkRec"))
		{
			  xml="<response><command>chkRec</command>";
			int isRecord=0;
			int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
			int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			String ss="SELECT COUNT(*) as count_one "+
					" FROM FAS_BRS_PART_2A "+
					" WHERE Accounting_Unit_Id    = "+unitcode+
					" AND Accounting_For_Office_Id="+offCode+
					" AND Pass_Sheet_Year         ="+passYear+
					" AND Pass_Sheet_Month        ="+passMonth+
					" AND ACCOUNT_NO              ="+accNo;
			System.out.println("ss:"+ss);
			try{
				
				  xml=xml+"<flag>success</flag>";
			PreparedStatement ps_one=con.prepareStatement(ss);
			ResultSet rs_one=ps_one.executeQuery();
			while(rs_one.next())
			{
				isRecord=rs_one.getInt("count_one");
			}
			if(isRecord>0)
			{
				  xml=xml+"<proceed>stop</proceed>";
			}
			else
			{
				  xml=xml+"<proceed>start</proceed>";
			}
				
			}
			 catch(Exception e) 
		       {
		              System.out.println("Exception in onload..."+e);
		              xml=xml+"<flag>failure</flag>";
		       }
			
			  xml = xml + "</response>";
				System.out.println(xml);
				out.println(xml);
		}
		else if(cmd.equalsIgnoreCase("checkFreezeStatus"))
		{
			  xml="<response><command>checkFreezeStatus</command>";
			//int isRecord=0;
			  System.out.println("checkFreezeStatus");
			
			int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
			int cashYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int cashMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			
			String ss="select 'X' from  FAS_BRS_MONTHLY_CLOSURE where "+
			" ACCOUNTING_UNIT_ID="+unitcode+
			" and ACCOUNTING_FOR_OFFICE_ID="+offCode+
			" and CASHBOOK_YEAR="+cashYear+
			" and CASHBOOK_MONTH="+cashMonth+
			" and STATUS='Y' " +
			" and ACCOUNT_NO="+accNo;
			
			//System.out.println("ss:"+ss);
			try{
				
				  xml=xml+"<flag>success</flag>";
			PreparedStatement ps_one=con.prepareStatement(ss);
			ResultSet rs_one=ps_one.executeQuery();
			if(rs_one.next())
			{
				 xml=xml+"<status>freeze</status>";
			}
			else
			{
				  xml=xml+"<status>notfreeze</status>";
			}
				
			}
			 catch(Exception e) 
		       {
		              System.out.println("Exception in onload..."+e);
		              xml=xml+"<flag>failure</flag>";
		       }
			
			  xml = xml + "</response>";
				System.out.println(xml);
				out.println(xml);
		}
	
	  
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String xml=null;
		
		Connection con = null;
		String bankName="",branchName="";
		PreparedStatement ppee4=null;
		ResultSet re4=null;
        Double four_bAmount=0.00,four_cAmount=0.00,excess_db=0.00,bank_credit=0.00,four_e4=0.00,d=0.00,five_a1=0.00,five_a1_ct_month=0.00;
	    BigDecimal ii  = null,i2=null,i3=null,bank_credit_bdecimal=null,i4_four=null,i2_five=null,i2_five_ct_month=null;
	    BigDecimal total_i=null;
	    String UnitName=null;
        String totalyear="",mode_id="";
          int count_test=0,exce_test=0,count_tesst=0,i4_excfour=0;
              int bank_id=0,branch_id=0,nw_fv=0,nw_fv_current=0;
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
		    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		
		HttpSession session=request.getSession(false);
        try
        {
            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        } 
		 String update_user=(String)session.getAttribute("UserId");
         long l=System.currentTimeMillis();
         Timestamp ts=new Timestamp(l);	
         
		
		String opr_node="";
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request
				.getParameter("cboCashBook_Year"));
		int cboCashBook_Month = Integer.parseInt(request
				.getParameter("cboCashBook_Month"));
		long cmbBankAccNo =Long.parseLong(request
				.getParameter("cmbBankAccNo"));
	
		try{
			PreparedStatement ps_node=con.prepareStatement(" SELECT BB.AC_OPERATIONAL_MODE_ID,B.BANK_NAME,BR.BRANCH_NAME FROM FAS_MST_BANK_BALANCE BB,FAS_MST_BANKS B,FAS_MST_BANK_BRANCHES BR WHERE BB.BANK_ID=B.BANK_ID AND B.BANK_ID=BR.BANK_ID AND BB.BRANCH_ID=BR.BRANCH_ID and bank_ac_no= "+cmbBankAccNo)	;
			
			ResultSet rs_node=ps_node.executeQuery();
			while(rs_node.next()){
				opr_node=rs_node.getString("AC_OPERATIONAL_MODE_ID");
				bankName=rs_node.getString("BANK_NAME");
				branchName=rs_node.getString("BRANCH_NAME");
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		String command=request.getParameter("command"); 

		
		
		String cmd=request.getParameter("command");
		String hid=request.getParameter("old");

		String month = null;
		if(cboCashBook_Month == 1){
			month = "January";
		}else if(cboCashBook_Month == 2){
			month = "Febrary";
		}else if(cboCashBook_Month == 3){
			month = "March";
		}else if(cboCashBook_Month == 4){
			month = "April";
		}else if(cboCashBook_Month == 5){
			month = "May";
		}else if(cboCashBook_Month == 6){
			month = "June";
		}else if(cboCashBook_Month == 7){
			month = "July";
		}else if(cboCashBook_Month == 8){
			month = "August";
		}else if(cboCashBook_Month == 9){
			month = "September";
		}else if(cboCashBook_Month == 10){
			month = "October";
		}else if(cboCashBook_Month == 11){
			month = "November";
		}else if(cboCashBook_Month == 12){
			month = "December";
		}
                
	    try {
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	    ps.setInt(1,cboAcc_UnitCode);
	    rs=ps.executeQuery();
	    if(rs.next())
	         UnitName=rs.getString("ACCOUNTING_UNIT_NAME");
	    
	    
	    }
	    catch (SQLException e) {
	        System.out.println("SQL Exception -->"+e);
	    }
	    
	    try {
		    
		    
		 PreparedStatement  ps_l=con.prepareStatement("SELECT to_char(last_day(to_date(date1, 'dd-mm-yy')),'dd-mm-yyyy')ls_date "+
								"  FROM "+
		    		"   (SELECT DISTINCT ('01' "+
		    		" 		      ||'-' "+
		    		" 		      ||CASHBOOK_MONTH "+
		    		" 		      ||'-' "+
		    		" 		      ||CASHBOOK_YEAR)date1 "+
		    		" 		    FROM FAS_BRS_TRANSACTION "+
		    		" 		    WHERE CASHBOOK_YEAR   = "+cboCashBook_Year+
		    		" 		    AND CASHBOOK_MONTH    = "+cboCashBook_Month+
		    		" 		    AND account_no        = "+cmbBankAccNo+
		    		" 		    AND accounting_unit_id= "+cboAcc_UnitCode+" 		    )");
		    
		  ResultSet  rs_l=ps_l.executeQuery();
		    if(rs_l.next())
		    {
		    String  last_date_one =rs_l.getString("ls_date");
		    System.out.println("last_date_one::"+last_date_one);
		    String[] splto=last_date_one.split("-");
		  String smonth="";
		    if(splto[1].equals("01"))
		    {
		    	smonth="jan";
		    }
		    else if(splto[1].equals("02"))
		    {
		    	smonth="feb";
		    }else if(splto[1].equals("03"))
		    {
		    	smonth="mar";
		    }else if(splto[1].equals("04"))
		    {
		    	smonth="apr";
		    }else if(splto[1].equals("05"))
		    {
		    	smonth="may";
		    }else if(splto[1].equals("06"))
		    {
		    	smonth="jun";
		    }else if(splto[1].equals("07"))
		    {
		    	smonth="jul";
		    }else if(splto[1].equals("08"))
		    {
		    	smonth="aug";
		    }else if(splto[1].equals("09"))
		    {
		    	smonth="sep";
		    }else if(splto[1].equals("10"))
		    {
		    	smonth="oct";
		    }else if(splto[1].equals("11"))
		    {
		    	smonth="nov";
		    }else if(splto[1].equals("12"))
		    {
		    	smonth="dec";
		    }
		    totalyear=splto[0]+"-"+smonth+"-"+splto[2];
		    System.out.println("totalyear:::"+totalyear);
		   
		    }
		    
		    }
		    catch (SQLException e) {
		        System.out.println("SQL Exception -->"+e);
		    }
                
	    try {
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	 	    
        String sql="select acc_u_id5,acc_off_id5,Acc_No5,sum(four_b)as four_b from\n" + 
        " (SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
        "  Account_No               AS Acc_No5,\n" + 
        "  SUM(DR_AMOUNT)           AS four_b\n" + 
        " FROM FAS_BRS_TRANSACTION\n" + 
        " WHERE accounting_unit_id               = " + cboAcc_UnitCode+
        " AND accounting_for_office_id           = " + cboOffice_code+
        " AND ((extract(YEAR FROM PASSBOOK_DATE) <" + cboCashBook_Year+
        " AND extract(MONTH FROM PASSBOOK_DATE) <=12)\n" + 
        " OR (extract(YEAR FROM PASSBOOK_DATE)   =" + cboCashBook_Year+
        " AND extract(MONTH FROM PASSBOOK_DATE) <="+cboCashBook_Month+"))\n" + 
        " AND ACCOUNT_NO                         = " + cmbBankAccNo+
        " AND Twad_Or_Non_Twad                   ='NT'\n" + 
        " AND TRANSACTION_TYPE                  IN(1,3,23,12,26,6,25,8,7)\n" + 
        " AND (CLEARED_BASED_ON_FOLLOWUP IS NULL OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
        " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "  Account_No\n" + 
        "  union all\n" + 
        "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
        "  Account_No               AS Acc_No5,\n" + 
        "  SUM(DR_AMOUNT)           AS four_b\n" + 
        " FROM FAS_BRS_TRANSACTION\n" + 
        " WHERE accounting_unit_id               = " + cboAcc_UnitCode+
        " AND accounting_for_office_id           = " + cboOffice_code+
        " AND ((extract(YEAR FROM PASSBOOK_DATE) <" + cboCashBook_Year+
        " AND extract(MONTH FROM PASSBOOK_DATE) <=12)\n" + 
        " OR (extract(YEAR FROM PASSBOOK_DATE)   =" + cboCashBook_Year+
        " AND extract(MONTH FROM PASSBOOK_DATE) <="+cboCashBook_Month+"))\n" + 
        " AND ACCOUNT_NO                         = " + cmbBankAccNo+
        " AND Twad_Or_Non_Twad                   ='NT'\n" + 
        " AND TRANSACTION_TYPE                  IN(1,3,23,12,26,6,25,8,7)\n" + 
        " AND (CLEARED_BASED_ON_FOLLOWUP='Y' AND clearence_date            >('"+totalyear+"'))\n" + 
        " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "  Account_No" +
        ")\n" + 
        "  group by acc_u_id5,acc_off_id5,Acc_No5";
	    
	    System.out.println("sql:"+sql);
	    ps=con.prepareStatement(sql);
	    rs=ps.executeQuery();
	    if(rs.next()){
	    
	         four_bAmount=rs.getDouble("four_b");
	     //	System.out.println("four_bAmount:::"+four_bAmount);
	        i2 = new BigDecimal(four_bAmount);
                count_test++;
            }
	   if(count_test==0) {
               i2=new BigDecimal(0.00);
           }
	   
	    }
	    catch (SQLException e) {
	        System.out.println("SQL Exception -->"+e);
	    }
        
	    try {
		    PreparedStatement ps=null;
		    ResultSet rs=null;
		    
		 	    
	        String sql="SELECT acc_u_id5, "+
				  " acc_off_id5, "+
	        	" Acc_No5, "+
	        	" SUM(five_a1)AS five_a1 "+
	        	" FROM "+
	        	" (SELECT ACCOUNTING_UNIT_ID AS acc_u_id5, "+
	        	"   ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5, "+
	        	"   Account_No               AS Acc_No5, "+
	        	"   SUM(DR_AMOUNT)           AS five_a1 "+
	        	" FROM FAS_BRS_TRANSACTION "+
	        	" WHERE accounting_unit_id               =  "+cboAcc_UnitCode+
	        	" AND accounting_for_office_id           =  "+cboOffice_code+
	        	" AND ((extract(YEAR FROM PASSBOOK_DATE) < "+cboCashBook_Year+
	        	" AND extract(MONTH FROM PASSBOOK_DATE) <=12) "+
	        	" OR (extract(YEAR FROM PASSBOOK_DATE)   = "+cboCashBook_Year+
	        	" AND extract(MONTH FROM PASSBOOK_DATE) <="+cboCashBook_Month+")) "+
	        	" AND ACCOUNT_NO                         =  "+cmbBankAccNo+
	        	" AND Twad_Or_Non_Twad                   ='NT' "+
	        	" AND TRANSACTION_TYPE                  =35 "+
	        	" AND (CLEARED_BASED_ON_FOLLOWUP        IS NULL "+
	        	" OR CLEARED_BASED_ON_FOLLOWUP           ='N') "+
	        	" GROUP BY ACCOUNTING_UNIT_ID, "+
	        	"   ACCOUNTING_FOR_OFFICE_ID, "+
	        	"   Account_No "+
	        	" UNION ALL "+
	        	" SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5, "+
	        	"    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5, "+
	        	"     Account_No               AS Acc_No5, "+
	        	"     SUM(DR_AMOUNT)           AS five_a1 "+
	        	"   FROM FAS_BRS_TRANSACTION "+
	        	"   WHERE accounting_unit_id               =  "+cboAcc_UnitCode+
	        	"   AND accounting_for_office_id           =  "+cboOffice_code+
	        	"   AND ((extract(YEAR FROM PASSBOOK_DATE) < "+cboCashBook_Year+
	        	"  AND extract(MONTH FROM PASSBOOK_DATE) <=12) "+
	        	"   OR (extract(YEAR FROM PASSBOOK_DATE)   = "+cboCashBook_Year+
	        	"   AND extract(MONTH FROM PASSBOOK_DATE) <="+cboCashBook_Month+")) "+
	        	"   AND ACCOUNT_NO                         =  "+cmbBankAccNo+
	        	"  AND Twad_Or_Non_Twad                   ='NT' "+
	        	"  AND TRANSACTION_TYPE                  =35 "+
	        	"   AND (CLEARED_BASED_ON_FOLLOWUP         ='Y' "+
	        	"  AND clearence_date                     >('31-aug-2011')) "+
	        	"  GROUP BY ACCOUNTING_UNIT_ID, "+
	        	"    ACCOUNTING_FOR_OFFICE_ID, "+
	        	"    Account_No "+
	        	"    UNION ALL\r\n" + 
	        	"  SELECT T.ACCOUNTING_UNIT_ID  AS ACC_U_ID5,\r\n" + 
	        	"    t.ACCOUNTING_FOR_OFFICE_ID AS ACC_OFF_ID5,\r\n" + 
	        	"    T.SUB_LEDGER_CODE          AS ACC_NO5,\r\n" + 
	        	"    SUM(T.AMOUNT)              AS five_a1\r\n" + 
	        	"  FROM FAS_journal_master m,\r\n" + 
	        	"    fas_journal_transaction t\r\n" + 
	        	"  WHERE m.accounting_unit_id    = t.accounting_unit_id\r\n" + 
	        	"  AND m.accounting_for_office_id= t.accounting_for_office_id\r\n" + 
	        	"  AND m.cashbook_month          = t.cashbook_month\r\n" + 
	        	"  AND m.cashbook_year           = t.cashbook_year\r\n" + 
	        	"  AND m.voucher_no              = t.voucher_no\r\n" + 
	        	"  AND m.journal_status          ='L'\r\n" + 
	        	"  AND t.cr_dr_indicator         ='DR'\r\n" + 
	        	"  AND m.accounting_unit_id      =" + cboAcc_UnitCode +
	        	"  AND m.accounting_for_office_id=" + cboOffice_code + 
//	        	"  AND m.cashbook_month          =" + cboCashBook_Month +
//	        	"  AND m.cashbook_year           =" + cboCashBook_Year +
	        	
	        	" AND ((m.cashbook_year         <" + cboCashBook_Year +
	        	"  AND m.cashbook_month         <=12)\r\n" + 
	        	"  OR (m.cashbook_year           =" + cboCashBook_Year+
	        	"  AND m.cashbook_month         <="+cboCashBook_Month+"))" +
	        	"  AND T.SUB_LEDGER_CODE         =" + cmbBankAccNo +
	        	"  AND T.SUB_LEDGER_TYPE_CODE    =6\r\n" + 
	        	"  AND T.CHEQUE_DD_NO           IN\r\n" + 
	        	"    (SELECT CAN.CHEQUE_DD_NO\r\n" + 
	        	"    FROM FAS_CHEQUE_CANCEL can\r\n" + 
	        	"    WHERE CAN.CHEQUE_DD_NO    =T.CHEQUE_DD_NO\r\n" + 
	        	"    AND CAN.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID\r\n" + 
	        	"    AND CAN.CASHBOOK_YEAR     =T.CASHBOOK_YEAR\r\n" + 
	        	"    AND CAN.CASHBOOK_MONTH    =T.CASHBOOK_MONTH\r\n" + 
	        	"    )" +
	        	"  GROUP BY t.ACCOUNTING_UNIT_ID,\r\n" + 
	        	"    t.ACCOUNTING_FOR_OFFICE_ID,\r\n" + 
	        	"    t.CASHBOOK_YEAR,\r\n" + 
	        	"    t.CASHBOOK_MONTH,\r\n" + 
	        	"    T.SUB_LEDGER_CODE,\r\n" + 
	        	"    T.CHEQUE_DD_NO\n" +
	        	"   ) "+
	        	" GROUP BY acc_u_id5, "+
	        	" acc_off_id5, "+
	        	"   Acc_No5";
		    
		    System.out.println("sql:"+sql);
		    ps=con.prepareStatement(sql);
		    rs=ps.executeQuery();
		    if(rs.next()){
		    
		    	five_a1=rs.getDouble("five_a1");
		     System.out.println("five_a1:::"+five_a1);
		    	i2_five = new BigDecimal(five_a1);
	                nw_fv++;
	            }
		   if(nw_fv==0) {
			   i2_five=new BigDecimal(0.00);
	           }
		   
		    }
		    catch (SQLException e) {
		        System.out.println("SQL Exception i2_five-->"+e);
		    }
	        
	    
          System.out.println("i2:::::::"+i2_five);
           
           try{
        	   int bank_credit_test=0;
        	   String bankCredit="SELECT acc_u_id5, "+
					 " acc_off_id5,Acc_No5, "+
        		   "  SUM(bank_credit)AS bank_credit "+
        		   " FROM "+
        		   "  ( "+
        		   "  (SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5, "+
        		   "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5, "+
        		   "    Account_No               AS Acc_No5, "+
        		   "    SUM(CR_AMOUNT)           AS bank_credit "+
        		   "  FROM FAS_BRS_TRANSACTION "+
        		   "  WHERE accounting_unit_id               =  "+cboAcc_UnitCode+
        		   "  AND accounting_for_office_id           =  "+cboOffice_code+
        		   "  AND ((extract(YEAR FROM PASSBOOK_DATE) < "+cboCashBook_Year+
        		   "  AND extract(MONTH FROM PASSBOOK_DATE) <=12) "+
        		   "  OR (extract(YEAR FROM PASSBOOK_DATE)   = "+cboCashBook_Year+
        		   "  AND extract(MONTH FROM PASSBOOK_DATE) <="+cboCashBook_Month+")) "+
        		   "  AND ACCOUNT_NO                         =  "+cmbBankAccNo+
        		   "  AND Twad_Or_Non_Twad                   ='NT' "+
        		  //type 26 removed sathya on 14/10/2016 tresuary issue
        		   "  AND TRANSACTION_TYPE                  IN(1,3,23,12) "+
        		   "  AND CLEARED_BASED_ON_FOLLOWUP         ='Y'  "+
        		   "  and extract (year from clearence_date)= "+cboCashBook_Year+
        		   "  and extract (month from clearence_date)= "+cboCashBook_Month+
        		   "  GROUP BY ACCOUNTING_UNIT_ID, "+
        		   "    ACCOUNTING_FOR_OFFICE_ID, "+
        		   "    Account_No) "+
        		   
        		  //Lakshmi 15April2014
        		  " union all "+
        		  " (SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,  "+
        		  "     ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,  "+
        		  "        Account_No               AS Acc_No5,  "+
        		  "        SUM(DR_AMOUNT)           AS bank_credit  "+
        		  "      FROM FAS_BRS_TRANSACTION  "+
        		  "  WHERE accounting_unit_id               =  "+cboAcc_UnitCode+
       		   "  AND accounting_for_office_id           =  "+cboOffice_code+
       		   "  AND ((extract(YEAR FROM PASSBOOK_DATE) < "+cboCashBook_Year+
       		   "  AND extract(MONTH FROM PASSBOOK_DATE) <=12) "+
       		   "  OR (extract(YEAR FROM PASSBOOK_DATE)   = "+cboCashBook_Year+
       		   "  AND extract(MONTH FROM PASSBOOK_DATE) <="+cboCashBook_Month+")) "+
       		   "  AND ACCOUNT_NO                         =  "+cmbBankAccNo+
       		   "  AND Twad_Or_Non_Twad                   ='T' "+
        		  "      AND TRANSACTION_TYPE                  =12 "+
        		  "      AND CLEARED_BASED_ON_FOLLOWUP         ='Y'   "+
        		  "   and DOC_TYPE='J' "+
        		  "  and extract (year from clearence_date)= "+cboCashBook_Year+
       		   "  and extract (month from clearence_date)= "+cboCashBook_Month+
        		  "      GROUP BY ACCOUNTING_UNIT_ID,  "+
        		  "        ACCOUNTING_FOR_OFFICE_ID,  "+
        		  "        Account_No ) "+

        		   
        		   "  ) "+
        		   " GROUP BY acc_u_id5, "+
        		   "  acc_off_id5, "+
        		   "  Acc_No5";
        	//   System.out.println("bankCredit:"+bankCredit);
       	    PreparedStatement pstat=con.prepareStatement(bankCredit);
       	    ResultSet set1=pstat.executeQuery();
       	    if(set1.next()){
       	    	bank_credit=set1.getDouble("bank_credit");
       	       	 bank_credit_bdecimal = new BigDecimal(bank_credit);
		       	bank_credit_test++;
                   }
	       	 if(bank_credit_test==0) {
	       		bank_credit_bdecimal=new BigDecimal(0.00);
	         }
           }
           catch(Exception eee)
           {
        	System.out.println("exception in bankcredit:::"+eee);   
           }
           System.out.println(bank_credit_bdecimal);
           
	    try {
	    PreparedStatement pss=null;
	    ResultSet rss=null;
	    
	    
        String ss="select acc_u_id5,acc_off_id5,csh_bk_yr5,csh_bk_mnth5,Acc_No5,sum(four_c)as four_c from\n" + 
        " (SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
        "  CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
        "  CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
        "  Account_No               AS Acc_No5,\n" + 
        "  SUM(DR_AMOUNT)           AS four_c\n" + 
        " FROM FAS_BRS_TRANSACTION\n" + 
        " WHERE accounting_unit_id              = " +cboAcc_UnitCode+ 
        " AND accounting_for_office_id          = " +cboOffice_code+ 
        " AND extract(YEAR FROM PASSBOOK_DATE)  = " +cboCashBook_Year+ 
        " AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
        " AND extract(MONTH FROM PASSBOOK_DATE) = " +cboCashBook_Month+ 
        " AND Twad_Or_Non_Twad                  ='NT'\n" + 
        " AND TRANSACTION_TYPE                 IN(1,3,23,12)\n" + 
        " AND (CLEARED_BASED_ON_FOLLOWUP IS NULL OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
        " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "  CASHBOOK_YEAR,\n" + 
        "  CASHBOOK_MONTH,\n" + 
        "  Account_No\n" + 
        "  union all\n" + 
        "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
        "  CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
        "  CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
        "  Account_No               AS Acc_No5,\n" + 
        "  SUM(DR_AMOUNT)           AS four_c\n" + 
        " FROM FAS_BRS_TRANSACTION\n" + 
        " WHERE accounting_unit_id              = " +cboAcc_UnitCode+ 
        " AND accounting_for_office_id          = " +cboOffice_code+ 
        " AND extract(YEAR FROM PASSBOOK_DATE)  = " +cboCashBook_Year+ 
        " AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
        " AND extract(MONTH FROM PASSBOOK_DATE) = " +cboCashBook_Month+ 
        " AND Twad_Or_Non_Twad                  ='NT'\n" + 
        " AND TRANSACTION_TYPE                 IN(1,3,23,12)\n" + 
        " AND (CLEARED_BASED_ON_FOLLOWUP='Y' AND clearence_date            >('"+totalyear+"'))\n" + 
        " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "  CASHBOOK_YEAR,\n" + 
        "  CASHBOOK_MONTH,\n" + 
        "  Account_No)\n" + 
        "  group by acc_u_id5,acc_off_id5,csh_bk_yr5,csh_bk_mnth5,Acc_No5";
	    System.out.println("ss:::"+ss);
	    System.out.println("----------------------------------------------------------------");
	    pss=con.prepareStatement(ss);
	   
	    rss=pss.executeQuery();
	    if(rss.next())
            {
                  four_cAmount=rss.getDouble("four_c");
                 // System.out.println("four_cAmount::::"+four_cAmount);
	        ii = new BigDecimal(four_cAmount);
	      //  System.out.println("four_cAmount:afetr:::"+ii);
                count_tesst++; 
            }
	        if(count_tesst==0) {
	        //	 System.out.println("ii no::::"+ii);
	            ii=new BigDecimal(0.00);
	            
	        }
	    }            
	    catch (SQLException e) {
	        System.out.println("SQL Exception -->"+e);
	    }
	    
	 //   System.out.println("ii"+ii);
	    total_i=ii.add(i2);
	   // System.out.println("total_i:"+total_i);
	    
	    
	    try {
		    PreparedStatement ps=null;
		    ResultSet rs=null;
		    
		 	    
	        String sql="SELECT acc_u_id5, "+
				  " acc_off_id5, "+
	        	" Acc_No5, "+
	        	" SUM(five_a1_ct_month)AS five_a1_ct_month "+
	        	" FROM "+
	        	" (SELECT ACCOUNTING_UNIT_ID AS acc_u_id5, "+
	        	"   ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5, "+
	        	"   Account_No               AS Acc_No5, "+
	        	"   SUM(DR_AMOUNT)           AS five_a1_ct_month "+
	        	" FROM FAS_BRS_TRANSACTION "+
	        	" WHERE accounting_unit_id               =  "+cboAcc_UnitCode+
	        	" AND accounting_for_office_id           =  "+cboOffice_code+
	        	" AND extract(YEAR FROM PASSBOOK_DATE)  = " +cboCashBook_Year+ 
	            " AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
	            " AND extract(MONTH FROM PASSBOOK_DATE) = " +cboCashBook_Month+ 
	        	" AND Twad_Or_Non_Twad                   ='NT' "+
	        	" AND TRANSACTION_TYPE                  =35 "+
	        	" AND (CLEARED_BASED_ON_FOLLOWUP        IS NULL "+
	        	" OR CLEARED_BASED_ON_FOLLOWUP           ='N') "+
	        	" GROUP BY ACCOUNTING_UNIT_ID, "+
	        	"   ACCOUNTING_FOR_OFFICE_ID, "+
	        	"   Account_No "+
	        	" UNION ALL "+
	        	" SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5, "+
	        	"    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5, "+
	        	"     Account_No               AS Acc_No5, "+
	        	"     SUM(DR_AMOUNT)           AS five_a1_ct_month "+
	        	"   FROM FAS_BRS_TRANSACTION "+
	        	"   WHERE accounting_unit_id               =  "+cboAcc_UnitCode+
	        	"   AND accounting_for_office_id           =  "+cboOffice_code+
	        	  " AND extract(YEAR FROM PASSBOOK_DATE)  = " +cboCashBook_Year+ 
	              " AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
	              " AND extract(MONTH FROM PASSBOOK_DATE) = " +cboCashBook_Month+ 
	        	"  AND Twad_Or_Non_Twad                   ='NT' "+
	        	"  AND TRANSACTION_TYPE                  =35 "+
	        	"   AND (CLEARED_BASED_ON_FOLLOWUP         ='Y' "+
	        	"  AND clearence_date                     >('31-aug-2011')) "+
	        	"  GROUP BY ACCOUNTING_UNIT_ID, "+
	        	"    ACCOUNTING_FOR_OFFICE_ID, "+
	        	"    Account_No "+
	        	"   ) "+
	        	" GROUP BY acc_u_id5, "+
	        	" acc_off_id5, "+
	        	"   Acc_No5";
		    
		    System.out.println("sql:"+sql);
		    ps=con.prepareStatement(sql);
		    rs=ps.executeQuery();
		    if(rs.next()){
		    
		    	five_a1_ct_month=rs.getDouble("five_a1_ct_month");
		   
		    	i2_five_ct_month = new BigDecimal(five_a1_ct_month);
	                nw_fv_current++;
	            }
		   if(nw_fv_current==0) {
			   i2_five_ct_month=new BigDecimal(0.00);
	           }
		   
		    }
		    catch (SQLException e) {
		        System.out.println("SQL Exception i2_five_ct_month-->"+e);
		    }
	    
	    try {
		    PreparedStatement pps=null;
		    ResultSet ress=null;
		    
            String ne="select acc_u_id5,acc_off_id5,Acc_No5,sum(four_bs) as four_bs from\n" + 
            "(SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
            "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
            "  Account_No               AS Acc_No5,\n" + 
            "  SUM(DR_AMOUNT)           AS four_bs\n" + 
            "FROM FAS_BRS_TRANSACTION\n" + 
            "WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "AND accounting_for_office_id= " +cboOffice_code+ 
            "AND cashbook_year           = " +cboCashBook_Year+ 
            "AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            "AND cashbook_month          = " +cboCashBook_Month+ 
            "AND Twad_Or_Non_Twad        ='NT'\n" + 
            "AND (CLEARED_BASED_ON_FOLLOWUP IS NULL OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
            "AND TRANSACTION_TYPE       IN(6,25,7,8)\n" + 
            "GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "  Account_No\n" + 
            "  union all\n" + 
            "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
            "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
            "  Account_No               AS Acc_No5,\n" + 
            "  SUM(DR_AMOUNT)           AS four_bs\n" + 
            "FROM FAS_BRS_TRANSACTION\n" + 
            "WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "AND accounting_for_office_id= " +cboOffice_code+ 
            "AND cashbook_year           = " +cboCashBook_Year+ 
            "AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            "AND cashbook_month          = " +cboCashBook_Month+ 
            "AND Twad_Or_Non_Twad        ='NT'\n" + 
            "AND (CLEARED_BASED_ON_FOLLOWUP='Y' AND clearence_date            >('"+totalyear+"'))\n" + 
            "AND TRANSACTION_TYPE       IN(6,25,7,8)\n" + 
            "GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "  Account_No)\n" + 
            "  group by acc_u_id5,acc_off_id5,Acc_No5";
		    
		   // System.out.println("ne:"+ne);
		    pps=con.prepareStatement(ne);
		    ress=pps.executeQuery();
		    if(ress.next()){
		    	
		         excess_db=ress.getDouble("four_bs");
		    // 	System.out.println("excess_db:::"+excess_db);
		        i3 = new BigDecimal(excess_db);
	                exce_test++;
	            }
		   if(exce_test==0) {
	               i3=new BigDecimal(0.00);
	           }
		    
		    }
		    catch (SQLException e) {
		        System.out.println("SQL Exception -->"+e);
		    }
		    
		    try{
				String ac="SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID,bank_id,branch_id From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "+cboAcc_UnitCode+" and status='Y' and bank_ac_no="+cmbBankAccNo;
				PreparedStatement pss=con.prepareStatement(ac);
				ResultSet rss=pss.executeQuery();
				if(rss.next())
				{
					mode_id=rss.getString("AC_OPERATIONAL_MODE_ID");//opr
					bank_id=rss.getInt("bank_id");//bank_id
					branch_id=rss.getInt("branch_id");//branch_id
				}
			}
			catch (Exception e) {
				System.out.println("Error in mode_id -->" + e);
			}
			
			try
			{

			    PreparedStatement pps=null;
			    ResultSet ress=null;
			    
	            String e444="SELECT  	 SUM(four_e)AS four_e 	            	 FROM  ( SELECT acc_u_id5, "+
				 " acc_off_id5, "+
	            	"  csh_bk_yr5, "+
	            	" csh_bk_mnth5, "+
	            	" Acc_No5, "+
	            	" SUM(four_e)AS four_e "+
	            	" FROM "+
	            	" (SELECT ACCOUNTING_UNIT_ID AS acc_u_id5, "+
	            	" ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5, "+
	            	" CASHBOOK_YEAR            AS csh_bk_yr5, "+
	            	" CASHBOOK_MONTH           AS csh_bk_mnth5, "+
	            	" Account_No               AS Acc_No5, "+
	            	//" SUM(CR_AMOUNT)           AS four_e "+ changed to DR on 18Feb2016
	            	" SUM(DR_AMOUNT)           AS four_e "+
	            	" FROM FAS_BRS_TRANSACTION "+
	            	" WHERE accounting_unit_id              =  "+cboAcc_UnitCode+
	            	" AND accounting_for_office_id          =  "+cboOffice_code+
	            	" AND extract(YEAR FROM PASSBOOK_DATE)  =  "+cboCashBook_Year+
	            	" AND ACCOUNT_NO                        =  "+cmbBankAccNo+
	            	" AND extract(MONTH FROM PASSBOOK_DATE) =  "+cboCashBook_Month+
	            	" AND Twad_Or_Non_Twad                  ='NT' "+
	            	" AND TRANSACTION_TYPE                 IN(30) "+
	            	" AND (CLEARED_BASED_ON_FOLLOWUP       IS NULL "+
	            	" OR CLEARED_BASED_ON_FOLLOWUP          ='N') "+
	            	" GROUP BY ACCOUNTING_UNIT_ID, "+
	            	" ACCOUNTING_FOR_OFFICE_ID, "+
	            	" CASHBOOK_YEAR, "+
	            	" CASHBOOK_MONTH, "+
	            	" Account_No "+
	            	"  UNION ALL "+
	            	"  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5, "+
	            	"   ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5, "+
	            	"  CASHBOOK_YEAR            AS csh_bk_yr5, "+
	            	"  CASHBOOK_MONTH           AS csh_bk_mnth5, "+
	            	"  Account_No               AS Acc_No5, "+
	            	//"   SUM(DR_AMOUNT)           AS four_e "+
	            	"   SUM(CR_AMOUNT)           AS four_e "+
	            	" FROM FAS_BRS_TRANSACTION "+
	            	"  WHERE accounting_unit_id              =  "+cboAcc_UnitCode+
	            	"  AND accounting_for_office_id          =  "+cboOffice_code+
	         //   	"  AND extract(YEAR FROM PASSBOOK_DATE)  =  "+cboCashBook_Year+
	            	 	"  AND extract(YEAR FROM CLEARENCE_DATE)  =  "+cboCashBook_Year+
	            	"  AND ACCOUNT_NO                        =  "+cmbBankAccNo+
	            //	"  AND extract(MONTH FROM PASSBOOK_DATE) =  "+cboCashBook_Month+
	            		"  AND extract(MONTH FROM CLEARENCE_DATE) =  "+cboCashBook_Month+
	            	"  AND Twad_Or_Non_Twad                  ='NT' "+
	            	"    AND 	( (CASHBOOK_YEAR = "+cboCashBook_Year+
	            	"     AND  	 CASHBOOK_MONTH <= "+cboCashBook_Month+" ) "+
	            	"   or CASHBOOK_YEAR  < "+cboCashBook_Year+" ) "+
	            //	"  AND TRANSACTION_TYPE                 IN(30) "+ added type 26 in addition on 19/10/2016  sathya  tresuary issue
	            	"  AND TRANSACTION_TYPE                 IN(30,6,7,12,34,26) "+
	            	"  AND CLEARED_BASED_ON_FOLLOWUP        ='Y' "+
	            	"  GROUP BY ACCOUNTING_UNIT_ID, "+
	            	"  ACCOUNTING_FOR_OFFICE_ID, "+
	            	"  CASHBOOK_YEAR, "+
	            	"  CASHBOOK_MONTH, "+
	            	"  Account_No "+
	            	"   ) "+
	            	" GROUP BY acc_u_id5, "+
	            	"  acc_off_id5, "+
	            	"  csh_bk_yr5, "+
	            	"  csh_bk_mnth5, "+
	            	"  Acc_No5 )";
			    
			    System.out.println("NIC test Feb 2016ne:"+e444);
			    ppee4=con.prepareStatement(e444);
			    re4=ppee4.executeQuery();
			    while(re4.next()){
			    
			    	four_e4=re4.getDouble("four_e");
			     	System.out.println("four_e4:::"+four_e4);
			        i4_four = new BigDecimal(four_e4);
		                i4_excfour++;
		            }
			   if(i4_excfour==0) {
				   i4_four=new BigDecimal(0.00);
		           }
			    
			}
			catch (Exception e4) {
				System.out.println("Error in 4e:-->" + e4);
			}
	     System.out.println("i4_four::"+i4_four);
            String sql_rep="";
            int c_new=0;String c_flag="";
            String IBT_flag="";
            // check IBT Accoutn Equal to FROM_Acc or To_Acc
            
       /*   try{  PreparedStatement ps_IBT=con.prepareStatement("SELECT " +
"  CASE " +
"    WHEN ACCOUNT_NO=FROM_ACCOUNT_NO " +
"    THEN 'from' " +
"    WHEN ACCOUNT_NO=TO_ACCOUNT_NO " +
"    THEN 'to' " +
"  END AS flag_IBT " +
" FROM " +
"  (SELECT T.ACCOUNT_NO, " +
"    i.FROM_ACCOUNT_NO, " +
"    i.TO_ACCOUNT_NO " +
"  FROM FAS_BRS_TRANSACTION T, " +
"    FAS_INTER_BANK_TRF_AT_HO I " +
"  WHERE T.ACCOUNTING_UNIT_ID             = " +cboAcc_UnitCode+ 
"  AND T.ACCOUNTING_FOR_OFFICE_ID         =" +cboOffice_code+ 
"  AND EXTRACT(YEAR FROM T.PASSBOOK_DATE) =" +cboCashBook_Year+ 
"  AND t.ACCOUNT_NO                       = " +cmbBankAccNo+ 
"  AND EXTRACT(MONTH FROM T.PASSBOOK_DATE)=" +cboCashBook_Month+ 
"  AND t.DOC_TYPE                         ='IBT' " +
"  AND T.ACCOUNTING_UNIT_ID               =I.ACCOUNTING_UNIT_ID " +
"  AND T.ACCOUNTING_FOR_OFFICE_ID         =I.ACCOUNTING_FOR_OFFICE_ID " +
"  AND T.CASHBOOK_YEAR                    =I.CASHBOOK_YEAR " +
"  AND T.CASHBOOK_MONTH                   =I.CASHBOOK_MONTH " +
"  AND (T.ACCOUNT_NO                      =I.FROM_ACCOUNT_NO " +
"  OR T.ACCOUNT_NO                        =I.TO_ACCOUNT_NO) " +
"  AND t.doc_date                         =i.DATE_OF_TRANSFER " +
"  )");
          ResultSet rs_IBT=ps_IBT.executeQuery();
          while(rs_IBT.next())
          {
        	  IBT_flag=rs_IBT.getString(1);
          }
          
          }catch(Exception e)
{
	e.printStackTrace();
}
            
            
          System.out.println("IBT_flag"+IBT_flag); */ 
            
            /// For there s no records in any transaction and that stuation we need to check previous month records
            
         try{   PreparedStatement ps_new=con.prepareStatement(
        		 "SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
            		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            		"  Account_No               AS Acc_No, " +
            		"  SUM(total_amount)        AS A_2a " +
            		"FROM FAS_payment_master " +
            	
            	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
       	            "          AND accounting_for_office_id=" +cboOffice_code+ 
       	            "          AND cashbook_month          = " +cboCashBook_Month+ 
       	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
       	            "          AND Account_No              = " +cmbBankAccNo+ 
            		"AND Payment_Status          ='L' " +
            		"GROUP BY Accounting_Unit_Id, " +
            		"  Accounting_For_Office_Id, " +
            		"  Cashbook_Year, " +
            		"  Cashbook_Month, " +
            		"  ACCOUNT_NO " +
            		"UNION ALL " +
            		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
            		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            		"  Account_No               AS Acc_No, " +
            		"  SUM(Total_Amount)        AS A_2a " +
            		"FROM FAS_receipt_master " +
            	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
       	            "          AND accounting_for_office_id=" +cboOffice_code+ 
       	            "          AND cashbook_month          = " +cboCashBook_Month+ 
       	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
       	            "          AND Account_No              = " +cmbBankAccNo+ 
            		"AND receipt_Status          ='L' " +
            		"AND CREATED_BY_MODULE       ='SC' " +
            		"GROUP BY Accounting_Unit_Id, " +
            		"  Accounting_For_Office_Id, " +
            		"  Cashbook_Year, " +
            		"  Cashbook_Month, " +
            		"  Account_No " +
            		"UNION ALL " +
            		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
            		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            		"  OFFICE_ACCOUNT_NO        AS Acc_No, " +
            		"  SUM(Total_Amount)        AS A_2a " +
            		"FROM FAS_FUND_TRF_FROM_OFFICE " +
            	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
       	            "          AND accounting_for_office_id=" +cboOffice_code+ 
       	            "          AND cashbook_month          = " +cboCashBook_Month+ 
       	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
       	            "          AND OFFICE_ACCOUNT_NO              = " +cmbBankAccNo+ 
            		
            		"GROUP BY Accounting_Unit_Id, " +
            		"  Accounting_For_Office_Id, " +
            		"  Cashbook_Year, " +
            		"  Cashbook_Month, " +
            		"  OFFICE_ACCOUNT_NO " +
            		"UNION ALL " +
            		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
            		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            		"  FROM_ACCOUNT_NO          AS Acc_No, " +
            		"  SUM(Total_Amount)        AS A_2a " +
            		"FROM FAS_INTER_BANK_TRF_AT_HO " +
            	      "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         	            "          AND accounting_for_office_id=" +cboOffice_code+ 
         	            "          AND cashbook_month          = " +cboCashBook_Month+ 
         	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
         	            "          AND FROM_ACCOUNT_NO              = " +cmbBankAccNo+ 
            	
            		"GROUP BY Accounting_Unit_Id, " +
            		"  Accounting_For_Office_Id, " +
            		"  Cashbook_Year, " +
            		"  CASHBOOK_MONTH, " +
            		"  FROM_ACCOUNT_NO" );
         ResultSet rs_new=ps_new.executeQuery();
         while(rs_new.next()){
        	 c_new=rs_new.getInt(1);
        	 c_new++;
        	
         }
         }catch(Exception e){
        	 e.printStackTrace();
         }
         
         System.out.println("SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
            		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            		"  Account_No               AS Acc_No, " +
            		"  SUM(total_amount)        AS A_2a " +
            		"FROM FAS_payment_master " +
            	
            	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
       	            "          AND accounting_for_office_id=" +cboOffice_code+ 
       	            "          AND cashbook_month          = " +cboCashBook_Month+ 
       	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
       	            "          AND Account_No              = " +cmbBankAccNo+ 
            		"AND Payment_Status          ='L' " +
            		"GROUP BY Accounting_Unit_Id, " +
            		"  Accounting_For_Office_Id, " +
            		"  Cashbook_Year, " +
            		"  Cashbook_Month, " +
            		"  ACCOUNT_NO " +
            		"UNION ALL " +
            		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
            		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            		"  Account_No               AS Acc_No, " +
            		"  SUM(Total_Amount)        AS A_2a " +
            		"FROM FAS_receipt_master " +
            	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
       	            "          AND accounting_for_office_id=" +cboOffice_code+ 
       	            "          AND cashbook_month          = " +cboCashBook_Month+ 
       	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
       	            "          AND Account_No              = " +cmbBankAccNo+ 
            		"AND receipt_Status          ='L' " +
            		"AND CREATED_BY_MODULE       ='SC' " +
            		"GROUP BY Accounting_Unit_Id, " +
            		"  Accounting_For_Office_Id, " +
            		"  Cashbook_Year, " +
            		"  Cashbook_Month, " +
            		"  Account_No " +
            		"UNION ALL " +
            		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
            		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            		"  OFFICE_ACCOUNT_NO        AS Acc_No, " +
            		"  SUM(Total_Amount)        AS A_2a " +
            		"FROM FAS_FUND_TRF_FROM_OFFICE " +
            	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
       	            "          AND accounting_for_office_id=" +cboOffice_code+ 
       	            "          AND cashbook_month          = " +cboCashBook_Month+ 
       	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
       	            "          AND OFFICE_ACCOUNT_NO              = " +cmbBankAccNo+ 
            		
            		"GROUP BY Accounting_Unit_Id, " +
            		"  Accounting_For_Office_Id, " +
            		"  Cashbook_Year, " +
            		"  Cashbook_Month, " +
            		"  OFFICE_ACCOUNT_NO " +
            		"UNION ALL " +
            		"SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
            		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            		"  FROM_ACCOUNT_NO          AS Acc_No, " +
            		"  SUM(Total_Amount)        AS A_2a " +
            		"FROM FAS_INTER_BANK_TRF_AT_HO " +
            	      "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
         	            "          AND accounting_for_office_id=" +cboOffice_code+ 
         	            "          AND cashbook_month          = " +cboCashBook_Month+ 
         	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
         	            "          AND FROM_ACCOUNT_NO              = " +cmbBankAccNo+ 
            	
            		"GROUP BY Accounting_Unit_Id, " +
            		"  Accounting_For_Office_Id, " +
            		"  Cashbook_Year, " +
            		"  CASHBOOK_MONTH, " +
            		"  FROM_ACCOUNT_NO"+"====================================>Four a Details");
         
         System.out.println("c_new ....................... "+c_new);
         if(c_new==0)
         {
        	 int month_new=0;
        	 int year_new=0;
        	 if (cboCashBook_Month==1){
        		 month_new=12;year_new=cboCashBook_Year-1;
        	 }else{
        		 month_new=cboCashBook_Month;
        		 year_new=cboCashBook_Year;
        	 }
        	 c_flag="SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
             		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
             		  cboCashBook_Year  +"         AS csh_bk_yr, " +
             		 cboCashBook_Month+"        AS csh_bk_mnth, " +
             		  cmbBankAccNo+"               AS Acc_No, " +
             		"  0       AS A_2a " +
             		"FROM FAS_payment_master " +
             	
             	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        	            "          AND accounting_for_office_id=" +cboOffice_code+ 
        	         /*   "          AND cashbook_month          = "+ month_new+ 
        	            "          AND Cashbook_Year           =" +year_new+ */
        	       "     AND ((cashbook_year         < "+cboCashBook_Year+
        	       "         AND cashbook_month         <=12) "+
        	       "         OR (CASHBOOK_YEAR           = "+cboCashBook_Year+
        	       "         AND CASHBOOK_MONTH         <= "+cboCashBook_Month+
        	            "))          AND Account_No              = " +cmbBankAccNo+ 
             		"AND Payment_Status          ='L' " +
             		"GROUP BY Accounting_Unit_Id, " +
             		"  Accounting_For_Office_Id, " +
             		"  Cashbook_Year, " +
             		"  Cashbook_Month, " +
             		"  ACCOUNT_NO " +
             		  //joan added on 08 jan 2015
      	            "    UNION ALL "+
      	            "    SELECT ACCOUNTING_UNIT_ID  AS acc_u_id," +
      	            "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id," +
      	          cboCashBook_Year  +"         AS csh_bk_yr, " +
          		 cboCashBook_Month+"        AS csh_bk_mnth, " +
          		  cmbBankAccNo+"               AS Acc_No, " +
      	            "     0      AS A_2a" +
      	            "    FROM FAS_INTER_BANK_TRF_AT_HO" +
      	            "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
      	            "          AND accounting_for_office_id=" +cboOffice_code+ 
      	            "          AND cashbook_month          = " +month_new+ 
      	            "          AND Cashbook_Year           =" +year_new+ 
      	            "          AND FROM_ACCOUNT_NO              = " +cmbBankAccNo+ 
      	             "    and TRANSFER_STATUS <> 'C'  GROUP BY Accounting_Unit_Id," +
      	             "      Accounting_For_Office_Id," +
      	             "      Cashbook_Year," +
      	             "      CASHBOOK_MONTH," +
      	             "      FROM_ACCOUNT_NO" ;
         }else if(c_new!=0)
         {
        	 c_flag= "SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
             		"  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
             		"  CASHBOOK_YEAR            AS csh_bk_yr, " +
             		"  CASHBOOK_MONTH           AS csh_bk_mnth, " +
             		"  Account_No               AS Acc_No, " +
             		"  SUM(total_amount)        AS A_2a " +
             		"FROM FAS_payment_master " +
             	
             	       "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        	            "          AND accounting_for_office_id=" +cboOffice_code+ 
        	            "          AND cashbook_month          = " +cboCashBook_Month+ 
        	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
        	            "          AND Account_No              = " +cmbBankAccNo+ 
             		"AND Payment_Status          ='L' " +
        	            " AND CREATED_BY_MODULE !='CRM' " +
             		"GROUP BY Accounting_Unit_Id, " +
             		"  Accounting_For_Office_Id, " +
             		"  Cashbook_Year, " +
             		"  Cashbook_Month, " +
             		"  ACCOUNT_NO " +
             		  //joan added on 08 jan 2015
      	            "    UNION ALL "+
      	            "    SELECT ACCOUNTING_UNIT_ID  AS acc_u_id," +
      	            "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id," +
      	            "      CASHBOOK_YEAR            AS csh_bk_yr," +
      	            "       CASHBOOK_MONTH           AS csh_bk_mnth," +
      	            "       FROM_ACCOUNT_NO        AS Acc_No," +
      	            "       SUM(Total_Amount)        AS A_2a" +
      	            "    FROM FAS_INTER_BANK_TRF_AT_HO" +
      	            "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
      	            "          AND accounting_for_office_id=" +cboOffice_code+ 
      	            "          AND cashbook_month          = " +cboCashBook_Month+ 
      	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
      	            "          AND FROM_ACCOUNT_NO              = " +cmbBankAccNo+ 
      	             "    and TRANSFER_STATUS <> 'C'  GROUP BY Accounting_Unit_Id," +
      	             "      Accounting_For_Office_Id," +
      	             "      Cashbook_Year," +
      	             "      CASHBOOK_MONTH," +
      	             "      FROM_ACCOUNT_NO" 
      	             
      	             //Joan Added 10 DEC 2015
      	             
//commanded on 22Jun2018      	             


//   "    UNION ALL "+
//     "    SELECT ACCOUNTING_UNIT_ID  AS acc_u_id," +
//     "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id," +
//     "      CASHBOOK_YEAR            AS csh_bk_yr," +
//     "       CASHBOOK_MONTH           AS csh_bk_mnth," +
//     "   TO_ACCOUNT_NO        AS Acc_No," +
//     "       SUM(Total_Amount)        AS A_2a" +
//     "    FROM FAS_INTER_BANK_TRF_AT_HO" +
//     "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
//     "          AND accounting_for_office_id=" +cboOffice_code+ 
//     "          AND cashbook_month          = " +cboCashBook_Month+ 
//     "          AND Cashbook_Year           =" +cboCashBook_Year+ 
//     "          AND TO_ACCOUNT_NO              = " +cmbBankAccNo+ 
//      "    and TRANSFER_STATUS <> 'C'  GROUP BY Accounting_Unit_Id," +
//      "      Accounting_For_Office_Id," +
//      "      Cashbook_Year," +
//      "      CASHBOOK_MONTH," +
//      "      TO_ACCOUNT_NO" 
      	             
      	             
      	             ;
         }
        
            
          
            	sql_rep=	"SELECT DECODE(OFFICE_NAME,NULL,'-',OFFICE_NAME) AS OFFICE_NAME,\n" + 
            	            " '"+bankName+"'   AS BANK_NAME,\n" + 
            	            " '"+branchName+"'      AS BRANCH_NAME,\n" + 
            	            "  acc_u_id,\n" + 
            	            "  acc_off_id,\n" + 
            	            "  csh_bk_yr,\n" + 
            	            "  csh_bk_mnth,\n" + 
            	            cmbBankAccNo+"   AS  acc_no,\n" + 
            	            "  DECODE(OB_PART2A,NULL,0,OB_PART2A) AS A_1,\n" + 
            	            "  nvl(A_2a,0) as A_2a,\n" + 
            	            "  nvl(A_2b,0) as A_2b ,\n" + 
            	            " nvl(A_2c,0) as A_2c ,\n" + 
            	            " nvl(A_2d,0) as A_2d ,\n" + 
            	            " nvl(A_2e,0) as A_2e ,\n" + 
            	            "  (DECODE(OB_PART2A,NULL,0,OB_PART2A)+ nvl(A_2e,0)) AS A_3,\n" + 
            	            " nvl(Pay_Amt,0) as Pay_Amt ,\n" + 
            	            "  DECODE(Sc_Amt,NULL,0,Sc_Amt)AS Sc_Amt,\n" + 
            	            "  DECODE(IBT_amt,NULL,0,IBT_amt)                             AS IBT_amt,\n" + 
            	            "  nvl(A_4,0) as A_4,\n" + 
            	            "  ( (DECODE(Ob_Part2a,NULL,0,Ob_Part2a)+A_2e)- nvl(A_4,0)) AS A_5,\n" + 
            	            " nvl(A_6a,0) as A_6a ,\n" + 
            	            " nvl(A_6b,0) as A_6b  ,\n" + 
            	            " nvl(A_6c,0) as A_6c ,\n" + 
            	            "  PASSBOOK_BALANCE\n" + 
            	            "FROM (\n" + 
            	            "  (SELECT Acc_U_Id6,\n" + 
            	            "    Acc_Off_Id6,\n" + 
            	            "    Csh_Bk_Yr6,\n" + 
            	            "    Csh_Bk_Mnth6,\n" + 
            	            "    Acc_No6,\n" + 
            	            "    Ob_Part2a,\n" + 
            	            "    OFFICE_NAME,\n" + 
            	            "    BANK_NAME,\n" + 
            	            "    BRANCH_NAME,\n" + 
            	            "    PASSBOOK_BALANCE\n" + 
            	            "  FROM\n" + 
            	            "    (SELECT rownum AS slno1,\n" + 
            	            "      acc_u_id6,\n" + 
            	            "      acc_off_id6,\n" + 
            	            "      csh_bk_yr6,\n" + 
            	            "      csh_bk_mnth6,\n" + 
            	            "      acc_no6,\n" + 
            	            "      OB_PART2A,\n" + 
            	            "      OFFICE_NAME\n" + 
            	            "    FROM\n" + 
            	            "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id6,\n" + 
            	            "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id6,\n" + 
            	            "        CASHBOOK_YEAR            AS csh_bk_yr6,\n" + 
            	            "        CASHBOOK_MONTH           AS csh_bk_mnth6,\n" + 
            	            "        ACCOUNT_NO               AS acc_no6,\n" + 
            	            "        OB_PART2A\n" + 
            	            "      FROM FAS_BRS_OB\n" + 
            	            "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            	            "      AND accounting_for_office_id= " +cboOffice_code+ 
            	            "      AND cashbook_month          = " +cboCashBook_Month+ 
            	            "      AND cashbook_year           =" +cboCashBook_Year+ 
            	            "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            	            "      )AUID1\n" + 
            	            "    right OUTER JOIN\n" + 
            	            "      (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES where OFFICE_ID=" + cboOffice_code+
            	            "      )AUID2\n" + 
            	            "    ON AUID1.acc_off_id6 = AUID2.OFFICE_ID\n" + 
            	            "    ) t1\n" + 
            	            "  LEFT OUTER JOIN\n" + 
            	            "    (SELECT Slno2,\n" + 
            	            "      Bank_Name,\n" + 
            	            "      Branch_Name,\n" + 
            	            "      PASSBOOK_BALANCE\n" + 
            	            "    FROM\n" + 
            	            "      (SELECT rownum AS slno2,\n" + 
            	            "        BANK_ID,\n" + 
            	            "        BRANCH_ID,\n" + 
            	            "        PASSBOOK_BALANCE\n" + 
            	            "      FROM FAS_BRS_MASTER\n" + 
            	            "      WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
            	            "      AND accounting_for_office_id= " +cboOffice_code+ 
            	            "      AND cashbook_month          = " +cboCashBook_Month+ 
            	            "      AND cashbook_year           =" +cboCashBook_Year+ 
            	            "      AND ACCOUNT_NO              =" +cmbBankAccNo+ 
            	            "      )sss\n" + 
            	            "    LEFT OUTER JOIN\n" + 
            	            "      (SELECT BRANCH_ID AS brnch_id,\n" + 
            	            "        BANK_ID         AS bnk_id,\n" + 
            	            "        BRANCH_NAME\n" + 
            	            "      FROM FAS_MST_BANK_BRANCHES\n" + 
            	            "      )c\n" + 
            	            "    ON sss.BANK_ID    = c.bnk_id\n" + 
            	            "    AND sss.BRANCH_ID = c.brnch_id\n" + 
            	            "    LEFT OUTER JOIN\n" + 
            	            "      (SELECT BANK_ID AS bnk_id1,BANK_NAME FROM FAS_BANK_LIST\n" + 
            	            "      )d\n" + 
            	            "    ON sss.BANK_ID  = d.bnk_id1\n" + 
            	            "    )t2 ON t1.slno1 = t2.slno2\n" + 
            	            "  )XX\n" + 
            	            "LEFT OUTER JOIN\n" + 
            	            "  (SELECT acc_u_id,\n" + 
            	            "    acc_off_id,\n" + 
            	            "    csh_bk_yr,\n" + 
            	            "    csh_bk_mnth,\n" + 
            	            "    acc_no,\n" + 
            	            "    A_2a,\n" + 
            	            "    A_2b,\n" + 
            	            "    A_2c,\n" + 
            	            "    A_2d,\n" + 
            	            "    (DECODE(A_2c,NULL,0,A_2c) - DECODE(A_2d,NULL,0,A_2d)) AS A_2e,\n" + 
            	            "    Pay_Amt,IBT_Amt,\n" + 
            	            "    Sc_Amt,\n" + 
            	            "    A_4,\n" + 
            	            "    DECODE(A_6a,NULL,0,A_6a)                             AS A_6a,\n" + 
            	            "    DECODE(A_6b,NULL,0,A_6b)                             AS A_6b ,\n" + 
            	            "    (DECODE(A_6a,NULL,0,A_6a) + DECODE(A_6b,NULL,0,A_6b))AS A_6c\n" + 
            	            "  FROM\n" + 
            	            "    (SELECT acc_u_id,\n" + 
            	            "      acc_off_id,\n" + 
            	            "      csh_bk_yr,\n" + 
            	            "      csh_bk_mnth,\n" + 
            	            "      acc_no,\n" + 
            	            "      A_2a,\n" + 
            	            "      DECODE(A_2b,NULL,0,A_2b)         AS A_2b,\n" + 
            	            "      A_2a+( DECODE(A_2b,NULL,0,A_2b)) AS A_2c,\n" + 
            	            "      DECODE(A_2d,NULL,0,A_2d)         AS A_2d\n" + 
            	            "    FROM\n" + 
            	            "      (SELECT acc_u_id,\n" + 
            	            "        acc_off_id,\n" + 
            	            "        csh_bk_yr,\n" + 
            	            "        csh_bk_mnth,\n" + 
            	            "        acc_no,\n" + 
            	            "        SUM(A_2a) AS A_2a\n" + 
            	            "      FROM\n" + 
            	            "        (SELECT acc_u_id,\n" + 
            	            "          acc_off_id,\n" + 
            	            "          csh_bk_yr,\n" + 
            	            "          csh_bk_mnth,\n" + 
            	            "          acc_no,\n" + 
            	            "          SUM(A_2a) AS A_2a\n" + 
            	            "        FROM\n" + 
            	            "          ( "
            	            +c_flag+
            	            "          UNION ALL\n" + 
            	            "          SELECT ACCOUNTING_UNIT_ID  AS acc_u_id,\n" + 
            	            "            ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
            	            "            CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
            	            "            CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
            	            "            Account_No               AS Acc_No,\n" + 
            	            "            SUM(Total_Amount)        AS A_2a\n" + 
            	            "          FROM FAS_receipt_master\n" + 
            	            "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            	            "          AND accounting_for_office_id=" +cboOffice_code+ 
            	            "          AND cashbook_month          = " +cboCashBook_Month+ 
            	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
            	            "          AND Account_No              = " +cmbBankAccNo+ 
            	            "          AND receipt_Status          ='L'\n" + 
            	            "          AND CREATED_BY_MODULE       ='SC'\n" + 
            	            "          GROUP BY Accounting_Unit_Id,\n" + 
            	            "            Accounting_For_Office_Id,\n" + 
            	            "            Cashbook_Year,\n" + 
            	            "            Cashbook_Month,\n" + 
            	            "            Account_No\n" + 
            	            // Joan add On 28Oct2014 for twad10146 requirement
            	            " UNION ALL " +
            	            " SELECT ACCOUNTING_UNIT_ID  AS acc_u_id, " +
            	            "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
            	            "  CASHBOOK_YEAR            AS csh_bk_yr, " +
            	            "  CASHBOOK_MONTH           AS csh_bk_mnth, " +
            	            "  OFFICE_ACCOUNT_NO        AS Acc_No, " +
            	            "  SUM(Total_Amount)        AS A_2a " +
            	            " FROM FAS_FUND_TRF_FROM_OFFICE " +
            	            "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            	            "          AND accounting_for_office_id=" +cboOffice_code+ 
            	            "          AND cashbook_month          = " +cboCashBook_Month+ 
            	            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
            	            "          AND OFFICE_ACCOUNT_NO              = " +cmbBankAccNo+ 
            	            "   and TRANSFER_STATUS <> 'C'  GROUP BY Accounting_Unit_Id, " +
            	            "  Accounting_For_Office_Id, " +
            	            "  Cashbook_Year, " +
            	            "  Cashbook_Month, " +
            	            "  OFFICE_ACCOUNT_NO " +
            	          
            	            
            	            "          )\n" + 
            	            "        GROUP BY acc_u_id,\n" + 
            	            "          acc_off_id,\n" + 
            	            "          csh_bk_yr,\n" + 
            	            "          Csh_Bk_Mnth,\n" + 
            	            "          acc_no\n" + 
            	            "        )\n" + 
            	            "      GROUP BY acc_u_id,\n" + 
            	            "        acc_off_id,\n" + 
            	            "        csh_bk_yr,\n" + 
            	            "        csh_bk_mnth,\n" + 
            	            "        acc_no\n" + 
            	            "      )a\n" + 
            	            "    LEFT OUTER JOIN\n" + 
            	            "      (\n" + 
            	            "      SELECT t.ACCOUNTING_UNIT_ID AS acc_u_id1,\n" + 
            	            "        t.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id1,\n" + 
            	            "        t.CASHBOOK_YEAR            AS csh_bk_yr1,\n" + 
            	            "        t.CASHBOOK_MONTH           AS csh_bk_mnth1,\n" + 
            	            "        t.sub_ledger_code               AS acc_no1,\n" + 
            	            "        SUM(t.AMOUNT)           AS A_2b\n" + 
            	            "      FROM FAS_journal_master m,fas_journal_transaction t\n" + 
            	            "      WHERE m.accounting_unit_id    = t.accounting_unit_id\n" + 
            	            "      AND m.accounting_for_office_id= t.accounting_for_office_id\n" + 
            	            "      AND m.cashbook_month          =  t.cashbook_month\n" + 
            	            "      AND m.cashbook_year           = t.cashbook_year\n" + 
            	            "      AND m.voucher_no           = t.voucher_no\n" + 
            	            "      and m.journal_status='L'\n" + 
            	            "      and t.cr_dr_indicator='CR'\n" + 
            	            "      and m.accounting_unit_id    =" +cboAcc_UnitCode+ 
            	            "      AND m.accounting_for_office_id= " +cboOffice_code+ 
            	            "      AND m.cashbook_month          =  " +cboCashBook_Month+ 
            	            "      AND m.cashbook_year           = " +cboCashBook_Year+ 
            	            "      AND t.sub_ledger_code            = " +cmbBankAccNo+ 
            	            "      and t.sub_ledger_type_code=6     \n" + 
            	            "      GROUP BY t.ACCOUNTING_UNIT_ID,\n" + 
            	            "        t.ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "        t.CASHBOOK_YEAR,\n" + 
            	            "        t.CASHBOOK_MONTH,\n" + 
            	            "        t.sub_ledger_code\n" + 
            	            "      )b\n" + 
            	            "    ON a.acc_u_id     =b.acc_u_id1\n" + 
            	            "    AND a.acc_off_id  = b.acc_off_id1\n" + 
            	            "    AND a.csh_bk_yr   = b.csh_bk_yr1\n" + 
            	            "    AND a.csh_bk_mnth = b.csh_bk_mnth1\n" + 
            	            "    AND a.acc_no      = b.acc_no1\n" + 
            	            "    LEFT OUTER JOIN\n" + 
            	            "      (SELECT Acc_U_Id2,\n" + 
            	            "        Acc_Off_Id2,\n" + 
            	            "        Csh_Bk_Yr2,\n" + 
            	            "        Csh_Bk_Mnth2,\n" + 
            	            "        Acc_No2,\n" + 
            	            "        SUM(A_2d)AS A_2d\n" + 
            	            "      FROM\n" + 
            	            "        (\n" + 
            	            "        SELECT t.ACCOUNTING_UNIT_ID AS acc_u_id2,\n" + 
            	            "        t.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id2,\n" + 
            	            "        t.CASHBOOK_YEAR            AS csh_bk_yr2,\n" + 
            	            "        t.CASHBOOK_MONTH           AS csh_bk_mnth2,\n" + 
            	            "        t.sub_ledger_code               AS acc_no2,\n" + 
            	            "        SUM(t.AMOUNT)           AS A_2d\n" + 
            	            "      FROM FAS_journal_master m,fas_journal_transaction t\n" + 
            	            "      WHERE m.accounting_unit_id    = t.accounting_unit_id\n" + 
            	            "      AND m.accounting_for_office_id= t.accounting_for_office_id\n" + 
            	            "      AND m.cashbook_month          =  t.cashbook_month\n" + 
            	            "      AND m.cashbook_year           = t.cashbook_year\n" + 
            	            "      AND m.voucher_no           = t.voucher_no\n" + 
            	            "      and m.journal_status='L'\n" + 
            	            "      and t.cr_dr_indicator='DR'\n" + 
            	            "      and m.accounting_unit_id    =" +cboAcc_UnitCode+ 
            	            "      AND m.accounting_for_office_id= " +cboOffice_code+ 
            	            "      AND m.cashbook_month          =" +cboCashBook_Month+ 
            	            "      AND m.cashbook_year           = " +cboCashBook_Year+ 
            	            "      AND t.sub_ledger_code            =" +cmbBankAccNo+ 
            	            "      and t.sub_ledger_type_code=6     \n" + 
            	                        	            
            	            "      GROUP BY t.ACCOUNTING_UNIT_ID,\n" + 
            	            "        t.ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "        t.CASHBOOK_YEAR,\n" + 
            	            "        t.CASHBOOK_MONTH,\n" + 
            	            "        t.sub_ledger_code\n" + 
            	            "        )\n" + 
            	            "      GROUP BY Acc_U_Id2,\n" + 
            	            "        Acc_Off_Id2,\n" + 
            	            "        Csh_Bk_Yr2,\n" + 
            	            "        Csh_Bk_Mnth2,\n" + 
            	            "        Acc_No2\n" + 
            	            "      )c\n" + 
            	            "    ON a.acc_u_id     =c.acc_u_id2\n" + 
            	            "    AND a.acc_off_id  = c.acc_off_id2\n" + 
            	            "    AND a.csh_bk_yr   = c.csh_bk_yr2\n" + 
            	            "    AND a.csh_bk_mnth = c.csh_bk_mnth2\n" + 
            	            "    AND a.acc_no      = c.acc_no2\n" + 
            	            "    )x\n" + 
            	            "  LEFT OUTER JOIN\n" + 
            	            "    (SELECT acc_u_id3,\n" + 
            	            "      acc_off_id3,\n" + 
            	            "      "+cboCashBook_Year+" as csh_bk_yr3,\n" + 
            	            "      "+cboCashBook_Month+" AS csh_bk_mnth3,\n" + 
            	            "      acc_no3,\n" + 
            	            "      sum(Pay_Amt)Pay_Amt,sum(IBT_Amt) as IBT_Amt,\n" + 
            	            "      sum(Sc_Amt)Sc_Amt,\n" + 
            	            "      sum(A_4)A_4\n" + 
            	            "    FROM\n" + 
            	            "      (SELECT A.Acc_U_Id3,\n" + 
            	            "        A.Acc_Off_Id3,\n" + 
            	            "        A.Csh_Bk_Yr3,\n" + 
            	            "        A.Acc_No3,\n" + 
            	            "        A.Pay_Amt,c.IBT_Amt,\n" + 
            	            "        Sc_Amt,\n" + 
            	            "        (A.Pay_Amt+DECODE(Sc_Amt,NULL,0,Sc_Amt)++DECODE(IBT_Amt,NULL,0,IBT_Amt)) AS a_4\n" + 
            	            "      FROM \n" + 
            	            /*"        (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
            	            "          ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
            	            "          CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
            	            "          Account_No                                   AS Acc_No3,\n" + 
            	          //AMOUNT_IN_PASSBOOK Lakshmi 8Nov13
            	     //       "          DECODE(SUM(DR_AMOUNT),NULL,0,SUM(DR_AMOUNT)) AS pay_amt\n" + 
            	           "          DECODE(SUM(AMOUNT_IN_PASSBOOK),NULL,0,SUM(AMOUNT_IN_PASSBOOK)) AS pay_amt\n" + 
            	            
            	            "        FROM FAS_BRS_TRANSACTION\n" + 
            	            "        WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
            	            "        AND accounting_for_office_id         =" +cboOffice_code+ 
            	            "        AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
            	            "        AND ACCOUNT_NO                       =" +cmbBankAccNo+ 
            	            "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
            	            "        AND TWAD_OR_NON_TWAD                 ='T'\n" + 
            	            "        AND doc_type                        IN ('P')\n" + 
            	            "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            	            "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "          Cashbook_Year,\n" + 
            	            "          Account_No\n" + */
            	            //15May14
            	         /*  " ( SELECT acc_u_id3, "+
            	            "   acc_off_id3, "+
            	            " csh_bk_yr3, "+
            	            "   Acc_No3, "+
            	            "  (dr_amt-cr_amt) as pay_amt "+
            	           
            	         
            	            "  from   "+
            	            
            	            "        (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
            	            "          ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
            	            "          CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
            	            "          Account_No                                   AS Acc_No3,\n" + 
            	         
            	         //  "          DECODE(SUM(AMOUNT_IN_PASSBOOK),NULL,0,SUM(AMOUNT_IN_PASSBOOK)) AS pay_amt\n" + 
            	         "   DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS cr_amt, "+
            	         "   DECODE(SUM(DR_AMOUNT),NULL,0,SUM(DR_AMOUNT)) AS dr_amt "+
            	            
            	            "        FROM FAS_BRS_TRANSACTION\n" + 
            	            "        WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
            	            "        AND accounting_for_office_id         =" +cboOffice_code+ 
            	            "        AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
            	            "        AND ACCOUNT_NO                       =" +cmbBankAccNo+ 
            	            "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
            	            "        AND TWAD_OR_NON_TWAD                 ='T'\n" + 
            	            "        AND doc_type                       IN ('P','FT from Office')\n" + 
            	            "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            	            "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "          Cashbook_Year,\n" + 
            	            "          Account_No\n" + 
            	            
            	            " ) "+
            	            
            	            "        )A\n" + 
            	            
  "     left outer join  (SELECT acc_u_id3,\n" + 
  "       acc_off_id3,\n" + 
  "       csh_bk_yr3,\n" + 
  "       Acc_No3,\n" + 
  "       (dr_amt-cr_amt) AS pay_amt\n" + 
  "      FROM\n" + 
  "       (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
  "         ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
  "         CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
  "         Account_No                                   AS Acc_No3,\n" + 
  "         DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS cr_amt,\n" + 
  "         0 AS dr_amt\n" + 
  "       FROM FAS_BRS_TRANSACTION\n" + 
  "        WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
  "        AND accounting_for_office_id         =" +cboOffice_code+ 
  "        AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
  "        AND ACCOUNT_NO                       =" +cmbBankAccNo+ 
  "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
  "        AND TWAD_OR_NON_TWAD                 ='T'\n" + 
  "     AND doc_type                        IN ('IBT')\n" + 
  "       GROUP BY ACCOUNTING_UNIT_ID,\n" + 
  "         ACCOUNTING_FOR_OFFICE_ID,\n" + 
  "         Cashbook_Year,\n" + 
  "         Account_No\n" + 
  "       )\n" + 
  "     )BB\n" + 
  "        ON A.ACC_U_ID3   =BB.ACC_U_ID3\n" + 
  "     AND A.ACC_OFF_ID3=BB.ACC_OFF_ID3\n" + 
  "     AND A.Csh_Bk_Yr3 =Bb.Csh_Bk_Yr3\n" + 
  "     AND A.Acc_No3    =Bb.Acc_No3\n" + */
            	            
  

  "    (      (SELECT acc_u_id3,\n" + 
  "           acc_off_id3,\n" + 
  "            csh_bk_yr3,\n" + 
  "           Acc_No3,\n" + 
 //"             (dr_amt-cr_amt) AS pay_amt\n" + 
 " AMOUNT_IN_PASSBOOK    AS pay_amt\n" + 
  "           FROM\n" + 
  "       (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
  "         ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
  "         CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
  "         Account_No                                   AS Acc_No3,\n" + 
 /* "         DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS cr_amt,\n" + 
  "         DECODE(SUM(DR_AMOUNT),NULL,0,SUM(DR_AMOUNT)) AS dr_amt\n" + */
  " sum(AMOUNT_IN_PASSBOOK) as AMOUNT_IN_PASSBOOK \n" + 
  "       FROM FAS_BRS_TRANSACTION\n" + 
  "       WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
  "       AND accounting_for_office_id         =" +cboOffice_code+ 
  "       AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
  "       AND ACCOUNT_NO                       =" +cmbBankAccNo+ 
  "       AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
  "       AND TWAD_OR_NON_TWAD                 ='T'\n" + 
  "       AND doc_type                        IN ('P','FT from Office')\n" + 
  "       GROUP BY ACCOUNTING_UNIT_ID,\n" + 
  "         ACCOUNTING_FOR_OFFICE_ID,\n" + 
  "         Cashbook_Year,\n" + 
  "         ACCOUNT_NO\n" + 
  "       ))\n" + 
      
//  "      union all\n" + 
//  "      SELECT acc_u_id3,\n" + 
//  "       acc_off_id3,\n" + 
//  "       csh_bk_yr3,\n" + 
//  "       ACC_NO3,\n" + 
//  //"       abs((cr_amt-dr_amt)) AS pay_amt\n" +
//  
//" AMOUNT_IN_PASSBOOK AS pay_amt\n" +
//  "      FROM\n" + 
//  "       (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
//  "         ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
//  "         CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
//  "         Account_No                                   AS Acc_No3,\n" +
///*  if(IBT_flag.equalsIgnoreCase("from")){
//  sql_rep=sql_rep+  "        DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS cr_amt,\n" + 
//  "         0                                            AS dr_amt\n" ;} 
//  else if(IBT_flag.equalsIgnoreCase("to"))
//  {
//
//	  sql_rep=sql_rep+  "         DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS cr_amt,\n" + 
//	  "         0                                            AS dr_amt\n" ;
//  }*/
//        //    	 "        DECODE(SUM(DR_AMOUNT),NULL,0,SUM(DR_AMOUNT)) AS cr_amt,\n" + 
//            //	  "         DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT))                 AS dr_amt\n" +\
//      /*
//       * 
//       * Joan changed on 07 Aug 15
//       */
//            
//        
//        //" sum(AMOUNT_IN_PASSBOOK) as AMOUNT_IN_PASSBOOK "+
//        " sum(AMOUNT_IN_PASSBOOK) - SUM(cr_amount) as AMOUNT_IN_PASSBOOK "+  
//"       FROM FAS_BRS_TRANSACTION\n" + 
//  "       WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
//  "       AND accounting_for_office_id         =" +cboOffice_code+ 
//  "       AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
//  "       AND ACCOUNT_NO                       =" +cmbBankAccNo+ 
//  "       AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
//  "       AND TWAD_OR_NON_TWAD                 ='T'\n" + 
//  "       AND doc_type                        IN ('IBT')\n" + 
// "  having SUM(AMOUNT_IN_PASSBOOK) - SUM(cr_amount) >0 "+
//  "       GROUP BY ACCOUNTING_UNIT_ID,\n" + 
//  "         ACCOUNTING_FOR_OFFICE_ID,\n" + 
//  "         Cashbook_Year,\n" + 
//  "         Account_No )" + 
  " )A \n " + 
  "       \n" + 
        
            	            
            	            
            	            
            	            "      LEFT OUTER JOIN\n" + 
            	            "        (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
            	            "          ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
            	            "          CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
            	            "          Account_No                                   AS Acc_No3,\n" + 
            	            //changed as on 31July2017 CR_amount to DR_amount by SS as CR changed to DR for SC in main form
            	            "          DECODE(SUM(DR_AMOUNT),NULL,0,SUM(DR_AMOUNT)) AS sc_amt\n" + 
            	            "        FROM FAS_BRS_TRANSACTION\n" + 
            	            "        WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
            	            "        AND accounting_for_office_id         =" +cboOffice_code+ 
            	            "        AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
            	            "        AND ACCOUNT_NO                       = " +cmbBankAccNo+ 
            	            "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
            	            "        AND TWAD_OR_NON_TWAD                 ='T'\n" + 
            	            "        AND doc_type                        IN ('SC')\n" + 
            	            "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            	            "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "          CASHBOOK_YEAR,\n" + 
            	      //      "          Cashbook_Month,\n" + 
            	            "          Account_No\n" + 
            	            "        )B\n" + 
            	            "      ON A.Acc_U_Id3   =B.Acc_U_Id3\n" + 
            	            "      AND A.acc_off_id3=B.acc_off_id3\n" + 
            	            "      AND A.Csh_Bk_Yr3 =B.Csh_Bk_Yr3\n" + 
            	            "      AND A.Acc_No3    =B.Acc_No3\n" + 
            	            
            	            
   "      LEFT OUTER JOIN\n" + 
   "      (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
   "        ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
   "        CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
   "        Account_No                                   As Acc_No3,\n" + 
   "        DECODE(SUM(DR_AMOUNT),NULL,0,SUM(DR_AMOUNT)) AS IBT_amt\n" + 
   "      FROM FAS_BRS_TRANSACTION\n" + 
   "      WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
            	            "        AND accounting_for_office_id         =" +cboOffice_code+ 
            	            "        AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
            	            "        AND ACCOUNT_NO                       = " +cmbBankAccNo+ 
            	            "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+
   "      And Twad_Or_Non_Twad                 ='T'\n" + 
   "      AND doc_type                        IN ('IBT')\n" + 
   "      GROUP BY ACCOUNTING_UNIT_ID,\n" + 
   "        ACCOUNTING_FOR_OFFICE_ID,\n" + 
   "        CASHBOOK_YEAR,\n" + 
   "        Account_No\n" + 
   "      )c\n" + 
   "      ON A.Acc_U_Id3   =c.Acc_U_Id3\n" + 
   "      AND A.acc_off_id3=c.acc_off_id3\n" + 
   "      And A.Csh_Bk_Yr3 =C.Csh_Bk_Yr3\n" + 
   "      AND A.Acc_No3    =c.Acc_No3\n" + 
            	            
            	             
            	            "      ) group by acc_u_id3,acc_off_id3,acc_no3\n" + 
            	            "    )y ON x.acc_u_id =y.acc_u_id3\n" + 
            	            "  AND x.acc_off_id   = y.acc_off_id3\n" + 
            	            "  AND x.csh_bk_yr    = y.csh_bk_yr3\n" + 
            	            "  AND x.csh_bk_mnth  = y.csh_bk_mnth3\n" + 
            	            "  AND x.acc_no       = y.acc_no3\n" + 
            	            "  LEFT OUTER JOIN\n" + 
            	            "    (SELECT acc_u_id4,\n" + 
            	            "      acc_off_id4,\n" + 
            	            "      Acc_No4,\n" + 
            	            "      SUM(A_6a) AS A_6a\n" + 
            	            "    FROM (\n" + 
            	            "      (\n" + 
            	            "      SELECT acc_u_id4,\n" + 
            	            "  acc_off_id4,\n" + 
            	            "  acc_no4,\n" + 
            	            "  SUM(A_6a) AS A_6a\n" + 
            	            "FROM\n" + 
            	            "  (SELECT ACCOUNTING_UNIT_ID AS acc_u_id4,\n" + 
            	            "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
            	            "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
            	            "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
            	            "    ACCOUNT_NO               AS acc_no4,\n" + 
            	            "    SUM(DR_AMOUNT)           AS A_6a\n" + 
            	            "  FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
            	            "  WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
            	            "  AND Accounting_For_Office_Id=" +cboOffice_code+ 
            	            " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            	            "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            	           // "  AND doc_type                ='P' \n" +
            	            "  AND doc_type                in ('P','FT from Office') \n" +
            	            //add Lakshmi 19Nov13
            	        //    " and twad_or_non_twad='T' \n" + 
            	            "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            	            "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "    CASHBOOK_YEAR,\n" + 
            	            "    CASHBOOK_MONTH,\n" + 
            	            "    ACCOUNT_NO\n" + 
            	            "  UNION ALL\n" + 
            	            "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
            	            "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
            	            "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
            	            "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
            	            "    ACCOUNT_NO               AS acc_no4,\n" + 
            	            "    SUM(CR_AMOUNT)           AS A_6a\n" + 
            	            "  FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
            	            "  WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
            	            "  AND Accounting_For_Office_Id=" +cboOffice_code+ 
            	            " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            	            "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            	          
            	            //Joann Change on 30 JAn 2015
            	       "  AND doc_type           in      ('IBT','SC')\n" +    
            	            //  "  AND doc_type                ='SC'\n" + 
            	            //add Lakshmi 19Nov13
            	         //   " and twad_or_non_twad='T' \n" + 
            	            "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            	            "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "    CASHBOOK_YEAR,\n" + 
            	            "    CASHBOOK_MONTH,\n" + 
            	            "    ACCOUNT_NO\n" + 
            	            "  union all\n" + 
            	            "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
            	            "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
            	            "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
            	            "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
            	            "    ACCOUNT_NO               AS acc_no4,\n" + 
            	            "    SUM(DR_AMOUNT)           AS A_6a\n" + 
            	            "  FROM FAS_BRS_TRANSACTION\n" + 
            	            "  WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
            	            "  AND Accounting_For_Office_Id         =" +cboOffice_code+ 
            	            "  and PASSBOOK_DATE>('"+totalyear+"')\n" +
            	            //changes on 17mar2014 by dhanapradha
            	         //   "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
            	            "  AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            	            "  AND Account_No                       =" +cmbBankAccNo+ 
            	            "  AND doc_type                        IN ('IBT')\n" + 
            	            //add Lakshmi 19Nov13
            	          //  " and twad_or_non_twad='T' \n" + 
            	            "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            	            "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "    CASHBOOK_YEAR,\n" + 
            	            "    CASHBOOK_MONTH,\n" + 
            	            "    ACCOUNT_NO\n" + 
            	            
            	            
   				/*
				 * " union all SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" +
				 * "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" +
				 * "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" +
				 * "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" +
				 * "    ACCOUNT_NO               AS acc_no4,\n" +
				 * "    SUM(DR_AMOUNT)           AS A_6a\n" +
				 * "  FROM FAS_BRS_TRANSACTION\n" +
				 * "  WHERE accounting_unit_id             =" +cboAcc_UnitCode+
				 * "  AND Accounting_For_Office_Id         =" +cboOffice_code+
				 * "  and PASSBOOK_DATE>('"+totalyear+"')\n" + //changes on
				 * 17mar2014 by dhanapradha //
				 * "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"
				 * +totalyear+"')\n" +
				 * "  AND ((cashbook_year                     <"
				 * +cboCashBook_Year
				 * +" and cashbook_month<=12) or (cashbook_year="
				 * +cboCashBook_Year
				 * +" and cashbook_month<="+cboCashBook_Month+"))\n" +
				 * "  AND Account_No                       =" +cmbBankAccNo+ //
				 * " AND EXTRACT(YEAR FROM DOC_DATE)  <> "
				 * +cboCashBook_Year+"        AND extract(MONTH FROM doc_date) <> "
				 * +cboCashBook_Month+
				 * "  AND doc_type                        IN ('P')\n" + //add
				 * Lakshmi 19Nov13 // " and twad_or_non_twad='T' \n" +
				 * "  GROUP BY ACCOUNTING_UNIT_ID,\n" +
				 * "    ACCOUNTING_FOR_OFFICE_ID,\n" + "    CASHBOOK_YEAR,\n" +
				 * "    CASHBOOK_MONTH,\n" + "    ACCOUNT_NO\n" + ///joan added
				 * 
				 * 
				 * "  union all\n" +
				 * "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" +
				 * "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" +
				 * "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" +
				 * "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" +
				 * "    ACCOUNT_NO               AS acc_no4,\n" +
				 * "    SUM(DR_AMOUNT)           AS A_6a\n" +
				 * "  FROM FAS_BRS_TRANSACTION\n" +
				 * "  WHERE accounting_unit_id             =" +cboAcc_UnitCode+
				 * "  AND Accounting_For_Office_Id         =" +cboOffice_code+
				 * "  and PASSBOOK_DATE>('"+totalyear+"')\n" +
				 * 
				 * //
				 * "  AND ((cashbook_year                     <"+cboCashBook_Year
				 * +
				 * " and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year
				 * +" and cashbook_month<="+cboCashBook_Month+"))\n" +
				 * " AND EXTRACT(YEAR FROM DOC_DATE) ="
				 * +cboCashBook_Year+"        AND extract(MONTH FROM doc_date)="
				 * +cboCashBook_Month+
				 * "  AND Account_No                       =" +cmbBankAccNo+ //
				 * "   and CASHBOOK_MONTH <> 7 "
				 * " AND doc_type                        IN ('P')\n" + //add
				 * Lakshmi 19Nov13 // " and twad_or_non_twad='T' \n" +
				 * "  GROUP BY ACCOUNTING_UNIT_ID,\n" +
				 * "    ACCOUNTING_FOR_OFFICE_ID,\n" + "    CASHBOOK_YEAR,\n" +
				 * "    CASHBOOK_MONTH,\n" + "    ACCOUNT_NO\n" +
				 */
				/*
				 * //*** Joan added on 15 May 2015
				 */
				/* " UNION ALL \n"+
				"  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n "
				+ "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n "
				+ "  CASHBOOK_YEAR            AS csh_bk_yr4,\n "
				+ "  CASHBOOK_MONTH           AS csh_bk_mnth4,\n "
				+ "  ACCOUNT_NO               AS acc_no4,\n "
				+ "  SUM(DR_AMOUNT)           AS A_6a \n "
			
				+ " FROM FAS_BRS_TRANSACTION \n"
				+ " WHERE accounting_unit_id             =" +cboAcc_UnitCode
				+ " \nAND Accounting_For_Office_Id     =" +cboOffice_code
				+ "\n AND PASSBOOK_DATE               >('"+totalyear+"') "
				+ " \n AND Account_No                  =" +cmbBankAccNo
				+ " \nAND ((((cashbook_year           <"+cboCashBook_Year
				+ "\n AND cashbook_month             <="+cboCashBook_Month+") "
				+ "\n OR (cashbook_year               ="+cboCashBook_Year
				+ "\n AND cashbook_month             <="+cboCashBook_Month+")) "
				+ " \nAND EXTRACT(YEAR FROM DOC_DATE) ="+cboCashBook_Year
				+ "\n AND extract(MONTH FROM doc_date)="+cboCashBook_Month+" ) "
				+ "\n OR (cashbook_year               ="+cboCashBook_Year
				+ "\n AND cashbook_month              >  "+cboCashBook_Month
				+ "\n AND EXTRACT(YEAR FROM DOC_DATE) = "+cboCashBook_Year
				+ "\n AND extract(MONTH FROM doc_date)="+cboCashBook_Month+" )"
						+ "\n OR (cashbook_year                 ="+cboCashBook_Year
						+ "\n AND cashbook_month                ="+cboCashBook_Month
						+ "\n AND doc_date < '"+totalyear+"' ) ) "
				+ "\n AND doc_type                   IN ('P') "
				+ "\n GROUP BY ACCOUNTING_UNIT_ID, "
				+ "\n  ACCOUNTING_FOR_OFFICE_ID, "
				+ " \n CASHBOOK_YEAR, "
				+ " \n CASHBOOK_MONTH, "
				+ "\n  ACCOUNT_NO \n "*/
			" union all "+
			  "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" +
			  "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" +
			 "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" +
			  "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" +
			  "    ACCOUNT_NO               AS acc_no4,\n" +
			  "    SUM(DR_AMOUNT)           AS A_6a\n" +
			  "  FROM FAS_BRS_TRANSACTION\n" +
			  "  WHERE accounting_unit_id             =" +cboAcc_UnitCode+
			  "  AND Accounting_For_Office_Id         =" +cboOffice_code+
			  "  and PASSBOOK_DATE > ('"+totalyear+"')\n" +
				 "\n AND doc_type                   IN ('P') "+
 " AND  extract (MONTH FROM doc_DATE)     <= "+cboCashBook_Month+
 " AND extract (YEAR FROM doc_DATE)        =  "+cboCashBook_Year+
	 "\n GROUP BY ACCOUNTING_UNIT_ID, "
				+ "\n  ACCOUNTING_FOR_OFFICE_ID, "
				+ " \n CASHBOOK_YEAR, "
				+ " \n CASHBOOK_MONTH, "
				+ "\n  ACCOUNT_NO \n "+
				"  UNION ALL\n"
				+ "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n"
				+ "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n"
				+ "    CASHBOOK_YEAR            AS csh_bk_yr4,\n"
				+ "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n"
				+ "    ACCOUNT_NO               AS acc_no4,\n"
				+ "    SUM(CR_AMOUNT)           AS A_6a\n"
				+ "  FROM FAS_BRS_TRANSACTION\n"
				+ "  WHERE accounting_unit_id             ="
				+ cboAcc_UnitCode
				+ "  AND Accounting_For_Office_Id         ="
				+ cboOffice_code
				+ "   and PASSBOOK_DATE>('"
				+ totalyear
				+ "')\n"
				+
				// changes on 17mar2014 by dhanapradha
				// "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n"
				// +
				"  AND ((cashbook_year                     <"
				+ cboCashBook_Year
				+ " and cashbook_month<=12) or (cashbook_year="
				+ cboCashBook_Year
				+ " and cashbook_month<="
				+ cboCashBook_Month
				+ "))\n"
				+ "  AND Account_No                       ="
				+ cmbBankAccNo
				+ "  AND doc_type                        IN ('SC')\n"
				+
				// add Lakshmi 19Nov13
				// " and twad_or_non_twad='T' \n" +
				"  GROUP BY ACCOUNTING_UNIT_ID,\n"
				+ "    ACCOUNTING_FOR_OFFICE_ID,\n"
				+ "    CASHBOOK_YEAR,\n"
				+ "    CASHBOOK_MONTH,\n"
				+ "    ACCOUNT_NO\n"
				+
            	            "  )\n" + 
            	            "GROUP BY acc_u_id4,\n" + 
            	            "  Acc_Off_Id4,\n" + 
            	            "  acc_no4\n" + 
            	            "      )\n" + 
            	            "     )\n" + 
            	            "    GROUP BY Acc_U_Id4,\n" + 
            	            "      Acc_Off_Id4,\n" + 
            	            "      Acc_No4\n" + 
            	            "    )g\n" + 
            	            "  ON x.acc_u_id    =g.acc_u_id4\n" + 
            	            "  AND x.acc_off_id = g.acc_off_id4\n" + 
            	            "  AND x.acc_no     = g.acc_no4\n" + 
            	            "  LEFT OUTER JOIN\n" + 
            	            "    (SELECT ACCOUNTING_UNIT_ID AS acc_u_id5,\n" + 
            	            "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
            	            "      CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
            	            "      CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
            	            "      ACCOUNT_NO               AS acc_no5,\n" + 
            	            "      SUM(CR_AMOUNT-DR_AMOUNT) AS A_6b\n" + 
            	            "    FROM FAS_BRS_TRANSACTION\n" + 
            	            "    WHERE accounting_unit_id    =" +cboAcc_UnitCode+ 
            	            "    AND accounting_for_office_id=" +cboOffice_code+ 
            	            "    AND cashbook_year           =" +cboCashBook_Year+ 
            	            "    AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            	            "    AND cashbook_month          = " +cboCashBook_Month+ 
            	            "    AND TWAD_OR_NON_TWAD        ='NT'\n" + 
            	            "    GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            	            "      ACCOUNTING_FOR_OFFICE_ID,\n" + 
            	            "      CASHBOOK_YEAR,\n" + 
            	            "      CASHBOOK_MONTH,\n" + 
            	            "      ACCOUNT_NO\n" + 
            	            "    )g1\n" + 
            	            "  ON x.acc_u_id       =g1.acc_u_id5\n" + 
            	            "  AND x.acc_off_id    = g1.acc_off_id5\n" + 
            	            "  AND x.csh_bk_yr     = g1.csh_bk_yr5\n" + 
            	            "  AND x.csh_bk_mnth   = g1.csh_bk_mnth5\n" + 
            	            "  AND x.acc_no        = g1.acc_no5\n" + 
            	            "  )YY ON XX.acc_u_id6 = YY.acc_u_id\n" + 
            	            " AND XX.acc_off_id6    = YY.acc_off_id\n" + 
            	            " AND XX.csh_bk_yr6     = YY.csh_bk_yr\n" + 
            	            " AND Xx.Csh_Bk_Mnth6   = Yy.Csh_Bk_Mnth\n" + 
            	            " AND XX.acc_no6        = YY.acc_no)" ;
            		
            //}
            System.out.println("sql_rep::"+sql_rep);
            
		    File reportFile = null;
			Map map = null;
			map = new HashMap();
			map.put("opr_node", opr_node);
			map.put("bank", bankName);
			map.put("branch", branchName);
			String sql="";
			try {
				System.out.println("calling servlet...");
				if(cmd.equalsIgnoreCase("Brs_Freezed")){
					sql="SELECT p1.ACCOUNTING_UNIT_ID, " +
					"  unit.ACCOUNTING_UNIT_NAME, " +
					"  office.OFFICE_NAME, " +
					"  p1.ACCOUNTING_FOR_OFFICE_ID, " +
					"  p1.PASS_SHEET_YEAR, " +
					"  p1.PASS_SHEET_MONTH, " +
					"  TWAD_OR_NON_TWAD, " +
					"  p1.ACCOUNT_NO, " +
					"  p1.BANK_ID, " +
					"  (SELECT BANK_NAME FROM FAS_BANK_LIST Bname WHERE p1.BANK_ID=Bname.BANK_ID " +
					"  )AS BANK_NAME, " +
					"  (SELECT BRANCH_NAME " +
					"  FROM FAS_MST_BANK_BRANCHES branch " +
					"  WHERE p1.BANK_ID=branch.BANK_ID " +
					"  AND p1.BRANCH_ID=branch.BRANCH_ID " +
					"  )AS BRANCH_NAME, " +
					"  (SELECT PASSBOOK_BALANCE " +
					"  FROM FAS_BRS_MASTER pbk " +
					"  WHERE pbk.ACCOUNTING_UNIT_ID    =p1.ACCOUNTING_UNIT_ID " +
					"  AND pbk.ACCOUNTING_FOR_OFFICE_ID=p1.ACCOUNTING_FOR_OFFICE_ID " +
					"  AND pbk.CASHBOOK_YEAR           =p1.PASS_SHEET_YEAR " +
					"  AND pbk.CASHBOOK_MONTH          =p1.PASS_SHEET_MONTH " +
					"  AND pbk.ACCOUNT_NO              =p1.ACCOUNT_NO " +
					"  )AS PASSBOOK_BALANCE, " +
					"  S1, " +
					"  S2A, " +
					"  S2B, " +
					"  S2C, " +
					"  S2D, " +
					"  S2E, " +
					"  S3, " +
					"  S4A, " +
					"  S4B, " +
					"  S4C,S4D,S4E,S4_FINAL, " +
					"  S5A,S4F,S5, "+
					"  S5B,S5A_I,S5_A_DIFFERENCE,CLOSING_BAL " +
					" FROM FAS_BRS_PART_2A p1, " +
					"  FAS_MST_ACCT_UNITS unit, " +
					"  COM_MST_OFFICES office " +
					" WHERE p1.ACCOUNTING_UNIT_ID    =  " +cboAcc_UnitCode+
					" AND ACCOUNTING_FOR_OFFICE_ID   =  " +cboOffice_code+
					" AND PASS_SHEET_YEAR            =  " +cboCashBook_Year+
					" AND PASS_SHEET_MONTH           =  " +cboCashBook_Month+
					" AND ACCOUNT_NO                 =  " +cmbBankAccNo+
					" AND p1.ACCOUNTING_UNIT_ID      =unit.ACCOUNTING_UNIT_ID " +
					" AND p1.ACCOUNTING_FOR_OFFICE_ID=office.OFFICE_ID";
					map.put("sql", sql);

					if(hid.equalsIgnoreCase("old")){
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Part_Jasper/BRS_Report_2_Report.jasper"));}
					else
					{
						reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Part_Jasper/FDW_PDF_BRS_2A new.jasper"));	
					}
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				

				map.put("UnitId", cboAcc_UnitCode);
				map.put("OfficeId", cboOffice_code);
				map.put("cbyear", cboCashBook_Year);
				map.put("cbmonth", cboCashBook_Month);
				map.put("accNo", cmbBankAccNo);
				map.put("month", month);
	            map.put("four_b", i2);//total_i//i2
	            System.out.println("amount"+i2);
	            map.put("four_c", ii);
	            map.put("UnitName","( "+cboAcc_UnitCode+" ) "+ UnitName);
	            map.put("excess_db",i3);
	            map.put("last_date_one", totalyear);
	          
	            System.out.println(""+map);
	          
	            
	           
	            
			    
			    
			    
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				System.out.println("upto");
				String rtype = "PDF";// request.getParameter("cmbReportType");
				System.out.println(rtype);
				if (rtype.equalsIgnoreCase("PDF")) {
					System.out.println(rtype);
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					System.out.println("Length  " + buf.length);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					// response.setHeader("content-disposition",
					// "inline;filename=OpenActionItems.pdf");
					// response.setContentType("application/force-download");

					response.setHeader("Content-Disposition",
							"attachment;filename=\"BRS_Part2A.pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				  }
				}
				else if(cmd.equalsIgnoreCase("printFunc")){
					System.out.println("print funct");
					
					try{
						if(hid.equalsIgnoreCase("old")){
							System.out.println("coming here for old option Report");
					reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_2.jasper"));}
						else{
							System.out.println("coming here for other report SSSS");
							reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Part_Jasper/BRS_fdw_2A.jasper"));
						}
					map.put("sql", sql_rep);
					
					if (!reportFile.exists())
						throw new JRRuntimeException(
								"File J not found. The report design must be compiled first.");

					JasperReport jasperReport = (JasperReport) JRLoader
							.loadObject(reportFile.getPath());
					

					map.put("UnitId", cboAcc_UnitCode);
					map.put("OfficeId", cboOffice_code);
					map.put("cbyear", cboCashBook_Year);
					map.put("cbmonth", cboCashBook_Month);
					map.put("accNo", cmbBankAccNo);
					map.put("month", month);
		            map.put("four_b", i2);//total_i//i2
		            System.out.println("amount"+i2);
		            map.put("four_c", ii);
		            map.put("UnitName",UnitName);
		            map.put("excess_db",i3);
		            map.put("last_date_one", totalyear);
		          
		            map.put("bank_credit_bdecimal", bank_credit_bdecimal);
		            map.put("i4_four_new", i4_four);
		            map.put("five_a1", i2_five);
		            map.put("five_a1_ct_mn", i2_five_ct_month);
		            
		            System.out.println("Values passing to repo SSS***"+map);
		            
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							jasperReport, map, con);
					System.out.println("upto");
					String rtype = "PDF";// request.getParameter("cmbReportType");
					System.out.println(rtype);
					if (rtype.equalsIgnoreCase("PDF")) {
						System.out.println(rtype);
						byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
						System.out.println("Length  " + buf.length);
						response.setContentType("application/pdf");
						response.setContentLength(buf.length);
						

						response.setHeader("Content-Disposition",
								"attachment;filename=\"BRS_Part2A.pdf\"");
						OutputStream out = response.getOutputStream();
						out.write(buf, 0, buf.length);
						out.close();
					}
					}
					catch (Exception ex) {
						String connectMsg = "Could not create the report "+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
						System.out.println(connectMsg);
					}
					}
				
				
				else if(cmd.equalsIgnoreCase("f_brs_check")){
					
					
					response.setContentType(CONTENT_TYPE);
					xml="<response><command>f_brs_check</command>";
					
					
					PrintWriter output = response.getWriter();
					d=i4_four.doubleValue();

					int jk=0;
					int insertCount=0;
					int checkfre=0;
					try{
			               PreparedStatement pss=con.prepareStatement(sql_rep);
			               ResultSet rss=pss.executeQuery();
			               
			               
				               if(rss.next())
				               {
				            	   System.out.println("four_c as II "+ii);
				            	   System.out.println("excess_db as i3 "+i3);
				            	   System.out.println("five_a1_ct_mn as i2_five_ct_month "+i2_five_ct_month);
				            	   
				            	   double tot1_3=rss.getDouble("A_3");
				            	   System.out.println("tot1_3  "+tot1_3);
				            	//   double tot2=   rss.getDouble("A_4")+four_c+excess_db+five_a1_ct_mn
				            	   double tot2=   rss.getDouble("A_4")+Double.parseDouble(ii.toString())+Double.parseDouble(i3.toString())+Double.parseDouble(i2_five_ct_month.toString());
				            	   System.out.println("tot2  "+tot2);
				            	   //  double tot3=$P{bank_credit_bdecimal}.add($P{i4_four_new})
				            	   double tot3=Double.parseDouble(bank_credit_bdecimal.toString())+Double.parseDouble(i4_four.toString());
				            	   System.out.println("tot3  "+tot3);
				            	   double tot1_4=tot2-tot3;
				            	   System.out.println("tot1_4  "+tot1_4);
				            	   
				            	   double tot1_5_final=tot1_3-tot1_4;
				            	   System.out.println("tot1_5_final  "+tot1_5_final);
				            	   
				            	 //  double tot4=rss.getDouble("A_6A")-$P{five_a1})
				            	   double tot4=rss.getDouble("A_6A")-five_a1;
				            	   System.out.println("tot4  "+tot4);
				            	   //$V{five_minus_one}.subtract( $P{four_b} )
				            	   double tot5=tot4-Double.parseDouble(i2.toString());
				            	   System.out.println("tot5  "+tot5);
				            	   double final_val=tot1_5_final-tot5;
				            	   //changed by sathya on 16Feb2016
				            	   DecimalFormat df = new DecimalFormat("#");
				                   df.setMaximumFractionDigits(3);
				                  
				                		double final_val_new=Double.parseDouble(df.format(final_val));
				            
				            	   System.out.println("final_val"+final_val_new);
				           	            		

				            	   
				            	   if(final_val_new==0.0){
				            	   //Lakshmi 7Nov13 
				            	   String ss="select 'X' from FAS_BRS_PART_2A where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo;
				            	  PreparedStatement psta=con.prepareStatement(ss);
				            	  // insertCount= psta.executeUpdate();
				            	 // System.out.println("ss "+ss);
				            	   ResultSet rsss=psta.executeQuery();
				            	   if(rsss.next()){
				            		   checkfre=1;  
				            	   }else{
				            		   System.out.println("else ");
				                //  if
//				            	  double s4_total=(rss.getDouble("A_4")+four_cAmount+excess_db)-bank_credit; 
//				            	  double s5=(rss.getDouble("A_3"))-(s4_total);
				            	   double s4_total=(rss.getDouble("A_4")+four_cAmount+excess_db+five_a1_ct_month)-(bank_credit+four_e4);
				            	   double s5=(rss.getDouble("A_3"))-(s4_total);
				            	  
				            	   double s5_difference=(rss.getDouble("A_6a")-five_a1);
				            	   //double closingbal=(s5_difference-four_bAmount);
				            	   double closingbal=(s5_difference-four_bAmount);
				            	  
				            	/*
				            	   PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2A (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
				            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2A,S2B,S2C,S2D,S2E,S3,S4A,S4B,S4C,S5A,S5B," +
				            	  "UPDATED_BY_USER_ID,UPDATED_DATE,s5,BANK_ID,BRANCH_ID,S4D,S4_FINAL,CLOSING_BAL,S4E,S4F) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				            	  
				            	
				            	 */
				            	  
				            	   }
				            	   if(checkfre==1)
					               {
					            	   con.commit();
										con.setAutoCommit(true);
//										sendMessage(response,"Already Part-2A Frozen  ","ok");
										xml=xml+"<flag>Already_Frozen</flag>";
					               }
				            	   
				               }else{
				            	   con.commit();
									con.setAutoCommit(true);
//									sendMessage(response,"Check the Part2A Report Caption"+"\""+"Difference between 5 and (5a-5b) "+"\""+" value is Zero ... ","ok"); 
									xml=xml+"<flag>Check the Part2A Report</flag>";
				               
				               }
				
				               }			               
	                }
	                catch(Exception ee)
	                {
	                	System.out.println("exception in fetching query::::"+ee);
	                }     
			xml = xml + "</response>";
			output.println(xml); 
			System.out.println("xml=====>"+xml);
		}

				else if(cmd.equalsIgnoreCase("f_brs")){
					 
					response.setContentType(CONTENT_TYPE);
					
					PrintWriter output = response.getWriter();
					d=i4_four.doubleValue();

					int jk=0;
					int insertCount=0;
					int checkfre=0;
					try{
			               PreparedStatement pss=con.prepareStatement(sql_rep);
			               ResultSet rss=pss.executeQuery();
			               
			               
				               if(rss.next())
				               {
				            	   System.out.println("four_c as II "+ii);
				            	   System.out.println("excess_db as i3 "+i3);
				            	   System.out.println("five_a1_ct_mn as i2_five_ct_month "+i2_five_ct_month);
				            	   
				            	   double tot1_3=rss.getDouble("A_3");
				            	   System.out.println("tot1_3  "+tot1_3);
				            	//   double tot2=   rss.getDouble("A_4")+four_c+excess_db+five_a1_ct_mn
				            	   double tot2=   rss.getDouble("A_4")+Double.parseDouble(ii.toString())+Double.parseDouble(i3.toString())+Double.parseDouble(i2_five_ct_month.toString());
				            	   System.out.println("tot2  "+tot2);
				            	   //  double tot3=$P{bank_credit_bdecimal}.add($P{i4_four_new})
				            	   double tot3=Double.parseDouble(bank_credit_bdecimal.toString())+Double.parseDouble(i4_four.toString());
				            	   System.out.println("tot3  "+tot3);
				            	   double tot1_4=tot2-tot3;
				            	   System.out.println("tot1_4  "+tot1_4);
				            	   
				            	   double tot1_5_final=tot1_3-tot1_4;
				            	   System.out.println("tot1_5_final  "+tot1_5_final);
				            	   
				            	 //  double tot4=rss.getDouble("A_6A")-$P{five_a1})
				            	   double tot4=rss.getDouble("A_6A")-five_a1;
				            	   System.out.println("tot4  "+tot4);
				            	   //$V{five_minus_one}.subtract( $P{four_b} )
				            	   double tot5=tot4-Double.parseDouble(i2.toString());
				            	   System.out.println("tot5  "+tot5);
				            	   double final_val=tot1_5_final-tot5;
				            	   //changed by sathya on 16Feb2016
				            	   DecimalFormat df = new DecimalFormat("#");
				                   df.setMaximumFractionDigits(3);
				                  
				                		double final_val_new=Double.parseDouble(df.format(final_val));
				            
				            	   System.out.println("final_val"+final_val_new);
				           	            		

				            	   
				            	   if(final_val_new==0.0){
				            	   //Lakshmi 7Nov13 
				            	   String ss="select 'X' from FAS_BRS_PART_2A where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo;
				            	  PreparedStatement psta=con.prepareStatement(ss);
				            	  // insertCount= psta.executeUpdate();
				            	 // System.out.println("ss "+ss);
				            	   ResultSet rsss=psta.executeQuery();
				            	   if(rsss.next()){
				            		   checkfre=1;  
				            	   }else{
				            		   System.out.println("else ");
				                //  if
//				            	  double s4_total=(rss.getDouble("A_4")+four_cAmount+excess_db)-bank_credit; 
//				            	  double s5=(rss.getDouble("A_3"))-(s4_total);
				            	   double s4_total=(rss.getDouble("A_4")+four_cAmount+excess_db+five_a1_ct_month)-(bank_credit+four_e4);
				            	   double s5=(rss.getDouble("A_3"))-(s4_total);
				            	  
				            	   double s5_difference=(rss.getDouble("A_6a")-five_a1);
				            	   //double closingbal=(s5_difference-four_bAmount);
				            	   double closingbal=(s5_difference-four_bAmount);
				            	  
				            	/*
				            	   PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2A (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
				            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2A,S2B,S2C,S2D,S2E,S3,S4A,S4B,S4C,S5A,S5B," +
				            	  "UPDATED_BY_USER_ID,UPDATED_DATE,s5,BANK_ID,BRANCH_ID,S4D,S4_FINAL,CLOSING_BAL,S4E,S4F) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				            	  
				            	
				            	 */
				            	   PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2A (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
					            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2A,S2B,S2C,S2D,S2E,S3,S4A,S4B,S4C,S4D,S4E,S4F,S4_FINAL," +
					            	   		" S5,S5A,S5A_I,S5_A_DIFFERENCE,S5B,CLOSING_BAL," +
					            	  "UPDATED_BY_USER_ID,UPDATED_DATE,BANK_ID,BRANCH_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					            	  
				            	  
				            	   pss1.setInt(1,cboAcc_UnitCode);
				            	   pss1.setInt(2,cboOffice_code);
				            	   pss1.setInt(3,cboCashBook_Year);
				            	   pss1.setInt(4,cboCashBook_Month);
				            	   pss1.setLong(5,cmbBankAccNo);
				            	   pss1.setDouble(6,rss.getDouble("A_1"));
				            	   pss1.setDouble(7,rss.getDouble("A_2a"));
				            	   pss1.setDouble(8,rss.getDouble("A_2b"));
				            	   pss1.setDouble(9,rss.getDouble("A_2c"));
				            	   pss1.setDouble(10,rss.getDouble("A_2d"));
				            	   
				            	   pss1.setDouble(11,rss.getDouble("A_2e"));
				            	   pss1.setDouble(12,rss.getDouble("A_3"));
				            	   pss1.setDouble(13,rss.getDouble("A_4"));//s4a
				            	   pss1.setDouble(14,excess_db);//s4b
				            	   pss1.setDouble(15,four_cAmount);//s4c
				            	   pss1.setDouble(16,bank_credit);//s4d
				            	   pss1.setDouble(17,d);//s4e
				            	   pss1.setDouble(18,five_a1_ct_month);//s4f
				            	   pss1.setDouble(19,s4_total);//s4 final
				            	   pss1.setDouble(20,s5);//s5
				            	   pss1.setDouble(21,rss.getDouble("A_6a"));//s5a
				            	   pss1.setDouble(22,five_a1);//s5a.1
				            	   
				            	    pss1.setDouble(23,s5_difference);   // S5_A_DIFFERENCE
				            	   pss1.setDouble(24,four_bAmount);//s5b
				            	   pss1.setDouble(25,closingbal);
				            	   
				            	   
				            	   pss1.setString(26,update_user);
				                   pss1.setTimestamp(27,ts);
				                
				                   pss1.setInt(28,bank_id);
				                   pss1.setInt(29,branch_id);
				                
				                   jk=pss1.executeUpdate();
				                   System.out.println("value jk:::"+jk);
				            	 
				                   if(jk>0)
					               {
					            	   con.commit();
										con.setAutoCommit(true);
//										sendMessage(response,"Records Inserted Successfully  ","ok");
										xml="<response><command><flag>success</flag></command>";
					               }
					               else
					               {
					            	   con.rollback();
										con.setAutoCommit(true);
//					            	   sendMessage(response,"Records Not Inserted into Part-2a ","ok"); 
										xml="<response><command><flag>failure</flag></command>";
					               }
				            	   }
				            	   if(checkfre==1)
					               {
					            	   con.commit();
										con.setAutoCommit(true);
//										sendMessage(response,"Already Part-2A Frozen  ","ok");
										xml="<response><command><flag>Already_Frozen</flag></command>";
					               }
				            	   
				               }else{
				            	   con.commit();
									con.setAutoCommit(true);
//									sendMessage(response,"Check the Part2A Report Caption"+"\""+"Difference between 5 and (5a-5b) "+"\""+" value is Zero ... ","ok"); 
									xml="<response><command><flag>Check the Part2A Report</flag></command>";
				               
				               }
				            	   /*else{
//Lakshmi 6Nov13
				            	 //  PreparedStatement psta=con.prepareStatement("delete from FAS_BRS_PART_2A where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
				            	//   insertCount= psta.executeUpdate();
				                //  if
//				            	  double s4_total=(rss.getDouble("A_4")+four_cAmount+excess_db)-bank_credit; 
//				            	  double s5=(rss.getDouble("A_3"))-(s4_total);
				            	   double s4_total=(rss.getDouble("A_4")+four_cAmount+excess_db)-(bank_credit+four_e4);
				            	   double s5=(rss.getDouble("A_3"))-(s4_total);
				            	  double closingbal=(rss.getDouble("A_6a")-four_bAmount);
				            	   
				            	  PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2A (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
				            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2A,S2B,S2C,S2D,S2E,S3,S4A,S4B,S4C,S5A,S5B," +
				            	   		"UPDATED_BY_USER_ID,UPDATED_DATE,s5,BANK_ID,BRANCH_ID,S4D,S4_FINAL,CLOSING_BAL,S4E) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				            	  
				            	
				            	//  TWAD_OR_NON_TWAD
				            	  
				            	   pss1.setInt(1,cboAcc_UnitCode);
				            	   pss1.setInt(2,cboOffice_code);
				            	   pss1.setInt(3,cboCashBook_Year);
				            	   pss1.setInt(4,cboCashBook_Month);
				            	   pss1.setLong(5,cmbBankAccNo);
				            	   pss1.setDouble(6,rss.getDouble("A_1"));
				            	   pss1.setDouble(7,rss.getDouble("A_2a"));
				            	   pss1.setDouble(8,rss.getDouble("A_2b"));
				            	   pss1.setDouble(9,rss.getDouble("A_2c"));
				            	   pss1.setDouble(10,rss.getDouble("A_2d"));
				            	   
				            	   pss1.setDouble(11,rss.getDouble("A_2e"));
				            	   pss1.setDouble(12,rss.getDouble("A_3"));
				            	   pss1.setDouble(13,rss.getDouble("A_4"));//s4a
				            	   pss1.setDouble(14,excess_db);
				            	   pss1.setDouble(15,four_cAmount);
				            	   pss1.setDouble(16,rss.getDouble("A_6a"));
				            	   pss1.setDouble(17,four_bAmount);
				            
				            	   pss1.setString(18,update_user);
				                   pss1.setTimestamp(19,ts);
				                   pss1.setDouble(20,s5);//s5
				                   pss1.setInt(21,bank_id);
				                   pss1.setInt(22,branch_id);
				                   pss1.setDouble(23,bank_credit);
				            	   pss1.setDouble(24,s4_total);
				            	   pss1.setDouble(25,closingbal);
				            	   pss1.setDouble(26,d);
				            	   
				                   jk=pss1.executeUpdate();
				                   System.out.println("value jk:::"+jk);
				            	 //  pss1.setInt(15,rss.getInt("total2"));
				                   if(jk>0)
					               {
					            	   con.commit();
										con.setAutoCommit(true);
										sendMessage(response,"Records Inserted Successfully  ","ok");
					               }
					               else
					               {
					            	   con.rollback();
										con.setAutoCommit(true);
					            	   sendMessage(response,"Records Not Inserted into Part-2a ","ok"); 
					               }
				               }*/
				               /*if(jk>0)
				               {
				            	   con.commit();
									con.setAutoCommit(true);
									sendMessage(response,"Records Inserted Successfully  ","ok");
				               }
				               else
				               {
				            	   con.rollback();
									con.setAutoCommit(true);
				            	   sendMessage(response,"Records Not Inserted into Part-2a ","ok"); 
				               }
				            */
				    //         System.out.println("checkfre =="+checkfre);  
				              /* if(checkfre==1)
				               {
				            	   con.commit();
									con.setAutoCommit(true);
									sendMessage(response,"Already Part-2A Frozen  ","ok");
				               }*/	}			               
			                }
			                catch(Exception ee)
			                {
			                	System.out.println("exception in fetching query::::"+ee);
			                }     
					xml = xml + "</response>";
					output.println(xml); 
					System.out.println("xml=====>"+xml);
				}
		} 
			catch (Exception ex) {
				System.out.println("exception in insertion::::"+ex);
	        	
				sendMessage(response,"Records Not Inserted ............ "+ ex, "ok");
		}

	}
	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
