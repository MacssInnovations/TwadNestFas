<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Master Asset Types Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/AssetMaster.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
<body onload="callServer('Get','null')" class="table">
<form action="" name="frmAssetMaster">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Asset Types Details</b></td>
                   
       </tr> 
        <tr>
            <td class="table">Asset Type Code</td>
          <td class="table">
            <input type="text" name="txtassettypecode" maxlength="2"
                   id="txtassettypecode" size="3"/>
           </td>
           
        </tr>
        <tr>
            <td class="table">Asset Type Description</td>
            <td class="table">
            <input type="text" name="txtassettypedesc" size="30" maxlength="25"
                   id="txtassettypedesc"/>
        </tr>
        
        <tr>
          <td colspan="2" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="CANCEL"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll()"/>
            <input type="button" name="Exit" value="Exit" onclick="closeWindow()">
          </td>
        </tr>
    </table>
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
          <td class="table"><b>Existing Details</b></td>
        </tr>
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Asset Type Code
          </th>
          <th>
            Asset Type Description
          </th>
          <th>
            Status 
          </th>
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
    
  </form>
</body>
</html>