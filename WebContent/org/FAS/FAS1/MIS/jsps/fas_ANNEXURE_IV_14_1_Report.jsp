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
    <title>Annexure IV (14) Report</title>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
    <script language="javascript" type="text/javascript"
            src="../scripts/GPFReport_Abstract.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         
        document.AnnexRep.txtCB_Year.value=year;
        document.AnnexRep.txtCB_Month.value=month;
        
         }
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
		
   function Chk_Date(val){
	   document.getElementById("hid_val").value=val;
	document.getElementById("MnthYearTit").style.display="none";
	document.getElementById("MnthYearTit1").style.display="block";
	document.getElementById("MnthYear").style.display="none";
	document.getElementById("MnthYear1").style.display="block";
   }
   function Chk_Month(val){
	   document.getElementById("hid_val").value=val;
	   if( document.getElementById("Type_btn").style.checked==true)
		   {
		   document.getElementById("Type_btn1").style.checked=false;
		   }
	   document.getElementById("MnthYearTit1").style.display="none";
		document.getElementById("MnthYearTit").style.display="block";
		document.getElementById("MnthYear1").style.display="none";
		document.getElementById("MnthYear").style.display="block"; 
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
	
	if(txtFromCB_Year.split("/")[2]>val.split("/")[2])
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
  <form action="../../../../../Annexure_Report_IV_14_1?Command=REPORT"
                                                       name="AnnexRep" id="AnnexRep"
                                                       method="post">
                                                      
 <input type="hidden" id="hid_val" name="hid_val" value="MonthWise">                                                     
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <th class="tdH" colspan="2">
            <center>Annexure IV (14-1) Report</center>
          </th>
        </tr></table>
        <br>
         <table cellspacing="2" cellpadding="3" border="0" width="100%">
            <tr align="left">
          <th class="tdH" colspan="2">
           General Details
          </th>
        </tr>
           <tr align="left">
          <td class="table">
            <div align="left">Cash Book Year &amp; Month Type</div>
            </td> <td class="table">
             <input type="radio" id="Type_btn" name="MonYear1" value="MonthWise" checked="checked" onclick="Chk_Month(this.value)"/>MonthWise
            <input type="radio" id="Type_btn1" name="MonYear1" value="DateWise" onclick="Chk_Date(this.value)"/>DateWise
           </td>
            </tr>
         
        <tr align="left">
          <td class="table">
            <div align="left" id="MnthYearTit">Cash Book Year &amp; Month</div>
          </td>
          <td>
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
         <tr align="left">
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
        </tr>
         <!-- added additional options such as regular or inclusive of SJV from 19 Jan 2012 -->
        <tr align="left">
          <td class="table">
            <div align="left">Selection of Report Type</div>
          </td>
          <td>
            <div align="left">                  
                            
                            <input type=radio id="reptype" name="reptype" value="Regular" checked onclick="ChooseReptype(this.value)"> Regular
                            <div id="dispSJV" name="dispSJV" style="display:none">
                            <input type=radio id="reptype" name="reptype" value="InclusiveSJV" onclick="ChooseReptype(this.value)"> Inclusive SJV
                           </div>
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
                String qry="select STATEMENT_NO,STATEMENT_NAME from FAS_MIS_ANNEXURE_MST where STATEMENT_NO in(2,3,12,13,14)";
             
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
          <td colspan="2">
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