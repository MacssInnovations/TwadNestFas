package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

public class TBunfreezedReqRaisedImpl {
	Connection connection = null;
	CallableStatement callableStatement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	LoadDriver loadDriver = new LoadDriver();
	String sql="";
	String xml = "";
	public synchronized List<Integer> addUnfreezeRequestRaised(int accountingUnit,int officeId,int cashbookYear,int cashbookMonth,String userId) throws SQLException,Exception{
		int errorCode = 0;
		int status = 0;
		int max = 1;
		String flag = "",change="";
		boolean isInserted = false;
		List<Integer> list = new ArrayList<Integer>();
		connection = loadDriver.getConnection();
		sql="select count(*) as status_code from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, accountingUnit);
		preparedStatement.setInt(2, cashbookYear);
		preparedStatement.setInt(3, cashbookMonth);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			if(resultSet.getInt("status_code")>0)
			{
			isInserted = true;
			}
		}
		
		System.out.println("Inserted====>"+isInserted);
		if(isInserted){
			System.out.println("is inserted:");
			sql="select max(REQUEST_NO) as REQUEST_NO,STATUS from FAS_TB_UNFREEZE_REQUEST where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? GROUP BY STATUS";
			
			System.out.println("SQL========>"+sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountingUnit);
			preparedStatement.setInt(2, cashbookYear);
			preparedStatement.setInt(3, cashbookMonth);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				if(resultSet.getString("STATUS").equalsIgnoreCase("C")){
					flag="insert";
					max=resultSet.getInt("REQUEST_NO");
					max +=1;
					change = "O";
					status = 1;
				}
				else
				{
					flag="already";
				}
			}
			else{
				flag="insert";				
				max = 1;
				change = "O";
				status = 1;
			}
			System.out.println("flag::"+flag);
			sql="call FAS_TB_UNFREEZEREQUEST(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric)";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, accountingUnit);
			callableStatement.setInt(2, officeId);
			callableStatement.setInt(3, cashbookYear);
			callableStatement.setInt(4, cashbookMonth);
			callableStatement.registerOutParameter(5, Types.NUMERIC);
			callableStatement.setNull(5, java.sql.Types.NUMERIC);
			callableStatement.setString(6, flag);
			callableStatement.setString(7, userId);
			callableStatement.setString(8, change);
			callableStatement.setInt(9, max);
			callableStatement.executeUpdate();
//			errorCode = callableStatement.getInt(5);	
			errorCode = callableStatement.getBigDecimal(5).intValue();
		}
		
		System.out.println("FLAG====>"+flag);
		
		if(flag.equals("already"))
		{
			System.out.println("nullll");
			errorCode=-1;
		}
		
		System.out.println("errorCode "+errorCode);
		list.add(errorCode);
		list.add(status);
		return list;
	}
	public String loadAccountUnit(int cashbookYear, int cashbookMonth) throws SQLException,Exception{
		xml = "";
		int count = 0;
		connection = loadDriver.getConnection();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT a.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, \n");
		stringBuffer.append("  b.accounting_unit_name    AS accounting_unit_name \n");
		stringBuffer.append("FROM \n");
		stringBuffer.append("  (SELECT ACCOUNTING_UNIT_ID \n");
		stringBuffer.append("  FROM FAS_TB_UNFREEZE_REQUEST \n");
		stringBuffer.append("  WHERE STATUS      ='O' \n");
		stringBuffer.append("  AND CASHBOOK_YEAR =? \n");
		stringBuffer.append("  AND CASHBOOK_MONTH=? \n");
		stringBuffer.append("  )a \n");
		stringBuffer.append("LEFT OUTER JOIN \n");
		stringBuffer.append("  ( SELECT accounting_unit_id, accounting_unit_name FROM fas_mst_acct_units \n");
		stringBuffer.append("  )b \n");
		stringBuffer.append("ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id \n");
		stringBuffer.append("ORDER BY a.ACCOUNTING_UNIT_ID");
		xml = "<response><command>loadaccount</command>";
		preparedStatement = connection.prepareStatement(stringBuffer.toString());
		preparedStatement.setInt(1, cashbookYear);
		preparedStatement.setInt(2, cashbookMonth);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<ACCOUNTING_UNIT_ID>"+resultSet.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>"+
				  "<accounting_unit_name>"+resultSet.getString("accounting_unit_name")+"</accounting_unit_name>";
			count++;
		}
		if(count>0){
			xml +="<status>success</status>";
		}else{
			xml +="<status>nodata</status>";
		}
		xml +="</response>";
		return xml;
	}
	//modified 16Jul2012
	public String loadstatus(int cashbookYear, int cashbookMonth, int accunitid) throws SQLException,Exception{
		xml = "";
		int count = 0;
		connection = loadDriver.getConnection();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT count(Tb_Status) as countTB From Fas_Trial_Balance_Status \n");
		stringBuffer.append("  Where Cashbook_Year=?  \n");
		stringBuffer.append("  and CASHBOOK_MONTH >? and ACCOUNTING_UNIT_ID=? \n");
				
		xml = "<response><command>loadstat</command>";
		preparedStatement = connection.prepareStatement(stringBuffer.toString());
		preparedStatement.setInt(1, cashbookYear);
		preparedStatement.setInt(2, cashbookMonth);
		preparedStatement.setInt(3, accunitid);
		resultSet = preparedStatement.executeQuery();
		int c=0;
		while(resultSet.next()){
			xml +="<countTB>"+resultSet.getInt("countTB")+"</countTB>";
			c=resultSet.getInt("countTB");
			count++;
		}
		System.out.println("c value :: "+c);
		if(c>0){
			xml +="<status>success</status>";
		}else{
			xml +="<status>nodata</status>";
		}
		xml +="</response>";
		return xml;
	}
	public String loadstatusSJV(int cashbookYear, int cashbookMonth, int accunitid, int sup_No) throws SQLException,Exception{
		xml = "";
		String msg= "";
		int count = 0;
		connection = loadDriver.getConnection();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT count(Tb_Status) as countTB From Fas_Trial_Balance_Status_sjv \n");
		stringBuffer.append("  Where Cashbook_Year=?  \n");
		stringBuffer.append("  and CASHBOOK_MONTH =? and ACCOUNTING_UNIT_ID=? and SUPPLEMENT_NO > ?\n");
				
		xml = "<response><command>loadstatSJV</command>";
		preparedStatement = connection.prepareStatement(stringBuffer.toString());
		preparedStatement.setInt(1, cashbookYear);
		preparedStatement.setInt(2, cashbookMonth);
		preparedStatement.setInt(3, accunitid);
		preparedStatement.setInt(4, sup_No);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<countTB>"+resultSet.getInt("countTB")+"</countTB>";
			count++;
		}
		if(count>0){
			//msg = "Raise the Request for subsequent supplement Number first";
			//sendMessage(response, msg, "ok");
			xml +="<status>success</status>";
		}else{
			xml +="<status>nodata</status>";
		}
		xml +="</response>";
		return xml;
		//return msg;
	}
	public synchronized List<Integer> addUnfreezeSusRequestRaised(int accountingUnit,int supplementNo,int cashbookYear,int cashbookMonth,String userId) throws SQLException,Exception{
		int errorCode = 0;
		int status = 0;
		int max = 1;
		String flag = "",change="";
		boolean isInserted = false;
		List<Integer> list = new ArrayList<Integer>();
		connection = loadDriver.getConnection();
		sql="select count(*) as status_code from FAS_TRIAL_BALANCE_STATUS_SJV where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, accountingUnit);
		preparedStatement.setInt(2, cashbookYear);
		preparedStatement.setInt(3, cashbookMonth);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			isInserted = true;
		}
		System.out.println(" isInserted  "+isInserted);
		if(isInserted){
			sql="select max(REQUEST_NO) as REQUEST_NO,STATUS from FAS_TB_SUS_UNFREEZE_REQUEST where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? GROUP BY STATUS";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountingUnit);
			preparedStatement.setInt(2, cashbookYear);
			preparedStatement.setInt(3, cashbookMonth);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				if(resultSet.getString("STATUS").equalsIgnoreCase("C")){
					flag="insert";
					max=resultSet.getInt("REQUEST_NO");
					max +=1;
					change = "O";
					status = 1;
				}
			}else{
				flag="insert";				
				max = 1;
				change = "O";
				status = 1;
			}
			sql="call FAS_TB_SUS_UNFREEZEREQUEST(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric)";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, accountingUnit);
			callableStatement.setInt(2, supplementNo);
			callableStatement.setInt(3, cashbookYear);
			callableStatement.setInt(4, cashbookMonth);
			callableStatement.registerOutParameter(5, Types.NUMERIC);
			callableStatement.setInt(5,0);
			callableStatement.setString(6, flag);
			callableStatement.setString(7, userId);
			callableStatement.setString(8, change);
			callableStatement.setInt(9, max);
			try {
			callableStatement.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			//errorCode = callableStatement.getInt(5);
			
			errorCode= callableStatement.getBigDecimal(5).intValue();
			
		}
		
		System.out.println("errorCode "+errorCode);
		list.add(errorCode);
		list.add(status);
		return list;
	}
	private void sendMessage(HttpServletResponse response,String msg,String bType) {
        try
        {
            String url="org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);          
        }
        catch(IOException e)
        {
        System.out.println("ERROR");
        }
    }


}
