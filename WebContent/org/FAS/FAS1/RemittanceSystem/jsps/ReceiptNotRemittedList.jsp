<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
  <style type="text/css">
  .btn-grad {background-image: linear-gradient(to right, #1A2980 0%, #26D0CE 51%, #1A2980 100%);color: white;}
  .btn-grad:hover { background-position: right center; }
  </style>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Remittance System</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/ReceiptNotRemittedList.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
   
   
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
  
   <script type="text/javascript" language="javascript">
     function loadyear_month()
     {
       
         var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
           // document.frmCashRemit_Monitor.txtCrea_date.value=day+"/"+month+"/"+year;
           document.getElementById("txtCB_Year").value=year;
      
        //doFunction('loadPendingRemittance','null');
     }
    </script>
    </head>
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')" >
  <form name="frmCashRemit_Monitor" id="frmCashRemit_Monitor"
    method="POST"  action="../../../../../Cash_Remit_Monitor_Create.view?Command=Add"
                  onsubmit="return checkNull()">
                  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
   <% 
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
      System.out.println("user id::"+empProfile.getEmployeeId());
      int empid=empProfile.getEmployeeId();
      int  oid=0;
      String oname="";
   %>
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="btn-grad">
        <td colspan="2">
          <div align="center">
            <strong>List of bank receipt not remitted</strong>
          </div>
        </td>
      </tr>
    </table>
     <div align="center">
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
                      tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
		          	<div align="left">
		              	Accepted During Year &amp; Month&nbsp;&nbsp;<strong>:</strong>
		              	</div>
		              	</td>
		              	<td>
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
		          </td>
		        </tr>
        </table>
          </div>
    <br>
     
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="btn-grad">
            
            <th>
                 Account No
            </th>
            <th>
                 Receipt No
            </th>
            <th>
                 Receipt Date
            </th>
            <th>
                Amount
            </th>
        
          </tr>
           <tbody id="grid_body" class="table" align="left">
           </tbody>
        </table>
         
         <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
        
          <tr>
  <td>
  
   <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
                     <tr class="btn-grad">
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
  </td>
  </tr>
      <tr class="btn-grad">
      <td>
          <div align="center">
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="self.close()">
      </div>
      </td>
      </tr>
      
      </table>
    
      </form>
  </body>
</html>