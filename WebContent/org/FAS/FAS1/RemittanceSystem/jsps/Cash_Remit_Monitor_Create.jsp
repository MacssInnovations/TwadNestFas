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
    <title>Remittance System</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/Cash_Remit_Monitor_Create.js"></script>
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
      
        doFunction('loadPendingRemittance','null');
     }
    </script>
      </head>
  <body class="table" onload="call_clr();loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')" >
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
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>Cash Remittances Monitoring System</strong>
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
                <tr class="table">
                <td>
                  <div align="left">
                    Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  
                  
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11" 
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                         <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmCashRemit_Monitor.txtCrea_date,1);"
                         alt="Show Calendar"></img>  
                   </div>
                </td>
              </tr>
        </table>
          </div>
    <br>
     
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
            <th>
                 Challan Number
            </th>
            <th>
                 Remitted On 
            </th>
            <th>
                 Total Amount
            </th>
            <th>
                Remarks
            </th>
         <!--   <th>
                Verified By
            </th>-->
            <th>
                Show Details
            </th>
          </tr>
           <tbody id="grid_body" class="table" align="left">
           </tbody>
        </table>
         
    <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
         <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
               <!-- <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/> -->
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="exit();"/>
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>