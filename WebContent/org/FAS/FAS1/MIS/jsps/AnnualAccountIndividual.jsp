<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<%@page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver"%><html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Annual Accounts Of TWAD Board </title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/AnnualAccountDetail.js"></script>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
        {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
                
        /** Load From Cash Book Month and From Cash Book Year during Form Load */
        document.finProgressReprot.txtCB_Year_from.value=year;
        document.finProgressReprot.txtCB_Month_from.value=month;
        
        /** Load To Cash Book Month and To Cash Book Year during Form Load */
        document.finProgressReprot.txtCB_Year_to.value=year;
        document.finProgressReprot.txtCB_Month_to.value=month;
        
      }
     function callSJVload()
     {
  	   
  	   var month_chosen=document.getElementById("txtCB_Month_to").value;
  	 
  	   var dispsjv=document.getElementById("dispSJV");
  	   if(month_chosen==3)
  		   {
  		   		
  		   		dispsjv.style.display="block";
  		   }
  	   else
  		   {
  				dispsjv.style.display="none";
  		   }
  	
     }
     function ChooseReptype(id)
     {
        
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");
         if(id=="Regular")
         {
                  
                 dispsupnochosen1.style.display="none";
                 dispsupnochosen2.style.display="none";
         }
         else
         {
                
                 dispsupnochosen1.style.display="block";
                 dispsupnochosen2.style.display="block";
                 alert("Enter the Supplement Number");
         }
     }
     function cb_month_year1(id){
         var Regions=document.getElementById("Regions");
        if(id=="RW") {
      	  Regions.style.display="block";
      	  Banks.style.display="none";
        }
        if(id=="ALL")
        {
         Regions.style.display="none";
        }
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
      <%!
       	Connection connection;
      	PreparedStatement preparedStatement;
      	ResultSet resultSet;
      %>
      <%connection  = new LoadDriver().getConnection(); %>
  <body class="table" onload="loadyear_month();setTimeout('callSJVload()',100);callServer('majorGroup');">
  <form name="finProgressReprot" id="finProgressReprot" method="POST" action="../../../../../summary_ind_report.pdf?command=annualindividual">
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>Annual Accounts Of TWAD Board </strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">             
                <tr align="left">
           			<td class="table">
              			<div align="left">
                 			Cash Book Year &amp; Month <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td>        				       
		          <div id="more">
          		From   
          		<input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          		<select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" >
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
          	To  
          	<input type="text" name="txtCB_Year_to" id="txtCB_Year_to" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          	<select name="txtCB_Month_to"  id="txtCB_Month_to" tabindex="4" onchange="callSJVload();">
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
          </div>      
          </td>
        </tr>
        <tr align="left">
          <td class="table">
            <div align="left">Selection of Report Type</div>
          </td>
          <td>
          <div align="left">                  
               <input type=radio id="reptype" name="reptype" value="Regular" checked onclick="ChooseReptype(this.value)"> Regular
               <div id="dispSJV" name="dispSJV" style="display:none">
               <input type=radio id="reptype" name="reptype" value="InclusiveSJV" onclick="ChooseReptype(this.value)"> Inclusive SJV
               </div>
            </div>
            </td>
         </tr>
         <tr align="left">
          <td class="table">
            <div align="left">Major Group Code</div>
          </td>
          <td>
          <div align="left">                  
               <select name="majorGroupCode" id="majorGroupCode" onchange="callServer('minorGroup');">
               		<option value="select">Select</option>
               </select>
            </div>
            </td>
         </tr>
         <tr align="left">
          <td class="table">
            <div align="left">Minor Group Code</div>
          </td>
          <td>
          <div align="left">                  
               <select name="minorGroupCode" id="minorGroupCode">
               		<option value="0">Select</option>
               </select>
            </div>
            </td>
         </tr>
        <tr>
        <tr>
           <td align="left">
               Report Option:
           </td>
           <td colspan="3" align="left">
              <input type=radio name=fileType id=txtoption value="PDF" checked>PDF
              <input type=radio name=fileType id=txtoption value="EXCEL">Excel
              <input type=radio name=fileType id=txtoption value="HTML">HTML
           </td>
                        
      </tr>
      <tr align="left">
         
            <td class="table">
              <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div id="dispsupno2" name="dispsupno2" style="display:none">
                <input type="text" name="txtsupplement_no" id="txtsupplement_no" size=2 >     
              </div>
              
            </td>
          </tr>
	</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type="submit"  value="Submit" id="sum" name="sum">
          <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="closeWindow()">
       </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>