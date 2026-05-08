<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Supplier List</title>
    <script type="text/javascript" src="../scripts/SupplierListJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   
  </head>
  <body  bgcolor="rgb(255,255,225)"><form name="supplierList" id="supplierList">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              &nbsp;List of Suppliers Subsidiary Ledger Master
            </div></td>
        </tr>
        <tr class="table">
          <td>&nbsp;Supplier's Name begins with the Letter&nbsp;&nbsp;
         
          <%
           
        out.println("&nbsp;&nbsp;");
        out.println("<a href=\"javascript:loadTable('list','a')\">A</a>");
        out.println("<a href=\"javascript:loadTable('list','b')\">B</a>");
        out.println("<a href=\"javascript:loadTable('list','c')\">C</a>");
        out.println("<a href=\"javascript:loadTable('list','d')\">D</a>");
        out.println("<a href=\"javascript:loadTable('list','e')\">E</a>");
        out.println("<a href=\"javascript:loadTable('list','f')\">F</a>");
        out.println("<a href=\"javascript:loadTable('list','g')\">G</a>");
        out.println("<a href=\"javascript:loadTable('list','h')\">H</a>");
        out.println("<a href=\"javascript:loadTable('list','i')\">I</a>");
        out.println("<a href=\"javascript:loadTable('list','j')\">J</a>");
        out.println("<a href=\"javascript:loadTable('list','k')\">K</a>");
        out.println("<a href=\"javascript:loadTable('list','l')\">L</a>");
        out.println("<a href=\"javascript:loadTable('list','m')\">M</a>");
        out.println("<a href=\"javascript:loadTable('list','n')\">N</a>");
        out.println("<a href=\"javascript:loadTable('list','o')\">O</a>");
        out.println("<a href=\"javascript:loadTable('list','p')\">P</a>");
        out.println("<a href=\"javascript:loadTable('list','q')\">Q</a>");
        out.println("<a href=\"javascript:loadTable('list','r')\">R</a>");
        out.println("<a href=\"javascript:loadTable('list','s')\">S</a>");
        out.println("<a href=\"javascript:loadTable('list','t')\">T</a>");
        out.println("<a href=\"javascript:loadTable('list','u')\">U</a>");
        out.println("<a href=\"javascript:loadTable('list','v')\">V</a>");
        out.println("<a href=\"javascript:loadTable('list','w')\">W</a>");
        out.println("<a href=\"javascript:loadTable('list','x')\">X</a>");
        out.println("<a href=\"javascript:loadTable('list','y')\">Y</a>");
        out.println("<a href=\"javascript:loadTable('list','z')\">Z</a>");
        out.println("&nbsp;");
        out.println("<a href=\"javascript:loadTabAll('ListAll')\">ListAll</a></td>");
          %>
        </tr>
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
        <th>
        EDIT
       </th>
       <th>
       Supplier ID
       </th>
       <th>
       Name
       </th>
       <th>
       Address
       </th>
      
       <th>
       City
       </th>
       <th>
      E-Mail
       </th>
       <th>
       Phone
       </th>
       <th>
       Fax
       </th>
      <th>
      Status
      </th>
       </tr>
       <tbody id="tb" class="table" align="left">
          
          
          </tbody>
       </table>
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>