//			MIS_Major_Grouping			//

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
			var baseResponse="";
			baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
                       var tagCommand = baseResponse.getElementsByTagName("command")[0];
                       
			var command = tagCommand.firstChild.nodeValue;

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse);
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
	var budgetGroup="";
	var url=""; 
	if(document.getElementById('cmbBudgetGroupMajor').value==""){
		url = path + "/MIS_Major_Grouping?command=getGrid";
	}else{
		budgetGroup=document.getElementById('cmbBudgetGroupMajor').value;
		url = path + "/MIS_Major_Grouping?command=getGrid&budgetGroup="+budgetGroup;
	}
	
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}

function firstLoad(baseResponse) {
	var majorId=document.getElementById('cmbBudgetGroupMajor').value;
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 = "success") {
		var Group_Head_Code = baseResponse
				.getElementsByTagName("Group_Head_Code")[0].firstChild.nodeValue;
		document.getElementById("txtGroup_Head_Code").value = Group_Head_Code;
	} else {
		alert("Failed to Generate Group Head Code");
	}

	var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag2 == "success") {
		var len1 = baseResponse.getElementsByTagName("BudgetIdMain").length;	
		var select=document.getElementById('cmbBudgetGroupMajor');
		var listOpt=document.createElement("option");
		select.length=0;
		select.appendChild(listOpt);
		listOpt.text="select";
		listOpt.value="select";
		for(var i=0; i<len1; i++){
			listOpt=document.createElement("option");
			select.appendChild(listOpt);
			listOpt.text=baseResponse.getElementsByTagName("BudgetDescMain")[i].firstChild.nodeValue;
			listOpt.value=baseResponse.getElementsByTagName("BudgetIdMain")[i].firstChild.nodeValue;
		}
		select.value=majorId;
		
	} else {
		alert("Fail to Load Major Head Code");
	}
	try{
		document.getElementById('tblList').innerHTML="";
	}catch(e){
		document.getElementById('tblList').innerText="";
	}		
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len = baseResponse.getElementsByTagName("txtGroup_Head_Code").length;
		for ( var k = 0; k < len; k++) {
			var txtGroup_Head_Code = baseResponse
					.getElementsByTagName("txtGroup_Head_Code")[k].firstChild.nodeValue;
			var cmbBudgetGroupMajor = baseResponse
					.getElementsByTagName("cmbBudgetGroupMajor")[k].firstChild.nodeValue;
			var txtGroup_Head_Desc = baseResponse
					.getElementsByTagName("txtGroup_Head_Desc")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = txtGroup_Head_Code;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + txtGroup_Head_Code
					+ "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var cmbBudgetGroupMajor = document
					.createTextNode(cmbBudgetGroupMajor);
			cell2.appendChild(cmbBudgetGroupMajor);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtGroup_Head_Code = document
					.createTextNode(txtGroup_Head_Code);
			cell3.appendChild(txtGroup_Head_Code);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var txtGroup_Head_Desc = document
					.createTextNode(txtGroup_Head_Desc);
			cell4.appendChild(txtGroup_Head_Desc);
			mycurrent_row.appendChild(cell4);

			tbody.appendChild(mycurrent_row);
		}
	} else {
		alert("Fail to Load Grid");
	}
}

function add(path) {
	//alert(path);

	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var txtGroup_Head_Code = document.getElementById("txtGroup_Head_Code").value;
	var txtGroup_Head_Desc = document.getElementById("txtGroup_Head_Desc").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmMIS_Major_Grouping.cmbBudgetGroupMajor.focus();
	} else if (txtGroup_Head_Code == "") {
		alert("Enter Group Head Code in the Field");
		document.frmMIS_Major_Grouping.txtGroup_Head_Code.focus();
	} else if (txtGroup_Head_Desc == "") {
		alert("Enter Group Head Desc in the Field");
		document.frmMIS_Major_Grouping.txtGroup_Head_Desc.focus();
	} else {

		var url = path + "/MIS_Major_Grouping?command=add&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor + "&txtGroup_Head_Code="
				+ txtGroup_Head_Code + "&txtGroup_Head_Desc="
				+ txtGroup_Head_Desc;

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

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 = "success") {
		var Group_Head_Code = parseInt(baseResponse
				.getElementsByTagName("Group_Head_Code")[0].firstChild.nodeValue);
		document.getElementById("txtGroup_Head_Code").value = Group_Head_Code;
	} else {
		alert("Failed to Generate Group Head Code");
	}

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");

		refresh();

		var cmbBudgetGroupMajor = baseResponse
				.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
		var txtGroup_Head_Code = baseResponse
				.getElementsByTagName("txtGroup_Head_Code")[0].firstChild.nodeValue;
		var txtGroup_Head_Desc = baseResponse
				.getElementsByTagName("txtGroup_Head_Desc")[0].firstChild.nodeValue;

		var tbody = document.getElementById("tblList");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = txtGroup_Head_Code;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + txtGroup_Head_Code
				+ "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var cmbBudgetGroupMajor = document.createTextNode(cmbBudgetGroupMajor);
		cell2.appendChild(cmbBudgetGroupMajor);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var txtGroup_Head_Code = document.createTextNode(txtGroup_Head_Code);
		cell3.appendChild(txtGroup_Head_Code);
		mycurrent_row.appendChild(cell3);

		var cell4 = document.createElement("TD");
		var txtGroup_Head_Desc = document.createTextNode(txtGroup_Head_Desc);
		cell4.appendChild(txtGroup_Head_Desc);
		mycurrent_row.appendChild(cell4);

		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Data are Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(txtGroup_Head_Code) {
	var r = document.getElementById(txtGroup_Head_Code);
	var rcells = r.cells;

	document.frmMIS_Major_Grouping.cmbBudgetGroupMajor.value = rcells.item(1).firstChild.nodeValue;
	document.frmMIS_Major_Grouping.txtGroup_Head_Code.value = rcells.item(2).firstChild.nodeValue;
	document.frmMIS_Major_Grouping.txtGroup_Head_Desc.value = rcells.item(3).firstChild.nodeValue;

	document.frmMIS_Major_Grouping.onsubmit.disabled = true;
	document.frmMIS_Major_Grouping.ondelete.disabled = false;
	document.frmMIS_Major_Grouping.onupdate.disabled = false;
}

function update(path) {
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var txtGroup_Head_Code = document.getElementById("txtGroup_Head_Code").value;
	var txtGroup_Head_Desc = document.getElementById("txtGroup_Head_Desc").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmMIS_Major_Grouping.cmbBudgetGroupMajor.focus();
	} else if (txtGroup_Head_Code == "") {
		alert("Enter Group Head Code in the Field");
		document.frmMIS_Major_Grouping.txtGroup_Head_Code.focus();
	} else if (txtGroup_Head_Desc == "") {
		alert("Enter Group Head Desc in the Field");
		document.frmMIS_Major_Grouping.txtGroup_Head_Desc.focus();
	} else {

		var url = path
				+ "/MIS_Major_Grouping?command=update&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor + "&txtGroup_Head_Code="
				+ txtGroup_Head_Code + "&txtGroup_Head_Desc="
				+ txtGroup_Head_Desc;

		//alert(url);
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
	if (flag1 = "success") {
		var Group_Head_Code = baseResponse
				.getElementsByTagName("Group_Head_Code")[0].firstChild.nodeValue;
		document.getElementById("txtGroup_Head_Code").value = Group_Head_Code;
	} else {
		alert("Failed to Generate MIS Major Group Id");
	}

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
		var items = new Array();
		items[0] = baseResponse.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("txtGroup_Head_Code")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("txtGroup_Head_Desc")[0].firstChild.nodeValue;

		var r = document.getElementById(items[1]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Failed to update values");
	}
}

function deletee(path) {
	//alert(path);
	var txtGroup_Head_Code = document
			.getElementById("txtGroup_Head_Code").value;
	if (txtGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmMIS_Major_Grouping.txtGroup_Head_Code.focus();
	} else {
		var r = confirm("This Process will Delete the Records in  'MIS Grouping Master' and also the Related Records in 'MIS Group and Acc Head Mapping' Are U Sure to Continue?");
		if (r == true) {
			var url = path
					+ "/MIS_Major_Grouping?command=deleted&txtGroup_Head_Code="
					+ txtGroup_Head_Code;
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
	if (flag1 = "success") {
		var Group_Head_Code = parseInt(baseResponse
				.getElementsByTagName("Group_Head_Code")[0].firstChild.nodeValue);
		document.getElementById("txtGroup_Head_Code").value = Group_Head_Code;
	} else {
		alert("Failed to Generate Group Head Code");
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Records in  'MIS Grouping Master' and also the Related Records in 'MIS Group and Acc Head Mapping' Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function refresh() {
	document.frmMIS_Major_Grouping.cmbBudgetGroupMajor.value = "";
	document.frmMIS_Major_Grouping.txtGroup_Head_Desc.value = "";

	document.frmMIS_Major_Grouping.onsubmit.disabled = false;
	document.frmMIS_Major_Grouping.ondelete.disabled = true;
	document.frmMIS_Major_Grouping.onupdate.disabled = true;
}

function refresh1(path) {
	var url = path + "/MIS_Major_Grouping?command=clear";

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function clear1(baseResponse) {
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 = "success") {
		var MIS_Major_Group_Id = baseResponse
				.getElementsByTagName("MIS_Major_Group_Id")[0].firstChild.nodeValue;
		document.getElementById("txtGroup_Head_Code").value = MIS_Major_Group_Id;
	} else {
		alert("Failed to Generate MIS_Major Group Id");
	}
	document.frmMIS_Major_Grouping.cmbBudgetGroupMajor.value = "";
	document.frmMIS_Major_Grouping.txtGroup_Head_Desc.value = "";

	document.frmMIS_Major_Grouping.onsubmit.disabled = false;
	document.frmMIS_Major_Grouping.ondelete.disabled = true;
	document.frmMIS_Major_Grouping.onupdate.disabled = true;
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
