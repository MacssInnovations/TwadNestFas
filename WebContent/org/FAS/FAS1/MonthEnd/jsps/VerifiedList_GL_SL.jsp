<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Verified List of GL and SL</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/ListofVerifiedGLSL.js"></script>
            
            <script language="javascript" type="text/javascript" src="../scripts/VerifiedList_GL_SL.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          
    
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmVerifiedListGLSL.txtCB_Year.value="2011";
        document.frmVerifiedListGLSL.txtCB_Month.value=month;
                            
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
  <body class="table" onload="loadyear_month()"><form name="frmVerifiedListGLSL"
                                                      method="POST"
                                                      action="../../../../../Arrival_CB_GL.kv"
                                                      onsubmit="return checknull()">
     
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Verified List of GL and SL</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
           <tr align="left">
            <td class="table">
              <div align="left">Cash Book Year &amp; Month</div>
            </td>
            <td>
              <input type="text" name="txtCB_Year" id="txtCB_Year" readonly tabindex="3"
                     maxlength="4" size="5" 
                     onkeypress="return numbersonly(event)" ></input>
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
                <option value="3">March</option>                
              </select>
            </td>
          </tr>
        </table>
      </div>
      <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      <tr class="tdH">
                      <script language="javascript" type="text/javascript"> 
                      function loadHead()
                      {
                    	  
                    	  var h1="";
                    	  var GLList=document.getElementById("cmdListGL").value;
                    	 
                    	  var SLList=document.getElementById("cmdListSL").value;
                    	 
                    	  if(GLList=="List GL")
                    		  {
                    		   h1="GL Freezed Date";
                    		   alert(h1);
                      		   }
                    	  else if(SLList=="List SL")
                    		  {
                    		   h1="SL Freezed Date";
                    		   alert(h1);
                    		  }
                    	  
                      }
                      </script>
                        <th> Accounting Unit Code </th>  
                        <th> Accounting unit Name </th>  
                        <th> Freezed Date </th>
                        <th> Month Closing Balance</th>
                       </tr>
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
       </div>
      
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdListGL" name="cmdListGL" value="List GL" onclick="callServer_LoadListGL()"></input>
              <input type="button" id="cmdListSL" name="cmdListSL" value="List SL" onclick="callServer_LoadListSL()"></input>
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
