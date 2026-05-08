<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    <link href='../../../../../css/CalendarControl.css' rel='stylesheet'media='screen'/>
    <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/pms_dcb_mst_int.js"></script>
    <style type="text/css">
					
					table.border1
					{
					color:#000000;
					padding:0px;
					border-top: 1px solid #dddddd;
					border-left: 1px solid #dddddd;
					border-bottom: 0px solid #dddddd;
					border-right: 0px solid #dddddd;
					font-size: 14  px;
					
					}
					
					table.border1 th, table.border1 td 
					{
					padding:5px;
					padding-bottom:2px;
					border-top: 0px solid #dddddd;
					border-left: 0px solid #dddddd;
					border-bottom: 1px solid #dddddd;
					border-right: 1px solid #dddddd;
					}
										
				</style>
    <title>pms_dcb_mst_int</title>
  </head>
 <body onload="loadbeneficiary();doFunction('get');">
<form action="" name="interest">
 <table border="1" width="80%" align="center" class="border1" cellpadding="0" cellspacing="0">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"><div align="center"><strong>Interest Details</strong></div></td>
    </tr>
    <tr class="table">
        <td>Interest No</td>
        <td><input type="text" name="Interest_Id" id="Interest_Id" maxlength="5" size="7" readonly="readonly" style="background-color: #ececec" /><small>(Auto generated)</small></td>
    </tr>
    <tr class="table">
        <td>Beneficiary Type</td>
        <td>
          <select name="Beneficiary_Type" id="Beneficiary_Type" tabindex="2" onchange="bentypecheck();">
        <!--  <select name="Beneficiary_Type" id="Beneficiary_Type" tabindex="2">-->
            <option value="" selected="selected">- - - Select - - -</option>
        </select>
        </td>
    </tr>
    <tr class="table">
        <td>Interest Rate <small>(%)</small></td>
        <td><input type="text" name="Interest_Rate" maxlength="35" size="7" id="Interest_Rate" onkeyPress="return numonly(event);"/></td>
    </tr>
    <tr class="table">
        <td>Interest w.e.f</td>
        <td><input type="text" name="Interest_wef" maxlength="35" size="8" id="Interest_wef" />
        <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.interest.Interest_wef)"></img>
        </td>
    </tr>
    <tr class="table" >
        <td> <div id="statusdivname">Status</div></td>
        <td><div id="statusdiv"><select id="status">
            <option value="" selected="selected"> - - - Select - - -</option>
            <option value="A">Active</option>
            <option value="D">Defunct</option>
        </select>
        </div>
        </td>
    </tr>
    <tr class="tdH" align="center">
        <td colspan="2" >               
                <input type="button" name="cmdadd" value="Add" id="cmdadd" onclick="doFunction('Add');"/>
                <input type="button" name="cmdupdate" value="Update" id="cmdupdate" onclick="doFunction('Update');" />
                <input type="button" name="cmddelete" value="Delete" id="cmddelete" onclick="doFunction('Delete');" />
                <input type="button" name="cmdclear" value="Clear"  id="cmdclear" onclick="refresh()"/>
                <input type="button" name="cmdexit" value="Exit"  id="cmdlist" onclick="exitwindow();"/>              
        </td>
    </tr>
</table>
<table id="existing" border="1" width="80%" align="center" cellpadding="0" cellspacing="0" class="border1">
    <tr>
        <th class="tdH">Select</th>
       <!-- <th class="tdH">No</th>-->
        <th class="tdH">Beneficiary Type</th>
        <th class="tdH">Interest Rate</th>
        <th class="tdH">Interest w.e.f</th>
        <th class="tdH">Status</th>
    </tr>
    <tbody id="getvaluerows" class="table"></tbody>
</table>
</form>
  
  
  </body>
</html>