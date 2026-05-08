<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>BRS Balance Update List</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../scripts/BRS_Bank_Balance_Update_List.js"></script>
  </head>
  <%
  int unitcode=Integer.parseInt(request.getParameter("unitid"));
  int txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
  int txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
  String totalcode=unitcode+","+txtCB_Year+","+txtCB_Month;
  %>
   <body onload="initialLoad('<%=totalcode %>');">
          <form name="brsbankbalanceupdatelistform" action="Get">
               <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                 <tr class="tdH" align="center">
                    <th>
                            List of BRS BANK BALANCE 
                    </th>
                </tr>
           </table>
           
           <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                <tr class="tdH">
                     <th width="1%">Select</th>
                     <th width="5%">CashBook Year</th>
                   <th width="5%">CashBook Month</th>
                      <th width="5%">Pass Sheet Year</th>
                   <th width="5%">Pass Sheet Month</th>
                   <th width="5%">Pass Sheet Date</th>
                    <th width="5%">Bank Name</th>
          <th width="5%">Type of Account</th>
          <th width="5%"> Account Number </th>
          <th width="5%"> Bank Balance as per the pass Sheet</th>
           <th width="5%">Debit Or Credit</th>
          <th width="10%">Remarks</th> 
                </tr>
             <tbody id="tblList" align="center" class="table">
             </tbody>
            </table>
            <table align="center" cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <td>
                    <div align="center">
                             <input type="button" id="exit" name="exit" value="Exit"
                             onclick=" self.close();"></input>
                    </div>
                  </td>
                </tr>
      </table> 
          </form>
  </body>
</html>