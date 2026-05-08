<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Accounting Unit Master</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Asset_Rendering.js"></script>
    
    <!-- script type="text/javascript" src="../scripts/Asset_Rendering_DateCheck.js"></script-->
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    
    <script type="text/javascript" src="../scripts/Asset_Rendering_ListAll.js"></script>
    
    <!-- script type="text/javascript" src="../../../../Library/scripts/CalendarControl.js"></script-->
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
    
    
    <script language="javascript" type="text/javascript">
				function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body class="table" onload="Load_circles()">
  <form action="../../../../../AccountingUnitServlet.con?command=Add" name="frmAccountUnit" id="frmAccountUnit" method="post" onsubmit="return nullcheck1()">
  <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="3" class="tdH" align="center"><b>Assets Account Rendering Units - Maintained by HO</b></td>
                   
       </tr> 
       
       
       
       
         <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code of Asset Rendering Unit
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                 
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="LoadOffice(this.value);refreshButtons();">
                
                </select>
              </div>
            </td>
          </tr>
          
          
          
          <tr class="table">
            <td>
              <div align="left">
                Accounting For Office Code of Asset Rendering Unit
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2">
                
                </select>
              </div>
            </td>
          </tr>
          
          
       
       
       
       <tr >
            <td class="table">Date Effect From:</td>
            <td class="table">
            <input type="text" name="txtDtFrm" id="txtDtFrm" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="10">
            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtDtFrm'),1);" alt="Show Calendar"
                                 height="24" width="19"></img>
            </td>
       </tr>
       <tr>
            <td class="table">Active:</td>
            <td class="table">
	            <input type="radio" name="radActive" id="radActive" value="Y" onclick="document.getElementById('trDtTo1').style.display='none';document.getElementById('trDtTo2').style.display='none';" checked>Yes &nbsp;
	            <input type="radio" name="radActive" id="radActive" value="N" onclick="document.getElementById('trDtTo1').style.display='block';document.getElementById('trDtTo2').style.display='block';" >No
            </td>
       </tr>
      
       <tr id="trDtTo" class="table" width="100%">
            <td class="table"><div id="trDtTo1" style="display:none;" > Date Effect Upto:</div></td>
            <td class="table">
            <div id="trDtTo2" style="display:none;" >
            <input type="text" name="txtDtTo" id="txtDtTo" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="10">
            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtDtTo'),1);" alt="Show Calendar"
                     height="24" width="19"></img>
             </div>
            </td>
       </tr>
      <!-- 
       <tr id="trDtTo" style="display='inline'" class="table" width="100%">
            <td class="table"><div id="trDtTo1"> Asset Rendered For:</div></td>
            <td class="table">
            <div id="trDtTo2">
            	<select id="accUnitRenderedFor" name="accUnitRenderedFor">
            		<option value="">--Select A/c Unit--</option>
            	</select>
            </div>
            </td>
       </tr>
       -->
    </table>
        
    
    <table cellspacing="2" cellpadding="3" border="0" width="50%" align="center">
       <tr style=" width : 869px;">
			<td class="tdH">
				<input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add','null')"/>
			</td>
	        <td class="tdH">    
	            <input type="button" name="CmdUpdate" value="UPDATE" id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
	        </td>
	        <td class="tdH">
	            <input type="button" name="CmdDelete" value="CANCEL" id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
	        </td>
	        <td class="tdH">
	            <input type="button" name="CmdClear" value="CLEAR" id="CmdClear" onclick="clearAll()"/>
	        </td>
	        <td class="tdH">
	            <input type="button" name="CmdList" value="LIST ALL" id="CmdList" onclick="ListAll()">
	        </td>
	        <td class="tdH">
	            <input type="button" name="CmdClose" value="EXIT" id="CmdList" onclick="closeWindow()">
	        </td>
       </tr>
    </table>
   
    
  </form>
  </body>
</html>