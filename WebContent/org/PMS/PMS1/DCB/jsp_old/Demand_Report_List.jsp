<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demanded Bill List</title>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>

<style>
 
.FR{
scrollbar-face-color:#9898D0;
scrollbar-arrow-color:indigo;
scrollbar-track-color:#C0DCC0;
scrollbar-shadow-color:'';
scrollbar-highlight-color:'';
scrollbar-3dlight-color:'';
scrollbar-darkshadow-Color:'';
}
 
</style>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand_Report.js"></script>
 
</head>
 <%
     
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String userid="112",Office_id="",Office_Name="";
			String Date_dis=day+"/"+month+"/"+year;
			   Controller obj=new Controller();
				Connection con;
				try
				{
				con=obj.con();
				obj.createStatement(con);
				 
				  
				  userid=(String)session.getAttribute("UserId");
			
				if(userid==null)
				{
				 	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				String OFFICE_ID=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				if (Office_id.equals("")) Office_id="0";
		 		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
				obj.conClose(con);
				}catch(Exception e) {
					
					userid="112";
				 
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				 
				}
%>
<body onload="year('year','ob'),month('month','ob')"  >
<form> 
<input type="hidden" id="subdiv" value="0"/>

<table width="100%" border="1" align="center"  cellpadding="0" cellspacing="0">

<tr>
	          <td colspan="4" align="center" ><font color="	#408080"> Tamil Nadu Water Supply and Drainage Board</font></td>
			  
			  </tr>
  			 <tr>
	          <td colspan="4" align="center"> <%=Office_Name%></td>
			  
			  </tr>
			  <tr>
	          <td colspan="4" align="center">Bill Demand List</td>
			  
			  </tr>
	         <tr>
	          <td> <font color="#0000A0"> Year </font></td>
			  <Td><select class="select"  id="year" name="year"  style="width:80pt" ></select> </tD>
			  </tr>
			 <Tr>
			   <td><font color="#0000A0">  Month </font></td>	  	          
			 <Td><select class="select" id="month" name="month"  style="width:80pt" onchange="data_show('show',14,'dmdlist')"></select></Td>	        
          	</tr>
			<Tr>			 
			 <td> <font color="#0000A0"> Bill No</font> </td>	  	    
			 <td><select class="select" id="dmdlist" name="dmdlist"  style="width:100pt" onchange="dmd_view() "></select> </td>	  
			 </Tr>
			 <Tr>
			 <td colspan="2" align="right"> <a href="javascript:demand_show('pdf',1,0)">Print</a> &nbsp;&nbsp;&nbsp;<a href="javascript:void()">All</a> &nbsp;&nbsp;&nbsp;<a href="javascript:window.close()">Exit</a> &nbsp;&nbsp;&nbsp; </td>
			 </Tr>
		</table>	
		<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
			
			<Tr>
			<td>
 			<iframe style="border-color: blue" id="ifr"  frameborder="10" scrolling="yes" width="100%" height="800"	align="right">				 
				 
				</iframe>
 				</td></Tr>
	
</table>
</form>
     

</body>
</html>