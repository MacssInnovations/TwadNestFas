// List_of_InterBankTransfer_MultipleBanks //
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

			if (command == "gettGrid") {
				loadGrid(baseResponse);
			} else if (command == "getVoucherDetails") {
				getVoucherDetails1(baseResponse);
			}
		}
	}
}
function doFunction1(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value
	var cmbOffice_code = document.getElementById("cmbOffice_code").value
	var cmbStatus = document.getElementById("cmbStatus").value;

	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;

	var txtFromDate = document.getElementById("txtFrom_date").value;
	var txtToDate = document.getElementById("txtTo_date").value;

	if((txtFromDate!="") && (txtToDate!=""))
	var browser = navigator.appName;
	if (browser == "Netscape") {
		var dd1 = txtFromDate.split('/');
		txtFromDate = dd1[1] + "/" + dd1[0] + "/" + dd1[2];

		var dd2 = txtToDate.split('/');
		txtToDate = dd2[1] + "/" + dd2[0] + "/" + dd2[2];

	}
	var a = txtFromDate.split('/');
	var b = txtToDate.split('/');

	var eDate = new Date(a[2], a[0] - 1, a[1]);
	var rDate = new Date(b[2], b[0] - 1, b[1]);
	
	if (txtCB_Year == "") {
		alert("Enter Cash book Year in the Field");
		document.getElementById("txtCB_Year").focus();
	} else if (txtFromDate == "") {
		alert("Enter From Date in the Field");
		document.getElementById("txtFrom_date").focus();
	} else if (txtToDate == "") {
		alert("Enter To Date in the Field");
		document.getElementById("txtTo_date").focus();
	} else if (eDate >= rDate) {
		alert("To Date must be greater than From Date");
		return 0;
	} else {
		var url = path
				+ "/List_of_InterBankTransfer_MultipleBanks?command=gettGrid&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cmbStatus=" + cmbStatus + "&txtCB_Year=" + txtCB_Year
				+ "&txtCB_Month=" + txtCB_Month + "&txtFromDate=" + txtFromDate
				+ "&txtToDate=" + txtToDate;

		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

	}
}
function doFunction(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value
	var cmbOffice_code = document.getElementById("cmbOffice_code").value
	var cmbStatus = document.getElementById("cmbStatus").value;

	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;

	var txtFromDate = document.getElementById("txtFrom_date").value;
	var txtToDate = document.getElementById("txtTo_date").value;

	if (txtCB_Year == "") {
		alert("Enter Cash book Year in the Field");
		document.getElementById("txtCB_Year").focus();
	} else {
		var url = path
				+ "/List_of_InterBankTransfer_MultipleBanks?command=gettGrid&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cmbStatus=" + cmbStatus + "&txtCB_Year=" + txtCB_Year
				+ "&txtCB_Month=" + txtCB_Month + "&txtFromDate=" + "empty"
				+ "&txtToDate=" + "empty";

		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}
function getVoucherDetails1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var tbody = document.getElementById("tblList");
		var rowcount = tbody.rows.length;
		// alert(rowcount);
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			// alert(r);
			tbody.deleteRow(r);
		}

		var len6 = baseResponse.getElementsByTagName("TO_ACCOUNT_NO").length;
		// alert(len6);
		if (len6 != 0) {
			for ( var k = 0; k <= len6; k++) {

				var TO_ACCOUNT_NO = baseResponse
						.getElementsByTagName("TO_ACCOUNT_NO")[k].firstChild.nodeValue;
				var bk_br_city = baseResponse
						.getElementsByTagName("bk_br_city")[k].firstChild.nodeValue;
				var DR_ACCOUNT_HEAD_CODE = baseResponse
						.getElementsByTagName("DR_ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
				var CHEQUE_OR_DD = baseResponse
						.getElementsByTagName("CHEQUE_OR_DD")[k].firstChild.nodeValue;
				var CHEQUE_DD_NO = baseResponse
						.getElementsByTagName("CHEQUE_DD_NO")[k].firstChild.nodeValue;
				var CHEQUE_DD_DATE = baseResponse
						.getElementsByTagName("CHEQUE_DD_DATE")[k].firstChild.nodeValue;
				var TOTAL_AMOUNT = baseResponse
						.getElementsByTagName("TOTAL_AMOUNT")[k].firstChild.nodeValue;
				var REMARKS = baseResponse.getElementsByTagName("REMARKS")[k].firstChild.nodeValue;

				var tbody = document.getElementById("tblList");
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = TO_ACCOUNT_NO;

				var cell1 = document.createElement("TD");
				var bk_br_city = document.createTextNode(bk_br_city);
				cell1.appendChild(bk_br_city);
				mycurrent_row.appendChild(cell1);

				var cell2 = document.createElement("TD");
				var TO_ACCOUNT_NO = document.createTextNode(TO_ACCOUNT_NO);
				cell2.appendChild(TO_ACCOUNT_NO);
				mycurrent_row.appendChild(cell2);

				var cell3 = document.createElement("TD");
				var DR_ACCOUNT_HEAD_CODE = document
						.createTextNode(DR_ACCOUNT_HEAD_CODE);
				cell3.appendChild(DR_ACCOUNT_HEAD_CODE);
				mycurrent_row.appendChild(cell3);

				var cell4 = document.createElement("TD");
				var CHEQUE_OR_DD = document.createTextNode(CHEQUE_OR_DD);
				cell4.appendChild(CHEQUE_OR_DD);
				mycurrent_row.appendChild(cell4);

				var cell5 = document.createElement("TD");
				var CHEQUE_DD_NO = document.createTextNode(CHEQUE_DD_NO);
				cell5.appendChild(CHEQUE_DD_NO);
				mycurrent_row.appendChild(cell5);

				var cell6 = document.createElement("TD");
				var CHEQUE_DD_DATE = document.createTextNode(CHEQUE_DD_DATE);
				cell6.appendChild(CHEQUE_DD_DATE);
				mycurrent_row.appendChild(cell6);

				var cell7 = document.createElement("TD");
				var TOTAL_AMOUNT = document.createTextNode(TOTAL_AMOUNT);
				cell7.appendChild(TOTAL_AMOUNT);
				mycurrent_row.appendChild(cell7);

				var cell8 = document.createElement("TD");
				var REMARKS = document.createTextNode(REMARKS);
				cell8.appendChild(REMARKS);
				mycurrent_row.appendChild(cell8);

				tbody.appendChild(mycurrent_row);

			}
		} else {
			alert("Record Does Not Exist");
		}
	} else {

		alert("Failed to Load");
	}

}
function loadGrid(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       // alert(flag)
	if (flag == "success") {

		var tbody = document.getElementById("tbody");
		var rowcount = tbody.rows.length;
		// alert(rowcount);
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			// alert(r);
			tbody.deleteRow(r);
		}

		var len6 = baseResponse.getElementsByTagName("VOUCHER_NO").length;
		// alert(len6);
		if (len6 != 0) {
			for ( var k = 0; k <= len6; k++) {

				var VOUCHER_NO = baseResponse
						.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;
				var VOUCHER_NO1 = baseResponse
						.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;
				var DATE_OF_TRANSFER = baseResponse
						.getElementsByTagName("DATE_OF_TRANSFER")[k].firstChild.nodeValue;
				var bk_br_city = baseResponse
						.getElementsByTagName("bk_br_city")[k].firstChild.nodeValue;
				var FROM_ACCOUNT_NO = baseResponse
						.getElementsByTagName("FROM_ACCOUNT_NO")[k].firstChild.nodeValue;
				var TOTAL_AMOUNT = baseResponse
						.getElementsByTagName("TOTAL_AMOUNT")[k].firstChild.nodeValue;
				var REF_NO = baseResponse.getElementsByTagName("REF_NO")[k].firstChild.nodeValue;
				var REF_DATE = baseResponse.getElementsByTagName("REF_DATE")[k].firstChild.nodeValue;
				var PARTICULARS = baseResponse
						.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;

				var tbody = document.getElementById("tbody");
				var table = document.getElementById("Existing");
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = VOUCHER_NO;

				var cell1 = document.createElement("TD");
				var VOUCHER_NO = document.createTextNode(VOUCHER_NO);
				cell1.appendChild(VOUCHER_NO);
				mycurrent_row.appendChild(cell1);

				var cell2 = document.createElement("TD");
				var DATE_OF_TRANSFER = document
						.createTextNode(DATE_OF_TRANSFER);
				cell2.appendChild(DATE_OF_TRANSFER);
				mycurrent_row.appendChild(cell2);

				var cell3 = document.createElement("TD");
				var bk_br_city = document.createTextNode(bk_br_city);
				cell3.appendChild(bk_br_city);
				mycurrent_row.appendChild(cell3);

				var cell4 = document.createElement("TD");
				var FROM_ACCOUNT_NO = document.createTextNode(FROM_ACCOUNT_NO);
				cell4.appendChild(FROM_ACCOUNT_NO);
				mycurrent_row.appendChild(cell4);

				var cell5 = document.createElement("TD");
				var TOTAL_AMOUNT = document.createTextNode(TOTAL_AMOUNT);
				cell5.appendChild(TOTAL_AMOUNT);
				mycurrent_row.appendChild(cell5);

				var cell6 = document.createElement("TD");
				var REF_NO = document.createTextNode(REF_NO);
				cell6.appendChild(REF_NO);
				mycurrent_row.appendChild(cell6);

				var cell7 = document.createElement("TD");
				var REF_DATE = document.createTextNode(REF_DATE);
				cell7.appendChild(REF_DATE);
				mycurrent_row.appendChild(cell7);

				var cell8 = document.createElement("TD");
				var PARTICULARS = document.createTextNode(PARTICULARS);
				cell8.appendChild(PARTICULARS);
				mycurrent_row.appendChild(cell8);

				var cell = document.createElement("TD");
				var anc = document.createElement("A");
				var url = "javascript:list('" + VOUCHER_NO1 + "')";

				anc.href = url;
				var txtedit = document.createTextNode("DETAILS");
				anc.appendChild(txtedit);
				cell.appendChild(anc);
				mycurrent_row.appendChild(cell);

				tbody.appendChild(mycurrent_row);

			}
		} else {
			alert("Record Does Not Exist");
		}
	} else {

		alert("Failed to Load");
	}

}

function list(VOUCHER_NO) {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value
	var cmbOffice_code = document.getElementById("cmbOffice_code").value
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;

	winemp = window.open(
			"List_of_InterBankTransfer_MultipleBanks_Details.jsp?cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month="
					+ txtCB_Month + "&VOUCHER_NO=" + VOUCHER_NO, "list",
			"status=1,height=500,width=600,resizable=YES, scrollbars=yes");
	winemp.moveTo(250, 250);
	winemp.focus();
}

function initialLoad(path, cboAcc_UnitCode, cboOffice_code, cboCashBook_Year,
		cboCashBook_Month, VOUCHER_NO) {
	//alert(path);

	var url = path
			+ "/List_of_InterBankTransfer_MultipleBanks?command=getVoucherDetails&VOUCHER_NO="
			+ VOUCHER_NO + "&cboOffice_code=" + cboOffice_code
			+ "&cboAcc_UnitCode=" + cboAcc_UnitCode + "&cboCashBook_Year="
			+ cboCashBook_Year + "&cboCashBook_Month=" + cboCashBook_Month;

	// alert(url);
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

function exitfun(path) {
	window.close();
}
