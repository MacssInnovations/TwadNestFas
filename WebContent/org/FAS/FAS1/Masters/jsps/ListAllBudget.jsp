<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <link href="../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../scripts/ListAllBudget.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    <title>List of All Budget</title>
  </head>
  <body class="table" onload="LoadValues()">
  <form action="">
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
  <tr>
        <td class="tdH"><center>List of All Budget</center></td>
  </tr>
    <tr>
    <td>
  <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%">
                          <tr class="tdH">
                          <th>
                            Select
                          </th>
                          <th>
                            Accounting Unit Name
                          </th>
                          <th>
                            Accounting Office Code
                          </th>
                          <th>
                            Financial Year
                          </th>
                          <th>
                          Account Head Description
                          </th>
                          <th>
                            Previous Year Expenditure
                          </th>
                          <th>
                            Current Year Budget Estimate
                          </th>
                          <th>
                            Current Year Revised Estimate
                          </th>
                          <th>
                           Next Year Estimate
                          </th>
                          <th>
                            Reference No 
                          </th>
                          <th>
                          Reference Date
                          </th>
                          <th>
                            Remarks
                          </th>
                           <th>
                          Budget Alloted 
                          </th>
                          <th>
                           Budget Spent
                          </th>
                          <th>
                          Type of Allocation
                          </th>
                          
                          </tr>
                          <tr>
            <tbody id="tblList" name="tblList">
                </tbody>
        </tr>
                          </table>
            </td>
        </tr>
        <tr>
        <td align="center">
        <input type="button" name="Exit" value="Exit" onclick="closeWindow()">
        </td>
        </tr>
    </table>
                        
  </form>
  
  </body>
</html>