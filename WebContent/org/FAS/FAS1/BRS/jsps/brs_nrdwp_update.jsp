<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>BRS Report</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
  
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
    
    
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    <script type="text/javascript" language="javascript">
        

    function loadyear_month()
   {       
       var today= new Date(); 
       var day=today.getDate();
       var month=today.getMonth();
       month=month+1;
       var year=today.getYear();
       if(year < 1900) year += 1900;
      
      document.brs_ct_status.asondate.value=day+"/"+month+"/"+year;
             
   }
    
     

    function doFun(val)
    {
   	 if(val=="Single")
   		 {
   		 document.getElementById("sel").style.display="block";
   		 }else{
   			 document.getElementById("sel").style.display="none";
   		 }
    }
            
        </script>
    
</head>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<body class="table" onload="loadyear_month();">
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">BRS NRDWP Update</font>
          </div>
        </td>
      </tr>
</table>

  
<form name="brs_ct_status" method="POST" action="../../../../../brs_nrdwp_update" onsubmit="return nullcheck()">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
					<!-- <tr align="left">
		            <td class="table">
		            Option:
		             </td>
		            <td>
		               <input type="radio" name="type_report" id="type_report" value="ALL" checked onClick="doFun(this)"/>All &nbsp;
		               <input type="radio" name="type_report" id="type_report" value="single" checked onClick="doFun(this)"/>Single &nbsp;
		              
		              
		              <select id="sel" name="sel" style="visibility: hidden;" >
			             
			               
                 
                  <option value="Main">BRS_NRDWP-Main</option>
                  <option value="Support">BRS_NRDWP-Support</option>
                  <option value="FDW">Full Deposit Work</option>
                  
			             </select>
		            </td>
		          </tr>
		     </table>     --> 
		     <tr align="left">
		            <td class="table">
		               As On Date
		             </td>
		            <td>
		                <input type="text" name="asondate" id="asondate" readonly="readonly" size=10 >     
		              
		            </td>
		          </tr>
		           <tr align="left">
			          <td class="table">
			            <div align="left">Report Type</div>
			          </td>
			          <td>
			                <input type="radio" name="type_report" id="type_report" value="ALL" checked onclick="doFun(this.value);" />All &nbsp;
			                <input type="radio" name="type_report" id="type_report" value="Single" onclick="doFun(this.value);"/> Single Type 
			             <select id="sel" name="sel" style="display: none;" >
			             <option value="">--Select--</option>
			                
                  
                  <option value="Main">OPR_NRDWP-Main</option>
                  <option value="Support">OPR_NRDWP-Support</option>
                  <option value="FDW">Full Deposit Work</option>
                  
			             </select>
			          </td>
			        </tr> 
                   
		          
		          
		            
                   
        
        <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
		
	      <tr class="tdH">
		      <td>
		          <div align="center">
		             <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                        &nbsp;&nbsp;&nbsp; 
		         	<input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="self.close()"/>
		     	  </div>
		      </td>
	      </tr>      
      	</table></table>
</form>
</body>
</html>
       
