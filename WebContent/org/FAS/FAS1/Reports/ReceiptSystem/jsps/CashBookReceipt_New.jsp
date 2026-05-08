<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/CashBookReceipt.js"></script>
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    
   <!--   <script type="text/javascript" src="../../../../../../org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script> -->
    <script type="text/javascript" src="../../../../../../org/FAS/FAS1/CommonControls/scripts/Common_Bank_Name_Loading.js"></script> 
 <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
  <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
     
    
        
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                  function loadyear_month()
                 {
               
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
               
                document.frmReport.txtCB_Year.value=year;
                document.frmReport.txtCB_Month.value=month;
                
                 }
                  function blockHead()
                  {
                     
                  	if(document.frmReport.DisMode[0].checked==true)
                  	{
                  		
                  		document.getElementById("head_div1").style.display="none";
                  		document.getElementById("head_div2").style.display="none";
                  	}
                  	else
                  	{
                  		
                  		document.getElementById("head_div1").style.display="block";
                  		document.getElementById("head_div2").style.display="block";
                  		
                  	}
                  }
                  function month_cli(){
                		document.getElementById("month1").style.display="block";
                  		document.getElementById("month2").style.display="block";
                  		document.getElementById("date1").style.display="none";
                  		document.getElementById("date2").style.display="none";
                  		document.getElementById("hid").value="month_val";
                  }
                  function date_cli(){
                		document.getElementById("month1").style.display="none";
                  		document.getElementById("month2").style.display="none";
                  		document.getElementById("date1").style.display="block";
                  		document.getElementById("date2").style.display="block";
                  		document.getElementById("hid").value="date_val";
                  }
                  function validateYear(year_val){
                	  var today= new Date(); 
                      var day=today.getDate();
                      var month=today.getMonth();
                      month=month+1;
                      var year=today.getYear();
                      if(year < 1900) year += 1900;
                      if(year_val>year){
                    	  alert("Enter Correct year ... ");
                    	  document.getElementById("txtCB_Year").value=year;
                      }
                  }
                  function validateMonth(month_val){
                	 
                	  var today= new Date(); 
                      var day=today.getDate();
                      var month=today.getMonth();
                      month=month+1;
                      var year=today.getYear();
                      if(year < 1900) year += 1900;
                      var year_val=document.getElementById("txtCB_Year").value;
                    
                      if(year_val>year){
                    	  alert("Enter Correct year ... ");
                    	  document.getElementById("txtCB_Year").value=year;
                      }else if(year_val==year){
                    	  if(month_val>month){
                    	 	  alert("Enter Correct Month ... ");
                    	 	 document.getElementById("txtCB_Month").value=month;
                    	  }
                    	 
                      } else{
                    	  
                      }
                  }
    </script>
     <script type="text/javascript" language="javascript">
   
    </script>
    <title>Cash Book Details</title>
  </head>
  
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1')">
  
  
  <form action="../../../../../../CashBookReceiptServlet.con" name=frmReport id="frmReport" method="GET" onsubmit="return nullcheck();"> 
    <input type="hidden" id="hid" name="hid" value="month_val">
    <table width="100%" >
        
        
		        <tr>
		            <td class="tdH"><center><b>Cash Book Details</b></center></td>
		        </tr>
		        
        
        
		          <tr>
		            <td>
		                <table border="0" cellspacing="3" cellpadding="3" width="100%">
		                
		                       <tr class="table">
		                <td>
		                  <div align="left">
		                    Accounting Unit Code 
		                    <font color="#ff2121">*</font>
		                  </div>
		                </td>
		                <td>
		                  <div align="left">
		                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
		     
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
              <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                      tabindex="2">
                
              </select>
            </div>
          </td>
        </tr>
		              
              
                               
                    <tr>
                    <td>Report Type<font color="#ff2121">*</font></td>
                    <td>
                    <input type="radio" id="Mon_rep" name="Rep_Type" value="Month" onclick="month_cli();" checked="checked">Month Wise
                     <input type="radio" id="Date_rep" name="Rep_Type" value="Date" onclick="date_cli();">Date Wise
                    </td>
                    </tr>
                    
                    
        
                    <tr >
                          <td class="table"><div id="month1" style="display: block;">
                         
                              Cash Book Year &amp; Month&nbsp;&nbsp; <font color="#ff2121">*</font></div>
                            </td>
                        <td><div id="month2" style="display: block;">
                           <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onchange="validateYear(this.value);" onkeypress="return numbersonly(event)">
                         
                          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="validateMonth(this.value);" >
                        
                          <option value="4">April</option>
                          <option value="5">May</option>
                          <option value="6">June</option>
                          <option value="7">July</option>
                          <option value="8">August</option>
                          <option value="9">September</option>
                          <option value="10">October</option>
                          <option value="11">November</option>
                          <option value="12">December</option>
                          <option value="1">January</option>
                          <option value="2">February</option>
                          <option value="3">March</option>
                          </select>
                            </div>
                          </td>
                    </tr>
   
                    <tr>
                        <td><div id="date1" style="display: none;">
                            From Date: ( WithIn CashBook Month)<font color="#ff2121">*</font></div>
                        </td>
                        <td><div id="date2" style="display: none;">
                            <input type=text name=txtfromdate id=txtfromdate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmReport.txtfromdate);" alt="Show Calendar"
                                 height="24" width="19"></img>
                                 
                            &nbsp;&nbsp;&nbsp;
                                 
                            To Date:
                            <input type=text name=txttodate id=txttodate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmReport.txttodate);" alt="Show Calendar" ></img>
                        </div> </td>
                    </tr>
                                       
                    <tr>
                        <td>
                            Bank Wise / Account Number Wise <font color="#ff2121">*</font>
                        </td>
                        <td colspan="3">
                        
                            <input type="radio" name="DisMode" id="DisMode" value="Bank" onclick="blockHead(),callCommon();" />Bank &nbsp;&nbsp;
                            <input type="radio" name="DisMode" id="DisMode" value="AccNo" onclick="blockHead(),callCommon();" checked="checked" />Account Number
                        </td>                        
                    </tr>
                    
                    <tr>
                <td>
                  <div id="head_div1" name="head_div1" >
                    Bank A/C No.
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div id="head_div2" name="head_div2" >
                
                    <select name="cmbBankAccNo" id="cmbBankAccNo" onchange="Bank_Branch_Namee1(this.value);">
              
              
			<option value="">-- Select Bank A/C No ---</option>
		</select>
		<input type="button" name="goo" id="goo" value="Go" onclick="LoadBankAccountNumber();"></input>
			<input type="hidden" name="txtOprMode" id="txtOprMode" tabindex="5" style="background-color: #ececec" readonly="readonly" size="50" />
                  </div>
                </td>
                
              </tr>
                    <tr>
                        <td>
                            Report Option:<font color="#ff2121">*</font>
                        </td>
                        <td colspan="3">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>                        
                    </tr>
                    
                                        
                    <tr>
                        <td colspan=4 class="tdH" align="center">
                        <input type=submit value=Submit >
                        <input type=reset value=Clear>
                        <input type=button value=Exit onclick="closeWindow()">
                        </td>
                    </tr>
                    
                    
                
                </table>
            </td>
           </tr>
        </table>
  
  </form>
  </body>
</html>