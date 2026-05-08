<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
                <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>  
                <meta http-equiv="cache-control" content="no-cache">
                <title>FAS Bill advance Applicable List</title>
              
                <link href="css/fas.css" rel="stylesheet"  media="screen"/>
                <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
                <script language="javascript" type="text/javascript">
                            function closeWindow()
                            {                
                                window.open('','_parent','');                
                                window.close(); 
                                window.opener.focus();
                            }
                </script>
                <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
                <script type="text/javascript"   src="../../ProceedingGeneration/scripts/sanction_proceed_master.js"></script>
                <script type="text/javascript"   src="../scripts/advance_applicable_master.js"></script>
  </head>
  <body onload="LoadBill_Majortype();LoadAllMinortype();callserver('get');"  class="table">
          <table cellspacing="1" cellpadding="3" width="100%" >
              <tr class="tdH">
                <td colspan="2">
                  <div align="center">
                    <font size="4">Advance Applicable for Bills</font>
                  </div>
                </td>
              </tr>
            </table>
   
    <form name="Bill_AdvanceApplicable_form" id="Bill_AdvanceApplicable_form" action="">
                <table cellspacing="1" cellpadding="3" border="1" width="100%">
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Bill Major Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_majr_code" id="txtbill_majr_code" tabindex="5" onchange="loadMinorType();">
                                    <option value="0">select bill major type</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Bill Minor Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_minr_code" id="txtbill_minr_code" tabindex="6" >
                                    <option value="0">select bill minor type</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                   <tr align="left" class="table"> 
                        <td>
                            <div align="left">
                                    Is Advance Applicable ?
                            </div>
                        </td>
                        <td>
                            <div align="left">
                                <table border="0">
                                    <tr> 
                                        <td>
                                            <div id="yes_applicable">
                                                <input type="radio" name="advance_applicable_YN" 
                                                    id="advance_applicable_YN" value="Y"/>YES
                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                            </div>   
                                        </td>
                                        <td>
                                            <div id="no_applicalbe">
                                                <input type="radio" name="advance_applicable_YN"
                                                    id="advance_applicable_YN" checked="checked"  value="N"/>NO
                                            </div>     
                                        </td>
                                    </tr>
                                </table>
                            </div>  
                    </tr>
                    <tr align="left" class="table"> 
                        <td>
                            <div align="left" >
                                Remarks
                            </div>
                        </td>
                        <td>
                            <div align="left" >
                                <textarea rows="5" cols="70"  name="txtbill_advremarks" id="txtbill_advremarks" onkeypress="return check_leng('remarks',this.value);" ></textarea>
                            </div>
                        </td>
                    </tr>
                </table>
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                    <tr class="tdTitle">
                        <td colspan="2" align="center">
                            <input type="button" name="cmd_add" value="ADD" id="cmd_add" onclick="callserver('add')"/>
                            <input type="button" name="cmd_update" value="UPDATE" id="cmd_update" 
                                onclick="callserver('update')" disabled="disabled"/>
                            <input type="button" name="cmd_delete" value="CANCEL" id="cmd_delete" 
                                onclick="callserver('delete')" disabled="disabled"/>
                            <input type="button" name="cmd_clear" value="CLEAR" id="cmd_clear" 
                                onclick="callrefresh1()"/>
                            <input type="button" name="cmd_exit" value="EXIT" id="cmd_exit"
                                onclick="exitmethod()"/>
                        </td>
                    </tr>
                </table>
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                    <tr>
                        <td colspan="2" align="center"><strong>Existing Details</strong></td>
                    <tr>
                </table>
                <table border="1" cellpadding="2" cellspacing="3" width="100%" id="dyntable">
                        <tr class="table">
                            <th class="tdH">Select</th>
                            <th class="tdH">Serial Number</th>
                            <th class="tdH">Bill MajorCode</th>
                            <th class="tdH">Bill MinorCode</th>
                            <th class="tdH">Advance Applicable</th>
                            <th class="tdH">Remarks</th>
                            <th class="tdH">Status</th>
                        </tr>
                        <tbody id="dynbody">
                        </tbody>
                </table>
    </form>
  </body>
</html>
                