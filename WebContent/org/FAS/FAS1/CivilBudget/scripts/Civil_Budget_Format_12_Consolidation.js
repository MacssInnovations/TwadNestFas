//			Civil_Budget_Format_12_Consolidation			//
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

	document.frmCivil_Budget_Format_12_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_12_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_12_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_12_Consolidation.butUpdate.disabled = true;
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

	/** Vehicles */
	var cell2 = document.createElement("TD");
	var Vehicles = document.createElement("input");
	Vehicles.type = "text";
	Vehicles.name = "Vehicles" + seq;
	Vehicles.id = "Vehicles" + seq;
	Vehicles.value = "";	
	cell2.appendChild(Vehicles);
	mycurrent_row.appendChild(cell2);

	/** Jobs Pending  */
	var cell3 = document.createElement("TD");
	var Jobs_Pending = document.createElement("input");
	Jobs_Pending.type = "text";
	Jobs_Pending.name = "Jobs_Pending" + seq;
	Jobs_Pending.id = "Jobs_Pending" + seq;
	Jobs_Pending.value = "";
	cell3.appendChild(Jobs_Pending);
	mycurrent_row.appendChild(cell3);

	/** No of Jobs Entrusted Previous_Year */
	var cell4 = document.createElement("TD");
	var Previous_Year_Jobs_Entrusted  = document.createElement("input");
	Previous_Year_Jobs_Entrusted.type = "text";
	Previous_Year_Jobs_Entrusted.name = "Previous_Year_Jobs_Entrusted" + seq;
	Previous_Year_Jobs_Entrusted.id = "Previous_Year_Jobs_Entrusted" + seq;
	Previous_Year_Jobs_Entrusted.value = "";
	Previous_Year_Jobs_Entrusted.size = "10";
	cell4.appendChild(Previous_Year_Jobs_Entrusted );
	mycurrent_row.appendChild(cell4);

	/** No of Jobs Entrusted Current_Year */
	var cell5 = document.createElement("TD");
	var Current_Year_Jobs_Entrusted = document.createElement("input");
	Current_Year_Jobs_Entrusted.type = "text";
	Current_Year_Jobs_Entrusted.name = "Current_Year_Jobs_Entrusted" + seq;
	Current_Year_Jobs_Entrusted.id = "Current_Year_Jobs_Entrusted" + seq;
	Current_Year_Jobs_Entrusted.value = "";
	Current_Year_Jobs_Entrusted.size = "10";
	cell5.appendChild(Current_Year_Jobs_Entrusted);
	mycurrent_row.appendChild(cell5);

	/** No of Jobs Complited  */
	var cell6 = document.createElement("TD");
	var Previous_Year_Jobs_Complited = document.createElement("input");
	Previous_Year_Jobs_Complited.type = "text";
	Previous_Year_Jobs_Complited.name = "Previous_Year_Jobs_Complited" + seq;
	Previous_Year_Jobs_Complited.id = "Previous_Year_Jobs_Complited" + seq;
	Previous_Year_Jobs_Complited.value = "";
	Previous_Year_Jobs_Complited.size = "10";
	cell6.appendChild(Previous_Year_Jobs_Complited);
	mycurrent_row.appendChild(cell6);

	/** No of Jobs Complited  */
	var cell7 = document.createElement("TD");
	var Current_Year_Jobs_Complited = document.createElement("input");
	Current_Year_Jobs_Complited.type = "text";
	Current_Year_Jobs_Complited.name = "Current_Year_Jobs_Complited" + seq;
	Current_Year_Jobs_Complited.id = "Current_Year_Jobs_Complited" + seq;
	Current_Year_Jobs_Complited.value = "";
	Current_Year_Jobs_Complited.size = "10";
	cell7.appendChild(Current_Year_Jobs_Complited);
	mycurrent_row.appendChild(cell7);

	/** Direct Labour */
	var cell8 = document.createElement("TD");
	var Previous_Year_Direct_Labour = document.createElement("input");
	Previous_Year_Direct_Labour.type = "text";
	Previous_Year_Direct_Labour.name = "Previous_Year_Direct_Labour" + seq;
	Previous_Year_Direct_Labour.id = "Previous_Year_Direct_Labour" + seq;
	Previous_Year_Direct_Labour.value = "";
	Previous_Year_Direct_Labour.size = "10";	
	cell8.appendChild(Previous_Year_Direct_Labour);
	mycurrent_row.appendChild(cell8);

	/** Direct Labour  */
	var cell9 = document.createElement("TD");
	var Current_Year_Direct_Labour = document.createElement("input");
	Current_Year_Direct_Labour.type = "text";
	Current_Year_Direct_Labour.name = "Current_Year_Direct_Labour" + seq;
	Current_Year_Direct_Labour.id = "Current_Year_Direct_Labour" + seq;
	Current_Year_Direct_Labour.value = "";
	Current_Year_Direct_Labour.size = "10";	
	cell9.appendChild(Current_Year_Direct_Labour);
	mycurrent_row.appendChild(cell9);

	/** Indirect Labour */
	var cell20 = document.createElement("TD");
	var Previous_Year_Indirect_Labour = document.createElement("input");
	Previous_Year_Indirect_Labour.type = "text";
	Previous_Year_Indirect_Labour.name = "Previous_Year_Indirect_Labour" + seq;
	Previous_Year_Indirect_Labour.id = "Previous_Year_Indirect_Labour" + seq;
	Previous_Year_Indirect_Labour.value = "";	
	Previous_Year_Indirect_Labour.size = "10";
	cell20.appendChild(Previous_Year_Indirect_Labour);
	mycurrent_row.appendChild(cell20);

	/** Indirect Labour */
	var cel21 = document.createElement("TD");
	var Current_Year_Indirect_Labour = document.createElement("input");
	Current_Year_Indirect_Labour.type = "text";
	Current_Year_Indirect_Labour.name = "Current_Year_Indirect_Labour" + seq;
	Current_Year_Indirect_Labour.id = "Current_Year_Indirect_Labour" + seq;
	Current_Year_Indirect_Labour.value = "";
	Current_Year_Indirect_Labour.size = "10";
	cel21.appendChild(Current_Year_Indirect_Labour);
	mycurrent_row.appendChild(cel21);

	/** Value of Work Turned Through Outside  */
	var cel22 = document.createElement("TD");
	var Previous_Year_Value_of_Work_Outside  = document.createElement("input");
	Previous_Year_Value_of_Work_Outside.type = "text";
	Previous_Year_Value_of_Work_Outside.name = "Previous_Year_Value_of_Work_Outside" + seq;
	Previous_Year_Value_of_Work_Outside.id = "Previous_Year_Value_of_Work_Outside" + seq;
	Previous_Year_Value_of_Work_Outside.value = "";
	Previous_Year_Value_of_Work_Outside.size = "10";
	cel22.appendChild(Previous_Year_Value_of_Work_Outside );
	mycurrent_row.appendChild(cel22);

	/** Value of Work Turned Through Outside  */
	var cel23 = document.createElement("TD");
	var Current_Year_Value_of_Work_Outside  = document.createElement("input");
	Current_Year_Value_of_Work_Outside.type = "text";
	Current_Year_Value_of_Work_Outside.name = "Current_Year_Value_of_Work_Outside" + seq;
	Current_Year_Value_of_Work_Outside.id = "Current_Year_Value_of_Work_Outside" + seq;
	Current_Year_Value_of_Work_Outside.value = "";
	Current_Year_Value_of_Work_Outside.size = "10";
	cel23.appendChild(Current_Year_Value_of_Work_Outside );
	mycurrent_row.appendChild(cel23);
	tbody.appendChild(mycurrent_row);

	/** Cost of Spares  */
	var cel24 = document.createElement("TD");
	var Previous_Year_Cost_of_Spares = document.createElement("input");
	Previous_Year_Cost_of_Spares.type = "text";
	Previous_Year_Cost_of_Spares.name = "Previous_Year_Cost_of_Spares" + seq;
	Previous_Year_Cost_of_Spares.id = "Previous_Year_Cost_of_Spares" + seq;
	Previous_Year_Cost_of_Spares.value = "";
	Previous_Year_Cost_of_Spares.size = "10";
	cel24.appendChild(Previous_Year_Cost_of_Spares);
	mycurrent_row.appendChild(cel24);

	/** Cost of Spares  */
	var cell25 = document.createElement("TD");
	var Current_Year_Cost_of_Spares = document.createElement("input");
	Current_Year_Cost_of_Spares.type = "text";
	Current_Year_Cost_of_Spares.name = "Current_Year_Cost_of_Spares" + seq;
	Current_Year_Cost_of_Spares.id = "Current_Year_Cost_of_Spares" + seq;
	Current_Year_Cost_of_Spares.value = "";
	Current_Year_Cost_of_Spares.size = "10";
	cell25.appendChild(Current_Year_Cost_of_Spares);
	mycurrent_row.appendChild(cell25);

	/** Expenditure of Others  */
	var cel26 = document.createElement("TD");
	var Previous_Year_Expenditure_of_Others  = document.createElement("input");
	Previous_Year_Expenditure_of_Others.type = "text";
	Previous_Year_Expenditure_of_Others.name = "Previous_Year_Expenditure_of_Others" + seq;
	Previous_Year_Expenditure_of_Others.id = "Previous_Year_Expenditure_of_Others" + seq;
	Previous_Year_Expenditure_of_Others.value = "";
	Previous_Year_Expenditure_of_Others.size = "10";
	cel26.appendChild(Previous_Year_Expenditure_of_Others );
	mycurrent_row.appendChild(cel26);

	/** Expenditure of Others  */
	var cel27 = document.createElement("TD");
	var Current_Year_Expenditure_of_Others  = document.createElement("input");
	Current_Year_Expenditure_of_Others.type = "text";
	Current_Year_Expenditure_of_Others.name = "Current_Year_Expenditure_of_Others" + seq;
	Current_Year_Expenditure_of_Others.id = "Current_Year_Expenditure_of_Others" + seq;
	Current_Year_Expenditure_of_Others.value = "";
	Current_Year_Expenditure_of_Others.size = "10";
	cel27.appendChild(Current_Year_Expenditure_of_Others );
	mycurrent_row.appendChild(cel27);

	/** Total Cost  */
	var cel28 = document.createElement("TD");
	var Previous_Year_Total_Cost  = document.createElement("input");
	Previous_Year_Total_Cost.type = "text";
	Previous_Year_Total_Cost.name = "Previous_Year_Total_Cost" + seq;
	Previous_Year_Total_Cost.id = "Previous_Year_Total_Cost" + seq;
	Previous_Year_Total_Cost.value = "";
	Previous_Year_Total_Cost.size = "10";
	cel28.appendChild(Previous_Year_Total_Cost );
	mycurrent_row.appendChild(cel28);
	tbody.appendChild(mycurrent_row);

	/** Total Cost  */
	var cel29 = document.createElement("TD");
	var Current_Year_Total_Cost  = document.createElement("input");
	Current_Year_Total_Cost.type = "text";
	Current_Year_Total_Cost.name = "Current_Year_Total_Cost" + seq;
	Current_Year_Total_Cost.id = "Current_Year_Total_Cost" + seq;
	Current_Year_Total_Cost.value = "";
	Current_Year_Total_Cost.size = "10";
	cel29.appendChild(Current_Year_Total_Cost );
	mycurrent_row.appendChild(cel29);

	tbody.appendChild(mycurrent_row);
	/** Increment Sequence Number */
	seq = seq + 1;

}

function initialLoade() {

	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1 = fy.split('-');
	var y1 = fy1[0];
	var y2 = fy1[1];

	document.frmCivil_Budget_Format_12_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_12_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_12_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_12_Consolidation.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_12_Consolidation?command=get&y1="
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
				item[0] = baseResponse.getElementsByTagName("Vehicles")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Jobs_Pending")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Previous_Year_Jobs_Entrusted")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("Current_Year_Jobs_Entrusted")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("Previous_Year_Jobs_Complited")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("Current_Year_Jobs_Complited")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Previous_Year_Direct_Labour")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("Current_Year_Direct_Labour")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="0";
				}
				item[8] = baseResponse.getElementsByTagName("Previous_Year_Indirect_Labour")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("Current_Year_Indirect_Labour")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("Previous_Year_Value_of_Work_Outside")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("Current_Year_Value_of_Work_Outside")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("Previous_Year_Cost_of_Spares")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("Current_Year_Cost_of_Spares")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("Previous_Year_Expenditure_of_Others")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
				}
				item[15] = baseResponse
						.getElementsByTagName("Current_Year_Expenditure_of_Others")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="0";
				}
				item[16] = baseResponse
						.getElementsByTagName("Previous_Year_Total_Cost")[k].firstChild.nodeValue;
				if(item[16]=='null'){
					item[16]="0";
				}
				item[17] = baseResponse.getElementsByTagName("Current_Year_Total_Cost")[k].firstChild.nodeValue;
				if(item[17]=='null'){
					item[17]="";
				}
				item[18] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[18]=='null'){
					item[18]="";
				}
				item[23] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[23]=='null'){
					item[23]="";
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

				/** Vehicles */
				var cell2 = document.createElement("TD");
				var Vehicles = document.createElement("input");
				Vehicles.type = "text";
				Vehicles.name = "Vehicles" + seq;
				Vehicles.id = "Vehicles" + seq;
				Vehicles.value = item[0];	
				cell2.appendChild(Vehicles);
				mycurrent_row.appendChild(cell2);

				/** Jobs Pending  */
				var cell3 = document.createElement("TD");
				var Jobs_Pending = document.createElement("input");
				Jobs_Pending.type = "text";
				Jobs_Pending.name = "Jobs_Pending" + seq;
				Jobs_Pending.id = "Jobs_Pending" + seq;
				Jobs_Pending.value = item[1];
				cell3.appendChild(Jobs_Pending);
				mycurrent_row.appendChild(cell3);

				/** No of Jobs Entrusted Previous_Year */
				var cell4 = document.createElement("TD");
				var Previous_Year_Jobs_Entrusted  = document.createElement("input");
				Previous_Year_Jobs_Entrusted.type = "text";
				Previous_Year_Jobs_Entrusted.name = "Previous_Year_Jobs_Entrusted" + seq;
				Previous_Year_Jobs_Entrusted.id = "Previous_Year_Jobs_Entrusted" + seq;
				Previous_Year_Jobs_Entrusted.value = item[2];
				Previous_Year_Jobs_Entrusted.size = "10";
				cell4.appendChild(Previous_Year_Jobs_Entrusted );
				mycurrent_row.appendChild(cell4);

				/** No of Jobs Entrusted Current_Year */
				var cell5 = document.createElement("TD");
				var Current_Year_Jobs_Entrusted = document.createElement("input");
				Current_Year_Jobs_Entrusted.type = "text";
				Current_Year_Jobs_Entrusted.name = "Current_Year_Jobs_Entrusted" + seq;
				Current_Year_Jobs_Entrusted.id = "Current_Year_Jobs_Entrusted" + seq;
				Current_Year_Jobs_Entrusted.value = item[3];
				Current_Year_Jobs_Entrusted.size = "10";
				cell5.appendChild(Current_Year_Jobs_Entrusted);
				mycurrent_row.appendChild(cell5);

				/** No of Jobs Complited  */
				var cell6 = document.createElement("TD");
				var Previous_Year_Jobs_Complited = document.createElement("input");
				Previous_Year_Jobs_Complited.type = "text";
				Previous_Year_Jobs_Complited.name = "Previous_Year_Jobs_Complited" + seq;
				Previous_Year_Jobs_Complited.id = "Previous_Year_Jobs_Complited" + seq;
				Previous_Year_Jobs_Complited.value = item[4];
				Previous_Year_Jobs_Complited.size = "10";
				cell6.appendChild(Previous_Year_Jobs_Complited);
				mycurrent_row.appendChild(cell6);

				/** No of Jobs Complited  */
				var cell7 = document.createElement("TD");
				var Current_Year_Jobs_Complited = document.createElement("input");
				Current_Year_Jobs_Complited.type = "text";
				Current_Year_Jobs_Complited.name = "Current_Year_Jobs_Complited" + seq;
				Current_Year_Jobs_Complited.id = "Current_Year_Jobs_Complited" + seq;
				Current_Year_Jobs_Complited.value = item[5];
				Current_Year_Jobs_Complited.size = "10";
				cell7.appendChild(Current_Year_Jobs_Complited);
				mycurrent_row.appendChild(cell7);

				/** Direct Labour */
				var cell8 = document.createElement("TD");
				var Previous_Year_Direct_Labour = document.createElement("input");
				Previous_Year_Direct_Labour.type = "text";
				Previous_Year_Direct_Labour.name = "Previous_Year_Direct_Labour" + seq;
				Previous_Year_Direct_Labour.id = "Previous_Year_Direct_Labour" + seq;
				Previous_Year_Direct_Labour.value = item[6]+"0";
				Previous_Year_Direct_Labour.style.textAlign="right";
				Previous_Year_Direct_Labour.size = "10";	
				cell8.appendChild(Previous_Year_Direct_Labour);
				mycurrent_row.appendChild(cell8);

				/** Direct Labour  */
				var cell9 = document.createElement("TD");
				var Current_Year_Direct_Labour = document.createElement("input");
				Current_Year_Direct_Labour.type = "text";
				Current_Year_Direct_Labour.name = "Current_Year_Direct_Labour" + seq;
				Current_Year_Direct_Labour.id = "Current_Year_Direct_Labour" + seq;
				Current_Year_Direct_Labour.value = item[7]+"0";
				Current_Year_Direct_Labour.style.textAlign="right";
				Current_Year_Direct_Labour.size = "10";	
				cell9.appendChild(Current_Year_Direct_Labour);
				mycurrent_row.appendChild(cell9);

				/** Indirect Labour */
				var cell20 = document.createElement("TD");
				var Previous_Year_Indirect_Labour = document.createElement("input");
				Previous_Year_Indirect_Labour.type = "text";
				Previous_Year_Indirect_Labour.name = "Previous_Year_Indirect_Labour" + seq;
				Previous_Year_Indirect_Labour.id = "Previous_Year_Indirect_Labour" + seq;
				Previous_Year_Indirect_Labour.value = item[8]+"0";	
				Previous_Year_Indirect_Labour.style.textAlign="right";
				Previous_Year_Indirect_Labour.size = "10";
				cell20.appendChild(Previous_Year_Indirect_Labour);
				mycurrent_row.appendChild(cell20);

				/** Indirect Labour */
				var cel21 = document.createElement("TD");
				var Current_Year_Indirect_Labour = document.createElement("input");
				Current_Year_Indirect_Labour.type = "text";
				Current_Year_Indirect_Labour.name = "Current_Year_Indirect_Labour" + seq;
				Current_Year_Indirect_Labour.id = "Current_Year_Indirect_Labour" + seq;
				Current_Year_Indirect_Labour.value = item[9]+"0";
				Current_Year_Indirect_Labour.style.textAlign="right";
				Current_Year_Indirect_Labour.size = "10";
				cel21.appendChild(Current_Year_Indirect_Labour);
				mycurrent_row.appendChild(cel21);

				/** Value of Work Turned Through Outside  */
				var cel22 = document.createElement("TD");
				var Previous_Year_Value_of_Work_Outside  = document.createElement("input");
				Previous_Year_Value_of_Work_Outside.type = "text";
				Previous_Year_Value_of_Work_Outside.name = "Previous_Year_Value_of_Work_Outside" + seq;
				Previous_Year_Value_of_Work_Outside.id = "Previous_Year_Value_of_Work_Outside" + seq;
				Previous_Year_Value_of_Work_Outside.value = item[10]+"0";
				Previous_Year_Value_of_Work_Outside.style.textAlign="right";
				Previous_Year_Value_of_Work_Outside.size = "10";
				cel22.appendChild(Previous_Year_Value_of_Work_Outside );
				mycurrent_row.appendChild(cel22);

				/** Value of Work Turned Through Outside  */
				var cel23 = document.createElement("TD");
				var Current_Year_Value_of_Work_Outside  = document.createElement("input");
				Current_Year_Value_of_Work_Outside.type = "text";
				Current_Year_Value_of_Work_Outside.name = "Current_Year_Value_of_Work_Outside" + seq;
				Current_Year_Value_of_Work_Outside.id = "Current_Year_Value_of_Work_Outside" + seq;
				Current_Year_Value_of_Work_Outside.value = item[11]+"0";
				Current_Year_Value_of_Work_Outside.style.textAlign="right";
				Current_Year_Value_of_Work_Outside.size = "10";
				cel23.appendChild(Current_Year_Value_of_Work_Outside );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** Cost of Spares  */
				var cel24 = document.createElement("TD");
				var Previous_Year_Cost_of_Spares = document.createElement("input");
				Previous_Year_Cost_of_Spares.type = "text";
				Previous_Year_Cost_of_Spares.name = "Previous_Year_Cost_of_Spares" + seq;
				Previous_Year_Cost_of_Spares.id = "Previous_Year_Cost_of_Spares" + seq;
				Previous_Year_Cost_of_Spares.value = item[12]+"0";
				Previous_Year_Cost_of_Spares.style.textAlign="right";
				Previous_Year_Cost_of_Spares.size = "10";
				cel24.appendChild(Previous_Year_Cost_of_Spares);
				mycurrent_row.appendChild(cel24);

				/** Cost of Spares  */
				var cell25 = document.createElement("TD");
				var Current_Year_Cost_of_Spares = document.createElement("input");
				Current_Year_Cost_of_Spares.type = "text";
				Current_Year_Cost_of_Spares.name = "Current_Year_Cost_of_Spares" + seq;
				Current_Year_Cost_of_Spares.id = "Current_Year_Cost_of_Spares" + seq;
				Current_Year_Cost_of_Spares.value = item[13]+"0";
				Current_Year_Cost_of_Spares.style.textAlign="right";
				Current_Year_Cost_of_Spares.size = "10";
				cell25.appendChild(Current_Year_Cost_of_Spares);
				mycurrent_row.appendChild(cell25);

				/** Expenditure of Others  */
				var cel26 = document.createElement("TD");
				var Previous_Year_Expenditure_of_Others  = document.createElement("input");
				Previous_Year_Expenditure_of_Others.type = "text";
				Previous_Year_Expenditure_of_Others.name = "Previous_Year_Expenditure_of_Others" + seq;
				Previous_Year_Expenditure_of_Others.id = "Previous_Year_Expenditure_of_Others" + seq;
				Previous_Year_Expenditure_of_Others.value = item[14]+"0";
				Previous_Year_Expenditure_of_Others.style.textAlign="right";
				Previous_Year_Expenditure_of_Others.size = "10";
				cel26.appendChild(Previous_Year_Expenditure_of_Others );
				mycurrent_row.appendChild(cel26);

				/** Expenditure of Others  */
				var cel27 = document.createElement("TD");
				var Current_Year_Expenditure_of_Others  = document.createElement("input");
				Current_Year_Expenditure_of_Others.type = "text";
				Current_Year_Expenditure_of_Others.name = "Current_Year_Expenditure_of_Others" + seq;
				Current_Year_Expenditure_of_Others.id = "Current_Year_Expenditure_of_Others" + seq;
				Current_Year_Expenditure_of_Others.value = item[15]+"0";
				Current_Year_Expenditure_of_Others.style.textAlign="right";
				Current_Year_Expenditure_of_Others.size = "10";
				cel27.appendChild(Current_Year_Expenditure_of_Others );
				mycurrent_row.appendChild(cel27);

				/** Total Cost  */
				var cel28 = document.createElement("TD");
				var Previous_Year_Total_Cost  = document.createElement("input");
				Previous_Year_Total_Cost.type = "text";
				Previous_Year_Total_Cost.name = "Previous_Year_Total_Cost" + seq;
				Previous_Year_Total_Cost.id = "Previous_Year_Total_Cost" + seq;
				Previous_Year_Total_Cost.value = item[16]+"0";
				Previous_Year_Total_Cost.style.textAlign="right";
				Previous_Year_Total_Cost.size = "10";
				cel28.appendChild(Previous_Year_Total_Cost );
				mycurrent_row.appendChild(cel28);
				tbody.appendChild(mycurrent_row);

				/** Total Cost  */
				var cel29 = document.createElement("TD");
				var Current_Year_Total_Cost  = document.createElement("input");
				Current_Year_Total_Cost.type = "text";
				Current_Year_Total_Cost.name = "Current_Year_Total_Cost" + seq;
				Current_Year_Total_Cost.id = "Current_Year_Total_Cost" + seq;
				Current_Year_Total_Cost.value = item[17]+"0";
				Current_Year_Total_Cost.style.textAlign="right";
				Current_Year_Total_Cost.size = "10";
				cel29.appendChild(Current_Year_Total_Cost );
				mycurrent_row.appendChild(cel29);

				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[23]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_12_Consolidation")
						.appendChild(slno_db);
				
				/** acc_unit_id */
				var acc_unit_id = document.createElement("input");
				acc_unit_id.setAttribute("type", "hidden");
				acc_unit_id.setAttribute("value", item[26]);
				acc_unit_id.setAttribute("name", "acc_unit_id" + seq);
				acc_unit_id.setAttribute("id", "acc_unit_id" + seq);
				document.getElementById("frmCivil_Budget_Format_12_Consolidation")
						.appendChild(acc_unit_id);								
				
				/** acc_office_id */
				var acc_office_id = document.createElement("input");
				acc_office_id.setAttribute("type", "hidden");
				acc_office_id.setAttribute("value", item[24]);
				acc_office_id.setAttribute("name", "acc_office_id" + seq);
				acc_office_id.setAttribute("id", "acc_office_id" + seq);
				document.getElementById("frmCivil_Budget_Format_12_Consolidation")
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
	document.frmCivil_Budget_Format_12_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_12_Consolidation.butSub.disabled = true;
	document.frmCivil_Budget_Format_12_Consolidation.butDelete.disabled = false;
	document.frmCivil_Budget_Format_12_Consolidation.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;

	var url = path + "/Civil_Budget_Format_12_Consolidation?filter=view&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&cmbOffice_code=" + cmbOffice_code + "&RecordCount="
			+ RecordCount;
	//alert(url);
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
				item[0] = baseResponse.getElementsByTagName("Vehicles")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Jobs_Pending")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Previous_Year_Jobs_Entrusted")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("Current_Year_Jobs_Entrusted")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("Previous_Year_Jobs_Complited")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("Current_Year_Jobs_Complited")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Previous_Year_Direct_Labour")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("Current_Year_Direct_Labour")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="0";
				}
				item[8] = baseResponse.getElementsByTagName("Previous_Year_Indirect_Labour")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("Current_Year_Indirect_Labour")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("Previous_Year_Value_of_Work_Outside")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("Current_Year_Value_of_Work_Outside")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("Previous_Year_Cost_of_Spares")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("Current_Year_Cost_of_Spares")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("Previous_Year_Expenditure_of_Others")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
				}
				item[15] = baseResponse
						.getElementsByTagName("Current_Year_Expenditure_of_Others")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="0";
				}
				item[16] = baseResponse
						.getElementsByTagName("Previous_Year_Total_Cost")[k].firstChild.nodeValue;
				if(item[16]=='null'){
					item[16]="0";
				}
				item[17] = baseResponse.getElementsByTagName("Current_Year_Total_Cost")[k].firstChild.nodeValue;
				if(item[17]=='null'){
					item[17]="";
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

				/** Vehicles */
				var cell2 = document.createElement("TD");
				var Vehicles = document.createElement("input");
				Vehicles.type = "text";
				Vehicles.name = "Vehicles" + seq;
				Vehicles.id = "Vehicles" + seq;
				Vehicles.value = item[0];	
				cell2.appendChild(Vehicles);
				mycurrent_row.appendChild(cell2);

				/** Jobs Pending  */
				var cell3 = document.createElement("TD");
				var Jobs_Pending = document.createElement("input");
				Jobs_Pending.type = "text";
				Jobs_Pending.name = "Jobs_Pending" + seq;
				Jobs_Pending.id = "Jobs_Pending" + seq;
				Jobs_Pending.value = item[1];
				cell3.appendChild(Jobs_Pending);
				mycurrent_row.appendChild(cell3);

				/** No of Jobs Entrusted Previous_Year */
				var cell4 = document.createElement("TD");
				var Previous_Year_Jobs_Entrusted  = document.createElement("input");
				Previous_Year_Jobs_Entrusted.type = "text";
				Previous_Year_Jobs_Entrusted.name = "Previous_Year_Jobs_Entrusted" + seq;
				Previous_Year_Jobs_Entrusted.id = "Previous_Year_Jobs_Entrusted" + seq;
				Previous_Year_Jobs_Entrusted.value = item[2];
				Previous_Year_Jobs_Entrusted.size = "10";
				cell4.appendChild(Previous_Year_Jobs_Entrusted );
				mycurrent_row.appendChild(cell4);

				/** No of Jobs Entrusted Current_Year */
				var cell5 = document.createElement("TD");
				var Current_Year_Jobs_Entrusted = document.createElement("input");
				Current_Year_Jobs_Entrusted.type = "text";
				Current_Year_Jobs_Entrusted.name = "Current_Year_Jobs_Entrusted" + seq;
				Current_Year_Jobs_Entrusted.id = "Current_Year_Jobs_Entrusted" + seq;
				Current_Year_Jobs_Entrusted.value = item[3];
				Current_Year_Jobs_Entrusted.size = "10";
				cell5.appendChild(Current_Year_Jobs_Entrusted);
				mycurrent_row.appendChild(cell5);

				/** No of Jobs Complited  */
				var cell6 = document.createElement("TD");
				var Previous_Year_Jobs_Complited = document.createElement("input");
				Previous_Year_Jobs_Complited.type = "text";
				Previous_Year_Jobs_Complited.name = "Previous_Year_Jobs_Complited" + seq;
				Previous_Year_Jobs_Complited.id = "Previous_Year_Jobs_Complited" + seq;
				Previous_Year_Jobs_Complited.value = item[4];
				Previous_Year_Jobs_Complited.size = "10";
				cell6.appendChild(Previous_Year_Jobs_Complited);
				mycurrent_row.appendChild(cell6);

				/** No of Jobs Complited  */
				var cell7 = document.createElement("TD");
				var Current_Year_Jobs_Complited = document.createElement("input");
				Current_Year_Jobs_Complited.type = "text";
				Current_Year_Jobs_Complited.name = "Current_Year_Jobs_Complited" + seq;
				Current_Year_Jobs_Complited.id = "Current_Year_Jobs_Complited" + seq;
				Current_Year_Jobs_Complited.value = item[5];
				Current_Year_Jobs_Complited.size = "10";
				cell7.appendChild(Current_Year_Jobs_Complited);
				mycurrent_row.appendChild(cell7);

				/** Direct Labour */
				var cell8 = document.createElement("TD");
				var Previous_Year_Direct_Labour = document.createElement("input");
				Previous_Year_Direct_Labour.type = "text";
				Previous_Year_Direct_Labour.name = "Previous_Year_Direct_Labour" + seq;
				Previous_Year_Direct_Labour.id = "Previous_Year_Direct_Labour" + seq;
				Previous_Year_Direct_Labour.value = item[6]+"0";
				Previous_Year_Direct_Labour.style.textAlign="right";
				Previous_Year_Direct_Labour.size = "10";	
				cell8.appendChild(Previous_Year_Direct_Labour);
				mycurrent_row.appendChild(cell8);

				/** Direct Labour  */
				var cell9 = document.createElement("TD");
				var Current_Year_Direct_Labour = document.createElement("input");
				Current_Year_Direct_Labour.type = "text";
				Current_Year_Direct_Labour.name = "Current_Year_Direct_Labour" + seq;
				Current_Year_Direct_Labour.id = "Current_Year_Direct_Labour" + seq;
				Current_Year_Direct_Labour.value = item[7]+"0";
				Current_Year_Direct_Labour.style.textAlign="right";
				Current_Year_Direct_Labour.size = "10";	
				cell9.appendChild(Current_Year_Direct_Labour);
				mycurrent_row.appendChild(cell9);

				/** Indirect Labour */
				var cell20 = document.createElement("TD");
				var Previous_Year_Indirect_Labour = document.createElement("input");
				Previous_Year_Indirect_Labour.type = "text";
				Previous_Year_Indirect_Labour.name = "Previous_Year_Indirect_Labour" + seq;
				Previous_Year_Indirect_Labour.id = "Previous_Year_Indirect_Labour" + seq;
				Previous_Year_Indirect_Labour.value = item[8]+"0";	
				Previous_Year_Indirect_Labour.style.textAlign="right";
				Previous_Year_Indirect_Labour.size = "10";
				cell20.appendChild(Previous_Year_Indirect_Labour);
				mycurrent_row.appendChild(cell20);

				/** Indirect Labour */
				var cel21 = document.createElement("TD");
				var Current_Year_Indirect_Labour = document.createElement("input");
				Current_Year_Indirect_Labour.type = "text";
				Current_Year_Indirect_Labour.name = "Current_Year_Indirect_Labour" + seq;
				Current_Year_Indirect_Labour.id = "Current_Year_Indirect_Labour" + seq;
				Current_Year_Indirect_Labour.value = item[9]+"0";
				Current_Year_Indirect_Labour.style.textAlign="right";
				Current_Year_Indirect_Labour.size = "10";
				cel21.appendChild(Current_Year_Indirect_Labour);
				mycurrent_row.appendChild(cel21);

				/** Value of Work Turned Through Outside  */
				var cel22 = document.createElement("TD");
				var Previous_Year_Value_of_Work_Outside  = document.createElement("input");
				Previous_Year_Value_of_Work_Outside.type = "text";
				Previous_Year_Value_of_Work_Outside.name = "Previous_Year_Value_of_Work_Outside" + seq;
				Previous_Year_Value_of_Work_Outside.id = "Previous_Year_Value_of_Work_Outside" + seq;
				Previous_Year_Value_of_Work_Outside.value = item[10]+"0";
				Previous_Year_Value_of_Work_Outside.style.textAlign="right";
				Previous_Year_Value_of_Work_Outside.size = "10";
				cel22.appendChild(Previous_Year_Value_of_Work_Outside );
				mycurrent_row.appendChild(cel22);

				/** Value of Work Turned Through Outside  */
				var cel23 = document.createElement("TD");
				var Current_Year_Value_of_Work_Outside  = document.createElement("input");
				Current_Year_Value_of_Work_Outside.type = "text";
				Current_Year_Value_of_Work_Outside.name = "Current_Year_Value_of_Work_Outside" + seq;
				Current_Year_Value_of_Work_Outside.id = "Current_Year_Value_of_Work_Outside" + seq;
				Current_Year_Value_of_Work_Outside.value = item[11]+"0";
				Current_Year_Value_of_Work_Outside.style.textAlign="right";
				Current_Year_Value_of_Work_Outside.size = "10";
				cel23.appendChild(Current_Year_Value_of_Work_Outside );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** Cost of Spares  */
				var cel24 = document.createElement("TD");
				var Previous_Year_Cost_of_Spares = document.createElement("input");
				Previous_Year_Cost_of_Spares.type = "text";
				Previous_Year_Cost_of_Spares.name = "Previous_Year_Cost_of_Spares" + seq;
				Previous_Year_Cost_of_Spares.id = "Previous_Year_Cost_of_Spares" + seq;
				Previous_Year_Cost_of_Spares.value = item[12]+"0";
				Previous_Year_Cost_of_Spares.style.textAlign="right";
				Previous_Year_Cost_of_Spares.size = "10";
				cel24.appendChild(Previous_Year_Cost_of_Spares);
				mycurrent_row.appendChild(cel24);

				/** Cost of Spares  */
				var cell25 = document.createElement("TD");
				var Current_Year_Cost_of_Spares = document.createElement("input");
				Current_Year_Cost_of_Spares.type = "text";
				Current_Year_Cost_of_Spares.name = "Current_Year_Cost_of_Spares" + seq;
				Current_Year_Cost_of_Spares.id = "Current_Year_Cost_of_Spares" + seq;
				Current_Year_Cost_of_Spares.value = item[13]+"0";
				Current_Year_Cost_of_Spares.style.textAlign="right";
				Current_Year_Cost_of_Spares.size = "10";
				cell25.appendChild(Current_Year_Cost_of_Spares);
				mycurrent_row.appendChild(cell25);

				/** Expenditure of Others  */
				var cel26 = document.createElement("TD");
				var Previous_Year_Expenditure_of_Others  = document.createElement("input");
				Previous_Year_Expenditure_of_Others.type = "text";
				Previous_Year_Expenditure_of_Others.name = "Previous_Year_Expenditure_of_Others" + seq;
				Previous_Year_Expenditure_of_Others.id = "Previous_Year_Expenditure_of_Others" + seq;
				Previous_Year_Expenditure_of_Others.value = item[14]+"0";
				Previous_Year_Expenditure_of_Others.style.textAlign="right";
				Previous_Year_Expenditure_of_Others.size = "10";
				cel26.appendChild(Previous_Year_Expenditure_of_Others );
				mycurrent_row.appendChild(cel26);

				/** Expenditure of Others  */
				var cel27 = document.createElement("TD");
				var Current_Year_Expenditure_of_Others  = document.createElement("input");
				Current_Year_Expenditure_of_Others.type = "text";
				Current_Year_Expenditure_of_Others.name = "Current_Year_Expenditure_of_Others" + seq;
				Current_Year_Expenditure_of_Others.id = "Current_Year_Expenditure_of_Others" + seq;
				Current_Year_Expenditure_of_Others.value = item[15]+"0";
				Current_Year_Expenditure_of_Others.style.textAlign="right";
				Current_Year_Expenditure_of_Others.size = "10";
				cel27.appendChild(Current_Year_Expenditure_of_Others );
				mycurrent_row.appendChild(cel27);

				/** Total Cost  */
				var cel28 = document.createElement("TD");
				var Previous_Year_Total_Cost  = document.createElement("input");
				Previous_Year_Total_Cost.type = "text";
				Previous_Year_Total_Cost.name = "Previous_Year_Total_Cost" + seq;
				Previous_Year_Total_Cost.id = "Previous_Year_Total_Cost" + seq;
				Previous_Year_Total_Cost.value = item[16]+"0";
				Previous_Year_Total_Cost.style.textAlign="right";
				Previous_Year_Total_Cost.size = "10";
				cel28.appendChild(Previous_Year_Total_Cost );
				mycurrent_row.appendChild(cel28);
				tbody.appendChild(mycurrent_row);

				/** Total Cost  */
				var cel29 = document.createElement("TD");
				var Current_Year_Total_Cost  = document.createElement("input");
				Current_Year_Total_Cost.type = "text";
				Current_Year_Total_Cost.name = "Current_Year_Total_Cost" + seq;
				Current_Year_Total_Cost.id = "Current_Year_Total_Cost" + seq;
				Current_Year_Total_Cost.value = item[17]+"0";
				Current_Year_Total_Cost.style.textAlign="right";
				Current_Year_Total_Cost.size = "10";
				cel29.appendChild(Current_Year_Total_Cost );
				mycurrent_row.appendChild(cel29);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[18]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_12_Consolidation")
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
		alert("Civil Budget Format-12 have Already Freezed");
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
					document.getElementById("frmCivil_Budget_Format_12_Consolidation")
							.appendChild(slno_db1);
				} else {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", "-1");
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("frmCivil_Budget_Format_12_Consolidation")
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

	document.frmCivil_Budget_Format_12_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_12_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_12_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_12_Consolidation.butUpdate.disabled = true;
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