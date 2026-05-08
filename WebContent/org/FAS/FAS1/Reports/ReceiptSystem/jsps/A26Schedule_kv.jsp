<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>A26 Schedule</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Account_Head_Check.js"></script>          
    
    <script type="text/javascript" src="../../../../../../org/FAS/FAS1/CommonControls/scripts/AccountHead_PopUp.js"></script>                 
          
    <script language="javascript" type="text/javascript"
            src="../scripts/A26ScheduleReport_kv.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
            
            
    <script type="text/javascript" 
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Com_Schedule_Account_Heads.js"> </script>                
                
                
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmA26Report.txtCB_Year.value=year
        document.frmA26Report.txtCB_Month.value=month;
        
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
  <body class="table" onload="loadyear_month();Schedule_Account_Heads('A26');LoadAccountingUnitID('FOR_LIST_1')">
  
  <form action="../../../../../../A26ScheduleReport_kv.kv"
           name="frmA26Report" id="frmA26Report" method="post" onsubmit="return checknull()">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>TWAD A-26 Schedule </center>
          </td>
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
                      tabindex="1" onchange="common_LoadOffice(this.value);">
               
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
              <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                      tabindex="2">
               
              </select>
            </div>
          </td>
        </tr>
        <tr align="left">
          <td class="table">
            <div align="left">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
                <option value="1">January</option>
                <option value="2">February</option>
                <option value="3">March</option>
                <option value="4">April</option>
                <option value="5">May</option>
                <option value="6">June</option>
                <option value="7">July</option>
                <option value="8">August</option>
                <option value="9">September</option>
                <option value="10">October</option>
                <option value="11">November</option>
                <option value="12">December</option>
              </select>
            </div>
          </td>
        </tr>
        
        
        <tr align="left">
          <td class="table">
            <div align="left">Report Type </div>
          </td>
          <td>
            <div align="left">
            
                <input type="radio" name="reporttype" id="reporttype" onclick="callSupp();" value="1" checked /> Regular &nbsp;
                <input type="radio" name="reporttype" id="reporttype" onclick="callSupp();" value="2"/> Regular( Account Head Code on New Page ) &nbsp;
                <input type="radio" name="reporttype" id="reporttype" onclick="callSupp();" value="3"/> Supplement 
                   
            </div>
          </td>
        </tr>    
        
        <tr align="left">
     
        <td class="table">
          <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">
            Supplement Number 
            <font color="#ff2121">*</font>
          </div>
        </td>
        <td>
          <div id="dispsupno2" name="dispsupno2" style="display:none">
            <input type="text" name="supno" id="supno" value=0 size=2 >     
          </div>
          
        </td>
      </tr>   
                
        <tr align="left">
          <td class="table">
            <div align="left">Account Head Code </div>
          </td>
          <td>
            <div align="left">
            
                   <select name="txtAcc_HeadCode" id="txtAcc_HeadCode" >
                     <option value="">  --- Select the Account Head Code --- </option> 
                     
                   </select>          
         
            </div>
          </td>
        </tr>    
        
        
        
        
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>               
         
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>