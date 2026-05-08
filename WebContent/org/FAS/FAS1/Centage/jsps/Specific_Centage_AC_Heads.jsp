<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Scheme Specific Centage Account Heads </title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
   
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
   
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Account_Head_Popup_4Centage.js"></script>         
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>            
            
    <script language="javascript" type="text/javascript"
            src="../scripts/Specific_Centage_AC_Heads.js"></script>
            
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>     
    
    
    
  </head>
  
  <body class="table" onload="LoadAccountingUnitID('BOTH_UNITS_AND_OFFICES')">
  
  <form  action="#" name="frmSpecificCentageACHeads" id="frmSpecificCentageACHeads" >
           
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>Scheme Specific Centage Account Heads </center>
          </td>
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
                        tabindex="1" onchange="common_LoadOffice()"></select>
            <!--   <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                      tabindex="1" onchange="LoadOffice(this.value);">  --> 
               
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
              <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
               
              </select>
            </div>
          </td>
        </tr>        
       
        
        <tr class="table">
          <td>
            <div align="left">            
                 Work Expenditure Account Head Code                  
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
                <input type="text" name="txtWorkExpACHeadCode" id="txtWorkExpACHeadCode"
                            maxlength="6" 
                            onkeypress="return numbersonly(event)"                           
                            onblur="doFunction_ACHeadDesc_Load('ExpCode');"  size="9" ></input>
                 <img src="../../../../../images/c-lovi.gif"
                                 width="20" height="20" alt="AccountHeadList"
                                 onclick="AccHeadpopup('ExpCode');" id="WxpTourch" style="visibility:visible" ></img>
                 <input type="text" name="txtWorkExpACHeadCode_Desc" id="txtWorkExpACHeadCode_Desc" style="background-color: #ececec"  maxlength="125" size="60" readonly="readonly" ></input>             
                 
            </div>
          </td>
        </tr>         
       
         <tr class="table">
          <td>
            <div align="left">            
                 Project SL Code 
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
               <select name="txtProjectSLCode" id="txtProjectSLCode" >
                 <option value="" >-- Select Project SL Code -- </option>
               </select> 
                <input type="button" name="cmdGO" value="GO"
                           id="cmdGO" onclick="doFunction_ccm('ProjectSL_Code','null')"
                           tabindex="40"/>         
            </div>
          </td>
        </tr>         
         
       
         <tr class="table">
          <td>
            <div align="left">
               Default Centage Category    
               <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
               <select name="txtDftCentageCtgy" id="txtDftCentageCtgy" >
                 <option value="" >-- Select Category Code -- </option>
               </select> 
               <input type="button" name="cmdGO" value="GO"
                           id="cmdGO" onclick="doFunction_ccm('Load_Category_Code','null')"
                           tabindex="40"/>         
            </div>
          </td>
        </tr>         
       
       
         <tr class="table">
          <td>
            <div align="left">
              Credit Account Head Code   
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
                 <input type="text" name="txtCrACHeadCode" id="txtCrACHeadCode"
                            maxlength="6" 
                            onkeypress="return numbersonly(event)"                           
                            onblur="doFunction_ACHeadDesc_Load('CrCode');"  size="9"
                 ></input>
                 <img src="../../../../../images/c-lovi.gif"
                                 width="20" height="20" alt="AccountHeadList"
                                 onclick="AccHeadpopup('CrCode');"></img>
                 <input type="text" name="txtCrACHeadCode_Desc" id="txtCrACHeadCode_Desc" style="background-color: #ececec"  maxlength="125" size="60" readonly="readonly" ></input>             
                 
                 
            </div>
          </td>
        </tr>         
       
       
         <tr class="table">
          <td>
            <div align="left">
              Debit Account Head Code   
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
                 <input type="text" name="txtDrACHeadCode" id="txtDrACHeadCode"
                            maxlength="6" 
                            onkeypress="return numbersonly(event)"                           
                            onblur="doFunction_ACHeadDesc_Load('DrCode');"  size="9" ></input>
                            
                 <img src="../../../../../images/c-lovi.gif"
                                 width="20" height="20" alt="AccountHeadList"
                                 onclick="AccHeadpopup('DrCode');"></img>
                                 
                 <input type="text" name="txtDrACHeadCode_Desc" id="txtDrACHeadCode_Desc" style="background-color: #ececec"  maxlength="125" size="60" readonly="readonly" ></input>             
            </div>
          </td>
        </tr>         
       
       
         <tr class="table">
          <td>
            <div align="left">
               Remarks              
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
                <textarea rows="4" cols="50" name="txtRemarks" id="txtRemarks"></textarea>
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