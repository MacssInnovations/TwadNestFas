<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>annual_report</title>
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
<form name="annual_report" id="annual_report"  method="POST" action="../../../../../../TWAD_A89_Reports" >
<table width="930" border="1" align="center">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>Annual Report </strong></div>		</td>
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
      <td>Minor Head Code <font color="#ff2121"> *</font></td>
	  <td><select name="cmbBudgetGroupMinor" id="cmbBudgetGroupMinor" onChange="LoadAccHdCode('<%=s%>');">
          <option value="">--- Select ---</option>
      </select></td>
    </tr>
   
   <tr class="table">
		<td>
		<div align="left">Financial  Year</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)">
		</input>
		- 
		<input type="text" name="txtCB_Year2"
			id="txtCB_Year2" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)">
		</div></td>
	</tr>
	 <tr class="table">
        <td align="left">Report Option:</td>
        <td colspan="3" align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
          HTML
        </td>
      </tr>
	
</table>
 
     <table width="930" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
</form>
</body>
</html>