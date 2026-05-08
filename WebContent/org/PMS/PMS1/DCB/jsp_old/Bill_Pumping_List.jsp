	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
<link href="../../../../../css/label.css" rel="stylesheet" media="screen"/> 
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<title>Bill Demand</title>
<script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<!--@MK121021 <script src="jquery-1.3.2.min.js" type="text/javascript"></script> -->
<script src="jquery-3.6.0.min.js" type="text/javascript"></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
<script type="text/javascript">
function rld(process,ben_value,ben_type)
{
	var month_value =document.getElementById("month").value;
	var year_value =document.getElementById("year").value;
 	var s="";
	if (process==1)
		s=window.open('Bill_Pumping_ed.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&ben_type="+ben_type,'windowname1','width=900, height=700')
	if (process==2)
	{
		document.getElementById("selected_ben").value=ben_value;
		s=window.open('Bill_Demand.jsp?month_value='+month_value+'&year_value='+year_value+"&ben_type="+ben_type,'windowname1','resizable=yes,width=900, height=800,scrollbars=1')
		
			
	}	
	if (process==3)
	{
		document.getElementById("selected_ben").value=ben_value;
		
		
		var mv=document.getElementById("month").options[document.getElementById("month").selectedIndex].text;
		var yv=document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
		var year_value =document.getElementById("year").value;
		s=window.open('pumping_report.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&mv="+mv+"&yv="+yv+"&ben_type="+ben_type,'windowname1','width=900, height=700')
			
	}	
}
</script>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
</head>
 	   <% 
 	    Controller obj=new Controller();
		Connection con;
		con=obj.con();
		obj.createStatement(con);
		String userid="0",Office_id="",Office_Name="";
		try
		{
		 userid=(String)session.getAttribute("UserId");
		}catch(Exception e) 
		{
			 response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
 		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
		obj.conClose(con);
		%>
<body bgcolor="#FFFFFA" onload="month('month','0'),year('year','0'),data_show_dcb('show',1,'subdiv'),data_show_dcb('show',2,'bentype')">
 
 <form name="list_demnad">
 <table align=center width="95%" border=0 cellspacing="0" cellpadding="2">
 <tr>
 	  
 	        <td align="center" colspan=8> <font    size="2"><b>Pumping Return -- <%=Office_Name%> </b></font>  </td>
 	
 	
 </tr>
 			<tr>
				<td valign="center"  align=left width=10% height=0><font    size="2"><b>Year</b></font> </td>
       			<td valign="center"  align=left width=10% height=0> <select class='select'  id="year" name="year"  style="width:90pt"></select></tD>			
        		<td  valign="center"  width=5% height=0><font    size="2"><b>Month</b></font> </td>
        		<td valign="center"  width=5%> <select class='select' id="month" name="month" style="width:90pt" onchange="list('show',1,this),flash()"></select></tD>
        		<td valign="center"  width=20% align=right><font    size="2"><b>Sub Division</b></font> </td>
        		<td valign="center"  width=10% > <select class='select'  id="subdiv" name="subdiv" onchange="list('show',2,this)"> </select></tD>
         		<td valign="center"   width=20% align=right><font    size="2"><b>Beneficiary Type</b></font> </td>
        		<td valign="center"  align=right > <select class='select'  id="bentype" name="bentype" onchange="list('show',3,this)" style="width:100pt"></select> </td>
        		
        	</tr>
        	<tr><td colspan=10 align=right  height=4>
        	<b><font  > <label id="msg"> </label></font></b></td></tr>
 </table>
 <table align=center width="95%" border=0  cellspacing="0" cellpadding="0"  >
 			<tr  >
 			    <td  width=1%><font size=2 >&nbsp;</font> </td>
 			      <td  valign="top" width=22%> 
               <font  size="2"  > 
				    &nbsp;&nbsp;<b>Beneficiary Name</font> </td>
				 <td  width=16%><font   size="2"  ><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beneficiary Type</font> </td>
         		<td  width=16%><font   size="2"  ><b>Total Units</font> </td>
				<td  width=16%><font  size="2"  >&nbsp;</td>
         		
         	</tr>
         	<Tr valign="top" height=490>
			<td colspan="5">
			<div id='scroll_clipper' style='position:relative; width:100%; border-height:3px; height: 470px; overflow:auto;white-space:nowrap;'>
			<div id='scroll_text'  >
			<table id="entred_data" align=center width="100%" border=1 cellspacing=0 cellpadding=1>
			 
			<tbody id="entred_body" align="left"   ></tbody>
         	</table>
			</div>
			</div>
			</td>
			</Tr>
			
			<tr>
			<td colspan=5 align=center><input type=button value=Exit onclick="javascript:window.close()"></input></td>
			</tr>
 </table>
		<input type="hidden" id="selected_ben" name="selected_ben" value="0"></input>
		   <input type=hidden id="pr_status" name="pr_status" value="0"> 
		
		</form>
		
</body>

</html>