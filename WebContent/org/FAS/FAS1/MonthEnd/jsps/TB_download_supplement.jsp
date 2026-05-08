<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Suppleement Trial Balance Download</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Sup_TB_download.js"></script>
    
   
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
       
        document.suptbdownload.from_txtCB_Year.value=year;
        document.suptbdownload.from_txtCB_Month.value=month;
        
       
        
         }
     function checknull()
     {
    	 if(document.suptbdownload.from_txtCB_Year.value=="")
    		 {
    		 alert("Enter Year");
    		 return false;
    		 }
    	 else if(document.suptbdownload.txtUnitId.value=="")
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
  <body class="table" onload="loadyear_month();setTimeout('loadTransferUnit()',900);setTimeout('Suppl_Number_CheckNew()',700);" >
  <form name="suptbdownload" method="POST" action="../../../../../Sup_TB_download" onsubmit="return checknull()">
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>Supplement Trial Balance Download </strong>
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
           tabindex="3"  maxlength="4" size="5" onchange="testlen();loadTransferUnit();" onkeypress="return numbersonly(event)">
         
          <select name="from_txtCB_Month"  id="from_txtCB_Month" tabindex="4" onchange="loadTransferUnit();">          
          
          <option value="3">March</option>
          
          </select>
          </td>
        </tr>
         <tr class="table">
                    <td>
                      <div align="left">Accounting Unit Id</div>
                    </td>
                    <td>
                      <div align="left">
                         <select name="txtUnitId" id="txtUnitId"  onchange="Suppl_Number_CheckNew();">
                            <option value="">Select Unit</option>
                         </select>                         
                      </div>                  
                    </td>
              </tr>
               <tr align="left">
            <td class="table">
              <div align="left">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div>
                <select name="txtsupplement_no" id="txtsupplement_no" >
                   <option value="" >-- Select Suppl No. -- </option> 
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