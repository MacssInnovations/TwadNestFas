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
				//getEstimateSanctionDate
				// alert("manipulate");
				getBillsubType1(baseResponse);
			} else if (command == "calculateBudget") {
				// alert("manipulate");
				calculateBudget1(baseResponse);
			} else if (command == "getEstimateSanctionDate") {
				// alert("manipulate");
				getEstimateSanctionDate1(baseResponse);
			} else if (command == "saveFunc") {
				// alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "PONo") {
				// alert("manipulate");
				forPassOrderNo1(baseResponse);
			} else if (command == "PODate") {
				// alert("manipulate");
				forPassOrderDate1(baseResponse);
			}
		}
	}
}

function initialLoad(path) {
	// alert(path);

	var url = path
			+ "/SanctionProceedingSinglePayeeFirm?command=getBillMajorType";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function getEstimateSanctionDate(path) {
	// alert(path);

	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;

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

	var cboEstimateSanctionNumber = document
			.getElementById("cboEstimateSanctionNumber").value;

	var url = path
			+ "/SanctionProceedingSinglePayeeFirm?command=getEstimateSanctionDate&cboAcc_UnitCode="
			+ cboAcc_UnitCode + "&cboOffice_code=" + cboOffice_code + "&year="
			+ year + "&year1=" + year1 + "&cboEstimateSanctionNumber="
			+ cboEstimateSanctionNumber;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function getEstimateSanctionDate1(baseResponse) {
	// alert("RKsbg");
	var EstimateSanctionDate = baseResponse
			.getElementsByTagName("EstimateSanctionDate")[0].firstChild.nodeValue;
	// alert(EstimateSanctionDate);
	document.frm_SanctionProceedingSinglePayeeFirm.txtEstimateSanctionDate.value = EstimateSanctionDate;

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
	// alert(MTCRegisterNO);
	document.frm_SanctionProceedingSinglePayeeFirm.txtEmpID_mas.value = empid;

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

		var len5 = baseResponse.getElementsByTagName("designationID").length;
		for ( var i = 0; i < len5; i++) {
			var designationID = baseResponse
					.getElementsByTagName("designationID")[i].firstChild.nodeValue;
			var designation = baseResponse.getElementsByTagName("designation")[i].firstChild.nodeValue;

			var se = document.getElementById("cboSanctioningAuthority");
			var op = document.createElement("OPTION");
			op.value = designationID;
			var txt = document.createTextNode(designation);
			op.appendChild(txt);
			se.appendChild(op);
		}

	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function getBillMinorType(path) {
	// alert(path);

	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;

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

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	// alert(cboBillMajorType);
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.cboBillMajorType.focus();
	} else {
		var url = path
				+ "/SanctionProceedingSinglePayeeFirm?command=getBillMinorType&cboBillMajorType="
				+ cboBillMajorType + "&cboAcc_UnitCode=" + cboAcc_UnitCode
				+ "&cboOffice_code=" + cboOffice_code + "&year=" + year
				+ "&year1=" + year1;
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
	document.frm_SanctionProceedingSinglePayeeFirm.cboBillMinorType.length = 1;
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
		document.frm_SanctionProceedingSinglePayeeFirm.cboBillMajorType.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.cboBillMinorType.focus();
	} else {
		var url = path
				+ "/SanctionProceedingSinglePayeeFirm?command=getBillsubType&cboBillMajorType="
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
	document.frm_SanctionProceedingSinglePayeeFirm.cboBillSubType.length = 1;
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
		document.frm_SanctionProceedingSinglePayeeFirm.txtAcc_HeadCode
				.focus();
	} else {
		var url = path
				+ "/SanctionProceedingSinglePayeeFirm?command=calculateBudget&cboAcc_UnitCode="
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

	var txtTotalSanctionedAmount = document
			.getElementById("txtTotalSanctionedAmount").value;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var BudgetProvided1 = baseResponse.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent1 = baseResponse.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;
		
		BudgetProvided = parseInt(BudgetProvided1);
		BudgetSoFarSpent = parseInt(BudgetSoFarSpent1);
		
		var balanceAmount = BudgetProvided - BudgetSoFarSpent;	

		document.frm_SanctionProceedingSinglePayeeFirm.txtBudgetProvided.value = BudgetProvided1;
		document.frm_SanctionProceedingSinglePayeeFirm.txtBudgetSpent.value = BudgetSoFarSpent1;
		document.frm_SanctionProceedingSinglePayeeFirm.txtBalanceAmount.value = balanceAmount;

		//var balanceAmount = baseResponse.getElementsByTagName("balanceAmount")[0].firstChild.nodeValue;
		if (txtTotalSanctionedAmount > balanceAmount) {
			alert("Total Sanctioned Amount is greater than Balance Amount in the Current Year");
		}
	} else if (flag == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Tally Sanctioned Amount with Budget Alloted for Current Year");
	}
}

function saveFunc(path) {

	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var txtSanctionProceedingDate = document
			.getElementById("txtSanctionProceedingDate").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var cboPayeeType = document.getElementById("cboPayeeType").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var cboSanctioningAuthority = document
			.getElementById("cboSanctioningAuthority").value;
	var cmbSL_Code = document.getElementById("txtEmpID_mas").value;
	var cboEstimateSanctionNumber = document
			.getElementById("cboEstimateSanctionNumber").value;
	var txtEstimateSanctionDate = document
			.getElementById("txtEstimateSanctionDate").value;
	var txtSanctionProceedingDate = document
			.getElementById("txtSanctionProceedingDate").value;
	var txtTotalSanctionedAmount = document
			.getElementById("txtTotalSanctionedAmount").value;
	var txtTotalSanctionedAmount1 = parseInt(txtTotalSanctionedAmount);
	var txtaccountheadcode = document.getElementById("txtAcc_HeadCode").value;
	//alert("1"+txtaccountheadcode);
	var mtxtParticulars = document.getElementById("mtxtParticulars").value;
	var txtBalanceAmount = document.getElementById("txtBalanceAmount").value;
	var txtBalanceAmount1 = parseInt(txtBalanceAmount);
	var txtBudgetProvided = document.getElementById("txtBudgetProvided").value;
	var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtInvoiceNo = document.getElementById("txtInvoiceNo").value;
	var txtInvoiceDate = document.getElementById("txtInvoiceDate").value;
	var txtInvoiceAmount = document.getElementById("txtInvoiceAmount").value;
	var txtSancAmount = document.getElementById("txtSancAmount").value;
	var txtmBookRefNo = document.getElementById("txtmBookRefNo").value;
	var txtmBookRefDate = document.getElementById("txtmBookRefDate").value;
	var mtxtParticulars1 = document.getElementById("mtxtParticulars1").value;		

	if (cboBillMajorType == "" || cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.cboBillMajorType.focus();
	} else if (cboBillMinorType == "" || cboBillMinorType == "s") {
		alert("Select Bill Minor Type in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.cboBillMinorType.focus();
	} else if (cboBillSubType == "" || cboBillSubType == "s") {
		alert("Select Bill Sub Type in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.cboBillSubType.focus();
	} else if (document.getElementById("cboPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.cboPayeeType.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtRefDate.focus();
	} else if (cboSanctioningAuthority == "" || cboSanctioningAuthority == "s") {
		alert("Select Sanctioning Authority in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.cboSanctioningAuthority
				.focus();
	} else if (cboEstimateSanctionNumber == ""
			|| cboEstimateSanctionNumber == "s") {
		alert("Select Estimate Sanction Number in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.cboEstimateSanctionNumber
				.focus();
	} else if (document.getElementById("txtEstimateSanctionDate").value == "") {
		alert("Enter Estimate Sanction Date in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtEstimateSanctionDate
				.focus();
	} else if (document.getElementById("txtSanctionProceedingDate").value == "") {
		alert("Enter Sanction Proceeding Date in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtSanctionProceedingDate
				.focus();
	} else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtAcc_HeadCode
				.focus();
		//alert(txtaccountheadcode);
	} else if (document.getElementById("txtTotalSanctionedAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtTotalSanctionedAmount
				.focus();
	} else if (document.getElementById("txtBalanceAmount").value == "") {
		alert("Budget Does not Alloted for Current Year");
		document.frm_SanctionProceedingSinglePayeeFirm.txtTotalSanctionedAmount
				.focus();
	} else if (txtTotalSanctionedAmount1 > txtBalanceAmount1) {
		alert("Total Sanctioned Amount is greater than Balance Amount in the Current Year");
		document.frm_SanctionProceedingSinglePayeeFirm.txtTotalSanctionedAmount
				.focus();
	} else if (document.getElementById("txtInvoiceNo").value == "") {
		alert("Enter Invoice No in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtInvoiceNo.focus();
	} else if (document.getElementById("txtInvoiceDate").value == "") {
		alert("Enter Invoice Date in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtInvoiceDate.focus();
	} else if (document.getElementById("mtxtParticulars1").value == "") {
		alert("Enter Particulars in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.mtxtParticulars1.focus();
	} else if (document.getElementById("txtmBookRefNo").value == "") {
		alert("Enter Book Ref No in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtmBookRefDate.focus();
	} else if (document.getElementById("txtmBookRefDate").value == "") {
		alert("Enter Book Ref Date in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtmBookRefDate.focus();
	} else if (document.getElementById("txtAgreementNo").value == "") {
		alert("Enter Agreement No in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtAgreementNo.focus();
	} else if (document.getElementById("txtAgreementDate").value == "") {
		alert("Enter Agreement Date in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtAgreementDate.focus();
	} else if (document.getElementById("txtSupplementAgreementNo").value == "") {
		alert("Enter Supplement Agreement No in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtSupplementAgreementNo.focus();
	} else if (document.getElementById("txtSupplementAgreementDate").value == "") {
		alert("Enter Supplement Agreement Date in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtSupplementAgreementDate.focus();
	} else if (document.getElementById("txtInvoiceAmount").value == "") {
		alert("Enter Invoice Amount in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtInvoiceAmount.focus();
	} else if (document.getElementById("txtSancAmount").value == "") {
		alert("Enter Sanc Amount in the Field");
		document.frm_SanctionProceedingSinglePayeeFirm.txtSancAmount.focus();
	} else {
		var url = path
				+ "/SanctionProceedingSinglePayeeFirm?command=saveFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&year=" + year + "&month=" + month
				+ "&txtSanctionProceedingDate=" + txtSanctionProceedingDate
				+ "&cboBillMajorType=" + cboBillMajorType
				+ "&cboBillMinorType=" + cboBillMinorType + "&cboBillSubType="
				+ cboBillSubType + "&cboPayeeType=" + cboPayeeType
				+ "&txtRefNo=" + txtRefNo + "&txtRefDate=" + txtRefDate
				+ "&cboSanctioningAuthority=" + cboSanctioningAuthority
				+ "&cmbSL_Code=" + cmbSL_Code + "&txtTotalSanctionedAmount="
				+ txtTotalSanctionedAmount + "&txtaccountheadcode="
				+ txtaccountheadcode + "&mtxtParticulars=" + mtxtParticulars
				+ "&txtInvoiceNo=" + txtInvoiceNo + "&txtInvoiceDate="
				+ txtInvoiceDate + "&txtInvoiceAmount=" + txtInvoiceAmount
				+ "&txtSancAmount=" + txtSancAmount + "&txtmBookRefNo="
				+ txtmBookRefNo + "&txtmBookRefDate=" + txtmBookRefDate
				+ "&mtxtParticulars1=" + mtxtParticulars1
				+ "&cboEstimateSanctionNumber=" + cboEstimateSanctionNumber
				+ "&txtEstimateSanctionDate=" + txtEstimateSanctionDate
				+ "&txtBudgetProvided=" + txtBudgetProvided
				+ "&txtBudgetSpent=" + txtBudgetSpent
				+ "&txtBalanceAmount=" + txtBalanceAmount;

		//alert(url);

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
	}
}


 function refresh() {
  document.getElementById("cboBillMajorType").value="s";
  document.getElementById("cboBillMinorType").value="s";
  document.getElementById("cboBillSubType").value="s";
  document.getElementById("cboPayeeType").value="";
  document.getElementById("txtRefNo").value="";
  document.getElementById("txtRefDate").value="";
  document.getElementById("cboSanctioningAuthority").length=1;
  document.getElementById("txtEmpID_mas").value="";
  document.getElementById("cboEstimateSanctionNumber").length=1;
  document.getElementById("txtEstimateSanctionDate").value="";
  document.getElementById("txtSanctionProceedingDate").value="";
  document.getElementById("txtAcc_HeadCode").value="";
  document.getElementById("txtAcc_HeadDesc").value="";
  document.getElementById("txtTotalSanctionedAmount").value="";
  
  document.getElementById("txtBudgetProvided").value="";
  document.getElementById("txtBudgetSpent").value="";
  document.getElementById("txtBalanceAmount").value="";
  document.getElementById("mtxtParticulars").value="";
  
  document.getElementById("txtInvoiceNo").value="";
  document.getElementById("txtInvoiceDate").value="";
  document.getElementById("mtxtParticulars1").value="";
  document.getElementById("txtmBookRefNo").value="";
  document.getElementById("txtmBookRefDate").value="";
  
  document.getElementById("txtAgreementNo").value="";
  document.getElementById("txtAgreementDate").value="";
  document.getElementById("txtSupplementAgreementNo").value="";
  document.getElementById("txtSupplementAgreementDate").value="";
  document.getElementById("txtInvoiceAmount").value="";
  document.getElementById("txtSancAmount").value="";
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