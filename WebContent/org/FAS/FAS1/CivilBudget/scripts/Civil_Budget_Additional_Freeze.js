//		Civil_Budget_Additional_Freeze		//
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

			if (command == "save") {
				save1(baseResponse);
			}
			else if(command=="save_consolidate"){
				save_consolidate2(baseResponse);
			}
			else if (command == "getStatementName") {
				getFormatName1(baseResponse);
			}
			else if (command == "DeleteFn") {
				DeleteFn1(baseResponse);
			}
			else if(command=="delete_consolidate"){
				delete_consolidate1(baseResponse);
			}
		}
	}
}

function initialLoad1(path) {
	//alert(path);
	var url = path + "/Civil_Budget_Statement_1?command=getStatementName";
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function FuncSave(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbFreezeType = document.getElementById("cmbFreezeType").value;
	var cmbFormat_Name = document.getElementById("cmbFormat_Name").value;

	if (cmbFreezeType == "") {
		alert("select Freeze Type");
		document.frmAdditional_Freeze.cmbFreezeType.focus;
	} else if (cmbFormat_Name == "") {
		alert("select statement Name");
		document.frmAdditional_Freeze.cmbFormat_Name.focus;
	} else {
		var url = path
				+ "/Civil_Budget_Additional_Freeze?command=save&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cmbFreezeType=" + cmbFreezeType + "&cmbFormat_Name="
				+ cmbFormat_Name + "&cmbFinancialYear=" + cmbFinancialYear;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function FuncDelete(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbFreezeType = document.getElementById("cmbFreezeType").value;
	var cmbFormat_Name = document.getElementById("cmbFormat_Name").value;

	if (cmbFreezeType == "") {
		alert("select Freeze Type");
		document.frmAdditional_Freeze.cmbFreezeType.focus;
	} else if (cmbFormat_Name == "") {
		alert("select statement Name");
		document.frmAdditional_Freeze.cmbFormat_Name.focus;
	} else {
		var url = path
				+ "/Civil_Budget_Additional_Freeze?command=DeleteFn&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cmbFreezeType=" + cmbFreezeType + "&cmbFormat_Name="
				+ cmbFormat_Name + "&cmbFinancialYear=" + cmbFinancialYear;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function FuncSave_Consolidate(path)
{
 
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value; 
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbFreezeType = document.getElementById("cmbFreezeType").value;
	var cmbFormat_Name = document.getElementById("cmbFormat_Name").value;
	if (cmbFreezeType == "") {
		alert("select Freeze Type");
		document.frmAdditional_Consolid_Freeze.cmbFreezeType.focus;
	} else if (cmbFormat_Name == "") {
		alert("select statement Name");
		document.frmAdditional_Consolid_Freeze.cmbFormat_Name.focus;
	} else {
		var url = path
				+ "/Civil_Budget_Additional_Freeze?command=save_consolidate&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+ "&cmbFreezeType=" + cmbFreezeType
				+ "&cmbFormat_Name="+ cmbFormat_Name + "&cmbFinancialYear=" + cmbFinancialYear;
		
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}


function FuncDelete_Consolidate(path)
{
 
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value; 
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbFreezeType = document.getElementById("cmbFreezeType").value;
	var cmbFormat_Name = document.getElementById("cmbFormat_Name").value;
	if (cmbFreezeType == "") {
		alert("select Freeze Type");
		document.frmAdditional_Consolid_Freeze.cmbFreezeType.focus;
	} else if (cmbFormat_Name == "") {
		alert("select statement Name");
		document.frmAdditional_Consolid_Freeze.cmbFormat_Name.focus;
	} else {
		var url = path
				+ "/Civil_Budget_Additional_Freeze?command=delete_consolidate&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+ "&cmbFreezeType=" + cmbFreezeType
				+ "&cmbFormat_Name="+ cmbFormat_Name + "&cmbFinancialYear=" + cmbFinancialYear;
		
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function getFormatName1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;
		
		//se.length=0;
		for ( var i = 0; i < len4; i++) {
			var STATEMENT_NO = baseResponse
					.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
			var STATEMENT_DESC = baseResponse
					.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbFormat_Name");
			var op = document.createElement("OPTION");
			op.value = STATEMENT_NO;
			var txt = document.createTextNode(STATEMENT_DESC);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed to Load Statement Name");
	}
}

function save1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "Exist") {
		alert("Additional Budget have Already Freezed");
	} else if (flag == "success") {
		clrForm();
		alert("Additional Budget Freezed Successfully");
	} else if(flag == "NotInsert"){
		clrForm();
		alert("Record Not Insert");
	}else {
		clrForm();
		alert("Record Insertion Failed");
	}
}


function DeleteFn1(baseResponse) {
	var status = baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(status=="RegionFreezed"){
		alert("Region was Freezed,So you cant unFreeze");
		
	}else if(status=="RegionNotFreezed"){
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

		 if (flag == "Deleted") {
			clrForm();
			alert("UnFreezed Successfully");
		} else if(flag == "NotDeleted"){
			//clrForm();
			alert("You didnt freeze");
		}else {
			clrForm();
			alert("Failed");
		}
	}else{
		alert("Error");
	}
	
}

function save_consolidate2(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "Exist") {
		alert("Additional Consolidate have Already Freezed");
	} else if (flag == "success") {
		clrForm();
		alert("Additional Consolidate Freezed Successfully");
	} else if(flag == "NotInsert"){
		clrForm();
		alert("Record Not Insert");
	}
	else if(flag == "someoffice"){
		clrForm();
		alert("Some Office not Freezed");
	}
	else {
		clrForm();
		alert("Record Insertion Failed");
	}
}


function delete_consolidate1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "deleted") {
		alert("UnFreezed Additional Consolidate");
	} else if (flag == "NotDelete") {
		clrForm();
		alert("You didnt freeze");
	} 
	else {
		clrForm();
		alert("Failed to unfreeze");
	}
}

function clrForm() {
	LoadAccountingUnitID('LIST_ALL_UNITS');
	//document.getElementById("cmbFinancialYear").length = 1;
	/*var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	var year1 = 0;
	var financialyear = 0;
	var financialyear1 = 0;
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}

	if (month < 4) {
		financialyear = year1 + "-" + year;
		financialyear1 = (year1 - 1) + "-" + (year - 1);
	} else {
		financialyear = year + "-" + year1;
		financialyear1 = (year - 1) + "-" + (year1 - 1);
	}
	for ( var k = 0; k < 2; k++) {
		if (k == 0) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear1;
			var txt = document.createTextNode(financialyear1);
			op.appendChild(txt);
			se.appendChild(op);
		} else if (k == 1) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear;
			var txt = document.createTextNode(financialyear);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	document.getElementById("cmbFinancialYear").value = financialyear;	*/
	document.getElementById("cmbFormat_Name").value = "";

}
/*function numbersonly1(e, t) {
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
}*/

function exitfun() {
	window.close();
}
