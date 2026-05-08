<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Payee Type List</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../scripts/cheqmemo_payeeList.js"></script>
      </head>

  <body onload="initialLoad();">
          <form name="cheqpayeelistform" action="Get">
               <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                 <tr class="tdH" align="center">
                    <th>
                            List of Cheque Memo Payee Type Code
                    </th>
                    <td>
                    <input type="hidden" name="cheqtype" id="cheqtype" value=<%=request.getParameter("cheqtype")%>> 
                    </td>
                </tr>
           </table>
           
           <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                <tr class="tdH">
                     <th>Select</th>
                     <th>Serial No</th>
                     <th>Cheque Memo Type Code</th>
                     <th>Payee Type Code</th>
                     <th>Status</th>
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