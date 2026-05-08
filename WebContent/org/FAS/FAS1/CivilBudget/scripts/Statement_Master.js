//	Statement_Master		//
var winemp;
var seq = 0;
var common = "";
var length = 0;
var flag, command, baseResponse="";
var pagesize = 10;
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
			} /*else if (command == "getGrid") {
				changepagesize(baseResponse);
			}*/
		}
	}
}

function changepagesize() {	
	//alert(pagesize);
	pagesize = document.getElementById("cmbpagination").value;
	//alert(pagesize = document.getElementById("cmbpagination").value);
	//var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	var len = baseResponse.getElementsByTagName("txtStatementNo").length;	
	var cmbpage = document.getElementById("cmbpage");
	try {	
		cmbpage.innerHTML = "";
	} catch (e) {
		cmbpage.innerText = "";
	}	
	var i = 1;
	for (i = 1; i <= ((len / pagesize) + 1); i++) {
		var option = document.createElement("OPTION");
		option.text = i;
		option.value = i;
		try {
			cmbpage.add(option);
		} catch (errorObject) {
			cmbpage.add(option, null);
		}
	}
	firstLoad();
	
}

function initialLoad(path) {
	//alert(path);

	var url = path + "/Statement_Master?command=getGrid";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		//manipulate(xmlrequest);
		viewdata(xmlrequest);
	}

	xmlrequest.send(null);

}

function firstLoad() {
		
	var tlist = document.getElementById("tblList");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	var len = baseResponse.getElementsByTagName("txtStatementNo").length;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var pageno = document.getElementById("cmbpage").value;
		var ul = 0, ll = 0;
		ul = pageno * pagesize;
		ll = ul - pagesize;
		try{
		for ( var k = ll; k < ul; k++) {
			var txtStatementNo = baseResponse.getElementsByTagName("txtStatementNo")[k].firstChild.nodeValue;
			var txtStatementDesc = baseResponse
					.getElementsByTagName("txtStatementDesc")[k].firstChild.nodeValue;
			
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = txtStatementNo;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + txtStatementNo + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var txtStatementNo = document.createTextNode(txtStatementNo);
			cell2.appendChild(txtStatementNo);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtStatementDesc = document.createTextNode(txtStatementDesc);
			cell3.appendChild(txtStatementDesc);
			mycurrent_row.appendChild(cell3);

			tbody.appendChild(mycurrent_row);
		}
		}catch(e)
		{}
			
	} else {
		alert("Fail to Load Grid");
	}
}
function viewdata(xmlrequest)
{
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
      //alert(xmlrequest.responseText);
	  baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	  changepagesize();
		}
		
	}
}
function add(path) {
	//alert(path);

	var txtStatementNo = document.getElementById("txtStatementNo").value;
	var txtStatementDesc = document.getElementById("txtStatementDesc").value;
	
	if (txtStatementNo == "") {
		alert("Enter Statement No in the Field");
		document.frmStatement_Master.txtStatementNo.focus();
	} else if (txtStatementDesc == "") {
		alert("Enter Statement Desc Main in the Field");
		document.frmStatement_Master.txtStatementDesc.focus();
	} else {

		var url = path + "/Statement_Master?command=add&txtStatementNo="
				+ txtStatementNo + "&txtStatementDesc=" + txtStatementDesc;

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

		var txtStatementNo = baseResponse.getElementsByTagName("txtStatementNo")[0].firstChild.nodeValue;
		var txtStatementDesc = baseResponse
				.getElementsByTagName("txtStatementDesc")[0].firstChild.nodeValue;
		
		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = txtStatementNo;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + txtStatementNo + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var txtStatementNo = document.createTextNode(txtStatementNo);
		cell2.appendChild(txtStatementNo);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var txtStatementDesc = document.createTextNode(txtStatementDesc);
		cell3.appendChild(txtStatementDesc);
		mycurrent_row.appendChild(cell3);

		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Statement No is Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(txtStatementNo) {
	document.getElementById("txtStatementNo").disabled = true;
	var r = document.getElementById(txtStatementNo);
	var rcells = r.cells;

	document.frmStatement_Master.txtStatementNo.value = rcells.item(1).firstChild.nodeValue;
	document.frmStatement_Master.txtStatementDesc.value = rcells.item(2).firstChild.nodeValue;

	document.frmStatement_Master.onsubmit.disabled = true;
	document.frmStatement_Master.ondelete.disabled = false;
	document.frmStatement_Master.onupdate.disabled = false;
}

function update(path) {
	//alert(path);
	var txtStatementNo = document.getElementById("txtStatementNo").value;
	var txtStatementDesc = document.getElementById("txtStatementDesc").value;	

	if (txtStatementNo == "") {
		alert("Enter Statement No in the Field");
		document.frmStatement_Master.txtStatementNo.focus();
	} else if (txtStatementDesc == "") {
		alert("Enter Statement Desc Main in the Field");
		document.frmStatement_Master.txtStatementDesc.focus();
	} else {

		var url = path + "/Statement_Master?command=update&txtStatementNo="
				+ txtStatementNo + "&txtStatementDesc=" + txtStatementDesc;

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
		items[0] = baseResponse.getElementsByTagName("txtStatementNo")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("txtStatementDesc")[0].firstChild.nodeValue;
		
		var r = document.getElementById(items[0]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];		
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Failed to update values");
	}
}

function deleteeee(path) {
	//alert(path);
	var txtStatementNo = document.getElementById("txtStatementNo").value;
	if (txtStatementNo == "") {
		alert("Enter Statement No in the Field");
		document.frmStatement_Master.txtStatementNo.focus();
	} else {
		var r = confirm("Are U Sure to Continue?");
		if (r) {
			var url = path + "/Statement_Master?command=deleted&txtStatementNo="
					+ txtStatementNo;
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
		alert("Records in 'Statement Master' Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function refresh() {
	document.getElementById("txtStatementNo").disabled = false;
	document.frmStatement_Master.txtStatementNo.value = "";
	document.frmStatement_Master.txtStatementDesc.value = "";
	
	document.frmStatement_Master.onsubmit.disabled = false;
	document.frmStatement_Master.ondelete.disabled = true;
	document.frmStatement_Master.onupdate.disabled = true;

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
