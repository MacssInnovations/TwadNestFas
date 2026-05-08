<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>List_of_InterBankTransfer_MultipleBanks_Details</title>

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link rel=StyleSheet href="../../../../../css/CalendarControl.css"
	type="text/css">

<script src="../scripts/List_of_InterBankTransfer_MultipleBanks.js" type="text/javascript"> </script>


</head>
<%
    String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
    String cmbOffice_code=request.getParameter("cmbOffice_code");
    String txtCB_Year=request.getParameter("txtCB_Year");
    String txtCB_Month=request.getParameter("txtCB_Month");
    String VOUCHER_NO=request.getParameter("VOUCHER_NO");
    
    String s=request.getContextPath();
    
    System.out.println("JSP:---"+cmbAcc_UnitCode);
    System.out.println("JSP:---"+cmbOffice_code);
    System.out.println("JSP:---"+txtCB_Year);
    System.out.println("JSP:---"+txtCB_Month);
    System.out.println("JSP:---"+VOUCHER_NO);
%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<body onLoad="initialLoad('<%=s %>','<%=cmbAcc_UnitCode %>','<%=cmbOffice_code %>','<%=txtCB_Year %>','<%=txtCB_Month %>','<%=VOUCHER_NO %>');">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr >
		<td colspan="2" class="table">
		<div id="Bill Details">

<table id="Existing" cellspacing="1" cellpadding="2" border="1"
	width="100%">
				<tr class="tdH">
				<td colspan="8">
				<div align="left"><strong>Voucher Details</strong></div>
				</td>
			</tr>
			
	<tr class="tdTitle">
				<th width="11%">
				<div align="center">To Bank</div>
				</th>

				<th width="11%">
				<div align="center">To A/C No </div>
			  </th>
				
				<th width="13%">
				<div align="center">Debit A/c Code</div>
				</th>
				
				<th width="12%">
				<div align="center">Cheque/DD</div>
				</th>
				
				<th width="16%">
				<div align="center">Cheque/DD Number</div>
				</th>
				
				<th width="13%">
				<div align="center">Cheque/DD Date</div>
				</th>
				
				<th width="11%">
				<div align="center">Amount</div>
			  </th>
				
				<th width="13%">
				<div align="center">Remarks</div>
			  </th>

	</tr>
	<tbody id="tblList" align="center">
	</tbody>
		
		<tr class="tdH">
		<td colspan="8">
		<div align="center"><input type="button" name="btnCan" id="btnCan"
			value="EXIT" onClick="exitfun();" /></div>
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