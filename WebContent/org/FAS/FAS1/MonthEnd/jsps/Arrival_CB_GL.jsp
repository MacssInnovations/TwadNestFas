<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Arrival CB For GL</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/ViewTrialBalance.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
               <script type="text/javascript" src="../scripts/Arrival_CB_GL_JS.js"></script>
    <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>            
    <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmTrialBalance.txtCB_Year.value="2011";
        document.frmTrialBalance.txtCB_Month.value=month;
       // setTimeout('checkAvailability();',900);             
         }
    </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table" onload="LoadAccountingUnitID('FOR_LIST_0');loadyear_month();">
  <form name="frmTrialBalance"                              method="POST"
                                                      action="../../../../../Arrival_CB_GL.kv"
                                                      onsubmit="return checknull()">
     
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Arrival of Closing Balance For GL</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
          <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">                 
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"  tabindex="1">
                
     
                </select>
              </div>
            </td>
          </tr>
          <tr align="left">
            <td class="table">
              <div align="left">Cash Book Year &amp; Month</div>
            </td>
            <td>
              <input type="text" name="txtCB_Year" id="txtCB_Year" readonly tabindex="3"
                     maxlength="4" size="5" 
                     onkeypress="return numbersonly(event)" ></input>
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
                <option value="3">March</option>                
              </select>
               <input type="button" name="gobtn" id="gobtn" value="Go" onclick="checkAvailability();"/>
            </td>
          </tr>
        </table>
      </div>
      <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      <tr class="tdH">
                        <th> A/C Head </th>  
                        <th> A/C Head Description </th>  
                                
                        <th> Month Closing Balance </th>
                       <th> Month CB Indicator </th>
                      </tr>
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
           <div align="center" id="blockSubmit" style="display:none">
              <input type="submit" id="submit" value="Submit" disabled="disabled"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
            <div align="center" id="secExit">
             <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
