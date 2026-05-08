<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Annual_Account_Sub_Group</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/Annual_Account_Sub_Group.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmAnnual_Account_Sub_Group" method="post"
	action="Annual_Account_Sub_Group" id="frmAnnual_Account_Sub_Group">
  <table width="930" border="1" align="center">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>Annual Accounting Sub Group </strong></div>		</td>
	</tr>
	<tr class="table">
      <td><div align="left"> Major Head Code <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select name="cmbBudgetGroupMajor" id="cmbBudgetGroupMajor" onChange="getMinorBudgetHeadDesc('<%=s%>'),initialLoad('<%=s%>');">
            <option value="">--- Select ---</option>
          </select>
      </div></td>
    </tr>
	<tr class="table">
      <td width="348"><div align="left">Group Head Code <font color="#ff2121"> *</font></div></td>
	  <td width="383"><div align="left">
	    <select name="cmbGroup_Head_Code" id="cmbGroup_Head_Code" onchange="initialLoad('<%=s%>');">
		<option value="">--- Select ---</option>
        </select>
	  </div>	  </td>
    </tr>
	<tr class="table">
      <td><div align="left">Sub Group Head Code <font color="#ff2121"> *</font></div></td>
	  <td><div align="left">
          <input type="text"
			name="txtSubGroup_Head_Code" id="txtSubGroup_Head_Code" maxlength="15"
			disabled="disabled" />
	    ( Auto Generated Field )</div></td>
    </tr>
	<tr class="table">
      <td><div align="left">Sub Group Head Description <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text"
			name="txtSubGroup_Head_Desc" id="txtSubGroup_Head_Desc" maxlength="90" style="width: 363px; "/>
      </div></td>
    </tr>
	
	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center">
		  <input type="button" name="onsubmit2"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" />
          <input
			type="button" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" />
          <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deletee('<%=s%>');" disabled="disabled" />
          <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="clearAll();" />
          <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" />
		</div>		</td>
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
		<th width="6%">Select</th>
		<th width="10%">Major Head Code</th>
		<th width="10%">Group Head Code</th>		
		<th width="17%">Sub Group Head Code</th>	
		<th width="17%">Sub Group Head Description</th>			
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</div>
</form>
</body>
</html>