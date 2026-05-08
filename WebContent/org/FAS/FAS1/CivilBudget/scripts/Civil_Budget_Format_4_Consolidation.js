//		Civil_Budget_Format_4_Consolidation		//
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
				deleteRow(baseResponse)
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

function initialLoade() {

	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1 = fy.split('-');
	var y1 = fy1[0];
	var y2 = fy1[1];

	document.frmCivil_Budget_Format_4_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_4_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_4_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_4_Consolidation.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_4_Consolidation?command=get&y1="
			+ y1
			+ "&y2="
			+ y2
			+ "&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

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

		var r_no = baseResponse.getElementsByTagName("slno");
		seq = 0;

		var item = new Array();
		if (r_no.length != 0) {
			for ( var k = 0; k < r_no.length; k++) {
				item[0] = baseResponse.getElementsByTagName("Name_of_Employee")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Designation")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Date_of_Retirement")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="";
				}
				item[3] = baseResponse
						.getElementsByTagName("S_Amt_Paid_upto_Nov_E_of_LS")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("S_Amt_Paid_upto_Nov_Cmutation_of_Pension")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("S_Amt_Paid_upto_Nov_Gratuity")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Date_of_Retirement1")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="";
				}
				item[7] = baseResponse
						.getElementsByTagName("VR_Amt_Paid_upto_Nov_E_of_LS")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("VR_Amt_Paid_upto_Nov_Cmutation_of_Pension")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("VR_Amt_Paid_upto_Nov_Gratuity")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("S_Anticipated_Amt_E_of_LS")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("S_Anticipated_Amt_Cmutation_of_Pension")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("S_Anticipated_Amt_Gratuity")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("VR_Anticipated_Amt_E_of_LS")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("VR_Anticipated_Amt_Cmutation_of_Pension")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
				}
				item[15] = baseResponse
						.getElementsByTagName("VR_Anticipated_Amt_Gratuity")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="0";
				}
							
				item[18] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[18]=='null'){
					item[18]="";
				}

				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				var cell = document.createElement("TD");
				var anc = document.createElement("input");
				anc.type = "checkbox";
				anc.id = "check" + seq;
				anc.name = "check" + seq;
				anc.checked = true;
				cell.appendChild(anc);
				mycurrent_row.appendChild(cell);

				/** Sl.No */
				var cell1 = document.createElement("TD");
				var slno = document.createElement("input");
				slno.type = "text";
				slno.name = "slno" + seq;
				slno.id = "slno" + seq;
				slno.value = seq+1;	
				slno.size = "2";
				cell1.appendChild(slno);
				mycurrent_row.appendChild(cell1);
				
				/** Name of Employee */
				var cell2 = document.createElement("TD");
				var Name_of_Employee = document.createElement("input");
				Name_of_Employee.type = "text";
				Name_of_Employee.name = "Name_of_Employee" + seq;
				Name_of_Employee.id = "Name_of_Employee" + seq;
				Name_of_Employee.value = item[0];	
				cell2.appendChild(Name_of_Employee);
				mycurrent_row.appendChild(cell2);

				/** Designation */
				var cell3 = document.createElement("TD");
				var Designation = document.createElement("input");
				Designation.type = "text";
				Designation.name = "Designation" + seq;
				Designation.id = "Designation" + seq;
				Designation.value = item[1];
				cell3.appendChild(Designation);
				mycurrent_row.appendChild(cell3);

				/** Date of Retirement */
				var cell4 = document.createElement("TD");
				var Date_of_Retirement  = document.createElement("input");
				Date_of_Retirement.type = "text";
				Date_of_Retirement.name = "Date_of_Retirement" + seq;
				Date_of_Retirement.id = "Date_of_Retirement" + seq;
				Date_of_Retirement.value = item[2];
				Date_of_Retirement.size = "10";
				cell4.appendChild(Date_of_Retirement );
				mycurrent_row.appendChild(cell4);

				/** Superannuation Encashment of L.S */
				var cell5 = document.createElement("TD");
				var S_Amt_Paid_upto_Nov_E_of_LS = document.createElement("input");
				S_Amt_Paid_upto_Nov_E_of_LS.type = "text";
				S_Amt_Paid_upto_Nov_E_of_LS.name = "S_Amt_Paid_upto_Nov_E_of_LS" + seq;
				S_Amt_Paid_upto_Nov_E_of_LS.id = "S_Amt_Paid_upto_Nov_E_of_LS" + seq;
				S_Amt_Paid_upto_Nov_E_of_LS.value = item[3]+"0";
				S_Amt_Paid_upto_Nov_E_of_LS.size = "10";
				S_Amt_Paid_upto_Nov_E_of_LS.style.textAlign="right";
				cell5.appendChild(S_Amt_Paid_upto_Nov_E_of_LS);
				mycurrent_row.appendChild(cell5);

				/** Superannuation Commutation of Pension */
				var cell6 = document.createElement("TD");
				var S_Amt_Paid_upto_Nov_Cmutation_of_Pension = document.createElement("input");
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.type = "text";
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.name = "S_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.id = "S_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.value = item[4]+"0";
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.size = "10";
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.style.textAlign="right";
				cell6.appendChild(S_Amt_Paid_upto_Nov_Cmutation_of_Pension);
				mycurrent_row.appendChild(cell6);

				/** Superannuation Gratuity */
				var cell7 = document.createElement("TD");
				var S_Amt_Paid_upto_Nov_Gratuity = document.createElement("input");
				S_Amt_Paid_upto_Nov_Gratuity.type = "text";
				S_Amt_Paid_upto_Nov_Gratuity.name = "S_Amt_Paid_upto_Nov_Gratuity" + seq;
				S_Amt_Paid_upto_Nov_Gratuity.id = "S_Amt_Paid_upto_Nov_Gratuity" + seq;
				S_Amt_Paid_upto_Nov_Gratuity.value = item[5]+"0";
				S_Amt_Paid_upto_Nov_Gratuity.size = "10";
				S_Amt_Paid_upto_Nov_Gratuity.style.textAlign="right";
				cell7.appendChild(S_Amt_Paid_upto_Nov_Gratuity);
				mycurrent_row.appendChild(cell7);

				/** Date of Retirement1 */
				var cell8 = document.createElement("TD");
				var Date_of_Retirement1 = document.createElement("input");
				Date_of_Retirement1.type = "text";
				Date_of_Retirement1.name = "Date_of_Retirement1" + seq;
				Date_of_Retirement1.id = "Date_of_Retirement1" + seq;
				Date_of_Retirement1.value = item[6];
				Date_of_Retirement1.size = "10";	
				cell8.appendChild(Date_of_Retirement1);
				mycurrent_row.appendChild(cell8);

				/** Voluntary Retirement Encashment of L.S */
				var cell9 = document.createElement("TD");
				var VR_Amt_Paid_upto_Nov_E_of_LS = document.createElement("input");
				VR_Amt_Paid_upto_Nov_E_of_LS.type = "text";
				VR_Amt_Paid_upto_Nov_E_of_LS.name = "VR_Amt_Paid_upto_Nov_E_of_LS" + seq;
				VR_Amt_Paid_upto_Nov_E_of_LS.id = "VR_Amt_Paid_upto_Nov_E_of_LS" + seq;
				VR_Amt_Paid_upto_Nov_E_of_LS.value = item[7]+"0";
				VR_Amt_Paid_upto_Nov_E_of_LS.size = "10";	
				VR_Amt_Paid_upto_Nov_E_of_LS.style.textAlign="right";
				cell9.appendChild(VR_Amt_Paid_upto_Nov_E_of_LS);
				mycurrent_row.appendChild(cell9);
				
				/** Voluntary Retirement Commutation of Pension */
				var cell99 = document.createElement("TD");
				var VR_Amt_Paid_upto_Nov_Cmutation_of_Pension = document.createElement("input");
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.type = "text";
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.name = "VR_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.id = "VR_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.value = item[8]+"0";
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.size = "10";	
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.style.textAlign="right";
				cell99.appendChild(VR_Amt_Paid_upto_Nov_Cmutation_of_Pension);
				mycurrent_row.appendChild(cell99);

				/** Voluntary Retirement Gratuity */
				var cell20 = document.createElement("TD");
				var VR_Amt_Paid_upto_Nov_Gratuity = document.createElement("input");
				VR_Amt_Paid_upto_Nov_Gratuity.type = "text";
				VR_Amt_Paid_upto_Nov_Gratuity.name = "VR_Amt_Paid_upto_Nov_Gratuity" + seq;
				VR_Amt_Paid_upto_Nov_Gratuity.id = "VR_Amt_Paid_upto_Nov_Gratuity" + seq;
				VR_Amt_Paid_upto_Nov_Gratuity.value = item[9]+"0";	
				VR_Amt_Paid_upto_Nov_Gratuity.size = "10";
				VR_Amt_Paid_upto_Nov_Gratuity.style.textAlign="right";
				cell20.appendChild(VR_Amt_Paid_upto_Nov_Gratuity);
				mycurrent_row.appendChild(cell20);

				/** Superannuation1 Encashment of L.S */
				var cel21 = document.createElement("TD");
				var S_Anticipated_Amt_E_of_LS = document.createElement("input");
				S_Anticipated_Amt_E_of_LS.type = "text";
				S_Anticipated_Amt_E_of_LS.name = "S_Anticipated_Amt_E_of_LS" + seq;
				S_Anticipated_Amt_E_of_LS.id = "S_Anticipated_Amt_E_of_LS" + seq;
				S_Anticipated_Amt_E_of_LS.value = item[10]+"0";
				S_Anticipated_Amt_E_of_LS.size = "10";
				S_Anticipated_Amt_E_of_LS.style.textAlign="right";
				cel21.appendChild(S_Anticipated_Amt_E_of_LS);
				mycurrent_row.appendChild(cel21);

				/** Superannuation1 Commutation of Pension */
				var cel22 = document.createElement("TD");
				var S_Anticipated_Amt_Cmutation_of_Pension  = document.createElement("input");
				S_Anticipated_Amt_Cmutation_of_Pension.type = "text";
				S_Anticipated_Amt_Cmutation_of_Pension.name = "S_Anticipated_Amt_Cmutation_of_Pension" + seq;
				S_Anticipated_Amt_Cmutation_of_Pension.id = "S_Anticipated_Amt_Cmutation_of_Pension" + seq;
				S_Anticipated_Amt_Cmutation_of_Pension.value = item[11]+"0";
				S_Anticipated_Amt_Cmutation_of_Pension.size = "10";
				S_Anticipated_Amt_Cmutation_of_Pension.style.textAlign="right";
				cel22.appendChild(S_Anticipated_Amt_Cmutation_of_Pension );
				mycurrent_row.appendChild(cel22);

				/** Superannuation1 Gratuity */
				var cel23 = document.createElement("TD");
				var S_Anticipated_Amt_Gratuity  = document.createElement("input");
				S_Anticipated_Amt_Gratuity.type = "text";
				S_Anticipated_Amt_Gratuity.name = "S_Anticipated_Amt_Gratuity" + seq;
				S_Anticipated_Amt_Gratuity.id = "S_Anticipated_Amt_Gratuity" + seq;
				S_Anticipated_Amt_Gratuity.value = item[12]+"0";
				S_Anticipated_Amt_Gratuity.size = "10";
				S_Anticipated_Amt_Gratuity.style.textAlign="right";
				cel23.appendChild(S_Anticipated_Amt_Gratuity );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** Voluntary Retirement1 Encashment of L.S */
				var cel24 = document.createElement("TD");
				var VR_Anticipated_Amt_E_of_LS = document.createElement("input");
				VR_Anticipated_Amt_E_of_LS.type = "text";
				VR_Anticipated_Amt_E_of_LS.name = "VR_Anticipated_Amt_E_of_LS" + seq;
				VR_Anticipated_Amt_E_of_LS.id = "VR_Anticipated_Amt_E_of_LS" + seq;
				VR_Anticipated_Amt_E_of_LS.value = item[13]+"0";
				VR_Anticipated_Amt_E_of_LS.size = "10";
				VR_Anticipated_Amt_E_of_LS.style.textAlign="right";
				cel24.appendChild(VR_Anticipated_Amt_E_of_LS);
				mycurrent_row.appendChild(cel24);

				/** Voluntary Retirement1 Commutation of Pension */
				var cell25 = document.createElement("TD");
				var VR_Anticipated_Amt_Cmutation_of_Pension = document.createElement("input");
				VR_Anticipated_Amt_Cmutation_of_Pension.type = "text";
				VR_Anticipated_Amt_Cmutation_of_Pension.name = "VR_Anticipated_Amt_Cmutation_of_Pension" + seq;
				VR_Anticipated_Amt_Cmutation_of_Pension.id = "VR_Anticipated_Amt_Cmutation_of_Pension" + seq;
				VR_Anticipated_Amt_Cmutation_of_Pension.value = item[14]+"0";
				VR_Anticipated_Amt_Cmutation_of_Pension.size = "10";
				VR_Anticipated_Amt_Cmutation_of_Pension.style.textAlign="right";
				cell25.appendChild(VR_Anticipated_Amt_Cmutation_of_Pension);
				mycurrent_row.appendChild(cell25);

				/** Voluntary Retirement1 Gratuity */
				var cel26 = document.createElement("TD");
				var VR_Anticipated_Amt_Gratuity  = document.createElement("input");
				VR_Anticipated_Amt_Gratuity.type = "text";
				VR_Anticipated_Amt_Gratuity.name = "VR_Anticipated_Amt_Gratuity" + seq;
				VR_Anticipated_Amt_Gratuity.id = "VR_Anticipated_Amt_Gratuity" + seq;
				VR_Anticipated_Amt_Gratuity.value = item[15]+"0";
				VR_Anticipated_Amt_Gratuity.size = "10";
				VR_Anticipated_Amt_Gratuity.style.textAlign="right";
				cel26.appendChild(VR_Anticipated_Amt_Gratuity);
				mycurrent_row.appendChild(cel26);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[18]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_4_Consolidation")
						.appendChild(slno_db);
				
				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
			document.getElementById("RecordCount").value = seq;
		} else {
			alert("Record Does Not Exist");
		}
	} else if (flag == "Freeze_Pending") {
		var ofiice_type = baseResponse.getElementsByTagName("ofiice_type")[0].firstChild.nodeValue;
		alert("All the "
				+ ofiice_type
				+ " Under your office have Not Freezed the Civil Budget Closure");
		clrForm();
	} else if (flag == "Freeze_Pending1") {
		alert("The Civil Budget Closure Should have been Freezed for your Office Also");
		clrForm();
	} else if (flag == "Freeze_Done") {
		alert("The Civil Budget Closure Consolidate Have Already Freezed");
		clrForm();
	} else {
		alert("Failed to Load Data");
	}
}

function initialLoad() {	
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
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
	var financialyear = 0;
	var financialyear1 = 0;
	var financialyear2 = 0;
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}
	if (month < 4) {
		financialyear = year1 + "-" + year;
		financialyear1 = (parseInt(year1)-1) + "-" + (parseInt(year)-1);
		financialyear2 = (parseInt(year1)-2) + "-" + (parseInt(year)-2);		
	} else {
		financialyear = year + "-" + year1;
		financialyear1 = (parseInt(year)-1) + "-" + (parseInt(year1)-1);
		financialyear2 = (parseInt(year)-2) + "-" + (parseInt(year1)-2);		
	}
	for(var k=0;k<3;k++)
	{
		if(k==0)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = financialyear2;
	  		var txt = document.createTextNode(financialyear2);
	  		op.appendChild(txt);
	  		se.appendChild(op);
		}else if(k==1)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = financialyear1;
	  		var txt = document.createTextNode(financialyear1);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}else if(k==2)
		{
			var se = document.getElementById("cmbFinancialYear");
	  		var op = document.createElement("OPTION");
	  		op.value = financialyear;
	  		var txt = document.createTextNode(financialyear);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}                               
	}    
	document.getElementById("cmbFinancialYear").value=financialyear;

	document.frmCivil_Budget_Format_4_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_4_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_4_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_4_Consolidation.butUpdate.disabled = true;
}

function AddRow() {
	/* Add Row */

	/** Get TBody Object */
	var tbody = document.getElementById("grid_body");

	/** Create Table Row */
	var mycurrent_row = document.createElement("TR");
	mycurrent_row.id = seq;

	var cell = document.createElement("TD");
	var anc = document.createElement("input");
	anc.type = "checkbox";
	anc.id = "check" + seq;
	anc.name = "check" + seq;
	anc.checked = true;
	anc.disabled = true
	anc.value = seq;
	cell.appendChild(anc);
	mycurrent_row.appendChild(cell);

	/** Sl.No */
	var cell1 = document.createElement("TD");
	var slno = document.createElement("input");
	slno.type = "text";
	slno.name = "slno" + seq;
	slno.id = "slno" + seq;
	slno.value = seq+1;	
	slno.size = "2";
	cell1.appendChild(slno);
	mycurrent_row.appendChild(cell1);
	
	/** Name of Employee */
	var cell2 = document.createElement("TD");
	var Name_of_Employee = document.createElement("input");
	Name_of_Employee.type = "text";
	Name_of_Employee.name = "Name_of_Employee" + seq;
	Name_of_Employee.id = "Name_of_Employee" + seq;
	Name_of_Employee.value = "";	
	cell2.appendChild(Name_of_Employee);
	mycurrent_row.appendChild(cell2);

	/** Designation */
	var cell3 = document.createElement("TD");
	var Designation = document.createElement("input");
	Designation.type = "text";
	Designation.name = "Designation" + seq;
	Designation.id = "Designation" + seq;
	Designation.value = "";
	cell3.appendChild(Designation);
	mycurrent_row.appendChild(cell3);

	/** Date of Retirement */
	var cell4 = document.createElement("TD");
	var Date_of_Retirement  = document.createElement("input");
	Date_of_Retirement.type = "text";
	Date_of_Retirement.name = "Date_of_Retirement" + seq;
	Date_of_Retirement.id = "Date_of_Retirement" + seq;
	Date_of_Retirement.value = "";
	Date_of_Retirement.size = "10";
	cell4.appendChild(Date_of_Retirement );
	mycurrent_row.appendChild(cell4);

	/** Superannuation Encashment of L.S */
	var cell5 = document.createElement("TD");
	var S_Amt_Paid_upto_Nov_E_of_LS = document.createElement("input");
	S_Amt_Paid_upto_Nov_E_of_LS.type = "text";
	S_Amt_Paid_upto_Nov_E_of_LS.name = "S_Amt_Paid_upto_Nov_E_of_LS" + seq;
	S_Amt_Paid_upto_Nov_E_of_LS.id = "S_Amt_Paid_upto_Nov_E_of_LS" + seq;
	S_Amt_Paid_upto_Nov_E_of_LS.value = "";
	S_Amt_Paid_upto_Nov_E_of_LS.size = "10";
	cell5.appendChild(S_Amt_Paid_upto_Nov_E_of_LS);
	mycurrent_row.appendChild(cell5);

	/** Superannuation Commutation of Pension */
	var cell6 = document.createElement("TD");
	var S_Amt_Paid_upto_Nov_Cmutation_of_Pension = document.createElement("input");
	S_Amt_Paid_upto_Nov_Cmutation_of_Pension.type = "text";
	S_Amt_Paid_upto_Nov_Cmutation_of_Pension.name = "S_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
	S_Amt_Paid_upto_Nov_Cmutation_of_Pension.id = "S_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
	S_Amt_Paid_upto_Nov_Cmutation_of_Pension.value = "";
	S_Amt_Paid_upto_Nov_Cmutation_of_Pension.size = "10";
	cell6.appendChild(S_Amt_Paid_upto_Nov_Cmutation_of_Pension);
	mycurrent_row.appendChild(cell6);

	/** Superannuation Gratuity */
	var cell7 = document.createElement("TD");
	var S_Amt_Paid_upto_Nov_Gratuity = document.createElement("input");
	S_Amt_Paid_upto_Nov_Gratuity.type = "text";
	S_Amt_Paid_upto_Nov_Gratuity.name = "S_Amt_Paid_upto_Nov_Gratuity" + seq;
	S_Amt_Paid_upto_Nov_Gratuity.id = "S_Amt_Paid_upto_Nov_Gratuity" + seq;
	S_Amt_Paid_upto_Nov_Gratuity.value = "";
	S_Amt_Paid_upto_Nov_Gratuity.size = "10";
	cell7.appendChild(S_Amt_Paid_upto_Nov_Gratuity);
	mycurrent_row.appendChild(cell7);

	/** Date of Retirement1 */
	var cell8 = document.createElement("TD");
	var Date_of_Retirement1 = document.createElement("input");
	Date_of_Retirement1.type = "text";
	Date_of_Retirement1.name = "Date_of_Retirement1" + seq;
	Date_of_Retirement1.id = "Date_of_Retirement1" + seq;
	Date_of_Retirement1.value = "";
	Date_of_Retirement1.size = "10";	
	cell8.appendChild(Date_of_Retirement1);
	mycurrent_row.appendChild(cell8);

	/** Voluntary Retirement Encashment of L.S */
	var cell9 = document.createElement("TD");
	var VR_Amt_Paid_upto_Nov_E_of_LS = document.createElement("input");
	VR_Amt_Paid_upto_Nov_E_of_LS.type = "text";
	VR_Amt_Paid_upto_Nov_E_of_LS.name = "VR_Amt_Paid_upto_Nov_E_of_LS" + seq;
	VR_Amt_Paid_upto_Nov_E_of_LS.id = "VR_Amt_Paid_upto_Nov_E_of_LS" + seq;
	VR_Amt_Paid_upto_Nov_E_of_LS.value = "";
	VR_Amt_Paid_upto_Nov_E_of_LS.size = "10";	
	cell9.appendChild(VR_Amt_Paid_upto_Nov_E_of_LS);
	mycurrent_row.appendChild(cell9);
	
	/** Voluntary Retirement Commutation of Pension */
	var cell99 = document.createElement("TD");
	var VR_Amt_Paid_upto_Nov_Cmutation_of_Pension = document.createElement("input");
	VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.type = "text";
	VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.name = "VR_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
	VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.id = "VR_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
	VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.value = "";
	VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.size = "10";		
	cell99.appendChild(VR_Amt_Paid_upto_Nov_Cmutation_of_Pension);
	mycurrent_row.appendChild(cell99);

	/** Voluntary Retirement Gratuity */
	var cell20 = document.createElement("TD");
	var VR_Amt_Paid_upto_Nov_Gratuity = document.createElement("input");
	VR_Amt_Paid_upto_Nov_Gratuity.type = "text";
	VR_Amt_Paid_upto_Nov_Gratuity.name = "VR_Amt_Paid_upto_Nov_Gratuity" + seq;
	VR_Amt_Paid_upto_Nov_Gratuity.id = "VR_Amt_Paid_upto_Nov_Gratuity" + seq;
	VR_Amt_Paid_upto_Nov_Gratuity.value = "";	
	VR_Amt_Paid_upto_Nov_Gratuity.size = "10";
	cell20.appendChild(VR_Amt_Paid_upto_Nov_Gratuity);
	mycurrent_row.appendChild(cell20);

	/** Superannuation1 Encashment of L.S */
	var cel21 = document.createElement("TD");
	var S_Anticipated_Amt_E_of_LS = document.createElement("input");
	S_Anticipated_Amt_E_of_LS.type = "text";
	S_Anticipated_Amt_E_of_LS.name = "S_Anticipated_Amt_E_of_LS" + seq;
	S_Anticipated_Amt_E_of_LS.id = "S_Anticipated_Amt_E_of_LS" + seq;
	S_Anticipated_Amt_E_of_LS.value = "";
	S_Anticipated_Amt_E_of_LS.size = "10";
	cel21.appendChild(S_Anticipated_Amt_E_of_LS);
	mycurrent_row.appendChild(cel21);

	/** Superannuation1 Commutation of Pension */
	var cel22 = document.createElement("TD");
	var S_Anticipated_Amt_Cmutation_of_Pension  = document.createElement("input");
	S_Anticipated_Amt_Cmutation_of_Pension.type = "text";
	S_Anticipated_Amt_Cmutation_of_Pension.name = "S_Anticipated_Amt_Cmutation_of_Pension" + seq;
	S_Anticipated_Amt_Cmutation_of_Pension.id = "S_Anticipated_Amt_Cmutation_of_Pension" + seq;
	S_Anticipated_Amt_Cmutation_of_Pension.value = "";
	S_Anticipated_Amt_Cmutation_of_Pension.size = "10";
	cel22.appendChild(S_Anticipated_Amt_Cmutation_of_Pension );
	mycurrent_row.appendChild(cel22);

	/** Superannuation1 Gratuity */
	var cel23 = document.createElement("TD");
	var S_Anticipated_Amt_Gratuity  = document.createElement("input");
	S_Anticipated_Amt_Gratuity.type = "text";
	S_Anticipated_Amt_Gratuity.name = "S_Anticipated_Amt_Gratuity" + seq;
	S_Anticipated_Amt_Gratuity.id = "S_Anticipated_Amt_Gratuity" + seq;
	S_Anticipated_Amt_Gratuity.value = "";
	S_Anticipated_Amt_Gratuity.size = "10";
	cel23.appendChild(S_Anticipated_Amt_Gratuity );
	mycurrent_row.appendChild(cel23);
	tbody.appendChild(mycurrent_row);

	/** Voluntary Retirement1 Encashment of L.S */
	var cel24 = document.createElement("TD");
	var VR_Anticipated_Amt_E_of_LS = document.createElement("input");
	VR_Anticipated_Amt_E_of_LS.type = "text";
	VR_Anticipated_Amt_E_of_LS.name = "VR_Anticipated_Amt_E_of_LS" + seq;
	VR_Anticipated_Amt_E_of_LS.id = "VR_Anticipated_Amt_E_of_LS" + seq;
	VR_Anticipated_Amt_E_of_LS.value = "";
	VR_Anticipated_Amt_E_of_LS.size = "10";
	cel24.appendChild(VR_Anticipated_Amt_E_of_LS);
	mycurrent_row.appendChild(cel24);

	/** Voluntary Retirement1 Commutation of Pension */
	var cell25 = document.createElement("TD");
	var VR_Anticipated_Amt_Cmutation_of_Pension = document.createElement("input");
	VR_Anticipated_Amt_Cmutation_of_Pension.type = "text";
	VR_Anticipated_Amt_Cmutation_of_Pension.name = "VR_Anticipated_Amt_Cmutation_of_Pension" + seq;
	VR_Anticipated_Amt_Cmutation_of_Pension.id = "VR_Anticipated_Amt_Cmutation_of_Pension" + seq;
	VR_Anticipated_Amt_Cmutation_of_Pension.value = "";
	VR_Anticipated_Amt_Cmutation_of_Pension.size = "10";
	cell25.appendChild(VR_Anticipated_Amt_Cmutation_of_Pension);
	mycurrent_row.appendChild(cell25);

	/** Voluntary Retirement1 Gratuity */
	var cel26 = document.createElement("TD");
	var VR_Anticipated_Amt_Gratuity  = document.createElement("input");
	VR_Anticipated_Amt_Gratuity.type = "text";
	VR_Anticipated_Amt_Gratuity.name = "VR_Anticipated_Amt_Gratuity" + seq;
	VR_Anticipated_Amt_Gratuity.id = "VR_Anticipated_Amt_Gratuity" + seq;
	VR_Anticipated_Amt_Gratuity.value = "";
	VR_Anticipated_Amt_Gratuity.size = "10";
	cel26.appendChild(VR_Anticipated_Amt_Gratuity);
	mycurrent_row.appendChild(cel26);	

	tbody.appendChild(mycurrent_row);
	/** Increment Sequence Number */
	seq = seq + 1;

}

function ClearRow() {
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	tbody.deleteRow(rowcount - 1);
	seq = seq - 1;
}

function funcSave() {
	document.getElementById("filter").value = "save";
	document.getElementById("RecordCount").value = seq;
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {
		return true;
	} else {
		alert("No Records Found to Insert...");
		return false;
	}	
}

function LoadData(path) {
	//alert(path);
	document.frmCivil_Budget_Format_4_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_4_Consolidation.butSub.disabled = true;
	document.frmCivil_Budget_Format_4_Consolidation.butDelete.disabled = false;
	document.frmCivil_Budget_Format_4_Consolidation.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;

	var url = path + "/Civil_Budget_Format_4_Consolidation?filter=view&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
			+ RecordCount;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function LoadData_View(baseResponse) {
	//alert("RK");
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
				item[0] = baseResponse.getElementsByTagName("Name_of_Employee")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Designation")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Date_of_Retirement")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="";
				}
				item[3] = baseResponse
						.getElementsByTagName("S_Amt_Paid_upto_Nov_E_of_LS")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("S_Amt_Paid_upto_Nov_Cmutation_of_Pension")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("S_Amt_Paid_upto_Nov_Gratuity")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Date_of_Retirement1")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="";
				}
				item[7] = baseResponse
						.getElementsByTagName("VR_Amt_Paid_upto_Nov_E_of_LS")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("VR_Amt_Paid_upto_Nov_Cmutation_of_Pension")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("VR_Amt_Paid_upto_Nov_Gratuity")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("S_Anticipated_Amt_E_of_LS")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("S_Anticipated_Amt_Cmutation_of_Pension")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("S_Anticipated_Amt_Gratuity")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("VR_Anticipated_Amt_E_of_LS")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("VR_Anticipated_Amt_Cmutation_of_Pension")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
				}
				item[15] = baseResponse
						.getElementsByTagName("VR_Anticipated_Amt_Gratuity")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="0";
				}
							
				item[18] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[18]=='null'){
					item[18]="";
				}

				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				var cell = document.createElement("TD");
				var anc = document.createElement("input");
				anc.type = "checkbox";
				anc.id = "check" + seq;
				anc.name = "check" + seq;
				anc.checked = true;
				cell.appendChild(anc);
				mycurrent_row.appendChild(cell);

				/** Sl.No */
				var cell1 = document.createElement("TD");
				var slno = document.createElement("input");
				slno.type = "text";
				slno.name = "slno" + seq;
				slno.id = "slno" + seq;
				slno.value = seq+1;	
				slno.size = "2";
				cell1.appendChild(slno);
				mycurrent_row.appendChild(cell1);
				
				/** Name of Employee */
				var cell2 = document.createElement("TD");
				var Name_of_Employee = document.createElement("input");
				Name_of_Employee.type = "text";
				Name_of_Employee.name = "Name_of_Employee" + seq;
				Name_of_Employee.id = "Name_of_Employee" + seq;
				Name_of_Employee.value = item[0];	
				cell2.appendChild(Name_of_Employee);
				mycurrent_row.appendChild(cell2);

				/** Designation */
				var cell3 = document.createElement("TD");
				var Designation = document.createElement("input");
				Designation.type = "text";
				Designation.name = "Designation" + seq;
				Designation.id = "Designation" + seq;
				Designation.value = item[1];
				cell3.appendChild(Designation);
				mycurrent_row.appendChild(cell3);

				/** Date of Retirement */
				var cell4 = document.createElement("TD");
				var Date_of_Retirement  = document.createElement("input");
				Date_of_Retirement.type = "text";
				Date_of_Retirement.name = "Date_of_Retirement" + seq;
				Date_of_Retirement.id = "Date_of_Retirement" + seq;
				Date_of_Retirement.value = item[2];
				Date_of_Retirement.size = "10";
				cell4.appendChild(Date_of_Retirement );
				mycurrent_row.appendChild(cell4);

				/** Superannuation Encashment of L.S */
				var cell5 = document.createElement("TD");
				var S_Amt_Paid_upto_Nov_E_of_LS = document.createElement("input");
				S_Amt_Paid_upto_Nov_E_of_LS.type = "text";
				S_Amt_Paid_upto_Nov_E_of_LS.name = "S_Amt_Paid_upto_Nov_E_of_LS" + seq;
				S_Amt_Paid_upto_Nov_E_of_LS.id = "S_Amt_Paid_upto_Nov_E_of_LS" + seq;
				S_Amt_Paid_upto_Nov_E_of_LS.value = item[3]+"0";
				S_Amt_Paid_upto_Nov_E_of_LS.size = "10";
				S_Amt_Paid_upto_Nov_E_of_LS.style.textAlign="right";
				cell5.appendChild(S_Amt_Paid_upto_Nov_E_of_LS);
				mycurrent_row.appendChild(cell5);

				/** Superannuation Commutation of Pension */
				var cell6 = document.createElement("TD");
				var S_Amt_Paid_upto_Nov_Cmutation_of_Pension = document.createElement("input");
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.type = "text";
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.name = "S_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.id = "S_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.value = item[4]+"0";
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.size = "10";
				S_Amt_Paid_upto_Nov_Cmutation_of_Pension.style.textAlign="right";
				cell6.appendChild(S_Amt_Paid_upto_Nov_Cmutation_of_Pension);
				mycurrent_row.appendChild(cell6);

				/** Superannuation Gratuity */
				var cell7 = document.createElement("TD");
				var S_Amt_Paid_upto_Nov_Gratuity = document.createElement("input");
				S_Amt_Paid_upto_Nov_Gratuity.type = "text";
				S_Amt_Paid_upto_Nov_Gratuity.name = "S_Amt_Paid_upto_Nov_Gratuity" + seq;
				S_Amt_Paid_upto_Nov_Gratuity.id = "S_Amt_Paid_upto_Nov_Gratuity" + seq;
				S_Amt_Paid_upto_Nov_Gratuity.value = item[5]+"0";
				S_Amt_Paid_upto_Nov_Gratuity.size = "10";
				S_Amt_Paid_upto_Nov_Gratuity.style.textAlign="right";
				cell7.appendChild(S_Amt_Paid_upto_Nov_Gratuity);
				mycurrent_row.appendChild(cell7);

				/** Date of Retirement1 */
				var cell8 = document.createElement("TD");
				var Date_of_Retirement1 = document.createElement("input");
				Date_of_Retirement1.type = "text";
				Date_of_Retirement1.name = "Date_of_Retirement1" + seq;
				Date_of_Retirement1.id = "Date_of_Retirement1" + seq;
				Date_of_Retirement1.value = item[6];
				Date_of_Retirement1.size = "10";	
				cell8.appendChild(Date_of_Retirement1);
				mycurrent_row.appendChild(cell8);

				/** Voluntary Retirement Encashment of L.S */
				var cell9 = document.createElement("TD");
				var VR_Amt_Paid_upto_Nov_E_of_LS = document.createElement("input");
				VR_Amt_Paid_upto_Nov_E_of_LS.type = "text";
				VR_Amt_Paid_upto_Nov_E_of_LS.name = "VR_Amt_Paid_upto_Nov_E_of_LS" + seq;
				VR_Amt_Paid_upto_Nov_E_of_LS.id = "VR_Amt_Paid_upto_Nov_E_of_LS" + seq;
				VR_Amt_Paid_upto_Nov_E_of_LS.value = item[7]+"0";
				VR_Amt_Paid_upto_Nov_E_of_LS.size = "10";	
				VR_Amt_Paid_upto_Nov_E_of_LS.style.textAlign="right";
				cell9.appendChild(VR_Amt_Paid_upto_Nov_E_of_LS);
				mycurrent_row.appendChild(cell9);
				
				/** Voluntary Retirement Commutation of Pension */
				var cell99 = document.createElement("TD");
				var VR_Amt_Paid_upto_Nov_Cmutation_of_Pension = document.createElement("input");
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.type = "text";
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.name = "VR_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.id = "VR_Amt_Paid_upto_Nov_Cmutation_of_Pension" + seq;
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.value = item[8]+"0";
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.size = "10";	
				VR_Amt_Paid_upto_Nov_Cmutation_of_Pension.style.textAlign="right";
				cell99.appendChild(VR_Amt_Paid_upto_Nov_Cmutation_of_Pension);
				mycurrent_row.appendChild(cell99);

				/** Voluntary Retirement Gratuity */
				var cell20 = document.createElement("TD");
				var VR_Amt_Paid_upto_Nov_Gratuity = document.createElement("input");
				VR_Amt_Paid_upto_Nov_Gratuity.type = "text";
				VR_Amt_Paid_upto_Nov_Gratuity.name = "VR_Amt_Paid_upto_Nov_Gratuity" + seq;
				VR_Amt_Paid_upto_Nov_Gratuity.id = "VR_Amt_Paid_upto_Nov_Gratuity" + seq;
				VR_Amt_Paid_upto_Nov_Gratuity.value = item[9]+"0";	
				VR_Amt_Paid_upto_Nov_Gratuity.size = "10";
				VR_Amt_Paid_upto_Nov_Gratuity.style.textAlign="right";
				cell20.appendChild(VR_Amt_Paid_upto_Nov_Gratuity);
				mycurrent_row.appendChild(cell20);

				/** Superannuation1 Encashment of L.S */
				var cel21 = document.createElement("TD");
				var S_Anticipated_Amt_E_of_LS = document.createElement("input");
				S_Anticipated_Amt_E_of_LS.type = "text";
				S_Anticipated_Amt_E_of_LS.name = "S_Anticipated_Amt_E_of_LS" + seq;
				S_Anticipated_Amt_E_of_LS.id = "S_Anticipated_Amt_E_of_LS" + seq;
				S_Anticipated_Amt_E_of_LS.value = item[10]+"0";
				S_Anticipated_Amt_E_of_LS.size = "10";
				S_Anticipated_Amt_E_of_LS.style.textAlign="right";
				cel21.appendChild(S_Anticipated_Amt_E_of_LS);
				mycurrent_row.appendChild(cel21);

				/** Superannuation1 Commutation of Pension */
				var cel22 = document.createElement("TD");
				var S_Anticipated_Amt_Cmutation_of_Pension  = document.createElement("input");
				S_Anticipated_Amt_Cmutation_of_Pension.type = "text";
				S_Anticipated_Amt_Cmutation_of_Pension.name = "S_Anticipated_Amt_Cmutation_of_Pension" + seq;
				S_Anticipated_Amt_Cmutation_of_Pension.id = "S_Anticipated_Amt_Cmutation_of_Pension" + seq;
				S_Anticipated_Amt_Cmutation_of_Pension.value = item[11]+"0";
				S_Anticipated_Amt_Cmutation_of_Pension.size = "10";
				S_Anticipated_Amt_Cmutation_of_Pension.style.textAlign="right";
				cel22.appendChild(S_Anticipated_Amt_Cmutation_of_Pension );
				mycurrent_row.appendChild(cel22);

				/** Superannuation1 Gratuity */
				var cel23 = document.createElement("TD");
				var S_Anticipated_Amt_Gratuity  = document.createElement("input");
				S_Anticipated_Amt_Gratuity.type = "text";
				S_Anticipated_Amt_Gratuity.name = "S_Anticipated_Amt_Gratuity" + seq;
				S_Anticipated_Amt_Gratuity.id = "S_Anticipated_Amt_Gratuity" + seq;
				S_Anticipated_Amt_Gratuity.value = item[12]+"0";
				S_Anticipated_Amt_Gratuity.size = "10";
				S_Anticipated_Amt_Gratuity.style.textAlign="right";
				cel23.appendChild(S_Anticipated_Amt_Gratuity );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** Voluntary Retirement1 Encashment of L.S */
				var cel24 = document.createElement("TD");
				var VR_Anticipated_Amt_E_of_LS = document.createElement("input");
				VR_Anticipated_Amt_E_of_LS.type = "text";
				VR_Anticipated_Amt_E_of_LS.name = "VR_Anticipated_Amt_E_of_LS" + seq;
				VR_Anticipated_Amt_E_of_LS.id = "VR_Anticipated_Amt_E_of_LS" + seq;
				VR_Anticipated_Amt_E_of_LS.value = item[13]+"0";
				VR_Anticipated_Amt_E_of_LS.size = "10";
				VR_Anticipated_Amt_E_of_LS.style.textAlign="right";
				cel24.appendChild(VR_Anticipated_Amt_E_of_LS);
				mycurrent_row.appendChild(cel24);

				/** Voluntary Retirement1 Commutation of Pension */
				var cell25 = document.createElement("TD");
				var VR_Anticipated_Amt_Cmutation_of_Pension = document.createElement("input");
				VR_Anticipated_Amt_Cmutation_of_Pension.type = "text";
				VR_Anticipated_Amt_Cmutation_of_Pension.name = "VR_Anticipated_Amt_Cmutation_of_Pension" + seq;
				VR_Anticipated_Amt_Cmutation_of_Pension.id = "VR_Anticipated_Amt_Cmutation_of_Pension" + seq;
				VR_Anticipated_Amt_Cmutation_of_Pension.value = item[14]+"0";
				VR_Anticipated_Amt_Cmutation_of_Pension.size = "10";
				VR_Anticipated_Amt_Cmutation_of_Pension.style.textAlign="right";
				cell25.appendChild(VR_Anticipated_Amt_Cmutation_of_Pension);
				mycurrent_row.appendChild(cell25);

				/** Voluntary Retirement1 Gratuity */
				var cel26 = document.createElement("TD");
				var VR_Anticipated_Amt_Gratuity  = document.createElement("input");
				VR_Anticipated_Amt_Gratuity.type = "text";
				VR_Anticipated_Amt_Gratuity.name = "VR_Anticipated_Amt_Gratuity" + seq;
				VR_Anticipated_Amt_Gratuity.id = "VR_Anticipated_Amt_Gratuity" + seq;
				VR_Anticipated_Amt_Gratuity.value = item[15]+"0";
				VR_Anticipated_Amt_Gratuity.size = "10";
				VR_Anticipated_Amt_Gratuity.style.textAlign="right";
				cel26.appendChild(VR_Anticipated_Amt_Gratuity);
				mycurrent_row.appendChild(cel26);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[18]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_4_Consolidation")
						.appendChild(slno_db);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
		} else {
			alert("Record Does Not Exist");
		}
		document.getElementById("RecordCount").value = seq;
	}else if(flag=="Freezed"){
		alert("Civil Budget Format-4 Consolidation have Already Freezed");
	}  else {
		alert("Failed to Load Data");
	}
}
function funcUpdate() {
	document.getElementById("filter").value = "update";
	document.getElementById("RecordCount").value = seq;
	return true;
}

function funcDelete() {
	var r = confirm("Are U Sure to Continue?");
	if (r == true) {
		document.getElementById("filter").value = "delete";
		var tbody = document.getElementById("grid_body");
		var rowcount = tbody.rows.length;
		if (rowcount != 0) {
			for ( var i = 0; i < rowcount; i++) {
				if (document.getElementById("check" + i).checked == true) {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", i);
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("frmCivil_Budget_Format_4_Consolidation")
							.appendChild(slno_db1);
				} else {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", "-1");
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("frmCivil_Budget_Format_4_Consolidation")
							.appendChild(slno_db1);
				}
			}
			return true;
		} else {
			alert("No Records Found to Delete...");
			return false;
		}

	} else {
		return false;
	}
}
function clrForm() {
	seq=0;
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	LoadAccountingUnitID('LIST_ALL_UNITS');	

	document.frmCivil_Budget_Format_4_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_4_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_4_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_4_Consolidation.butUpdate.disabled = true;
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