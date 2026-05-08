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

			if (command == "SanctionProceedingNo") {
				// alert("manipulate");
				SanctionProceedingNo1(baseResponse);
			} else if (command == "SanctionProceedingDate") {
				// alert("manipulate");
				SanctionProceedingDate1(baseResponse);
			} else if (command == "saveFunc") {
				// alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			}
		}
	}
}

function SanctionProceedingNo(path) {
	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;

	var url = path
			+ "/General_Sanction_Proceedings?command=SanctionProceedingNo&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function SanctionProceedingNo1(baseResponse) {
	document.frm_General_Sanction_Proceedings.cboSanctionProceedingNo.length = "1";
	document.frm_General_Sanction_Proceedings.txtSanctionProceedingDate.value = "";
	document.frm_General_Sanction_Proceedings.txtSanctioningAuthority.value = "";
	document.frm_General_Sanction_Proceedings.txtSanctionedBy.value = "";
	document.frm_General_Sanction_Proceedings.txtTotalSanctionedAmount.value = "";
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len5 = baseResponse.getElementsByTagName("SanctionProceedingNo").length;
		for ( var i = 0; i < len5; i++) {
			var SanctionProceedingNo = baseResponse
					.getElementsByTagName("SanctionProceedingNo")[i].firstChild.nodeValue;
			var se = document.getElementById("cboSanctionProceedingNo");
			var op = document.createElement("OPTION");
			op.value = SanctionProceedingNo;
			var txt = document.createTextNode(SanctionProceedingNo);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else if (flag == "Nodata") {
		alert("Record Does Not Exist");
	} else {
		alert("Record Updation Failed");
	}
}

function SanctionProceedingDate(path) {
	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboSanctionProceedingNo = document
			.getElementById("cboSanctionProceedingNo").value;

	var url = path
			+ "/General_Sanction_Proceedings?command=SanctionProceedingDate&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cboSanctionProceedingNo="
			+ cboSanctionProceedingNo;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function SanctionProceedingDate1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var SanctionProceedingDate = baseResponse
				.getElementsByTagName("SanctionProceedingDate")[0].firstChild.nodeValue;
		var SanctionProceedingAuthority = baseResponse
				.getElementsByTagName("SanctionProceedingAuthority")[0].firstChild.nodeValue;
		var SanctionProceedingSactionedBy = baseResponse
				.getElementsByTagName("SanctionProceedingSactionedBy")[0].firstChild.nodeValue;
		var SanctionProceedingAmount = baseResponse
				.getElementsByTagName("SanctionProceedingAmount")[0].firstChild.nodeValue;

		document.frm_General_Sanction_Proceedings.txtSanctionProceedingDate.value = SanctionProceedingDate;
		document.frm_General_Sanction_Proceedings.txtSanctioningAuthority.value = SanctionProceedingAuthority;
		document.frm_General_Sanction_Proceedings.txtSanctionedBy.value = SanctionProceedingSactionedBy;
		document.frm_General_Sanction_Proceedings.txtTotalSanctionedAmount.value = SanctionProceedingAmount;
	} else {
		alert("Record Updation Failed");
	}
}

function saveFunc(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var txtSanctionProceedingDate = document
			.getElementById("txtSanctionProceedingDate").value;
	var cboSanctionProceedingNo = document
			.getElementById("cboSanctionProceedingNo").value;
	var txtSanctioningAuthority = document
			.getElementById("txtSanctioningAuthority").value;
	var txtSanctionedBy = document.getElementById("txtSanctionedBy").value;
	var txtTotalSanctionedAmount = document
			.getElementById("txtTotalSanctionedAmount").value;
	var txtPresidingOfficer = document.getElementById("txtPresidingOfficer").value;
	var txtPrefix = document.getElementById("txtPrefix").value;
	var txtSuffix = document.getElementById("txtSuffix").value;
	var txtPresidingOfficerDesignation = document
			.getElementById("txtPresidingOfficerDesignation").value;
	var mtxtHeader = document.getElementById("mtxtHeader").value;
	var mtxtSubject = document.getElementById("mtxtSubject").value;
	var mtxtReference = document.getElementById("mtxtReference").value;
	var mtxtBodyOftheProceeding = document
			.getElementById("mtxtBodyOftheProceeding").value;
	var mtxtProceedingtobeAddressedTo = document
			.getElementById("mtxtProceedingtobeAddressedTo").value;
	var mtxtCopyTo = document.getElementById("mtxtCopyTo").value;
	var mtxtAdditionalPara1 = document.getElementById("mtxtAdditionalPara1").value;
	var mtxtAdditionalPara2 = document.getElementById("mtxtAdditionalPara2").value;
	var mtxtSignedByWithDesignation = document
			.getElementById("mtxtSignedByWithDesignation").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;

	if (cmbAcc_UnitCode == "" || cmbAcc_UnitCode == "s") {
		alert("Select Account Unit Code in the Field");
		document.frm_General_Sanction_Proceedings.cmbAcc_UnitCode.focus();
	} else if (cmbOffice_code == "" || cmbOffice_code == "s") {
		alert("Select Account for Office code the Field");
		document.frm_General_Sanction_Proceedings.cmbOffice_code.focus();
	} else if (document.getElementById("cboCashBook_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frm_General_Sanction_Proceedings.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frm_General_Sanction_Proceedings.cboCashBook_Month
				.focus();
	}else if (cboSanctionProceedingNo == "" || cboSanctionProceedingNo == "s") {
		alert("Select Sanction Proceeding No in the Field");
		document.frm_General_Sanction_Proceedings.cboSanctionProceedingNo
				.focus();
	} else if (document.getElementById("txtSanctionProceedingDate").value == "") {
		alert("Enter Sanction Proceeding Date in the Field");
		document.frm_General_Sanction_Proceedings.txtSanctionProceedingDate
				.focus();
	} else if (document.getElementById("txtSanctioningAuthority").value == "") {
		alert("Enter Sanctioning Authority in the Field");
		document.frm_General_Sanction_Proceedings.txtSanctioningAuthority
				.focus();
	} else if (document.getElementById("txtSanctionedBy").value == "") {
		alert("Enter Sanctioned By in the Field");
		document.frm_General_Sanction_Proceedings.txtSanctionedBy.focus();
	} else if (document.getElementById("txtTotalSanctionedAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_General_Sanction_Proceedings.txtTotalSanctionedAmount
				.focus();
	} else if (document.getElementById("txtPresidingOfficer").value == "") {
		alert("Enter Presiding Officer in the Field");
		document.frm_General_Sanction_Proceedings.txtPresidingOfficer.focus();
	} else if (document.getElementById("txtPrefix").value == "") {
		alert("Enter Prefix in the Field");
		document.frm_General_Sanction_Proceedings.txtPrefix.focus();
	} else if (document.getElementById("txtPresidingOfficerDesignation").value == "") {
		alert("Enter Presiding Officer Designation in the Field");
		document.frm_General_Sanction_Proceedings.txtPresidingOfficerDesignation.focus();
	} else if (document.getElementById("mtxtHeader").value == "") {
		alert("enter Header in the Field");
		document.frm_General_Sanction_Proceedings.mtxtHeader.focus();
	} else if (document.getElementById("mtxtSubject").value == "") {
		alert("Enter Subject in the Field");
		document.frm_General_Sanction_Proceedings.mtxtSubject.focus();
	} else if (document.getElementById("mtxtBodyOftheProceeding").value == "") {
		alert("Enter Body Of the Proceeding in the Field");
		document.frm_General_Sanction_Proceedings.mtxtBodyOftheProceeding.focus();
	} else if (document.getElementById("mtxtProceedingtobeAddressedTo").value == "") {
		alert("Enter Proceeding to be Addressed To in the Field");
		document.frm_General_Sanction_Proceedings.mtxtProceedingtobeAddressedTo
				.focus();
	} else if (document.getElementById("mtxtAdditionalPara1").value == "") {
		alert("Enter Additional Para-1 in the Field");
		document.frm_General_Sanction_Proceedings.mtxtAdditionalPara1.focus();
	} else if (document.getElementById("mtxtSignedByWithDesignation").value == "") {
		alert("Enter Signed By With Designation in the Field");
		document.frm_General_Sanction_Proceedings.mtxtSignedByWithDesignation
				.focus();
	} else {
		var url = path
				+ "/General_Sanction_Proceedings?command=saveFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cboCashBook_Year=" + cboCashBook_Year
				+ "&cboCashBook_Month=" + cboCashBook_Month
				+ "&cboSanctionProceedingNo=" + cboSanctionProceedingNo
				+ "&txtSanctionProceedingDate=" + txtSanctionProceedingDate
				+ "&txtSanctioningAuthority=" + txtSanctioningAuthority
				+ "&txtSanctionedBy=" + txtSanctionedBy
				+ "&txtTotalSanctionedAmount=" + txtTotalSanctionedAmount
				+ "&txtPresidingOfficer=" + txtPresidingOfficer + "&txtPrefix="
				+ txtPrefix + "&txtSuffix=" + txtSuffix
				+ "&txtPresidingOfficerDesignation="
				+ txtPresidingOfficerDesignation + "&mtxtHeader=" + mtxtHeader
				+ "&mtxtSubject=" + mtxtSubject + "&mtxtReference="
				+ mtxtReference + "&mtxtBodyOftheProceeding="
				+ mtxtBodyOftheProceeding + "&mtxtProceedingtobeAddressedTo="
				+ mtxtProceedingtobeAddressedTo + "&mtxtCopyTo=" + mtxtCopyTo
				+ "&mtxtAdditionalPara1=" + mtxtAdditionalPara1
				+ "&mtxtAdditionalPara2=" + mtxtAdditionalPara2
				+ "&mtxtSignedByWithDesignation=" + mtxtSignedByWithDesignation
				+ "&mtxtRemarks=" + mtxtRemarks;

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
		alert("Record Inserted Successfully");
		refresh();
	}else if(flag == "Exist"){
		alert("Record Already Exist");
	}else {
		alert("Record Updation Failed");
	}
}

function refresh()
{
	var today = new Date();	
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	//alert(year);
	document.frm_General_Sanction_Proceedings.cboCashBook_Year.value = year;
	document.frm_General_Sanction_Proceedings.cboCashBook_Month.value = "s";
	document.frm_General_Sanction_Proceedings.cboSanctionProceedingNo.length = "1";
	document.frm_General_Sanction_Proceedings.txtSanctionProceedingDate.value = "";
	document.frm_General_Sanction_Proceedings.txtSanctioningAuthority.value = "";
	document.frm_General_Sanction_Proceedings.txtSanctionedBy.value = "";
	document.frm_General_Sanction_Proceedings.txtTotalSanctionedAmount.value = "";	
	document.frm_General_Sanction_Proceedings.txtPresidingOfficer.value = "";
	document.frm_General_Sanction_Proceedings.txtPrefix.value = "";
	document.frm_General_Sanction_Proceedings.txtSuffix.value = "";
	document.frm_General_Sanction_Proceedings.txtPresidingOfficerDesignation.value = "";
	document.frm_General_Sanction_Proceedings.mtxtHeader.value = "";
	document.frm_General_Sanction_Proceedings.mtxtSubject.value = "";
	document.frm_General_Sanction_Proceedings.mtxtReference.value = "";	
	document.frm_General_Sanction_Proceedings.mtxtBodyOftheProceeding.value = "";
	document.frm_General_Sanction_Proceedings.mtxtProceedingtobeAddressedTo.value = "";
	document.frm_General_Sanction_Proceedings.mtxtCopyTo.value = "";
	document.frm_General_Sanction_Proceedings.mtxtAdditionalPara1.value = "";
	document.frm_General_Sanction_Proceedings.mtxtAdditionalPara2.value = "";	
	document.frm_General_Sanction_Proceedings.mtxtSignedByWithDesignation.value = "";	
	document.frm_General_Sanction_Proceedings.mtxtRemarks.value = "";	
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
