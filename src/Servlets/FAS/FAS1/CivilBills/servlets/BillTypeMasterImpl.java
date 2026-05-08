package Servlets.FAS.FAS1.CivilBills.servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BillTypeMasterImpl {
	Connection connection = null;
	ResultSet resultSet = null;
	LoadDriver load = new LoadDriver();	
	String sql = "";
	String xml = "";
	int errorCode = 0;
	public String viewExistingDetails() throws SQLException{
		int change = 0;
		connection = load.getConnection();
		xml = "";
		sql="select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC,REMARKS,STATUS from FAS_BILL_MAJOR_TYPES order by BILL_MAJOR_TYPE_CODE";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);		
		resultSet = preparedStatement.executeQuery();
		xml = "<response>";
		while(resultSet.next()){
			xml +="<BILL_CODE>"+resultSet.getInt("BILL_MAJOR_TYPE_CODE")+"</BILL_CODE>"				
				+"<BILL_DESC>"+resultSet.getString("BILL_MAJOR_TYPE_DESC")+"</BILL_DESC>"
				+"<REMARKS>"+resultSet.getString("REMARKS")+"</REMARKS>"
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
	public String addBillType(String billDesc, String remarks, String userid) throws SQLException{
		connection = load.getConnection();
		xml = "";
		int billSlNo = 1;		
		xml="<response><command>added</command>";		
		PreparedStatement preparedStatement = null;		
		sql = "select MAX(BILL_MAJOR_TYPE_CODE)as max from FAS_BILL_MAJOR_TYPES";
		preparedStatement = connection.prepareStatement(sql);		
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			if(resultSet.getString("max") != null){
				billSlNo = resultSet.getInt("max");
				billSlNo = billSlNo + 1;
			}
		}		
		CallableStatement callableStatement = null; 
		sql="{call FAS_BILL_MASTER(?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, billSlNo);
		callableStatement.setString(2, billDesc);
		callableStatement.setString(3, remarks);
		callableStatement.setString(4, userid);
		callableStatement.setString(5, "insert");
		callableStatement.registerOutParameter(6, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(6);		
	 	if(errorCode!=0){
	 	 	xml +="<flag>not</flag>";	
	 	 }else{
	 	 	xml +="<flag>success</flag>";
	 	 }
	 	System.out.println("errorCode "+errorCode);
		xml +="<status>success</status></response>";
		return xml;
	}
	public String editBillType(int billCode) throws SQLException {
		// TODO Auto-generated method stub
		xml = "";
		connection = load.getConnection();
		sql="select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC,REMARKS from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, billCode);		
		resultSet = preparedStatement.executeQuery();
		xml="<response>";
		if(resultSet.next()){
			xml += "<status>success</status>"
			+"<BILL_CODE>"+resultSet.getInt("BILL_MAJOR_TYPE_CODE")+"</BILL_CODE>"				
			+"<BILL_DESC>"+resultSet.getString("BILL_MAJOR_TYPE_DESC")+"</BILL_DESC>"
			+"<REMARKS>"+resultSet.getString("REMARKS")+"</REMARKS>";
		}else{
			xml += "<status>fail</status>";
		}
		xml += "</response>";
		return xml;
	}
	public String updateBillType(int billCode, String billDesc, String remarks, String userid) throws SQLException {
		// TODO Auto-generated method stub
		connection = load.getConnection();
		xml = "";
		CallableStatement callableStatement = null; 
		sql="{call FAS_BILL_MASTER(?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, billCode);
		callableStatement.setString(2, billDesc);
		callableStatement.setString(3, remarks);
		callableStatement.setString(4, userid);
		callableStatement.setString(5, "update");		
		callableStatement.registerOutParameter(6, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(6);
		xml="<response><command>update</command>";
 	 	if(errorCode!=0){
 	 		xml +="<flag>not</flag>";	
 	 	}else{
 	 		xml +="<flag>success</flag>";
 	 	} 	 	
 	 	xml +="<status>success</status></response>";
		return xml;
	}
	public String deleteBillType(int billCode, String billDesc, String remarks,String userid) throws SQLException {
		// TODO Auto-generated method stub
		connection = load.getConnection();
		xml = "";
		CallableStatement callableStatement = null; 
		sql="{call FAS_BILL_MASTER(?,?,?,?,?,?)}";
		callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, billCode);
		callableStatement.setString(2, billDesc);
		callableStatement.setString(3, remarks);
		callableStatement.setString(4, userid);
		callableStatement.setString(5, "validate");		
		callableStatement.registerOutParameter(6, Types.INTEGER);
		callableStatement.executeUpdate();
		errorCode = callableStatement.getInt(6);
		xml="<response><command>delete</command>";
 	 	if(errorCode!=0){
 	 		xml +="<flag>not</flag>";	
 	 	}else{
 	 		xml +="<flag>success</flag>";
 	 	} 	 	
 	 	xml +="<status>success</status></response>";
		return xml;
	}
	
	public Date date_convertion(String dojj){
        java.sql.Date formatted_date = null;
        String dateString1 = dojj;
        java.util.Date d1 = null;       

        if(dojj==""){           
            formatted_date=null;           
        } else {     
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");           
            try {
                d1 = dateFormat1.parse(dojj.trim());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dateFormat1.applyPattern("yyyy-MM-dd");
            dateString1 = dateFormat1.format(d1);
            formatted_date = java.sql.Date.valueOf(dateString1);           
        }
        return formatted_date;
    }
	
}
