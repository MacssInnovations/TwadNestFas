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
    <title>Section Report for SJV</title>
    
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
          
    <!--<script language="javascript" type="text/javascript" src="../scripts/GPFReport_VoucherWise.js"></script>
        -->
        <script language="javascript" type="text/javascript" src="../scripts/GPFReport_VoucherWise_SJV.js"></script>
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
          
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>          
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
  
           
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         
        document.GPFVocherwise.txtCB_Year.value=year;
        document.GPFVocherwise.txtCB_Month.value=month;                
        
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
  <body class="table" onload="loadyear_month();callServer_LoadSection()">
  <form action="" 
        id="GPFVocherwise" 
        name="GPFVocherwise"
        method="post"
        onsubmit="return checknull()">
        
        
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>Section Report ( Voucher Wise ) </center>
          </td>
        </tr>
        
           <tr align="left">
          <td class="table">
            <div align="left">Account Head Code</div>
          </td>
          <td>
            <div align="left">               
              <select name="txtAcc_HeadCode" id="txtAcc_HeadCode" tabindex="4">               
              </select>
            </div>
          </td>
        </tr>

        
         <tr align="left">
          <td class="table">
            <div align="left">Sorting Order</div>
          </td>
          <td>
            <div align="left">               
              <select name="txtSortingOrder" id="txtSortingOrder" tabindex="4">
                <option value="11">Debit Amount wise </option>
                <option value="22">Credit Amount wise</option>
                <option value="33">Unit wise,Date wise</option>
                <option value="44">Date wise,Debit Amount wise</option>
                <option value="55">Date wise,Credit Amount wise </option>
                <option value="66">Date wise, Net Amount wise </option>
              </select>
            </div>            
          </td>
        </tr> 
        <tr align="left">
          <td class="table">
            <div align="left">Supplement No</div>
          </td>
          <td>
             <input type="text" id="supno" name="supno" size="2"/>
             
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
               
                <option value="3">March</option>
               
              </select>
              
              <input type="button" id="go" name="go" value="Go" onclick="FormSubmit('one')"/>
              
              
            </div>
          </td>
        </tr>
        
                    
                
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>