<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.normal
{
    BORDER-RIGHT: #808080 2px solid;
    PADDING-RIGHT: 2px;
    BORDER-TOP: #808080 2px solid;
    PADDING-LEFT: 2px;
    FONT-WEIGHT: lighter;
    FONT-SIZE: medium;
    PADDING-BOTTOM: 2px;
    MARGIN: 16px 2%;
    BORDER-LEFT: #808080 2px solid;
    COLOR: #000000;
    PADDING-TOP: 2px;
    BORDER-BOTTOM: #808080 2px solid;
    FONT-FAMILY: 'Courier New', Courier, 'Times New Roman';
    HEIGHT: 180px;
    BACKGROUND-COLOR: #ffffff;
    TEXT-DECORATION: none

}


</style>
 <%@page import="java.util.Calendar" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="java.sql.*,java.util.ResourceBundle"%>


 <%
 String userid="112",Office_id="",Office_Name="";

Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) + 1;
int year = cal.get(Calendar.YEAR);
int yearmonthenable=2;
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

    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
    if (Office_id.equals("")) Office_id="5082";
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
	
     obj.conClose(con);
}catch(Exception e) {
	
	 
	 
	response.sendRedirect(request.getContextPath()+"/index.jsp");

}
%>
 
<script language="javascript" type='text/javascript'>

function setEnable()
{
	var set = document.getElementById("monthyearenable").value;
	if (set==1)
	{
		document.getElementById("month").disabled = true;
		document.getElementById("year").disabled = true;
	}
	else
	{
		document.getElementById("month").disabled = false;
		document.getElementById("year").disabled = false;
	}
}
function ckset(r)
{    

	vs() // enable the dcb button
	try
	{
		
		dcbshow(document.getElementById('selprocess').value);

		
		document.getElementById("bname").innerHTML="";
	}catch(e) {}
	  
	var label=document.getElementById("disvalue"+r).innerHTML;
	 
 	var row=document.getElementById("rowcnt").value;
  
 	for (i=1;i<=row;i++)
	{
 		
		if (i!=r)
		{
		document.getElementById("ch"+i).checked=false;
		document.getElementById("row"+i).style.backgroundColor="";
		} 
		 
		if (i==r)
		{
			document.getElementById("row"+i).style.backgroundColor="#dddddd";
			document.getElementById("BENEFICIARY_SNO").value=document.getElementById("select"+i).value
			
			document.getElementById("bentype").value=document.getElementById("bentype").value
		}
		document.getElementById("bname").innerHTML=label;
}
		
 			 single_vlaue('show',5);
}
function value_set(row)
{
	document.getElementById("BENEFICIARY_SNO").value=document.getElementById("select"+row).value;
}
function sh()
{
	document.getElementById('ent_div').style.visibility = 'hidden';
}

function dcbshow(process)
{
	
	document.getElementById('ent_div').style.visibility = 'hidden';
	
	if (process==1)
	{
		document.getElementById('selprocess').value=1;
		
		
		
	var dcb="<table   id='ben_entred_data width='100%' align='left' border='0' cellspacing='0' cellpadding='0' height='300'>";
	dcb+="<tbody id='ben_entred_body' align='left'>";
	dcb+="<tr class='tdH'><td  align='left' width='50%' align='center' colspan='3'><font size=3 face='Constantia'>DCB </font></td>";
 	dcb+="<tr><td width='50%' align='center' colspan='3'><font size=3 color='blue'><b>Maintenance </font></td></tr><tr><td width='70%'><font size='3' >Maintenance Due as on 31-03-<%=year%> Rs.</font></td>";
	dcb+="<td width='50%'><input class='tb5' type=text id='OB_MAINTENANCE_CHARGES' name='OB_MAINTENANCE_CHARGES' onKeyUp=isInteger(this,9,event)></input></td></tr>	<tr><td><font size=3 >Additions if any upto previous month for the current fin year  Rs.</font></td><td><input class='tb5' type=text id='ADDN_UPTO_PMTH_MCHARGES' name='ADDN_UPTO_PMTH_MCHARGES' onKeyUp=isInteger(this,9,event)></input></td></tr>";
	dcb+="<tr><td width='50%'><font size=3>Collection upto previous month for the current fin year  Rs.</font>  </td><td><input class='tb5' type=text id='COLN_UPTO_PMTH_MAINT' name='COLN_UPTO_PMTH_MAINT' onKeyUp=isInteger(this,9,event)></input></td></tr>";
		dcb+="<tr><td width='50%' align='center' colspan=2><font size=3 color='blue'><b>Water Charges</font></td></tr>";
			dcb+="<tr><td><font size=3 >Yester year  -- water charge  due as on 31-03-2004  Rs.</font></td><td><input type=text class='tb5' id='OB_YESTER_YR_upto_2004' name='OB_YESTER_YR_upto_2004' onKeyUp=isInteger(this,9,event)></input></td></tr>";
			dcb+="<tr><td><font size=3 >Water charge  due  from  01-04-2004&nbsp;  &nbsp; up to End of Current Fin.year  Rs.</font></td><td><input type=text id='OB_YESTER_YR_2004_CY' name='OB_YESTER_YR_2004_CY' class='tb5'  onKeyUp=isInteger(this,9,event)></input></td></tr>";
				dcb+="<tr><td><font size=3 >Demand upto previous month  for the current fin year</font></td><td><input class='tb5' type=text id='OB_WATER_CHARGES' name='OB_WATER_CHARGES' onKeyUp=isInteger(this,9,event)></input></td></tr>";
					dcb+="<tr><td align='center' colspan=2><font size=3 color='blue' ><b>Water Charges Collection upto Previous month </font></td></tr>";
						dcb+="<tr><td><font size=3 > On yester year demand  Rs.</font></td><td><input type=text id='COLN_UPTO_PMTH_YESTER_YR' name='COLN_UPTO_PMTH_YESTER_YR' class='tb5'  onKeyUp=isInteger(this,9,event)></input></td></tr>";
							dcb+="<tr><td><font size=3 > On Current year demand  Rs.</font></td><td><input type=text id='COLN_UPTO_PMTH_WCHARGES' name='COLN_UPTO_PMTH_WCHARGES' class='tb5'  onKeyUp=isInteger(this,9,event)></input></td></tr>";
								dcb+="<tr><td align='right'><font size=3 ><input type=button value=Submit id='button1'  class='fb2'  onclick=data_show_dcb('add',4,'0')></input></td><td><input  class='fb2' type=button value=Update onclick=data_show_dcb('add',10,'0') id='button2' ></input></font></td></tr>";
								document.getElementById('ent_div').innerHTML=dcb;;
								 

								
	}	
	else
	{
		document.getElementById('selprocess').value=2;
		var intr="<table   id='ben_entred_data width='100%' align='left' border='0' cellspacing='0' cellpadding='0' height='300'>";
		intr+="<tbody id='ben_entred_body' align='left' >";
		intr+="<tr class='tdH'><td width='70%' align='left' colspan='3'><font size=3>Interest</font></td></tr>";
		intr+="<tr><td width='50%' align='center' colspan='3'><font size=3 color='blue' >Maintenance </font></td></tr>";
		intr+="<tr><td><font size=3 >Interest upto Prev fin year  Rs. </font></td><td><input  class='tb5' type=text id='OB_INTEREST_AMT_MAINT' name='OB_INTEREST_AMT_MAINT' value='0' onKeyUp=isInteger(this,9,event)></input></td></tr>";
		intr+="<tr><td width='50%'><font size=3 >Interest collected upto prev. month for the current fin year  Rs.</font>  </td><td><input  class='tb5' type=text name='COLN_UPTO_PMTH_INTEREST_MAINT' id='COLN_UPTO_PMTH_INTEREST_MAINT' onKeyUp=isInteger(this,9,event)></input></td></tr>";
		intr+="<tr><td width='50%' align='center' colspan=2><font size=3 color='blue'>Water Charges</font></td></tr>";
		intr+="<tr><td><font size=3 >Interest upto prev fin year  Rs.</font></td><td><input  class='tb5' type=text name='OB_INTEREST_AMT_WC' id='OB_INTEREST_AMT_WC' onKeyUp=isInteger(this,9,event)></input></td></tr>";
		intr+="<tr><td><font size=3 >Interest levied upto Prev month for the current fin year  Rs.</font></td><td><input class='tb5' type=text name='INT_UPTO_CUR_MONTH_CALC' id='INT_UPTO_CUR_MONTH_CALC' onKeyUp=isInteger(this,9,event)></input></td></tr>";
		intr+="<tr><td><font size=3 >Int collected upto  Prev month  Rs.</font></td><td><input  class='tb5' type=text name='COLN_UPTO_PMTH_INTEREST_WC' id='COLN_UPTO_PMTH_INTEREST_WC' onKeyUp=isInteger(this,9,event)></input></td></tr>";
		
		intr+="<tr><td align='center' colspan=2><font size=3 ><input type=button value=Submit class='fb2' onclick=data_show_dcb('add',6,'0') id='button1'></input><input  class='fb2' type=button id='button2' value=Update onclick=data_show_dcb('add',11,'0') ></input></font></td></tr>";
		document.getElementById('ent_div').innerHTML=intr;

		}						
	   document.getElementById('ent_div').style.visibility = 'visible';
	    document.getElementById("button2").disabled = true;
	    document.getElementById("button1").disabled = false;
}
function vs()
{
	
 	document.getElementById("dcbbutton").disabled =false;
}
function ben_filter()
{
	     grid_show(3,'data','ben_data','ben_body' ,'bentype');
}
 
	 

 



 
</script>

	 

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Opening Balance - DCB    <%=Office_Name%></title>
 
       <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
     <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Bill Demand</title>
          <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
	<script type="text/javascript" src="../scripts/master.js"></script>
    <script type="text/javascript" src="../scripts/Ben_Report.js"></script>
          <script type="text/javascript" src="../scripts/iframerequest.js"></script>
      
    </head>
    <body onload=" div_sh(),year('year','ob'),month('month','ob'),setEnable(),sh() ,data_show_dcb('show',2,'bentype'),report('show',1,'dis_value','')">
<table  class="table" id="data_table" width=95% align=center border=0  cellspacing="0" cellpadding="2" valign=top>
 <tr class="tdH">
          <td align=center height="20px"   colspan=4 height=10>
          			Opening Balance -   <%=Office_Name%></td><td align="right"><blink>  <font color=red><label id="msg"></label>
          </td>
        </tr> 
		<input type="hidden" name="monthyearenable" id="monthyearenable" value='<%=yearmonthenable%>'></input>  
			 <input type=hidden id="subdiv" name="subdiv" value="0">
		<input type=hidden name="obsno" id="obsno"></input>
		</tr>
		<tr class="">
		<td width=10%><font   face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>Month&Year</font> </td>
        <td width=20% align=left>
      <!--   <input type=text id="year" name="year" value="<%=year%>" size=4></input> -->
        <select class="select" id="month" name="month"  style="width:80pt"></select><select class="select" id="year" name="year"  style="width:80pt"></select> </tD><Td width=35%><font size=3 color=blue>&nbsp;Beneficiary Name : <label id=bname></label></font>&nbsp;&nbsp;&nbsp;<label id="msg"></label></font>
         
         
         </tD>
         <td valign="center" width=20% align=right><input type=button value="DCB" id="dcbbutton" onclick="dcbshow(1)" class='fb2'><input type=hidden value="Interest" onclick="dcbshow(2)"></input>&nbsp;<input class='fb2' type=button value="Exit" onclick="self.close()"></input></td>
        </tr> 
		<tr class="">
			<td width=12%><font   face="Verdana, Arial, Helvetica, sans-serif" size="2"><b> Beneficiary Type</font> </td>
			<td width=10% align=left> <select class="select" id="bentype" name="bentype" onchange="grid_show(7,'report','','bentype','subdiv'),vs(),grid_show(3,'data','ben_data','ben_body' ,'bentype'),flash()">  </select></td>
			<td width=20%><div id="dis"><font   face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>District &nbsp;:&nbsp; <select id="dis_value" class="select"   name="dis_value" onchange="ben_report_show('show',3,this),report('show',2,'block_value',this) " style="width:100pt"></select></div></td><td align=right width=20%> <div id="block"><font   face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>Block &nbsp;:&nbsp;<select class="select" id="block_value" class=selectbox onchange="ben_filter()" name="block_value"  style="width:100pt"></select></div></td>
			
		</tr>
		
		
</table>
<table  class="table"   width=95% align=center border=0  cellspacing="0" cellpadding="0" height=300>
<tr>
    

	<td width=35% valign="top">
	<table class="table" width=100% align=left border=0  cellspacing="0" cellpadding="0"  >
	<Tr valign="top" >
	<td>

		<div id='scroll_clipper' style='position:absolute; width:345px; height: 306px; overflow:auto;white-space:nowrap;'>
   		<div id='scroll_text'  >
		<table   id="ben_data" width=100% align=left border=0  cellspacing="0" cellpadding="0"  >
				<Tr class="tdH"  >
 				 	<td width=10%><font size=3 > Select</font> </td>
				 	<td width=90%> <font size=3 >&nbsp;Beneficiary Name</font> </td>
				 </Tr> 
			 			
				 <tbody id="ben_body" align="left"  >
				 
				</tbody>
				
		</table>
		
		
	
	</div>
	</div>
	
	</tr>
	</table>
	</td>
 	 
	<td width=60% valign=top>
		<div id="ent_div">
		
		</div>
		</td>
	</tr>
</table>  
        <table class="table" bordercolor="#00FFFF"   width=60% align=center border=0   cellspacing="0" cellpadding="0">
        <tr>
        <td align=center width=2%><font size=1><a href="#A">A</a></font></td><td align=center  width=2%><font size=1><a href="#B">B</a></font></td><td  align=center width=2%><font size=1><a href="#C">C</a></font></td><td   align=center width=2%><font size=1><a href="#D">D</a></font></td><td  align=center  width=2%><font size=1><a href="#E">E</a></font></td><td align=center   width=2%><font size=1><a href="#F">F</a></font></td><td  align=center  width=2%><font size=1><a href="#G">G</a></font></td><td  align=center  width=2%><font size=1><a href="#H">H</a></font></td>
        <td align=center width=2%><font size=1><a href="#I">I</a></font></td><td align=center  width=2%><font size=1><a href="#R">J</a></font></td><td  align=center width=2%><font size=1><a href="#K">K</a></font></td><td   align=center width=2%><font size=1><a href="#L">L</a></font></td><td  align=center  width=2%><font size=1><a href="#M">M</a></font></td><td align=center   width=2%><font size=1><a href="#N">N</a></font></td><td align=center   width=2%><font size=1><a href="#O">O</a></font></td><td  align=center  width=2%><font size=1><a href="#P">P</a></font></td>
        <td align=center width=2%><font size=1><a href="#Q">Q</a></font></td><td align=center width=2%><font size=1><a href="#R">R</a></font></td><td align=center width=2%><font size=1><a href="#S">S</a></font></td><td align=center   width=2%><font size=1><a href="#T">T</a></font></td><td align=center   width=2%><font size=1><a href="#U">U</a></font></td><td align=center  width=2%><font size=1><a href="#V">V</a></font></td><td align=center   width=2%><font size=1><a href="#W">W</a></font></td><td align=center   width=2%><font size=1><a href="#Y">Y</a></font></td>
        <td align=center  width=2%><font size=1><a href="#X">X</a></font></td><td align=center  width=2%><font size=1><a href="#Y">Y</a></font></td><td  align=center width=2%><font size=1><a href="#Z">Z</a></font></td>
        </tr>
		</table>        	
   
  				<table class="table" bordercolor="#00FFFF"   width=95% align=center border=1   cellspacing="0" cellpadding="0">
  				<tr >
				<td align=left width=12% colspan=4><font size=3  >Process</font> </td>
				
				<td align=left width=5%><font size=3  >&nbsp;&nbsp;&nbsp;Select</font> </td>				
  				<td align=left width=20%><font size=3 >&nbsp;&nbsp;&nbsp;Beneficiary Type</font> </td>
				<td align=left width=50%><font size=3 >&nbsp;&nbsp;&nbsp;Beneficiary Name</font> </td>
				
 				</tr>
 				</table>
 				<center>
<div id='scroll_clipper'  style=' width:980px; border-height:1px; height:120px; overflow:auto;white-space:nowrap;'  >
<div id='scroll_text'  >
 		<table class="table" bordercolor="#00FFFF"  id="entred_data" width=95% align=center border=0   cellspacing="0" cellpadding="2">
				
 				
				 <tbody id="entred_body" align="left"   >
				 
	 
				 </tbody>
		 
		</table>
		
</div>		
</div>
</center>
<input type=hidden name="BENEFICIARY_SNO" id="BENEFICIARY_SNO"></input>
<input type=hidden name="bentype" id="bentype"></input>
<input type=hidden name="rowcnt" id="rowcnt"></input>

<input type=hidden name="en_rowcnt" id="en_rowcnt"></input>

<input type=hidden name="OFFICE_ID" id="OFFICE_ID" style="" value="5430"></input>
<input type=hidden name="selsno" id="selsno"></input>
<input type=hidden name="selbentype" id="selbentype"></input>
<input type=hidden name="selprocess" id="selprocess" value=1></input>
    <input type=hidden id="pr_status" name="pr_status" value="0"> 
   
 
 
 
 
 


</body>
</html>