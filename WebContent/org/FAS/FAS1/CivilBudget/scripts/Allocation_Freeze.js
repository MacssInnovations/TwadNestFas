//		Allocation_Freeze		//
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


function Funclist(path) {
	//alert(path);
	//var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	//var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var Statement_Name = document.getElementById("Statement_Name").value;

	if (Statement_Name == "") {
		alert("select Format Name in the Filed");
		document.frmAllocation_Freezed_List.Statement_Name.focus;
	} else {
		var url = path
				+ "/Allocation_Freezed_Report?command=list&Statement_Name="
				+ Statement_Name + "&cmbFinancialYear=" + cmbFinancialYear;
	//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
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
			else if(command=="save_HO"){
				save2(baseResponse);
			}
			else if(command=="Push_HO"){
				Push_Data(baseResponse);
			}
			else if (command == "getStatementName") {
				getFormatName1(baseResponse);
			} else if (command == "list") {
				getStstementList(baseResponse);
			}
		}
	}
}


function getStstementList(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
 var grid_body=document.getElementById("grid_body");
 var t = 0,n=0,k=0;
	for (t = grid_body.rows.length - 1; t >= 0; t--) {
		grid_body.deleteRow(0);
	}

		
		var type = baseResponse.getElementsByTagName("type")[0].firstChild.nodeValue;
		//alert(type);
		
		if(type=="A"){
			var len1 = baseResponse.getElementsByTagName("count").length;
for(var j=0;j<len1;j++)
{
	
	var grid_body=document.getElementById("grid_body");
	var STATEMENT_NO = baseResponse.getElementsByTagName("STATEMENT_NO")[j].firstChild.nodeValue;
	var STATEMENT_DESC = baseResponse.getElementsByTagName("STATEMENT_DESC")[j].firstChild.nodeValue;
	var len4 = baseResponse.getElementsByTagName("Acc_UnitID"+j).length;
		var mycurrent_row1 = document.createElement("TR");
	var cell2 = document.createElement("TD");
	cell2.setAttribute('colspan','3');
	cell2.setAttribute('align','left');
	
	//cell2.setAttribute('bgcolor','red');
	var hid_Stae_name = document.createTextNode(STATEMENT_DESC);	
	cell2.style.color="red";
	cell2.appendChild(hid_Stae_name);
	mycurrent_row1.appendChild(cell2);
	grid_body.appendChild(mycurrent_row1);
	//n=n+1;
	//alert(">>> n    "+n);
	
			for (  i = 0; i < len4; i++) {
			
				var Acc_UnitID = baseResponse.getElementsByTagName("Acc_UnitID"+j)[i].firstChild.nodeValue;
				var offid = baseResponse.getElementsByTagName("offid"+j)[i].firstChild.nodeValue;
				var UnitID = baseResponse.getElementsByTagName("UnitID"+j)[i].firstChild.nodeValue;
				var Acc_offid = baseResponse.getElementsByTagName("Acc_offid"+j)[i].firstChild.nodeValue;				
				var Freezed_Date = baseResponse.getElementsByTagName("Freezed_Date"+j)[i].firstChild.nodeValue;
				
			
				 
				var mycurrent_row = document.createElement("TR");
				var cell2 = document.createElement("TD");
				cell2.setAttribute('align','left');
				var hid_unit=document.createElement("input");
				hid_unit.type="hidden";
				hid_unit.id="unit_id"+i;
				hid_unit.name="unit_id"+i;
				hid_unit.value=Acc_UnitID;		
				var hid_unit_name = document.createTextNode(UnitID);
				cell2.appendChild(hid_unit);
				cell2.appendChild(hid_unit_name);
				mycurrent_row.appendChild(cell2);

				var cell2 = document.createElement("TD");
				cell2.setAttribute('align','left');
				var offid_unit=document.createElement("input");
				offid_unit.type="hidden";
				offid_unit.id="offid"+i;
				offid_unit.name="offid"+i;
				offid_unit.value=offid;		
				var offid_unit_name = document.createTextNode(Acc_offid);
				cell2.appendChild(offid_unit);
				cell2.appendChild(offid_unit_name);
				mycurrent_row.appendChild(cell2);
				
				var cell3 = document.createElement("TD");
				var Freezed_Date = document.createTextNode(Freezed_Date);
				cell3.appendChild(Freezed_Date);
				mycurrent_row.appendChild(cell3);
				
			//	grid_body.appendChild(mycurrent_row1);
				grid_body.appendChild(mycurrent_row);
			}
			
}
		}else{
			var len4 = baseResponse.getElementsByTagName("Acc_UnitID").length;
		for ( var i = 0; i < len4; i++) {
			
			var Acc_UnitID = baseResponse.getElementsByTagName("Acc_UnitID")[i].firstChild.nodeValue;
			var offid = baseResponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
			var UnitID = baseResponse.getElementsByTagName("UnitID")[i].firstChild.nodeValue;
			var Acc_offid = baseResponse.getElementsByTagName("Acc_offid")[i].firstChild.nodeValue;
			var STATEMENT_NO = baseResponse.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
			var STATEMENT_DESC = baseResponse.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue;
			var Freezed_Date = baseResponse.getElementsByTagName("Freezed_Date")[i].firstChild.nodeValue;
			 var grid_body=document.getElementById("grid_body");
			var mycurrent_row = document.createElement("TR");
			var cell2 = document.createElement("TD");
			cell2.setAttribute('align','left');
			var hid_unit=document.createElement("input");
			hid_unit.type="hidden";
			hid_unit.id="unit_id"+i;
			hid_unit.name="unit_id"+i;
			hid_unit.value=Acc_UnitID;		
			var hid_unit_name = document.createTextNode(UnitID);
			cell2.appendChild(hid_unit);
			cell2.appendChild(hid_unit_name);
			mycurrent_row.appendChild(cell2);

			var cell2 = document.createElement("TD");
			cell2.setAttribute('align','left');
			var offid_unit=document.createElement("input");
			offid_unit.type="hidden";
			offid_unit.id="offid"+i;
			offid_unit.name="offid"+i;
			offid_unit.value=offid;		
			var offid_unit_name = document.createTextNode(Acc_offid);
			cell2.appendChild(offid_unit);
			cell2.appendChild(offid_unit_name);
			mycurrent_row.appendChild(cell2);
			
			var cell3 = document.createElement("TD");
			var Freezed_Date = document.createTextNode(Freezed_Date);
			cell3.appendChild(Freezed_Date);
			mycurrent_row.appendChild(cell3);
			
			
			grid_body.appendChild(mycurrent_row);
		}

	}
		}else if(flag=="No_Data"){
		alert('No Data Found .... ');
	}else {
		alert("Failed to Load Statement Name");
	}
}

function Stat_clear() {
	document.getElementById("Statement_Name").value="s";
}


function initialLoad1(path) {
	//alert(path);
	var url = path + "/Civil_Budget_Statement_1?command=getStatementName";
	//alert(path);
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
		alert("select Freeze Type in the Filed");
		document.frmAllocation_Freeze.cmbFreezeType.focus;
	} else if (cmbFormat_Name == "") {
		alert("select Format Name in the Filed");
		document.frmAllocation_Freeze.cmbFormat_Name.focus;
	} else {
		var url = path
				+ "/Allocation_Freeze?command=save&cmbAcc_UnitCode="
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
function FuncSave_HO(path)
{
 
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value; 
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	//var cmbFreezeType = document.getElementById("cmbFreezeType").value;
	var cmbFormat_Name = document.getElementById("cmbFormat_Name").value;

		var url = path
				+ "/Allocation_Freeze?command=save_HO&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&cmbFormat_Name="+ cmbFormat_Name + "&cmbFinancialYear=" + cmbFinancialYear;
		
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	
}


function push_Allocation(path){
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value; 
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbFormat_Name = document.getElementById("cmbFormat_Name").value;
	var url = path
	+ "/Allocation_Freeze?command=Push_HO&cmbAcc_UnitCode="
	+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
	+ "&cmbFormat_Name="+ cmbFormat_Name + "&cmbFinancialYear=" + cmbFinancialYear;

var xmlrequest = AjaxFunction();
xmlrequest.open("POST", url, true);
xmlrequest.onreadystatechange = function() {
manipulate(xmlrequest);
}

xmlrequest.send(null);

}
function getFormatName1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;
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
		alert("Budget Closure Consolidate have Already Freezed");
	} else if (flag == "success") {
		clrForm();
		alert("Budget Closure Consolidate Freezed Successfully");
	} else {
		clrForm();
		alert("Record Insertion Failed");
	}
}
function save2(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "Exist") {
		alert("HO Budget Closure have Already Freezed");
	} else if (flag == "success") {
		clrForm();
		alert("HO Budget Closure Freezed Successfully");
	} else {
		clrForm();
		alert("Record Insertion Failed");
	}
}
function Push_Data(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "Exist") {
		alert("Budget Details have Already Pushed");
	}
	else if (flag == "success") {
		//clrForm();
		alert(" Budget Details Pushed Successfully");
	} else if (flag == "failure_1") {
		//clrForm();
		alert(" Budget Details Not Freezed");
	} else {
		//clrForm();
		alert("Record Insertion Failed");
	}
}
function clrForm() {
	LoadAccountingUnitID('LIST_ALL_UNITS');
	/*document.getElementById("cmbFinancialYear").length = 1;
	var today = new Date();
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
	}*/
	//document.getElementById("cmbFinancialYear").value = financialyear;	
	document.getElementById("cmbFormat_Name").value = "";

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
