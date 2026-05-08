

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

function journal_Report_date() {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtFrom_date = document.getElementById("txtFrom_date").value;
	var txtTo_date=document.getElementById("txtTo_date").value;
	/*var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;*/
	var txtCB_Year=0;
	var txtCB_Month=0;
	var sl_type=document.getElementById("sl_type").value;
	
	var url="";
		url = "../../../../../../Jornal_Report?command=Jornal_Report_date&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+"&Year="+txtCB_Year+"&Month="+txtCB_Month
				+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date;
			/*	+"&sl_type="+sl_type;*/
		alert(url);
		document.journal_report.action = url;
		document.journal_report.submit();
}
function journal_Report_month() {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtFrom_date = document.getElementById("txtFrom_date").value;
	var txtTo_date=document.getElementById("txtTo_date").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	var sl_type=document.getElementById("sl_type").value;
	
	var url="";
		url = "../../../../../../Jornal_Report?command=Jornal_Report_month&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+"&Year="+txtCB_Year+"&Month="+txtCB_Month
				+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date;
			/*	+
				"&sl_type="+sl_type;*/
		alert(url);
		document.journal_report.action = url;
		document.journal_report.submit();
}