<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Budget_Heads_Ac_heads_mapping</title>
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
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');formateno('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmBudget_Heads_Ac_heads_mapping" method="GET"
action="../../../../../Budget_Heads_Ac_heads_mapping?command=Report"
	id="frmBudget_Heads_Ac_heads_mapping"><input type="hidden"
	name="flag" id="flag" value="0" /> <input type="hidden"
	name="cmbMas_SL_type" id="cmbMas_SL_type" value="7"
	onchange="doFunction('Load_MasterSL_Code',this.value);" />

<table width="930" border="1" align="center">
<select name="cmbBudgetGroupMinor" id="cmbBudgetGroupMinor" style="visibility: hidden;">
            <option value="s">--- Select ---</option>
          </select>
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Budget Heads &ndash; A/c
		heads mapping</strong></div>		</td>
	</tr>
	<tr class="table1">
		<td width="348">Format No<font color="#ff2121">*</font></td>
		<td width="383"><label> 
		<select name="cmbFormatNo" id="cmbFormatNo" onChange="LoadMajorandSubBudgetHeads('<%=s%>'),initialLoad('<%=s%>'); ">
			<option value="">--- Select ---</option>
		</select> </label></td>
	</tr>
		<tr class="table1">
      <td>Budget Group Desc<font color="#ff2121">*</font></td>
	  <td><label>
	  <select name="cmbBudgetGroupMajor" id="cmbBudgetGroupMajor" onChange="getBudgetGroupMinor('<%=s%>'),LoadAccHdCode('<%=s%>'),initialLoad('<%=s%>');">
	  <option value="">--- Select ---</option>
	    </select>
	    <input type="hidden" name="cmbBudgetDescMain" id="cmbBudgetDescMain" value="0">
	    <input type="hidden" name="cmbBudgetDescSub" id="cmbBudgetDescSub" value="0">
	  </label></td>
    </tr>
	<tr class="table1">
		<td>
		<div align="left">AccountHeadCode & Desc</div>		</td>
		 <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6" 
                            onkeypress="return numbersonly(event,this)"
                            onchange="sixdigit();" 
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
	<tr class="table1">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deleteeee('<%=s%>');" disabled="disabled" /> <input
			type="hidden" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refresh();" /> <input
type="submit" name="reportbutton" value="REPORT" />
			 <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>		</td>
	</tr>
</table>

<p>&nbsp;</p>
<div id=div2 style="overflow: scroll; absolute; height:350px; width:94% align:center ">
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="97%" align="center" class="table1">
	<tr class="tdH1">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH1">
		<th width="16%">Select</th>
		<th width="20%">Format No</th>
		<!--<th width="10%">Budget Group Major Code</th>-->
		<th width="30%">Budget Group Major Desc</th>
		<th width="30%">Account Head Code</th>
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</div>
</form>
</body>
</html>