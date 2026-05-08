<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,java.text.DateFormatSymbols,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../script/Retirement_Cadre_wise.js"></script>
<script type="text/javascript" src="../script/google_api.js"></script>


 <script type="text/javascript">     
      google.load('visualization', '1.0', {'packages':['corechart']});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
    	  cadre_wise_retire_sub_chart();
      }
    </script>
    
    <link href="../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
<link href="../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadre Wise Retirement</title>


<%  

Connection con=null;
try
{
          ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
          String ConnectionString="";
          String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
          String strdsn=rb.getString("Config.DSN");
          String strhostname=rb.getString("Config.HOST_NAME");
          String strportno=rb.getString("Config.PORT_NUMBER");
          String strsid=rb.getString("Config.SID");
          String strdbusername=rb.getString("Config.USER_NAME");
          String strdbpassword=rb.getString("Config.PASSWORD");
          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
         // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

          Class.forName(strDriver.trim());
           
con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
}
catch(Exception e)
{
  System.out.println("Exception in connection...."+e);
}
HttpSession session1=request.getSession(false);
UserProfile empProfile=(UserProfile)session1.getAttribute("UserProfile");
    
  System.out.println("user id::"+empProfile.getEmployeeId());
  int empid=empProfile.getEmployeeId();

  
  

int year=0,finyear=0;
String regionSno="";
try{
year = Calendar.getInstance().get(Calendar.YEAR); 
year++;
regionSno=request.getParameter("regionSno");
finyear=Integer.parseInt(request.getParameter("year"));




}
catch(Exception e)
{

}
%>
</head>
<body>
<form action="">

<input type="hidden" id="cadre_id" value="<%=regionSno %>" name="cadre_id"/>
<input type="hidden" id="year_wise" value="<%=finyear %>" name="year_wise"/>


<table  cellpadding="2" cellspacing="2" width="70%"  border="0" align="center" style="border:2px solid #006699">

<tr>
<td align="center" colspan="2" class="tdH">
 Retirement Cadre Wise Details :
</td>
</tr>
<tr>

<td colspan="2" align="right">
<a href="Retirement_Cadre_Wise.jsp?regionSno=<%=regionSno %>&year=<%=finyear %>"><font color="blue"> >>Previous</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="../../../../images1/printIcon.jpg" onclick="window.print();"  style="height : 35px; width : 35px;"/>
</td>
</tr>
<!-- 
<tr>
<td align="left"   >
Select  Year :
</td>
<td align="left" >
<select id="year" name="year" onchange="cadre_wise_retire();">
<%for(int i=0;i<5;i++){
	if(year==(Calendar.getInstance().get(Calendar.YEAR))+1)
	{
	%>
	<option value="<%=year %>" selected="selected"><%=year %></option>
	<% 
	}
	else
	{ %>
		<option value="<%=year %>" ><%=year %></option>  
		
		<%
	}
	year++;
	%>
	<% 
}
%>
</select>
</td>
<td>
 Service Group  : </td><td><select id="ser_group" name="ser_group" onchange="cadre_wise_retire();">
   <%
   try{
	  

	  String sql="select service_group_id,service_group_name from hrm_mst_service_group order by service_group_name";
	  PreparedStatement ps=con.prepareStatement(sql);
	  ResultSet rs = ps.executeQuery();
	  while(rs.next())
	  {
		  
		  System.out.println("service group name is ==== "+rs.getString("service_group_name"));
		  if(rs.getInt("service_group_id")==6)
		  {
	  	%>
	  	<option value="<%=rs.getInt("service_group_id") %>" selected="selected"><%=rs.getString("service_group_name") %></option>
	  	<% }
		  else{
			  %>
			  <option value="<%=rs.getInt("service_group_id") %>" ><%=rs.getString("service_group_name") %></option>
			  <%
		  }
	  	%>
	  	
	  	<%
	  }
	  
  }catch(Exception e)
  {
	  
  }
  
 %>
 
 </select> 
 

</td>



</tr>

-->

<tr>


<td >
<div id="vacance_bar_programs"></div>
</td>

<td >
<div id="vacance_bar_participates" ></div></td>



</tr>



<tr><td colspan="2" >
<table cellpadding="2" cellspacing="2" width="100%"  border="0" align="center" >
<tr bgcolor="#006699" style="height:30px;">

<td align="center">
<font color="#FFF"><b>Cadre ID</b></font>
</td>

<td align="center" >
<font color="#FFF" style=""><b>Cadre Name</b></font>
</td>


<td align="center" >
<font color="#FFF" style=""><b>Year</b></font>
</td>


<td align="center" >
<font color="#FFF" style=""><b>Total Retirement</b></font>
</td>







<!--
<td align="center">
<font color="#FFF"><b>  Total No Of Participants</b></font>
</td>

-->
</tr>



<tbody id="vacance_deatils" align="center" style="width: 200px"></tbody>

</table></td></tr>


</table>


</form>
</body>
</html>