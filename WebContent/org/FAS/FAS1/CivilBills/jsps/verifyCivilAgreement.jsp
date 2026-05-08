<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Verify Civil Agreement Master</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
     
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>  
    
    <script type="text/javascript" src="../scripts/verifyCivilAgreement.js"></script>
  
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../Reports/ReceiptSystem/scripts/CalendarControl.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   
     <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
        
        function loadDate()
        { 
        	 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
                 var monthArray =new Array("January", "February", "March", 
                           "April", "May", "June", "July", "August",
                           "September", "October", "November", "December");
                 document.formverifyCivilAgree_Master.txtCivilAgreeDate.value=day+"/"+month+"/"+year;                
        }
    
    </script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();call_clr();" bgcolor="rgb(255,255,225)">
        <table cellspacing="1" cellpadding="3" width="100%" >
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <font size="4">Verify Civil Agreement Master </font>
              </div>
            </td>
          </tr>
        </table>
        
        <form name="formverifyCivilAgree_Master" id="formverifyCivilAgree_Master" method="POST" 
        action="../../../../../VerifyCivilAgreement?Command=Add" onsubmit="checkStatus()">
          <div align="left">
             <table cellspacing="1" cellpadding="2" border="1" width="100%">
                <tr class="table">
                    <td>
                      <div align="left">
                        Accounting Unit Code 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                        </select>
                      </div>
                    </td>
                  </tr>
                <tr class="table">
                 <td>
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                    <td>
                      <div align="left">
                        Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtCivilAgreeDate" id="txtCivilAgreeDate" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return checkdt(this);"/>&nbsp;&nbsp;
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.formverifyCivilAgree_Master.txtCivilAgreeDate,0);"
                             alt="Show Calendar"></img>                        
                      </div> 
                    </td>
              </tr>
              
              
            </table>
         </div>
         <div align="center">            
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
                <tr class="tdH">
                    <td>
                        <div align="center">
                            <input type="button" name="butGo" id="butGo" value="GO" onclick="showDetails();"/>&nbsp;&nbsp;&nbsp;
                            <input type="button" name="butExit" id="butExit" value="CANCEL" onclick="call_clr();"/>
                        </div>
                    </td>
                </tr>
            </table>
         </div>
        <div id="details_disp" style="display:none" align="center">
            <h3>Details</h3>
              <table cellpadding="3"  cellspacing="2" border="1"  width="100%" >
                   <tr  class="tdH">
                        <th>Agreement No</th>
                        <th>Agreement Date</th>   
                        <th>Name of the work</th>
                       <!-- <th>Firm's/Contractor's Name</th> -->
                        <th>Value of the work</th>
                        <th>Work/Supply Order No</th>
                        <th>Work/supply Order Date</th>
                        <th>Remarks</th>
                        <th>Verify</th>
                    </tr>
                        <tbody id="grid_body" class="table" align="left" >
                        </tbody>
            </table>
                    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
                <tr class="tdH">
                    <td>
                        <div align="center">
                            <input type="submit" name="butSubmit" id="butSubmit" value="SUBMIT"/>&nbsp;&nbsp;&nbsp;
                            <input type="button" name="butExit" id="butExit" value="EXIT" onclick="exit();"/>
                        </div>
                    </td>
                </tr>
            </table>
         </div>
       </form>
    </body>
</html>
           