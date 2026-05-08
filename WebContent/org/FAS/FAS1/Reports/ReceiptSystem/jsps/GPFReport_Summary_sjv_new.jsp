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
    <title>Section Report</title>
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
         
        document.miscRep.txtCB_Year.value=year
        document.miscRep.txtCB_Month.value=month;
        
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
  <body class="table" onload="loadyear_month();callServer_LoadSection()"><form action="../../../../../../GPFReport?Command=Sjv_Summary"
                                                       name="miscRep"
                                                       method="post"
                                                       onsubmit="return checknull()">
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td class="tdH" colspan="2">
            <center>Section Detail Report</center>
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
               
              <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
                
                <option value="3">March</option>
                
               
              </select>
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
            <div align="left" id="head_vis" >Name of The Head</div>
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
              <div align="left">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div>
                <input type="text" name="txtsupplement_no" id="txtsupplement_no" size=2 >     
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