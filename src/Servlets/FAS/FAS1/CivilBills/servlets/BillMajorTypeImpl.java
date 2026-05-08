package Servlets.FAS.FAS1.CivilBills.servlets;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BillMajorTypeImpl extends BillTypeMasterImpl{
	int count;
	public String viewExistingDetails() throws SQLException{
		return super.viewExistingDetails();
	}
	
	public String loadBillNo(int unitcode) throws SQLException{
		xml = "";
		sql="";
		count = 0;
		connection = load.getConnection();
		xml = "<response><command>account</command>";//int billMajorType,
		sql="select BILL_NO from FAS_BILL_REGISTER_MASTER where ACCOUNTING_UNIT_ID=? and (Bill_Approved <>'y' OR BILL_APPROVED IS NULL)";//BILL_MAJOR_TYPE=? and 		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		//preparedStatement.setInt(1,billMajorType);
		preparedStatement.setInt(1,unitcode);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<BILL_NO>"+resultSet.getInt("BILL_NO")+"</BILL_NO>";
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
	public String loadpassNo(int officecode,int unitcode) throws SQLException{
		xml = "";
		sql="";
		count = 0;
		connection = load.getConnection();
		xml = "<response><command>passorder</command>";
		sql = "SELECT distinct m.PASS_ORDER_NO,"
				// + "t.slno,"
				// + "t.sanction_proc_no,"
				+ "m.CASHBOOK_YEAR,m.CASHBOOK_MONTH,T.BILL_NO, T.BILL_AMOUNT "
				+
				// " t.bill_amount,T.BILL_NO " +
				"	FROM FAS_PASS_ORDER_MST m,fas_pass_order_trn t "
				+ "	WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "
				+ "	and m.accounting_for_office_id=t.accounting_for_office_id "
				+ "	and m.cashbook_year=t.cashbook_year "
				+ "	and m.cashbook_month=t.cashbook_month "
				+ " and m.pass_order_no=t.pass_order_no "
				+ " and m.ACCOUNTING_UNIT_ID    =? "
				+ " AND m.ACCOUNTING_FOR_OFFICE_ID=? "
				+ " and m.status='L' and APPROVED_BY is null"
				+ " and (t.bill_date , t.bill_no)  not in ( "
				+ " 		select tt.bill_date , tt.bill_no  from FAS_BILL_REGISTER_MASTERNEW tt "
				+ " 		where tt.bill_date =t.bill_date  and tt.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID and tt.bill_no=t.bill_no and tt.status='C'union all "
				+

				" 		select tt.bill_date , tt.bill_no  from FAS_BILL_REGISTER_MASTER tt "
				+ " 		where tt.bill_date =t.bill_date  and tt.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID and tt.bill_no=t.bill_no and tt.status='C' and tt.bill_date < '01-Apr-15' "
				+ " 				) " + " order by PASS_ORDER_NO";
		System.out.println("sql:::"+sql);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setInt(1,unitcode);
		preparedStatement.setInt(2,officecode);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<PASS_ORDER_NO>"+resultSet.getInt("PASS_ORDER_NO")+"</PASS_ORDER_NO>";
			//xml +="<slno>"+resultSet.getInt("slno")+"</slno>";
			//xml +="<sanction_proc_no>"+resultSet.getInt("sanction_proc_no")+"</sanction_proc_no>";
			xml +="<sanction_proc_no>''</sanction_proc_no>";
			xml +="<CASHBOOK_YEAR>"+resultSet.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>";
			xml +="<CASHBOOK_MONTH>"+resultSet.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>";
			xml +="<BILL_NO>"+resultSet.getInt("BILL_NO")+"</BILL_NO>";
		//	Double bill_amt = resultSet.getDouble("bill_amount");
		//	int BILL_AMOUNT = bill_amt.intValue();
			xml +="<BILL_AMOUNT>"+resultSet.getInt("BILL_AMOUNT")+"</BILL_AMOUNT>";
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
	
	
	
public String loadbillDetailsMTC(int officecode,int unitcode,int year,int month){
	try{
	xml = "";
		sql="";
		count = 0;
		connection = load.getConnection();
		xml = "<response><command>loadbillDetailsMTC</command>";
		sql =  " 		(SELECT BILL_NO "
				+ " 			FROM FAS_BILL_REGISTER_MASTERNEW "
				+ " 		WHERE status                 ='L' "
				+ " 		AND ACCOUNTING_UNIT_ID       = ? "
				+ " 		AND ACCOUNTING_UNIT_OFFICE_ID= ?"
				+ " 		AND CASHBOOK_YEAR            = ?"
				+ " 		AND CASHBOOK_MONTH           =  ?"
				+ " 		AND BILL_APPROVED            ='Y' "
				+ " 		AND MTC70ENTRY               ='Y' "
				+ " 		AND MTC_70_REGISTER_DATE    IS NULL "
			+ " 			) "
				+ " 		UNION ALL "
				+ " 		  (SELECT BILL_NO "
				+ " 		  FROM FAS_BILL_REGISTER_MASTER "
				+ " 		  WHERE status                 ='L' "
				+ " 		  AND ACCOUNTING_UNIT_ID       = ? "
			+ " 			  AND ACCOUNTING_UNIT_OFFICE_ID= ?"
			+ " 			  AND CASHBOOK_YEAR            = ?"
			+ " 			  AND CASHBOOK_MONTH           = ?"
			+ " 			  AND BILL_APPROVED            ='Y' "
			+ " 			  AND MTC70ENTRY               ='Y' "
			+ " 			  AND MTC_70_REGISTER_DATE    IS NULL "
			+ "	 ) ";
		System.out.println("sql:::"+sql);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setInt(1,unitcode);
		preparedStatement.setInt(2,officecode);
		preparedStatement.setInt(3,year);
		preparedStatement.setInt(4,month);
		preparedStatement.setInt(5,unitcode);
		preparedStatement.setInt(6,officecode);
		preparedStatement.setInt(7,year);
		preparedStatement.setInt(8,month);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			xml +="<BILL_NO>"+resultSet.getInt("BILL_NO")+"</BILL_NO>";
			
			count++;
		}
		if(count==0){
			xml +="<status>nodata</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	return xml;
	}
	
public String savechangeOff(int officecode,int unitcode,int year,int month,int billNo,int chan_Unit,int chan_Office){
	try{
	xml = "";
		sql="";
		count = 0;
		connection = load.getConnection();
		xml = "<response><command>savechangeOff</command>";
		sql =  "UPDATE Fas_Memo_Of_Payment_trn " +
				" SET Payment_Unit            =? , " +
				"  PAYMENT_OFFICE            =? " +
				" WHERE Accounting_Unit_Id    =? " +
				" AND Accounting_For_Office_Id=? " +
				" AND Cashbook_Year           =? " +
				" AND Cashbook_Month          =?" +
				" AND Bill_No                 =?"
;
		System.out.println("sql:::"+sql);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setInt(1,chan_Unit);
		preparedStatement.setInt(2,chan_Office);
		preparedStatement.setInt(3,unitcode);
		preparedStatement.setInt(4,officecode);
		preparedStatement.setInt(5,year);
		preparedStatement.setInt(6,month);
		preparedStatement.setInt(7,billNo);
		
	int l=preparedStatement.executeUpdate();
		
		if(l==0){
			xml +="<status>Failure</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	return xml;
	}
public String loadbillMTC(int officecode,int unitcode,int year,int month,int billNo){
	try{
	xml = "";
		sql="";
		count = 0;
		connection = load.getConnection();
		xml = "<response><command>loadbillMTC</command>";
		sql =  "SELECT a.*, " +
				"  bill_major_type_desc, " +
				"  BILL_MINOR_TYPE_DESC, " +
				"  j2.bill_sub_type_desc ,  nvl( e.employee_initial , '')||' '|| e.employee_name as emp_pass_app " +
				" FROM ( " +
				"  (SELECT Sanction_Proc_No, " +
				"   to_char( Bill_Date,'dd/mm/yy') as Bill_Date , " +
				"    to_char( Bill_Scrutiny_Date,'dd/mm/yy') as Bill_Scrutiny_Date,  " +
				"    Bill_Minor_Type_Code, " +
				"    BILL_MAJOR_TYPE, " +
				"    Bill_Sub_Type_Code, " +
				"    TOTAL_SANCTIONED_AMOUNT, " +
				"    Total_Bill_Amount, " +
				"    DEDUCTED_AMOUNT, " +
				"   to_char( Pass_Order_Date,'dd/mm/yy') as Pass_Order_Date , " +
				"    Pass_Order_By,NET_AMOUNT, " +
				"    PASS_ORDER_AMOUNT " +
				"  FROM FAS_BILL_REGISTER_MASTERNEW " +
				"  WHERE Status                 ='L' " +
				"  AND Accounting_Unit_Id       = ? " +
				"  AND Accounting_Unit_Office_Id= ? " +
				"  AND Cashbook_Year            = ? " +
				"  AND CASHBOOK_MONTH           = ? " +
				"  AND BILL_APPROVED            ='Y' " +
				"  AND Mtc70entry               ='Y' " +
				"  AND BILL_NO                  =? " +
				"  AND MTC_70_REGISTER_DATE    IS NULL " +
				"  ) " +
				" UNION ALL " +
				"  (SELECT Sanction_Proc_No, " +
				"   to_char( Bill_Date,'dd/mm/yy') as Bill_Date , " +
				"   to_char( Bill_Scrutiny_Date,'dd/mm/yy') as Bill_Scrutiny_Date,  " +
				"    Bill_Minor_Type_Code, " +
				"    BILL_MAJOR_TYPE, " +
				"    Bill_Sub_Type_Code, " +
				"    TOTAL_SANCTIONED_AMOUNT, " +
				"    Total_Bill_Amount, " +
				"    DEDUCTED_AMOUNT, " +
				"   to_char( Pass_Order_Date,'dd/mm/yy') as Pass_Order_Date , " +
				"    Pass_Order_By,NET_AMOUNT, " +
				"    PASS_ORDER_AMOUNT " +
				"  FROM FAS_BILL_REGISTER_MASTER " +
				"  WHERE status                 ='L' " +
				"  AND Accounting_Unit_Id       = ? " +
				"  AND Accounting_Unit_Office_Id= ? " +
				"  AND Cashbook_Year            = ? " +
				"  AND CASHBOOK_MONTH           = ? " +
				"  AND BILL_APPROVED            ='Y' " +
				"  AND MTC70ENTRY               ='Y' " +
				"  AND Mtc_70_Register_Date    IS NULL " +
				"  AND BILL_NO                  =? " +
				"  ) )A " +
				" INNER JOIN Fas_Bill_Major_Types J " +
				" ON J.Bill_Major_Type_Code=A.Bill_Major_Type " +
				" INNER JOIN Fas_Bill_Minor_Types_Mst J1 " +
				" ON J1.Bill_Major_Type_Code =A.Bill_Major_Type " +
				" AND j1.BILL_MINOR_TYPE_CODE=a.Bill_Minor_Type_Code " +
				"  INNER JOIN Fas_Bill_Sub_Types J2 " +
				" ON J2.Bill_Major_Type_Code =A.Bill_Major_Type " +
				" AND J2.Bill_Minor_Type_Code=A.Bill_Minor_Type_Code " +
				" AND j2.bill_sub_type_code  =a.Bill_Sub_Type_Code" +
				" Inner Join Hrm_Mst_Employees E "+
                "  on 	 a.Pass_Order_By=e.employee_id";
		System.out.println("sql:::"+sql);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setInt(1,unitcode);
		preparedStatement.setInt(2,officecode);
		preparedStatement.setInt(3,year);
		preparedStatement.setInt(4,month);
		preparedStatement.setInt(5,billNo);
		preparedStatement.setInt(6,unitcode);
		preparedStatement.setInt(7,officecode);
		preparedStatement.setInt(8,year);
		preparedStatement.setInt(9,month);
		preparedStatement.setInt(10,billNo);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			xml +="<Sanction_Proc_No>"+resultSet.getString("Sanction_Proc_No")+"</Sanction_Proc_No>";
			xml +="<Bill_Date>"+resultSet.getString("Bill_Date")+"</Bill_Date>";
			xml +="<Bill_Scrutiny_Date>"+resultSet.getString("Bill_Scrutiny_Date")+"</Bill_Scrutiny_Date>";
			xml +="<Bill_Minor_Type_Code>"+resultSet.getString("Bill_Minor_Type_Code")+'/'+resultSet.getString("BILL_MINOR_TYPE_DESC")+"</Bill_Minor_Type_Code>";
			xml +="<BILL_MAJOR_TYPE>"+resultSet.getString("BILL_MAJOR_TYPE")+'/'+resultSet.getString("bill_major_type_desc")+"</BILL_MAJOR_TYPE>";
			xml +="<Bill_Sub_Type_Code>"+resultSet.getString("Bill_Sub_Type_Code")+'/'+resultSet.getString("bill_sub_type_desc")+"</Bill_Sub_Type_Code>";
			xml +="<TOTAL_SANCTIONED_AMOUNT>"+resultSet.getString("TOTAL_SANCTIONED_AMOUNT")+"</TOTAL_SANCTIONED_AMOUNT>";
			xml +="<Total_Bill_Amount>"+resultSet.getString("Total_Bill_Amount")+"</Total_Bill_Amount>";
			xml +="<DEDUCTED_AMOUNT>"+resultSet.getString("DEDUCTED_AMOUNT")+"</DEDUCTED_AMOUNT>";
			xml +="<Pass_Order_Date>"+resultSet.getString("Pass_Order_Date")+"</Pass_Order_Date>";
			xml +="<Pass_Order_By>"+resultSet.getString("Pass_Order_By")+'/'+resultSet.getString("emp_pass_app")+"</Pass_Order_By>";
			xml +="<PASS_ORDER_AMOUNT>"+resultSet.getString("PASS_ORDER_AMOUNT")+"</PASS_ORDER_AMOUNT>";
			xml +="<NET_AMOUNT>"+resultSet.getString("NET_AMOUNT")+"</NET_AMOUNT>";
			count++;
		}
		if(count==0){
			xml +="<status>nodata</status>";
		}else{
			xml +="<status>success</status>";
		}
		xml +="</response>";
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	return xml;
	}
	
	
	public String getPassOrderDetails( int cmbAcc_UnitCode,int passno,int pass_year,int pass_month) throws SQLException{
		xml ="";
		sql="";
		count = 0;
		connection = load.getConnection();
		xml = "<response><command>passorderdetail</command>";
		System.out.println("pass_amt"+passno);
		/*sql="SELECT distinct m.BILL_NO,  "+
			"  M.Sanction_Proc_No, "+
			" M.Payee_Code, "+
			" TO_CHAR(m.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "+
			" M.Bill_Processing_Done_By, "+
			//" T.Account_Head_Code,"+
			" m.REMARKS, "+
			" M.Status, "+
			" M.Bill_Minor_Type_Code, "+
			" M.Bill_Major_Type, "+
			" M.Bill_Sub_Type_Code, "+
			" M.Total_Sanctioned_Amount, "+
			" m.TOTAL_BILL_AMOUNT, "+
			" M.Mtc70entry, "+
			" To_Char(M.Drawing_Officer_Approve_Date,'dd/MM/yyyy') As Drawing_Officer_Approve_Date, "+
			" M.Bill_Approved, "+
			" To_Char(M.Mtc_70_Register_Date,'dd/MM/yyyy') As Mtc_70_Register_Date, "+
			" M.Deducted_Amount, "+
			" To_Char(M.Pass_Order_Date,'dd/MM/yyyy') As Pass_Order_Date, "+
			" M.Pass_Order_By, "+
			" M.Pass_Order_Amount, "+
			" M.Drawing_Officer_Code, "+
			" m.Reason_For_Reject "+
			" From Fas_Bill_Register_Master M,Fas_Bill_Register_Transaction T "+
			" Where M.Accounting_Unit_Id=T.Accounting_Unit_Id "+
			" And M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id "+
			" And M.Cashbook_Year=T.Cashbook_Year "+
			" And M.Cashbook_Month=T.Cashbook_Month "+
			" And M.Bill_No=T.Bill_No  and bill_type <> 'WOSP' and   m.STATUS='L'"+
			" AND (M.Bill_Approved           <>'Y' or M.Bill_Approved is null) "+
			" and m.Accounting_Unit_Id="+cmbAcc_UnitCode+" and m.Sanction_Proc_No='"+sanc+"'" +
			" and M.TOTAL_SANCTIONED_AMOUNT        = "+pass_amt+ " and m.BILL_NO="+Bill_no+
			
		" UNION ALL " +
		" SELECT distinct m.BILL_NO,  "+
		"  M.Sanction_Proc_No, "+
		" M.Payee_Code, "+
		" TO_CHAR(m.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "+
		" M.Bill_Processing_Done_By, "+
	//	" T.Account_Head_Code,"+
		" m.REMARKS, "+
		" M.Status, "+
		" M.Bill_Minor_Type_Code, "+
		" M.Bill_Major_Type, "+
		" M.Bill_Sub_Type_Code, "+
		" M.Total_Sanctioned_Amount, "+
		" m.TOTAL_BILL_AMOUNT, "+
		" M.Mtc70entry, "+
		" To_Char(M.Drawing_Officer_Approve_Date,'dd/MM/yyyy') As Drawing_Officer_Approve_Date, "+
		" M.Bill_Approved, "+
		" To_Char(M.Mtc_70_Register_Date,'dd/MM/yyyy') As Mtc_70_Register_Date, "+
		" M.Deducted_Amount, "+
		" To_Char(M.Pass_Order_Date,'dd/MM/yyyy') As Pass_Order_Date, "+
		" M.Pass_Order_By, "+
		" M.Pass_Order_Amount, "+
		" M.Drawing_Officer_Code, "+
		" m.Reason_For_Reject "+
		" From Fas_Bill_Register_Master M,Fas_Bill_Register_Transaction T "+
		" Where M.Accounting_Unit_Id=T.Accounting_Unit_Id "+
		" And M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id "+
		" And M.Cashbook_Year=T.Cashbook_Year "+
		" And M.Cashbook_Month=T.Cashbook_Month  and bill_type='WOSP' "+
		" And M.Bill_No=T.Bill_No and  m.STATUS='L'"+
		" AND (M.Bill_Approved           <>'Y' or M.Bill_Approved is null) "+
		" and m.Accounting_Unit_Id="+cmbAcc_UnitCode+" and m.Sanction_Proc_No like '"+sanc+"'"+
		" and M.TOTAL_SANCTIONED_AMOUNT        ="+pass_amt+" and m.BILL_NO ="+Bill_no+
		
		" union all "+
		"  (SELECT distinct m.BILL_NO, "+
		"  M.Sanction_Proc_No, "+
		"  M.Payee_Code, "+
		"  TO_CHAR(m.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "+
		"  M.Bill_Processing_Done_By, "+
	//	" m.Account_Head_Code, "+
		"  m.REMARKS, "+
		"  M.Status, "+
		"   M.Bill_Minor_Type_Code, "+
		"   M.Bill_Major_Type, "+
		"   M.Bill_Sub_Type_Code, "+
		"   M.Total_Sanctioned_Amount, "+
		"  m.TOTAL_BILL_AMOUNT, "+
		"   M.Mtc70entry, "+
		"   TO_CHAR(M.Drawing_Officer_Approve_Date,'dd/MM/yyyy') AS Drawing_Officer_Approve_Date, "+
		  "  M.Bill_Approved, "+
		"   TO_CHAR(M.Mtc_70_Register_Date,'dd/MM/yyyy') AS Mtc_70_Register_Date, "+
		"   M.Deducted_Amount, "+
		"   TO_CHAR(M.Pass_Order_Date,'dd/MM/yyyy') AS Pass_Order_Date, "+
		"  M.Pass_Order_By, "+
		  "  M.Pass_Order_Amount, "+
		"  M.Drawing_Officer_Code, "+
		"  m.Reason_For_Reject "+
		" FROM FAS_BILL_REGISTERNEW M "+
		" WHERE  "+
		"  m.Accounting_Unit_Id       ="+cmbAcc_UnitCode+ " and m.BILL_NO="+Bill_no+
			 " AND (m.Sanction_Proc_No         ='"+sanc+"' or m.Sanction_Proc_No   is null ) "+
			 " AND m.STATUS='L' and "+
			 " (M.Bill_Approved          <>'Y' "+
			 " OR M.Bill_Approved            IS NULL) "+
			 " ) ";*/
		
		sql="SELECT SUM(X.TOTAL_SANCTIONED_AMOUNT) TOTAL_SANCTIONED_AMOUNT, " +
				"  SUM(X.DEDUCTED_AMOUNT) DEDUCTED_AMOUNT , " +
				"  SUM(X.TOTAL_BILL_AMOUNT) TOTAL_BILL_AMOUNT , " +
				"  x.Pass_Order_Date, " +
				"  X.PASS_ORDER_BY, " +
				"  x.Pass_Order_Amount " +
				"FROM " +
				"  (SELECT DISTINCT m.BILL_NO, " +
				"    M.Sanction_Proc_No, " +
				"    M.Payee_Code, " +
				"    TO_CHAR(m.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, " +
				"    M.Bill_Processing_Done_By, " +
				"    m.REMARKS, " +
				"    M.Status, " +
				"    M.Bill_Minor_Type_Code, " +
				"    M.Bill_Major_Type, " +
				"    M.Bill_Sub_Type_Code, " +
				"    M.Total_Sanctioned_Amount, " +
				"    m.TOTAL_BILL_AMOUNT, " +
				"    M.Mtc70entry, " +
				"    TO_CHAR(M.Drawing_Officer_Approve_Date,'dd/MM/yyyy') AS Drawing_Officer_Approve_Date, " +
				"    M.Bill_Approved, " +
				"    TO_CHAR(M.Mtc_70_Register_Date,'dd/MM/yyyy') AS Mtc_70_Register_Date, " +
				"    M.Deducted_Amount, " +
				"    TO_CHAR(M.Pass_Order_Date,'dd/MM/yyyy') AS Pass_Order_Date, " +
				"    M.Pass_Order_By, " +
				"    M.Pass_Order_Amount, " +
				"    M.Drawing_Officer_Code, " +
				"    m.Reason_For_Reject " +
				"  FROM Fas_Bill_Register_Master M, " +
				"    Fas_Bill_Register_Transaction T " +
				"  WHERE M.Accounting_Unit_Id     =T.Accounting_Unit_Id " +
				"  AND M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id " +
				"  AND M.Cashbook_Year            =T.Cashbook_Year " +
				"  AND M.Cashbook_Month           =T.Cashbook_Month " +
				"  AND M.Bill_No                  =T.Bill_No " +
				"  AND bill_type                 <> 'WOSP' " +
				"  AND m.STATUS                   ='L' " +
				"  AND (M.Bill_Approved          <>'Y' " +
				"  OR M.Bill_Approved            IS NULL) " +
				"  AND M.ACCOUNTING_UNIT_ID       = " +cmbAcc_UnitCode+
				/*"  AND M.CASHBOOK_MONTH           = " +pass_month+
				"  AND M.CASHBOOK_YEAR            =  " +pass_year+*/
				" AND EXTRACT(MONTH FROM M.PASS_ORDER_DATE)= "+pass_month+
  " AND EXTRACT(year FROM m.Pass_Order_Date)= "+ pass_year+
				"  UNION ALL " +
				"  SELECT DISTINCT m.BILL_NO, " +
				"    M.Sanction_Proc_No, " +
				"    M.Payee_Code, " +
				"    TO_CHAR(m.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, " +
				"    M.Bill_Processing_Done_By, " +
				"    m.REMARKS, " +
				"    M.Status, " +
				"    M.Bill_Minor_Type_Code, " +
				"    M.Bill_Major_Type, " +
				"    M.Bill_Sub_Type_Code, " +
				"    M.Total_Sanctioned_Amount, " +
				"    m.TOTAL_BILL_AMOUNT, " +
				"    M.Mtc70entry, " +
				"    TO_CHAR(M.Drawing_Officer_Approve_Date,'dd/MM/yyyy') AS Drawing_Officer_Approve_Date, " +
				"    M.Bill_Approved, " +
				"    TO_CHAR(M.Mtc_70_Register_Date,'dd/MM/yyyy') AS Mtc_70_Register_Date, " +
				"    M.Deducted_Amount, " +
				"    TO_CHAR(M.Pass_Order_Date,'dd/MM/yyyy') AS Pass_Order_Date, " +
				"    M.Pass_Order_By, " +
				"    M.Pass_Order_Amount, " +
				"    M.Drawing_Officer_Code, " +
				"    m.Reason_For_Reject " +
				"  FROM Fas_Bill_Register_Master M, " +
				"    Fas_Bill_Register_Transaction T " +
				"  WHERE M.Accounting_Unit_Id     =T.Accounting_Unit_Id " +
				"  AND M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id " +
				"  AND M.Cashbook_Year            =T.Cashbook_Year " +
				"  AND M.Cashbook_Month           =T.Cashbook_Month " +
				"  AND bill_type                  ='WOSP' " +
				"  AND M.Bill_No                  =T.Bill_No " +
				"  AND m.STATUS                   ='L' " +
				"  AND (M.Bill_Approved          <>'Y' " +
				"  OR M.Bill_Approved            IS NULL) " +
				"  AND M.ACCOUNTING_UNIT_ID       = " +cmbAcc_UnitCode+
		/*		"  AND M.CASHBOOK_MONTH           = " +pass_month+
				"  AND M.CASHBOOK_YEAR            =  " +pass_year+*/
					" AND EXTRACT(MONTH FROM M.PASS_ORDER_DATE)= "+pass_month+
  " AND EXTRACT(year FROM m.Pass_Order_Date)= "+ pass_year+
  " UNION ALL "+
 " SELECT DISTINCT m.BILL_NO, " +
	"    M.Sanction_Proc_No, " +
	"    M.Payee_Code, " +
	"    TO_CHAR(m.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, " +
	"    M.Bill_Processing_Done_By, " +
	"    m.REMARKS, " +
	"    M.Status, " +
	"    M.Bill_Minor_Type_Code, " +
	"    M.Bill_Major_Type, " +
	"    M.Bill_Sub_Type_Code, " +
	"    M.Total_Sanctioned_Amount, " +
	"    m.TOTAL_BILL_AMOUNT, " +
	"    M.Mtc70entry, " +
	"    TO_CHAR(M.Drawing_Officer_Approve_Date,'dd/MM/yyyy') AS Drawing_Officer_Approve_Date, " +
	"    M.Bill_Approved, " +
	"    TO_CHAR(M.Mtc_70_Register_Date,'dd/MM/yyyy') AS Mtc_70_Register_Date, " +
	"    M.Deducted_Amount, " +
	"    TO_CHAR(M.Pass_Order_Date,'dd/MM/yyyy') AS Pass_Order_Date, " +
	"    M.Pass_Order_By, " +
	"    M.Pass_Order_Amount, " +
	"    M.Drawing_Officer_Code, " +
	"    m.Reason_For_Reject " +
	"  FROM Fas_Bill_Register_MasterNEW M, " +
	"    Fas_Bill_Register_TransactionW T " +
	"  WHERE M.Accounting_Unit_Id     =T.Accounting_Unit_Id " +
	"  AND M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id " +
	"  AND M.Cashbook_Year            =T.Cashbook_Year " +
	"  AND M.Cashbook_Month           =T.Cashbook_Month " +
	"  AND M.Bill_No                  =T.Bill_No " +
	"  AND bill_type                 <> 'WOSP' " +
	"  AND m.STATUS                   ='L' " +
	"  AND (M.Bill_Approved          <>'Y' " +
	"  OR M.Bill_Approved            IS NULL) " +
	"  AND M.ACCOUNTING_UNIT_ID       = " +cmbAcc_UnitCode+
	/*"  AND M.CASHBOOK_MONTH           = " +pass_month+
	"  AND M.CASHBOOK_YEAR            =  " +pass_year+*/
	" AND EXTRACT(MONTH FROM M.PASS_ORDER_DATE)= "+pass_month+
" AND EXTRACT(year FROM m.Pass_Order_Date)= "+ pass_year+
	"  UNION ALL " +
	"  SELECT DISTINCT m.BILL_NO, " +
	"    M.Sanction_Proc_No, " +
	"    M.Payee_Code, " +
	"    TO_CHAR(m.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, " +
	"    M.Bill_Processing_Done_By, " +
	"    m.REMARKS, " +
	"    M.Status, " +
	"    M.Bill_Minor_Type_Code, " +
	"    M.Bill_Major_Type, " +
	"    M.Bill_Sub_Type_Code, " +
	"    M.Total_Sanctioned_Amount, " +
	"    m.TOTAL_BILL_AMOUNT, " +
	"    M.Mtc70entry, " +
	"    TO_CHAR(M.Drawing_Officer_Approve_Date,'dd/MM/yyyy') AS Drawing_Officer_Approve_Date, " +
	"    M.Bill_Approved, " +
	"    TO_CHAR(M.Mtc_70_Register_Date,'dd/MM/yyyy') AS Mtc_70_Register_Date, " +
	"    M.Deducted_Amount, " +
	"    TO_CHAR(M.Pass_Order_Date,'dd/MM/yyyy') AS Pass_Order_Date, " +
	"    M.Pass_Order_By, " +
	"    M.Pass_Order_Amount, " +
	"    M.Drawing_Officer_Code, " +
	"    m.Reason_For_Reject " +
	"  FROM Fas_Bill_Register_MasterNEW M, " +
	"    Fas_Bill_Register_TransactionW T " +
	"  WHERE M.Accounting_Unit_Id     =T.Accounting_Unit_Id " +
	"  AND M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id " +
	"  AND M.Cashbook_Year            =T.Cashbook_Year " +
	"  AND M.Cashbook_Month           =T.Cashbook_Month " +
	"  AND bill_type                  ='WOSP' " +
	"  AND M.Bill_No                  =T.Bill_No " +
	"  AND m.STATUS                   ='L' " +
	"  AND (M.Bill_Approved          <>'Y' " +
	"  OR M.Bill_Approved            IS NULL) " +
	"  AND M.ACCOUNTING_UNIT_ID       = " +cmbAcc_UnitCode+
/*		"  AND M.CASHBOOK_MONTH           = " +pass_month+
	"  AND M.CASHBOOK_YEAR            =  " +pass_year+*/
		" AND EXTRACT(MONTH FROM M.PASS_ORDER_DATE)= "+pass_month+
" AND EXTRACT(year FROM m.Pass_Order_Date)= "+ pass_year+
				"  UNION ALL " +
				"    (SELECT DISTINCT m.BILL_NO, " +
				"      M.Sanction_Proc_No, " +
				"      M.Payee_Code, " +
				"      TO_CHAR(m.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, " +
				"      M.Bill_Processing_Done_By, " +
				"      m.REMARKS, " +
				"      M.Status, " +
				"      M.Bill_Minor_Type_Code, " +
				"      M.Bill_Major_Type, " +
				"      M.Bill_Sub_Type_Code, " +
				"      M.Total_Sanctioned_Amount, " +
				"      m.TOTAL_BILL_AMOUNT, " +
				"      M.Mtc70entry, " +
				"      TO_CHAR(M.Drawing_Officer_Approve_Date,'dd/MM/yyyy') AS Drawing_Officer_Approve_Date, " +
				"      M.Bill_Approved, " +
				"      TO_CHAR(M.Mtc_70_Register_Date,'dd/MM/yyyy') AS Mtc_70_Register_Date, " +
				"      M.Deducted_Amount, " +
				"      TO_CHAR(M.Pass_Order_Date,'dd/MM/yyyy') AS Pass_Order_Date, " +
				"      M.Pass_Order_By, " +
				"      M.Pass_Order_Amount, " +
				"      M.Drawing_Officer_Code, " +
				"      m.Reason_For_Reject " +
				"    FROM FAS_BILL_REGISTERNEW M " +
				"  where  M.ACCOUNTING_UNIT_ID       = " +cmbAcc_UnitCode+
		/*		"  AND M.CASHBOOK_MONTH           = " +pass_month+
				"  AND M.CASHBOOK_YEAR            =  " +pass_year+*/
					" AND EXTRACT(MONTH FROM M.PASS_ORDER_DATE)= "+pass_month+
  " AND EXTRACT(year FROM m.Pass_Order_Date)= "+ pass_year+
				"    AND m.STATUS               ='L' " +
				"    AND (M.Bill_Approved      <>'Y' " +
				"    OR M.BILL_APPROVED        IS NULL) " +
				"    ) " +
				"  )X " +
				"INNER JOIN " +
				"  (SELECT t.BILL_NO " +
				"  FROM FAS_PASS_ORDER_MST M, " +
				"    FAS_PASS_ORDER_TRN T " +
				"  WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
				"  AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID " +
				"  AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
				"  AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
				"  AND M.PASS_ORDER_NO           =T.PASS_ORDER_NO " +
				"  AND M.ACCOUNTING_UNIT_ID      = " +cmbAcc_UnitCode+
				//"  AND M.ACCOUNTING_FOR_OFFICE_ID=5983 " +
				"  AND M.STATUS                  ='L' " +
				"  AND M.PASS_ORDER_NO           =" +passno+
				"  AND M.CASHBOOK_MONTH           = " +pass_month+
				"  AND M.CASHBOOK_YEAR            =  " +pass_year+
				"  )Y " +
				"ON x.BILL_NO=y.BILL_NO " +
				"GROUP BY x.Pass_Order_Date, " +
				"  X.PASS_ORDER_BY, " +
				"  x.Pass_Order_Amount" ;
		
		System.out.println("sql::::"+sql);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		//preparedStatement.setInt(1, billMajorType);
		//preparedStatement.setInt(1, billNo);
		//preparedStatement.setInt(2,cmbAcc_UnitCode);
		resultSet = preparedStatement.executeQuery();
		int net = 0;
		while(resultSet.next()){
			System.out.println("net loop value********"+resultSet.getInt("TOTAL_SANCTIONED_AMOUNT"));
			net=resultSet.getInt("TOTAL_SANCTIONED_AMOUNT")-resultSet.getInt("DEDUCTED_AMOUNT");
			xml +="<status>success</status>" +
			        "<bill_major_type>0</bill_major_type>" +
			        "<BILL_NO>0</BILL_NO>" +
					"<BILL_MINOR_TYPE_CODE>0</BILL_MINOR_TYPE_CODE>" +
					"<BILL_SUB_TYPE_CODE>0</BILL_SUB_TYPE_CODE>" +
					"<BILL_DATE>null</BILL_DATE>" +
					"<MTC_REGISTER_REQUIRED>null</MTC_REGISTER_REQUIRED>" +
					"<REF_DATE>null</REF_DATE>" +
					"<TOTAL_SANCTIONED_AMOUNT>"+resultSet.getInt("TOTAL_SANCTIONED_AMOUNT")+"</TOTAL_SANCTIONED_AMOUNT>" +
					"<DEDUCTED_AMOUNT>"+resultSet.getInt("DEDUCTED_AMOUNT")+"</DEDUCTED_AMOUNT>" +					
					"<NET_AMOUNT>"+net+"</NET_AMOUNT>"+
					"<REMARKS>null</REMARKS>" +
					"<PASSDATE>"+resultSet.getString("PASS_ORDER_DATE")+"</PASSDATE>" +
					"<PASSNO>"+resultSet.getInt("PASS_ORDER_BY")+"</PASSNO>" +
				//	"<passNo1>"+resultSet.getInt("passNo")+"</passNo1>" +
					"<PASS_ORDER_AMOUNT>"+resultSet.getInt("PASS_ORDER_AMOUNT")+"</PASS_ORDER_AMOUNT>" +
					"<DRAWING_OFFICER>0</DRAWING_OFFICER>" +
					"<BILL_APPROVED>null</BILL_APPROVED>" +
					"<APPROVE_DATE>null</APPROVE_DATE>" +
					"<REJECT_REASON>null</REJECT_REASON>" +					
					"<STATUS>null</STATUS>" ;	
		}/*else{
			xml +="<status>fail</status>";
		}*/
		xml +="</response>";
		System.out.println("xml in hjav "+xml);
		return xml;
	}
	
	public String updatePassApproval(int officeid,  int drawOffice, String approve, Date approveDate, 
			String rejectReason, String userid, int cmbAcc_UnitCode, int sanctionno,int yr,int mn,int passno,String PassOrderDate) throws SQLException{
		xml = "";
		Calendar c3;
		  String[] sd=PassOrderDate.split("/");
          c3=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
          java.util.Date d=c3.getTime();
         Date PassOrderDate1=new Date(d.getTime());
		count = 0;//int billNo, String micEntry,
		connection = load.getConnection();
		int cc=0,cc1=0,cc2=0;
		int kg=0;
		/*String ss="select m.bill_no,M.BILL_MAJOR_TYPE FROM FAS_BILL_REGISTER_MASTER M,FAS_BILL_REGISTER_TRANSACTION T  "+
			 	        	 " WHERE M.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID  "+
			 	        	"  AND M.ACCOUNTING_UNIT_OFFICE_ID=T.ACCOUNTING_UNIT_OFFICE_ID  "+
			 	        	"  AND M.CASHBOOK_YEAR=T.CASHBOOK_YEAR  "+
			 	        	"  AND M.CASHBOOK_MONTH=T.CASHBOOK_MONTH  "+
			 	        	"  AND M.BILL_NO=T.BILL_NO  "+
			 	        	"  AND M.ACCOUNTING_UNIT_ID    =  "+cmbAcc_UnitCode+
			 	        	"  and m.accounting_unit_office_id= "+officeid+
			 	        	"  and M.STATUS='L' and m.Sanction_Proc_No like '"+sanctionno+"%'";
	*/
	
		
		/*String ss="SELECT t.BILL_NO, " +
				"  B.BILL_MAJOR_TYPE " +
				" FROM FAS_PASS_ORDER_MST M, " +
				"  FAS_PASS_ORDER_TRN T, " +
				"  FAS_BILL_REGISTER_MASTER b " +
				" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
				" AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID " +
				"  AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
				" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
				" AND M.ACCOUNTING_UNIT_ID      =b.ACCOUNTING_UNIT_ID " +
				" AND M.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID " +
			//	" AND M.CASHBOOK_YEAR           =b.CASHBOOK_YEAR " +
			//	" AND M.CASHBOOK_MONTH          =B.CASHBOOK_MONTH " +
			"   and T.BILL_DATE=B.BILL_DATE "+
				" AND M.PASS_ORDER_NO           =T.PASS_ORDER_NO " +
				" AND T.BILL_NO                 =B.BILL_NO " +
				" AND M.ACCOUNTING_UNIT_ID      = " +cmbAcc_UnitCode+
				" AND m.ACCOUNTING_FOR_OFFICE_ID= " +officeid+
				" AND M.PASS_ORDER_DATE         = ? " +
				" AND M.STATUS                  ='L' " +
				" AND B.STATUS                  ='L' " +
				" AND M.PASS_ORDER_NO           ="+passno;*/
		
		
		String ss="SELECT t.BILL_NO,EXTRACT(MONTH FROM T.BILL_DATE) MN,extract(year from t.bill_date) yr, " +
		"  B.BILL_MAJOR_TYPE " +
		" FROM FAS_PASS_ORDER_MST M, " +
		"  FAS_PASS_ORDER_TRN T, " +
		"  FAS_BILL_REGISTER_MASTER b " +
		" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
		" AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID " +
		" AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
		" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
		" AND M.ACCOUNTING_UNIT_ID      =b.ACCOUNTING_UNIT_ID " +
		" AND M.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID " +
		" AND T.BILL_DATE               =B.BILL_DATE " +
		" AND M.PASS_ORDER_NO           =T.PASS_ORDER_NO " +
		" AND T.BILL_NO                 =B.BILL_NO " +
		" AND M.ACCOUNTING_UNIT_ID      =  " +cmbAcc_UnitCode+
		" AND M.ACCOUNTING_FOR_OFFICE_ID=  " +officeid+
		" AND M.PASS_ORDER_DATE         = ? " +
		"  AND M.STATUS                  ='L' " +
		" AND B.STATUS                  ='L' " +
		" AND M.PASS_ORDER_NO           = " +passno+
		" UNION ALL " +
		" SELECT t.BILL_NO,EXTRACT(MONTH FROM T.BILL_DATE) MN,extract(year from t.bill_date) yr, " +
		"  B.BILL_MAJOR_TYPE " +
		" FROM FAS_PASS_ORDER_MST M, " +
		"  FAS_PASS_ORDER_TRN T, " +
		"   FAS_BILL_REGISTER_MASTERnew b " +
		" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
		" AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID " +
		" AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
		" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
		" AND M.ACCOUNTING_UNIT_ID      =b.ACCOUNTING_UNIT_ID " +
		" AND M.ACCOUNTING_FOR_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID " +
		" AND T.BILL_DATE               =B.BILL_DATE " +
		" AND M.PASS_ORDER_NO           =T.PASS_ORDER_NO " +
		" AND T.BILL_NO                 =B.BILL_NO " +
		" AND M.ACCOUNTING_UNIT_ID      =  " +cmbAcc_UnitCode+
		" AND M.ACCOUNTING_FOR_OFFICE_ID=  " +officeid+
		" AND M.PASS_ORDER_DATE         = ? " +
		" AND M.STATUS                  ='L' " +
		" AND B.STATUS                  ='L' " +
		" AND M.PASS_ORDER_NO           ="+passno;

		
		PreparedStatement p1 = connection.prepareStatement(ss);
		p1.setDate(1, PassOrderDate1);
		p1.setDate(2, PassOrderDate1);
		System.out.println(":::::::  "+ss);
		ResultSet rs =p1.executeQuery();
		if(rs.next()){
			int kk=0;
			///loop
			
			
			String ss1="select m.bill_no,M.BILL_MAJOR_TYPE FROM FAS_BILL_REGISTER_MASTER M,FAS_BILL_REGISTER_TRANSACTION T  "+
	 	        	 " WHERE M.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID  "+
	 	        	"  AND M.ACCOUNTING_UNIT_OFFICE_ID=T.ACCOUNTING_UNIT_OFFICE_ID  "+
	 	        	"  AND M.CASHBOOK_YEAR=T.CASHBOOK_YEAR  "+
	 	        	"  AND M.CASHBOOK_MONTH=T.CASHBOOK_MONTH  "+
	 	        	"  AND M.BILL_NO=T.BILL_NO  "+
	 	        	"  AND M.BILL_DAte=T.BILL_DATE  "+
	 	        	"  AND M.ACCOUNTING_UNIT_ID    =  "+cmbAcc_UnitCode+
	 	        	"  and m.accounting_unit_office_id= "+officeid+
	 	        	"  and M.STATUS='L' and m.Sanction_Proc_No like '"+sanctionno+"%'";
PreparedStatement p11 = connection.prepareStatement(ss);
p11.setDate(1, PassOrderDate1);
p11.setDate(2, PassOrderDate1);
ResultSet rs1 =p11.executeQuery();
			
			while(rs1.next())
			{
		int billnoo=rs1.getInt("bill_no");
		String sub_q="";
		System.out.println("billnoo >> "+billnoo);
		int billmajocode=rs.getInt("BILL_MAJOR_TYPE");
		if((rs.getInt("mn")>3 && rs.getInt("yr")==2015 ) || (rs.getInt("yr")>2015))
			//if(rs.getInt("mn")>3 && rs.getInt("yr")>2014 )
		{
			sub_q = " FAS_BILL_REGISTER_MASTERnew ";	
		}else{
			sub_q = " FAS_BILL_REGISTER_MASTER ";		
		}
		xml = "<response><command>update</command>";				
		sql ="update "+	sub_q+" set DRAWING_OFFICER_CODE=?,BILL_APPROVED=?,DRAWING_OFFICER_APPROVE_DATE=?," +
				"REASON_FOR_REJECT=?,UPDATED_BY_USERID=?,UPDATED_DATE=now() where BILL_MAJOR_TYPE=? and BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and PASS_ORDER_DATE=? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		//preparedStatement = connection.prepareStatement(sql);
	//	preparedStatement.setString(1, micEntry);
		preparedStatement.setInt(1, drawOffice);
		preparedStatement.setString(2, approve);
	//	System.out.println("approveDate:::"+approveDate);
		preparedStatement.setDate(3, approveDate);
		preparedStatement.setString(4, rejectReason);
		preparedStatement.setString(5, userid);
		preparedStatement.setInt(6, billmajocode);
		preparedStatement.setInt(7, billnoo);
		preparedStatement.setInt(8, cmbAcc_UnitCode);
		preparedStatement.setInt(9, officeid);
		preparedStatement.setDate(10, PassOrderDate1);
		cc = preparedStatement.executeUpdate();
		System.out.println("cc:"+cc);
		
		String hm ="update FAS_PASS_ORDER_MST set APPROVED_BY='"+approve+"',APPROVED_DATE=? where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
				"CASHBOOK_YEAR="+yr+" and CASHBOOK_MONTH="+mn+" and PASS_ORDER_NO="+passno+" and STATUS='L'";
		
		PreparedStatement pres = connection.prepareStatement(hm);
		pres.setDate(1, approveDate);
		kg=pres.executeUpdate();
	
		}	if(kg>0)
		{
		cc2=cc2+cc;
		}
		else
		{
			cc2=0;	
		}
		cc1++;
		}
		//Without
		else{
			
			//System.out.println("else part without san");
			String ss1=" select m.bill_no,M.BILL_MAJOR_TYPE FROM "+
			" FAS_BILL_REGISTERNEW M "+
        	" WHERE M.ACCOUNTING_UNIT_ID    = "+cmbAcc_UnitCode+
        	   " and m.accounting_unit_office_id="+officeid+
        	  " and M.STATUS='L' and ( m.Sanction_Proc_No='"+sanctionno+ "' or m.Sanction_Proc_No    is null)";
        	//  " and CASHBOOK_YEAR="+yr+
        	//  " and CASHBOOK_MONTH="+mn;
        	//System.out.println("ss1 without  "+ss1);
				PreparedStatement p11 = connection.prepareStatement(ss1);
				ResultSet rs1 =p11.executeQuery();
				if(rs1.next()){
				
				int billnoo=rs1.getInt("bill_no");
				System.out.println(" billnoo "+billnoo);
				int billmajocode=rs1.getInt("BILL_MAJOR_TYPE");
				xml = "<response><command>update</command>";				
				sql ="update FAS_BILL_REGISTERNEW set DRAWING_OFFICER_CODE=?,BILL_APPROVED=?,DRAWING_OFFICER_APPROVE_DATE=?," +
				"REASON_FOR_REJECT=?,UPDATED_BY_USERID=?,UPDATED_DATE=now() where BILL_MAJOR_TYPE=? and BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?  and PASS_ORDER_DATE=? ";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				//preparedStatement = connection.prepareStatement(sql);
				//	preparedStatement.setString(1, micEntry);
				preparedStatement.setInt(1, drawOffice);
				preparedStatement.setString(2, approve);
				//	System.out.println("approveDate:::"+approveDate);
				preparedStatement.setDate(3, approveDate);
				preparedStatement.setString(4, rejectReason);
				preparedStatement.setString(5, userid);
				preparedStatement.setInt(6, billmajocode);
				preparedStatement.setInt(7, billnoo);
				preparedStatement.setInt(8, cmbAcc_UnitCode);
				preparedStatement.setInt(9, officeid);
				preparedStatement.setDate(10, PassOrderDate1);
				cc = preparedStatement.executeUpdate();
				System.out.println("cc:"+cc+" passno "+passno);
				
				String hm ="update FAS_PASS_ORDER_MST set APPROVED_BY='"+approve+"',APPROVED_DATE=? where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
				"CASHBOOK_YEAR="+yr+" and CASHBOOK_MONTH="+mn+" and PASS_ORDER_NO="+passno;
				
				PreparedStatement pres = connection.prepareStatement(hm);
				pres.setDate(1, approveDate);
				kg=pres.executeUpdate();
				if(kg>0)
				{
				cc2=cc2+cc;
				}
				else
				{
				cc2=0;	
				}
				cc1++;
				}
		}
		
		
		System.out.println("cc=="+cc+" cc1=="+cc1+" cc2 "+cc2);
		if((cc2==cc1)&&(cc2>0)&&(cc1>0)){
			//System.out.println("if "+cc2+""+cc1);
			xml +="<status>success</status>";
		}else{
			//System.out.println("else  "+cc2+""+cc1);
			xml +="<status>fail</status>";
		}
		xml +="</response>";
		System.out.println("xml--> "+xml);
		return xml;
		
	}

	public String deletePassApproval(int officeid,  int drawOffice, String approve, Date approveDate, String rejectReason, String userid, int unitcode,int sanctionno) throws SQLException {
		// TODO Auto-generated method stub
		
		int cc=0,cc1=0,cc2=0;
		String ss="select m.bill_no,M.BILL_MAJOR_TYPE FROM FAS_BILL_REGISTER_MASTER M,FAS_BILL_REGISTER_TRANSACTION T  "+
    	 " WHERE M.ACCOUNTING_UNIT_ID=T.ACCOUNTING_UNIT_ID  "+
    	"  AND M.ACCOUNTING_UNIT_OFFICE_ID=T.ACCOUNTING_UNIT_OFFICE_ID  "+
    	"  AND M.CASHBOOK_YEAR=T.CASHBOOK_YEAR  "+
    	"  AND M.CASHBOOK_MONTH=T.CASHBOOK_MONTH  "+
    	"  AND M.BILL_NO=T.BILL_NO  "+
    	"  AND M.ACCOUNTING_UNIT_ID    =  "+unitcode+
    	"  and m.accounting_unit_office_id= "+officeid+
    	"  and M.STATUS='L' and m.Sanction_Proc_No='"+sanctionno+"'";
		PreparedStatement p1 = connection.prepareStatement(ss);
		ResultSet rs =p1.executeQuery();
		if(rs.next()){
		int billnoo=rs.getInt("bill_no");
		int billmajocode=rs.getInt("BILL_MAJOR_TYPE");
	
		System.out.println("delete unitcode:::"+unitcode);
		
		
		
		sql ="update FAS_BILL_REGISTER_MASTER set DRAWING_OFFICER_CODE=?,BILL_APPROVED=?,DRAWING_OFFICER_APPROVE_DATE=?," +
		"REASON_FOR_REJECT=?,UPDATED_BY_USERID=?,UPDATED_DATE=now() where BILL_MAJOR_TYPE=? and BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			//preparedStatement = connection.prepareStatement(sql);
			//	preparedStatement.setString(1, micEntry);
			ps.setInt(1, 0);
			ps.setString(2, "");
			ps.setDate(3, null);
			ps.setString(4, "");
			ps.setString(5, userid);
			ps.setInt(6, billmajocode);
			ps.setInt(7, billnoo);
			ps.setInt(8, unitcode);
			ps.setInt(9, officeid);
			cc = ps.executeUpdate();

		
				/*
				sql = "delete from FAS_BILL_REGISTER_MASTER where BILL_MAJOR_TYPE=? and BILL_NO=? and ACCOUNTING_UNIT_ID=?";
				count = 0;
				connection = load.getConnection();
				xml = "<response><command>delete</command>";				
				//sql="";
				PreparedStatement ps = connection.prepareStatement(sql);
				//preparedStatement = connection.prepareStatement(sql);		
				ps.setInt(1, billmajocode);
				ps.setInt(2, billnoo);
				ps.setInt(3, unitcode);
				cc = ps.executeUpdate();*/
				/*count = ps.executeUpdate();
				if(count==0){
					xml +="<status>fail</status>";
				}else{
					xml +="<status>success</status>";
				}*/
		cc2=cc2+cc;
		cc1++;
	}
		//without
		else{
			String ss1=" select m.bill_no,M.BILL_MAJOR_TYPE FROM "+
			" FAS_BILL_REGISTERNEW M "+
        	" WHERE M.ACCOUNTING_UNIT_ID    = "+unitcode+
        	   " and m.accounting_unit_office_id="+officeid+
        	  " and M.STATUS='L' and ( m.Sanction_Proc_No='"+sanctionno+ "' or m.Sanction_Proc_No    is null)";
        	//  " and CASHBOOK_YEAR="+yr+
        	//  " and CASHBOOK_MONTH="+mn;
        	//System.out.println("ss1 without  "+ss1);
			
			PreparedStatement p11 = connection.prepareStatement(ss1);
			ResultSet rs1 =p11.executeQuery();
			if(rs1.next()){
			int billnoo=rs1.getInt("bill_no");
			int billmajocode=rs1.getInt("BILL_MAJOR_TYPE");
		
			System.out.println("delete unitcode:::"+unitcode);
			
			
			
			sql ="update FAS_BILL_REGISTERNEW set DRAWING_OFFICER_CODE=?,BILL_APPROVED=?,DRAWING_OFFICER_APPROVE_DATE=?," +
			"REASON_FOR_REJECT=?,UPDATED_BY_USERID=?,UPDATED_DATE=now() where BILL_MAJOR_TYPE=? and BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? ";
				PreparedStatement ps = connection.prepareStatement(sql);
				//preparedStatement = connection.prepareStatement(sql);
				//	preparedStatement.setString(1, micEntry);
				ps.setInt(1, 0);
				ps.setString(2, "");
				ps.setDate(3, null);
				ps.setString(4, "");
				ps.setString(5, userid);
				ps.setInt(6, billmajocode);
				ps.setInt(7, billnoo);
				ps.setInt(8, unitcode);
				ps.setInt(9, officeid);
				cc = ps.executeUpdate();

			
					/*
					sql = "delete from FAS_BILL_REGISTER_MASTER where BILL_MAJOR_TYPE=? and BILL_NO=? and ACCOUNTING_UNIT_ID=?";
					count = 0;
					connection = load.getConnection();
					xml = "<response><command>delete</command>";				
					//sql="";
					PreparedStatement ps = connection.prepareStatement(sql);
					//preparedStatement = connection.prepareStatement(sql);		
					ps.setInt(1, billmajocode);
					ps.setInt(2, billnoo);
					ps.setInt(3, unitcode);
					cc = ps.executeUpdate();*/
					/*count = ps.executeUpdate();
					if(count==0){
						xml +="<status>fail</status>";
					}else{
						xml +="<status>success</status>";
					}*/
			cc2=cc2+cc;
			cc1++;
		}
			
		}
		System.out.println("cc2=="+cc2+" cc1=="+cc1+" cc="+cc);
		if((cc2==cc1)&&(cc2>0)&&(cc1>0)){
			xml +="<status>success</status>";
		}else{
			xml +="<status>fail</status>";
		}
		xml +="</response>";
		return xml;
	}

}
