<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MIS_Annual_Acc_Grouping_Statement_AbstractReport</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script type="text/javascript"
	src="../../../../Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript" src="../scripts/TwadMIS_Report(TB1).js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
	   var year1;
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             if(month>3)           
			 {
			 year1 = year+1
			 }else{
			 year1 = year-1
			 }
			 
			 if(month>3)           
			 {
			 document.frmMIS_Twad.txtCB_Year.value=year;
			document.frmMIS_Twad.txtCB_Year2.value=year1;
			 }else{
			document.frmMIS_Twad.txtCB_Year.value=year1;
			document.frmMIS_Twad.txtCB_Year2.value=year;
			 }
            
           
      }
    
    </script>
</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS')">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Trial Balance - Report</font>
		</div>
		</td>
	</tr>
</table>


<form name="frmMIS_Twad" id="frmMIS_Twad" action="">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Financial Year</div>
		</td>
		<td><select id="fin_year" name="fin_year"><!--
			<option value="2006-2007">2006-07</option>
			<option value="2007-2008">2007-08</option>
			--><option value="2008-2009">2008-09</option>
			<option value="2009-2010">2009-10</option>
			<option value="2010-2011">2010-11</option>
			<option value="2011-2012">2011-12</option>
			<option value="2012-2013">2012-13</option>
				<option value="2013-2014">2013-14</option>
					<option value="2014-2015"  selected="selected">2014-15</option>
		</select></td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Month</div>
		</td>
		<td>From <select id="CmbFrom_Month" name="CmbFrom_Month">
		<option value="">Select</option>
			<option value="1">January</option>
			<option value="2">February</option>
			<option value="3">March</option>
			<option value="4">April</option>
			<option value="5">May</option>
			<option value="6">June</option>
			<option value="7">July</option>
			<option value="8">August</option>
			<option value="9">September</option>
			<option value="10">October</option>
			<option value="11">November</option>
			<option value="12">December</option>
		</select> To <select id="CmbTo_Month" name="CmbTo_Month">
		<option value="">Select</option>
			<option value="1">January</option>
			<option value="2">February</option>
			<option value="3">March</option>
			<option value="4">April</option>
			<option value="5">May</option>
			<option value="6">June</option>
			<option value="7">July</option>
			<option value="8">August</option>
			<option value="9">September</option>
			<option value="10">October</option>
			<option value="11">November</option>
			<option value="12">December</option>
		</select><input type="checkbox" id="chk1" name="chk1" onclick="unitAll('<%=s%>');" />All Unit
			<input type="checkbox" id="chk" name="chk"
			onclick="divSel();" />Unitwise</td>
	</tr>

	<tr class="table" id="tr_division">
		<td>
		<div id="td_division" style="display: none;">Unit Code</div>
		</td>
		<td>
		<div id="td_divisionX" style="display: none;">
		<select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="unitWise(this.value,'<%=s%>');"
                        tabindex="1">
                
                </select>
                </div>
                <!--<input type="text"
			id="div_code" name="div_code" size="10" /> <input type="button"
			id="help" name="help" value="HELP" onclick="headGrid();" />-->
			
		</td>
	</tr>


	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>

	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit1" value="SUBMIT" id="onsubmit1" onClick="printFunc('<%=s%>');" />
			
			 <input type="button" name="onsubmit1" value="PDF" id="onsubmit1"  onClick="printPDF('<%=s%>');" />
			 <!--<input type="button" value="Back" id="back" style="display: none;" name="back" onclick="back_fun();"/>
		&nbsp;&nbsp;&nbsp; 
		<input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" /> &nbsp;&nbsp;&nbsp; --> 
			<input type="button" name="butCan" id="butCan" value="EXIT" onclick="window.close();" />
			</div>
		</td>
	</tr>
</table>
<div id="detail" style="display: block;">
<table cellspacing="2" cellpadding="3" border="1" width="100%">

	<tr class="tdH">
		<th>Account HeadCode</th>
		<th>Description</th>
		<th>Debit</th>
		<th>Credit</th>
		<th>Balance</th>
	</tr>
	
	<tbody id="tblList" class="table" align="left"></tbody>
</table>
</div>
<div id="detail_unit" style="display: none;">
<table cellspacing="2" cellpadding="3" border="1" width="100%">

	
	<tr class="tdH">
	<th>Select</th>
	<th>Month/Year</th>
			<th>Debit</th>
		<th>Credit</th>
		<th>Balance</th>
	</tr>
	<tbody id="tblList1" class="table" align="left"></tbody>
</table>
</div>
</div>
</form>
</body>
</html>