<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Adjustment Memo Register Officewise</title>
   
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
   
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

    <script type="text/javascript"
           src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
   
   <script type="text/javascript" language="javascript">
    function loadyear_month(){
                var today= new Date(); 
                var day=today.getDate();
                var month=today.getMonth();
                month=month+1;
                var year=today.getYear();
                if(year < 1900) year += 1900;
              
               document.frmadj_memo_report_officewise.txtCB_Year.value=year;
               document.frmadj_memo_report_officewise.txtCB_Month.value=month;
                       
               document.frmadj_memo_report_officewise.txtCB_Year_from.value=year;
               document.frmadj_memo_report_officewise.txtCB_Month_from.value=month;
               
               document.frmadj_memo_report_officewise.txtCB_Year_to.value=year;
               document.frmadj_memo_report_officewise.txtCB_Month_to.value=month;
      }

        function checkNull()
         {
        	//var cbmontyr;
        	//alert("call null method");
          if((document.frmadj_memo_report_officewise.month_year[0].checked==false) && (document.frmadj_memo_report_officewise.month_year[1].checked==false))
          {
        	 alert("select cashbook month and year");
        	 return false;
          }  
          return true;
                
         }
        function cb_month_year(id)
        {
           var particular=document.getElementById("particular");
           var more=document.getElementById("more");
               
          if(id=="particular_cb")
          {
             particular.style.display="block";
             more.style.display="none";
          }
          if(id=="more_cb")
          {
            more.style.display="block";
            particular.style.display="none";
          }
        }
   </script>
  </head>  
<body class="table" onload="loadyear_month(),LoadAccountingUnitID('FOR_LIST_1');">  
 <form name="frmadj_memo_report_officewise" id="frmadj_memo_report_officewise" action="../../../../../Adj_Memo_Report_Officewise" method="POST" onsubmit="return checkNull()"> 
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Adjustment Memo Register Officewise</font>
          </div>
        </td>
      </tr>
    </table>
    
    
          <table cellspacing="2" align="center" cellpadding="2" border="1" width="80%">
           <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
          
          <tr class="table">
                <td>
                  <div align="left">
                    Advice Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="advice_type" id="advice_type" 
                           checked="checked" value="CR" />Credit Advice
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="advice_type" id="advice_type" 
                           value="DR" />Debit Advice &nbsp;&nbsp;&nbsp;&nbsp; 
                    
                  </div>
                </td>
              </tr>
          
    		<tr align="left">
           <td class="table">
              <div align="left">
                 Raised Cash Book Year &amp; Month <font color="#ff2121">*</font>
              </div>
           </td>
          <td>
         
          <input type="radio" name="month_year" id="month_year" value="particular_cb" onclick="cb_month_year(this.value)" >one Month 
          <input type="radio" name="month_year" id="month_year" value="more_cb" onclick="cb_month_year(this.value)"> More than one Month 
          
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
          
          </div>
          
          <div id="more" name="more" style="display:none">
          
          From   
          <input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" >
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
          To  
          <input type="text" name="txtCB_Year_to" id="txtCB_Year_to" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_to"  id="txtCB_Month_to" tabindex="4" >
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
               <td>
                 <div align="left">
              		Report
                 </div>
               </td>             
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
            
            <tr class="tdH">
              
               <td colspan="2">
                <div align="center">
                	<input type="submit" value="Submit"> &nbsp;&nbsp; <input type="button" value="Exit" onclick="javascript:window.close()">
                </div>
               </td>
              </tr>                 
            </table>            
        </form>
    </body>
</html>