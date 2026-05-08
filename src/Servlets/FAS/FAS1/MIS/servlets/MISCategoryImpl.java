package Servlets.FAS.FAS1.MIS.servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

public class MISCategoryImpl {
	Connection connection = null;
	ResultSet resultSet = null;
	LoadDriver load = new LoadDriver();	
	PreparedStatement preparedStatement = null;
	String sql = "";
	String xml = "";
	public String mainCategoryList() throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="select SCH_MAIN_CATEGORY_CODE,SCH_MAIN_CATEGORY_DESC,STATUS from FAS_WE_SCH_MAIN_CATEGORY order by SCH_MAIN_CATEGORY_CODE";
		preparedStatement = connection.prepareStatement(sql);		
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"				
				+"<MAJOR_TYPE_NAME>"+resultSet.getString("SCH_MAIN_CATEGORY_DESC")+"</MAJOR_TYPE_NAME>"
				+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>";
			change++;
		}
		if(change == 0){
			xml += "<status>fail</status>";
		}else{
			xml += "<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String addMisMainCategory(String mainCategoryDesc, String userid) throws SQLException {
		
		sql="";
		xml = "";
		int max = 1;
		int errorCode = 0;
		boolean isInsert = false;
		connection = load.getConnection();
		sql="select max(SCH_MAIN_CATEGORY_CODE) as max from FAS_WE_SCH_MAIN_CATEGORY";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();		
		if(resultSet.next()){
			max = resultSet.getInt("max");
			max += 1;					
		}
		sql="select * from FAS_WE_SCH_MAIN_CATEGORY where SCH_MAIN_CATEGORY_CODE='"+max+"' or SCH_MAIN_CATEGORY_DESC='"+mainCategoryDesc+"'";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			isInsert = false;
		}else{
			isInsert = true;
		}
		if(isInsert){				
			sql="insert into FAS_WE_SCH_MAIN_CATEGORY(SCH_MAIN_CATEGORY_CODE,SCH_MAIN_CATEGORY_DESC,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,SYSTIMESTAMP)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, max);
			preparedStatement.setString(2, mainCategoryDesc);
			preparedStatement.setString(3, "L");
			preparedStatement.setString(4, userid);
			errorCode = preparedStatement.executeUpdate();
			if(errorCode==0){
				xml="<response><status>success</status><value>Notadded</value></response>";	
		 	 }else{
		 		xml="<response><status>success</status><value>added</value><code>"+max+"</code></response>";
		 	 }
		}
		return xml;
	}
	public String editMisMainCategory(int mainCategoryId) throws SQLException{
		xml = "";
		connection = load.getConnection();
		sql="select SCH_MAIN_CATEGORY_CODE,SCH_MAIN_CATEGORY_DESC,STATUS from FAS_WE_SCH_MAIN_CATEGORY where SCH_MAIN_CATEGORY_CODE=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, mainCategoryId);		
		resultSet = preparedStatement.executeQuery();
		xml="<response>";
		if(resultSet.next()){
			xml += "<status>success</status>"
			+"<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"				
			+"<MAJOR_TYPE_NAME>"+resultSet.getString("SCH_MAIN_CATEGORY_DESC")+"</MAJOR_TYPE_NAME>"
			+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>";
		}else{
			xml += "<status>fail</status>";
		}
		xml += "</response>";
		return xml;
	}	
	public String updateMajorType(int mainCategoryId, String mainCategoryDesc,
			String userid, String delete) throws SQLException {
		
		xml = "";
		int status = 0;
		connection = load.getConnection();
		sql ="update FAS_WE_SCH_MAIN_CATEGORY set SCH_MAIN_CATEGORY_DESC=?," +
				"UPDATED_BY_USER_ID=?,UPDATED_DATE=SYSTIMESTAMP,STATUS=? where SCH_MAIN_CATEGORY_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, mainCategoryDesc);
		preparedStatement.setString(2, userid);
		if(delete.equalsIgnoreCase("y")){
			preparedStatement.setString(3, "C");
		}else{
			preparedStatement.setString(3, "L");
		}
		preparedStatement.setInt(4, mainCategoryId);
		status = preparedStatement.executeUpdate();
		if(status==0){
			xml ="<response><status>success</status><value>Notadded</value></response>";
		}else{
			xml ="<response><status>success</status><value>update</value><code>"+mainCategoryId+"</code></response>";				
		}
		return xml;
	}
	public String subCategoryList(String categoryCode) throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";		
		sql="SELECT a.SCH_MAIN_CATEGORY_CODE AS SCH_MAIN_CATEGORY_CODE, " +
		"  a.SCH_SUB_CATEGORY_CODE       AS SCH_SUB_CATEGORY_CODE, " +
		"  a.SCH_SUB_CATEGORY_DESC       AS SCH_SUB_CATEGORY_DESC, " +
		"  a.STATUS                      AS STATUS, " +
		"  b.SCH_MAIN_CATEGORY_DESC      AS SCH_MAIN_CATEGORY_DESC " +
		"FROM " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_SUB_CATEGORY_CODE, " +
		"    SCH_SUB_CATEGORY_DESC, " +
		"    STATUS " +
		"  FROM FAS_WE_SCH_SUB_CATEGORY ";
		if(!categoryCode.equalsIgnoreCase("select")){
			sql+=" WHERE SCH_MAIN_CATEGORY_CODE="+Integer.parseInt(categoryCode);
		}
		sql+="  )a " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_CATEGORY_DESC " +
		"  FROM FAS_WE_SCH_MAIN_CATEGORY " +
		"  )b " +
		"ON a.SCH_MAIN_CATEGORY_CODE=b.SCH_MAIN_CATEGORY_CODE " +
		"ORDER BY a.SCH_SUB_CATEGORY_CODE";
		preparedStatement = connection.prepareStatement(sql);		
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"				
				+"<MAJOR_TYPE_NAME>"+resultSet.getString("SCH_MAIN_CATEGORY_DESC")+"</MAJOR_TYPE_NAME>"
				+"<SUB_TYPE_CODE>"+resultSet.getInt("SCH_SUB_CATEGORY_CODE")+"</SUB_TYPE_CODE>"
				+"<SUB_TYPE_NAME>"+resultSet.getString("SCH_SUB_CATEGORY_DESC")+"</SUB_TYPE_NAME>"
				+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>";
			change++;
		}
		if(change == 0){
			xml += "<status>fail</status>";
		}else{
			xml += "<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String loadMainCombo() throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="SELECT SCH_MAIN_CATEGORY_CODE, " +
		"  SCH_MAIN_CATEGORY_DESC " +
		"FROM FAS_WE_SCH_MAIN_CATEGORY " +
		"WHERE STATUS<>'C' " +
		"ORDER BY SCH_MAIN_CATEGORY_CODE";
		preparedStatement = connection.prepareStatement(sql);		
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<categoryid>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</categoryid>" +
			  "<categorydesc>"+resultSet.getString("SCH_MAIN_CATEGORY_DESC")+"</categorydesc>";
			change++;
		}
		if(change == 0){
			xml +="<status>fail</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String addMisSubCategory(int mainCategoryId, String subCategoryName,
			String userid) throws SQLException {
		sql="";
		xml = "";
		int max = 1;
		int errorCode = 0;
		boolean isInsert = false;
		connection = load.getConnection();
		sql="select max(SCH_SUB_CATEGORY_CODE) as max from FAS_WE_SCH_SUB_CATEGORY where SCH_MAIN_CATEGORY_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, mainCategoryId);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			max = resultSet.getInt("max");
			max += 1;					
		}
		sql="SELECT * " +
		"FROM FAS_WE_SCH_SUB_CATEGORY " +
		"WHERE SCH_MAIN_CATEGORY_CODE='"+mainCategoryId+"' " +
		"AND SCH_SUB_CATEGORY_CODE  ='"+max+"' " +
		"AND SCH_SUB_CATEGORY_DESC    ='"+subCategoryName+"'";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			isInsert = false;
		}else{
			isInsert = true;
		}
		if(isInsert){				
			sql="insert into FAS_WE_SCH_SUB_CATEGORY(SCH_MAIN_CATEGORY_CODE,SCH_SUB_CATEGORY_CODE,SCH_SUB_CATEGORY_DESC,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,SYSTIMESTAMP)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, mainCategoryId);
			preparedStatement.setInt(2, max);
			preparedStatement.setString(3, subCategoryName);
			preparedStatement.setString(4, "L");
			preparedStatement.setString(5, userid);
			errorCode = preparedStatement.executeUpdate();
			if(errorCode==0){
				xml="<response><status>success</status><value>Notadded</value></response>";	
		 	 }else{
		 		xml="<response><status>success</status><value>added</value><code>"+max+"</code></response>";
		 	 }
		}
		return xml;
	}
	public String editSubCode(int mainCategoryId, int subCode) throws SQLException{
		xml = "";
		connection = load.getConnection();
		sql="SELECT a.SCH_MAIN_CATEGORY_CODE AS SCH_MAIN_CATEGORY_CODE, " +
		"  a.SCH_SUB_CATEGORY_CODE       AS SCH_SUB_CATEGORY_CODE, " +
		"  a.SCH_SUB_CATEGORY_DESC       AS SCH_SUB_CATEGORY_DESC, " +
		"  a.STATUS                      AS STATUS, " +
		"  b.SCH_MAIN_CATEGORY_DESC      AS SCH_MAIN_CATEGORY_DESC " +
		"FROM " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_SUB_CATEGORY_CODE, " +
		"    SCH_SUB_CATEGORY_DESC, " +
		"    STATUS " +
		"  FROM FAS_WE_SCH_SUB_CATEGORY " +
		"  WHERE SCH_MAIN_CATEGORY_CODE=? " +
		"  AND SCH_SUB_CATEGORY_CODE   =? " +
		"  )a " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_CATEGORY_DESC " +
		"  FROM FAS_WE_SCH_MAIN_CATEGORY " +
		"  )b " +
		"ON a.SCH_MAIN_CATEGORY_CODE=b.SCH_MAIN_CATEGORY_CODE";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, mainCategoryId);
		preparedStatement.setInt(2, subCode);
		resultSet = preparedStatement.executeQuery();
		xml="<response>";
		if(resultSet.next()){
			xml += "<status>success</status>"			
			+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>"
			+"<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"
			+"<MAJOR_TYPE_NAME>"+resultSet.getString("SCH_MAIN_CATEGORY_DESC")+"</MAJOR_TYPE_NAME>"
			+"<SUB_TYPE_CODE>"+resultSet.getInt("SCH_SUB_CATEGORY_CODE")+"</SUB_TYPE_CODE>"
			+"<SUB_TYPE_NAME>"+resultSet.getString("SCH_SUB_CATEGORY_DESC")+"</SUB_TYPE_NAME>";
		}else{
			xml += "<status>fail</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String updateSubType(int mainCategoryId, int subCategoryId,
			String subCategoryName, String userid, String delete) throws SQLException {
		
		xml = "";
		int status = 0;
		connection = load.getConnection();
		sql ="update FAS_WE_SCH_SUB_CATEGORY set SCH_SUB_CATEGORY_DESC=?," +
				"UPDATED_BY_USER_ID=?,UPDATED_DATE=SYSTIMESTAMP,STATUS=? where SCH_MAIN_CATEGORY_CODE=?" +
				"and SCH_SUB_CATEGORY_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1,subCategoryName);
		preparedStatement.setString(2, userid);
		if(delete.equalsIgnoreCase("y")){
			preparedStatement.setString(3, "C");
		}else{
			preparedStatement.setString(3, "L");
		}
		preparedStatement.setInt(4, mainCategoryId);
		preparedStatement.setInt(5, subCategoryId);
		status = preparedStatement.executeUpdate();
		if(status==0){
			xml ="<response><status>success</status><value>Notadded</value></response>";
		}else{
			xml ="<response><status>success</status><value>update</value><code>"+subCategoryId+"</code></response>";				
		}
		return xml;
	}
	public String acCategoryList() throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="SELECT a.SCH_MAIN_CATEGORY_CODE AS SCH_MAIN_CATEGORY_CODE, " +
		"  a.SCH_SUB_CATEGORY_CODE AS SCH_SUB_CATEGORY_CODE, " +
		"  a.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
		"  a.STATUS AS STATUS, " +
		"  b.SCH_MAIN_CATEGORY_DESC AS SCH_MAIN_CATEGORY_DESC, " +
		"  c.SCH_SUB_CATEGORY_DESC AS SCH_SUB_CATEGORY_DESC, " +
		"  d.ACCOUNT_HEAD_DESC AS ACCOUNT_HEAD_DESC FROM" +			
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_SUB_CATEGORY_CODE, " +
		"    STATUS, " +
		"    ACCOUNT_HEAD_CODE " +
		"  FROM FAS_WE_SCH_AC_HEAD_MAPPING " +
		"  )a " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_CATEGORY_DESC " +
		"  FROM FAS_WE_SCH_MAIN_CATEGORY " +
		"  )b " +
		"ON a.SCH_MAIN_CATEGORY_CODE=b.SCH_MAIN_CATEGORY_CODE " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_SUB_CATEGORY_CODE, " +
		"    SCH_SUB_CATEGORY_DESC " +
		"  FROM FAS_WE_SCH_SUB_CATEGORY " +
		"  )c " +
		"ON a.SCH_SUB_CATEGORY_CODE=c.SCH_SUB_CATEGORY_CODE " +
		"LEFT OUTER JOIN " +
		"  ( SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
		"  )d " +
		"ON a.ACCOUNT_HEAD_CODE=d.ACCOUNT_HEAD_CODE";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"
			+"<MAJOR_TYPE_NAME>"+(resultSet.getString("SCH_MAIN_CATEGORY_DESC"))+"</MAJOR_TYPE_NAME>"
			+"<SUB_TYPE_CODE>"+(resultSet.getInt("SCH_SUB_CATEGORY_CODE"))+"</SUB_TYPE_CODE>"
			+"<SUB_TYPE_NAME>"+(resultSet.getString("SCH_SUB_CATEGORY_DESC"))+"</SUB_TYPE_NAME>"
			+"<ACCOUNT_CODE>"+(resultSet.getLong("ACCOUNT_HEAD_CODE"))+"</ACCOUNT_CODE>"
			+"<ACCOUNT_NAME>"+(resultSet.getString("ACCOUNT_HEAD_DESC"))+"</ACCOUNT_NAME>"
			+"<STATUSTYPE>"+(resultSet.getString("STATUS"))+"</STATUSTYPE>";
			change++;
		}		
		
		if(change == 0){
			xml+="<status>Nodate</status>";
		}else{
			xml+="<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String loadSubCombo(int majorCode) throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="SELECT SCH_MAIN_CATEGORY_CODE, " +
		"  SCH_SUB_CATEGORY_CODE, " +
		"  SCH_SUB_CATEGORY_DESC " +
		"FROM FAS_WE_SCH_SUB_CATEGORY " +
		"WHERE SCH_MAIN_CATEGORY_CODE=? " +
		"AND STATUS                 <>'C'";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, majorCode);
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<categoryid>"+resultSet.getInt("SCH_SUB_CATEGORY_CODE")+"</categoryid>" +
			  "<categorydesc>"+resultSet.getString("SCH_SUB_CATEGORY_DESC")+"</categorydesc>";
			change++;
		}
		if(change == 0){
			xml +="<status>fail</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}	
	public int addAcHeadCodeMst(int mainCategoryId, int subCategoryId,
			String userid) throws SQLException {
		sql="";
		xml = "";
		int errorCode = 1;
		boolean isInsert = false;
		connection = load.getConnection();
		sql="SELECT * " +
		"FROM FAS_WE_FUND_AC_HEAD_MAP_MST " +
		"WHERE SCH_MAIN_CATEGORY_CODE='"+mainCategoryId+"' " +
		"AND SCH_SUB_CATEGORY_CODE  ='"+subCategoryId+"' " +
		"AND MAJOR_HEAD_CODE='L' " +
		"AND MINOR_HEAD_CODE=183" ;		
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			isInsert = false;
		}else{
			isInsert = true;
		}		
		if(isInsert){				
			sql="insert into FAS_WE_FUND_AC_HEAD_MAP_MST(MAJOR_HEAD_CODE,MINOR_HEAD_CODE,SCH_MAIN_CATEGORY_CODE,SCH_SUB_CATEGORY_CODE,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,SYSTIMESTAMP)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "L");
			preparedStatement.setInt(2, 183);
			preparedStatement.setInt(3, mainCategoryId);
			preparedStatement.setInt(4, subCategoryId);
			preparedStatement.setString(5, "L");
			preparedStatement.setString(6, userid);
			errorCode = preparedStatement.executeUpdate();			
		}
		System.out.println("errorCode "+errorCode);
		return errorCode;
	}
	public List<String> addMisAcHeadCodeTrn(int mainCategoryId, int subCategoryId, String fundExpend,
			String[] accHead, String userid) throws SQLException {
		sql="";
		xml = "";
		boolean isInsert = true;
		List<String> removeAcc = new ArrayList<String>();
		List<String> actualAcc = Arrays.asList(accHead);
		List<String> insAcc = new ArrayList<String>(Arrays.asList(accHead));
		connection = load.getConnection();
		/*for(int i=0; i<accHead.length; i++){
			accheadstr
		}*/
		sql="SELECT ACCOUNT_HEAD_CODE " +
		"FROM FAS_WE_FUND_AC_HEAD_MAP_TRN " +
		"WHERE SCH_MAIN_CATEGORY_CODE='"+mainCategoryId+"' " +
		"AND SCH_SUB_CATEGORY_CODE  ='"+subCategoryId+"' " +
		"AND FUND_OR_EXP    ='"+fundExpend+"'"+
		"AND MAJOR_HEAD_CODE='L'"+
		"AND MINOR_HEAD_CODE=183";		
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			removeAcc.add(resultSet.getString("ACCOUNT_HEAD_CODE"));
		}
		if(!removeAcc.isEmpty()){
			for(int i=0; i<actualAcc.size(); i++){
				if(actualAcc.contains(removeAcc.get(i))){
					insAcc.remove(removeAcc.get(i));
				}
			}
		}				
		long acountHead = 0;
		if(isInsert){				
			sql="insert into FAS_WE_FUND_AC_HEAD_MAP_TRN(MAJOR_HEAD_CODE,MINOR_HEAD_CODE,SCH_MAIN_CATEGORY_CODE,SCH_SUB_CATEGORY_CODE,FUND_OR_EXP,ACCOUNT_HEAD_CODE,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS)values(?,?,?,?,?,?,?,SYSTIMESTAMP,'L')";
			preparedStatement = connection.prepareStatement(sql);
			for(String acc: insAcc){
				preparedStatement.setString(1, "L");
				preparedStatement.setInt(2, 183);
				preparedStatement.setInt(3, mainCategoryId);
				preparedStatement.setInt(4, subCategoryId);
				preparedStatement.setString(5, fundExpend);
				acountHead = Long.parseLong(acc);				
				preparedStatement.setLong(6, acountHead);
				preparedStatement.setString(7, userid);
				preparedStatement.executeUpdate();
			}			
		}
		return insAcc;
	}
	public String updateAcHeadCode(int mainCategoryId, int subCategoryId, String fundType,
			long accCode, String userid, String delete, long preAccCode) throws SQLException {
		xml = "";
		int status = 0;
		connection = load.getConnection();
		if(delete.equalsIgnoreCase("y")){
			sql ="update FAS_WE_FUND_AC_HEAD_MAP_TRN set ACCOUNT_HEAD_CODE=?," +
			"UPDATED_BY_USER_ID=?,UPDATED_DATE=SYSTIMESTAMP,STATUS=? where SCH_MAIN_CATEGORY_CODE=?" +
			"and SCH_SUB_CATEGORY_CODE=? and FUND_OR_EXP=? and ACCOUNT_HEAD_CODE=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, accCode);
			preparedStatement.setString(2, userid);	
			preparedStatement.setString(3, "C");
			preparedStatement.setInt(4, mainCategoryId);
			preparedStatement.setInt(5, subCategoryId);
			preparedStatement.setString(6, fundType);
			preparedStatement.setLong(7, preAccCode);
			status = preparedStatement.executeUpdate();
		}else{
			sql="SELECT SCH_MAIN_CATEGORY_CODE, " +
			"  SCH_SUB_CATEGORY_CODE, " +
			"  FUND_OR_EXP, " +
			"  ACCOUNT_HEAD_CODE, " +
			"  STATUS " +
			"FROM FAS_WE_FUND_AC_HEAD_MAP_TRN " +
			"WHERE SCH_MAIN_CATEGORY_CODE=? " +
			"AND SCH_SUB_CATEGORY_CODE   =? " +
			"AND FUND_OR_EXP             =? " +
			"AND ACCOUNT_HEAD_CODE       =?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, mainCategoryId);
			preparedStatement.setInt(2, subCategoryId);
			preparedStatement.setString(3, fundType);
			preparedStatement.setLong(4, accCode);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				xml ="<response><status>success</status><value>Notadded</value></response>";
			}else{
				System.out.println("is update");
				sql ="update FAS_WE_FUND_AC_HEAD_MAP_TRN set ACCOUNT_HEAD_CODE=?," +
				"UPDATED_BY_USER_ID=?,UPDATED_DATE=SYSTIMESTAMP,STATUS=? where SCH_MAIN_CATEGORY_CODE=?" +
				"and SCH_SUB_CATEGORY_CODE=? and FUND_OR_EXP=? and ACCOUNT_HEAD_CODE=?";
				
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, accCode);
				preparedStatement.setString(2, userid);		
				preparedStatement.setString(3, "L");
				preparedStatement.setInt(4, mainCategoryId);
				preparedStatement.setInt(5, subCategoryId);
				preparedStatement.setString(6, fundType);
				preparedStatement.setLong(7, preAccCode);
				status = preparedStatement.executeUpdate();
			}
		}		
		if(status==0){
			xml ="<response><status>success</status><value>Notadded</value></response>";
		}else{
			xml ="<response><status>success</status><value>update</value></response>";				
		}
		return xml;
	}
	public String addFundProgress(String fundType,String fundDesc,String userid) throws SQLException{
		sql="";
		xml = "";
		int max = 1;
		int errorCode = 0;
		boolean isInsert = true;
		connection = load.getConnection();
		sql="select max(FAS_WE_FUND_PROG_CODE) as max from FAS_WE_FUND_PROG_MST";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			max = resultSet.getInt("max");
			max += 1;					
		}
		/*sql="SELECT * " +
		"FROM FAS_WE_SCH_SUB_CATEGORY " +
		"WHERE SCH_MAIN_CATEGORY_CODE='"+mainCategoryId+"' " +
		"AND SCH_SUB_CATEGORY_CODE  ='"+max+"' " +
		"AND SCH_SUB_CATEGORY_DESC    ='"+subCategoryName+"'";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			isInsert = false;
		}else{
			isInsert = true;
		}*/
		if(isInsert){				
			sql="insert into FAS_WE_FUND_PROG_MST(FAS_WE_FUND_TYPE,FAS_WE_FUND_PROG_CODE,FAS_WE_FUND_PROG_DESC,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,SYSTIMESTAMP)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, fundType);
			preparedStatement.setInt(2, max);
			preparedStatement.setString(3, fundDesc);
			preparedStatement.setString(4, "L");
			preparedStatement.setString(5, userid);
			errorCode = preparedStatement.executeUpdate();
			if(errorCode==0){
				xml="<response><status>success</status><value>Notadded</value></response>";	
		 	 }else{
		 		xml="<response><status>success</status><value>added</value><code>"+max+"</code></response>";
		 	 }
		}
		return xml;
	}
	public String financialProgressList() throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="SELECT FAS_WE_FUND_TYPE, " +
		"  FAS_WE_FUND_PROG_CODE, " +
		"  FAS_WE_FUND_PROG_DESC, " +
		"  STATUS " +
		"FROM FAS_WE_FUND_PROG_MST " +
		"ORDER BY FAS_WE_FUND_PROG_CODE";
		preparedStatement = connection.prepareStatement(sql);		
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<FUND_TYPE>"+resultSet.getString("FAS_WE_FUND_TYPE")+"</FUND_TYPE>"				
				+"<FUND_CODE>"+resultSet.getInt("FAS_WE_FUND_PROG_CODE")+"</FUND_CODE>"
				+"<FUND_NAME>"+resultSet.getString("FAS_WE_FUND_PROG_DESC")+"</FUND_NAME>"
				+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>";
			change++;
		}
		if(change == 0){
			xml += "<status>fail</status>";
		}else{
			xml += "<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String editFundProgress(int fundCode) throws SQLException{
		xml = "";
		connection = load.getConnection();
		sql="SELECT FAS_WE_FUND_TYPE, " +
		"  FAS_WE_FUND_PROG_CODE, " +
		"  FAS_WE_FUND_PROG_DESC, " +
		"  STATUS " +
		"FROM FAS_WE_FUND_PROG_MST " +
		"WHERE FAS_WE_FUND_PROG_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, fundCode);
		resultSet = preparedStatement.executeQuery();
		xml="<response>";
		if(resultSet.next()){
			xml += "<status>success</status>"			
			+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>"
			+"<FUND_TYPE>"+resultSet.getString("FAS_WE_FUND_TYPE")+"</FUND_TYPE>"
			+"<FUND_CODE>"+resultSet.getInt("FAS_WE_FUND_PROG_CODE")+"</FUND_CODE>"
			+"<FUND_NAME>"+resultSet.getString("FAS_WE_FUND_PROG_DESC")+"</FUND_NAME>";
		}else{
			xml += "<status>fail</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String updateFinancialProgress(String fundType, int fundCode,
			String fundDesc, String userid, String delete) throws SQLException {
		xml = "";
		int status = 0;
		connection = load.getConnection();
		sql ="update FAS_WE_FUND_PROG_MST set FAS_WE_FUND_TYPE=?,FAS_WE_FUND_PROG_DESC=?," +
				"UPDATED_BY_USER_ID=?,UPDATED_DATE=SYSTIMESTAMP,STATUS=? where FAS_WE_FUND_PROG_CODE=?";				
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1,fundType);
		preparedStatement.setString(2,fundDesc);
		preparedStatement.setString(3, userid);
		if(delete.equalsIgnoreCase("y")){
			preparedStatement.setString(4, "C");
		}else{
			preparedStatement.setString(4, "L");
		}
		preparedStatement.setInt(5, fundCode);
		status = preparedStatement.executeUpdate();
		if(status==0){
			xml ="<response><status>success</status><value>Notadded</value></response>";
		}else{
			xml ="<response><status>success</status><value>update</value><code>"+fundCode+"</code></response>";				
		}
		return xml;
	}
	public String acHeadCodeList(String fundExpend) throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		if(fundExpend.equalsIgnoreCase("f")){
			sql="SELECT ACCOUNT_HEAD_CODE, " +
			"  ACCOUNT_HEAD_DESC " +
			"FROM COM_MST_ACCOUNT_HEADS " +
			"WHERE MAJOR_HEAD_CODE='L' " +
			"AND MINOR_HEAD_CODE  =183 " +
			"AND SUB_HEAD1_CODE   =46";
		}else{
			sql="SELECT ACCOUNT_HEAD_CODE, " +
			"  ACCOUNT_HEAD_DESC " +
			"FROM COM_MST_ACCOUNT_HEADS " +
			"WHERE MAJOR_HEAD_CODE='L' " +
			"AND MINOR_HEAD_CODE  =183 " +
			"AND SUB_HEAD1_CODE  IN(31,32)";
		}
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<ACCOUNT_CODE>"+(resultSet.getLong("ACCOUNT_HEAD_CODE"))+"</ACCOUNT_CODE>"
			+"<ACCOUNT_NAME>"+(resultSet.getString("ACCOUNT_HEAD_DESC"))+"</ACCOUNT_NAME>";			
			change++;
		}		
		
		if(change == 0){
			xml+="<status>Nodate</status>";
		}else{
			xml+="<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}	
	public String acHeadCodeExistList(int mainCategoryId,int subCategoryId,String fundExpend) throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="SELECT a.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
		"  b.ACCOUNT_HEAD_DESC      AS ACCOUNT_HEAD_DESC, " +
		"  a.SCH_MAIN_CATEGORY_CODE      AS SCH_MAIN_CATEGORY_CODE, " +
		"  a.SCH_SUB_CATEGORY_CODE      AS SCH_SUB_CATEGORY_CODE, " +
		"  a.FUND_OR_EXP      AS FUND_OR_EXP, " +
		"  a.STATUS      AS STATUS " +
		"FROM " +
		"  (SELECT MAJOR_HEAD_CODE, " +
		"    MINOR_HEAD_CODE, " +		
		"    SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_SUB_CATEGORY_CODE, " +
		"    FUND_OR_EXP, " +
		"    ACCOUNT_HEAD_CODE, " +
		"    STATUS " +
		"  FROM FAS_WE_FUND_AC_HEAD_MAP_TRN " +
		"  WHERE MAJOR_HEAD_CODE     ='L' " +
		"  AND MINOR_HEAD_CODE       =183 " +
		"  AND FUND_OR_EXP           =? " +
		"  AND SCH_MAIN_CATEGORY_CODE=? " +
		"  AND SCH_SUB_CATEGORY_CODE =? " +
		"  )a " +
		"LEFT OUTER JOIN " +
		"  ( SELECT ACCOUNT_HEAD_CODE, ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " +
		"  )b " +
		"ON a.ACCOUNT_HEAD_CODE=b.ACCOUNT_HEAD_CODE";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, fundExpend);
		preparedStatement.setInt(2, mainCategoryId);
		preparedStatement.setInt(3, subCategoryId);		
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<ACCOUNT_CODE>"+(resultSet.getLong("ACCOUNT_HEAD_CODE"))+"</ACCOUNT_CODE>"
			+"<ACCOUNT_NAME>"+(resultSet.getString("ACCOUNT_HEAD_DESC"))+"</ACCOUNT_NAME>"
			+"<MAJOR_TYPE_CODE>"+(resultSet.getInt("SCH_MAIN_CATEGORY_CODE"))+"</MAJOR_TYPE_CODE>"
			+"<SUB_TYPE_CODE>"+(resultSet.getInt("SCH_SUB_CATEGORY_CODE"))+"</SUB_TYPE_CODE>"
			+"<FUND_TYPE>"+(resultSet.getString("FUND_OR_EXP"))+"</FUND_TYPE>"
			+"<STATUSTYPE>"+(resultSet.getString("STATUS"))+"</STATUSTYPE>";			
			change++;
		}		
		
		if(change == 0){
			xml+="<status>Nodate</status>";
		}else{
			xml+="<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String editAcCode(int majorCode, int subCode, long acCode, String fundType) throws SQLException{
		xml = "";
		connection = load.getConnection();
		sql="SELECT SCH_MAIN_CATEGORY_CODE, " +
		"  SCH_SUB_CATEGORY_CODE, " +
		"  FUND_OR_EXP, " +
		"  ACCOUNT_HEAD_CODE, " +
		"  STATUS " +
		"FROM FAS_WE_FUND_AC_HEAD_MAP_TRN " +
		"WHERE SCH_MAIN_CATEGORY_CODE=? " +
		"AND SCH_SUB_CATEGORY_CODE   =? " +
		"AND FUND_OR_EXP             =? " +
		"AND ACCOUNT_HEAD_CODE       =?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, majorCode);
		preparedStatement.setInt(2, subCode);
		preparedStatement.setString(3, fundType);
		preparedStatement.setLong(4, acCode);		
		resultSet = preparedStatement.executeQuery();
		xml="<response>";
		if(resultSet.next()){
			xml += "<status>success</status>"			
			+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>"
			+"<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"
			+"<SUB_TYPE_CODE>"+resultSet.getInt("SCH_SUB_CATEGORY_CODE")+"</SUB_TYPE_CODE>"
			+"<ACCOUNT_CODE>"+resultSet.getLong("ACCOUNT_HEAD_CODE")+"</ACCOUNT_CODE>"
			+"<FUND_TYPE>"+resultSet.getString("FUND_OR_EXP")+"</FUND_TYPE>";
		}else{
			xml += "<status>fail</status>";
		}
		xml += "</response>";
		return xml;
	}
	public boolean isequalval(String str, String mincheck){
		boolean isStr = true;
		if(str.trim().equals(mincheck)){
			 isStr = false;
		 }
		return isStr;
	}
	public String addMisMainType(int mainCategoryId, String mainTypeDesc,
			String userid) throws SQLException {
		sql="";
		xml = "";
		int max = 1;
		int errorCode = 0;
		boolean isInsert = false;
		connection = load.getConnection();
		sql="select max(SCH_MAIN_TYPE_CODE) as max from FAS_WE_SCH_MAIN_TYPES where SCH_MAIN_CATEGORY_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, mainCategoryId);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			max = resultSet.getInt("max");
			max += 1;					
		}
		sql="SELECT * " +
		"FROM FAS_WE_SCH_MAIN_TYPES " +
		"WHERE SCH_MAIN_CATEGORY_CODE='"+mainCategoryId+"' " +		
		"AND SCH_MAIN_TYPE_DESC    ='"+mainTypeDesc+"'";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			isInsert = false;
		}else{
			isInsert = true;
		}
		if(isInsert){				
			sql="insert into FAS_WE_SCH_MAIN_TYPES(SCH_MAIN_CATEGORY_CODE,SCH_MAIN_TYPE_CODE,SCH_MAIN_TYPE_DESC,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,SYSTIMESTAMP)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, mainCategoryId);
			preparedStatement.setInt(2, max);
			preparedStatement.setString(3, mainTypeDesc);
			preparedStatement.setString(4, "L");
			preparedStatement.setString(5, userid);
			errorCode = preparedStatement.executeUpdate();
			if(errorCode==0){
				xml="<response><status>success</status><value>Notadded</value></response>";	
		 	 }else{
		 		xml="<response><status>success</status><value>added</value><code>"+max+"</code></response>";
		 	 }
		}
		return xml;
	}
	public String updateMainType(int mainCategoryId, int mainTypeCode,
			String mainTypeDesc, String userid, String delete) throws SQLException {
		
		xml = "";
		int status = 0;
		connection = load.getConnection();
		sql ="update FAS_WE_SCH_MAIN_TYPES set SCH_MAIN_TYPE_DESC=?," +
				"UPDATED_BY_USER_ID=?,UPDATED_DATE=SYSTIMESTAMP,STATUS=? where SCH_MAIN_CATEGORY_CODE=?" +
				"and SCH_MAIN_TYPE_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1,mainTypeDesc);
		preparedStatement.setString(2, userid);
		if(delete.equalsIgnoreCase("y")){
			preparedStatement.setString(3, "C");
		}else{
			preparedStatement.setString(3, "L");
		}
		preparedStatement.setInt(4, mainCategoryId);
		preparedStatement.setInt(5, mainTypeCode);
		status = preparedStatement.executeUpdate();
		if(status==0){
			xml ="<response><status>success</status><value>Notadded</value></response>";
		}else{
			xml ="<response><status>success</status><value>update</value><code>"+mainTypeCode+"</code></response>";				
		}
		return xml;
	}
	public String misMainTypeList(String categoryCode) throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="SELECT a.SCH_MAIN_CATEGORY_CODE AS SCH_MAIN_CATEGORY_CODE, " +
		"  a.SCH_MAIN_TYPE_CODE          AS SCH_MAIN_TYPE_CODE, " +
		"  a.SCH_MAIN_TYPE_DESC          AS SCH_MAIN_TYPE_DESC, " +
		"  a.STATUS                      AS STATUS, " +
		"  b.SCH_MAIN_CATEGORY_DESC      AS SCH_MAIN_CATEGORY_DESC " +
		"FROM " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_TYPE_CODE, " +
		"    SCH_MAIN_TYPE_DESC, " +
		"    STATUS " +
		"  FROM FAS_WE_SCH_MAIN_TYPES ";
		if(!categoryCode.equalsIgnoreCase("select")){
			sql+=" WHERE SCH_MAIN_CATEGORY_CODE="+Integer.parseInt(categoryCode);
		}
		sql+="  )a " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_CATEGORY_DESC " +
		"  FROM FAS_WE_SCH_MAIN_CATEGORY " +
		"  )b " +
		"ON a.SCH_MAIN_CATEGORY_CODE=b.SCH_MAIN_CATEGORY_CODE " +
		"ORDER BY a.SCH_MAIN_TYPE_CODE";		
		preparedStatement = connection.prepareStatement(sql);		
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"				
				+"<MAJOR_TYPE_NAME>"+resultSet.getString("SCH_MAIN_CATEGORY_DESC")+"</MAJOR_TYPE_NAME>"
				+"<MAIN_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_TYPE_CODE")+"</MAIN_TYPE_CODE>"
				+"<MAIN_TYPE_NAME>"+resultSet.getString("SCH_MAIN_TYPE_DESC")+"</MAIN_TYPE_NAME>"
				+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>";
			change++;
		}
		if(change == 0){
			xml += "<status>fail</status>";
		}else{
			xml += "<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String editMainCode(int mainCategoryId, int mainTypeCode) throws SQLException{
		xml = "";
		connection = load.getConnection();
		sql="SELECT a.SCH_MAIN_CATEGORY_CODE AS SCH_MAIN_CATEGORY_CODE, " +
		"  a.SCH_MAIN_TYPE_CODE          AS SCH_MAIN_TYPE_CODE, " +
		"  a.SCH_MAIN_TYPE_DESC          AS SCH_MAIN_TYPE_DESC, " +
		"  a.STATUS                      AS STATUS, " +
		"  b.SCH_MAIN_CATEGORY_DESC      AS SCH_MAIN_CATEGORY_DESC " +
		"FROM " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_TYPE_CODE, " +
		"    SCH_MAIN_TYPE_DESC, " +
		"    STATUS " +
		"  FROM FAS_WE_SCH_MAIN_TYPES " +
		"  WHERE SCH_MAIN_CATEGORY_CODE=? " +
		"  AND SCH_MAIN_TYPE_CODE      =? " +
		"  )a " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_CATEGORY_DESC " +
		"  FROM FAS_WE_SCH_MAIN_CATEGORY " +
		"  )b " +
		"ON a.SCH_MAIN_CATEGORY_CODE=b.SCH_MAIN_CATEGORY_CODE";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, mainCategoryId);
		preparedStatement.setInt(2, mainTypeCode);
		resultSet = preparedStatement.executeQuery();
		xml="<response>";
		if(resultSet.next()){
			xml += "<status>success</status>"			
			+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>"
			+"<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"
			+"<MAJOR_TYPE_NAME>"+resultSet.getString("SCH_MAIN_CATEGORY_DESC")+"</MAJOR_TYPE_NAME>"
			+"<SUB_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_TYPE_CODE")+"</SUB_TYPE_CODE>"
			+"<SUB_TYPE_NAME>"+resultSet.getString("SCH_MAIN_TYPE_DESC")+"</SUB_TYPE_NAME>";
		}else{
			xml += "<status>fail</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String loadMainType(int majorCode) throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="SELECT SCH_MAIN_CATEGORY_CODE, " +
		"  SCH_MAIN_TYPE_CODE, " +
		"  SCH_MAIN_TYPE_DESC " +
		"FROM FAS_WE_SCH_MAIN_TYPES " +
		"WHERE SCH_MAIN_CATEGORY_CODE=? " +
		"AND STATUS                 <>'C'";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, majorCode);
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<MAIN_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_TYPE_CODE")+"</MAIN_TYPE_CODE>" +
			  "<MAIN_TYPE_DESC>"+resultSet.getString("SCH_MAIN_TYPE_DESC")+"</MAIN_TYPE_DESC>";
			change++;
		}
		if(change == 0){
			xml +="<status>fail</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String subTypeList(String categoryCode, String mainTypeCode) throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="SELECT a.SCH_MAIN_CATEGORY_CODE AS SCH_MAIN_CATEGORY_CODE, " +
		"  a.SCH_MAIN_TYPE_CODE          AS SCH_MAIN_TYPE_CODE, " +
		"  a.SCH_SUB_TYPE_CODE           AS SCH_SUB_TYPE_CODE, " +
		"  a.SCH_SUB_TYPE_DESC           AS SCH_SUB_TYPE_DESC, " +
		"  a.STATUS                      AS STATUS, " +
		"  b.SCH_MAIN_TYPE_DESC          AS SCH_MAIN_TYPE_DESC, " +
		"  c.SCH_MAIN_CATEGORY_DESC      AS SCH_MAIN_CATEGORY_DESC " +
		"FROM " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_TYPE_CODE, " +
		"    SCH_SUB_TYPE_CODE, " +
		"    SCH_SUB_TYPE_DESC, " +
		"    STATUS " +
		"  FROM FAS_WE_SCH_SUB_TYPES " ;
		if(!categoryCode.equalsIgnoreCase("select")){
			sql+=" WHERE SCH_MAIN_CATEGORY_CODE="+Integer.parseInt(categoryCode);
		}
		if(!mainTypeCode.equalsIgnoreCase("select")){
			sql+=" AND SCH_MAIN_TYPE_CODE="+Integer.parseInt(mainTypeCode);
		}
		sql+="  )a " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_TYPE_CODE, " +
		"    SCH_MAIN_TYPE_DESC " +
		"  FROM FAS_WE_SCH_MAIN_TYPES " +
		"  )b " +
		"ON a.SCH_MAIN_CATEGORY_CODE=b.SCH_MAIN_CATEGORY_CODE " +
		"AND a.SCH_MAIN_TYPE_CODE   =b.SCH_MAIN_TYPE_CODE " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_CATEGORY_DESC " +
		"  FROM FAS_WE_SCH_MAIN_CATEGORY " +
		"  )c " +
		"ON a.SCH_MAIN_CATEGORY_CODE=c.SCH_MAIN_CATEGORY_CODE " +
		"ORDER BY a.SCH_SUB_TYPE_CODE";
		preparedStatement = connection.prepareStatement(sql);		
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"				
				+"<MAJOR_TYPE_NAME>"+resultSet.getString("SCH_MAIN_CATEGORY_DESC")+"</MAJOR_TYPE_NAME>"
				+"<MAIN_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_TYPE_CODE")+"</MAIN_TYPE_CODE>"
				+"<MAIN_TYPE_DESC>"+resultSet.getString("SCH_MAIN_TYPE_DESC")+"</MAIN_TYPE_DESC>"
				+"<SUB_TYPE_CODE>"+resultSet.getInt("SCH_SUB_TYPE_CODE")+"</SUB_TYPE_CODE>"
				+"<SUB_TYPE_NAME>"+resultSet.getString("SCH_SUB_TYPE_DESC")+"</SUB_TYPE_NAME>"
				+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>";
			change++;
		}
		if(change == 0){
			xml += "<status>fail</status>";
		}else{
			xml += "<status>success</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String editSubType(int mainCategoryId, int mainTypeCode, int subCode) throws SQLException{
		xml = "";
		connection = load.getConnection();
		sql="SELECT a.SCH_MAIN_CATEGORY_CODE AS SCH_MAIN_CATEGORY_CODE, " +
		"  a.SCH_MAIN_TYPE_CODE          AS SCH_MAIN_TYPE_CODE, " +
		"  a.SCH_SUB_TYPE_CODE           AS SCH_SUB_TYPE_CODE, " +
		"  a.SCH_SUB_TYPE_DESC           AS SCH_SUB_TYPE_DESC, " +
		"  a.STATUS                      AS STATUS, " +
		"  b.SCH_MAIN_TYPE_DESC          AS SCH_MAIN_TYPE_DESC " +
		"FROM " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_TYPE_CODE, " +
		"    SCH_SUB_TYPE_CODE, " +
		"    SCH_SUB_TYPE_DESC, " +
		"    STATUS " +
		"  FROM FAS_WE_SCH_SUB_TYPES " +
		"  WHERE SCH_MAIN_CATEGORY_CODE=? " +
		"  AND SCH_MAIN_TYPE_CODE      =? " +
		"  AND SCH_SUB_TYPE_CODE       =? " +
		"  )a " +
		"LEFT OUTER JOIN " +
		"  (SELECT SCH_MAIN_CATEGORY_CODE, " +
		"    SCH_MAIN_TYPE_CODE, " +
		"    SCH_MAIN_TYPE_DESC " +
		"  FROM FAS_WE_SCH_MAIN_TYPES " +
		"  )b " +
		"ON a.SCH_MAIN_CATEGORY_CODE=b.SCH_MAIN_CATEGORY_CODE " +
		"AND a.SCH_MAIN_TYPE_CODE   =b.SCH_MAIN_TYPE_CODE" ;
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, mainCategoryId);
		preparedStatement.setInt(2, mainTypeCode);
		preparedStatement.setInt(3, subCode);
		resultSet = preparedStatement.executeQuery();
		xml="<response>";
		if(resultSet.next()){
			xml += "<status>success</status>"			
			+"<STATUSTYPE>"+resultSet.getString("STATUS")+"</STATUSTYPE>"
			+"<MAJOR_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_CATEGORY_CODE")+"</MAJOR_TYPE_CODE>"
			+"<MAIN_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_TYPE_CODE")+"</MAIN_TYPE_CODE>"
			+"<MAIN_TYPE_NAME>"+resultSet.getString("SCH_MAIN_TYPE_DESC")+"</MAIN_TYPE_NAME>"
			+"<SUB_TYPE_CODE>"+resultSet.getInt("SCH_MAIN_TYPE_CODE")+"</SUB_TYPE_CODE>"
			+"<SUB_TYPE_NAME>"+resultSet.getString("SCH_SUB_TYPE_DESC")+"</SUB_TYPE_NAME>";
		}else{
			xml += "<status>fail</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String addMisSubType(int mainCategoryId, int mainTypeCode, String subTypeDesc,
			String userid) throws SQLException {
		sql="";
		xml = "";
		int max = 1;
		int errorCode = 0;
		boolean isInsert = false;
		connection = load.getConnection();
		sql="select max(SCH_SUB_TYPE_CODE) as max from FAS_WE_SCH_SUB_TYPES where SCH_MAIN_CATEGORY_CODE=? and SCH_MAIN_TYPE_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, mainCategoryId);
		preparedStatement.setInt(2, mainTypeCode);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			max = resultSet.getInt("max");
			max += 1;					
		}
		sql="SELECT * " +
		"FROM FAS_WE_SCH_SUB_TYPES " +
		"WHERE SCH_MAIN_CATEGORY_CODE='"+mainCategoryId+"' " +
		"AND SCH_MAIN_TYPE_CODE  ='"+mainTypeCode+"' " +
		"AND SCH_SUB_TYPE_DESC    ='"+subTypeDesc+"'";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			isInsert = false;
		}else{
			isInsert = true;
		}
		if(isInsert){				
			sql="insert into FAS_WE_SCH_SUB_TYPES(SCH_MAIN_CATEGORY_CODE,SCH_MAIN_TYPE_CODE,SCH_SUB_TYPE_CODE,SCH_SUB_TYPE_DESC,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,SYSTIMESTAMP)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, mainCategoryId);
			preparedStatement.setInt(2, mainTypeCode);
			preparedStatement.setInt(3, max);
			preparedStatement.setString(4, subTypeDesc);
			preparedStatement.setString(5, "L");
			preparedStatement.setString(6, userid);
			errorCode = preparedStatement.executeUpdate();
			if(errorCode==0){
				xml="<response><status>success</status><value>Notadded</value></response>";	
		 	 }else{
		 		xml="<response><status>success</status><value>added</value><code>"+max+"</code></response>";
		 	 }
		}
		return xml;
	}
	public String updateSubTypes(int mainCategoryId, int mainTypeCode, int subTypeCode,
			String subTypeDesc, String userid, String delete) throws SQLException {
		
		xml = "";
		int status = 0;
		connection = load.getConnection();
		sql ="update FAS_WE_SCH_SUB_TYPES set SCH_SUB_TYPE_DESC=?," +
				"UPDATED_BY_USER_ID=?,UPDATED_DATE=SYSTIMESTAMP,STATUS=? where SCH_MAIN_CATEGORY_CODE=?" +
				"and SCH_MAIN_TYPE_CODE=? and SCH_SUB_TYPE_CODE=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1,subTypeDesc);
		preparedStatement.setString(2, userid);
		if(delete.equalsIgnoreCase("y")){
			preparedStatement.setString(3, "C");
		}else{
			preparedStatement.setString(3, "L");
		}
		preparedStatement.setInt(4, mainCategoryId);
		preparedStatement.setInt(5, mainTypeCode);
		preparedStatement.setInt(6, subTypeCode);
		status = preparedStatement.executeUpdate();
		if(status==0){
			xml ="<response><status>success</status><value>Notadded</value></response>";
		}else{
			xml ="<response><status>success</status><value>update</value><code>"+subTypeCode+"</code></response>";				
		}
		return xml;
	}

}
