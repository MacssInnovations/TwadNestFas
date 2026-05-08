<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil Budget Format Report 1</title>
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
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript"
	src="../scripts/Civil_Budget_Format_Report_1.js"></script>

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
			 document.frmCivil_Budget_Format_Report_1.txtCB_Year.value=year;
			document.frmCivil_Budget_Format_Report_1.txtCB_Year2.value=year1;
			 }else{
			document.frmCivil_Budget_Format_Report_1.txtCB_Year.value=year1;
			document.frmCivil_Budget_Format_Report_1.txtCB_Year2.value=year;
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
<body onLoad="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget Format -  Report</font> (<font size="4"> Summary</font> ) </div>
		</td>
	</tr>
</table>


<form
	name="frmCivil_Budget_Format_Report_1"
	id="frmCivil_Budget_Format_Report_1"
	method="POST"
	action="Civil_Budget_Format_Report_1">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>



	<tr class="table">
		<td width="42%">
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td width="58%">
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
		</select></div>
		</td>
	</tr>



	<tr class="table">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">
		</select></div>
		</td>
	</tr>



	<tr class="table">
		<td>
		<div align="left">Financial  Year</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)">
		
		
		<input type="text" name="txtCB_Year2"
			id="txtCB_Year2" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)">
		</div></td>
	</tr>
     <tr class="table">
        <td align="left">Report Option:</td>
        <td colspan="3" align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
          HTML
        </td>
      </tr>
	<tr class="table">
		<td>
		<div align="left">Type Of Format</div>
		</td>
		<td>
		<div align="left"><select size="1" name="format_type"
			id="format_type" tabindex="2">
			<option value="f1">Format-1</option>
			<option value="f2">Format-2</option>
			<option value="f3">Format-3</option>
			<option value="f4">Format-4</option>
			<option value="f5">Format-5</option>
			<option value="f6">Format-6</option>
			<option value="f7">Format-7</option>
			<option value="f8">Format-8</option>
			<option value="f9">Format-9</option>
			<option value="f10">Format-10</option>
			<option value="f11">Format-11</option>
			
			</select>
		</div>
		</td>
		</tr>

	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<div align="left" style="visibility: hidden"><input type="hidden"
		name="txtBankID" id="txtBankID" tabindex="5"
		style="background-color: #ececec" readonly="readonly" size="50" /> <input
		type="hidden" name="txtBranchID" id="txtBranchID" size="50"
		style="background-color: #ececec" readonly="readonly" /></div>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit1"
			value="SUBMIT" id="onsubmit1" onClick="printFunc('<%=s%>');" />
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