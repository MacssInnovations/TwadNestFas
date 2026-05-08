<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Journal Data Download</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Journal_download.js"></script>
    
   
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.Jrnldownload.from_txtCB_Year.value=year;
        document.Jrnldownload.from_txtCB_Month.value=month;
        
       
        
         }
     function checknull()
     {
    	 if(document.Jrnldownload.from_txtCB_Year.value=="")
    		 {
    		 alert("Enter Year");
    		 return false;
    		 }
    	 else if(document.Jrnldownload.txtUnitId.value=="")
    		 {
    		 alert("Select Unit Id");
    		 return false;
    		 }
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
  <body class="table" onload="loadyear_month();setTimeout('forFundTransferBank();',900);" >
  <form name="Jrnldownload" method="POST" action="../../../../../Jrnl_download" onsubmit="return checknull()">
    <input type="hidden" value="FundTransferBank" id="jrnl" name="jrnl">
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>Fund Transfer By Bank Data Download</strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
             
              
                <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
          <td>
          <input type="text" name="from_txtCB_Year" id="from_txtCB_Year"
           tabindex="3"  maxlength="4" size="5" onchange="testlen();" onkeypress="return numbersonly(event)" onblur="forFundTransferBank();">
         
          <select name="from_txtCB_Month"  id="from_txtCB_Month" tabindex="4" onchange=" forFundTransferBank(); ">          
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
          </td>
        </tr>
         <tr class="table">
                    <td>
                      <div align="left">Accounting Unit Id</div>
                    </td>
                    <td>
                      <div align="left">
                         <select name="txtUnitId" id="txtUnitId"  >
                            <option value=0>All Units</option>
                         </select>                         
                      </div>                  
                    </td>
              </tr>
        
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type=submit value="Download" >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>