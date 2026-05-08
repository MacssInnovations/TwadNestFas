<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Centage Category Master</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href='css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
            
    <script type="text/javascript" src="../scripts/Centage_Category_Master.js"></script>
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

    
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
    </script>
  </head>
  <body onload="foc();LoadAccountingUnitID('LIST_ALL_UNITS')" bgcolor="rgb(255,255,225)"><table cellspacing="1"
                                                         cellpadding="3"
                                                         width="100%">
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Centage Category Master</font>
          </div>
        </td>
      </tr>
    </table><form name="frmCentageCategoryMaster" id="frmCentageCategoryMaster"
                  action="#">
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="0" width="100%">
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
                        tabindex="1" onchange="common_LoadOffice()"></select>
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
                        tabindex="2" ></select>
              </div>
            </td>
          </tr>
          
      
      
        <tr class="table">
          <td>
            <div align="left">
              Category Code 
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <input type="hidden" name="txtCategory_Code" id="txtCategory_Code"></input>System Generated
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Category Description 
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <textarea rows="4" cols="50" name="txtCategory_Desc"
                        id="txtCategory_Desc"></textarea>
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Category Type 
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <input type="text" id="cmbCategoryType" name="cmbCategoryType" value="Specific" readonly>
            </div>
          </td>
        </tr>
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <table>
                <tr>
                  <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd"
                           onclick="doFunction_ccm('Add','null')"
                           tabindex="20"/>
                  </td>
                  <td>
                    <input type="button" name="cmdUpdate" value="UPDATE"
                           id="cmdUpdate" style="display:none"
                           onclick="doFunction_ccm('Update','null')"
                           tabindex="30"/>
                  </td>
                  <td>
                    <input type="button" name="cmdDelete" value="DELETE"
                           id="cmdDelete" style="display:none"
                           onclick="doFunction_ccm('Delete','null')"
                           tabindex="40"/>
                  </td>
                  <td>
                    <input type="button" name="cmdClear" value="CLEAR"
                           id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                  </td>
                  <td>
                    <input type="button" name="cmdList" value="LIST"
                           id="cmdList" onclick="ListHeads()" tabindex="60"/>
                  </td>
                  <td>
                    <input type="button" id="Exit" name="Exit" value="EXIT"
                           onclick="exit()" tabindex="70"/>
                  </td>
                </tr>
              </table>
            </div>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>