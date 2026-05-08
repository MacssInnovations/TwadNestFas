<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>  
  <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Twad Opening Balance</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
   <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
   
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript" src="../scripts/Twad_OpeningBalance.js"></script>
    
    
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }

        
          function loadDate()
         {
             // alert('tezt')
        	  //call_bankUpdate();
               var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
            //document.frmMIS_Twad.date_val.value=day+"/"+month+"/"+year;
            document.getElementById("date_val").value=day+"/"+month+"/"+year;
       
        
         }
          function loadOnyear(val)
          {
              
              var year=val.split("-");
              var data=document.getElementById("op_5").innerHTML;
              var data1=document.getElementById("op_06").innerHTML;
              var data2=document.getElementById("op_11").innerHTML;
              var data3=document.getElementById("op_12").innerHTML;
              var data4=document.getElementById("op_13").innerHTML;
              var data5=document.getElementById("op_14").innerHTML;
              var data6=document.getElementById("op_15").innerHTML;
              var data7=document.getElementById("op_16").innerHTML;
              var data8=document.getElementById("op_17").innerHTML;
              var data9=document.getElementById("op_21").innerHTML;
              document.getElementById("op_5").innerHTML=data+" "+year[0];
             document.getElementById("op_06").innerHTML=data1+" "+year[0];
             document.getElementById("op_11").innerHTML=data2+" "+year[1];
             document.getElementById("op_12").innerHTML=data3+" "+year[0];alert(year[0]+"-"+year[1].substring(2));
             document.getElementById("op_13").innerHTML=data4+" "+year[0]+"-"+year[1].substring(2);
             document.getElementById("op_14").innerHTML=data5+" "+year[1];
             document.getElementById("op_15").innerHTML=data6+" "+year[1];
             document.getElementById("op_16").innerHTML=data7+" "+year[0];
             document.getElementById("op_17").innerHTML=data8+" "+year[0]+"-"+year[1].substring(2);
             document.getElementById("op_21").innerHTML=data9+" "+year[1];
          }
</script>
  </head>
  <%
  int val=1;
	String s = request.getContextPath();
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
  int unitid=0;
  String unitname="";
	System.out.println(s);
  %>
  <body><table cellspacing="1" cellpadding="3" width="1500">
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Twad OB Details</font>
          </div>
        </td>
      </tr>
    </table>
  <form name="frmOB_Twad" id="frmOB_Twad" action="">
 
    <table cellspacing="1" cellpadding="2" border="0" id ="table_main" width="1500" >
  <tr class="table">
		<td>
		<div align="left">Financial Year</div>
		</td>
		<td>
		<select id="fin_year" name="fin_year" onchange="loadOnyear(this.value);">
			<option value="2006-2007">2006-07</option>
			<option value="2007-2008">2007-08</option>
			<option value="2008-2009">2008-09</option>
			<option value="2009-2010">2009-10</option>
			<option value="2010-2011">2010-11</option>
			<option value="2011-2012">2011-12</option>
			<option value="2012-2013">2012-13</option>
			<option value="2014-2015">2013-14</option>
			<option value="2014-2015"  selected="selected">2014-15</option>
		</select>
	<!--	
		<label id="fin_year" name="fin_year" >2012-13</label>-->
		</td>
	</tr>
	
		<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit1" value="SUBMIT" id="onsubmit1" onClick="Load_Function('<%=s%>');" />
			 <input type="button" name="onsubmit1" value="PDF" id="onsubmit1"  onClick="printPDF('<%=s%>','G');" />
			<input type="button" name="butCan" id="butCan" value="Cancel" onclick="window.location.reload();" />
			
			</div>
		</td>
	</tr></table>
	<div>
<table cellspacing="2" id ="table_tbody" cellpadding="3" border="1" width="1500" style="font-size:7pt; ">	
	<tr class="tdH"  >
		<th>Sl.No</th>
		<th>Particulars</th>
		<th ><label id="op_5">Opening Balance as on 01.04.</label></th>
		<th>Addition</th>
		<th>Total</th>
		<th>Deletion</th>
		<th>Grand Total</th>
		<th><label id="op_06">Depreciation as on 31.03.</label></th>
			<th>Dep during the Year(Cr)</th>
				<th>Dep yester Year(Dt)</th>
		<th>Net Depreciation</th>
		<th>Depreciation upto date</th>
		<th><label id="op_11">Depreciation cost on 31.03.</label></th>
		<th><label id="op_12">OB of Discorded Asset as on 01.04.</label></th>
		<th><label id="op_13">Add Discorded Asset Accounts</label></th>
		<th><label id="op_14">Discorded Asset as on 31.03.</label></th>
		<th><label id="op_15">Balance as on 31.03</label></th>
		<th><label id="op_16">Apportionment of Grant as on 31.03.</label></th>
		<th><label id="op_17">Apportionment of Grant</label></th>
		<th>Apportionment of Grant YY</th>
		<th>Apportionment of Grant Net</th>
		<th>Apportionment of Grant upto_date</th>
		<th><label id="op_21">Balance as on 31.03.</label></th>
		
	</tr>
	<tbody id="tblList" class="table" align="left"></tbody>
</table>
	</div>
	
  </form>
  </body>
</html>
