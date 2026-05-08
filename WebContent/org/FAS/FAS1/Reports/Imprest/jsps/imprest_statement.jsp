<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Statement of Imprest/Temporary Advance</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/imprest_statement.js"></script>
            
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript" 
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
            
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
     {
   
		     var today= new Date(); 
		     var day=today.getDate();
		     var month=today.getMonth();
		     month=month+1;
		     var year=today.getYear();
		     if(year < 1900) year += 1900;
		   
		     document.frmSubLedgerReport.txtCB_Year.value=year;
		     document.frmSubLedgerReport.txtCB_Month.value=month;
		   		    
     }
     function closeWindow()
     {                
	         window.open('','_parent','');                
	         window.close(); 
	         window.opener.focus();
     }
     function ChooseReptype(id)
     {
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");

         if(document.frmSubLedgerReport.reporttype[0].checked==true)
         {
        	     dispsupnochosen1.style.display="none";
                 dispsupnochosen2.style.display="none";
         }
         else if(document.frmSubLedgerReport.reporttype[1].checked==true)
         {
        	 dispsupnochosen1.style.display="block";
             dispsupnochosen2.style.display="block";
             alert("Enter the Supplement Number");
         }
     }
     function dispsuppno()
     {
    	 var dispsupnochosen3=document.getElementById("dispsupno3");
         var dispsupnochosen4=document.getElementById("dispsupno4");
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");

         if(document.getElementById("txtCB_Month").value==3)
         {
        	 //alert("March month chosen");
        	 dispsupnochosen3.style.display="block";
        	 dispsupnochosen4.style.display="block";
         }
         else 
         {
        	 dispsupnochosen3.style.display="block";
        	 document.frmSubLedgerReport.reporttype[0].checked=true;
        	 dispsupnochosen4.style.display="none";
        	 dispsupnochosen1.style.display="none";
        	 dispsupnochosen2.style.display="none";
         }
     }
    </script>
</head>
<body class="table" onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1')">
<form name="frmSubLedgerReport" method="POST" action="../../../../../../imprest_Statement" onsubmit="return checknull()">
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Statement Of Imprest/Temporary Advance</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
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
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
          <tr align="left">
            <td class="table">
              <div align="left">Cash Book Year &amp; Month</div>
            </td>
            <td>
              <div align="left">
               
                <input type="text" name="txtCB_Year" id="txtCB_Year"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>
                 
                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="dispsuppno()">
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
          <tr class="table">
                <td width="30%">
                  <div align="left">
                    Advance Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="cmbAdvance_type" id="cmbAdvance_type" tabindex="4">
                        <option value="I" selected="selected">Imprest</option>
                        <option value="T">Temporary Advance</option>
                    </select>                     
                  </div>
                </td>
              </tr>
               <tr align="left">
          <td class="table">
            <div align="left">Report Type </div>
          </td>
          <td>
            <div align="left" id="dispsupno3" style="display:block">
                <input type="radio" name="reporttype" id="reporttype" value="1" checked onclick="ChooseReptype(this.value);"/>Regular &nbsp;
                </div>
                <div align="left" id="dispsupno4" style="display:none">
                <input type="radio" name="reporttype" id="reporttype" value="3" onclick="ChooseReptype(this.value);"/> Supplement 
            </div>
          </td>
        </tr> 
        <tr align="left">
            <td class="table">
              <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div id="dispsupno2" name="dispsupno2" style="display:none">
                <input type="text" name="supno" id="supno" size=2 value= 0>     
              </div>
              
            </td>
          </tr>  
        </table>
      </div>
      <tr>
        <td align="left">Report Option:</td>
        <td colspan="3" align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
          HTML
        </td>
      </tr>
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>