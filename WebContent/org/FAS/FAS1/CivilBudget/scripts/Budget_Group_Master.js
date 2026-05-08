//			Budget_Group_Master			//

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
			} else if (command == "clear") {
				clear1(baseResponse);
			}
		}
	}
}

function initialLoad(path) {
	//alert(path);

	var url = path + "/Budget_Group_Master?command=getGrid";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function firstLoad(baseResponse) {

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if(flag1="success")
		{
		var BudgetGroupId = baseResponse.getElementsByTagName("BudgetGroupId")[0].firstChild.nodeValue;
		document.getElementById("txtBudgetGroupId").value = BudgetGroupId;
		}else{
			alert("Failed to Generate Budget Group Id")
		}
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len = baseResponse.getElementsByTagName("txtBudgetGroupId").length;
		for ( var k = 0; k < len; k++) {
			var txtBudgetGroupId = baseResponse
					.getElementsByTagName("txtBudgetGroupId")[k].firstChild.nodeValue;
			var txtBudgetGroupMajor = baseResponse
					.getElementsByTagName("txtBudgetGroupMajor")[k].firstChild.nodeValue;
			
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = txtBudgetGroupId;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + txtBudgetGroupId
					+ "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var txtBudgetGroupId = document.createTextNode(txtBudgetGroupId);
			cell2.appendChild(txtBudgetGroupId);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtBudgetGroupMajor = document
					.createTextNode(txtBudgetGroupMajor);
			cell3.appendChild(txtBudgetGroupMajor);
			mycurrent_row.appendChild(cell3);

			tbody.appendChild(mycurrent_row);
		}
	} else {
		alert("Fail to Load Grid");
	}
}

function add(path) {
	//alert(path);

	var txtBudgetGroupId = document.getElementById("txtBudgetGroupId").value;
	var txtBudgetGroupMajor = document.getElementById("txtBudgetGroupMajor").value;

	if (txtBudgetGroupId == "") {
		alert("Enter Budget Group Id in the Field");
		document.frmBudgetGroupMaster.txtBudgetGroupId.focus();
	} else if (txtBudgetGroupMajor == "") {
		alert("Enter Budget Group Major in the Field");
		document.frmBudgetGroupMaster.txtBudgetGroupMajor.focus();
	} else {

		var url = path + "/Budget_Group_Master?command=add&txtBudgetGroupId="
				+ txtBudgetGroupId + "&txtBudgetGroupMajor="
				+ txtBudgetGroupMajor;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function addRow(baseResponse) {
	
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if(flag1="success")
		{
		var BudgetGroupId = parseInt(baseResponse.getElementsByTagName("BudgetGroupId")[0].firstChild.nodeValue)+1;
		document.getElementById("txtBudgetGroupId").value = BudgetGroupId;
		}else{
			alert("Failed to Generate Budget Group Id")
		}
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");

		refresh();

		var txtBudgetGroupId = baseResponse
				.getElementsByTagName("txtBudgetGroupId")[0].firstChild.nodeValue;
		var txtBudgetGroupMajor = baseResponse
				.getElementsByTagName("txtBudgetGroupMajor")[0].firstChild.nodeValue;
		
		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = txtBudgetGroupId;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + txtBudgetGroupId + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var txtBudgetGroupId = document.createTextNode(txtBudgetGroupId);
		cell2.appendChild(txtBudgetGroupId);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var txtBudgetGroupMajor = document.createTextNode(txtBudgetGroupMajor);
		cell3.appendChild(txtBudgetGroupMajor);
		mycurrent_row.appendChild(cell3);
		
		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Budget Group Id is Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(txtBudgetGroupId) {	
	var r = document.getElementById(txtBudgetGroupId);
	var rcells = r.cells;

	document.frmBudgetGroupMaster.txtBudgetGroupId.value = rcells.item(1).firstChild.nodeValue;
	document.frmBudgetGroupMaster.txtBudgetGroupMajor.value = rcells.item(2).firstChild.nodeValue;	

	document.frmBudgetGroupMaster.onsubmit.disabled = true;
	document.frmBudgetGroupMaster.ondelete.disabled = false;
	document.frmBudgetGroupMaster.onupdate.disabled = false;
}

function update(path) {
	//alert(path);
	var txtBudgetGroupId = document.getElementById("txtBudgetGroupId").value;
	var txtBudgetGroupMajor = document.getElementById("txtBudgetGroupMajor").value;

	if (txtBudgetGroupId == "") {
		alert("Enter Budget Group Id in the Field");
		document.frmBudgetGroupMaster.txtBudgetGroupId.focus();
	} else if (txtBudgetGroupMajor == "") {
		alert("Enter Budget Group Major in the Field");
		document.frmBudgetGroupMaster.txtBudgetGroupMajor.focus();
	} else {

		var url = path
				+ "/Budget_Group_Master?command=update&txtBudgetGroupId="
				+ txtBudgetGroupId + "&txtBudgetGroupMajor="
				+ txtBudgetGroupMajor;

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

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if(flag1="success")
		{
		var BudgetGroupId = baseResponse.getElementsByTagName("BudgetGroupId")[0].firstChild.nodeValue;
		document.getElementById("txtBudgetGroupId").value = BudgetGroupId;
		}else{
			alert("Failed to Generate Budget Group Id");
		}
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
		var items = new Array();
		items[0] = baseResponse.getElementsByTagName("txtBudgetGroupId")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("txtBudgetGroupMajor")[0].firstChild.nodeValue;		

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
	var txtBudgetGroupId = document.getElementById("txtBudgetGroupId").value;
	if (txtBudgetGroupId == "") {
		alert("Enter Budget Group Id in the Field");
		document.frmBudgetGroupMaster.txtBudgetGroupId.focus();
	} else {
		var r = confirm("This Process will Delete the Records in 'Budget Group Master' and also the Related Records in 'Budget Heads Master' & 'Budget Heads Acc Heads Mapping'.Are U Sure to Continue?");
		if (r == true) {
			var url = path
					+ "/Budget_Group_Master?command=deleted&txtBudgetGroupId="
					+ txtBudgetGroupId;
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
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if(flag1="success")
		{
		var BudgetGroupId = baseResponse.getElementsByTagName("BudgetGroupId")[0].firstChild.nodeValue;
		document.getElementById("txtBudgetGroupId").value = BudgetGroupId;
		}else{
			alert("Failed to Generate Budget Group Id")
		}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Records in 'Budget Group Master' and also the Related Records in 'Budget Heads Master' & 'Budget Heads Acc Heads Mapping' Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function refresh() {		
	document.frmBudgetGroupMaster.txtBudgetGroupMajor.value = "";	
	document.frmBudgetGroupMaster.onsubmit.disabled = false;
	document.frmBudgetGroupMaster.ondelete.disabled = true;
	document.frmBudgetGroupMaster.onupdate.disabled = true;
}

function refresh1(path) {
	document.frmBudgetGroupMaster.txtBudgetGroupMajor.value = "";
	var url = path + "/Budget_Group_Master?command=clear";

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
	document.frmBudgetGroupMaster.onsubmit.disabled = false;
	document.frmBudgetGroupMaster.ondelete.disabled = true;
	document.frmBudgetGroupMaster.onupdate.disabled = true;
}

function clear1(baseResponse) {
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if(flag1="success")
		{
		var BudgetGroupId = baseResponse.getElementsByTagName("BudgetGroupId")[0].firstChild.nodeValue;
		document.getElementById("txtBudgetGroupId").value = BudgetGroupId;
		}else{
			alert("Failed to Generate Budget Group Id");
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

function exitfun() {
	window.close();
}
