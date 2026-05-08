<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>A47 Schedule</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script language="javascript" type="text/javascript"
            src="../scripts/A47ScheduleServlet.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Account_Head_Check.js"></script>          
    
     <script type="text/javascript" src="../../../../../../org/FAS/FAS1/CommonControls/scripts/AccountHead_PopUp.js"></script> 
     
     <script type="text/javascript" 
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Com_Schedule_Account_Heads.js"> </script>                
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>                            
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
       
        document.frmA47Report.txtCB_Year.value=year;
        document.frmA47Report.txtCB_Month.value=month;
        
         }
     
     function ChooseReptype(id)
     {
        
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");

         
         //var reg_date_id=document.getElementById("reg_date_id");
         
         if(document.frmA47Report.reporttype[0].checked==true)
         {
        	 
	        	 //reg_id.style.display="block";
	        	 //reg_date_id.style.display="block";
                 dispsupnochosen1.style.display="none";
                 dispsupnochosen2.style.display="none";

                 
         }
         else if(document.frmA47Report.reporttype[1].checked==true)
         {
        	 dispsupnochosen1.style.display="none";
             dispsupnochosen2.style.display="none";
         }
         else
         {
	        	// reg_id.style.display="none";
	        	// reg_date_id.style.display="none";
                 dispsupnochosen1.style.display="block";
                 dispsupnochosen2.style.display="block";
                 alert("Enter the Supplement Number");
                 document.getElementById("supno").focus();
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
  
  <body class="table" onload="loadyear_month();Schedule_Account_Heads('A47');LoadAccountingUnitID('FOR_LIST_1')">
  
  
  <form action="../../../../../../A47ScheduleServlet.con" name="frmA47Report" id="frmA47Report"
           method="post" onsubmit="return checknull()">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>A 47 Schedule of Debits to Contingent Expenditure</center>
          </td>
        </tr>
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
                      tabindex="1" onchange="common_LoadOffice(this.value);">              
              
              </select>
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
                      tabindex="2">
                
              </select>
            </div>
          </td>
        </tr>
        <tr align="left">
          <td class="table">
            <div align="left">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
                
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
            <div align="left">Report Type </div>
          </td>
          <td>
            <div align="left">
            
                <input type="radio" name="reporttype" id="reporttype" value="1" checked onclick="ChooseReptype(this.value);" /> Regular &nbsp;
                <input type="radio" name="reporttype" id="reporttype" value="2" onclick="ChooseReptype(this.value);" /> Regular( Account Head Code on New Page) &nbsp;
                <input type="radio" name="reporttype" id="reporttype" value="3" onclick="ChooseReptype(this.value);" /> Supplement 
                   
            </div>
          </td>
        </tr>    
         <tr align="left">
         
            <td class="table">
              <div align="left" style="display: none;" name="dispsupno1" id="dispsupno1">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div style="display: none;" name="dispsupno2" id="dispsupno2">
                <input type="text" size="2" id="supno" name="supno"  onkeypress="return numbersonly(event)" />     
              </div>
              
            </td>
          </tr> 
         
          <tr align="left">
          <td class="table">
            <div align="left">Account Head Code </div>
          </td>
          <td>
            <div align="left">
            
                   <select name="txtAcc_HeadCode" id="txtAcc_HeadCode" >
                     <option value="">  --- Select the Account Head Code --- </option> 
                     
                   </select>          
         
            </div>
          </td>
        </tr>    
        
        
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <input type="submit" value="Submit" name="cmdSubmit" ></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>               
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>