<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Barred_Cheque_Master_list</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>

<script type="text/javascript" src="../scripts/Barred_Cheque_Master.js"></script>

</head>
<%
	String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
	String cmbOffice_code=request.getParameter("cmbOffice_code");
	String txtCB_Year=request.getParameter("txtCB_Year");
	String txtCB_Month=request.getParameter("txtCB_Month");
	
	String s=request.getContextPath();
	
	System.out.println("JSP:---"+cmbAcc_UnitCode);
	System.out.println("JSP:---"+cmbOffice_code);
	System.out.println("JSP:---"+txtCB_Year);
	System.out.println("JSP:---"+txtCB_Month);
%>
<body onLoad="initialLoad('<%=s %>','<%=cmbAcc_UnitCode %>','<%=cmbOffice_code %>','<%=txtCB_Year %>','<%=txtCB_Month %>');">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr >
		<td colspan="2" class="table">
		<div id="List Details">

<table id="Existing" cellspacing="1" cellpadding="2" border="1"
	width="100%">
				<tr class="tdTitle">
				<td colspan="16">
				<div align="left"><strong>Bank Reconciliation System - Barred Cheque Master List</strong></div>
				</td>
			</tr>
			
	<tr class="tdH">
	            <th>
				<div align="center">Accounting Unit Code</div>
				</th>

				<th>
				<div align="center">Accounting For Office Code</div>
				</th>
				
				<th>
				<div align="center">Cash Book Year</div>
				</th>
				
				<th>
				<div align="center">Cash Book Month</div>
				</th>
				
				<th>
				<div align="center">Bank A/C No</div>
				</th>

				<th>
				<div align="center">Doc Type</div>
				</th>
				
				<th>
				<div align="center">Cheque No</div>
				</th>
				
				<th>
				<div align="center">Doc No</div>
				</th>
				
				<th>
				<div align="center">Doc Date</div>
				</th>								
				
				<th>
				<div align="center">Cheque Date</div>
				</th>
				
				<th>
				<div align="center">Cheque Amount</div>
				</th>
				
				<th>
				<div align="center">Cheque Valid Upto</div>
				</th>
				
				<th>
				<div align="center">Followup Action</div>
				</th>
				
				<th>
				<div align="center">Cleared Entries</div>
				</th>
				
				<th>
				<div align="center">Cleared Date</div>
				</th>
				
				<th>
				<div align="center">Edit</div>
				</th>

	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>

		</div>

		</td>
	</tr>
</table>
</div>
</body>
</html>