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
    <title>A52-Register(Physical Verification)</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Asset_Rendering.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    
    <script type="text/javascript" src="../scripts/PhysicalVerification_js.js"></script>
    
 
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
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');callServer('loadMajor');">
  <form action="" name="phyVerification" method="get" >
  <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="3" class="tdH" align="center"><b>A52-Register(Physical Verification)</b></td>
                   
       </tr> 
       
       
       
       
         <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                 
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="LoadOffice(this.value);">
                
                </select>
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">
                Accounting For Office Code 
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
          
       <tr class="table">
            <td>
              <div align="left">
                Financial Year 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="fin_year" id="fin_year"  tabindex="2"><!--
                      <option value="2009-10">2009-2010</option>
                      <option value="2010-11">2010-11</option>-->
                      <option value="2011-12">2011-12</option>
						  <option value="2012-13">2012-13</option>
                       
                </select>
              </div>
            </td>
          </tr>
          
            <tr class="table">
                <td>
                  <div align="left">
                     Major Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbmajorasset" id="cmbmajorasset" tabindex="3" onblur="callServer('loadMinor');">
                    <option value=0>-- Select Major Asset Code --</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
              
                <tr class="table">
                <td>
                  <div align="left">
                     Minor Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbminorasset" id="cmbminorasset" tabindex="3" ><option value=0>-- Select Minor Asset Code --</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
              
       <tr >
            <td class="table">Verification Done on:</td>
            <td class="table">
            <input type="text" name="txtDtFrm" id="txtDtFrm"  onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="10">
            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtDtFrm'),1);" alt="Show Calendar"
                                 height="24" width="19"></img>
            </td>
       </tr><!--
       
      <tr class="table">
      
                <td>
                    Verified By &amp; Designation &amp; Place of Posting 
                </td>
                <td>
                  <div align="left">
	           <input type="text" name="verify_emp"  id="verify_emp"  maxlength="40" size="30"/>&nbsp;&nbsp;                   
                <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth" onclick="callGridItems()" tabindex="5" />
                  </div>
                </td>
      
              </tr>
       --><tr class="table">
      
               
                <td colspan=2>
                  <div align="center">
	                   
                <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth" onclick="checkStatus()" tabindex="5" />
                  </div>
                </td>
      
              </tr>
   </table>
    <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
              
            <th width="6%">
             Select
            </th>
            
            <th width="6%">
              Asset Code
            </th>
              <th width="6%">
             Particulars
            </th>
            <th width="6%">
              Qty as per A52
            </th>
            <th width="6%">Excess Qty</th>
            <th width="6%">Shortage Qty</th>
           <!--<th>usableCondition</th>-->
            <th width="6%">
            Qty Usable
            </th>
            <!--<th>
            Whether under non-usableCondition
            </th>-->
            <th width="6%">
            Qty Non-Usable
            </th>
            <!--<th>
            Whether can be made to usableCondition
            </th>-->
            <th width="6%">
            Qty can be made usable
            </th>
             <!--<th>
           Condemned/Disposed
            </th>-->
             <th width="5%">
           Qty to be Condemned /Disposed
            </th>
            <th width="10%">
            Reason
            </th>
          </tr>
          <tbody id="grid_body" class="table">
          </tbody>
        </table>    
      <table cellspacing="2" cellpadding="3" border="0" align="center">
       <tr>
			<td class="tdH">
				<input type="button" name="CmdAdd" value="SUBMIT" id="CmdAdd" onclick="addBtn();">
			</td>
	        
	        <td class="tdH">
	            <input type="button" name="CmdClose" value="CANCEL" id="CmdList" onclick="closeWindow()">
	        </td>
       </tr>
    </table>
   
    
  </form>
  </body>
</html>