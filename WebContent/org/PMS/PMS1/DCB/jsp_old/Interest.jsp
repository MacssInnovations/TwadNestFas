<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/pms_dcb_mst_tariff.js"></script>
    <title>Pms_Dcb_Mst_Tariff</title>
  </head>
  <body onload="loadUOM();loadbeneficiary();doFunction('get');">
  <form name="chargedetails" action="">
   <table border="1" width="80%" align="center">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"> <div align="center"><strong>Tariff Master Details</strong></div></td>
    </tr>
    <tr>
    <td>
    
    </td>
    </tr>
    
   <!-- <tr class="table">
         
        <td><input type="hidden" name="Tariff_Code" maxlength="35" size="35" id="Tariff_Code" style="TEXT-TRANSFORM:UPPERCASE" /></td>
    </tr>-->
  <tr class="table">
        <td>Tariff No</td>
        <td><input type="text" name="tariff_Id" id="tariff_Id" maxlength="5" size="7" readonly="readonly" style="background-color: #ececec" /><small>(Auto generated)</small></td>
    </tr>
    
    <!--<tr class="table">
        <td>Tariff Code <small>(user reference)</small></td>
        <td><input type="text" name="Tariff_Code" maxlength="35" size="35" id="Tariff_Code" style="TEXT-TRANSFORM:UPPERCASE" /></td>
    </tr>-->
    
     <tr class="table">
        <td>Beneficiary Type</td>
        <td>
            <select name="Beneficiary_Type" id="Beneficiary_Type" tabindex="2">
            <option value="" selected="selected">- - - Select - - -</option>
        </select>
        </td>
    </tr>
    
    <!--<tr class="table">
       
       <td>Charge Type</td>
        <td>
        <select name="charge_type_Id" id="charge_type_Id" tabindex="2">
            <option value="" selected="selected">- - - Select - - -</option>
        </select>
       
       <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Beneficiary"></img>
       <input type="text" name="charge_type_Id" id="charge_type_Id" maxlength="5" size="5"  tabindex="2"/>
        </td>
    </tr>
    <tr class="table">
        <td>Tariff Desc</td>
        <td><input type="text" name="tariff_Desc" maxlength="35" size="35" id="tariff_Desc" style="TEXT-TRANSFORM:UPPERCASE" /></td>
    </tr>-->
    <tr class="table">
        <td>Tariff Rate <small>(Rs)</small></td>
        <td><input type="text" name="tariff_Rate" maxlength="35" size="7" id="tariff_Rate" style="TEXT-TRANSFORM:UPPERCASE" onkeyPress="return numonly(event);"/></td>
    </tr>
    
   <!--  <tr class="table">
        <td>Excess Tariff Rate <small>(Rs)</small></td>
        <td><input type="text" name="excess_tariff_Rate" maxlength="35" size="7" id="excess_tariff_Rate" style="TEXT-TRANSFORM:UPPERCASE" onkeyPress="return numonly(event);"/></td>
    </tr>-->
    
   
    
    <tr class="table">
        <td>Tariff w.e.f</td>
        <td><input type="text" name="Tariff_wef" maxlength="35" size="7" id="Tariff_wef" style="TEXT-TRANSFORM:UPPERCASE" />
       <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(Tariff_wef)"></img>
        </td>
    </tr>
    <tr class="table">
        <td>Unit of Measurement</td>
        <td><!--<input type="text" name="UOM" id="UOM" maxlength="35" size="35" id="UOM" style="TEXT-TRANSFORM:UPPERCASE" />-->
        <select id="UOM">
            <option value="" selected="selected"> - - - Select - - -</option>
         <!--   <option value="KLD">KLD</option>
            <option value="Percentage">Percentage</option>-->
        </select>
        
        </td>
    </tr>
   <tr class="table">
        <td>Status</td>
        <td><select id="status">
            <option value="" selected="selected"> - - - Select - - -</option>
            <option value="A">Active</option>
            <option value="D">Defunct</option>
        </select>
        </td>
    </tr>
    
    
    <tr class="tdH" align="center">
        <td colspan="2" >               
                <input type="button" name="cmdadd" value="Add" id="cmdadd" onclick="doFunction('Add');"/>
                <input type="button" name="cmdupdate" value="Update" id="cmdupdate" onclick="doFunction('Update');" disabled/>
                <input type="button" name="cmddelete" value="Delete" id="cmddelete" onclick="doFunction('Delete');" disabled/>
                <input type="button" name="cmdclear" value="Clear"  id="cmdclear" onclick="refresh()"/>
                <input type="button" name="cmdexit" value="Exit"  id="cmdlist" onclick="exitwindow();"/>              
        </td>
    </tr>
    </table>
    <table id="existing" border="1" width="80%" align="center">
        <tr>
            <!--<th class="tdH">Select the value to edit
            <th class="tdH">No</th>-->
           
            <th class="tdH">Select</th>
           <!-- <th class="tdH">Tariff Code</th>-->
           <th class="tdH">No</th>
            <th class="tdH">Beneficiary Type</th>
          <!--  <th class="tdH">Charge Type</th>-->
            <th class="tdH">Tariff Rate</th>
            <!--<th class="tdH">Excess Tariff Rate</th>-->
            <th class="tdH">Tariff w.e.f</th>
            <th class="tdH">Unit of Measurement</th>
            <th class="tdH">Status</th>
           
         </tr>
        
         
        
        <tbody id="getvaluerows" class="table"></tbody>
        <!--<tbody id="getvalue">
        
        </tbody>-->
        
    </table>
     </form>
  </body>
 
</html>