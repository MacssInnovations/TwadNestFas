//	Annual_Account_AccHead_Mapping	//
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
			} else if (command == "LoadSubHeadCode") {
				LoadSubHeadCode1(baseResponse);
			}
		}
	}
}

function deletee(path) {
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var cmbBudgetGroupMinor = document.getElementById("cmbBudgetGroupMinor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbBudgetGroupMajor.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbGroup_Head_Code.focus();
	} else if (cmbBudgetGroupMinor == "") {
		alert("Select Budget Group Minor in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbBudgetGroupMinor.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmAnnual_Account_AccHead_Mapping.txtAcc_HeadCode.focus();
	} else {
		var r = confirm("Are U Sure?");
		if (r == true) {
			var url = path
					+ "/Annual_Account_AccHead_Mapping?command=deleted&cmbBudgetGroupMajor="
					+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
					+ cmbGroup_Head_Code + "&cmbBudgetGroupMinor="
					+ cmbBudgetGroupMinor + "&txtAcc_HeadCode="
					+ txtAcc_HeadCode;

			alert(url);
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
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Records Deleted Successfully");
		refreshh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function loadValuesFromTable(cmbBudgetGroupMajor, cmbGroup_Head_Code,
		cmbBudgetGroupMinor, txtAcc_HeadCode) {
	document.getElementById("cmbBudgetGroupMajor").disabled = true;
	document.getElementById("cmbGroup_Head_Code").disabled = true;
	document.getElementById("cmbBudgetGroupMinor").disabled = true;
	document.getElementById("txtAcc_HeadCode").disabled = true;
	document.getElementById("txtAcc_HeadDesc").value = "";
	var url = "../../../../../Annual_Account_AccHead_Mapping?command=LoadData&cmbBudgetGroupMajor="
			+ cmbBudgetGroupMajor
			+ "&cmbGroup_Head_Code="
			+ cmbGroup_Head_Code
			+ "&cmbBudgetGroupMinor="
			+ cmbBudgetGroupMinor
			+ "&txtAcc_HeadCode=" + txtAcc_HeadCode;

	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}

function LoadData1(baseResponse) {

	document.getElementById("cmbGroup_Head_Code").length = 1;
	document.getElementById("cmbBudgetGroupMinor").length = 1
	document.getElementById("txtAcc_HeadCode").length = 1;

	var cmbBudgetGroupMajor1 = baseResponse
			.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
	var cmbGroup_Head_Code1 = baseResponse
			.getElementsByTagName("cmbGroup_Head_Code")[0].firstChild.nodeValue;
	var cmbGroup_Head_Desc1 = baseResponse
			.getElementsByTagName("cmbGroup_Head_Desc")[0].firstChild.nodeValue;
	var cmbBudgetGroupMinor1 = baseResponse
			.getElementsByTagName("cmbBudgetGroupMinor")[0].firstChild.nodeValue;
	var cmbBudgetGroupMinorDesc1 = baseResponse
			.getElementsByTagName("cmbBudgetGroupMinorDesc")[0].firstChild.nodeValue;
	var txtAcc_HeadCode1 = baseResponse.getElementsByTagName("txtAcc_HeadCode")[0].firstChild.nodeValue;
	var txtAcc_HeadCodeDesc1 = baseResponse
			.getElementsByTagName("txtAcc_HeadCodeDesc")[0].firstChild.nodeValue;

	document.getElementById("cmbBudgetGroupMajor").value = cmbBudgetGroupMajor1;

	var se = document.getElementById("cmbGroup_Head_Code");
	var op = document.createElement("OPTION");
	op.value = cmbGroup_Head_Code1;
	var txt = document.createTextNode(cmbGroup_Head_Desc1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("cmbGroup_Head_Code").value = cmbGroup_Head_Code1;

	var se = document.getElementById("cmbBudgetGroupMinor");
	var op = document.createElement("OPTION");
	op.value = cmbBudgetGroupMinor1;
	var txt = document.createTextNode(cmbBudgetGroupMinorDesc1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("cmbBudgetGroupMinor").value = cmbBudgetGroupMinor1;

	var se = document.getElementById("txtAcc_HeadCode");
	var op = document.createElement("OPTION");
	op.value = txtAcc_HeadCode1;
	var txt = document.createTextNode(txtAcc_HeadCode1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("txtAcc_HeadCode").value = txtAcc_HeadCode1;
	document.getElementById("txtAcc_HeadDesc").value = txtAcc_HeadCodeDesc1;

	document.frmAnnual_Account_AccHead_Mapping.onsubmit.disabled = true;
	document.frmAnnual_Account_AccHead_Mapping.ondelete.disabled = false;

}

function add(path) {
	//alert(path);
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var cmbSubHeadCode = document.getElementById("cmbSubHeadCode").value;
	var cmbBudgetGroupMinor = document.getElementById("cmbBudgetGroupMinor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbBudgetGroupMajor.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbGroup_Head_Code.focus();
	} else if (cmbSubHeadCode == "") {
		alert("Select Sub Head Code in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbSubHeadCode.focus();
	} else if (cmbBudgetGroupMinor == "") {
		alert("Select Budget Group Minor in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbBudgetGroupMinor.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmAnnual_Account_AccHead_Mapping.txtAcc_HeadCode.focus();
	} else {

		var url = path
				+ "/Annual_Account_AccHead_Mapping?command=add&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
				+ cmbGroup_Head_Code + "&cmbBudgetGroupMinor="
				+ cmbBudgetGroupMinor + "&txtAcc_HeadCode=" + txtAcc_HeadCode
				+ "&cmbSubHeadCode=" + cmbSubHeadCode;

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

		var cmbBudgetGroupMajor = baseResponse
				.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
		var cmbGroup_Head_Code = baseResponse
				.getElementsByTagName("cmbGroup_Head_Code")[0].firstChild.nodeValue;
		var cmbSubHeadCode = baseResponse
				.getElementsByTagName("cmbSubHeadCode")[0].firstChild.nodeValue;
		var cmbBudgetGroupMinor = baseResponse
				.getElementsByTagName("cmbBudgetGroupMinor")[0].firstChild.nodeValue;
		var txtAcc_HeadCode = baseResponse
				.getElementsByTagName("txtAcc_HeadCode")[0].firstChild.nodeValue;

		var tbody = document.getElementById("tblList");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = cmbBudgetGroupMajor + cmbGroup_Head_Code
				+ cmbSubHeadCode + cmbBudgetGroupMinor + txtAcc_HeadCode;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cmbBudgetGroupMajor
				+ "','" + cmbGroup_Head_Code + "','" + cmbSubHeadCode + "','"
				+ cmbBudgetGroupMinor + "','" + txtAcc_HeadCode + "')";

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

		var cell31 = document.createElement("TD");
		var cmbSubHeadCode = document.createTextNode(cmbSubHeadCode);
		cell31.appendChild(cmbSubHeadCode);
		mycurrent_row.appendChild(cell31);

		var cell4 = document.createElement("TD");
		var cmbBudgetGroupMinor = document.createTextNode(cmbBudgetGroupMinor);
		cell4.appendChild(cmbBudgetGroupMinor);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var txtAcc_HeadCode = document.createTextNode(txtAcc_HeadCode);
		cell5.appendChild(txtAcc_HeadCode);
		mycurrent_row.appendChild(cell5);

		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Data are Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function LoadAccHdCode(path) {
	var cmbBudgetDescMain = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbBudgetDescSub = document.getElementById("cmbBudgetGroupMinor").value;
	if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbBudgetDescMain.focus();
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbBudgetDescSub.focus();
	} else {
		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=LoadAccHdCode&cmbBudgetDescMain="
				+ cmbBudgetDescMain + "&cmbBudgetDescSub=" + cmbBudgetDescSub;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function LoadAccHdCode1(baseResponse) {
	document.getElementById("txtAcc_HeadCode").length = 1;
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var len4 = baseResponse.getElementsByTagName("AccHdCode").length;
		if (len4 > 0) {
			for ( var i = 0; i < len4; i++) {
				var AccHdCode = baseResponse.getElementsByTagName("AccHdCode")[i].firstChild.nodeValue;

				var se = document.getElementById("txtAcc_HeadCode");
				var op = document.createElement("OPTION");
				op.value = AccHdCode;
				var txt = document.createTextNode(AccHdCode);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Account Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Account Head Code");
	}
}

function LoadAccHdDesc(path) {
	var cmbBudgetDescMain = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbBudgetDescSub = document.getElementById("cmbBudgetGroupMinor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbBudgetDescMain.focus();
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
		document.frmAnnual_Account_AccHead_Mapping.cmbBudgetDescSub.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmAnnual_Account_AccHead_Mapping.txtAcc_HeadCode.focus();
	} else {
		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=LoadAccHdDesc&cmbBudgetDescMain="
				+ cmbBudgetDescMain + "&cmbBudgetDescSub=" + cmbBudgetDescSub
				+ "&txtAcc_HeadCode=" + txtAcc_HeadCode;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function LoadAccHdDesc1(baseResponse) {

	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var AccHdDesc = baseResponse.getElementsByTagName("AccHdDesc")[0].firstChild.nodeValue;
		document.getElementById("txtAcc_HeadDesc").value = AccHdDesc;
	} else {
		alert("Fail to Load Account Head Desc");
	}
}

function getMinorBudgetHeadDesc(path) {
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Master.cmbBudgetGroupMajor.focus();
	} else {

		var url = path
				+ "/Annual_Account_AccHead_Mapping?command=getMinorBudgetHeadDesc&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function getMinorBudgetHeadDesc1(baseResponse) {
	document.getElementById("cmbGroup_Head_Code").length = 1;
	document.getElementById("cmbBudgetGroupMinor").length = 1;
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var len45 = baseResponse.getElementsByTagName("BudgetIdSub").length;
		if (len45 != 0) {
			for ( var i = 0; i < len45; i++) {
				var BudgetIdSub = baseResponse
						.getElementsByTagName("BudgetIdSub")[i].firstChild.nodeValue;
				var BudgetDescSub = baseResponse
						.getElementsByTagName("BudgetDescSub")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbBudgetGroupMinor");
				var op = document.createElement("OPTION");
				op.value = BudgetIdSub;
				var txt = document.createTextNode(BudgetDescSub);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Budget Desc Minor Does Not Exist");
		}
	} else {
		alert("Fail to Load Budget Desc Minor");
	}

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
	var url = path + "/Annual_Account_AccHead_Mapping?command=FirstLoad1";
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}

function LoadSubHeadCode(path) {
	//alert(path);
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Master.cmbBudgetGroupMajor.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmBudget_Heads_Master.cmbGroup_Head_Code.focus();
	} else {
		var url = path
				+ "/Annual_Account_AccHead_Mapping?command=LoadSubHeadCode&cmbGroup_Head_Code="
				+ cmbGroup_Head_Code + "&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function LoadSubHeadCode1(baseResponse) {

	var flag2 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag2 == "success") {
		var len44 = baseResponse.getElementsByTagName("cmbSubHeadCode").length;
		if (len44 > 0) {
                var se = document.getElementById("cmbSubHeadCode");
                                se.length=0;
			for ( var i = 0; i < len44; i++) {
				var cmbSubHeadCode = baseResponse
						.getElementsByTagName("cmbSubHeadCode")[i].firstChild.nodeValue;
				var cmbSubHeadDesc = baseResponse
						.getElementsByTagName("cmbSubHeadDesc")[i].firstChild.nodeValue;

				
				var op = document.createElement("OPTION");
				op.value = cmbSubHeadCode;
				var txt = document.createTextNode(cmbSubHeadDesc);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Sub Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Sub Head Code");
	}
}

function firstLoad(baseResponse) {

	var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag2 == "success") {
		var len44 = baseResponse.getElementsByTagName("BudgetIdMain").length;
		if (len44 > 0) {
			for ( var i = 0; i < len44; i++) {
				var BudgetIdMain = baseResponse
						.getElementsByTagName("BudgetIdMain")[i].firstChild.nodeValue;
				var BudgetDescMain = baseResponse
						.getElementsByTagName("BudgetDescMain")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbBudgetGroupMajor");
				var op = document.createElement("OPTION");
				op.value = BudgetIdMain;
				var txt = document.createTextNode(BudgetDescMain);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Major Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Major Head Code");
	}

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len = baseResponse.getElementsByTagName("txtGroup_Head_Code").length;
		for ( var k = 0; k < len; k++) {
			var cmbBudgetGroupMajor = baseResponse
					.getElementsByTagName("cmbBudgetGroupMajor")[k].firstChild.nodeValue;
			var txtGroup_Head_Code = baseResponse
					.getElementsByTagName("txtGroup_Head_Code")[k].firstChild.nodeValue;
			var cmbSubHeadCode = baseResponse
					.getElementsByTagName("cmbSubHeadCode")[k].firstChild.nodeValue;
			var cmbBudgetGroupMinor = baseResponse
					.getElementsByTagName("cmbBudgetGroupMinor")[k].firstChild.nodeValue;
			var txtAcc_HeadCode = baseResponse
					.getElementsByTagName("txtAcc_HeadCode")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = cmbBudgetGroupMajor + txtGroup_Head_Code
					+ cmbSubHeadCode + cmbBudgetGroupMinor + txtAcc_HeadCode;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbBudgetGroupMajor
					+ "','" + txtGroup_Head_Code + "','" + cmbSubHeadCode
					+ "','" + cmbBudgetGroupMinor + "','" + txtAcc_HeadCode
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

			var cell21 = document.createElement("TD");
			var txtGroup_Head_Code = document
					.createTextNode(txtGroup_Head_Code);
			cell21.appendChild(txtGroup_Head_Code);
			mycurrent_row.appendChild(cell21);

			var cell211 = document.createElement("TD");
			var cmbSubHeadCode = document.createTextNode(cmbSubHeadCode);
			cell211.appendChild(cmbSubHeadCode);
			mycurrent_row.appendChild(cell211);

			var cell22 = document.createElement("TD");
			var cmbBudgetGroupMinor = document
					.createTextNode(cmbBudgetGroupMinor);
			cell22.appendChild(cmbBudgetGroupMinor);
			mycurrent_row.appendChild(cell22);

			var cell3 = document.createElement("TD");
			var txtAcc_HeadCode = document.createTextNode(txtAcc_HeadCode);
			cell3.appendChild(txtAcc_HeadCode);
			mycurrent_row.appendChild(cell3);

			tbody.appendChild(mycurrent_row);

		}
	} else {
		alert("Fail to Load Grid");
	}
}

function refresh() {
	document.frmAnnual_Account_AccHead_Mapping.cmbBudgetGroupMajor.value = "";
	document.frmAnnual_Account_AccHead_Mapping.cmbGroup_Head_Code.value = "";
	document.frmAnnual_Account_AccHead_Mapping.cmbSubHeadCode.value = "";
	document.frmAnnual_Account_AccHead_Mapping.cmbBudgetGroupMinor.value = "";
	document.frmAnnual_Account_AccHead_Mapping.txtAcc_HeadCode.value = "";
	document.frmAnnual_Account_AccHead_Mapping.txtAcc_HeadDesc.value = "";

	document.frmAnnual_Account_AccHead_Mapping.onsubmit.disabled = false;
	document.frmAnnual_Account_AccHead_Mapping.ondelete.disabled = true;
}

function refreshh() {
	document.getElementById("cmbBudgetGroupMajor").disabled = false;
	document.getElementById("cmbGroup_Head_Code").disabled = false;
	document.getElementById("cmbBudgetGroupMinor").disabled = false;
	document.getElementById("txtAcc_HeadCode").disabled = false;

	document.frmAnnual_Account_AccHead_Mapping.cmbBudgetGroupMajor.value = "";
	document.frmAnnual_Account_AccHead_Mapping.cmbGroup_Head_Code.value = "";
	document.frmAnnual_Account_AccHead_Mapping.cmbSubHeadCode.value = "";
	document.frmAnnual_Account_AccHead_Mapping.cmbBudgetGroupMinor.value = "";
	document.frmAnnual_Account_AccHead_Mapping.txtAcc_HeadCode.value = "";
	document.frmAnnual_Account_AccHead_Mapping.txtAcc_HeadDesc.value = "";

	document.frmAnnual_Account_AccHead_Mapping.onsubmit.disabled = false;
	document.frmAnnual_Account_AccHead_Mapping.ondelete.disabled = true;
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
