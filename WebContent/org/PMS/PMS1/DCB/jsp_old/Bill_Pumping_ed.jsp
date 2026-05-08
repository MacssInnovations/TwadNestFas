<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>

<head>
<style type="text/css">
form.cmxform { width: 30em; }
form.cmxform label.error {
	display: block;
	margin-left: 1em;
	width: auto;
}

</style>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>

<script language="javascript" type="text/javascript">

function ben_report_pump()
{   
	grid_show(3,'data','ben_data','ben_body' ,'bentype')
}
function pump_report()
{	
	var ben_value=document.getElementById("BENEFICIARY_SNO").value;
	var month_value =document.getElementById("month").value;
	 var year_value =document.getElementById("year").value;
	 var mv=document.getElementById("vmonth").value
	 var yv=year_value;
	//var mv=document.getElementById("month").options[document.getElementById("month").selectedIndex].text;
	//var yv=document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
	
	
	
	document.getElementById("selected_ben").value=ben_value;
 
s=window.open('pumping_report.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&mv="+mv+"&yv="+yv,'windowname1','width=900, height=700')
}
function calcuate(row)
{
	
 	var METRE_INIT_READING =document.getElementById("METRE_INIT_READING"+row).value;
	if (METRE_INIT_READING=="") METRE_INIT_READING="0";
	var METRE_FIXED=document.getElementById("METRE_FIXED"+row).value;
 	var METRE_WORKING=document.getElementById("METRE_WORKING"+row).value; 
 	
	var read=document.getElementById("read"+row).value;
	 
	 
	if (read=="") read="0";
  	if (METRE_FIXED=="Y" && METRE_WORKING=="Y")
	{ 
  		  
		if (parseInt(read) < parseInt(METRE_INIT_READING))
		{
 			alert("check the reading! (row no "+row+") \n------------------------------------")
			document.getElementById("nounit"+row).value=0;
			document.getElementById("read"+row).value=0;
			document.getElementById("nounit"+row).focus();
		}else

		{ 
			 
		var nounit=parseInt(read)-parseInt(METRE_INIT_READING);
		document.getElementById("nounit"+row).value=parseInt(nounit);
		}
	}
 	else
 	{ 
 		 
 		document.getElementById("nounit"+row).value=document.getElementById("nounit"+row).value;
 	 }
	
 	var row=document.getElementById("rowcnt_meter").value;
 	var tot_read=0;
 
	for (i=1;i<=row;i++)
	{
		tot_read+=parseInt(document.getElementById("nounit"+i).value);
	}
	 
		document.getElementById("netunit").value=tot_read;
}

function ckset(r)
{
	  
	var label=document.getElementById("disvalue"+r).innerHTML;
	document.getElementById("bname").innerHTML="";
	 
 	var row=document.getElementById("rowcnt").value;
  
 	for (i=1;i<=row;i++)
	{
		 
		 
		if (i!=r)
		{
		document.getElementById("netunit").value=0;
		document.getElementById("ch"+i).checked=false;
		document.getElementById("row"+i).style.backgroundColor="";
		} 
		if (i==r)
		{
			document.getElementById("netunit").value=0;
			document.getElementById("row"+i).style.backgroundColor="#dddfdd";
			document.getElementById("BENEFICIARY_SNO").value=document.getElementById("select"+i).value
			document.getElementById("bentype").value=document.getElementById("bentype").value
		}

		
		
		document.getElementById("bname").innerHTML=label;
		
	}
 	var month =document.getElementById("month").value;
	var year=document.getElementById("year").value;
	 
		var vtype=document.getElementById("bentype").value;

		 
		
			 	
		
		
		
		if (vtype!=6)
		{
			 grid_show(4,'data','ben_meter_data','ben_meter_body','bentype')
			 document.getElementById('block').style.visibility = 'hidden';
			 document.getElementById('dis').style.visibility = 'hidden';	
		}
		else
		{
			grid_show(4,'data','ben_meter_data','ben_meter_body','bentype')
		}
		
	 
}
function value_set(row)
{
	document.getElementById("BENEFICIARY_SNO").value=document.getElementById("select"+row).value;
}
 

function sh()
{
	document.getElementById('hlab').innerHTML=""
}
function mselection(row)
{ 
	document.getElementById("vmonth").value=monthselect(row) ;
	 
}
</script>
<%
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) ;

int year = cal.get(Calendar.YEAR);

 if (month >=4)
	year=year;
else
	year=year;

int pumpingfalg=0;
/*

	flag from setting table
	pumpingfalg=1

*/

String Date_dis=day+"/"+month+"/"+year;
String userid="0",Office_id="",Office_Name="";
Controller obj=new Controller();
Connection con;
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

String ben_value="";
ben_value=request.getParameter("ben_value");
if (ben_value==null) ben_value="0";
	
%>
 

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
 
       <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
         <link href="../../../../../css/label.css" rel="stylesheet" media="screen"/> 
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pumping Return </title>
          <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
    	<script type="text/javascript" src="../scripts/master.js"></script>
        <script type="text/javascript" src="../scripts/Ben_Report.js"></script>
    
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    </head>
<body 	onload="data_show('show',11,<%=ben_value%>)">
<form  >

<table valign=top class="table" id="" width=96% align=center border="0"   cellspacing="0" cellpadding="5">

 <tr >
      <td  class="tdH" colspan=2  height=30>
       <Center>Pumping Return -- <%=Office_Name%></Center>
                
           
          </td>
        </tr>
        <tr>
        <td colspan=2 align=right> 
        <font size=2 >&nbsp;Date :</font> <font size=2><%=Date_dis%></font>&nbsp;&nbsp;&nbsp;<label id="msg"></label></font>
        
        </td>
        </tr>
        <tr>        
         <td  align="left"><font size=2  >&nbsp;Pumping Month & Year:</font></td><td> <input type=text id="vmonth" name="vmonth" value="" class="tb0" size=2><input type=hidden id="month" name="month" value="<%=month%>" size=4 > </input>
         &<input type=text id="year" name="year" value="" size=4 class="tb1"></input> </td>
         </tr>
        
		<tr class="">
			<td  width="30%" align=left ><font size=2  >&nbsp;Beneficiary Type&nbsp;</font> </td>
			<td   align=left id="bentype" name="bentype"  ></td>
		</tr>
		<tr class="">	
			<td>
					<font size="2" >&nbsp;Beneficiary Name&nbsp; :&nbsp;</font></td>
					<td  > <label id="ben_name"></label></td>
 		</tr>
		
			 
</table>
 
<table class="table" width=96% align=center border=1  cellspacing="0" cellpadding="2"  bordercolor="blue">
<Tr>
 
	<td colspan=2>
	
	<table  class="table" id="ben_meter_data_ed" width=100% align=center border="0" cellspacing="0" cellpadding="2"  >
			<tR  class="tdH" valign="top" > 
						
						<td   valign=top  align=left> <font class="label"> Location</font> </td>		 	
						<td   valign=top  align=left> <font class="label">Scheme </font> </td>
						<td width=5% valign=top  align=center> <font class="label">Meter<br> Available</font> </td>
						<td align=5% valign=top  align=center> <font class="label">Meter<br> Working</font> </td>
 						<td width=10%  valign=top align=center> <font class="label">Opening <br>Reading</font> </td>
 					 	<td width=10% valign=top  align=center> <font class="label">Closing<br>  Reading</font> </td>
					 	<td width=10% valign=top  align=center> <font class="label">No of Units</font> </td>
 		   </Tr>
		   	<tbody id="ben_meter_body_ed" align="left"   ></tbody>
	</table>
	</td>
	
</Tr>
 
<tr >
<td align=right colspan=2> Total Units<input class="tb5" type=text id="netunit"  name="netunit" style="text-align: right" size="7"></input></td>

</tr>
</table>
 
				<table width=90% align=center border=0   cellspacing="0" cellpadding="2"  > 
			
				<Tr>
					 				 	<td colspan=5 align=center>&nbsp;<input type=submit value=Submit class="fb2" onclick="data_show('add',12,'0')">&nbsp;<input type=button class="fb2" onclick="pump_report()" value=Report> <input type=button class="fb2" value=Exit onclick="javascript:window.close()"> </td>
				 
				</Tr> 
				
				</table>
<!-- <div align="center" style="position:absolute;top:10; left:20; width:100%; height:175;">
		<table   id="entred_data" width=90% align=center border=0   cellspacing="0" cellpadding="2">
				<tr >
				<td align=center width=5%><font size=2  >Select</font> </td>				
  				<td align=left width=15%><font size=2 >Beneficiary Type</font> </td>
				<td align=left width=70%><font size=2 >Beneficiary Name</font> </td>
				<td align=center width=5%><font size=2  >Select</font> </td>
				</tr>
				 <tbody id="entred_body" align="left"   >
				 
	      
				 </tbody>
		 
		</table>
		
</div>
 -->				 
 
<input type=hidden name="BENEFICIARY_SNO" id="BENEFICIARY_SNO"></input>
<input type=hidden name="selbentype" id="selbentype"></input>
<input type=hidden name="rowcnt" id="rowcnt"></input>
<input type=hidden name="rowcnt_meter" id="rowcnt_meter"></input>
<input type=hidden name="OFFICE_ID" id="OFFICE_ID" value="5430"></input>
<input type="hidden" id="selected_ben" name="selected_ben" value="0"></input>
<input type="hidden" id="MONPR_SNO" name="MONPR_SNO" value="0"></input>
   <input type=hidden id="pr_status" name="pr_status" value="0"> 

</form>
</body>
</html>