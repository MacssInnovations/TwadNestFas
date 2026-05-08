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

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			// alert("manipulate-command:--->>>"+command);

			if (command == "getBillMajorType") {
				// alert("manipulate");
				firstLoad(baseResponse);
			} else if (command == "getBillMinorType") {
				// alert("manipulate");
				getBillMinorType1(baseResponse);
			} else if (command == "getBillsubType") {
				// getEstimateSanctionDate
				// alert("manipulate");
				getBillsubType1(baseResponse);
			} else if (command == "calculateBudget") {
				// alert("manipulate");
				calculateBudget1(baseResponse);
			} else if (command == "getOffice") {
				// alert("manipulate");
				getOffice1(baseResponse);
			} else if (command == "saveFunc") {
				// alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "Edit") {
				// alert("manipulate");
				Edit1(baseResponse);
			} else if (command == "deleted") {
				// alert("manipulate");
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "ClearAll") {
				ClearAll1(baseResponse);
			} else if (command == "IVno") {
				IVno1(baseResponse);
			}else if (command == "InvoiceDetails") {
				InvoiceDetails1(baseResponse);
			}

		}
	}
}

function initialLoad(path) {
	// alert(path);

	var url = path
			+ "/BillTokenRegisterEntry_WithoutProceeding?command=getBillMajorType";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function getOffice(path) {
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var url = path
			+ "/BillTokenRegisterEntry_WithoutProceeding?command=getOffice&txtEmpID_mas="
			+ txtEmpID_mas;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function getOffice1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.value = OfficeID;
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function firstLoad(baseResponse) {
	// alert("RKsbg");
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

	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas.value = empid;

	var BillNo = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = BillNo;

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
		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.value = OfficeID;
		}

	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function calculateBudget(path) {
	// alert(path);
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var txtaccountheadcode = document.getElementById("txtAcc_HeadCode").value;

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}

	if (txtaccountheadcode == "") {
		alert("Enter Account Head Code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode
				.focus();
	} else {
		var url = path
				+ "/BillTokenRegisterEntry_WithoutProceeding?command=calculateBudget&cboAcc_UnitCode="
				+ cboAcc_UnitCode + "&cboOffice_code=" + cboOffice_code
				+ "&year=" + year + "&year1=" + year1 + "&txtaccountheadcode="
				+ txtaccountheadcode;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function calculateBudget1(baseResponse) {
	// alert("RKsbg");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision.value = BudgetProvided;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flag == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}
}

function getBillMinorType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else {
		var url = path
				+ "/BillTokenRegisterEntry_WithoutProceeding?command=getBillMinorType&cboBillMajorType="
				+ cboBillMajorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}

}

function getBillMinorType1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}

		var len5 = baseResponse.getElementsByTagName("EstimateSanctionNo").length;
		// alert(len5);
		for ( var i = 0; i < len5; i++) {
			var EstimateSanctionNo = baseResponse
					.getElementsByTagName("EstimateSanctionNo")[i].firstChild.nodeValue;
			// alert(EstimateSanctionNo);
			var se = document.getElementById("cboEstimateSanctionNumber");
			var op = document.createElement("OPTION");
			op.value = EstimateSanctionNo;
			var txt = document.createTextNode(EstimateSanctionNo);
			op.appendChild(txt);
			se.appendChild(op);
		}

	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}

function getBillsubType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	// alert(cboBillMajorType);
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType
				.focus();
	} else {
		var url = path
				+ "/BillTokenRegisterEntry_WithoutProceeding?command=getBillsubType&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}

}

function getBillsubType1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}
function IVno(path) {
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[0].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[0].value;
	} else {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[1].value;
	}

	if (rdoInvoiceEntryOption == "Entry") {
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.disabled = true;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.disabled = false;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.value = "";
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.value = "";
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.value = "";
	} else {
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.disabled = true;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.disabled = false;

		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var today = new Date();
		var day = today.getDate();
		var month = today.getMonth();
		month = month + 1;
		var year = today.getYear();
		if (year < 1900)
			year += 1900;

		var url = path
				+ "/BillTokenRegisterEntry_WithoutProceeding?command=IVno&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&month=" + month + "&year=" + year;

		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

	}
}

function IVno1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.length = 1;	
	if (flag == "success1") {
		var len4 = baseResponse.getElementsByTagName("InvoiceNo").length;
		for ( var i = 0; i < len4; i++) {
			var InvoiceNo = baseResponse.getElementsByTagName("InvoiceNo")[i].firstChild.nodeValue;
			var se = document.getElementById("txtIfSelectfromList");
			var op = document.createElement("OPTION");
			op.value = InvoiceNo;
			var txt = document.createTextNode(InvoiceNo);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}

function InvoiceDetails(path)
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtIfSelectfromList = document.getElementById("txtIfSelectfromList").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (txtIfSelectfromList == "" || txtIfSelectfromList == "s") {
		alert("Select Invoice No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.focus();
} else 
{
	var url = path
			+ "/BillTokenRegisterEntry_WithoutProceeding?command=InvoiceDetails&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&month=" + month + "&year=" + year + "&txtIfSelectfromList=" + txtIfSelectfromList;

	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
}

function InvoiceDetails1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag == "success1") {
		var InvoiceDate = baseResponse.getElementsByTagName("InvoiceDate")[0].firstChild.nodeValue;
		var InvoiceAmount = baseResponse.getElementsByTagName("InvoiceAmount")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.value = InvoiceDate;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.value = InvoiceAmount;
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}

function saveFunc(path) {

	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	var txtInvoiceReceivedDate = document
			.getElementById("txtInvoiceReceivedDate").value;
	var txtNoofInvoices = document.getElementById("txtNoofInvoices").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtPayeeType = document.getElementById("txtPayeeType").value;
	var txtPayeeCode = document.getElementById("txtPayeeCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cboOffice = document.getElementById("cboOffice").value;
	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[0].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[1].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[1].value;
	} else {
		rdoInvoiceEntryOption = "";
	}
	if (rdoInvoiceEntryOption == "Entry") {
		var txtInvoiceNo = document.getElementById("txtInvoiceNo").value;
	} else
		(rdoInvoiceEntryOption == "List")
	{
		var txtIfSelectfromList = document
				.getElementById("txtIfSelectfromList").value;
	}
	var txtInvoiceDate = document.getElementById("txtInvoiceDate").value;
	var txtInvoiceAmount = document.getElementById("txtInvoiceAmount").value;
	var mtxtParticulars1 = document.getElementById("mtxtParticulars1").value;
	var BalanceAmount = (parseInt(txtBudgetProvision) - parseInt(txtBudgetSpent));
	if (cboBillMajorType == "" || cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else if (cboBillMinorType == "" || cboBillMinorType == "s") {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType
				.focus();
	} else if (cboBillSubType == "" || cboBillSubType == "s") {
		alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType
				.focus();
	} else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.focus();
	} else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate
				.focus();
	} else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
		alert("Enter Invoice Received Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
				.focus();
	} else if (document.getElementById("txtNoofInvoices").value == "") {
		alert("Enter No of Invoices in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount
				.focus();
	} else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode
				.focus();
	} else if (document.getElementById("txtPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType
				.focus();
	} else if (document.getElementById("txtPayeeCode").value == "") {
		alert("Enter Payee Code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode
				.focus();
	} else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas
				.focus();
	} else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.focus();
	} else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate
				.focus();
	} else if (rdoInvoiceEntryOption == "") {
		alert("Select Invoice Entry Option in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption
				.focus();
	} else if (rdoInvoiceEntryOption == "Entry" && document.getElementById("txtInvoiceNo").value == "") {		
			alert("Enter Invoice No in the Field");
			document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.focus();		
	} else if (rdoInvoiceEntryOption == "List" && (txtIfSelectfromList == "" || txtIfSelectfromList == "s")) {
			alert("Select Invoice No in the Field");
			document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.focus();
	} else if (document.getElementById("txtInvoiceDate").value == "") {
		alert("Enter Invoice Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.focus();
	} else if (document.getElementById("txtInvoiceAmount").value == "") {
		alert("Enter Invoice Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.focus();
	} else if (txtTotalBillAmount > BalanceAmount) {
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount.focus();
	} else {
		if (rdoInvoiceEntryOption == "Entry") {
			var url = path
					+ "/BillTokenRegisterEntry_WithoutProceeding?command=saveFunc&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&year=" + year + "&month=" + month
					+ "&cboBillMajorType=" + cboBillMajorType
					+ "&cboBillMinorType=" + cboBillMinorType
					+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
					+ txtBillNo + "&txtBillDate=" + txtBillDate
					+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
					+ "&txtNoofInvoices=" + txtNoofInvoices
					+ "&txtTotalBillAmount=" + txtTotalBillAmount
					+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
					+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
					+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice="
					+ cboOffice + "&txtBudgetProvision=" + txtBudgetProvision
					+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo="
					+ txtRefNo + "&txtRefDate=" + txtRefDate + "&mtxtRemarks="
					+ mtxtRemarks + "&rdoInvoiceEntryOption="
					+ rdoInvoiceEntryOption + "&txtInvoiceNo=" + txtInvoiceNo
					+ "&txtInvoiceDate=" + txtInvoiceDate
					+ "&txtInvoiceAmount=" + txtInvoiceAmount
					+ "&mtxtParticulars1=" + mtxtParticulars1;

		//	alert(url);
		} else {
			var url = path
					+ "/BillTokenRegisterEntry_WithoutProceeding?command=saveFunc&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&year=" + year + "&month=" + month
					+ "&cboBillMajorType=" + cboBillMajorType
					+ "&cboBillMinorType=" + cboBillMinorType
					+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
					+ txtBillNo + "&txtBillDate=" + txtBillDate
					+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
					+ "&txtNoofInvoices=" + txtNoofInvoices
					+ "&txtTotalBillAmount=" + txtTotalBillAmount
					+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
					+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
					+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice="
					+ cboOffice + "&txtBudgetProvision=" + txtBudgetProvision
					+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo="
					+ txtRefNo + "&txtRefDate=" + txtRefDate + "&mtxtRemarks="
					+ mtxtRemarks + "&rdoInvoiceEntryOption="
					+ rdoInvoiceEntryOption + "&txtIfSelectfromList="
					+ txtIfSelectfromList + "&txtInvoiceDate=" + txtInvoiceDate
					+ "&txtInvoiceAmount=" + txtInvoiceAmount
					+ "&mtxtParticulars1=" + mtxtParticulars1;

		//	alert(url);
		}
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

	var BillNo1 = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
	var BillNo = parseInt(BillNo1);
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = BillNo + 1;

	if (flag == "success") {
		alert("Record Updated Successfully");
		refresh();
	} else {
		alert("Record Updation Failed");
	}
}

function forList(path) {
	// alert(path);
	winemp = window.open("BillTokenRegisterEntry_WithoutProceeding_List.jsp",
			"list",
			"status=1,height=550,width=1200,resizable=YES, scrollbars=yes");
	winemp.moveTo(30, 150);
	winemp.focus();
}

function ParentDrawing(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13,
		v14, v15, v16, v17, v18, v19, v20, v21) {
	// alert(v3);
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}

	document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbAcc_UnitCode.value = v1;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbOffice_code.value = v2;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType.value = v3;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = v6;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate.value = v7;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate.value = v8;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices.value = v9;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount.value = v10;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.value = v11;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType.value = v12;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode.value = v13;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas.value = v14;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.value = v15;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate.value = v16;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtRemarks.value = v17;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.value = v18;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.value = v19;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.value = v20;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtParticulars1.value = v21;

	document.frm_BillTokenRegisterEntry_WithoutProceeding.onsubmit.disabled = true;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.ondelete.disabled = false;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.onupdate.disabled = false;

	var url = "../../../../../BillTokenRegisterEntry_WithoutProceeding?command=Edit&txtBillNo="
			+ v6
			+ "&txtEmpID_mas="
			+ v14
			+ "&cmbAcc_UnitCode="
			+ v1
			+ "&cmbOffice_code="
			+ v2
			+ "&year="
			+ year
			+ "&year1="
			+ year1
			+ "&txtAcc_HeadCode=" + v11;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);

}

function Edit1(baseResponse) {
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.length = "1";

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		var len = baseResponse.getElementsByTagName("BillProcessingDoneBy").length;

		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.value = billMinorTypeCode;
		}
		var len5 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		// alert(len5);
		for ( var i = 0; i < len5; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.value = billSubTypeCode;
		}

		var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.length = "1";
		for ( var i = 0; i < len; i++) {
			var BillProcessingDoneBy = baseResponse
					.getElementsByTagName("BillProcessingDoneBy")[0].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(BillProcessingDoneBy);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.value = empid;
		}
	}

	var flagg = baseResponse.getElementsByTagName("flagg")[0].firstChild.nodeValue;
	if (flagg == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision.value = BudgetProvided;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flagg == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}

	var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag2 == "success") {

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.value = OfficeID;
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function deleteeee(path) {
	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path
				+ "/BillTokenRegisterEntry_WithoutProceeding?command=deleted&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtBillNo=" + txtBillNo;
		alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function deleteRow(baseResponse) {
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
		var txtBillNo = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = txtBillNo;
	} else {
		alert("Failed to Generate Check List Code.");
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/*
		 * var ApportCode =
		 * baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue; var
		 * tbody = document.getElementById("Existing"); var r =
		 * document.getElementById(ApportCode); var ri = r.rowIndex;
		 * tbody.deleteRow(ri);
		 */
		alert("Deleted Successfully");
		refresh();
	} else {
		alert("Unable to Delete");
	}
}

function update(path) {
	// alert("update");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	var txtInvoiceReceivedDate = document
			.getElementById("txtInvoiceReceivedDate").value;
	var txtNoofInvoices = document.getElementById("txtNoofInvoices").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtPayeeType = document.getElementById("txtPayeeType").value;
	var txtPayeeCode = document.getElementById("txtPayeeCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cboOffice = document.getElementById("cboOffice").value;
	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[0].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[1].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[1].value;
	} else {
		rdoInvoiceEntryOption = "";
	}
	if (rdoInvoiceEntryOption == "Entry") {
		var txtInvoiceNo = document.getElementById("txtInvoiceNo").value;
	} else
		(rdoInvoiceEntryOption == "List")
	{
		var txtIfSelectfromList = document
				.getElementById("txtIfSelectfromList").value;
	}
	var txtInvoiceDate = document.getElementById("txtInvoiceDate").value;
	var txtInvoiceAmount = document.getElementById("txtInvoiceAmount").value;
	var mtxtParticulars1 = document.getElementById("mtxtParticulars1").value;
	var BalanceAmount = (parseInt(txtBudgetProvision) - parseInt(txtBudgetSpent));
	if (cboBillMajorType == "" || cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else if (cboBillMinorType == "" || cboBillMinorType == "s") {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType
				.focus();
	} else if (cboBillSubType == "" || cboBillSubType == "s") {
		alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType
				.focus();
	} else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.focus();
	} else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate
				.focus();
	} else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
		alert("Enter Invoice Received Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
				.focus();
	} else if (document.getElementById("txtNoofInvoices").value == "") {
		alert("Enter No of Invoices in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount
				.focus();
	} else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode
				.focus();
	} else if (document.getElementById("txtPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType
				.focus();
	} else if (document.getElementById("txtPayeeCode").value == "") {
		alert("Enter Payee Code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode
				.focus();
	} else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas
				.focus();
	} else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.focus();
	} else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate
				.focus();
	} else if (rdoInvoiceEntryOption == "") {
		alert("Select Invoice Entry Option in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption
				.focus();
	} else if (rdoInvoiceEntryOption == "Entry" && document.getElementById("txtInvoiceNo").value == "") {		
			alert("Enter Invoice No in the Field");
			document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.focus();		
	} else if (rdoInvoiceEntryOption == "List" && (txtIfSelectfromList == "" || txtIfSelectfromList == "s")) {
			alert("Select Invoice No in the Field");
			document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.focus();
	} else if (document.getElementById("txtInvoiceDate").value == "") {
		alert("Enter Invoice Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.focus();
	} else if (document.getElementById("txtInvoiceAmount").value == "") {
		alert("Enter Invoice Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.focus();
	} else if (txtTotalBillAmount > BalanceAmount) {
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount.focus();
	} else {
		if (rdoInvoiceEntryOption == "Entry") {
			var url = path
					+ "/BillTokenRegisterEntry_WithoutProceeding?command=update&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&year=" + year + "&month=" + month
					+ "&cboBillMajorType=" + cboBillMajorType
					+ "&cboBillMinorType=" + cboBillMinorType
					+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
					+ txtBillNo + "&txtBillDate=" + txtBillDate
					+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
					+ "&txtNoofInvoices=" + txtNoofInvoices
					+ "&txtTotalBillAmount=" + txtTotalBillAmount
					+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
					+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
					+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice="
					+ cboOffice + "&txtBudgetProvision=" + txtBudgetProvision
					+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo="
					+ txtRefNo + "&txtRefDate=" + txtRefDate + "&mtxtRemarks="
					+ mtxtRemarks + "&rdoInvoiceEntryOption="
					+ rdoInvoiceEntryOption + "&txtInvoiceNo=" + txtInvoiceNo
					+ "&txtInvoiceDate=" + txtInvoiceDate
					+ "&txtInvoiceAmount=" + txtInvoiceAmount
					+ "&mtxtParticulars1=" + mtxtParticulars1;

		//	alert(url);
		} else {
			var url = path
					+ "/BillTokenRegisterEntry_WithoutProceeding?command=update&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&year=" + year + "&month=" + month
					+ "&cboBillMajorType=" + cboBillMajorType
					+ "&cboBillMinorType=" + cboBillMinorType
					+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
					+ txtBillNo + "&txtBillDate=" + txtBillDate
					+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
					+ "&txtNoofInvoices=" + txtNoofInvoices
					+ "&txtTotalBillAmount=" + txtTotalBillAmount
					+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
					+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
					+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice="
					+ cboOffice + "&txtBudgetProvision=" + txtBudgetProvision
					+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo="
					+ txtRefNo + "&txtRefDate=" + txtRefDate + "&mtxtRemarks="
					+ mtxtRemarks + "&rdoInvoiceEntryOption="
					+ rdoInvoiceEntryOption + "&txtIfSelectfromList="
					+ txtIfSelectfromList + "&txtInvoiceDate=" + txtInvoiceDate
					+ "&txtInvoiceAmount=" + txtInvoiceAmount
					+ "&mtxtParticulars1=" + mtxtParticulars1;

		//	alert(url);
		}
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function updateRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
		var BillNo = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = BillNo;
	} else {
		alert("Fail to Generate Bill No Automatically");
	}
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
	} else {
		alert("Failed to update values");
	}
}

function refresh() {
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType.value = "s";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadDesc.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtRemarks.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtParticulars1.value = "";
}

function ClearAll(path) {
	var url = path
			+ "/BillTokenRegisterEntry_WithoutProceeding?command=ClearAll";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function ClearAll1(baseResponse) {
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
		var BillNo = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = BillNo;
	} else {
		alert("Fail to Generate Bill No Automatically");
	}
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType.value = "s";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadDesc.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtRemarks.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtParticulars1.value = "";
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

function exitfun(path) {
	window.close();
}