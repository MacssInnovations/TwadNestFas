<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.sql.*, Servlets.Security.classes.UserProfile"%>
<%@ page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver" %>
<html>
<head>
<meta http-equiv="Content-Type"	content="text/html; charset=windows-1252" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/> 
<script type="text/javascript"	src="../scripts/bill_major_type_mst.js"></script>
<title>Bill Types- Major</title>
</head>
<body onload="callServer('Get')" class="table">
<form name="billTypes" id="billTypes" method="post">
	<table cellspacing="2" cellpadding="3" border="1" width="75%" align="center">
		<tr>
			<th colspan="2" class="tdH">Major Bill Types Master Details</th>
		</tr>
		<tr>
          	<th colspan="2" align="left" style="background-color: rgb(181,195,246);"> Bill Details	</th>
         </tr>
       		<tr>     
				<td class="tdH">Bill Major Type Code</td>				
				<td class="tdH">				
					<input type="text" name="bill_type_code"  id="bill_type_code" readonly size="3"/>(Auto generated)
				</td>
			</tr>		  
			<tr> 
				<td class="tdH" style="width: 208px">Bill Major Type Description</td>
				<td class="tdH">
					<input type="text" name="bill_type_desc" id="bill_type_desc" maxlength="50" />  
				</td>
			</tr>
			<tr> 
				<td class="tdH" style="width: 208px">Remarks</td>
				<td class="tdH">
					<textarea cols="45" rows="5" name="remarks" id="bill_remarks"></textarea>										  
				</td>
			</tr>			                 
			<tr>
				<td colspan="4" class="table">
				<center>
				<input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add')" /> 
				<input type="button" name="CmdUpdate" value="UPDATE" id="CmdUpdate" onclick="callServer('Update')" disabled /> 
				<input type="button" name="CmdDelete" value="CANCEL" id="CmdDelete" onclick="callServer('Delete')" disabled /> 
				<!--<input type="button" name="CmdValidate" value="VALIDATE" id="CmdValidate" onclick="callServer('Validate')" disabled /> 
				--><input type="reset" name="CmdClear" value="CLEAR ALL" id="CmdClear" onclick="clearAll()"/>
				<input type="button" name="CmdExit1" value="EXIT" id="CmdExit1" onclick="self.close()"/>
				</center></td>			
			</tr>		
	</table>
	<table id="mytable" cellspacing="3" cellpadding="2" border="1" width="75%" align="center">
		<tr class="tdH">
			<td colspan="3">Existing Details</td>
			<td colspan="2">
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
			<th>Bill Major Type Code</th>			
			<th>Bill Major Type Description</th>
			<th>Remarks</th>
			<th>Status</th>			                     
 		</tr>
		<tbody id="tblList" class="table">
			<!-- 
          	-->
		</tbody>
 		<tr>
         	<td colspan="5" class="tdH">
         	<center>Pages          
          	<select name="cmbpage" id="cmbpage" onchange="changepage()" ></select></center>
        	</td>
     	</tr>
	</table>
</form>
</body>
</html>