<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Drawing Officer Details</title>
<script type="text/javascript" src="../scripts/DrawingList.js"></script>
 <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
 
 

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<%
    String s1=request.getParameter("unit_id");
    String s2=request.getParameter("office_id");
    System.out.println("jspdsfsdfsdfsdfsdfsdfdsfsdfsdfsdf"+s1);
    System.out.println("jspdsfsdfsdfsdfsdfsdfdsfsdfsdfsdf"+s2);
%>

 <body onload="initialLoad('<%=s1%>','<%=s2%>');"> 

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
                                     <th width="16%">Drawing Officer Designation</th>
                                     <th width="14%">Current Drawing Officer Name</th>
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
</body>
</html>