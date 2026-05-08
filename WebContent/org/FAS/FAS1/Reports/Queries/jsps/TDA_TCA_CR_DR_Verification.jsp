<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Verification Of TDA/TCA Register</title>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/TDA_TCA_CR_DR_Verification.js"></script>
            
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
<form name="tda_tca_grid" id="tda_tca_grid" method="post" action="../../../../../../Twad_common_servlet?Command=Add" onsubmit="return checkNull();">
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Verification Of TDA/TCA Register</strong>
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
                 </select>
                <input type="button" name="gobtn" id="gobtn" value="Go" onclick="callGrid();"></input>
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
                <th>Head Of Account</th>
                <th>Transaction DR Amount</th>
                <th>TDA DR Amount</th>
                <th>Transaction CR Amount</th>
                <th>TDA CR Amount</th>
                <th>Transaction NET Amount</th>
                <th>TDA NET Amount</th>
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
              <input type="submit" value="Verify" id="btnSubmit" ></input>
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
             <div align="center" style="display:block" id="two_id">
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>   
          </td>
        </tr>
      </table>
    </form>
    </body>
</html>