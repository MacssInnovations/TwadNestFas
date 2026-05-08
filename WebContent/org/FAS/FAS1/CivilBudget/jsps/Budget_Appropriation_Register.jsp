<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Budget Appropriation Register</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../Library/scripts/comJS.js"></script>

<script src="../scripts/Budget_Heads_Ac_heads_mapping.js"
	type="text/javascript"> </script>
	
	<script type="text/javascript">

	function loadyear_month()
	{
	 document.getElementById("fin_year").length=1;
	 var today = new Date();
		var day = today.getDate();
		var month = today.getMonth();
		month = month + 1;
		var year = today.getYear();
		var year1 = 0;
		var financialyear = 0;
		var financialyear1 = 0;
		var fin1="";
		var fin2="";
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
		var ssyr1=financialyear.substring(0,5);
		var ssyr2=financialyear.substring(7,9);
			fin1=ssyr1+ssyr2;
			
			var ssyr3=financialyear1.substring(0,5);
		var ssyr4=financialyear1.substring(7,9);
			fin2=ssyr3+ssyr4;
			
		} else {
			financialyear = year + "-" + year1;
			financialyear1 = (year-1) + "-" + (year1-1);
			var ssyr1=financialyear.substring(0,5);
		var ssyr2=financialyear.substring(7,9);
			fin1=ssyr1+ssyr2;
			var ssyr3=financialyear1.substring(0,5);
		var ssyr4=financialyear1.substring(7,9);
			fin2=ssyr3+ssyr4;

		}
		
		for(var k=0;k<2;k++)
		{
			if(k==0)
			{
				var se = document.getElementById("fin_year");
		  		var op = document.createElement("OPTION");
		  		op.value = financialyear1;
		  		var txt = document.createTextNode(fin2);
		  		op.appendChild(txt);
		  		se.appendChild(op);
			}else if(k==1)
			{
				var se = document.getElementById("fin_year");
		  		var op = document.createElement("OPTION");
		  		op.value = financialyear;
		  		var txt = document.createTextNode(fin1);
		  		op.appendChild(txt);
		  		se.appendChild(op);
		  		
			}                           
		}    
		document.getElementById("fin_year").value=financialyear;          
	}
	
	</script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();" bgcolor="#FFF9FF">
<form name="frmBudget_Heads_Ac_heads_mapping" method="GET"
action="../../../../../Budget_Appropriation_Register"
	id="frmBudget_Heads_Ac_heads_mapping">
	<input type="hidden"
	name="command" id="command" value="Report" /> 
	<input type="hidden"
	name="cmbBudgetDescMain" id="cmbBudgetDescMain" value="0" /> 
	<input type="hidden"
	name="cmbBudgetDescSub" id="cmbBudgetDescSub" value="0">
<table width="930" border="1" align="center">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Budget Appropriation Register</strong></div>		</td>
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
	<tr class="table1">
		<td>
		<div align="left">Financial Year <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="fin_year"
			id="fin_year" tabindex="2">
			
			
		</select></div>
		</td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">AccountHeadCode & Desc</div>		</td>
		 <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6" 
                           
                            onblur="LoadAccHdDesc('<%=s%>');"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
		  <input type="text" name="txtAcc_HeadDesc" id="txtAcc_HeadDesc" size="50" readonly="readonly">
                  </div>
                </td>
		<!--<td><label>
		  <select name="txtAcc_HeadCode" id="txtAcc_HeadCode" onChange="LoadAccHdDesc('<%=s%>');">
		  <option value="">--- Select ---</option>
	    </select>
	     <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
		  <input type="text" name="txtAcc_HeadDesc" id="txtAcc_HeadDesc" size="50" readonly="readonly">
		</label></td>		
	--></tr>
</table>

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td>
		<div align="center">
		<input type="submit" name="pdfRep" id="pdfRep" value="PDF" />
		</div>
		</td>
	</tr>
</table>
</div>
	


</form>
</body>
</html>