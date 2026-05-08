<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
<meta http-equiv="cache-control" content="no-cache">
    <title> Journal Integration Heads  </title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
   
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
 
    
    <script type="text/javascript" src="../scripts/Journal_Integration_Heads.js"></script>
   
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <!--<script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>-->
   
    <script language="javascript" type="text/javascript" src="../scripts/ScheduleMaster.js"></script>
    <script language="javascript" type="text/javascript">
                function foc()
                {
                }
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
</head>
<body onload="loadData();" bgcolor="rgb(255,255,225)">
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Journal Integration Head </font>
          </div>
        </td>
      </tr>
    </table>
<form action="" name="frmjournal_Integration" id="frmjournal_Integration">
<input type="hidden" id="row_hid" name="row_hid" value="">
<div align="center">
  <table cellspacing="2" cellpadding="3" border="1" width="100%" >
  <tr class="table">
          <td><div align="left">
                   Module Id
                    <font color="#ff2121">*</font>
                  </div>
          </td>
           <td> <div align="left">
          <select id="moduleid" name="moduleid" onchange="loadcolumn();">
          <option value="">--select--</option>
          <option value="PEN">PEN</option>
          <option value="GPF">GPF</option>
          </select></div>
          </td>
   </tr>
   <tr class="table">
          <td><div align="left">
                   Column to be Journalised
                    <font color="#ff2121">*</font>
                  </div>
          </td>
          <td>
               <select id="cmbjournalised" name="cmbjournalised">
               <option value="">--Select--</option>
               </select>
          </td>
          
   </tr>
   <tr class="table">
          <td><div align="left">
                   Account Head Code
                    <font color="#ff2121">*</font>
                  </div>
          </td>
          <td>
                  <div align="left">
                   <input type=text id="txtaccountheadcode" name=txtaccountheadcode size=8 maxlength=8 onchange="doFunction1('checkCode','null')" onkeypress="return numbersonly1(event,this)">
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtaccountheadname" readonly="readonly" 
                           id="txtaccountheadname" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>
                </td>
   </tr>
   <tr class="table">
                <td><div align="left">
                   Date WEF
                    <font color="#ff2121">*</font>
                  </div>
               </td>
        
               <td>
                  <div align="left">
                   <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"   
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl_1(document.frmjournal_Integration.txtCrea_date);"
                         alt="Show Calendar"></img> 
                    
                  </div>
                </td>
          
   </tr>
   <tr class="table">
              <td><div align="left">
                  DR/CR Indicator
                    <font color="#ff2121">*</font>
                  </div>
               </td>
               <td>
                  <div align="left">
                   <input type="radio" id="check_cr_dr" name="check_cr_dr">Credit
                   <input type="radio" id="check_cr_dr" name="check_cr_dr">Debit
                  </div>
                </td>
   </tr>
    <tr class="table">
           <td><div align="left">
                 Type
                    <font color="#ff2121">*</font>
                  </div>
               </td>
               <td> <div align="left">
          <select id="typepf" name="typepf">
          <option value="">--select--</option>
          <option value="P">P</option>
          <option value="F">F</option>
          </select></div>
          </td>
    </tr>
    <tr class="table">
                <td><div align="left">
                   Order Number
                    <font color="#ff2121">*</font>
                  </div>
               </td>
               <td> <div align="left">
               <input type="text" id="txtorerno" name="txtorerno">
               </div></td>
               </tr>
  </table>
  
  </div>
  <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="button" name="butSub3" id="butSub3" value="ADD" onclick="doinsert();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan1" id="butCan1" value="UPDATE" disabled="disabled"
                       onclick="updatedata();"/>
                <input  type="button" name="butCan2" id="butCan2" value="DELETE" disabled="disabled"
                       onclick="deletedata();"/>       
               <input type="button" name="butCan" id="butCan" value="CLEAR"
                       onclick="clearAll();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="window.close();"/>
              </div>
            </td>
          </tr>
        </table>
        

<div id=div2 style="overflow: scroll; absolute; height:400px; width:94% align:center ">
<table id="Existing" cellspacing="2" cellpadding="3" border="1"	width="100%" align="center" class="table">
	<tr class="tdH">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH">
		<th width="6%">Select</th>
		<th width="6%">Module Id</th>
		<th width="10%">Column to be Journalised</th>		
		<th width="17%">Account Head Code</th>	
		<th width="10%">Date WEF</th>
		<th width="6%">DR/CR Indicator</th>
		<th width="6%">Type</th>
		<th width="6%">Order Number</th>		
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</div>
</form>
</body>
</html>