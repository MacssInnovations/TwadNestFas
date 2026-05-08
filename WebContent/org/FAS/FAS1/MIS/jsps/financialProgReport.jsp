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
   <script type="text/javascript" src="../scripts/financialProgReport.js"></script>   
  </head>
  <body onload="callServer('Get')">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Works Expenditure </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="fundProgress" id="fundProgress" method="POST">  
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="table">
                <td>
                  <div align="left">Fund Type</div>
                </td>
                <td>
                  <select name="fundType" id="fundType">
                  	<option value="select">Select</option>
                  	<option value="u">Urban</option>
                  	<option value="r">Rural</option>
                  </select>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">Fund Progress Code(System Generate)</div>
                </td>
                <td>
                  <input type="text" name="fundCode" id="fundCode" disabled="disabled">
                                     
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">
                    Fund Progress Description
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="fundDesc" id="fundDesc" size="50%;"/>
                  </div>
                </td>
              </tr>               
            </table>
            <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="callServer('Add')"/>
                 &nbsp;&nbsp;&nbsp;
                 <input type="button" name="update" id="update" value="UPDATE" disabled="disabled" onclick="callServer('update')"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL" disabled="disabled" onclick="callServer('delete')"/>
                 &nbsp;&nbsp;&nbsp;                  
                <input type="button" name="butCan" id="butCan" value="EXIT" onclick="Exit();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>       
	   <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%" align="center">
		<tr class="tdH">
			<td colspan="4">Existing Details</td>
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
			<th>Fund Type</th>		
			<th>Fund Progress Code</th>			
			<th>Fund Progress Description</th>
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
      
      
      
    </form></body>
</html>