<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
      <title>Trial Balance</title>
      <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
            media="screen"/>
      <link href="../../../../../css/Sample3.css" rel="stylesheet"
            media="screen"/>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript"
              src="../../../../../../org/Library/scripts/checkDate.js"></script>
      <script language="javascript" type="text/javascript"
              src="../scripts/FreezeTB.js"></script>
      <script type="text/javascript"
              src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
     
        <script language="javascript" type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/MIS/scripts/Fas_ConsolB_Details.js"></script>
             
       <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
              
      <script type="text/javascript" language="javascript">
      function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmTrialBalance.txtCB_Year.value=year;
        document.frmTrialBalance.txtCB_Month.value=month;
        
         }
      //New function added Kanaga on 19/8/2019
      
      function getTransport()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}


function LoadAccountingUnitID_new(COMMAND)
{
	
        command_for_office = COMMAND;
        var url="../../../../../Load_Accounting_Unit_ID.kv?COMMAND="+COMMAND;
    
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          handle_loadAccountingUnitID(req);
        }        
        req.send(null);
    
}
function handle_loadAccountingUnitID(req)
{
   
    if(req.readyState==4)
    {
   
     if(req.status==200)
     {
   
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
      
        if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");         
           //     cmbAcc_UnitCode.length=0;
          
            var option_count=baseresponse.getElementsByTagName("option");       
         //   alert(option_count.length);
            var root = null;
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                
                option.text=accounting_unit_name+"("+accounting_unit_id+")";
                option.value=accounting_unit_id;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
                       
           // alert("command_for_office:::"+command_for_office);
            
            /** Load Accounting Office ID */ 
          
            if ( (command_for_office == "ONLY_UNITS") || (command_for_office=="LIST_ALL_UNITS_ONLY") || (command_for_office=="FOR_LIST_0" ) )
            {
            
            }
            else
            {
            	
            	//loadOffice_REC(); 
            	common_LoadOffice();
            }         
            
            
        }
        else
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
}
      
      
      
      
      
      
      
      
      
      
      
      
      
      
         function RPType_Disp()
                 {
                 var type=document.frmTrialBalance.headwise.value;
                // alert(type+"*********#########********");
                 
                 if(type=="unitwise_Abstract")
                 {
                 var Month=document.getElementById("txtCB_Month").value;
				 if(Month==3)
				 {
				 document.getElementById("rptype").style.display="block";
				 document.getElementById("Regular").style.display="block";
				 document.getElementById("Supp").style.display="block";
				 }
				 else
				 {
				 document.getElementById("rptype").style.display="none";
				 document.getElementById("Regular").style.display="none";
				 document.getElementById("Supp").style.display="none";
				 }                 
                 }
                 else                 
                 {
                 document.getElementById("rptype").style.display="none";
				 document.getElementById("Regular").style.display="none";
				 document.getElementById("Supp").style.display="none";
                 }
                 
                 }
         function ChooseReptype(id)
     {
        
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");
    	if(id=="Supplement")
         {
                  
                 dispsupnochosen1.style.display="block";
                 dispsupnochosen2.style.display="block";
                 alert("Enter the Supplement Number");
		 }
         else
         {
                 dispsupnochosen1.style.display="none";
                 dispsupnochosen2.style.display="none";
                
         }
     }
     
     function checkNull()
     {
    
    var type=document.frmTrialBalance.headwise.value;   
    //alert(type+"******#####******");
    var rptsel=document.getElementById("rptsel").value;
     if(type=="unitwise_Abstract")
     {
    // alert(type+"******##inside##******");
     
     var Month=document.getElementById("txtCB_Month").value;
    // alert("Month>>>>>"+Month+ "***********" + "rptsel>>>>>>"+rptsel)
     if(Month==3 && document.frmTrialBalance.rptsel[1].checked==true)
	 {
   //  alert(type+"******## March & Sup ##******");
     if(document.getElementById("supno").value==0 || document.getElementById("supno").value=="")
        {
         alert("Please Enter the Supplement Number");
         return false;
        }
     }   
       		 
	 }
        
     
     }
     
      </script>
      <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
   </head>
   <body class="table" onload="loadyear_month();LoadAccountingUnitID_new('LIST_ALL_UNITS');">
   <form id="frmTrialBalance"    name="frmTrialBalance"  method="POST"   action="../../../../../Fas_ConsolidateTBAcc_Details" onsubmit="return checkNull();">
         <%
			response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
			response.setHeader("Pragma","no-cache"); //HTTP 1.0
			response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
			%>
         <% 
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
        System.out.println("user id::"+empProfile.getEmployeeId());
        int empid=empProfile.getEmployeeId();
        int  oid=0;
        String oname="";
        String FAS_SU="";
   
        if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
           FAS_SU="YES";
        else
           FAS_SU="NO";   
   %>
         <table cellspacing="2" cellpadding="3" width="100%">
            <tr class="tdH">
               <td colspan="2">
                  <div align="center">
                     <strong>Trial Balance</strong>
                  </div>
               </td>
            </tr>
         </table>
         <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table">
                  <td>
                     <div align="left">
                        Accounting Unit Code 
                        <font color="#ff2121">*</font>
                     </div>
                  </td>
                 <td>
                     <div align="left">
                        <select size="1" name="cmbAcc_UnitCode" onchange="checkVerification();"
                                id="cmbAcc_UnitCode" tabindex="1">
                                <option value="000">All</option></select>
                     </div>
                  </td>
               </tr>
               
               
               <tr align="left">
                  <td class="table">
                     <div align="left">Type Of report</div>
                  </td>
                  <td>
                     <div align="left"></div>
                    	
	<input type="radio" id="headwise" name="headwise" value="headwise" checked="checked" onchange="RPType_Disp();">HeadWise
	<input type="radio" id="headwise" name="headwise" value="unitwise" onchange="RPType_Disp();">UnitWise
	<input type="radio" id="headwise" name="headwise" value="unitwise_Abstract" onchange="RPType_Disp();">UnitWise Abstract 
                     
                     </td></tr>
               
               
               
               
               
               
               <tr align="left">
                  <td class="table">
                     <div align="left">Cash Book Year &amp; Month</div>
                  </td>
                  <td>
                     <div align="left">
                        <input type="text" name="txtCB_Year" id="txtCB_Year"
                               tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)"></input>
                         
                        <select name="txtCB_Month" id="txtCB_Month" onchange="RPType_Disp();checkVerification();"
                                tabindex="4">
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
                  <tr>
              <td>
              <div align="left" id="rptype" style="display:none">Selection of Report Type</div>
              </td>
              <td>
              
              <div align="left" id="Regular" style="display:none">
              <input type="radio" id="rptsel" name="rptsel" value="Regular" onclick="ChooseReptype(this.value)" checked>Regular</div>
              <div align="left" id="Supp" style="display:none">
              <input type="radio" id="rptsel" name="rptsel" value="Supplement" onclick="ChooseReptype(this.value)">Supplement</div>
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
            <input type="text" name="supno" id="supno" value=0 size=2 >     
            
          </div>
          
        </td>
      </tr>   

                <tr align="left">
                  <td class="table">
                     <div align="left">Report Option</div>
                  </td>
                  <td>
                     <div align="left"></div>
                    	
	<input type="radio" id="txtoption" name="txtoption" value="PDF" checked="checked">PDF
	<input type="radio" id="txtoption" name="txtoption" value="HTML">HTML
	<input type="radio" id="txtoption" name="txtoption" value="EXCEL">EXCEL
                     
                     </td></tr>
   <!--  <tr align="left" id="areaId" style="display:none">
    <td>
    </td>
      <td class="table">
   <textarea rows="10" cols="100" style="color:red" >
 Since the introduction of Supplement TDA/TCA certain errors have been noticed for few units.
 Because of this error, TDA/TCA Transaction total and TB total differs for TDA/TCA heads.
 Users are requested to verify the same for March-2012 Regular,
 March-2012 Supplement and April-2012 using the screen provided.
 The screen for verification is available in MONTH END OPERATIONS REGULAR ->TDA_TCA_APRIL_VEIRFY.
 Using this screen user has to check the figures displayed and verify for all the three TB figures.
 Only if the difference is zero for all the TDA/TCA Heads for March-2012 Regular,
 March-2012 Supplement and April-2012 users will be able to freeze April-2012 Trial Balance.
 Where ever there is a difference users are requested to enter the details 
 in the column provided so as to take corrective action.
    </textarea>
     </td>
     </tr>   -->
            </table>
         </div>
         <div align="left" style="display:none">Trial Balance Status</div>
         <div align="left" style="display:none">
            <input type="radio" id="radTB_status" name="radTB_status" value="Y"
                   checked="checked"></input>
             Yes 
            <input type="radio" id="radTB_status" name="radTB_status" value="N"></input>
             No
         </div>
         <table align="center" cellspacing="3" cellpadding="2" border="1"
                width="100%">
            <tr class="tdH">
               <td>
                  <div align="center">
                     <input type="submit" value="Submit"></input>
                      
                     <input type="button" id="cmdcancel" name="cancel"
                            value="EXIT" onclick="closeWindow()"></input>
                  </div>
               </td>
            </tr>
         </table>
      </form></body>
</html>