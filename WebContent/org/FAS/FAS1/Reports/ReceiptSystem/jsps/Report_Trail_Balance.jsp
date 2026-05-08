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
    <title>Trail Balance Report</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script language="javascript" type="text/javascript"
            src="../scripts/GPFReport_Abstract.js"></script>
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
             
            document.frmtrailbal.txtCB_Year_From.value=year;
            document.frmtrailbal.txtCB_Month_From.value=month;
            document.frmtrailbal.txtCB_Year_To.value=year;
            document.frmtrailbal.txtCB_Month_To.value=month;
        
        }
        
        function pop()
        {
        
                var today= new Date();          
                var month=today.getMonth();
                month=month+1;        
                if(document.getElementById("txtCB_Month_From"))
                {
                    var obj =document.getElementById("txtCB_Month_From");
                    var mon_names = new Array("January","February","march","April","May","June","July","Augest","September","October","November","December");
                    if(month >= 1 && month <= 12)
                    {
                        if(month > 1) 
                            var disp = month-1;
                        else	
                            var disp = 12;
                    } 		
                     
                    var j=0;   
                    for ( i=4; i<=disp;i++)
                    {	
                    
                        obj.options[j] = new Option(mon_names[i-1],i);				
                        j++;
                    }
                
                
                }
                if(document.getElementById("txtCB_Month_To"))
                {
                    var obj1 =document.getElementById("txtCB_Month_To");
                    var mon_names = new Array("January","February","march","April","May","June","July","Augest","September","October","November","December");
                    if(month >= 1 && month <= 12)
                    {
                        if(month > 1) 
                            var disp = month-1;
                        else	
                            var disp = 12;
                    } 		
                     
                    var j=0;   
                    for ( i=4; i<=disp;i++)
                    {	
                    
                        obj1.options[j] = new Option(mon_names[i-1],i);				
                        j++;
                    }
                
                
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
  <body class="table" onload="loadyear_month();callServer_LoadSection()">
  <form action="../../../../../../Trail_Balance_Serv_Report"
                                                       name="frmtrailbal" id=""
                                                       method="post"
                                                       onsubmit="return checknull()">
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>Trail Balance Report</center>
          </td>
        </tr>
        
        
        <tr align="left">
          <td class="table">
            <div align="left">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left"> From :
              <input type="text" name="txtCB_Year_From" id="txtCB_Year_From" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
                <select name="txtCB_Month_From" id="txtCB_Month_From" tabindex="4">
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
           
To :
              <input type="text" name="txtCB_Year_To" id="txtCB_Year_To" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
                <select name="txtCB_Month_To" id="txtCB_Month_To" tabindex="4">
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
        
        
      
        
        
        
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>