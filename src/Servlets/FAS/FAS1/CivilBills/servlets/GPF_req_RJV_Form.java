package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.mail.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class GPF_req_RJV_Form
 */
public class GPF_req_RJV_Form extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_req_RJV_Form() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test get servlet");
		

		  try
	         {
		             HttpSession session=request.getSession(false);
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
	         Connection con=null;
	         ResultSet rs=null;
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
	         PreparedStatement ps=null,ps1=null,ps2=null;
	         java.sql.Date txtVrDate = null;
	         java.sql.Date txtOriDate = null;
	         try 
	         {
	                 ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                 String ConnectionString="";
	                 String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                 String strdsn=rs1.getString("Config.DSN");
	                 String strhostname=rs1.getString("Config.HOST_NAME");
	                 String strportno=rs1.getString("Config.PORT_NUMBER");
	                 String strsid=rs1.getString("Config.SID");
	                 String strdbusername=rs1.getString("Config.USER_NAME");
	                 String strdbpassword=rs1.getString("Config.PASSWORD");
	                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                 Class.forName(strDriver.trim());
	                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	         }
	         catch(Exception e)
	         {
	        	 	 System.out.println("Exception in openeing connection :"+e);

	         }
	    
	         HttpSession session=request.getSession(false);
	         String userid=(String) session.getAttribute("UserId");
	         
	    Servlets.Security.classes.UserProfile empProfile=(UserProfile) session.getAttribute("UserProfile");
	         int empid=empProfile.getEmployeeId();
	      //  System.out.println("servlet called");
	         long l=System.currentTimeMillis();
		        Timestamp ts=new Timestamp(l);
	        String CONTENT_TYPE = "text/xml; charset=windows-1252";
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	    String xml="";
	    int count=0;
	    String command=request.getParameter("Command");
	 if(command.equalsIgnoreCase("Goclick")){
	    try{
	        	txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        	
	        }
	        try{
	        	txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        xml="<response><command>goClick</command>";
	        try{
	        	ps=con.prepareStatement("SELECT DISTINCT m.request_no, " +
	        			"  m.ACCOUNTING_UNIT_ID, trim(u.accounting_unit_name) unit_name," +
	        			"  M.APPROVED, " +
	        			"  m.ACCOUNTING_FOR_OFFICE_ID, " +
	        			"  to_char(m.VOUCHER_DATE,'dd/mm/yyyy') as VOUCHER_DATE , " +
	        			"  m.JOURNAL_AMOUNT,TYPE_OPTION " +
	        			"FROM FAS_GPF_RJV_REQ_MST m " +
	        			"INNER JOIN FAS_GPF_RJV_REQ_TRN t " +
	        			"ON m.accounting_unit_id       =t.accounting_unit_id " +
	        			"AND m.accounting_for_office_id=t.accounting_for_office_id " +
	        			"AND m.cashbook_month          = t.cashbook_month " +
	        			"AND m.cashbook_year           =t.cashbook_year " +
	        			"AND m.request_no              =t.request_no " +
	        			"AND m.cashbook_month          =? " +
	        			"AND m.cashbook_year           =? " +
	        			"AND m.status                  ='L' " +
	        			"AND ( M.APPROVED             IS NULL " +
	        			"OR M.APPROVED                <> 'Y') " +
	        			" inner join FAS_MST_ACCT_UNITS u on m.accounting_unit_id   =u.accounting_unit_id "+
	        			"ORDER BY m.request_no"

);
	        	ps.setInt(1, txtCB_Month);
	        	ps.setInt(2, txtCB_Year);
	        	rs=ps.executeQuery();
	        	while(rs.next()){
	        		xml+="<request_no>"+rs.getInt(1)+"</request_no>"+"<ACCOUNTING_UNIT_ID>"+rs.getInt(2)+"</ACCOUNTING_UNIT_ID>"+
	        				"<unit_name>"+rs.getString(3)+"</unit_name>"+	"<VOUCHER_DATE>"+rs.getString(6)+"</VOUCHER_DATE>"+
	        				"<Amount>"+rs.getString(7)+"</Amount><TYPE_OPTION>"+rs.getString("TYPE_OPTION")+"</TYPE_OPTION>";
	        		count++;
	        	}
	        	if(count>0)
	        		xml+="<flag>success</flag>";
	        		else
	        			xml+="<flag>nodata</flag>";
	        }catch(Exception e){
	        	xml+="<flag>failure</flag>";
	        	e.printStackTrace();
	        }
	     xml+="</response>";
	        out.print(xml);
	        System.out.println(" :::::   "+xml);
	        out.close();
	 }else if(command.equalsIgnoreCase("loadBAHead")) 
	 {
			String mod_id="MF004",CR_DR="DR";
		    
		        try{
		        	cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        xml="<response><command>loadBAHead</command>";
		        String sql_bank="select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || br.CITY_TOWN_NAME as bk_br_city "+
		  		      " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? "+
		  		      " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.STATUS='Y' and curr.ac_operational_mode_id='COL'";
		        try{
		        	ps=con.prepareStatement(sql_bank);
		        	ps.setInt(1,cmbAcc_UnitCode);
		        	ps.setString(2,mod_id);
		        	ps.setString(3,CR_DR);
		        	rs=ps.executeQuery();
		        	while(rs.next()){
				      
				      System.out.println("inside if");
				      int bankID=rs.getInt("BANK_ID");
				     int  branchID=rs.getInt("BRANCH_ID");
				    long   bankAccNo=rs.getLong("BANK_AC_NO");
				    int   AC_HEAD_CODE=rs.getInt("AC_HEAD_CODE");
				   String    bk_br_city=rs.getString("bk_br_city");
				          System.out.println("bank details..."+bankID+" "+branchID+ " "+ bankAccNo+" "+bk_br_city+" "+AC_HEAD_CODE);
				     
				          xml+="<head>"+rs.getInt("AC_HEAD_CODE")+"</head>";
				          xml+="<bankID>"+rs.getInt("BANK_ID")+"</bankID>";
				          xml+="<branchID>"+rs.getInt("BRANCH_ID")+"</branchID>";
				          xml+="<bankAccNo>"+rs.getLong("BANK_AC_NO")+"</bankAccNo>";
				          xml+="<bk_br_city>"+rs.getString("bk_br_city")+"</bk_br_city>";
				      	count++;
				      }
				  
		        	
		        	
		        	
		        	if(count>0)
		        		xml+="<flag>success</flag>";
		        		else
		        			xml+="<flag>nodata</flag>";
		            ps.close();
				      rs.close();
		        }catch(Exception e){
		        	xml+="<flag>failure</flag>";
		        	e.printStackTrace();
		        }
		     xml+="</response>";
		        out.print(xml);
		        System.out.println(" :::::   "+xml);
		        out.close();
		  
	 }
	 
	 else if(command.equalsIgnoreCase("GoclickApp")){
	    try{
	        	txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        try{
	        	txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	        xml="<response><command>GoclickApp</command>";
	        try{
	        	ps=con.prepareStatement("SELECT DISTINCT m.request_no, " +
	        			"  m.ACCOUNTING_UNIT_ID, trim(u.accounting_unit_name) unit_name," +
	        			"  M.APPROVED, " +
	        			"  m.ACCOUNTING_FOR_OFFICE_ID, " +
	        			"  to_char(m.VOUCHER_DATE,'dd/mm/yyyy') as VOUCHER_DATE , " +
	        			"  m.JOURNAL_AMOUNT,TYPE_OPTION " +
	        			"FROM FAS_GPF_RJV_REQ_MST m " +
	        			"INNER JOIN FAS_GPF_RJV_REQ_TRN t " +
	        			"ON m.accounting_unit_id       =t.accounting_unit_id " +
	        			"AND m.accounting_for_office_id=t.accounting_for_office_id " +
	        			"AND m.cashbook_month          = t.cashbook_month " +
	        			"AND m.cashbook_year           =t.cashbook_year " +
	        			"AND m.request_no              =t.request_no " +
	        			"AND m.cashbook_month          =? " +
	        			"AND m.cashbook_year           =? " +
	        			" and m.accounting_unit_id="+cmbAcc_UnitCode+
	        			"  AND m.status                  ='L' " +
	        			"AND  M.APPROVED           = 'Y' and  RJV_NO is null and RJV_DATE is null and ( RJV_CREATED <> 'Y' or RJV_CREATED is null ) " +
	        			" inner join FAS_MST_ACCT_UNITS u on m.accounting_unit_id   =u.accounting_unit_id "+
	        			"ORDER BY m.request_no"

);
	        	ps.setInt(1, txtCB_Month);
	        	ps.setInt(2, txtCB_Year);
	        	rs=ps.executeQuery();
	        	while(rs.next()){
	        		xml+="<request_no>"+rs.getInt(1)+"</request_no>"+"<ACCOUNTING_UNIT_ID>"+rs.getInt(2)+"</ACCOUNTING_UNIT_ID>"+
	        				"<unit_name>"+rs.getString(3)+"</unit_name>"+	"<VOUCHER_DATE>"+rs.getString(6)+"</VOUCHER_DATE>"+
	        				"<Amount>"+rs.getString(7)+"</Amount><TYPE_OPTION>"+rs.getString("TYPE_OPTION")+"</TYPE_OPTION>";
	        		
	        		count++;
	        	}
	        	if(count>0)
	        		xml+="<flag>success</flag>";
	        		else
	        			xml+="<flag>nodata</flag>";
	        }catch(Exception e){
	        	xml+="<flag>failure</flag>";
	        	e.printStackTrace();
	        }
	     xml+="</response>";
	        out.print(xml);
	        System.out.println(" :::::   "+xml);
	        out.close();
	 }else if(command.equalsIgnoreCase("Add_Jrl")){
System.out.println("command >> "+command);
		 
		 int request_no=0,y=0,Office_code=0,txtSubBankId=0,txtSub_Office_code=0;
         String Amount="",VOUCHER_DATE="",unspent_OR_col="";
         int res=0;
         
         int ori_month=0,ori_year=0;
         int kk=0;int txtJournalVou_No=0;
        
  String unit[]=request.getParameterValues("cmbAcc_UnitCode");
  String req_no[]=request.getParameterValues("request_no");
  String vrDate[]=request.getParameterValues("VOUCHER_DATE");
  String Amount1[]=request.getParameterValues("Amount");
  String type_opt[]=request.getParameterValues("type_opt");
  String hid[]=request.getParameterValues("hid_o");
  
  
  
  try{
      String[] sd1=request.getParameter("AppDate").split("/");
   
     Calendar c1=new java.util.GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
      java.util.Date d1=c1.getTime();

      txtOriDate=new Date(d1.getTime());
      System.out.println("txtOriDate >> "+txtOriDate);
        }catch(Exception e){
        	e.printStackTrace();
        }
  
  
  System.out.println("Amount1.length >> "+Amount1.length);
  try{
	  txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
  }catch(Exception e){System.out.println("Exception in getting txtCB_Year:"+e);}
  try{
	  txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
  }catch(Exception e){System.out.println("Exception in getting txtCB_Month:"+e);}
  for(int k=0; k<Amount1.length;k++){

	  try{
	      String[] sd=vrDate[k].split("/");
	   
	     Calendar c=new java.util.GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	      java.util.Date d=c.getTime();

	      txtVrDate=new Date(d.getTime());
	      System.out.println("txtOriDate >> "+txtOriDate);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	  
	  System.out.println(k+"   ----   "+hid[k]);
	  if(hid[k].equalsIgnoreCase("Checked"))
     
		  {
		  try{
		  
        	  cmbAcc_UnitCode=Integer.parseInt(unit[k]);
          }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
      
          try{
        	  request_no=Integer.parseInt(req_no[k]);
             }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
            
       
          try{
        	  Amount=Amount1[k];
             }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
            
					xml = "<response><command>" + command + "</command>";
					try {
						ps = con.prepareStatement("SELECT ACCOUNTING_UNIT_ID, "
								+ "  ACCOUNTING_FOR_OFFICE_ID, "
								+ "  extract(YEAR FROM VOUCHER_DATE)  AS CASHBOOK_YEAR, "
								+ "  extract(MONTH FROM VOUCHER_DATE) AS CASHBOOK_MONTH, "
								+ "  REQUEST_NO, "
								+ "  JOURNAL_AMOUNT, "
								+ "  VOUCHER_DATE, "
								+ "  RELATIVECB_MONTH, "
								+ "  RELATIVECB_YEAR, "
								+ "  (SELECT COUNT(*) "
								+ "  FROM FAS_GPF_RJV_REQ_TRN t "
								+ "  WHERE   t.ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID   AND t.REQUEST_NO          =m.REQUEST_NO "
								+ "  ) AS total_rec,HEAD_CODE,TYPE_OPTION,ACCOUNT_NO,BANK_ID,BRANCH_ID,RECEIVED_FROM "
								+ " FROM FAS_GPF_RJV_REQ_MST m "
								+ " WHERE ACCOUNTING_UNIT_ID=? "
								+ " AND REQUEST_NO          =? "
								+ " AND JOURNAL_AMOUNT      =?::numeric "
								+ "  AND VOUCHER_DATE        =? "
								+ " AND CASHBOOK_YEAR       =? "
								+ " AND CASHBOOK_MONTH      =? "
								+ " AND APPROVED            ='Y' "
								+ " GROUP BY ACCOUNTING_UNIT_ID, "
								+ "  ACCOUNTING_FOR_OFFICE_ID, "
								+ "  extract(YEAR FROM VOUCHER_DATE), "
								+ "  extract(MONTH FROM VOUCHER_DATE), "
								+ "  REQUEST_NO, "
								+ "  JOURNAL_AMOUNT, "
								+ "  VOUCHER_DATE, "
								+ "  RELATIVECB_MONTH, "
								+ "  RELATIVECB_YEAR,HEAD_CODE,TYPE_OPTION,ACCOUNT_NO,BANK_ID,BRANCH_ID,RECEIVED_FROM ");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, request_no);
						ps.setString(3, Amount);
						ps.setDate(4, txtVrDate);
						ps.setInt(5, txtCB_Year);
						ps.setInt(6, txtCB_Month);
						ResultSet rs1 = ps.executeQuery();
						System.out.println("rs1 " + rs1);
						while (rs1.next()) {
							ori_month = rs1.getInt("CASHBOOK_MONTH");
							System.out.println("ori_month " + ori_month);
							ori_year = rs1.getInt("CASHBOOK_YEAR");
							System.out.println("ori_year " + ori_year);
							int errcode = 0;
							CallableStatement cs = null;
							if (type_opt[k].equalsIgnoreCase("JR")) {
								System.out.println("Journal Part ");
								 cs = con.prepareCall("call FAS_JOURNAL_MASTER_PROCRJV(?::numeric, ?::numeric, ?::numeric, ?::numeric, ?::numeric, ?::date, ?::numeric, ?::numeric, ?::numeric, ?::varchar, ?::date, ?::varchar, ?::numeric, ?::varchar, ?::varchar, ?::varchar, ?::varchar, ?::int, ?::varchar, ?::timestamp, ?::numeric, ?::numeric)");

								cs.setBigDecimal(1, BigDecimal.valueOf(cmbAcc_UnitCode));
								cs.setBigDecimal(2, BigDecimal.valueOf(rs1.getInt("ACCOUNTING_FOR_OFFICE_ID")));
								cs.setBigDecimal(3, BigDecimal.valueOf(rs1.getInt("CASHBOOK_YEAR")));
								cs.setBigDecimal(4, BigDecimal.valueOf(rs1.getInt("CASHBOOK_MONTH")));
								cs.setNull(5, java.sql.Types.NUMERIC); // INOUT param
								cs.setDate(6, txtVrDate); // This is OK, but ensure txtVrDate is java.sql.Date
								cs.setBigDecimal(7, BigDecimal.valueOf(75));
								cs.setBigDecimal(8, BigDecimal.valueOf(7));
								cs.setDouble(9, 0.0); // OK for numeric
								cs.setNull(10, java.sql.Types.VARCHAR);
								cs.setNull(11, java.sql.Types.DATE);
								cs.setNull(12, java.sql.Types.VARCHAR);
								cs.setBigDecimal(13, BigDecimal.valueOf(rs1.getInt("total_rec")));
								cs.setString(14, "GPF RJV Created for the req no " + request_no + " dt. " + vrDate[k] + " and approved by GPF Section on Approval date " + txtOriDate);
								cs.setString(15, "A");
								cs.setString(16, "GJV");
								cs.setString(17, "insert");
								
								cs.registerOutParameter(5, java.sql.Types.NUMERIC);
								cs.registerOutParameter(18, java.sql.Types.INTEGER);
								  cs.setNull(5, java.sql.Types.NUMERIC);
 				                 cs.setNull(18, java.sql.Types.NUMERIC);
								cs.setString(19, userid);
								cs.setObject(20, ts, java.sql.Types.TIMESTAMP);
								cs.setBigDecimal(21, BigDecimal.valueOf(rs1.getInt("RELATIVECB_MONTH")));
								cs.setBigDecimal(22, BigDecimal.valueOf(rs1.getInt("RELATIVECB_YEAR")));

		        				              //  cs.setInt(21,supplement_no);
		        				                System.out.println("b4 exe ");
		        				                cs.execute();
//		        				                txtJournalVou_No = cs.getInt(5);
//		        				                 errcode = cs.getInt(18);
		        				                txtJournalVou_No = cs.getBigDecimal(5).intValue();
    							                errcode =  cs.getInt(18);
		        				                System.out.println("SQLCODE:::" + errcode);
							
		        				                
		        				}else 	if(type_opt[k].equalsIgnoreCase("CR"))
		        				                
		        				{
		        					System.out.println("Cash Receipt Part ");
		        					 cs =
		        							  //con.prepareCall("{call FAS_RECEIPT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		        							 con.prepareCall("call FAS_RECEIPT_MASTER_PROC(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?,?,?,?,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?)");              

		        		                cs.setInt(1, cmbAcc_UnitCode);
		        		                cs.setInt(2, rs1.getInt("CASHBOOK_YEAR"));
		        		                cs.setInt(3, rs1.getInt("CASHBOOK_MONTH"));
		        		                cs.setInt(4, 0);
		        		                cs.setInt(5, cmbOffice_code);
		        		                cs.setDate(6, txtVrDate);
		        		                cs.setString(7, "C");
		        		                cs.setInt(8, rs1.getInt("HEAD_CODE"));
		        		                cs.setString(9, "DR");
		        		                cs.setInt(10,  rs1.getInt("BANK_ID"));
		        		                cs.setInt(11,  rs1.getInt("BRANCH_ID"));
		        		                cs.setLong(12, rs1.getInt("ACCOUNT_NO"));
		        		                cs.setString(13, rs1.getString("RECEIVED_FROM"));
		        		                cs.setString(14, "");
		        		                cs.setDate(15, null);
		        		                cs.setInt(16, 0);
		        		                cs.setInt(17, 0);
		        		                cs.setDate(18, null);
		        		                cs.setObject(19,  rs1.getDouble("JOURNAL_AMOUNT"));
		        		                //cs.setDouble(19, txtAmount);
		        		                cs.setInt(20,  rs1.getInt("total_rec"));
		        		                cs.setString(21, "");
		        		                cs.setInt(22, 0);
		        		                cs.setDate(23, null);
//		        		                cs.setInt(24, txtJournal_code);
		        		                cs.setString(24,"");
		        		                cs.setString(25, "GPF RJV Created for the req no "+request_no+" dt. "+vrDate[k]+"  and approved by GPF Section on Approval date "+txtOriDate);
		        		                cs.setString(26, "M");
		        		                cs.setString(27, "CR");
		        		                cs.setString(28, "insert");
		        		                cs.registerOutParameter(4, java.sql.Types.NUMERIC);
		        		                //cs.setN(29, java.sql.Types.NUMERIC);
		        		                cs.registerOutParameter(29, java.sql.Types.NUMERIC);
		        		                cs.setNull(4, java.sql.Types.NUMERIC);
		        		                cs.setNull(29, java.sql.Types.NUMERIC);
		        		                cs.setInt(30, 0);
		        		                cs.setInt(31, 0);
		        		                //cs.setInt(32,cmbMas_offid);
		        		                cs.setString(32, userid);
		        		                
		        		                cs.setTimestamp(33, ts);
		        		                cs.setString(34, "Y");
		        		                System.out.println("b4 exe ");
		        		               
		        		                cs.execute();
		        		                txtJournalVou_No =cs.getBigDecimal(4).intValue();
		        		                 errcode = cs.getBigDecimal(29).intValue();
		        		                System.out.println("SQLCODE:::" + errcode);
		        		                
		        				}else 	if(type_opt[k].equalsIgnoreCase("BR"))
    				                
    				{

		        					System.out.println("Cash Receipt Part ");
		        					 cs =
//		        							  con.prepareCall("{call FAS_RECEIPT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//		        							                cs.setInt(1, cmbAcc_UnitCode);
//		        							                cs.setInt(2,  rs1.getInt("CASHBOOK_YEAR")); //2
//		        							                cs.setInt(3, rs1.getInt("CASHBOOK_MONTH"));//3
//		        							                cs.setInt(4, 0);
//		        							                cs.setInt(5, rs1.getInt("ACCOUNTING_FOR_OFFICE_ID"));//1
//		        							                cs.setDate(6, txtVrDate);//4
//		        							                cs.setString(7, "B");//5
//		        							                cs.setInt(8, rs1.getInt("HEAD_CODE")); //6
//		        							                cs.setString(9, "DR");
//		        							                cs.setInt(10, rs1.getInt("BANK_ID")); 
//		        							                cs.setInt(11, rs1.getInt("BRANCH_ID"));
//		        							                cs.setLong(12, rs1.getInt("ACCOUNT_NO"));
//		        							                cs.setString(13, "");
//		        							                cs.setString(14, "");
//		        							                cs.setDate(15, null);
//		        							                cs.setInt(16, 0);
//		        							                cs.setInt(17, 0);
//		        							                cs.setDate(18, null);
//		        							                cs.setDouble(19, rs1.getDouble("JOURNAL_AMOUNT"));
//		        							                cs.setInt(20,  rs1.getInt("total_rec"));
//		        							                cs.setString(21, "");
//		        							                cs.setInt(22, 0);
//		        							                cs.setDate(23, null);
//		        							                cs.setInt(24, 0);
//		        							                cs.setString(25, "GPF RJV Created for the req no "+request_no+" dt. "+vrDate[k]+"  and approved by GPF Section on Approval date "+txtOriDate);
//		        							                cs.setString(26, "M");
//		        							                cs.setString(27, "BR");
//		        							                cs.setString(28, "insert");
//		        							                cs.registerOutParameter(4, java.sql.Types.NUMERIC);
//		        							                cs.registerOutParameter(29, java.sql.Types.NUMERIC);
//		        							                cs.setInt(30,0);
//		        							                cs.setInt(31,0);
//		        							                //cs.setInt(32,cmbMas_offid);
//		        							                cs.setString(32, userid);
//		        							                cs.setTimestamp(33, ts);
//		        							                cs.setString(34, "Y");//previously 'N' changed on 28/03/2016
//		        							                System.out.println("b4 exe ");
//		        							                cs.execute();
//		        							                txtJournalVou_No = cs.getInt(4);
//		        							                 errcode = cs.getInt(29);
		        							 con.prepareCall("call FAS_RECEIPT_MASTER_PROC(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?,?,?,?,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?)");
		        					 cs.setInt(1, cmbAcc_UnitCode);
						                cs.setInt(2,  rs1.getInt("CASHBOOK_YEAR")); //2
						                cs.setInt(3, rs1.getInt("CASHBOOK_MONTH"));//3
						                cs.setInt(4, 0);
						                cs.setInt(5, rs1.getInt("ACCOUNTING_FOR_OFFICE_ID"));//1
						                cs.setDate(6, txtVrDate);//4
						                cs.setString(7, "B");//5
						                cs.setInt(8, rs1.getInt("HEAD_CODE")); //6
						                cs.setString(9, "DR");
						                cs.setInt(10, rs1.getInt("BANK_ID")); 
						                cs.setInt(11, rs1.getInt("BRANCH_ID"));
						                cs.setLong(12, rs1.getInt("ACCOUNT_NO"));
						                cs.setString(13, "");
						                cs.setString(14, "");
						                cs.setDate(15, null);
						                cs.setInt(16, 0);
						                cs.setInt(17, 0);
						                cs.setDate(18, null);
						                cs.setDouble(19, rs1.getDouble("JOURNAL_AMOUNT"));
						                cs.setInt(20,  rs1.getInt("total_rec"));
						                cs.setString(21, "");
						                cs.setInt(22, 0);
						                cs.setDate(23, null);
						                cs.setInt(24, 0);
						                cs.setString(25, "GPF RJV Created for the req no "+request_no+" dt. "+vrDate[k]+"  and approved by GPF Section on Approval date "+txtOriDate);
						                cs.setString(26, "M");
						                cs.setString(27, "BR");
						                cs.setString(28, "insert");
						                cs.registerOutParameter(4, java.sql.Types.NUMERIC);
						                cs.registerOutParameter(29, java.sql.Types.NUMERIC);
						                cs.setNull(4, java.sql.Types.NUMERIC);
						                cs.setNull(29, java.sql.Types.NUMERIC);
						                cs.setInt(30,0);
						                cs.setInt(31,0);
						                //cs.setInt(32,cmbMas_offid);
						                cs.setString(32, userid);
						                cs.setTimestamp(33, ts);
						                cs.setString(34, "Y");//previously 'N' changed on 28/03/2016
						                System.out.println("b4 exe ");
						                cs.execute();
					                 
						                txtJournalVou_No = cs.getBigDecimal(4).intValue();
						                errcode = cs.getBigDecimal(29).intValue();						 
		        							                System.out.println("SQLCODE:::" + errcode);
		        				
    				}
		        				                
		        				                
		        				                
		        				                
		        				                
		        				                
		        				                
		        				                
		        				                
		        				                if (errcode != 0) {
								con.rollback();
								System.out.println("redirect");
								sendMessage(response,
										"The  Voucher Number Creation Failed ",
										"ok", "GPF_req_RJV_FormApp.jsp");
								xml = xml + "<flag>failure</flag>";
							} else {
								PreparedStatement ps_tr=null;
								 
								 int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code =0	, adjYear =0,docNo=0,
				                    		adjMonth=0, cmbSL_type = 0, txtCB_REF_NO = 0;
				                    Date txtBill_Date = null, txtAgree_Date =
				                        null, txtCheque_DD_date = null, txtCB_REF_DATE = null;
				                    double txtsub_Amount = 0;
				                    String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
				                        "", txtAgree_No = "", txtParticular = "";
				                    String txtCheque_DD = "", txtCheque_DD_NO = ""; 
								  
								  
								  
								  try{
									 ps_tr=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID, " +
"  ACCOUNTING_FOR_OFFICE_ID, " +
"  ACCOUNT_HEAD_CODE, " +
"  AMOUNT, " +
"  CR_DR_TYPE, " +
"  SL_NO, " +
"  SUB_LEDGER_TYPE_CODE, " +
"  SUB_LEDGER_CODE,PARTICULARS,ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH,ADJ_DOC_TYPE,ADJ_DOC_NO  " +
"FROM FAS_GPF_RJV_REQ_TRN m " +
"WHERE ACCOUNTING_UNIT_ID=? " +
"AND REQUEST_NO          =? " +
"AND CASHBOOK_YEAR       =? " +
"AND CASHBOOK_MONTH      =?");
								
									
									ps_tr.setInt(1,cmbAcc_UnitCode);	System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
									ps_tr.setInt(2,request_no);	System.out.println("request_no "+request_no);
							
								
									ps_tr.setInt(3,txtCB_Year);	System.out.println("txtCB_Year "+txtCB_Year);
									ps_tr.setInt(4,txtCB_Month);
						        	ResultSet rs_tr=ps_tr.executeQuery();
									
									
									while(rs_tr.next())
									{
										
										 	if(type_opt[k].equalsIgnoreCase("CR")||type_opt[k].equalsIgnoreCase("BR"))
	        				                
				        				{
										 		
										 		 String sql1 =
									                        "insert into FAS_RECEIPT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
									                        "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, RECEIPT_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
									                        "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, RECEIVED_FROM," +
									                        "CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, BANK_NAME, DRAWEE_BRANCH, " +
									                        "BANK_MICR_CODE, AMOUNT, PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
									                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
										 	    ps = con.prepareStatement(sql1);
										 	    
										 	    

						                        try {
						                            txtAcc_HeadCode =rs_tr.getInt("ACCOUNT_HEAD_CODE");
						                        } catch (Exception e) {
						                            System.out.println("exception in trans " + e);
						                        }
						                        rad_sub_CR_DR = rs_tr.getString("CR_DR_TYPE");

						                        try {
						                            cmbSL_type = rs_tr.getInt("SUB_LEDGER_TYPE_CODE");
						                        } catch (Exception e) {
						                            System.out.println("exception in trans " + e);
						                        }
						                        try {
						                            cmbSL_Code = rs_tr.getInt("SUB_LEDGER_CODE");
						                        } catch (Exception e) {
						                            System.out.println("exception in trans " + e);
						                        }
						                     
										 		  ps.setInt(1, cmbAcc_UnitCode);
							                        ps.setInt(2,  rs1.getInt("ACCOUNTING_FOR_OFFICE_ID"));
							                        ps.setInt(3, ori_year);
							                        ps.setInt(4, ori_month);
							                        ps.setInt(5, txtJournalVou_No);
							                        ps.setInt(6, rs_tr.getInt("SL_NO"));
							                        ps.setInt(7, txtAcc_HeadCode);
							                        ps.setString(8, "CR");
							                        ps.setInt(9, cmbSL_type);
							                        ps.setInt(10, cmbSL_Code);
							                        String txtsub_Recei_from="",txtBank_Name="",txtDraw_BR="",txtBank_M_Code="";
							                        ps.setString(11, txtsub_Recei_from);
							                        //ps.setString(12,DPN_deptId);
							                        //ps.setInt(13,DPN_offId);
							                        ps.setString(12, txtCheque_DD);
							                        ps.setString(13, txtCheque_DD_NO);
							                        ps.setDate(14, txtCheque_DD_date);
							                        ps.setString(15, txtBank_Name);
							                        ps.setString(16, txtDraw_BR);
							                        ps.setString(17, txtBank_M_Code);
							                        ps.setDouble(18, rs_tr.getDouble("AMOUNT"));
							                        ps.setString(19, rs_tr.getString("PARTICULARS"));
							                        ps.setString(20, userid);
							                        ps.setTimestamp(21, ts);		
							                        int res_c= ps.executeUpdate();
							                        SL_NO++;
							                    	if(res_c > 0)
														res=res+0;
													else
														res=res+1;
							                    ps.close();
										 		
				        				}
										else 	if(type_opt[k].equalsIgnoreCase("JR"))
	        				                
				        				{

			                   

			                    String sql =
			                        "insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
			                        "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
			                        "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
			                        "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
			                        "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH,ADJ_DOC_TYPE,ADJ_DOC_NO ) " +
			                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			                   

			                    ps = con.prepareStatement(sql);
			                    
			                    
			                    
			                
			                        try {
			                            txtAcc_HeadCode =rs_tr.getInt("ACCOUNT_HEAD_CODE");
			                        } catch (Exception e) {
			                            System.out.println("exception in trans " + e);
			                        }
			                        rad_sub_CR_DR = rs_tr.getString("CR_DR_TYPE");

			                        try {
			                            cmbSL_type = rs_tr.getInt("SUB_LEDGER_TYPE_CODE");
			                        } catch (Exception e) {
			                            System.out.println("exception in trans " + e);
			                        }
			                        try {
			                            cmbSL_Code = rs_tr.getInt("SUB_LEDGER_CODE");
			                        } catch (Exception e) {
			                            System.out.println("exception in trans " + e);
			                        }
			                     
			                     
			                        txtBill_no = "";

			                        txtBill_Type = null;

			                       /* if (!Grid_Bill_date[k].equalsIgnoreCase("")) {
			                            sd = Grid_Bill_date[k].split("/");
			                            c =
			   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
			                         Integer.parseInt(sd[0]));
			                            d = c.getTime();
			                            txtBill_Date = new Date(d.getTime());
			                        }*/

			                        txtAgree_No = "";
			                       /* if (!Grid_Agree_date[k].equalsIgnoreCase("")) {
			                            sd = Grid_Agree_date[k].split("/");
			                            c =
			   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
			                         Integer.parseInt(sd[0]));
			                            d = c.getTime();
			                            txtAgree_Date = new Date(d.getTime());
			                        }
*/		


			                        System.out.println("txtBill_no..." + txtBill_no);
			                        System.out.println("txtBill_Type..." + txtBill_Type);
			                        System.out.println("txtBill_Date..." + txtBill_Date);
			                        System.out.println("txtAgree_No..." + txtAgree_No);
			                        System.out.println("txtAgree_Date..." + txtAgree_Date);

			                        txtsub_Amount = rs_tr.getDouble("AMOUNT");
			                        txtParticular = "";
			                        System.out.println("amount");
			                        System.out.println("Grid_sl_amt[k] " + txtsub_Amount);
			                         System.out.println("txtJournalVou_No  ]]]]]]]]]]] "+txtJournalVou_No);
			               
			                         
			                     /*	txtBill_no = Grid_Bill_No[k];

									txtBill_Type = Grid_Bill_type[k];*/
									try {
										 adjYear = rs_tr.getInt("ADJ_AGAINST_YEAR");
										 adjMonth = rs_tr.getInt("ADJ_AGAINST_MONTH");
										 docNo =  rs_tr.getInt("ADJ_DOC_NO");
									} catch (Exception e) {
										System.out
												.println("this is not have doc type and voucherno");
									}
									String docType = rs_tr.getString("ADJ_DOC_TYPE");
									System.out.println("docType"+docType);
									
									
								/*	if (docType.equalsIgnoreCase("") || docType.equalsIgnoreCase(null)) {
										docType = null;
									}*/
			                         /*      System.out.println("Grid_particular[k] " +
			                                           Grid_particular[k]);*/

			                        ps.setInt(1, cmbAcc_UnitCode);
			                        ps.setInt(2,  rs1.getInt("ACCOUNTING_FOR_OFFICE_ID"));
			                        ps.setInt(3, ori_year);
			                        ps.setInt(4, ori_month);
			                        ps.setInt(5, txtJournalVou_No);
			                        ps.setInt(6,  rs_tr.getInt("SL_NO"));
			                        ps.setInt(7, txtAcc_HeadCode);
			                        ps.setString(8, rad_sub_CR_DR);
			                        ps.setInt(9, cmbSL_type);
			                        ps.setInt(10, cmbSL_Code);
			                        ps.setString(11, txtBill_no);
			                        ps.setString(12, txtBill_Type);
			                        ps.setString(13, txtAgree_No);
			                        ps.setDate(14, txtAgree_Date);
			                        ps.setDate(15, txtBill_Date);

			                        ps.setString(16, txtCheque_DD);
			                        ps.setString(17, txtCheque_DD_NO);
			                        ps.setDate(18, txtCheque_DD_date);

			                        ps.setDouble(19, txtsub_Amount);
			                        ps.setString(20,  rs_tr.getString("PARTICULARS"));
			                        ps.setInt(21, txtCB_REF_NO);
			                        ps.setDate(22, txtCB_REF_DATE);
			                        ps.setString(23, userid);
			                        ps.setTimestamp(24, ts);
			                    	ps.setInt(25, adjYear);
									ps.setInt(26, adjMonth);
									ps.setString(27, docType);
									ps.setInt(28, docNo);
			                        SL_NO++;
			                      int res_c= ps.executeUpdate();

									if(res_c > 0)
										res=res+0;
									else
										res=res+1;
			                    ps.close();
									}
			                    
									}
								}catch (Exception e) {
									con.rollback();
									e.printStackTrace();
								}
								System.out.println("res >> "+res);
								if(res==0){
									
									 try{
								        PreparedStatement	ps_ck=con.prepareStatement("update FAS_GPF_RJV_REQ_MST set RJV_NO=? ,RJV_DATE=? ,RJV_CREATED='Y' where "
								        			+ "ACCOUNTING_UNIT_ID=?  and "
								        			+ "REQUEST_NO=? and JOURNAL_AMOUNT="+Amount+" and VOUCHER_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ");
								        ps_ck.setInt(1,txtJournalVou_No);
								        ps_ck.setDate(2,txtOriDate); 
								        ps_ck.setInt(3,cmbAcc_UnitCode);
								    	ps_ck.setInt(4,request_no);
							        	
							        	ps_ck.setDate(5,txtVrDate);
							        	ps_ck.setInt(6,txtCB_Year);
							        	ps_ck.setInt(7,txtCB_Month);
								        	 kk=ps_ck.executeUpdate();
								        	if(kk> 0){
								        		  System.out.println("b4 commit");
//								                    con.commit();
								        	
								        		sendMessage(response,
						                                "The  Voucher Number '" + txtJournalVou_No +
						                                "' has been Created Successfully ", "ok","GPF_req_RJV_FormAppved.jsp");
								        	}else{
								        		con.rollback();
								        		sendMessage(response," Not Saved Successfully","OK","GPF_req_RJV_FormAppved.jsp" );
								        	}
			                
									 }
									catch (Exception e) {
//										con.rollback();
									e.printStackTrace();
								}
								
							}
								else{
									con.rollback();	
								}

						}
					} 
		        }catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
  }
	 else{


		 int request_no=0,y=0,Office_code=0,txtSubBankId=0,txtSub_Office_code=0,txtrel_Year=0,txtrel_Month=0;
         String Amount="",VOUCHER_DATE="",unspent_OR_col="";
         int kk=0;
  String unit[]=request.getParameterValues("cmbAcc_UnitCode");
  String req_no[]=request.getParameterValues("request_no");
  String vrDate[]=request.getParameterValues("VOUCHER_DATE");
  String Amount1[]=request.getParameterValues("Amount");
  String hid[]=request.getParameterValues("hid");
  try{
	  txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
  }catch(Exception e){System.out.println("Exception in getting txtCB_Year:"+e);}
  try{
	  txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
  }catch(Exception e){System.out.println("Exception in getting txtCB_Month:"+e);}
  try{
	  txtrel_Year=Integer.parseInt(request.getParameter("txtrel_Year"));
  }catch(Exception e){System.out.println("Exception in getting txtCB_Year:"+e);}
  try{
	  txtrel_Month=Integer.parseInt(request.getParameter("txtrel_Month"));
  }catch(Exception e){System.out.println("Exception in getting txtCB_Month:"+e);}
  for(int k=0; k<Amount1.length;k++){
	  if(hid[k].equalsIgnoreCase("Checked"))
       
		  {
		  try{
		  
        	  cmbAcc_UnitCode=Integer.parseInt(unit[k]);
          }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
      
          try{
        	  request_no=Integer.parseInt(req_no[k]);
             }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
            
       
          try{
        	  Amount=Amount1[k];
             }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
            
          try{
    	      String[] sd=vrDate[k].split("/");
    	   
    	     Calendar c=new java.util.GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
    	      java.util.Date d=c.getTime();
    	
    	   txtVrDate=new Date(d.getTime());
    	      System.out.println("txtVrDate >> "+txtVrDate);
    	        }catch(Exception e){
    	        	e.printStackTrace();
    	        }
		        xml="<response><command>"+command+"</command>";
		        try{
		        	ps=con.prepareStatement("update FAS_GPF_RJV_REQ_MST set APPROVED='Y',APPROVEDBY=? where "
		        			+ "ACCOUNTING_UNIT_ID=?  and "
		        			+ "REQUEST_NO=? and JOURNAL_AMOUNT="+Amount+" and VOUCHER_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ");
		        	System.out.println("***********************"+String.valueOf(empid));
		        	ps.setString(1,String.valueOf(empid));
		        	ps.setInt(2,cmbAcc_UnitCode);
		        	ps.setInt(3,request_no);
		        	ps.setDate(4,txtVrDate);
		        	ps.setInt(5,txtCB_Year);
		        	ps.setInt(6,txtCB_Month);
		        	 kk=ps.executeUpdate();
		        	if(kk> 0){
		        		sendMessage(response,"  Approved Successfully","OK","GPF_req_RJV_FormApp.jsp" );
		        	}else{
		        		sendMessage(response," Not Approved Successfully","OK","GPF_req_RJV_FormApp.jsp" );
		        	}
		        }
		        catch(Exception e){
		        	e.printStackTrace();
		        }
	 }
  }
	 
	 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test servlet");
		

		  try
	         {
		             HttpSession session=request.getSession(false);
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
	         Connection con=null;
	         ResultSet rs=null;
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0, txtrel_Year=0, txtrel_Month=0;
	         PreparedStatement ps=null,ps1=null,ps2=null;
	         java.sql.Date txtVrDate = null;
	         try 
	         {
	                 ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                 String ConnectionString="";
	                 String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                 String strdsn=rs1.getString("Config.DSN");
	                 String strhostname=rs1.getString("Config.HOST_NAME");
	                 String strportno=rs1.getString("Config.PORT_NUMBER");
	                 String strsid=rs1.getString("Config.SID");
	                 String strdbusername=rs1.getString("Config.USER_NAME");
	                 String strdbpassword=rs1.getString("Config.PASSWORD");
	                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                 Class.forName(strDriver.trim());
	                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	         }
	         catch(Exception e)
	         {
	        	 	 System.out.println("Exception in openeing connection :"+e);

	         }
	       
	         HttpSession session=request.getSession(false);
	         String userid=(String) session.getAttribute("UserId");
	         
	      //  System.out.println("servlet called");
	        String CONTENT_TYPE = "text/xml; charset=windows-1252";
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	     Calendar c;String docType ="";
	     int i1=0,i=1,j=0 ,adjYear =0,docNo=0,
         		adjMonth=0 ;
	     double txtJnl_Amt=0.0f;
	     int sl_no=1;
	        try{
	        	cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        try{
	        	cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }try{
	        	txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        try{
	        	txtrel_Month=Integer.parseInt(request.getParameter("txtrel_Month"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }try{
	        	txtrel_Year=Integer.parseInt(request.getParameter("txtrel_Year"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        try{
	        	txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
	        }catch(Exception e){
	        	e.printStackTrace();
	        }try{
	      String[] sd=request.getParameter("txtVrDate").split("/");
	      c=new java.util.GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	      java.util.Date d=c.getTime();
	      txtVrDate=new Date(d.getTime());
	      System.out.println("txtVrDate >> "+txtVrDate);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        String type_1=request.getParameter("type_1");
	        txtJnl_Amt=Double.parseDouble(request.getParameter("txtJnl_Amt"));
	        long l=System.currentTimeMillis();
	        Timestamp ts=new Timestamp(l);
	        int chk_avail=0;
	        try{
	        	ps1 = con
						.prepareStatement("select count(*) as availa from FAS_GPF_RJV_REQ_MST where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=?  And Journal_Amount           =? AND VOUCHER_DATE          =? and TYPE_OPTION=?");	
	        
	        	 ps1.setInt(1, cmbAcc_UnitCode);
	 	        ps1.setInt(2, cmbOffice_code);
	 	       ps1.setDouble(3, txtJnl_Amt); 
				ps1.setDate(4, txtVrDate);
				   ps1.setString(5, type_1);
				rs=ps1.executeQuery();
	 	       while (rs.next()) {
	 	    	  chk_avail=  rs.getInt(1);
	 	       }
	        System.out.println("chk_avail >> "+chk_avail);
	        }catch(Exception e){
	    
	        }finally{
	        	 try {
	        			ps1.close();
						  rs.close();
	        	 }catch(Exception e){
	        		    
	 	        }
	        }
	        System.out.println("chk_avail"+chk_avail);
	        if(chk_avail==0){
	        try {
				ps1 = con
						.prepareStatement("select MAX(REQUEST_NO) from FAS_GPF_RJV_REQ_MST ");
				rs = ps1.executeQuery();

				if (rs.next()) {
					i1 = rs.getInt(1);
					i = i + i1;
				} else {
					i = 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				 try {
					
						ps1.close();
						  rs.close();
		        	 }catch(Exception e){
		        		    
		 	        }
			}
	        int head_code=0,BANK_ID=0,BRANCH_ID=0;
	        		String ACCOUNT_NO="",txtRecei_from="";
	        if(type_1.equalsIgnoreCase("JR")){
	        	head_code=0;
	        BANK_ID=0;
	        BRANCH_ID=0;
	        ACCOUNT_NO=null;
	        txtRecei_from="";
	        }
	        else{
	        	head_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));
	        	if(head_code==820101){
	        		  BANK_ID=0;
	      	        BRANCH_ID=0;
	      	        ACCOUNT_NO=null;
	      	        txtRecei_from=request.getParameter("txtRecei_from");
	        	}else{
		        BANK_ID=Integer.parseInt(request.getParameter("txtBankId"));
		        BRANCH_ID=Integer.parseInt(request.getParameter("txtBranchId"));
		        ACCOUNT_NO=request.getParameter("txtBankAccountNo");
		        txtRecei_from=request.getParameter("txtRecei_from");
	        	}
	        }
	       try{
	    	   con.clearWarnings();
	             con.setAutoCommit(false);
	         ps=con.prepareStatement("insert into FAS_GPF_RJV_REQ_MST (ACCOUNTING_UNIT_ID,"+
"ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,UPDATED_BY_USERID,UPDATED_DATE,REQUEST_NO,"+
"JOURNAL_AMOUNT,VOUCHER_DATE,STATUS,RELATIVECB_MONTH,RELATIVECB_YEAR,HEAD_CODE,TYPE_OPTION,ACCOUNT_NO,BANK_ID,BRANCH_ID,RECEIVED_FROM) values(?,?,?,?,?,?,?,?,?,'L',?,?,?,?,?::numeric,?,?,?)");
	        ps.setInt(1, cmbAcc_UnitCode);
	        ps.setInt(2, cmbOffice_code);
	        ps.setInt(3, txtCB_Year);
	        ps.setInt(4, txtCB_Month);
	        ps.setString(5, userid);
	        ps.setTimestamp(6, ts);
	        ps.setInt(7, i);
	        ps.setDouble(8, txtJnl_Amt); 
			ps.setDate(9, txtVrDate);
		    ps.setInt(10, txtrel_Month);
	        ps.setInt(11, txtrel_Year);
	        ps.setInt(12, head_code);
	        ps.setString(13, type_1);
	        ps.setString(14, ACCOUNT_NO);
	        ps.setInt(15, BANK_ID);
	        ps.setInt(16, BRANCH_ID);
	        ps.setString(17, txtRecei_from);
	        
			int k=ps.executeUpdate();
			if(k==0){
				con.rollback();
				sendMessage(response," Not Saved Successfully","OK","GPF_req_RJV_Form.jsp" );
				return;
			}else{
				String[] head=request.getParameterValues("head");
				String[] cDr_Type=request.getParameterValues("cDr_Type");
				String[] grid_amt=request.getParameterValues("grid_amt");
				String[] SlType=request.getParameterValues("grid_Sltype");
				String[] SlTypeCode=request.getParameterValues("grid_SlCode");
				String[] grid_part=request.getParameterValues("grid_part");
				String Grid_adj_year[] = request
						.getParameterValues("adj_year");
				String Grid_adj_month[] = request
						.getParameterValues("adj_month");

				String Grid_doc_no[] = request.getParameterValues("doc_no");
				String Grid_doc_type[] = request
						.getParameterValues("doc_type");
			
				System.out.println("grid_amt.length >> "+grid_amt.length);
				for(int kk=0;kk<grid_amt.length;kk++){
					try {
						 adjYear = Integer.parseInt(Grid_adj_year[kk]);
						 adjMonth = Integer.parseInt(Grid_adj_month[kk]);
						 docNo = Integer.parseInt(Grid_doc_no[kk]);
					} catch (Exception e) {
						System.out
								.println("this is not have doc type and voucherno");
					}
					 docType = Grid_doc_type[kk];
					if (docType.equalsIgnoreCase("")) {
						docType = null;
					}
					System.out.println("adjYear"+adjYear);
					
					
				try{
				ps2=con.prepareStatement("insert into FAS_GPF_RJV_REQ_TRN (ACCOUNTING_UNIT_ID,"+
						"ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,UPDATED_BY_USERID,UPDATED_DATE,REQUEST_NO,"+
						"ACCOUNT_HEAD_CODE,AMOUNT,CR_DR_TYPE,sl_no,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,PARTICULARS,ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH,ADJ_DOC_TYPE,ADJ_DOC_NO) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps2.setInt(1, cmbAcc_UnitCode);
				ps2.setInt(2, cmbOffice_code);
				ps2.setInt(3, txtCB_Year);
				ps2.setInt(4, txtCB_Month);
				ps2.setString(5, userid);
				ps2.setTimestamp(6, ts);
				ps2.setInt(7, i);
				System.out.println( Integer.parseInt(head[kk]));
				ps2.setInt(8, Integer.parseInt(head[kk]));
				System.out.println( Double.parseDouble(grid_amt[kk]));
				ps2.setDouble(9, Double.parseDouble(grid_amt[kk]));
				System.out.println( cDr_Type[kk]);
				ps2.setString(10, cDr_Type[kk]);
			
				ps2.setInt(11, sl_no);
				ps2.setInt(12, Integer.parseInt(SlType[kk]));
				ps2.setInt(13, Integer.parseInt(SlTypeCode[kk]));
				ps2.setString(14, grid_part[kk]);
				ps2.setInt(15, adjYear);
				ps2.setInt(16, adjMonth);
				ps2.setString(17, docType);
				ps2.setInt(18, docNo);
				int cnt=ps2.executeUpdate();
				if(cnt==0){
					j=j+1;
				}else{
					j=0;
					sl_no++;
				}
				}catch(Exception e){
					j=1;
					e.printStackTrace();
				}
				 adjYear =0;
				 adjMonth  =0;
				 docNo  =0;
				 docType ="";
				
				
				
			}
				if(j==0){
					con.commit();
					sendMessage(response," Saved Successfully","OK","GPF_req_RJV_Form.jsp" );
					return;
				}else{
					con.rollback();
					sendMessage(response," Not Saved Successfully","OK","GPF_req_RJV_Form.jsp" );
					return;
				
				}
			}
	       
	       }catch(Exception e){
	    		
					try {
						con.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
	    		sendMessage(response," Not Saved Successfully","OK","GPF_req_RJV_Form.jsp" );
	    		return;
	    	
	       }  finally
           {
               System.out.println("done");
               try{con.setAutoCommit(true);  }catch(SQLException sqle){}
           }
	        }else{
	        	sendMessage(response," Record Exist ","OK","GPF_req_RJV_Form.jsp" );
		    	  return;
	        }
	}



private void sendMessage(HttpServletResponse response, String msg,
		String bType, String jsp) {
	try {
		String url = "org/FAS/FAS1/CivilBills/jsps/MessengerOkBack.jsp?message="
				+ msg + "&button=" + bType + "&jspname=" + jsp;
		response.sendRedirect(url);
		return;
	} catch (IOException e) {
		e.printStackTrace();
	}
}

}
