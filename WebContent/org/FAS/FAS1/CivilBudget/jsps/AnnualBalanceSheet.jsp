<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>AnnualBalanceSheetReport</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/RegisterChequeReport.js"></script>
  	<link href="../../../../../css/Sample3.css" rel="stylesheet"    media="screen"/>
     	<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />   
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
   <script type="text/javascript">
   function loadyear_month()
   {
  
    var today= new Date(); 
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var year=today.getYear();
    if(year < 1900) year += 1900;
           
   /** Load From Cash Book Month and From Cash Book Year during Form Load */
   document.frmAnnualReport.txtCB_Year_from.value=year;
   document.frmAnnualReport.txtCB_Month_from.value=month;
   
   
   /** Load To Cash Book Month and To Cash Book Year during Form Load */
   document.frmAnnualReport.txtCB_Year_to.value=year;
   document.frmAnnualReport.txtCB_Month_to.value=month;
   
   document.frmAnnualReport.txtCB_Year.value=year;
   document.frmAnnualReport.txtCB_Month.value=month;
   
 }
   
   function radiochk()
   {
	
	document.getElementById("rdobtn").style.display="none";	
	if(document.getElementById('balance_Sheet_Annual').checked){
		document.getElementById("Annual_div").style.display="inline";
		document.getElementById("backBtn").style.display="inline";
		document.getElementById("hid").value="Annual_div";
	}if(document.getElementById('balance_Sheet_month').checked){
		document.getElementById("Month_div").style.display="inline";
		document.getElementById("backBtn").style.display="inline";
		document.getElementById("hid").value="Month_div";
	}
	
   }
   
   </script>
    
    </head>
  <body class="table" onload="LoadAccountingUnitID('FOR_LIST_1');loadyear_month();" >
  <form name="frmAnnualReport" method="POST" action="../../../../../AnnualBalanceSheet" onsubmit="return checknull()">
   <input type="hidden" id="hid" name="hid" />
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>Account Balance Sheet Report</strong>
          </div>
        </td>
      </tr>
    </table>
   
    

    
  
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
      
            
       
               <tr align="left">
           			<td class="table">
              			<div align="left">
                 			Cash Book Year &amp; Month <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td>        				       
		          <div id="more">
          		From   
          		<input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          		<select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" >
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
          	To  
          	<input type="text" name="txtCB_Year_to" id="txtCB_Year_to" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          	<select name="txtCB_Month_to"  id="txtCB_Month_to" tabindex="4" onchange="callSJVload();">
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
           <tr  align="left">
            <td>
                Report Option:
            </td>
            <td>
                <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                <input type=radio name=txtoption id=txtoption value="HTML">HTML
            </td>            
        </tr>
        
          
    
  
        
        
</table>
        
  
    <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
      
      <tr class="tdH">
      <td>
          <div align="center">
         <input type=submit value=Submit >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="window.close();">
      </div>
      </td>
      </tr>
      
      </table>
      
      </form>
  </body>
</html>