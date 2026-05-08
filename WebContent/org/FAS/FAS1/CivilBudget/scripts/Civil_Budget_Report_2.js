//  //
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
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Year2 = document.getElementById("txtCB_Year2").value;

	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Financial Year in the Field");
		document.frmCivil_Budget_Report2.txtCB_Year.focus();
	} else if (cboCashBook_Year2 == "") {
		alert("Select Financial Month in the Field");
		document.frmCivil_Budget_Report2.txtCB_Month2.focus();
	}else {
		var url = path
				+ "/Civil_Budget_Report_2?command=printFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cboCashBook_Year=" + cboCashBook_Year
				+ "&cboCashBook_Year2=" + cboCashBook_Year2;
		//alert(url);
		document.frmCivil_Budget_Report2.action = url;
		document.frmCivil_Budget_Report2.submit();

	}
}

function printFunc1() {

}

function refresh() {
	var year1;
    var today= new Date(); 
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var year=today.getYear();
    if(year < 1900) year += 1900;
    if(month>3)           
	 {
	 year1 = year+1
	 }else{
	 year1 = year-1
	 }

	LoadAccountingUnitID('LIST_ALL_UNITS');
	if(month>3)           
	 {
	 document.frmCivil_Budget_Report2.txtCB_Year.value=year;
	document.frmCivil_Budget_Report2.txtCB_Year2.value=year1;
	 }else{
	document.frmCivil_Budget_Report2.txtCB_Year.value=year1;
	document.frmCivil_Budget_Report2.txtCB_Year2.value=year;
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

function exitfun(path) {
	window.close();
}
