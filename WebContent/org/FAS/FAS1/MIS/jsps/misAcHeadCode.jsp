<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Works Expenditure System</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   <script type="text/javascript" src="../scripts/misAcHeadCode.js"></script>   
  </head>
  <body onload="callServer('mainCat')">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Works Expenditure </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="misAcHeadCodeCategory" id="misAcHeadCodeCategory" method="POST">  
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="table">
                <td>
                  <div align="left">Main Category Code</div>
                </td>
                <td>
                  <select name="categoryCode" id="categoryCode" onchange="callServer('subCat')">
                  	<option value="select">Select</option>
                  </select>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">Sub Category Code</div>
                </td>
                <td>
                	<select name="subCategoryCode" id="subCategoryCode">
                  		<option value="select">Select</option>
                  	</select>                                                       
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Fund or Expenditure</div>
                </td>
                <td>
                	<select name="fundExpend" id="fundExpend">
                  		<option value="select">Select</option>
                  		<option value="f">Fund</option>
                  		<option value="e">Expenditure</option>
                  	</select>
                  	<input name="go" id="go" value="GO" type="button" onclick="callServer('Get')">                                                     
                  	<input name="clear" id="clear" value="Clear" type="button" onclick="clearAll();">                  	
                </td>                
              </tr> 
              <tr class="table">
                <td>
                  <div align="left">Account Head Code</div>
                </td>
                <td>
                	<select name="txtaccountheadcode" id="txtaccountheadcode" disabled="disabled">
                  		<option value="select">Select</option>                  		
                  	</select>  
                  	<input type="hidden" id="txtaccountheadname" name="txtaccountheadname">
                  	<input name="go" id="go" value="GO" type="button" onclick="callServer('Get')" style="visibility: hidden;">                                                     
                  	<input name="clear" id="clear" value="Clear" type="button" style="visibility: hidden;">
                </td>                
              </tr>               
       </table>
       <div id="gridDiv" style="visibility:hidden; width: 100%;">              
	   <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%" align="center">
		<tr class="tdH">
			<td colspan="2">Existing Details</td>
			<td colspan="1">
            	Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" id="cmbpagination" onchange="changepagesize()">
                  <option value="5" >5</option>
                  <option value="10" selected="selected">10</option>
                  <option value="15">15</option>
                  <option value="20">20</option>
                </select>
          	</td>
		</tr>
		<tr>
			<th>Select</th>			
			<th>Account Head Code</th>
			<th>Account Head Description</th>							                     
 		</tr>
		<tbody id="tblList" class="table">
			<!-- 
          	-->
		</tbody>
 		<tr>
         	<td colspan="3" class="tdH">
         	<center>Pages          
          	<select name="cmbpage" id="cmbpage" onchange="changepage()" ></select></center>
        	</td>
     	</tr>
	</table>
	<table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="callServer('Add')"/>
                 &nbsp;&nbsp;&nbsp;
                 <input type="button" name="update" id="update" value="UPDATE" disabled="disabled" onclick="callServer('Update')"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL" disabled="disabled" onclick="callServer('Delete')"/>
                 &nbsp;&nbsp;&nbsp;
                 <input type="button" name="list" id="list" value="LIST" onclick="accountList();"/>
                 &nbsp;&nbsp;&nbsp;                  
                <input type="button" name="butCan" id="butCan" value="EXIT" onclick="Exit();"/>
              </div>
            </td>
          </tr>
        </table>
	</div> 
    </form></body>
</html>