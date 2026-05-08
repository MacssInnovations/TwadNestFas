package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.*;
import javax.servlet.http.*;


import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;
import Servlets.FAS.FAS1.CommonClass.FASCommon;

public class Cheque_Number_details extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7209405360295024722L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
    	response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String strCommand = "";
		String xml = "";
		LoadDriver load = new LoadDriver();
    	Connection connection = null;
    	ResultSet resultSet = null;
    	PreparedStatement preparedStatement = null;
    	connection = load.getConnection();
    	String sql ="" ;
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try {
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			strCommand = request.getParameter("command");
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strCommand.equalsIgnoreCase("chequeRange")) {
			xml = "";
			int change = 0;
			int accunitId = Integer.parseInt(request.getParameter("accunitId"));
			long accountNo = Long.parseLong(request.getParameter("accountNo"));
			try {   				
				sql="SELECT ACCOUNTING_UNIT_ID, " +
				"  ACCOUNTING_FOR_OFFICE_ID, " +
				"  BANK_ID, " +
				"  BRANCH_ID, " +
				"  ACCOUNT_NO, " +
				"  START_LEAF_NO, " +
				"  END_LEAF_NO, " +
				"  CHEQUE_BOOK_CODE, " +
				"  STATUS " +
				"FROM COM_MST_CHEQUE_BOOKS_SL " +
				"WHERE ACCOUNTING_UNIT_ID=? " +
				"AND ACCOUNT_NO          =? " +
				"AND status              ='L'" +
				" ORDER BY START_LEAF_NO";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, accunitId);
				preparedStatement.setLong(2, accountNo);
				resultSet = preparedStatement.executeQuery();
				xml = "<response>";
				while(resultSet.next()){
					xml +="<leafvalue>"+resultSet.getString("START_LEAF_NO")+"-"+resultSet.getString("END_LEAF_NO")+"</leafvalue>" +
					  "<leafdesc>"+resultSet.getString("START_LEAF_NO")+"-"+resultSet.getString("END_LEAF_NO")+"</leafdesc>" +
					"<chequecode>"+resultSet.getString("CHEQUE_BOOK_CODE")+"</chequecode>";
					change++;
				}
				if(change == 0){
					xml +="<status>fail</status>";
				}else{
					xml +="<status>success</status>";
				}
				xml += "</response>";  				
			} catch (SQLException e) {				
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
			out.write(xml);
			out.flush();
			out.close();

		}else if (strCommand.equalsIgnoreCase("bankaccNo")) {
			xml = "";
			int change = 0;
			int accunitId = Integer.parseInt(request.getParameter("accunitId"));			
			try {   				
				sql="SELECT BANK_AC_NO, " +
				"  BANK_AC_TYPE_ID " +
				"  ||'-' " +
				"  ||bank_ac_no " +
				"  ||'-' " +
				"  ||AC_OPERATIONAL_MODE_ID AS bankacno_type " +
				"FROM FAS_MST_BANK_BALANCE " +
				"WHERE accounting_unit_id    =? " +
				"AND AC_OPERATIONAL_MODE_ID IN ('OPR')";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, accunitId);				
				resultSet = preparedStatement.executeQuery();
				xml = "<response>";
				while(resultSet.next()){
					xml +="<bankacno>"+resultSet.getLong("bank_ac_no")+"</bankacno>" +
					"<bankactype>"+resultSet.getString("bankacno_type")+"</bankactype>";
					change++;
				}
				if(change == 0){
					xml +="<status>fail</status>";
				}else{
					xml +="<status>success</status>";
				}
				xml += "</response>";  				
			} catch (SQLException e) {				
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
			out.write(xml);
			out.flush();
			out.close();

		}
        
    }    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
    	LoadDriver load = new LoadDriver();
    	FASCommon common = new FASCommon();
    	Connection connection = null;
    	ResultSet resultSet = null;
    	PreparedStatement preparedStatement = null;
    	connection = load.getConnection();
    	String sql ="",xml="";
    	response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();    	
    	int accountUnitId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));   	
    	long accountNo = Long.parseLong(request.getParameter("txtBankAccountNo"));
    	String chequeLeaf = request.getParameter("startEndLeaf");
    	String[] startEndLeaf = chequeLeaf.split(",");
    	String[] startEnd = startEndLeaf[1].split("-");
    	int startLeaf = Integer.parseInt(startEnd[0]);
    	int endLeaf = Integer.parseInt(startEnd[1]);
    	String commond = request.getParameter("check");
    	if(commond.equalsIgnoreCase("asondate")){
    		java.sql.Date asonDate = common.date_convertion(request.getParameter("asonDate"));        	
        	Set<CheckNumberBean> set = new TreeSet<CheckNumberBean>();
        	CheckNumberBean nextCheque = null;
        	System.out.println("START12  "+accountUnitId+" "+accountNo+" "+startLeaf+" "+endLeaf+" "+asonDate);
        	try {		
        		sql="SELECT ACCOUNTING_UNIT_ID, " +
        		"  ACCOUNTING_FOR_OFFICE_ID, " +
        		"  CASHBOOK_YEAR, " +
        		"  CASHBOOK_MONTH, " +
        		"  ACCOUNT_HEAD_CODE, " +
        		"  ACCOUNT_NO, " +
        		"  CHEQUE_DD_NO, " +
        		"  AMOUNT, " +
        		"  COPERATIONALMODE, " +
        		"  PAYMENT_DATE " +
        		"FROM " +
        		"  (SELECT a.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, " +
        		"    a.ACCOUNTING_FOR_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID, " +
        		"    a.CASHBOOK_YEAR            AS CASHBOOK_YEAR, " +
        		"    a.CASHBOOK_MONTH           AS CASHBOOK_MONTH, " +
        		"    a.ACCOUNT_HEAD_CODE        AS ACCOUNT_HEAD_CODE, " +
        		"    a.ACCOUNT_NO               AS ACCOUNT_NO, " +
        		"    a.CHEQUE_DD_NO             AS CHEQUE_DD_NO, " +
        		"    a.AMOUNT                   AS AMOUNT, " +
        		"    c.AC_OPERATIONAL_MODE_ID   AS COPERATIONALMODE, " +
        		"    d.PAYMENT_DATE             AS PAYMENT_DATE " +
        		"  FROM " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      ACCOUNTING_FOR_OFFICE_ID, " +
        		"      CASHBOOK_YEAR, " +
        		"      CASHBOOK_MONTH, " +
        		"      VOUCHER_NO, " +
        		"      ACCOUNT_HEAD_CODE, " +
        		"      ACCOUNT_NO, " +
        		"      CHEQUE_DD_NO, " +
        		"      AMOUNT " +
        		"    FROM FAS_PAYMENT_TRANSACTION " +
        		"    WHERE ACCOUNTING_UNIT_ID =? " +
        		"    AND ACCOUNT_NO           =? " +
        		"    AND CHEQUE_DD_NO BETWEEN ?::varchar AND ?::varchar " +
        		"    AND CHEQUE_DD_DATE::varchar<=? " +
        		"    )a " +
        		"  LEFT OUTER JOIN " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      BANK_AC_NO, " +
        		"      AC_OPERATIONAL_MODE_ID " +
        		"    FROM FAS_MST_BANK_BALANCE " +
        		"    WHERE AC_OPERATIONAL_MODE_ID='OPR' " +
        		"    )c " +
        		"  ON a.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID " +
        		"  AND a.ACCOUNT_NO       =c.BANK_AC_NO " +
        		"  LEFT OUTER JOIN " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      TO_CHAR(PAYMENT_DATE,'dd/MM/yyyy') AS PAYMENT_DATE, " +
        		"      CASHBOOK_YEAR, " +
        		"      CASHBOOK_MONTH, " +
        		"      ACCOUNT_NO, " +
        		"      VOUCHER_NO " +
        		"    FROM FAS_PAYMENT_MASTER " +
        		"    WHERE PAYMENT_STATUS='L' " +
        		"    )d " +
        		"  ON a.ACCOUNTING_UNIT_ID=d.ACCOUNTING_UNIT_ID " +
        		"  AND a.CASHBOOK_YEAR    =d.CASHBOOK_YEAR " +
        		"  AND a.CASHBOOK_MONTH   =d.CASHBOOK_MONTH " +
        		"  AND a.VOUCHER_NO       =d.VOUCHER_NO " +
        		"  AND a.ACCOUNT_NO       =d.ACCOUNT_NO " +
        		"  UNION ALL " +
        		"  SELECT bb.ACCOUNTING_UNIT_ID  AS ACCOUNTING_UNIT_ID, " +
        		"    bb.ACCOUNTING_FOR_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID, " +
        		"    bb.CASHBOOK_YEAR            AS CASHBOOK_YEAR, " +
        		"    bb.CASHBOOK_MONTH           AS CASHBOOK_MONTH, " +
        		"    bb.ACCOUNT_HEAD_CODE        AS ACCOUNT_HEAD_CODE, " +
        		"    aa.ACCOUNT_NO               AS ACCOUNT_NO, " +
        		"    bb.CHEQUE_DD_NO             AS CHEQUE_DD_NO, " +
        		"    bb.AMOUNT                   AS AMOUNT, " +
        		"    aa.RECEIPT_TYPE             AS COPERATIONALMODE, " +
        		"    RECEIPT_DATE                AS PAYMENT_DATE " +
        		"  FROM " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      ACCOUNTING_FOR_OFFICE_ID, " +
        		"      TO_CHAR(RECEIPT_DATE,'dd/MM/yyyy') AS RECEIPT_DATE, " +
        		"      CASHBOOK_YEAR, " +
        		"      CASHBOOK_MONTH, " +
        		"      RECEIPT_TYPE, " +
        		"      RECEIPT_NO, " +
        		"      ACCOUNT_HEAD_CODE, " +
        		"      CR_DR_INDICATOR, " +
        		"      BANK_ID, " +
        		"      BRANCH_ID, " +
        		"      ACCOUNT_NO, " +
        		"      RECEIVED_FROM, " +
        		"      RECEIPT_STATUS " +
        		"    FROM FAS_RECEIPT_MASTER " +
        		"    WHERE RECEIPT_STATUS='L' " +
        		"    )aa " +
        		"  LEFT OUTER JOIN " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      ACCOUNTING_FOR_OFFICE_ID, " +
        		"      CASHBOOK_YEAR, " +
        		"      CASHBOOK_MONTH, " +
        		"      RECEIPT_NO, " +
        		"      SL_NO, " +
        		"      ACCOUNT_HEAD_CODE, " +
        		"      CR_DR_INDICATOR, " +
        		"      SUB_LEDGER_TYPE_CODE, " +
        		"      SUB_LEDGER_CODE, " +
        		"      RECEIVED_FROM, " +
        		"      CHEQUE_OR_DD, " +
        		"      CHEQUE_DD_NO, " +
        		"      CHEQUE_DD_DATE, " +
        		"      BANK_NAME, " +
        		"      AMOUNT " +
        		"    FROM FAS_RECEIPT_TRANSACTION " +
        		"    )bb " +
        		"  ON aa.ACCOUNTING_UNIT_ID       =bb.ACCOUNTING_UNIT_ID " +
        		"  AND aa.ACCOUNTING_FOR_OFFICE_ID=bb.ACCOUNTING_FOR_OFFICE_ID " +
        		"  AND aa.CASHBOOK_YEAR           =bb.CASHBOOK_YEAR " +
        		"  AND aa.CASHBOOK_MONTH          =bb.CASHBOOK_MONTH " +
        		"  AND aa.RECEIPT_NO              =bb.RECEIPT_NO " +
        		"  WHERE aa.ACCOUNTING_UNIT_ID    =? " +
        		"  AND aa.ACCOUNT_NO              =? " +
        		"  AND bb.CHEQUE_DD_NO BETWEEN ?::varchar AND ?::varchar " +
      		"  AND bb.CHEQUE_DD_DATE::varchar<=? " +
        
  //      		"  ) as opt1" +
    		"  ) as opt1"; 
    //    		"ORDER BY CHEQUE_DD_NO";
    			preparedStatement = connection.prepareStatement(sql);
    			preparedStatement.setInt(1, accountUnitId);		
    			preparedStatement.setLong(2, accountNo);
    			preparedStatement.setInt(3, startLeaf);
    			preparedStatement.setInt(4, endLeaf);
    			preparedStatement.setDate(5, asonDate);
    			preparedStatement.setInt(6, accountUnitId);		
    			preparedStatement.setLong(7, accountNo);
    			preparedStatement.setInt(8, startLeaf);
    			preparedStatement.setInt(9, endLeaf);
    			preparedStatement.setDate(10, asonDate);
    			resultSet = preparedStatement.executeQuery();
    			while(resultSet.next()){    				
    				if(resultSet.getString("CHEQUE_DD_NO")!=null){
    					nextCheque = new CheckNumberBean();
    					nextCheque.setChequeNo(Integer.parseInt(resultSet.getString("CHEQUE_DD_NO").trim()));
    					nextCheque.setPaymentDate((resultSet.getString("PAYMENT_DATE")));
    					set.add(nextCheque);
    				}
    			}						
    			 List<CheckNumberBean> list = new ArrayList<CheckNumberBean>(set);
    			 CheckNumberBean bean = null;
    			 List<CheckNumberBean> list1 = new ArrayList<CheckNumberBean>();
    			 for(int i=startLeaf; i<=endLeaf; i++){
    				 bean = new CheckNumberBean();
    				 bean.setChequeNo(i);				 				 				 
    				 list1.add(bean);				 
    			 }
    			 Iterator<CheckNumberBean> it = list.iterator();
    				while(it.hasNext()){
    					CheckNumberBean element = it.next();
    					if(list1.contains(element)){
    						list1.remove(element);
    					}
    					
    				}
    				xml="<response>";
    				if(list1.isEmpty()){
    					xml+="<status>fail</status>";
    				}else{
    					xml+="<status>success</status>";
    				}
    				if(list.isEmpty()){
    					xml+="<startchequeno>-</startchequeno>";
    					xml+="<endchequeno>-</endchequeno>";
    					xml+="<startdate>-</startdate>";
    					xml+="<enddate>-</enddate>";
    				 }else{
    					 for(CheckNumberBean s: set){							
    							xml+="<startchequeno>"+s.getChequeNo()+"</startchequeno>";
    							xml+="<startdate>"+s.getPaymentDate()+"</startdate>";
    							break;
    						}
    					 for(int c=set.size()-1; c>0;){							
    							 xml+="<endchequeno>"+list.get(c).getChequeNo()+"</endchequeno>";					 
    							 xml+="<enddate>"+list.get(c).getPaymentDate()+"</enddate>";
    							break;
    						}					 
    				 }				
    			 for(CheckNumberBean a:list1){
    				 xml+="<chequeNo>"+a.getChequeNo()+"</chequeNo>" +
    				 	  "<startNo>"+startLeaf+"</startNo>" +
    				 	  "<endNo>"+endLeaf+"</endNo>";
    			 }	
    			 xml+="</response>";			 
    		} catch (SQLException e) {			
    			xml="<response><status>fail</status></response>";
    			e.printStackTrace();
    		}
    	}else if(commond.equalsIgnoreCase("formonth")){
    		int cashYear = 0;
        	int cashMonth = 0;
        	cashYear = Integer.parseInt(request.getParameter("txtCB_Year"));
        	cashMonth = Integer.parseInt(request.getParameter("txtCB_Month"));
        	System.out.println("START14  "+accountUnitId+" "+accountNo+" "+startLeaf+" "+endLeaf);
        	Set<CheckNumberBean> set = new TreeSet<CheckNumberBean>();
        	CheckNumberBean nextCheque = null;
        	try {   			
        		sql="SELECT ACCOUNTING_UNIT_ID, " +
        		"  ACCOUNTING_FOR_OFFICE_ID, " +
        		"  CASHBOOK_YEAR, " +
        		"  CASHBOOK_MONTH, " +
        		"  ACCOUNT_HEAD_CODE, " +
        		"  ACCOUNT_NO, " +
        		"  CHEQUE_DD_NO, " +
        		"  AMOUNT, " +
        		"  COPERATIONALMODE, " +
        		"  PAYMENT_DATE " +
        		"FROM " +
        		"  (SELECT a.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, " +
        		"    a.ACCOUNTING_FOR_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID, " +
        		"    a.CASHBOOK_YEAR            AS CASHBOOK_YEAR, " +
        		"    a.CASHBOOK_MONTH           AS CASHBOOK_MONTH, " +
        		"    a.ACCOUNT_HEAD_CODE        AS ACCOUNT_HEAD_CODE, " +
        		"    a.ACCOUNT_NO               AS ACCOUNT_NO, " +
        		"    a.CHEQUE_DD_NO             AS CHEQUE_DD_NO, " +
        		"    a.AMOUNT                   AS AMOUNT, " +
        		"    c.AC_OPERATIONAL_MODE_ID   AS COPERATIONALMODE, " +
        		"    d.PAYMENT_DATE             AS PAYMENT_DATE " +
        		"  FROM " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      ACCOUNTING_FOR_OFFICE_ID, " +
        		"      CASHBOOK_YEAR, " +
        		"      CASHBOOK_MONTH, " +
        		"      VOUCHER_NO, " +
        		"      ACCOUNT_HEAD_CODE, " +
        		"      ACCOUNT_NO, " +
        		"      CHEQUE_DD_NO, " +
        		"      AMOUNT " +
        		"    FROM FAS_PAYMENT_TRANSACTION " +
        		"    WHERE ACCOUNTING_UNIT_ID =? " +
        		"    AND ACCOUNT_NO           =? " +
        		"    AND CHEQUE_DD_NO BETWEEN ?::varchar AND ?::varchar " +
        		"    AND CASHBOOK_YEAR =? " +
        		"    AND CASHBOOK_MONTH=? " +
        		"    )a " +
        		"  LEFT OUTER JOIN " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      BANK_AC_NO, " +
        		"      AC_OPERATIONAL_MODE_ID " +
        		"    FROM FAS_MST_BANK_BALANCE " +
        		"    WHERE AC_OPERATIONAL_MODE_ID='OPR' " +
        		"    )c " +
        		"  ON a.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID " +
        		"  AND a.ACCOUNT_NO       =c.BANK_AC_NO " +
        		"  LEFT OUTER JOIN " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      TO_CHAR(PAYMENT_DATE,'dd/MM/yyyy') AS PAYMENT_DATE, " +
        		"      CASHBOOK_YEAR, " +
        		"      CASHBOOK_MONTH, " +
        		"      ACCOUNT_NO, " +
        		"      VOUCHER_NO " +
        		"    FROM FAS_PAYMENT_MASTER " +
        		"    WHERE PAYMENT_STATUS='L' " +
        		"    )d " +
        		"  ON a.ACCOUNTING_UNIT_ID=d.ACCOUNTING_UNIT_ID " +
        		"  AND a.CASHBOOK_YEAR    =d.CASHBOOK_YEAR " +
        		"  AND a.CASHBOOK_MONTH   =d.CASHBOOK_MONTH " +
        		"  AND a.VOUCHER_NO       =d.VOUCHER_NO " +
        		"  AND a.ACCOUNT_NO       =d.ACCOUNT_NO " +
        		"  UNION ALL " +
        		"  SELECT bb.ACCOUNTING_UNIT_ID  AS ACCOUNTING_UNIT_ID, " +
        		"    bb.ACCOUNTING_FOR_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID, " +
        		"    bb.CASHBOOK_YEAR            AS CASHBOOK_YEAR, " +
        		"    bb.CASHBOOK_MONTH           AS CASHBOOK_MONTH, " +
        		"    bb.ACCOUNT_HEAD_CODE        AS ACCOUNT_HEAD_CODE, " +
        		"    aa.ACCOUNT_NO               AS ACCOUNT_NO, " +
        		"    bb.CHEQUE_DD_NO             AS CHEQUE_DD_NO, " +
        		"    bb.AMOUNT                   AS AMOUNT, " +
        		"    aa.RECEIPT_TYPE             AS COPERATIONALMODE, " +
        		"    RECEIPT_DATE                AS PAYMENT_DATE " +
        		"  FROM " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      ACCOUNTING_FOR_OFFICE_ID, " +
        		"      TO_CHAR(RECEIPT_DATE,'dd/MM/yyyy') AS RECEIPT_DATE, " +
        		"      CASHBOOK_YEAR, " +
        		"      CASHBOOK_MONTH, " +
        		"      RECEIPT_TYPE, " +
        		"      RECEIPT_NO, " +
        		"      ACCOUNT_HEAD_CODE, " +
        		"      CR_DR_INDICATOR, " +
        		"      BANK_ID, " +
        		"      BRANCH_ID, " +
        		"      ACCOUNT_NO, " +
        		"      RECEIVED_FROM, " +
        		"      RECEIPT_STATUS " +
        		"    FROM FAS_RECEIPT_MASTER " +
        		"    WHERE RECEIPT_STATUS='L' " +
        		"    )aa " +
        		"  LEFT OUTER JOIN " +
        		"    (SELECT ACCOUNTING_UNIT_ID, " +
        		"      ACCOUNTING_FOR_OFFICE_ID, " +
        		"      CASHBOOK_YEAR, " +
        		"      CASHBOOK_MONTH, " +
        		"      RECEIPT_NO, " +
        		"      SL_NO, " +
        		"      ACCOUNT_HEAD_CODE, " +
        		"      CR_DR_INDICATOR, " +
        		"      SUB_LEDGER_TYPE_CODE, " +
        		"      SUB_LEDGER_CODE, " +
        		"      RECEIVED_FROM, " +
        		"      CHEQUE_OR_DD, " +
        		"      CHEQUE_DD_NO, " +
        		"      CHEQUE_DD_DATE, " +
        		"      BANK_NAME, " +
        		"      AMOUNT " +
        		"    FROM FAS_RECEIPT_TRANSACTION " +
        		"    )bb " +
        		"  ON aa.ACCOUNTING_UNIT_ID       =bb.ACCOUNTING_UNIT_ID " +
        		"  AND aa.ACCOUNTING_FOR_OFFICE_ID=bb.ACCOUNTING_FOR_OFFICE_ID " +
        		"  AND aa.CASHBOOK_YEAR           =bb.CASHBOOK_YEAR " +
        		"  AND aa.CASHBOOK_MONTH          =bb.CASHBOOK_MONTH " +
        		"  AND aa.RECEIPT_NO              =bb.RECEIPT_NO " +
        		"  WHERE aa.ACCOUNTING_UNIT_ID    =? " +
        		"  AND aa.ACCOUNT_NO              =? " +
        		"  AND bb.CHEQUE_DD_NO BETWEEN ?::varchar AND ?::varchar " +
        		"  AND bb.CASHBOOK_YEAR =? " +
        		"  AND bb.CASHBOOK_MONTH=? " +
  //      		"  ) as opt1 " +
        		"  ) as opt1 " ;
  //      		"ORDER BY CHEQUE_DD_NO";
    			preparedStatement = connection.prepareStatement(sql);
    			preparedStatement.setInt(1, accountUnitId);		
    			preparedStatement.setLong(2, accountNo);
    			preparedStatement.setInt(3, startLeaf);
    			preparedStatement.setInt(4, endLeaf);
    			preparedStatement.setInt(5, cashYear);
				preparedStatement.setInt(6, cashMonth);
				preparedStatement.setInt(7, accountUnitId);		
    			preparedStatement.setLong(8, accountNo);
    			preparedStatement.setInt(9, startLeaf);
    			preparedStatement.setInt(10, endLeaf);
    			preparedStatement.setInt(11, cashYear);
				preparedStatement.setInt(12, cashMonth);
    			resultSet = preparedStatement.executeQuery();
    			while(resultSet.next()){
    				if(resultSet.getString("CHEQUE_DD_NO")!=null){
    					nextCheque = new CheckNumberBean();
    					nextCheque.setChequeNo(Integer.parseInt(resultSet.getString("CHEQUE_DD_NO").trim()));
    					nextCheque.setPaymentDate((resultSet.getString("PAYMENT_DATE")));
    					set.add(nextCheque);
    				}
    			}
    			xml="<response>";
    			 List<CheckNumberBean> list = new ArrayList<CheckNumberBean>(set);
    			 int startL=0,endL=0;
    			 if(list.isEmpty()){
 					xml+="<startchequeno>-</startchequeno>";
 					xml+="<endchequeno>-</endchequeno>";
 					xml+="<startdate>-</startdate>";
 					xml+="<enddate>-</enddate>";
 				 }else{
 					 for(CheckNumberBean s: set){							
 							xml+="<startchequeno>"+s.getChequeNo()+"</startchequeno>";
 							xml+="<startdate>"+s.getPaymentDate()+"</startdate>";
 							startL=s.getChequeNo();
 							break;
 						}
 					 for(int c=set.size()-1; c>0;){							
 							 xml+="<endchequeno>"+list.get(c).getChequeNo()+"</endchequeno>";					 
 							 xml+="<enddate>"+list.get(c).getPaymentDate()+"</enddate>";
 							 endL=list.get(c).getChequeNo();
 							break;
 						}					 
 				 }
    			 CheckNumberBean bean = null;
    			 List<CheckNumberBean> list1 = new ArrayList<CheckNumberBean>();
    			 for(int i=startL; i<=endL; i++){
    				 bean = new CheckNumberBean();
    				 bean.setChequeNo(i);				 				 				 
    				 list1.add(bean);				 
    			 }
    			 Iterator<CheckNumberBean> it = list.iterator();
    				while(it.hasNext()){
    					CheckNumberBean element = it.next();
    					if(list1.contains(element)){
    						list1.remove(element);
    					}
    					
    				}
    				
    				if(list1.isEmpty()){
    					xml+="<status>fail</status>";
    				}else{
    					xml+="<status>success</status>";
    				}
    			 for(CheckNumberBean a:list1){
    				 xml+="<chequeNo>"+a.getChequeNo()+"</chequeNo>" +
    				 	  "<startNo>"+startL+"</startNo>" +
    				 	  "<endNo>"+endL+"</endNo>";
    			 }	
    			 xml+="</response>";			 
    		} catch (SQLException e) {			
    			xml="<response><status>fail</status></response>";
    			e.printStackTrace();
    		}
    	}
		out.write(xml);
		out.flush();
		out.close();
    }
}
