<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>Work_Bill_Journal_Voucher_Details</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link rel=StyleSheet href="../../../../../css/CalendarControl.css"
	type="text/css">
<script src="../scripts/Work_Bill_Journal.js" type="text/javascript"> </script>
</head>
<%
    String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
    String cmbOffice_code=request.getParameter("cmbOffice_code");
    String txtCB_Year=request.getParameter("txtCB_Year");
    String txtCB_Month=request.getParameter("txtCB_Month");
    String JournalVoucherNo=request.getParameter("JournalVoucherNo");
    
    String s=request.getContextPath();
    
    System.out.println("JSP:---"+cmbAcc_UnitCode);
    System.out.println("JSP:---"+cmbOffice_code);
    System.out.println("JSP:---"+txtCB_Year);
    System.out.println("JSP:---"+txtCB_Month);
    System.out.println("JSP:---"+JournalVoucherNo);
    
%>
<body onLoad="initialLoad('<%=s %>','<%=cmbAcc_UnitCode %>','<%=cmbOffice_code %>','<%=txtCB_Year %>','<%=txtCB_Month %>','<%=JournalVoucherNo %>');">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr >
		<td colspan="2" class="table">
		<div id="Bill Details">
		  <table id="Existing" cellspacing="1" cellpadding="2" border="1"
	width="100%">
            <tr class="">
              <td colspan="20"><div align="left"><strong>Work Bill Journal Voucher Details</strong></div></td>
            </tr>
            <tr class="tdTitle">            
              <th width="5%" >Sl No</th>
			  <th width="9%" >Acc Hd Code</th>
              <th width="10%">Sub Ledger Type</th>
              <th width="11%" >Sub Ledger Code</th>
              <th width="8%">CR/DR Indicator</th>
              <th width="5%">Bill No</th>
              <th width="6%" >Bill Type</th>
              <th width="10%" >Agreement No</th>
              <th width="10%" >Agreement Date</th>
              <th width="5%" >Bill Date</th>
              <th width="7%" >Amount</th>             
            </tr>
            <tbody id="tblList" align="center">
            </tbody>
          </table>
		</div>		</td>
	</tr>
	<tr class="tdH">
		<td colspan="7">
		<div align="center"><input type="button" name="btnCan" id="btnCan"
			value="EXIT" onClick="exit();" /></div>
		</td>
	</tr>
</table>
</div>
</body>
</html>