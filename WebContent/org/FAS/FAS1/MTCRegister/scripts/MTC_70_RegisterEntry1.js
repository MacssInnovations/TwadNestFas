//         MTC_70_RegisterEntry1         //

var seq = 0;

function AjaxFunction() {
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}
var check_memo_list;
function viewDetails(path)
{
	//alert(path);
	var unitcode = document.getElementById("cmbAcc_UnitCode").value;
    var offid = document.getElementById("cmbOffice_code").value;
    var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	//var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillNo = document.getElementById("cboBillNo").value;

	check_memo_list= window.open("../../../../../org/FAS/FAS1/BillScrutiny/jsps/Bill_Scrutiny_list.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+txtCB_Year+"&cboCashBook_Month="+txtCB_Month+"&cboBillNo="+cboBillNo,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
 	     
	check_memo_list.moveTo(250,250);  
	check_memo_list.focus();
}

function checkMTC()
{
	var txtBillDate=document.getElementById("txtBillDate").value;
	var txtBillDate_one=txtBillDate.split("/");
	
	var txtMTCEntryDate=document.getElementById("txtMTCEntryDate").value;
	 var txtMTCEntryDate_two=txtMTCEntryDate.split("/");
	
	 if(txtBillDate_one[2]>txtMTCEntryDate_two[2])
	 {
		document.getElementById("txtMTCEntryDate").value="";
		alert("MTCEntry Date Should not be less than Bill Date");
		return false;
		
	 }
	 else if(txtBillDate_one[2]==txtMTCEntryDate_two[2])
	 {
		
   	 if(txtBillDate_one[1]>txtMTCEntryDate_two[1])
   	 {
   		document.getElementById("txtMTCEntryDate").value="";
		alert("MTCEntry Date Should not be less than Bill Date");
		return false;
   	 }
   	 else if(txtBillDate_one[1]==txtMTCEntryDate_two[1])
   	 {
   		 var apl;
   		 if(txtBillDate_one[0].length==2)
   		 {
   			apl=txtBillDate_one[0];
   		 }
   		 else
   		 {
   			apl="0"+txtBillDate_one[0];
   		 }
   		 if(apl>txtMTCEntryDate_two[0])
       	 {
	   		document.getElementById("txtMTCEntryDate").value="";
			alert("MTCEntry Date Should not be less than Bill Date");
			return false;
       	 } 
   	 }
	 }
	
	
}


function callemp(path)
{
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
		var url = path+ "/Bills_Token_Register_with_SP?command=getempname_off&txtEmpID_mas="+ txtEmpID_mas+"&cmbOffice_code="+cmbOffice_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

}

function getempname_re(baseResponse)
{

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	var empname = baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
	
	document.getElementById("cmbMas_SL_Code").length = 0;
	var len1 = baseResponse.getElementsByTagName("empname").length;
	for ( var i = 0; i < len1; i++) {
		var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
		var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empid;
		var txt = document.createTextNode(empname);
		op.appendChild(txt);
		se.appendChild(op);
	
	}
	}
	else
	{
		alert("Enter Relevant EmployeeId For This Office");
		document.getElementById("cmbMas_SL_Code").length = 0;
		document.getElementById("txtEmpID_mas").value="";
	}
}


function initialLoad(path) {
	//alert(path);
	var url = path + "/MTC_70_RegisterEntry1?command=getEmp";

	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function getBillNo(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	//var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if (txtCB_Year == "") {
		alert("Enter Cashbook Year in the Field")
		document.getElementById("txtCB_Year").focus();
	} else if (txtCB_Month == "s") {
		alert("Select Cashbook Month in the Field");
		document.getElementById("txtCB_Month").focus();
	}  else {
		var url = path
				+ "/MTC_70_RegisterEntry1?command=getBillNo&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month;
				
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function getBillDetails(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	//var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillNo = document.getElementById("cboBillNo").value;

	var url = path
			+ "/MTC_70_RegisterEntry1?command=getBillDetails&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month + "&cboBillNo="
			+ cboBillNo;
	// alert(url)
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function numbersonly1(e, t) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false
	}
}

function saveFunc(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillNo = document.getElementById("cboBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	var txtTotalSanctionAmount = document
			.getElementById("txtTotalSanctionAmount").value;
	var txtTotalDeductionAmount = document
			.getElementById("txtTotalDeductionAmount").value;
	var txtNetAmount = document.getElementById("txtNetAmount").value;
	var txtMTCEntryDate = document.getElementById("txtMTCEntryDate").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;


	var bill_approval_date=document.getElementById("bill_approval_date").value;
	var txtBillDate_one=bill_approval_date.split("/");
	
	var txtMTCEntryDate=document.getElementById("txtMTCEntryDate").value;
	 var txtMTCEntryDate_two=txtMTCEntryDate.split("/");
	
	 if(txtBillDate_one[2]>txtMTCEntryDate_two[2])
	 {
		document.getElementById("txtMTCEntryDate").value="";
		alert("MTCEntry Date Should not be less than Bill Approval Date");
		return false;
		
	 }
	 else if(txtBillDate_one[2]==txtMTCEntryDate_two[2])
	 {
		
   	 if(txtBillDate_one[1]>txtMTCEntryDate_two[1])
   	 {
   		document.getElementById("txtMTCEntryDate").value="";
		alert("MTCEntry Date Should not be less than Bill Approval Date");
		return false;
   	 }
   	 else if(txtBillDate_one[1]==txtMTCEntryDate_two[1])
   	 {
   		 var apl;
   		 if(txtBillDate_one[0].length==2)
   		 {
   			apl=txtBillDate_one[0];
   		 }
   		 else
   		 {
   			apl="0"+txtBillDate_one[0];
   		 }
   		 if(apl>txtMTCEntryDate_two[0])
       	 {
	   		document.getElementById("txtMTCEntryDate").value="";
			alert("MTCEntry Date Should not be less than Bill Approval Date");
			return false;
       	 } 
   	 }
	 }
	
	if (txtCB_Year == "") {
		alert("Enter Cashbook Year in the Field")
		document.getElementById("txtCB_Year").focus();
	} else if (txtCB_Month == "s") {
		alert("Select Cashbook Month in the Field")
		document.getElementById("txtCB_Month").focus();
	} else if (cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field")
		document.getElementById("cboBillMajorType").focus();
	} else if (cboBillNo == "s") {
		alert("Select Bill No in the Field")
		document.getElementById("cboBillNo").focus();
	} else if (txtBillDate == "") {
		alert("Enter Bill Date in the Field")
		document.getElementById("txtBillDate").focus();
	} else if (txtTotalSanctionAmount == "") {
		alert("Enter Total Sanction Amount in the Field")
		document.getElementById("txtTotalSanctionAmount").focus();
	} else if (txtTotalDeductionAmount == "") {
		alert("Enter Total Deduction Amount in the Field")
		document.getElementById("txtTotalDeductionAmount").focus();
	} else if (txtNetAmount == "") {
		alert("Enter Net Amount in the Field")
		document.getElementById("txtNetAmount").focus();
	} else if (txtMTCEntryDate == "") {
		alert("Enter MTC Entry Date in the Field")
		document.getElementById("txtMTCEntryDate").focus();
	} else if (txtEmpID_mas == "") {
		alert("Enter MTC Entry Done by in the Field")
		document.getElementById("txtEmpID_mas").focus();
	} else {
		var url = path
				+ "/MTC_70_RegisterEntry1?command=saveFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cboBillMajorType=" + cboBillMajorType + "&cboBillNo="
				+ cboBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
				+ "&txtTotalDeductionAmount=" + txtTotalDeductionAmount
				+ "&txtNetAmount=" + txtNetAmount + "&txtMTCEntryDate="
				+ txtMTCEntryDate + "&txtEmpID_mas=" + txtEmpID_mas
				+ "&mtxtRemarks=" + mtxtRemarks+"&ori_unit="+document.getElementById("ori_unit").value+"&ori_office="+document.getElementById("ori_office").value;

		// alert('ff'+url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function saveFunc1(baseResponse) {
	//alert("saveFunc1");

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");
		refresh();

	} else if (flag == "Exist") {
		alert("Records of given Bill No is Already Exist");
		refresh();
	} else {
		alert("Failed to Add values.");
		refresh();
	}

}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;

			if (command == "getBillNo") {
				LoadBillNo(baseResponse);
			} else if (command == "getEmp") {
				setEmp(baseResponse);
			} else if (command == "getBillDetails") {
				setBillDetails(baseResponse);
			} else if (command == "saveFunc") {
				saveFunc1(baseResponse);
			}
			 else if (command == "getempname_off") {
				 //alert("manipulate");
				getempname_re(baseResponse);
			}
			
		}
	}
}

function refresh_new()
{
	document.frm_MTC_70_RegisterEntry1.txtBillDate.value = "";
	document.frm_MTC_70_RegisterEntry1.txtTotalSanctionAmount.value = "";
	document.frm_MTC_70_RegisterEntry1.txtTotalDeductionAmount.value = "";
	document.frm_MTC_70_RegisterEntry1.txtNetAmount.value = "";
}

function LoadBillNo(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//document.frm_MTC_70_RegisterEntry1.cboBillNo.length = 1;
	var se = document.getElementById("cboBillNo");
	se.length=1;
	if (flag == "success") {
		var len55 = baseResponse.getElementsByTagName("BillNo").length;
		
		if (len55 > 0) {
			for ( var i = 0; i < len55; i++) {
				var BillNo = baseResponse.getElementsByTagName("BillNo")[i].firstChild.nodeValue;
				var op = document.createElement("OPTION");
				op.value = BillNo;
				var txt = document.createTextNode(BillNo);
				op.appendChild(txt);
				se.appendChild(op);

			}
		} else {
			alert("Record Does Not Exist");
		}
	} else if (flag == "failure") {
		alert("No Bill No is available for this month");
		document.frm_MTC_70_RegisterEntry1.txtBillDate.value = "";
		document.frm_MTC_70_RegisterEntry1.txtTotalSanctionAmount.value = "";
		document.frm_MTC_70_RegisterEntry1.txtTotalDeductionAmount.value = "";
		document.frm_MTC_70_RegisterEntry1.txtNetAmount.value = "";
		document.frm_MTC_70_RegisterEntry1.bill_approval_date.value = "";
		
		
	}
	else {
		alert("Failed to Load");
	}
}

function setEmp(baseResponse) {

	var len1 = baseResponse.getElementsByTagName("empName").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("empName")[i].firstChild.nodeValue;
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empName;
		var txt = document.createTextNode(empName);
		op.appendChild(txt);
		se.appendChild(op);

	}

	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
	document.frm_MTC_70_RegisterEntry1.txtEmpID_mas.value = empid;

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var se1 = document.getElementById("billMajorTypeCode");
	se1.length=1;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("billMajorTypeCode").length;
		for ( var ii = 0; ii < len4; ii++) {
			var billMajorTypeCode = baseResponse
					.getElementsByTagName("billMajorTypeCode")[ii].firstChild.nodeValue;
			var billMajorTypeDesc = baseResponse
					.getElementsByTagName("billMajorTypeDesc")[ii].firstChild.nodeValue;

			//var se = document.getElementById("cboBillMajorType");
			var op1 = document.createElement("OPTION");
			op1.value = billMajorTypeCode;
			var txt1 = document.createTextNode(billMajorTypeDesc);
			op1.appendChild(txt1);
			se1.appendChild(op1);
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}

}

function setBillDetails(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	// alert(flag);
	if (flag == "success") {
		var billDate = baseResponse.getElementsByTagName("billDate")[0].firstChild.nodeValue;
		var bill_app_date = baseResponse.getElementsByTagName("bill_app_date")[0].firstChild.nodeValue;
		var totalSanctionedAmount = baseResponse
				.getElementsByTagName("totalSanctionedAmount")[0].firstChild.nodeValue;
		var DeductedAmount = baseResponse
				.getElementsByTagName("DeductedAmount")[0].firstChild.nodeValue;
		var netAmount = baseResponse.getElementsByTagName("netAmount")[0].firstChild.nodeValue;

		document.frm_MTC_70_RegisterEntry1.txtBillDate.value = billDate;
		document.frm_MTC_70_RegisterEntry1.bill_approval_date.value = bill_app_date;
		
		document.frm_MTC_70_RegisterEntry1.txtTotalSanctionAmount.value = totalSanctionedAmount
				+ ".00";
		document.frm_MTC_70_RegisterEntry1.txtTotalDeductionAmount.value = DeductedAmount
				+ "0";
		document.frm_MTC_70_RegisterEntry1.txtNetAmount.value = netAmount + "0";
		
		var major_code = baseResponse.getElementsByTagName("major_code")[0].firstChild.nodeValue;
		var major_desc = baseResponse.getElementsByTagName("major_desc")[0].firstChild.nodeValue;
		document.getElementById("cboBillMajorType").options[document.getElementById("cboBillMajorType").selectedIndex].text=major_desc;
        document.getElementById("cboBillMajorType").options[document.getElementById("cboBillMajorType").selectedIndex].value=major_code;
      
        document.frm_MTC_70_RegisterEntry1.ori_unit.value = baseResponse.getElementsByTagName("ori_unit")[0].firstChild.nodeValue;
        document.frm_MTC_70_RegisterEntry1.ori_office.value = baseResponse.getElementsByTagName("ori_office")[0].firstChild.nodeValue;
        
       
		
	} else {
		alert("Failed to Load");
	}
}
function refresh() {

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month >= 10) {
		if (day >= 10) {
			var date = (day + "/" + month + "/" + year);
		} else {
			var date = ("0" + day + "/0" + month + "/" + year);
		}
	} else {
		if (day >= 10) {
			var date = (day + "/" + month + "/" + year);
		} else {
			var date = ("0" + day + "/0" + month + "/" + year);
		}
	}
	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.frm_MTC_70_RegisterEntry1.txtCB_Year.value = year;
	document.frm_MTC_70_RegisterEntry1.txtCB_Month.value = "s";
	document.frm_MTC_70_RegisterEntry1.cboBillNo.length = 1;
	document.frm_MTC_70_RegisterEntry1.txtBillDate.value = "";
	document.frm_MTC_70_RegisterEntry1.txtTotalSanctionAmount.value = "";
	document.frm_MTC_70_RegisterEntry1.txtTotalDeductionAmount.value = "";
	document.frm_MTC_70_RegisterEntry1.txtNetAmount.value = "";
	document.frm_MTC_70_RegisterEntry1.txtMTCEntryDate.value = date;
	document.frm_MTC_70_RegisterEntry1.mtxtRemarks.value = "";

}

function exitfun(path) {
	window.close();
}
function clr()
{
	document.frm_MTC_70_RegisterEntry1.txtBillDate.value = "";
	document.frm_MTC_70_RegisterEntry1.txtTotalSanctionAmount.value = "";
	document.frm_MTC_70_RegisterEntry1.txtTotalDeductionAmount.value = "";
	document.frm_MTC_70_RegisterEntry1.txtNetAmount.value = "";
	document.frm_MTC_70_RegisterEntry1.bill_approval_date.value = "";
	document.frm_MTC_70_RegisterEntry1.txtCB_Month.value = "s";
	var se = document.getElementById("cboBillNo");
	se.length=1;
	var se1 = document.getElementById("cboBillMajorType");
	se1.length=0;
	if(se1.length==0)
	{var se1 = document.getElementById("cboBillMajorType");
		var op=document.createElement("OPTION");
	op.value="";
	var txt1 = document.createTextNode("--Select--");
	op.appendChild(txt1);
	se1.appendChild(op);}
	
}
