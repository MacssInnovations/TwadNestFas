<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Voucher Total - Checking for Difference</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript" language="javascript">
function loadyear_month()
{
      var today= new Date(); 
      var day=today.getDate();
      var month=today.getMonth();
      month=month+1;
      var year=today.getYear();
      if(year < 1900) year += 1900;
                 
     document.frmlistReport.txtCB_Year.value=year;
     document.frmlistReport.txtCB_Month.value=month;

}
      </script>
</head>
<%
	String s = request.getContextPath();
	System.out.println("s");
%><!--

<body
	onLoad="loadyear_month(),LoadAccountingUnitID('LIST_ALL_UNITS'),setTimeout('checkTB()',200);"
	bgcolor="#FFF9FF">
-->

<body
	onLoad="loadyear_month(),LoadAccountingUnitID('LIST_ALL_UNITS'),checkTB();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Voucher Total - Checking for Difference</font></div>
		</td>
	</tr>
</table>

<form name="frmlistReport" id="frmlistReport"
	method="POST" action="../../../../../ReportList"><div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>

	<tr class="table1">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
		</select></div>
		</td>
	</tr>
	<tr class="table1">
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
		<div align="left">Cash Book Year &amp; Month</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)" onchange="checkTB();"></input> 
			<select name="txtCB_Month" id="txtCB_Month" onchange="checkTB();" tabindex="4">
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
		</select></div>
		</td>
	</tr>

<tr class="table">
		<td>
		<div align="left">Doc Type.</div>
		</td>
		<td>
		<div align="left"><select name="cmbDocType" id="cmbDocType" >
			<option value="">--Select--</option>
			<option value="R">Receipt</option>
			<option value="P">Payment</option>
			<option value="J">Journal</option>
		</select>  </div>
		</td>
	</tr>



<tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
          <td colspan="3" class="table">
            <input type="submit" name="CmdGo" value="GO" id="CmdGo" />
            
            <input type="button" name="Exit" value="Exit"
                   id="Exit" onclick="window.close();"/>
            
          </td>
        </tr>
         </table>
         </div>
              </td>
            </tr>


	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>
		</td>
	</tr>
</table>
</div>

</form>
</body>
</html>