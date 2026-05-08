<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<head>
<script language=javascript type='text/javascript'>

function ben_report_pump()
{   
	grid_show(3,'data','ben_data','ben_body' ,'bentype')
}
function show_prv(row)
{
	 var ben_value=document.getElementById("BENEFICIARY_SNO").value;
	 var month_value =document.getElementById("month").value;
	  var year_value =document.getElementById("year").value;
	 var mv=document.getElementById("vmonth").value
 
	 
	 var msno=document.getElementById("selsno"+row).value
	   v = window.open('prv_month_entry.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&msno="+msno,'windowname1','width=400, height=250,status=0')
	    setTimeout("v.location.href = 'javascript:self.close()'",10000);
																																																  
 }
function pump_report()
{	
	var ben_value=document.getElementById("BENEFICIARY_SNO").value;
	var ben_type=document.getElementById("bentype").value;	
	var month_value =document.getElementById("month").value;
	 var year_value =document.getElementById("year").value;
	 var mv=document.getElementById("vmonth").value
	 var yv=year_value;
	//var mv=document.getElementById("month").options[document.getElementById("month").selectedIndex].text;
	//var yv=document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
	
	
	
	document.getElementById("selected_ben").value=ben_value;
 
s=window.open('pumping_report.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&mv="+mv+"&yv="+yv+"&ben_type="+ben_type,'windowname1','width=900, height=700')
}
function calcuate(row)
{
 	var METRE_INIT_READING =document.getElementById("METRE_INIT_READING"+row).value;
	if (METRE_INIT_READING=="") METRE_INIT_READING="0";
	var METRE_FIXED=document.getElementById("METRE_FIXED"+row).innerHTML;
 	var METRE_WORKING=document.getElementById("METRE_WORKING"+row).value; 
 	var METRE_TYPE=document.getElementById("METRE_TYPE"+row).value; 
 	
	var read=document.getElementById("read"+row).value;
	if (read=="") read="0";
	
  	if (METRE_FIXED=="Yes" && METRE_WORKING=="Y")
	{
		if (parseInt(read) < parseInt(METRE_INIT_READING))
		{
 			alert("check the reading! (row no "+row+") \n------------------------------------")
			document.getElementById("nounit"+row).value=0;
			document.getElementById("read"+row).value=0;
			//document.getElementById("nounit"+row).focus();
		}else

		{
			 
		var nounit=parseInt(read)-parseInt(METRE_INIT_READING);
		if (parseInt(METRE_TYPE)==2)
			document.getElementById("nounit"+row).value=parseInt(nounit)*1000;
		else
			document.getElementById("nounit"+row).value=parseInt(nounit);
		}
	}
 	else
 	{ 
 		var s=0; 
 		//document.getElementById("nounit"+row).value=document.getElementById("read"+row).value;
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

String Date_dis=day+"/"+(month+1)+"/"+year;
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
	}catch(Exception e) {userid="0";}
	if(userid==null)
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
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
    	<script type="text/javascript" src="../scripts/master.js"></script>
        <script type="text/javascript" src="../scripts/Ben_Report.js"></script>
    
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    </head>
<body onload="div_sh(),mselection('<%=month%>') ,data_show_dcb('show',2,'bentype'),report('show',1,'dis_value','')">
<table valign=top class="table" id="" width=96% align=center border="0"   cellspacing="0" cellpadding="3">

 <tr >
          <td  class="tdH" colspan=6 height=30>
            <Center>Pumping Return -- <%=Office_Name%></Center>
                
           
          </td>
        </tr>
        <tr>
        
        <input type=hidden id="subdiv" name="subdiv"  style="width:160pt" value=0>
          
      <!--  <select id="smonth" name="smonth" style="width:100pt" onchange="combo_sel_value('smonth','vmonth')"></select> --> </tD>
        
   	
       
        <!-- <select id="syear" name="syear"  style="width:100pt" onchange="combo_sel_value('syear','year')"></select> -->  
   
       <!--  <td width=10%><font size=2 >&nbsp;Sub Division&nbsp;</font> </td> -->
       
        <td width=10%> 
        <font size=2 >&nbsp;Date : <%=Date_dis%></font>
        
        </td>
        
         <td width=17% align="left"><font size=2 >Month:</font> <input type=text class="tb1"  id="vmonth" name="vmonth" value="" size=2><input type=hidden id="month" name="month" value="<%=month%>" size=4> </input> <font size=2 >Year:</font><input type=text  class="tb1"   id="year" name="year" value="<%=year%>" size=4></input> </td>
    	 <td   width=27%  align=left><div id="dis"><font   face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>District &nbsp;: <select id="dis_value"  class=select  name="dis_value" onchange="report('show',2,'block_value',this) " style="width:100pt"></select></div> 
    	  </td ><td width=27% ><div id="block"><font   face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>Block &nbsp;:&nbsp;<select id="block_value" class=select onchange="ben_report_pump()" name="block_value"  style="width:100pt"></select></div></td>	
        </tr>
        
		<tr class="">
		
		
			 <td align=left valign="top" width=10% ><font size=2 >&nbsp;Beneficiary Type&nbsp;</font> </td>
			<td width=5% align=left> <select class="select" id="bentype" name="bentype" onchange="grid_show(3,'data','ben_data','ben_body' ,'bentype'),flash()"></select>
			&nbsp;&nbsp;&nbsp;</font></td>
			
			
			<td valign="top" width=30% colspan=3><font size=3 color=blue>&nbsp;Beneficiary Name&nbsp; :&nbsp; <label id=bname></label></font>&nbsp; :&nbsp;&nbsp; :&nbsp;</td><td valign="top" align="right"><font color="red"><label id="msg"></label></font></td>
				
		</tr>
		
</table>
 
<table class="table" width="96%" align=center border="0"  cellspacing="0" cellpadding="2"  >
<Tr valign="top" height="400">
	<td width="25%">
	<div id='scroll_clipper' style='position:absolute; width:230px; border-height:3px; height: 400px; overflow:auto;white-space:nowrap;'>
	<div id='scroll_text'  >
		<table  id="ben_data" width=100% align=center border=0   cellspacing="0" cellpadding="2"   >
				
				<Tr class="tdH" valign="top"  height=40>
 				 			<td align=center valign=top width=10%><font size=2 > Select</font> </td>
							<td align=center valign=top ><font size=2> Beneficiary</font> </td>
				</tr>
				<tbody id="ben_body" align="left"   ></tbody>
		</table>
	</div>
	</div>
	</td>

	<td valign=top" width="75%">
	<div id='scroll_clipper' style='position:absolute; width:710px; border-height:1px; height: 400px; overflow:auto;white-space:nowrap;'>
	<div id='scroll_text'  >
	
	<table  class="table" id="ben_meter_data" width=100% align=center border=0   cellspacing="0" cellpadding="2"  >
			<tR  class="tdH" valign="top" > 
						
						<td width=25% valign=top  align=left> <font class="label"> Location</font> </td>		 	
						<td width=25% valign=top  align=left> <font class="label">Scheme </font> </td>
						<td width=5% valign=top  align=center> <font class="label">Meter<br> Available</font> </td>
						<td align=5% valign=top  align=center> <font class="label">Meter<br> Working</font> </td>
 						<!--<td width=15% valign=center  align=center> <font class="label">Initial<br> Meter Reading</font> </td>  -->
						<td width=10%  valign=top align=center> <font class="label">Opening <br>Reading</font> </td>
 					 	<td width=10% valign=top  align=center> <font class="label">Closing<br>  Reading</font> </td>
					 	<td width=10% valign=top  align=center> <font class="label">No of Units[KL]</font> </td>
 					 	<td align=10% valign=top  align=center> &nbsp;</font> </td>
		   </Tr>
		   	<tbody id="ben_meter_body" align="left"   ></tbody>
	</table>
	</div>
	</div>
	</td>
	
</Tr>
 
<tr >
<td width=10% align=right><font size=2 >&nbsp;</a> </font> </td><td align=right > Total Units<input class="tb5" type=text id="netunit"  name="netunit" style="text-align: right"></input></td>

</tr>
</table>
 
				<table width=90% align=center border=0   cellspacing="0" cellpadding="2"  > 
			
				<Tr>
					 				 	<td colspan=5 align=center><input type=submit class="fb2"  value=Submit onclick="data_show_dcb('add',7,'')">&nbsp;<input type=button  class="fb2"  onclick="pump_report()" value=Report>&nbsp; <input class="fb2"   type=submit value=Exit onclick="javascript:window.close()"> </td>
				 
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
   <input type=hidden id="pr_status" name="pr_status" value="0"> 

</body>
</html>