<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Download Trial Balance Data</title>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/TB_Not_Generated_Offices.js"></script>
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
       
        document.frmTB_Not_Generated_Offices.txtCB_Year.value=year
        document.frmTB_Not_Generated_Offices.txtCB_Month.value=month;
        
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
    
    <style type="text/css">
    UL{
        list-style-type: none;
    }
    </style>
    
    <script type="text/javascript" src="../../../../../../org/FAS/FAS1/CommonControls/scripts/Tree_Menu_Generation.js"></script>
  
    
    
  </head>
  <body class="table" onload="loadyear_month();loadAll()"><form name="frmTB_Not_Generated_Offices"
                                                      method="POST"
                                                      action="../../../../../../Trial_Balance_ReportServ.kv"
                                                      onsubmit="return checknull()">
                                                      
                                                      
                                                      
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Download Trial Balance Data </strong>
            </div>
          </td>
        </tr>
      </table>
      
      
  <table id="tlb" border="0" cellpadding="1">    
  <tbody id="dtable">
   
    <tr>    
      <td>
       <ul>
        <li>
          &nbsp; <input type="checkbox" name="ALL_UNITS" id="ALL_UNITS" value="ALL_UNITS" > All Units (including Head Office)
        </li>
       </ul>       
       <ul>
        <li>
          <img id="HO_img" src="../../../../../../images/plus1.GIF" onclick="showList(this)"/>
          <input type="checkbox" name="H0_ONLY" id="HO_ONLY" value="HO_ONLY" onclick="checkFirstLevel(this)">
          Head Offices Only   
        </li>
        <li id="TD_HO" style="display:none">
        </li>
      </ul>
      </td>
      </tr>
      
      <tr class="tdH">
      <td colspan="2">   
          <strong>Selection of Office Jurisdiction </strong>
      </td>
      </tr>
      <tr>
      <td>
       <ul>
        <li>
        <input CHECKED=true type="radio" name="Sel_Opt" id="Sel_Opt_Region" value="REGION" /> Region
        <input type="radio" name="Sel_Opt" id="Sel_Opt_Circle" value="CIRCLE" /> Circle
        <input type="radio" name="Sel_Opt" id="Sel_Opt_Unit" value="UNITS" /> Units
        </li>
       </ul> 
      </td>          
     </tr>  
     
     <tr>
      <td>
       <ul>
         <li>
           <img id="DrillDown_img" src="../../../../../../images/plus1.GIF" onclick="showList(this)"/>
           <input type="checkbox" name="All_Units_Except_HO" id="All_Units_Except_HO" value="All_Units_Except_HO" onclick="checkFirstLevel(this)"> 
           Units - Drill down
         </li>
         <li id="TD_DrillDown" style="display:none">
         </li>
       </ul>   
      </td>       
    </tr>    
  </tbody>
  
  </table>  
  
      
      
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="0" width="100%">
          <tr align="left">
            <td class="table" width="40%">
              <div align="left">Cash Book Year &amp; Month</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtCB_Year" id="txtCB_Year"
                       tabindex="3" maxlength="4" size="5"
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
        </table>
      </div>
      
      
      
      
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">
        <tr class="tdH">
          <td>
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