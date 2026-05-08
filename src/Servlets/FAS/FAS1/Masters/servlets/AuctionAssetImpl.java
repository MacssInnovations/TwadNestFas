package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import Servlets.FAS.FAS1.CivilBills.servlets.BillTypeMasterImpl;
import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

public class AuctionAssetImpl implements AuctionAssetsService{
	
	Connection connection = null;
	ResultSet resultSet = null;	
	LoadDriver load = new LoadDriver();
	private String sql = "";
	private String xml;
	private int count;
	private int errorCode;

	
	public String getAuctionOffice() throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		sql = "select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES";
		preparedStatement=connection.prepareStatement(sql);        
        resultSet=preparedStatement.executeQuery();
        xml="<response><command>getOffice</command>";
        while(resultSet.next()){
        	preparedStatement1=connection.prepareStatement("select OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
        	preparedStatement1.setInt(1,resultSet.getInt("ACCOUNTING_FOR_OFFICE_ID"));
        	resultSet1=preparedStatement1.executeQuery();
        	if(resultSet1.next()){        		
        		xml +="<OFFICE_ID>"+resultSet1.getInt("OFFICE_ID")+"</OFFICE_ID>" +
        			  "<OFFICE_NAME>"+resultSet1.getString("OFFICE_NAME")+"</OFFICE_NAME>";
        	}
        	count++;
        }
        if(count==0){
        	xml +="<status>nodata</status>";
        }else{
        	xml +="<status>success</status>";
        }
        xml +="</response>";
		return xml;
	}

	
	public String surveyReportNo(int unitCode, int officeCode) throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		xml = "<response><command>surveyNo</command>";
		sql ="SELECT DISTINCT SURVEY_NO, " +
		"  ASSET_CODE " +
		"FROM FAS_ASSET_SURVEY_REPORT " +
		"WHERE ACCOUNTING_UNIT_ID    =? " +
		"AND ACCOUNTING_FOR_OFFICE_ID=? " +
		"AND (STATUS                IS NULL " +
		"OR STATUS                  <>'y')" +
		"ORDER BY SURVEY_NO";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, unitCode);
		preparedStatement.setInt(2, officeCode);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<SURVEY_NO>"+resultSet.getInt("SURVEY_NO")+"</SURVEY_NO>";				  
			count++;
		}
		if(count==0){
			xml +="<status>nodata</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		return xml;
	}
	

	
	public Set<AuctionAssetBean> surveyReportNo(int unitCode, int officeCode, String check)
			throws SQLException {
		// TODO Auto-generated method stub
		Set<AuctionAssetBean> set = new HashSet<AuctionAssetBean>();
		AuctionAssetBean bean = null;
		xml = "";
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		//xml = "<response><command>surveyNo</command>";
		sql ="SELECT * " +
		"FROM FAS_ASSET_SURVEY_REPORT " +
		"WHERE ACCOUNTING_UNIT_ID    =? " +
		"AND ACCOUNTING_FOR_OFFICE_ID=? " +
		"AND STATUS                  ='y'" ;
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, unitCode);
		preparedStatement.setInt(2, officeCode);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			bean = new AuctionAssetBean();
			bean.setAccountId(resultSet.getInt("ACCOUNTING_UNIT_ID"));
			bean.setOfficeId(resultSet.getInt("ACCOUNTING_FOR_OFFICE_ID"));
			bean.setSurveyNo(resultSet.getString("SURVEY_NO"));
			bean.setDesignation(resultSet.getInt("DESIGNATION"));
			bean.setAssetCode(resultSet.getInt("ASSET_CODE"));
			bean.setRefNo(resultSet.getInt("REF_NO"));
			bean.setRefDate(resultSet.getDate("REF_DATE"));
			bean.setStatus(resultSet.getString("STATUS"));
			bean.setPosting(resultSet.getInt("POSTING"));
			bean.setRemarks(resultSet.getString("REMARKS"));
			set.add(bean);
			//xml +="<SURVEY_NO>"+resultSet.getInt("SURVEY_NO")+"</SURVEY_NO>";				  
			//count++;
		}
		/*if(count==0){
			xml +="<status>nodata</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";*/
		return set;
	}

	
	public String viewAuctionedDetails(int unitCode, int officeCode,
			int surveyCode, int assetCode) throws SQLException {
		// TODO Auto-generated method stub
		xml ="";
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		xml = "<response><command>assetcode</command>";
		sql ="";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, unitCode);
		preparedStatement.setInt(2, officeCode);
		preparedStatement.setInt(3, surveyCode);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<ASSET_CODE>"+resultSet.getInt("ASSET_CODE")+"</ASSET_CODE>";				  
			count++;
		}
		if(count==0){
			xml +="<status>nodata</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		return xml;
	}

	
	public String assetCode(int unitCode, int officeCode, int surveyCode) throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		xml = "<response><command>assetcode</command>";
		sql ="SELECT SURVEY_NO, " +
		"ASSET_CODE "+
		"FROM FAS_ASSET_SURVEY_REPORT " +
		"WHERE ACCOUNTING_UNIT_ID    =? " +
		"AND ACCOUNTING_FOR_OFFICE_ID=? " +
		"AND SURVEY_NO=? "+
		"AND (STATUS                IS NULL " +
		"OR STATUS                  <>'y')" +
		"ORDER BY ASSET_CODE";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, unitCode);
		preparedStatement.setInt(2, officeCode);
		preparedStatement.setInt(3, surveyCode);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<ASSET_CODE>"+resultSet.getInt("ASSET_CODE")+"</ASSET_CODE>";				  
			count++;
		}
		if(count==0){
			xml +="<status>nodata</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		return xml;
	}
	
	public String assetCode(int unitCode, int officeCode, int surveyCode,
			String check) throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		xml = "<response><command>assetcode</command>";
		sql ="SELECT SURVEY_NO, " +
		"  ASSET_CODE " +
		"FROM FAS_ASSET_SURVEY_REPORT " +
		"WHERE ACCOUNTING_UNIT_ID    =? " +
		"AND ACCOUNTING_FOR_OFFICE_ID=? " +
		"AND SURVEY_NO               =? " +
		"AND STATUS                  ='y' " +
		"ORDER BY ASSET_CODE";		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, unitCode);
		preparedStatement.setInt(2, officeCode);
		preparedStatement.setInt(3, surveyCode);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<ASSET_CODE>"+resultSet.getInt("ASSET_CODE")+"</ASSET_CODE>";				  
			count++;
		}
		if(count==0){
			xml +="<status>nodata</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		return xml;
	}

	
	public String addAssetAuction(int accountUnitId, int accountOfficeId,
			String financialYear, int surveyNo, Date surveyDate,
			int auctionOffice, int assetCode, String userid)
			throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		sql = "";
		xml = "<response><command>add</command>";		
		CallableStatement callableStatement = null;
		connection = load.getConnection();
		sql="{call FAS_ACUTION_ASSET_TOBE_PROC(?,?,?,?,?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, accountUnitId);
		callableStatement.setInt(2, accountOfficeId);
		callableStatement.setString(3, financialYear);
		callableStatement.setInt(4, surveyNo);
		callableStatement.setDate(5, surveyDate);
		callableStatement.setInt(6, auctionOffice);
		callableStatement.setInt(7, assetCode);
		callableStatement.setString(8, userid);
		callableStatement.setString(9, "insert");
		callableStatement.registerOutParameter(10, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(10);
		if(errorCode !=0){
			xml += "<status>success</status><flag>not</flag>";
		}else{
			xml +="<status>success</status><flag>success</flag>";
		}
		xml +="</response>";
		return xml;
	}

	
	public Map<String,Integer> addAuctionDoneMaster(int accountUnitId, int accountOfficeId,
			int surveyNo, Date auctionDate, int auctionOffice,
			String[] assetAuction, String[] referenceNo,
			String[] referenceDate, String[] auctioneer,
			String[] auctionAmount, String[] remarks, String userid)
			throws SQLException {
		// TODO Auto-generated method stub
		int referNo = 0,assetAuc = 0,auctionAmt = 0,len=0,trnCode=-1,count=0;
		Date referDate;		
		String sql1 = "",aucteer="",remark="";
		int auctionNo = 1;
		Map<String,Integer> map = new HashMap<String,Integer>();
		BillTypeMasterImpl dat = new BillTypeMasterImpl();
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		CallableStatement callableStatement = null;
		CallableStatement callableStatement1 = null;		
		sql="select max(AUCTION_NO) as AUCTION_NO from FAS_AUCTION_DONE_MST where SURVEY_NO=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, surveyNo);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			auctionNo = resultSet.getInt("AUCTION_NO");
			auctionNo += 1;
		}
		sql="{call FAS_AUCTION_DONE_MST_PROC(?,?,?,?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, accountUnitId);
		callableStatement.setInt(2, accountOfficeId);
		callableStatement.setInt(3, surveyNo);
		callableStatement.setInt(4, auctionNo);
		callableStatement.setDate(5, auctionDate);
		callableStatement.setInt(6, auctionOffice);
		callableStatement.setString(7, userid);
		callableStatement.setString(8, "insert");
		callableStatement.registerOutParameter(9, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(9);		
		if(errorCode !=0){
			map.put("first",errorCode);
		}else{
			len = assetAuction.length;
			for(int i = 0; i<len; i++){
				sql1="{call FAS_AUCTION_DONE_TRN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
				callableStatement1 = connection.prepareCall(sql1);
				//System.out.println("assetAuction[i] "+assetAuction[i]);
				assetAuc = Integer.parseInt(assetAuction[i]);
				referNo = Integer.parseInt(referenceNo[i]);
				referDate = dat.date_convertion(referenceDate[i]);
				aucteer = auctioneer[i];
				auctionAmt = Integer.parseInt(auctionAmount[i]);
				remark = remarks[i];
				callableStatement1.setInt(1, accountUnitId);
				callableStatement1.setInt(2, accountOfficeId);
				callableStatement1.setInt(3, surveyNo);
				callableStatement1.setInt(4, auctionNo);
				callableStatement1.setInt(5, assetAuc);
				callableStatement1.setInt(6, referNo);
				callableStatement1.setDate(7, referDate);
				callableStatement1.setString(8, aucteer);
				callableStatement1.setInt(9, auctionAmt);
				callableStatement1.setString(10, userid);
				callableStatement1.setString(11, remark);
				callableStatement1.setString(12, "insert");
				callableStatement1.registerOutParameter(13, Types.INTEGER);
				callableStatement1.executeUpdate();
				trnCode = callableStatement1.getInt(13);
				count++;
			}
			map.put("first",errorCode);
		}
		if(errorCode ==0 && count==0){
			map.put("firsts",count);
			map.put("second",count);
		}else if(errorCode ==0 && count>0){
			map.put("firsts",count);
			map.put("second",count);						
			len = assetAuction.length;
			for(int i=0; i<len; i++){
				sql1="UPDATE FAS_ASSET_SURVEY_REPORT " +
				"SET STATUS                  ='y' " +
				"WHERE ACCOUNTING_UNIT_ID    =? " +
				"AND ACCOUNTING_FOR_OFFICE_ID=? " +
				"AND SURVEY_NO               =? " +
				"AND ASSET_CODE              =?";
				preparedStatement1 = connection.prepareStatement(sql1);
				preparedStatement1.setInt(1, accountUnitId);
				preparedStatement1.setInt(2, accountOfficeId);
				preparedStatement1.setInt(3, surveyNo);
				preparedStatement1.setInt(4, Integer.parseInt(assetAuction[i]));
				preparedStatement1.executeUpdate();
			}			
		}
		return map;
	}

	
	public void sendMessage(HttpServletResponse response,String msg,String bType){
	        try{
	             String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
	             response.sendRedirect(url);
	        }catch(IOException e){
	                System.out.println("Exception arised :"+e);
	        }
	}

	
	public String viewDetails(int accountUnitId, int accountOfficeId, String surveyNo, String assetAuctioin, String category) throws SQLException {
		// TODO Auto-generated method stub
		xml ="";
		sql ="";
		count = 0;
		int survey = 0;
		int asset = 0;
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		xml ="<response><command>get</command>";
		if(category.equals("asset")){
			survey = Integer.parseInt(surveyNo);
			asset = Integer.parseInt(assetAuctioin);
			sql="SELECT ACCOUNTING_UNIT_ID, " +
			"  ACCOUNTING_FOR_OFFICE_ID, " +
			"  SURVEY_NO, " +
			"  AUCTION_NO, " +
			"  ASSET_CODE, " +
			"  REF_NO, " +
			"  to_char(REF_DATE, 'dd/MM/yyyy') AS REF_DATE," +
			"  AUCTIONER, " +
			"  AUCTION_AMOUNT, " +
			"  REMARKS " +
			"FROM FAS_AUCTION_DONE_TRN " +
			"WHERE ACCOUNTING_UNIT_ID    =? " +
			"AND ACCOUNTING_FOR_OFFICE_ID=?" +
			"AND SURVEY_NO    =? " +
			"AND ASSET_CODE=?" ;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountUnitId);
			preparedStatement.setInt(2, accountOfficeId);
			preparedStatement.setInt(3, survey);
			preparedStatement.setInt(4, asset);		
		}else{
			sql="SELECT ACCOUNTING_UNIT_ID, " +
			"  ACCOUNTING_FOR_OFFICE_ID, " +
			"  SURVEY_NO, " +
			"  AUCTION_NO, " +
			"  ASSET_CODE, " +
			"  REF_NO, " +
			"  to_char(REF_DATE, 'dd/MM/yyyy') AS REF_DATE," +
			"  AUCTIONER, " +
			"  AUCTION_AMOUNT, " +
			"  REMARKS " +
			"FROM FAS_AUCTION_DONE_TRN " +
			"WHERE ACCOUNTING_UNIT_ID    =? " +
			"AND ACCOUNTING_FOR_OFFICE_ID=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountUnitId);
			preparedStatement.setInt(2, accountOfficeId);
		}
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<SURVEY_NO>"+resultSet.getInt("SURVEY_NO")+"</SURVEY_NO>" +
				  "<AUCTION_NO>"+resultSet.getInt("AUCTION_NO")+"</AUCTION_NO>" +
				  "<ASSET_CODE>"+resultSet.getInt("ASSET_CODE")+"</ASSET_CODE>" +
				  "<REF_NO>"+resultSet.getInt("REF_NO")+"</REF_NO>" +
				  "<REF_DATE>"+resultSet.getString("REF_DATE")+"</REF_DATE>" +
				  "<AUCTIONER>"+resultSet.getString("AUCTIONER")+"</AUCTIONER>" +
				  "<AUCTION_AMOUNT>"+resultSet.getInt("AUCTION_AMOUNT")+"</AUCTION_AMOUNT>" +
				  "<REMARKS>"+resultSet.getString("REMARKS")+"</REMARKS>";
			count++;
		}
		if(count==0){
			xml +="<status>fail</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		return xml;
	}

	
	public String getAuctionDetails(int unitCode, int officeCode, int surveyNo,
			int assetcode) throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		PreparedStatement preparedStatement = null;
		connection = load.getConnection();
		sql="SELECT aa.AUCTION_NO     AS AUCTION_NO, " +
		"  aa.AUCTION_AMOUNT      AS AUCTION_AMOUNT, " +
		"  bb.AUCTION_DATE        AS AUCTION_DATE, " +
		"  bb.AUCTIONED_AT_OFFICE AS AUCTIONED_AT_OFFICE " +
		"FROM " +
		"  (SELECT ACCOUNTING_UNIT_ID, " +
		"    ACCOUNTING_FOR_OFFICE_ID, " +
		"    SURVEY_NO, " +
		"    AUCTION_NO, " +
		"    AUCTION_AMOUNT, " +
		"    AUCTION_STATUS " +
		"  FROM FAS_AUCTION_DONE_TRN " +
		"  WHERE ACCOUNTING_UNIT_ID    =? " +
		"  AND ACCOUNTING_FOR_OFFICE_ID=? " +
		"  AND SURVEY_NO               =? " +
		"  AND ASSET_CODE              =? " +
		"  )aa " +
		"LEFT OUTER JOIN " +
		"  (SELECT ACCOUNTING_UNIT_ID, " +
		"    ACCOUNTING_FOR_OFFICE_ID, " +
		"    SURVEY_NO, " +
		"    AUCTION_NO, " +
		"    to_char(AUCTION_DATE, 'dd/MM/yyyy') as AUCTION_DATE, " +
		"    AUCTIONED_AT_OFFICE, " +
		"    STATUS, " +
		"    UPDATED_BY_USERID, " +
		"    UPDATED_DATE " +
		"  FROM FAS_AUCTION_DONE_MST " +
		"  )bb " +
		"ON aa.ACCOUNTING_UNIT_ID       =bb.ACCOUNTING_UNIT_ID " +
		"AND aa.ACCOUNTING_FOR_OFFICE_ID=bb.ACCOUNTING_FOR_OFFICE_ID " +
		"AND aa.SURVEY_NO               =bb.SURVEY_NO";
		xml = "<response><command>assetdetails</command>";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, unitCode);
		preparedStatement.setInt(2, officeCode);
		preparedStatement.setInt(3, surveyNo);
		preparedStatement.setInt(4, assetcode);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			xml +="<status>success</status>" +
				  "<AUCTION_NO>"+resultSet.getInt("AUCTION_NO")+"</AUCTION_NO>" +
				  "<AUCTION_AMOUNT>"+resultSet.getInt("AUCTION_AMOUNT")+"</AUCTION_AMOUNT>" +
				  "<AUCTION_DATE>"+resultSet.getString("AUCTION_DATE")+"</AUCTION_DATE>" +
				  "<AUCTIONED_AT_OFFICE>"+resultSet.getInt("AUCTIONED_AT_OFFICE")+"</AUCTIONED_AT_OFFICE>";
		}else{
			xml +="<status>fail</status>";
		}
		xml += "</response>";
		return xml;
	}

	
	public String getCashReceipt(int unitCode, int officeCode, int year,
			int month, String check) throws SQLException {
		// TODO Auto-generated method stub
		count = 0;
		xml = "";
		PreparedStatement preparedStatement = null;
		connection = load.getConnection();
		if(check.equalsIgnoreCase("cash")){
			sql="SELECT to_char(RECEIPT_DATE,'dd/MM/yyyy') AS RECEIPT_DATE," +
			"  RECEIPT_NO " +
			"FROM FAS_RECEIPT_MASTER " +
			"WHERE ACCOUNTING_UNIT_ID    =? " +
			"AND ACCOUNTING_FOR_OFFICE_ID=? " +
			"AND CASHBOOK_YEAR           =? " +
			"AND CASHBOOK_MONTH          =? ";
			xml = "<response><command>cashreceipt</command>";
		}else{
			sql="SELECT to_char(VOUCHER_DATE,'dd/MM/yyyy') AS RECEIPT_DATE," +
			"VOUCHER_NO AS RECEIPT_NO " +
			"FROM FAS_JOURNAL_MASTER " +
			"WHERE ACCOUNTING_UNIT_ID    =? " +
			"AND ACCOUNTING_FOR_OFFICE_ID=? " +
			"AND CASHBOOK_YEAR           =? " +
			"AND CASHBOOK_MONTH          =? ";
			xml = "<response><command>journalreceipt</command>";
		}		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, unitCode);
		preparedStatement.setInt(2, officeCode);
		preparedStatement.setInt(3, year);
		preparedStatement.setInt(4, month);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<RECEIPT_DATE>"+resultSet.getString("RECEIPT_DATE")+"</RECEIPT_DATE>" +
				  "<RECEIPT_NO>"+resultSet.getInt("RECEIPT_NO")+"</RECEIPT_NO>" ;
			count++;
		}
		if(count==0){
			xml +="<status>fail</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}

	
	public String addAssetAuctionVoucher(int accountUnitId,
			int accountOfficeId, String financialYear, int surveyNo,
			int assetAuction, Date auctionDate, int auctionNo,
			int auctionOffice, int auctionAmount, int recoverYear, int month,
			int receiptNo, Date receiptDate, int jvrYear, int jvrmonth,
			int journalNo, Date journalDate, String userid) throws SQLException {
		// TODO Auto-generated method stub
		xml="";		
		CallableStatement callableStatement = null;
		connection = load.getConnection();
		xml = "<response><command>add</command>";
		sql="{call FAS_AUCTION_VOUCHER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, accountUnitId);
		callableStatement.setInt(2, accountOfficeId);
		callableStatement.setString(3, financialYear);
		callableStatement.setInt(4, surveyNo);
		callableStatement.setInt(5, assetAuction);
		callableStatement.setInt(6, auctionNo);		
		callableStatement.setDate(7, auctionDate);
		callableStatement.setInt(8, receiptNo);
		callableStatement.setDate(9, receiptDate);
		callableStatement.setInt(10, journalNo);
		callableStatement.setDate(11, journalDate);
		callableStatement.setString(12, userid);		
		callableStatement.setString(13, "insert");
		callableStatement.registerOutParameter(14, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(14);	
		if(errorCode !=0){
			xml += "<status>success</status><flag>not</flag>";
		}else{
			xml +="<status>success</status><flag>success</flag>";
		}
		xml +="</response>";
		return xml;
	}

	
	public String viewDetails(int accountUnitId, int accountOfficeId)
			throws SQLException {
		// TODO Auto-generated method stub
		xml ="";
		sql ="";
		count = 0;		
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		xml ="<response><command>get</command>";		
		sql="SELECT SURVEY_NO, " +
		"  ASSET_CODE, " +
		"  AUCTION_NO, " +
		"  to_char(AUCTION_DATE,'dd/MM/yyyy') AS AUCTION_DATE," +
		"  RECEIPT_NO, " +
		"  to_char(RECEIPT_DATE,'dd/MM/yyyy') AS RECEIPT_DATE," +
		"  JOURNAL_NO, " +
		"  to_char(JOURNAL_DATE,'dd/MM/yyyy') AS JOURNAL_DATE," +
		"  STATUS " +
		"FROM FAS_AUCTION_VOUCHER_DETAILS " +
		"WHERE ACCOUNTING_UNIT_ID    =? " +
		"AND ACCOUNTING_FOR_OFFICE_ID=? ";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, accountUnitId);
		preparedStatement.setInt(2, accountOfficeId);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<SURVEY_NO>"+resultSet.getInt("SURVEY_NO")+"</SURVEY_NO>" +				  
				  "<ASSET_CODE>"+resultSet.getInt("ASSET_CODE")+"</ASSET_CODE>" +
				  "<REF_NO>"+resultSet.getInt("RECEIPT_NO")+"</REF_NO>" +
				  "<REF_DATE>"+resultSet.getString("RECEIPT_DATE")+"</REF_DATE>" +
				  "<JOURNAL_NO>"+resultSet.getInt("JOURNAL_NO")+"</JOURNAL_NO>" +
				  "<JOURNAL_DATE>"+resultSet.getString("JOURNAL_DATE")+"</JOURNAL_DATE>" +
				  "<STATUS>"+resultSet.getString("STATUS")+"</STATUS>";
			count++;
		}
		if(count==0){
			xml +="<status>fail</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		return xml;
	}
	

}
