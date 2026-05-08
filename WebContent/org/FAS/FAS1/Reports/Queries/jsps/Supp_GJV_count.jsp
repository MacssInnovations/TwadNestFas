<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Number of Supplements Authorized</title>
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
     function callYr()
     {
    	 
    	 if(document.ftfsFormreport.alltype[0].checked==true)
    	 {
    		 var cell=document.getElementById("fdiv");
             cell.style.display="none";
             var cell=document.getElementById("sdiv");
             cell.style.display="none";
    		
    	 }
    	 else
    	 {
    		 var cell=document.getElementById("fdiv");
             cell.style.display="block";
             var cell=document.getElementById("sdiv");
             cell.style.display="block";
    	 }
     }
    </script>
</head>
<body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')">
<form name="ftfsFormreport" id="ftfsFormreport"  method="POST" 
action="../../../../../../supp_GJV_count?Command=formReport" >
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>List Number of Supplements Authorized</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
          <tr>
        <td align="left">Type</td>
        <td colspan="3" align="left">
          <input type="radio" name="alltype" id="alltype" value="all" onclick="callYr();"
                 checked="checked"></input>
          ALL
          <input type="radio" name="alltype" id="alltype" value="finyear" onclick="callYr();"></input>
          YearWise
          
        </td>
      </tr>
          <tr align="left">
            <td class="table">
            
              <div align="left" id="fdiv" style="display:none">Finanacial Year </div>
            </td>
            <td>
              <div align="left" id="sdiv" style="display:none">
                 <select name="txtCB_Year" id="txtCB_Year" >
                  <option value="2012-2013">2012-2013</option>
                  <option value="2011-2012">2011-2012</option>
                  <option value="2010-2011">2010-2011</option>
                  <option value="2009-2010">2009-2010</option>
                  <option value="2008-2009">2008-2009</option>
                  <option value="2007-2008">2007-2008</option>
                  <option value="2006-2007">2006-2007</option>
                 </select>
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
              <input type="submit" value="Submit" ></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form>
    </body>
</html>