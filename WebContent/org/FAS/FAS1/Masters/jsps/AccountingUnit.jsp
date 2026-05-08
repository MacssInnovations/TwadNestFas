<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    
   
    <title>Accounting Unit Master</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
     <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../scripts/AccountingUnit.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function loadDate()
                {
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
                   document.frmAccountUnit.date_open.value=day+"/"+month+"/"+year;
                   call_date( document.frmAccountUnit.date_open);
                }
    </script>
  </head>
  <body class="table" onload="loadform();loadDate()">
  <form action="../../../../../AccountingUnitServlet.con?command=Add" name="frmAccountUnit" method="post" onsubmit="return nullcheck1()">
  <table cellspacing="2" cellpadding="3" border="0" width="100%" align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Accounting Unit Details</b></td>                   
       </tr> 
       <tr>
            <td class="table">Accounting Unit Id:</td>
            <td class="table">
            <input type="text" name="txtaccountid" maxlength="2"
                   id="txtaccountid" readonly size="3"/>System Generated
                   <input type="hidden" name="txtHAccountid" id="txtHAcoountid" size="10">
           </td>
       </tr>
       
       <tr>
            <td class="table">Accounting Unit Name:</td>
            <td class="table">
            <input type="text" name="txtaccountname" id="txtaccountname" size="90">
            </td>
       </tr>
       
       <!-- added sathya on 22/06/2012 -->
       <tr>
		    <td class="table">Date of Opening</td>
            <td class="table">
		                  
                  <div align="left">
                   <input type="text" name="date_open" id="date_open" tabindex="3" 
                           maxlength="10" size="11" 
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           
                           onblur="call_mainJSP_script(this,1); "/>
                        <!--   
                           onblur="return checkdt(this);"/>  -->
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAccountUnit.date_open,1);"
                         alt="Show Calendar"></img>               
                           
                    
                  </div>
              </td>
	       </tr>
       
       <tr>
            <td class="table">Location:</td>
            <td class="table">
            <input type="text" name="txtLOffice" id="txtLOffice" maxlength="4" onchange="loadOffice(this.value,true);" onkeypress="return numbersonly1(event,this)">
            <img src="../../../../../images/c-lovi.gif" alt="" onclick="jobpopup()" ></img>
            </td>
       </tr>
       
       <tr>
            <td class="table">Office Name:</td>
            <td class="table"><input type="text" name="txtLofficename" id="txtLofficename" disabled size=50>
       </tr>
       
       <tr>
            <td class="tdH" align="left" colspan="2"><b>Rendering Offices</b></td>
            
       </tr>
       <tr>
            <td class="table">Office Id:</td>
            <td class="table"><input type="text" name="txtOffice" id="txtOffice" maxlength="4" onchange="loadOffice(this.value,false)" onkeypress="return numbersonly1(event,this)">
            <img src="../../../../../images/c-lovi.gif" alt="" onclick="jobpopup1()" ></img>
            </td>
       </tr>
       <tr>
            <td class="table">Office Name:</td>
            <td class="table"><input type="text" name="txtofficename" id="txtofficename" disabled size=50>
       </tr>
       <tr align="center">
          <td colspan="2" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE" id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE" id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdList" value=ListAll id="CmdList" onclick="ListAllBudget()">
            
            </td>
        </tr>
        
    </table>
    <table cellspacing="3" cellpadding="2" border="0" width="100%"  align="center" >
            <tr>
                <td class="tdH" colspan="2"><b>Existing Details</b></td>
            </tr>
    </table>
    <table id="Existing" cellspacing="2" cellpadding="3" border="0" width="100%" align="center">
        <tr class="tdH">
          <th align="center">
            Select
          </th>
           <th align="left">
            Office Id
          </th>
           <th align="left">
            Office Name
          </th>
        </tr>
        <tbody id="tblList" name="tblList" class="table">
        </tbody>
        <tr>
            <td colspan="3" align="center">
            <input type="submit" value="Submit">
            <input type="reset" value="Clear" onclick="clearall()">
            <input type="button" value="Exit" onclick="closeWindow()">
    </td>
        </tr>
    </table>
    
  </form>
  </body>
</html>