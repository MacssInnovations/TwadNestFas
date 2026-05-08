<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statement_Acc_Hd_Mapping</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../Library/scripts/comJS.js"></script>

<script src="../scripts/Statement_Acc_Hd_Mapping.js"
	type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="statementname('<%=s%>'),initialLoad('<%=s%>')" bgcolor="#FFF9FF">
<form name="frmStatement_Acc_Hd_Mapping" method="GET"
action="../../../../../Statement_Acc_Hd_Mapping?command=Report"
	id="frmStatement_Acc_Hd_Mapping"><input type="hidden"
	name="flag" id="flag" value="0" /> <input type="hidden"
	name="cmbMas_SL_type" id="cmbMas_SL_type" value="7"
	onchange="doFunction('Load_MasterSL_Code',this.value);" />

<table width="930" border="1" align="center">
<select name="cmbBudgetGroupMinor" id="cmbBudgetGroupMinor" style="visibility: hidden;">
            <option value="s">--- Select ---</option>
          </select>
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Statement &ndash; A/c
		heads mapping</strong></div>		</td>
	</tr>
	<tr class="table1">
		<td width="348">Statement Name<font color="#ff2121">*</font></td>
		<td width="383"><label> <select name="cmbStatementName"
			id="cmbStatementName" onChange="secondload('<%=s%>'),LoadStatementGroupNo('<%=s%>');">
			<option value="">--- Select ---</option>
		</select> </label></td>
	</tr>
	<tr class="table1">
      <td>Statement Group No<font color="#ff2121">*</font></td>
	  <td><label>
	  <select name="cmbStatementGroupNo" id="cmbStatementGroupNo" onChange="secondload('<%=s%>'),loadStatementSubGroupNo('<%=s%>');">
	  <option value="">--- Select ---</option>
	    </select>
	  </label></td>
    </tr>
    <tr class="table1">
      <td>Statement Sub Group No<font color="#ff2121">*</font></td>
	  <td><label>
	  <select name="statementSubGroupNo" id="statementSubGroupNo" onChange="secondload('<%=s%>')"> 
	  	<option value="">--- Select ---</option>
	  </select>
	  </label></td>
    </tr>
    <tr class="table1">
		<td>
		<div align="left">Group Type<font color="#ff2121">*</font>		</div>		</td>
		<td>
		  <div align="left">
		    <input type="radio" name="groupType" id="groupType1" value="G" checked="checked" onclick="clearGroupCheck()">Group wise
		    <input type="radio" name="groupType" id="groupType2" value="H" onclick="clearGroupCheck()">Headwise
		  </div></td>
	</tr>	
	<tr class="table1">
		<td>From Account Head Code <font color="#ff2121">*</font></td>
		<td><label>
		  <input type="text" name="txtFromAccHdCode" id="txtFromAccHdCode" maxlength="6" onblur="LoadAccHdDescFrom1('<%=s%>');checkGrouptype()" onchange="CheckAccHdCode('<%=s %>');" onkeypress="return numbersonly1(event,this);">
		</label><input type="text" name="txtFromAcc_HeadDesc" readonly="readonly" 
                           id="txtFromAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/></td>
	</tr>	
	<tr class="table1">
		<td>
		<div align="left">To Account Head Code <font color="#ff2121">*</font>		</div>		</td>
		<td>
		  <div align="left">
		    <input type="text" name="txtToAccHdCode" id="txtToAccHdCode" maxlength="6" onblur="LoadAccHdDescTo1('<%=s%>');checkGrouptype()" onchange="CheckAccHdCode1('<%=s %>');" onkeypress="return numbersonly1(event,this);">
		    <input type="text" name="txtToAcc_HeadDesc" readonly="readonly" 
                           id="txtToAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
		  </div></td>
	</tr>
	<tr class="table1">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" />
			  <input
			type="button" name="onUpdate" value="UPDATE" id="onUpdate"
			onClick="Update_DAta('<%=s%>');" disabled="disabled" />
		  <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deleteeee('<%=s%>');" disabled="disabled" />
		  <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refresh();" /> 
			
			
			 <input
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
		<td align="center" colspan="5"><b>EXISTING DETAILS </b></td>
		<td colspan="1">
            	Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" id="cmbpagination" onchange="changepagesize();">
                  <option value="5" >5</option>
                  <option value="10" selected="selected">10</option>
                  <option value="15">15</option>
                  <option value="20">20</option>
                </select>
          	</td>
	</tr>
	<tr class="tdH1">
		<th width="6%">Select</th>
		<th width="9%">Statement Name</th>
		<th width="12%">Statement Group Desc</th>			
		<th width="12%">Statement Sub Group Desc</th>
		<th width="17%">From Account Head Code </th>
		<th width="10%">To Account Head Code </th>		
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
	<tr>
         	<td colspan="6" class="tdH">
         	<center>Pages          
          	<select name="cmbpage" id="cmbpage" onchange="firstLoad();" ></select></center>
        	</td>
     	</tr>
</table>
</div>
</form>
</body>
</html>