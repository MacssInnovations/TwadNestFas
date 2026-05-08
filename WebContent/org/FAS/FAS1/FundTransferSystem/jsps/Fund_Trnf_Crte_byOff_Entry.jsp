<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
<!--     <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" > -->
    <title>Fund Transfer Entry </title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"   media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/PaymentSystem/scripts/Common_PaymentType.js"></script>
    <script type="text/javascript" src="../scripts/Fund_Transfer_Create_byOffice.js"></script>
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script> 
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>
  <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script> 
    
  
    <script type="text/javascript" language="javascript">
         function foc()
         {
         
         }
         function loadDate()
         {
        	// call_bankUpdate();
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
                document.frmFundTrs_Create_byOffice1.txtCrea_date1.value=day+"/"+month+"/"+year;
                //call_date(document.frmFundTrs_Create_byOffice.txtCrea_date);  
       }
   </script>
  </head>
  <%-- <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%> --%>
<%
String unit_id="",office_id="";
int  cmbAcc_UnitCode=0,cmbOffice_code=0;
 unit_id=request.getParameter("unit_id");
 office_id=request.getParameter("office_id");
 cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
 cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
%>
  <body onload="loadDate();loadschme();" bgcolor="rgb(255,255,225)">
  
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Fund Transfer Entry </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmFundTrs_Create_byOffice1" 
          id="frmFundTrs_Create_byOffice1" 
          method="POST"
          action="../../../../../Fund_Transfer_Create_byOffice.view?Command=Add"
          onsubmit="return checkNull()">
 
           
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                   <option value= "<%=cmbAcc_UnitCode%>" ><%=unit_id%></option>
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
                  <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="1">   
                    <option value= "<%=cmbOffice_code%>" ><%=office_id%></option>        
                </select>
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
                    <input type="text" name="txtCrea_date1" id="txtCrea_date1" tabindex="3" 
                           maxlength="10" size="11"  readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmFundTrs_Create_byOffice1.txtCrea_date1,1);"
                         alt="Show Calendar"></img> 
                  </div>
                </td>
              </tr>
            <tr class="table">
                <td>
                  <div align="left">
                    Receipt Number
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtVoucher_No1" id="txtVoucher_No1"  
                     size="6" maxlength="5"/>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Scheme Name
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="txtScheme_id" id="txtScheme_id" style="width: 50%;">
                    <option value="">--Select--</option>
                    <% 
                    try{
                     ps=con.prepareStatement("select project_id,sch_name from pms_sch_master  WHERE (SCH_STATUS_ID !=10  AND sch_status_id    !=11) and office_id="+cmbOffice_code+" order by project_id");
                  
                     rs=ps.executeQuery();
                      while(rs.next())
                      {
                    //  System.out.println(rs.getInt(1));
                     // System.out.println(rs.getString(2));
                    
                      out.println("<option value="+rs.getInt(1)+" >"+rs.getString(2).trim()+"</option>");
                      
                      }
                      ps.close();
                      rs.close();
                  }
              
          catch(Exception e)
            {
                System.out.println(e);
            }
                    
                    %>
                    </select>
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                   Amount
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount" id="txtAmount"  
                     size="15" maxlength="12"/>
                  </div>
                </td>
              </tr>
             
              <tr class="table"> 
                <td colspan="2"> <center><input type="button" name="butAdd" id="butAdd" value="Add"
                       onclick="Addvalu();"/></center></td></tr>
            </table>
          </div>
        <table cellspacing="2" cellpadding="3" border="1" width="100%">
           <tr class="tdH">
               <th>Sl No </th>
               <th>Subledger Type code</th>
               <th>Scheme</th>
               <th>Receipt No</th>
               <th>Receipt Date</th>
               <th>Amount Received</th>
            </tr>
        <tbody id="tblList" class="table"></tbody></table>
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center" style="display:block" id="newDiv" name="newDiv">
                <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="sendVa();"/>
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
    </form></body>
</html>