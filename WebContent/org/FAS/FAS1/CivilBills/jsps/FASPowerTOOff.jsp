<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PowerToWriteOff</title>
<script type="text/javascript" src="../scripts/PowerToWriteOff.js"></script>
 <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<body onload="initialLoad1()">
<form name="FAS_PowerToOffice" id="FAS_PowerToOffice" method="post" action="../../../../../PowerToWriteOff">
  
                    
 <table width="533" align="center" cellspacing="2" border="1">
     <tr class="table">
      <td colspan="2" class="tdH"><div align="center"><strong>Power To Write Off</strong> </div></td>
    </tr>
    <tr class="table">
      <td colspan="2">&nbsp;</td>
    </tr>
    
    
                        <tr class="table">
                        <td> Financial_Year</td>
                        
                        <td>
                        <select id="cmbFinancialYear" name="cmbFinancialYear"></select>
                        
                        </td>
                        </tr>
    
    
    
                        <tr class="table">
                                    <td width="257">Level<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbLevel" id="cmbLevel" style="width:147px" readonly=readonly>
                                    <option value="select">----Select---- </option>
                                    <option value="Board">Board </option>
                                    <option value="MD">MD</option>
                                    <option value="CE">CE</option>
                                    <option value="SE">SE</option>
                                    <option value="EE">EE</option>
                                    </select></td>             
                        </tr>
                        <tr class="table">
                          <td>Type<span class="style1">* </span> </td>
                          <td> <input type="radio" name="r1" id="r1" value="L">Loss  <input type="radio" name="r1"   id="r1" value="I">Irrecoverable Value  </td>
                        </tr>
                        <tr class="table">
                          <td>Value Upto<span class="style1">* </span></td>
                           <td> <input type="text" name="txtValueUpto" id="txtValueUpto" maxlength="30"> </td>
                        </tr>
                    
       
                       
                        <tr class="table">
                          <td>Remarks<span class="style1">* </span> </td>
                          <td> <textarea rows="3" cols="18" name="mtxtRemarks" id="mtxtRemarks" maxlength="30"></textarea>                          </td>
                        </tr>
                            <tr class="table">
      <td colspan="2" class="table">&nbsp;</td>
    </tr>
                         
                        <tr class="table">
                                   
                                    <td colspan="2">
                                            <div align="center">
                                              <input type="button" name="onsubmit" value="ADD" id="onsubmit" onClick="add();" />  
                                              <input type="button" name="ondelete" value="CANCEL" id="ondelete" onClick="delete1();" disabled="disabled" /> 
                                              <input type="button" name="onupdate" value="UPDATE" id="onupdate" onClick="update();" disabled="disabled" /> 
                                              <input type="button" name="onlist" value="List" id="onlist" onClick="listPopup();" />  
                                              <input type="button" name="clear" value="CLEAR" id="clear" onClick="clearAll();" />
                                              <input type="button" name="onexit" value="EXIT" id="onexit" onClick="exitfun();" />                                   
                                            </div></td> 
                        </tr>
  </table>
 
</form>
</body>
</html>