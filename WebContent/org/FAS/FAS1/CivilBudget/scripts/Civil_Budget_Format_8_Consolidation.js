//		Civil_Budget_Format_8_Consolidation		//

var seq = 0;
var seq1 = 1;
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
			}
		}
	}
}
function initialLoad1() {

	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1 = fy.split('-');
	var y1 = fy1[0];
	var y2 = fy1[1];

	document.frmCivil_Budget_Format_8_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_8_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_8_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_8_Consolidation.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_8_Consolidation?command=get&y1=" + y1
			+ "&y2=" + y2 + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}
function initialLoad() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
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

	document.frmCivil_Budget_Format_8_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_8_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_8_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_8_Consolidation.butUpdate.disabled = true;
}

function firstLoad(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
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

				item[0] = baseResponse.getElementsByTagName("Head_of_Account")[k].firstChild.nodeValue;
				if ((item[0] == 0) || (item[0] == 'null')) {
					item[0] = "0";
				}
				item[1] = baseResponse
						.getElementsByTagName("BUDGET_GROUP_MAJOR")[k].firstChild.nodeValue;
				if ((item[1] == 0) || (item[1] == 'null')) {
					item[1] = "";
				}
				item[2] = baseResponse.getElementsByTagName("BE_for_the_Year")[k].firstChild.nodeValue;
				if ((item[2] == 0) || (item[2] == 'null')) {
					item[2] = "0";
				}
				item[3] = baseResponse
						.getElementsByTagName("Anticipated_Dec_to_End_of_CY")[k].firstChild.nodeValue;
				if ((item[3] == 0) || (item[3] == 'null')) {
					item[3] = "0";
				}
				item[4] = baseResponse.getElementsByTagName("Total")[k].firstChild.nodeValue;
				if ((item[4] == 0) || (item[4] == 'null')) {
					item[4] = "0";
				}
				item[5] = baseResponse.getElementsByTagName("Next_Year")[k].firstChild.nodeValue;
				if ((item[5] == 0) || (item[5] == 'null')) {
					item[5] = "0";
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
				Head_of_Account.value = item[1];
				Head_of_Account.readOnly = true;
				Head_of_Account.setAttribute("cols", "5");
				Head_of_Account.style.height = "60px"
				Head_of_Account.style.width = "350px";
				cell2.appendChild(Head_of_Account);
				mycurrent_row.appendChild(cell2);

				/** BE for the Year */
				var cell4 = document.createElement("TD");
				var BE_for_the_Year = document.createElement("input");
				BE_for_the_Year.type = "hidden";
				BE_for_the_Year.name = "BE_for_the_Year" + seq;
				BE_for_the_Year.id = "BE_for_the_Year" + seq;
				BE_for_the_Year.value = item[2] + ".00";
				cell4.appendChild(BE_for_the_Year);
				cell4.style.textAlign = "right";
				var currentText = document.createTextNode(item[2] + ".00");
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);

				/** Anticipated Dec to End of CY */
				var cell6 = document.createElement("TD");
				var Anticipated_Dec_to_End_of_CY = document
						.createElement("input");
				Anticipated_Dec_to_End_of_CY.type = "Text";
				Anticipated_Dec_to_End_of_CY.name = "Anticipated_Dec_to_End_of_CY"
						+ seq;
				Anticipated_Dec_to_End_of_CY.id = "Anticipated_Dec_to_End_of_CY"
						+ seq;
				Anticipated_Dec_to_End_of_CY.value = item[3] + ".00";
				Anticipated_Dec_to_End_of_CY.setAttribute('onchange', "Total("
						+ seq + ")");
				Anticipated_Dec_to_End_of_CY.style.textAlign = "right";
				cell6.appendChild(Anticipated_Dec_to_End_of_CY);
				var currentText = document.createTextNode("");
				cell6.appendChild(currentText);
				mycurrent_row.appendChild(cell6);

				/** Total */
				var cell7 = document.createElement("TD");
				var Total = document.createElement("input");
				Total.type = "Text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				Total.value = item[4] + ".00";
				Total.readOnly = true;
				Total.style.textAlign = "right";
				cell7.appendChild(Total);
				var currentText = document.createTextNode("");
				cell7.appendChild(currentText);
				mycurrent_row.appendChild(cell7);

				/** Next Year */
				var cell8 = document.createElement("TD");
				var Next_Year = document.createElement("input");
				Next_Year.type = "Text";
				Next_Year.name = "Next_Year" + seq;
				Next_Year.id = "Next_Year" + seq;
				Next_Year.value = item[5] + ".00";
				Next_Year.style.textAlign = "right";
				cell8.appendChild(Next_Year);
				var currentText = document.createTextNode("");
				cell8.appendChild(currentText);
				mycurrent_row.appendChild(cell8);

				/** budger_g_id */
				var budger_g_id = document.createElement("input");
				budger_g_id.setAttribute("type", "hidden");
				budger_g_id.setAttribute("value", item[0]);
				budger_g_id.setAttribute("name", "budger_g_id" + seq);
				budger_g_id.setAttribute("id", "budger_g_id" + seq);
				document.getElementById("frmCivil_Budget_Format_8_Consolidation")
						.appendChild(budger_g_id);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
			document.getElementById("RecordCount").value = seq;
		} else {
			alert("Record Does Not Exist");
		}
	} else if (flag == "Freeze_Pending") {
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
	} else {
		alert("Failed to Load Data");
	}
}

function LoadData(path) {
	//alert(path);
	document.frmCivil_Budget_Format_8_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_8_Consolidation.butSub.disabled = true;
	document.frmCivil_Budget_Format_8_Consolidation.butDelete.disabled = false;
	document.frmCivil_Budget_Format_8_Consolidation.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;
	var url = path + "/Civil_Budget_Format_8_Consolidation?filter=view&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
			+ RecordCount;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function LoadData_View(baseResponse) {
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

				item[0] = baseResponse.getElementsByTagName("Head_of_Account")[k].firstChild.nodeValue;
				if ((item[0] == 0) || (item[0] == 'null')) {
					item[0] = "0";
				}
				item[1] = baseResponse
						.getElementsByTagName("BUDGET_GROUP_MAJOR")[k].firstChild.nodeValue;
				if ((item[1] == 0) || (item[1] == 'null')) {
					item[1] = "";
				}
				item[2] = baseResponse.getElementsByTagName("BE_for_the_Year")[k].firstChild.nodeValue;
				if ((item[2] == 0) || (item[2] == 'null')) {
					item[2] = "0";
				}
				item[3] = baseResponse
						.getElementsByTagName("Anticipated_Dec_to_End_of_CY")[k].firstChild.nodeValue;
				if ((item[3] == 0) || (item[3] == 'null')) {
					item[3] = "0";
				}
				item[4] = baseResponse.getElementsByTagName("Total")[k].firstChild.nodeValue;
				if ((item[4] == 0) || (item[4] == 'null')) {
					item[4] = "0";
				}
				item[5] = baseResponse.getElementsByTagName("Next_Year")[k].firstChild.nodeValue;
				if ((item[5] == 0) || (item[5] == 'null')) {
					item[5] = "0";
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
				Head_of_Account.value = item[1];
				Head_of_Account.readOnly = true;
				Head_of_Account.setAttribute("cols", "5");
				Head_of_Account.style.height = "60px"
				Head_of_Account.style.width = "350px";
				cell2.appendChild(Head_of_Account);
				mycurrent_row.appendChild(cell2);

				/** BE for the Year */
				var cell4 = document.createElement("TD");
				var BE_for_the_Year = document.createElement("input");
				BE_for_the_Year.type = "hidden";
				BE_for_the_Year.name = "BE_for_the_Year" + seq;
				BE_for_the_Year.id = "BE_for_the_Year" + seq;
				BE_for_the_Year.value = item[2] + ".00";
				cell4.appendChild(BE_for_the_Year);
				cell4.style.textAlign = "right";
				var currentText = document.createTextNode(item[2] + ".00");
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);

				/** Anticipated Dec to End of CY */
				var cell6 = document.createElement("TD");
				var Anticipated_Dec_to_End_of_CY = document
						.createElement("input");
				Anticipated_Dec_to_End_of_CY.type = "Text";
				Anticipated_Dec_to_End_of_CY.name = "Anticipated_Dec_to_End_of_CY"
						+ seq;
				Anticipated_Dec_to_End_of_CY.id = "Anticipated_Dec_to_End_of_CY"
						+ seq;
				Anticipated_Dec_to_End_of_CY.value = item[3] + ".00";
				Anticipated_Dec_to_End_of_CY.setAttribute('onchange', "Total("
						+ seq + ")");
				Anticipated_Dec_to_End_of_CY.style.textAlign = "right";
				cell6.appendChild(Anticipated_Dec_to_End_of_CY);
				var currentText = document.createTextNode("");
				cell6.appendChild(currentText);
				mycurrent_row.appendChild(cell6);

				/** Total */
				var cell7 = document.createElement("TD");
				var Total = document.createElement("input");
				Total.type = "Text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				Total.value = item[4] + ".00";
				Total.readOnly = true;
				Total.style.textAlign = "right";
				cell7.appendChild(Total);
				var currentText = document.createTextNode("");
				cell7.appendChild(currentText);
				mycurrent_row.appendChild(cell7);

				/** Next Year */
				var cell8 = document.createElement("TD");
				var Next_Year = document.createElement("input");
				Next_Year.type = "Text";
				Next_Year.name = "Next_Year" + seq;
				Next_Year.id = "Next_Year" + seq;
				Next_Year.value = item[5] + ".00";
				Next_Year.style.textAlign = "right";
				cell8.appendChild(Next_Year);
				var currentText = document.createTextNode("");
				cell8.appendChild(currentText);
				mycurrent_row.appendChild(cell8);

				/** budger_g_id */
				var budger_g_id = document.createElement("input");
				budger_g_id.setAttribute("type", "hidden");
				budger_g_id.setAttribute("value", item[0]);
				budger_g_id.setAttribute("name", "budger_g_id" + seq);
				budger_g_id.setAttribute("id", "budger_g_id" + seq);
				document.getElementById("frmCivil_Budget_Format_8_Consolidation")
						.appendChild(budger_g_id);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
		} else {
			alert("Record Does Not Exist");
		}
		document.getElementById("RecordCount").value = seq;
	} else if (flag == "Exist") {
		alert("Format-8 Consolidation have Already Freezed");
		clrForm();
	} else {
		alert("Failed to Load Data");
	}
}

function funcSave() {
	document.getElementById("filter").value = "save";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {
		for ( var i = 0; i < rowcount; i++) {
			if (document
					.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).value == "") {
				alert("Enter Anticipated for the Period Dec to Mar in the Field");
				document
						.getElementById("Anticipated_for_Period_Dec_to_Mar" + i)
						.focus();
				return false;
			} else if (document.getElementById("RE_for_Year" + i).value == "") {
				alert("Enter RE for the Year in the Field");
				document.getElementById("RE_for_Year" + i).focus();
				return false;
			} else if (document.getElementById("BE_for_Next_Year" + i).value == "") {
				alert("Enter BE for Next Year in the Field");
				document.getElementById("BE_for_Next_Year" + i).focus();
				return false;
			} else {
				return true;
			}
		}
	} else {
		alert("No Records Found to Insert...");
		return false;
	}
}
function funcUpdate() {
	document.getElementById("filter").value = "update";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {
		for ( var i = 0; i < rowcount; i++) {
			if (document
					.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).value == "") {
				alert("Enter Anticipated for the Period Dec to Mar in the Field");
				document
						.getElementById("Anticipated_for_Period_Dec_to_Mar" + i)
						.focus();
				return false;
			} else if (document.getElementById("RE_for_Year" + i).value == "") {
				alert("Enter RE for the Year in the Field");
				document.getElementById("RE_for_Year" + i).focus();
				return false;
			} else if (document.getElementById("BE_for_Next_Year" + i).value == "") {
				alert("Enter BE for Next Year in the Field");
				document.getElementById("BE_for_Next_Year" + i).focus();
				return false;
			} else {
				return true;
			}
		}
	} else {
		alert("No Records Found to Insert...");
		return false;
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
function Total(sam) {
	var BE_for_the_Year = parseFloat(document.getElementById("BE_for_the_Year"
			+ sam).value);
	var Anticipated_Dec_to_End_of_CY = parseFloat(document
			.getElementById("Anticipated_Dec_to_End_of_CY" + sam).value);
	var tot = BE_for_the_Year + Anticipated_Dec_to_End_of_CY;
	document.getElementById("Total" + sam).value = tot;
}

function clrForm() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	LoadAccountingUnitID('LIST_ALL_UNITS');

	document.frmCivil_Budget_Format_8_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_8_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_8_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_8_Consolidation.butUpdate.disabled = true;
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
