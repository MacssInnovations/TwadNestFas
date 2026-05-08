<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS OB Report</title>
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
 <script language="javascript" type="text/javascript"  src="../scripts/BRS_OB_Report_not_pushed.js"></script>
<%
	String s = request.getContextPath();
%>
<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
    	 //  setTimeout('LoadMonthYear()',900);
    	   LoadMonthYear('<%=s%>');
      }
       function exit()
       {
          self.close();
       }
       function numbersonly1(e,t)
       {
          var unicode=e.charCode? e.charCode : e.keyCode;
          if(unicode==13)
           {
             try{t.blur();}catch(e){}
             return true;
           
           }
           if (unicode!=8 && unicode !=9  )
           {
               if (unicode<48||unicode>57 ) 
                   return false 
           }
       }
    </script>
</head>


<body 
	onload="LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>BRS OB Report</strong> </div>
		</td>
	</tr>
</table>


<form name="formbrs_ob_report" id="formbrs_ob_report" method="POST"
	action="../../../../../BRS_OB_Report_not_pushed?command=not_pushed"><input
	type="hidden" name="filterFlag" id="filterFlag" value="no">
	<input type="hidden" name="filterFlag1" id="filterFlag1" value="no">
	<input type="hidden" name="filterFlag2" id="filterFlag2" value="no"> 


<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong>
		<div id="imgfld"
			style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
		</div>
		</td>
	</tr>



	<tr class="table">
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
		<div align="left">Bank A/C No.</div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo" onchange="LoadMonthYear('<%=s%>');">
		
			<option value="">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber()" />
		<input type="hidden"
			name="txtOprMode" id="txtOprMode" tabindex="5"
			style="background-color: #ececec" readonly="readonly" size="50" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Cash Book Year &amp; Month</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year" onChange="LoadMonthYear('<%=s%>');"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> 
			<select name="txtCB_Month" id="txtCB_Month" tabindex="4" onChange="LoadMonthYear('<%=s%>');">
			<option value="s">--- Select ---</option>
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
		<div align="left">Type</div>
		</td>
		<td>
		<div align="left">
		<input type="radio" name="twadtype" id="twadtype" value="T" checked="checked">TWAD 
     <!--   <input type="radio" name="twadtype" id="twadtype" value="NT" >NON-TWAD   -->
        </div>
		</td>
	</tr>
</table>






<br>



<!-- ------------------------- Buttons Part --------------------------------  -->

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="SUBMIT" name="butSub"
			id="butSub" value="SUBMIT" />
		&nbsp;&nbsp;&nbsp; <!--
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
               --> <input type="button" name="butCan" id="butCan"
			value="EXIT" onClick="exit();" /></div>
		</td>
	</tr>
</table>
</div>


</form>
</body>
</html>