//			Civil_Budget_Format_3_Consolidation			//
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

	document.frmCivil_Budget_Format_3_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_3_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_3_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_3_Consolidation.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_3_Consolidation?command=get&y1="
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
				item[0] = baseResponse.getElementsByTagName("Name_of_Category")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Time_Scale_of_Pay")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("No_of_Sanctioned_Posts")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("No_of_Incumbents_in_Roll")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("No_of_Vacant_Posts")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("Begining_of_the_Year_Basic_Pay")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Begining_of_the_Year_50_BP")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("Increment_Date")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("Increment_Amount")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("After_Increment_Basic_Pay")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("After_Increment_50_BP")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("Basic_Pay_After_Increment")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("Basic_Pay_50_Dearness_pay")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("DA_32_on_Col_No_13")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("Other_Allowances")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
				}
				item[15] = baseResponse
						.getElementsByTagName("Total")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="0";
				}
				item[16] = baseResponse
						.getElementsByTagName("Date_of_Retirement")[k].firstChild.nodeValue;
				if(item[16]=='null'){
					item[16]="";
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

				/** Name of Category */
				var cell2 = document.createElement("TD");
				var Name_of_Category = document.createElement("input");
				Name_of_Category.type = "text";
				Name_of_Category.name = "Name_of_Category" + seq;
				Name_of_Category.id = "Name_of_Category" + seq;
				Name_of_Category.value = item[0];	
				cell2.appendChild(Name_of_Category);
				mycurrent_row.appendChild(cell2);

				/** Time Scale of Pay (Specify O.G/S.G/Spl.Gr */
				var cell3 = document.createElement("TD");
				var Time_Scale_of_Pay = document.createElement("input");
				Time_Scale_of_Pay.type = "text";
				Time_Scale_of_Pay.name = "Time_Scale_of_Pay" + seq;
				Time_Scale_of_Pay.id = "Time_Scale_of_Pay" + seq;
				Time_Scale_of_Pay.value = item[1];
				cell3.appendChild(Time_Scale_of_Pay);
				mycurrent_row.appendChild(cell3);

				/** No of Sanctioned Posts */
				var cell4 = document.createElement("TD");
				var No_of_Sanctioned_Posts  = document.createElement("input");
				No_of_Sanctioned_Posts.type = "text";
				No_of_Sanctioned_Posts.name = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.id = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.value = item[2];
				No_of_Sanctioned_Posts.size = "10";
				cell4.appendChild(No_of_Sanctioned_Posts );
				mycurrent_row.appendChild(cell4);

				/** No of Incumbents in Roll */
				var cell5 = document.createElement("TD");
				var No_of_Incumbents_in_Roll = document.createElement("input");
				No_of_Incumbents_in_Roll.type = "text";
				No_of_Incumbents_in_Roll.name = "No_of_Incumbents_in_Roll" + seq;
				No_of_Incumbents_in_Roll.id = "No_of_Incumbents_in_Roll" + seq;
				No_of_Incumbents_in_Roll.value = item[3];
				No_of_Incumbents_in_Roll.size = "10";
				cell5.appendChild(No_of_Incumbents_in_Roll);
				mycurrent_row.appendChild(cell5);

				/** NO of Vacant Posts */
				var cell6 = document.createElement("TD");
				var No_of_Vacant_Posts = document.createElement("input");
				No_of_Vacant_Posts.type = "text";
				No_of_Vacant_Posts.name = "No_of_Vacant_Posts" + seq;
				No_of_Vacant_Posts.id = "No_of_Vacant_Posts" + seq;
				No_of_Vacant_Posts.value = item[4];
				No_of_Vacant_Posts.size = "10";
				cell6.appendChild(No_of_Vacant_Posts);
				mycurrent_row.appendChild(cell6);

				/** As on Begining of the Year - Basic Pay */
				var cell7 = document.createElement("TD");
				var Begining_of_the_Year_Basic_Pay = document.createElement("input");
				Begining_of_the_Year_Basic_Pay.type = "text";
				Begining_of_the_Year_Basic_Pay.name = "Begining_of_the_Year_Basic_Pay" + seq;
				Begining_of_the_Year_Basic_Pay.id = "Begining_of_the_Year_Basic_Pay" + seq;
				Begining_of_the_Year_Basic_Pay.value = item[5]+"0";
				Begining_of_the_Year_Basic_Pay.size = "10";
				Begining_of_the_Year_Basic_Pay.style.textAlign="right";
				cell7.appendChild(Begining_of_the_Year_Basic_Pay);
				mycurrent_row.appendChild(cell7);

				/** As on Begining of the Year - 50% B.P */
				var cell8 = document.createElement("TD");
				var Begining_of_the_Year_50_BP = document.createElement("input");
				Begining_of_the_Year_50_BP.type = "text";
				Begining_of_the_Year_50_BP.name = "Begining_of_the_Year_50_BP" + seq;
				Begining_of_the_Year_50_BP.id = "Begining_of_the_Year_50_BP" + seq;
				Begining_of_the_Year_50_BP.value = item[6]+"0";
				Begining_of_the_Year_50_BP.size = "10";	
				Begining_of_the_Year_50_BP.style.textAlign="right";
				cell8.appendChild(Begining_of_the_Year_50_BP);
				mycurrent_row.appendChild(cell8);

				/** Increment Date */
				var cell9 = document.createElement("TD");
				var Increment_Date = document.createElement("input");
				Increment_Date.type = "text";
				Increment_Date.name = "Increment_Date" + seq;
				Increment_Date.id = "Increment_Date" + seq;
				Increment_Date.value = item[7];
				Increment_Date.size = "10";					
				cell9.appendChild(Increment_Date);
				mycurrent_row.appendChild(cell9);
				
				/** Increment Amount */
				var cell99 = document.createElement("TD");
				var Increment_Amount = document.createElement("input");
				Increment_Amount.type = "text";
				Increment_Amount.name = "Increment_Amount" + seq;
				Increment_Amount.id = "Increment_Amount" + seq;
				Increment_Amount.value = item[8]+"0";
				Increment_Amount.size = "10";		
				Increment_Amount.style.textAlign="right";
				cell99.appendChild(Increment_Amount);
				mycurrent_row.appendChild(cell99);

				/** Pay After Increment - Basic Pay */
				var cell20 = document.createElement("TD");
				var After_Increment_Basic_Pay = document.createElement("input");
				After_Increment_Basic_Pay.type = "text";
				After_Increment_Basic_Pay.name = "After_Increment_Basic_Pay" + seq;
				After_Increment_Basic_Pay.id = "After_Increment_Basic_Pay" + seq;
				After_Increment_Basic_Pay.value = item[9]+"0";	
				After_Increment_Basic_Pay.size = "10";
				After_Increment_Basic_Pay.style.textAlign="right";
				cell20.appendChild(After_Increment_Basic_Pay);
				mycurrent_row.appendChild(cell20);

				/** Pay After Increment - 50% B.P */
				var cel21 = document.createElement("TD");
				var After_Increment_50_BP = document.createElement("input");
				After_Increment_50_BP.type = "text";
				After_Increment_50_BP.name = "After_Increment_50_BP" + seq;
				After_Increment_50_BP.id = "After_Increment_50_BP" + seq;
				After_Increment_50_BP.value = item[10]+"0";
				After_Increment_50_BP.size = "10";
				After_Increment_50_BP.style.textAlign="right";
				cel21.appendChild(After_Increment_50_BP);
				mycurrent_row.appendChild(cel21);

				/** Basic Pay After Increment */
				var cel22 = document.createElement("TD");
				var Basic_Pay_After_Increment  = document.createElement("input");
				Basic_Pay_After_Increment.type = "text";
				Basic_Pay_After_Increment.name = "Basic_Pay_After_Increment" + seq;
				Basic_Pay_After_Increment.id = "Basic_Pay_After_Increment" + seq;
				Basic_Pay_After_Increment.value = item[11]+"0";
				Basic_Pay_After_Increment.size = "10";
				Basic_Pay_After_Increment.style.textAlign="right";
				cel22.appendChild(Basic_Pay_After_Increment );
				mycurrent_row.appendChild(cel22);

				/**
				 * Total Basic Pay plus 50% Dearness pay for 12 Months During
				 * the Year
				 */
				var cel23 = document.createElement("TD");
				var Basic_Pay_50_Dearness_pay  = document.createElement("input");
				Basic_Pay_50_Dearness_pay.type = "text";
				Basic_Pay_50_Dearness_pay.name = "Basic_Pay_50_Dearness_pay" + seq;
				Basic_Pay_50_Dearness_pay.id = "Basic_Pay_50_Dearness_pay" + seq;
				Basic_Pay_50_Dearness_pay.value = item[12]+"0";
				Basic_Pay_50_Dearness_pay.size = "10";
				Basic_Pay_50_Dearness_pay.style.textAlign="right";
				cel23.appendChild(Basic_Pay_50_Dearness_pay );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** D.A @ 32% on Col.No.13 */
				var cel24 = document.createElement("TD");
				var DA_32_on_Col_No_13 = document.createElement("input");
				DA_32_on_Col_No_13.type = "text";
				DA_32_on_Col_No_13.name = "DA_32_on_Col_No_13" + seq;
				DA_32_on_Col_No_13.id = "DA_32_on_Col_No_13" + seq;
				DA_32_on_Col_No_13.value = item[13]+"0";
				DA_32_on_Col_No_13.style.textAlign="right";
				DA_32_on_Col_No_13.size = "10";
				cel24.appendChild(DA_32_on_Col_No_13);
				mycurrent_row.appendChild(cel24);

				/** Other Allowances for 12 Months */
				var cell25 = document.createElement("TD");
				var Other_Allowances = document.createElement("input");
				Other_Allowances.type = "text";
				Other_Allowances.name = "Other_Allowances" + seq;
				Other_Allowances.id = "Other_Allowances" + seq;
				Other_Allowances.value = item[14]+"0";
				Other_Allowances.style.textAlign="right";
				Other_Allowances.size = "10";
				cell25.appendChild(Other_Allowances);
				mycurrent_row.appendChild(cell25);

				/** Total (9+10+11) */
				var cel26 = document.createElement("TD");
				var Total  = document.createElement("input");
				Total.type = "text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				Total.value = item[15]+"0";
				Total.style.textAlign="right";
				Total.size = "10";
				cel26.appendChild(Total );
				mycurrent_row.appendChild(cel26);

				/** Date of Retirement During the Year */
				var cel27 = document.createElement("TD");
				var Date_of_Retirement  = document.createElement("input");
				Date_of_Retirement.type = "text";
				Date_of_Retirement.name = "Date_of_Retirement" + seq;
				Date_of_Retirement.id = "Date_of_Retirement" + seq;
				Date_of_Retirement.value = item[16];
				Date_of_Retirement.size = "10";
				cel27.appendChild(Date_of_Retirement);
				mycurrent_row.appendChild(cel27);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[18]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_3_Consolidation")
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

	document.frmCivil_Budget_Format_3_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_3_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_3_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_3_Consolidation.butUpdate.disabled = true;
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

	/** Name of Category */
	var cell2 = document.createElement("TD");
	var Name_of_Category = document.createElement("input");
	Name_of_Category.type = "text";
	Name_of_Category.name = "Name_of_Category" + seq;
	Name_of_Category.id = "Name_of_Category" + seq;
	Name_of_Category.value = "";	
	cell2.appendChild(Name_of_Category);
	mycurrent_row.appendChild(cell2);

	/** Time Scale of Pay (Specify O.G/S.G/Spl.Gr */
	var cell3 = document.createElement("TD");
	var Time_Scale_of_Pay = document.createElement("input");
	Time_Scale_of_Pay.type = "text";
	Time_Scale_of_Pay.name = "Time_Scale_of_Pay" + seq;
	Time_Scale_of_Pay.id = "Time_Scale_of_Pay" + seq;
	Time_Scale_of_Pay.value = "";
	cell3.appendChild(Time_Scale_of_Pay);
	mycurrent_row.appendChild(cell3);

	/** No of Sanctioned Posts */
	var cell4 = document.createElement("TD");
	var No_of_Sanctioned_Posts  = document.createElement("input");
	No_of_Sanctioned_Posts.type = "text";
	No_of_Sanctioned_Posts.name = "No_of_Sanctioned_Posts" + seq;
	No_of_Sanctioned_Posts.id = "No_of_Sanctioned_Posts" + seq;
	No_of_Sanctioned_Posts.value = "";
	No_of_Sanctioned_Posts.size = "10";
	cell4.appendChild(No_of_Sanctioned_Posts );
	mycurrent_row.appendChild(cell4);

	/** No of Incumbents in Roll */
	var cell5 = document.createElement("TD");
	var No_of_Incumbents_in_Roll = document.createElement("input");
	No_of_Incumbents_in_Roll.type = "text";
	No_of_Incumbents_in_Roll.name = "No_of_Incumbents_in_Roll" + seq;
	No_of_Incumbents_in_Roll.id = "No_of_Incumbents_in_Roll" + seq;
	No_of_Incumbents_in_Roll.value = "";
	No_of_Incumbents_in_Roll.size = "10";
	cell5.appendChild(No_of_Incumbents_in_Roll);
	mycurrent_row.appendChild(cell5);

	/** NO of Vacant Posts */
	var cell6 = document.createElement("TD");
	var No_of_Vacant_Posts = document.createElement("input");
	No_of_Vacant_Posts.type = "text";
	No_of_Vacant_Posts.name = "No_of_Vacant_Posts" + seq;
	No_of_Vacant_Posts.id = "No_of_Vacant_Posts" + seq;
	No_of_Vacant_Posts.value = "";
	No_of_Vacant_Posts.size = "10";
	cell6.appendChild(No_of_Vacant_Posts);
	mycurrent_row.appendChild(cell6);

	/** As on Begining of the Year - Basic Pay */
	var cell7 = document.createElement("TD");
	var Begining_of_the_Year_Basic_Pay = document.createElement("input");
	Begining_of_the_Year_Basic_Pay.type = "text";
	Begining_of_the_Year_Basic_Pay.name = "Begining_of_the_Year_Basic_Pay" + seq;
	Begining_of_the_Year_Basic_Pay.id = "Begining_of_the_Year_Basic_Pay" + seq;
	Begining_of_the_Year_Basic_Pay.value = "";
	Begining_of_the_Year_Basic_Pay.size = "10";
	cell7.appendChild(Begining_of_the_Year_Basic_Pay);
	mycurrent_row.appendChild(cell7);

	/** As on Begining of the Year - 50% B.P */
	var cell8 = document.createElement("TD");
	var Begining_of_the_Year_50_BP = document.createElement("input");
	Begining_of_the_Year_50_BP.type = "text";
	Begining_of_the_Year_50_BP.name = "Begining_of_the_Year_50_BP" + seq;
	Begining_of_the_Year_50_BP.id = "Begining_of_the_Year_50_BP" + seq;
	Begining_of_the_Year_50_BP.value = "";
	Begining_of_the_Year_50_BP.size = "10";	
	cell8.appendChild(Begining_of_the_Year_50_BP);
	mycurrent_row.appendChild(cell8);

	/** Increment Date */
	var cell9 = document.createElement("TD");
	var Increment_Date = document.createElement("input");
	Increment_Date.type = "text";
	Increment_Date.name = "Increment_Date" + seq;
	Increment_Date.id = "Increment_Date" + seq;
	Increment_Date.value = "";
	Increment_Date.size = "10";	
	cell9.appendChild(Increment_Date);
	mycurrent_row.appendChild(cell9);
	
	/** Increment Amount */
	var cell99 = document.createElement("TD");
	var Increment_Amount = document.createElement("input");
	Increment_Amount.type = "text";
	Increment_Amount.name = "Increment_Amount" + seq;
	Increment_Amount.id = "Increment_Amount" + seq;
	Increment_Amount.value = "";
	Increment_Amount.size = "10";		
	cell99.appendChild(Increment_Amount);
	mycurrent_row.appendChild(cell99);

	/** Pay After Increment - Basic Pay */
	var cell20 = document.createElement("TD");
	var After_Increment_Basic_Pay = document.createElement("input");
	After_Increment_Basic_Pay.type = "text";
	After_Increment_Basic_Pay.name = "After_Increment_Basic_Pay" + seq;
	After_Increment_Basic_Pay.id = "After_Increment_Basic_Pay" + seq;
	After_Increment_Basic_Pay.value = "";	
	After_Increment_Basic_Pay.size = "10";
	cell20.appendChild(After_Increment_Basic_Pay);
	mycurrent_row.appendChild(cell20);

	/** Pay After Increment - 50% B.P */
	var cel21 = document.createElement("TD");
	var After_Increment_50_BP = document.createElement("input");
	After_Increment_50_BP.type = "text";
	After_Increment_50_BP.name = "After_Increment_50_BP" + seq;
	After_Increment_50_BP.id = "After_Increment_50_BP" + seq;
	After_Increment_50_BP.value = "";
	After_Increment_50_BP.size = "10";
	cel21.appendChild(After_Increment_50_BP);
	mycurrent_row.appendChild(cel21);

	/** Basic Pay After Increment */
	var cel22 = document.createElement("TD");
	var Basic_Pay_After_Increment  = document.createElement("input");
	Basic_Pay_After_Increment.type = "text";
	Basic_Pay_After_Increment.name = "Basic_Pay_After_Increment" + seq;
	Basic_Pay_After_Increment.id = "Basic_Pay_After_Increment" + seq;
	Basic_Pay_After_Increment.value = "";
	Basic_Pay_After_Increment.size = "10";
	cel22.appendChild(Basic_Pay_After_Increment );
	mycurrent_row.appendChild(cel22);

	/** Total Basic Pay plus 50% Dearness pay for 12 Months During the Year */
	var cel23 = document.createElement("TD");
	var Basic_Pay_50_Dearness_pay  = document.createElement("input");
	Basic_Pay_50_Dearness_pay.type = "text";
	Basic_Pay_50_Dearness_pay.name = "Basic_Pay_50_Dearness_pay" + seq;
	Basic_Pay_50_Dearness_pay.id = "Basic_Pay_50_Dearness_pay" + seq;
	Basic_Pay_50_Dearness_pay.value = "";
	Basic_Pay_50_Dearness_pay.size = "10";
	cel23.appendChild(Basic_Pay_50_Dearness_pay );
	mycurrent_row.appendChild(cel23);
	tbody.appendChild(mycurrent_row);

	/** D.A @ 32% on Col.No.13 */
	var cel24 = document.createElement("TD");
	var DA_32_on_Col_No_13 = document.createElement("input");
	DA_32_on_Col_No_13.type = "text";
	DA_32_on_Col_No_13.name = "DA_32_on_Col_No_13" + seq;
	DA_32_on_Col_No_13.id = "DA_32_on_Col_No_13" + seq;
	DA_32_on_Col_No_13.value = "";
	DA_32_on_Col_No_13.size = "10";
	cel24.appendChild(DA_32_on_Col_No_13);
	mycurrent_row.appendChild(cel24);

	/** Other Allowances for 12 Months */
	var cell25 = document.createElement("TD");
	var Other_Allowances = document.createElement("input");
	Other_Allowances.type = "text";
	Other_Allowances.name = "Other_Allowances" + seq;
	Other_Allowances.id = "Other_Allowances" + seq;
	Other_Allowances.value = "";
	Other_Allowances.size = "10";
	cell25.appendChild(Other_Allowances);
	mycurrent_row.appendChild(cell25);

	/** Total (9+10+11) */
	var cel26 = document.createElement("TD");
	var Total  = document.createElement("input");
	Total.type = "text";
	Total.name = "Total" + seq;
	Total.id = "Total" + seq;
	Total.value = "";
	Total.size = "10";
	cel26.appendChild(Total );
	mycurrent_row.appendChild(cel26);

	/** Date of Retirement During the Year */
	var cel27 = document.createElement("TD");
	var Date_of_Retirement  = document.createElement("input");
	Date_of_Retirement.type = "text";
	Date_of_Retirement.name = "Date_of_Retirement" + seq;
	Date_of_Retirement.id = "Date_of_Retirement" + seq;
	Date_of_Retirement.value = "";
	Date_of_Retirement.size = "10";
	cel27.appendChild(Date_of_Retirement);
	mycurrent_row.appendChild(cel27);

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
	document.frmCivil_Budget_Format_3_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_3_Consolidation.butSub.disabled = true;
	document.frmCivil_Budget_Format_3_Consolidation.butDelete.disabled = false;
	document.frmCivil_Budget_Format_3_Consolidation.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;

	var url = path + "/Civil_Budget_Format_3_Consolidation?filter=view&cmbFinancialYear="
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
				item[0] = baseResponse.getElementsByTagName("Name_of_Category")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("Time_Scale_of_Pay")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("No_of_Sanctioned_Posts")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("No_of_Incumbents_in_Roll")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("No_of_Vacant_Posts")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("Begining_of_the_Year_Basic_Pay")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Begining_of_the_Year_50_BP")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("Increment_Date")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="";
				}
				item[8] = baseResponse.getElementsByTagName("Increment_Amount")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("After_Increment_Basic_Pay")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("After_Increment_50_BP")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("Basic_Pay_After_Increment")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("Basic_Pay_50_Dearness_pay")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("DA_32_on_Col_No_13")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("Other_Allowances")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
				}
				item[15] = baseResponse
						.getElementsByTagName("Total")[k].firstChild.nodeValue;
				if(item[15]=='null'){
					item[15]="0";
				}
				item[16] = baseResponse
						.getElementsByTagName("Date_of_Retirement")[k].firstChild.nodeValue;
				if(item[16]=='null'){
					item[16]="";
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

				/** Name of Category */
				var cell2 = document.createElement("TD");
				var Name_of_Category = document.createElement("input");
				Name_of_Category.type = "text";
				Name_of_Category.name = "Name_of_Category" + seq;
				Name_of_Category.id = "Name_of_Category" + seq;
				Name_of_Category.value = item[0];	
				cell2.appendChild(Name_of_Category);
				mycurrent_row.appendChild(cell2);

				/** Time Scale of Pay (Specify O.G/S.G/Spl.Gr */
				var cell3 = document.createElement("TD");
				var Time_Scale_of_Pay = document.createElement("input");
				Time_Scale_of_Pay.type = "text";
				Time_Scale_of_Pay.name = "Time_Scale_of_Pay" + seq;
				Time_Scale_of_Pay.id = "Time_Scale_of_Pay" + seq;
				Time_Scale_of_Pay.value = item[1];
				cell3.appendChild(Time_Scale_of_Pay);
				mycurrent_row.appendChild(cell3);

				/** No of Sanctioned Posts */
				var cell4 = document.createElement("TD");
				var No_of_Sanctioned_Posts  = document.createElement("input");
				No_of_Sanctioned_Posts.type = "text";
				No_of_Sanctioned_Posts.name = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.id = "No_of_Sanctioned_Posts" + seq;
				No_of_Sanctioned_Posts.value = item[2];
				No_of_Sanctioned_Posts.size = "10";
				cell4.appendChild(No_of_Sanctioned_Posts );
				mycurrent_row.appendChild(cell4);

				/** No of Incumbents in Roll */
				var cell5 = document.createElement("TD");
				var No_of_Incumbents_in_Roll = document.createElement("input");
				No_of_Incumbents_in_Roll.type = "text";
				No_of_Incumbents_in_Roll.name = "No_of_Incumbents_in_Roll" + seq;
				No_of_Incumbents_in_Roll.id = "No_of_Incumbents_in_Roll" + seq;
				No_of_Incumbents_in_Roll.value = item[3];
				No_of_Incumbents_in_Roll.size = "10";
				cell5.appendChild(No_of_Incumbents_in_Roll);
				mycurrent_row.appendChild(cell5);

				/** NO of Vacant Posts */
				var cell6 = document.createElement("TD");
				var No_of_Vacant_Posts = document.createElement("input");
				No_of_Vacant_Posts.type = "text";
				No_of_Vacant_Posts.name = "No_of_Vacant_Posts" + seq;
				No_of_Vacant_Posts.id = "No_of_Vacant_Posts" + seq;
				No_of_Vacant_Posts.value = item[4];
				No_of_Vacant_Posts.size = "10";
				cell6.appendChild(No_of_Vacant_Posts);
				mycurrent_row.appendChild(cell6);

				/** As on Begining of the Year - Basic Pay */
				var cell7 = document.createElement("TD");
				var Begining_of_the_Year_Basic_Pay = document.createElement("input");
				Begining_of_the_Year_Basic_Pay.type = "text";
				Begining_of_the_Year_Basic_Pay.name = "Begining_of_the_Year_Basic_Pay" + seq;
				Begining_of_the_Year_Basic_Pay.id = "Begining_of_the_Year_Basic_Pay" + seq;
				Begining_of_the_Year_Basic_Pay.value = item[5]+"0";
				Begining_of_the_Year_Basic_Pay.size = "10";
				Begining_of_the_Year_Basic_Pay.style.textAlign="right";
				cell7.appendChild(Begining_of_the_Year_Basic_Pay);
				mycurrent_row.appendChild(cell7);

				/** As on Begining of the Year - 50% B.P */
				var cell8 = document.createElement("TD");
				var Begining_of_the_Year_50_BP = document.createElement("input");
				Begining_of_the_Year_50_BP.type = "text";
				Begining_of_the_Year_50_BP.name = "Begining_of_the_Year_50_BP" + seq;
				Begining_of_the_Year_50_BP.id = "Begining_of_the_Year_50_BP" + seq;
				Begining_of_the_Year_50_BP.value = item[6]+"0";
				Begining_of_the_Year_50_BP.size = "10";	
				Begining_of_the_Year_50_BP.style.textAlign="right";
				cell8.appendChild(Begining_of_the_Year_50_BP);
				mycurrent_row.appendChild(cell8);

				/** Increment Date */
				var cell9 = document.createElement("TD");
				var Increment_Date = document.createElement("input");
				Increment_Date.type = "text";
				Increment_Date.name = "Increment_Date" + seq;
				Increment_Date.id = "Increment_Date" + seq;
				Increment_Date.value = item[7];
				Increment_Date.size = "10";					
				cell9.appendChild(Increment_Date);
				mycurrent_row.appendChild(cell9);
				
				/** Increment Amount */
				var cell99 = document.createElement("TD");
				var Increment_Amount = document.createElement("input");
				Increment_Amount.type = "text";
				Increment_Amount.name = "Increment_Amount" + seq;
				Increment_Amount.id = "Increment_Amount" + seq;
				Increment_Amount.value = item[8]+"0";
				Increment_Amount.size = "10";		
				Increment_Amount.style.textAlign="right";
				cell99.appendChild(Increment_Amount);
				mycurrent_row.appendChild(cell99);

				/** Pay After Increment - Basic Pay */
				var cell20 = document.createElement("TD");
				var After_Increment_Basic_Pay = document.createElement("input");
				After_Increment_Basic_Pay.type = "text";
				After_Increment_Basic_Pay.name = "After_Increment_Basic_Pay" + seq;
				After_Increment_Basic_Pay.id = "After_Increment_Basic_Pay" + seq;
				After_Increment_Basic_Pay.value = item[9]+"0";	
				After_Increment_Basic_Pay.size = "10";
				After_Increment_Basic_Pay.style.textAlign="right";
				cell20.appendChild(After_Increment_Basic_Pay);
				mycurrent_row.appendChild(cell20);

				/** Pay After Increment - 50% B.P */
				var cel21 = document.createElement("TD");
				var After_Increment_50_BP = document.createElement("input");
				After_Increment_50_BP.type = "text";
				After_Increment_50_BP.name = "After_Increment_50_BP" + seq;
				After_Increment_50_BP.id = "After_Increment_50_BP" + seq;
				After_Increment_50_BP.value = item[10]+"0";
				After_Increment_50_BP.size = "10";
				After_Increment_50_BP.style.textAlign="right";
				cel21.appendChild(After_Increment_50_BP);
				mycurrent_row.appendChild(cel21);

				/** Basic Pay After Increment */
				var cel22 = document.createElement("TD");
				var Basic_Pay_After_Increment  = document.createElement("input");
				Basic_Pay_After_Increment.type = "text";
				Basic_Pay_After_Increment.name = "Basic_Pay_After_Increment" + seq;
				Basic_Pay_After_Increment.id = "Basic_Pay_After_Increment" + seq;
				Basic_Pay_After_Increment.value = item[11]+"0";
				Basic_Pay_After_Increment.size = "10";
				Basic_Pay_After_Increment.style.textAlign="right";
				cel22.appendChild(Basic_Pay_After_Increment );
				mycurrent_row.appendChild(cel22);

				/**
				 * Total Basic Pay plus 50% Dearness pay for 12 Months During
				 * the Year
				 */
				var cel23 = document.createElement("TD");
				var Basic_Pay_50_Dearness_pay  = document.createElement("input");
				Basic_Pay_50_Dearness_pay.type = "text";
				Basic_Pay_50_Dearness_pay.name = "Basic_Pay_50_Dearness_pay" + seq;
				Basic_Pay_50_Dearness_pay.id = "Basic_Pay_50_Dearness_pay" + seq;
				Basic_Pay_50_Dearness_pay.value = item[12]+"0";
				Basic_Pay_50_Dearness_pay.size = "10";
				Basic_Pay_50_Dearness_pay.style.textAlign="right";
				cel23.appendChild(Basic_Pay_50_Dearness_pay );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/** D.A @ 32% on Col.No.13 */
				var cel24 = document.createElement("TD");
				var DA_32_on_Col_No_13 = document.createElement("input");
				DA_32_on_Col_No_13.type = "text";
				DA_32_on_Col_No_13.name = "DA_32_on_Col_No_13" + seq;
				DA_32_on_Col_No_13.id = "DA_32_on_Col_No_13" + seq;
				DA_32_on_Col_No_13.value = item[13]+"0";
				DA_32_on_Col_No_13.style.textAlign="right";
				DA_32_on_Col_No_13.size = "10";
				cel24.appendChild(DA_32_on_Col_No_13);
				mycurrent_row.appendChild(cel24);

				/** Other Allowances for 12 Months */
				var cell25 = document.createElement("TD");
				var Other_Allowances = document.createElement("input");
				Other_Allowances.type = "text";
				Other_Allowances.name = "Other_Allowances" + seq;
				Other_Allowances.id = "Other_Allowances" + seq;
				Other_Allowances.value = item[14]+"0";
				Other_Allowances.style.textAlign="right";
				Other_Allowances.size = "10";
				cell25.appendChild(Other_Allowances);
				mycurrent_row.appendChild(cell25);

				/** Total (9+10+11) */
				var cel26 = document.createElement("TD");
				var Total  = document.createElement("input");
				Total.type = "text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				Total.value = item[15]+"0";
				Total.style.textAlign="right";
				Total.size = "10";
				cel26.appendChild(Total );
				mycurrent_row.appendChild(cel26);

				/** Date of Retirement During the Year */
				var cel27 = document.createElement("TD");
				var Date_of_Retirement  = document.createElement("input");
				Date_of_Retirement.type = "text";
				Date_of_Retirement.name = "Date_of_Retirement" + seq;
				Date_of_Retirement.id = "Date_of_Retirement" + seq;
				Date_of_Retirement.value = item[16];
				Date_of_Retirement.size = "10";
				cel27.appendChild(Date_of_Retirement);
				mycurrent_row.appendChild(cel27);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[18]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_3_Consolidation")
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
		alert("Civil Budget Format-3 Consolidation have Already Freezed");
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
					document.getElementById("frmCivil_Budget_Format_3_Consolidation")
							.appendChild(slno_db1);
				} else {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", "-1");
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("frmCivil_Budget_Format_3_Consolidation")
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

	document.frmCivil_Budget_Format_3_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_3_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_3_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_3_Consolidation.butUpdate.disabled = true;
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