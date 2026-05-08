<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Journal System Report</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/Journal_forCashBookMonth.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
          <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
  <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
      
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmJournal_forCashBookMonth.txtCB_Year.value=year
        document.frmJournal_forCashBookMonth.txtCB_Month.value=month;
        
         }
    </script>
      </head>
  <body class="table" onload="loadyear_month(),LoadAccountingUnitID('FOR_LIST_1')" >
  <form name="frmJournal_forCashBookMonth" method="POST" action="../../../../../../Journal_forCashBookMonth_withoutSJV.view" onsubmit="return checknull()">
  
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>JOURNAL BOOK (For the Cash Book Month)</strong>
          </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr>
		            <td>
		                <table border="0" cellspacing="3" cellpadding="3" width="100%">
		                
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
               
                  <!--  This is used to load details about login user( super user or oridinary user ) login and their unit id-->
                
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                    
                    </select>
                  </div>
                </td>
              </tr>
            <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <!--<option value="">select the Month</option>-->
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
        
      <tr>
                        <td>
                            From no:
                        </td>
                        <td>
                            <input type="text" name="frm" id="frm" tabindex="3"  maxlength="4" size="5" >         
                            
                            &nbsp;&nbsp;&nbsp;
                                 
                            To no:
                            <input type="text" name="to" id="to" tabindex="3"  maxlength="4" size="5" >  
                            
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
                <input type="hidden" name="hid" id="hid" value="new_Report"/> 
            </td>
            
        </tr>
</table>
          </div>
    <br>
       <table cellspacing="1" cellpadding="1" border="0" width="100%">
       
        
       
     </table>
     <br>
         <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type=submit value=Submit >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>