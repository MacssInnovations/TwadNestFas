<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>List of PB updated Units</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" language="javascript">
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

  		if (month < 4) {
  			financialyear = year1 + "-" + year;
  			financialyear1 = (year1-1) + "-" + (year-1);
  		} else {
  			financialyear = year + "-" + year1;
  			financialyear1 = (year-1) + "-" + (year1-1);
  		}
  		for(var k=0;k<1;k++)
  		{
  		/*	if(k==0)
  			{
  				var se = document.getElementById("financialYear");
  		  		var op = document.createElement("OPTION");
  		  		op.value = financialyear1;
  		  		var txt = document.createTextNode(financialyear1);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  			} */
  			if(k==0)
  			{
  				var se = document.getElementById("financialYear");
  		  		var op = document.createElement("OPTION");
  		  		op.value = financialyear;
  		  		var txt = document.createTextNode(financialyear);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  		  		
  			}                           
  		}    
  		document.getElementById("financialYear").value=financialyear; 
      }
    </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    
      </head>
      <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table" >
  <form name="pbupdatedUnits" id="pbupdatedUnits" method="POST" action="../../../../../../ListPBUpdatedUnits?command=listpb">
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>List of PB updated Units</strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">             
                <tr align="left">
           			<td class="table">
              			<div align="left">
                 			Financial Year <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td>        				       
		          		<select name="financialYear"  id="financialYear">
			          		<option value="2011-2012">2011-2012 </option>
		          		</select>          	
		          </td>
        </tr> 
        <tr align="left">
           			<td class="table">
              			<div align="left">
                 			Status
              			</div>
           			</td>
          			<td>  
			      		  <input type="radio" name="freezetype" id="freezetype" value="F" checked>Freezed
			              <input type="radio" name="freezetype" id="freezetype" value="NF">Not Freezed 
              </td>
              </tr>      
        <tr>
           <td align="left">
               Report Option:
           </td>
           <td colspan="3" align="left">
              <input type="radio" name="fileType" id="txtoption1" value="PDF" checked>PDF
              <input type="radio" name="fileType" id="txtoption2" value="EXCEL">Excel
              <input type="radio" name="fileType" id="txtoption3" value="HTML">HTML
           </td>
                        
      </tr>
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type="submit"  value="GO" id="sum" name="sum">
          <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="closeWindow()">
       </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>