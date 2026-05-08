<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <title>Invoice Bills List</title>
        <link href="../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
        <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
        <script type="text/javascript" src="../../Reports/ReceiptSystem/scripts/CalendarControl.js"></script>
        <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
       <script type="text/javascript" src="../scripts/FirmBills_List_js.js"></script>
       <script type="text/javascript" language="javascript">
       function loadDate()
       {
           try
           { 
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
            
             var year=today.getYear();
             if(year < 1900) year += 1900;
            
            document.listform.year.value=year;
            document.listform.month.value=month;
           } 
           catch(e)
           {
                alert(e.description);
           }
   
        }
        </script>
  </head>

  <body onload="loadDate()">
             <form name="listform" action="Get">
              <%
                int acc_unit_id=Integer.parseInt(request.getParameter("unit_id"));
                int office_id=Integer.parseInt(request.getParameter("office_id"));
                out.println("<input type='hidden' name='unit_id' id='unit_id' value="+acc_unit_id+">");
                out.println("<input type='hidden' name='office_id' id='office_id' value="+office_id+">");
          %>
                <table cellspacing="1" cellpadding="1" border="0" width="100%">
                            <tr align="center" class="tdH">
                                    <td colspan="2">
                                         <!--    <div id="Searchid" style="display:block">
                                                     Invoice Number List
                                            </div>  -->
                                            <div id="Searchid" style="display:block">
                                                    List of Invoice Details 
                                             </div>
                                    </td>
                            </tr>
                            <tr align="left" class="tdH">
                                    <td colspan="2">
                                            <div id="Searchid" style="display:block">
                                                    Search By Month or Date
                                            </div>
                                    </td>
                            </tr>
                
                            <tr align="center">
                                    <td class="table">
                                       <div align="left" id="divid" >    
                                                CashBook Year & Month : 
                                                    <input type="text" name="year" id="year"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <select name="month"  id="month">
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
                                                  <input type="button" name="gobtn" id="gobtn" value="Go" onclick="doFunction1();"/>
                                          </div> 
                                    </td>
                            </tr>
                            <tr>
                                    <td class="table">
                                            <div id="dateid" style="display:block">
                                                From Date & To Date &nbsp;&nbsp;&nbsp;&nbsp; :
                                                <input type="text" name="fromdate" id="fromdate"/>
                                                <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.listform.fromdate, 1);" alt="Show Calendar"></img>
                                                &nbsp;&nbsp;&nbsp;
                                                <input type="text" name="todate" id="todate"/>
                                                <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.listform.todate, 1);" alt="Show Calendar"></img>
                                                <input type="button" name="gobtn" id="gobtn" value="Go" onclick="gobtnFunction();"/>
                                            </div>
                                    </td>
                            </tr>
                       
                        <tr>
                                <td align="center">
                                     <div id="allid" class="table" style="display:none">
                                        <input type="radio" name="all" value="All" checked="checked"  onclick="calldefault();"/>
                                        All 
                                        <input type="radio" name="all" value="Particular" onclick="callfunc();"/>
                                        Particular 
                                    </div>
                                    <div id="comboid" style="display:none" class="table">
                                           <select name="particularcmb" id="particularcmb" onchange="callino();"> 
                                                <option value="">Select BillNo</option>
                                           </select>
                                    </div>
                                </td>
                        </tr>
                </table>               
                <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                            <tr class="tdH">
                                         <th>Select</th>
                                         <th>Bill No</th>
                                         <th>Bill Date</th>
                                         <th>Invoice No-Date </th>
                                         <th>Invoice Amount</th>
                                         <th>MajorType-MinorType-SubType</th>  
                                         <th>Invoice Particulars</th>
                                         <th>Agreement No-Date</th>
                                         <th>Firm Name</th>
                                         <th>Remarks</th>
                            </tr>
                            
                <tbody id="tblList" align="center" class="table">
                 </tbody>
                </table>
                <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
               		<tr class="tdH">
                            <td  colspan="2" align="center">
	                            <div align="center">
	                            	 <input type="button" name="onexit" value="Exit" id="onexit" onclick=" self.close();" />
	                            </div>
                            </td>
                     </tr>
                </table>
        </form>
  </body>
</html>