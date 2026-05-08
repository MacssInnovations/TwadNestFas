// MIS_Monthly_Acc_Grouping_of_Consolidated_trial_Balance_Report //
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

			if (command == "printFunc") {
				// alert("manipulate saveFunc");
				printFunc1(baseResponse);
			}
		}
	}
}

function printFunc(path) {
	//alert(path);
//	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
//	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;

	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmMIS_Monthly_Acc_Grouping_of_Consolidated_trial_Balance_Report.txtCB_Year
				.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmMIS_Monthly_Acc_Grouping_of_Consolidated_trial_Balance_Report.txtCB_Month
				.focus();
	}else {
		var url = path
				+ "/MIS_Monthly_Acc_Grouping_of_Consolidated_trial_Balance_Report?command=printFunc&cboCashBook_Year=" + cboCashBook_Year
				+ "&cboCashBook_Month=" + cboCashBook_Month;
		//alert(url);
		document.frmMIS_Monthly_Acc_Grouping_of_Consolidated_trial_Balance_Report.action = url;
		document.frmMIS_Monthly_Acc_Grouping_of_Consolidated_trial_Balance_Report
				.submit();

	}
}

function printFunc1() {

}

function refresh() {
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.frmMIS_Monthly_Acc_Grouping_of_Consolidated_trial_Balance_Report.txtCB_Year.value = year;
	document.frmMIS_Monthly_Acc_Grouping_of_Consolidated_trial_Balance_Report.txtCB_Month.value = month;	
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
