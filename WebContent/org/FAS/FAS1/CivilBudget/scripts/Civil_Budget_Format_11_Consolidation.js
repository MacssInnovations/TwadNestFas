//			Civil_Budget_Format_11_Consolidation			//
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

	document.frmCivil_Budget_Format_11_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_11_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_11_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_11_Consolidation.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_11_Consolidation?command=get&y1="
			+ y1
			+ "&y2="
			+ y2
			+ "&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

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

	document.frmCivil_Budget_Format_11_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_11_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_11_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_11_Consolidation.butUpdate.disabled = true;
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

	/** Divn Code */
	var cell2 = document.createElement("TD");
	var Divn_Code = document.createElement("input");
	Divn_Code.type = "text";
	Divn_Code.name = "Divn_Code" + seq;
	Divn_Code.id = "Divn_Code" + seq;
	Divn_Code.value = "";
	Divn_Code.size = "10";
	cell2.appendChild(Divn_Code);
	mycurrent_row.appendChild(cell2);

	/** Type of Vehicle */
	var cell3 = document.createElement("TD");
	var Type_of_Vehicle = document.createElement("input");
	Type_of_Vehicle.type = "text";
	Type_of_Vehicle.name = "Type_of_Vehicle" + seq;
	Type_of_Vehicle.id = "Type_of_Vehicle" + seq;
	Type_of_Vehicle.value = "";
	cell3.appendChild(Type_of_Vehicle);
	mycurrent_row.appendChild(cell3);

	/** Regn No */
	var cell4 = document.createElement("TD");
	var Regn_No = document.createElement("input");
	Regn_No.type = "text";
	Regn_No.name = "Regn_No" + seq;
	Regn_No.id = "Regn_No" + seq;
	Regn_No.value = "";
	cell4.appendChild(Regn_No);
	mycurrent_row.appendChild(cell4);

	/** Date Of Purchase */
	var cell5 = document.createElement("TD");
	var Date_Of_Purchase = document.createElement("input");
	Date_Of_Purchase.type = "text";
	Date_Of_Purchase.name = "Date_Of_Purchase" + seq;
	Date_Of_Purchase.id = "Date_Of_Purchase" + seq;
	Date_Of_Purchase.value = "";
	Date_Of_Purchase.maxLength = "10";
	Date_Of_Purchase.size = "10";
	cell5.appendChild(Date_Of_Purchase);
	mycurrent_row.appendChild(cell5);

	/** No of Kms Done */
	var cell6 = document.createElement("TD");
	var No_of_Kms_Done = document.createElement("input");
	No_of_Kms_Done.type = "text";
	No_of_Kms_Done.name = "No_of_Kms_Done" + seq;
	No_of_Kms_Done.id = "No_of_Kms_Done" + seq;
	No_of_Kms_Done.value = "";
	No_of_Kms_Done.size = "10";
	cell6.appendChild(No_of_Kms_Done);
	mycurrent_row.appendChild(cell6);

	/** Age of Vehicle */
	var cell7 = document.createElement("TD");
	var Age_of_Vehicle = document.createElement("input");
	Age_of_Vehicle.type = "text";
	Age_of_Vehicle.name = "Age_of_Vehicle" + seq;
	Age_of_Vehicle.id = "Age_of_Vehicle" + seq;
	Age_of_Vehicle.value = "";
	Age_of_Vehicle.size = "10";
	cell7.appendChild(Age_of_Vehicle);
	mycurrent_row.appendChild(cell7);

	/** Date of Condemnation Fit */
	var cell8 = document.createElement("TD");
	var Date_of_Condemnation_Fit = document.createElement("input");
	Date_of_Condemnation_Fit.type = "text";
	Date_of_Condemnation_Fit.name = "Date_of_Condemnation_Fit" + seq;
	Date_of_Condemnation_Fit.id = "Date_of_Condemnation_Fit" + seq;
	Date_of_Condemnation_Fit.value = "";
	Date_of_Condemnation_Fit.maxLength = "10";
	Date_of_Condemnation_Fit.size = "10";
	cell8.appendChild(Date_of_Condemnation_Fit);
	mycurrent_row.appendChild(cell8);

	/** Date of Condemnation Not Fit */
	var cell9 = document.createElement("TD");
	var Date_of_Condemnation_Not_Fit = document.createElement("input");
	Date_of_Condemnation_Not_Fit.type = "text";
	Date_of_Condemnation_Not_Fit.name = "Date_of_Condemnation_Not_Fit" + seq;
	Date_of_Condemnation_Not_Fit.id = "Date_of_Condemnation_Not_Fit" + seq;
	Date_of_Condemnation_Not_Fit.value = "";
	Date_of_Condemnation_Not_Fit.maxLength = "10";
	Date_of_Condemnation_Not_Fit.size = "10";
	cell9.appendChild(Date_of_Condemnation_Not_Fit);
	mycurrent_row.appendChild(cell9);

	/** Details for Last Year - Salary */
	var cell20 = document.createElement("TD");
	var Salary_LY = document.createElement("input");
	Salary_LY.type = "text";
	Salary_LY.name = "Salary_LY" + seq;
	Salary_LY.id = "Salary_LY" + seq;
	Salary_LY.value = "";
	Salary_LY.size = "10";
	cell20.appendChild(Salary_LY);
	mycurrent_row.appendChild(cell20);

	/** Details for Last Year - Fuel / Material */
	var cel21 = document.createElement("TD");
	var Fuel_Materia_LY = document.createElement("input");
	Fuel_Materia_LY.type = "text";
	Fuel_Materia_LY.name = "Fuel_Materia_LY" + seq;
	Fuel_Materia_LY.id = "Fuel_Materia_LY" + seq;
	Fuel_Materia_LY.value = "";
	cel21.appendChild(Fuel_Materia_LY);
	mycurrent_row.appendChild(cel21);

	/** Details for Last Year - Ordinary Repairs */
	var cel22 = document.createElement("TD");
	var Ordinary_Repairs_LY = document.createElement("input");
	Ordinary_Repairs_LY.type = "text";
	Ordinary_Repairs_LY.name = "Ordinary_Repairs_LY" + seq;
	Ordinary_Repairs_LY.id = "Ordinary_Repairs_LY" + seq;
	Ordinary_Repairs_LY.value = "";
	cel22.appendChild(Ordinary_Repairs_LY);
	mycurrent_row.appendChild(cel22);

	/** Details for Last Year - Special Repairs */
	var cel23 = document.createElement("TD");
	var Special_Repairs_LY = document.createElement("input");
	Special_Repairs_LY.type = "text";
	Special_Repairs_LY.name = "Special_Repairs_LY" + seq;
	Special_Repairs_LY.id = "Special_Repairs_LY" + seq;
	Special_Repairs_LY.value = "";
	cel23.appendChild(Special_Repairs_LY);
	mycurrent_row.appendChild(cel23);
	tbody.appendChild(mycurrent_row);

	/** Details for Last Year - Total Cost */
	var cel24 = document.createElement("TD");
	var Total_Cost_LY = document.createElement("input");
	Total_Cost_LY.type = "text";
	Total_Cost_LY.name = "Total_Cost_LY" + seq;
	Total_Cost_LY.id = "Total_Cost_LY" + seq;
	Total_Cost_LY.value = "";
	Total_Cost_LY.size = "10";
	cel24.appendChild(Total_Cost_LY);
	mycurrent_row.appendChild(cel24);

	/** Details for Current Year - Salary */
	var cell25 = document.createElement("TD");
	var Salary_CY = document.createElement("input");
	Salary_CY.type = "text";
	Salary_CY.name = "Salary_CY" + seq;
	Salary_CY.id = "Salary_CY" + seq;
	Salary_CY.value = "";
	Salary_CY.size = "10";
	cell25.appendChild(Salary_CY);
	mycurrent_row.appendChild(cell25);

	/** Details for Current Year - Fuel / Materia */
	var cel26 = document.createElement("TD");
	var Fuel_Materia_CY = document.createElement("input");
	Fuel_Materia_CY.type = "text";
	Fuel_Materia_CY.name = "Fuel_Materia_CY" + seq;
	Fuel_Materia_CY.id = "Fuel_Materia_CY" + seq;
	Fuel_Materia_CY.value = "";
	cel26.appendChild(Fuel_Materia_CY);
	mycurrent_row.appendChild(cel26);

	/** Details for Current Year - Ordinary Repairs */
	var cel27 = document.createElement("TD");
	var Ordinary_Repairs_CY = document.createElement("input");
	Ordinary_Repairs_CY.type = "text";
	Ordinary_Repairs_CY.name = "Ordinary_Repairs_CY" + seq;
	Ordinary_Repairs_CY.id = "Ordinary_Repairs_CY" + seq;
	Ordinary_Repairs_CY.value = "";
	cel27.appendChild(Ordinary_Repairs_CY);
	mycurrent_row.appendChild(cel27);

	/** Details for Current Year - Special Repairs */
	var cel28 = document.createElement("TD");
	var Special_Repairs_CY = document.createElement("input");
	Special_Repairs_CY.type = "text";
	Special_Repairs_CY.name = "Special_Repairs_CY" + seq;
	Special_Repairs_CY.id = "Special_Repairs_CY" + seq;
	Special_Repairs_CY.value = "";
	cel28.appendChild(Special_Repairs_CY);
	mycurrent_row.appendChild(cel28);
	tbody.appendChild(mycurrent_row);

	/** Details for Current Year - Total Cost */
	var cel29 = document.createElement("TD");
	var Total_Cost_CY = document.createElement("input");
	Total_Cost_CY.type = "text";
	Total_Cost_CY.name = "Total_Cost_CY" + seq;
	Total_Cost_CY.id = "Total_Cost_CY" + seq;
	Total_Cost_CY.value = "";
	Total_Cost_CY.size = "10";
	cel29.appendChild(Total_Cost_CY);
	mycurrent_row.appendChild(cel29);

	/** Details for Next Year - Salary */
	var cel30 = document.createElement("TD");
	var Salary_NY = document.createElement("input");
	Salary_NY.type = "text";
	Salary_NY.name = "Salary_NY" + seq;
	Salary_NY.id = "Salary_NY" + seq;
	Salary_NY.value = "";
	Salary_NY.size = "10";
	cel30.appendChild(Salary_NY);
	mycurrent_row.appendChild(cel30);

	/** Details for Next Year - Fuel / Materia */
	var cel31 = document.createElement("TD");
	var Fuel_Materia_NY = document.createElement("input");
	Fuel_Materia_NY.type = "text";
	Fuel_Materia_NY.name = "Fuel_Materia_NY" + seq;
	Fuel_Materia_NY.id = "Fuel_Materia_NY" + seq;
	Fuel_Materia_NY.value = "";
	cel31.appendChild(Fuel_Materia_NY);
	mycurrent_row.appendChild(cel31);

	/** Details for Next Year - Ordinary Repairs */
	var cel32 = document.createElement("TD");
	var Ordinary_Repairs_NY = document.createElement("input");
	Ordinary_Repairs_NY.type = "text";
	Ordinary_Repairs_NY.name = "Ordinary_Repairs_NY" + seq;
	Ordinary_Repairs_NY.id = "Ordinary_Repairs_NY" + seq;
	Ordinary_Repairs_NY.value = "";
	cel32.appendChild(Ordinary_Repairs_NY);
	mycurrent_row.appendChild(cel32);

	/** Details for Next Year - Special Repairs */
	var cel33 = document.createElement("TD");
	var Special_Repairs_NY = document.createElement("input");
	Special_Repairs_NY.type = "text";
	Special_Repairs_NY.name = "Special_Repairs_NY" + seq;
	Special_Repairs_NY.id = "Special_Repairs_NY" + seq;
	Special_Repairs_NY.value = "";
	cel33.appendChild(Special_Repairs_NY);
	mycurrent_row.appendChild(cel33);
	tbody.appendChild(mycurrent_row);

	/** Details for Next Year - Total Cost */
	var cel34 = document.createElement("TD");
	var Total_Cost_NY = document.createElement("input");
	Total_Cost_NY.type = "text";
	Total_Cost_NY.name = "Total_Cost_NY" + seq;
	Total_Cost_NY.id = "Total_Cost_NY" + seq;
	Total_Cost_NY.value = "";
	Total_Cost_NY.size = "10";
	cel34.appendChild(Total_Cost_NY);
	mycurrent_row.appendChild(cel34);

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
	document.frmCivil_Budget_Format_11_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_11_Consolidation.butSub.disabled = true;
	document.frmCivil_Budget_Format_11_Consolidation.butDelete.disabled = false;
	document.frmCivil_Budget_Format_11_Consolidation.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;

	var url = path + "/Civil_Budget_Format_11_Consolidation?filter=view&cmbFinancialYear="
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
				item[0] = baseResponse.getElementsByTagName("Divn_Code2")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Type_of_Vehicle2")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Regn_No2")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="";
				}
				item[3] = baseResponse
						.getElementsByTagName("Date_Of_Purchase2")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="";
				}
				item[4] = baseResponse.getElementsByTagName("No_of_Kms_Done2")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="";
				}
				item[5] = baseResponse.getElementsByTagName("Age_of_Vehicle2")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="";
				}
				item[6] = baseResponse
						.getElementsByTagName("Date_of_Condemnation_Fit2")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="";
				}
				item[7] = baseResponse
						.getElementsByTagName("Date_of_Condemnation_Not_Fit2")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("Salary_LY2")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="";
				}
				item[9] = baseResponse.getElementsByTagName("Fuel_Materia_LY2")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="";
				}
				item[10] = baseResponse
						.getElementsByTagName("Ordinary_Repairs_LY2")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="";
				}
				item[11] = baseResponse
						.getElementsByTagName("Special_Repairs_LY2")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="";
				}
				item[12] = baseResponse.getElementsByTagName("Total_Cost_LY2")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="";
				}
				item[13] = baseResponse.getElementsByTagName("Salary_CY2")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="";
				}
				item[14] = baseResponse
						.getElementsByTagName("Fuel_Materia_CY2")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="";
				}
				item[15] = baseResponse
						.getElementsByTagName("Ordinary_Repairs_CY2")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="";
				}
				item[16] = baseResponse
						.getElementsByTagName("Special_Repairs_CY2")[k].firstChild.nodeValue;
				if(item[16]=='null'){
					item[16]="";
				}
				item[17] = baseResponse.getElementsByTagName("Total_Cost_CY2")[k].firstChild.nodeValue;
				if(item[17]=='null'){
					item[17]="";
				}
				item[18] = baseResponse.getElementsByTagName("Salary_NY2")[k].firstChild.nodeValue;
				if(item[18]=='null'){
					item[18]="";
				}
				item[19] = baseResponse
						.getElementsByTagName("Fuel_Materia_NY2")[k].firstChild.nodeValue;
				if(item[19]=='null'){
					item[19]="";
				}
				item[20] = baseResponse
						.getElementsByTagName("Ordinary_Repairs_NY2")[k].firstChild.nodeValue;
				if(item[20]=='null'){
					item[20]="";
				}
				item[21] = baseResponse
						.getElementsByTagName("Special_Repairs_NY2")[k].firstChild.nodeValue;
				if(item[21]=='null'){
					item[21]="";
				}
				item[22] = baseResponse.getElementsByTagName("Total_Cost_NY2")[k].firstChild.nodeValue;
				if(item[22]=='null'){
					item[22]="";
				}
				item[23] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[23]=='null'){
					item[23]="";
				}
				
				item[24] = baseResponse.getElementsByTagName("office_id")[k].firstChild.nodeValue;
				if(item[24]=='null'){
					item[24]="";
				}
				
				item[25] = baseResponse.getElementsByTagName("office_name")[k].firstChild.nodeValue;
				if(item[25]=='null'){
					item[25]="";
				}
				
				item[26] = baseResponse.getElementsByTagName("acc_unit_id")[k].firstChild.nodeValue;
				if(item[26]=='null'){
					item[26]="";
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


				/** Acc office name */
				var celll2 = document.createElement("TD");
				var Acc_office_name = document.createElement("input");
				Acc_office_name.type = "text";
				Acc_office_name.name = "Acc_office_name" + seq;
				Acc_office_name.id = "Acc_office_name" + seq;
				Acc_office_name.value = item[25];
				Acc_office_name.size = "50";
				celll2.appendChild(Acc_office_name);
				mycurrent_row.appendChild(celll2);
				
				/** Divn Code */
				var cell2 = document.createElement("TD");
				var Divn_Code = document.createElement("input");
				Divn_Code.type = "text";
				Divn_Code.name = "Divn_Code" + seq;
				Divn_Code.id = "Divn_Code" + seq;
				Divn_Code.value = item[0];
				Divn_Code.size = "10";
				cell2.appendChild(Divn_Code);
				mycurrent_row.appendChild(cell2);

				/** Type of Vehicle */
				var cell3 = document.createElement("TD");
				var Type_of_Vehicle = document.createElement("input");
				Type_of_Vehicle.type = "text";
				Type_of_Vehicle.name = "Type_of_Vehicle" + seq;
				Type_of_Vehicle.id = "Type_of_Vehicle" + seq;
				Type_of_Vehicle.value = item[1];
				cell3.appendChild(Type_of_Vehicle);
				mycurrent_row.appendChild(cell3);

				/** Regn No */
				var cell4 = document.createElement("TD");
				var Regn_No = document.createElement("input");
				Regn_No.type = "text";
				Regn_No.name = "Regn_No" + seq;
				Regn_No.id = "Regn_No" + seq;
				Regn_No.value = item[2];
				cell4.appendChild(Regn_No);
				mycurrent_row.appendChild(cell4);

				/** Date Of Purchase */
				var cell5 = document.createElement("TD");
				var Date_Of_Purchase = document.createElement("input");
				Date_Of_Purchase.type = "text";
				Date_Of_Purchase.name = "Date_Of_Purchase" + seq;
				Date_Of_Purchase.id = "Date_Of_Purchase" + seq;
				Date_Of_Purchase.value = item[3];
				Date_Of_Purchase.maxLength = "10";
				Date_Of_Purchase.size = "10";
				cell5.appendChild(Date_Of_Purchase);
				mycurrent_row.appendChild(cell5);

				/** No of Kms Done */
				var cell6 = document.createElement("TD");
				var No_of_Kms_Done = document.createElement("input");
				No_of_Kms_Done.type = "text";
				No_of_Kms_Done.name = "No_of_Kms_Done" + seq;
				No_of_Kms_Done.id = "No_of_Kms_Done" + seq;
				No_of_Kms_Done.value = item[4];
				No_of_Kms_Done.size = "10";
				cell6.appendChild(No_of_Kms_Done);
				mycurrent_row.appendChild(cell6);

				/** Age of Vehicle */
				var cell7 = document.createElement("TD");
				var Age_of_Vehicle = document.createElement("input");
				Age_of_Vehicle.type = "text";
				Age_of_Vehicle.name = "Age_of_Vehicle" + seq;
				Age_of_Vehicle.id = "Age_of_Vehicle" + seq;
				Age_of_Vehicle.value = item[5];
				Age_of_Vehicle.size = "10";
				cell7.appendChild(Age_of_Vehicle);
				mycurrent_row.appendChild(cell7);

				/** Date of Condemnation Fit */
				var cell8 = document.createElement("TD");
				var Date_of_Condemnation_Fit = document.createElement("input");
				Date_of_Condemnation_Fit.type = "text";
				Date_of_Condemnation_Fit.name = "Date_of_Condemnation_Fit"
						+ seq;
				Date_of_Condemnation_Fit.id = "Date_of_Condemnation_Fit" + seq;
				Date_of_Condemnation_Fit.value = item[6];
				Date_of_Condemnation_Fit.maxLength = "10";
				Date_of_Condemnation_Fit.size = "10";
				cell8.appendChild(Date_of_Condemnation_Fit);
				mycurrent_row.appendChild(cell8);

				/** Date of Condemnation Not Fit */
				var cell9 = document.createElement("TD");
				var Date_of_Condemnation_Not_Fit = document
						.createElement("input");
				Date_of_Condemnation_Not_Fit.type = "text";
				Date_of_Condemnation_Not_Fit.name = "Date_of_Condemnation_Not_Fit"
						+ seq;
				Date_of_Condemnation_Not_Fit.id = "Date_of_Condemnation_Not_Fit"
						+ seq;
				Date_of_Condemnation_Not_Fit.value = item[7];
				Date_of_Condemnation_Not_Fit.maxLength = "10";
				Date_of_Condemnation_Not_Fit.size = "10";
				cell9.appendChild(Date_of_Condemnation_Not_Fit);
				mycurrent_row.appendChild(cell9);

				/** Details for Last Year - Salary */
				var cell20 = document.createElement("TD");
				var Salary_LY = document.createElement("input");
				Salary_LY.type = "text";
				Salary_LY.name = "Salary_LY" + seq;
				Salary_LY.id = "Salary_LY" + seq;
				Salary_LY.value = item[8]+"0";
				Salary_LY.style.textAlign="right";
				Salary_LY.size = "10";
				cell20.appendChild(Salary_LY);
				mycurrent_row.appendChild(cell20);

				/** Details for Last Year - Fuel / Material */
				var cel21 = document.createElement("TD");
				var Fuel_Materia_LY = document.createElement("input");
				Fuel_Materia_LY.type = "text";
				Fuel_Materia_LY.name = "Fuel_Materia_LY" + seq;
				Fuel_Materia_LY.id = "Fuel_Materia_LY" + seq;
				Fuel_Materia_LY.value = item[9];
				cel21.appendChild(Fuel_Materia_LY);
				mycurrent_row.appendChild(cel21);

				/** Details for Last Year - Ordinary Repairs */
				var cel22 = document.createElement("TD");
				var Ordinary_Repairs_LY = document.createElement("input");
				Ordinary_Repairs_LY.type = "text";
				Ordinary_Repairs_LY.name = "Ordinary_Repairs_LY" + seq;
				Ordinary_Repairs_LY.id = "Ordinary_Repairs_LY" + seq;
				Ordinary_Repairs_LY.value = item[10];
				cel22.appendChild(Ordinary_Repairs_LY);
				mycurrent_row.appendChild(cel22);

				/** Details for Last Year - Special Repairs */
				var cel23 = document.createElement("TD");
				var Special_Repairs_LY = document.createElement("input");
				Special_Repairs_LY.type = "text";
				Special_Repairs_LY.name = "Special_Repairs_LY" + seq;
				Special_Repairs_LY.id = "Special_Repairs_LY" + seq;
				Special_Repairs_LY.value = item[11];
				cel23.appendChild(Special_Repairs_LY);
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** Details for Last Year - Total Cost */
				var cel24 = document.createElement("TD");
				var Total_Cost_LY = document.createElement("input");
				Total_Cost_LY.type = "text";
				Total_Cost_LY.name = "Total_Cost_LY" + seq;
				Total_Cost_LY.id = "Total_Cost_LY" + seq;
				Total_Cost_LY.value = item[12]+"0";
				Total_Cost_LY.style.textAlign="right";
				Total_Cost_LY.size = "10";
				cel24.appendChild(Total_Cost_LY);
				mycurrent_row.appendChild(cel24);

				/** Details for Current Year - Salary */
				var cell25 = document.createElement("TD");
				var Salary_CY = document.createElement("input");
				Salary_CY.type = "text";
				Salary_CY.name = "Salary_CY" + seq;
				Salary_CY.id = "Salary_CY" + seq;
				Salary_CY.value = item[13]+"0";
				Salary_CY.style.textAlign="right";
				Salary_CY.size = "10";
				cell25.appendChild(Salary_CY);
				mycurrent_row.appendChild(cell25);

				/** Details for Current Year - Fuel / Materia */
				var cel26 = document.createElement("TD");
				var Fuel_Materia_CY = document.createElement("input");
				Fuel_Materia_CY.type = "text";
				Fuel_Materia_CY.name = "Fuel_Materia_CY" + seq;
				Fuel_Materia_CY.id = "Fuel_Materia_CY" + seq;
				Fuel_Materia_CY.value = item[14];
				cel26.appendChild(Fuel_Materia_CY);
				mycurrent_row.appendChild(cel26);

				/** Details for Current Year - Ordinary Repairs */
				var cel27 = document.createElement("TD");
				var Ordinary_Repairs_CY = document.createElement("input");
				Ordinary_Repairs_CY.type = "text";
				Ordinary_Repairs_CY.name = "Ordinary_Repairs_CY" + seq;
				Ordinary_Repairs_CY.id = "Ordinary_Repairs_CY" + seq;
				Ordinary_Repairs_CY.value = item[15];
				cel27.appendChild(Ordinary_Repairs_CY);
				mycurrent_row.appendChild(cel27);

				/** Details for Current Year - Special Repairs */
				var cel28 = document.createElement("TD");
				var Special_Repairs_CY = document.createElement("input");
				Special_Repairs_CY.type = "text";
				Special_Repairs_CY.name = "Special_Repairs_CY" + seq;
				Special_Repairs_CY.id = "Special_Repairs_CY" + seq;
				Special_Repairs_CY.value = item[16];
				cel28.appendChild(Special_Repairs_CY);
				mycurrent_row.appendChild(cel28);
				tbody.appendChild(mycurrent_row);

				/** Details for Current Year - Total Cost */
				var cel29 = document.createElement("TD");
				var Total_Cost_CY = document.createElement("input");
				Total_Cost_CY.type = "text";
				Total_Cost_CY.name = "Total_Cost_CY" + seq;
				Total_Cost_CY.id = "Total_Cost_CY" + seq;
				Total_Cost_CY.value = item[17]+"0";
				Total_Cost_CY.style.textAlign="right";
				Total_Cost_CY.size = "10";
				cel29.appendChild(Total_Cost_CY);
				mycurrent_row.appendChild(cel29);

				/** Details for Next Year - Salary */
				var cel30 = document.createElement("TD");
				var Salary_NY = document.createElement("input");
				Salary_NY.type = "text";
				Salary_NY.name = "Salary_NY" + seq;
				Salary_NY.id = "Salary_NY" + seq;
				Salary_NY.value = item[18]+"0";
				Salary_NY.style.textAlign="right";
				Salary_NY.size = "10";
				cel30.appendChild(Salary_NY);
				mycurrent_row.appendChild(cel30);

				/** Details for Next Year - Fuel / Materia */
				var cel31 = document.createElement("TD");
				var Fuel_Materia_NY = document.createElement("input");
				Fuel_Materia_NY.type = "text";
				Fuel_Materia_NY.name = "Fuel_Materia_NY" + seq;
				Fuel_Materia_NY.id = "Fuel_Materia_NY" + seq;
				Fuel_Materia_NY.value = item[19];
				cel31.appendChild(Fuel_Materia_NY);
				mycurrent_row.appendChild(cel31);

				/** Details for Next Year - Ordinary Repairs */
				var cel32 = document.createElement("TD");
				var Ordinary_Repairs_NY = document.createElement("input");
				Ordinary_Repairs_NY.type = "text";
				Ordinary_Repairs_NY.name = "Ordinary_Repairs_NY" + seq;
				Ordinary_Repairs_NY.id = "Ordinary_Repairs_NY" + seq;
				Ordinary_Repairs_NY.value = item[20];
				cel32.appendChild(Ordinary_Repairs_NY);
				mycurrent_row.appendChild(cel32);

				/** Details for Next Year - Special Repairs */
				var cel33 = document.createElement("TD");
				var Special_Repairs_NY = document.createElement("input");
				Special_Repairs_NY.type = "text";
				Special_Repairs_NY.name = "Special_Repairs_NY" + seq;
				Special_Repairs_NY.id = "Special_Repairs_NY" + seq;
				Special_Repairs_NY.value = item[21];
				cel33.appendChild(Special_Repairs_NY);
				mycurrent_row.appendChild(cel33);
				tbody.appendChild(mycurrent_row);

				/** Details for Next Year - Total Cost */
				var cel34 = document.createElement("TD");
				var Total_Cost_NY = document.createElement("input");
				Total_Cost_NY.type = "text";
				Total_Cost_NY.name = "Total_Cost_NY" + seq;
				Total_Cost_NY.id = "Total_Cost_NY" + seq;
				Total_Cost_NY.value = item[22]+"0";
				Total_Cost_NY.style.textAlign="right";
				Total_Cost_NY.size = "10";
				cel34.appendChild(Total_Cost_NY);
				mycurrent_row.appendChild(cel34);

				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[23]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_11_Consolidation")
						.appendChild(slno_db);
				
				/** acc_unit_id */
				var acc_unit_id = document.createElement("input");
				acc_unit_id.setAttribute("type", "hidden");
				acc_unit_id.setAttribute("value", item[26]);
				acc_unit_id.setAttribute("name", "acc_unit_id" + seq);
				acc_unit_id.setAttribute("id", "acc_unit_id" + seq);
				document.getElementById("frmCivil_Budget_Format_11_Consolidation")
						.appendChild(acc_unit_id);								
				
				/** acc_office_id */
				var acc_office_id = document.createElement("input");
				acc_office_id.setAttribute("type", "hidden");
				acc_office_id.setAttribute("value", item[24]);
				acc_office_id.setAttribute("name", "acc_office_id" + seq);
				acc_office_id.setAttribute("id", "acc_office_id" + seq);
				document.getElementById("frmCivil_Budget_Format_11_Consolidation")
						.appendChild(acc_office_id);
				
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

function LoadData_View(baseResponse) {
	//alert("RK");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
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
				item[0] = baseResponse.getElementsByTagName("Divn_Code2")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Type_of_Vehicle2")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Regn_No2")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="";
				}
				item[3] = baseResponse
						.getElementsByTagName("Date_Of_Purchase2")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="";
				}
				item[4] = baseResponse.getElementsByTagName("No_of_Kms_Done2")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="";
				}
				item[5] = baseResponse.getElementsByTagName("Age_of_Vehicle2")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="";
				}
				item[6] = baseResponse
						.getElementsByTagName("Date_of_Condemnation_Fit2")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="";
				}
				item[7] = baseResponse
						.getElementsByTagName("Date_of_Condemnation_Not_Fit2")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("Salary_LY2")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="";
				}
				item[9] = baseResponse.getElementsByTagName("Fuel_Materia_LY2")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="";
				}
				item[10] = baseResponse
						.getElementsByTagName("Ordinary_Repairs_LY2")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="";
				}
				item[11] = baseResponse
						.getElementsByTagName("Special_Repairs_LY2")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="";
				}
				item[12] = baseResponse.getElementsByTagName("Total_Cost_LY2")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="";
				}
				item[13] = baseResponse.getElementsByTagName("Salary_CY2")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="";
				}
				item[14] = baseResponse
						.getElementsByTagName("Fuel_Materia_CY2")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="";
				}
				item[15] = baseResponse
						.getElementsByTagName("Ordinary_Repairs_CY2")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="";
				}
				item[16] = baseResponse
						.getElementsByTagName("Special_Repairs_CY2")[k].firstChild.nodeValue;
				if(item[16]=='null'){
					item[16]="";
				}
				item[17] = baseResponse.getElementsByTagName("Total_Cost_CY2")[k].firstChild.nodeValue;
				if(item[17]=='null'){
					item[17]="";
				}
				item[18] = baseResponse.getElementsByTagName("Salary_NY2")[k].firstChild.nodeValue;
				if(item[18]=='null'){
					item[18]="";
				}
				item[19] = baseResponse
						.getElementsByTagName("Fuel_Materia_NY2")[k].firstChild.nodeValue;
				if(item[19]=='null'){
					item[19]="";
				}
				item[20] = baseResponse
						.getElementsByTagName("Ordinary_Repairs_NY2")[k].firstChild.nodeValue;
				if(item[20]=='null'){
					item[20]="";
				}
				item[21] = baseResponse
						.getElementsByTagName("Special_Repairs_NY2")[k].firstChild.nodeValue;
				if(item[21]=='null'){
					item[21]="";
				}
				item[22] = baseResponse.getElementsByTagName("Total_Cost_NY2")[k].firstChild.nodeValue;
				if(item[22]=='null'){
					item[22]="";
				}
				item[23] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[23]=='null'){
					item[23]="";
				}

				item[24] = baseResponse.getElementsByTagName("office_id")[k].firstChild.nodeValue;
				if(item[24]=='null'){
					item[24]="";
				}
				
				item[25] = baseResponse.getElementsByTagName("office_name")[k].firstChild.nodeValue;
				if(item[25]=='null'){
					item[25]="";
				}
				
				item[26] = baseResponse.getElementsByTagName("acc_unit_id")[k].firstChild.nodeValue;
				if(item[26]=='null'){
					item[26]="";
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

				/** Acc office name */
				var celll2 = document.createElement("TD");
				var Acc_office_name = document.createElement("input");
				Acc_office_name.type = "text";
				Acc_office_name.name = "Acc_office_name" + seq;
				Acc_office_name.id = "Acc_office_name" + seq;
				Acc_office_name.value = item[25];
				Acc_office_name.size = "50";
				celll2.appendChild(Acc_office_name);
				mycurrent_row.appendChild(celll2);
				
				/** Divn Code */
				var cell2 = document.createElement("TD");
				var Divn_Code = document.createElement("input");
				Divn_Code.type = "text";
				Divn_Code.name = "Divn_Code" + seq;
				Divn_Code.id = "Divn_Code" + seq;
				Divn_Code.value = item[0];
				Divn_Code.size = "10";
				cell2.appendChild(Divn_Code);
				mycurrent_row.appendChild(cell2);

				/** Type of Vehicle */
				var cell3 = document.createElement("TD");
				var Type_of_Vehicle = document.createElement("input");
				Type_of_Vehicle.type = "text";
				Type_of_Vehicle.name = "Type_of_Vehicle" + seq;
				Type_of_Vehicle.id = "Type_of_Vehicle" + seq;
				Type_of_Vehicle.value = item[1];
				cell3.appendChild(Type_of_Vehicle);
				mycurrent_row.appendChild(cell3);

				/** Regn No */
				var cell4 = document.createElement("TD");
				var Regn_No = document.createElement("input");
				Regn_No.type = "text";
				Regn_No.name = "Regn_No" + seq;
				Regn_No.id = "Regn_No" + seq;
				Regn_No.value = item[2];
				cell4.appendChild(Regn_No);
				mycurrent_row.appendChild(cell4);

				/** Date Of Purchase */
				var cell5 = document.createElement("TD");
				var Date_Of_Purchase = document.createElement("input");
				Date_Of_Purchase.type = "text";
				Date_Of_Purchase.name = "Date_Of_Purchase" + seq;
				Date_Of_Purchase.id = "Date_Of_Purchase" + seq;
				Date_Of_Purchase.value = item[3];
				Date_Of_Purchase.maxLength = "10";
				Date_Of_Purchase.size = "10";
				cell5.appendChild(Date_Of_Purchase);
				mycurrent_row.appendChild(cell5);

				/** No of Kms Done */
				var cell6 = document.createElement("TD");
				var No_of_Kms_Done = document.createElement("input");
				No_of_Kms_Done.type = "text";
				No_of_Kms_Done.name = "No_of_Kms_Done" + seq;
				No_of_Kms_Done.id = "No_of_Kms_Done" + seq;
				No_of_Kms_Done.value = item[4];
				No_of_Kms_Done.size = "10";
				cell6.appendChild(No_of_Kms_Done);
				mycurrent_row.appendChild(cell6);

				/** Age of Vehicle */
				var cell7 = document.createElement("TD");
				var Age_of_Vehicle = document.createElement("input");
				Age_of_Vehicle.type = "text";
				Age_of_Vehicle.name = "Age_of_Vehicle" + seq;
				Age_of_Vehicle.id = "Age_of_Vehicle" + seq;
				Age_of_Vehicle.value = item[5];
				Age_of_Vehicle.size = "10";
				cell7.appendChild(Age_of_Vehicle);
				mycurrent_row.appendChild(cell7);

				/** Date of Condemnation Fit */
				var cell8 = document.createElement("TD");
				var Date_of_Condemnation_Fit = document.createElement("input");
				Date_of_Condemnation_Fit.type = "text";
				Date_of_Condemnation_Fit.name = "Date_of_Condemnation_Fit"
						+ seq;
				Date_of_Condemnation_Fit.id = "Date_of_Condemnation_Fit" + seq;
				Date_of_Condemnation_Fit.value = item[6];
				Date_of_Condemnation_Fit.maxLength = "10";
				Date_of_Condemnation_Fit.size = "10";
				cell8.appendChild(Date_of_Condemnation_Fit);
				mycurrent_row.appendChild(cell8);

				/** Date of Condemnation Not Fit */
				var cell9 = document.createElement("TD");
				var Date_of_Condemnation_Not_Fit = document
						.createElement("input");
				Date_of_Condemnation_Not_Fit.type = "text";
				Date_of_Condemnation_Not_Fit.name = "Date_of_Condemnation_Not_Fit"
						+ seq;
				Date_of_Condemnation_Not_Fit.id = "Date_of_Condemnation_Not_Fit"
						+ seq;
				Date_of_Condemnation_Not_Fit.value = item[7];
				Date_of_Condemnation_Not_Fit.maxLength = "10";
				Date_of_Condemnation_Not_Fit.size = "10";
				cell9.appendChild(Date_of_Condemnation_Not_Fit);
				mycurrent_row.appendChild(cell9);

				/** Details for Last Year - Salary */
				var cell20 = document.createElement("TD");
				var Salary_LY = document.createElement("input");
				Salary_LY.type = "text";
				Salary_LY.name = "Salary_LY" + seq;
				Salary_LY.id = "Salary_LY" + seq;
				Salary_LY.value = item[8]+"0";
				Salary_LY.style.textAlign="right";
				Salary_LY.size = "10";
				cell20.appendChild(Salary_LY);
				mycurrent_row.appendChild(cell20);

				/** Details for Last Year - Fuel / Material */
				var cel21 = document.createElement("TD");
				var Fuel_Materia_LY = document.createElement("input");
				Fuel_Materia_LY.type = "text";
				Fuel_Materia_LY.name = "Fuel_Materia_LY" + seq;
				Fuel_Materia_LY.id = "Fuel_Materia_LY" + seq;
				Fuel_Materia_LY.value = item[9];
				cel21.appendChild(Fuel_Materia_LY);
				mycurrent_row.appendChild(cel21);

				/** Details for Last Year - Ordinary Repairs */
				var cel22 = document.createElement("TD");
				var Ordinary_Repairs_LY = document.createElement("input");
				Ordinary_Repairs_LY.type = "text";
				Ordinary_Repairs_LY.name = "Ordinary_Repairs_LY" + seq;
				Ordinary_Repairs_LY.id = "Ordinary_Repairs_LY" + seq;
				Ordinary_Repairs_LY.value = item[10];
				cel22.appendChild(Ordinary_Repairs_LY);
				mycurrent_row.appendChild(cel22);

				/** Details for Last Year - Special Repairs */
				var cel23 = document.createElement("TD");
				var Special_Repairs_LY = document.createElement("input");
				Special_Repairs_LY.type = "text";
				Special_Repairs_LY.name = "Special_Repairs_LY" + seq;
				Special_Repairs_LY.id = "Special_Repairs_LY" + seq;
				Special_Repairs_LY.value = item[11];
				cel23.appendChild(Special_Repairs_LY);
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** Details for Last Year - Total Cost */
				var cel24 = document.createElement("TD");
				var Total_Cost_LY = document.createElement("input");
				Total_Cost_LY.type = "text";
				Total_Cost_LY.name = "Total_Cost_LY" + seq;
				Total_Cost_LY.id = "Total_Cost_LY" + seq;
				Total_Cost_LY.value = item[12]+"0";
				Total_Cost_LY.style.textAlign="right";
				Total_Cost_LY.size = "10";
				cel24.appendChild(Total_Cost_LY);
				mycurrent_row.appendChild(cel24);

				/** Details for Current Year - Salary */
				var cell25 = document.createElement("TD");
				var Salary_CY = document.createElement("input");
				Salary_CY.type = "text";
				Salary_CY.name = "Salary_CY" + seq;
				Salary_CY.id = "Salary_CY" + seq;
				Salary_CY.value = item[13]+"0";
				Salary_CY.style.textAlign="right";
				Salary_CY.size = "10";
				cell25.appendChild(Salary_CY);
				mycurrent_row.appendChild(cell25);

				/** Details for Current Year - Fuel / Materia */
				var cel26 = document.createElement("TD");
				var Fuel_Materia_CY = document.createElement("input");
				Fuel_Materia_CY.type = "text";
				Fuel_Materia_CY.name = "Fuel_Materia_CY" + seq;
				Fuel_Materia_CY.id = "Fuel_Materia_CY" + seq;
				Fuel_Materia_CY.value = item[14];
				cel26.appendChild(Fuel_Materia_CY);
				mycurrent_row.appendChild(cel26);

				/** Details for Current Year - Ordinary Repairs */
				var cel27 = document.createElement("TD");
				var Ordinary_Repairs_CY = document.createElement("input");
				Ordinary_Repairs_CY.type = "text";
				Ordinary_Repairs_CY.name = "Ordinary_Repairs_CY" + seq;
				Ordinary_Repairs_CY.id = "Ordinary_Repairs_CY" + seq;
				Ordinary_Repairs_CY.value = item[15];
				cel27.appendChild(Ordinary_Repairs_CY);
				mycurrent_row.appendChild(cel27);

				/** Details for Current Year - Special Repairs */
				var cel28 = document.createElement("TD");
				var Special_Repairs_CY = document.createElement("input");
				Special_Repairs_CY.type = "text";
				Special_Repairs_CY.name = "Special_Repairs_CY" + seq;
				Special_Repairs_CY.id = "Special_Repairs_CY" + seq;
				Special_Repairs_CY.value = item[16];
				cel28.appendChild(Special_Repairs_CY);
				mycurrent_row.appendChild(cel28);
				tbody.appendChild(mycurrent_row);

				/** Details for Current Year - Total Cost */
				var cel29 = document.createElement("TD");
				var Total_Cost_CY = document.createElement("input");
				Total_Cost_CY.type = "text";
				Total_Cost_CY.name = "Total_Cost_CY" + seq;
				Total_Cost_CY.id = "Total_Cost_CY" + seq;
				Total_Cost_CY.value = item[17]+"0";
				Total_Cost_CY.style.textAlign="right";
				Total_Cost_CY.size = "10";
				cel29.appendChild(Total_Cost_CY);
				mycurrent_row.appendChild(cel29);

				/** Details for Next Year - Salary */
				var cel30 = document.createElement("TD");
				var Salary_NY = document.createElement("input");
				Salary_NY.type = "text";
				Salary_NY.name = "Salary_NY" + seq;
				Salary_NY.id = "Salary_NY" + seq;
				Salary_NY.value = item[18]+"0";
				Salary_NY.style.textAlign="right";
				Salary_NY.size = "10";
				cel30.appendChild(Salary_NY);
				mycurrent_row.appendChild(cel30);

				/** Details for Next Year - Fuel / Materia */
				var cel31 = document.createElement("TD");
				var Fuel_Materia_NY = document.createElement("input");
				Fuel_Materia_NY.type = "text";
				Fuel_Materia_NY.name = "Fuel_Materia_NY" + seq;
				Fuel_Materia_NY.id = "Fuel_Materia_NY" + seq;
				Fuel_Materia_NY.value = item[19];
				cel31.appendChild(Fuel_Materia_NY);
				mycurrent_row.appendChild(cel31);

				/** Details for Next Year - Ordinary Repairs */
				var cel32 = document.createElement("TD");
				var Ordinary_Repairs_NY = document.createElement("input");
				Ordinary_Repairs_NY.type = "text";
				Ordinary_Repairs_NY.name = "Ordinary_Repairs_NY" + seq;
				Ordinary_Repairs_NY.id = "Ordinary_Repairs_NY" + seq;
				Ordinary_Repairs_NY.value = item[20];
				cel32.appendChild(Ordinary_Repairs_NY);
				mycurrent_row.appendChild(cel32);

				/** Details for Next Year - Special Repairs */
				var cel33 = document.createElement("TD");
				var Special_Repairs_NY = document.createElement("input");
				Special_Repairs_NY.type = "text";
				Special_Repairs_NY.name = "Special_Repairs_NY" + seq;
				Special_Repairs_NY.id = "Special_Repairs_NY" + seq;
				Special_Repairs_NY.value = item[21];
				cel33.appendChild(Special_Repairs_NY);
				mycurrent_row.appendChild(cel33);
				tbody.appendChild(mycurrent_row);

				/** Details for Next Year - Total Cost */
				var cel34 = document.createElement("TD");
				var Total_Cost_NY = document.createElement("input");
				Total_Cost_NY.type = "text";
				Total_Cost_NY.name = "Total_Cost_NY" + seq;
				Total_Cost_NY.id = "Total_Cost_NY" + seq;
				Total_Cost_NY.value = item[22]+"0";
				Total_Cost_NY.style.textAlign="right";
				Total_Cost_NY.size = "10";
				cel34.appendChild(Total_Cost_NY);
				mycurrent_row.appendChild(cel34);

				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[23]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_11_Consolidation")
						.appendChild(slno_db);

				/** acc_unit_id */
				var acc_unit_id = document.createElement("input");
				acc_unit_id.setAttribute("type", "hidden");
				acc_unit_id.setAttribute("value", item[26]);
				acc_unit_id.setAttribute("name", "acc_unit_id" + seq);
				acc_unit_id.setAttribute("id", "acc_unit_id" + seq);
				document.getElementById("frmCivil_Budget_Format_11_Consolidation")
						.appendChild(acc_unit_id);
				
				/** acc_office_id */
				var acc_office_id = document.createElement("input");
				acc_office_id.setAttribute("type", "hidden");
				acc_office_id.setAttribute("value", item[24]);
				acc_office_id.setAttribute("name", "acc_office_id" + seq);
				acc_office_id.setAttribute("id", "acc_office_id" + seq);
				document.getElementById("frmCivil_Budget_Format_11_Consolidation")
						.appendChild(acc_office_id);
				
				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
		} else {
			alert("Record Does Not Exist");
		}
		document.getElementById("RecordCount").value = seq;
	}else if(flag=="Freezed"){
		alert("Civil Budget Format-11 have Already Freezed");
	} else {
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
					document.getElementById("frmCivil_Budget_Format_11_Consolidation")
							.appendChild(slno_db1);
				} else {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", "-1");
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("frmCivil_Budget_Format_11_Consolidation")
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

	document.frmCivil_Budget_Format_11_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_11_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_11_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_11_Consolidation.butUpdate.disabled = true;
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