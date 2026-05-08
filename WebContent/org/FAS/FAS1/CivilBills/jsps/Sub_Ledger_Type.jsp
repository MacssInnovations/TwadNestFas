<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sub-Ledger Types - User Defined</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link rel=StyleSheet href="css/Sample3.css" type="text/css">

<script src="../scripts/Sub_Ledger_Types.js" type="text/javascript"> </script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript" >
function callload()
{
	initialLoad();
		}
function callloadNew()
{
	initialLoadNew();
		}


</script>



</head>
<%
	String s = request.getContextPath();
	System.out.println("Value of S****"+s);
%>
<%
	System.out.println(s);
%>
<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');setTimeout('callloadNew()',1000);setTimeout('callload()',600)">
<form name="SubLedgerTypes" method="post" action="SubLedgerTypes"
	id="SubLedgerTypes">
	<input type="hidden" name="typeid" id=""typeid"">
<table width="100%" border="1" align="center">
	<tr>
		<td colspan="2" class="tdH">
		<div align="center">Sub-Ledger Types - User Defined</div>		</td>
	</tr>
	<tr class="table">
		<td width="348"><div align="left">Accounting Unit<font color="#ff2121">*</font></div></td>
		<td width="383"><label> 
		<div align="left">
		  <select size="1"
			name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onChange="common_LoadOffice(this.value);initialLoad('<%=s%>');">
	      </select>
	    </div>
		</label></td>
	</tr>
	<tr class="table">
		<td><div align="left">Accounting For Office<font color="#ff2121">*</font></div></td>
		<td><div align="left">
		  <select size="1" name="cmbOffice_code" id="cmbOffice_code">
	      </select>
		  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Sub Ledger Type<font color="#ff2121">*</font></div></td>
		<td><div align="left">
		  <select name="cbosubLedgerType" id="cbosubLedgerType">			
		    <option value="1">Miscellaneous</option>
	      </select>
		  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Name<font color="#ff2121">*</font></div></td>
		<td><div align="left">
		  <input type="text" name="txtName" id="txtName">
		  </div></td>
	</tr>

	<tr class="table">
		<td><div align="left">Address<font color="#ff2121">*</font></div></td>
		<td><label> 
		<div align="left">
		  <textarea name="mtxtAddress" id="mtxtAddress"></textarea>
	    </div>
		</label></td>
	</tr>
	<tr class="table">
		<td><div align="left">Phone<font color="#ff2121">*</font></div></td>
		<td><div align="left">
		  <input type="text" name="txtPhone" id="txtPhone" onKeyPress="return numbersonly1(event,this)">
		  </div></td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deleteeee('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refresh();" /> <input type="button"
			name="onexit" value="EXIT" id="onexit" onClick="exitfun();" />
		</div>		</td>
	</tr>
</table>
<table cellspacing="3" cellpadding="2" border="1" width="100%"
	align="center">
	<tr class="tdH">
		<td align="center" class="tdH"><b>EXISTING DETAILS </b></td>
	</tr>
</table>
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="100%" align="center">
	<tr class="tdH">
		<th width="2%">Select</th>
		<th width="3%">Accounting Unit</th>
		<th width="4%">Accounting For Office</th>		
		<th width="4%">Name</th>
		<th width="4%">Address</th>
		<th width="2%">Phone</th>
		
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>

</form>
</body>
</html>