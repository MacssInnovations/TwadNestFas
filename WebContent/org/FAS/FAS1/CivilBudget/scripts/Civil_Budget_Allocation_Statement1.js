//		Civil_Budget_Allocation_Statement1		//
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

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse)
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "Edit") {
				Edit1(baseResponse);
			} else if (command == "get") {
				firstLoad(baseResponse);
			} else if (command == "LoadData") {
				LoadData_View(baseResponse);
			} else if (command == "LoadAccountingUnitID") {
				LoadAccountingUnitID_Response(baseResponse)
			} else if (command == "LoadAccountingUnitIDD") {
				LoadAccountingUnitIDD(baseResponse)
			}
		}
	}
}

function LoadAccountingUnitID_Response(baseResponse) {
	var flag1 = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
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

			var se = document.getElementById("cmbAcc_UnitCode1");
			var op = document.createElement("OPTION");
			op.value = Acc_UnitID;
			var txt = document.createTextNode(Acc_UnitName);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else {
		alert("Fail to Load Budget Alloted to Unit");
	}
}
function initialLoad() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	var tbody1 = document.getElementById("grid_body1");
	var t1 = 0;
	for (t1 = tbody1.rows.length - 1; t1 >= 0; t1--) {
		tbody1.deleteRow(0);
	}

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	var year1 = 0;
	var financialyear = 0;
	var financialyear1 = 0;
	var financialyear2 = 0;
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}

	if (month < 4) {
		financialyear = year1 + "-" + year;
		financialyear1 = (parseInt(year1) - 1) + "-" + (parseInt(year) - 1);
		financialyear2 = (parseInt(year1) - 2) + "-" + (parseInt(year) - 2);
	} else {
		financialyear = year + "-" + year1;
		financialyear1 = (parseInt(year) - 1) + "-" + (parseInt(year1) - 1);
		financialyear2 = (parseInt(year) - 2) + "-" + (parseInt(year1) - 2);
	}

	for ( var k = 0; k < 3; k++) {
		if (k == 0) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear2;
			var txt = document.createTextNode(financialyear2);
			op.appendChild(txt);
			se.appendChild(op);
		} else if (k == 1) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear1;
			var txt = document.createTextNode(financialyear1);
			op.appendChild(txt);
			se.appendChild(op);

		} else if (k == 2) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear;
			var txt = document.createTextNode(financialyear);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	document.getElementById("cmbFinancialYear").value = financialyear;

	document.Civil_Budget_Allocation_Statement1.butView.disabled = false;
	document.Civil_Budget_Allocation_Statement1.butSub.disabled = false;
	document.Civil_Budget_Allocation_Statement1.butDelete.disabled = true;
	document.Civil_Budget_Allocation_Statement1.butUpdate.disabled = true;
}
function initialLoad1() {
	document.Civil_Budget_Allocation_Statement1.butView.disabled = false;
	document.Civil_Budget_Allocation_Statement1.butSub.disabled = false;
	document.Civil_Budget_Allocation_Statement1.butDelete.disabled = true;
	document.Civil_Budget_Allocation_Statement1.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode1").value;
	var cmbAcc_UnitCode1 = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var financialYear = document.getElementById("cmbFinancialYear").value;
	var cmbFormat_Type = document.getElementById("cmbFormat_Type").value;

	var url = "../../../../../Civil_Budget_Allocation_Statement1?command=get&financialYear="
			+ financialYear
			+ "&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode
			+ "&cmbFormat_Type="
			+ cmbFormat_Type
			+ "&cmbAcc_UnitCode1="
			+ cmbAcc_UnitCode1 + "&cmbOffice_code=" + cmbOffice_code;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function firstLoad(baseResponse) {
	var office_id = baseResponse.getElementsByTagName("office_id")[0].firstChild.nodeValue;
	if (office_id == 5000) {
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if (flag == "success") {
			document.getElementById("HO").style.visibility = "visible";
			document.getElementById("RN_CL").style.visibility = "hidden";
			/** Delete Existing Values from Grid */
			var tbody = document.getElementById("grid_body");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			var r_no = baseResponse.getElementsByTagName("Head_of_Account");
			seq = 0;

			var item = new Array();
			if (r_no.length != 0) {
				for ( var k = 0; k < r_no.length; k++) {

					item[0] = baseResponse
							.getElementsByTagName("Head_of_Account")[k].firstChild.nodeValue;

					item[1] = baseResponse
							.getElementsByTagName("BE_for_Next_Year")[k].firstChild.nodeValue;
					if ((item[1] == 0) || (item[8] == 'null')) {
						item[1] = "0";
					}

					/** Create Table Row */
					var mycurrent_row = document.createElement("TR");
					mycurrent_row.id = seq;

					/** Sl No */
					var cell0 = document.createElement("TD");
					var slno = document.createTextNode(seq + 1);
					cell0.appendChild(slno);
					mycurrent_row.appendChild(cell0);

					/** Head of Account */
					var cell2 = document.createElement("TD");
					var Head_of_Account = document.createElement('TEXTAREA',
							'option1');
					Head_of_Account.name = "Head_of_Account" + seq;
					Head_of_Account.id = "Head_of_Account" + seq;
					Head_of_Account.value = item[0];
					Head_of_Account.readOnly = true;
					Head_of_Account.setAttribute("cols", "5");
					Head_of_Account.style.height = "60px"
					Head_of_Account.style.width = "350px";
					cell2.appendChild(Head_of_Account);
					mycurrent_row.appendChild(cell2);

					/** BE for Next Year */
					var cell20 = document.createElement("TD");
					var BE_for_Next_Year = document.createElement("input");
					BE_for_Next_Year.type = "Text";
					BE_for_Next_Year.name = "BE_for_Next_Year" + seq;
					BE_for_Next_Year.id = "BE_for_Next_Year" + seq;
					BE_for_Next_Year.value = item[1] + ".00";
					BE_for_Next_Year.style.textAlign = "right";
					// BE_for_Next_Year.maxLength = "10";
					// BE_for_Next_Year.size = "10";
					cell20.appendChild(BE_for_Next_Year);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/** Amount to be Alloted */
					var cell20 = document.createElement("TD");
					var Amount_to_be_Alloted = document.createElement("input");
					Amount_to_be_Alloted.type = "Text";
					Amount_to_be_Alloted.name = "Amount_to_be_Alloted" + seq;
					Amount_to_be_Alloted.id = "Amount_to_be_Alloted" + seq;
					Amount_to_be_Alloted.value = "";
					Amount_to_be_Alloted.style.textAlign = "right";
					cell20.appendChild(Amount_to_be_Alloted);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/**
					 * Reason for Variation if any between RE for the Year and
					 * the next Year
					 */
					var cell21 = document.createElement("TD");
					var Reason_for_Variation = document.createElement(
							'TEXTAREA', 'option1');
					Reason_for_Variation.name = "Reason_for_Variation" + seq;
					Reason_for_Variation.id = "Reason_for_Variation" + seq;
					Reason_for_Variation.value = "";
					Reason_for_Variation.setAttribute("cols", "5");
					Reason_for_Variation.style.height = "60px"
					Reason_for_Variation.style.width = "200px";
					cell21.appendChild(Reason_for_Variation);
					mycurrent_row.appendChild(cell21);

					tbody.appendChild(mycurrent_row);

					/** Increment Sequence Number */
					seq = seq + 1;
				}
				document.getElementById("RecordCount").value = seq;
			} else {
				document.getElementById("RN_CL").style.visibility = "hidden";
				document.getElementById("HO").style.visibility = "hidden";
				alert("Record Does Not Exist");
			}
		} else if (flag == "Freeze_Done") {
			document.getElementById("RN_CL").style.visibility = "hidden";
			document.getElementById("HO").style.visibility = "hidden";
			alert("Budget Allocation Have Already Freezed for This Format");
		}
	} else {
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if (flag == "success") {
			document.getElementById("RN_CL").style.visibility = "visible";
			document.getElementById("HO").style.visibility = "hidden";

			var flagg = baseResponse.getElementsByTagName("flagg")[0].firstChild.nodeValue;
			if (flagg == "success") {
				var slno = baseResponse.getElementsByTagName("Indicator");
				for ( var g = 0; g < slno.length; g++) {
					var Amount = baseResponse.getElementsByTagName("Amount")[g].firstChild.nodeValue;
					if ((Amount == "") || (Amount == 'null') || (Amount == 0)) {
						Amount = 0;
					}
					var Indicator = baseResponse
							.getElementsByTagName("Indicator")[g].firstChild.nodeValue;
					if (Indicator == "T_Altd_Amt_By_HO") {
						document.getElementById("T_Altd_Amt_By_HO").value = Amount;
					} else if (Indicator == "T_Altd_Amt_By_RN") {
						document.getElementById("T_Altd_Amt_By_RN").value = Amount;
					}
				}
			} else {

			}
			/** Delete Existing Values from Grid */
			var tbody = document.getElementById("grid_body1");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			var r_no = baseResponse.getElementsByTagName("Head_of_Account");
			seq = 0;

			var item = new Array();
			if (r_no.length != 0) {
				for ( var k = 0; k < r_no.length; k++) {

					item[0] = baseResponse
							.getElementsByTagName("Head_of_Account")[k].firstChild.nodeValue;

					item[1] = baseResponse
							.getElementsByTagName("BE_for_Next_Year")[k].firstChild.nodeValue;
					if ((item[1] == 0) || (item[1] == 'null')) {
						item[1] = "0";
					}

					item[2] = baseResponse
							.getElementsByTagName("Amount_Alloted")[k].firstChild.nodeValue;
					if ((item[2] == 0) || (item[2] == 'null')) {
						item[2] = "0";
					}

					/** Create Table Row */
					var mycurrent_row = document.createElement("TR");
					mycurrent_row.id = seq;

					/** Sl No */
					var cell0 = document.createElement("TD");
					var slno = document.createTextNode(seq + 1);
					cell0.appendChild(slno);
					mycurrent_row.appendChild(cell0);

					/** Head of Account */
					var cell2 = document.createElement("TD");
					var Head_of_Account = document.createElement('TEXTAREA',
							'option1');
					Head_of_Account.name = "Head_of_Account" + seq;
					Head_of_Account.id = "Head_of_Account" + seq;
					Head_of_Account.value = item[0];
					Head_of_Account.readOnly = true;
					Head_of_Account.setAttribute("cols", "5");
					Head_of_Account.style.height = "60px"
					Head_of_Account.style.width = "350px";
					cell2.appendChild(Head_of_Account);
					mycurrent_row.appendChild(cell2);

					/** Amount Alloted By Head Office */
					var cell20 = document.createElement("TD");
					var Amount_Alloted_By_HO = document.createElement("input");
					Amount_Alloted_By_HO.type = "Text";
					Amount_Alloted_By_HO.name = "Amount_Alloted_By_HO" + seq;
					Amount_Alloted_By_HO.id = "Amount_Alloted_By_HO" + seq;
					Amount_Alloted_By_HO.value = item[2] + ".00";
					Amount_Alloted_By_HO.style.textAlign = "right";
					Amount_Alloted_By_HO.readOnly = true;
					// Amount_Alloted_By_HO.maxLength = "10";
					// Amount_Alloted_By_HO.size = "10";
					cell20.appendChild(Amount_Alloted_By_HO);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/** BE for Next Year */
					var cell20 = document.createElement("TD");
					var BE_for_Next_Year = document.createElement("input");
					BE_for_Next_Year.type = "Text";
					BE_for_Next_Year.name = "BE_for_Next_Year" + seq;
					BE_for_Next_Year.id = "BE_for_Next_Year" + seq;
					BE_for_Next_Year.value = item[1] + ".00";
					BE_for_Next_Year.style.textAlign = "right";
					// BE_for_Next_Year.maxLength = "10";
					// BE_for_Next_Year.size = "10";
					cell20.appendChild(BE_for_Next_Year);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/** Amount to be Alloted */
					var cell20 = document.createElement("TD");
					var Amount_to_be_Alloted = document.createElement("input");
					Amount_to_be_Alloted.type = "Text";
					Amount_to_be_Alloted.name = "Amount_to_be_Alloted" + seq;
					Amount_to_be_Alloted.id = "Amount_to_be_Alloted" + seq;
					Amount_to_be_Alloted.value = "";
					Amount_to_be_Alloted.style.textAlign = "right";
					cell20.appendChild(Amount_to_be_Alloted);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/**
					 * Reason for Variation if any between RE for the Year and
					 * the next Year
					 */
					var cell21 = document.createElement("TD");
					var Reason_for_Variation = document.createElement(
							'TEXTAREA', 'option1');
					Reason_for_Variation.name = "Reason_for_Variation" + seq;
					Reason_for_Variation.id = "Reason_for_Variation" + seq;
					Reason_for_Variation.value = "";
					Reason_for_Variation.setAttribute("cols", "5");
					Reason_for_Variation.style.height = "60px"
					Reason_for_Variation.style.width = "200px";
					cell21.appendChild(Reason_for_Variation);
					mycurrent_row.appendChild(cell21);

					tbody.appendChild(mycurrent_row);

					/** Increment Sequence Number */
					seq = seq + 1;
				}
				document.getElementById("RecordCount").value = seq;
			} else {
				document.getElementById("RN_CL").style.visibility = "hidden";
				document.getElementById("HO").style.visibility = "hidden";
				alert("Record Does Not Exist");
			}
		} else if (flag == "Freeze_Done") {
			document.getElementById("RN_CL").style.visibility = "hidden";
			document.getElementById("HO").style.visibility = "hidden";
			alert("Budget Allocation Have Already Freezed for This Format");
		}
	}

}
/*	else if (flag == "Freeze_Pending") {
 var ofiice_type = baseResponse.getElementsByTagName("ofiice_type")[0].firstChild.nodeValue;
 alert("All the "
 + ofiice_type
 + " Under your office have Not Freezed the Civil Budget Closure");
 clrForm();
 } else if (flag == "Freeze_Pending1") {
 alert("The Civil Budget Closure Should have been Freezed for your Office Also");
 clrForm();
 } else if (flag == "Freeze_Done") {
 alert("The Civil Budget Closure Consolidate Have Already Freezed");
 clrForm();
 } 
 else {
 alert("Failed to Load Data");
 }
 }*/

function funcSave() {
	var T_amt_Altd_by_RN = 0;
	var amt_T = 0;
	var T_Altd_Amt_By_HO = parseFloat(document
			.getElementById("T_Altd_Amt_By_HO").value);
	var T_Altd_Amt_By_RN = parseFloat(document
			.getElementById("T_Altd_Amt_By_RN").value);

	var tbody = document.getElementById("grid_body1");
	var rowcount = tbody.rows.length;
	for ( var i = 0; i < rowcount; i++) {
		amt_T = amt_T
				+ parseFloat(document
						.getElementById("Amount_to_be_Alloted" + i).value);
	}
	T_amt_Altd_by_RN = amt_T + T_Altd_Amt_By_RN;

	if (T_amt_Altd_by_RN > T_Altd_Amt_By_HO) {
		alert("Alloted Budget Exceeds the Budget Alloted by Head Office to Your Region");
		return false;
	} else {
		document.getElementById("filter").value = "save";
		return true;
	}
}

function funcUpdate() {
	var T_amt_Altd_by_RN = 0;
	var amt_T = 0;
	var T_Altd_Amt_By_HO = parseFloat(document
			.getElementById("T_Altd_Amt_By_HO").value);
	var T_Altd_Amt_By_RN = parseFloat(document
			.getElementById("T_Altd_Amt_By_RN").value);

	var tbody = document.getElementById("grid_body1");
	var rowcount = tbody.rows.length;
	for ( var i = 0; i < rowcount; i++) {
		amt_T = amt_T
				+ parseFloat(document
						.getElementById("Amount_to_be_Alloted" + i).value);
	}
	T_amt_Altd_by_RN = amt_T + T_Altd_Amt_By_RN;

	if (T_amt_Altd_by_RN > T_Altd_Amt_By_HO) {
		alert("Alloted Budget Exceeds the Budget Alloted by Head Office to Your Region");
		return false;
	} else {
		document.getElementById("filter").value = "update";
		return true;
	}
}

function funcDelete() {
	var r = confirm("Are U Sure to Continue?");
	if (r == true) {
		document.getElementById("filter").value = "delete";
		return true;
	} else {
		return false;
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

function LoadAccountingUnitID(path) {
	//alert(path);
	var url = path
			+ "/Civil_Budget_Allocation_Statement1?command=LoadAccountingUnitID";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function LoadAccountingUnitIDD(baseResponse) {
	var len4 = baseResponse.getElementsByTagName("Unit_id").length;
	for ( var i = 0; i < len4; i++) {
		var Acc_UnitID = baseResponse.getElementsByTagName("Unit_id")[i].firstChild.nodeValue;
		var Acc_UnitName = baseResponse.getElementsByTagName("Unit_name")[i].firstChild.nodeValue;
		var Acc_OfficeID = baseResponse.getElementsByTagName("Office_id")[i].firstChild.nodeValue;
		var Acc_OfficeName = baseResponse.getElementsByTagName("Office_name")[i].firstChild.nodeValue;

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
	}
}
function LoadAccountingUnitID_OfficeID(path) {
	//alert(path);
	var url = path
			+ "/Civil_Budget_Allocation_Statement1?command=LoadAccountingUnitID1";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function LoadData(path) {
	//alert(path);
	document.Civil_Budget_Allocation_Statement1.butView.disabled = false;
	document.Civil_Budget_Allocation_Statement1.butSub.disabled = true;
	document.Civil_Budget_Allocation_Statement1.butDelete.disabled = false;
	document.Civil_Budget_Allocation_Statement1.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode1 = document.getElementById("cmbAcc_UnitCode1").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbFormat_Type = document.getElementById("cmbFormat_Type").value;

	var RecordCount = 0;
	var url = path
			+ "/Civil_Budget_Allocation_Statement1?filter=view&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode1=" + cmbAcc_UnitCode1
			+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
			+ RecordCount + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbFormat_Type=" + cmbFormat_Type;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function LoadData_View(baseResponse) {
	var office_id = baseResponse.getElementsByTagName("office_id")[0].firstChild.nodeValue;
	if (office_id == 5000) {
		document.getElementById("HO").style.visibility = "visible";
		document.getElementById("RN_CL").style.visibility = "hidden";
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if (flag == "success") {
			/** Delete Existing Values from Grid */
			var tbody = document.getElementById("grid_body");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			var r_no = baseResponse.getElementsByTagName("Head_of_Account");
			var len = baseResponse.getElementsByTagName("Head_of_Account").length;
			seq = 0;
			if (len != 0) {
				var item = new Array();
				for ( var k = 0; k < r_no.length; k++) {

					item[0] = baseResponse
							.getElementsByTagName("Head_of_Account")[k].firstChild.nodeValue;

					item[1] = baseResponse
							.getElementsByTagName("BE_for_Next_Year")[k].firstChild.nodeValue;
					if ((item[1] == 0) || (item[1] == 'null')) {
						item[1] = "0";
					}

					item[2] = baseResponse
							.getElementsByTagName("Amount_to_be_Alloted")[k].firstChild.nodeValue;
					if ((item[2] == 0) || (item[8] == 'null')) {
						item[2] = "0";
					}
					item[3] = baseResponse
							.getElementsByTagName("Reason_for_Variation")[k].firstChild.nodeValue;
					if (item[3] == 'null') {
						item[3] = "";
					}

					/** Create Table Row */
					var mycurrent_row = document.createElement("TR");
					mycurrent_row.id = seq;

					/** Sl No */
					var cell0 = document.createElement("TD");
					var slno = document.createTextNode(seq + 1);
					cell0.appendChild(slno);
					mycurrent_row.appendChild(cell0);

					/** Head of Account */
					var cell2 = document.createElement("TD");
					var Head_of_Account = document.createElement('TEXTAREA',
							'option1');
					Head_of_Account.name = "Head_of_Account" + seq;
					Head_of_Account.id = "Head_of_Account" + seq;
					Head_of_Account.value = item[0];
					Head_of_Account.readOnly = true;
					Head_of_Account.setAttribute("cols", "5");
					Head_of_Account.style.height = "60px"
					Head_of_Account.style.width = "350px";
					cell2.appendChild(Head_of_Account);
					mycurrent_row.appendChild(cell2);

					/** BE for Next Year */
					var cell20 = document.createElement("TD");
					var BE_for_Next_Year = document.createElement("input");
					BE_for_Next_Year.type = "Text";
					BE_for_Next_Year.name = "BE_for_Next_Year" + seq;
					BE_for_Next_Year.id = "BE_for_Next_Year" + seq;
					BE_for_Next_Year.value = item[1] + ".00";
					BE_for_Next_Year.style.textAlign = "right";
					// BE_for_Next_Year.maxLength = "10";
					// BE_for_Next_Year.size = "10";
					cell20.appendChild(BE_for_Next_Year);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/** Amount_to_be_Alloted */
					var cell20 = document.createElement("TD");
					var Amount_to_be_Alloted = document.createElement("input");
					Amount_to_be_Alloted.type = "Text";
					Amount_to_be_Alloted.name = "Amount_to_be_Alloted" + seq;
					Amount_to_be_Alloted.id = "Amount_to_be_Alloted" + seq;
					Amount_to_be_Alloted.value = item[2] + ".00";
					Amount_to_be_Alloted.style.textAlign = "right";
					// Amount_to_be_Alloted.maxLength = "10";
					// Amount_to_be_Alloted.size = "10";
					cell20.appendChild(Amount_to_be_Alloted);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/** Reason for Variation */
					var cell9 = document.createElement("TD");
					var Reason_for_Variation = document.createElement(
							'TEXTAREA', 'option1');
					Reason_for_Variation.name = "Reason_for_Variation" + seq;
					Reason_for_Variation.id = "Reason_for_Variation" + seq;
					Reason_for_Variation.value = item[3];
					Reason_for_Variation.setAttribute("cols", "5");
					Reason_for_Variation.style.height = "60px"
					Reason_for_Variation.style.width = "150px";
					cell9.appendChild(Reason_for_Variation);
					mycurrent_row.appendChild(cell9);

					tbody.appendChild(mycurrent_row);

					/** Increment Sequence Number */
					seq = seq + 1;
				}
			} else {
				alert("Record Does Not Exist");
			}
			document.getElementById("RecordCount").value = seq;
		} else if (flag == "Freeze_Done") {
			alert("The Civil Budget Allocation Closure Have Already Freezed");
			clrForm();
		} else {
			alert("Failed to Load Data");
		}
	} else {
		document.getElementById("RN_CL").style.visibility = "visible";
		document.getElementById("HO").style.visibility = "hidden";
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if (flag == "success") {
			var flagg = baseResponse.getElementsByTagName("flagg")[0].firstChild.nodeValue;
			if (flagg == "success") {
				var slno = baseResponse.getElementsByTagName("Indicator");
				for ( var g = 0; g < slno.length; g++) {
					var Amount = baseResponse.getElementsByTagName("Amount")[g].firstChild.nodeValue;
					if ((Amount == "") || (Amount == 'null') || (Amount == 0)) {
						Amount = 0;
					}
					var Indicator = baseResponse
							.getElementsByTagName("Indicator")[g].firstChild.nodeValue;
					if (Indicator == "T_Altd_Amt_By_HO") {
						document.getElementById("T_Altd_Amt_By_HO").value = Amount;
					} else if (Indicator == "T_Altd_Amt_By_RN") {
						document.getElementById("T_Altd_Amt_By_RN").value = Amount;
					}
				}
			} else {

			}
			/** Delete Existing Values from Grid */
			var tbody = document.getElementById("grid_body");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			/** Delete Existing Values from Grid */
			var tbody = document.getElementById("grid_body1");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			var r_no = baseResponse.getElementsByTagName("Head_of_Account");
			var len = baseResponse.getElementsByTagName("Head_of_Account").length;
			seq = 0;
			if (len != 0) {
				var item = new Array();
				for ( var k = 0; k < r_no.length; k++) {

					item[0] = baseResponse
							.getElementsByTagName("Head_of_Account")[k].firstChild.nodeValue;

					item[1] = baseResponse
							.getElementsByTagName("BE_for_Next_Year")[k].firstChild.nodeValue;
					if ((item[1] == 0) || (item[1] == 'null')) {
						item[1] = "0";
					}

					item[2] = baseResponse
							.getElementsByTagName("Amount_to_be_Alloted")[k].firstChild.nodeValue;
					if ((item[2] == 0) || (item[8] == 'null')) {
						item[2] = "0";
					}
					item[3] = baseResponse
							.getElementsByTagName("Reason_for_Variation")[k].firstChild.nodeValue;
					if (item[3] == 'null') {
						item[3] = "";
					}

					item[4] = baseResponse
							.getElementsByTagName("Amount_Alloted")[k].firstChild.nodeValue;
					if ((item[4] == 0) || (item[4] == 'null')) {
						item[4] = "0";
					}

					/** Create Table Row */
					var mycurrent_row = document.createElement("TR");
					mycurrent_row.id = seq;

					/** Sl No */
					var cell0 = document.createElement("TD");
					var slno = document.createTextNode(seq + 1);
					cell0.appendChild(slno);
					mycurrent_row.appendChild(cell0);

					/** Head of Account */
					var cell2 = document.createElement("TD");
					var Head_of_Account = document.createElement('TEXTAREA',
							'option1');
					Head_of_Account.name = "Head_of_Account" + seq;
					Head_of_Account.id = "Head_of_Account" + seq;
					Head_of_Account.value = item[0];
					Head_of_Account.readOnly = true;
					Head_of_Account.setAttribute("cols", "5");
					Head_of_Account.style.height = "60px"
					Head_of_Account.style.width = "350px";
					cell2.appendChild(Head_of_Account);
					mycurrent_row.appendChild(cell2);

					/** Amount Alloted By Head Office */
					var cell3 = document.createElement("TD");
					var Amount_Alloted_By_HO = document.createElement("input");
					Amount_Alloted_By_HO.type = "Text";
					Amount_Alloted_By_HO.name = "Amount_Alloted_By_HO" + seq;
					Amount_Alloted_By_HO.id = "Amount_Alloted_By_HO" + seq;
					Amount_Alloted_By_HO.value = item[4] + ".00";
					Amount_Alloted_By_HO.style.textAlign = "right";
					Amount_Alloted_By_HO.readOnly = true;
					// Amount_Alloted_By_HO.maxLength = "10";
					// Amount_Alloted_By_HO.size = "10";
					cell3.appendChild(Amount_Alloted_By_HO);
					var currentText = document.createTextNode("");
					cell3.appendChild(currentText);
					mycurrent_row.appendChild(cell3);

					/** BE for Next Year */
					var cell20 = document.createElement("TD");
					var BE_for_Next_Year = document.createElement("input");
					BE_for_Next_Year.type = "Text";
					BE_for_Next_Year.name = "BE_for_Next_Year" + seq;
					BE_for_Next_Year.id = "BE_for_Next_Year" + seq;
					BE_for_Next_Year.value = item[1] + ".00";
					BE_for_Next_Year.style.textAlign = "right";
					// BE_for_Next_Year.maxLength = "10";
					// BE_for_Next_Year.size = "10";
					cell20.appendChild(BE_for_Next_Year);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/** Amount_to_be_Alloted */
					var cell20 = document.createElement("TD");
					var Amount_to_be_Alloted = document.createElement("input");
					Amount_to_be_Alloted.type = "Text";
					Amount_to_be_Alloted.name = "Amount_to_be_Alloted" + seq;
					Amount_to_be_Alloted.id = "Amount_to_be_Alloted" + seq;
					Amount_to_be_Alloted.value = item[2] + ".00";
					Amount_to_be_Alloted.style.textAlign = "right";
					// Amount_to_be_Alloted.maxLength = "10";
					// Amount_to_be_Alloted.size = "10";
					cell20.appendChild(Amount_to_be_Alloted);
					var currentText = document.createTextNode("");
					cell20.appendChild(currentText);
					mycurrent_row.appendChild(cell20);

					/** Reason for Variation */
					var cell9 = document.createElement("TD");
					var Reason_for_Variation = document.createElement(
							'TEXTAREA', 'option1');
					Reason_for_Variation.name = "Reason_for_Variation" + seq;
					Reason_for_Variation.id = "Reason_for_Variation" + seq;
					Reason_for_Variation.value = item[3];
					Reason_for_Variation.setAttribute("cols", "5");
					Reason_for_Variation.style.height = "60px"
					Reason_for_Variation.style.width = "150px";
					cell9.appendChild(Reason_for_Variation);
					mycurrent_row.appendChild(cell9);

					tbody.appendChild(mycurrent_row);

					/** Increment Sequence Number */
					seq = seq + 1;
				}
			} else {
				alert("Record Does Not Exist");
			}
			document.getElementById("RecordCount").value = seq;
		} else if (flag == "Freeze_Done") {
			alert("The Civil Budget Allocation Closure Have Already Freezed");
			clrForm();
		} else {
			alert("Failed to Load Data");
		}
	}
}
