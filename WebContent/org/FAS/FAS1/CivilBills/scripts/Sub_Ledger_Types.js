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

function initialLoad() {
//	alert(path);
	var acc_unitid=document.getElementById("cmbAcc_UnitCode").value;
//	alert("sathya SSS"+acc_unitid);
	var url ="../../../../../SubLedgerTypes?command=gett&accunitid="+acc_unitid;
//	 alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}
//added as on 01MAr2012
function initialLoadNew()
{
			var url = "../../../../../SubLedgerTypes?command=gettall";
			//alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);  
			}

			xmlrequest.send(null);
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
			} else if (command == "gett") {
				loadGrid(baseResponse);
			} else if (command == "gett1") {
				forRefresh1(baseResponse);
			} else if (command == "loadValuesFromTable") {
				loadValuesFromTable1(baseResponse);
			}
			else if (command == "gettall") {
				loadGrid(baseResponse);
			}
			
		}
	}
}

function add(path) {
	// alert(path);
	var cmbAcc_UnitCode = document.SubLedgerTypes.cmbAcc_UnitCode.value;
	var cmbOffice_code = document.SubLedgerTypes.cmbOffice_code.value;
	var cbosubLedgerType = document.SubLedgerTypes.cbosubLedgerType.value;
	var txtName = document.SubLedgerTypes.txtName.value;
	var mtxtAddress = document.SubLedgerTypes.mtxtAddress.value;
	var txtPhone = document.SubLedgerTypes.txtPhone.value;

	if ((document.SubLedgerTypes.cmbAcc_UnitCode.value == "")
			|| (document.SubLedgerTypes.cmbAcc_UnitCode.value.length <= 0)
			|| (document.SubLedgerTypes.cmbAcc_UnitCode.value == "S")) {
		alert("Select Accounting Unit in the Field");
		document.SubLedgerTypes.cmbAcc_UnitCode.focus();
	} else if ((document.SubLedgerTypes.cmbOffice_code.value == "")
			|| (document.SubLedgerTypes.cmbOffice_code.value.length <= 0)
			|| (document.SubLedgerTypes.cmbOffice_code.value == "S")) {
		alert("Select Accounting For Office in the Field");
		document.SubLedgerTypes.cmbOffice_code.focus();
	} else if ((document.SubLedgerTypes.cbosubLedgerType.value == "")
			|| (document.SubLedgerTypes.cbosubLedgerType.value.length <= 0)
			|| (document.SubLedgerTypes.cbosubLedgerType.value == "s")) {
		alert("Select sub Ledger Type in the Field");
		document.SubLedgerTypes.cbosubLedgerType.focus();
	} else if (txtName == "") {
		alert("Enter Name in the Field");
		document.SubLedgerTypes.txtName.focus();
	} else if (mtxtAddress == "") {
		alert("Enter Address in the Field");
		document.SubLedgerTypes.mtxtAddress.focus();
	} else if (txtPhone == "") {
		alert("Enter Phone in the Field");
		document.SubLedgerTypes.txtPhone.focus();
	} else {

		var url = path + "/SubLedgerTypes?command=add&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtName=" + txtName + "&mtxtAddress=" + mtxtAddress
				+ "&txtPhone=" + txtPhone;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function addRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");

		refresh();

		var AccountingUnit = baseResponse
				.getElementsByTagName("AccountingUnit")[0].firstChild.nodeValue;
		var AccountingForOffice = baseResponse
				.getElementsByTagName("AccountingForOffice")[0].firstChild.nodeValue;
		var Name = baseResponse.getElementsByTagName("Name")[0].firstChild.nodeValue;
		var Address = baseResponse.getElementsByTagName("Address")[0].firstChild.nodeValue;
		var phone = baseResponse.getElementsByTagName("phone")[0].firstChild.nodeValue;
		var typeID = baseResponse.getElementsByTagName("typeID")[0].firstChild.nodeValue;

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = typeID;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + typeID + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var tnodeAccountingUnit = document.createTextNode(AccountingUnit);
		cell2.appendChild(tnodeAccountingUnit);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var tnodeAccountingForOffice = document
				.createTextNode(AccountingForOffice);
		cell3.appendChild(tnodeAccountingForOffice);
		mycurrent_row.appendChild(cell3);
		// --------------------------------------------------------

		var cell4 = document.createElement("TD");
		var tnodeName = document.createTextNode(Name);
		cell4.appendChild(tnodeName);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var tnodeAddress = document.createTextNode(Address);
		cell5.appendChild(tnodeAddress);
		mycurrent_row.appendChild(cell5);

		var cell6 = document.createElement("TD");
		var tnodephone = document.createTextNode(phone);
		cell6.appendChild(tnodephone);
		mycurrent_row.appendChild(cell6);

		tbody.appendChild(mycurrent_row);

	} else {
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(idd) {
	//alert(idd);
	var r = document.getElementById(idd);
	var rcells = r.cells;

	var officeid = rcells.item(2).firstChild.nodeValue;
	//alert(officeid);
	var url = "../../../../../SubLedgerTypes?command=loadValuesFromTable&officeid="
			+ officeid + "&idd=" + idd;

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function loadValuesFromTable1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var idd = baseResponse.getElementsByTagName("idd")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len = baseResponse.getElementsByTagName("officeid").length;
		for ( var k = 0; k < len; k++) {
			var officeid = baseResponse.getElementsByTagName("officeid")[k].firstChild.nodeValue;
			var officename = baseResponse.getElementsByTagName("officename")[k].firstChild.nodeValue;

			var se = document.getElementById("cmbOffice_code");
			var op = document.createElement("OPTION");
			op.value = officeid;
			var txt = document.createTextNode(officename);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	var r = document.getElementById(idd);
	var rcells = r.cells;

	document.SubLedgerTypes.cmbAcc_UnitCode.value = rcells.item(1).firstChild.nodeValue;
	document.SubLedgerTypes.cmbOffice_code.value = rcells.item(2).firstChild.nodeValue;
	document.SubLedgerTypes.txtName.value = rcells.item(3).firstChild.nodeValue;
	document.SubLedgerTypes.mtxtAddress.value = rcells.item(4).firstChild.nodeValue;
	document.SubLedgerTypes.txtPhone.value = rcells.item(5).firstChild.nodeValue;
	document.SubLedgerTypes.typeid.value = idd;

	document.SubLedgerTypes.onsubmit.disabled = true;
	document.SubLedgerTypes.ondelete.disabled = false;
	document.SubLedgerTypes.onupdate.disabled = false;
}

function loadGrid(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var len = baseResponse.getElementsByTagName("typeID").length;
		for ( var k = 0; k < len; k++) {
			var AccountingUnit = baseResponse
					.getElementsByTagName("AccountingUnit")[k].firstChild.nodeValue;
			var AccountingForOffice = baseResponse
					.getElementsByTagName("AccountingForOffice")[k].firstChild.nodeValue;
			var Name = baseResponse.getElementsByTagName("Name")[k].firstChild.nodeValue;
			var Address = baseResponse.getElementsByTagName("Address")[k].firstChild.nodeValue;
			var phone = baseResponse.getElementsByTagName("phone")[k].firstChild.nodeValue;
			var typeID = baseResponse.getElementsByTagName("typeID")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = typeID;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + typeID + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var tnodeAccountingUnit = document.createTextNode(AccountingUnit);
			cell2.appendChild(tnodeAccountingUnit);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var tnodeAccountingForOffice = document
					.createTextNode(AccountingForOffice);
			cell3.appendChild(tnodeAccountingForOffice);
			mycurrent_row.appendChild(cell3);
			// --------------------------------------------------------

			var cell4 = document.createElement("TD");
			var tnodeName = document.createTextNode(Name);
			cell4.appendChild(tnodeName);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var tnodeAddress = document.createTextNode(Address);
			cell5.appendChild(tnodeAddress);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var tnodephone = document.createTextNode(phone);
			cell6.appendChild(tnodephone);
			mycurrent_row.appendChild(cell6);

			tbody.appendChild(mycurrent_row);

		}

	} else {
		alert("Fail to Load");
	}

}

function deleteeee(path) {
	var typeid = document.SubLedgerTypes.typeid.value;
	// alert("DELETE:========>>>>>>>"+typeid);
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path + "/SubLedgerTypes?command=deleted&typeid=" + typeid;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function deleteRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Deleted Successfully");
		refresh();
	} else {
		alert("Unable to Delete");
	}
}

function update(path) {
	//alert("update");
	var cmbAcc_UnitCode = document.SubLedgerTypes.cmbAcc_UnitCode.value;
	var cmbOffice_code = document.SubLedgerTypes.cmbOffice_code.value;
	var cbosubLedgerType = document.SubLedgerTypes.cbosubLedgerType.value;
	var txtName = document.SubLedgerTypes.txtName.value;
	var mtxtAddress = document.SubLedgerTypes.mtxtAddress.value;
	var txtPhone = document.SubLedgerTypes.txtPhone.value;
	var typeid = document.SubLedgerTypes.typeid.value;

	if ((document.SubLedgerTypes.cmbAcc_UnitCode.value == "")
			|| (document.SubLedgerTypes.cmbAcc_UnitCode.value.length <= 0)
			|| (document.SubLedgerTypes.cmbAcc_UnitCode.value == "S")) {
		alert("Select Accounting Unit in the Field");
		document.SubLedgerTypes.cmbAcc_UnitCode.focus();
	} else if ((document.SubLedgerTypes.cmbOffice_code.value == "")
			|| (document.SubLedgerTypes.cmbOffice_code.value.length <= 0)
			|| (document.SubLedgerTypes.cmbOffice_code.value == "S")) {
		alert("Select Accounting For Office in the Field");
		document.SubLedgerTypes.cmbOffice_code.focus();
	} else if ((document.SubLedgerTypes.cbosubLedgerType.value == "")
			|| (document.SubLedgerTypes.cbosubLedgerType.value.length <= 0)
			|| (document.SubLedgerTypes.cbosubLedgerType.value == "s")) {
		alert("Select sub Ledger Type in the Field");
		document.SubLedgerTypes.cbosubLedgerType.focus();
	} else if (txtName == "") {
		alert("Enter Name in the Field");
		document.SubLedgerTypes.txtName.focus();
	} else if (mtxtAddress == "") {
		alert("Enter Address in the Field");
		document.SubLedgerTypes.mtxtAddress.focus();
	} else if (txtPhone == "") {
		alert("Enter Phone in the Field");
		document.SubLedgerTypes.txtPhone.focus();
	} else {

		var url = path + "/SubLedgerTypes?command=update&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtName=" + txtName + "&mtxtAddress=" + mtxtAddress
				+ "&txtPhone=" + txtPhone + "&typeid=" + typeid;

		// alert(url);
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
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();

		var items = new Array();

		items[0] = baseResponse.getElementsByTagName("AccountingUnit")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("AccountingForOffice")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("Name")[0].firstChild.nodeValue;
		items[3] = baseResponse.getElementsByTagName("Address")[0].firstChild.nodeValue;
		items[4] = baseResponse.getElementsByTagName("phone")[0].firstChild.nodeValue;
		items[5] = baseResponse.getElementsByTagName("typeID")[0].firstChild.nodeValue;

		var r = document.getElementById(items[5]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];
		rcells.item(4).firstChild.nodeValue = items[3];
		rcells.item(5).firstChild.nodeValue = items[4];

	} else {
		alert("Failed to update values");
	}
}

function refresh() {
	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.SubLedgerTypes.txtName.value = "";
	document.SubLedgerTypes.mtxtAddress.value = "";
	document.SubLedgerTypes.txtPhone.value = "";

	document.SubLedgerTypes.onsubmit.disabled = false;
	document.SubLedgerTypes.ondelete.disabled = true;
	document.SubLedgerTypes.onupdate.disabled = true;
	document.SubLedgerTypes.txtName.focus;

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