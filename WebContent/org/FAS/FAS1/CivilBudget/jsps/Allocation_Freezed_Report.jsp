<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Allocation_Freezed_List</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<!--<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
-->
<script type="text/javascript" src="../scripts/Allocation_Freeze.js"></script>
<script type="text/javascript" language="javascript">
      function loadyear_month()
         {
    	  document.getElementById("cmbFinancialYear").length=1;
    	  var today = new Date();
  		var day = today.getDate();
  		var month = today.getMonth();
  		month = month + 1;
  		var year = today.getYear();
  		var year1 = 0;
  		var financialyear = 0;
  		var financialyear1 = 0;
  		var fin1="";
  		var fin2="";
  		if (year < 1900)
  			year += 1900;
  		if (month < 4) {
  			year1 = year - 1;
  		} else {
  			year1 = year + 1;
  		}

  		if (month < 4) {
  			financialyear = year1 + "-" + year;
  			financialyear1 = (year1-1) + "-" + (year-1);
			var ssyr1=financialyear.substring(0,5);
			var ssyr2=financialyear.substring(7,9);
  			fin1=ssyr1+ssyr2;
  			
  			var ssyr3=financialyear1.substring(0,5);
			var ssyr4=financialyear1.substring(7,9);
  			fin2=ssyr3+ssyr4;
  			
  		} else {
  			financialyear = year + "-" + year1;
  			financialyear1 = (year-1) + "-" + (year1-1);
  			var ssyr1=financialyear.substring(0,5);
			var ssyr2=financialyear.substring(7,9);
  			fin1=ssyr1+ssyr2;
  			var ssyr3=financialyear1.substring(0,5);
			var ssyr4=financialyear1.substring(7,9);
  			fin2=ssyr3+ssyr4;

  		}
  		for(var k=0;k<2;k++)
  		{
  			if(k==0)
  			{
  				var se = document.getElementById("cmbFinancialYear");
  		  		var op = document.createElement("OPTION");
  		  		op.value = fin2;
  		  		var txt = document.createTextNode(fin2);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  			}else if(k==1)
  			{
  				var se = document.getElementById("cmbFinancialYear");
  		  		var op = document.createElement("OPTION");
  		  		op.value = fin1;
  		  		var txt = document.createTextNode(fin1);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  		  		
  			}                           
  		}    
  		document.getElementById("cmbFinancialYear").value=fin1;          
         }
      </script>
</head>
<%
	String s = request.getContextPath();
String userid = (String) session.getAttribute("UserId");
System.out.println("User Id is:" + userid);
UserProfile up=null;
up=(UserProfile)session.getAttribute("UserProfile");
System.out.println("User name is:" + up.getEmployeeName());
%>


 <!-- <body onLoad="LoadAccountingUnitID_OfficeID('<%=s%>');initialLoad1('<%=s%>');loadyear_month();" bgcolor="#FFF9FF"> -->
 
<body onLoad="loadyear_month();" bgcolor="#FFF9FF">



<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget Allocation - Freezed Units List </font></div>
		</td>
	</tr>
</table>

<form name="frmAllocation_Freezed_List" id="frmAllocation_Freezed_List"
	method="POST" action="../../../../../Allocation_Freezed_Report?command=list">
	<%
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      Statement st=null;
		      PreparedStatement ps=null;
		      try
		      {
		                ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
		                String ConnectionString="";
		    
		                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
		                String strdsn=rs1.getString("Config.DSN");
		                String strhostname=rs1.getString("Config.HOST_NAME");
		                String strportno=rs1.getString("Config.PORT_NUMBER");
		                String strsid=rs1.getString("Config.SID");
		                String strdbusername=rs1.getString("Config.USER_NAME");
		                String strdbpassword=rs1.getString("Config.PASSWORD");
		                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		                //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		    
		                Class.forName(strDriver.trim());
		                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		      }
		      catch(Exception e)
		      {
		        System.out.println("Exception in connection...."+e);
		      }
    %>	
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>		</td>
	</tr>

	<!--<tr class="table1">
      <td><div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div></td>
	  <td><div align="left">
	    <select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
          </select>
	    </div></td>
	  </tr>
	<tr class="table1">
      <td><div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">
          </select>
	    </div></td>
	  </tr>
	
	--><tr class="table1">
		<td>
		<div align="left">Financial Year <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select name="cmbFinancialYear" id="cmbFinancialYear" onchange="Stat_clear();">
		</select></div>		</td>
	</tr>
	<tr class="table1">
		<td width="30%">
		<div align="left">Statement Name <font color="#ff2121">*</font></div>		</td>
		<td width="70%">
		<div align="left"><select name="Statement_Name"
			id="Statement_Name" onchange="Funclist('<%=s %>');">
			<option value="s">--- Select ---</option>
			<option value="A">All</option>
			<%
		try{
			String qry="SELECT Statement_Desc,Statement_No FROM Fas_Statement_Master  order by Statement_No";
			ps=con.prepareStatement(qry);
		rs=ps.executeQuery();
	     while(rs.next())
         {
         out.println("<option value="+rs.getString("Statement_No")+">"+rs.getString("Statement_Desc")+"</option>");
         }
     } 
     catch(Exception e)
     {
     System.out.println("Exception in Major combo..."+e);
     }
     finally
     {
     rs.close();
     ps.close();
     } %>
		
		</select></div>		</td>
	</tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>		</td>
	</tr>
</table>
</div>
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>Details</strong></div>
		</td>
	</tr>
	<tr>
	  <td colspan="2">

		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="tdH1" align="center">				
				<th width="30%" style="font-size: 13pt;">Accounting Unit Name</th>
				<th width="41%" style="font-size: 13pt;">Accounting Units for Office Name</th>
				<th width="29%" style="font-size: 13pt;">Freezed Date</th>				
			</tr>
			<tbody id="grid_body" class="table1" align="Center" width="200%">
			</tbody>
		</table>

	  </td>
	</tr>
</table>
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td>
		<div align="center">
		<!--<input type="button" name="butSub"
			id="butSub" value="LIST" onClick="Funclist('<%=s %>');" />
		&nbsp;&nbsp;&nbsp;<input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="clrForm();" /> &nbsp;&nbsp;&nbsp;-->
			 <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onClick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>