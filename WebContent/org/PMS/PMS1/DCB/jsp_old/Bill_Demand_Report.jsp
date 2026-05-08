<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
     <%@ page import="java.sql.*,java.util.ResourceBundle"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
 <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
  <script type="text/javascript" src="../scripts/cellcreate.js"></script>
   <script type="text/javascript" src="../scripts/Bill_Demand_Report.js"></script>
   <script language=javascript src="../scripts/RIW.js"></script>
</head>

<%
String BENEFICIARY_TYPE_SNO="0";
String maxsno=request.getParameter("maxsno");
if (maxsno=="" || maxsno==null ) maxsno="0";
Controller obj=new Controller();
Connection con;
try
{
con=obj.con();
obj.createStatement(con);

   String BENEFICIARY_SNO=obj.getValue("PMS_DCB_TRN_BILLING_DMD", "BENEFICIARY_SNO", "where BILL_SNO="+maxsno) ;


  BENEFICIARY_TYPE_SNO=obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID", "where BENEFICIARY_SNO="+BENEFICIARY_SNO) ;
  
  if (BENEFICIARY_TYPE_SNO.equalsIgnoreCase("")) BENEFICIARY_TYPE_SNO="0";
}catch(Exception e) {
}
int col=0;
if (Integer.parseInt(BENEFICIARY_TYPE_SNO) > 6)
{
	col=11;
}
else
{
	col=4;
}

%>
<body onload="demand_show('show',1,<%=maxsno%>),flash()">
<table align="center" width="85%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center">
				<font size=5>Tamil Nadu Water Supply and Drainage Board</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <font color=red><label id="msg"></label></font>
			</td>
		</tr>
		<tr>
			<td align="center">
				<font size="3">Bill of cost water supplied during the month ending <label id='monthlabel'></label></font> 
			</td>
		</tr>
</table>
<table align="center" width="85%" cellpadding="0" cellspacing="0">
<tr height="34">
<td  align="left">Executive Engineer,TWAD Board<br>
		<label id="divaddr-label"></label> 
</td>
<td  align="left">
		<label id="divname-label"></label>
</td>

</tr>
<tr height="34"><td>&nbsp;</td></tr>
<tr height="34">
<td align="left">
		<label id="ben-label"></label>
</td>
<td align="left">
		<table>
		<tr><Td>Bill No </Td><td id='billno'></td><td>Dated</td><td id='billdate'></td></tr>
		<tr><Td>Billing From </Td><td id='billnofrom'></td><td>To</td><td id='billnoto'></td></tr>
		</table>
		
</td>
</tr>
<tr>
	<td align="left" colspan="8">Details of Meter Reading and Consumption </td>
</tr>
</table>
<table align="center" width="90%" id="demand_meter_table" border="1" cellpadding="0" cellspacing="0">
<tr>
 
 		<td align=center width="25%">Location</td>
		<td align=center width="5%">Final Reading</td>
		<td align=center width="5%">Initial Reading</td>
		<%
		if (Integer.parseInt(BENEFICIARY_TYPE_SNO) > 6) 
		{
		%>		
		<td align=center width="5%">Difference(KL)</td>
					
		
		<%} %>	
					
		
		<td align=center width="5%">Total Consumption(KL)</td>
		<td align=center width="5%" id='rate_type'> </td> 
		<%
		if (Integer.parseInt(BENEFICIARY_TYPE_SNO) > 6) 
		{
		%>
		<td align=center width="5%"> Min Bill Qty(KL)</td>
		<%} %>
		<%
		if (Integer.parseInt(BENEFICIARY_TYPE_SNO) > 6) 
		{
		%>
		<td align=center width="2%">Tariff Rate(Rs.)</td>
		<%} %>
		
		<%
		if (Integer.parseInt(BENEFICIARY_TYPE_SNO) > 6) 
		{
		%>
		<td align=center width="2%">Alloted Qty(KL)</td>
		<td align=center width="2%">Excess Consumption(KL)</td>
		<td align=center width="2%">Excess Rate(Rs.)</td>
		<td align=center width="2%">Excess Amount(Rs.)</td>
 		 <%} %> 
		 <td  width="5%" align="center">Amount Rs.</td>
 </tr>
 
<tbody id="demand_meter_body"></tbody>
<tr>
		<td colspan="<%=col-6%>" align=left>Total Consumption</td>
		<td  align="right" id="total_consumption_td"></td>
		<td  align="right">&nbsp;</td>
</tr>
<tr>
		<td colspan="<%=col+1%>" align=left>Gross Water Charges</td>
		<td align="right" id="total_cross_watercharges_td"></td>
</tr>
<tr>

		<td  align="left">Add Supplements <br><< Penalty@<label id='pen_rate'></label>% for Rs.<label id='we_ob'></label>>></td>
		<td colspan="<%=col%>" align=left id="total_penalty_td"></td>
		
</tr>
<tr>

		<td  align=left  >Net Total </td>
	<td  colspan="<%=col+1%>" align=right id="total_wc_amount"></td>
</tr>
<tr>

		<td  align=left cols>Scheme Name & Type</td>
		<td colspan=6 align=left id="scheme_name_td"></td>
</tr>

</table>
<table align=center width=60% id="sch_table" border=1 cellpadding=0 cellspacing=0>
<tr>
	<td width=35%><B> Scheme Type </td>
	<td  width=35%><B> Scheme Name </td>
	<td width=15%><B> Total Qty Consumed </td>
</tr>

<tbody id="sch_body"></tbody>

</table>

<table align=center width=85%  cellpadding=0 cellspacing=0 border="1">
	<tr>
		<td>Bill Value is <label id="bill_value_word"></label></td> 
	</tr>
	<td>
		<table align=left width=85%  cellpadding=0 cellspacing=0 border=0>
			<tr>
						 <td>&nbsp;</td>
						 <td>&nbsp;</td>
						  <td>&nbsp;</td>
						 <td align=center>WC</td>
						 <td align=center>MC</td>
						 <!-- <td align=right>Penalty</td> -->
				</tr>
				<tr>
						 <td>Amount due to end of Previous Month  </td>
						 <td align=right width=2%>Rs.</td>
						 <td width=15% align=right id="tot_wc_cb_ob"></td>
						 <td width=15% align=right id="wc_cb_ob"></td>
						 <td width=15%   align=right id="main_cb_ob"></td>
						<!--  <td align=right id="pen_cb_ob"></td> -->
				</tr>
				<tr>
						 <td>Amount Received duriung Month  </td>
						 <td align=right width=2%>Rs.</td>
						 <td width=15% align=right id="tot_wc_cb_rec"></td>
						 <td width=15% align=right id="wc_cb_rec"></td>
						 <td width=15% align=right id="main_cb_rec"></td>
						<!--  <td id="pen_cb_rec"></td> -->
				</tr>
				<tr>
						 <td>Balance  </td>
						 <td  width=2% align=right>Rs.</td>
						 <td width=15% align=right id="tot_wc_cb_bal"></td>
						 
						 <td width=15% align=right id="wc_cb_bal"></td>
						 <td width=15% align=right id="main_cb_bal"></td>
						 <!-- <td id="pen_cb_bal"></td> -->
				</tr>
				<tr>
						 <td>Amount of this Bill</td>
						 <td  width=2% align=right>Rs.</td>
						 <td width=15% align=right id="amount_this"></td>
				</tr>
				<tr>
						 <td>Outstanding Penalty</td>
						 <td  width=2% align=right>Rs.</td>
						 <td width=15% align=right id="out_pen">0.00</td>
				</tr>
				<tr>
						 <td align=left>Total Amount due to end of this month </td>
						 <td  width=2% align=right>Rs.</td>
						 <td width=15% align=right id="total_amount_due"></td>
				</tr>
		</table>
	
	</td>
</table>
<table align=center width=85%  cellpadding=0 cellspacing=0>
<tr>
	<p>The amount shouldbe send by Local Bank Cheque (Subjet to Realistation)  or  DD in favour of Excecutive Engineer ,TWAD Board ,Exeecutive Engineer ,TWAD Board <label id='div_lable'></label></p>
</tr>
</table>
<table align=center width=85%  cellpadding=0 cellspacing=0>
<tr>
<td  align=right>Executive Engineer,TWAD Board<br>
		<label id='divaddr-label_bottom'></label> 
</td>
</tr>
<tr>
<td  align=center>Copy to Assistant Executive Engineer,TWAD Board.
		 
</td>
</tr>

</table>
   <input type=hidden id="pr_status" name="pr_status" value="0"> 

</body>
</html>
