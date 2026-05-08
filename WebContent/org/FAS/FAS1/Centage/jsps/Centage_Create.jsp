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
    <title>Centage Create</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
            
    <script language="javascript" type="text/javascript"
            src="../scripts/Centage_Create.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>
            
    <script type="text/javascript" language="javascript">
    
   
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
             month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmIndependentCentageACHeads.txtCB_Year.value=year;
        
        document.frmIndependentCentageACHeads.txtCB_Month.value='';        
         }
    </script>
  </head>
  <body class="table"
        onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');"><form action=""
                                                                                       name="frmIndependentCentageACHeads"
                                                                                       id="frmIndependentCentageACHeads"
                                                                                       method="POST" >
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>Centage Create</center>
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
            <div align="left">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
              <select name="txtCB_Month" id="txtCB_Month" onchange="PendingJournals_11()" tabindex="4"> 
                <option value="">-- Select the Month ---</option>
                <option value="1">January</option>
                <option value="2">February</option>
                <option value="3">March</option>
                <option value="4">April</option>
                <option value="5">May</option>
                <option value="6">June</option>
                <option value="7">July</option>
                <option value="8">August</option>
                <option value="9">September</option>
                <option value="10">October</option>
                <option value="11">November</option>
                <option value="12">December</option>
              </select>
            </div>
          </td>
        </tr>
      </table>
      
      
      <table cellspacing="1" cellpadding="1" border="0" >
        <tr>
          <td colspan="2">
            <div id="sub_ledge_dis"></div>
            <div id="grid" style="display:block">
              <table id="mytable" cellspacing="1" cellpadding="1" border="0"
                     width="100%">
                     
                     
                <tr class="table">
                  <th class="tdH" rowspan="2" >Select</th>
                  <th class="tdH" rowspan="2" >LJV No.</th>
                  <th class="tdH" rowspan="2" >LJV Date</th>
                  <th class="tdH" rowspan="2" >Wexp HOA Code</th>                  
                  <th class="tdH" rowspan="2" >Project Code</th>    
                  <th class="tdH" rowspan="2" >Category Code</th>    
                  <th class="tdH" rowspan="2" >Centage category</th>
                  <th class="tdH" rowspan="2" >Centage % </th>
                  <th class="tdH" colspan="2" >Debit Details</th>                  
                  <th class="tdH" colspan="2" >Credit Details</th>
                  <th class="tdH" rowspan="2" >Remarks</th>     
                  <th class="tdH" rowspan="2" >SL Type Code</th>     
                </tr>                
                
                <tr>
                    <th class="tdH" >HOA</th>
                    <th class="tdH" >Amount</th>
                    <th class="tdH" >HOA</th>
                    <th class="tdH" >Amount</th>
                </tr>
                
                 <tbody id="grid_body" class="table" align="left" >
                 </tbody>
              </table>
            </div>
          </td>
        </tr>
        
        <tr>
         <td align="center">
         
           <input type="hidden" value="" id="TotalRecords" name="TotalRecords" />
           <input type="button" value="Create" id="create" onclick="btSubmit()" /> 
           <input type="button" value="Exit" id="exit" onclick="self.close()" /> 
           
         </td>
        </tr>
        
      </table>
      
      
    </form></body>
</html>