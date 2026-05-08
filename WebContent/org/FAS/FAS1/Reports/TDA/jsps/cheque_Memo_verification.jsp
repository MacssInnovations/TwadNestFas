<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Verification Of Cheque Memo and Payments</title>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/tda_tca_verification_units_only.js"></script>
            
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
		   
		     document.cheque_grid.txtCB_Year.value=year;
             document.cheque_grid.txtCB_Month.value=month;
     }
     function closeWindow()
     {                
	         window.open('','_parent','');                
	         window.close(); 
	         window.opener.focus();
     }
    </script>
</head>
<%
	String s1 = request.getContextPath();
%>
<body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')">
<form name="cheque_grid" id="cheque_grid" method="post" >
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Verification Of Cheque Memo and Payments</strong>
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
              <div align="left">Year </div>
            </td>
            <td>
              <div align="left">
                 <select name="txtCB_Year" id="txtCB_Year" >
                  <option value="2012">2012</option>
                  <option value="2013">2013</option>
                   <option value="2014">2014</option>
                      <option value="2015">2015</option>
                 </select>
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
                <input type="button" name="gobtn" id="gobtn" value="Go" onclick="callGrid_Memo();"></input>
              </div>
            </td>
            
          </tr>
          
         
          
          <tr align="left">
            <td class="table">
              <div align="left">Particulars
             <font color="#ff2121">*</font></div>
            </td>
            <td>
             <textarea name="txtParticular" id="txtParticular" cols="70"  rows="3"></textarea>
            </td>
          </tr>
         
        </table>
      </div>
      
         
      <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
             <div id="grid_one" style="display:block">
            <table id="mytable1" cellspacing="3" cellpadding="2"
                   border="0" width="100%">
              <tr class="table">
              	<th>Month</th>
                <th>Cheque No</th>
                <th>Cheque Date</th>
                <th>Cheque Memo No</th>
                   <th>Cheque Memo Date</th>
                <th>Payment Voucher No</th>
                   <th>Payment Voucher Date</th>
                <th>Cheque Memo Amount</th>
                 <th>Payment Voucher Amount</th>
           
                <th>Difference</th>
                                      
              </tr>
              <tbody id="grid_body1" class="table" align="left" >
              </tbody>
            </table>
          </div>
            
          </tr>
         
        </table>
      
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center" style="display:none" id="one_id">
              <input type="button" value="Verify" id="btnSubmit" onclick="verify_btn1();"></input>
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
             <div align="center" style="display:block" id="two_id">
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>  
            <div align="center" style="display:none" id="three_id">
             <input type="button" value="Verified" id="btnverified" disabled="disabled"></input>
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div> 
          </td>
        </tr>
      </table>
    </form>
    </body>
</html>