<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>MIS_Level3_AbstractReport_JSP</title>
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <script language="javascript" src="../scripts/MIS_Level3_AbstractReport_JS.js" type="text/javascript">
    </script>
  </head>
  <body onload=loadMajorHead()>
  <form action="../../../../../../MIS_Level3_AbstractReport_Serv" name="level2_grouping" method="post" onsubmit="return checknull()">
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
    <tr>
     <td>
      <table cellspacing="2" cellpadding="3" border="0" width="100%" class="table">
          <tr>
              <td class="tdH" colspan="2"><center>Level3 Abstract Report</center></td>
          </tr>
          <tr class="table">
              <td width="40%" align="left">
                    Major head 
              </td>
              <td width="60%">
                     <select name="major_head" id="major_head" onchange="loadLevel2()">
                        <option value="">Select Major Head</option>
                     </select>
              </td>
          </tr> 
          <tr class="table">
              <td width="40%" align="left">
                    Level2 
              </td>
              <td width="60%">
                    <select name="level2" id="level2" onchange="loadLevel3()">
                        <option value="">Select Level2</option>
                    </select>
              </td>
          </tr> 
          <tr class="table">
              <td width="40%" align="left">
                    Level3 
              </td>
              <td width="60%">
                    <select name="level3" id="level3">
                        <option value="">Select Level3</option>
                    </select>
              </td>
          </tr>
          <tr class="table">
              <td width="40%" align="left">
                    Period 
              </td>
              <td width="60%">
                    <input type="radio" name="period" id="period" value="annual" checked="checked" onclick="loadMonthYear()">Annual &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="period" id="period" value="monthly" onclick="loadMonthYear()">Monthly
              </td>
          </tr>
          <tr class="table">
              <td width="40%" align="left">
              </td>
              <td width="60%">
                    <!--div id="annualDiv" style="display:block"-->
                        <select name="year" id="year">
                            <option value="">Select Year</option>
                        </select>
                    <!--/div><div id="monthlyDiv" style="display:none"-->
                            <select name="month" id="month" style="visibility:hidden">
                            <option value="">Select Month</option>
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
          <tr class="table">
              <td width="40%" align="left">
                    Heading 
              </td>
              <td width="60%">
                    <input type="text" name="heading" id="heading">
              </td>
          </tr>
          <tr class="tdH">
            <td colspan="2" align="center">
                 <input type=submit value=Submit >
                 <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="javascript:self.close()">
            </td>
           </tr>
           
      </table>
     </td>
    </tr>
   </table>
  </form>
  </body>
</html>