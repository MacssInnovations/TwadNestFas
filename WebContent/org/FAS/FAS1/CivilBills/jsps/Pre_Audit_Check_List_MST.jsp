<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pre_Audit_Check_List_MST</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script src="../scripts/Pre_Audit_Check_List_MST.js"
	type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body
	onload="LoadAccountingUnitID('LIST_ALL_UNITS'),initialLoad('<%=s%>');">
<form name="frm_Pre_Audit_Check_List_MST" method="post" action="Pre_Audit_Check_List_MST"
	id="frm_Pre_Audit_Check_List_MST">
<table width="100%" border="1" align="center">
	<tr>
		<td colspan="2" class="tdH">
		<div align="center">Pre-Audit Check List Master</div>		</td>
	</tr>
	<!--  <tr class="table">
		<td width="348"><div align="left">Accounting Unit<font color="#ff2121">*</font></div></td>
		<td width="383"><label> 
		  <div align="left">
		    <select size="1"
			name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onChange="common_LoadOffice(this.value);">
	          </select>
	    </div>
		</label></td>
	</tr>
	<tr class="table">
		<td><div align="left">Accounting For Office<font color="#ff2121">*</font></div></td>
		<td><div align="left">
		  <select size="1" name="cmbOffice_code" id="cmbOffice_code"
			tabindex="2">
	      </select>
	    </div></td>
	</tr>-->
			<tr class="table">
		<td>
		  <div align="left">Check List Code<font color="#ff2121">*</font>		  </div></td>
		<td>
		  <div align="left">
		    <input type="text" name="txtCheckListCode"
			id="txtCheckListCode" maxlength="15" onKeyPress="return numbersonly1(event,this)" readonly="true"/> 
		    (Auto Generated)</div></td>
	</tr>
		<tr class="table">
		<td>
		  <div align="left">Check List Description<font color="#ff2121">*</font></div></td>
		<td>
		  <div align="left">
		    <textarea name="mtxtCheckListDesc"
			id="mtxtCheckListDesc" cols="50" rows="4"></textarea>
			<input type="hidden" name="pat" id="pat" value="<%=s%>">
	      </div></td>
	</tr>
		<tr class="table">
		<td>
		  <div align="left">Bill Major Type <font color="#ff2121">*</font>		  </div></td>
		<td>
		  <div align="left">
		    <select size="1" name="cboBillMajorType"
			id="cboBillMajorType" onChange="getBillMinorType('<%=s %>');">
		      <option value="s">---Select---</option>
	          </select>
	      </div></td>
	</tr>
	<tr class="table">
		<td>
	    <div align="left">Bill Minor Type <font color="#ff2121">*</font>	    </div></td>
		<td>
		  <div align="left">
		    <select size="1" name="cboBillMinorType"
			id="cboBillMinorType" onChange="getBillsubType('<%=s %>');">
		      <option value="s">---Select---</option>
	          </select>
        </div></td>
	</tr>
	<tr class="table">
		<td>
	    <div align="left">Bill Sub Type <font color="#ff2121">*</font></div></td>
		<td>
		  <div align="left">
		    <select size="1" name="cboBillSubType"
			id="cboBillSubType" onChange="getGrid('<%=s %>')">
		      <option value="s">---Select---</option>
	          </select>
        </div></td>
	</tr>
		<tr class="table">
			<td>
			  <div align="left">Not Applicable ?<font color="#ff2121">*</font></div></td>
			<td>
			<div align="left"><input type="radio" name="rdoNotApplicable"
				id="rdoNotApplicable" value="Y" onclick="notApp()"><label>Yes</label><input
				type="radio" name="rdoNotApplicable" id="rdoNotApplicable" value="N" checked="checked" onclick="notApp()"><label>No</label></div>		</td>
		</tr>
		<tr class="table">
		<td>
		  <div align="left">Mandate ?<font color="#ff2121">*</font></div></td>
		<td>
		<div align="left"><input type="radio" name="rdoMandate"
			id="rdoMandate" value="Y" checked="checked" disabled="disabled"><label>Yes</label><input
			type="radio" name="rdoMandate" id="rdoMandate" value="N" disabled="disabled"><label>No</label></div>		</td>
	</tr>		
		<tr class="table">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" /> <input
			type="button" name="ondelete" value="CANCEL" id="ondelete"
			onClick="deleteeee('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="ClearAll('<%=s%>');" /> <input type="button"
			name="onexit" value="EXIT" id="onexit" onClick="exitfun('<%=s%>');" />
		</div>		</td>
	</tr>
	</table>
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="100%" align="center">
	<tr class="tdH">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH">
		<th width="4%">Select</th>
		<!--<th width="7%">Accounting Unit</th>
		<th width="7%">Accounting For Office</th>-->
		<th width="5%">Check List Code</th>
		<th width="6%">Check List Description</th>
		<th width="7%">Bill Major Type</th>
		<th width="7%">Bill Minor Type</th>
		<th width="6%">Bill Sub Type</th>
		<th width="7%">Mandate</th>
		<th width="6%">Not Applicable</th>		
		<th width="7%">Status</th>
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</form>
</body>
</html>