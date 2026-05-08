<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PowerToWriteOff</title>
<script type="text/javascript" src="../scripts/PowerToOffList.js"></script>
 <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
 
 

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>


 <body onload="initialLoad();"> 
 <form name="FAS_PowerToOffice" id="FAS_PowerToOffice" method="post" action="../../../../../PowerToWriteOff">
  

 <div style="position:absolute; left: 35px;  width: 948px;" left=180>
                <table cellspacing="3" cellpadding="2" border="1" width="100%" align="center" >
                        <tr class="table">
                                    <td align="center" class="tdH"> 
                                            <b>EXISTING DETAILS </b>
                                    </td>
                        </tr>
                </table>
                <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                        <tr class="table">
                                     <th width="5%">Select</th>
                                     <th width="16%">Level</th>
                                     <th width="14%">Type</th>
                                      <th width="14%">Value UpTo</th>
                                     <th width="12%">Remarks</th>
                                     <th width="12%">Status</th>
                                     
									  
                        </tr>
                <tbody id="tblList" align="center">
                 </tbody>
  </table> 
 
   <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr>
          <td align="center">
           
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="self.close();"></input>
           
          </td>
        </tr>
      </table> 
       </div>
       </form>
</body>
</html>