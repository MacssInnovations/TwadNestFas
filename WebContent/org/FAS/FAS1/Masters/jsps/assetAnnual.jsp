<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Accounting Unit Master</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/assetAnnual.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    <script type="text/javascript"
        src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js">
    </script>        
    <script language="javascript" type="text/javascript">
				function closeWindow(){                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');callServer('get');">
  <form name="fromAssetAnnual" id="fromAssetAnnual" method="post">
  <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="3" class="tdH" align="center"><b>Annual Updation of Assets Value - to be updated by value A/C Rendering unit</b></td>
       </tr>
         <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                 
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="LoadOffice(this.value);">
                
                </select>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Accounting For Office Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">                
                </select>
              </div>
            </td>
          </tr>      
       	  <tr>
            <td class="table">Financial Year</td>
            <td class="table">
            	<input type="text" name="financialYear" id="financialYear" maxlength="9">            
            </td>
       	</tr>
       	<tr>
            <td class="table">Asset Code</td>
            <td class="table">
            	<select name="assetCode" id="assetCode" style="width: 20%;">
            		<option value="select">Select</option>
            	</select>            
            </td>
       </tr>
       	<tr>
            <td class="table">Useful pending life cycle</td>
            <td class="table">
	           <input type="text" name="year" id="year" size="7" onkeypress="return numbersonly(event,this)">Year
	           <input type="text" name="month" id="month" size="7" onkeypress="return numbersonly(event,this)">Month 
	           <input type="text" name="day" id="day" size="7" onkeypress="return numbersonly(event,this)">Day
            </td>
       </tr>      
       <tr class="table">
            <td class="table"><div id="trDtTo1">Fair Market value of the Asset</div></td>
            <td class="table">
            <div id="trDtTo2">
            	<input type="text" name="fairMarket" id="fairMarket" onkeypress="return numbersonly(event,this)">            
             </div>
            </td>
       </tr>      
       <tr class="table">
            <td class="table"><div id="trDtTo1">Remarks</div></td>
            <td class="table">
            <div id="trDtTo2">
            	<textarea name="remarks" id="remarks" rows="2" cols="20"></textarea>
            </div>
            </td>
       </tr>
       <tr>
       		<td colspan="4" class="table">
       		<center>
       			<input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add')"/>
       			<input type="button" name="CmdUpdate" value="UPDATE" id="CmdUpdate" onclick="callServer('Update')" disabled/>
       			<input type="button" name="CmdDelete" value="CANCEL" id="CmdDelete" onclick="callServer('Delete')" disabled/>
       			<input type="button" name="CmdClear" value="CLEAR" id="CmdClear" onclick="clear()"/>
       			<input type="button" name="CmdClose" value="EXIT" id="CmdList" onclick="closeWindow()">
       		</center>
       		</td>
       </tr>       
    </table>    
   <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%" align="center">
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
			<th>Asset Code</th>			
			<th>Fair Markets Value</th>
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