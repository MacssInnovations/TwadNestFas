<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>charges_type_details</title>
    <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../scripts/charge_type_details.js">
   
   </script>
  </head>
  <body onload="loadCombo();doFunction('get'); " >
  <form name="chargedetails" action="">
  <table border="1" width="80%" align="center">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"> <div align="center"><strong>Charges Type Details</strong></div></td>
    </tr>
    <tr class="table">
        <td>Charge Type No</td>
        <td><input type="text" name="charge_type_Id" id="charge_type_Id" maxlength="5" size="5" readonly="readonly" style="background-color: #ececec" tabindex="1"/><small>(Auto generated)</small></td>
    </tr>
    <tr class="table">
        <td>Charge Type Description</td>
        <!--<td><input type="text" name="charge_type_desc" maxlength="35" size="35" id="charge_type_desc" style="TEXT-TRANSFORM:UPPERCASE" /></td>-->
        <td>
        <select name="charge_type_desc" id="charge_type_desc" tabindex="2">
            <option value="" selected="selected">- - - Select - - -</option>
        </select>
        </td>
    </tr>
    <tr class="table">
    <td>Charge A/c Head(Credit)</td>
    <td><input type="text" name="txtAcc_HeadCode" id="txtAcc_HeadCode" maxlength="6" size="9" tabindex="3" onKeyPress="return numonly(event);" onblur="doFunction('Reterive');"/>
    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="AccountHeadList"  onclick="AccHeadpopup();"></img>
    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70" tabindex="4"/>
    </td>
    </tr>
   <tr>
    <td class="table">Charge A/c Head(Debit)</td>
    <td><input type="text" name="txtAcc_HeadCode_dr" id="txtAcc_HeadCode_dr" maxlength="6" size="9" tabindex="3" onKeyPress="return numonly(event);" onblur="doFunction('Reterivevalue2');"/>
    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="AccountHeadList"  onclick="AccHeadpopupone();"></img>
    <input type="text" name="txtAcc_HeadDesc_dr" readonly="readonly" id="txtAcc_HeadDesc_dr" style="background-color: #ececec"  maxlength="125" size="70" tabindex="4"/>
    </td>
    </tr>
    <tr class="tdH" align="center">
        <td colspan="2" >               
                <input type="button" name="cmdadd" value="Add" id="cmdadd" onclick="doFunction('Add');"/>
                <input type="button" name="cmdupdate" value="Update" id="cmdupdate" onclick="doFunction('Update','null')" disabled/>
                <input type="button" name="cmddelete" value="Delete" id="cmddelete" onclick="doFunction('Delete','null')" disabled/>
                <input type="button" name="cmdclear" value="Clear"  id="cmdclear" onclick="refresh()"/>
                <input type="button" name="cmdexit" value="Exit"  id="cmdlist" onclick="exitwindow()"/>              
        </td>
    </tr>
    </table>
    <table id="existing" border="1" width="80%" align="center">
        <tr>
            <th class="tdH">Select the value to edit
            <th class="tdH">No</th>
            <th class="tdH">Description</th>
            <th class="tdH">Charge A/c Head(Credit)</th>
            <th class="tdH">Charge A/c Head(Debit)</th>
        </tr>
        <tbody id="getvaluerows" class="table">
        </tbody>
    </table>
    </form>
</body>
</html>