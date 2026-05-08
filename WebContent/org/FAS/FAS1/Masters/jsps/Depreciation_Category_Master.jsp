<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Depreciation Category Master</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Depreciation_Category_Master.js"></script>
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
  <!--onload="callServer('Get','null')"-->
  <body onload="callServer('Get','null');" class="table">
  <form action="" name="frmDepreciationCatMaster">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="3" class="tdH" align="center"><b>Depreciation Category Master</b></td>
                   
       </tr> 
        <tr>
            <td class="table">Depreciation Category Code</td>
          <td class="table">
            <input type="text" name="txtDepreciationCatCode" maxlength="2"
                   id="txtDepreciationCatCode" readonly size="3"/>System Generated
           </td>
           
        </tr>
        <tr>
            <td class="table">Depreciation Category Description</td>
            <td class="table">
	            <input type="text" name="txtDepreciationCatDesc" id="txtDepreciationCatDesc" onchange="callServer('Load','null')"/>
            </td>
        </tr>

        <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled />
            <input type="button" name="CmdDelete" value="CANCEL"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled />
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
          	Depreciation Category Code
          </th>
          <th>
          	Depreciation Category Description
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