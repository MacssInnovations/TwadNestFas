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

function initialLoads(path) {
	// alert(path);

	var today = new Date();
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	var url = path
			+ "/MTC70Register_Updated_By_Treasury_Section?command=gett&year="
			+ year;
	// alert(url);
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
			// alert("manipulate-command:--->>>"+command);

			if (command == "gett") {
				// alert("manipulate");
				firstLoad(baseResponse);
			} else if (command == "saveFunc") {
				// alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "getRegisterDate") {
				getMTCRegisterDate1(baseResponse);
			}
		}
	}
}

function firstLoad(baseResponse) {
	// alert("firstLoad");
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month >= 10) {
		var date = (day + "/" + month + "/" + year);
	} else {
		var date = (day + "/0" + month + "/" + year);
	}
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtUpdatedOn.value = date;

	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
	// alert(empid);
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtUpdatedBy.value = empid;

	var len1 = baseResponse.getElementsByTagName("mtc70RegisterNo").length;
	for ( var i = 0; i < len1; i++) {
		var mtc70RegisterNo = baseResponse
				.getElementsByTagName("mtc70RegisterNo")[i].firstChild.nodeValue;
		// alert(mtc70RegisterNo);
		var se = document.getElementById("MTCRegisterNo");
		var op = document.createElement("OPTION");
		op.value = mtc70RegisterNo;
		var txt = document.createTextNode(mtc70RegisterNo);
		op.appendChild(txt);
		se.appendChild(op);

	}

}
function getMTCRegisterDate(path) {
	// alert(path);
	var MTCRegisterNo = document.getElementById("MTCRegisterNo").value;
	var url = path
			+ "/MTC70Register_Updated_By_Treasury_Section?command=getRegisterDate&MTCRegisterNo="
			+ MTCRegisterNo;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
function getMTCRegisterDate1(baseResponse) {
	var mtc70RegisterDate = baseResponse
			.getElementsByTagName("mtc70RegisterDate")[0].firstChild.nodeValue;

	document.frm_MTC70Register_Updated_By_Treasury_Section.txtMTCEntryDate.value = mtc70RegisterDate;
}
function saveFunc(path) {
	var MTCRegisterNo = document.getElementById("MTCRegisterNo").value;
	var MTCEntryDate = document.getElementById("txtMTCEntryDate").value;
	var EmpID_mas = document.getElementById("txtEmpID_mas").value;
	var ReceivedOn = document.getElementById("ReceivedOn").value;
	var UpdatedOn = document.getElementById("txtUpdatedOn").value;
	var UpdatedBy = document.getElementById("txtUpdatedBy").value;
	var SenttoPreAuditon = document.getElementById("SenttoPreAuditon").value;
	var Remarks = document.getElementById("mtxtRemarks").value;

	var browser = navigator.appName;
	//alert(browser);
	if (browser == "Netscape") {
		var dd1 = MTCEntryDate.split('/');
		MTCEntryDate = dd1[1] + "/" + dd1[0] + "/" + dd1[2];

		var dd2 = ReceivedOn.split('/');
		ReceivedOn = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		
		var dd3 = UpdatedOn.split('/');
		UpdatedOn = dd3[1] + "/" + dd3[0] + "/" + dd3[2];
		
		var dd4 = SenttoPreAuditon.split('/');
		SenttoPreAuditon = dd4[1] + "/" + dd4[0] + "/" + dd4[2];

	}
	var a = MTCEntryDate.split('/');
	var b = ReceivedOn.split('/');
	var c = UpdatedOn.split('/');
	var d = SenttoPreAuditon.split('/');

	var eDate = new Date(a[2], a[0] - 1, a[1]);
	var rDate = new Date(b[2], b[0] - 1, b[1]);
	var sDate = new Date(c[2], c[0] - 1, c[1]);
	var spDate = new Date(d[2], d[0] - 1, d[1]);

	if ((MTCRegisterNo == "") || (document.getElementById("MTCRegisterNo").value == "s")) {
		alert("Select MTCRegisterNo Option in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.MTCRegisterNo.focus();
	} else if (MTCEntryDate == "") {
		alert("Enter MTC Entry Date in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtMTCEntryDate.focus();
		
	}else if (EmpID_mas == "") {
		alert("Enter Received By in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtEmpID_mas.focus();
	} else if (document.getElementById("ReceivedOn").value == "") {
		//alert("test");
		alert("Enter Received On in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.ReceivedOn.focus();
	} else if (document.getElementById("txtUpdatedOn").value == "") {
		alert("test");
		alert("Enter Received On in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtUpdatedOn.focus();
	} else if (document.getElementById("txtUpdatedBy").value == "") {
		//alert("test1");
		alert("Enter Updated By in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.txtUpdatedBy.focus();
	} else if (document.getElementById("SenttoPreAuditon").value == "") {
		alert("Enter Sent to Pre-Auditon in the Field");
		document.frm_MTC70Register_Updated_By_Treasury_Section.SenttoPreAuditon.focus();
	} else if (eDate > rDate) {
		alert("ReceivedOn must be greater than MTCEntryDate");
		return 0;
	} else if (rDate > sDate) {
		alert("Updated On must be greater than ReceivedOn");
		return 0;

	} else if (eDate > sDate) {
		alert("Updated On must be greater than MTCEntryDate");
		return 0;
	}else if (sDate > spDate) {
		alert("SenttoPre-Audit on must be greater than Updated On");
		return 0;
	}

	else {
	var url = path + "/MTC70Register_Updated_By_Treasury_Section?command=saveFunc&MTCRegisterNo="
			+ MTCRegisterNo + "&MTCEntryDate=" + MTCEntryDate + "&EmpID_mas="
			+ EmpID_mas + "&ReceivedOn=" + ReceivedOn + "&UpdatedOn="
			+ UpdatedOn + "&UpdatedBy=" + UpdatedBy + "&SenttoPreAuditon="
			+ SenttoPreAuditon + "&Remarks=" + Remarks;

	//alert(url);

	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
	}
}
function saveFunc1(baseResponse){
	var flag = baseResponse
	.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(flag == "success")
	{
		alert("Record Updated Successfully");
		refresh();
	}
}

function refresh()
{
	var xmlrequest = AjaxFunction();
	document.frm_MTC70Register_Updated_By_Treasury_Section.MTCRegisterNo.value = "s";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtMTCEntryDate.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.txtEmpID_mas.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.ReceivedOn.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.SenttoPreAuditon.value = "";
	document.frm_MTC70Register_Updated_By_Treasury_Section.mtxtRemarks.value = "";

}

function exitfun(path) {
	window.close();
}