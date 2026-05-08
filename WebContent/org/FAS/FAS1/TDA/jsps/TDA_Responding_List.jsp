<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>List of TDA/TCA for Suspense Head</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/TDA_Responding_List.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
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
	       
	        document.respond_List.txtCB_Year.value=year
	        document.respond_List.txtCB_Month.value=month;
        
    }
    </script>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');" >
  <form name="respond_List" method="POST">
         <input type="hidden" id="hid" name="hid" value="Nonsup">
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>List of TDA/TCA for Suspense Head</strong>
          </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
         <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);call_clr();">        
                         </select>
                      </div>
                    </td>
             </tr>           
              <tr class="table">
                    <td>
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
                        </select>
                      </div>
                    </td>
             </tr> 
              <tr>
		            <td class="table">Journal Type</td>
		            <td class="table">
			       <!--     <input type="radio" name="radActive" id="radActive" value="Pending" checked>TDA Responding Pending &nbsp;
			            <input type="radio" name="radActive" id="radActive" value="Cleared" >TDA Responding Cleared  -->
                                    <select name="radActive" id="radActive" tabindex="4" onchange="showGrid()">
                                          <option value="">Choose Journal Type</option>
                                          <option value="tdaPending">TDA Responding Pending</option>
                                          <option value="tdaCleared">TDA Responding Cleared Against month</option>
                                          <option value="tdaClearedDuring">TDA Responding Cleared During The Month</option>
                                          <option value="tcaPending">TCA Responding Pending</option>
                                          <option value="tcaCleared">TCA Responding Cleared Against month</option>
                                          <option value="tcaClearedDuring">TCA Responding Cleared During The Month</option>
                                          
                                      </select>
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
                      <option value="C">Cancelled</option> 
                    </select>
                   </div>
                </td>
              </tr>                           
        </table>
          </div> 
    <br>
       <table cellspacing="1" cellpadding="1" border="0" width="100%">
       <tr align="left" class="tdH"> <th>Search By Month or Date</th></tr>
        <tr align="left">
          <td class="table">
          <div align="left" id="bymonth">
              Cash Book Year &amp; Month&nbsp;&nbsp;<strong>:</strong>
	          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
	          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
          
           <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="doFunction('searchByMonths','null')"/>
           </div>
          </td>
        </tr>
        <tr align="left">
          <td class="table">
          <div align="left" id="bydate">
              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong>
                    <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.respond_List.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.respond_List.txtTo_date);"
                         alt="Show Calendar"></img>
            <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth" tabindex="8" onclick="doFunction('searchByDate','null')"/>
            </div>
          </td>          
        </tr>
     </table>
     <br>
     <div id="pendingDiv" style="display:block">
     	<table id="mytable" align="center"  cellspacing="3" 
	         cellpadding="2" width="100%">
	         <tr class="tdH">
		        	
		            <th align="centre">
		               Response Pending
		            </th>
		            
		            
	         </tr>
         </table>
         <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
            <th >
              Voucher Number
            </th>
            <th>
              Voucher Date
            </th>
            <th>
            Remarks
            </th>
            <th>
            Total Amount
            </th>
             <th>       	
            Accepting Unit            	
            </th>            
            <th>
            Show Details ?
            </th>
          </tr>
          <tbody id="tbody" class="table">          
          </tbody>
        </table>
        </div>
        <div id="clearedDiv" style="display:none">
        <table id="mytable" align="center"  cellspacing="3" 
	         cellpadding="2" width="100%">
	         <tr class="tdH">
		            <th align="centre">
		               Response Cleared
		            </th>
		            
	         </tr>
         </table>
     <table id="mytable1" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
            <th>
              Voucher Number
            </th>
            <th>
              Voucher Date
            </th>
            <th>
            Remarks
            </th>
            <th>
            Total Amount
            </th>
             <th>       	
            Accepting Unit            	
            </th>            
            <th>
            Responding JVR No
            </th>
            <th>
            Responding JVR Date
            </th>
            <th>
            Show Details ?
            </th>
          </tr>
          <tbody id="tbody1" class="table">          
          </tbody>
        </table>
        </div>
         <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            <tr>
                <td>
                    <table align="center" cellspacing="3" cellpadding="2" border="1"  width="100%">
                     <tr class="tdH">
                        <td>
                            <table align="center" cellspacing="3" cellpadding="2"  border="0" width="100%">
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
                                                        Page&nbsp;&nbsp;<select name="cmbpage"  id="cmbpage" onchange="changepage()"></select>
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
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>