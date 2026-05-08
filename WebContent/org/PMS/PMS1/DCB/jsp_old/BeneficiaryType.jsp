<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Beneficiary Type</title>
    <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
     <script type="text/javascript" src="../scripts/BeneficiaryType.js"></script>
  </head>
  <body onload="callServer('Get'); callServer('Type');" >
  <form name="mstprivate" action="">
  <table border="1" width="80%" align="center">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"> <div align="center"><strong>Beneficiary Type</strong></div></td>
    </tr>
    <tr class="table">
        <td>Beneficiary Type Sno</td>
        <td><input type="text" name="sno" id="sno" maxlength="5" size="5" readonly="readonly" style="background-color: #ececec"/><small>(Auto generated)</small></td>
    </tr>
    
    
    
    <tr class="table">
        <td>Description</td>
        <td><input type="text" name="desc" maxlength="35" size="35" id="desc" /></td>
    </tr>
    
    
    <tr class="table">
        <td>Short Description</td>
        <td><input type="text" name="sdesc" maxlength="4" size="4" id="sdesc" style="TEXT-TRANSFORM:UPPERCASE"/></td>
    </tr>

    
    <tr class="table">
        <td>Private Beneficiary or Local Body?</td>
        <td>
        	<input type="radio" name="prvlb" id="prv" value="P" onclick="dispAdr(this.value);" checked/> Private &nbsp; &nbsp;
        	<input type="radio" name="prvlb" id="lb" value="L" onclick="dispAdr(this.value);" /> Local Body
        </td>
    </tr>

    
    <tr class="table">
        <td><span id="adrlbl" style="display:none">Addressed To</span></td>
        <td><input type="text" name="adr" maxlength="35" size="35" id="adr" style="display:none" /></td>
    </tr>

    <tr class="tdH" align="center">
        <td colspan="1" >               
                <input type="button" name="Add" value="Add" id="Add" onclick="callServer('Add')"/>
                <input type="button" name="Update" value="Update" id="Update" onclick="callServer('Update')" disabled/>
                <input type="button" name="Delete" value="Delete" id="Delete" onclick="callServer('Delete')" disabled/>
                <input type="reset" name="Clear" value="Clear"  id="Clear" onclick="clearForm()"/>
                <input type="button" name="Exit" value="Exit"  id="Exit" onclick="self.close()"/>              
        </td>
        <td colspan="1" >               
                <input type="button" name="Tariff" value="Tariff" id="Tariff" onclick="openWindow('../../../../../org/PMS/PMS1/DCB/jsps/Pms_Dcb_Mst_Tariff.jsp','Tariff');"/>
                <input type="button" name="Interest" value="Interest" id="Interest" onclick="openWindow('../../../../../org/PMS/PMS1/DCB/jsps/pms_dcb_mst_int.jsp','Interest');" disabled/>
        </td>
    </tr>
   
    </table>
     </form>
    <table id="existing" border="1" width="80%" align="center">
        <tr>
            <th class="tdH">Select
            <th class="tdH">Beneficiary Type</th>
            <th class="tdH">In Short</th>
            <th class="tdH">Addressed To</th>
        </tr>
        <tbody id="tblList" name="tblList" class="table">
        </tbody>
    </table>
  </body>
</html>