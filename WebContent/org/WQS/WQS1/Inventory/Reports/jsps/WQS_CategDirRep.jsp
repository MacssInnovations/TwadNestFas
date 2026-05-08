<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_CategDirRep</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript" src="../scripts/WQS_CategDirRep.js"></script> 
  </head>
  <body>
  <form name="CategDirReport" method="get" action="">
  <table cellspacing="2" cellpadding="3" border="1" width="90%">
        <tr class="tdH">
          <th colspan="2">
            <strong><font face="Times New Roman" size="4">
                Category Directory Report
              </font></strong>
          </th>
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr class="table">
          <td width="51%">
            <font face="Times New Roman" size="4">
              Select Report Type
            </font></td>
          <td width="49%"><select name="cmbReportType" id="cmbReportType">
          
          <option value="PDF">PDF</option>
          <option value="HTML">HTML</option>
          <option value="EXCEL">EXCEL</option>
          </select>
          </td>
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
          <input type="button" value="Generate Report" onclick="gen_rep()"/>
          <input type="button" value="Exit" onclick="close_win()"/>
          </td>
        </tr>        
      </table>
      <p>
        &nbsp;
      </p>
      <p>
        &nbsp;
      </p>
      <p>
        &nbsp;
      </p>
      <p>
        &nbsp;
      </p>
      </form>
  </body>
</html>