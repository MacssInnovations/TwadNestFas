//	Annual_Account_Sub_Group	//

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
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "Edit") {
				Edit1(baseResponse);
			} else if (command == "FirstLoad1") {
				firstLoad(baseResponse);
			} else if (command == "LoadMajorandSubBudgetHeads") {
				LoadMajorandSubBudgetHeads1(baseResponse);
			} else if (command == "LoadData") {
				LoadData1(baseResponse);
			} else if (command == "getMinorBudgetHeadDesc") {
				getMinorBudgetHeadDesc1(baseResponse);
			} else if (command == "LoadAccHdCode") {
				LoadAccHdCode1(baseResponse);
			} else if (command == "LoadAccHdDesc") {
				LoadAccHdDesc1(baseResponse);
			} else if (command == "getBudgetGroupMinor") {
				getBudgetGroupMinor1(baseResponse);
			}
		}
	}
}

function getMinorBudgetHeadDesc(path) {
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmAnnual_Account_Sub_Group.cmbBudgetGroupMajor.focus();
	} else {

		var url = path
				+ "/Annual_Account_Sub_Group?command=getMinorBudgetHeadDesc&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}


function getMinorBudgetHeadDesc1(baseResponse) {
	document.getElementById("cmbGroup_Head_Code").length = 1;	
	var flag4 = baseResponse.getElementsByTagName("flag4")[0].firstChild.nodeValue;
	if (flag4 == "success") {
		var len45 = baseResponse.getElementsByTagName("cmbGroup_Head_Code").length;
		if (len45 != 0) {
			for ( var i = 0; i < len45; i++) {
				var cmbGroup_Head_Code = baseResponse
						.getElementsByTagName("cmbGroup_Head_Code")[i].firstChild.nodeValue;
				var cmbGroup_Head_Desc = baseResponse
						.getElementsByTagName("cmbGroup_Head_Desc")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbGroup_Head_Code");
				var op = document.createElement("OPTION");
				op.value = cmbGroup_Head_Code;
				var txt = document.createTextNode(cmbGroup_Head_Desc);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Group Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Group Head Code");
	}
}

function initialLoad(path) {
	//alert(path);
	//var url = path + "/Annual_Account_Sub_Group?command=FirstLoad1";
	var budgetGroup="",groupHead="";
	var url=""; 
	if(document.getElementById('cmbBudgetGroupMajor').value==""){
		url = path + "/Annual_Account_Sub_Group?command=FirstLoad1";
	}else{
		budgetGroup=document.getElementById('cmbBudgetGroupMajor').value;
		groupHead=document.getElementById('cmbGroup_Head_Code').value;
		url = path + "/Annual_Account_Sub_Group?command=FirstLoad1&budgetGroup="+budgetGroup+"&groupHead="+groupHead;
	}
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
		var sub_Head_Code = baseResponse
				.getElementsByTagName("sub_Head_Code")[0].firstChild.nodeValue;
		document.getElementById("txtSubGroup_Head_Code").value = sub_Head_Code;
	} else {
		alert("Failed to Generate Sub Group Head Code");
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
	var majorCode="";
	if (flag == "success") {

		var len = baseResponse.getElementsByTagName("txtGroup_Head_Code").length;
		for ( var k = 0; k < len; k++) {
			var cmbBudgetGroupMajor = baseResponse
					.getElementsByTagName("cmbBudgetGroupMajor")[k].firstChild.nodeValue;
			var txtGroup_Head_Code = baseResponse
					.getElementsByTagName("txtGroup_Head_Code")[k].firstChild.nodeValue;
			var txtSub_Head_Code = baseResponse
					.getElementsByTagName("txtSub_Head_Code")[k].firstChild.nodeValue;
			var txtSub_Head_Desc = baseResponse
					.getElementsByTagName("txtSub_Head_Desc")[k].firstChild.nodeValue;
			var gROUP_HEAD_DESC=baseResponse.getElementsByTagName("GROUP_HEAD_DESC")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = cmbBudgetGroupMajor + txtGroup_Head_Code
					+ txtSub_Head_Code;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbBudgetGroupMajor
					+ "','" + txtGroup_Head_Code + "','" + txtSub_Head_Code	+ "')";
			if(cmbBudgetGroupMajor=="L")
			{
				majorCode="Liabilities";
			}
			else if(cmbBudgetGroupMajor=="A")
			{
				majorCode="Assets";
			}
			else if(cmbBudgetGroupMajor=="E")
			{
				majorCode="Expenditure";
			}
			else if(cmbBudgetGroupMajor=="I")
			{
				majorCode="Income";
			}

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var cmbBudgetGroupMajor = document
					.createTextNode(majorCode);
			cell2.appendChild(cmbBudgetGroupMajor);
			mycurrent_row.appendChild(cell2);

			var cell21 = document.createElement("TD");
			var txtGroup_Head_Code = document
					.createTextNode(gROUP_HEAD_DESC);
			cell21.appendChild(txtGroup_Head_Code);
			mycurrent_row.appendChild(cell21);

			var cell22 = document.createElement("TD");
			var txtSub_Head_Code = document
					.createTextNode(txtSub_Head_Code);
			cell22.appendChild(txtSub_Head_Code);
			mycurrent_row.appendChild(cell22);

			var cell3 = document.createElement("TD");
			var txtSub_Head_Desc = document.createTextNode(txtSub_Head_Desc);
			cell3.appendChild(txtSub_Head_Desc);
			mycurrent_row.appendChild(cell3);

			tbody.appendChild(mycurrent_row);

		}
	} else {
		alert("Fail to Load Grid");
	}
}

function add(path) {
	//alert(path);
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var txtSubGroup_Head_Code = document.getElementById("txtSubGroup_Head_Code").value;
	var txtSubGroup_Head_Desc = document.getElementById("txtSubGroup_Head_Desc").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmAnnual_Account_Sub_Group.cmbBudgetGroupMajor
				.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmAnnual_Account_Sub_Group.cmbGroup_Head_Code.focus();
	} else if (txtSubGroup_Head_Code == "") {
		alert("Enter SubGroup_Head_Code in the Field");
		document.frmAnnual_Account_Sub_Group.txtSubGroup_Head_Code
				.focus();
	} else if (txtSubGroup_Head_Desc == "") {
		alert("Enter SubGroup_Head_Desc in the Field");
		document.frmAnnual_Account_Sub_Group.txtSubGroup_Head_Desc.focus();
	} else {

		var url = path
				+ "/Annual_Account_Sub_Group?command=add&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
				+ cmbGroup_Head_Code + "&txtSubGroup_Head_Code="
				+ txtSubGroup_Head_Code + "&txtSubGroup_Head_Desc=" + txtSubGroup_Head_Desc;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function addRow(baseResponse) {

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 = "success") {
		var sub_Head_Code = baseResponse
				.getElementsByTagName("sub_Head_Code")[0].firstChild.nodeValue;
		document.getElementById("txtSubGroup_Head_Code").value = sub_Head_Code;
	} else {
		alert("Failed to Generate Sub Group Head Code");
	}
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");

		refresh();

		var cmbBudgetGroupMajor = baseResponse
				.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
		var cmbGroup_Head_Code = baseResponse
				.getElementsByTagName("cmbGroup_Head_Code")[0].firstChild.nodeValue;
		var txtSubGroup_Head_Code = baseResponse
				.getElementsByTagName("txtSubGroup_Head_Code")[0].firstChild.nodeValue;
		var txtSubGroup_Head_Desc = baseResponse
				.getElementsByTagName("txtSubGroup_Head_Desc")[0].firstChild.nodeValue;

		var tbody = document.getElementById("tblList");
		var mycurrent_row = document.createElement("TR");
		
		mycurrent_row.id = cmbBudgetGroupMajor + cmbGroup_Head_Code
				+ txtSubGroup_Head_Code;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cmbBudgetGroupMajor
				+ "','" + cmbGroup_Head_Code + "','" + txtSubGroup_Head_Code + "')";

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
		var cmbGroup_Head_Code = document.createTextNode(cmbGroup_Head_Code);
		cell3.appendChild(cmbGroup_Head_Code);
		mycurrent_row.appendChild(cell3);

		var cell4 = document.createElement("TD");
		var txtSubGroup_Head_Code = document.createTextNode(txtSubGroup_Head_Code);
		cell4.appendChild(txtSubGroup_Head_Code);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var txtSubGroup_Head_Desc = document.createTextNode(txtSubGroup_Head_Desc);
		cell5.appendChild(txtSubGroup_Head_Desc);
		mycurrent_row.appendChild(cell5);

		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Data are Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(cmbBudgetGroupMajor,cmbGroup_Head_Code,txtSubGroup_Head_Code) {
	document.getElementById("cmbBudgetGroupMajor").disabled = true;
	document.getElementById("cmbGroup_Head_Code").disabled = true;
	document.getElementById("txtSubGroup_Head_Code").disabled = true;	
	document.getElementById("txtSubGroup_Head_Desc").value = "";
	
	var url = "../../../../../Annual_Account_Sub_Group?command=LoadData&cmbBudgetGroupMajor="
			+ cmbBudgetGroupMajor
			+ "&cmbGroup_Head_Code="
			+ cmbGroup_Head_Code
			+ "&txtSubGroup_Head_Code="
			+ txtSubGroup_Head_Code;

	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}

function LoadData1(baseResponse) {

	document.getElementById("cmbGroup_Head_Code").length = 1;

	var cmbBudgetGroupMajor = baseResponse
			.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
	var cmbGroup_Head_Code = baseResponse
			.getElementsByTagName("cmbGroup_Head_Code")[0].firstChild.nodeValue;
	var txtSubGroup_Head_Code = baseResponse
			.getElementsByTagName("txtSubGroup_Head_Code")[0].firstChild.nodeValue;
	
	document.getElementById("cmbBudgetGroupMajor").value = cmbBudgetGroupMajor;
	
	var flag2 = baseResponse.getElementsByTagName("flag4")[0].firstChild.nodeValue;
	
	if (flag2 == "success") {
		var se = document.getElementById("cmbGroup_Head_Code");
		se.length=0;
		var len44 = baseResponse.getElementsByTagName("cmbGroup_Head_Code1").length;
		if (len44 > 0) {
			for ( var i = 0; i < len44; i++) {
				var cmbGroup_Head_Code1 = baseResponse
						.getElementsByTagName("cmbGroup_Head_Code1")[i].firstChild.nodeValue;
				var cmbGroup_Head_Desc1 = baseResponse
						.getElementsByTagName("cmbGroup_Head_Desc1")[i].firstChild.nodeValue;

				
				var op = document.createElement("OPTION");
				
				op.text = cmbGroup_Head_Desc1;
				op.value = cmbGroup_Head_Code1;
				se.appendChild(op);
				
				
			}
		} else {
			alert("Group Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Major Head Code");
	}

	
	document.getElementById("cmbGroup_Head_Code").value = cmbGroup_Head_Code;
	document.getElementById("txtSubGroup_Head_Code").value = txtSubGroup_Head_Code;
	
	var r = document.getElementById(cmbBudgetGroupMajor+cmbGroup_Head_Code+txtSubGroup_Head_Code);
	var rcells = r.cells;	
	document.frmAnnual_Account_Sub_Group.txtSubGroup_Head_Desc.value = rcells.item(4).firstChild.nodeValue;
	
	document.frmAnnual_Account_Sub_Group.onsubmit.disabled = true;
	document.frmAnnual_Account_Sub_Group.ondelete.disabled = false;
	document.frmAnnual_Account_Sub_Group.onupdate.disabled = false;

}

function update(path) {
	//alert(path);
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var txtSubGroup_Head_Code = document.getElementById("txtSubGroup_Head_Code").value;
	var txtSubGroup_Head_Desc = document.getElementById("txtSubGroup_Head_Desc").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmAnnual_Account_Sub_Group.cmbBudgetGroupMajor
				.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmAnnual_Account_Sub_Group.cmbGroup_Head_Code.focus();
	} else if (txtSubGroup_Head_Code == "") {
		alert("Enter SubGroup_Head_Code in the Field");
		document.frmAnnual_Account_Sub_Group.txtSubGroup_Head_Code
				.focus();
	} else if (txtSubGroup_Head_Desc == "") {
		alert("Enter SubGroup_Head_Desc in the Field");
		document.frmAnnual_Account_Sub_Group.txtSubGroup_Head_Desc.focus();
	} else {

		var url = path
				+ "/Annual_Account_Sub_Group?command=update&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
				+ cmbGroup_Head_Code + "&txtSubGroup_Head_Code="
				+ txtSubGroup_Head_Code + "&txtSubGroup_Head_Desc=" + txtSubGroup_Head_Desc;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function updateRow(baseResponse) {

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 = "success") {
		var sub_Head_Code = baseResponse
				.getElementsByTagName("sub_Head_Code")[0].firstChild.nodeValue;
		document.getElementById("txtSubGroup_Head_Code").value = sub_Head_Code;
	} else {
		alert("Failed to Generate Sub Group Head Code");
	}
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
		var items = new Array();
		items[0] = baseResponse.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("cmbGroup_Head_Code")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("txtSubGroup_Head_Code")[0].firstChild.nodeValue;
		items[3] = baseResponse.getElementsByTagName("txtSubGroup_Head_Desc")[0].firstChild.nodeValue;
		
		var r = document.getElementById(items[0]+items[1]+items[2]);
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

function deletee(path) {
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var txtSubGroup_Head_Code = document.getElementById("txtSubGroup_Head_Code").value;	

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmAnnual_Account_Sub_Group.cmbBudgetGroupMajor
				.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmAnnual_Account_Sub_Group.cmbGroup_Head_Code.focus();
	} else if (txtSubGroup_Head_Code == "") {
		alert("Enter Sub Group Head Code in the Field");
		document.frmAnnual_Account_Sub_Group.txtSubGroup_Head_Code
				.focus();
	}else {
		var r = confirm("Are U Sure?");
		if (r == true) {
			var url = path
			+ "/Annual_Account_Sub_Group?command=deleted&cmbBudgetGroupMajor="
			+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
			+ cmbGroup_Head_Code + "&txtSubGroup_Head_Code="
			+ txtSubGroup_Head_Code;
			
			//alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}
	}
}

function deleteRow(baseResponse) {
	
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 = "success") {
		var sub_Head_Code = baseResponse
				.getElementsByTagName("sub_Head_Code")[0].firstChild.nodeValue;
		document.getElementById("txtSubGroup_Head_Code").value = sub_Head_Code;
	} else {
		alert("Failed to Generate Sub Group Head Code");
	}
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Records Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}
function refresh() {
	document.frmAnnual_Account_Sub_Group.cmbBudgetGroupMajor.value = "";
	document.frmAnnual_Account_Sub_Group.cmbGroup_Head_Code.value = "";
	document.frmAnnual_Account_Sub_Group.txtSubGroup_Head_Desc.value = "";
	document.getElementById("cmbBudgetGroupMajor").disabled = false;
	document.getElementById("cmbGroup_Head_Code").disabled = false;
	document.frmAnnual_Account_Sub_Group.onsubmit.disabled = false;
	document.frmAnnual_Account_Sub_Group.ondelete.disabled = true;
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
			return false;
	}
}

function exitfun() {
	window.close();
}
function clearAll(){
	document.getElementById('cmbBudgetGroupMajor').selectedIndex=0;
	document.getElementById('cmbGroup_Head_Code').selectedIndex=0;
	document.getElementById('cmbGroup_Head_Code').length=1;
	document.getElementById('txtSubGroup_Head_Code').value="";
	document.getElementById('txtSubGroup_Head_Desc').value="";
}


