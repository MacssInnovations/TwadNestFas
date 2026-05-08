<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Convert General Journal to Liability Journal </title>
    
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/Library/scripts/checkDate.js"></script>
            
    <script language="javascript" type="text/javascript"
            src="../scripts/Convert_GJV_To_LJV.js"></script>
            
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          
    <script type="text/javascript"
            src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
            
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
            
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;       
         document.frmJournal_ListAll.txtCB_Year.value=year;    
         
         }
    </script>
  </head>
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')">
  <form name="frmJournal_ListAll"  method="POST" action="../../../../../Convert_GJV_To_LJV.kv">
   
 
      <table cellspacing="4" cellpadding="1" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Convert General Journal to Liability Journal</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="4" cellpadding="1" border="0" width="100%">
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
                        tabindex="1" onchange="LoadOffice(this.value);doFunction('searchByMonth','null')" >
                 
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
              <!--  This is used to load details about login user( super user or oridinary user ) login and their unit id-->
              <div align="left">
              
              </div>
              <!--end -->
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
                Voucher Status 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbStatus" id="cmbStatus" tabindex="3">
                  <option value="L">Alive</option>                  
                </select>
              </div>
            </td>
          </tr>
          
          
          
          <tr class="table">          
          <td>          
            <div align="left">
              Cash Book Year &amp; Month&nbsp;&nbsp;<strong>:</strong>  <font color="#ff2121">*</font>                
            </div>  
          </td>
          
          <td>              
            <div align="left">
              <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="doFunction('searchByMonth','null')">              
                <option value=""> -- Select the Month -- </option> 
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
      </div>
      <br></br>      
      
      
      <table id="mytable" align="center" cellspacing="1" cellpadding="1"
             border="0" width="100%">
        <tr class="tdH">
          <th>Select </th>
          <th>Voucher Number</th>
          <th>Voucher Date</th>
          
          <th>Old Voucher Type</th>
          <th>New Voucher Type</th>
          <th> </th>
          <th>Remarks</th>
          <th>Total Amount</th>
          <th>Show Details ?</th>          
        </tr>
        
        <tbody id="tbody" class="table">          
         
        </tbody>
        
      </table>
      
      
      
      
      
      <table align="center" cellspacing="1" cellpadding="1" border="0"
             width="100%">
        <tr>
          <td>
            <table align="center" cellspacing="1" cellpadding="1" border="0"
                   width="100%">
              <tr class="tdH">
                <td>
                  <table align="center" cellspacing="1" cellpadding="1"
                         border="0" width="100%">
                    <tr>
                      <td width="30%">
                        <div align="left">
                          <div id="divpre" style="display:none"></div>
                        </div>
                      </td>
                      <td width="40%">
                        <div align="center">
                          <table border="0">
                            <tr>
                              <td>
                                <div id="divcmbpage" style="display:none">
                                  Page&nbsp;&nbsp;<select name="cmbpage"
                                                          id="cmbpage"
                                                          onchange="changepage()"></select>
                                </div>
                              </td>
                              <td>
                                <div id="divpage"></div>
                              </td>
                            </tr>
                          </table>
                        </div>
                      </td>
                      <td width="30%">
                        <div align="right">
                          <div id="divnext" style="display:none"></div>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" id="cmdConvert" name="Convert" value="Convert"></input>              
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="btncancel()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>