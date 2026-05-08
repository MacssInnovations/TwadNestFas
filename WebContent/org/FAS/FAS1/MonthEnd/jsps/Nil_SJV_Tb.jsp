<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
   <head>
   
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
      <meta http-equiv="cache-control" content="no-cache"></meta>
      <title> List of Units with Nil Supplement TB  </title>
      <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
            media="screen"/>
      <link href="../../../../../css/Sample3.css" rel="stylesheet"
            media="screen"/>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript"
              src="../../../../../../org/Library/scripts/checkDate.js"></script>
              
      <script language="javascript" type="text/javascript"
              src="../scripts/Nil_SJV_Tb.js.js"></script>
              
              
      <script type="text/javascript"
              src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
              
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Supplement_Number_Check.js"></script>

       
      <script type="text/javascript" language="javascript">
      function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmTrialBalance.txtCB_Year.value=year
        document.frmTrialBalance.txtCB_Month.value=month;
        
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
   
   <body class="table" onload="loadyear_month();Suppl_Number_Check(); ">
   
   
   <form id="frmTrialBalance"
                                                       name="frmTrialBalance"
                                                       method="POST" action="../../../../../Nil_SJV_Tb.kv"  > 
                                                       
                                                       
  <!--  
   onsubmit="return confirmation()" >                                                       
  -->  
                                                      
                                                      
 
         <table cellspacing="2" cellpadding="3" width="100%">
            <tr class="tdH">
               <td colspan="2">
                  <div align="center">
                     <strong>List of Units with Nil Supplement TB  </strong>
                  </div>
               </td>
            </tr>
         </table>
         
         <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
               
               <tr align="left">
                  <td class="table">
                     <div align="left">Cash Book Year &amp; Month</div>
                  </td>
                  <td>
                     <div align="left">
                        <input type="text" name="txtCB_Year" id="txtCB_Year"
                               tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)"  onblur="Suppl_Number_Check();" ></input>
                         
                        <select name="txtCB_Month" id="txtCB_Month"
                                tabindex="4"  onchange="Suppl_Number_Check();">
                           <option value="3">March</option>                          
                        </select>
                     </div>
                  </td>
               </tr>
               
            <tr align="left">
            <td class="table">
            <div align="left">Supplement Number</div>
            </td>
            <td>
              <div align="left">
               
               <select name="txtsupplement_no" id="txtsupplement_no" >
                   <option value="" >-- Select Suppl No. -- </option> 
                </select>       
                
                       
                </div>
            </td>
          </tr>                   
               
           </table>
         </div>
         
         <div align="left" style="display:none">Trial Balance Status</div>
         
         
         <div align="left" style="display:none">
            <input type="radio" id="radTB_status" name="radTB_status" value="Y"
                   checked="checked"></input>
             Yes 
            <input type="radio" id="radTB_status" name="radTB_status" value="N"></input>
             No
         </div>
         
         
         <table align="center" cellspacing="3" cellpadding="2" border="1"
                width="100%">
            <tr class="tdH">
               <td>
                  <div align="center">
                     <input type="submit" value="Submit"></input>
                      
                     <input type="button" id="cmdcancel" name="cancel"
                            value="EXIT" onclick="closeWindow()"></input>
                  </div>
               </td>
            </tr>
         </table>
      </form></body>
</html>