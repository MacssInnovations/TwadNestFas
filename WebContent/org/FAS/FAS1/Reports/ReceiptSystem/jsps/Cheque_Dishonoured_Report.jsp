<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Cheque Dishonoured Report</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
   
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
            <script type="text/javascript" language="javascript">
            function loadyear_month(){
                
                var today= new Date(); 
                var day=today.getDate();
                var month=today.getMonth();
                month=month+1;
                var year=today.getYear();
                if(year < 1900) year += 1900;
              
               /** Load Cash Book Month and Cash Book Year during Form Load */
               document.frmChequeDishonoured.txtCB_Year.value=year;
               document.frmChequeDishonoured.txtCB_Month.value=month;
            
             }
            
            function cb_month_year(id)
            {
               var particular=document.getElementById("particular");
               //var All=document.getElementById("All");
                   
              if(id=="particular_cb")
              {
                 particular.style.display="block";
                 //All.style.display="none";
              }
              if(id=="All")
              {
                 particular.style.display="none";
                 //All.style.display="none";
              }
              
            }
            
            function accountid_wise(id)
            {
               var particular_acct1=document.getElementById("particular_acct1");
               var particular_acct2=document.getElementById("particular_acct2");
               var particular_acct3=document.getElementById("particular_acct3");
               var particular_acct4=document.getElementById("particular_acct4");
             
                   
              if(id=="particular_acct")
              {
            	  particular_acct1.style.display="block";
            	  particular_acct2.style.display="block";
            	  particular_acct3.style.display="block";
            	  particular_acct4.style.display="block";
                 //All.style.display="none";
              }
              if(id=="All")
              {
            	  particular_acct1.style.display="none";
            	  particular_acct2.style.display="none";
            	  particular_acct3.style.display="none";
            	  particular_acct4.style.display="none";
                 //All.style.display="none";
              }
              
            }
            
            function checkNull()
            {
            	
                   if(document.frmChequeDishonoured.month_year[1].checked==true)
                   {
                	  
                	 if(document.getElementById("txtCB_Year").value==""){
                		 alert("Enter CashBook Month & Year");
                         return false;
                	 }  
                  
                   }
                   
                   
            }
            function numbersonly(e)
            {
                var unicode=e.charCode? e.charCode : e.keyCode;
               if(unicode==13)
                {
                  //t.blur();
                  //return true;-------------------- for taking action when press ENTER
                
                }
                if (unicode!=8 && unicode !=9  )
                {
                    if (unicode<48 || unicode>57 ) 
                        return false ;
                }
             }
            function btncancel()
            {

             self.close();
            }
            </script>
  </head>
  <body class="table"
        onload="LoadAccountingUnitID('FOR_LIST_1'),loadyear_month();">
        <form name="frmChequeDishonoured" method="POST" action="../../../../../../Cheque_Dishonoured_Report"
                                                                           onsubmit="return checkNull()">
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Cheque Dishonoured Report</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
        
           <tr align="left">
           <td class="table">
              <div align="left">
                 Accounting Unit<font color="#ff2121">*</font>
              </div>
           </td>
          <td>
         
          <input type="radio" name="accountid" id="accountid" value="All" onclick="accountid_wise(this.value)" checked="checked" > All Units
          <input type="radio" name="accountid" id="accountid" value="particular_acct" onclick="accountid_wise(this.value)"> Particular Unit
         
          </td>
        </tr>
        
          <tr class="table">
            <td>
             <div id="particular_acct1" name="particular_acct1" style="display:none">
              
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div id="particular_acct2" name="particular_acct2" style="display:none">
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="common_LoadOffice(this.value);"></select>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div id="particular_acct3" name="particular_acct3" style="display:none">
                Accounting For Office Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
             <div id="particular_acct4" name="particular_acct4" style="display:none">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
          
          <tr align="left">
           <td class="table">
              <div align="left">
                 Cash Book Year &amp; Month <font color="#ff2121">*</font>
              </div>
           </td>
          <td>
         
          <input type="radio" name="month_year" id="month_year" value="All" onclick="cb_month_year(this.value)" checked="checked" > All
          <input type="radio" name="month_year" id="month_year" value="particular_cb" onclick="cb_month_year(this.value)"> Particular
          
          <br><br>       
          
          <div id="particular" name="particular" style="display:none">
            
          Year 
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          Month 
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
          
          </div></td>
        </tr>
         <tr align="left">
            <td>Report Option:</td>
            <td>
              <input type="radio" name="txtoption" id="txtoption" value="PDF"
                     checked="checked"></input>
              PDF
              <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
              Excel
              <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
              HTML
            </td>
          </tr>
        </table>
      </div>
      <br></br>
      <br></br>
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="btncancel()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>