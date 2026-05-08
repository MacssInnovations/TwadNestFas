
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




function Brs_reportFunction(path) {
	alert("test");
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url="";
	var url = path
	+ "/Brs_nonFreezed?command=PDF&cmbAcc_UnitCode="
	+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code;
	
		alert(url);
		document.BRSfreezedReport.action = url;
		document.BRSfreezedReport.submit();

	
}