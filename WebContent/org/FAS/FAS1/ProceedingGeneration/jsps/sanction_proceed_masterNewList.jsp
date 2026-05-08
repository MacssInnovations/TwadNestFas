<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sanction Proceeding List</title>
<script type="text/javascript" src="../scripts/sanction_proceed_masterNew.js"></script>
 <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
 
 

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>


 <body onload="initialLoad();"> 
<form name="sanctionList" id="sanctionList">
<div style="position:absolute; left: 35px;  width: 948px;">
        <table cellspacing="0" cellpadding="0" border="1" width="100%" align="center" >
               <tr class="table">
                     <td align="center" class="tdH"> 
                            <b>EXISTING DETAILS </b>
                      </td>
                </tr>
        </table>
        <table id="mytable" cellspacing="0" cellpadding="0" border="1" width="100%" align="center">
			<tr class="tdH">
				<td colspan="8">Existing Details</td>
				<td colspan="4">
	            	Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" id="cmbpagination" onchange="changepagesize()">
	                  <option value="5" >5</option>
	                  <option value="10" selected="selected">10</option>
	                  <option value="15">15</option>
	                  <option value="20">20</option>
	                </select>
	          	</td>
			</tr>
			<tr style="background-color: rgb(181,195,246);">
				<th>Select</th>
				<th>Sanction Proc No</th>
				<th>Sanction Proc Date</th>
				<th>Payee Type</th>			
				<th>Payee Code</th>
				<th>Sanctioned By</th>
				<th>Account Head Code</th>
				<th>Budget Provided</th>
				<th>Budget so far spent</th>
				<th>Balance Amount</th>
				<th>Remarks</th>
				<th>Status</th>			                     
	 		</tr>
			<tbody id="tblList" class="table">
				<!-- 
	          	-->
			</tbody>
	 		<tr>
	         	<td colspan="12" class="tdH">
	         	<center>Pages          
	          	<select name="cmbpage" id="cmbpage" onchange="changepage()" ></select></center>
	        	</td>
	     	</tr>
		</table> 
	   <table align="center" cellspacing="0" cellpadding="0" border="1" width="100%">
	        <tr>
	          <td align="center">
	                <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="self.close();"></input>
	          </td>
	        </tr>
	   </table> 
</div>
</form>
</body>
</html>