//                      Barred_Cheque_FollowUp_List           //

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

			if (command == "list") {
				loadGrid(baseResponse);
			}
		}
	}
}

function list(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBarred_Cheque_FollowUp_List.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBarred_Cheque_FollowUp_List.txtCB_Month.focus();
	} else if (cmbBankAccNo == "") {
		alert("Select Bank Account No in the Field");
		document.frmBarred_Cheque_FollowUp_List.cmbBankAccNo.focus();
	} else {
		var url = path
				+ "/Barred_Cheque_FollowUp_List?command=list&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);

	}
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

		var len = baseResponse.getElementsByTagName("Cheque_No").length;
		if (len != 0) {
			for ( var k = 0; k < len; k++) {
				var Cheque_No = baseResponse.getElementsByTagName("Cheque_No")[k].firstChild.nodeValue;
				var Doc_Type = baseResponse.getElementsByTagName("Doc_Type")[k].firstChild.nodeValue;
				var Doc_No = baseResponse.getElementsByTagName("Doc_No")[k].firstChild.nodeValue;
				var ChequeDate = baseResponse.getElementsByTagName("ChequeDate")[k].firstChild.nodeValue;
				var DocDate = baseResponse.getElementsByTagName("DocDate")[k].firstChild.nodeValue;
				var ChequeAmount = baseResponse.getElementsByTagName("ChequeAmount")[k].firstChild.nodeValue;
				var CheckValidUpto = baseResponse.getElementsByTagName("CheckValidUpto")[k].firstChild.nodeValue;
				var Follow_Up = baseResponse.getElementsByTagName("Follow_Up")[k].firstChild.nodeValue;

				var tbody = document.getElementById("tblList");
				var table = document.getElementById("Existing");
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = Cheque_No;

				var cell = document.createElement("TD");
				var sno = document.createTextNode(k+1);
				cell.appendChild(sno);
				mycurrent_row.appendChild(cell);
				
				var cell2 = document.createElement("TD");
				var Doc_Type = document.createTextNode(Doc_Type);
				cell2.appendChild(Doc_Type);
				mycurrent_row.appendChild(cell2);

				var cell3 = document.createElement("TD");
				var Cheque_No = document.createTextNode(Cheque_No);
				cell3.appendChild(Cheque_No);
				mycurrent_row.appendChild(cell3);

				var cell4 = document.createElement("TD");
				var Doc_No = document.createTextNode(Doc_No);
				cell4.appendChild(Doc_No);
				mycurrent_row.appendChild(cell4);

				var cell5 = document.createElement("TD");
				var DocDate = document.createTextNode(DocDate);
				cell5.appendChild(DocDate);
				mycurrent_row.appendChild(cell5);

				var cell6 = document.createElement("TD");
				var ChequeDate = document.createTextNode(ChequeDate);
				cell6.appendChild(ChequeDate);
				mycurrent_row.appendChild(cell6);

				var cell7 = document.createElement("TD");
				var ChequeAmount = document.createTextNode(ChequeAmount);
				cell7.appendChild(ChequeAmount);
				mycurrent_row.appendChild(cell7);

				var cell8 = document.createElement("TD");
				var CheckValidUpto = document.createTextNode(CheckValidUpto);
				cell8.appendChild(CheckValidUpto);
				mycurrent_row.appendChild(cell8);

				var cell9 = document.createElement("TD");
				var Follow_Up = document.createTextNode(Follow_Up);
				cell9.appendChild(Follow_Up);
				mycurrent_row.appendChild(cell9);

				tbody.appendChild(mycurrent_row);
			}
		} else {
			alert("Record Does Not Exist");
		}
	} else {
		alert("Fail to Load");
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

function refresh()
{
	var tbody = document.getElementById("tblList");
	var table = document.getElementById("Existing");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.frmBarred_Cheque_FollowUp_List.txtCB_Year.value = year;
	document.frmBarred_Cheque_FollowUp_List.txtCB_Month.value = month;
	document.getElementById("cmbBankAccNo").value = "";
}
function exitfun() {
	window.close();
}