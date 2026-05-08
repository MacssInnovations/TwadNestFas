<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>InterBankTransfer_MultipleBanks</title>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/PaymentSystem/scripts/Common_PaymentType.js"></script>
<script type="text/javascript"
	src="../scripts/InterBankTransfer_MultipleBanks.js"></script>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
<!-- to avoid future date the above script used-->


<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>

<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_FT_forIBT.js"></script>
	
	 <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    

<script type="text/javascript" language="javascript"><!--
         function foc()
         {
         }
         function loadDate()
         {
        	// call_bankUpdate();
        	//setTimeout('call_a52()',900);
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
             var monthArray1 =new Array("Jan", "Feb", "Mar", 
                     "Apr", "May", "Jun", "Jul", "Aug",
                     "Sep", "Oct", "Nov", "Dec");
            // alert(day+"/"+month+"/"+year);
            document.frm_InterBankTransfer_MultipleBanks.txtCrea_date.value=day+"/"+month+"/"+year;
           // alert(day+"/"+month+"/"+year);
          var ctdate=day+"-"+monthArray1[today.getMonth()]+"-"+year;  
           
     
         }
         function loadDate1()
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
                document.frm_InterBankTransfer_MultipleBanks.txtCrea_date.value=day+"/"+month+"/"+year;
                call_date(document.frm_InterBankTransfer_MultipleBanks.txtCrea_date);
       }
</script>
</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<body onLoad="setTimeout('loadDate1()', 300);LoadAccountingUnitID_Create('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Inter Bank Transfer
		System (Create) for Multiple Banks </font></div>
		</td>
	</tr>
</table>



<form name="frm_InterBankTransfer_MultipleBanks"
	id="frm_InterBankTransfer_MultipleBanks" method="POST"
	action="../../../../../InterBankTransfer_MultipleBanks"><input
	type="hidden" name="RecordCount" id="RecordCount" value="0"><input
	type="hidden" name="filter" id="filter" value="">  <%
 	Connection con = null;
 	ResultSet rs = null, rs2 = null, rsbank = null;
 	PreparedStatement ps = null, ps2 = null, psbank = null;
 	PreparedStatement ps_acc = null;
 	ResultSet rs_acc = null;
 	ResultSet results = null;
 	ResultSet results1 = null;
 	ResultSet results2 = null;
 	try {

 		ResourceBundle rs1 = ResourceBundle
 				.getBundle("Servlets.Security.servlets.Config");
 		String ConnectionString = "";

 		String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
 		String strdsn = rs1.getString("Config.DSN");
 		String strhostname = rs1.getString("Config.HOST_NAME");
 		String strportno = rs1.getString("Config.PORT_NUMBER");
 		String strsid = rs1.getString("Config.SID");
 		String strdbusername = rs1.getString("Config.USER_NAME");
 		String strdbpassword = rs1.getString("Config.PASSWORD");

 		//ConnectionString = strdsn.trim() + "@" + strhostname.trim()
 			//	+ ":" + strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
 		Class.forName(strDriver.trim());
 		con = DriverManager.getConnection(ConnectionString,
 				strdbusername.trim(), strdbpassword.trim());
 	} catch (Exception e) {
 		System.out.println("Exception in connection...." + e);
 	}
 	long l=System.currentTimeMillis();
	Timestamp ts=new Timestamp(l);                      
	 Date ctdate = new java.sql.Date(ts.getTime()); 
 %> <%
 	HttpSession session = request.getSession(false);
 	UserProfile empProfile = (UserProfile) session
 			.getAttribute("UserProfile");

 	System.out.println("user id::" + empProfile.getEmployeeId());
 	int empid = empProfile.getEmployeeId();

 	//int empid=9315;
 	int oid = 0;
 	String oname = "";
 	try {

 		//ps = con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
 		//ps.setInt(1, empid);
 		 ps=con.prepareStatement(" SELECT "+
            		"  CASE "+
            		 "   When Old_Office_Id   Is Not Null "+
            		  "  AND DATE_ALLOWED_UPTO>=? "+
            		    " THEN OLD_OFFICE_ID "+
            		    " ELSE Office_Id "+
            		  " END AS OFFICE_ID "+
            		" FROM "+
            		  " (SELECT Office_Id, "+
            		    " OLD_OFFICE_ID, "+
            		    " DATE_ALLOWED_UPTO "+
            		  " From Hrm_Emp_Current_Posting "+
            		  " Where Employee_Id=? )as ps" );
            ps.setDate(1, ctdate);
            ps.setInt(2,empid);
 		results = ps.executeQuery();
 		if (results.next()) {
 			oid = results.getInt("OFFICE_ID");
 		}
 		results.close();
 		ps.close();
 		ps = con
 				.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
 		ps.setInt(1, oid);
 		results = ps.executeQuery();
 		if (results.next()) {
 			oname = results.getString("OFFICE_NAME");
 		}
 		results.close();
 		ps.close();
 		/* */
 		System.out.println("off id.. emp id" + oid + ".." + empid);
 	} catch (Exception e) {
 		System.out.println(e);
 	}
 %>


<div class="tab-pane" id="tab-pane-1"><!-- 1st Tab General Starts -->
<div class="tab-page">
<h2 class="tab">General</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
		<select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
			tabindex="1"  onChange="common_LoadOffice_New(this.value);">
			<!-- <option value="0"> Select Account Unit </option>-->
			<%
				int unitid = 0;
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
						ps = con
								.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
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
		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">

			<%
				System.out.println("here");
				System.out.println(oid + "  " + oname);
				try {
					if (oid == 5000) {
						out.println("<option value=" + oid + ">" + "HEAD OFFICE"
								+ "</option>");
					} else {
						ps = con
								.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
						ps.setInt(1, unitid);
						rs = ps.executeQuery();
						//out.println("<option value="+oid+">"+oname+"</option>");
						int countoffice = 0; // used to load the bank details for the first office of combo box
						while (rs.next()) {
							ps2 = con
									.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
							ps2.setInt(1, rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								out.println("<option value="
										+ rs.getInt("ACCOUNTING_FOR_OFFICE_ID")
										+ ">" + rs2.getString("OFFICE_NAME")
										+ "</option>");
							}
						}
					}

				} catch (Exception e) {
					System.out.println("Exception in Office combo..." + e);
				} finally {
					rs.close();
					ps.close();
				}
			%>
		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Date <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtCrea_date" readonly
			id="txtCrea_date" tabindex="3" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" 
			onblur="call_date(this);dateCheck(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_InterBankTransfer_MultipleBanks.txtCrea_date,1);"
			alt="Show Calendar"></img>
		<label>
		<input type="button" name="btnView" id="btnView" value="VIEW VOUCHER NO FOR UPDATE" onClick="load_Voucher_No();">
		</label>
		</div>		</td>
	</tr>


	<tr class="table">
		<td>
		<div align="left">Voucher Number</div>		</td>
		<td>
		<div align="left">
		  <label>
		  <select size="1" name="txtVoucher_No" id="txtVoucher_No" tabindex="5" onChange="load_Voucher_details();" style="visibility:hidden">
            <option value="">--Select Voucher Number--</option>
          </select>
		  </label>
		  (System Generated)</div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">From Bank Account Number <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><!--  <input type="text" name="txtBankAccountNo"  onkeypress="return numbersonly(event)" onchange="doFunction('load_bank_deatils','CR');"
                           id="txtBankAccountNo" maxlength="15"  size="15" />-->
		<select name="txtBankAccountNo" id="txtBankAccountNo" onchange="doFunction('load_bank_deatils','CR');">
			<option value="">--Select Bank Account Number--</option>
			<%
				ps_acc = con.prepareStatement("select b.BANK_AC_NO, a.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID || '-' || b.BANK_AC_NO as BANK_AC_NO_DISPLAY from FAS_MST_BANKS a,FAS_OFFICE_BANK_AC_CURRENT b where b.STATUS='Y' and b.BANK_ID=a.BANK_ID and ACCOUNTING_UNIT_ID=? and  MODULE_ID=? and  CR_DR_TYPE=? order by a.BANK_SHORT_NAME");
				ps_acc.setInt(1, unitid);
				ps_acc.setString(2, "MF010");
				ps_acc.setString(3, "CR");
				rs_acc = ps_acc.executeQuery();
				while (rs_acc.next()) {
					out.println("<option value=" + rs_acc.getLong("BANK_AC_NO")
							+ ">" + rs_acc.getString("BANK_AC_NO_DISPLAY")
							+ "</OPTION>");
					System.out.println("bank_ac_no..."
							+ rs_acc.getLong("BANK_AC_NO"));
				}
				rs_acc.close();
				ps_acc.close();
			%>
		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Credit A/c Code <font color="#ff2121">
		<font color="#ff2121"> * </font> </font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtCash_Acc_code"
			id="txtCash_Acc_code" style="background-color: #ececec"
			readonly="readonly" maxlength="8" size="9" /> <!--  <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountNumberList"
                             onclick="MainAccNopopup();"></img>--></div>		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">From Bank Name</div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBankName"
			id="txtBankName" readonly="readonly"
			style="background-color: #ececec" size="50" maxlength="49" /> <input
			type="hidden" name="txtBankId" readonly="readonly" id="txtBankId"
			size="5" maxlength="5" /> <input type="hidden" name="txtBranchId"
			readonly="readonly" id="txtBranchId" size="5" maxlength="5" /></div>		</td>
	</tr>
	 <tr class="table">
                <td>
                  <div align="left">
                    Total Amount (Rs. P.) 
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtTotalAmount"  onkeypress="return limit_amt(this,event);"  onblur="valid_amt(this);"
                           id="txtTotalAmount" maxlength="17" size="18"/>
                  </div>                </td>
              </tr>
	<tr class="table">
		<td>
		<div align="left">Head Office Reference Number</div>		</td>
		<td>
		<div align="left"><input type="text" name="txtReferenceNo"
			id="txtReferenceNo" size="50" maxlength="50" /></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Head Office Reference Date</div>		</td>
		<td>
		<div align="left"><input type="text" name="txtReferenceDate"
			id="txtReferenceDate" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);"
			onblur=" return checkdt(this);;" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_InterBankTransfer_MultipleBanks.txtReferenceDate);"
			alt="Show Calendar"></img></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Remarks</div>		</td>
		<td>
		<div align="left"><textarea name="txtRemarks" id="txtRemarks"
			cols="60" onKeyPress="return check_leng(this.value);" rows="3"></textarea>
		</div>		</td>
	</tr>
</table>
</div>
<br>

</div>
<!-- 2st General Tab Ends --> <!-- 3rd Detail Tab Starts -->
<div class="tab-page" id="gd">
<h2 class="tab">Details</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr>
		<td colspan="2">
		<div id="ProceedingDetails">

		<table id="Existing" cellspacing="1" cellpadding="2" border="0"
			width="100%" class="table">
			<tr class="tdTitle">
				<td colspan="9">
				<div align="left"><strong> Details</strong></div>
				</td>
			</tr>

			<tr class="table">
			
			    <th width="7%">
				<div align="center">Select</div>
				</th>
				
				<th width="18%">
				<div align="center">To Bank Account Number</div>
				</th>

				<th width="11%">
				<div align="center">Debit A/c Code</div>
				</th>

				<th width="10%">
				<div align="center">To Bank Name</div>
				</th>

				<th width="9%">
				<div align="center">Cheque/DD <font color="#ff2121"> </font></div>
				</th>

				<th width="12%">
				<div align="center">Cheque/DD Number</div>
				</th>

				<th width="11%">
				<div align="center">Cheque/DD Date</div>
				</th>

				<th width="9%">
				<div align="center">Total Amount</div>
				</th>
				
				<th width="13%">
				<div align="center">Remarks</div>
				</th>
			</tr>
			<tr class="table">
				<th><div align="right">
  <input type="button" name="Add" id="Add" value="Add"
					onclick="AddRow();" />
  &nbsp;&nbsp;&nbsp;</div></th>
				<th><div align="left">
				  <input type="button"
					name="Clear" id="Clear" value="Clear" onClick="ClearRow();" />
				  </div></th></tr>
			<tbody id="tblList" align="center">
			</tbody>
		</table>

		</div>

		</td>
	</tr>
</table>
</div>
</div>
<!-- 2rd Detail tab ends --></div>
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="submit" name="butSub"
			id="butSub" value="Submit" onClick="return funcSave();"/>  &nbsp;&nbsp;&nbsp;
		  <input type="submit" name="butUpdate"
			id="butUpdate" value="UPDATE" onClick="return funcUpdate();" disabled="disabled"/>
		  &nbsp;&nbsp;
          <input type="submit" name="butCancel"
			id="butCancel" value="CANCEL" onClick="return funcCancel();" disabled="disabled"/>
&nbsp; 
		  <input
			type="button" name="butCan" id="butCan" value="CLEAR"
			onClick="clrForm();" /> &nbsp;&nbsp;&nbsp; <input type="button"
			name="butCan" id="butCan" value="EXIT" onClick="exit();" /></div>		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>