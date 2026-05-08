<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver"%><html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Expenditure Summary</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
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
                
        /** Load From Cash Book Month and From Cash Book Year during Form Load */
        document.finProgressReprotSummary_jrnl.txtCB_Year.value=year;
        document.finProgressReprotSummary_jrnl.txtCB_Month.value=month;
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
       <%!
       	Connection connection;
      	PreparedStatement preparedStatement;
      	ResultSet resultSet;
      %>
      <%connection  = new LoadDriver().getConnection(); %>
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">
  <form name="finProgressReprotSummary_jrnl" id="finProgressReprotSummary_jrnl" method="POST" action="../../../../../summary_ind_report_jrnl.pdf?command=summary">
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>Expenditure Summary - Journal</strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">             
                
                <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);loadTransferUnit()">        
                         </select>
                      </div>
                    </td>
              </tr>


              <tr class="table">
                    <td>
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
                        </select>
                      </div>
                    </td>
              </tr>
                
                <tr align="left">
           			<td class="table">
              			<div align="left">
                 			Cash Book Year &amp; Month <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td>        				       
		          <div id="more">
          		
          		<input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          		<select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
               <td>
                 <div style="display:none">
              		Displaying Order <font color="#ff2121"> * </font>
                 </div>
               </td>
               <td>
                <div style="display:none">
                    <input type="radio" name="displayingOrder" checked id="displayingOrder" value="UW" ></input>Office Wise &nbsp;&nbsp;
                    </div>
                 
               </td>               
              </tr>
        <tr>
           <td align="left">
               Report Option:
           </td>
           <td colspan="3" align="left">
              <input type=radio name=fileType id=txtoption value="PDF" checked>PDF
              <input type=radio name=fileType id=txtoption value="EXCEL">Excel
              <input type=radio name=fileType id=txtoption value="HTML">HTML
           </td>
                        
      </tr>
     
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type="submit"  value="Submit" id="sum" name="sum">
          <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="closeWindow()">
       </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>