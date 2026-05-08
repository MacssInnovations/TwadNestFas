<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MIS_Major_Group_Acc_Head_Mapping</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/MIS_Major_Group_Acc_Head_Mapping.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmMIS_Major_Group_Acc_Head_Mapping" method="post"
	action="MIS_Major_Group_Acc_Head_Mapping" id="frmMIS_Major_Group_Acc_Head_Mapping">
<table width="930" border="1" align="center">
			
	<tr class="tdH">
		<td colspan="2">
			<div align="center"><strong>MIS Group and Acc Head Mapping </strong></div>
			<input type="hidden" name="strpath" id="strpath" value="<%=s%>">
		</td>
	</tr>
	<tr class="table">
		<td><div align="left">Type<font color="#ff2121">*</font></div> </td>
		<td><input type="radio" name="checke" id="checke1" value="MIS" onclick="checkvalue('<%=s%>');" checked >MIS
			<input type="radio" name="checke" id="checke2" value="EXP" onclick="checkvalue('<%=s%>');" >EXP
		</td>	
	</tr>
	<tr class="table">
      <td><div align="left"> Major Head Code <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select name="cmbBudgetGroupMajor" id="cmbBudgetGroupMajor" onChange="loadGroupMinor('<%=s%>'),loadExp('<%=s%>');">
            <option value="">--- Select ---</option>
          </select>
      </div></td>
    </tr>
	<tr class="table">
      <td width="348"><div align="left">Group Head Code <font color="#ff2121"> *</font></div></td>
	  <td width="383"><div align="left">
	    <select name="cmbGroup_Head_Code" id="cmbGroup_Head_Code" onchange="LoadGroupHeadCode('<%=s%>');">
		<option value="">--- Select ---</option>
        </select>
	  </div>	  </td>
    </tr>
	<tr class="table">
      <td>Minor/Sub Head Code <font color="#ff2121"> *</font></td>
	  <td><select name="cmbBudgetGroupMinor" id="cmbBudgetGroupMinor" onChange="LoadtxtAccCode('<%=s%>'),LoadGroupHeadCode('<%=s%>');">
          <option value="">--- Select ---</option>
      </select></td>
    </tr>
	<tr class="table">
      <td><div align="left">AccountHeadCode & Desc <font color="#ff2121">*</font></div></td>
	  <td>
        <select name="txtAcc_HeadCode" id="txtAcc_HeadCode" onChange="LoadAccHdDesc('<%=s%>'),LoadGroupHeadCode('<%=s%>');">
          <option value="">--- Select ---</option>
        </select>
        <input type="text" name="txtAcc_HeadDesc" id="txtAcc_HeadDesc" size="50" readonly="readonly">      </td>
    </tr>
	<tr class="table">
		<td><div align="left">Level No. <font color="#ff2121">*</font></div></td>
		<td><select name="levelno" id="levelno">
          <option value="">--- Select ---</option>
          <option value="0">0</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select></td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="AddValue('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="DeleteValue('<%=s%>');" disabled="disabled" />
		  <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refreshh();" /> <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>		</td>
	</tr>
</table>

<p>&nbsp;</p>
<div id=div2 style="overflow: scroll; absolute; height:400px; width:94% align:center ">
<table id="Existing" cellspacing="2" cellpadding="3" border="1"	width="100%" align="center" class="table">
	<tr class="tdH">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH">
		<th width="6%">Select</th>
		<th width="10%">Major Head Code</th>
		<th width="10%">Group Head Code</th>		
		<th width="17%">Minor/Sub Head Code</th>	
		<th width="17%">Account Head Code</th>			
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
<div id="ajax" align="center" style="visibility: visible; top: 30%; position: relative;">
	 <font style="font-style:italic; color: gray;">Please Wait Loading</font>	
	 <img src="../../../../../images/ajax-loader.gif" alt="Show ajax image"></img>
</div>
</div>
</form>
</body>
</html>