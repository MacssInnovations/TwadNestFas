<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Authorization System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Authorization_for_BRS.js" ></script>
 
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    
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
                        
            document.frmAuthorizationBRS.txtCB_Year.value=year;
            document.frmAuthorizationBRS.txtCB_Month.value=month;

            if(day<=9 && day>=1)
                day="0"+day;
                if(month<=9 && month>=1)
               	  month="0"+month;
                var year=today.getYear();
                if(year < 1900) year += 1900;
                var monthArray =new Array("January", "February", "March", 
                          "April", "May", "June", "July", "August",
                          "September", "October", "November", "December");
               document.frmAuthorizationBRS.txtCrea_date.value=day+"/"+month+"/"+year;
				//setTimeout('check_ob()','900');
            
      }
    function LoadBankAccountNumber1()
    {
    	 document.frmAuthorizationBRS.cmbBankAccNo.length=0;
    	
    }
    
    </script>
    
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body  onload="LoadAccountingUnitID('LIST_ALL_UNITS');setTimeout('LoadBankAccountNumber()',900);" bgcolor="rgb(255,255,225)">
   
   
   
   
   
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">BRS Authorization </font>
          </div>
        </td>
      </tr>
    </table>
    
    
    	   
    
    <form name="frmAuthorizationBRS" id="frmAuthorizationBRS" method="POST"
                  action="../../../../../Authorization_for_BRS.kv?Command=Add"
                  onsubmit="return checkNull()">
                  
                  
       <div align="center">
       
       
             <input type="hidden" name="txtCrea_date" id="txtCrea_date"  />    
             <input type="hidden" name="txtTotTrans" id="txtTotTrans" value="0" />              
                           
       
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
               
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);LoadBankAccountNumber1();" >
                    </select>
                  </div>                </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >                      
                    </select>
                  </div>                </td>
              </tr>
              
              
              
              
             
            
                 <tr class="table" >
                <td width="40%">
                  <div align="left">
                     Bank A/C No. <font color="#ff2121">*</font>
                  </div>
                </td>
                 <td width="60%">
                  <div align="left">
                     <select name="cmbBankAccNo" id="cmbBankAccNo">
                      <option value="">-- Select Bank A/C No ---</option>
                     </select>   
                     
                     <input type="button" name="goo" id="goo" value="Go" onclick="LoadBankAccountNumber();"></input>
                     <input type="hidden" name="txtOprMode" id="txtOprMode"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly"  size="50"/>
                  </div>
                </td>
              </tr>
            
            
              <tr class="table">
                <td width="40%">
                  <div align="left">
                     Transaction Type 
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="radio" name="TransType" id="TransType" value="T" checked onclick="TWAD_NONTWAD(this.value)" />TWAD Trasactions &nbsp;&nbsp;                     
                    <input type="radio" name="TransType" id="TransType" value="NT"  onclick="TWAD_NONTWAD(this.value)" />Non-TWAD Trasactions                    
                  </div>
                </td>
              </tr>

				<tr class="table">
                <td width="40%">
                  <div align="left">
                    PassSheet Year & Month  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                   <div align="left">
	                  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
	                     maxlength="4" size="5" 
	                     onkeypress="return numbersonly(event)"></input>
	               
	            	  <select name="txtCB_Month" id="txtCB_Month" tabindex="4"  onchange="brsMonth();">
                                <option value="0">select Month</option>
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
	              	  <input type="button" name="List" id="List" value="GO" onclick="doFunction('Load_TransDetails')" />
                  </div>
                </td>
            </tr>
            
                 <tr align="left">
          <td class="table">
          <div align="left">
              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong>
              </div></td>
               <td class="table"> <div align="left">
                    <input type="text" name="txtFrom_date" id="txtFrom_date"   tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                          onblur="brs_year(1);return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmAuthorizationBRS.txtFrom_date);" 
                       alt="Show Calendar"></img>
                       <!--   onclick="showCalendarControl(document.frmAuthorizationBRS.txtFrom_date);setTimeout('beforeTest()',900);" 
                       onclick="showCalendarControl(document.frmAuthorizationBRS.txtTo_date);setTimeout('beforeTest_one()',900);"
                       -->
                         
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="brs_year(2);return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAuthorizationBRS.txtTo_date);"
                         alt="Show Calendar"></img>
            <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth" tabindex="8" onclick="doFunction('Load_TransDetails_date')"/>
            </div>
          </td>          
        </tr>
              
              <tr class="table">
                <td width="40%">
                  <div align="left">
                    Reference Number 
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtReferNO_edit"
                           id="txtReferNO_edit" maxlength="15" size="16"/>
                  </div>
                </td>
			              </tr>
		
				<tr class="table">
					<td width="40%">
					<div align="left">Reference Date</div>
					</td>
					<td width="60%">
					<div align="left"><input type="text" name="txtReferDate_edit"
						id="txtReferDate_edit" maxlength="10" size="11"
						onfocus="javascript:vDateType='3';"
						onkeypress="return calins(event,this);" onblur="call_date(this);" />
					<img src="../../../../../images/calendr3.gif"
						onclick="showCalendarControl(document.frmAuthorizationBRS.txtReferDate_edit,1);"
						alt="Show Calendar"></img></div>
					</td>
				</tr>

				<tr class="table">
                <td width="40%">
                  <div align="left">
                    Remarks 
                <font color="#ff2121">
                  *
                </font>
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <textarea name="txtRemak_edit" id="txtRemak_edit" cols="60"  onkeypress="return check_leng(this.value);"
                              rows="3"></textarea>  
                              <input type="hidden" id="txtTotTrans" name="txtTotTrans"></input>                             
                  </div>
                </td>
              </tr>
              
            </table>
          </div>
      
      
      
           <div id="TWAD" name="TWAD" style="display:block">           
             <table cellspacing="1" cellpadding="2" border="0" width="100%">
               <tr>
                 <td colspan="2">
                   <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2" border="0" width="100%">
                      <tr class="table">
                       <th>Select
                         <a href="javascript:selectAll('ALL');">All</a>
                         <a href="javascript:selectAll('UNSelect');">Unselect</a> 
                      </th>
                        <th > Challan No </th>
                        <th >PassBook Date</th>
                        <th > Doc No. / Type </th>
                         <th >Sl No</th>
                        <th > Remittance Date </th>
                        <th > Withdrawal Date </th>                        
                        <th >Cheque / DD No</th>
                        <th >CR Amount </th>
                        <th >DR Amount </th>
                         <th >Amount in PassBook </th>
                       </tr>
                       
                       <tbody id="grid_body_TWAD" class="table" align="left" >
                       </tbody>
                       
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>    
          
          
          <div id="NON_TWAD" name="NON_TWAD" style="display:none">       
          <table cellspacing="1" cellpadding="2" border="0" width="100%">
               <tr>
                 <td colspan="2">
                   <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2" border="0" width="100%">
                      <tr class="table">
                        <th>Select
                         <a href="javascript:selectAll_two('ALL');">All</a>
                         <a href="javascript:selectAll_two('UNSelect');">Unselect</a> 
                      </th>
                        <th > PassBook Date </th>
                         <th > Sl No</th>
                        <th > Particulars </th>                        
                        <th > Cheque / DD No</th>
                        <th > CR Amount </th>
                        <th > DR Amount </th>
                       </tr>                       
                       <tbody id="grid_body_NONTWAD" class="table" align="left" >
                       </tbody>                       
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>   
           
      
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
               <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="exit();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
      
      
    </form>   
   </body>
</html>