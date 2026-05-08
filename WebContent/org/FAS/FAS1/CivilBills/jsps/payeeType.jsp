<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <title>PayeeType</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
     
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>  
    <script type="text/javascript" src="../scripts/payeeType.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
  </head>
  
  <body onload="getpayee()" bgcolor="rgb(255,255,225)">
        <table cellspacing="1" cellpadding="3" width="100%" >
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <font size="4">Payee Type Master</font>
              </div>
            </td>
          </tr>
        </table>
        
        <form name="formPayeeType_Master" method="" action="">
          <div align="left">
             <table cellspacing="1" cellpadding="2" border="1" width="100%">
                <tr class="table">
                    <td>
                      <div align="left">
                        Payee Type Code 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                <input type="text" name="txtPayeeypeCode" id="txtPayeeypeCode" maxlength="5" size="5" readonly="readonly"/>(Auto Generated)
                      </div>
                    </td>
                  </tr>
                <tr class="table">
                <td>
                  <div align="left">
                     Payee Type Code Description
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtPayeetypeDesc"  id="txtPayeetypeDesc"  maxlength="30" size="31" />
                  </div>
                </td>
              </tr>
            </table>
        </div>
        <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                 <input type="button" name="butAdd" id="butAdd" value="ADD" onclick="callpayeeAdd();"/> 
                    &nbsp;
                 <input type="button" name="butUpdate" id="butUpdate" value="UPDATE" disabled="disabled" onclick="callpayeeUpdate();"/>
                    &nbsp;
                 <input type="button" name="butDelete" id="butDelete" value="CANCEL" disabled="disabled" onclick="callpayeeDelete();"/><!--
                    &nbsp;
                 <input type="button" name="butList" id="butList" value="LIST" onclick="callpayeeList();"/>
                    &nbsp;      
                 --><input type="button" name="butClear" value="CLEARALL" id="butClear" onclick="clearAll();" /> 
                    &nbsp;
                    <input type="button" name="butExit" id="butExit" value="EXIT" onclick="self.close();"/>
              </div>
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
                     <th>Payee Type Code</th>
                     <th>Payee Description</th>
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
        
        
      </div>
    </form>
  </body>
</html>