<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/xml; charset=windows-1252"/>
    <title>HRM_LeaveExtensionJSP</title>
    <script type="text/javascript" src="../scripts/HRM_LeaveExtensionJS.js">
    </script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>  
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../Library/scripts/CalendarControl.js"></script>
  </head>
  <body>
  <form name=LeaveExtension>
  <div align=center>
  <table border=1 width="100%">
  <tr class="tdH">
  <td colspan=2 align=center height="40">
              <strong>
                  Leave Extension Request Form
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
  <input type=hidden name=tname><input type=hidden name=tid>
  </td>
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
  <tr class="tdH">
  <td colspan="2" align="center" height="35">Leave Extension Details</td>
  </tr>
  <tr class="table">
        <td height="35" align="left">Medical Certificate Attached </td>
        <td height="35" align="left">
            <input type="radio" value="Y" name="mc" id="mc1"></input>
                       Yes&nbsp;               
            <input type="radio" value="N" name="mc" id="mc2"></input>
                       No&nbsp;
        </td>
 </tr>
  <tr class="table">
  <td height="35" align="left">Leave Extended Up to <font color="Gray">(dd/mm/yyyy)</font></td>
  <td height="35" align="left"><input type="text" name=extension id=extension size="20" onfocus="ordercheck()" onblur="datefun(periodTo.value,this.value)" onkeypress="return calinsert(event,this)">
  <img src="../../../../../images/calendr3.gif" onclick="if(ordercheck()==true)showCalendarControl(document.LeaveExtension.extension);" alt="Show Calendar"></img>
  </td>
  </tr>
  <tr class="table">
  <td height="35" align="left">No.of Suffix Holidays </td>
  <td height="35" align="left"><input type="text" name=suffix id=suffix size="20"></td>
  </tr>
  <tr class="table">
  <td align="left">Reason for extension</td>
  <td align="left"><textarea rows="2" name="reason" cols="20"></textarea></td>
  </tr>
  <tr class="table">
  <td height="35" align="left">No.of Leave Days Including Extension </td>
  <td height="35" align="left"><input type="text" name=totdays id=totdays size="20" disabled="true"></td>
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