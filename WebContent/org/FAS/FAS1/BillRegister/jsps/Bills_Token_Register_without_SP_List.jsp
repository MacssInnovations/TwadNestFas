<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bills_Token_Register_without_SP_List</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script src="../scripts/Bills_Token_Register_without_SP_List.js"
	type="text/javascript"> </script>


</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>

<body onLoad="initialLoad('<%=s%>');">
 
<form name="FrmBillTokenRegisterEntry_WithoutProceeding_List" method="post" action="BillTokenRegisterEntry_WithoutProceeding_List" id="FrmBillTokenRegisterEntry_WithoutProceeding_List">
<%
                int acc_unit_id=Integer.parseInt(request.getParameter("unit_id"));
                int office_id=Integer.parseInt(request.getParameter("office_id"));
                out.println("<input type='hidden' name='unit_id' id='unit_id' value="+acc_unit_id+">");
                out.println("<input type='hidden' name='office_id' id='office_id' value="+office_id+">");
          %>
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="221%" align="center">
	<tr class="tdH">
		<td align="left" colspan="27"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH">
		<th style="font-size: xx-small" width="4%">Select</th><!--
		<th width="8%">AccountingUnit</th>
		<th width="8%">AccountingForOffice</th>
		--><th style="font-size: xx-small" width="7%">BillMajorType-BillMinorType</th>
		<!--<th style="font-size: xx-small" width="7%">BillMinorType</th>
		--><th style="font-size: xx-small" width="7%">BillSubType</th>
		<th style="font-size: xx-small" width="7%">BillNo-BillDate</th><!--
		<th style="font-size: xx-small" width="7%">BillDate</th>
		--><th style="font-size: xx-small" width="7%">ManualProcNo-ManualProcDate</th>
		<!--<th style="font-size: xx-small" width="7%">ManualProcDate</th>
		--><th style="font-size: xx-small" width="7%">InvoiceRcvdDate-NoOfInvoices</th>
		<!--<th style="font-size: xx-small" width="7%">NoOfInvoices</th>
		--><!--<th style="font-size: xx-small" width="7%">MTC70Entry</th>
		--><th style="font-size: xx-small" width="7%">TotalSanctionAmount</th>
		<th style="font-size: xx-small" width="7%">TotalBillAmount</th>
		<th style="font-size: xx-small" width="7%">Deducted Amount</th>
		<th style="font-size: xx-small" width="7%">Acc_HeadCode</th>
		<th style="font-size: xx-small" width="7%">PayeeType-PayeeCode</th>
		<!--<th style="font-size: xx-small" width="7%">PayeeCode</th>
		--><!--<th style="font-size: xx-small" width="7%">BillProcessingDoneBy</th>
		<th width="6%">RefNo</th>		
		<th width="8%">RefDate</th>
		--><th style="font-size: xx-small" width="7%">Remarks</th>
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</form>
</body>
</html>