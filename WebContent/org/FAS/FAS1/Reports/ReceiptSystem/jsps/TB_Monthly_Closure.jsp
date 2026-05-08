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
    <title>Trial Balance Monthly Closure</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script language="javascript" type="text/javascript"
            src="../scripts/Annex-III(a)JS.js"></script>
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
         
         document.TBClosure.cbyear.value=year;
        
         }
    function numbersonly(e)
    {
      var unicode=e.charCode? e.charCode : e.keyCode;
      if(unicode==13)
      {
      }
      if (unicode!=8 && unicode !=9  )
      {
        if (unicode<48 || unicode>57 ) 
            return false 
      }
     }   
    
    function checknull()
    {
      if ( document.getElementById("cbyear").value=="")
      {
        alert("Cashbook Year Not Found");
        return false;
      }
      return true;
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
  <body class="table" onload="loadyear_month();">
  <form action="../../../../../../TB_Monthly_Closure.kv" name="TBClosure"
           method="post" onsubmit="return checknull()" >
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>Trial Balance Monthly Closure</center>
          </td>
        </tr>
        <tr align="left">
          <td class="table">
            <div align="left">Cash Book Year </div>
           
          </td>   
          <td class="table">
             <input type="text" name="cbyear" id="cbyear" size="6" onkeypress="return numbersonly(event)"/>
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