<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>List of SL updated Units</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
        {
       	var finYear="";
         var today= new Date(); 
         var year=today.getFullYear();         
         var select=document.getElementById('financialYear');
        	listOpt=document.createElement("option");
        	select.length=0;
        	select.appendChild(listOpt);
        	listOpt.text="2011-2012";
        	listOpt.value="2011-2012";
        	var incy=0;	
        	for(var i=2011; i<=year; i++){
            		incy=parseInt(i)+1;
        			finYear=i+"-"+incy;
        			listOpt=document.createElement("option");
        			select.appendChild(listOpt);
        			listOpt.text=finYear;
        			listOpt.value=finYear;
        		}
        		document.getElementById('financialYear').selectedIndex=document.getElementById('financialYear').length-1;
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
  <body class="table" >
  <form name="pbupdatedUnits" id="pbupdatedUnits" method="POST" action="../../../../../../ListPBUpdatedUnits?command=listsl">
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>List of SL updated Units</strong>
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
			          		<option value="2011-2012">2011-2012</option>
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