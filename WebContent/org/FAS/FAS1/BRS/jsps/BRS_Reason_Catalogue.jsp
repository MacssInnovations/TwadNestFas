<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Reason Catalogue Maintenance System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <link href='css/Fas_Account.css' rel='stylesheet' media='screen'/>
      
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script> 
    
    
    
    <script type="text/javascript" src="../scripts/BRS_Reason_Catalogue.js"></script>        
    
 </head>
 
  <body >
  
  
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Reason Catalogue Maintenance System</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmBill_Receipt_Register" id="frmBill_Receipt_Register" action="#">                 
                 
                  
             <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
               <tr class="table">
                <td>
                  <div align="left">
                     Reason Code 
                  </div>
                </td>
                <td>
                  <div align="left">                      
                      <input type="hidden" name="txtReasonCode"  tabindex="7" id="txtReasonCode" size="18" readonly /> System Generated                  
                  </div>
                </td>
              </tr>
          
              <tr class="table">
                <td>
                  <div align="left">
                    Reason Short Description 
                  </div>
                </td>
                <td>
                  <div align="left">
                      <input type="text" name="txtReasonShortDesc"  tabindex="7" id="txtReasonShortDesc" size="18"/>                   
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                     Reason Description
                  </div>
                </td>
                <td>
                  <div align="left">
                     <textarea name="txtReasonDesc" id="txtReasonDesc" cols="50" tabindex="10" onkeypress="return check_leng(this.value);"  rows="3"></textarea>
                  </div>
                </td>
              </tr>
              
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction_brr('Add','null')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction_brr('Update','null')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction_brr('Delete','null')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListHeads()" tabindex="60"/>
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