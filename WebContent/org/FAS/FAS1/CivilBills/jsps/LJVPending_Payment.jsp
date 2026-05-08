<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adjustment Memo List</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
  
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
    
    
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
      <script type="text/javascript" src="../scripts/LJV_pending.js"></script>
    
    <script type="text/javascript" language="javascript">
     	 function loadyear_month()
         {
       		
	         var today= new Date(); 
	         var day=today.getDate();
	         var month=today.getMonth();
	         month=month+1;
	         var year=today.getYear();
	         if(year < 1900) year += 1900;
	       
	         document.SingleReceiptList.txtCB_Year.value=year;
	         document.SingleReceiptList.txtCB_Month.value=month;
        
         }
    </script>
    
</head>
<body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();" >
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">LJVs Pending for Payment</font>
          </div>
        </td>
      </tr>
</table>
 
<form name="SingleReceiptList" method="GET" action="../../../../../LJVSPendingServlet?Command=add">
<input type="hidden" id="Command" name="Command" value="add">
<div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
		            <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);">        
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
		             
		             <tr  align="left">
            <td>
                Report Option:
            </td>
            <td>
                <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                <input type=radio name=txtoption id=txtoption value="HTML">HTML
            </td>
            
        </tr>
		             
            </table>
      </div>
       <br>
       <table cellspacing="1" cellpadding="1" border="0" width="100%">
		       <tr align="left" class="tdH"> <th>Search By Month or Date</th></tr>
		        <tr align="left">
		          <td class="table">
		          	<div align="left">
		              	Accepted During Year &amp; Month&nbsp;&nbsp;<strong>:</strong>
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
				          
				          <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="doFunction('searchByMonth','null')"/>
		           </div>
		          </td>
		        </tr>
		        <tr align="left">
		          <td class="table">
			          <div align="left">
			              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong>
			                    <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
			                           maxlength="10" size="11"
			                           onfocus="javascript:vDateType='3';"
			                           onkeypress="return calins(event,this);"
			                           onblur="return checkdt(this);"/>
			                     
			                    <img src="../../../../../images/calendr3.gif"
			                         onclick="showCalendarControl(document.SingleReceiptList.txtFrom_date);"
			                         alt="Show Calendar"></img>
			           
			                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
			                           maxlength="10" size="11"
			                           onfocus="javascript:vDateType='3';"
			                           onkeypress="return calins(event,this);"
			                           onblur="return checkdt(this);"/>
			                     
			                     <img src="../../../../../images/calendr3.gif"
			                         onclick="showCalendarControl(document.SingleReceiptList.txtTo_date);"
			                         alt="Show Calendar"></img>
			            <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth" tabindex="8" onclick="doFunction('searchByDate','null')"/>
			            </div>
		          </td>          
		        </tr>
     </table>
     <br>
    
     <table id="mytable" align="center"  cellspacing="5" style="display:block"
         cellpadding="3" border="1" width="100%">
          <tr class="tdH">
            
            <th>Vorcher Number</th>
            <th>Vorcher Date</th>
            <th>Voucher Type</th>   
            <th>Remarks</th>            
            <th>Total Amount</th> 
             <th>Total Amount1</th> 
            <th>Not Cleared</th>
            <th>Partialy Cleared</th>             
            <th>Pending amount </th>
          </tr>
          <tbody id="tbody" class="table">                
          </tbody>
        </table> 
        <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
		 <tr>
		 <td>    
		  </td>
		  </tr>
	      <tr class="tdH">
		      <td>
		          <div align="center">
		           <input id="butSub" type="submit" value="Add" name="butSub"/>
		          <input id="butRpt" type="button" value="Report" name="butRpt" onclick="Report()"/>
		         
		         	<input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
		     	  </div>
		      </td>
	      </tr>      
      	</table>
</form>
</body>
</html>
