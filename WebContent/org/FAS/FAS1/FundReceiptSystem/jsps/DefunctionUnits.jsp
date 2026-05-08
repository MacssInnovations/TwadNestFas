<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Defunctional Units List</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    

    
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
    
      
  
    <script language="javascript" type="text/javascript"><!--
				function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
				function loadyear_month()
		         {    	  
		    	  var today = new Date();
		  		var day = today.getDate();
		  		var month = today.getMonth();
		  		month = month + 1;
		  		var year = today.getYear();
		  		var year1 = 0;
		  		var financialyear = 0;
		  		var financialyear1 = 0;
		  		if (year < 1900)
		  			year += 1900;
		  		if (month < 4) {
		  			year1 = year - 1;
		  		} else {
		  			year1 = year + 1;
		  		}

		  		//document.getElementById("month_val").value=month;  
		  		//document.getElementById("year_val").value=year;  
		  	   if(day<=9 && day>=1)
		           day="0"+day;
		           if(month<=9 && month>=1)
		           month="0"+month;
		           var year=today.getYear();
		           if(year < 1900) year += 1900;
		           var monthArray =new Array("January", "February", "March", 
		                     "April", "May", "June", "July", "August",
		                     "September", "October", "November", "December");
		        
		         // doFunction('load_Voucher_No','null');
		  	
		  		if (month < 4) {
		  			
		  	
		  			financialyear = year1 + "-" + year;
		  			financialyear1 = (year1-1) + "-" + (year-1);
		  		} else {
		  			financialyear = year + "-" + year1;
		  			financialyear1 = (year-1) + "-" + (year1-1);
		  		}
		  	  
		  	
		  	  document.frmDefunction.txtCrea_date.value=day+"/"+month+"/"+year;
		         }
		        
    --></script>
  </head>
  <body class="table" onload="loadyear_month();">
  <form action="../../../../../DefunctionUnits" name="frmDefunction" id="frmDefunction" method="post" >
     <input type='hidden' name='RecordCount' id='RecordCount' value='0' /> 
  <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Defunctional Units List</b></td>
                   
       </tr> 
       
      <!-- 
          
         
           <tr class="table">
                            <td>Defunctional Units As on<font color="#ff2121">*</font></td>
                            <td>
                                year<font color="#ff2121">*</font><input type="text" id="year_val" name="year_val"/>
                                Month<font color="#ff2121">*</font>
                                <select id="month_val" name="month_val">
			<option value="">--Select--</option>
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
                              
                            </td>
                          
                          </tr> -->
                          
                               <tr class="table">
                <td>
                  <div align="left">
                    Defunctional Units As On
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date"
                           tabindex="3" maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmDefunction.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
                          
                          <tr class="table">
        <td align="left">Report Option <font color="#ff2121">*</font></td>
        <td colspan="3" align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
         
        </td>
       </tr>
                          
                          </table>
        
        <table cellspacing="2" cellpadding="3" border="0" width="100%" align="center">
       <tr>
			
	        <td class="tdH" align="center">
	            
	        <input type="submit" name="CmdSubmit" value="Submit" id="CmdSubmit">
	        <input type="button" name="CmdClose" value="EXIT" id="CmdList" onclick="closeWindow()">
	       
	        </td>
       </tr>
       
    </table>
 </form>
  </body>
</html>