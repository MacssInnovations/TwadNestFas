<%@ page contentType="text/html;charset=windows-1252"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>RegFees Master</title>
    <script language="javascript" type="text/javascript" src="../scripts/AjaxMstRegFees.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample.css" rel="stylesheet" media="screen"/>
   <script language="javascript">
   
function popWindow()
{
      my_window= window.open("ListAllRegFees1.jsp","mywindow1","status=1,height=500,width=600"); 
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
          <td colspan="2" class="tdH">
            <DIV align="center">
              <P>
                <strong>CONTRACTOR REGISTRATION FEES DETAILS </strong>
              </P>
            </DIV>
          </td>
        </tr>
        <tr>
          <td>
            <FONT face="Times New Roman">Office of Registration</FONT>
          </td>
          <td>
            <input type="radio" name="Reg_Office" value="DN" onclick="getClasss(event)" checked/>
            <FONT face="Times New Roman">Division &nbsp;</FONT><input type="radio" name="Reg_Office" value="CL" onclick="getClasss(event)" />
            <FONT face="Times New Roman">Circle</FONT></td>
        </tr>
        <tr>
          <td>
            <FONT face="Times New Roman">Class of Registration</FONT>
            </td>
          <td>
            <select size="1" name="Reg_Id">
              <option>--Select Here--</option>
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
          <td colspan="2" class="tdH">
            <input type="button" name="cmdAdd" value="  Add  " onclick="addRecord();"/>
            <input type="button" name="Edit" value="  Edit  " onclick="promptID()"/>
            <input type="button" name="Update" value="Update" onclick="callServer('Update')" disabled/>
            <input type="button" name="Delete" value="Delete" onclick= "callServer('Delete')" disabled/>
            <input type="button" name="Clear All" value="Clear All" onclick="clearAll()"/>
            <input type="button" name="List" value="List All" onclick="popWindow()"/>
            <input type="BUTTON" value="  Exit  " onclick="self.close();"/>
     
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





