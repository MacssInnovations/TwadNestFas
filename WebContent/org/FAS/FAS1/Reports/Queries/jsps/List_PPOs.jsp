<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title> List of PPOs</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/List_PPOs.js"></script>
   <link href='<%=request.getContextPath()%>/css/Fas_Account.css' rel='stylesheet'
	media='screen' /> 
	<link href="<%=request.getContextPath()%>/css/Sample3.css" rel="stylesheet"
	media="screen" />        
  </head>
  <%
	String s = request.getContextPath();
%>
  <body onload="callList('<%=s%>');">
  <!--<table cellspacing="2" cellpadding="3" width="100%" align="center" class="table1" >
      <tr class="tdH1">
        <td colspan="2">
          <div align="center">
            <font size="4">List  Of  PPOs </font>
          </div>
        </td>
      </tr>
    </table>
    
    --><form name="frmList_PPOs" id="frmList_PPOs" method="get" action="List_PPOs">
    
         <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="80%" align="center" class="table1">
         <tr class="tdH1">
		<td align="center" colspan="3"><b>List  Of  PPOs </b></td>
	</tr>
                <tr class="tdH1">
                     <th width="1%">S.No</th>
                     <th width="5%">Office ID</th>
                    <th width="5%">Office Name</th>                  
                </tr>
	             <tbody id="tblList" align="center" class="table">
	             </tbody>
	            
      </table> 
     
    </form>
    <table align="center">
     <tr class="tdH1">
		<td colspan="3">
		<div align="center"> <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>	
			</td>
	   </tr>
    </table>
    </body>
</html>