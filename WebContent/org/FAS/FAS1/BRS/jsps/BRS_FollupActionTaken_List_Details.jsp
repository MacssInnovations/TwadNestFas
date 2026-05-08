<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS_FollupActionTaken_List_Details</title>

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link rel=StyleSheet href="../../../../../css/CalendarControl.css"
	type="text/css">

<script src="../scripts/BRS_FollupActionTaken_List_Details.js"
	type="text/javascript"> </script>
</head>
<%
    String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
    String cmbOffice_code=request.getParameter("cmbOffice_code");
    String txtCB_Year=request.getParameter("txtCB_Year");
    String txtCB_Month=request.getParameter("txtCB_Month");
    String cmbBankAccNo=request.getParameter("cmbBankAccNo");
    String TwadNonTwad=request.getParameter("TwadNonTwad");
    String slno=request.getParameter("slno");
    String ActionNo=request.getParameter("ActionNo");
    
    String s=request.getContextPath();       
%>
<body
	onload="initialLoad('<%=s %>','<%=cmbAcc_UnitCode %>','<%=cmbOffice_code %>','<%=txtCB_Year %>','<%=txtCB_Month %>','<%=cmbBankAccNo %>','<%=TwadNonTwad %>','<%=slno %>','<%=ActionNo %>');">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr>
		<td colspan="2" class="table">
		<div id="Details">

		<table id="Existing" cellspacing="1" cellpadding="2" border="1"
			width="100%">
			<tr class="tdH">
				<td colspan="11">
				<div align="left"><strong>Previous Action Taken</strong></div>
				</td>
			</tr>

			<tr class="tdTitle">
				<th width="7%"><font size=2>Reference Serial Number </font></th>
				<th width="10%"><font size=2>Cheque / DD No</font></th>
				<th width="10%"><font size=2>CR Amount </font></th>
				<th width="10%"><font size=2>DR Amount </font></th>
				<th width="10%"><font size=2>Entry Date </font></th>
				<th width="8%"><font size=2>TWAD / Non-TWAD </font></th>
				<th width="9%"><font size=2>Doc.No </font></th>
				<th width="8%"><font size=2>Doc.Type</font></th>
				<th width="7%"><font size=2>Action No. </font></th>
				<th width="8%"><font size=2>Action Date </font></th>
				<th width="13%"><font size=2>Action Taken </font></th>

			</tr>
			<tbody id="tblList" align="center">
			</tbody>

			<tr class="tdH">
				<td colspan="11">
				<div align="center"><input type="button" name="btnCan"
					id="btnCan" value="EXIT" onClick="exitfun('<%=s %>');" /></div>
				</td>
			</tr>
		</table>

		</div>

		</td>
	</tr>
</table>
</div>
</body>
</html>