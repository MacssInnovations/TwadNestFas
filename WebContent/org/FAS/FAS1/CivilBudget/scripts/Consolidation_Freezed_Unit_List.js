//			Civil_Budget_Freezed_Unit_List			//
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
			//alert(command);
			if (command == "list") {
				list1(baseResponse);
			} else if (command == "getFormatName") {
				getFormatName1(baseResponse)
			} else if (command == "LoadAccountingUnitID") {
				LoadAccountingUnitID_Response(baseResponse)
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

function LoadAccountingUnitID_OfficeID(path) {
	//alert(path);
	var url = path
			+ "/Civil_Budget_Freezed_Unit_List?command=LoadAccountingUnitID";
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function Funclist(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbFormat_Name = document.getElementById("cmbFormat_Name").value;

	if (cmbFormat_Name == "") {
		alert("select Format Name in the Filed");
		document.frmCivil_Budget_Freezed_Unit_List.cmbFormat_Name.focus;
	} else {
		var url = path
				+ "/Civil_Budget_Freezed_Unit_List?command=list&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cmbFormat_Name=" + cmbFormat_Name + "&cmbFinancialYear="
				+ cmbFinancialYear;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function LoadAccountingUnitID_Response(baseResponse) {
	var flag1 = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag1);
	if (flag1 == "success") {
		var len4 = baseResponse.getElementsByTagName("Acc_UnitID").length;
		for ( var i = 0; i < len4; i++) {
			var Acc_UnitID = baseResponse.getElementsByTagName("Acc_UnitID")[i].firstChild.nodeValue;
			var Acc_UnitName = baseResponse
					.getElementsByTagName("Acc_UnitName")[i].firstChild.nodeValue;
			var Acc_OfficeID = baseResponse
					.getElementsByTagName("Acc_OfficeID")[i].firstChild.nodeValue;
			var Acc_OfficeName = baseResponse
					.getElementsByTagName("Acc_OfficeName")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbAcc_UnitCode");
			var op = document.createElement("OPTION");
			op.value = Acc_UnitID;
			var txt = document.createTextNode(Acc_UnitName);
			op.appendChild(txt);
			se.appendChild(op);

			var se = document.getElementById("cmbOffice_code");
			var op = document.createElement("OPTION");
			op.value = Acc_OfficeID;
			var txt = document.createTextNode(Acc_OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.getElementById("cmbOffice_code").length = 1;
		}
	} else {
		alert("Fail to Load Format No");
	}
}

function getFormatName1(baseResponse) {

	var flag1 = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag1 == "success") {
		var len4 = baseResponse.getElementsByTagName("txtFormatNo").length;
		if (len4 != 0) {
			for ( var i = 0; i < len4; i++) {
				var FormatNo = baseResponse.getElementsByTagName("txtFormatNo")[i].firstChild.nodeValue;
var FORMAT_DESC_SUB=baseResponse.getElementsByTagName("FORMAT_DESC_SUB")[i].firstChild.nodeValue;
				var se = document.getElementById("cmbFormat_Name");
				var op = document.createElement("OPTION");
				op.value = FormatNo;
				var txt = document.createTextNode(FORMAT_DESC_SUB);
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

function list1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("Acc_UnitID");
		if (r_no.length != 0) {
			for ( var k = 0; k < r_no.length; k++) {
				var Acc_UnitID = baseResponse
						.getElementsByTagName("Acc_UnitID")[k].firstChild.nodeValue;
				var Acc_UnitName = baseResponse
						.getElementsByTagName("Acc_UnitName")[k].firstChild.nodeValue;
				var Acc_OfficeID = baseResponse
						.getElementsByTagName("Acc_OfficeID")[k].firstChild.nodeValue;
				var Acc_OfficeName = baseResponse
						.getElementsByTagName("Acc_OfficeName")[k].firstChild.nodeValue;
				var Freezed_Date = baseResponse
						.getElementsByTagName("Freezed_Date")[k].firstChild.nodeValue;

				var tbody = document.getElementById("grid_body");
				var mycurrent_row = document.createElement("TR");

				var cell1 = document.createElement("TD");
				var Acc_UnitName = document.createTextNode(Acc_UnitName);
				cell1.appendChild(Acc_UnitName);
				mycurrent_row.appendChild(cell1);

				var cell2 = document.createElement("TD");
				var Acc_OfficeName = document.createTextNode(Acc_OfficeName);
				cell2.appendChild(Acc_OfficeName);
				mycurrent_row.appendChild(cell2);

				var cell3 = document.createElement("TD");
				var Freezed_Date = document.createTextNode(Freezed_Date);
				cell3.appendChild(Freezed_Date);
				mycurrent_row.appendChild(cell3);

				tbody.appendChild(mycurrent_row);
			}
		} else {
			alert("Record Does Not Exist");
		}
	} else {
		clrForm();
		alert("Failed to Load Values");
	}
}

function clrForm() {
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
	document.getElementById("cmbFormat_Name").value = "";

	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
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
