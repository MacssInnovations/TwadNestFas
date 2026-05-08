<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adjustment Memo System(Single Receipt-Multiple Units)</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>

<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>

<script type="text/javascript"
	src="../js/Adj_Memo_SingleReceipt_Creation.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>

<!-- to avoid future date the above script used-->
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
             document.Adjustment_Memo_SingleReceipt.txtCrea_date.value=day+"/"+month+"/"+year;
             document.Adjustment_Memo_SingleReceipt.txtCB_Year.value=year;
             document.Adjustment_Memo_SingleReceipt.txtCB_Month.value=month;
             setTimeout('doFunction_voucher("load_Receipt_No")',900);
             
        }
        
        function clrallsl(){
    	
    	 document.Adjustment_Memo_SingleReceipt.cmbSL_type.disabled=false;
    	 document.Adjustment_Memo_SingleReceipt.cmbSL_Code.disabled=false;
    }
        
        function disp(dvalue){
        	if(dvalue=="Receipt"){
        		document.getElementById("hId").style.display="block";
        		document.getElementById("hId1").style.display="none";
        		document.getElementById("hId2").style.display="none";
        		document.getElementById("hIs").style.display="block";
        		document.getElementById("hIs1").style.display="none";
        		document.getElementById("hIs2").style.display="none";
        		document.getElementById("txtCB_Month").value="";
        		document.getElementById("txtAcc_HeadCode").value="610101";
        		document.forms[0].rad_sub_CR_DR[0].checked=true;
        		doFunction('checkCode','null');
        		clear();
        	}
        	else if(dvalue=="Payment"){
        	
        	    document.getElementById("hId").style.display="none";
        		document.getElementById("hId1").style.display="none";
        		document.getElementById("hId2").style.display="block";
        		document.getElementById("hIs").style.display="none";
        		document.getElementById("hIs1").style.display="none";
        		document.getElementById("hIs2").style.display="block";
        		document.getElementById("txtCB_Month").value="";
        		document.getElementById("txtAcc_HeadCode").value="900201";
        		document.forms[0].rad_sub_CR_DR[1].checked=true;
        		doFunction('checkCode','null');
        		clear();
        	
        	}
        	else{
        		document.getElementById("hId1").style.display="block";
        		document.getElementById("hId").style.display="none";
        		document.getElementById("hId2").style.display="none";
        		document.getElementById("hIs1").style.display="block";
        		document.getElementById("hIs").style.display="none";
        		document.getElementById("hIs2").style.display="none";
        		document.getElementById("txtAcc_HeadCode").value="610101";
        		document.forms[0].rad_sub_CR_DR[0].checked=true;
        		doFunction('checkCode','null');
        		clear();
        	}
        }
        function clear()
        {
        document.getElementById("ho_ref_no").value="";
        document.getElementById("ho_ref_date").value="";
        document.getElementById("txtTotalAmt").value="";
        
        }
        
        
	</script>
</head>
<body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS');"
	bgcolor="rgb(255,255,225)">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Adjustment Memo from
		Board(Single Receipt-Multiple Units)</font></div>
		</td>
	</tr>
</table>
<%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      PreparedStatement ps=null,ps2=null,ps3=null;
		      ResultSet results=null;
		      ResultSet results1=null,rs3=null;
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
		    
		           //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		    				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		                Class.forName(strDriver.trim());
		                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		      }
		      catch(Exception e)
		      {
		        		System.out.println("Exception in connection...."+e);
		      }
		      
  %>

<form name="Adjustment_Memo_SingleReceipt"
	id="Adjustment_Memo_SingleReceipt" method="POST"
	action="../../../../../Adj_Memo_SingleReceipt_Creation?Command=Add"
	onsubmit="return checkNull();">
<div class="tab-pane" id="tab-pane-1">
<div class="tab-page">
<h2 class="tab">General</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr class="table">
		<td>
		<div align="left">Accounting Unit Id <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">


		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Accounting For Office Id <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">

		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Receipt/Payment Date <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCrea_date"
			id="txtCrea_date" tabindex="3" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);"
			onblur="return checkdt(this);doFunction_voucher('load_Receipt_No',null);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.forms[0].txtCrea_date);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Authority Name</div>


		</td>
		<td>
		<div align="left"><textarea rows="3" tabindex="4" cols="35"
			id="authority" name="authority"></textarea></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Authority Address</div>


		</td>
		<td>
		<div align="left"><textarea rows="3" tabindex="4" cols="35"
			id="authorityaddress" name="authorityaddress"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Particulars</div>
		</td>
		<td>
		<div align="left"><textarea rows="3" tabindex="6" cols="35"
			id="particulars" name="particulars"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Adjustment Type</div>
		</td>
		<td>
		<div align="left">
		<input type="radio" id="rad_adj" name="rad_adj" value="Receipt" checked="checked" onclick="disp(this.value)">Receipt
		<!--  <input type="radio" id="rad_adj" name="rad_adj" value="Journal" onclick="disp(this.value)">Journal-->
		<input type="radio" id="rad_adj" name="rad_adj" value="Payment" onclick="disp(this.value)">Payment
		</div>
		</td>
	</tr>
	
	<tr class="table">
		<td>
		<div align="left" id="hId" style="display: block;">Receipt Cash Book Year &amp; Month <font
			color="#ff2121">*</font></div>
				<div align="left" id="hId1"  style="display: none;">Journal Cash Book Year &amp; Month <font
			color="#ff2121">*</font></div>
			<div align="left" id="hId2"  style="display: none;">Payment Cash Book Year &amp; Month <font
			color="#ff2121">*</font></div>
			
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly(event)"></input> <select
			name="txtCB_Month" id="txtCB_Month" tabindex="4"
			onchange="doFunction_voucher('load_Receipt_No',null);">
			<option value="">Select Month</option>
			<option value="01">January</option>
			<option value="02">February</option>
			<option value="03">March</option>
			<option value="04">April</option>
			<option value="05">May</option>
			<option value="06">June</option>
			<option value="07">July</option>
			<option value="08">August</option>
			<option value="09">September</option>
			<option value="10">October</option>
			<option value="11">November</option>
			<option value="12">December</option>
		</select></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left"  id="hIs" style="display: block;">Receipt Number <font color="#ff2121">*</font>	</div>
		<div align="left"  id="hIs1" style="display: none;">Journal Number <font color="#ff2121">*</font>
		</div>
		<div align="left"  id="hIs2" style="display: block;">Payment Number <font color="#ff2121">*</font>	</div>
		</td>
		<td>
		<div align="left"><select size="1" name="txtReceipt_No"
			id="txtReceipt_No" tabindex="5"
			onchange="doFunction_voucher('load_Receipt_Details','null');">
			<option value="">--Select--</option>
		</select></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">HO Ref. Number</div>
		</td>
		<td>
		<div align="left"><input type="text" name="ho_ref_no"
			id="ho_ref_no" readonly="readonly" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">HO Ref. Date</div>
		</td>
		<td>
		<div align="left"><input type="text" name="ho_ref_date"
			id="ho_ref_date" maxlength="10" size="11" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';" readonly="readonly" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Total Amount <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalAmt"
			id="txtTotalAmt" maxlength="16"
			onkeypress="return filter_real(event,this,10,2)" readonly
			tabindex="9"></input></div>
		</td>
	</tr>
</table>
</div>
</div>
<div class="tab-page" id="gd">
<h2 class="tab">Details</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr>
		<td colspan="2">
		<div id="sub_ledge_dis">
		<table cellspacing="1" cellpadding="2" border="1" width="100%">

			<tr class="table">
				<td>
				<div align="left">Account Head Code <font color="#ff2121">*</font>
				</div>
				</td>
				<td>
				<div align="left"><input type="text" name="txtAcc_HeadCode"
					id="txtAcc_HeadCode" maxlength="6" value="610101"
					disabled="disabled" onkeypress="return numbersonly(event)"
					onchange="sixdigit();" onblur="clrallsl();doFunction('checkCode','null');cr_dr();"
					size="9" /> <img src="../../../../../images/c-lovi.gif" width="20"
					height="20" alt="AccountHeadList" onclick="AccHeadpopup();"></img>
				<input type="text" name="txtAcc_HeadDesc" readonly="readonly"
					value="TRANSFER FORM BOARD  -RECEIPT ADJUSTED" id="txtAcc_HeadDesc"
					style="background-color: #ececec" maxlength="125" size="70" /></div>
				</td>
			</tr>

			<tr class="table">
				<td>
				<div align="left">CR/DR <font color="#ff2121">*</font></div>
				</td>
				<td>
				<div align="left"><input type="radio" name="rad_sub_CR_DR"
					id="rad_sub_CR_DR" checked="checked" value="CR" />Credit
				&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="rad_sub_CR_DR"
					id="rad_sub_CR_DR" value="DR" />Debit &nbsp;&nbsp;&nbsp;&nbsp;</div>
				</td>
			</tr>

			<tr class="table">
				<td>
				<div align="left">On Behalf of which office <font
					color="#ff2121">*</font></div>
				</td>
				<td>
				<div align="left"><select name="office_id" id="office_id"
					tabindex="4" onchange="CaLLoadSubLedger()">
					<option value="">--Select--</option>
					<%
		                    			ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS order by ACCOUNTING_UNIT_NAME");
		                    			rs=ps.executeQuery();
		                    			while(rs.next())
		                    			{
		                    					out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
		                    			}
		                    	%>
				</select></div>
				</td>
			</tr>
			<tr class="table">
				<td width="40%">
				<div align="left">Sub-Ledger Type <font color="#ff2121">*</font></div>
				</td>
				<td width="60%">
				<div align="left"><select size="1" name="cmbSL_type"
					id="cmbSL_type" >
					<option value="15">Accounting Units</option>
				</select></div>
				<div align="left" style="display: none"><select size="1"
					name="cmbMas_SL_type" id="cmbMas_SL_type">
					  <option value="">--Select Type--</option>
				</select></div>
				</td>
			</tr>

			<tr class="table">
				<td width="40%">
				<div align="left">Sub-Ledger Code <font color="#ff2121">*</font></div>
				</td>
				<td width="60%">
				<table align="left">
					<tr align="left">
						<td>
						<div align="left"><select size="1" name="cmbSL_Code"
							id="cmbSL_Code">
							<option value="">--Select Code--</option>
						</select></div>
						<div align="left" style="display: none"><select
							name="cmbMas_SL_Code" id="cmbMas_SL_Code">
							<option value="">--Select Type--</option>
						</select></div>
						</td>
						<td>
						<div align="left" id="offlist_div_trans" style="display: none">
						<img src="../../../../../images/c-lovi.gif" width="20" height="20"
							alt="OfficeList" onclick="jobpopup_trans();"></img> <input
							type="text" name="txtOfficeID_trs" id="txtOfficeID_trs"
							maxlength="4" size="5" onblur="trs_office(this.value);" /></div>
						<div align="left" id="emplist_div_trans" style="display: none">
						<img src="../../../../../images/c-lovi.gif" width="20" height="20"
							alt="empList" onclick="employee_popup_trans();"></img> <input
							type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5"
							size="5" onblur="trs_employee(this.value);" /></div>
						</td>

					</tr>
				</table>
				</td>
			</tr>
			<!-- 	<tr class="table">
					<td width="40%">
					<div align="left">Name of the Town <font color="#ff2121">*</font></div>
					</td>
					<td>
					<div align="left" ><select
								name="townname" id="townname">
								<option value="">--Select Town--</option>
							</select></div>
					
					</td>
				</tr>  -->
			<tr class="table">
				<td>
				<div align="left">Amount <font color="#ff2121">*</font></div>
				</td>
				<td>
				<div align="left"><input type="text" name="txtsub_Amount"
					id="txtsub_Amount" size="18" /></div>
				</td>
			</tr>

			<tr class="table">
				<td>
				<div align="left">Remarks</div>
				</td>
				<td>
				<div align="left"><textarea name="txtParticular"
					id="txtParticular" cols="70" rows="3"></textarea></div>
				</td>
			</tr>
			<tr class="table">
				<td>
				<div align="left">Letter No.</div>
				</td>
				<td>
				<div align="left"><input type="text" name="letterNo"
					id="letterNo" tabindex="5" onkeypress="return numbersonly(event)"></input></div>
				</td>
			</tr>

			<tr class="table">
				<td>
				<div align="left">Letter Date</div>
				</td>
				<td>
				<div align="left"><input type="text" name="letterDate"
					id="letterDate" maxlength="10" size="11" tabindex="6"
					onfocus="javascript:vDateType='3';"
					onkeypress="return calins(event,this);"
					onblur="return checkdt(this);" /> <img
					src="../../../../../images/calendr3.gif"
					onclick="showCalendarControl(document.forms[0].letterDate);"
					alt="Show Calendar"></img></div>
				</td>
			</tr>
			<!--  	<tr class="table">
					<td width="40%">
					<div align="left">Amount Released <font color="#ff2121">*</font></div>
					</td>
					<td>
					<input type="text" name="amountreleased" id="amountreleased"/>
					
					</td>
				</tr>   -->	
			<tr class="tdTitle">
				<td colspan="2" height="23">
				<div align="center">
				<table border="0">
					<tr>
						<td><input type="button" name="cmdadd" id="cmdadd" 
							value="ADD" onclick="load_grid('ADD_GRID')"
							style="display: block" /></td>
						<td><input type="button" name="cmdupdate" value="UPDATE"
							id="cmdupdate" onclick="up();" style="display: none" /></td>
						<!-- <td>
	                                       <input type="button" name="cmdupdate" value="UPDATE"
	                                              id="cmdupdate" onclick="load_grid('update_GRID')"
	                                              style="display:none" /></td> -->
						<td><input type="button" name="cmddelete" value="DELETE"
							id="cmddelete" onclick="delete_GRID()" disabled="disabled" /></td>
						<td><input type="button" name="cmdclear" value="CLEAR ALL"
							id="cmdclear" onclick="clearall()" /></td>
					</tr>

				</table>
				</div>
				</td>
			</tr>
		</table>
		</div>

		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="1"
			width="100%">

			<tr class="table">
				<th>Select</th>
				<th>A/c Head Code-Desc</th>
				<th>CR/DR</th>
				<th>Sub Ledger Type</th>
				<th>Sub Ledger Code</th>
				<th>Office</th>
				<th>Amount</th>
				<th>Particulars</th>
				<th>Letter No</th>
				<th>Letter Date</th>
			</tr>
			<tbody id="grid_body" class="table" align="left">
			</tbody>
		</table>
		</div>
		</td>
	</tr>
</table>
</div>
</div>
</div>
<br>
  <div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="submit" name="butSub"
			id="butSub" value="SUBMIT" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="CANCEL"
			onclick="clrForm();" /> &nbsp;&nbsp;&nbsp; <input type="button"
			name="butCan" id="butCan" value="EXIT"
			onclick="javascript:self.close();" /></div>
		</td>
	</tr>
</table>
</div>

</form>
</body>
</html>