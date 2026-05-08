<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>List Details</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" src="../scripts/AjaxListRegFees.js"></script>
  </head>
  <body onload="listAll()">
  <form name="frmListAllFees">
   
    <table cellspacing="2" cellpadding="3" border="1" width="90%" align="center" class="td">
      <tr>
        <td class="tdH">
          <DIV align="center">&nbsp;&nbsp;
          <STRONG>Listing All the Details</STRONG></DIV>
        </td>
      </tr>
    </table>
   
  <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="90%"
             align="center">
        <tr class="table">
           <th>
            Sl.No
          </th>
          <th>
            Office Level Id
          </th>
          
          <th>
            Regn Class Id
          </th>
          <th>
            State Coverage Applicable?
          </th>
          <th>
            Regn Fees
          </th>
          <th>
            Date effective from
          </th>
          </tr>
          <tbody id="tblList" class="table">
          </tbody>
          
      </table>
      <DIV align="center">
        <input type="BUTTON" value="  EXIT  " onclick="self.close();"/>
      </DIV>
      </form>
  </body>
</html>
