<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<META HTTP-EQUIV="CACHE-CONTROL"
	CONTENT=" no-store, no-cache, must-revalidate">
<META HTTP-EQUIV="CACHE-CONTROL"
	CONTENT=" pre-check=0, post-check=0, max-age=0">
<title>Journal System</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />


<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
 
 <script type="text/javascript" src="../scripts/OandM_Bills.js"></script>

<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js">
	
</script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
  <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
  

</head>
<%
	String s = request.getContextPath();
String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
String cmbOffice_code=request.getParameter("cmbOffice_code");
%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevent caching at the proxy server
%>
<body onload="loadsch(<%= cmbAcc_UnitCode%>,<%= cmbOffice_code%>);" bgcolor="rgb(255,255,225)">
	<form name="frmLjvlist" id="frmLjvlist" >
	<input type="hidden" id="hid_Unit" name="hid_Unit" value=<%= cmbAcc_UnitCode%>>
	<input type="hidden" id="hid_Office" name="hid_Office" value=<%= cmbOffice_code%>>
		<div
	     class="tab-page" id="gd">
				<h3 class="tdH"><center>Scheme List</center></h3>

				<div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="tdH">
                        <th > Select  </th>
                      
                        <th >Scheme Name</th>
                     
                        
                      </tr>
                      
                       <tbody id="grid_bodylist" class="table" align="left" >
                        
                       </tbody>
                       
                    </table>
                  </div>
			<center><input type="button" value="submit" onclick="javascript:btnListSub();"></center>
		</div>
			
	</form>
</body>
</html>