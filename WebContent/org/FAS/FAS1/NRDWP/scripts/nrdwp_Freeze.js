//			Freeze NRDWP FREEZE		//
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
			} 
		}
	}
}


function FuncSave(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	if (cmbFinancialYear == "") {
		alert("select  Financial year");
		document.frmnrdwp_Freeze.cmbFreezeType.focus;
	} 
		var url = path
				+ "/nrdwp_Freeze?command=save&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cmbFinancialYear=" + cmbFinancialYear;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	
}

function save1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "Exist") {
		alert("NRDWP Budget have Already Freezed");
	} else if (flag == "success") {
		clrForm();
		alert("NRDWP Budget Freezed Successfully");
	} else {
		clrForm()
		alert("Record Insertion Failed");
	}
}

function clrForm() {
	LoadAccountingUnitID('LIST_ALL_UNITS');
	//document.getElementById("cmbFinancialYear").length = 1;
	document.getElementById("cmbFinancialYear").value = "";	

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
