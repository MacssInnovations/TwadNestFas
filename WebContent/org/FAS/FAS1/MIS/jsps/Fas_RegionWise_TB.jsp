<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>RegionWise Trial Balance Details</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    

    <script language="javascript" type="text/javascript" src="../scripts/Fas_Region_TB.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>     
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>     
    <script type="text/javascript" language="javascript">

    
       
    </script>
      </head>
       <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table"  onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1')">
  <form name="frm_List" method="GET" action="../../../../../fas_RegWise_Report">
   <input type="hidden" id="Hid_text" name="Hid_text" value="">
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>RegionWise Trial Balance</strong>
          </div>
        </td>
      </tr>
    </table>
    <table width="100%" border="0" align="center">
    <tr class="table">
   	<td width="10%"><div align="left">Region Wise</div></td>   	
  <td  width="10%" colspan="2">
  <select id="cmb_REgion_Data" name="cmb_REgion_Data">
  <%
  String qry="SELECT REGION_OFFICE_ID,CIRCLE_OFFICE_ID,OFFICE_LEVEL_ID,OFFICE_ID  ,OFFICE_NAME as reg"+
	  " FROM COM_MST_ALL_OFFICES_VIEW  WHERE OFFICE_LEVEL_ID='RN'  order by OFFICE_NAME";
  try{
	  ps=con.prepareStatement(qry);
	  rs=ps.executeQuery();
	  out.println("<option value=0>All</option>");
	  while(rs.next()){
		  out.println("<option value="+rs.getInt("REGION_OFFICE_ID")+">"+rs.getString("reg")+"</option>");
	  }	  
  }catch(Exception e)
  {
	  out.println(" Exception  "+e);
  }
  %>
  </select>
  </td>
	<td  width="70%">&nbsp;</td>
    </tr>
<tr class="table">
		<td width="10%"><div align="left">&nbsp;</div></td>
		<td  width="10%">From</td>
		<td  width="10%">To</td>
		<td  width="70%">&nbsp;</td>
		</tr>

	<tr class="table">
		<td  width="10%">
		<div align="left" id="divFin">Financial Year</div>
		</td>
		<td  width="10%"><div align="left" id="divDetFin">
		<select id="liveFY" name="liveFY" style="width: 80pt;" onchange="monthLoad(this.value,'FYL');">
			<option value="">--Select--</option>
			<option value="2009-2010">2009-10</option>
			<option value="2010-2011">2010-11</option>
			<option value="2011-2012">2011-12</option>			
			<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
			<option value="2014-2015">2014-15</option>
		</select></div></td>
		<td  width="10%">
		<div align="left" id="divDetFin">
		<select id="liveTY" name="liveTY" style="width: 80pt;" onchange="monthLoad(this.value,'TYL');">
			<option value="">--Select--</option>			
		    <option value="2009-2010">2009-10</option>
			<option value="2010-2011">2010-11</option>
			<option value="2011-2012">2011-12</option>			
			<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
			<option value="2014-2015">2014-15</option>
		</select></div>
		</td>
		<td  width="70%">&nbsp;</td>		
		</tr>
	  <tr class="table">
		<td  width="10%">
		<div align="left"  id="motnDiv">Month</div>
		</td>
		<td  width="10%">
		<div align="left">
			<select id="fromMonth" name="fromMonth" style="width: 80pt;" >			
		</select></div>
		</td><td  width="10%"><div align="left" >
			<select id="toMonth" name="toMonth" style="width: 80pt;">			
		</select>		
		</div></td>
		<td  width="70%">&nbsp;</td>
	</tr>
	<tr class="table">
	<td width="10%"><div align="left">Report Type</div></td>
	<td width="10%" colspan="2">
	<input type="radio" id="rad_R" name="rad_R" value="PDF">PDF	
	<input type="radio" id="rad_R" name="rad_R" value="HTML">HTML
		<input type="radio" id="rad_R" name="rad_R" value="EXCEL">EXCEL
	</td>
	<td  width="70%">&nbsp;</td>
	</tr>      
</table>
  <table width="100%" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit" ></input>               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>  
      <div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>   
      </form>
  </body>
</html>