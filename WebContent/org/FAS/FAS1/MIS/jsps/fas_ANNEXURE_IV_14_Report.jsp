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
   <META HTTP-EQUIV="Expires" CONTENT="-1" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>MWSS MIS Reports</title>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
    <script language="javascript" type="text/javascript"
            src="../scripts/GPFReport_Abstract.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
   <script type="text/javascript" src="../scripts/Fas_NRDWP_Details.js"></script>         
   <script type="text/javascript" src="../scripts/Fas_NRDWP_Details.js"></script>
    <script type="text/javascript" language="javascript">
    
    </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function ChooseReptype(id)
                {
                   
                    var dispsupnochosen1=document.getElementById("dispsupno1");
                    var dispsupnochosen2=document.getElementById("dispsupno2");
                    if(id=="Regular")
                    {
                             
                            dispsupnochosen1.style.display="none";
                            dispsupnochosen2.style.display="none";
                            document.getElementById("txtsupplement_no").value=0;
                    }
                    else
                    {
                           
                            dispsupnochosen1.style.display="block";
                            dispsupnochosen2.style.display="block";
                            alert("Enter the Supplement Number");
                    }
                }
   function callSJVload()
   {
	   
	   var month_chosen=document.getElementById("txtCB_Month").value;
	 
	   var dispsjv=document.getElementById("dispSJV");
	   if(month_chosen==3)
		   {
		   		
		   		dispsjv.style.display="block";
		   }
	   else
		   {
				dispsjv.style.display="none";
		   }
	
   }
   function callSJVload_(mnth)
   { 
	   var dispsjv=document.getElementById("dispSJV");
   if(mnth==3)
   {
   		
   		dispsjv.style.display="block";
   }
else
   {
		dispsjv.style.display="none";
   }
   }
		
   function Chk_Month(val){
	   document.getElementById("hid_val").value=val;
	   if( document.getElementById("Type_btn1").style.checked==true)
	   {
	   document.getElementById("Type_btn").style.checked=false;
	   }
	/* document.getElementById("MnthYearTit").style.display="none";
	document.getElementById("MnthYearTit1").style.display="block";
	document.getElementById("MnthYear").style.display="none";
	document.getElementById("MnthYear1").style.display="block"; */
	   document.getElementById("divFin").style.display="none";
		document.getElementById("divFin1").style.display="none";
		document.getElementById("divFin").style.display="none";
		document.getElementById("divFin1").style.display="none";
		document.getElementById("divDetFin").style.display="none";
		document.getElementById("divDetFin1").style.display="none";
		document.getElementById("motnDiv").style.display="none";
		document.getElementById("divmotnDiv").style.display="none";
		document.getElementById("MnthYear").style.display="block";
		document.getElementById("MnthYearTit").style.display="block";
   }
   function Chk_Date(val){
	   document.getElementById("hid_val").value=val;
	   if( document.getElementById("Type_btn").style.checked==true)
		   {
		   document.getElementById("Type_btn1").style.checked=false;
		   }
	  /*  document.getElementById("MnthYearTit1").style.display="none";
		document.getElementById("MnthYearTit").style.display="block";
		document.getElementById("MnthYear1").style.display="none";
		document.getElementById("MnthYear").style.display="block";  */
		document.getElementById("divFin").style.display="block";
		document.getElementById("divFin1").style.display="block";
		document.getElementById("divFin").style.display="block";
		document.getElementById("divFin1").style.display="block";
		document.getElementById("divDetFin").style.display="block";
		document.getElementById("divDetFin1").style.display="block";
		document.getElementById("motnDiv").style.display="block";
		document.getElementById("divmotnDiv").style.display="block";
		document.getElementById("MnthYear").style.display="none";
		document.getElementById("MnthYearTit").style.display="none";
		
   }
   function chk_month(val){
	var txtFromCB_Year=document.getElementById("txtFromCB_Year").value;
	if(txtFromCB_Year=="" || txtFromCB_Year==null){
		alert('Enter From Date ... ');
		document.getElementById("txtFromCB_Year").value="";
		document.getElementById("txtToCB_Year").value="";
		document.getElementById("txtFromCB_Year").focus();
	}
	
	
	var From_Date=txtFromCB_Year.split("/")[1];
	var T_Date=val.split("/")[1];
	// added to check the start date must be 01/04/yyyy and end date may be 31/12/yyyy or 31/03/yyyy......if end date is march 		// supplement should include
	/*if(txtFromCB_Year.split("/")[2]>val.split("/")[2])
		{
		alert('Enter Correct ToDate ... ');
		document.getElementById("txtToCB_Year").value="";
		}else {
			if(txtFromCB_Year.split("/")[1]>val.split("/")[1]){
				alert('Enter Correct ToDate ... ');
				document.getElementById("txtToCB_Year").value="";
			}else{
				if(From_Date<='03'   || T_Date>='03' ){
					callSJVload_(3);
				}else if(From_Date!='03' && T_Date!='03'){
					callSJVload_(2);
				}else if(From_Date>'03' && T_Date<'03'){
					callSJVload_(2);
				}	
			}
			
		}*/
	var From_Date_date=txtFromCB_Year.split("/")[0];
	var T_Date_date=val.split("/")[0];
	
	if( txtFromCB_Year.split("/")[2]>val.split("/")[2] )
		{
		alert('Enter Correct ToDate ... ');
		document.getElementById("txtToCB_Year").value="";
		}
	 if ( (txtFromCB_Year.split("/")[2] == val.split("/")[2]) || (txtFromCB_Year.split("/")[2] < val.split("/")[2]) ){
		//alert("If the same year");
		if ( (From_Date != 04) || (From_Date_date !=01) )
			{
			alert("The From Date should start with 01/04/yyyy...");
			document.getElementById("txtFromCB_Year").value="";
			document.getElementById("txtToCB_Year").value="";
			document.getElementById("txtFromCB_Year").focus();
			
			}
		
		if( (T_Date==1) || (T_Date ==2) || (T_Date==3) ) 
				{
				if(From_Date>'03' && T_Date!='03'){
					callSJVload_(2);
				}else if(From_Date>'03' && T_Date=='03'){
					callSJVload_(3);
				}	
				}
		}		
			
   }
   function To_cler(){
	   document.getElementById("txtToCB_Year").value="";
   }
    </script>
    

  </head>
     <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table" onload="loadyear_month();callServer_LoadSection();setTimeout('callSJVload()',100);">
  <form action="../../../../../Annexure_Report?Command=REport"
                                                       name="AnnexRep" id="AnnexRep"
                                               method="post">
                                                      
 <input type="hidden" id="hid_val" name="hid_val" value="MonthWise">                                                     
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <th class="tdH" colspan="2">
            <center>MWSS MIS Reports</center>
          </th>
        </tr></table>
        <br>
         <table cellspacing="2" cellpadding="3" border="0" width="100%" align="center">
            <tr align="left">
          <th class="tdH" colspan="4">
           General Details
          </th>
        </tr>
           <tr align="left">
          <td  class="table">
            <div align="left">Cash Book Year &amp; Month Type</div>
            </td> <td  class="table">
             <input type="radio" id="Type_btn" name="MonYear1" value="MonthWise" checked="checked" onclick="Chk_Month(this.value)"/>MonthWise
            <input type="radio" id="Type_btn1" name="MonYear1" value="DateWise" onclick="Chk_Date(this.value)"/>PeriodWise
           </td>
            </tr>
         
        <tr align="left">
          <td class="table" >
            <div align="left" id="MnthYearTit">Cash Book Year &amp; Month</div>
          </td>
          <td >
            <div align="left" id="MnthYear">
              <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="callSJVload();">
                <option value="1">January</option>
                <option value="2">February</option>
                <option value="3">March</option>
                <option value="4">April</option>
                <option value="5">May</option>
                <option value="6">June</option>
                <option value="7">July</option>
                <option value="8">August</option>
                <option value="9">September</option>
                <option value="10">October</option>
                <option value="11">November</option>
                <option value="12">December</option>
              </select>
            </div>
          </td>
        </tr>
     <!--     <tr align="left">
          <td class="table">
            <div align="left" id="MnthYearTit1" style="display: none;">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left" id="MnthYear1" style="display: none;"> From 
              <input type="text" name="txtFromCB_Year" id="txtFromCB_Year" 
                     maxlength="" size="10" onchange="To_cler();" onclick="javascript:alert('Like This Format dd/mm/yyyy');"></input>
               To 
              <input type="text" name="txtToCB_Year" id="txtToCB_Year" 
                     maxlength="" size="10" onclick="javascript:alert('Like This Format dd/mm/yyyy');" onchange="chk_month(this.value)"></input>
             
            </div>
          </td>
        </tr> -->
        <tr class="table">
		<td ><div align="left"  id="divFin1" style="display: none;">&nbsp;</div></td>
		<td><div align="left" id="divDetFin1" style="display: none;"><table width="80%"><tr><td width="40%" >From</td>
		<td  width="40%">To</td></tr></table></div>
	</td>
		</tr>
        	<tr class="table">
		<td >
		<div align="left" id="divFin" style="display: none;">Financial Year</div>
		</td>
		<td ><div align="left" id="divDetFin" style="display: none;">
		<table  width="80%"><tr><td  width="40%">
		<select id="liveFY" name="liveFY" style="width: 80pt;" onchange="monthLoad(this.value,'FYL');">
			<option value="">--Select--</option>			
			<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
			<option value="2014-2015">2014-15</option>
			<option value="2015-2016">2015-16</option>
			<option value="2016-2017">2016-17</option>
			<option value="2017-2018">2017-18</option>
		</select></td><td  width="40%">
		
		<select id="liveTY" name="liveTY" style="width: 80pt;" onchange="monthLoad(this.value,'TYL');">
			<option value="">--Select--</option>			
			<option value="2012-2013">2012-13</option>
			<option value="2013-2014">2013-14</option>
			<option value="2014-2015">2014-15</option>
			<option value="2015-2016">2015-16</option>
			<option value="2016-2017">2016-17</option>
			<option value="2017-2018">2017-18</option>
		</select></td></tr></table></div>
		</td>
		</tr>
	  <tr class="table">
		<td >
		<div align="left"  id="motnDiv" style="display: none;">Month</div>
		</td>
		<td ><div align="left" id="divmotnDiv" style="display: none;">
		<div align="left"><table width="80%"><tr><td  width="40%">
			<select id="fromMonth" name="fromMonth" style="width: 80pt;" >			
		</select></td><td width="40%">
			<select id="toMonth" name="toMonth" style="width: 80pt;">			
		</select></td></tr>	</table></div>	
		</div></td>

	</tr>

        <tr align="left" >
          <td class="table" >
            <div align="left" >Selection of Report Type</div>
          </td>
          <td >
            <div align="left" >                  

                            <input type=radio id="reptype" name="reptype" value="Regular" checked onclick="ChooseReptype(this.value)"> Regular
                            <div id="dispSJV" name="dispSJV" style="display:none">
                            <input type=radio id="reptype" name="reptype" value="InclusiveSJV" onclick="ChooseReptype(this.value)"> Inclusive SJV
                           </div>
            </div>
            </td>
            
       
         </tr>
         
         <tr align="left" >
          <td class="table" >
            <div align="left" > Report Format</div>
          </td>
            <td >
            <div align="left" >                  

                            <input type=radio id="repformat" name="repformat" value="PDF" checked > PDF
                            <input type=radio id="repformat" name="repformat" value="EXCEL"> EXCEL         
            </div>
            </td>
         </tr>
         
         
         <tr align="left">
          <td class="table">
            <div align="left">Report Type</div>
          </td>
          <td>
            <div align="left">               
              <select name="txtSection" id="txtSection" tabindex="4" onchange="loadGroup(this.value);">
                <option value="">-- Select Report Type --</option>
                <%
                String qry="select STATEMENT_NO,STATEMENT_NAME from FAS_MIS_ANNEXURE_MST order by order_no";

                try{
                	   ps=con.prepareStatement(qry);
                	   rs=ps.executeQuery();
                	   while(rs.next()){
                		   out.println("<option value="+rs.getString("STATEMENT_NO")+">"+rs.getString("STATEMENT_NAME")+"</option>");
                	   }
                }catch(Exception e)
                {
                	System.out.println("Eception >>> "+e);
                }
                %>
              </select>
            </div>
          </td>
        </tr>
         <tr align="left">
         
            <td class="table">
              <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div id="dispsupno2" name="dispsupno2" style="display:none">
                <input type="text" name="txtsupplement_no" id="txtsupplement_no" size=2 value="">     
              </div>
              
            </td>
          </tr>
        <tr height="50%"><td class="table" colspan="2"></td></tr>
        <tr class="tdH">
          <td colspan="4">
            <div align="center">
              <input type="submit" value="Submit"></input>               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
