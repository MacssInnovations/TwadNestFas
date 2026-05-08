<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bills_Token_Register_with_SP_List</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script src="../scripts/Bills_Token_Register_with_SP_List.js"
	type="text/javascript"> </script>


</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<%
int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
%>
<body onLoad="initialLoad('<%=s%>',<%=unitcode %>);">
<form name="FrmBillTokenRegisterEntry_WithProceeding_List" method="post" action="BillTokenRegisterEntry_WithProceeding_List" id="FrmBillTokenRegisterEntry_WithProceeding_List">

<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="221%" align="center">
	<tr class="tdH">
		<td align="left" colspan="27"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH">
		<th width="4%">Select</th>
	
		<th width="8%">Type</th>
		
                <th width="6%">Bill No</th>
		<th width="8%">BillDate</th>
		<th width="8%">ProceedingNo</th>
		<th width="8%">ProceedingDate</th>
        <th width="10%">PayeeType</th>
		<th width="7%">PayeeCode</th>
		<th width="7%">SancAmount</th>
        <th width="7%">BillAmount</th>
        <th width="7%">Payable To</th>
        <th width="7%">BillProcessingDoneBy</th>
        <th width="7%">MTC70EntryRequired</th>
		<th width="8%">Acc_HeadCode</th>
        <th width="8%">BudgetProvision</th>
        <th width="8%">BudgetSofarSent</th>
		<th width="6%">RefNo</th>		
		<th width="8%">RefDate</th>
		<th width="7%">Remarks</th>
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</form>
</body>
</html>