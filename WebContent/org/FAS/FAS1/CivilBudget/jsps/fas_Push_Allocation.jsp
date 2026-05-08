<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*,java.util.*"%>
    
    <%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Allocation_Push</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript" src="../scripts/Allocation_Freeze.js"></script>
<script type="text/javascript" language="javascript">
      function loadyear_month()
         {    	 
    	  var today = new Date();
  		var day = today.getDate();
  		var month = today.getMonth();
  		month = month + 1;
  		var year = today.getYear();
  		var year1 = 0;
  		var financialyear = 0;
  		var financialyear1 = 0;
  		var financialyear2 = 0;
  		var financialyear3 = 0;
  		var financialyear4 = 0;
  		var financialyear5 = 0;
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
  			financialyear2 = (year1-2) + "-" + (year-2);
  			financialyear3 = (year1-3) + "-" + (year-3);
  			financialyear4 = (year1-4) + "-" + (year-4);
  			financialyear5 = (year1-5) + "-" + (year-5);
  		} else {
  			financialyear = year + "-" + year1;
  			financialyear1 = (year-1) + "-" + (year1-1);
  			financialyear2 = (year-2) + "-" + (year1-2);
  			financialyear3 = (year-3) + "-" + (year1-3);
  			financialyear4 = (year-4) + "-" + (year1-4);
  			financialyear5 = (year-5) + "-" + (year1-5);
  		}
  		for(var k=0;k<6;k++)
  		{
  			if(k==0)
  			{
  				var se = document.getElementById("cmbFinancialYear1");
  		  		var op = document.createElement("OPTION");
  		  		op.value = financialyear5;
  		  		var txt = document.createTextNode(financialyear5);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  			}else if(k==1)
  			{
  				var se = document.getElementById("cmbFinancialYear1");
  		  		var op = document.createElement("OPTION");
  		  		op.value = financialyear4;
  		  		var txt = document.createTextNode(financialyear4);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  		  		
  			}
  			else if(k==2)
  			{
  				var se = document.getElementById("cmbFinancialYear1");
  		  		var op = document.createElement("OPTION");
  		  		op.value = financialyear3;
  		  		var txt = document.createTextNode(financialyear3);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  		  		
  			} 
  			else if(k==3)
  			{
  				var se = document.getElementById("cmbFinancialYear1");
  		  		var op = document.createElement("OPTION");
  		  		op.value = financialyear2;
  		  		var txt = document.createTextNode(financialyear2);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  		  		
  			} 
  			else if(k==4)
  			{
  				var se = document.getElementById("cmbFinancialYear1");
  		  		var op = document.createElement("OPTION");
  		  		op.value = financialyear1;
  		  		var txt = document.createTextNode(financialyear1);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  		  		
  			}
  			else if(k==5)
  			{
  				var se = document.getElementById("cmbFinancialYear1");
  		  		var op = document.createElement("OPTION");
  		  		op.value = financialyear;
  		  		var txt = document.createTextNode(financialyear);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  		  		
  			}
  		}    
  		document.getElementById("cmbFinancialYear1").value=financialyear;          
         }
      </script>
</head>
<%
	String s = request.getContextPath();
	System.out.println("s");
%>

<body
	onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad1('<%=s%>');loadyear_month();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget - Push for Budget Allocation </font></div>
		</td>
	</tr>
</table>

<form name="frmAllocation_Freeze" id="frmAllocation_Freeze"
	method="POST" action="../../../../../Allocation_Freeze">
	
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
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>

	<tr class="table1">
		<td>
		<div align="left">Budget Allocated Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
		</select></div>
		</td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">Budget Allocated Accounting For Office Code <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">
		</select></div>
		</td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">Financial Year <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmbFinancialYear"
			id="cmbFinancialYear" onChange="ChangeYearDuration();">
			<option value="0">Select</option>
                                   	<%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
			
		</select></div>
		</td>
	</tr>
	<!--<tr class="table1">
		<td>
		<div align="left">Freeze Type <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmbFreezeType"
			id="cmbFreezeType">			
			<option value="FBA">Freeze-Budget Allocation</option>			
		</select></div>
		</td>
	</tr>
	--><tr class="table1">
		<td width="35%">
		<div align="left">Statement Name <font color="#ff2121">*</font></div>
		</td>
		<td width="65%">
		<div align="left"><select name="cmbFormat_Name"
			id="cmbFormat_Name">
			<option value="">--- Select ---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>
		</td>
	</tr>
</table>
</div>

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td>
		<div align="center"><input type="button" name="butSub"
			id="butSub" value="SUBMIT" onClick="push_Allocation('<%=s%>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="clrForm();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onClick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>