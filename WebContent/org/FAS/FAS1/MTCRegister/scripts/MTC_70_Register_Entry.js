var seq = 0;

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

function initialLoad(path) {
	// alert(path);

	var url = path + "/MTC70Register_Entry?command=gett";

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

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			// alert("manipulate-command:--->>>"+command);

			if (command == "gett") {
				//	alert("manipulate");
				firstLoad(baseResponse);
			} else if (command == "saveFunc") {
				// alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "PONo") {
				// alert("manipulate");
				forPassOrderNo1(baseResponse);
			} else if (command == "PODate") {
				// alert("manipulate");
				forPassOrderDate1(baseResponse);
			}
		}
	}
}

function firstLoad(baseResponse) {

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
	document.frm_MTC_70_Register_Entry.txtMTCEntryDate.value = date;

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
		// alert("firstLoad");
		var MTCRegisterNO = baseResponse.getElementsByTagName("MTCRegisterNO")[0].firstChild.nodeValue;
		// alert(MTCRegisterNO);
		document.frm_MTC_70_Register_Entry.txtMTCRegisterNO.value = MTCRegisterNO;
	}

	var len1 = baseResponse.getElementsByTagName("empName").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("empName")[i].firstChild.nodeValue;
		// alert(empName);
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empName;
		var txt = document.createTextNode(empName);
		op.appendChild(txt);
		se.appendChild(op);

	}

	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
	// alert(MTCRegisterNO);
	document.frm_MTC_70_Register_Entry.txtEmpID_mas.value = empid;

	var len3 = baseResponse.getElementsByTagName("cachBookYear").length;
	for ( var i = 0; i < len3; i++) {
		var cachBookYear = baseResponse.getElementsByTagName("cachBookYear")[i].firstChild.nodeValue;
		var se = document.getElementById("cboCashBook_Year");
		var op = document.createElement("OPTION");
		op.value = cachBookYear;
		var txt = document.createTextNode(cachBookYear);
		op.appendChild(txt);
		se.appendChild(op);

	}
	var len4 = baseResponse.getElementsByTagName("cachBookMonth").length;
	for ( var i = 0; i < len4; i++) {
		var cachBookMonth = baseResponse.getElementsByTagName("cachBookMonth")[i].firstChild.nodeValue;
		var se = document.getElementById("cboCashBook_Month");
		var op = document.createElement("OPTION");
		op.value = cachBookMonth;
		var txt = document.createTextNode(cachBookMonth);
		op.appendChild(txt);
		se.appendChild(op);

	}

	// -------------------------------------------------------------------------------------------------------------------

}
function forPassOrderNo(path) {

	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var MTCEntryDate = document.getElementById("txtMTCEntryDate").value;
	
	if(cboCashBook_Month < 10)
	{
		cboCashBook_Month = "0" + cboCashBook_Month;
	}
	var txtMTCEntryDate = MTCEntryDate.split('/');
	if(cboCashBook_Month != txtMTCEntryDate[1])
	{
		document.frm_MTC_70_Register_Entry.txtMTCEntryDate.value = "";
	}
	var url = path + "/MTC70Register_Entry?command=PONo&cboOffice_code="
			+ cboOffice_code + "&cboAcc_UnitCode=" + cboAcc_UnitCode
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month;

	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
function forPassOrderNo1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if (flag == " success ") {
		document.frm_MTC_70_Register_Entry.cboPassOrderNo.length = 1;
		document.frm_MTC_70_Register_Entry.txtPassOrderDate.value = "";
		document.frm_MTC_70_Register_Entry.txtApprovedBy.value = "";
		
		var tbody = document.getElementById("tblList");
		var rowcount = tbody.rows.length;
		//alert(rowcount);
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			//alert(r);
			tbody.deleteRow(r);
		}
		
	var len55 = baseResponse.getElementsByTagName("passOrderNo").length;
	for ( var i = 0; i < len55; i++) {
		var passOrderNo = baseResponse.getElementsByTagName("passOrderNo")[i].firstChild.nodeValue;
		var se = document.getElementById("cboPassOrderNo");
		var op = document.createElement("OPTION");
		op.value = passOrderNo;
		var txt = document.createTextNode(passOrderNo);
		op.appendChild(txt);
		se.appendChild(op);

	}
	}else if(flag == " NoData ") {		
		alert("Record Does Not Exist");
	}
	else
	{
	alert("Failed to Load");
}

}
function forPassOrderDate(path) {
	// alert("path:-"+path);

	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboPassOrderNo = document.getElementById("cboPassOrderNo").value;

	var url = path + "/MTC70Register_Entry?command=PODate&cboPassOrderNo="
			+ cboPassOrderNo + "&cboOffice_code=" + cboOffice_code
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

function forPassOrderDate1(baseResponse) {

	var passOrderDate1 = baseResponse.getElementsByTagName("passOrderDate")[0].firstChild.nodeValue;
	var approvedBy1 = baseResponse.getElementsByTagName("approvedBy")[0].firstChild.nodeValue;

	document.frm_MTC_70_Register_Entry.txtPassOrderDate.value = passOrderDate1;
	document.frm_MTC_70_Register_Entry.txtApprovedBy.value = approvedBy1;

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		
		var tbody = document.getElementById("tblList");
		var rowcount = tbody.rows.length;
		//alert(rowcount);
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			//alert(r);
			tbody.deleteRow(r);
		}
		
		var len6 = baseResponse.getElementsByTagName("billNo").length;
		if(len6!=0)
		{
		for ( var k = 0; k <= len6; k++) {

			var ProceedingOrderNo = baseResponse
					.getElementsByTagName("proceedingNo")[k].firstChild.nodeValue;
			var ProceedingOrderDate = baseResponse
					.getElementsByTagName("proceedingDate")[k].firstChild.nodeValue;
			var billNo = baseResponse.getElementsByTagName("billNo")[k].firstChild.nodeValue;
			var billDate = baseResponse.getElementsByTagName("billDate")[k].firstChild.nodeValue;
			var billAmount = baseResponse.getElementsByTagName("billAmount")[k].firstChild.nodeValue;
			var Remarks = baseResponse.getElementsByTagName("Remarks")[k].firstChild.nodeValue;
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			var cell1 = document.createElement("TD");
			var tnodeProceedingOrderNo = document
					.createTextNode(ProceedingOrderNo);
			cell1.appendChild(tnodeProceedingOrderNo);
			mycurrent_row.appendChild(cell1);

			var cell2 = document.createElement("TD");
			var tnodeProceedingOrderDate = document
					.createTextNode(ProceedingOrderDate);
			cell2.appendChild(tnodeProceedingOrderDate);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var tnodebillNo = document.createTextNode(billNo);
			cell3.appendChild(tnodebillNo);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var tnodebillDate = document.createTextNode(billDate);
			cell4.appendChild(tnodebillDate);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var tnodebillAmount = document.createTextNode(billAmount);
			cell5.appendChild(tnodebillAmount);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var tnodeRemarks = document.createTextNode(Remarks);
			cell6.appendChild(tnodeRemarks);
			mycurrent_row.appendChild(cell6);

			tbody.appendChild(mycurrent_row);

		}
		}
		else
		{
			alert("Record Does Not Exist For Proceeding Details");
		}
	} else {

	}
}

function saveFunc(path) {
	// alert("path:-"+path);
	//alert("saveFunc");

	var t;
	var k = 0;
	var tbody = document.getElementById("tblList");
	var rowcount = tbody.rows.length;
	var al = new Array();
	for ( var i = 0; i < rowcount; i++) {
		var r = tbody.rows[i];
		var s = r.cells.length;
		for ( var j = 0; j < s; j++) {
			al[k] = r.cells[j].firstChild.nodeValue;
			k++;
		}

	}


	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboPassOrderNo = document.getElementById("cboPassOrderNo").value;
	//alert("cboPassOrderNo:-->>"+cboPassOrderNo);
	var txtMTCEntryDate = document.getElementById("txtMTCEntryDate").value;
	var cboEnteredBy = document.getElementById("txtEmpID_mas").value;
	var txtPassOrderDate = document.getElementById("txtPassOrderDate").value;
	var txtMTCRegisterNO = document.getElementById("txtMTCRegisterNO").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;
	var txtApprovedBy = document.getElementById("txtApprovedBy").value;
	
	var browser = navigator.appName;
	if (browser == "Netscape") {
		var dd1 = txtMTCEntryDate.split('/');
		txtMTCEntryDate = dd1[1] + "/" + dd1[0] + "/" + dd1[2];

		var dd2 = txtRefDate.split('/');
		txtRefDate = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
	}
	var a = txtMTCEntryDate.split('/');
	var b = txtRefDate.split('/');

	var MTCEntryDate = new Date(a[2], a[0] - 1, a[1]);
	var RefDate = new Date(b[2], b[0] - 1, b[1]);

	if ((document.getElementById("cboCashBook_Month").value == "")
			|| (document.getElementById("cboCashBook_Month").value <= 0)
			|| (document.getElementById("cboCashBook_Month").value == "s")) {
		alert("Select Cash Book Month in the Field");
		document.frm_MTC_70_Register_Entry.cboCashBook_Month.focus();
	}else if ((document.getElementById("cboPassOrderNo").value == "")
			|| (document.getElementById("cboPassOrderNo").value <= 0)
			|| (document.getElementById("cboPassOrderNo").value == "s")) {
		alert("Select Pass Order No Option in the Field");
		document.frm_MTC_70_Register_Entry.cboPassOrderNo.focus();
	} else if (document.getElementById("txtPassOrderDate").value == "") {
		alert("Enter Pass Order Date in the Field");
		document.frm_MTC_70_Register_Entry.txtPassOrderDate.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_MTC_70_Register_Entry.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_MTC_70_Register_Entry.txtRefDate.focus();
	} else if (rowcount <= 0) {
		alert("Record Does Not Exist For Proceeding Details");
	}else if (MTCEntryDate > RefDate) {
		alert("Rent Ref Date must be greater than MTC Entry Date");
		return 0;
	}else if (document.getElementById("txtApprovedBy").value == "") {
		alert("Enter Approved By in The Field");
	}else {
		var url = path
				+ "/MTC70Register_Entry?command=saveFunc&cboPassOrderNo="
				+ cboPassOrderNo + "&cboOffice_code=" + cboOffice_code
				+ "&cboAcc_UnitCode=" + cboAcc_UnitCode + "&cboCashBook_Year="
				+ cboCashBook_Year + "&cboCashBook_Month=" + cboCashBook_Month
				+ "&txtMTCEntryDate=" + txtMTCEntryDate + "&cboEnteredBy="
				+ cboEnteredBy + "&txtPassOrderDate=" + txtPassOrderDate
				+ "&txtMTCRegisterNO=" + txtMTCRegisterNO + "&txtRefNo="
				+ txtRefNo + "&txtRefDate=" + txtRefDate + "&mtxtRemarks="
				+ mtxtRemarks + "&txtApprovedBy=" + txtApprovedBy + "&al=" + al
				+ "&rowcount=" + rowcount;

		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

	}
}
function saveFunc1(baseResponse) {
	//alert("saveFunc1");

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");
		refresh();

	} else {
		alert("Failed to Add values.");
	}

}

function refresh() {
	
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	
	document.frm_MTC_70_Register_Entry.cboCashBook_Year.value = year
	document.frm_MTC_70_Register_Entry.cboCashBook_Month.value = "s";

	document.frm_MTC_70_Register_Entry.txtMTCEntryDate.value = "";
	document.frm_MTC_70_Register_Entry.cboPassOrderNo.length = 1;
	document.frm_MTC_70_Register_Entry.txtPassOrderDate.value = "";
	document.frm_MTC_70_Register_Entry.txtRefNo.value = "";
	document.frm_MTC_70_Register_Entry.txtRefDate.value = "";
	document.frm_MTC_70_Register_Entry.txtApprovedBy.value = "";
	document.frm_MTC_70_Register_Entry.mtxtRemarks.value = "";

	var tbody = document.getElementById("tblList");
	var rowcount = tbody.rows.length;
	//alert(rowcount);
	for ( var i = 0; i < rowcount; i++) {
		var r = tbody.rows[i];
		//alert(r);
		tbody.deleteRow(r);
	}

}

function exitfun(path) {	
	window.close();
}