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
    <title>List of CashBook Details</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/Journal_Bill_ListAll.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/fAs_Journal_List.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>     
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>     
    <script type="text/javascript" language="javascript">
    function ChkMonth(val)
    {
  
    document.getElementById("Hid_text").value=val;
    document.getElementById("fir_td").style.display="block";
     document.getElementById("sec_td").style.display="block";
      document.getElementById("thrd_td").style.display="none";
       document.getElementById("frth_td").style.display="none";
    }
    function ChkDate(val)
    {
   
      document.getElementById("Hid_text").value=val;
       document.getElementById("fir_td").style.display="none";
     document.getElementById("sec_td").style.display="none";
      document.getElementById("thrd_td").style.display="block";
       document.getElementById("frth_td").style.display="block";
    }
    
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmJournal_List.txtCB_Year.value=year;
        document.frmJournal_List.txtCB_Month.value=month;
        
         }
         function Report_Chnge()
                  {
                	  var optom_val=document.getElementById("Re_type").value;
if(optom_val=="2"){
	document.getElementById("All_Disp").style.display="none";
	document.getElementById("All_DispX").style.display="none";
	document.getElementById("Bank_Disp").style.display="block";
	document.getElementById("Bank_DispX").style.display="block";
	document.getElementById("AcNo_Disp").style.display="none";
	document.getElementById("AcNo_DispX").style.display="none";
		
}  else if(optom_val=="3")
	{
	document.getElementById("All_Disp").style.display="none";
	document.getElementById("All_DispX").style.display="none";
	document.getElementById("Bank_Disp").style.display="none";
	document.getElementById("Bank_DispX").style.display="none";
	document.getElementById("AcNo_Disp").style.display="block";
	document.getElementById("AcNo_DispX").style.display="block";
	}else{
		document.getElementById("All_Disp").style.display="block";
		document.getElementById("All_DispX").style.display="block";
		document.getElementById("Bank_Disp").style.display="none";
		document.getElementById("Bank_DispX").style.display="none";
		document.getElementById("AcNo_Disp").style.display="none";
		document.getElementById("AcNo_DispX").style.display="none";
	}
                  }
    </script>
      </head>
       <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table"  onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1')">
  <form name="frmJournal_List" method="GET" action="../../../../../fas_journal_Report">
   <input type="hidden" id="Hid_text" name="Hid_text" value="">
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>List of CashBook Details</strong>
          </div>
        </td>
      </tr>
    </table><!--  
     <div align="center">
             <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
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
          </div>-->
    <br>
       <table cellspacing="1" cellpadding="1" border="0" width="100%">
     <!--   <tr align="left" class="tdH"> <th colspan="2">Search By Month or Date</th></tr> -->
        <tr align="left">
          <td class="table" width="35%">
          <div align="left">
          Report Details Type          
          </div></td>
           <td class="table" width="35%">
            <div align="left"><input type="radio" id="Rad_DetMnt" name="Rad_Det" value="MonthWise" checked="checked" onchange="ChkMonth(this.value);">MonthWise
            <input type="radio" id="Rad_DetD" name="Rad_Det" value="DateWise" onchange="ChkDate(this.value);">DateWise</div></td>
          </tr>
        <tr align="left">
          <td class="table" width="35%">
          <div align="left" id="fir_td" style="display: block;" >
              Cash Book Year &amp; Month&nbsp;&nbsp;</div></td>
              <td width="65%" >  <div align="left" id="sec_td" style="display: block;">
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
           
           </div>
          </td>
        </tr>
        <tr align="left">
          <td class="table"  width="35%">
          <div align="left" id="thrd_td" style="display: none;">
              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
              <td width="65%"><div align="left" id="frth_td" style="display: none;">
                                   <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_List.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_List.txtTo_date);"
                         alt="Show Calendar"></img>
            
            </div>
          </td>          
        </tr>
                          <tr>
                      <td width="35%">Report Type</td>
                      <td width="65%"><select id="Re_type" name="Re_type" onchange="Report_Chnge();">
                      <option value="">--Select--</option>
                      <option value="1">All</option>
                      <option value="2">Bank Wise</option>
                      <option value="3">Account Number Wise</option>
                      
                      </select></td>
                      </tr>                 
                    <tr>
                   
                        <td  width="35%">
                        <div id="All_Disp" style="display: block;">
                            Bank Wise / Account Number Wise 
                        </div>
                        <div id="Bank_Disp" style="display: none;">
                            Bank Wise 
                        </div>
                         <div id="AcNo_Disp" style="display: none;">
                             Account Number Wise 
                        </div>
                        </td>
                        <td width="65%">
                         <div id="All_DispX" style="display: block;">
                            <input type="radio" name="DisMode" id="DisMode" value="Bank" onclick="callCommon();" />Bank &nbsp;&nbsp;
                            <input type="radio" name="DisMode" id="DisMode" value="AccNo" onclick="callCommon();" />Account Number
                        </div>
                        <div id="Bank_DispX" style="display: none;">
                        <select id="BAnk_Sel" name="BAnk_Sel">
                      
					<option value="1">Reserve Bank Of India</option>
					<option value="2">State Bank of India</option>
					<option value="13">Bank Of India</option>
					<option value="15">Canara Bank</option>
					<option value="19">Indian Bank</option>
					<option value="20">India Overseas Bank</option>
					<option value="24">Punjab National Bank</option>
					<option value="25">Syndicate Bank</option>
					<option value="26">Union Bank of India</option>
</select>
                        </div>
                          <div id="AcNo_DispX" style="display: none;">
                          <input type="text" id="AcNo_text" name="AcNo_text">
                          </div>
                        </td>                        
                    </tr>
                    
                    
                    <tr>
                        <td  width="35%">
                            Report Option:
                        </td>
                        <td width="65%">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>                        
                    </tr>
     </table>
    
     <!-- 
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
            <th>
              Voucher Number
            </th>
            <th>
              Voucher Date
            </th>
             <th>
              Voucher Type
            </th>
            <th>
            Remarks
            </th>
            <th>
            Total Amount
            </th>
           <th>
            Show Details ?
            </th>
             <th>
            Print Journal?
            </th>
          </tr>
          <tbody id="tbody" class="table">
          
         
          </tbody>
        </table> -->
         <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            <tr>  <!-- 
  <td>

   <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
                     <tr class="tdH">
                  <td>
                    <table align="center" cellspacing="3" cellpadding="2"
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
  </td>-->
  </tr>
      <tr class="tdH">
      <td>
          <div align="center">
          <input type="submit" value="GO" name="ByMonth" id="ByMonth" tabindex="8"/>
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="window.close();">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>