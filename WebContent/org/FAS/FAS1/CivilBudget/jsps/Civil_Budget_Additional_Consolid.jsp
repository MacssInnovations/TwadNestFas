<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil Budget Additional Consolidate</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript" src="../scripts/Civil_Budget_Additional_Consolid.js"></script>
<script type="text/javascript" src="../scripts/Un_Freeze_for_Consolidate.js"></script>
<style type="text/css">
.combolist{
width:350px !important;
}

</style>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget Additional  </font><strong> - Consolidate </strong> </div>
		</td>
	</tr>
</table>

<form name="frmCivil_Budget_Additional_Consolid" id="frmCivil_Budget_Additional_Consolid"
	method="POST" action="../../../../../Civil_Budget_Additional_Consolid?command=add" onsubmit="return checkNull()">
	
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
	
	
	<input
	type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='RecordCount1' id='RecordCount1' value='0' /><input
	type='hidden' name='filter' id='filter' value='no' />
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>		</td>
	</tr>
	
	<tr class="table1">
      <td><div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div></td>
	  <td><div align="left">
          <select name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" 
			onchange="common_LoadOffice(this.value)" class="combolist">
          </select>
      </div></td>
	  </tr>
	  
	<tr class="table1">
      <td><div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2" class="combolist">
          </select>
      </div></td>
	  </tr><%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
	<tr class="table1">
		<td width="30%"><div align="left">Financial Year <font color="#ff2121">*</font></div></td>
		<td width="70%"><div align="left">
		  <select name="cmbFinancialYear" id="cmbFinancialYear" >
		  <option value="0">Select</option>
                                   	<%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
		    </select>
		
		</div></td>
	</tr>	
	<tr class="table1">
      <td>Statement Name<font color="#ff2121">*</font></td>
	  <td><label>
        <select name="cmbStatementName" id="cmbStatementName" onChange="chooseGroup('<%=s%>');checkFreeze()">
          <option value="">---Select---</option>
        </select>
	  </label></td>
	  </tr>
	  <tr class="table1">
      <td>Statement Group<font color="#ff2121">*</font></td>
	  <td>
        <select name="statementGp" id="statementGp" onchange="callStatement('<%=s%>');callHead();callAmt('<%=s%>');">
          <option value="">---Select---</option>
        </select>
        
	  </td>
	  </tr>
	  <tr class="table1">
      <td>Type of Re-Allocation<font color="#ff2121">*</font></td>
	  <td>
        <input type="radio" name="groupId" id="groupId" value="H" onclick="blockHead();">HeadWise
        <input type="radio" name="groupId" id="groupId" value="G" onclick="blockHead();">GroupWise
        
	  </td>
	  </tr>
	  <tr class="table1">
	  <td>Total Allocation By HO<font color="#ff2121">*</font></td>
	  <td><!--
        <input type="text" readonly="readonly" name="budgetId" id="budgetId" size="20" value=0></input>
        --><input type="text" readonly="readonly" name="hoamountinlak" id="hoamountinlak" size="20" value=0></input>
        <font color="red">
		(Allocation in Rupees)
		</font>
	  </td>
	  </tr>
	 
	  <tr class="table1">
      <td>
      <div id="head_div1" name="head_div1" >
      Head Of Account<font color="#ff2121">*</font>
      </div></td>
	  <td width="80%">
	  <div id="head_div2" name="head_div2" >
        <select name="head_code" id="head_code" onchange="reallocation_fn('<%=s %>');">
          <option value="">---Choose A/c---</option>
        </select>
       <input type="text" size="5" readonly="readonly" name="groupType" id="groupType"></input>
       <input type="text" size="25" readonly="readonly" name="groupDesc" id="groupDesc"></input>
       </div>
	 </td>
	  </tr>
	 
	 
	  <tr class="table1">
	  <td>Total Additional Allocation Required<font color="#ff2121">*</font></td>
	  <td>
        <input type="text" name="reallocation" id="reallocation" size="20" value=0></input>
        <input type="button" name="submitbtn" id="submitbtn" size="20" value="GO" onclick="loadGrid()"></input>
      </td>
	  </tr></table>
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
				<th width="1%">Sl No</th>
				<th width="8%">Name Of the Offices</th>
				<th width="8%">Amount Additional Required (Rupees)<label id="l1" name="l1"></label></th>				
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
		<td colspan="2">
		<div align="center"><input type="submit" name="butSub"
			id="butSub" value="SAVE" /> &nbsp; 
			<input	type="button" name="butDelete" id="butDelete" value="DELETE"
			disabled="disabled"  onclick="deleteFn()" /> &nbsp; 
			<!--<input type="button" name="butCan" id="butCan" value="CLEAR"
			onclick="clrForm1();" /> &nbsp;&nbsp;&nbsp; 
			--><input type="button"
			name="butCan" id="butCan" value="EXIT" onClick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>