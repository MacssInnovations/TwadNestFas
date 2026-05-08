<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 
<head>
<style type="text/css">
.selectbox
{
  font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
  font-size: 8x;
  display : red;   
}
</style>
     <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%
 		Calendar cal = java.util.Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);

		Controller obj=new Controller();
		Connection con;
		
		String userid="112",Office_id="",Office_Name="";
		try
		{	
			con=obj.con();
			obj.createStatement(con);
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
		
		}catch(Exception e) {
			
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			
		}
%>
<title>Opening Balance Report - <%=Office_Name%> </title>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
</head>

<body onload="year('year','0'),data_show_dcb('show',2,'bentype')">
<table align=center width="95%" border=0 cellspacing="0" cellpadding="0" height="40">
 <tr >
 	 
 	        <td align="center" colspan=8 bgcolor="#C89800"   height="10"> <font  color="#FFFFFF" face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>Beneficiary Opening Balance Report - <%=Office_Name%></b></font>  </td>
 	
 </tr>
 			<tr>
 			 <td> <font   face="Verdana, Arial, Helvetica, sans-serif" size="1"><b>Year&nbsp;&nbsp;&nbsp;</font><select class='select' id="year" name="year"  style="width:75pt" onchange="grid_show(1,'report','','bentype','subdiv'),flash()"></select></td>
 			 <input type=hidden id="subdiv" name="subdiv" value="0"/> 	
 			 
          		<td  width=40%><font   face="Verdana, Arial, Helvetica, sans-serif" size="1"><b>Beneficiary Type&nbsp;:&nbsp;</b></font> <select  class='select' id="bentype" name="bentype"  onchange="grid_show(2,'report','','bentype','subdiv')"  style="width:150pt"></select><font color="green">&nbsp;&nbsp;&nbsp;<label id="msg"></label></font>
          		<td> <label id='divname'></label></td>
          		<td width=20%><div id="dis" style="visibility: hidden;"><font   face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>District &nbsp;:&nbsp; <select id="dis_value"  class='select' name="dis_value" onchange="ben_report_show('show',3,this),report('show',2,'block_value',this) " style="width:100pt"></select></div></td><td width=20%> <div id="block" style="visibility: hidden;"><font   face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>Block &nbsp;:&nbsp;<select id="block_value" class=selectbox onchange="ben_filter()" name="block_value"  style="width:100pt"></select></div></td>
         	</tr>
         	<tr>
         	<td> <input type="button" onclick="pdf_show(1,'pdf')" value="Print" class='fb2'></td>
         	</tr>
   <Tr valign="top" height=470 >
			<td colspan="5"   >
			<div id='scroll_clipper' style='position:absolute; width:920px; border-height:3px; height: 470px; overflow:auto;white-space:nowrap;'>
			<div id='scroll_text'  >
			<table bordercolor="f0f0f0" id="entred_data" align=center width="100%" border=1 cellspacing=0 cellpadding=0>
			<tr >
				<td align=center colspan=6 width=50%>	
						Maintenance
						
				</td>
				<td align=center colspan=5 width=50%>	
						Water Charges
				</td>
				 
			</tr>
			<tr >	<td valign=top width=20% align=center>Beneficiary</td>
					<!--<td valign=top width=10% align=center>City</td> -->
					
					<td  width=8% align=center>Opening <br>Balance</td>
					<td  width=8% align=center>Addns<br>if any</td>
					<td  width=8% align=center>Collection<br>upto<br>Prev Mth</td>
					<td  width=8% align=center>Int upto<Br>Prev Fin.Yr.</td>
					<td  width=8% align=center>Int Collected<Br>Current <br>Fin.Yr.</td>
					 
						<td  width=8% align=center>Opening <br>Yester Yr.</td>
					<td  width=8% align=center>Opening<br>Current<br>Yr.</td>
					<td  width=8% align=center>Demand<br>Upto<br>Prev Mth</td>
					<td  width=8% align=center>Int<br>upto Prev<br>Fin.Yr. </td>
					<td  width=8% align=center>Int<br>levied<Br>upto Prev Mth </td>
					<td  width=8% align=center>Int<br>collected<Br> upto Prev Mth </td>
					
					
			</tr>
			<tbody id="entred_body_head" align="left" >
			
			
			</tbody>
			<tbody id="entred_body" align="left"   ></tbody>
         	</table>
			</div>
			</div>
			</td>
			</Tr>
			<tr>
				<td align=center colspan=3><input class='fb2' type=button value="Exit" onclick="self.close()"></input></td>
			</tr>
</table>
   <input type=hidden id="en_rowcnt" name="en_rowcnt"> 
   <input type=hidden id="pr_status" name="pr_status" value="0"> 
      
</body>
</html>