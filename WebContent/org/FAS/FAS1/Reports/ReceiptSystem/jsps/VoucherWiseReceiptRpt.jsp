<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Section Report</title>
    
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
          
    <script language="javascript" type="text/javascript"
            src="../scripts/GPFReport_VoucherWiseCompSec.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
          
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>          
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
  
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         
        document.GPFVocherwise_receipt.txtCB_Year.value=year;
       // document.GPFVocherwise.txtCB_Month.value=month;                
        
         }
     function callSJVload()
     {
  	   
  	   var month_chosen=document.getElementById("txtCB_Month").value;
  	 
  	   var dispsjv=document.getElementById("dispSJV");
  	 var regyr=document.getElementById("regyr");
  	var regdate=document.getElementById("regdate");
  	 
  	   if(month_chosen==3)
  		   {

  		 document.getElementById("labeliddd").style.display="block";
  		document.getElementById("reguDis").style.display="block";
  		   		dispsjv.style.display="block";
  		   		//regyr.style.display="block";
  		  		//regdate.style.display="block";
  		   }
  	   else
  		   {
  		 document.getElementById("labeliddd").style.display="none";
   		document.getElementById("reguDis").style.display="none";
  				dispsjv.style.display="none";
  				  document.getElementById("dispsupno1").style.display="none";
  		         document.getElementById("dispsupno2").style.display="none";
  				//regyr.style.display="block";
  		  	//	regdate.style.display="block";
  		   }
  	
     }
     function ChooseReptype(id)
     {
        
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");
    	 var regyr=document.getElementById("regyr");
    	  	var regdate=document.getElementById("regdate");
         if(id=="Regular")
         {
                  
                 dispsupnochosen1.style.display="none";
                 dispsupnochosen2.style.display="none";
				//fromdate
             //    regyr.style.display="block";
   		  	//	regdate.style.display="block";
         }
         else
         {

        	// regyr.style.display="none";
		  //	regdate.style.display="none";
                
                 dispsupnochosen1.style.display="block";
                 dispsupnochosen2.style.display="block";
                 alert("Enter the Supplement Number");
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
  <body class="table" onload="loadyear_month();">
  <form action="" 
        id="GPFVocherwise_receipt" 
        name="GPFVocherwise_receipt"
        method="post"
        onsubmit="return checknull()">
        
        
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>Section Report-Voucher Wise (Receipt Only) </center>
          </td>
        </tr>
     
      <tr class="table">
      <td>
                  <div align="left">
                    Account Head Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="8"
                           onkeypress="return numbersonly(event)"
                            onchange="sixdigit('this.value');" 
                            onblur="doFunction('accdesc','null');"  size="9"/>
                    <img src="../../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>
                </td>
        </tr>
         <tr align="left">
          <td class="table">
            <div align="left">Sorting Order</div>
          </td>
          <td>
            <div align="left">               
              <select name="txtSortingOrder" id="txtSortingOrder" tabindex="4">
                <option value="33">Unit wise,Date wise</option>
                 <option value="77">Unit wise Abstract </option> 
              </select>
            </div>            
          </td>
        </tr> 
        
        <tr align="left">
          <td class="table">
            <div align="left">Type</div>
          </td>
          <td>
            <div align="left">               
             <input type=radio id="monthType" name="monthType" value="month" checked onclick="monthPeriod1(this.value);"> MonthWise
             <input type=radio id="monthType" name="monthType"  value="year" onclick="monthPeriod1(this.value);"/> PeriodWise
            </div>            
          </td>
        </tr> 
        
         <tr align="left">
          <td class="table">
            <div align="left" id="monthdivid" style="display:block">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left" id="monthdividtext" style="display:block">
              <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5"
                     onkeypress="return numbersonly(event)"></input>
               
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4" >
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
              
        <!--        <input type="button" id="go" name="go" value="Go" onclick="FormSubmit_new();"/> -->
              
              
            </div>
          </td>
        </tr>
                 <!-- added additional options such as regular or inclusive of SJV from 20/Feb/2012 -->
        <tr align="left">
          <td class="table">
            <div align="left" id="labeliddd" style="display:none">Selection of Report Type</div>
          </td>
          <td>
            <div align="left" id="reguDis" style="display:none">                
                            
                            <input type=radio id="reptype" name="reptype" value="Regular" onclick="ChooseReptype(this.value)"> Regular
                            </div>
                            <div id="dispSJV" name="dispSJV" style="display:none">
                            <input type=radio id="reptype" name="reptype" value="InclusiveSJV" onclick="ChooseReptype(this.value)"> Inclusive SJV
                           
            </div>
            </td>
         </tr>
        <tr align="left">
          <td class="table">
            <div align="left" id="regyr" style="display:none">Cash Book Year &amp; Month</div>
          </td>
          <td>
            <div align="left" id="regdate" style="display:none">
                  From 
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                 
                       
                  To 
                  
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                                
                
           <!--      <input type="button" id="go" name="go" value="Go" onclick="FormSubmit('morethanone')"/>
                 --> 
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
            <input type="text" name="supno" id="supno" value=0 size=2 >     
          </div>
          
        </td>
      </tr>   
        
                           
         <tr align="left">
     
      </tr>     
        <tr class="tdH">
          <td colspan="2">
            <div align="center" id="monthGo" style="display:block">
             <input type="button" id="go" name="go" value="Go"
                     onclick="FormSubmit_receipt()"></input>
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
            <div align="center" id="dateGo" style="display:none">
             <input type="button" id="go" name="go" value="submit"
                     onclick="FormSubmit_receipt1('morethanone')"></input>
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
