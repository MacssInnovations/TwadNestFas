<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Allocation_Freeze_HO</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript" src="../scripts/Allocation_Freeze.js"></script>
<script type="text/javascript" language="javascript">



function loadyear_month()
{
 document.getElementById("cmbFinancialYear").length=1;
 var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	var year1 = 0;
	var financialyear = 0;
	var financialyear1 = 0;
	var financialyear2 = 0;
	var financialyear3 = 0;
	var financialyear4 = 0;
	var financialyear5 = 0;
	
	
	var fin1="";
	var fin2="";
	var fin3="";
	var fin4="";
	var fin5="";
	var fin6="";
	
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}

	if (month < 4) {
		financialyear = year1 + "-" + year;
		financialyear1 = (year1-1) + "-" + (year-1);
		financialyear2 = (year1-2) + "-" + (year-2);
		financialyear3 = (year1-3) + "-" + (year-3);
		financialyear4 = (year1-4) + "-" + (year-4);
		financialyear5 = (year1-5) + "-" + (year-5);
	var ssyr1=financialyear.substring(0,5);
	var ssyr2=financialyear.substring(7,9);
		fin1=ssyr1+ssyr2;
	var ssyr3=financialyear1.substring(0,5);
	var ssyr4=financialyear1.substring(7,9);
		fin2=ssyr3+ssyr4;
		
	var ssyr5=financialyear2.substring(0,5);
	var ssyr6=financialyear2.substring(7,9);
		fin3=ssyr5+ssyr6;
	var ssyr7=financialyear3.substring(0,5);
	var ssyr8=financialyear3.substring(7,9);
		fin4=ssyr7+ssyr8;
		
	var ssyr9=financialyear4.substring(0,5);
	var ssyr10=financialyear4.substring(7,9);
		fin5=ssyr9+ssyr10;
	var ssyr11=financialyear5.substring(0,5);
	var ssyr12=financialyear5.substring(7,9);
		fin6=ssyr11+ssyr12;
		
	} else {
		financialyear = year + "-" + year1;
		financialyear1 = (year-1) + "-" + (year1-1);
		financialyear2 = (year-2) + "-" + (year1-2);
		financialyear3 = (year-3) + "-" + (year1-3);
		financialyear4 = (year-4) + "-" + (year1-4);
		financialyear5 = (year-5) + "-" + (year1-5);
	var ssyr1=financialyear.substring(0,5);
	var ssyr2=financialyear.substring(7,9);
		fin1=ssyr1+ssyr2;
	var ssyr3=financialyear1.substring(0,5);
	var ssyr4=financialyear1.substring(7,9);
		fin2=ssyr3+ssyr4;
		var ssyr5=financialyear2.substring(0,5);
	var ssyr6=financialyear2.substring(7,9);
		fin3=ssyr5+ssyr6;
	var ssyr7=financialyear3.substring(0,5);
	var ssyr8=financialyear3.substring(7,9);
		fin4=ssyr7+ssyr8;
	var ssyr9=financialyear4.substring(0,5);
	var ssyr10=financialyear4.substring(7,9);
		fin5=ssyr9+ssyr10;
	var ssyr11=financialyear5.substring(0,5);
	var ssyr12=financialyear5.substring(7,9);
		fin6=ssyr11+ssyr12;

	}
	for(var k=0;k<6;k++)
	{
		if(k==0)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = fin6;
	  		var txt = document.createTextNode(fin6);
	  		op.appendChild(txt);
	  		se.appendChild(op);
		}else if(k==1)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = fin5;
	  		var txt = document.createTextNode(fin5);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}
		else if(k==2)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = fin4;
	  		var txt = document.createTextNode(fin4);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}
		else if(k==3)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = fin3;
	  		var txt = document.createTextNode(fin3);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}     
		else if(k==4)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = fin2;
	  		var txt = document.createTextNode(fin2);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}     
		else if(k==5)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = fin1;
	  		var txt = document.createTextNode(fin1);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}                                
	}    
	document.getElementById("cmbFinancialYear").value=fin1;          
}

     
      </script>
</head>
<%
	String s = request.getContextPath();
	System.out.println("s");
%>

<body
	onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad1('<%=s%>');loadyear_month();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget - Freeze
		or Nill Freeze  for HO Budget Allocation </font></div>
		</td>
	</tr>
</table>

<form name="frmAllocation_Freeze" id="frmAllocation_Freeze"
	method="POST" action="../../../../../Allocation_Freeze">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>

	<tr class="table1">
		<td>
		<div align="left">Budget Allocated Accounting Unit Code <font color="#ff2121">*</font>
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
		<div align="left">Budget Allocated Accounting For Office Code <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">
		</select></div>
		</td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">Financial Year <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmbFinancialYear"
			id="cmbFinancialYear" onChange="ChangeYearDuration();">
		</select></div>
		</td>
	</tr><!--
	<tr class="table1">
		<td>
		<div align="left">Freeze Type <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmbFreezeType"
			id="cmbFreezeType">			
			<option value="FBA">Freeze-Budget Allocation</option>			
		</select></div>
		</td>
	</tr>
	--><tr class="table1">
		<td width="35%">
		<div align="left">Statement Name <font color="#ff2121">*</font></div>
		</td>
		<td width="65%">
		<div align="left"><select name="cmbFormat_Name"
			id="cmbFormat_Name">
			<option value="">--- Select ---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>
		</td>
	</tr>
</table>
</div>

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td>
		<div align="center"><input type="button" name="butSub"
			id="butSub" value="SUBMIT" onClick="FuncSave_HO('<%=s%>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="clrForm();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onClick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>