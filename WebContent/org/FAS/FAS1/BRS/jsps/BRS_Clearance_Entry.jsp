<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS_Clearance_Entry</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script type="text/javascript"
	src="../../../../Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript" src="../scripts/BRS_Clearance_Entry.js"></script>

</head>
<%
	String s = request.getContextPath();

    String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
    String cmbOffice_code=request.getParameter("cmbOffice_code");
    String txtCB_Year=request.getParameter("txtCB_Year");
    String txtCB_Month=request.getParameter("txtCB_Month");
    String cmbBankAccNo=request.getParameter("cmbBankAccNo");
    String Amt_in_pasbook=request.getParameter("Amt_in_pasbook");
    int docno=Integer.parseInt(request.getParameter("docno"));
    String docdate=request.getParameter("docdate");
    String doctype=request.getParameter("doctype");
    String indicator=request.getParameter("indicator");
    String Trans_Type_NT0=request.getParameter("Trans_Type_NT0"); 
    System.out.println("Trans_Type_NT0===>"+Trans_Type_NT0);
    String Trans_Type_NT_New0=request.getParameter("Trans_Type_NT_New0"); 
	System.out.println("Trans_Type_NT_NEW0===>"+Trans_Type_NT_New0);
	
	String Particualrs_NT=request.getParameter("Particualrs_NT"); 
	System.out.println("Particualrs_NT===>"+Particualrs_NT);
	
%>
<body onLoad="initialLoad('<%=s %>','<%=cmbAcc_UnitCode %>','<%=cmbOffice_code %>','<%=txtCB_Year %>','<%=txtCB_Month %>','<%=cmbBankAccNo %>','<%=docno %>','<%=docdate %>','<%=doctype %>','<%=indicator %>','<%=Trans_Type_NT0 %>','<%=Trans_Type_NT_New0%>','<%=Particualrs_NT%>');">
<input type="hidden" id="Trans_Type_NT0" name="Trans_Type_NT0" value='<%=Trans_Type_NT0 %>'>
<input type="hidden" id="Trans_Type_NT_New0" name="Trans_Type_NT_New0" value='<%=Trans_Type_NT_New0 %>'>
<input type="hidden" id="Particualrs_NT" name="Particualrs_NT" value='<%=Particualrs_NT %>'>
<table cellspacing="1" cellpadding="3" width="100%">

	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bank Reconciliation	System - BRS Clearance Entry_T</font></div>
		</td>
	</tr>
</table>


<form name="frmBRS_Clearance_Entry" id="frmBRS_Clearance_Entry" method="POST" action="BRS_Clearance_Entry">
<div id="imgfld" style="position:absolute; top: 212px; visibility:hidden; left: 390px; width: 212px; height: 6px;" left=100 top=100>
  <input type="image" name="img1" id="img1" src="../../../../../images/Loading.gif" height="200"></div>
<!-- ------------------------- TWAD  Transactions Part II ---------------------------------->
<div class="tab-pane" id="tab-pane-1">
<div class="tab-page" id="gd">
<h2 class="tab">Cash Book Transactions</h2>

<div align="center">


<table id="mytable" cellspacing="0" cellpadding="0" border="0">
	<tbody id="grid_body_TotTrans" class="table" align="left">
	</tbody>
</table>




<table cellspacing="1" cellpadding="2" border="0" width="100%">
<tr class="tdTitle">
		<td colspan="2">
		  <div align="left"><strong>No of Records   :</strong>
		   <input name="txtNoofRecords" type="text" id="txtNoofRecords" size="5" readonly="true">
		   <input name="journal_date" type="text" id="journal_date" size="10" value="<%=docdate %>" readonly="true">
		   <input name="ref_doc_no" type="text" id="ref_doc_no" size="10" value="<%=docno %>" readonly="true">
		   <input name="ref_doc_type" type="text" id="ref_doc_type" size="10" value="<%=doctype %>" readonly="true">
		   <input name="ref_doc_amt" type="text" id="ref_doc_amt" size="10" value="<%=Amt_in_pasbook %>" readonly="true">
		
		  </div>
		  
		  </td>
	</tr>
	<tr>
		<td colspan="2">

		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="107%">
			<tr class="table">
				<th><font size=2> Remittance Date </font></th>
				<th><font size=2> Withdrawal Date </font></th>
				<th><font size=2> Challan / Voucher No </font></th>
				<th><font size=2>Cheque / DD No</font></th>
				<th><font size=2>CR Amount </font></th>
				<th><font size=2>DR Amount </font></th>
				<th><font size=2>Entry Date </font></th>
				<th><font size=2>Amount in Pass Book </font></th>
				<th><font size=2>Difference</font></th>
				<th><font size=2>Reason for Difference</font></th>
				<th><font size=2>Select</font></th>				
			</tr>
			<tbody id="grid_body_TWAD" class="table" align="Center">
			</tbody>

		</table>
		</div>

		</td>
	</tr>
</table>

</div>
</div>
</div>
<br>

<!-- ------------------------- Buttons Part --------------------------------  -->

<div align="center">
<table cellspacing="1" cellpadding="4" width="108%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="button" name="onsubmit1"
			value="Update" id="onsubmit1" onClick="SaveFunc('<%=s %>','<%=cmbAcc_UnitCode %>','<%=cmbOffice_code %>','<%=txtCB_Year %>','<%=txtCB_Month %>','<%=cmbBankAccNo %>','<%=Amt_in_pasbook %>','<%=Particualrs_NT%>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onclick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>