//			Freeze_or_NillFreeze			//
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

			if (command == "save") {
				save1(baseResponse);
			} else if (command == "getFormatName") {
				getFormatName1(baseResponse)
			}
		}
	}
}

function initialLoad1(path) {
	//alert(path);
	var url = path + "/CivilBudget_Freeze_or_NillFreeze?command=getFormatName";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function FuncSave(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbFreezeType = document.getElementById("cmbFreezeType").value;
	var cmbFormat_Name = document.getElementById("cmbFormat_Name").value;

	if (cmbFreezeType == "") {
		alert("select Freeze Type in the Filed");
		document.frmFreeze_or_NillFreeze.cmbFreezeType.focus;
	} else if (cmbFormat_Name == "") {
		alert("select Format Name in the Filed");
		document.frmFreeze_or_NillFreeze.cmbFormat_Name.focus;
	} else {
		var url = path
				+ "/CivilBudget_Freeze_or_NillFreeze?command=save&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cmbFreezeType=" + cmbFreezeType + "&cmbFormat_Name="
				+ cmbFormat_Name + "&cmbFinancialYear=" + cmbFinancialYear;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}
function getFormatName1(baseResponse) {

	var flag1 = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag1 == "success") {
		var len4 = baseResponse.getElementsByTagName("txtFormatNo").length;
		if (len4 != 0) {
			for ( var i = 0; i < len4; i++) {
				var FormatNo = baseResponse.getElementsByTagName("txtFormatNo")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbFormat_Name");
				var op = document.createElement("OPTION");
				op.value = FormatNo;
				var txt = document.createTextNode(FormatNo);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Format No Does Not Exist");
		}
	} else {
		alert("Fail to Load Format No");
	}
}

function save1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "Exist") {
		alert("Budget Closure have Already Freezed");
	} else if (flag == "success") {
		clrForm()
		alert("Budget Closure Freezed Successfully");
	} else {
		clrForm()
		alert("Record Insertion Failed");
	}
}

function clrForm() {
	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.getElementById("cmbFinancialYear").length = 1;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	var year1 = 0;
	var financialyear = 0;
	var financialyear1 = 0;
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}

	if (month < 4) {
		financialyear = year1 + "-" + year;
		financialyear1 = (year1 - 1) + "-" + (year - 1);
	} else {
		financialyear = year + "-" + year1;
		financialyear1 = (year - 1) + "-" + (year1 - 1);
	}
	for ( var k = 0; k < 2; k++) {
		if (k == 0) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear1;
			var txt = document.createTextNode(financialyear1);
			op.appendChild(txt);
			se.appendChild(op);
		} else if (k == 1) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear;
			var txt = document.createTextNode(financialyear);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	document.getElementById("cmbFinancialYear").value = financialyear;
	document.getElementById("cmbFreezeType").value = "";
	document.getElementById("cmbFormat_Name").value = "";

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

function exitfun() {
	window.close();
}
