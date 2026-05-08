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
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/TB_GenertedNot_Freezed.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                     window.close(); 
                }
                 function loadyear_month()
                 {
               
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
               
                document.frmReport.txtCB_Year.value=year
                document.frmReport.txtCB_Month.value=month;
                
                 }
                                 
                //Null check Validation
                function nullcheck()
                {
                    var txtCB_Year=document.getElementById("txtCB_Year").value;
                    var txtCB_Month=document.getElementById("txtCB_Month").value;
                    
                    if(txtCB_Year.length!=4 || txtCB_Month.length==0)
                    {
                        alert("Specify the year(4 digit) and month");
                        return false;
                    }
                return true;
                }
                
                
                ///////////////////////////////////////  Numbers only fields
                function numbersonly(e)
                {
                    var unicode=e.charCode? e.charCode : e.keyCode;
                   if(unicode==13)
                    {
                      //t.blur();
                      //return true;-------------------- for taking action when press ENTER
                    
                    }
                    if (unicode!=8 && unicode !=9  )
                    {
                        if (unicode<48 || unicode>57 ) 
                            return false 
                    }
                 }
    </script>
    <title>TB-Generted Not Freezed</title>
  </head>
  <body class="table" onload="loadyear_month()">
  
  
    
  <form action="../../../../../../TB_GenertedNot_Freezed?Command=trial" name=frmReport method=post onsubmit="return nullcheck();"> 
    <table width="100%" >
        <tr>
            <td class="tdH"><center><b>View Trial Balance Generated Units </b></center></td>
        </tr>
          <tr>
            <td>
                <table border="4" cellspacing="0" cellpadding="0" width="100%">
            
              <tr >
                          <td class="table">
                         
                              Cash Book Year &amp; Month&nbsp;&nbsp; <font color="#ff2121">*</font>
                            </td>
                         <td>
                           <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
                         
                          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
                    </tr>
                        <tr>
                        <td>
                            Report Option:
                        </td>
                        <td colspan="3">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>
                        
                    </tr>
                    <tr>
                        <td colspan=4 class="tdH" align="center">
                        <input type=submit value=Submit >
                        <input type=reset value=Clear>
                        <input type=button value=Exit onclick="closeWindow()">
                        </td>
                    </tr>
                    
                
                </table>
            </td>
           </tr>
        </table>
  
  </form>
  </body>
</html>