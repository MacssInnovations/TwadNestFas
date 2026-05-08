<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<META HTTP-EQUIV="CACHE-CONTROL"
	CONTENT=" no-store, no-cache, must-revalidate">
<META HTTP-EQUIV="CACHE-CONTROL"
	CONTENT=" pre-check=0, post-check=0, max-age=0">
<title>Journal System</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />



	
	 <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/new _Contractor _Form.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
	


  <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
  
   
<script type="text/javascript" language="javascript">
	function foc() {
	}
	function loadDate() {
		
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
document.frmJournal_Bill_Create1.txtCrea_date.value = day + "/" + month	+ "/" + year;
		call_date(document.frmJournal_Bill_Create1.txtCrea_date); 
		
	}
	function onclr(){
	
		 var tbody = document.getElementById("grid_body");
		try {
			tbody.innerHTML = "";
		} catch (e) {
			tbody.innerText = "";
		}	
	}
	function clr()
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
document.frmJournal_Bill_Create1.txtCrea_date.value = day + "/" + month	+ "/" + year;
		//document.getElementById("Schemename").value="";
		
		document.getElementById("cmbMas_SL_type").value="";
		document.getElementById("cmbMas_SL_Code").value="";
		document.getElementById("txtBill_NO").value="";
		document.getElementById("txtBill_date").value="";
		document.getElementById("txtBill_type").value="";
		document.getElementById("txtAgree_No").value="";
		document.getElementById("txtAgree_Date").value="";
		document.getElementById("txtRemarks").value="";
		document.getElementById("txtBill_type").value="";
		document.getElementById("txtBill_type").value="";
		document.getElementById("txtBill_type").value="";
	}
	
    function allnumeric(inputtxt)  
    {  
       var numbers = /^[0-9]+$/;  
       if(inputtxt.value.match(numbers))  
       {  
       alert('enter bill number....');  
       document.frmJournal_Bill_Create1.txtBill_NO.focus();  
       return true;  
       }  
       else  
       {  
       alert('Please input numeric characters only');  
       document.frmJournal_Bill_Create1.txtBill_NO.focus();  
       return false;  
       }  
    }  
    
    function allnumeric1(inputtxt)  
    {  
       var numbers = /^[0-9]+$/;  
       if(inputtxt.value.match(numbers))  
       {  
       alert('enter bill number....');  
       document.frmJournal_Bill_Create1.txtAgree_No.focus();  
       return true;  
       }  
       else  
       {  
       alert('Please input numeric characters only');  
       document.frmJournal_Bill_Create1.txtAgree_No.focus();  
       return false;  
       }  
    }   
    
    
    function allLetter(inputtxt)  
    {   
    var letters = /^[A-Za-z]+$/;  
    if(inputtxt.value.match(letters))  
    {  
    alert('enter remarks alphabet in charaters only');  
    return true;  
    }  
    else  
    {  
    alert('Please input alphabet characters only');  
    return false;  
    }  
    }  

	
	
</script>
</head>
<%
	String s = request.getContextPath();
	String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
	String cmbOffice_code=request.getParameter("cmbOffice_code");
%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevent caching at the proxy server
%>
<body onload="call_clr();LoadAccountingUnitID_Create('LIST_ALL_UNITS');loadDate();foc();" bgcolor="rgb(255,255,225)">
	<table cellspacing="1" cellpadding="3" width="100%">
		<tr class="tdH">
			<td colspan="2">
				<div align="center">
					<font size="4">Create Journal System (for Liablity ) </font>
				</div>
			</td>
		</tr>
	</table>
	<form name="frmJournal_Bill_Create1" id="frmJournal_Bill_Create1"
		method="POST" action="../../../../../new_Contractor_LJV"
		onsubmit="return checkNull()">
<input type="hidden" id="command" name="command" value="Add">
		<div class="tab-pane" id="tab-pane-1">
			<div class="tab-page">
				<h2 class="tab">General</h2>

				<div align="center">
					<table cellspacing="1" cellpadding="2" border="1" width="100%">
						<tr class="tdTitle">
							<td colspan="2">
								<div align="left">
									<strong>Office Details</strong>
								</div>
							</td>
						</tr>
						<tr class="table">
							<td>
								<div align="left">Office&nbsp;Name</div>
							</td>
							<td>
								<div align="left">
									<input type="text" name="txtAcc_unitName" id="txtAcc_unitName"
										value="<%=oname%>" maxlength="60" size="60"
										readonly="readonly" class="disab"/>
								</div>
							</td>
						</tr>
						<tr class="tdTitle">
							<td colspan="2">
								<div align="left">
									<strong>General Details</strong>
								</div>
							</td>
						</tr>
						<tr class="table">
							<td>
								<div align="left">
									Accounting Unit Code <font color="#ff2121">*</font>
								</div>
							</td>
							<td>
							
								<div align="left">
									

									<select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onChange="common_LoadOffice_New(this.value);" tabindex="1">
										<!-- <option value="0"> Select Account Unit </option>-->
										<%
											int unitid = 0;
							              int office = 0;
							              String JournalType=null;
											String unitname = "";
											try {
												if (oid == 5000) {
													//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
													//ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
													String getWing = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
													ps = con.prepareStatement(getWing);
													ps.setInt(1, oid);
													ps.setInt(2, empid);
													ps.setInt(3, oid);
													rs = ps.executeQuery();

													if (rs.next()) {
														out.println("<option value="
																+ rs.getInt("ACCOUNTING_UNIT_ID") + ">"
																+ rs.getString("ACCOUNTING_UNIT_NAME")
																+ "</option>");
														unitid = rs.getInt("ACCOUNTING_UNIT_ID");

														System.out.println(".."
																+ rs.getInt("ACCOUNTING_UNIT_ID"));
														System.out.println(".."
																+ rs.getString("ACCOUNTING_UNIT_NAME"));
														System.out
																.println(".." + rs.getInt("OFFICE_WING_SINO"));

													}
													System.out.println(oid + " " + oname);
													ps.close();
													rs.close();
												} else {
													ps = con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
													ps.setInt(1, oid);
													rs = ps.executeQuery();
													if (rs.next()) {
														System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
														System.out
																.println(rs.getString("ACCOUNTING_UNIT_NAME"));
														//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
														out.println("<option value="
																+ rs.getInt("ACCOUNTING_UNIT_ID") + " >"
																+ rs.getString("ACCOUNTING_UNIT_NAME")
																+ "</option>");
														unitid = rs.getInt("ACCOUNTING_UNIT_ID");
													}
													ps.close();
													rs.close();
												}
											} catch (Exception e) {
												System.out.println(e);
											}
										%>
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
									<select size="1" name="cmbOffice_code" id="cmbOffice_code"
										tabindex="2">
										<%
											int office_id = 0;
											System.out.println("here");
											System.out.println(oid + "  " + oname);
											try {
												if (oid == 5000) {
													out.println("<option value=" + oid + ">" + "HEAD OFFICE"
															+ "</option>");
												} else {

													ps = con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
													ps.setInt(1, unitid);
													rs = ps.executeQuery();
													//out.println("<option value="+oid+">"+oname+"</option>");
													while (rs.next()) {
														int old_offid = 0;

														ps2 = con
																.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=?");
														ps2.setInt(1, empid);
														rs2 = ps2.executeQuery();
														while (rs2.next()) {
															old_offid = old_offid + 1;
														}
														if (old_offid != 0) {
															ps2 = con
																	.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? ");
														} else if (old_offid == 0) {
															ps2 = con
																	.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('CL','NC','RD')");
														}
														//ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
														office = rs.getInt("ACCOUNTING_FOR_OFFICE_ID");
														ps2.setInt(1, office);
														rs2 = ps2.executeQuery();
														if (rs2.next())
															out.println("<option value="
																	+ rs.getInt("ACCOUNTING_FOR_OFFICE_ID")
																	+ ">" + rs2.getString("OFFICE_NAME")
																	+ "</option>");
													}
												}

											} catch (Exception e) {
												System.out.println("Exception in Office combo..." + e);
											} finally {
												rs.close();
												ps.close();
											}
										%>
									</select>
								</div>
							</td>
						</tr>

						
	<tr class="table">
							<td>
								<div align="left">
									Date <font color="#ff2121">*</font>
								</div>
							</td>
							<td>
								<div align="left">
									<input type="text" name="txtCrea_date" id="txtCrea_date"
										readonly tabindex="3" maxlength="10" size="11"
										onfocus="javascript:vDateType='3';"
										onkeypress="return calins(event,this);"
										onblur="dateCheck1(this);return call_mainJSP_script(this,1);"  /> 
										
										   
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_Bill_Create1.txtCrea_date,1);" 
                         alt="Show Calendar"></img>
										
								</div>
							</td>
						</tr>
						
						<tr class="table">
							<td>
								<div align="left">
									<!-- Schemes <font color="#ff2121">*</font>-->
									<font color="Red" size="1%">Select Scheme</font>
								</div>
							</td>
							<td>
								<div align="left">
									<%-- <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->

									<select size="1" name="Schemename" style="width: 400px;" id="Schemename" tabindex="1" onchange="loadSchdebitcode();">
										<option value="">-- Select --</option>
										<%
											ps = con.prepareStatement("select sch_sno, project_name from PMS_MST_PROJECTS_VIEW  where office_id=5000");
											out.println("schmename" + ps);
											rs = ps.executeQuery();
											while (rs.next()) {

												System.out.println(rs.getString("project_name"));
												//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
												out.println("<option value=" + rs.getInt("sch_sno") + " >"
														+ rs.getString("project_name") + "</option>");
												// unitid=rs.getInt("ACCOUNTING_UNIT_ID");
											}
											ps.close();
											rs.close();
										%>



									</select> <input type="button" onclick="loadsch();" value="Go">--%>
									<!-- <a href="javascript:loadscpage();"><font color="Red" size="3%">Select Scheme</font></a>-->
								
								
								<input type="text" name="sch_code"  
                           id="sch_code" maxlength="6"
                           onkeypress="return numbersonly(event)"
                            onchange="ListSub('loadSchdebitcode','null')" 
                            onblur="ListSub('loadSchdebitcode','null')"  size="9"/>
                            
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="loadscpage();"></img>
								
								<input type="text" name="sch_desc" readonly="readonly" 
                           id="sch_desc" style="background-color: #ececec"  maxlength="125" size="70"/>
								
								</div>
								
							</td>
						</tr>
								<tr class="table">
							<td>
								<div align="left">Journal&nbsp;Voucher Number</div>
							</td>
							<td>
								<div align="left">
									<input type="hidden" name="txtJournalVou_No"
										id="txtJournalVou_No" style="background-color: #ececec"
										readonly="readonly" size="6" maxlength="5" />(System
									Generated)
								</div>
							</td>
						</tr>
						<tr class="table">
							<td width="30%">
								<div align="left">
									Journal Type <font color="#ff2121">*</font>
								</div>
							</td>
							<td>
								<div align="left">
									<select name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="6" 
										onchange="doFunction('Load_MasterSL_Codenew',this.value);onclr();"> 
										<option value="">--Select Journal Type--</option>
										
										<%
											try {
												ps = con.prepareStatement("select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where CATEGORY='L' AND JOURNAL_TYPE_CODE in (1,2,9,11) order by JOURNAL_TYPE_CODE");
												rs = ps.executeQuery();
												while (rs.next()) {
													out.println("<option value="
															+ rs.getInt("JOURNAL_TYPE_CODE") + ">"
															+ rs.getString("JOURNAL_TYPE_DESC") + "</option>");
													JournalType=rs.getString("JOURNAL_TYPE_DESC");
													System.out.println("JournalType"+JournalType);
													request.setAttribute("JournalType1", JournalType);
													System.out.println("JournalType"+request.getAttribute("JournalType1"));
												}

											} catch (Exception e) {
												System.out.println("Exception in Journal combo..." + e);
											} finally {
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
								<div align="left">Sub-Ledger Code</div>
							</td>
							<td width="60%">
								<table align="left">
									<tr align="left">
										<td>
											<div align="left">
												<select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code" 
												onchange="getCrHEAD(this.value);"		tabindex="7">
																								
										<!-- 		onchange="getmyvalue();" -->
												
												
												
											

													<option value="">--Select Code--</option>
												</select>
											</div>
											
										</td>
										<td>
											<div align="left" id="offlist_div_master"
												style="display: none">

												<img src="../../../../../images/c-lovi.gif" width="20"
													height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
												<input type="text" name="txtOfficeID_mas"
													id="txtOfficeID_mas" maxlength="4" size="5"
													onblur="mas_office(this.value);" />
											</div>
											<div align="left" id="emplist_div_master"
												style="display: none">
												<img src="../../../../../images/c-lovi.gif" width="20"
													height="20" alt="empList"
													onclick="employee_popup_master();"></img> <input
													type="text" name="txtEmpID_mas" id="txtEmpID_mas"
													maxlength="5" size="5" onblur="mas_employee(this.value);" />
											</div>
										</td>

									</tr>
								</table>
							</td>
						</tr>

						<tr class="table">
							<td>
								<div align="left">Bill Number</div>
							</td>
							<td>
								<div align="left">
									<input type="text" name="txtBill_NO" maxlength="10" 
										 id="txtBill_NO" 
										size="11" />
								</div>
							</td>
						</tr>
						<tr class="table">
							<td>
								<div align="left">Bill Date</div>
							</td>
							<td>
								<div align="left">
									<input type="text" name="txtBill_date" id="txtBill_date"
										maxlength="10" size="11" onfocus="javascript:vDateType='3';"
										onkeypress="return calins(event,this);"
										onblur="return checkdt(this);" /> <img
										src="../../../../../images/calendr3.gif"
										onclick="showCalendarControl(document.frmJournal_Bill_Create1.txtBill_date);"
										alt="Show Calendar"></img>
								</div>
							</td>
						</tr>
						<tr class="table">
							<td>
								<div align="left">Bill Type</div>
							</td>
							<td>
								<div align="left">
									<input type="text" name="txtBill_type" id="txtBill_type" 
										size="50" />
								</div>
							</td>
						</tr>
						<tr class="table">
							<td>
								<div align="left">Agreement Number</div>
							</td>
							<td>
								<div align="left">
									<input type="text" name="txtAgree_No" id="txtAgree_No" 
										size="11" maxlength="10" />
								</div>
							</td>
						</tr>
						<tr class="table">
							<td>
								<div align="left">Agreement Date</div>
							</td>
							<td>
								<div align="left">
									<input type="text" name="txtAgree_Date" id="txtAgree_Date"
										maxlength="10" size="11" onfocus="javascript:vDateType='3';"
										onkeypress="return calins(event,this);"
										onblur="return checkdt(this);" /> <img
										src="../../../../../images/calendr3.gif"
										onclick="showCalendarControl(document.frmJournal_Bill_Create1.txtAgree_Date);"
										alt="Show Calendar"></img>
								</div>
							</td>
						</tr>
						<tr class="table">
							<td width="30%">
								<div align="left">
									Remarks <font color="#ff2121">*</font>
								</div>
							</td>
							<td>
								<div align="left">
									<textarea name="txtRemarks" id="txtRemarks" cols="50"
										tabindex="6"   onkeypress="return check_leng(this.value);"
										rows="4"></textarea>
								</div>
							</td>
						</tr>


					</table>
				</div>
			</div>

	
		<div
	     class="tab-page" id="gd">
				<h2 class="tab">Debit</h2>

				<div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="tdH">
                        <th > Sno  </th>
                       <th>A/c Head Code</th>
                       <th  style="display: none;">CR/DR</th>       
                        <th >Sub Ledger Type</th>
                        <th>Sub Ledger Code</th>
                        <th>Amount</th>
                         <th>Remarks</th>
                        
                      </tr>
                      
                       <tbody id="grid_body2" class="table" align="left" >
                        
                       </tbody>
                       
                    </table>
                  </div>
			
		</div>
				<div class="tab-page" id="gd">
				<h2 class="tab">Credit</h2>

				
				
				<div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="tdH">
                        <th > Sno  </th>
                       <th>A/c Head Code</th>
                          <th style="display: none;">CR</th>    
                        <th >Sub Ledger Type</th>
                        
                        <th>Sub Ledger Code</th>
                        <th>Amount</th>
                         <th>Remarks</th>
                        
                      </tr>
                      
                      
                       <tbody id="grid_body" class="table" align="left" >
                     <%--   
                      <% 
                      int a=0,b=0;
                      String s1="",s2="";
                      int[] intArray = new int[12];
                      List<String> intArray1 = new LinkedList<String>();
                      int size = intArray1.size();
                      intArray[0] = 100101;
                      intArray[1]= 130109;
                      intArray[2]= 222110;
                      intArray[3] = 550102;
                      intArray[4]= 550112;
                      intArray[5] = 550401;
                      intArray[6] = 550402;	
                      intArray[7] = 550520;
                      intArray[8] = 550601;
                      intArray[9] = 550602;		
                      intArray[10] = 550603;
                      intArray[11]= 901001;
                      
                      for (int i=0; i<intArray.length; i++)
                      {
                  
                        try {
							ps = con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE="+intArray[i]+"");
							rs = ps.executeQuery();
							while (rs.next()) {
						  a=	rs.getInt("ACCOUNT_HEAD_CODE");
							 s1=	rs.getString("ACCOUNT_HEAD_DESC"); 
							
						   intArray1.add(s1);	
							 
							 
							}%>
							
							  
                      <%    }
							catch (Exception e) {
							
							
							}
                        
                    
                        
                        
                        
                        
                        
                        
                        
                      }
							%>
							
							
							
                     
							 
		              <%int k=1; %>
		                        <% for(int i = 0; i<intArray.length; i++) { %>
		                       <tr id="trow<%=i %>"  name="trow<%=i %>" ><td><input type="hidden" id="sno"  name="sno" value="<%=k %>"/><label><%=k %></label></td>
		                       <td>
															<input type="hidden" id="H_code" name="H_code" value="<%=intArray[i] %>" />
															<label><%=intArray[i] %></label><label> - </label><label><%=intArray1.get(i) %></label>
													 <input type="text" readonly="readonly" maxlength="150"  style="width:92%" value="<%=intArray1.get(i) %>" />
		                       <td style="display: none;"><input type="hidden" name="CR_DR_type"  value="CR"  id="CR_DR_type" /> <label style="text-align: center;">CR</label> </td>
		                       <td><select  name="SLtype" id="SLtype" onchange="subledgercode(this.value,<%=i %>);"> 
		                     
	   <option value="">--select--</option>
		                       <%       try {
							PreparedStatement ps_n = con.prepareStatement(" SELECT h.ACCOUNT_HEAD_CODE, " +
									//	"  ah.ACCOUNTING_UNIT_ID, " +
									"  h.ACCOUNT_HEAD_DESC, " +
									"  h.BALANCE_TYPE, " +
									"  h.SUB_LEDGER_TYPE_APPLICABLE, " +
									"  h.REMARKS, " +
									"  h.sl_mandatory, " +
									"  app.SUB_LEDGER_TYPE_CODE, " +
									"  sl.sub_ledger_type_desc " +
									"FROM COM_MST_ACCOUNT_HEADS h, " +
									//"  Fas_Restricted_Ac_Heads ah, " +
									"  FAS_APPLICABLE_SL_TYPE app, " +
									"  COM_MST_SL_TYPES sl " +
									" WHERE h.USAGE_STATUS       ='Y' " +
									" AND h.ACCOUNT_HEAD_CODE   = " +intArray[i]+
									" AND h.ACCOUNT_HEAD_CODE    =app.ACCOUNT_HEAD_CODE " +
								//	"AND H.ACCOUNT_HEAD_CODE    = AH.ACCOUNT_HEAD_CODE " +
									" AND sl.SUB_LEDGER_TYPE_CODE=app.sub_ledger_type_code " );
						ResultSet	rs_n = ps_n.executeQuery();
							while (rs_n.next()) {
						  b=	rs_n.getInt("SUB_LEDGER_TYPE_CODE");
							 s2=	rs_n.getString("sub_ledger_type_desc"); 
							
						//   intArray1.add(s1);	
							
							 out.println("<option value="+b+">"+s2+"</option>");
				
							}
							
							  
                    }
							catch (Exception e) {
							
							
							}
		                           %>
		                       
		                       </select></td>
		                       <td><select id="SL_code<%=i %>" name="SL_code<%=i %>"  Style= width:90%; ><option value="">--select--</option>
		                       </select>   </td>
		                       <td> <input type="text" name="sl_amt" id="sl_amt_<%=i %>" maxlength="15"/>   </td>
		                       <td> <input type="text" name="particular" id="particular"/>   </td>
		             

		                       
		                  </tr>
		                   <% k++;} %> --%>
		                       </tbody>
		                       
		                    </table>
		                  </div>
						
						
					</div>
				
		
		
	</div>
		<br></br>
		<div align="center">
			<table cellspacing="1" cellpadding="3" width="100%">
				<tr class="tdH">
					<td>
						<div align="center" style="disply: block" id="newDiv"	name="newDiv">
							<input type="submit" name="butSub" id="butSub" value="SUBMIT" />
							&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
								value="CANCEL" onclick="clrForm();" /> &nbsp;&nbsp;&nbsp; <input
								type="button" name="butCan" id="butCan" value="EXIT"
								onclick="exit();" />
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>