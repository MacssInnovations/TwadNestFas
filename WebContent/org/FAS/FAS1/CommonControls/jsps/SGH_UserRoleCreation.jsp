<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Section User Role Creation</title>
          
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>          
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>            
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/SGH_UserRoleCreation.js"></script>            
     
  </head>
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');callServer('combo','null');clearAll()">
  <form name="frmSGH_UserRoleCreation"
                                                      method="POST"
                                                      action="../../../../../SubLedgerMasterServlet_MonthEnd.con"
                                                      onsubmit="return checknull()">
      <table cellspacing="2" cellpadding="3" width="100%">
      
      
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Section User Role Creation</strong>
            </div>
          </td>
        </tr>
      </table>
      
      
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
                        tabindex="1" onchange="LoadOffice(this.value);"></select>
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
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
          
          <tr align="left">
            <td class="table">
              User ID
            </td>
            <td>
              <div align="left">
              
                <input type="text" name="txtUserID" id="txtUserID"  
                       tabindex="3" maxlength="12" size="12"  onblur="callServer('LoginUid','null')" ></input>   
                       
              </div>
            </td>
          </tr>   
          
          
          <tr align="left">
            <td class="table">
              Employee ID
            </td>
            <td>
              <div align="left">
              
                <input type="text" name="txtEmpID" id="txtEmpID"
                       tabindex="3" maxlength="5" size="6" readonly ></input>   
                       
                <input type="text" name="txtEmpName" id="txtEmpName"
                       tabindex="3" maxlength="40" size="40" readonly></input>   
              </div>
            </td>
          </tr>   
          
          
          <tr align="left">
            <td class="table">
              Sections
            </td>
            <td>
              <div align="left">                
                <select name="txtSection" id="txtSection" tabindex="4" >                 
                   <option value=" ">-- Select Section --- </option>
                </select>
              </div>
            </td>
          </tr>   
          
          
          
          
        </table>
      </div>      
      
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
            
               <input type="Button" value="  Grant " id="Grant" onclick="callServer('Add','null')" name="cmdAdd"> 
               <input type="Button" value=" Revoke" id="Revoke" onclick="callServer('Delete','null')" disabled name="cmdDelete">
               <input type="Button" value="Clear All" onclick="clearAll()" name="cmdClearAll">                                 
               <input type="button" name="CmdExit" value="EXIT" id="CmdExit" onclick="Exit()" align="middle"/>
               
            </div>
          </td>
        </tr>
      </table>
      
      
       <table name="Existing" id="Existing"  border="0" width="100%"  >
       
       <tr class="tdH">
           <th >View</th>
           <th >Section Id</th>
           <th >Section Name</th>           
       </tr>
           <tbody id="tblList" name="tblList">
           </tbody>
       </table>
      
      
    </form>
  </body>
</html>