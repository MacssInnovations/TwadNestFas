<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Annual_Account_AccHead_Mapping</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/Annual_Account_AccHead_Mapping.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmAnnual_Account_AccHead_Mapping" method="post"
	action="Annual_Account_AccHead_Mapping" id="frmAnnual_Account_AccHead_Mapping">
<table width="930" border="1" align="center">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>Annual Accounting  Acc Head Mapping </strong></div>		</td>
	</tr>
	<tr class="table">
      <td><div align="left"> Major Head Code <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select name="cmbBudgetGroupMajor" id="cmbBudgetGroupMajor" onChange="getMinorBudgetHeadDesc('<%=s%>');">
            <option value="">--- Select ---</option>
          </select>
      </div></td>
    </tr>
	<tr class="table">
      <td width="348"><div align="left">Group Head Code <font color="#ff2121"> *</font></div></td>
	  <td width="383"><div align="left">
	    <select name="cmbGroup_Head_Code" id="cmbGroup_Head_Code" onChange="LoadSubHeadCode('<%=s%>');">
		<option value="">--- Select ---</option>
        </select>
	  </div>	  </td>
    </tr>
	<tr class="table">
      <td>Sub Head Code <font color="#ff2121"> *</font></td>
	  <td><select name="cmbSubHeadCode" id="cmbSubHeadCode" onChange="LoadAccHdCode('<%=s%>');">
        <option value="">--- Select ---</option>
      </select></td>
	</tr>
	<tr class="table">
      <td>Minor Head Code <font color="#ff2121"> *</font></td>
	  <td><select name="cmbBudgetGroupMinor" id="cmbBudgetGroupMinor" onChange="LoadAccHdCode('<%=s%>');">
          <option value="">--- Select ---</option>
      </select></td>
    </tr>
    <!--	<tr class="table">
      <td><div align="left">AccountHeadCode & Desc <font color="#ff2121">*</font></div></td>
	  <td>
        <select name="txtAcc_HeadCode" id="txtAcc_HeadCode" onChange="LoadAccHdDesc('<%=s%>');">
          <option value="">--- Select ---</option>
        </select>
        <input type="text" name="txtAcc_HeadDesc" id="txtAcc_HeadDesc" size="50" readonly="readonly">      </td>
    </tr>  -->
	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deletee('<%=s%>');" disabled="disabled" />
		  <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refreshh();" /> <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>		</td>
	</tr>
</table>

<p>&nbsp;</p>
<div id=div2 style="overflow: scroll; absolute; height:400px; width:94% align:center ">
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="100%" align="center" class="table">
	<tr class="tdH">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH">
		<th width="11%">Select</th>
		<th width="16%">Major Head Code</th>
		<th width="18%">Group Head Code</th>
		<th width="15%">Sub Head Code</th>		
		<th width="21%">Minor Head Code</th>	
		<th width="19%">Account Head Code</th>			
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</div>
</form>
</body>
</html>