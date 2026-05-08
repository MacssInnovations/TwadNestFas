<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Login Report</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    
    <!--<script type="text/javascript" src="../../scripts/CalendarControl.js"></script>-->
    <script type="text/javascript" src="../scripts/LoginReportWithIP.js"></script>
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body class="table">
  <form action="../../../../../../LoginReportWithIPServ.rep"   name="frmReport" method="post" onsubmit="return nullcheck()">
      <table border="1" cellspacing="2" cellpadding="1" width="100%">
        <tr>
          <td class="tdH">
            <center>
              <b>Login List With IP</b>
            </center>
          </td>
        </tr>
        <tr>
          <td>
            <table border="1" cellspacing="2" cellpadding="1" width="100%">
              <tr>
                <td >From Date:</td>
                <td>
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmReport.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
                
              </tr>
              <tr>
                <td>Report Option:</td>
                <td >
                  <input type="radio" name="txtoption" id="txtoption"
                         value="PDF" checked="checked"/>
                  PDF
                  <input type="radio" name="txtoption" id="txtoption"
                         value="EXCEL"/>
                  Excel
                  <input type="radio" name="txtoption" id="txtoption"
                         value="HTML"/>
                  HTML
                </td>
              </tr>
              <tr>
                <td colspan="2" class="tdH" align="center">
                  <input type="submit" value="Submit"/>
                  <input type="reset" value="Clear"/>
                  <input type="button" value="Exit" onclick="closeWindow()"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form></body>
</html>