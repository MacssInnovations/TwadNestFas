<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ASSET REGISTER CALCULATION </title>
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
<script type="text/javascript" src="../scripts/TwadMISAsset_Report.js"></script>

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
			 document.frmMIS_Asset.txtCB_Year.value=year;
			document.frmMIS_Asset.txtCB_Year2.value=year1;
			 }else{
			document.frmMIS_Asset.txtCB_Year.value=year1;
			document.frmMIS_Asset.txtCB_Year2.value=year;
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
<body onload="">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Assets Register Calculation Details</font>
		</div>
		</td>
	</tr>
</table>


<form name="frmMIS_Asset" id="frmMIS_Asset" action="">
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
		<td><select id="fin_year" name="fin_year">
			<option value="2006-2007">2006-07</option>
			<option value="2007-2008">2007-08</option>
			<option value="2008-2009">2008-09</option>
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
		</select>
	</td>
	</tr>
	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>

	<tr class="tdH">
		<td colspan="2">
		<div align="center">
		<input type="button" name="onsubmit1" value="SUBMIT" id="onsubmit1" onClick="load_Grid_TB('<%=s%>');" />
	    <input type="button" name="onsubmit1" value="PDF" id="onsubmit1"  onClick="printPDF('<%=s%>','All');" />
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
		<th width="20%">Net amount for the year ( Debit - Credit )</th>		
		<th id="th_id">OB for the year</th>
	</tr>
	
	<tbody id="tblList" class="table" align="left"></tbody>
</table>
</div>
<div id="detail_unit" style="display: none;">
<table cellspacing="2" cellpadding="3" border="1" width="100%">	
	<tr class="tdH" >
		<th>Major Head</th>
		<th>Debit</th>
		<th>Credit</th>
		<th>Balance</th>
	</tr>
	<tbody id="tblList1" class="table" align="left"></tbody>
</table>
</div>
<div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
		
</div>
</form>
</body>
</html>