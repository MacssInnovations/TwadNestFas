//	MTC70_Register_Updated_By_Treasury_Section1		//
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

function initialLoad(path) {
	//alert(path);

	//var url = path+ "/Bills_Token_Register_without_SP?command=getBillMajorType";
	var url = path+ "/BillTokenRegisterEntry_WithoutProceeding?command=getBillMajorType";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function checkpassdt()
{

	var txtmtcdate=document.getElementById("txtmtcdate").value;
	var txtBillDate_one=txtmtcdate.split("/");
	
	var txtChecked=document.getElementById("txtChecked_Passed_Date").value;
	 var txtChecked_two=txtChecked.split("/");
	
	 if(txtBillDate_one[2]>txtChecked_two[2])
	 {
		document.getElementById("txtChecked_Passed_Date").value="";
		alert("Checked & Passed on Date Should not be less than Bill Date");
		return false;
		
	 }
	 else if(txtBillDate_one[2]==txtChecked_two[2])
	 {
		
   	 if(txtBillDate_one[1]>txtChecked_two[1])
   	 {
   		document.getElementById("txtChecked_Passed_Date").value="";
		alert("Checked & Passed on Date Should not be less than MTC Date");
		return false;
   	 }
   	 else if(txtBillDate_one[1]==txtChecked_two[1])
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
   		 if(apl>txtChecked_two[0])
       	 {
   			document.getElementById("txtChecked_Passed_Date").value="";
   			alert("Checked & Passed on Date Should not be less than MTC Date");
   			return false;
       	 } 
   	 }
	 }
	 document.getElementById("txtMTCUpdatedDate").value="";
	 document.getElementById("SenttoPreAuditon").value="";
}
function checkmtc()
{
	
	var txtChecked=document.getElementById("txtChecked_Passed_Date").value;
	 var txtChecked_two=txtChecked.split("/");
	
	 var txtMTCUpdatedDate=document.getElementById("txtMTCUpdatedDate").value;
		var txtMTCUpdatedDate_one=txtMTCUpdatedDate.split("/");
	 
	 if(txtMTCUpdatedDate_one[2]<txtChecked_two[2])
	 {
		document.getElementById("txtMTCUpdatedDate").value="";
		alert("MTC Updated Date Should not be less than Checked & Passed on Date");
		return false;
		
	 }
	 else if(txtMTCUpdatedDate_one[2]==txtChecked_two[2])
	 {
		
   	 if(txtMTCUpdatedDate_one[1]<txtChecked_two[1])
   	 {
   		document.getElementById("txtMTCUpdatedDate").value="";
		alert("MTC Updated Date Should not be less than Checked & Passed on Date");
		return false;
   	 }
   	 else if(txtMTCUpdatedDate_one[1]==txtChecked_two[1])
   	 {
   		 var apl;
   		 if(txtMTCUpdatedDate_one[0].length==2)
   		 {
   			apl=txtMTCUpdatedDate_one[0];
   		 }
   		 else
   		 {
   			apl="0"+txtMTCUpdatedDate_one[0];
   		 }
   		 if(apl<txtChecked_two[0])
       	 {
   			document.getElementById("txtMTCUpdatedDate").value="";
   			alert("MTC Updated Date Should not be less than Checked & Passed on Date");
   			return false;
       	 } 
   	 }
	 }
	 document.getElementById("SenttoPreAuditon").value="";
}
function checkaudit()
{

	
	
	 var txtMTCUpdatedDate=document.getElementById("txtMTCUpdatedDate").value;
		var txtMTCUpdatedDate_one=txtMTCUpdatedDate.split("/");
		
		var SenttoPreAuditon=document.getElementById("SenttoPreAuditon").value;
		 var SenttoPreAuditontwo=SenttoPreAuditon.split("/");
	 
	 if(txtMTCUpdatedDate_one[2]>SenttoPreAuditontwo[2])
	 {
		document.getElementById("SenttoPreAuditon").value="";
		alert("Sent to Pre-Audit on Should not be less than MTC Updated Date");
		return false;
		
	 }
	 else if(txtMTCUpdatedDate_one[2]==SenttoPreAuditontwo[2])
	 {
		
   	 if(txtMTCUpdatedDate_one[1]>SenttoPreAuditontwo[1])
   	 {
   		document.getElementById("SenttoPreAuditon").value="";
		alert("Sent to Pre-Audit on Should not be less than MTC Updated Date ");
		return false;
   	 }
   	 else if(txtMTCUpdatedDate_one[1]==SenttoPreAuditontwo[1])
   	 {
   		 var apl;
   		 if(txtMTCUpdatedDate_one[0].length==2)
   		 {
   			apl=txtMTCUpdatedDate_one[0];
   		 }
   		 else
   		 {
   			apl="0"+txtMTCUpdatedDate_one[0];
   		 }
   		 if(apl>SenttoPreAuditontwo[0])
       	 {
   			document.getElementById("SenttoPreAuditon").value="";
   			alert("Sent to Pre-Audit on Should not be less than MTC Updated Date");
   			return false;
       	 } 
   	 }
	 }	
}
function firstLoad(baseResponse) {
	// alert("RKsbg");	
	document.getElementById("cmbMas_SL_Code").length = 1;
	var len1 = baseResponse.getElementsByTagName("empName").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("empName")[i].firstChild.nodeValue;
		// alert(empName);
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empName;
		var txt = document.createTextNode(empName);
		op.appendChild(txt);
		se.appendChild(op);

	}
	document.frm_MTC70Register_Updated_By_Treasury_Section.cmbMas_SL_Code.value = empName;
	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtEmpID_mas.value = empid;

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("billMajorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMajorTypeCode = baseResponse
					.getElementsByTagName("billMajorTypeCode")[i].firstChild.nodeValue;
			var billMajorTypeDesc = baseResponse
					.getElementsByTagName("billMajorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMajorType");
			var op = document.createElement("OPTION");
			op.value = billMajorTypeCode;
			var txt = document.createTextNode(billMajorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function getBillNo(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	
	if (txtCB_Year == "") {
		alert("Enter Cashbook Year in the Field")
		document.getElementById("txtCB_Year").focus();
	} else if (txtCB_Month == "s") {
		alert("Select Cashbook Month in the Field")
		document.getElementById("txtCB_Month").focus();
	} else {
		var url = path
				+ "/MTC_70_Treasury?command=getBillNo&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month;
			
		// alert(url)
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			// alert("manipulate-command:--->>>"+command);

			if (command == "getBillNo") {
				LoadBillNo(baseResponse);
			} else if (command == "saveFunc") {
				saveFunc1(baseResponse);
			} else if (command == "getBillDetails") {
				setBillDetails(baseResponse);
			} else if (command == "getBillMajorType") {
				firstLoad(baseResponse);
			}
		}
	}
}

function LoadBillNo(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		document.frm_MTC70Register_Updated_By_Treasury_Section.cboBillNo.length = 1;

		var len55 = baseResponse.getElementsByTagName("BillNo").length;
		if (len55 > 0) {
			for ( var i = 0; i < len55; i++) {
				var BillNo = baseResponse.getElementsByTagName("BillNo")[i].firstChild.nodeValue;
				var se = document.getElementById("cboBillNo");
				var op = document.createElement("OPTION");
				op.value = BillNo;
				var txt = document.createTextNode(BillNo);
				op.appendChild(txt);
				se.appendChild(op);

			}
		} else {
			alert("Record Does Not Exist");
		}
	} else {
		alert("Failed to Load");
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
	if (txtCB_Year == "") {
		alert("Enter Cashbook Year in the Field")
		document.getElementById("txtCB_Year").focus();
	} else if (txtCB_Month == "s") {
		alert("Select Cashbook Month in the Field")
		document.getElementById("txtCB_Month").focus();
	}  else {
		var url = path
				+ "/MTC_70_Treasury?command=getBillDetails&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
			    + "&cboBillNo="
				+ cboBillNo;
		//alert(url)
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}
function setBillDetails(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	// alert(flag);
	if (flag == "success") {
		var billDate = baseResponse.getElementsByTagName("billDate")[0].firstChild.nodeValue;
		var mtc_orginal_date = baseResponse.getElementsByTagName("mtc_orginal_date")[0].firstChild.nodeValue;
		
		var totalSanctionedAmount = baseResponse
				.getElementsByTagName("totalSanctionedAmount")[0].firstChild.nodeValue;
		var DeductedAmount = baseResponse
				.getElementsByTagName("DeductedAmount")[0].firstChild.nodeValue;
		var netAmount = baseResponse.getElementsByTagName("netAmount")[0].firstChild.nodeValue;

		 var AccHdCode = baseResponse.getElementsByTagName("AccHdCode")[0].firstChild.nodeValue;
		var PayeeType = baseResponse.getElementsByTagName("PayeeType")[0].firstChild.nodeValue;
		var PayeeCode = baseResponse.getElementsByTagName("PayeeCode")[0].firstChild.nodeValue;
//alert();
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtBillDate.value = billDate;
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtmtcdate.value = mtc_orginal_date;
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtTotalSanctionAmount.value = totalSanctionedAmount
				+ ".00";
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtTotalDeductionAmount.value = DeductedAmount
				+ "0";
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtNetAmount.value = netAmount
				+ "0";

		document.frm_MTC70Register_Updated_By_Treasury_Section.txtPassOrderAmount.value = netAmount
				+ "0";
		
	//	document.getElementById("cboBillMajorType").length=0;
		var major_code = baseResponse.getElementsByTagName("major_code")[0].firstChild.nodeValue;
		var major_desc = baseResponse.getElementsByTagName("major_desc")[0].firstChild.nodeValue;
		document.getElementById("cboBillMajorType").options[document.getElementById("cboBillMajorType").selectedIndex].text=major_desc;
        document.getElementById("cboBillMajorType").options[document.getElementById("cboBillMajorType").selectedIndex].value=major_code;
    // alert( baseResponse.getElementsByTagName("Accounting_Unit_Office_Id")[0].firstChild.nodeValue);

		document.frm_MTC70Register_Updated_By_Treasury_Section.txtAcc_HeadCode.value = AccHdCode;
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtPayeeType.value = PayeeType;
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtPayeeCode.value = PayeeCode;
		document.frm_MTC70Register_Updated_By_Treasury_Section.ori_unit.value =  baseResponse.getElementsByTagName("Accounting_Unit_Id")[0].firstChild.nodeValue;
		document.frm_MTC70Register_Updated_By_Treasury_Section.ori_office.value = baseResponse.getElementsByTagName("Accounting_Unit_Office_Id")[0].firstChild.nodeValue;
	} else {
		alert("Failed to Load");
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
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtPayeeType = document.getElementById("txtPayeeType").value;
	var txtPayeeCode = document.getElementById("txtPayeeCode").value;
	var txtPassOrderAmount = document.getElementById("txtPassOrderAmount").value;
	var txtChecked_Passed_Date = document
			.getElementById("txtChecked_Passed_Date").value;
	var EmpID_mas = document.getElementById("txtEmpID_mas").value;
	var txtMTCUpdatedDate = document.getElementById("txtMTCUpdatedDate").value;
	var SenttoPreAuditon = document.getElementById("SenttoPreAuditon").value;
	var Remarks = document.getElementById("mtxtRemarks").value;
	var ori_unit = document.getElementById("ori_unit").value;
	var ori_office = document.getElementById("ori_office").value;
	
	

	if (txtCB_Year == "") {
		alert("Enter Cashbook Year in the Field");
		document.getElementById("txtCB_Year").focus();
	} else if (txtCB_Month == "s") {
		alert("Select Cashbook Month in the Field");
		document.getElementById("txtCB_Month").focus();
	} else if (cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field");
		document.getElementById("cboBillMajorType").focus();
	} else if (cboBillNo == "s") {
		alert("Select Bill No in the Field");
		document.getElementById("cboBillNo").focus();
	} else if (txtBillDate == "") {
		alert("Enter Bill Date in the Field");
		document.getElementById("txtBillDate").focus();
	} else if (txtTotalSanctionAmount == "") {
		alert("Enter Total Sanction Amount in the Field");
		document.getElementById("txtTotalSanctionAmount").focus();
	} else if (txtTotalDeductionAmount == "") {
		alert("Enter Total Deduction Amount in the Field");
		document.getElementById("txtTotalDeductionAmount").focus();
	} else if (txtNetAmount == "") {
		alert("Enter Net Amount in the Field");
		document.getElementById("txtNetAmount").focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Enter AccHead Code in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtAcc_HeadCode
				.focus();
	} else if (txtPayeeType == "") {
		alert("Enter Payee Type in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtPayeeType
				.focus();
	} else if (txtPayeeCode == "") {
		alert("Enter Payee Code in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtPayeeCode
				.focus();
	} else if (document.getElementById("txtPassOrderAmount").value == "") {
		alert("Enter Pass Order Amount in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtPassOrderAmount
				.focus();
	} else if (document.getElementById("txtChecked_Passed_Date").value == "") {
		alert("Enter Checked Passed Date in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtChecked_Passed_Date
				.focus();
	} else if (EmpID_mas == "") {
		alert("Enter Received By in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtEmpID_mas
				.focus();
	} else if (txtMTCUpdatedDate == "") {
		alert("Enter MTC Updated Date in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtMTCUpdatedDate
				.focus();
	} else if (document.getElementById("SenttoPreAuditon").value == "") {
		alert("Enter Sent to Pre-Auditon in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.SenttoPreAuditon
				.focus();
	}

	/*
	"else if (eDate > rDate) {
		alert("ReceivedOn must be greater than MTCEntryDate");
		return 0;
	} else if (rDate > sDate) {
		alert("Updated On must be greater than ReceivedOn");
		return 0;

	} else if (eDate > sDate) {
		alert("Updated On must be greater than MTCEntryDate");
		return 0;
	} else if (sDate > spDate) {
		alert("SenttoPre-Audit on must be greater than Updated On");
		return 0;
	}
	 */

	else {
		var url = path
				+ "/MTC_70_Treasury?command=saveFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cboBillMajorType=" + cboBillMajorType + "&cboBillNo="
				+ cboBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
				+ "&txtTotalDeductionAmount=" + txtTotalDeductionAmount
				+ "&txtNetAmount=" + txtNetAmount + "&txtAcc_HeadCode="
				+ txtAcc_HeadCode + "&txtPayeeType=" + txtPayeeType
				+ "&txtPayeeCode=" + txtPayeeCode + "&txtPassOrderAmount="
				+ txtPassOrderAmount + "&txtChecked_Passed_Date="
				+ txtChecked_Passed_Date + "&EmpID_mas=" + EmpID_mas
				+ "&txtMTCUpdatedDate=" + txtMTCUpdatedDate
				+ "&SenttoPreAuditon=" + SenttoPreAuditon + "&Remarks="
				+ Remarks+"&ori_unit="+ori_unit+"&ori_office="+ori_office;

		alert(url);

		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}
function saveFunc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Updated Successfully");
		refresh();
	} else {
		alert("Record Updation Failed");
		refresh();
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
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtCB_Year.value = year;
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtCB_Month.value = "s";
	document.frm_MTC70Register_Updated_By_Treasury_Section.cboBillMajorType.value = "s";
	document.frm_MTC70Register_Updated_By_Treasury_Section.cboBillNo.length = 1;
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtBillDate.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtTotalSanctionAmount.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtTotalDeductionAmount.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtNetAmount.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtAcc_HeadCode.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtPayeeType.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtPayeeCode.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtPassOrderAmount.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtChecked_Passed_Date.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtMTCUpdatedDate.value = date;
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtEmpID_mas.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.SenttoPreAuditon.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.mtxtRemarks.value = "";
}

function exitfun(path) {
	window.close();
}