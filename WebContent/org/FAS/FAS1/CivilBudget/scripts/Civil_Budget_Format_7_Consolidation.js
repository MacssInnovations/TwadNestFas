//		Civil_Budget_Format_7_Consolidation		//
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
function initialLoad1() {

	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1 = fy.split('-');
	var y1 = fy1[0];
	var y2 = fy1[1];

	document.frmCivil_Budget_Format_7_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_7_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_7_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_7_Consolidation.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var url = "../../../../../Civil_Budget_Format_7_Consolidation?command=get&y1="
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
		financialyear1 = (parseInt(year1) - 1) + "-" + (parseInt(year) - 1);
		financialyear2 = (parseInt(year1) - 2) + "-" + (parseInt(year) - 2);
	} else {
		financialyear = year + "-" + year1;
		financialyear1 = (parseInt(year) - 1) + "-" + (parseInt(year1) - 1);
		financialyear2 = (parseInt(year) - 2) + "-" + (parseInt(year1) - 2);
	}

	for ( var k = 0; k < 3; k++) {
		if (k == 0) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear2;
			var txt = document.createTextNode(financialyear2);
			op.appendChild(txt);
			se.appendChild(op);
		} else if (k == 1) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear1;
			var txt = document.createTextNode(financialyear1);
			op.appendChild(txt);
			se.appendChild(op);

		} else if (k == 2) {
			var se = document.getElementById("cmbFinancialYear");
			var op = document.createElement("OPTION");
			op.value = financialyear;
			var txt = document.createTextNode(financialyear);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	document.getElementById("cmbFinancialYear").value = financialyear;

	document.frmCivil_Budget_Format_7_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_7_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_7_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_7_Consolidation.butUpdate.disabled = true;
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

		var r_no = baseResponse.getElementsByTagName("post_rank_id");
		seq = 0;

		var item = new Array();
		if (r_no.length != 0) {
			for ( var k = 0; k < r_no.length; k++) {

			item[0] = baseResponse.getElementsByTagName("post_rank_id")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("post_rank_name")[k].firstChild.nodeValue;
			item[2] = baseResponse
					.getElementsByTagName("sanctioned_no_of_posts")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("filledup_posts")[k].firstChild.nodeValue;
			item[4] = baseResponse.getElementsByTagName("diversion_to_other")[k].firstChild.nodeValue;

			item[5] = baseResponse.getElementsByTagName("diversion_from_other")[k].firstChild.nodeValue;
			item[6] = baseResponse.getElementsByTagName("total")[k].firstChild.nodeValue;
			item[7] = baseResponse.getElementsByTagName("remainingposts")[k].firstChild.nodeValue;

			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;

			/** Sl No */
			var cell0 = document.createElement("TD");
			var slno = document.createTextNode(seq + 1);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);

			/** Post Rank Name */
			var cell2 = document.createElement("TD");
			var Post_Rank_Name = document.createElement('TEXTAREA', 'option1');
			Post_Rank_Name.name = "Post_Rank_Name" + seq;
			Post_Rank_Name.id = "Post_Rank_Name" + seq;
			Post_Rank_Name.value = item[1];
			Post_Rank_Name.readOnly = true;
			Post_Rank_Name.setAttribute("cols", "5");
			Post_Rank_Name.style.height = "30px"
			Post_Rank_Name.style.width = "330px";
			cell2.appendChild(Post_Rank_Name);
			mycurrent_row.appendChild(cell2);

			/** Sanctioned Post (Upto the Year ) */
			var cell3 = document.createElement("TD");
			var Sanctioned_Post = document.createElement("input");
			Sanctioned_Post.type = "text";
			Sanctioned_Post.name = "Sanctioned_Post" + seq;
			Sanctioned_Post.id = "Sanctioned_Post" + seq;
			Sanctioned_Post.value = item[2];
			cell3.appendChild(Sanctioned_Post);
			Sanctioned_Post.style.textAlign = "right";
			Sanctioned_Post.size = "10";
			var currentText = document.createTextNode("");
			cell3.appendChild(currentText);
			mycurrent_row.appendChild(cell3);

			/** Diversion to Others(-) */
			var cell4 = document.createElement("TD");
			var Diversion_to_Others = document.createElement("input");
			Diversion_to_Others.type = "text";
			Diversion_to_Others.name = "Diversion_to_Others" + seq;
			Diversion_to_Others.id = "Diversion_to_Others" + seq;
			Diversion_to_Others.value = item[4];
			cell4.appendChild(Diversion_to_Others);
			Diversion_to_Others.style.textAlign = "right";
			Diversion_to_Others.size = "10";
			var currentText = document.createTextNode("");
			cell4.appendChild(currentText);
			mycurrent_row.appendChild(cell4);

			/** Diversion from Others(+) */
			var cell5 = document.createElement("TD");
			var Diversion_from_Others = document.createElement("input");
			Diversion_from_Others.type = "text";
			Diversion_from_Others.name = "Diversion_from_Others" + seq;
			Diversion_from_Others.id = "Diversion_from_Others" + seq;
			Diversion_from_Others.value = item[5];
			cell5.appendChild(Diversion_from_Others);
			Diversion_from_Others.style.textAlign = "right";
			Diversion_from_Others.size = "10";
			var currentText = document.createTextNode("");
			cell5.appendChild(currentText);
			mycurrent_row.appendChild(cell5);

			/** Total (Upto to the Year) */
			var cell5 = document.createElement("TD");
			var Total = document.createElement("input");
			Total.type = "text";
			Total.name = "Total" + seq;
			Total.id = "Total" + seq;
			Total.value = item[6];
			cell5.appendChild(Total);
			Total.style.textAlign = "right";
			Total.size = "10";
			var currentText = document.createTextNode("");
			cell5.appendChild(currentText);
			mycurrent_row.appendChild(cell5);

			/** Utilised (Upto Nov) */
			var cell5 = document.createElement("TD");
			var Utilised = document.createElement("input");
			Utilised.type = "text";
			Utilised.name = "Utilised" + seq;
			Utilised.id = "Utilised" + seq;
			Utilised.value = item[3];
			cell5.appendChild(Utilised);
			Utilised.style.textAlign = "right";
			Utilised.size = "10";
			var currentText = document.createTextNode("");
			cell5.appendChild(currentText);
			mycurrent_row.appendChild(cell5);

			/** Vacant as on I st Dec */
			var cell5 = document.createElement("TD");
			var Vacant = document.createElement("input");
			Vacant.type = "text";
			Vacant.name = "Vacant" + seq;
			Vacant.id = "Vacant" + seq;
			Vacant.value = item[7];
			cell5.appendChild(Vacant);
			Vacant.style.textAlign = "right";
			Vacant.size = "10";
			var currentText = document.createTextNode("");
			cell5.appendChild(currentText);
			mycurrent_row.appendChild(cell5);

			/** Post Rank id */
			var Post_Rank_id = document.createElement("input");
			Post_Rank_id.setAttribute("type", "hidden");
			Post_Rank_id.setAttribute("value", item[0]);
			Post_Rank_id.setAttribute("name", "Post_Rank_id" + seq);
			Post_Rank_id.setAttribute("id", "Post_Rank_id" + seq);
			document.getElementById("frmCivil_Budget_Format_7_Consolidation")
					.appendChild(Post_Rank_id);

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

function LoadData(path) {
	//alert(path);
	document.frmCivil_Budget_Format_7_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_7_Consolidation.butSub.disabled = true;
	document.frmCivil_Budget_Format_7_Consolidation.butDelete.disabled = false;
	document.frmCivil_Budget_Format_7_Consolidation.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var RecordCount = 0;
	var url = path
			+ "/Civil_Budget_Format_7_Consolidation?filter=view&cmbFinancialYear="
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
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("post_rank_id");
		var len = baseResponse.getElementsByTagName("post_rank_id").length;
		seq = 0;
		if (len != 0) {
			var item = new Array();
			for ( var k = 0; k < r_no.length; k++) {

				item[0] = baseResponse.getElementsByTagName("post_rank_id")[k].firstChild.nodeValue;
				item[1] = baseResponse.getElementsByTagName("post_rank_name")[k].firstChild.nodeValue;
				item[2] = baseResponse
						.getElementsByTagName("sanctioned_no_of_posts")[k].firstChild.nodeValue;
				item[3] = baseResponse.getElementsByTagName("filledup_posts")[k].firstChild.nodeValue;
				item[4] = baseResponse
						.getElementsByTagName("diversion_to_other")[k].firstChild.nodeValue;

				item[5] = baseResponse
						.getElementsByTagName("diversion_from_other")[k].firstChild.nodeValue;
				item[6] = baseResponse.getElementsByTagName("total")[k].firstChild.nodeValue;
				item[7] = baseResponse.getElementsByTagName("remainingposts")[k].firstChild.nodeValue;

				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				/** Sl No */
				var cell0 = document.createElement("TD");
				var slno = document.createTextNode(seq + 1);
				cell0.appendChild(slno);
				mycurrent_row.appendChild(cell0);

				/** Post Rank Name */
				var cell2 = document.createElement("TD");
				var Post_Rank_Name = document.createElement('TEXTAREA',
						'option1');
				Post_Rank_Name.name = "Post_Rank_Name" + seq;
				Post_Rank_Name.id = "Post_Rank_Name" + seq;
				Post_Rank_Name.value = item[1];
				Post_Rank_Name.readOnly = true;
				Post_Rank_Name.setAttribute("cols", "5");
				Post_Rank_Name.style.height = "30px"
				Post_Rank_Name.style.width = "330px";
				cell2.appendChild(Post_Rank_Name);
				mycurrent_row.appendChild(cell2);

				/** Sanctioned Post (Upto the Year ) */
				var cell3 = document.createElement("TD");
				var Sanctioned_Post = document.createElement("input");
				Sanctioned_Post.type = "text";
				Sanctioned_Post.name = "Sanctioned_Post" + seq;
				Sanctioned_Post.id = "Sanctioned_Post" + seq;
				Sanctioned_Post.value = item[2];
				cell3.appendChild(Sanctioned_Post);
				Sanctioned_Post.style.textAlign = "right";
				Sanctioned_Post.size = "10";
				var currentText = document.createTextNode("");
				cell3.appendChild(currentText);
				mycurrent_row.appendChild(cell3);

				/** Diversion to Others(-) */
				var cell4 = document.createElement("TD");
				var Diversion_to_Others = document.createElement("input");
				Diversion_to_Others.type = "text";
				Diversion_to_Others.name = "Diversion_to_Others" + seq;
				Diversion_to_Others.id = "Diversion_to_Others" + seq;
				Diversion_to_Others.value = item[4];
				cell4.appendChild(Diversion_to_Others);
				Diversion_to_Others.style.textAlign = "right";
				Diversion_to_Others.size = "10";
				var currentText = document.createTextNode("");
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);

				/** Diversion from Others(+) */
				var cell5 = document.createElement("TD");
				var Diversion_from_Others = document.createElement("input");
				Diversion_from_Others.type = "text";
				Diversion_from_Others.name = "Diversion_from_Others" + seq;
				Diversion_from_Others.id = "Diversion_from_Others" + seq;
				Diversion_from_Others.value = item[5];
				cell5.appendChild(Diversion_from_Others);
				Diversion_from_Others.style.textAlign = "right";
				Diversion_from_Others.size = "10";
				var currentText = document.createTextNode("");
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** Total (Upto to the Year) */
				var cell5 = document.createElement("TD");
				var Total = document.createElement("input");
				Total.type = "text";
				Total.name = "Total" + seq;
				Total.id = "Total" + seq;
				Total.value = item[6];
				cell5.appendChild(Total);
				Total.style.textAlign = "right";
				Total.size = "10";
				var currentText = document.createTextNode("");
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** Utilised (Upto Nov) */
				var cell5 = document.createElement("TD");
				var Utilised = document.createElement("input");
				Utilised.type = "text";
				Utilised.name = "Utilised" + seq;
				Utilised.id = "Utilised" + seq;
				Utilised.value = item[3];
				cell5.appendChild(Utilised);
				Utilised.style.textAlign = "right";
				Utilised.size = "10";
				var currentText = document.createTextNode("");
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** Vacant as on I st Dec */
				var cell5 = document.createElement("TD");
				var Vacant = document.createElement("input");
				Vacant.type = "text";
				Vacant.name = "Vacant" + seq;
				Vacant.id = "Vacant" + seq;
				Vacant.value = item[7];
				cell5.appendChild(Vacant);
				Vacant.style.textAlign = "right";
				Vacant.size = "10";
				var currentText = document.createTextNode("");
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** Post Rank id */
				var Post_Rank_id = document.createElement("input");
				Post_Rank_id.setAttribute("type", "hidden");
				Post_Rank_id.setAttribute("value", item[0]);
				Post_Rank_id.setAttribute("name", "Post_Rank_id" + seq);
				Post_Rank_id.setAttribute("id", "Post_Rank_id" + seq);
				document.getElementById(
						"frmCivil_Budget_Format_7_Consolidation").appendChild(
						Post_Rank_id);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
		} else {
			alert("Record Does Not Exist");
		}
		document.getElementById("RecordCount").value = seq;
	} else if (flag == "Exist") {
		alert("Format-7 have Already Freezed");
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
		for ( var i = 0; i < rowcount; i++) {
			if (document
					.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).value == "") {
				alert("Enter Anticipated for the Period Dec to Mar in the Field");
				document
						.getElementById("Anticipated_for_Period_Dec_to_Mar" + i)
						.focus();
				return false;
			} else if (document.getElementById("RE_for_Year" + i).value == "") {
				alert("Enter RE for the Year in the Field");
				document.getElementById("RE_for_Year" + i).focus();
				return false;
			} else if (document.getElementById("BE_for_Next_Year" + i).value == "") {
				alert("Enter BE for Next Year in the Field");
				document.getElementById("BE_for_Next_Year" + i).focus();
				return false;
			} else {
				return true;
			}
		}
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
		for ( var i = 0; i < rowcount; i++) {
			if (document
					.getElementById("Anticipated_for_Period_Dec_to_Mar" + i).value == "") {
				alert("Enter Anticipated for the Period Dec to Mar in the Field");
				document
						.getElementById("Anticipated_for_Period_Dec_to_Mar" + i)
						.focus();
				return false;
			} else if (document.getElementById("RE_for_Year" + i).value == "") {
				alert("Enter RE for the Year in the Field");
				document.getElementById("RE_for_Year" + i).focus();
				return false;
			} else if (document.getElementById("BE_for_Next_Year" + i).value == "") {
				alert("Enter BE for Next Year in the Field");
				document.getElementById("BE_for_Next_Year" + i).focus();
				return false;
			} else {
				return true;
			}
		}
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
function Variation_bt_BE_RE(sam) {
	var RE_for_Year = parseInt(document.getElementById("RE_for_Year" + sam).value);
	var BE_for_the_Year = parseInt(document.getElementById("BE_for_the_Year"
			+ sam).value);
	if (RE_for_Year > BE_for_the_Year) {
		var diff = RE_for_Year - BE_for_the_Year;
	} else {
		var diff = BE_for_the_Year - RE_for_Year;
	}
	document.getElementById("Variation_betwen_BE_and_RE" + sam).value = diff;
}

function clrForm() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	LoadAccountingUnitID('LIST_ALL_UNITS');

	document.frmCivil_Budget_Format_7_Consolidation.butView.disabled = false;
	document.frmCivil_Budget_Format_7_Consolidation.butSub.disabled = false;
	document.frmCivil_Budget_Format_7_Consolidation.butDelete.disabled = true;
	document.frmCivil_Budget_Format_7_Consolidation.butUpdate.disabled = true;
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
