package Servlets.FAS.FAS1.Masters.servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

public class AssetAnnaulImpl {
	
	Connection connection = null;
	ResultSet resultSet = null;
	LoadDriver load = new LoadDriver();
	private String sql = "";
	private String xml;
	private int count;
	private int errorCode;
	
	public String viewExistingDetails(int accountUnitId, int accountOfficeId, int assetCode) throws SQLException{
		connection = load.getConnection();
		xml ="";
		sql ="";
		count = 0;
		xml = "<response>";
		PreparedStatement preparedStatement = null;
		if(assetCode==-10){
			sql="select * from FAS_ASSET_ANNUAL_VALUE";
			preparedStatement = connection.prepareStatement(sql);
		}else{
			sql="SELECT ASSET_CODE, " +			
			"  FINANCIAL_YEAR, " +
			"  PENDING_LIFE_CYCLE_YEARS, " +
			"  PENDING_LIFE_CYCLE_MONTHS, " +
			"  PENDING_LIFE_CYCLE_DAYS, " +
			"  FAIR_MARKET_VALUE, " +
			"  REMARKS, " +		
			"  STATUS " +
			"FROM FAS_ASSET_ANNUAL_VALUE " +
			"where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and ASSET_CODE=? order by ASSET_CODE";		
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountUnitId);
			preparedStatement.setInt(2, accountOfficeId);
			preparedStatement.setInt(3, assetCode);
		}		
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<ASSET_CODE>"+resultSet.getInt("ASSET_CODE")+"</ASSET_CODE>" +
					"<FINANCIAL_YEAR>"+resultSet.getString("FINANCIAL_YEAR")+"</FINANCIAL_YEAR>" +
					"<PENDING_YEARS>"+resultSet.getInt("PENDING_LIFE_CYCLE_YEARS")+"</PENDING_YEARS>" +
					"<PENDING_MONTH>"+resultSet.getInt("PENDING_LIFE_CYCLE_MONTHS")+"</PENDING_MONTH>" +
					"<PENDING_DAY>"+resultSet.getInt("PENDING_LIFE_CYCLE_DAYS")+"</PENDING_DAY>" +
					"<FAIR_MARKET_VALUE>"+resultSet.getInt("FAIR_MARKET_VALUE")+"</FAIR_MARKET_VALUE>" +
					"<REMARKS>"+resultSet.getString("REMARKS")+"</REMARKS>" +
					"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>";
			count++;
		}
		if(count==0){
			xml += "<status>nodata</status>";
		}else{
			xml += "<status>success</status>";
		}
		xml +="</response>";
		return xml;
	}

	public String loadAssetCode(int accountUnitId, int accountOfficeId) throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		sql = "";
		count = 0;
		connection = load.getConnection();
		xml ="<response><command>assetcode</command>";
		//sql="select ASSET_CODE FROM FAS_ASSET_ANNUAL_VALUE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
		sql="select distinct ASSET_CODE,particulars from FAS_ASSET_VAL_AC_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? order by ASSET_CODE";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, accountUnitId);
		preparedStatement.setInt(2, accountOfficeId);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<ASSET_CODE>"+resultSet.getInt("ASSET_CODE")+"</ASSET_CODE>";
			xml +="<ASSET_DESC>"+resultSet.getString("particulars")+"</ASSET_DESC>";
			count++;
		}
		if(count==0){
			xml += "<status>nodata</status>";
		}else{
			xml += "<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}

	public String addAssetAnnualValue(int accountUnitId, int accountOfficeId,
			int assetCode, String financialYear, int year, int month, int day,
			int fairMarket, String remarks, String userid, String status) throws SQLException {
		// TODO Auto-generated method stub		
		xml = "";
		sql = "";
		xml = "<response><command>add</command>";
		connection = load.getConnection();
		CallableStatement callableStatement = null;
		sql = "{call FAS_ANNUAL_ASSET_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, accountUnitId);
		callableStatement.setInt(2, accountOfficeId);
		callableStatement.setInt(3, assetCode);
		callableStatement.setString(4, financialYear);
		callableStatement.setInt(5, year);
		callableStatement.setInt(6, month);
		callableStatement.setInt(7, day);
		callableStatement.setInt(8, fairMarket);
		callableStatement.setString(9, remarks);
		callableStatement.setString(10, userid);
		callableStatement.setString(11, status);
		callableStatement.setString(12, "insert");
		callableStatement.registerOutParameter(13, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(13);
		if(errorCode !=0){
			xml += "<status>success</status><flag>not</flag>";
		}else{
			xml +="<status>success</status><flag>success</flag>";
		}
		xml +="</response>";		
		return xml;
	}

	public String editAssetAnnualValue(int assetCode) throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		sql="";
		connection = load.getConnection();
		PreparedStatement preparedStatement = null;
		xml ="<response>";
		sql ="SELECT ACCOUNTING_UNIT_ID, " +
			"  ACCOUNTING_UNIT_OFFICE_ID, " +
			"  ASSET_CODE, " +			
			"  FINANCIAL_YEAR, " +
			"  PENDING_LIFE_CYCLE_YEARS, " +
			"  PENDING_LIFE_CYCLE_MONTHS, " +
			"  PENDING_LIFE_CYCLE_DAYS, " +
			"  FAIR_MARKET_VALUE, " +
			"  REMARKS " +
			"FROM FAS_ASSET_ANNUAL_VALUE " +
			"WHERE ASSET_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, assetCode);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			xml +="<status>success</status>" +
				  "<ACCOUNTING_UNIT_ID>"+resultSet.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>" +
				  "<ACCOUNTING_UNIT_OFFICE_ID>"+resultSet.getInt("ACCOUNTING_UNIT_OFFICE_ID")+"</ACCOUNTING_UNIT_OFFICE_ID>" +
				  "<ASSET_CODE>"+resultSet.getInt("ASSET_CODE")+"</ASSET_CODE>" +
				  "<FINANCIAL_YEAR>"+resultSet.getString("FINANCIAL_YEAR")+"</FINANCIAL_YEAR>" +
				  "<PENDING_YEARS>"+resultSet.getInt("PENDING_LIFE_CYCLE_YEARS")+"</PENDING_YEARS>" +
				  "<PENDING_MONTH>"+resultSet.getInt("PENDING_LIFE_CYCLE_MONTHS")+"</PENDING_MONTH>" +
				  "<PENDING_DAY>"+resultSet.getInt("PENDING_LIFE_CYCLE_DAYS")+"</PENDING_DAY>" +
				  "<FAIR_MARKET_VALUE>"+resultSet.getInt("FAIR_MARKET_VALUE")+"</FAIR_MARKET_VALUE>" +
				  "<REMARKS>"+resultSet.getString("REMARKS")+"</REMARKS>";
				 
		}else{
			xml +="<status>fail</status>"; 
		}
		xml +="</response>";
		return xml;
	}

	public String updateAnnualAssetValue(int accountUnitId,
			int accountOfficeId, int assetCode, String financialYear, int year,
			int month, int day, int fairMarket, String remarks, String userid) throws SQLException {
		// TODO Auto-generated method stub
		xml ="";
		sql = "";
		xml = "<response><command>update</command>";
		connection = load.getConnection();
		CallableStatement callableStatement = null;
		sql = "{call FAS_ANNUAL_ASSET_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, accountUnitId);
		callableStatement.setInt(2, accountOfficeId);
		callableStatement.setInt(3, assetCode);
		callableStatement.setString(4, financialYear);
		callableStatement.setInt(5, year);
		callableStatement.setInt(6, month);
		callableStatement.setInt(7, day);
		callableStatement.setInt(8, fairMarket);
		callableStatement.setString(9, remarks);
		callableStatement.setString(10, userid);
		callableStatement.setString(11, "L");
		callableStatement.setString(12, "update");
		callableStatement.registerOutParameter(13, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(13);
		if(errorCode !=0){
			xml += "<status>success</status><flag>not</flag>";
		}else{
			xml +="<status>success</status><flag>success</flag>";
		}
		xml +="</response>";
		return xml;
	}

	public String deleteAnnualAssetValue(int accountUnitId,
			int accountOfficeId, int assetCode, String financialYear, int year,
			int month, int day, int fairMarket, String remarks, String userid,
			String status) throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		sql = "";
		connection = load.getConnection();
		xml = "<response><command>delete</command>";
		CallableStatement callableStatement = null;
		sql = "{call FAS_ANNUAL_ASSET_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, accountUnitId);
		callableStatement.setInt(2, accountOfficeId);
		callableStatement.setInt(3, assetCode);
		callableStatement.setString(4, financialYear);
		callableStatement.setInt(5, year);
		callableStatement.setInt(6, month);
		callableStatement.setInt(7, day);
		callableStatement.setInt(8, fairMarket);
		callableStatement.setString(9, remarks);
		callableStatement.setString(10, userid);
		callableStatement.setString(11, status);
		callableStatement.setString(12, "cancel");
		callableStatement.registerOutParameter(13, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(13);
		System.out.println("delete "+errorCode);
		if(errorCode !=0){
			xml += "<status>fail</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		return xml;
	}
}
