<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>A52 Abstract MIS </title>

     <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
     <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript"  src="../../../../../org/FAS/FAS1/Masters/scripts/A52_Abstract_MIS.js"></script>
    <script type="text/javascript" language="javascript">
         function foc()
         {
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
  <body>
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">A52 Abstract MIS </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmA52_Abstract_mis" id="frmA52_Abstract_mis" method="get" action="../../../../../A52_Abstract_MIS" onsubmit="return checkNull_verify()">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
   
           <tr class="table">
              <td>
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td>
                    <select name="cmbFinancialYear" id="cmbFinancialYear" >
                    <option value="">--Select Year--</option>
                  <option value="2011-12">2011-12</option>
                  <option value="2012-13">2012-13</option>
                    </select>
              </td>
              </tr>
                       
       <tr class="tdH">
        <td colspan="2">
        
                <div align="center">
                <table >
                 <tr>
          <td colspan="3" class="table">
             <input type="button" name="CmdGo" value="Go" id="CmdGo" onclick="callServer('Go')"/>   
           <input type="button" name="butCan" id="butCan" value="EXIT" onclick="closeWindow();"/>
          </td>
        </tr>
                </table>
                </div>
              </td>

            </tr>
         </table>
         <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                <tr class="tdH">
		<th>Asset Type</th>
		<th>Closing Qty</th>
		<th>Closing Value</th>
		<th>Book Value</th>
		<th>View</th>
			
	</tr>
             <tbody id="tblList" align="center" class="table">
             </tbody>
            </table><!--
            <table align="center" cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <td>
                    <div align="center">
                             <input type="button" id="updateTotally" name="updateTotally" value="Update" onclick="UpdateTotallyValues();"></input>
                            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
                    </div>
                  </td>
                </tr>
      </table> 
    --></form>
    </body>
</html>