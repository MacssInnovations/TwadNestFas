<%@ page contentType="text/html;charset=windows-1252"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>RegFees Master</title>
    <script language="javascript" type="text/javascript" src="../scripts/AjaxRegFees.js"></script>
    <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample.css" rel="stylesheet" media="screen"/>
   <script language="javascript">
   function loadDate()
   {
     var today = new Date()
     var year = today.getYear()
     if(year < 1000)
     {
      year += 1900
     }
    
     document.frmRegFees.txtDate.value=today.getDate() + "/" + (today.getMonth()+1) +  "/" + (year+"");
}

function popupWindow()
{
      my_window= window.open("ListPop.jsp","mywindow1","status=1,height=500,width=600"); 
      my_window.moveTo(250,250);    
}

</script>
  </head>
  <body onload="loadDate()">  
  <form name="frmRegFees" >
      <p>
        &nbsp;
      </p>
      <table cellspacing="2" cellpadding="3" border="1" width="90%" align="center" class="table">
        <tr>
          <td colspan="2" class="td">
            <DIV align="center">
              <P>
                <strong><u>Contractor Registration Fees Master </u></strong>
              </P>
            </DIV>
          </td>
        </tr>
        <tr>
          <td>
            <FONT face="Times New Roman">Office of Registration</FONT>
          </td>
          <td>
            <input type="radio" name="Reg_Office" value="DN" onclick="Division();" checked/>
            <FONT face="Times New Roman">Division &nbsp;</FONT><input type="radio" name="Reg_Office" value="CL" onclick="Circle();" />
            <FONT face="Times New Roman">Circle</FONT></td>
        </tr>
        <tr>
          <td>
            <FONT face="Times New Roman">Class of Registration</FONT>
            </td>
          <td>
            <select size="1" name="Reg_Id">
              <option value="4">Class-IV</option>
              <option value="5">Class-V</option>
            </select>
          </td>
        </tr>
        <tr>
          <td>
            <FONT face="Times New Roman">Is Jurisdiction over entire state?</FONT>
          </td>
          <td>
            <input type="radio" name="Entire_state" value="Y" onblur="check();get()" checked/>Yes<input type="radio" name="Entire_state" value="N" onblur="check();get()"/>No</td>
        </tr>
        <tr>
          <td>
            <FONT face="Times New Roman">Registration Fees</FONT>
          </td>
          <td>
            <input type="text" name="Reg_Fees"/>
          </td>
        </tr>
        <tr>
          <td>
            <FONT face="Times New Roman">Date Effective From</FONT>
          </td>
          <td>
            <input type="text" name="txtDate" size="10" maxlength="10" onchange="DateCheck()"/>
            (dd/mm/yyyy)
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <input type="button" name="cmdAdd" value="  Add  " onclick="addRecord();"/>
            <input type="button" name="Edit" value="  Edit  " onclick="promptID()"/>
            <input type="button" name="Update" value="Update" onclick="callServer('Update')" disabled/>
            <input type="button" name="Delete" value="Delete" onclick= "callServer('Delete')" disabled/>
            <input type="button" name="Clear All" value="Clear All" onclick="clearAll()"/>
            <input type="button" name="List" value="List" onclick="popupWindow()"/>
          </td>
        </tr>
      </table>
      <p>
        &nbsp;
      </p>
      <p>
        &nbsp;
      </p>
    </form></body>
</html>




