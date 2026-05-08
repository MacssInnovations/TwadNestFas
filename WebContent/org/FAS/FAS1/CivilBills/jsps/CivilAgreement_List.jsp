<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>CivilAgreement_List</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../scripts/CivilAgreement_List.js"></script>
  </head>
  <body onload="initialLoad();">
          <form name="civillistform" action="Get">
          <%
                int acc_unit_id=Integer.parseInt(request.getParameter("unit_id"));
                int office_id=Integer.parseInt(request.getParameter("office_id"));
                out.println("<input type='hidden' name='unit_id' id='unit_id' value="+acc_unit_id+">");
                out.println("<input type='hidden' name='office_id' id='office_id' value="+office_id+">");
          %>
           <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                <tr class="tdH">
                     <th>Select</th>
                     <th>Agreement No</th>  
                     <th>Agreement Date</th>
                     <th>Agreement Type</th>
                     <th>Firm's</th><!--  /Contractor's Name
                     --><th>Value of the Work</th>
                     <th>Remarks</th>
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