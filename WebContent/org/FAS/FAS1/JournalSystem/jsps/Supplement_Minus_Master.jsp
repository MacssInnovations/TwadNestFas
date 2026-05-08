<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Supplement TB Master</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <link href='css/Fas_Account.css' rel='stylesheet' media='screen'/>
      
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <!--
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    -->
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/CalendarControl.js"></script> 
    
        
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script> 
    <script type="text/javascript" src="../scripts/Supplement_Minus_Master.js"></script>
    
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
    </script>
    
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmSupplement_Minus.txtCashbook_Year.value=year
        
        
         }
    </script>
    
 </head>
 
  <body  bgcolor="rgb(255,255,225)"  onload="loadyear_month()" >
  
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Supplement Minus Master </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmSupplement_Minus" id="frmSupplement_Minus" action="#">
                 
                 
    
             <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                   Cash Book Year 
                   </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="txtCashbook_Year" id="txtCashbook_Year" value=""  />                    
                  </div>
                </td>
              </tr>



              <tr class="table">
                <td>
                  <div align="left">
                     Cash Book Month
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCashbook_Month" id="txtCashbook_Month" value="3" readonly />                    
                    
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">
              Gjv Minus
            </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtStatus_GJV" id="txtStatus_GJV" value="Y" checked /> YES 
                      <input type="radio" name="txtStatus_GJV" id="txtStatus_GJV" value="N" />NO
                  </div>
                </td>
              </tr>  
              
              
              
             <tr class="table">
                <td>
                  <div align="left">
              Sjv Minus
            </div>
                </td>
                <td>
                  <div align="left">
                      <input type="radio" name="txtStatus_SJV" id="txtStatus_SJV" value="Y" checked /> YES 
                      <input type="radio" name="txtStatus_SJV" id="txtStatus_SJV" value="N" />NO
                  </div>
                </td>
              </tr>
              
              
              
             <tr class="table">
                <td>
                  <div align="left">Remarks </div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="10" onkeypress="return check_leng(this.value);"
                              rows="2"></textarea>
                  </div>
                </td>
              </tr>
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction_Supplement_TB('Add','null')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="Update" id="cmdFreeze" style="display:none" onclick="doFunction_Supplement_TB('update','null')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="Delete" id="cmdUnfreeze" style="display:none" onclick="doFunction_Supplement_TB('delete','null')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="List()" tabindex="60"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()" tabindex="70"/>
                     </td>
                 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
        
      
    </form></body>
</html>