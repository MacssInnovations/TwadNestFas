<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >

<title>GPF_Debit_Schedule</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
      <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
      <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
      
      <script language="javascript" type="text/javascript" src="../scripts/GPF_Debit_Schedule.js"></script>
      
       <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script> 

</head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<%String s=request.getContextPath(); %>
<body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS')">
 <form name="GPF_Debit" id ="GPF_Debit ">
 
  <%
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;
            
             Connection connection=null;
        
             ResultSet results=null,rs2=null;
             ResultSet results1=null;
             ResultSet results2=null;
             try
             {
                   ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                   String ConnectionString="";
                   String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                   String strdsn=rs1.getString("Config.DSN");
                   String strhostname=rs1.getString("Config.HOST_NAME");
                   String strportno=rs1.getString("Config.PORT_NUMBER");
                   String strsid=rs1.getString("Config.SID");
                   String strdbusername=rs1.getString("Config.USER_NAME");
                   String strdbpassword=rs1.getString("Config.PASSWORD");
                   //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                   ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                   Class.forName(strDriver.trim());
                   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
		   catch(Exception e)
             {
                       System.out.println("Exception in opening connection :"+e);
             } 
   %>
 
 
 <table cellspacing="2" cellpadding="3" width="100%" >
 
 <tr class="tdH">
 <td colspan="2">
 <div align="center">
 <strong>GPF_Debit_Schedule</strong>
 </div>
 </td>
 </tr>
  </table>
  
  <div align="center">
  <table cellspacing="1" cellpadding="2" border="1" width="100%">
  <tr class="table">
  <td>
  <div align="left">
  Accounting Unit Id
  <font color="#ff2121">*</font>
  </div>
  </td>
  
  <td>
  <div align="left">
 <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                            tabindex="1" onchange="common_LoadOffice(this.value);"></select>
  </div>
  </td>
  </tr>
  
  <tr class="table">
  <td>
  <div align="left">
  Accounting for Office Id<font color="#ff2121">*</font>
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
  CashBook Year and Month<font color="#ff2121">*</font>
  </div>
  </td>
  
  <td>
  <input type="text" name="cbyear" id="cbyear" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)"/>
  <select name="cbmonth" id="cbmonth">
  		  <option value="">Select Month</option>
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
  </tr>
  
   <tr class="table">
                <td width="40%">
                  <div align="left">
                       Bill Major Type <font color="#ff2121">*</font>
                  </div>
                  
                </td>
                <td width="60%">
                  <div align="left">
                    <select  name="majorType" id="majorType" tabindex="6"
                            onchange="callminor()">
                      <option value="">--Select Major Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE=2 order by BILL_MAJOR_TYPE_DESC");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("BILL_MAJOR_TYPE_CODE")+">"+rs.getString("BILL_MAJOR_TYPE_DESC")+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in Reason combo..."+e);
                        }
                        finally
                        {
                        rs.close();
                        ps.close();
                        }   
                      %>
                    </select>
                  </div>
                </td>
          </tr>
          <tr class="table">
            <td width="40%">
              <div align="left">
                   Bill Minor Type <font color="#ff2121">*</font>
              </div>
            </td>
            <td width="60%">
                <table align="left">
                 <tr align="left">
                     <td>
                         <div align="left">
                                <select size="1" name="minorType" id="minorType" onchange="callsub(this.value);">                                            
                                  <option value="">--Select Minor Type--</option>
                                </select>
                         </div>
                      </td>
                 </tr>
               </table>
            </td>
          </tr>
          
          
          
          <tr>
                                    <td class="table" width="40%" align="left">Bill Sub Type<font color="#ff2121">*</font></td>
                                    <td class="table" align="left"> 
                                            <select size="1" name="billsubtype" id="billsubtype"> 
                                                <option value='0'>--Select Sub Type--</option>
                                            </select>
                                    </td>               
          </tr>
          
          
          <tr class="table">
				<td width="40%">
				<div align="left">Emp_Name and Emp_Code<font color="#ff2121">*</font></div>		</td>
				<td width="60%">
				<table align="left">
					<tr align="left">
						<td>
						<div align="left"><select size="1" name="cmbMas_SL_Code"
							id="cmbMas_SL_Code"></select></div>				</td>
						<td>
						<div align="left" id="offlist_div_master" style="display: none">
		
						<img src="../../../../../images/c-lovi.gif" width="20" height="20"
							alt="OfficeList" onClick="jobpopup_master();"></img> <input
							type="text" name="txtOfficeID_mas" id="txtOfficeID_mas"
							maxlength="4" size="5" onBlur="mas_office(this.value);" /></div>
						<div align="left" id="emplist_div_master"><img
							src="../../../../../images/c-lovi.gif" width="20" height="20"
							alt="empList" onClick="employee_popup_master();"></img> 
		                <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6" size="6"   onchange="callemp('<%=s %>');"  onchange="getOffice('<%=s %>');"/></div>
						<input type="hidden" name="cmbSL_type" id="cmbSL_type" /> 
		                                <input type="hidden" name="cmbSL_Code" id="cmbSL_Code" /></td>
					</tr>
				</table>		</td>
			</tr>
  
  <tr class="table">
  <td>
  <div align="left">
  Debit Schedule Type:
  </div>
  </td>
  
  <td>
  <select name="debit_type" id="debit_type">
  <option value="">select type</option>
  <option value="R">GPF Regular</option>
  <option value="IM-R">GPF Impounded - Regular</option>
  <option value="IM">GPF - Impounded-2003</option>
  
  
  </select>
  </td>
  
  </tr>
  
  
  <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="View_HOA" id="View_HOA"
                                 value="View HOA selected" /></td>
                          <td>
                          <input type="button" name="View_Data" id="View_Data" value="View Data to be transferred" /></td>
                          
                          <td><input type="button" name="SUBMIT" id="SUBMIT" value="SUBMIT" onclick="SubmitFun();" /></td>
                          <td><input type="button" name="Cancel" id="Cancel" value="Cancel" onclick="clrForm();"/></td>
                          <td><input type="button" name="exit" id="exit" value="Exit" onclick="self.close();"/></td>
                        </tr>
                        </table>
                        </div>
                        </td>
                      </tr>
  
  
  </table>
  </div>
  
 </form>


</body>
</html>