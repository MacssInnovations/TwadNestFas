<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>HRM_Leave_JoiningDutyJSP</title>
    <script type="text/javascript" src="../scripts/HRM_Leave_JoiningDutyJS.js">
    </script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>  
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../Library/scripts/CalendarControl.js"></script>
  </head>
  <body>
  <form name=joining>
  <div align=center>
  <table border=1 width="80%">
  <tr class="tdH">
  <td colspan=2 align=center height="40">
              <strong>
                  Joining Duty Form
              </strong> </td>
  </tr>
   <tr class="table">
  <td height="35" align="left">Employee Id </td>
  <td height="35" align="left"><input type="text" name=eid id=eid size="15" onchange="getFunction(this.value)">
  <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">
  </td>
  </tr>
  <tr class="table">
  <td height="35" align="left">Employee Name </td>
  <td height="35" align="left"><input type="text" name=ename id=ename size="25" disabled="true"></td>
  </tr>
  <tr class="table">
  <td height="35" align="left">Designation </td>
  <td height="35" align="left"><input type="text" name=design id=design size="25" disabled="true"></td>
  </tr>
  <tr class="table">
  <td height="35" align="left">Balance Available Leave  </td>
  <td height="35" align="left"><input type="text" name=balanceEL id=balanceEL size="18" disabled="true"></td>
  </tr>
  <tr class="tdH">
  <td colspan="2" align="center" height="35">Current Leave Request Details</td>
  </tr>
  
  <tr class="table">
  <td height="35" align="left">Leave Request Id </td>
  <td height="35" align="left"><input type="text" name=rid id=rid size="15" onchange=getRequest(this.value)>
  <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="select" onclick="selectfun()">
  <input type=hidden name=tid></td>
  </tr>
  
  <tr class="table">
  <td height="35" align="left">Period From<font color="Gray">(dd/mm/yyyy)</font></td>
  <td height="35" align="left"><input type="text" name=periodFrom id=periodFrom size="20" disabled="true"></td>
  </tr>
  <tr class="table">
  <td height="35" align="left">Period TO<font color="Gray">(dd/mm/yyyy)</font></td>
  <td height="35" align="left"><input type="text" name=periodTo id=periodTo size="20" disabled="true"></td>
  </tr>
  <tr class="table">
  <td height="35" align="left">No.of Prefix Holidays </td>
  <td height="35" align="left"><input type="text" name=ph id=ph size="15" disabled="true"></td>
  </tr>
  <tr class="table">
  <td height="35" align="left">No.of Suffix Holidays </td>
  <td height="35" align="left"><input type="text" name=sh id=sh size="15" disabled="true"></td>
  </tr>
  <tr class="table">
  <td height="35" align="left">No.of Leave Days </td>
  <td height="35" align="left"><input type="text" name=leaveDays id=leaveDays size="15" disabled="true"></td>
  </tr>
  <tr class="table">
  <td align="left">Address During Leave Period </td>
  <td align="left"><textarea rows="2" name="address" cols="20" disabled="true"></textarea></td>
  </tr>
  <tr class="table">
  <td height="35" align="left">Date Applied<font color="Gray">(dd/mm/yyyy)</font> </td>
  <td height="35" align="left"><input type="text" name=dateApplied id=dateApplied size="20" disabled="true"></td>
  </tr>
  <tr class="table">
  <td align="left">Duty Joining Date</td>
  <td align="left">
  <input type=text name=jdate id=jdate size="20" onkeypress="return calinsert(event,this)"></input>
  
  <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.joining.jdate);"
                           alt="Show Calendar"></img>
  
  <select name=jtime id=jtime>
  <option> FN </option>
  <option> AN </option>
  </select>
  </td>
  </tr>
  <tr class="table">
  <td align="left">In Case of ML, Whether Fitness Certificate Produced</td>
  <td align="left">
  <input type="radio" value="Yes" name="DateFrom" id="DateFrom1"
                             onclick="return checkempid();" onmouseup="return checkValue('Yes');"></input>
                       Yes&nbsp;               
  <input type="radio" value="No" name="DateFrom"
                             id="DateFrom2" onclick="return checkValue('No');"></input>
                       No&nbsp;
  </td>
  </tr>
  <tr class="table">
  <td align="left">Fitness Certificate</td>
  <td align="left">
   <input type="radio" value="Register Practioner" name="optDateFrom" id="optDateFrom1"
                             onclick="return checkempid();"></input>
                       Register Practioner&nbsp;                           
   <input type="radio" value="Medical Board" name="optDateFrom"
                             id="optDateFrom2" onclick="return checkempid();"></input>
                       Medical Board&nbsp;
  </td>
  </tr>
  <tr class="table">
  <td align="left">Remarks</td>
  <td align="left"><textarea rows="2" name="reason" cols="20"></textarea></td>
  </tr>
  <tr class="tdH">
  <td colspan="2" align="center"><input type="button" name="submit" value="Submit" onclick="insertfun()">
  <input type="button" name=cancel value="Cancel" onclick="javascript:self.close();"></td>
  </tr>
  </table>
   </div>
   </form>
  </body>
</html>