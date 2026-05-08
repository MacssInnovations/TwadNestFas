var seq = 0;
var seq1 = 0;
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
			//alert("manipulate-command:--->>>"+command);
			if (command == "ListAll") {
				//alert("manipulate");
				LoadList(baseResponse);
			} else if (command == "SaveFunc") {
				//alert("manipulate");
				SaveFunc1(baseResponse);
			}
		}
	}
}

function initialLoad(path, cboAcc_UnitCode, cboOffice_code, cboCashBook_Year,
		cboCashBook_Month, cmbBankAccNo,docno,docdate,doctype,indicator,Trans_Type_NT0,Trans_Type_NT_New0) {
	//alert("Welcome");
	//alert("Trans_Type_NT0(initial load)"+Trans_Type_NT0);
	
	
	
	
	var url = path + "/BRS_Clearance_Entry?command=ListAll&cmbAcc_UnitCode="
			+ cboAcc_UnitCode + "&cmbOffice_code=" + cboOffice_code
			+ "&txtCB_Year=" + cboCashBook_Year + "&txtCB_Month="
			+ cboCashBook_Month + "&cmbBankAccNo=" + cmbBankAccNo+"&indicator="+indicator+"&Trans_Type_NT0="+Trans_Type_NT0+"&Trans_Type_NT_New0="+Trans_Type_NT_New0;
	//alert("url---<Clearance Entry>"+url);

	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);
	document.getElementById("imgfld").style.visibility = "visible"
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function LoadList(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = i.rowIndex;
			tbody.deleteRow(r);
		}
		var len = baseResponse.getElementsByTagName("RemitanceDate").length;
		for ( var k = 0; k < len; k++) {
			var T_or_NT = baseResponse.getElementsByTagName("T_or_NT")[k].firstChild.nodeValue;
			var RemitanceDate = baseResponse
					.getElementsByTagName("RemitanceDate")[k].firstChild.nodeValue;
			if (RemitanceDate == 'null') {
				RemitanceDate = "-";
			}
			var WithdrawlDate = baseResponse
					.getElementsByTagName("WithdrawlDate")[k].firstChild.nodeValue;
			if (WithdrawlDate == 'null') {
				WithdrawlDate = "-";
			} else if (WithdrawlDate == '0/00/0') {
				WithdrawlDate = "-";
			}
			var Voucher_or_ChallanNo = baseResponse
					.getElementsByTagName("Voucher_or_ChallanNo")[k].firstChild.nodeValue;
			if (Voucher_or_ChallanNo == 'null') {
				Voucher_or_ChallanNo = "-";
			}
			var Cheqe_or_DDNo = baseResponse
					.getElementsByTagName("Cheqe_or_DDNo")[k].firstChild.nodeValue;
			if (Cheqe_or_DDNo == 'null') {
				Cheqe_or_DDNo = "-";
			}
			var CRAmount = baseResponse.getElementsByTagName("CRAmount")[k].firstChild.nodeValue;
			if (CRAmount == 'null') {
				CRAmount = "-";
			}
			var DRAmount = baseResponse.getElementsByTagName("DRAmount")[k].firstChild.nodeValue;
			if (DRAmount == 'null') {
				DRAmount = "-";
			}
			var EntryFoundInPassBook = baseResponse
					.getElementsByTagName("EntryFoundInPassBook")[k].firstChild.nodeValue;
			if (EntryFoundInPassBook == 'null') {
				EntryFoundInPassBook = "-";
			}
			var EntryDate = baseResponse.getElementsByTagName("EntryDate")[k].firstChild.nodeValue;
			if (EntryDate == 'null') {
				EntryDate = "-";
			}
			var Amount = baseResponse.getElementsByTagName("Amount")[k].firstChild.nodeValue;
			if (Amount == 'null') {
				Amount = "-";
			}
			var Difference = baseResponse.getElementsByTagName("Difference")[k].firstChild.nodeValue;
			if (Difference == 'null') {
				Difference = "-";
			}
			var RsnForDiff = baseResponse.getElementsByTagName("RsnForDiff")[k].firstChild.nodeValue;
			if (RsnForDiff == 'null') {
				RsnForDiff = "-";
			}
			var FollowUpAction = baseResponse
					.getElementsByTagName("FollowUpAction")[k].firstChild.nodeValue;
			if (FollowUpAction == 'null') {
				FollowUpAction = "-";
			}
			var Clearance = baseResponse.getElementsByTagName("Clearance")[k].firstChild.nodeValue;
			if (Clearance == 'null') {
				Clearance = "-";
			}
			var doc_no = baseResponse.getElementsByTagName("doc_no")[k].firstChild.nodeValue;
			if (doc_no == 'null') {
				doc_no = "-";
			}
			var doc_type = baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;
			if (doc_type == 'null') {
				doc_type = "-";
			}

			var doc_date1 = baseResponse.getElementsByTagName("com_doc_date")[k].firstChild.nodeValue;
			//alert("item[20]"+item[20]);
			if (doc_date1 == 'null' || doc_date1 == '0') {
				doc_date1 = "";
			}

			var sl_no = baseResponse.getElementsByTagName("sl_no")[k].firstChild.nodeValue;
			if (sl_no == 'null') {
				sl_no = "-";
			}
			var E_Date = baseResponse.getElementsByTagName("E_Date")[k].firstChild.nodeValue;
			if (E_Date == 'null') {
				E_Date = "-";
			}
			
			var year = baseResponse.getElementsByTagName("year")[k].firstChild.nodeValue;
			//document.getElementById("cashyear").value=year;
			var month = baseResponse.getElementsByTagName("month")[k].firstChild.nodeValue;
			//document.getElementById("cashmonth").value=month;
			
			
			
			var tbody = document.getElementById("grid_body_TWAD");
			var table = document.getElementById("mytable");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;

			var cell2 = document.createElement("TD");
			var RemitanceDate = document.createTextNode(RemitanceDate);
			cell2.appendChild(RemitanceDate);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var WithdrawlDate = document.createTextNode(WithdrawlDate);
			cell3.appendChild(WithdrawlDate);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var Voucher_or_ChallanNo = document
					.createTextNode(Voucher_or_ChallanNo);
			cell4.appendChild(Voucher_or_ChallanNo);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var Cheqe_or_DDNo = document.createTextNode(Cheqe_or_DDNo);
			cell5.appendChild(Cheqe_or_DDNo);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var CRAmount = document.createTextNode(CRAmount);
			cell6.appendChild(CRAmount);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			var DRAmount = document.createTextNode(DRAmount);
			cell7.appendChild(DRAmount);
			mycurrent_row.appendChild(cell7);

			var cell9 = document.createElement("TD");
			var EntryDate = document.createTextNode(EntryDate);
			cell9.appendChild(EntryDate);
			mycurrent_row.appendChild(cell9);

			var cel20 = document.createElement("TD");
			var Amount = document.createTextNode(Amount);
			cel20.appendChild(Amount);
			mycurrent_row.appendChild(cel20);

			var cel21 = document.createElement("TD");
			var Difference = document.createTextNode(Difference);
			cel21.appendChild(Difference);
			mycurrent_row.appendChild(cel21);

			var cel22 = document.createElement("TD");
			var RsnForDiff = document.createTextNode(RsnForDiff);
			cel22.appendChild(RsnForDiff);
			mycurrent_row.appendChild(cel22);

			var cell = document.createElement("TD");
			var anc = document.createElement("input");
			anc.type = "checkbox";
			anc.id = "Select" + seq;
			anc.name = "Select" + seq;
			//anc.checked=true;
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell41 = document.createElement("TD");
			var doc_no1 = document.createElement("hidden");
			doc_no1.type = "hidden";
			doc_no1.name = "doc_no" + seq;
			doc_no1.id = "doc_no" + seq;
			doc_no1.value = doc_no;
			cell41.appendChild(doc_no1);
			var currentText = document.createTextNode(doc_no);
			cell41.appendChild(currentText);
			cell41.style.visibility = "hidden"
			mycurrent_row.appendChild(cell41);

			var cell44 = document.createElement("TD");
			var doc_type1 = document.createElement("hidden");
			doc_type1.type = "hidden";
			doc_type1.name = "doc_type" + seq;
			doc_type1.id = "doc_type" + seq;
			doc_type1.value = doc_type;
			cell44.appendChild(doc_type1);
			var currentText = document.createTextNode(doc_type);
			cell44.appendChild(currentText);
			cell44.style.visibility = "hidden"
			mycurrent_row.appendChild(cell44);

			var cell51 = document.createElement("TD");
			var sl_nos = document.createElement("hidden");
			sl_nos.type = "hidden";
			sl_nos.name = "sl_nos" + seq;
			sl_nos.id = "sl_nos" + seq;
			// alert("sl_nos.id:-->>"+sl_nos.id);
			sl_nos.value = sl_no;
			// alert("sl_nos.value:-->>"+sl_nos.value);
			cell51.appendChild(sl_nos);
			var currentText = document.createTextNode(sl_no);
			cell51.appendChild(currentText);
			cell51.style.visibility = "hidden"
			mycurrent_row.appendChild(cell51);

			var cell55 = document.createElement("TD");
			var E_Datee = document.createElement("hidden");
			E_Datee.type = "hidden";
			E_Datee.name = "E_Datee" + seq;
			E_Datee.id = "E_Datee" + seq;
			E_Datee.value = E_Date;
			cell55.appendChild(E_Datee);
			var currentText = document.createTextNode(E_Date);
			cell55.appendChild(currentText);
			cell55.style.visibility = "hidden";
			mycurrent_row.appendChild(cell55);

			/** doc Date */
			var cell56 = document.createElement("TD");
			var doc_date = document.createElement("hidden");
			doc_date.type = "hidden";
			doc_date.name = "doc_date" + seq;
			doc_date.id = "doc_date" + seq;
			doc_date.value = doc_date1;
			cell56.appendChild(doc_date);
			var currentText = document.createTextNode(doc_date1);
			cell56.appendChild(currentText);
			cell56.style.visibility = "hidden";
			mycurrent_row.appendChild(cell56);

			/** T or NT */
			var cell57 = document.createElement("TD");
			var T_or_NT1 = document.createElement("hidden");
			T_or_NT1.type = "text";
			T_or_NT1.name = "T_or_NT" + seq;
			T_or_NT1.id = "T_or_NT" + seq;
			T_or_NT1.value = T_or_NT;
			cell57.appendChild(T_or_NT1);
			var currentText = document.createTextNode(T_or_NT);
			cell57.appendChild(currentText);
			//cell57.style.visibility = "text"
			mycurrent_row.appendChild(cell57);

			var cell58 = document.createElement("TD");
			var cycmonth = document.createElement("hidden");
			cycmonth.type = "hidden";
			cycmonth.name = "cycm" + seq;
			cycmonth.id = "cycm" + seq;
			cycmonth.value = year+"-"+month;
			cell58.appendChild(cycmonth);
			var currentText = document.createTextNode(year+"-"+month);
			cell58.appendChild(currentText);
			cell58.style.visibility = "text";
			mycurrent_row.appendChild(cell58);
			
			tbody.appendChild(mycurrent_row);
			seq++;
		}
	} else {
		alert("Fail to Load Grid Values");
	}
	document.getElementById("imgfld").style.visibility = "hidden";
	document.getElementById('txtNoofRecords').value = seq;
	seq = 0;
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

function SaveFunc(path, cboAcc_UnitCode, cboOffice_code, cboCashBook_Year,
		cboCashBook_Month, cmbBankAccNo, Amt_in_pasbook,Particualrs_NT) {
	var k = 0
	var cr1;
	var dr1;
	var cr2;
	var dr2;
	var Amt1 = 0;
	var Amt = 0;
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;
	var al = new Array();
	for ( var i = 0; i < rowcount; i++) {
		var r = tbody.rows[i];
		var s = r.cells.length;
		for ( var j = 0; j < 10; j++) {
			if (r.cells[10].firstChild.checked) {
				al[k] = r.cells[j].firstChild.nodeValue;
				k++;
			}
		}
		if (r.cells[10].firstChild.checked) {
			var T_or_NT1 = document.getElementById("T_or_NT" + i).value;			
			if (T_or_NT1 == "T") {
				al[k] = "T";
				k++;
			} else {
				al[k] = "NT";
				k++;
			}
			al[k] = document.getElementById("doc_no" + i).value;
			k++;
			al[k] = document.getElementById("doc_type" + i).value;
			k++;
			al[k] = document.getElementById("sl_nos" + i).value;
			k++;
			al[k] = document.getElementById("E_Datee" + i).value;

			
			k++;
		}
		if (r.cells[10].firstChild.checked) {
			
			al[k] = r.cells[17].firstChild.value;
		//	alert(al[k]);
			k++;
		}
		
		if (r.cells[10].firstChild.checked) {
			cr2 = r.cells[4].firstChild.nodeValue;			
			cr1 = parseFloat(cr2);			
			dr2 = r.cells[5].firstChild.nodeValue;			
			dr1 = parseFloat(dr2);			
			Amt1 = cr1 + dr1;			
			Amt = Amt + parseFloat(Amt1);			
		}
	}	
	Amount = parseInt(Amt);
	Amount_IN_Passbook = parseInt(Amt_in_pasbook);	
	if (Amount > Amount_IN_Passbook) {
		alert("The Total Selected Records Amount Exceeds 'Amount in Passbook' ");
	} 
	else if(Amount<Amount_IN_Passbook)
	{
		alert("The Total Selected Records Amount should Be Equal to 'Amount in Passbook' ");
	}
	else {
		var journal_date=document.getElementById("journal_date").value;
		var ref_doc_no=document.getElementById("ref_doc_no").value;
		var ref_doc_type=document.getElementById("ref_doc_type").value;
		var ref_doc_amt=document.getElementById("ref_doc_amt").value;
		var Trans_Type_NT0=document.getElementById("Trans_Type_NT0").value;
		var Trans_Type_NT_New0=document.getElementById("Trans_Type_NT_New0").value;
		var Particualrs_NT=document.getElementById("Particualrs_NT").value;
		
//		var cashyear=document.getElementById("cashyear").value;
//		var cashmonth=document.getElementById("cashmonth").value;
		
		var url = path
				+ "/BRS_Clearance_Entry?command=SaveFunc&cmbAcc_UnitCode="
				+ cboAcc_UnitCode + "&cmbOffice_code=" + cboOffice_code
				+ "&txtCB_Year=" + cboCashBook_Year + "&txtCB_Month="
				+ cboCashBook_Month + "&cmbBankAccNo=" + cmbBankAccNo + "&al="
				+ al+"&journal_date="+journal_date+"&ref_doc_no="+ref_doc_no+"&ref_doc_type="
				+ref_doc_type+"&ref_doc_amt="+ref_doc_amt+"&Trans_Type_NT0="+Trans_Type_NT0+"&Trans_Type_NT_New0="+Trans_Type_NT_New0+"&Particualrs_NT="+Particualrs_NT;
		//alert(al);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null); 
	}  
}
function SaveFunc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Updated Successfully");
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = i.rowIndex;
			tbody.deleteRow(r);
		}
		window.close();
	} else {
		alert("Record Updation Failed");
		var date1 = baseResponse.getElementsByTagName("Date")[0].firstChild.nodeValue;
		if (date1 == "invalid") {
			alert(" Enter Valid Action Date ex:-dd/mm/yyyy ");
		}
	}
}

function refresh() {
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;
	for ( var i = 0; i < rowcount; i++) {
		var r = i.rowIndex;
		tbody.deleteRow(r);
	}

	document.frmBRS_Clearance_Entry.cmbBankAccNo.value = "s";
}

function exitfun(path) {
	window.close();
}