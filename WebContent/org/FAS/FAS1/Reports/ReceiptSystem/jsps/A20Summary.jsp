<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>A20 Summary Of Assets </title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/A20Summary.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript" 
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
       
    <script type="text/javascript" language="javascript">
    function loadyear_month()
    {
  
    var today= new Date(); 
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var year=today.getYear();
    if(year < 1900) year += 1900;
  
   document.frmA20Summary.from_txtCB_Year.value=year;
   document.frmA20Summary.from_txtCB_Month.value=month;
   
   document.frmA20Summary.to_txtCB_Year.value=year;
   document.frmA20Summary.to_txtCB_Month.value=month;
    }
    
    
    function MajorCode1(id){
        // var Regions=document.getElementById("Regions");
         var major=document.getElementById("Majorwise");
      
        if(id=="MA")
        {
        	major.style.display="block";
        }
        if(id=="ALL")
        {
        	major.style.display="none";
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
  <body onload="LoadAccountingUnitID('FOR_LIST_1'),loadyear_month();">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">A20 Summary of Assets </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmA20Summary" id="frmA20Summary" method="post" action="../../../../../../A20Summary" onsubmit="return nullCheck()">
                  
  
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                    
                      </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
  
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
 
                    </select>
                  </div>
                </td>
              </tr>
              
           <tr class="table">
              <td>
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" >
                    <option value="">--Select Year--</option>
                  <option value="2010-11">2010-11</option>
                  <option value="2011-12">2011-12</option>
                  <option value="2012-13">2012-13</option>
                    </select>
              </td>
              </tr>
                   <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
          <td>
          From &nbsp;&nbsp;
         
          <input type="text" name="from_txtCB_Year" id="from_txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)" onblur="return checkFromYear();" >
         
          <select name="from_txtCB_Month"  id="from_txtCB_Month" tabindex="4" onchange="return checkfromyearmonth()">          
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
         
         
          To &nbsp;&nbsp;  
           
         
          <input type="text" name="to_txtCB_Year" id="to_txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)" onblur="return checkToYear();">
         
          <select name="to_txtCB_Month"  id="to_txtCB_Month" tabindex="4"  onchange="return checktoyearmonth();">          
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
         
           
          </td>
        </tr>
         <!--------------------------major asset-->
         <tr class="table">
               <td>
                 <div align="left">
              		Displaying Order <font color="#ff2121"> * </font>
                 </div>
               </td>
               <td>
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="ALL" onclick="MajorCode1(this.value)" checked ></input>All    &nbsp;&nbsp; 
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="MA" onclick="MajorCode1(this.value),callServer('loadMajor');" ></input>Major Wise      
                  <br><br><div align="left" id="Majorwise" name="Majorwise" style="display:none">
                         	
                     <select size="1" name="cmbmajorasset" id="cmbmajorasset" tabindex="3" >
                     
                    </select> 
                    
                </div>
               </td>               
              </tr><!-- 
         
         
                    <tr class="table">
                <td>
                  <div align="left">
                     Major Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbmajorasset" id="cmbmajorasset" tabindex="3" ><option value=0>-- Select Major Asset Code --</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
         --></table>
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type=submit value=Submit >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()">
      </div>
      </td>
      </tr>
      
      </table>     
    </form>
    </body>
</html>