var __pagination = 15;
var totalblock = 0;
function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function loadGrid() {
	//alert("inside load grid");
	var off_id = document.getElementById("off_id").value;
	//alert(off_id);
	if (off_id == 5000) {
		var cmbWing = document.getElementById("cmbWing").value;
		var url = "../../../../../NewReqRegn_Servlet.view?command=loadGrid&txtOffID="
				+ off_id + "&cmbWing=" + cmbWing;
	} else {
		var url = "../../../../../NewReqRegn_Servlet.view?command=loadGrid&txtOffID="
				+ off_id;
	}
	//alert(url);
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processResponse(req);
	}
	req.send(null);
}

function processResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var response = req.responseXML.getElementsByTagName("response")[0];
			var cmd = response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag = response.getElementsByTagName("flag")[0].firstChild.nodeValue;
			record1 = new Array();
			record2 = new Array();
			record3 = new Array();
			record4 = new Array();
			record5 = new Array();
			record6 = new Array();
			record7 = new Array();
			record8 = new Array();
			record9 = new Array();
			record10 = new Array();
			record11 = new Array();
			record12 = new Array();
			record13 = new Array();
			record14 = new Array();
			if (flag == "success") {

				var count = response.getElementsByTagName("contractor_id");

				for (i = 0; i < count.length; i++) {
					record1[i] = response.getElementsByTagName("contractor_id")[i].firstChild.nodeValue;
					record2[i] = response
							.getElementsByTagName("contractor_name")[i].firstChild.nodeValue;
					record3[i] = response.getElementsByTagName("address")[i].firstChild.nodeValue;
					record4[i] = response.getElementsByTagName("regn_slno")[i].firstChild.nodeValue;
					record5[i] = response.getElementsByTagName("date_of_regn")[i].firstChild.nodeValue;
					record6[i] = response.getElementsByTagName("regn_class_id")[i].firstChild.nodeValue;
					record7[i] = response.getElementsByTagName("phone")[i].firstChild.nodeValue;
					record8[i] = response.getElementsByTagName("email")[i].firstChild.nodeValue;
					record9[i] = response
							.getElementsByTagName("regn_valid_upto")[i].firstChild.nodeValue;
					record10[i] = response
							.getElementsByTagName("REGN_STATE_COVERAGE")[i].firstChild.nodeValue;
					record11[i] = response
							.getElementsByTagName("REGN_ALIAS_CODE")[i].firstChild.nodeValue;
					record12[i] = response.getElementsByTagName("jurisdiction")[i].firstChild.nodeValue;
					record13[i] = response.getElementsByTagName("status")[i].firstChild.nodeValue;
					record14[i] = response.getElementsByTagName("wing_id")[i].firstChild.nodeValue;

				}
				totalblock = 0;
				if (record1.length > 0) {
					totalblock = parseInt(record1.length / __pagination);
					//alert("test on 24Feb2012"+totalblock);
					if (record1.length % __pagination != 0) {
						totalblock = totalblock + 1;
					}
					var cmbpage = document.getElementById("cmbpage");
					try {
						cmbpage.innerHTML = "";
					} catch (e) {
						cmbpage.innerText = "";
					}

					for (i = 1; i <= totalblock; i++) {
						var option = document.createElement("OPTION");
						option.text = i;
						option.value = i;
						try {
							cmbpage.add(option);
						} catch (errorObject) {
							cmbpage.add(option, null);
						}
					}
					loadRecordVal(1);
				}
			} else {

				var tbody = document.getElementById("tblList");
				try {
					tbody.innerHTML = "";
				} catch (e) {
					tbody.innerText = "";
				}
			}
		}
	}
}

function loadRecordVal(page) {
	var i = 0;
	var c = 0;
	var p = __pagination * (page - 1);
	var sno = 0;
	var tbody = document.getElementById("tblList");
	try {
		tbody.innerHTML = "";
	} catch (e) {
		tbody.innerText = "";
	}
	document.frmAccountList.cmbpage.selectedIndex = page - 1;
	for (i = p; i < record1.length && c < __pagination; i++) {
		c++;
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = sno;
		cell1 = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable(" + sno + ")";
		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell1.appendChild(anc);
		mycurrent_row.appendChild(cell1);

		cell1 = document.createElement("TD");
		var hidval = document.createElement("input");
		hidval.type = "hidden";
		hidval.name = "cid";
		hidval.id = "cid";
		hidval.value = record1[i];

		cell1.appendChild(hidval);
		var hidval1 = document.createElement("input");
		hidval1.type = "hidden";
		hidval1.name = "cname";
		hidval1.id = "cname";
		hidval1.value = record2[i];

		cell1.appendChild(hidval1);
		var currentText = document.createTextNode(record2[i]);
		cell1.appendChild(currentText);
		mycurrent_row.appendChild(cell1);

		cell1 = document.createElement("TD");
		var hidval = document.createElement("input");
		hidval.type = "hidden";
		hidval.name = "address";
		hidval.id = "address";
		hidval.value = record3[i];

		cell1.appendChild(hidval);
		var hidval1 = document.createElement("input");
		hidval1.type = "hidden";
		hidval1.name = "phone";
		hidval1.id = "phone";
		hidval1.value = record7[i];

		cell1.appendChild(hidval1);
		var currentText = document.createTextNode(record3[i]);
		cell1.appendChild(currentText);
		mycurrent_row.appendChild(cell1);

		cell1 = document.createElement("TD");
		var hidval = document.createElement("input");
		hidval.type = "hidden";
		hidval.name = "regn_slno";
		hidval.id = "regn_slno";
		hidval.value = record4[i];

		cell1.appendChild(hidval);
		var hidval1 = document.createElement("input");
		hidval1.type = "hidden";
		hidval1.name = "email";
		hidval1.id = "email";
		hidval1.value = record8[i];

		cell1.appendChild(hidval1);
		var currentText = document.createTextNode(record4[i]);
		cell1.appendChild(currentText);
		mycurrent_row.appendChild(cell1);

		cell1 = document.createElement("TD");
		var hidval = document.createElement("input");
		hidval.type = "hidden";
		hidval.name = "dt_of_regn";
		hidval.id = "dt_of_regn";
		hidval.value = record5[i];

		cell1.appendChild(hidval);
		var hidval1 = document.createElement("input");
		hidval1.type = "hidden";
		hidval1.name = "valid_upto";
		hidval1.id = "valid_upto";
		hidval1.value = record9[i];

		cell1.appendChild(hidval1);
		var hidval2 = document.createElement("input");
		hidval2.type = "hidden";
		hidval2.name = "juris";
		hidval2.id = "juris";
		hidval2.value = record12[i];

		cell1.appendChild(hidval2);
		var currentText = document.createTextNode(record5[i]);
		cell1.appendChild(currentText);
		mycurrent_row.appendChild(cell1);

		cell1 = document.createElement("TD");
		var hidval = document.createElement("input");
		hidval.type = "hidden";
		hidval.name = "class_id";
		hidval.id = "class_id";
		hidval.value = record6[i];

		cell1.appendChild(hidval);
		var hidval1 = document.createElement("input");
		hidval1.type = "hidden";
		hidval1.name = "regn_coverage";
		hidval1.id = "regn_coverage";
		hidval1.value = record10[i];

		cell1.appendChild(hidval1);
		var hidval2 = document.createElement("input");
		hidval2.type = "hidden";
		hidval2.name = "alias_code";
		hidval2.id = "alias_code";
		hidval2.value = record11[i];

		cell1.appendChild(hidval2);
		var currentText = document.createTextNode(record6[i]);
		cell1.appendChild(currentText);
		mycurrent_row.appendChild(cell1);

		cell1 = document.createElement("TD");
		var hidval = document.createElement("input");
		hidval.type = "hidden";
		hidval.name = "status";
		hidval.id = "status";
		hidval.value = record13[i];

		cell1.appendChild(hidval);
		var hidval1 = document.createElement("input");
		hidval1.type = "hidden";
		hidval1.name = "reg_code";
		hidval1.id = "reg_code";
		hidval1.value = record12[i];

		cell1.appendChild(hidval);
		var hidval1 = document.createElement("input");
		hidval1.type = "hidden";
		hidval1.name = "wing_id";
		hidval1.id = "wing_id";
		hidval1.value = record14[i];

		cell1.appendChild(hidval1);
		var currentText = document.createTextNode(record13[i]);
		cell1.appendChild(currentText);
		mycurrent_row.appendChild(cell1);
		sno++;

		tbody.appendChild(mycurrent_row);

	}
	/*This Part Is Used To Move The Next Page Or The Previous Page In The Grid*/

	var cell = document.getElementById("divcmbpage");
	cell.style.display = "block";
	var cell = document.getElementById("divpage");
	cell.style.display = "block";
	try {
		cell.innerHTML = '/' + totalblock;
	} catch (e) {
		cell.innerText = '/' + totalblock;
	}
	if (page < totalblock) {
		var cell = document.getElementById("divnext");
		cell.style.display = "block";
		try {
			cell.innerHTML = "";
		} catch (e) {
			cell.innerText = "";
		}
		var anc = document.createElement("A");
		var url = "javascript:loadRecordVal(" + (page + 1) + ")";
		anc.href = url;
		var txtedit = document.createTextNode("<<Next>>");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
	} else {
		var cell = document.getElementById("divnext");
		cell.style.display = "block";
		try {
			cell.innerHTML = "";
		} catch (e) {
			cell.innerText = "";
		}
	}
	if (page > 1) {
		var cell = document.getElementById("divpre");
		cell.style.display = "block";
		try {
			cell.innerHTML = "";
		} catch (e) {
			cell.innerText = "";
		}
		var anc = document.createElement("A");
		var url = "javascript:loadRecordVal(" + (page - 1) + ")";
		anc.href = url;
		var txtedit = document.createTextNode("<<Previous>>");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
	} else {
		var cell = document.getElementById("divpre");
		cell.style.display = "block";
		try {
			cell.innerHTML = "";
		} catch (e) {
			cell.innerText = "";
		}
	}

}

function changepage() {
	var page = document.frmAccountList.cmbpage.value;
	loadRecordVal(parseInt(page));

}

function loadValuesFromTable(row_id) {
	alert("new changes ***");
	var tbody = document.getElementById("tblList");
	tb_length = tbody.rows.length;

	if (tb_length > 1) {

		var name = document.frmAccountList.cname[row_id].value;
		var address = document.frmAccountList.address[row_id].value;
		var reg_slno = document.frmAccountList.regn_slno[row_id].value;
		var reg_date = document.frmAccountList.dt_of_regn[row_id].value;
		var class_id = document.frmAccountList.class_id[row_id].value;
		var phone = document.frmAccountList.phone[row_id].value;

		var email = document.frmAccountList.email[row_id].value;
		var juris = document.frmAccountList.juris[row_id].value;
		var contid = document.frmAccountList.cid[row_id].value;
		var date_upto = document.frmAccountList.valid_upto[row_id].value;
		var statewise_coverage = document.frmAccountList.regn_coverage[row_id].value;
		var region_alias_code = document.frmAccountList.alias_code[row_id].value;
		var status = document.frmAccountList.status[row_id].value;
		var wing_id = document.frmAccountList.wing_id[row_id].value;
	} else {
		var name = document.frmAccountList.cname.value;
		var address = document.frmAccountList.address.value;
		var reg_slno = document.frmAccountList.regn_slno.value;
		var reg_date = document.frmAccountList.dt_of_regn.value;
		var class_id = document.frmAccountList.class_id.value;
		var phone = document.frmAccountList.phone.value;
		var email = document.frmAccountList.email.value;
		var juris = document.frmAccountList.juris.value;
		var contid = document.frmAccountList.cid.value;
		var date_upto = document.frmAccountList.valid_upto.value;
		var statewise_coverage = document.frmAccountList.regn_coverage.value;
		var region_alias_code = document.frmAccountList.alias_code.value;
		var status = document.frmAccountList.status.value;
		var wing_id = document.frmAccountList.wing_id.value;
	}
	Minimize();
	if (phone == "-")
		phone = "";
	opener.List(name, address, reg_slno, reg_date, class_id, phone, email,
			juris, contid, date_upto, tb_length, statewise_coverage,
			region_alias_code, status, wing_id);
	return true;
}

function Minimize() {
	window.close();
	opener.window.focus();
}
