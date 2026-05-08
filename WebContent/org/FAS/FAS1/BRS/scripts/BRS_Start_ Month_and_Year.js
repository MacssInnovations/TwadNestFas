//                 BRS_Start_Month_and_Year             //

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
			if (command == "Add") {
				Add1(baseResponse);
			} else if (command == "LoadMonthYear") {
				LoadMonthYear1(baseResponse);
			} else if (command == "Update") {
				Update1(baseResponse);
			}
		}
	}
}

function Add(path) {
	//alert(path);	    
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_Start_Month_and_Year.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBRS_Start_Month_and_Year.txtCB_Month.focus();
	} else {

		var url = path
				+ "/BRS_Start_Month_and_Year?command=Add&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}


function loadStart_year()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var url = "../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear_start&cmbAcc_UnitCode="
		+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

//alert(url);
var req = getTransport();
req.open("POST", url, true);

req.onreadystatechange = function() {
	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML
					.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "LoadMonthYear_start") {
				
				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			       
				if (flag == "success") {
					var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
					var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
					document.getElementById("txtCB_Year").value=CB_Year1;
					
					var mn=parseInt(CB_Month1);
				
					document.getElementById("txtCB_Month").value=mn;
				//	document.getElementById("btnList").disabled=false;
					document.getElementById("onsubmit1").disabled=false;
					
				} else if (flag == "NoData") {
					alert("First Set BRS Initiation Month and Year");
					document.getElementById("txtCB_Month").value="";
					document.getElementById("txtCB_Year").value="";
				} 
				else if (flag == "NotAllowed") {
					alert("BRS Monthly Closure is Freezed.So Cannot Update Start Month&Year");
					//document.getElementById("btnList").disabled=true;
					document.getElementById("onsubmit1").disabled=true;
					//document.getElementById("btnUpdate").disabled=true;
					document.getElementById("txtCB_Month").value="";
					document.getElementById("txtCB_Year").value="";
				} 
				else {
					alert("Failed to Load Month and Year");		
				}
			}
		}
	}
}
req.send(null);
}

function Update(path) {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_Start_Month_and_Year.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBRS_Start_Month_and_Year.txtCB_Month.focus();
	} else {

		var url = path
				+ "/BRS_Start_Month_and_Year?command=Update&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function LoadMonthYear(path) {
	document.frmBRS_Start_Month_and_Year.onsubmit1.disabled = true;
//	document.frmBRS_Start_Month_and_Year.btnUpdate.disabled = false;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = path
			+ "/BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code;

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function Add1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Inserted Successfully");
		refresh();
	} else if (flag == "Exist") {
		alert("Record Already Exist");
		refresh();
	} else {
		alert("Record Insertion Failed");
		refresh();
	}
}

function Update1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Updated Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
		refresh();
	} else {
		alert("Record Updation Failed");
		refresh();
	}
}

function LoadMonthYear1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var CB_Year = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		document.getElementById("txtCB_Year").value = CB_Year;
		document.getElementById("txtCB_Month").value = CB_Month;
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Failed to Load Values");
		refresh();
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
			return false;
	}
}

function refresh1() {
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	document.frmBRS_Start_Month_and_Year.txtCB_Year.value = year;
	document.frmBRS_Start_Month_and_Year.txtCB_Month.value = month;
	LoadAccountingUnitID('LIST_ALL_UNITS');

	document.frmBRS_Start_Month_and_Year.onsubmit1.disabled = false;
	//document.frmBRS_Start_Month_and_Year.btnUpdate.disabled = true;
}

function exitfun(path) {
	window.close();
}
