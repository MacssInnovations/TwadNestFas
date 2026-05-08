<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Office Conversion</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
    </style>
   
  </head>
  <body><form name="frmOfficeDetailRep"  action="../../../../../OfficeDetailRepServ.rep"  method="POST">
      <table cellspacing="2" cellpadding="3" border="1" width="80%" align="center">
        <tr class="tdH">
          <th align="center" colspan="2">Office Report Details</th>
        </tr>
        <tr class="table">
          <td>
            <div align="left">Selection Filter</div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              <input type="radio" name="optoffdetail" checked="checked"
                     value="ALL"/>
               All Offices 
              <br/>
               
              <input type="radio" name="optoffdetail" 
                     value="RN"/>
               Only Region Offices<br/>
               
              <input type="radio" name="optoffdetail" 
                     value="CL"/>
               Only Circle Offices 
              <br/>
               
              <input type="radio" name="optoffdetail" 
                     value="DN"/>
               Only Division Offices 
              <br/>
               
              <input type="radio" name="optoffdetail" 
                     value="SD"/>
               Only Subdivision Offices<br/>
            </div>
          </td>
        </tr>
         <tr class="table">
           <td colspan="2">
              <div align="left">Report Type 
                <select name="cmbReportType">
                  <option value="PDF" selected="selected">PDF</option>
                  <option value="EXCEL">EXCEL</option>
                <!--  <option value="TXT">TXT</option>-->
                  <option value="HTML">HTML</option>
                </select>
               </div>
            </td>
          </tr>
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <input type="submit" name="Submit" value="Submit"
                     id="cmdsubmit"/>
               
              <input type="button" name="close" value="Close" id="close" onclick="window.close()"/>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>