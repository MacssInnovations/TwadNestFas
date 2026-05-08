<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver" %>
	<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rent Master</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link rel=StyleSheet href="css/Sample3.css" type="text/css">
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />

 <script type="text/javascript"
            src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>

 <script type="text/javascript" src="../../../../Library/scripts/comJS.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
	
	<script type="text/javascript" src="../scripts/Rent_Master.js" > </script>
	
	<script type="text/javascript" language="javascript">
    	function loadDate()
        { 
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
                 var monthArray =new Array("January", "February", "March", 
                           "April", "May", "June", "July", "August",
                           "September", "October", "November", "December");
                 var finyear="";
                 if(month>=4)
                	 {
                	 	finyear=year+"-"+(year+1);
                	 }
                 else
                	 {
                	 	finyear=year+"-"+(year-1);
                	 }
                document.RentMaster.txtRentValueasonDate.value=day+"/"+month+"/"+year;
        }
    	</script>
</head>



 <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>

<%
	String s = request.getContextPath();
%>
<%
	System.out.println("printing the patttttttttthhhhhh:::"+s);
%>

<body
	onload="loadDate();LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad('<%=s%>');">
	<%
  Connection connection=null;
  PreparedStatement statement=null,Pstatement=null,Pstmt1=null,Pstmt2=null;;
  session = request.getSession();
  ResultSet rset=null,rset1=null,rset2=null,results=null;
  HttpSession user_session=null;
  int emp_code=0;
  int offid=0;
  String offname="";  
    int count=0;
	        try
	            {
	             	LoadDriver load = new LoadDriver();
	             	connection = load.getConnection();	             	
	              try
	              {
	        user_session=request.getSession(false);

			//empid=(String)user_session.getAttribute("empid");
			UserProfile up=null;
 			up=(UserProfile)session.getAttribute("UserProfile"); 			
			emp_code=up.getEmployeeId();
	              
	              Pstmt2=connection.prepareStatement("select p.OFFICE_ID,v.office_name from HRM_EMP_CURRENT_POSTING p,com_mst_offices  v where EMPLOYEE_ID=? and v.office_id = p.office_id");
	              connection.clearWarnings();
	            
	              }
	              catch(SQLException e1)
	              {
					 System.out.println("Exception in creating statement:"+e1);
	              }          
	           }
	          catch(Exception e1)
	          {
	             System.out.println("Exception in openeing connection:"+e1);
	          }
	          
	          
	            try
 			   {
            Pstmt2.setInt(1,emp_code);
            results=Pstmt2.executeQuery();
                 if(results.next())
                 {
                    offid=results.getInt("OFFICE_ID");
                    offname=results.getString("office_name");
                
                 }
            results.close();
            Pstmt2.close();
           }
        catch(SQLException e)
    {
        System.out.println(e);
    }
  %>
<form name="RentMaster" method="post" action="RentMasterServlet"
	id="RentMaster">

<table width="100%" border="1" align="center">
	<tr>
		<td colspan="2" class="tdH">
		<div align="center"><strong>Rent Master</strong></div>		</td>
	</tr>
	<tr class="table">
		<td width="348"><div align="left">Accounting Unit</div></td>
		<td width="383"> <div align="left">
		  <select size="1"
			name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1"  onChange="common_LoadOffice(this.value);"  >
	      </select>
	  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Accounting For Office</div></td>
		<td><div align="left">
		  <select size="1" name="cmbOffice_code" id="cmbOffice_code" 
			tabindex="2" >
	      </select>
		  </div></td>
	</tr>
	 <tr class="table" id="tr4">
                <td>
                  <div align="left">
                  Office Id
                   <!--   <font color="#ff2121">*</font>-->
                  </div>
                </td>
                <td>
                  <div align="left">
                        <input type="text" name="officeId"  id="officeId" value="<%=offid%>" onkeypress="return numbersonly1(event,this)" maxlength="20" size="7" onblur="callServer('Check','null');loadownerName();" />
                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup('txtOffice_Id','txtOfficeName')" onblur="loadownerName();" id="off_img" name="off_img"></img>  <input type="hidden" name="dept_id" id="dept_id"/>
                  </div>
                </td>
              </tr>
                <tr class="table">
                <td>
                  <div align="left">
                  Office Name
                   <!--   <font color="#ff2121">*</font>-->
                  </div>
                </td>
                <td>
                  <div align="left">
                        <input type="text" name="txtemp_office"  id="txtemp_office"  maxlength="50" size="60" readonly/>
                  </div>
                </td>
              </tr>
	<!-- <tr class="table">
		<td><div align="left">Office Address</div></td>
		<td><label> 
		<div align="left">
		  <textarea name="mtxtOfficeAddress"
			id="mtxtOfficeAddress"></textarea>
        </div>
		</label></td>
	</tr> -->
	<tr class="table">
		<td><div align="left">Owner's Name and Code</div></td>
		<td><div align="left">
	      <!--  <input type="text" name="txtOwnersCode" id="txtOwnersCode"  size="6"  maxsize="7" onchange="loadownerName(this.value,'<%=s%>');>-->
	      
	      <select name="txtOwnersCode" id="txtOwnersCode" >
			<option value="">-- Select ---</option> 
			</select>
			
			
       </div></td>
	</tr>
	
	<tr class="table">
		<td><div align="left">Rent Agreement Period</div></td>
		<td><div align="left">From 
	      <input type="text" name="txtRentAgreementPeriodFrom" maxlength="10" size="10"
                   id="txtRentAgreementPeriodFrom" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.RentMaster.txtRentAgreementPeriodFrom);"
                         alt="Show Calendar"></img>&nbsp;
		To
        <input type="text" name="txtRentAgreementPeriodTo" maxlength="10" size="10"
                   id="txtRentAgreementPeriodTo" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.RentMaster.txtRentAgreementPeriodTo);"
                         alt="Show Calendar"></img></div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Advance Paid</div></td>
		<td><div align="left">
		  <input type="text" name="txtAdvancePaid" id="txtAdvancePaid" maxlength="12" size="12"
			onkeypress="return numbersonly1(event,this)">
		  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Advance Paid Date</div></td>
		<td><div align="left">
		  <input type="text" name="txtAdvancePaidDate" maxlength="10" size="10"
                   id="txtAdvancePaidDate" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
		  <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.RentMaster.txtAdvancePaidDate);"
                         alt="Show Calendar"></img></div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Rent Value as on Date</div></td>
		<td><div align="left">
          <input type="text" name="txtRentValueasonDate" maxlength="10" size="10"
                   id="txtRentValueasonDate" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.RentMaster.txtRentValueasonDate);"
                         alt="Show Calendar"></img>&nbsp;
          <input type="text" name="txtRentValue" id="txtRentValue" maxlength="12" size="12"
			onkeypress="return numbersonly1(event,this)">
		</div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Rental Payment Option</div></td>
		<td><div align="left">
		  <select name="cboRentalPaymentOption"
			id="cboRentalPaymentOption" onChange="RentalPaymentOption();">
		    <option value="S">---Select---</option>
		    <option value="M" selected>Monthly</option>
		   <!--  <option value="Q">Quarterly</option>
		    <option value="H">Half-Yearly</option>
		    <option value="A">Annually</option>   -->
	      </select>
		  </div></td>
	</tr>
	<!--  <tr class="table">
		<td><div align="left">Payment Month 1</div></td>
		<td><div align="left">
		  <select name="cboPaymentMonth11" id="cboPaymentMonth11">
		    <option value="0">---Select---</option>
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
		  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Payment Month 2</div></td>
		<td>
		  <div align="left">
		    <select name="cboPaymentMonth12"
			id="cboPaymentMonth12">
		      <option value="0">---Select---</option>
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
      </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Payment Month 3</div></td>
		<td>
		  <div align="left">
		    <select name="cboPaymentMonth13"
			id="cboPaymentMonth13">
		      <option value="0">---Select---</option>
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
      </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Payment Month 4	</div></td>
		<td>
		  <div align="left">
		    <select name="cboPaymentMonth14"
			id="cboPaymentMonth14">
		      <option value="0">---Select---</option>
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
      </div></td>
	</tr>  -->
	<tr class="table">
		<td><div align="left">TDS Deduction if any</div></td>
		<td><div align="left">
		  <input type="text" name="txtTDSDeductionifany" id="txtTDSDeductionifany" maxlength="12" size="12"
			onkeypress="return numbersonly1(event,this)">
		  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Annual amount subject to IT </div></td>
		<td><div align="left">
		  <input type="text" name="txtAnnualAmounttoIT" id="txtAnnualAmounttoIT" maxlength="12" size="12"
		  onkeypress="return numbersonly1(event,this)">
		  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">IT % </div></td>
		<td><div align="left">
		  <input type="text" name="txtITPercentage" id="txtITPercentage" size="1"  maxlength="2"
		  onkeypress="return numbersonly1(event,this)">
		  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Whether IT Exempted </div></td>
		<td><div align="left">
		  <input type="radio" name="radioITExempted" id="radioITExempted" value="Y" checked>Yes
		  <input type="radio" name="radioITExempted" id="radioITExempted" value="N">No
		  </div></td>
	</tr>
	<tr class="table">
		<td><div align="left">Remarks</div></td>
		<td><label> 
		<div align="left">
		  <textarea name="mtxtRemarks" id="mtxtRemarks"></textarea>
	    </div>
		</label></td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deleteeee('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="forRefresh('<%=s%>');" /> <input type="button"
			name="onexit" value="EXIT" id="onexit" onClick="exitfun('<%=s%>');" />
		</div>		</td>
	</tr>
</table>

<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="125%" align="center">
	<tr class="tdH">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH">
		<th width="4%">Select</th>
		<th width="8%">Accounting Unit</th>
		<th width="8%">Accounting For Office</th>
		<th width="6%">Owner's Code</th>
		<th width="6%">Owner's Name</th>
		<th width="8%">Rent Agreement Period From</th>
		<th width="8%">Rent Agreement Period To</th>
		<th width="7%">Advance Paid</th>
		<th width="7%">Advance Paid Date</th>
		<th width="7%">Rent as on Date</th>
		<th width="8%">Rent Value</th>
		<th width="10%">Rental Payment Option</th>
		<!-- <th width="7%">Payment Month 1</th>
		<th width="7%">Payment Month 2</th>
		<th width="7%">Payment Month 3</th>
		<th width="8%">Payment Month 4</th> -->
		<th width="7%">TDS Deduction if any</th>
		<th width="7%">Annual amount subject to IT</th>
		<th width="7%">IT %</th>
		<th width="7%">whether IT exempted</th>
		<th width="7%">Office</th>
		<th width="6%">Remarks</th>
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>

</form>
</body>
</html>
