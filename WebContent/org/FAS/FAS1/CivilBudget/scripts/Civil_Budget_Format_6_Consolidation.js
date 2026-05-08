//		Civil_Budget_Format_6_Consolidation		//
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

	document.frmCivil_Budget_Format_6_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_6_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_6_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_6_Consolidation.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_6_Consolidation?command=get&y1="
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
				item[0] = baseResponse.getElementsByTagName("Category")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("No_of_Pensioners")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Total_Basic_Pension_Upto_11Y")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("Total_Basic_Pension_Anticipated_12Y_3Y1")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("Total_D_A_Upto_11Y")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("Total_D_A_Anticipated_12Y_3Y1")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Total_Other_Payment_Upto_11Y")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("Total_Other_Payment_Anticipated_12Y_3Y1")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="0";
				}
				item[8] = baseResponse.getElementsByTagName("Total_Upto_11Y")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("Total_Anticipated_12Y_3Y1")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("No_of_Pensioners1")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("Total_Basic_Pension")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("Total_D_A")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("Total_Other_Payment")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("Total")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
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
				var cell2 = document.createElement("TD");
				var slno = document.createElement("input");
				slno.type = "text";
				slno.name = "slno" + seq;
				slno.id = "slno" + seq;
				slno.value = seq+1;	
				slno.size = "2";
				cell2.appendChild(slno);
				mycurrent_row.appendChild(cell2);

				/** Category */
				var cell3 = document.createElement("TD");
				var Category = document.createElement("input");
				Category.type = "text";
				Category.name = "Category" + seq;
				Category.id = "Category" + seq;
				Category.value = item[0];
				cell3.appendChild(Category);
				mycurrent_row.appendChild(cell3);

				/** No of Pensioners */
				var cell4 = document.createElement("TD");
				var No_of_Pensioners  = document.createElement("input");
				No_of_Pensioners.type = "text";
				No_of_Pensioners.name = "No_of_Pensioners" + seq;
				No_of_Pensioners.id = "No_of_Pensioners" + seq;
				No_of_Pensioners.value = item[1];
				No_of_Pensioners.size = "3";
				cell4.appendChild(No_of_Pensioners);
				mycurrent_row.appendChild(cell4);

				/** Total Basic Pension-Upto 11/yyyy */
				var cell5 = document.createElement("TD");
				var Total_Basic_Pension_Upto_11Y = document.createElement("input");
				Total_Basic_Pension_Upto_11Y.type = "text";
				Total_Basic_Pension_Upto_11Y.name = "Total_Basic_Pension_Upto_11Y" + seq;
				Total_Basic_Pension_Upto_11Y.id = "Total_Basic_Pension_Upto_11Y" + seq;
				Total_Basic_Pension_Upto_11Y.value = item[2]+".00";
				Total_Basic_Pension_Upto_11Y.size = "10";
				Total_Basic_Pension_Upto_11Y.style.textAlign="right";
				cell5.appendChild(Total_Basic_Pension_Upto_11Y);
				mycurrent_row.appendChild(cell5);

				/** Total Basic Pension-Anticipated 12/yyyy to 3/yyyy+1 */
				var cell6 = document.createElement("TD");
				var Total_Basic_Pension_Anticipated_12Y_3Y1 = document.createElement("input");
				Total_Basic_Pension_Anticipated_12Y_3Y1.type = "text";
				Total_Basic_Pension_Anticipated_12Y_3Y1.name = "Total_Basic_Pension_Anticipated_12Y_3Y1" + seq;
				Total_Basic_Pension_Anticipated_12Y_3Y1.id = "Total_Basic_Pension_Anticipated_12Y_3Y1" + seq;
				Total_Basic_Pension_Anticipated_12Y_3Y1.value = item[3]+".00";
				Total_Basic_Pension_Anticipated_12Y_3Y1.size = "10";
				Total_Basic_Pension_Anticipated_12Y_3Y1.style.textAlign="right";
				cell6.appendChild(Total_Basic_Pension_Anticipated_12Y_3Y1);
				mycurrent_row.appendChild(cell6);

				/** Total D.A-Upto 11/yyyy */
				var cell7 = document.createElement("TD");
				var Total_D_A_Upto_11Y = document.createElement("input");
				Total_D_A_Upto_11Y.type = "text";
				Total_D_A_Upto_11Y.name = "Total_D_A_Upto_11Y" + seq;
				Total_D_A_Upto_11Y.id = "Total_D_A_Upto_11Y" + seq;
				Total_D_A_Upto_11Y.value = item[4]+".00";
				Total_D_A_Upto_11Y.size = "10";
				Total_D_A_Upto_11Y.style.textAlign="right";
				cell7.appendChild(Total_D_A_Upto_11Y);
				mycurrent_row.appendChild(cell7);

				/** Total D.A-Anticipated 12/yyyy to 3/yyyy+1 */
				var cell8 = document.createElement("TD");
				var Total_D_A_Anticipated_12Y_3Y1 = document.createElement("input");
				Total_D_A_Anticipated_12Y_3Y1.type = "text";
				Total_D_A_Anticipated_12Y_3Y1.name = "Total_D_A_Anticipated_12Y_3Y1" + seq;
				Total_D_A_Anticipated_12Y_3Y1.id = "Total_D_A_Anticipated_12Y_3Y1" + seq;
				Total_D_A_Anticipated_12Y_3Y1.value = item[5]+".00";
				Total_D_A_Anticipated_12Y_3Y1.size = "10";	
				Total_D_A_Anticipated_12Y_3Y1.style.textAlign="right";
				cell8.appendChild(Total_D_A_Anticipated_12Y_3Y1);
				mycurrent_row.appendChild(cell8);

				/** Total Other Payment-Upto 11/yyyy */
				var cell9 = document.createElement("TD");
				var Total_Other_Payment_Upto_11Y = document.createElement("input");
				Total_Other_Payment_Upto_11Y.type = "text";
				Total_Other_Payment_Upto_11Y.name = "Total_Other_Payment_Upto_11Y" + seq;
				Total_Other_Payment_Upto_11Y.id = "Total_Other_Payment_Upto_11Y" + seq;
				Total_Other_Payment_Upto_11Y.value = item[6]+".00";
				Total_Other_Payment_Upto_11Y.size = "10";	
				Total_Other_Payment_Upto_11Y.style.textAlign="right";
				cell9.appendChild(Total_Other_Payment_Upto_11Y);
				mycurrent_row.appendChild(cell9);

				/** Total Other Payment -Anticipated 12/yyyy to 3/yyyy+1 */
				var cell20 = document.createElement("TD");
				var Total_Other_Payment_Anticipated_12Y_3Y1 = document.createElement("input");
				Total_Other_Payment_Anticipated_12Y_3Y1.type = "text";
				Total_Other_Payment_Anticipated_12Y_3Y1.name = "Total_Other_Payment_Anticipated_12Y_3Y1" + seq;
				Total_Other_Payment_Anticipated_12Y_3Y1.id = "Total_Other_Payment_Anticipated_12Y_3Y1" + seq;
				Total_Other_Payment_Anticipated_12Y_3Y1.value = item[7]+".00";
				Total_Other_Payment_Anticipated_12Y_3Y1.size = "10";
				Total_Other_Payment_Anticipated_12Y_3Y1.style.textAlign="right";
				cell20.appendChild(Total_Other_Payment_Anticipated_12Y_3Y1);
				mycurrent_row.appendChild(cell20);

				/** Total Upto 11/yyyy (4+6+8) */
				var cel21 = document.createElement("TD");
				var Total_Upto_11Y = document.createElement("input");
				Total_Upto_11Y.type = "text";
				Total_Upto_11Y.name = "Total_Upto_11Y" + seq;
				Total_Upto_11Y.id = "Total_Upto_11Y" + seq;
				Total_Upto_11Y.value = item[8]+".00";
				Total_Upto_11Y.size = "10";
				Total_Upto_11Y.style.textAlign="right";
				cel21.appendChild(Total_Upto_11Y);
				mycurrent_row.appendChild(cel21);

				/** Total - Anticipated 12/yyyy to 3/yyyy+1 (5+7+9) */
				var cel22 = document.createElement("TD");
				var Total_Anticipated_12Y_3Y1  = document.createElement("input");
				Total_Anticipated_12Y_3Y1.type = "text";
				Total_Anticipated_12Y_3Y1.name = "Total_Anticipated_12Y_3Y1" + seq;
				Total_Anticipated_12Y_3Y1.id = "Total_Anticipated_12Y_3Y1" + seq;
				Total_Anticipated_12Y_3Y1.value = item[9]+".00";
				Total_Anticipated_12Y_3Y1.size = "10";
				Total_Anticipated_12Y_3Y1.style.textAlign="right";
				cel22.appendChild(Total_Anticipated_12Y_3Y1);
				mycurrent_row.appendChild(cel22);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)-No of Pensioners
				 */
				var cel23 = document.createElement("TD");
				var No_of_Pensioners1  = document.createElement("input");
				No_of_Pensioners1.type = "text";
				No_of_Pensioners1.name = "No_of_Pensioners1" + seq;
				No_of_Pensioners1.id = "No_of_Pensioners1" + seq;
				No_of_Pensioners1.value = item[10];
				No_of_Pensioners1.size = "3";
				cel23.appendChild(No_of_Pensioners1 );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)-Total Basic Pension
				 */
				var cel24 = document.createElement("TD");
				var Total_Basic_Pension = document.createElement("input");
				Total_Basic_Pension.type = "text";
				Total_Basic_Pension.name = "Total_Basic_Pension" + seq;
				Total_Basic_Pension.id = "Total_Basic_Pension" + seq;
				Total_Basic_Pension.value = item[11]+".00";
				Total_Basic_Pension.size = "10";
				Total_Basic_Pension.style.textAlign="right";
				cel24.appendChild(Total_Basic_Pension);
				mycurrent_row.appendChild(cel24);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)- Total D.A
				 */
				var cell25 = document.createElement("TD");
				var Total_D_A = document.createElement("input");
				Total_D_A.type = "text";
				Total_D_A.name = "Total_D_A" + seq;
				Total_D_A.id = "Total_D_A" + seq;
				Total_D_A.value = item[12]+".00";
				Total_D_A.size = "10";
				Total_D_A.style.textAlign="right";
				cell25.appendChild(Total_D_A);
				mycurrent_row.appendChild(cell25);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)-Total Other Payment
				 */
				var cel26 = document.createElement("TD");
				var Total_Other_Payment  = document.createElement("input");
				Total_Other_Payment.type = "text";
				Total_Other_Payment.name = "Total_Other_Payment" + seq;
				Total_Other_Payment.id = "Total_Other_Payment" + seq;
				Total_Other_Payment.value = item[13]+".00";
				Total_Other_Payment.size = "10";
				Total_Other_Payment.style.textAlign="right";
				cel26.appendChild(Total_Other_Payment);
				mycurrent_row.appendChild(cel26);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)-Total
				 */
				var cel27 = document.createElement("TD");
				var Total  = document.createElement("input");
				Total.type = "text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				Total.value = item[14]+".00";
				Total.size = "10";
				Total.style.textAlign="right";
				cel27.appendChild(Total);
				mycurrent_row.appendChild(cel27);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[18]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_6_Consolidation")
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
		document.getElementById("l1").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year1))+ '</font>';
		document.getElementById("l2").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year1)) + " to 3/"+(parseInt(year))+ '</font>';
		document.getElementById("l3").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year1))+ '</font>';
		document.getElementById("l4").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year1)) + " to 3/"+(parseInt(year))+ '</font>';
		document.getElementById("l5").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year1))+ '</font>';
		document.getElementById("l6").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year1)) + " to 3/"+(parseInt(year))+ '</font>';
		document.getElementById("l7").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year1))+ '</font>';
		document.getElementById("l8").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year1)) + " to 3/"+(parseInt(year))+ '</font>';
	} else {
		financialyear = year + "-" + year1;
		financialyear1 = (parseInt(year)-1) + "-" + (parseInt(year1)-1);
		financialyear2 = (parseInt(year)-2) + "-" + (parseInt(year1)-2);
		document.getElementById("l1").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year))+ '</font>';
		document.getElementById("l2").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year)) + " to 3/"+(parseInt(year1))+ '</font>';
		document.getElementById("l3").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year))+ '</font>';
		document.getElementById("l4").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year)) + " to 3/"+(parseInt(year1))+ '</font>';
		document.getElementById("l5").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year))+ '</font>';
		document.getElementById("l6").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year)) + " to 3/"+(parseInt(year1))+ '</font>';
		document.getElementById("l7").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year))+ '</font>';
		document.getElementById("l8").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year)) + " to 3/"+(parseInt(year1))+ '</font>';
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

	document.frmCivil_Budget_Format_6_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_6_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_6_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_6_Consolidation.butUpdate.disabled = true;
}

function ChangeYearDuration()
{
	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1=fy.split('-');
	var year1 = fy1[0];
	var year = fy1[1];
	
	document.getElementById("l1").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year1))+ '</font>';
	document.getElementById("l2").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year1)) + " to 3/"+(parseInt(year))+ '</font>';
	document.getElementById("l3").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year1))+ '</font>';
	document.getElementById("l4").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year1)) + " to 3/"+(parseInt(year))+ '</font>';
	document.getElementById("l5").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year1))+ '</font>';
	document.getElementById("l6").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year1)) + " to 3/"+(parseInt(year))+ '</font>';
	document.getElementById("l7").innerHTML='<font color="#FF0000">'+" 11/"+(parseInt(year1))+ '</font>';
	document.getElementById("l8").innerHTML='<font color="#FF0000">'+" 12/"+(parseInt(year1)) + " to 3/"+(parseInt(year))+ '</font>';
			
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
	var cell2 = document.createElement("TD");
	var slno = document.createElement("input");
	slno.type = "text";
	slno.name = "slno" + seq;
	slno.id = "slno" + seq;
	slno.value = seq+1;	
	slno.size = "2";
	cell2.appendChild(slno);
	mycurrent_row.appendChild(cell2);

	/** Category */
	var cell3 = document.createElement("TD");
	var Category = document.createElement("input");
	Category.type = "text";
	Category.name = "Category" + seq;
	Category.id = "Category" + seq;
	Category.value = "";
	cell3.appendChild(Category);
	mycurrent_row.appendChild(cell3);

	/** No of Pensioners */
	var cell4 = document.createElement("TD");
	var No_of_Pensioners  = document.createElement("input");
	No_of_Pensioners.type = "text";
	No_of_Pensioners.name = "No_of_Pensioners" + seq;
	No_of_Pensioners.id = "No_of_Pensioners" + seq;
	No_of_Pensioners.value = "";
	No_of_Pensioners.size = "3";
	cell4.appendChild(No_of_Pensioners);
	mycurrent_row.appendChild(cell4);

	/** Total Basic Pension-Upto 11/yyyy */
	var cell5 = document.createElement("TD");
	var Total_Basic_Pension_Upto_11Y = document.createElement("input");
	Total_Basic_Pension_Upto_11Y.type = "text";
	Total_Basic_Pension_Upto_11Y.name = "Total_Basic_Pension_Upto_11Y" + seq;
	Total_Basic_Pension_Upto_11Y.id = "Total_Basic_Pension_Upto_11Y" + seq;
	Total_Basic_Pension_Upto_11Y.value = "";
	Total_Basic_Pension_Upto_11Y.size = "10";
	cell5.appendChild(Total_Basic_Pension_Upto_11Y);
	mycurrent_row.appendChild(cell5);

	/** Total Basic Pension-Anticipated 12/yyyy to 3/yyyy+1 */
	var cell6 = document.createElement("TD");
	var Total_Basic_Pension_Anticipated_12Y_3Y1 = document.createElement("input");
	Total_Basic_Pension_Anticipated_12Y_3Y1.type = "text";
	Total_Basic_Pension_Anticipated_12Y_3Y1.name = "Total_Basic_Pension_Anticipated_12Y_3Y1" + seq;
	Total_Basic_Pension_Anticipated_12Y_3Y1.id = "Total_Basic_Pension_Anticipated_12Y_3Y1" + seq;
	Total_Basic_Pension_Anticipated_12Y_3Y1.value = "";
	Total_Basic_Pension_Anticipated_12Y_3Y1.size = "10";
	cell6.appendChild(Total_Basic_Pension_Anticipated_12Y_3Y1);
	mycurrent_row.appendChild(cell6);

	/** Total D.A-Upto 11/yyyy */
	var cell7 = document.createElement("TD");
	var Total_D_A_Upto_11Y = document.createElement("input");
	Total_D_A_Upto_11Y.type = "text";
	Total_D_A_Upto_11Y.name = "Total_D_A_Upto_11Y" + seq;
	Total_D_A_Upto_11Y.id = "Total_D_A_Upto_11Y" + seq;
	Total_D_A_Upto_11Y.value = "";
	Total_D_A_Upto_11Y.size = "10";
	cell7.appendChild(Total_D_A_Upto_11Y);
	mycurrent_row.appendChild(cell7);

	/** Total D.A-Anticipated 12/yyyy to 3/yyyy+1 */
	var cell8 = document.createElement("TD");
	var Total_D_A_Anticipated_12Y_3Y1 = document.createElement("input");
	Total_D_A_Anticipated_12Y_3Y1.type = "text";
	Total_D_A_Anticipated_12Y_3Y1.name = "Total_D_A_Anticipated_12Y_3Y1" + seq;
	Total_D_A_Anticipated_12Y_3Y1.id = "Total_D_A_Anticipated_12Y_3Y1" + seq;
	Total_D_A_Anticipated_12Y_3Y1.value = "";
	Total_D_A_Anticipated_12Y_3Y1.size = "10";	
	cell8.appendChild(Total_D_A_Anticipated_12Y_3Y1);
	mycurrent_row.appendChild(cell8);

	/** Total Other Payment-Upto 11/yyyy */
	var cell9 = document.createElement("TD");
	var Total_Other_Payment_Upto_11Y = document.createElement("input");
	Total_Other_Payment_Upto_11Y.type = "text";
	Total_Other_Payment_Upto_11Y.name = "Total_Other_Payment_Upto_11Y" + seq;
	Total_Other_Payment_Upto_11Y.id = "Total_Other_Payment_Upto_11Y" + seq;
	Total_Other_Payment_Upto_11Y.value = "";
	Total_Other_Payment_Upto_11Y.size = "10";	
	cell9.appendChild(Total_Other_Payment_Upto_11Y);
	mycurrent_row.appendChild(cell9);

	/** Total Other Payment -Anticipated 12/yyyy to 3/yyyy+1 */
	var cell20 = document.createElement("TD");
	var Total_Other_Payment_Anticipated_12Y_3Y1 = document.createElement("input");
	Total_Other_Payment_Anticipated_12Y_3Y1.type = "text";
	Total_Other_Payment_Anticipated_12Y_3Y1.name = "Total_Other_Payment_Anticipated_12Y_3Y1" + seq;
	Total_Other_Payment_Anticipated_12Y_3Y1.id = "Total_Other_Payment_Anticipated_12Y_3Y1" + seq;
	Total_Other_Payment_Anticipated_12Y_3Y1.value = "";
	Total_Other_Payment_Anticipated_12Y_3Y1.size = "10";
	cell20.appendChild(Total_Other_Payment_Anticipated_12Y_3Y1);
	mycurrent_row.appendChild(cell20);

	/** Total Upto 11/yyyy (4+6+8) */
	var cel21 = document.createElement("TD");
	var Total_Upto_11Y = document.createElement("input");
	Total_Upto_11Y.type = "text";
	Total_Upto_11Y.name = "Total_Upto_11Y" + seq;
	Total_Upto_11Y.id = "Total_Upto_11Y" + seq;
	Total_Upto_11Y.value = "";
	Total_Upto_11Y.size = "10";
	cel21.appendChild(Total_Upto_11Y);
	mycurrent_row.appendChild(cel21);

	/** Total - Anticipated 12/yyyy to 3/yyyy+1 (5+7+9) */
	var cel22 = document.createElement("TD");
	var Total_Anticipated_12Y_3Y1  = document.createElement("input");
	Total_Anticipated_12Y_3Y1.type = "text";
	Total_Anticipated_12Y_3Y1.name = "Total_Anticipated_12Y_3Y1" + seq;
	Total_Anticipated_12Y_3Y1.id = "Total_Anticipated_12Y_3Y1" + seq;
	Total_Anticipated_12Y_3Y1.value = "";
	Total_Anticipated_12Y_3Y1.size = "10";
	cel22.appendChild(Total_Anticipated_12Y_3Y1);
	mycurrent_row.appendChild(cel22);

	/**
	 * Addition During yyyy-yyyy+1 with Reference to Statement 3(Col.14)-No of
	 * Pensioners
	 */
	var cel23 = document.createElement("TD");
	var No_of_Pensioners1  = document.createElement("input");
	No_of_Pensioners1.type = "text";
	No_of_Pensioners1.name = "No_of_Pensioners1" + seq;
	No_of_Pensioners1.id = "No_of_Pensioners1" + seq;
	No_of_Pensioners1.value = "";
	No_of_Pensioners1.size = "3";
	cel23.appendChild(No_of_Pensioners1 );
	mycurrent_row.appendChild(cel23);
	tbody.appendChild(mycurrent_row);

	/**
	 * Addition During yyyy-yyyy+1 with Reference to Statement 3(Col.14)-Total
	 * Basic Pension
	 */
	var cel24 = document.createElement("TD");
	var Total_Basic_Pension = document.createElement("input");
	Total_Basic_Pension.type = "text";
	Total_Basic_Pension.name = "Total_Basic_Pension" + seq;
	Total_Basic_Pension.id = "Total_Basic_Pension" + seq;
	Total_Basic_Pension.value = "";
	Total_Basic_Pension.size = "10";
	cel24.appendChild(Total_Basic_Pension);
	mycurrent_row.appendChild(cel24);

	/**
	 * Addition During yyyy-yyyy+1 with Reference to Statement 3(Col.14)- Total
	 * D.A
	 */
	var cell25 = document.createElement("TD");
	var Total_D_A = document.createElement("input");
	Total_D_A.type = "text";
	Total_D_A.name = "Total_D_A" + seq;
	Total_D_A.id = "Total_D_A" + seq;
	Total_D_A.value = "";
	Total_D_A.size = "10";
	cell25.appendChild(Total_D_A);
	mycurrent_row.appendChild(cell25);

	/**
	 * Addition During yyyy-yyyy+1 with Reference to Statement 3(Col.14)-Total
	 * Other Payment
	 */
	var cel26 = document.createElement("TD");
	var Total_Other_Payment  = document.createElement("input");
	Total_Other_Payment.type = "text";
	Total_Other_Payment.name = "Total_Other_Payment" + seq;
	Total_Other_Payment.id = "Total_Other_Payment" + seq;
	Total_Other_Payment.value = "";
	Total_Other_Payment.size = "10";
	cel26.appendChild(Total_Other_Payment);
	mycurrent_row.appendChild(cel26);

	/** Addition During yyyy-yyyy+1 with Reference to Statement 3(Col.14)-Total */
	var cel27 = document.createElement("TD");
	var Total  = document.createElement("input");
	Total.type = "text";
	Total.name = "Total" + seq;
	Total.id = "Total" + seq;
	Total.value = "";
	Total.size = "10";
	cel27.appendChild(Total);
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
	document.frmCivil_Budget_Format_6_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_6_Consolidation.butSub.disabled = true;
	document.frmCivil_Budget_Format_6_Consolidation.butDelete.disabled = false;
	document.frmCivil_Budget_Format_6_Consolidation.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;

	var url = path + "/Civil_Budget_Format_6_Consolidation?filter=view&cmbFinancialYear="
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
				item[0] = baseResponse.getElementsByTagName("Category")[k].firstChild.nodeValue;
				if(item[0]=='null'){
					item[0]="";
				}
				item[1] = baseResponse.getElementsByTagName("No_of_Pensioners")[k].firstChild.nodeValue;
				if(item[1]=='null'){
					item[1]="";
				}
				item[2] = baseResponse.getElementsByTagName("Total_Basic_Pension_Upto_11Y")[k].firstChild.nodeValue;
				if(item[2]=='null'){
					item[2]="0";
				}
				item[3] = baseResponse
						.getElementsByTagName("Total_Basic_Pension_Anticipated_12Y_3Y1")[k].firstChild.nodeValue;
				if(item[3]=='null'){
					item[3]="0";
				}
				item[4] = baseResponse.getElementsByTagName("Total_D_A_Upto_11Y")[k].firstChild.nodeValue;
				if(item[4]=='null'){
					item[4]="0";
				}
				item[5] = baseResponse.getElementsByTagName("Total_D_A_Anticipated_12Y_3Y1")[k].firstChild.nodeValue;
				if(item[5]=='null'){
					item[5]="0";
				}
				item[6] = baseResponse
						.getElementsByTagName("Total_Other_Payment_Upto_11Y")[k].firstChild.nodeValue;
				if(item[6]=='null'){
					item[6]="0";
				}
				item[7] = baseResponse
						.getElementsByTagName("Total_Other_Payment_Anticipated_12Y_3Y1")[k].firstChild.nodeValue;
				if(item[7]=='null'){
					item[7]="0";
				}
				item[8] = baseResponse.getElementsByTagName("Total_Upto_11Y")[k].firstChild.nodeValue;
				if(item[8]=='null'){
					item[8]="0";
				}
				item[9] = baseResponse.getElementsByTagName("Total_Anticipated_12Y_3Y1")[k].firstChild.nodeValue;
				if(item[9]=='null'){
					item[9]="0";
				}
				item[10] = baseResponse
						.getElementsByTagName("No_of_Pensioners1")[k].firstChild.nodeValue;
				if(item[10]=='null'){
					item[10]="0";
				}
				item[11] = baseResponse
						.getElementsByTagName("Total_Basic_Pension")[k].firstChild.nodeValue;
				if(item[11]=='null'){
					item[11]="0";
				}
				item[12] = baseResponse.getElementsByTagName("Total_D_A")[k].firstChild.nodeValue;
				if(item[12]=='null'){
					item[12]="0";
				}
				item[13] = baseResponse.getElementsByTagName("Total_Other_Payment")[k].firstChild.nodeValue;
				if(item[13]=='null'){
					item[13]="0";
				}
				item[14] = baseResponse
						.getElementsByTagName("Total")[k].firstChild.nodeValue;
				if(item[14]=='null'){
					item[14]="0";
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
				var cell2 = document.createElement("TD");
				var slno = document.createElement("input");
				slno.type = "text";
				slno.name = "slno" + seq;
				slno.id = "slno" + seq;
				slno.value = seq+1;	
				slno.size = "2";
				cell2.appendChild(slno);
				mycurrent_row.appendChild(cell2);

				/** Category */
				var cell3 = document.createElement("TD");
				var Category = document.createElement("input");
				Category.type = "text";
				Category.name = "Category" + seq;
				Category.id = "Category" + seq;
				Category.value = item[0];
				cell3.appendChild(Category);
				mycurrent_row.appendChild(cell3);

				/** No of Pensioners */
				var cell4 = document.createElement("TD");
				var No_of_Pensioners  = document.createElement("input");
				No_of_Pensioners.type = "text";
				No_of_Pensioners.name = "No_of_Pensioners" + seq;
				No_of_Pensioners.id = "No_of_Pensioners" + seq;
				No_of_Pensioners.value = item[1];
				No_of_Pensioners.size = "3";
				cell4.appendChild(No_of_Pensioners);
				mycurrent_row.appendChild(cell4);

				/** Total Basic Pension-Upto 11/yyyy */
				var cell5 = document.createElement("TD");
				var Total_Basic_Pension_Upto_11Y = document.createElement("input");
				Total_Basic_Pension_Upto_11Y.type = "text";
				Total_Basic_Pension_Upto_11Y.name = "Total_Basic_Pension_Upto_11Y" + seq;
				Total_Basic_Pension_Upto_11Y.id = "Total_Basic_Pension_Upto_11Y" + seq;
				Total_Basic_Pension_Upto_11Y.value = item[2]+".00";
				Total_Basic_Pension_Upto_11Y.size = "10";
				Total_Basic_Pension_Upto_11Y.style.textAlign="right";
				cell5.appendChild(Total_Basic_Pension_Upto_11Y);
				mycurrent_row.appendChild(cell5);

				/** Total Basic Pension-Anticipated 12/yyyy to 3/yyyy+1 */
				var cell6 = document.createElement("TD");
				var Total_Basic_Pension_Anticipated_12Y_3Y1 = document.createElement("input");
				Total_Basic_Pension_Anticipated_12Y_3Y1.type = "text";
				Total_Basic_Pension_Anticipated_12Y_3Y1.name = "Total_Basic_Pension_Anticipated_12Y_3Y1" + seq;
				Total_Basic_Pension_Anticipated_12Y_3Y1.id = "Total_Basic_Pension_Anticipated_12Y_3Y1" + seq;
				Total_Basic_Pension_Anticipated_12Y_3Y1.value = item[3]+".00";
				Total_Basic_Pension_Anticipated_12Y_3Y1.size = "10";
				Total_Basic_Pension_Anticipated_12Y_3Y1.style.textAlign="right";
				cell6.appendChild(Total_Basic_Pension_Anticipated_12Y_3Y1);
				mycurrent_row.appendChild(cell6);

				/** Total D.A-Upto 11/yyyy */
				var cell7 = document.createElement("TD");
				var Total_D_A_Upto_11Y = document.createElement("input");
				Total_D_A_Upto_11Y.type = "text";
				Total_D_A_Upto_11Y.name = "Total_D_A_Upto_11Y" + seq;
				Total_D_A_Upto_11Y.id = "Total_D_A_Upto_11Y" + seq;
				Total_D_A_Upto_11Y.value = item[4]+".00";
				Total_D_A_Upto_11Y.size = "10";
				Total_D_A_Upto_11Y.style.textAlign="right";
				cell7.appendChild(Total_D_A_Upto_11Y);
				mycurrent_row.appendChild(cell7);

				/** Total D.A-Anticipated 12/yyyy to 3/yyyy+1 */
				var cell8 = document.createElement("TD");
				var Total_D_A_Anticipated_12Y_3Y1 = document.createElement("input");
				Total_D_A_Anticipated_12Y_3Y1.type = "text";
				Total_D_A_Anticipated_12Y_3Y1.name = "Total_D_A_Anticipated_12Y_3Y1" + seq;
				Total_D_A_Anticipated_12Y_3Y1.id = "Total_D_A_Anticipated_12Y_3Y1" + seq;
				Total_D_A_Anticipated_12Y_3Y1.value = item[5]+".00";
				Total_D_A_Anticipated_12Y_3Y1.size = "10";	
				Total_D_A_Anticipated_12Y_3Y1.style.textAlign="right";
				cell8.appendChild(Total_D_A_Anticipated_12Y_3Y1);
				mycurrent_row.appendChild(cell8);

				/** Total Other Payment-Upto 11/yyyy */
				var cell9 = document.createElement("TD");
				var Total_Other_Payment_Upto_11Y = document.createElement("input");
				Total_Other_Payment_Upto_11Y.type = "text";
				Total_Other_Payment_Upto_11Y.name = "Total_Other_Payment_Upto_11Y" + seq;
				Total_Other_Payment_Upto_11Y.id = "Total_Other_Payment_Upto_11Y" + seq;
				Total_Other_Payment_Upto_11Y.value = item[6]+".00";
				Total_Other_Payment_Upto_11Y.size = "10";	
				Total_Other_Payment_Upto_11Y.style.textAlign="right";
				cell9.appendChild(Total_Other_Payment_Upto_11Y);
				mycurrent_row.appendChild(cell9);

				/** Total Other Payment -Anticipated 12/yyyy to 3/yyyy+1 */
				var cell20 = document.createElement("TD");
				var Total_Other_Payment_Anticipated_12Y_3Y1 = document.createElement("input");
				Total_Other_Payment_Anticipated_12Y_3Y1.type = "text";
				Total_Other_Payment_Anticipated_12Y_3Y1.name = "Total_Other_Payment_Anticipated_12Y_3Y1" + seq;
				Total_Other_Payment_Anticipated_12Y_3Y1.id = "Total_Other_Payment_Anticipated_12Y_3Y1" + seq;
				Total_Other_Payment_Anticipated_12Y_3Y1.value = item[7]+".00";
				Total_Other_Payment_Anticipated_12Y_3Y1.size = "10";
				Total_Other_Payment_Anticipated_12Y_3Y1.style.textAlign="right";
				cell20.appendChild(Total_Other_Payment_Anticipated_12Y_3Y1);
				mycurrent_row.appendChild(cell20);

				/** Total Upto 11/yyyy (4+6+8) */
				var cel21 = document.createElement("TD");
				var Total_Upto_11Y = document.createElement("input");
				Total_Upto_11Y.type = "text";
				Total_Upto_11Y.name = "Total_Upto_11Y" + seq;
				Total_Upto_11Y.id = "Total_Upto_11Y" + seq;
				Total_Upto_11Y.value = item[8]+".00";
				Total_Upto_11Y.size = "10";
				Total_Upto_11Y.style.textAlign="right";
				cel21.appendChild(Total_Upto_11Y);
				mycurrent_row.appendChild(cel21);

				/** Total - Anticipated 12/yyyy to 3/yyyy+1 (5+7+9) */
				var cel22 = document.createElement("TD");
				var Total_Anticipated_12Y_3Y1  = document.createElement("input");
				Total_Anticipated_12Y_3Y1.type = "text";
				Total_Anticipated_12Y_3Y1.name = "Total_Anticipated_12Y_3Y1" + seq;
				Total_Anticipated_12Y_3Y1.id = "Total_Anticipated_12Y_3Y1" + seq;
				Total_Anticipated_12Y_3Y1.value = item[9]+".00";
				Total_Anticipated_12Y_3Y1.size = "10";
				Total_Anticipated_12Y_3Y1.style.textAlign="right";
				cel22.appendChild(Total_Anticipated_12Y_3Y1);
				mycurrent_row.appendChild(cel22);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)-No of Pensioners
				 */
				var cel23 = document.createElement("TD");
				var No_of_Pensioners1  = document.createElement("input");
				No_of_Pensioners1.type = "text";
				No_of_Pensioners1.name = "No_of_Pensioners1" + seq;
				No_of_Pensioners1.id = "No_of_Pensioners1" + seq;
				No_of_Pensioners1.value = item[10];
				No_of_Pensioners1.size = "3";
				cel23.appendChild(No_of_Pensioners1 );
				mycurrent_row.appendChild(cel23);
				tbody.appendChild(mycurrent_row);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)-Total Basic Pension
				 */
				var cel24 = document.createElement("TD");
				var Total_Basic_Pension = document.createElement("input");
				Total_Basic_Pension.type = "text";
				Total_Basic_Pension.name = "Total_Basic_Pension" + seq;
				Total_Basic_Pension.id = "Total_Basic_Pension" + seq;
				Total_Basic_Pension.value = item[11]+".00";
				Total_Basic_Pension.size = "10";
				Total_Basic_Pension.style.textAlign="right";
				cel24.appendChild(Total_Basic_Pension);
				mycurrent_row.appendChild(cel24);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)- Total D.A
				 */
				var cell25 = document.createElement("TD");
				var Total_D_A = document.createElement("input");
				Total_D_A.type = "text";
				Total_D_A.name = "Total_D_A" + seq;
				Total_D_A.id = "Total_D_A" + seq;
				Total_D_A.value = item[12]+".00";
				Total_D_A.size = "10";
				Total_D_A.style.textAlign="right";
				cell25.appendChild(Total_D_A);
				mycurrent_row.appendChild(cell25);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)-Total Other Payment
				 */
				var cel26 = document.createElement("TD");
				var Total_Other_Payment  = document.createElement("input");
				Total_Other_Payment.type = "text";
				Total_Other_Payment.name = "Total_Other_Payment" + seq;
				Total_Other_Payment.id = "Total_Other_Payment" + seq;
				Total_Other_Payment.value = item[13]+".00";
				Total_Other_Payment.size = "10";
				Total_Other_Payment.style.textAlign="right";
				cel26.appendChild(Total_Other_Payment);
				mycurrent_row.appendChild(cel26);

				/**
				 * Addition During yyyy-yyyy+1 with Reference to Statement
				 * 3(Col.14)-Total
				 */
				var cel27 = document.createElement("TD");
				var Total  = document.createElement("input");
				Total.type = "text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				Total.value = item[14]+".00";
				Total.size = "10";
				Total.style.textAlign="right";
				cel27.appendChild(Total);
				mycurrent_row.appendChild(cel27);
				
				/** Sl No */
				var slno_db = document.createElement("input");
				slno_db.setAttribute("type", "hidden");
				slno_db.setAttribute("value", item[18]);
				slno_db.setAttribute("name", "slno_db" + seq);
				slno_db.setAttribute("id", "slno_db" + seq);
				document.getElementById("frmCivil_Budget_Format_6_Consolidation")
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
		alert("Civil Budget Format-6 Consolidation have Already Freezed");
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
					document.getElementById("frmCivil_Budget_Format_6_Consolidation")
							.appendChild(slno_db1);
				} else {
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", "-1");
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("frmCivil_Budget_Format_6_Consolidation")
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

	document.frmCivil_Budget_Format_6_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_6_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_6_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_6_Consolidation.butUpdate.disabled = true;
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