<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>GPFReport</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
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
             
            document.miscRep.txtCB_Year.value=year;
            document.miscRep.txtCB_Month.value=month;
        
        }
        function testSup()
        {
        	var sup=document.miscRep.txtsupplement_no.value;
        	if(sup==0)
        		{
        		
        		document.miscRep.txtsupplement_no.value="";
        		alert("Enter Supplement No Greater than Zero");
        		return false;
        		}
        }
        function pop()
        {
        
                var today= new Date();          
                var month=today.getMonth();
                month=month+1;        
                if(document.getElementById("txtCB_Month"))
                {
                    var obj =document.getElementById("txtCB_Month");
                    var mon_names = new Array("January","February","march","April","May","June","July","Augest","September","October","November","December");
                    if(month >= 1 && month <= 12)
                    {
                        if(month > 1) 
                            var disp = month-1;
                        else	
                            var disp = 12;
                    } 		
                    
                    var j=0;   
                    for ( i=4; i<=disp;i++)
                    {	
                    
                        obj.options[j] = new Option(mon_names[i-1],i);				
                        j++;
                        //obj.options[disp]="";
                                     
                    }
                
                
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
      function  checknullNew_test()
      {
    	  var t=document.getElementById("txtSection").value;
    	  if(t=="")
    		  {
    		  alert("Select Report Type");
    		  return false;
    		  }
    	  if(document.miscRep.reptype[1].checked==true)
    	  {
	    		  if(document.getElementById("txtsupplement_no").value=="")
	    			  {
	    			  alert("Enter Supplement No");
	        		  return false;
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
                function ChooseReptype(id)
                {
                   
                    var dispsupnochosen1=document.getElementById("dispsupno1");
                    var dispsupnochosen2=document.getElementById("dispsupno2");
                    if(id=="Regular")
                    {
                             
                            dispsupnochosen1.style.display="none";
                            dispsupnochosen2.style.display="none";
                    }
                    else
                    {
                           
                            dispsupnochosen1.style.display="block";
                            dispsupnochosen2.style.display="block";
                            alert("Enter the Supplement Number");
                    }
                }
                
    </script>
  </head>
  <body class="table" onload="loadyear_month();callServer_LoadSection();setTimeout('callSJVload()',100);">
  <form action="../../../../../../GPFReport?Command=SummarywithSJV"
                                                       name="miscRep"
                                                       method="post"
                                                       onsubmit="return checknullNew_test()">
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>Detail Report</center>
          </td>
        </tr>
        
        
        <tr align="left">
          <td class="table">
            <div align="left">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left">
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
         <!-- added additional options such as regular or inclusive of SJV from 20/Feb/2012 -->
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
              </select>
            </div>
          </td>
        </tr>
         <tr align="left">
          <td class="table">
            <div align="left">Name of The Group</div>
            </td>
              <td>
            <div align="left">               
              <select name="txtGrp" id="txtGrp" tabindex="4" onchange="load_Head(this.value);">
                <option value="">-- Select Group Type --</option>
               
              </select>
            </div>
        </td></tr>
        
             <tr align="left">
          <td class="table">
            <div align="left" id="head_vis">Name of The Head</div>
            </td>
              <td>
            <div align="left" id="head_vis1" >               
              <select name="txtHead" id="txtHead" tabindex="4">
                <option value="">-- Select Head --</option>
               
              </select>
            </div>
        </td></tr>
        <tr align="left">
         
            <td class="table">
              <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div id="dispsupno2" name="dispsupno2" style="display:none">
                <input type="text" name="txtsupplement_no" id="txtsupplement_no" size=2 onblur="testSup();">     
              </div>
              
            </td>
          </tr>
        
        
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