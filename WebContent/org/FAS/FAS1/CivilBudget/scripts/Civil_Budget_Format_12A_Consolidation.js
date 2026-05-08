//			Civil_Budget_Format_12A_Consolidation			//
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
	//alert("RK");
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

	document.frmCivil_Budget_Format_12A_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_12A_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_12A_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_12A_Consolidation.butUpdate.disabled = true;
}

function initialLoade() {

	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1 = fy.split('-');
	var y1 = fy1[0];
	var y2 = fy1[1];

	document.frmCivil_Budget_Format_12A_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_12A_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_12A_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_12A_Consolidation.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_12A_Consolidation?command=get&y1="
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
				item[1] = baseResponse.getElementsByTagName("Pending_on_Begining_of_the_Yr_1a")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Pending_on_Begining_of_the_Yr_1b")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("TDA_Raised_2a")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="";
				}
				item[4] = baseResponse.getElementsByTagName("TDA_Raised_2b")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("TDA_Raised_3a")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="";
				}
				item[6] = baseResponse
						.getElementsByTagName("TDA_Raised_3b")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("TDA_Accepted_and_Adjusted_4a")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("TDA_Accepted_and_Adjusted_4b")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("TDA_Accepted_and_Adjusted_5a")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="";
				}
				item[10] = baseResponse
						.getElementsByTagName("TDA_Accepted_and_Adjusted_5b")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("Value_of_Jobs_in_Progress_6a")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="";
				}
				item[12] = baseResponse.getElementsByTagName("Value_of_Jobs_in_Progress_6b")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("Value_of_Jobs_in_Progress_7a")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="";
				}				
				item[14] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="";
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

				/** 1a */
				var cell3 = document.createElement("TD");
				var Pending_on_Begining_of_the_Yr_1a=document.createElement("input");
				Pending_on_Begining_of_the_Yr_1a.type="text";
				Pending_on_Begining_of_the_Yr_1a.name="Pending_on_Begining_of_the_Yr_1a"+seq;
				Pending_on_Begining_of_the_Yr_1a.id="Pending_on_Begining_of_the_Yr_1a"+seq;
				Pending_on_Begining_of_the_Yr_1a.value=item[1];
				Pending_on_Begining_of_the_Yr_1a.size="10";
				cell3.appendChild(Pending_on_Begining_of_the_Yr_1a);
				mycurrent_row.appendChild(cell3);

				/** 1b */
				var cell4 = document.createElement("TD");
				var Pending_on_Begining_of_the_Yr_1b=document.createElement("input");
				Pending_on_Begining_of_the_Yr_1b.type ="text";
				Pending_on_Begining_of_the_Yr_1b.name="Pending_on_Begining_of_the_Yr_1b"+seq;
				Pending_on_Begining_of_the_Yr_1b.id="Pending_on_Begining_of_the_Yr_1b"+seq;
				Pending_on_Begining_of_the_Yr_1b.value=item[2]+"0";
				Pending_on_Begining_of_the_Yr_1b.style.textAlign="right";
				Pending_on_Begining_of_the_Yr_1b.size="10";
				cell4.appendChild(Pending_on_Begining_of_the_Yr_1b);
				mycurrent_row.appendChild(cell4);

				/** 2a */
				var cell5 = document.createElement("TD");
				var TDA_Raised_2a=document.createElement("input");
				TDA_Raised_2a.type="text";
				TDA_Raised_2a.name="TDA_Raised_2a"+seq;
				TDA_Raised_2a.id="TDA_Raised_2a"+seq;
				TDA_Raised_2a.value=item[3];
				TDA_Raised_2a.size="10";
				cell5.appendChild(TDA_Raised_2a);
				mycurrent_row.appendChild(cell5);

				/** 2b */
				var cell6 = document.createElement("TD");
				var TDA_Raised_2b=document.createElement("input");
				TDA_Raised_2b.type="text";
				TDA_Raised_2b.name="TDA_Raised_2b"+seq;
				TDA_Raised_2b.id="TDA_Raised_2b"+seq;
				TDA_Raised_2b.value=item[4]+"0";
				TDA_Raised_2b.style.textAlign="right";
				TDA_Raised_2b.size="10";
				cell6.appendChild(TDA_Raised_2b);
				mycurrent_row.appendChild(cell6);

				/** 3a */
				var cell7 = document.createElement("TD");
				var TDA_Raised_3a=document.createElement("input");
				TDA_Raised_3a.type="text";
				TDA_Raised_3a.name="TDA_Raised_3a"+seq;
				TDA_Raised_3a.id ="TDA_Raised_3a"+seq;
				TDA_Raised_3a.value=item[5];
				TDA_Raised_3a.size="10";
				cell7.appendChild(TDA_Raised_3a);
				mycurrent_row.appendChild(cell7);

				/** 3b */
				var cell8 = document.createElement("TD");
				var TDA_Raised_3b=document.createElement("input");
				TDA_Raised_3b.type="text";
				TDA_Raised_3b.name="TDA_Raised_3b"+seq;
				TDA_Raised_3b.id = "TDA_Raised_3b"+seq;
				TDA_Raised_3b.value=item[6]+"0";
				TDA_Raised_3b.style.textAlign="right";
				TDA_Raised_3b.size="10";	
				cell8.appendChild(TDA_Raised_3b);
				mycurrent_row.appendChild(cell8);

				/** 4a */
				var cell9 = document.createElement("TD");
				var TDA_Accepted_and_Adjusted_4a=document.createElement("input");
				TDA_Accepted_and_Adjusted_4a.type="text";
				TDA_Accepted_and_Adjusted_4a.name="TDA_Accepted_and_Adjusted_4a"+seq;
				TDA_Accepted_and_Adjusted_4a.id="TDA_Accepted_and_Adjusted_4a"+seq;
				TDA_Accepted_and_Adjusted_4a.value=item[7];
				TDA_Accepted_and_Adjusted_4a.size="10";	
				cell9.appendChild(TDA_Accepted_and_Adjusted_4a);
				mycurrent_row.appendChild(cell9);

				/** 4b */
				var cell20 = document.createElement("TD");
				var TDA_Accepted_and_Adjusted_4b=document.createElement("input");
				TDA_Accepted_and_Adjusted_4b.type="text";
				TDA_Accepted_and_Adjusted_4b.name="TDA_Accepted_and_Adjusted_4b"+seq;
				TDA_Accepted_and_Adjusted_4b.id="TDA_Accepted_and_Adjusted_4b"+seq;
				TDA_Accepted_and_Adjusted_4b.value=item[8]+"0";	
				TDA_Accepted_and_Adjusted_4b.style.textAlign="right";
				TDA_Accepted_and_Adjusted_4b.size="10";
				cell20.appendChild(TDA_Accepted_and_Adjusted_4b);
				mycurrent_row.appendChild(cell20);

				/** 5a */
				var cel21 = document.createElement("TD");
				var TDA_Accepted_and_Adjusted_5a=document.createElement("input");
				TDA_Accepted_and_Adjusted_5a.type="text";
				TDA_Accepted_and_Adjusted_5a.name="TDA_Accepted_and_Adjusted_5a"+seq;
				TDA_Accepted_and_Adjusted_5a.id="TDA_Accepted_and_Adjusted_5a"+seq;
				TDA_Accepted_and_Adjusted_5a.value=item[9];
				TDA_Accepted_and_Adjusted_5a.size="10";
				cel21.appendChild(TDA_Accepted_and_Adjusted_5a);
				mycurrent_row.appendChild(cel21);

				/** 5b */
				var cel22 = document.createElement("TD");
				var TDA_Accepted_and_Adjusted_5b=document.createElement("input");
				TDA_Accepted_and_Adjusted_5b.type="text";
				TDA_Accepted_and_Adjusted_5b.name="TDA_Accepted_and_Adjusted_5b"+seq;
				TDA_Accepted_and_Adjusted_5b.id ="TDA_Accepted_and_Adjusted_5b"+seq;
				TDA_Accepted_and_Adjusted_5b.value=item[10]+"0";
				TDA_Accepted_and_Adjusted_5b.style.textAlign="right";
				TDA_Accepted_and_Adjusted_5b.size="10";
				cel22.appendChild(TDA_Accepted_and_Adjusted_5b);
				mycurrent_row.appendChild(cel22);

				/** 6a */
				var cel23 = document.createElement("TD");
				var Value_of_Jobs_in_Progress_6a=document.createElement("input");
				Value_of_Jobs_in_Progress_6a.type="text";
				Value_of_Jobs_in_Progress_6a.name="Value_of_Jobs_in_Progress_6a"+seq;
				Value_of_Jobs_in_Progress_6a.id="Value_of_Jobs_in_Progress_6a"+seq;
				Value_of_Jobs_in_Progress_6a.value=item[11];
				Value_of_Jobs_in_Progress_6a.size="10";
				cel23.appendChild(Value_of_Jobs_in_Progress_6a);
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);	

				/** 6b */
				var cell25 = document.createElement("TD");
				var Value_of_Jobs_in_Progress_6b=document.createElement("input");
				Value_of_Jobs_in_Progress_6b.type="text";
				Value_of_Jobs_in_Progress_6b.name="Value_of_Jobs_in_Progress_6b"+seq;
				Value_of_Jobs_in_Progress_6b.id="Value_of_Jobs_in_Progress_6b"+seq;
				Value_of_Jobs_in_Progress_6b.value=item[12]+"0";
				Value_of_Jobs_in_Progress_6b.style.textAlign="right";
				Value_of_Jobs_in_Progress_6b.size="10";
				cell25.appendChild(Value_of_Jobs_in_Progress_6b);
				mycurrent_row.appendChild(cell25);

				/** 7a */
				var cel26 = document.createElement("TD");
				var Value_of_Jobs_in_Progress_7a=document.createElement("input");
				Value_of_Jobs_in_Progress_7a.type="text";
				Value_of_Jobs_in_Progress_7a.name="Value_of_Jobs_in_Progress_7a"+seq;
				Value_of_Jobs_in_Progress_7a.id ="Value_of_Jobs_in_Progress_7a"+seq;
				Value_of_Jobs_in_Progress_7a.value=item[13];
				Value_of_Jobs_in_Progress_7a.size="10";
				cel26.appendChild(Value_of_Jobs_in_Progress_7a);
				mycurrent_row.appendChild(cel26);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[14]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_12A_Consolidation")
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

	/** 1a */
	var cell3 = document.createElement("TD");
	var Pending_on_Begining_of_the_Yr_1a=document.createElement("input");
	Pending_on_Begining_of_the_Yr_1a.type="text";
	Pending_on_Begining_of_the_Yr_1a.name="Pending_on_Begining_of_the_Yr_1a"+seq;
	Pending_on_Begining_of_the_Yr_1a.id="Pending_on_Begining_of_the_Yr_1a"+seq;
	Pending_on_Begining_of_the_Yr_1a.value="";
	Pending_on_Begining_of_the_Yr_1a.size="10";
	cell3.appendChild(Pending_on_Begining_of_the_Yr_1a);
	mycurrent_row.appendChild(cell3);

	/** 1b */
	var cell4 = document.createElement("TD");
	var Pending_on_Begining_of_the_Yr_1b=document.createElement("input");
	Pending_on_Begining_of_the_Yr_1b.type ="text";
	Pending_on_Begining_of_the_Yr_1b.name="Pending_on_Begining_of_the_Yr_1b"+seq;
	Pending_on_Begining_of_the_Yr_1b.id="Pending_on_Begining_of_the_Yr_1b"+seq;
	Pending_on_Begining_of_the_Yr_1b.value="";
	Pending_on_Begining_of_the_Yr_1b.size="10";
	cell4.appendChild(Pending_on_Begining_of_the_Yr_1b);
	mycurrent_row.appendChild(cell4);

	/** 2a */
	var cell5 = document.createElement("TD");
	var TDA_Raised_2a=document.createElement("input");
	TDA_Raised_2a.type="text";
	TDA_Raised_2a.name="TDA_Raised_2a"+seq;
	TDA_Raised_2a.id="TDA_Raised_2a"+seq;
	TDA_Raised_2a.value="";
	TDA_Raised_2a.size="10";
	cell5.appendChild(TDA_Raised_2a);
	mycurrent_row.appendChild(cell5);

	/** 2b */
	var cell6 = document.createElement("TD");
	var TDA_Raised_2b=document.createElement("input");
	TDA_Raised_2b.type="text";
	TDA_Raised_2b.name="TDA_Raised_2b"+seq;
	TDA_Raised_2b.id="TDA_Raised_2b"+seq;
	TDA_Raised_2b.value="";
	TDA_Raised_2b.size="10";
	cell6.appendChild(TDA_Raised_2b);
	mycurrent_row.appendChild(cell6);

	/** 3a */
	var cell7 = document.createElement("TD");
	var TDA_Raised_3a=document.createElement("input");
	TDA_Raised_3a.type="text";
	TDA_Raised_3a.name="TDA_Raised_3a"+seq;
	TDA_Raised_3a.id ="TDA_Raised_3a"+seq;
	TDA_Raised_3a.value="";
	TDA_Raised_3a.size="10";
	cell7.appendChild(TDA_Raised_3a);
	mycurrent_row.appendChild(cell7);

	/** 3b */
	var cell8 = document.createElement("TD");
	var TDA_Raised_3b=document.createElement("input");
	TDA_Raised_3b.type="text";
	TDA_Raised_3b.name="TDA_Raised_3b"+seq;
	TDA_Raised_3b.id = "TDA_Raised_3b"+seq;
	TDA_Raised_3b.value="";
	TDA_Raised_3b.size="10";	
	cell8.appendChild(TDA_Raised_3b);
	mycurrent_row.appendChild(cell8);

	/** 4a */
	var cell9 = document.createElement("TD");
	var TDA_Accepted_and_Adjusted_4a=document.createElement("input");
	TDA_Accepted_and_Adjusted_4a.type="text";
	TDA_Accepted_and_Adjusted_4a.name="TDA_Accepted_and_Adjusted_4a"+seq;
	TDA_Accepted_and_Adjusted_4a.id="TDA_Accepted_and_Adjusted_4a"+seq;
	TDA_Accepted_and_Adjusted_4a.value="";
	TDA_Accepted_and_Adjusted_4a.size="10";	
	cell9.appendChild(TDA_Accepted_and_Adjusted_4a);
	mycurrent_row.appendChild(cell9);

	/** 4b */
	var cell20 = document.createElement("TD");
	var TDA_Accepted_and_Adjusted_4b=document.createElement("input");
	TDA_Accepted_and_Adjusted_4b.type="text";
	TDA_Accepted_and_Adjusted_4b.name="TDA_Accepted_and_Adjusted_4b"+seq;
	TDA_Accepted_and_Adjusted_4b.id="TDA_Accepted_and_Adjusted_4b"+seq;
	TDA_Accepted_and_Adjusted_4b.value="";	
	TDA_Accepted_and_Adjusted_4b.size="10";
	cell20.appendChild(TDA_Accepted_and_Adjusted_4b);
	mycurrent_row.appendChild(cell20);

	/** 5a */
	var cel21 = document.createElement("TD");
	var TDA_Accepted_and_Adjusted_5a=document.createElement("input");
	TDA_Accepted_and_Adjusted_5a.type="text";
	TDA_Accepted_and_Adjusted_5a.name="TDA_Accepted_and_Adjusted_5a"+seq;
	TDA_Accepted_and_Adjusted_5a.id="TDA_Accepted_and_Adjusted_5a"+seq;
	TDA_Accepted_and_Adjusted_5a.value="";
	TDA_Accepted_and_Adjusted_5a.size="10";
	cel21.appendChild(TDA_Accepted_and_Adjusted_5a);
	mycurrent_row.appendChild(cel21);

	/** 5b */
	var cel22 = document.createElement("TD");
	var TDA_Accepted_and_Adjusted_5b=document.createElement("input");
	TDA_Accepted_and_Adjusted_5b.type="text";
	TDA_Accepted_and_Adjusted_5b.name="TDA_Accepted_and_Adjusted_5b"+seq;
	TDA_Accepted_and_Adjusted_5b.id ="TDA_Accepted_and_Adjusted_5b"+seq;
	TDA_Accepted_and_Adjusted_5b.value="";
	TDA_Accepted_and_Adjusted_5b.size="10";
	cel22.appendChild(TDA_Accepted_and_Adjusted_5b);
	mycurrent_row.appendChild(cel22);

	/** 6a */
	var cel23 = document.createElement("TD");
	var Value_of_Jobs_in_Progress_6a=document.createElement("input");
	Value_of_Jobs_in_Progress_6a.type="text";
	Value_of_Jobs_in_Progress_6a.name="Value_of_Jobs_in_Progress_6a"+seq;
	Value_of_Jobs_in_Progress_6a.id="Value_of_Jobs_in_Progress_6a"+seq;
	Value_of_Jobs_in_Progress_6a.value="";
	Value_of_Jobs_in_Progress_6a.size="10";
	cel23.appendChild(Value_of_Jobs_in_Progress_6a);
	mycurrent_row.appendChild(cel23);
	tbody.appendChild(mycurrent_row);	

	/** 6b */
	var cell25 = document.createElement("TD");
	var Value_of_Jobs_in_Progress_6b=document.createElement("input");
	Value_of_Jobs_in_Progress_6b.type="text";
	Value_of_Jobs_in_Progress_6b.name="Value_of_Jobs_in_Progress_6b"+seq;
	Value_of_Jobs_in_Progress_6b.id="Value_of_Jobs_in_Progress_6b"+seq;
	Value_of_Jobs_in_Progress_6b.value="";
	Value_of_Jobs_in_Progress_6b.size="10";
	cell25.appendChild(Value_of_Jobs_in_Progress_6b);
	mycurrent_row.appendChild(cell25);

	/** 7a */
	var cel26 = document.createElement("TD");
	var Value_of_Jobs_in_Progress_7a=document.createElement("input");
	Value_of_Jobs_in_Progress_7a.type="text";
	Value_of_Jobs_in_Progress_7a.name="Value_of_Jobs_in_Progress_7a"+seq;
	Value_of_Jobs_in_Progress_7a.id ="Value_of_Jobs_in_Progress_7a"+seq;
	Value_of_Jobs_in_Progress_7a.value="";
	Value_of_Jobs_in_Progress_7a.size="10";
	cel26.appendChild(Value_of_Jobs_in_Progress_7a);
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
	document.frmCivil_Budget_Format_12A_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_12A_Consolidation.butSub.disabled = true;
	document.frmCivil_Budget_Format_12A_Consolidation.butDelete.disabled = false;
	document.frmCivil_Budget_Format_12A_Consolidation.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;

	var url = path + "/Civil_Budget_Format_12A_Consolidation?filter=view&cmbFinancialYear="
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
				item[0] = baseResponse.getElementsByTagName("Vehicles")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Pending_on_Begining_of_the_Yr_1a")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Pending_on_Begining_of_the_Yr_1b")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("TDA_Raised_2a")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="";
				}
				item[4] = baseResponse.getElementsByTagName("TDA_Raised_2b")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("TDA_Raised_3a")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="";
				}
				item[6] = baseResponse
						.getElementsByTagName("TDA_Raised_3b")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("TDA_Accepted_and_Adjusted_4a")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("TDA_Accepted_and_Adjusted_4b")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("TDA_Accepted_and_Adjusted_5a")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="";
				}
				item[10] = baseResponse
						.getElementsByTagName("TDA_Accepted_and_Adjusted_5b")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("Value_of_Jobs_in_Progress_6a")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="";
				}
				item[12] = baseResponse.getElementsByTagName("Value_of_Jobs_in_Progress_6b")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("Value_of_Jobs_in_Progress_7a")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="";
				}				
				item[14] = baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="";
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

				/** 1a */
				var cell3 = document.createElement("TD");
				var Pending_on_Begining_of_the_Yr_1a=document.createElement("input");
				Pending_on_Begining_of_the_Yr_1a.type="text";
				Pending_on_Begining_of_the_Yr_1a.name="Pending_on_Begining_of_the_Yr_1a"+seq;
				Pending_on_Begining_of_the_Yr_1a.id="Pending_on_Begining_of_the_Yr_1a"+seq;
				Pending_on_Begining_of_the_Yr_1a.value=item[1];
				Pending_on_Begining_of_the_Yr_1a.size="10";
				cell3.appendChild(Pending_on_Begining_of_the_Yr_1a);
				mycurrent_row.appendChild(cell3);

				/** 1b */
				var cell4 = document.createElement("TD");
				var Pending_on_Begining_of_the_Yr_1b=document.createElement("input");
				Pending_on_Begining_of_the_Yr_1b.type ="text";
				Pending_on_Begining_of_the_Yr_1b.name="Pending_on_Begining_of_the_Yr_1b"+seq;
				Pending_on_Begining_of_the_Yr_1b.id="Pending_on_Begining_of_the_Yr_1b"+seq;
				Pending_on_Begining_of_the_Yr_1b.value=item[2]+"0";
				Pending_on_Begining_of_the_Yr_1b.style.textAlign="right";
				Pending_on_Begining_of_the_Yr_1b.size="10";
				cell4.appendChild(Pending_on_Begining_of_the_Yr_1b);
				mycurrent_row.appendChild(cell4);

				/** 2a */
				var cell5 = document.createElement("TD");
				var TDA_Raised_2a=document.createElement("input");
				TDA_Raised_2a.type="text";
				TDA_Raised_2a.name="TDA_Raised_2a"+seq;
				TDA_Raised_2a.id="TDA_Raised_2a"+seq;
				TDA_Raised_2a.value=item[3];
				TDA_Raised_2a.size="10";
				cell5.appendChild(TDA_Raised_2a);
				mycurrent_row.appendChild(cell5);

				/** 2b */
				var cell6 = document.createElement("TD");
				var TDA_Raised_2b=document.createElement("input");
				TDA_Raised_2b.type="text";
				TDA_Raised_2b.name="TDA_Raised_2b"+seq;
				TDA_Raised_2b.id="TDA_Raised_2b"+seq;
				TDA_Raised_2b.value=item[4]+"0";
				TDA_Raised_2b.style.textAlign="right";
				TDA_Raised_2b.size="10";
				cell6.appendChild(TDA_Raised_2b);
				mycurrent_row.appendChild(cell6);

				/** 3a */
				var cell7 = document.createElement("TD");
				var TDA_Raised_3a=document.createElement("input");
				TDA_Raised_3a.type="text";
				TDA_Raised_3a.name="TDA_Raised_3a"+seq;
				TDA_Raised_3a.id ="TDA_Raised_3a"+seq;
				TDA_Raised_3a.value=item[5];
				TDA_Raised_3a.size="10";
				cell7.appendChild(TDA_Raised_3a);
				mycurrent_row.appendChild(cell7);

				/** 3b */
				var cell8 = document.createElement("TD");
				var TDA_Raised_3b=document.createElement("input");
				TDA_Raised_3b.type="text";
				TDA_Raised_3b.name="TDA_Raised_3b"+seq;
				TDA_Raised_3b.id = "TDA_Raised_3b"+seq;
				TDA_Raised_3b.value=item[6]+"0";
				TDA_Raised_3b.style.textAlign="right";
				TDA_Raised_3b.size="10";	
				cell8.appendChild(TDA_Raised_3b);
				mycurrent_row.appendChild(cell8);

				/** 4a */
				var cell9 = document.createElement("TD");
				var TDA_Accepted_and_Adjusted_4a=document.createElement("input");
				TDA_Accepted_and_Adjusted_4a.type="text";
				TDA_Accepted_and_Adjusted_4a.name="TDA_Accepted_and_Adjusted_4a"+seq;
				TDA_Accepted_and_Adjusted_4a.id="TDA_Accepted_and_Adjusted_4a"+seq;
				TDA_Accepted_and_Adjusted_4a.value=item[7];
				TDA_Accepted_and_Adjusted_4a.size="10";	
				cell9.appendChild(TDA_Accepted_and_Adjusted_4a);
				mycurrent_row.appendChild(cell9);

				/** 4b */
				var cell20 = document.createElement("TD");
				var TDA_Accepted_and_Adjusted_4b=document.createElement("input");
				TDA_Accepted_and_Adjusted_4b.type="text";
				TDA_Accepted_and_Adjusted_4b.name="TDA_Accepted_and_Adjusted_4b"+seq;
				TDA_Accepted_and_Adjusted_4b.id="TDA_Accepted_and_Adjusted_4b"+seq;
				TDA_Accepted_and_Adjusted_4b.value=item[8]+"0";	
				TDA_Accepted_and_Adjusted_4b.style.textAlign="right";
				TDA_Accepted_and_Adjusted_4b.size="10";
				cell20.appendChild(TDA_Accepted_and_Adjusted_4b);
				mycurrent_row.appendChild(cell20);

				/** 5a */
				var cel21 = document.createElement("TD");
				var TDA_Accepted_and_Adjusted_5a=document.createElement("input");
				TDA_Accepted_and_Adjusted_5a.type="text";
				TDA_Accepted_and_Adjusted_5a.name="TDA_Accepted_and_Adjusted_5a"+seq;
				TDA_Accepted_and_Adjusted_5a.id="TDA_Accepted_and_Adjusted_5a"+seq;
				TDA_Accepted_and_Adjusted_5a.value=item[9];
				TDA_Accepted_and_Adjusted_5a.size="10";
				cel21.appendChild(TDA_Accepted_and_Adjusted_5a);
				mycurrent_row.appendChild(cel21);

				/** 5b */
				var cel22 = document.createElement("TD");
				var TDA_Accepted_and_Adjusted_5b=document.createElement("input");
				TDA_Accepted_and_Adjusted_5b.type="text";
				TDA_Accepted_and_Adjusted_5b.name="TDA_Accepted_and_Adjusted_5b"+seq;
				TDA_Accepted_and_Adjusted_5b.id ="TDA_Accepted_and_Adjusted_5b"+seq;
				TDA_Accepted_and_Adjusted_5b.value=item[10]+"0";
				TDA_Accepted_and_Adjusted_5b.style.textAlign="right";
				TDA_Accepted_and_Adjusted_5b.size="10";
				cel22.appendChild(TDA_Accepted_and_Adjusted_5b);
				mycurrent_row.appendChild(cel22);

				/** 6a */
				var cel23 = document.createElement("TD");
				var Value_of_Jobs_in_Progress_6a=document.createElement("input");
				Value_of_Jobs_in_Progress_6a.type="text";
				Value_of_Jobs_in_Progress_6a.name="Value_of_Jobs_in_Progress_6a"+seq;
				Value_of_Jobs_in_Progress_6a.id="Value_of_Jobs_in_Progress_6a"+seq;
				Value_of_Jobs_in_Progress_6a.value=item[11];
				Value_of_Jobs_in_Progress_6a.size="10";
				cel23.appendChild(Value_of_Jobs_in_Progress_6a);
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);	

				/** 6b */
				var cell25 = document.createElement("TD");
				var Value_of_Jobs_in_Progress_6b=document.createElement("input");
				Value_of_Jobs_in_Progress_6b.type="text";
				Value_of_Jobs_in_Progress_6b.name="Value_of_Jobs_in_Progress_6b"+seq;
				Value_of_Jobs_in_Progress_6b.id="Value_of_Jobs_in_Progress_6b"+seq;
				Value_of_Jobs_in_Progress_6b.value=item[12]+"0";
				Value_of_Jobs_in_Progress_6b.style.textAlign="right";
				Value_of_Jobs_in_Progress_6b.size="10";
				cell25.appendChild(Value_of_Jobs_in_Progress_6b);
				mycurrent_row.appendChild(cell25);

				/** 7a */
				var cel26 = document.createElement("TD");
				var Value_of_Jobs_in_Progress_7a=document.createElement("input");
				Value_of_Jobs_in_Progress_7a.type="text";
				Value_of_Jobs_in_Progress_7a.name="Value_of_Jobs_in_Progress_7a"+seq;
				Value_of_Jobs_in_Progress_7a.id ="Value_of_Jobs_in_Progress_7a"+seq;
				Value_of_Jobs_in_Progress_7a.value=item[13];
				Value_of_Jobs_in_Progress_7a.size="10";
				cel26.appendChild(Value_of_Jobs_in_Progress_7a);
				mycurrent_row.appendChild(cel26);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[14]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_12A_Consolidation")
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
		alert("Civil Budget Format-12A Consolidation have Already Freezed");
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
					document.getElementById("frmCivil_Budget_Format_12A_Consolidation")
							.appendChild(slno_db1);
				} else {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", "-1");
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("frmCivil_Budget_Format_12A_Consolidation")
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

	document.frmCivil_Budget_Format_12A_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_12A_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_12A_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_12A_Consolidation.butUpdate.disabled = true;
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