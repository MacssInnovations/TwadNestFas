//		Civil_Budget_Format_5		//
var seq = 0;
var seq1 = 1;
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
			} else if (command == "get") {
				firstLoad(baseResponse);
			} else if (command == "LoadData") {
				LoadData_View(baseResponse);
			}
		}
	}
}
function initialLoad1() {

	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1 = fy.split('-');
	var y1 = fy1[0];
	var y2 = "20"+fy1[1];

	document.frmCivil_Budget_Format_5.butView.disabled = false;
	document.frmCivil_Budget_Format_5.butSub.disabled = false;
	document.frmCivil_Budget_Format_5.butDelete.disabled = true;
	document.frmCivil_Budget_Format_5.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_5?command=get&y1=" + y1
			+ "&y2=" + y2 + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	document.getElementById("imgfld").style.visibility = "visible";
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}
function initialLoad() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	var year1 = 0;
	var financialyear0 = 0;
	var financialyear = 0;
	var fin1=0;
	var fin2=0; 
	/*var financialyear1 = 0;
	var financialyear2 = 0;*/
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}

	if (month < 4) {
		financialyear0= (parseInt(year1)+1)+ "-" + (parseInt(year)+1);
		financialyear = year1 + "-" + year;
		var ssyr1=financialyear0.substring(0,5);
		var ssyr2=financialyear0.substring(7,9);
			fin1=ssyr1+ssyr2;
			
		var ssyr3=financialyear.substring(0,5);
		var ssyr4=financialyear.substring(7,9);
			fin2=ssyr3+ssyr4;
		/*financialyear1 = (parseInt(year1) - 1) + "-" + (parseInt(year) - 1);
		financialyear2 = (parseInt(year1) - 2) + "-" + (parseInt(year) - 2);*/
	} else {
		financialyear0=(parseInt(year)+1)+ "-" + (parseInt(year1)+1);
		financialyear = year + "-" + year1;
		var ssyr1=financialyear0.substring(0,5);
		var ssyr2=financialyear0.substring(7,9);
			fin1=ssyr1+ssyr2;
			
		var ssyr3=financialyear.substring(0,5);
		var ssyr4=financialyear.substring(7,9);
			fin2=ssyr3+ssyr4;
		/*financialyear1 = (parseInt(year) - 1) + "-" + (parseInt(year1) - 1);
		financialyear2 = (parseInt(year) - 2) + "-" + (parseInt(year1) - 2);*/
	}

	for ( var k = 1; k < 2; k++) {
		/*if (k == 0) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear;
			var txt = document.createTextNode(financialyear);
			op.appendChild(txt);
			se.appendChild(op);
		} else */if (k == 1) {
			var se = document.getElementById("cmbFinancialYear");
//			var op = document.createElement("OPTION");
//			op.value = fin1;
			var op = document.createElement("OPTION");
			op.value = fin2;
			var txt = document.createTextNode(fin2);
			op.appendChild(txt);
			se.appendChild(op);

		} /*else if (k == 2) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear;
			var txt = document.createTextNode(financialyear);
			op.appendChild(txt);
			se.appendChild(op);

		}else if(k==3)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = financialyear0;
	  		var txt = document.createTextNode(financialyear0);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}      */
	}
	document.getElementById("cmbFinancialYear").value = fin1;
	//document.getElementById("imgfld").style.visibility = "hidden";
	document.frmCivil_Budget_Format_5.butView.disabled = false;
	document.frmCivil_Budget_Format_5.butSub.disabled = false;
	document.frmCivil_Budget_Format_5.butDelete.disabled = true;
	document.frmCivil_Budget_Format_5.butUpdate.disabled = true;
}

function firstLoad(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("emp_id");
		seq = 0;
		var le=r_no.length;
         // alert(r_no.length);
		var item = new Array();
		if(le!=0){
			for ( var k = 0; k < r_no.length; k++) {

				item[0] = baseResponse.getElementsByTagName("emp_id")[k].firstChild.nodeValue;
				item[1] = baseResponse.getElementsByTagName("emp_name")[k].firstChild.nodeValue;
				item[2] = baseResponse.getElementsByTagName("desc_id")[k].firstChild.nodeValue;
				item[3] = baseResponse.getElementsByTagName("desc_name")[k].firstChild.nodeValue;
				item[4] = baseResponse.getElementsByTagName("retire_date")[k].firstChild.nodeValue;

				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				/** Sl No */
				var cell0 = document.createElement("TD");
				var slno = document.createTextNode(seq + 1);
				cell0.appendChild(slno);
				mycurrent_row.appendChild(cell0);

				/** Name of Employee  */
				var cell2 = document.createElement("TD");
				var Name_of_Employee = document
						.createElement('TEXTAREA', 'option1');
				Name_of_Employee.name = "Name_of_Employee" + seq;
				Name_of_Employee.id = "Name_of_Employee" + seq;
				Name_of_Employee.value = item[1];
				Name_of_Employee.readOnly = true;
				Name_of_Employee.setAttribute("cols", "5");
				Name_of_Employee.style.height = "30px";
				Name_of_Employee.style.width = "100px";
				cell2.appendChild(Name_of_Employee);
				mycurrent_row.appendChild(cell2);

				/** Designation */
				var cell3 = document.createElement("TD");
				var Designation = document.createElement('TEXTAREA', 'option1');
				Designation.name = "Designation" + seq;
				Designation.id = "Designation" + seq;
				Designation.value = item[3];
				Designation.readOnly = true;
				Designation.setAttribute("cols", "5");
				Designation.style.height = "30px";
				Designation.style.width = "100px";
				cell3.appendChild(Designation);
				mycurrent_row.appendChild(cell3);

				/** Date of Retirement */
				var cell4 = document.createElement("TD");
				var Date_of_Retirement = document.createElement("input");
				Date_of_Retirement.type = "hidden";
				Date_of_Retirement.name = "Date_of_Retirement" + seq;
				Date_of_Retirement.id = "Date_of_Retirement" + seq;
				Date_of_Retirement.value = item[4];
				Date_of_Retirement.size="5";
				cell4.appendChild(Date_of_Retirement);
				var currentText = document.createTextNode(item[4]);
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);

				/** Encashment of L.S */
				var cell5 = document.createElement("TD");
				var SA_Encashment_of_LS = document.createElement("input");
				SA_Encashment_of_LS.type = "hidden";
				SA_Encashment_of_LS.type = "Text";
				SA_Encashment_of_LS.name = "SA_Encashment_of_LS" + seq;
				SA_Encashment_of_LS.id = "SA_Encashment_of_LS" + seq;
				SA_Encashment_of_LS.value = "";
				SA_Encashment_of_LS.size = "5";
				SA_Encashment_of_LS.style.textAlign = "right";
				cell5.appendChild(SA_Encashment_of_LS);
				var currentText = document.createTextNode("");
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** Commutation of Pension */
				var cell6 = document.createElement("TD");
				var SA_Commutation_of_Pension = document.createElement("input");
				SA_Commutation_of_Pension.type = "Text";
				SA_Commutation_of_Pension.name = "SA_Commutation_of_Pension" + seq;
				SA_Commutation_of_Pension.id = "SA_Commutation_of_Pension" + seq;
				SA_Commutation_of_Pension.value = "";
				SA_Commutation_of_Pension.style.textAlign = "right";
				//SA_Commutation_of_Pension.maxLength = "10";
				SA_Commutation_of_Pension.size = "5";
				cell6.appendChild(SA_Commutation_of_Pension);
				var currentText = document.createTextNode("");
				cell6.appendChild(currentText);
				mycurrent_row.appendChild(cell6);

				/** Gratuity */
				var cell7 = document.createElement("TD");
				var SA_Gratuity = document.createElement("input");
				SA_Gratuity.type = "Text";
				SA_Gratuity.name = "SA_Gratuity" + seq;
				SA_Gratuity.id = "SA_Gratuity" + seq;
				SA_Gratuity.value = "";
				SA_Gratuity.style.textAlign = "right";
				//SA_Gratuity.maxLength = "10";
				SA_Gratuity.size = "5";
				cell7.appendChild(SA_Gratuity);
				var currentText = document.createTextNode("");
				cell7.appendChild(currentText);
				mycurrent_row.appendChild(cell7);

				/** Date_of_Retirement */
				var cell8 = document.createElement("TD");
				var Date_of_Retirement1 = document.createElement("input");
				Date_of_Retirement1.type = "Text";
				Date_of_Retirement1.name = "Date_of_Retirement1" + seq;
				Date_of_Retirement1.id = "Date_of_Retirement1" + seq;
				Date_of_Retirement1.value = "";
				//Date_of_Retirement1.maxLength = "5";
				Date_of_Retirement1.size = "5";
				cell8.appendChild(Date_of_Retirement1);
				var currentText = document.createTextNode("");
				cell8.appendChild(currentText);
				mycurrent_row.appendChild(cell8);

				/** SA_Encashment_of_LS */
				var cell9 = document.createElement("TD");
				var VR_Encashment_of_LS = document.createElement("input");
				VR_Encashment_of_LS.type = "Text";
				VR_Encashment_of_LS.name = "VR_Encashment_of_LS" + seq;
				VR_Encashment_of_LS.id = "VR_Encashment_of_LS" + seq;
				VR_Encashment_of_LS.value = "";
				VR_Encashment_of_LS.style.textAlign = "right";
				//VR_Encashment_of_LS.maxLength = "10";
				VR_Encashment_of_LS.size = "5";
				cell9.appendChild(VR_Encashment_of_LS);
				var currentText = document.createTextNode("");
				cell9.appendChild(currentText);
				mycurrent_row.appendChild(cell9);

				/** SA_Commutation_of_Pension */
				var cell20 = document.createElement("TD");
				var VR_Commutation_of_Pension = document.createElement("input");
				VR_Commutation_of_Pension.type = "Text";
				VR_Commutation_of_Pension.name = "VR_Commutation_of_Pension" + seq;
				VR_Commutation_of_Pension.id = "VR_Commutation_of_Pension" + seq;
				VR_Commutation_of_Pension.value = "";
				VR_Commutation_of_Pension.style.textAlign = "right";
				//VR_Commutation_of_Pension.maxLength = "10";
				VR_Commutation_of_Pension.size = "5";
				cell20.appendChild(VR_Commutation_of_Pension);
				var currentText = document.createTextNode("");
				cell20.appendChild(currentText);
				mycurrent_row.appendChild(cell20);

				/** SA_Gratuity	 */
				var cell21 = document.createElement("TD");
				var VR_Gratuity = document.createElement("input");
				VR_Gratuity.type = "Text";
				VR_Gratuity.name = "VR_Gratuity" + seq;
				VR_Gratuity.id = "VR_Gratuity" + seq;
				VR_Gratuity.value = "";
				//VR_Gratuity.maxLength = "10";
				VR_Gratuity.style.textAlign = "right";
				VR_Gratuity.size = "5";
				cell21.appendChild(VR_Gratuity);
				var currentText = document.createTextNode("");
				cell21.appendChild(currentText);
				mycurrent_row.appendChild(cell21);

				/** emp_id */
				var emp_id = document.createElement("input");
				emp_id.setAttribute("type", "hidden");
				emp_id.setAttribute("value", item[0]);
				emp_id.setAttribute("name", "emp_id" + seq);
				emp_id.setAttribute("id", "emp_id" + seq);
				document.getElementById("frmCivil_Budget_Format_5").appendChild(
						emp_id);

				/** desc_id */
				var desc_id = document.createElement("input");
				desc_id.setAttribute("type", "hidden");
				desc_id.setAttribute("value", item[2]);
				desc_id.setAttribute("name", "desc_id" + seq);
				desc_id.setAttribute("id", "desc_id" + seq);
				document.getElementById("frmCivil_Budget_Format_5").appendChild(
						desc_id);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
		}
		else{
			document.getElementById("imgfld").style.visibility = "hidden";
			alert("Record not exist");
		}
		
		document.getElementById("RecordCount").value = seq;
		document.getElementById("imgfld").style.visibility = "hidden";
	} else if (flag == "Exist") {
		alert("Format-5 have Already Freezed");
		clrForm();
	} else {
		alert("Failed to Load Data");
	}
	//document.getElementById("imgfld").style.visibility = "hiddden";
}

function LoadData(path) {
	//alert(path);
	document.frmCivil_Budget_Format_5.butView.disabled = false;
	document.frmCivil_Budget_Format_5.butSub.disabled = true;
	document.frmCivil_Budget_Format_5.butDelete.disabled = false;
	document.frmCivil_Budget_Format_5.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;
	//../../../../../Civil_Budget_Format_5?command=
	var url = path + "/Civil_Budget_Format_5?filter=view&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
			+ RecordCount;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	document.getElementById("imgfld").style.visibility = "visible";
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}

function LoadData_View(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("slno");
		var len = baseResponse.getElementsByTagName("slno").length;
		seq = 0;
		if (len != 0) {
			var item = new Array();
			for ( var k = 0; k < r_no.length; k++) {

				item[0] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				item[1] = baseResponse.getElementsByTagName("emp_id")[k].firstChild.nodeValue;
				if ((item[1] == 0) || (item[1] == 'null')) {
					item[1] = "0";
				}
				item[2] = baseResponse.getElementsByTagName("emp_name")[k].firstChild.nodeValue;
				if ((item[2] == 0) || (item[2] == 'null')) {
					item[2] = "";
				}
				item[3] = baseResponse.getElementsByTagName("desc_id")[k].firstChild.nodeValue;
				if ((item[3] == 0) || (item[3] == 'null')) {
					item[3] = "0";
				}
				item[4] = baseResponse.getElementsByTagName("desc_name")[k].firstChild.nodeValue;
				if ((item[4] == 0) || (item[4] == 'null')) {
					item[4] = "";
				}
				item[5] = baseResponse
						.getElementsByTagName("Date_of_Retirement")[k].firstChild.nodeValue;
				if ((item[5] == 0) || (item[5] == 'null')) {
					item[5] = "";
				}
				item[6] = baseResponse
						.getElementsByTagName("SA_Encashment_of_LS")[k].firstChild.nodeValue;
				if ((item[6] == 0) || (item[6] == 'null')) {
					item[6] = "0";
				}
				item[7] = baseResponse
						.getElementsByTagName("SA_Commutation_of_Pension")[k].firstChild.nodeValue;
				if ((item[7] == 0) || (item[7] == 'null')) {
					item[7] = "0";
				}
				item[8] = baseResponse.getElementsByTagName("SA_Gratuity")[k].firstChild.nodeValue;
				if ((item[8] == 0) || (item[8] == 'null')) {
					item[8] = "0";
				}
				item[9] = baseResponse
						.getElementsByTagName("Date_of_Retirement1")[k].firstChild.nodeValue;
				if (item[9] == 'null') {
					item[9] = "";
				}

				item[10] = baseResponse
						.getElementsByTagName("VR_Encashment_of_LS")[k].firstChild.nodeValue;
				if ((item[10] == 0) || (item[10] == 'null')) {
					item[10] = "0";
				}
				item[11] = baseResponse.getElementsByTagName("VR_Commutation_of_Pension")[k].firstChild.nodeValue;
				if ((item[11] == 0) || (item[11] == 'null')) {
					item[11] = "0";
				}
				item[12] = baseResponse
						.getElementsByTagName("VR_Gratuity")[k].firstChild.nodeValue;
				if (item[12] == 'null') {
					item[12] = "0";
				}

				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				/** Sl No */
				var cell0 = document.createElement("TD");
				var slno = document.createTextNode(seq + 1);
				cell0.appendChild(slno);
				mycurrent_row.appendChild(cell0);

				/** Name of Employee  */
				var cell2 = document.createElement("TD");
				var Name_of_Employee = document.createElement('TEXTAREA',
						'option1');
				Name_of_Employee.name = "Name_of_Employee" + seq;
				Name_of_Employee.id = "Name_of_Employee" + seq;
				Name_of_Employee.value = item[2];
				Name_of_Employee.readOnly = true;
				Name_of_Employee.setAttribute("cols", "5");
				Name_of_Employee.style.height = "30px";
				Name_of_Employee.style.width = "100px";
				cell2.appendChild(Name_of_Employee);
				mycurrent_row.appendChild(cell2);

				/** Designation */
				var cell3 = document.createElement("TD");
				var Designation = document.createElement('TEXTAREA', 'option1');
				Designation.name = "Designation" + seq;
				Designation.id = "Designation" + seq;
				Designation.value = item[4];
				Designation.readOnly = true;
				Designation.setAttribute("cols", "5");
				Designation.style.height = "30px";
				Designation.style.width = "100px";
				cell3.appendChild(Designation);
				mycurrent_row.appendChild(cell3);

				/** Date of Retirement */
				var cell4 = document.createElement("TD");
				var Date_of_Retirement = document.createElement("input");
				Date_of_Retirement.type = "hidden";
				Date_of_Retirement.type = "Text";
				Date_of_Retirement.name = "Date_of_Retirement" + seq;
				Date_of_Retirement.id = "Date_of_Retirement" + seq;
				Date_of_Retirement.value = item[5];
				Date_of_Retirement.size="5";
				Date_of_Retirement.readOnly = true;
				cell4.appendChild(Date_of_Retirement);
				var currentText = document.createTextNode("");
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);

				/** Encashment of L.S */
				var cell5 = document.createElement("TD");
				var SA_Encashment_of_LS = document.createElement("input");
				SA_Encashment_of_LS.type = "hidden";
				SA_Encashment_of_LS.type = "Text";
				SA_Encashment_of_LS.name = "SA_Encashment_of_LS" + seq;
				SA_Encashment_of_LS.id = "SA_Encashment_of_LS" + seq;
				SA_Encashment_of_LS.value = item[6] + ".00";
				SA_Encashment_of_LS.size = "5";
				SA_Encashment_of_LS.style.textAlign = "right";
				cell5.appendChild(SA_Encashment_of_LS);
				var currentText = document.createTextNode("");
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** Commutation of Pension */
				var cell6 = document.createElement("TD");
				var SA_Commutation_of_Pension = document.createElement("input");
				SA_Commutation_of_Pension.type = "Text";
				SA_Commutation_of_Pension.name = "SA_Commutation_of_Pension"
						+ seq;
				SA_Commutation_of_Pension.id = "SA_Commutation_of_Pension"
						+ seq;
				SA_Commutation_of_Pension.value = item[7] + ".00";
				SA_Commutation_of_Pension.style.textAlign = "right";
				//SA_Commutation_of_Pension.maxLength = "10";
				SA_Commutation_of_Pension.size = "5";
				cell6.appendChild(SA_Commutation_of_Pension);
				var currentText = document.createTextNode("");
				cell6.appendChild(currentText);
				mycurrent_row.appendChild(cell6);

				/** Gratuity */
				var cell7 = document.createElement("TD");
				var SA_Gratuity = document.createElement("input");
				SA_Gratuity.type = "Text";
				SA_Gratuity.name = "SA_Gratuity" + seq;
				SA_Gratuity.id = "SA_Gratuity" + seq;
				SA_Gratuity.value = item[8] + ".00";
				SA_Gratuity.style.textAlign = "right";
				//SA_Gratuity.maxLength = "10";
				SA_Gratuity.size = "5";
				cell7.appendChild(SA_Gratuity);
				var currentText = document.createTextNode("");
				cell7.appendChild(currentText);
				mycurrent_row.appendChild(cell7);

				/** Date_of_Retirement */
				var cell8 = document.createElement("TD");
				var Date_of_Retirement1 = document.createElement("input");
				Date_of_Retirement1.type = "Text";
				Date_of_Retirement1.name = "Date_of_Retirement1" + seq;
				Date_of_Retirement1.id = "Date_of_Retirement1" + seq;
				Date_of_Retirement1.value = item[9];
				Date_of_Retirement1.maxLength = "5";
				Date_of_Retirement1.size = "5";
				cell8.appendChild(Date_of_Retirement1);
				var currentText = document.createTextNode("");
				cell8.appendChild(currentText);
				mycurrent_row.appendChild(cell8);

				/** SA_Encashment_of_LS */
				var cell9 = document.createElement("TD");
				var VR_Encashment_of_LS = document.createElement("input");
				VR_Encashment_of_LS.type = "Text";
				VR_Encashment_of_LS.name = "VR_Encashment_of_LS" + seq;
				VR_Encashment_of_LS.id = "VR_Encashment_of_LS" + seq;
				VR_Encashment_of_LS.value = item[10] + ".00";
				VR_Encashment_of_LS.style.textAlign = "right";
				//VR_Encashment_of_LS.maxLength = "5";
				VR_Encashment_of_LS.size = "5";
				cell9.appendChild(VR_Encashment_of_LS);
				var currentText = document.createTextNode("");
				cell9.appendChild(currentText);
				mycurrent_row.appendChild(cell9);

				/** SA_Commutation_of_Pension */
				var cell20 = document.createElement("TD");
				var VR_Commutation_of_Pension = document.createElement("input");
				VR_Commutation_of_Pension.type = "Text";
				VR_Commutation_of_Pension.name = "VR_Commutation_of_Pension"
						+ seq;
				VR_Commutation_of_Pension.id = "VR_Commutation_of_Pension"
						+ seq;
				VR_Commutation_of_Pension.value = item[11] + ".00";
				VR_Commutation_of_Pension.style.textAlign = "right";
				//VR_Commutation_of_Pension.maxLength = "5";
				VR_Commutation_of_Pension.size = "5";
				cell20.appendChild(VR_Commutation_of_Pension);
				var currentText = document.createTextNode("");
				cell20.appendChild(currentText);
				mycurrent_row.appendChild(cell20);

				/** SA_Gratuity	 */
				var cell21 = document.createElement("TD");
				var VR_Gratuity = document.createElement("input");
				VR_Gratuity.type = "Text";
				VR_Gratuity.name = "VR_Gratuity" + seq;
				VR_Gratuity.id = "VR_Gratuity" + seq;
				VR_Gratuity.value = item[12] + ".00";
				//VR_Gratuity.maxLength = "10";
				VR_Gratuity.style.textAlign = "right";
				VR_Gratuity.size = "5";
				cell21.appendChild(VR_Gratuity);
				var currentText = document.createTextNode("");
				cell21.appendChild(currentText);
				mycurrent_row.appendChild(cell21);

				/** emp_id */
				var emp_id = document.createElement("input");
				emp_id.setAttribute("type", "hidden");
				emp_id.setAttribute("value", item[1]);
				emp_id.setAttribute("name", "emp_id" + seq);
				emp_id.setAttribute("id", "emp_id" + seq);
				document.getElementById("frmCivil_Budget_Format_5")
						.appendChild(emp_id);

				/** desc_id */
				var desc_id = document.createElement("input");
				desc_id.setAttribute("type", "hidden");
				desc_id.setAttribute("value", item[3]);
				desc_id.setAttribute("name", "desc_id" + seq);
				desc_id.setAttribute("id", "desc_id" + seq);
				document.getElementById("frmCivil_Budget_Format_5")
						.appendChild(desc_id);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
			document.getElementById("imgfld").style.visibility = "hidden";
		} else {
			document.getElementById("imgfld").style.visibility = "hidden";
			alert("Record Does Not Exist");
		}
		document.getElementById("imgfld").style.visibility = "hidden";
		document.getElementById("RecordCount").value = seq;
	} else if (flag == "Exist") {
		alert("Format-5 have Already Freezed");
		clrForm();
	} else {
		alert("Failed to Load Data");
	}
}

function funcSave() {
	document.getElementById("filter").value = "save";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {

		/*for ( var i = 0; i < rowcount; i++) {
			if (document.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).value == "") {
				alert("Enter Anticipated for the Period Dec to Mar in the Field");
				document.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).focus();
				return false;
			}else if(document.getElementById("RE_for_Year" + i).value == ""){
				alert("Enter RE for the Year in the Field");
				document.getElementById("RE_for_Year" + i).focus();
				return false;
			}else if(document.getElementById("BE_for_Next_Year" + i).value == ""){
				alert("Enter BE for Next Year in the Field");
				document.getElementById("BE_for_Next_Year" + i).focus();
				return false;
			}else {
				return true;
			}
		}*/

	} else {
		alert("No Records Found to Insert...");
		return false;
	}
}
function funcUpdate() {
	document.getElementById("filter").value = "update";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {

		/*for ( var i = 0; i < rowcount; i++) {
			if (document.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).value == "") {
				alert("Enter Anticipated for the Period Dec to Mar in the Field");
				document.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).focus();
				return false;
			}else if(document.getElementById("RE_for_Year" + i).value == ""){
				alert("Enter RE for the Year in the Field");
				document.getElementById("RE_for_Year" + i).focus();
				return false;
			}else if(document.getElementById("BE_for_Next_Year" + i).value == ""){
				alert("Enter BE for Next Year in the Field");
				document.getElementById("BE_for_Next_Year" + i).focus();
				return false;
			}else {
				return true;
			}
		}*/

	} else {
		alert("No Records Found to Insert...");
		return false;
	}
}

function funcDelete() {
	var r = confirm("Are U Sure to Continue?");
	if (r == true) {
		document.getElementById("filter").value = "delete";
		return true;
	} else {
		return false;
	}
}

function clrForm() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.getElementById("imgfld").style.visibility = "hidden";
	document.frmCivil_Budget_Format_5.butView.disabled = false;
	document.frmCivil_Budget_Format_5.butSub.disabled = false;
	document.frmCivil_Budget_Format_5.butDelete.disabled = true;
	document.frmCivil_Budget_Format_5.butUpdate.disabled = true;
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
