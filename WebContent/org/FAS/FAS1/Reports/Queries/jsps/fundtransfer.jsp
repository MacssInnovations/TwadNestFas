<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FT vs FR Report</title>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
           
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
   
          
            <script type="text/javascript" language="javascript">
            
            function doFun(val)
            {
           	 if(val=="Single")
           		 {
           		 document.getElementById("sel").style.display="block";
           		 }else{
           			 document.getElementById("sel").style.display="none";
           		 }
            } 
            
            
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
<body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS')">
<form name="ftfsForm" id="ftfsForm"  method="GET" action="../../../../../../funsTransferReport" >
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Fund Transfer vs Fund Receipt</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
          
          <tr align="left">
            <td class="table">
              <div align="left">Finacial Year </div>
            </td>
            <td>
              <div align="left">
                 <select name="txtCB_Year" id="txtCB_Year" >
                 <option value="2015-2016">2015-2016</option>
                 <option value="2014-2015">2014-2015</option>
                 <option value="2013-2014">2013-2014</option>
                  <option value="2012-2013">2012-2013</option>
                 <option value="2011-2012">2011-2012</option>
                 <option value="2010-2011">2010-2011</option>
                
                 </select>
                 
                  <tr align="left">
			          <td class="table">
			            <div align="left">Report Type</div>
			          </td>
			          <td>
			                <input type="radio" name="type_report" id="type_report" value="one"/>Finacial_Year 
			                <input type="radio" name="type_report" id="type_report" value="two"/> Accepted Next_Year 
			                <input type="radio" name="type_report" id="type_report" value="three" />Accepted Previous_year
			             
			            
			          </td>
			        </tr>   
                
              </div>
            </td>
          </tr>
          
         
        </table>
      </div>
      
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
                <input type="submit" value="ViewReport" ></input>
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form>
    </body>
</html>