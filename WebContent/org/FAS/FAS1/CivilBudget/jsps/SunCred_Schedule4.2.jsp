<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Schedule 4.2(Sundry Creditors) Report</title>
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
           
     document.frmSchedule1_1.txtCB_Year_From.value=year;
   document.frmSchedule1_1.txtCB_Year_To.value=year;
   
 }
   
  
   
   </script>
    
    </head>
  <body class="table" onload="LoadAccountingUnitID('FOR_LIST_1');loadyear_month();" >
  <form name="frmSchedule1_1" method="POST" action="../../../../../Schedule_Reports?command=Schedule4.2(sunCred)" onsubmit="return checknull()">
   <input type="hidden" id="hid" name="hid" />
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>Schedule 4.2 (Sundry Creditors) - Report</strong>
          </div>
        </td>
      </tr>
    </table>
   
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
      
            
       
              <tr align="left">
           			<td class="table">
              			<div align="left">
                 	From Year <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td>        				       
		        <input type="text" id="txtCB_Year_From" name="txtCB_Year_From" />
		        
      
          </td>
        </tr>
                    <tr align="left">
           			<td class="table">
              			<div align="left">
                 	To Year <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td>        				       
		        
		          <input type="text" id="txtCB_Year_To" name="txtCB_Year_To" />
          	
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
         <tr class="tdH">
      <td colspan="2">
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