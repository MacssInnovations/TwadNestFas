<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>Works Expenditure Details</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen" />
<script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
	<script type="text/javascript" src="../scripts/Fas_WorksExpnd.js"></script>
	<script type="text/javascript">
	
function loadDte(){
   

    var today= new Date();
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var year=today.getYear();
    if(year < 1900) year += 1900;
    document.getElementById("liveFY").length=0;
    var sele= document.getElementById("liveFY");
    var option =document.createElement("OPTION");
    option.text="--Select--";
    option.value="";
    sele.appendChild(option);
    if(month<=3){
    for(var i=0;i<3 ;i++){
   	 var year2=parseInt(year)-(parseInt(i)+1);
   	 var year1=parseInt(year)-i;
   
   	 var option =document.createElement("OPTION");
        option.text=year2+"-"+year1.toString().substring(2, 4);
        option.value=year2+"-"+year1;
        sele.appendChild(option);
   	 
    }
}	else{

    for(var i=0;i<3 ;i++){
   	 var year1=parseInt(year)-i;
   	 var year2=parseInt(year1)+1;
  
   	
   	 var option =document.createElement("OPTION");
        option.text=year1+"-"+year2.toString().substring(2, 4);
        option.value=year1+"-"+year2;
        sele.appendChild(option);
   	 
    }
}
     
    
    document.getElementById("liveTY").length=0;
    var sele1= document.getElementById("liveTY");
    var option1 =document.createElement("OPTION");
    option1.text="--Select--";
    option1.value="";
    sele1.appendChild(option1);
    if(month<=3){
    for(var i=0;i<3 ;i++){
   	 var year2=parseInt(year)-(parseInt(i)+1);
   	 var year1=parseInt(year)-i;
   
   	 var option1 =document.createElement("OPTION");
        option1.text=year2+"-"+year1.toString().substring(2, 4);
        option1.value=year2+"-"+year1;
        sele1.appendChild(option1);
   	 
    }
}	else{

    for(var i=0;i<3 ;i++){
   	 var year1=parseInt(year)-i;
   	 var year2=parseInt(year1)+1;
  
   	
   	 var option1 =document.createElement("OPTION");
        option1.text=year1+"-"+year2.toString().substring(2, 4);
        option1.value=year1+"-"+year2;
        sele1.appendChild(option1);
   	 
    }
}
    
    }

function butChk(val)
{
if(val=='Monthly')
	{
	document.getElementById("motnDiv").style.display="block";
	document.getElementById("motndetDiv").style.display="block";
	document.getElementById("divDetFin").style.display="block";
	document.getElementById("divFin").style.display="block";
	document.getElementById("hid").value="Monthly";
	}
else if(val=='yearly')
	{	
	document.getElementById("divDetFin").style.display="block";
	document.getElementById("divFin").style.display="block";
	document.getElementById("motnDiv").style.display="none";
	document.getElementById("motndetDiv").style.display="none";
	document.getElementById("hid").value="yearly";
	}
}
function fun3(v)
{
	//alert(v);
	if(v=="4" || v=="6"){
		document.getElementById("div1").style.display="block";
	}else{
		document.getElementById("div1").style.display="none";
	}
}
</script>
</head>
 <%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>
<body bgcolor="#FFF9FF" onload="loadDte();LoadAccountingUnitID('LIST_ALL_UNITS');">
<form name="frmFas_Details" id="frmFas_Details"  method="" action="" >
<input type="hidden" value="WF">
 <input type="hidden" id="cmd" name="cmd" value="yearly">
 <table width="100%" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center">Works Expenditure Details</div></td>
            </tr>
            </table>
              <div class="tab-pane" id="tab-pane-1">          
           <table width="100%" border="0" align="center">
<tr class="table">
               <td width="30%">
                  <div align="left">
                    Accounting Unit Code                   
                  </div>
                </td>
                <td width="20%">
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" >
                     <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
                             //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                            //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                            String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                            ps=con.prepareStatement(getWing);
                            ps.setInt(1,oid);
                            ps.setInt(2,empid);
                            ps.setInt(3,oid);
                            rs=ps.executeQuery();
                          
                              if(rs.next())
                              {
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                              unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                              
                              System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                              System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                              System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                              
                              }
                          System.out.println(oid+" "+oname);
                          ps.close();
                          rs.close();
                          }
                              else
                              {
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                ps.setInt(1,oid);
                                rs=ps.executeQuery();
                                  if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  }
                                  ps.close();
                                  rs.close();
                              }
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
                      </select>
                  </div>
                </td>
                <td width="50%"></td>
              </tr></table>
                <div class="tab-page">
          <h2 class="tab" >Live</h2>
           
          <div align="center">
<table width="100%" border="0" align="center">

<tr class="table">
		<td width="10%"><div align="left">&nbsp;</div></td>
		<td  width="15%">From</td>
		<td  width="15%">To</td>
		<td  width="60%">&nbsp;</td>
		</tr>

	<tr class="table">
		<td  width="10%">
		<div align="left" id="divFin">Financial Year</div>
		</td>
		<td  width="15%"><div align="left" id="divDetFin">
		<select id="liveFY" name="liveFY" style="width: 80pt;" onchange="monthLoad(this.value,'FYL');">
			<option value="">--Select--</option>			
			<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
			<option value="2014-2015">2014-15</option>
		</select></div></td>
		<td  width="15%">
		<div align="left" id="divDetFin">
		<select id="liveTY" name="liveTY" style="width: 80pt;" onchange="monthLoad(this.value,'TYL');">
			<option value="">--Select--</option>			
			<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
			<option value="2014-2015">2014-15</option>
		</select></div>
		</td>
		<td  width="60%">&nbsp;</td>		
		</tr>
	  <tr class="table">
		<td  width="10%">
		<div align="left"  id="motnDiv">Month</div>
		</td>
		<td  width="15%">
		<div align="left">
			<select id="fromMonth" name="fromMonth" style="width: 80pt;" >			
		</select></div>
		</td><td  width="15%"><div align="left" >
			<select id="toMonth" name="toMonth" style="width: 80pt;">			
		</select>		
		</div></td>
		<td  width="60%">&nbsp;</td>
	</tr>
		<tr class="table">
	<td><div align="left">Type</div></td>
	<td  colspan="2">
	<input type="radio" id="WE_R" name="WE_R" value="1167" checked="checked">Works Expenditure
	<input type="radio" id="WE_R" name="WE_R" value="1166">Funds Received
	<input type="radio" id="WE_R" name="WE_R" value="1">All
	</td><td  width="60%">&nbsp;</td>
	</tr>
	<tr class="table">
	<td><div align="left">Type</div></td>
	<td width="15%" colspan="">
	<select id="LReport" name="LReport" style="width: 5.5cm;" onchange="fun3(this.value);">
			<option value="">--Select--</option>			
	<option value="1">Summary</option>
	<option value="2">Abstract-All</option>
	<option value="5">Abstract-Unitwise</option>
	<option value="4">Abstract-Head Filter (All)</option>
	<option value="6">Abstract-Head Filter(Unitwise)</option>
	<option value="3">Details</option></select>
	</td><td  width="15%"><div id="div1" style="display: none;"><input type="text" id="head_Code"  name="head_Code" size="10"></div></td>
	<td  width="60%">&nbsp;</td>
	</tr>
	<tr class="table">
	<td><div align="left">Report Type</div></td>
	<td colspan="2">
	<input type="radio" id="rad_R" name="rad_R" value="PDF_R" checked="checked">PDF
	<input type="radio" id="rad_R" name="rad_R" value="HTML_R">HTML
	<input type="radio" id="rad_R" name="rad_R" value="Excel_R">EXCEL
	</td><td  width="60%">&nbsp;</td>
	</tr>
</table>
</div>
  <table width="100%" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" value="SUBMIT" onclick="fun_Live();"></input>               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow();"></input>
            </div>
          </td>
        </tr>
      </table>
</div>
<!--
  <div class="tab-page">
          <h2 class="tab" >Freezed</h2>
           
          <div align="center">
<table width="100%" border="0" align="center">

<tr class="table">
			<td width="10%"><div align="left">&nbsp;</div></td>
		<td  width="10%">From</td>
		<td  width="10%">To</td>
		<td  width="70%">&nbsp;</td>
		</tr>

		

	<tr class="table">
		<td width="10%">
		<div align="left" id="divFin">Financial Year</div>
		</td>
		<td width="10%"><div align="left" id="divDetFin">
			<select id="fzdFY" name="fzdFY" style="width: 80pt;" onchange="monthLoad(this.value,'FYFz');" >
			<option value="">--Select--</option>				
			<option value="2008-2009">2008-09</option>
			<option value="2009-2010">2009-10</option>
			<option value="2010-2011">2010-11</option>
			<option value="2011-2012">2011-12</option>
				<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
		
		</select></div></td>
		<td width="10%"><div align="left" id="divDetFin">
		<select id="fzdTY" name="fzdTY" style="width: 80pt;" onchange="monthLoad(this.value,'TYFz');">
			<option value="">--Select--</option>				
			<option value="2008-2009">2008-09</option>
			<option value="2009-2010">2009-10</option>
			<option value="2010-2011">2010-11</option>
			<option value="2011-2012">2011-12</option>
				<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
		
		</select></div></td>
		<td  width="70%">&nbsp;</td>
	</tr>
	  <tr class="table">
		<td width="10%">
		<div align="left" id="motnDiv">Month</div>
		</td>
		<td width="10%">
		<div align="left" id="motndetDiv">
			<select id="fromfzdMonth" name="fromfzdMonth" style="width: 80pt;">
			
		</select>
		</div>
		</td>
		<td width="10%">
		<div align="left" id="motndetDiv">
			<select id="tofzdMonth" name="tofzdMonth" style="width: 80pt;">
			
		</select>		
		</div></td>
		<td  width="70%">&nbsp;</td>
	</tr>
	<tr class="table">
	<td><div align="left">Type</div></td>
	<td  colspan="2">
		<input type="radio" id="FzWE_R" name="FzWE_R" value="1166">Works Expenditure
	<input type="radio" id="FzWE_R" name="FzWE_R" value="1167">Funds Received
	</td><td  width="70%">&nbsp;</td>
	</tr>
	<tr class="table">
	<td><div align="left">Type</div></td>
	<td  colspan="2">
	<select id="FzReport" name="FzReport" style="width: 80pt;">
			<option value="">--Select--</option>			
	
	<option value="1">Summary</option>
	<option value="2">Abstract</option>
	<option value="3">Details</option></select>
	</td><td  width="70%">&nbsp;</td>
	</tr>
	<tr class="table">
	<td><div align="left">Report Type</div></td>
	<td colspan="2">
	<input type="radio" id="FZrad_R" name="FZrad_R" value="PDF_R">PDF
	<input type="radio" id="FZrad_R" name="FZrad_R" value="HTML_R">HTML
	</td><td  width="70%">&nbsp;</td>
	</tr>
</table>
  <table width="100%" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" value="SUBMIT" onclick="fun_fzdLive();"></input>               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
</div>
</div>

--><!--

<table width="100%" border="1" align="center">
        <tr class="tdH">
        <th>Sl.No</th>
        <th>Head Code</th>
         <th>Head Desc</th>
        <th>Debit</th>
         <th>Credit</th>
        <th>Net</th>
        </tr>
        <tbody id="tbList"></tbody>
      </table>
 
     -->
   
     
      <div id="imgfld" style="position: absolute; top: 354px; visibility:hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top="100"><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200">
			</div>
</div>
</form>
</body>
</html>