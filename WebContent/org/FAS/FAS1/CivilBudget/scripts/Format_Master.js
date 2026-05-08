//			Format_Master			//

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

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse)
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "Edit") {
				Edit1(baseResponse);
			} else if (command == "getGrid") {
				firstLoad(baseResponse);
			}
		}
	}
}

function initialLoad(path) {
	//alert(path);

	var url = path + "/Format_Master?command=getGrid";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}

function firstLoad(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len = baseResponse.getElementsByTagName("txtFormatNo").length;
		for ( var k = 0; k < len; k++) {
			var txtFormatNo = baseResponse.getElementsByTagName("txtFormatNo")[k].firstChild.nodeValue;
			var txtFormatDescMain = baseResponse
					.getElementsByTagName("txtFormatDescMain")[k].firstChild.nodeValue;
			var txtFormatDescSub = baseResponse
					.getElementsByTagName("txtFormatDescSub")[k].firstChild.nodeValue;
			if ((txtFormatDescSub == "") || (txtFormatDescSub == 'null')) {
				txtFormatDescSub = "";
			}
			var txtFormatType = baseResponse
					.getElementsByTagName("txtFormatType")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = txtFormatNo;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + txtFormatNo + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var txtFormatNo = document.createTextNode(txtFormatNo);
			cell2.appendChild(txtFormatNo);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtFormatDescMain = document.createTextNode(txtFormatDescMain);
			cell3.appendChild(txtFormatDescMain);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var txtFormatDescSub = document.createTextNode(txtFormatDescSub);
			cell4.appendChild(txtFormatDescSub);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var txtFormatType = document.createTextNode(txtFormatType);
			cell5.appendChild(txtFormatType);
			mycurrent_row.appendChild(cell5);

			tbody.appendChild(mycurrent_row);
		}
	} else {
		alert("Fail to Load Grid");
	}
}

function add(path) {
	//alert(path);

	var txtFormatNo = document.getElementById("txtFormatNo").value;
	var txtFormatDescMain = document.getElementById("txtFormatDescMain").value;
	var txtFormatDescSub = document.getElementById("txtFormatDescSub").value;
	if(document.frmFormat_Master.txtFormatType[0].checked==true)
	{
		txtFormatType=document.frmFormat_Master.txtFormatType[0].value;
	}
   else
	{
	   txtFormatType=document.frmFormat_Master.txtFormatType[1].value;
	}

	if (txtFormatNo == "") {
		alert("Enter Format No in the Field");
		document.frmFormat_Master.txtFormatNo.focus();
	} else if (txtFormatDescMain == "") {
		alert("Enter Format Desc Main in the Field");
		document.frmFormat_Master.txtFormatDescMain.focus();
	} else {

		var url = path + "/Format_Master?command=add&txtFormatNo="
				+ txtFormatNo + "&txtFormatDescMain=" + txtFormatDescMain
				+ "&txtFormatDescSub=" + txtFormatDescSub + "&txtFormatType="
				+ txtFormatType;

		//alert(url);
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

		var txtFormatNo = baseResponse.getElementsByTagName("txtFormatNo")[0].firstChild.nodeValue;
		var txtFormatDescMain = baseResponse
				.getElementsByTagName("txtFormatDescMain")[0].firstChild.nodeValue;
		var txtFormatDescSub = baseResponse
				.getElementsByTagName("txtFormatDescSub")[0].firstChild.nodeValue;
		if ((txtFormatDescSub == "") || (txtFormatDescSub == 'null')) {
			txtFormatDescSub = "";
		}
		var txtFormatType = baseResponse.getElementsByTagName("txtFormatType")[0].firstChild.nodeValue;

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = txtFormatNo;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + txtFormatNo + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var txtFormatNo = document.createTextNode(txtFormatNo);
		cell2.appendChild(txtFormatNo);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var txtFormatDescMain = document.createTextNode(txtFormatDescMain);
		cell3.appendChild(txtFormatDescMain);
		mycurrent_row.appendChild(cell3);

		var cell4 = document.createElement("TD");
		var txtFormatDescSub = document.createTextNode(txtFormatDescSub);
		cell4.appendChild(txtFormatDescSub);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var txtFormatType = document.createTextNode(txtFormatType);
		cell5.appendChild(txtFormatType);
		mycurrent_row.appendChild(cell5);

		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Format No is Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(txtFormatNo) {
	document.getElementById("txtFormatNo").disabled = true;
	var r = document.getElementById(txtFormatNo);
	var rcells = r.cells;

	document.frmFormat_Master.txtFormatNo.value = rcells.item(1).firstChild.nodeValue;
	document.frmFormat_Master.txtFormatDescMain.value = rcells.item(2).firstChild.nodeValue;
	document.frmFormat_Master.txtFormatDescSub.value = rcells.item(3).firstChild.nodeValue;
	if(rcells.item(4).firstChild.nodeValue == "P")
	{
		document.frmFormat_Master.txtFormatType[0].checked = true;
	}
	else
	{
		document.frmFormat_Master.txtFormatType[1].checked = true;
	}	
	document.frmFormat_Master.onsubmit.disabled = true;
	document.frmFormat_Master.ondelete.disabled = false;
	document.frmFormat_Master.onupdate.disabled = false;
}

function update(path) {
	//alert(path);
	var txtFormatNo = document.getElementById("txtFormatNo").value;
	var txtFormatDescMain = document.getElementById("txtFormatDescMain").value;
	var txtFormatDescSub = document.getElementById("txtFormatDescSub").value;
	if(document.frmFormat_Master.txtFormatType[0].checked==true)
	{
		txtFormatType=document.frmFormat_Master.txtFormatType[0].value;
	}
   else
	{
	   txtFormatType=document.frmFormat_Master.txtFormatType[1].value;
	}

	if (txtFormatNo == "") {
		alert("Enter Format No in the Field");
		document.frmFormat_Master.txtFormatNo.focus();
	} else if (txtFormatDescMain == "") {
		alert("Enter Format Desc Main in the Field");
		document.frmFormat_Master.txtFormatDescMain.focus();
	} else {

		var url = path + "/Format_Master?command=update&txtFormatNo="
				+ txtFormatNo + "&txtFormatDescMain=" + txtFormatDescMain
				+ "&txtFormatDescSub=" + txtFormatDescSub + "&txtFormatType="
				+ txtFormatType;

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
		items[0] = baseResponse.getElementsByTagName("txtFormatNo")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("txtFormatDescMain")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("txtFormatDescSub")[0].firstChild.nodeValue;
		if ((items[2] == "") || (items[2] == 'null')) {
			items[2] = "";
		}
		items[3] = baseResponse.getElementsByTagName("txtFormatType")[0].firstChild.nodeValue;

		var r = document.getElementById(items[0]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];
		rcells.item(4).firstChild.nodeValue = items[3];
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Failed to update values");
	}
}

function deleteeee(path) {
	//alert(path);
	var txtFormatNo = document.getElementById("txtFormatNo").value;
	if (txtFormatNo == "") {
		alert("Enter Format No in the Field");
		document.frmFormat_Master.txtFormatNo.focus();
	} else {
		var r = confirm("This Process will Delete the Records in 'Format Master' and also the Related Records in 'Budget Heads Master' & 'Budget Heads A/c heads mapping'.Are U Sure to Continue?");
		if (r == true) {
			var url = path + "/Format_Master?command=deleted&txtFormatNo="
					+ txtFormatNo;
			// alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			}
			xmlrequest.send(null);
		}
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
		alert("Records in 'Format Master'and also the Related Records in 'Budget Heads Master' & 'Budget Heads A/c heads mapping' Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function refresh() {
	document.getElementById("txtFormatNo").disabled = false;
	document.frmFormat_Master.txtFormatNo.value = "";
	document.frmFormat_Master.txtFormatDescMain.value = "";
	document.frmFormat_Master.txtFormatDescSub.value = "";	
	
	document.frmFormat_Master.txtFormatType[0].checked = true
	
	document.frmFormat_Master.onsubmit.disabled = false;
	document.frmFormat_Master.ondelete.disabled = true;
	document.frmFormat_Master.onupdate.disabled = true;

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
