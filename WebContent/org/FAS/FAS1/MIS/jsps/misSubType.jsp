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
   <script type="text/javascript" src="../scripts/misSubType.js"></script>   
  </head>
  <body onload="callServer('Get'),callServer('mainCat')">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Works Expenditure </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="misSubType" id="misSubType" method="POST">  
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="table">
                <td>
                  <div align="left">Main Category Code</div>
                </td>
                <td>
                  <select name="categoryCode" id="categoryCode" onchange="callServer('mainType'),callServer('Get');">
                  	<option value="select">Select</option>
                  </select>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Main Type Code</div>
                </td>
                <td>
                  <select name="mainTypeCode" id="mainTypeCode" style="width: 25%;" onchange="callServer('Get');">
                  	<option value="select">Select</option>
                  </select>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">Sub Type Code</div>
                </td>
                <td>
                	<input type="text" id="subTypeCode" name="subTypeCode" disabled="disabled">                                                       
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Sub Type Description</div>
                </td>
                <td>
                	<input type="text" id="subTypeDesc" name="subTypeDesc">                                                       
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
                <input type="button" name="butCan" id="butCan" value="EXIT" onclick="Exit();"/>
              </div>
            </td>
          </tr>
        </table>
	   <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%" align="center">
		<tr class="tdH">
			<td colspan="6">Existing Details</td>
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
			<th>Major Category Description</th>			
			<th>Main Type Code</th>
			<th>Main Type Description</th>			
			<th>Sub Type Code</th>
			<th>Sub Type Description</th>			
			<th>Status</th>								                     
 		</tr>
		<tbody id="tblList" class="table">
			<!-- 
          	-->
		</tbody>
 		<tr>
         	<td colspan="7" class="tdH">
         	<center>Pages          
          	<select name="cmbpage" id="cmbpage" onchange="changepage()" ></select></center>
        	</td>
     	</tr>
	</table>
    </form>
 </body>
</html>