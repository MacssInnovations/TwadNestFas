package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class AA52_Register_OB_Edit
 */
public class AA52_Register_OB_Edit extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AA52_Register_OB_Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

String qry1="",insert_qry="",del_qry="",update_sel_qry="";
int k=0,k1=0;
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		 PreparedStatement ps_insert=null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		Date Serveydate=null,auctiondate=null,voucherdate=null,journaldate=null;
		int unitid = 0;
		String unitname = "";
		int accid = 0;
		String[] year1=null;
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
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		
		String strCommand = "",major_code="",minor_code="",cmbFinancialYear="";
		String xml = "";
 String userid ="";int empid =0,cmbAcc_UnitCode=0,cmbOffice_code=0;
		String empName ="";String fin_year="",major_cls_cde="";
		Calendar c1;
         try
         {
             HttpSession session=request.getSession(false);
             if(session==null)
             {
                 System.out.println(request.getContextPath()+"/index.jsp");
                 response.sendRedirect(request.getContextPath()+"/index.jsp");
                 return;
             }
             UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
              userid = (String) session.getAttribute("UserId");
            
               empid = empProfile.getEmployeeId();
              empName = empProfile.getEmployeeName();
             System.out.println("test enpid"+empid+"empname >>> "+empName);
                 
         }catch(Exception e)
         {
         System.out.println("Redirect Error :"+e);
         }
		
		System.out.println("User Id is:" + userid+"emp id"+empid);
		try {
			strCommand = request.getParameter("command");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println(strCommand);
		
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		String qry = "";
		int c=0;
		if (strCommand.equalsIgnoreCase("minor_code")) 
		{
			
			try {
				
				
				major_code=request.getParameter("major_code");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			xml+="<response><flag>minorCode</flag>";
			try{
			qry="select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from  FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+major_code;
		ps=connection.prepareStatement(qry);
		results=ps.executeQuery();	
		while(results.next()){
			xml+="<code>"+results.getString("ASSET_MINOR_CLASS_CODE")+"</code>";
			xml+="<desc>"+results.getString("ASSET_MINOR_CLASS_DESC")+"</desc>";
			c++;
		}
		xml+="<count>"+c+"</count>";
		}catch (Exception e) {
			System.out.println(e);
		}
		xml+="</response>";
		}
		
		if(strCommand.equalsIgnoreCase("get"))
		{
			int no=0;
			try {
				strCommand = request.getParameter("command");
				major_cls_cde=request.getParameter("major_code");
				major_code=request.getParameter("major_cmb");
				//minor_code=request.getParameter("minor_cmb");
				cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
				cmbFinancialYear=request.getParameter("cmbFinancialYear");
				 year1=cmbFinancialYear.split("-");
				 fin_year=year1[0]+"-"+year1[1].substring(2);
				System.out.println("strCommand:-" + strCommand+year1[0]+year1[1]+fin_year);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("tets");
			xml+="<response><flag>get</flag>";
			
			try{
				
				qry="SELECT ACCOUNTING_UNIT_ID, " +
				"  ACCOUNTING_UNIT_OFFICE_ID, " +
				"  FINANCIAL_YEAR, " +
				"  (SELECT cc.Accounting_Unit_Name " +
				"  FROM fas_mst_acct_units cc " +
				"  WHERE cc.Accounting_Unit_Id=a.ACCOUNTING_UNIT_ID " +
				"  )AS unitname, " +
				"  ACCOUNTING_UNIT_OFFICE_ID, " +
				"  (SELECT oo.OFFICE_NAME " +
				"  FROM COM_MST_OFFICES oo " +
				"  WHERE oo.OFFICE_ID=ACCOUNTING_UNIT_OFFICE_ID " +
				"  )AS officename, " +
				" decode(ASSET_CODE,'',0,ASSET_CODE)as ASSET_CODE, " +
				"  decode(BOOKVALUE,'',0,BOOKVALUE)as BOOKVALUE, " +
				"  decode(APPOR_GRANT,'',0,APPOR_GRANT)as APPOR_GRANT, " +
				"  decode(DEP_DEBIT,'',0,DEP_DEBIT)as DEP_DEBIT, " +
				"  decode(JOURNAL_NO,'',0,JOURNAL_NO)as JOURNAL_NO," +
				"  TO_CHAR(JOURNAL_DATE,'dd/MM/yyyy') AS JOURNAL_DATE, " +
				"  decode(SURVEY_NO,'',0,SURVEY_NO)as SURVEY_NO, " +
				"  TO_CHAR(SURVEY_DATE,'dd/MM/yyyy')  AS SURVEY_DATE, " +
				"  TO_CHAR(AUCTION_DATE,'dd/MM/yyyy') AS AUCTION_DATE, " +
				"  PERSON_NAME, " +
				"  decode(AUCTION_AMOUNT,'',0,AUCTION_AMOUNT)as AUCTION_AMOUNT, " +
				"  decode(CB_VOUCHERNO,'',0,CB_VOUCHERNO)as CB_VOUCHERNO," +
				"  TO_CHAR(CB_VOUCHERDATE,'dd/MM/yyyy')AS CB_VOUCHERDATE, " +
				"  decode(PROFIT,'',0,PROFIT)as PROFIT, " +
				"  decode(LOSS,'',0,LOSS)as LOSS, " +
				"  decode(OFF_DEBIT,'',0,OFF_DEBIT)as OFF_DEBIT, " +
				"  decode(OFF_CREDIT,'',0,OFF_CREDIT)as OFF_CREDIT, " +
				"  REMARKS, " +
				"  UPDATED_BY_USERID, " +
				"  TO_CHAR(UPDATED_DATE,'dd/MM/yyyy')AS UPDATED_DATE, " +
				"  ASSET_MAJOR_CLASS_CODE, " +
				"ASSET_MINOR_CLASS_CODE, " +
				"(SELECT minor.ASSET_MINOR_CLASS_DESC " +
				"FROM FAS_ASSET_MINOR_CLASSIFICATION MINOR " +
				"WHERE MINOR.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND MINOR.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE " +
				") " +
				"AS " +
				"  ASSET_MINOR_CLASS_DESC "+
				" FROM FAS_AA52REGISTER a"+
				" WHERE ACCOUNTING_UNIT_ID     = " +cmbAcc_UnitCode+
				" AND ACCOUNTING_UNIT_OFFICE_ID=" +cmbOffice_code+
				" AND FINANCIAL_YEAR           ='"+fin_year+"' " +
				" AND ASSET_MAJOR_CLASS_CODE   = " +major_code;
				//" AND ASSET_MINOR_CLASS_CODE   ="+minor_code;
				
				insert_qry=	"INSERT " +
				"INTO FAS_AA52REGISTER_CP " +
				"  ( " +
				"    ACCOUNTING_UNIT_ID, " +
				"    ACCOUNTING_UNIT_OFFICE_ID, " +
				"    FINANCIAL_YEAR, " +
				"    UNITNAME, " +
				"    OFFICENAME, " +
				"    ASSET_CODE, " +
				"    BOOKVALUE, " +
				"    APPOR_GRANT, " +
				"    DEP_DEBIT, " +
				"    JOURNAL_NO, " +
				"    JOURNAL_DATE, " +
				"    SURVEY_NO, " +
				"    SURVEY_DATE, " +
				"    AUCTION_DATE, " +
				"    PERSON_NAME, " +
				"    AUCTION_AMOUNT, " +
				"    CB_VOUCHERNO, " +
				"    CB_VOUCHERDATE, " +
				"    PROFIT, " +
				"    LOSS, " +
				"    OFF_DEBIT, " +
				"    OFF_CREDIT, " +
				"    REMARKS, " +
				"    UPDATED_BY_USERID, " +
				"    UPDATED_DATE, " +
				"    ASSET_MAJOR_CLASS_CODE, " +
				"    ASSET_MINOR_CLASS_CODE, " +
				"    ASSET_MINOR_CLASS_DESC " +
				"  ) " +
				"SELECT ACCOUNTING_UNIT_ID, " +
				"  ACCOUNTING_UNIT_OFFICE_ID, " +
				"  FINANCIAL_YEAR, " +
				"  (SELECT cc.Accounting_Unit_Name " +
				"  FROM fas_mst_acct_units cc " +
				"  WHERE cc.Accounting_Unit_Id=a.ACCOUNTING_UNIT_ID " +
				"  )AS unitname, " +
				"  (SELECT oo.OFFICE_NAME " +
				"  FROM COM_MST_OFFICES oo " +
				"  WHERE oo.OFFICE_ID=ACCOUNTING_UNIT_OFFICE_ID " +
				"  )                                   AS officename, " +
				"  DECODE(ASSET_CODE,'',0,ASSET_CODE)  AS ASSET_CODE, " +
				"  DECODE(BOOKVALUE,'',0,BOOKVALUE)    AS BOOKVALUE, " +
				"  DECODE(APPOR_GRANT,'',0,APPOR_GRANT)AS APPOR_GRANT, " +
				"  DECODE(DEP_DEBIT,'',0,DEP_DEBIT)    AS DEP_DEBIT, " +
				"  DECODE(JOURNAL_NO,'',0,JOURNAL_NO)  AS JOURNAL_NO, " +
				//"  -- TO_CHAR(JOURNAL_DATE,'dd/MM/yyyy')  AS " +
				"  JOURNAL_DATE, " +
				"  DECODE(SURVEY_NO,'',0,SURVEY_NO) AS SURVEY_NO, " +
				//"  --TO_CHAR(SURVEY_DATE,'dd/MM/yyyy')   AS " +
				"  SURVEY_DATE, " +
				//"  -- TO_CHAR(AUCTION_DATE,'dd/MM/yyyy')  AS " +
				"  AUCTION_DATE, " +
				"  PERSON_NAME, " +
				"  DECODE(AUCTION_AMOUNT,'',0,AUCTION_AMOUNT)AS AUCTION_AMOUNT, " +
				"  DECODE(CB_VOUCHERNO,'',0,CB_VOUCHERNO)    AS CB_VOUCHERNO, " +
				//"  -- TO_CHAR(CB_VOUCHERDATE,'dd/MM/yyyy')      AS " +
				"  CB_VOUCHERDATE, " +
				"  DECODE(PROFIT,'',0,PROFIT)         AS PROFIT, " +
				"  DECODE(LOSS,'',0,LOSS)             AS LOSS, " +
				"  DECODE(OFF_DEBIT,'',0,OFF_DEBIT)   AS OFF_DEBIT, " +
				"  DECODE(OFF_CREDIT,'',0,OFF_CREDIT) AS OFF_CREDIT, " +
				"  REMARKS, " +
				"  UPDATED_BY_USERID, " +
			//	"  -- TO_CHAR(UPDATED_DATE,'dd/MM/yyyy')AS " +
				"  UPDATED_DATE, " +
				"  ASSET_MAJOR_CLASS_CODE, " +
				"  ASSET_MINOR_CLASS_CODE, " +
				"(SELECT minor.ASSET_MINOR_CLASS_DESC " +
				" FROM FAS_ASSET_MINOR_CLASSIFICATION MINOR " +
				" WHERE MINOR.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND MINOR.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE " +
				") " +
				" AS ASSET_MINOR_CLASS_DESC " +
				" FROM FAS_AA52REGISTER a " +
				" WHERE ACCOUNTING_UNIT_ID     = " +cmbAcc_UnitCode+
				" AND ACCOUNTING_UNIT_OFFICE_ID=" +cmbOffice_code+
				" AND FINANCIAL_YEAR           ='"+fin_year+"' " +
				" AND ASSET_MAJOR_CLASS_CODE   = " +major_code;
				System.out.println(qry);
				System.out.println(insert_qry);
				ps=connection.prepareStatement(qry);
				
				results=ps.executeQuery();	
				while(results.next()){
					//xml+="<leng>";
					xml+="<ACCOUNTING_UNIT_ID>"+results.getString("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";
					xml+="<ACCOUNTING_UNIT_OFFICE_ID>"+results.getString("ACCOUNTING_UNIT_OFFICE_ID")+"</ACCOUNTING_UNIT_OFFICE_ID>";
					xml+="<FINANCIAL_YEAR>"+results.getString("FINANCIAL_YEAR")+"</FINANCIAL_YEAR>";
					xml+="<unitname>"+results.getString("unitname")+"</unitname>";
					xml+="<officename>"+results.getString("officename")+"</officename>";
					xml+="<ASSET_CODE>"+results.getString("ASSET_CODE")+"</ASSET_CODE>";
					xml+="<BOOKVALUE>"+results.getString("BOOKVALUE")+".00"+"</BOOKVALUE>";
					xml+="<APPOR_GRANT>"+results.getString("APPOR_GRANT")+".00"+"</APPOR_GRANT>";
					xml+="<DEP_DEBIT>"+results.getString("DEP_DEBIT")+".00"+"</DEP_DEBIT>";
					xml+="<JOURNAL_NO>"+results.getString("JOURNAL_NO")+"</JOURNAL_NO>";
					xml+="<JOURNAL_DATE>"+results.getString("JOURNAL_DATE")+"</JOURNAL_DATE>";
					xml+="<SURVEY_NO>"+results.getString("SURVEY_NO")+"</SURVEY_NO>";
					xml+="<SURVEY_DATE>"+results.getString("SURVEY_DATE")+"</SURVEY_DATE>";
					xml+="<AUCTION_DATE>"+results.getString("AUCTION_DATE")+"</AUCTION_DATE>";
					xml+="<PERSON_NAME>"+results.getString("PERSON_NAME")+"</PERSON_NAME>";
					xml+="<AUCTION_AMOUNT>"+results.getString("AUCTION_AMOUNT")+".00"+"</AUCTION_AMOUNT>";
					xml+="<CB_VOUCHERNO>"+results.getString("CB_VOUCHERNO")+"</CB_VOUCHERNO>";
					xml+="<CB_VOUCHERDATE>"+results.getString("CB_VOUCHERDATE")+"</CB_VOUCHERDATE>";
					xml+="<PROFIT>"+results.getString("PROFIT")+".00"+"</PROFIT>";
					xml+="<LOSS>"+results.getString("LOSS")+".00"+"</LOSS>";
					xml+="<OFF_DEBIT>"+results.getString("OFF_DEBIT")+".00"+"</OFF_DEBIT>";
					xml+="<OFF_CREDIT>"+results.getString("OFF_CREDIT")+".00"+"</OFF_CREDIT>";
					xml+="<REMARKS>"+results.getString("REMARKS")+"</REMARKS>";
					xml+="<UPDATED_BY_USERID>"+results.getString("UPDATED_BY_USERID")+"</UPDATED_BY_USERID>";
					xml+="<UPDATED_DATE>"+results.getString("UPDATED_DATE")+"</UPDATED_DATE>";		
					xml+="<ASSET_MAJOR_CLASS_CODE>"+results.getString("ASSET_MAJOR_CLASS_CODE")+"</ASSET_MAJOR_CLASS_CODE>";
					xml+="<ASSET_MINOR_CLASS_CODE>"+results.getString("ASSET_MINOR_CLASS_CODE")+"</ASSET_MINOR_CLASS_CODE>";
					xml+="<ASSET_MINOR_CLASS_DESC>"+results.getString("ASSET_MINOR_CLASS_DESC")+"</ASSET_MINOR_CLASS_DESC>";
					//xml+="</leng>";
					
					
						qry1="select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from  FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+major_code;
						ps1=connection.prepareStatement(qry1);
						results2=ps1.executeQuery();
						 
						while(results2.next()){
							 xml=xml+"<min_class_details"+c+">";  
							xml+="<MINOR_CLASS_CODE>"+results2.getInt("ASSET_MINOR_CLASS_CODE")+"</MINOR_CLASS_CODE>";
							xml+="<MINOR_CLASS_DESC>"+results2.getString("ASSET_MINOR_CLASS_DESC")+"</MINOR_CLASS_DESC>";
							xml=xml+"</min_class_details"+c+">";
						}
						
					
					 c++;
				}
				xml+="<count>"+c+"</count>";
				 if(c==0)
                     xml=xml+"<results>NoRecords</results>";
				 else                           
                	 xml=xml+"<results>success</results>";
                 try{
			
			System.out.println(insert_qry);
			 ps_insert=connection.prepareStatement(insert_qry);
			 k=ps_insert.executeUpdate();
			 System.out.println("k>>>"+k);}
                 catch (Exception e) {
					System.out.println(e);
				}
                 if(k==0){
			 xml=xml+"<insert>failure</insert>";}
                 else{
              xml=xml+"<insert>success</insert>";}
			 
			}
           /* ps.close();
            results.close();*/
				
			catch (Exception e) {
				System.out.println(e);
			}
			xml+="</response>";
		}
	
		if(strCommand.equalsIgnoreCase("update")){
			
			System.out.println("tets");
			xml+="<response><flag>update</flag>";	
			try {
				strCommand = request.getParameter("command");
				major_cls_cde=request.getParameter("major_code");
				major_code=request.getParameter("major_cmb");
				//minor_code=request.getParameter("minor_cmb");
				cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
				cmbFinancialYear=request.getParameter("cmbFinancialYear");
				 year1=cmbFinancialYear.split("-");
				 fin_year=year1[0]+"-"+year1[1].substring(2);
				System.out.println("strCommand:-" + strCommand+year1[0]+year1[1]+fin_year);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] assMinorCode=request.getParameterValues("assMinorCode");
			
			String[] BOOKVALUE=request.getParameterValues("BOOKVALUE");
			String[] APPOR_GRANT=request.getParameterValues("APPOR_GRANT");
			String[] DEP_DEBIT=request.getParameterValues("DEP_DEBIT");
			String[] JOURNAL_NO=request.getParameterValues("JOURNAL_NO");
			String[] JOURNAL_DATE=request.getParameterValues("JOURNAL_DATE");
			String[] SURVEY_NO=request.getParameterValues("SURVEY_NO");
			String[] SURVEY_DATE=request.getParameterValues("SURVEY_DATE");
			String[] AUCTION_DATE=request.getParameterValues("AUCTION_DATE");
			String[] PERSON_NAME=request.getParameterValues("PERSON_NAME");
			String[] AUCTION_AMOUNT=request.getParameterValues("AUCTION_AMOUNT");
			String[] CB_VOUCHERNO=request.getParameterValues("CB_VOUCHERNO");
			String[] CB_VOUCHERDATE=request.getParameterValues("CB_VOUCHERDATE");
			String[] PROFIT=request.getParameterValues("PROFIT");
			String[] LOSS=request.getParameterValues("LOSS");
			String[] OFF_DEBIT=request.getParameterValues("OFF_DEBIT");
			String[] OFF_CREDIT=request.getParameterValues("OFF_CREDIT");
			String[] REMARKS=request.getParameterValues("REMARKS");
			String[] ASSETCODE=request.getParameterValues("ASSETCODE");
			System.out.println(BOOKVALUE.length+"length");
			for(int i=0;i<BOOKVALUE.length;i++){
			
				String[] jour=null,Ser=null,auct=null,cb=null;
				String journaldte="",Serveydte="",auctiondte="",cb_vouc_dte="";
				if(SURVEY_DATE[i].equals("-"))
				{
					Serveydte="";	
				}else{
					Ser=SURVEY_DATE[i].split("/");
					/*if(Ser[1].equalsIgnoreCase("01"))Ser[1]="jan";
					if(Ser[1].equalsIgnoreCase("02"))Ser[1]="feb";
					if(Ser[1].equalsIgnoreCase("03"))Ser[1]="mar";
					if(Ser[1].equalsIgnoreCase("04"))Ser[1]="apr";
					if(Ser[1].equalsIgnoreCase("05"))Ser[1]="may";
					if(Ser[1].equalsIgnoreCase("06"))Ser[1]="jun";
					if(Ser[1].equalsIgnoreCase("07"))Ser[1]="jul";
					if(Ser[1].equalsIgnoreCase("08"))Ser[1]="aug";
					if(Ser[1].equalsIgnoreCase("09"))Ser[1]="sep";
					if(Ser[1].equalsIgnoreCase("10"))Ser[1]="oct";
					if(Ser[1].equalsIgnoreCase("11"))Ser[1]="nov";
					if(Ser[1].equalsIgnoreCase("12"))Ser[1]="dec";*/
					Serveydte=Ser[0]+"-"+Ser[1]+"-"+Ser[2].substring(2);
					//Serveydte=SURVEY_DATE[i];
				}
			
				if(JOURNAL_DATE[i].equals("-"))
				{
					journaldte="";	
				}else{
					Ser=JOURNAL_DATE[i].split("/");
					/*if(Ser[1].equalsIgnoreCase("01"))Ser[1]="jan";
					if(Ser[1].equalsIgnoreCase("02"))Ser[1]="feb";
					if(Ser[1].equalsIgnoreCase("03"))Ser[1]="mar";
					if(Ser[1].equalsIgnoreCase("04"))Ser[1]="apr";
					if(Ser[1].equalsIgnoreCase("05"))Ser[1]="may";
					if(Ser[1].equalsIgnoreCase("06"))Ser[1]="jun";
					if(Ser[1].equalsIgnoreCase("07"))Ser[1]="jul";
					if(Ser[1].equalsIgnoreCase("08"))Ser[1]="aug";
					if(Ser[1].equalsIgnoreCase("09"))Ser[1]="sep";
					if(Ser[1].equalsIgnoreCase("10"))Ser[1]="oct";
					if(Ser[1].equalsIgnoreCase("11"))Ser[1]="nov";
					if(Ser[1].equalsIgnoreCase("12"))Ser[1]="dec";*/
					journaldte=Ser[0]+"-"+Ser[1]+"-"+Ser[2].substring(2);
					//journaldte=JOURNAL_DATE[i];
				}
				
				if(AUCTION_DATE[i].equals("-"))
				{
					auctiondte="";	
				}else{
					//auctiondte=AUCTION_DATE[i];
			     Ser=AUCTION_DATE[i].split("/");
					/*if(Ser[1].equalsIgnoreCase("01"))Ser[1]="jan";
					if(Ser[1].equalsIgnoreCase("02"))Ser[1]="feb";
					if(Ser[1].equalsIgnoreCase("03"))Ser[1]="mar";
					if(Ser[1].equalsIgnoreCase("04"))Ser[1]="apr";
					if(Ser[1].equalsIgnoreCase("05"))Ser[1]="may";
					if(Ser[1].equalsIgnoreCase("06"))Ser[1]="jun";
					if(Ser[1].equalsIgnoreCase("07"))Ser[1]="jul";
					if(Ser[1].equalsIgnoreCase("08"))Ser[1]="aug";
					if(Ser[1].equalsIgnoreCase("09"))Ser[1]="sep";
					if(Ser[1].equalsIgnoreCase("10"))Ser[1]="oct";
					if(Ser[1].equalsIgnoreCase("11"))Ser[1]="nov";
					if(Ser[1].equalsIgnoreCase("12"))Ser[1]="dec";*/
					auctiondte=Ser[0]+"-"+Ser[1]+"-"+Ser[2].substring(2);
				}
				
				if(CB_VOUCHERDATE[i].equals("-"))
				{
					cb_vouc_dte="";	
				}else{
					//cb_vouc_dte=CB_VOUCHERDATE[i];
					Ser=CB_VOUCHERDATE[i].split("/");
					/*if(Ser[1].equalsIgnoreCase("01"))Ser[1]="jan";
					if(Ser[1].equalsIgnoreCase("02"))Ser[1]="feb";
					if(Ser[1].equalsIgnoreCase("03"))Ser[1]="mar";
					if(Ser[1].equalsIgnoreCase("04"))Ser[1]="apr";
					if(Ser[1].equalsIgnoreCase("05"))Ser[1]="may";
					if(Ser[1].equalsIgnoreCase("06"))Ser[1]="jun";
					if(Ser[1].equalsIgnoreCase("07"))Ser[1]="jul";
					if(Ser[1].equalsIgnoreCase("08"))Ser[1]="aug";
					if(Ser[1].equalsIgnoreCase("09"))Ser[1]="sep";
					if(Ser[1].equalsIgnoreCase("10"))Ser[1]="oct";
					if(Ser[1].equalsIgnoreCase("11"))Ser[1]="nov";
					if(Ser[1].equalsIgnoreCase("12"))Ser[1]="dec";*/
					cb_vouc_dte=Ser[0]+"-"+Ser[1]+"-"+Ser[2].substring(2);
				}
				
				
				int Bk_value=Integer.parseInt(BOOKVALUE[i]);
				int Apr_value=Integer.parseInt(APPOR_GRANT[i]);
				int Dep_value=Integer.parseInt(DEP_DEBIT[i]);
				int jrl_value=Integer.parseInt(JOURNAL_NO[i]);

				int serNo_value=Integer.parseInt(SURVEY_NO[i]);
				int Act_amt=Integer.parseInt(AUCTION_AMOUNT[i]);
				int cbVouNo_value=Integer.parseInt(CB_VOUCHERNO[i]);
				int profit_value=Integer.parseInt(PROFIT[i]);
				int Loss_value=Integer.parseInt(LOSS[i]);
				int offDep=Integer.parseInt(OFF_DEBIT[i]);
				int offCre=Integer.parseInt(OFF_CREDIT[i]);
				int ass_code=Integer.parseInt(ASSETCODE[i]);
				String min_Code="";
				if(assMinorCode[i]=="")min_Code="";
				else
			     min_Code=assMinorCode[i];
				
				String rema=REMARKS[i];
			
				
			try{
			
				qry="UPDATE FAS_AA52REGISTER " +
				"SET BOOKVALUE              = "+Bk_value+", " +
				"  APPOR_GRANT              = "+Apr_value+", " +
				"  DEP_DEBIT                = "+Dep_value+", " +
				"  JOURNAL_NO               = "+jrl_value+", " +
				"  JOURNAL_DATE             ='"+journaldte+"', " +
				"  SURVEY_NO                = "+serNo_value+", " +
			    "  SURVEY_DATE              ='"+Serveydte+"'," +
				"  AUCTION_DATE             ='"+auctiondte+"'," +
				"  AUCTION_AMOUNT           ="+Act_amt+", " +
				"  CB_VOUCHERNO             ="+cbVouNo_value+", " +
				"  CB_VOUCHERDATE           ='"+cb_vouc_dte+"', " +
				"  PROFIT                   ="+profit_value+", " +
				"  LOSS                     ="+Loss_value+", " +
				"  OFF_DEBIT                ="+offDep+", " +
				"  REMARKS                ='"+rema+"', " +
				"  OFF_CREDIT               ="+offCre+","+ 
				" ASSET_MINOR_CLASS_CODE="+min_Code+
				"  WHERE ACCOUNTING_UNIT_ID   ="+cmbAcc_UnitCode+
				" and ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+
				"  and FINANCIAL_YEAR           ='"+fin_year+"'"+
				"  and ASSET_MAJOR_CLASS_CODE   ="+major_code+
			//	"  and ASSET_MINOR_CLASS_CODE   ="+minor_code+
				" and ASSET_CODE="+ass_code;
				
				del_qry="DELETE  " +
				" FROM FAS_AA52REGISTER_CP " +
				" WHERE BOOKVALUE              = "+Bk_value+
				" AND APPOR_GRANT              = "+Apr_value+
				" AND DEP_DEBIT                = "+Dep_value+
				" AND JOURNAL_NO               = "+jrl_value+
				" AND JOURNAL_DATE             ='"+journaldte+"'" +
				" AND SURVEY_NO                ="+serNo_value+
				" AND AUCTION_AMOUNT           ="+Act_amt+
				" AND CB_VOUCHERNO             ="+cbVouNo_value+
				" AND PROFIT                   ="+profit_value+
				" AND LOSS                     ="+Loss_value+
				" AND OFF_DEBIT                ="+offDep+
				" AND REMARKS                  ='"+rema+"'" +
				" AND OFF_CREDIT               ="+offCre+
				" AND ASSET_MINOR_CLASS_CODE   ="+min_Code+
				" AND ACCOUNTING_UNIT_ID       ="+cmbAcc_UnitCode+
				" AND ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+
				" AND FINANCIAL_YEAR           ='"+fin_year+"'"+
				" AND ASSET_MAJOR_CLASS_CODE   ="+major_code+
				" AND ASSET_CODE               ="+ass_code;
				
			System.out.println(qry);
			ps=connection.prepareStatement(qry);			
		    k= ps.executeUpdate();
		    System.out.println(k+"k value");
		    if(k==1){
		    	try{
		    	System.out.println(del_qry);
		    	PreparedStatement del_ps=connection.prepareStatement(del_qry);
		    	k1=del_ps.executeUpdate();
		    	System.out.println(k1);
		    	}catch (Exception e) {
					
					System.out.println("error in deleting in db"+e);
				} 
		    	if(k1==0)
		    		xml+="<Del-result>failure</Del-result>";
		    	else
		    		xml+="<Del-result>success</Del-result>";
		    }
		  
			}
			catch (Exception e) {
				
				System.out.println("error in updating in db"+e);
			}   
			
			
		}
		
			if(k>0){
				//System.out.println("k>0");
				xml+="<result>success</result>";
			}else{
				//System.out.println("k<0");
				xml+="<result>failure</result>";
			}
			xml+="</response>";

		
		
	}

		if(strCommand.equalsIgnoreCase("Del_UnUpdated"))
		{
			xml+="<response><flag>Del_UnUpdated</flag>";
			try {
				major_cls_cde=request.getParameter("major_code");
				major_code=request.getParameter("major_cmb");
				cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
				cmbFinancialYear=request.getParameter("cmbFinancialYear");
				 year1=cmbFinancialYear.split("-");
				 fin_year=year1[0]+"-"+year1[1].substring(2);
				System.out.println("strCommand:-" + strCommand+year1[0]+year1[1]+fin_year);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try
			{
				del_qry="DELETE  " +
				" FROM FAS_AA52REGISTER_CP " +
				" where ACCOUNTING_UNIT_ID       ="+cmbAcc_UnitCode+
				" AND ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+
				" AND FINANCIAL_YEAR           ='"+fin_year+"'"+
				" AND ASSET_MAJOR_CLASS_CODE   ="+major_code;
				System.out.println(del_qry);
		    	PreparedStatement del_ps=connection.prepareStatement(del_qry);
		    	k1=del_ps.executeUpdate();
		    	System.out.println(k1);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(k1==0)
	    		xml+="<result>failure</result>";
	    	else
	    		xml+="<result>success</result>";
			
			xml+="</response>";
			
		}
		 
		
		if(strCommand.equalsIgnoreCase("Get_UnUpdated"))
		{
			xml+="<response><flag>Get_UnUpdated</flag>";
			try {
				major_cls_cde=request.getParameter("major_code");
				major_code=request.getParameter("major_cmb");
				cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
				cmbFinancialYear=request.getParameter("cmbFinancialYear");
				 year1=cmbFinancialYear.split("-");
				 fin_year=year1[0]+"-"+year1[1].substring(2);
				System.out.println("strCommand:-" + strCommand+year1[0]+year1[1]+fin_year);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			try{
				qry="SELECT ACCOUNTING_UNIT_ID, " +
				"  ACCOUNTING_UNIT_OFFICE_ID, " +
				"  FINANCIAL_YEAR, " +
				"  unitname, " +
				"  officename, " +
				" decode(ASSET_CODE,'',0,ASSET_CODE)as ASSET_CODE, " +
				"  decode(BOOKVALUE,'',0,BOOKVALUE)as BOOKVALUE, " +
				"  decode(APPOR_GRANT,'',0,APPOR_GRANT)as APPOR_GRANT, " +
				"  decode(DEP_DEBIT,'',0,DEP_DEBIT)as DEP_DEBIT, " +
				"  decode(JOURNAL_NO,'',0,JOURNAL_NO)as JOURNAL_NO," +
				"  TO_CHAR(JOURNAL_DATE,'dd/MM/yyyy') AS JOURNAL_DATE, " +
				"  decode(SURVEY_NO,'',0,SURVEY_NO)as SURVEY_NO, " +
				"  TO_CHAR(SURVEY_DATE,'dd/MM/yyyy')  AS SURVEY_DATE, " +
				"  TO_CHAR(AUCTION_DATE,'dd/MM/yyyy') AS AUCTION_DATE, " +
				"  PERSON_NAME, " +
				"  decode(AUCTION_AMOUNT,'',0,AUCTION_AMOUNT)as AUCTION_AMOUNT, " +
				"  decode(CB_VOUCHERNO,'',0,CB_VOUCHERNO)as CB_VOUCHERNO," +
				"  TO_CHAR(CB_VOUCHERDATE,'dd/MM/yyyy')AS CB_VOUCHERDATE, " +
				"  decode(PROFIT,'',0,PROFIT)as PROFIT, " +
				"  decode(LOSS,'',0,LOSS)as LOSS, " +
				"  decode(OFF_DEBIT,'',0,OFF_DEBIT)as OFF_DEBIT, " +
				"  decode(OFF_CREDIT,'',0,OFF_CREDIT)as OFF_CREDIT, " +
				"  REMARKS, " +
				"  UPDATED_BY_USERID, " +
				"  TO_CHAR(UPDATED_DATE,'dd/MM/yyyy')AS UPDATED_DATE, " +
				"  ASSET_MAJOR_CLASS_CODE, " +
				" ASSET_MINOR_CLASS_CODE, " +
				"  ASSET_MINOR_CLASS_DESC "+
				" FROM FAS_AA52REGISTER_CP a"+
				" WHERE ACCOUNTING_UNIT_ID     = " +cmbAcc_UnitCode+
				" AND ACCOUNTING_UNIT_OFFICE_ID=" +cmbOffice_code+
				" AND FINANCIAL_YEAR           ='"+fin_year+"' " +
				" AND ASSET_MAJOR_CLASS_CODE   = " +major_code;
				ps=connection.prepareStatement(qry);
				results=ps.executeQuery();
				while(results.next())
				{

					//xml+="<leng>";
					xml+="<ACCOUNTING_UNIT_ID>"+results.getString("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";
					xml+="<ACCOUNTING_UNIT_OFFICE_ID>"+results.getString("ACCOUNTING_UNIT_OFFICE_ID")+"</ACCOUNTING_UNIT_OFFICE_ID>";
					xml+="<FINANCIAL_YEAR>"+results.getString("FINANCIAL_YEAR")+"</FINANCIAL_YEAR>";
					xml+="<unitname>"+results.getString("unitname")+"</unitname>";
					xml+="<officename>"+results.getString("officename")+"</officename>";
					xml+="<ASSET_CODE>"+results.getString("ASSET_CODE")+"</ASSET_CODE>";
					xml+="<BOOKVALUE>"+results.getString("BOOKVALUE")+".00"+"</BOOKVALUE>";
					xml+="<APPOR_GRANT>"+results.getString("APPOR_GRANT")+".00"+"</APPOR_GRANT>";
					xml+="<DEP_DEBIT>"+results.getString("DEP_DEBIT")+".00"+"</DEP_DEBIT>";
					xml+="<JOURNAL_NO>"+results.getString("JOURNAL_NO")+"</JOURNAL_NO>";
					xml+="<JOURNAL_DATE>"+results.getString("JOURNAL_DATE")+"</JOURNAL_DATE>";
					xml+="<SURVEY_NO>"+results.getString("SURVEY_NO")+"</SURVEY_NO>";
					xml+="<SURVEY_DATE>"+results.getString("SURVEY_DATE")+"</SURVEY_DATE>";
					xml+="<AUCTION_DATE>"+results.getString("AUCTION_DATE")+"</AUCTION_DATE>";
					xml+="<PERSON_NAME>"+results.getString("PERSON_NAME")+"</PERSON_NAME>";
					xml+="<AUCTION_AMOUNT>"+results.getString("AUCTION_AMOUNT")+".00"+"</AUCTION_AMOUNT>";
					xml+="<CB_VOUCHERNO>"+results.getString("CB_VOUCHERNO")+"</CB_VOUCHERNO>";
					xml+="<CB_VOUCHERDATE>"+results.getString("CB_VOUCHERDATE")+"</CB_VOUCHERDATE>";
					xml+="<PROFIT>"+results.getString("PROFIT")+".00"+"</PROFIT>";
					xml+="<LOSS>"+results.getString("LOSS")+".00"+"</LOSS>";
					xml+="<OFF_DEBIT>"+results.getString("OFF_DEBIT")+".00"+"</OFF_DEBIT>";
					xml+="<OFF_CREDIT>"+results.getString("OFF_CREDIT")+".00"+"</OFF_CREDIT>";
					xml+="<REMARKS>"+results.getString("REMARKS")+"</REMARKS>";
					xml+="<UPDATED_BY_USERID>"+results.getString("UPDATED_BY_USERID")+"</UPDATED_BY_USERID>";
					xml+="<UPDATED_DATE>"+results.getString("UPDATED_DATE")+"</UPDATED_DATE>";		
					xml+="<ASSET_MAJOR_CLASS_CODE>"+results.getString("ASSET_MAJOR_CLASS_CODE")+"</ASSET_MAJOR_CLASS_CODE>";
					xml+="<ASSET_MINOR_CLASS_CODE>"+results.getString("ASSET_MINOR_CLASS_CODE")+"</ASSET_MINOR_CLASS_CODE>";
					xml+="<ASSET_MINOR_CLASS_DESC>"+results.getString("ASSET_MINOR_CLASS_DESC")+"</ASSET_MINOR_CLASS_DESC>";
					//xml+="</leng>";
					
					
						qry1="select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from  FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+major_code;
						ps1=connection.prepareStatement(qry1);
						results2=ps1.executeQuery();
						 
						while(results2.next()){
							 xml=xml+"<min_class_details"+c+">";  
							xml+="<MINOR_CLASS_CODE>"+results2.getInt("ASSET_MINOR_CLASS_CODE")+"</MINOR_CLASS_CODE>";
							xml+="<MINOR_CLASS_DESC>"+results2.getString("ASSET_MINOR_CLASS_DESC")+"</MINOR_CLASS_DESC>";
							xml=xml+"</min_class_details"+c+">";
						}
						
					
					 c++;
				}
				xml+="<count>"+c+"</count>";
				 if(c==0){
                     xml=xml+"<results>NoRecords</results>";}
                 else  {                          
                     xml=xml+"<results>success</results>";
				 }
                     
               /* ps.close();
                results.close();*/
				
			
				
			}catch (Exception e) {
				System.out.println(e);
			}
			xml+="</response>";
		}
		out.write(xml);
	System.out.println("xml !!!!!!!!!!!"+xml);
	out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
