<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>BRS OB Non Twad All </title>
   
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
   
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
       
   <script type="text/javascript" language="javascript">
    function loadyear_month(){
                var today= new Date(); 
                var day=today.getDate();
                var month=today.getMonth();
                month=month+1;
                var year=today.getYear();
                if(year < 1900) year += 1900;
              
               document.frmBrs_OB_Nontwad_ALL.txtCB_Year.value=year;
               document.frmBrs_OB_Nontwad_ALL.txtCB_Month.value=month;
 
      }

    function cb_month_year1(id){
      // var Regions=document.getElementById("Regions");
       var Banks=document.getElementById("officewise");
    
      if(id=="OW")
      {
    	 Banks.style.display="block";
         //Regions.style.display="none";
      }
      if(id=="ALL")
      {
    	 Banks.style.display="none";
         //Regions.style.display="none";
      }
    }

     </script>
  </head>  
<body class="table" onload="loadyear_month();">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">BRS OB Non Twad Report</font>
          </div>
        </td>
      </tr>
    </table>
    <form name="frmBrs_OB_Nontwad_ALL" id="frmBrs_OB_Nontwad_ALL" method="POST" action="../../../../../BRS_OB_NonTwad_All">
    
          <table cellspacing="2" align="center" cellpadding="2" border="1" width="80%">
          <tr align="left">
           <td class="table">
		<div align="left">Cash Book Year &amp; Month<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> <select
			name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
		</select></div>
		</td>
	</tr>
              <tr class="table">
               <td>
                 <div align="left">
              		Displaying Order <font color="#ff2121"> * </font>
                 </div>
               </td>
               <td>
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="ALL" onclick="cb_month_year1(this.value)" checked ></input>All    &nbsp;&nbsp; 
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="OW" onclick="cb_month_year1(this.value),LoadAccountingUnitID('LIST_ALL_UNITS');" ></input>Office Wise      
                  <br><br><div align="left" id="officewise" name="officewise" style="display:none">
                         	
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1"></select>     
                    
                </div>
               </td>               
              </tr>            
                 <tr class="table">
               <td>
                 <div align="left">
              		Report
                 </div>
               </td>
               <td>
                <div align="left">
                	<select name="txtoption" id="txtoption" >
                		<option value="PDF" selected="selected">PDF</option>      
                		<option value="Excel">Excel</option>    
                	</select>
                </div>
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