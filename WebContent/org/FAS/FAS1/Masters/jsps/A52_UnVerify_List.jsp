<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>A52 UnVerified List</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript" language="javascript">
       function loadinitial()
       { 
            document.frmA52UnVerified_List.submit();   
      }
       function exit()
       {
       self.close();
       }
    </script>

</head>
<body>
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">A52 UnVerified List( Qty )</font></div>
		</td>
	</tr>
</table>

<form name="frmA52UnVerified_List" id="frmA52UnVerified_List"
	method="POST" action="../../../../../A52_Verified_List?command=A52UnVerified">
<div align="center">

 <table cellspacing="1" cellpadding="2" border="1" width="100%">
   
           <tr class="table">
              <td>
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td>
                    <select name="cmbFinancialYear" id="cmbFinancialYear" ><!--
                    <option value="">--Select Year--</option>
                  --><option value="2011-12">2011-12</option>
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
             <input type="submit" name="CmdGo" value="Go" id="CmdGo"/>   
           <input type="button" name="butCan" id="butCan" value="EXIT" onclick="exit();"/>
          </td>
        </tr>
                </table>
                </div>
              </td>

            </tr>
         </table>

</div>
</form>
</body>
</html>