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
    <title>Centage Rates Master</title>
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
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
            
            
            
            
    <script type="text/javascript" src="../scripts/Centage_Rates.js"></script>
    
    <script type="text/javascript" language="javascript">
         function foc()
         {         
          
         }
    </script>
  </head>
  
  <body onload="loadfyr();foc();LoadAccountingUnitID('LIST_ALL_UNITS')" bgcolor="rgb(255,255,225)"><table cellspacing="1"
                                                         cellpadding="3"
                                                         width="100%">
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Centage Rate Master</font>
          </div>
        </td>
      </tr>
    </table><form name="frmCentageRates" id="frmCentageRates"
                  action="#">
                  
                  
                  
      <table cellspacing="1" cellpadding="2" border="1" width="100%">      
      
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
              <select name="txtCategory_Code" id="txtCategory_Code" >
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
               Centage Rate
              <font color="#ff2121">*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtCentageRates" id="txtCentageRates" onkeypress="return numbersonly1(event)" onblur="return RateValidation(this.value)" ></input>
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
              <select name="txtFinancialYear" id="txtFinancialYear">
                <option value="2000-2001">2000-2001</option>
                <option value="2001-2002">2001-2002</option>
                <option value="2002-2003">2002-2003</option>
                <option value="2003-2004">2003-2004</option>
                <option value="2004-2005">2004-2005</option>
                <option value="2005-2006">2005-2006</option>
                <option value="2006-2007">2006-2007</option>
                <option value="2007-2008">2007-2008</option>
                <option value="2008-2009">2008-2009</option>
                <option value="2009-2010">2009-2010</option>
                <option value="2010-2011">2010-2011</option>
                <option value="2011-2012">2011-2012</option>
                <option value="2012-2013">2012-2013</option>
                <option value="2013-2014">2013-2014</option>
                <option value="2014-2015">2014-2015</option>
                <option value="2015-2016">2015-2016</option>    
                <option value="2016-2017">2016-2017</option>    
                <option value="2017-2018">2017-2018</option>    
                <option value="2018-2019">2018-2019</option>    
                <option value="2019-2020">2019-2020</option>    
                <option value="2020-2021">2020-2021</option>  
              </select>              
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
    </form></body>
</html>