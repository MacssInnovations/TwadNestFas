<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Journal Supplement Data Download</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Journal_download_supplement.js"></script>
    
       <script type="text/javascript"
           src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Supplement_Number_Check_4Rpt.js"></script>
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
       
        document.JrnlSupdownload.txtCB_Year.value=year;
        //.document.JrnlSupdownload.txtCB_Month.value=month;
        
       
        
         }
     function checknull()
     {
    	 if(document.JrnlSupdownload.txtCB_Year.value=="")
    		 {
    		 alert("Enter Year");
    		 return false;
    		 }
    	 else if(document.JrnlSupdownload.txtUnitId.value=="")
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
  <body class="table" onload="loadyear_month();setTimeout('loadTransferUnit()',900);" >
  <form name="JrnlSupdownload" method="POST" action="../../../../../Jrnl_download_sup" onsubmit="return checknull()">
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>Journal Supplement Data Download</strong>
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
          <input type="text" name="txtCB_Year" id="txtCB_Year"
           tabindex="3"  maxlength="4" size="5" onchange="testlen();loadTransferUnit();" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="loadTransferUnit();">          
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
                            <option value=0>All Units</option>
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