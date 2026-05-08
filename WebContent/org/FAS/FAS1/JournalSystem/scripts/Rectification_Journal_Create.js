//			Rectification_Journal_Create 		//
var com_id;
var common_cmbSL_Code = "";
var common_cmbSL_type = "";
var seq = 0;
var job_flag;
var common_AHead_code_flag = "";
var emp_flag;
// /////////////////////////////////////////// XML req
// /////////////////////////////////////////////////////
window.onunload = function() {
	if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed)
		winAccHeadCode.close();
	if (winjob && winjob.open && !winjob.closed)
		winjob.close();
	if (winemp && winemp.open && !winemp.closed)
		winemp.close();

}

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

/////////////////////////////////////////////   load Table() // ///////////////////////////////////////////////////

function loadTable(scod) {
	com_id = scod; // to identify in UPDATE_GRID ,which row loaded
	clearall();
	// document.FasAcc_Headform.cmdadd.disabled=true;
	// document.getElementById("txtAcc_HeadCode").readOnly=true; // do not
	// change the Account Head
	// text_field.readOnly=true;
	var r = document.getElementById(scod);
	var rcells = r.cells;
	try {
		document.getElementById("txtAcc_HeadCode").value = rcells.item(1).firstChild.value;
	} catch (e) {
	}
	try {
		common_cmbSL_type = rcells.item(3).firstChild.value;
	} catch (e) {
		common_cmbSL_type = ""
	}
	//alert("U"+common_cmbSL_type+"U")
	try {
		common_cmbSL_Code = rcells.item(4).firstChild.value;
	} catch (e) {
		common_cmbSL_Code = ""
	}

	if (common_cmbSL_type == 5) {
		document.getElementById("txtOfficeID_trs").value = common_cmbSL_Code;
		job_flag = false;
		// doFunction('office',common_cmbSL_Code);
		// doFunction('Load_SL_Code',);
	}
	if (common_cmbSL_type == 7) {
		document.getElementById("txtEmpID_trs").value = common_cmbSL_Code;
		emp_flag = false;
		// doFunction('office',common_cmbSL_Code);
		// doFunction('Load_SL_Code',);
	}

	doFunction('checkCode', 'null');
	doFunction('Load_SL_Code', common_cmbSL_type);
	document.getElementById("txtUnitId").value = common_cmbSL_Code;
	if (rcells.item(2).firstChild.value == "CR")
		document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
	else if (rcells.item(2).firstChild.value == "DR")
		document.frmJournal_General.rad_sub_CR_DR[1].checked = true;

	// try{document.getElementById("cmbSL_Code").value=rcells.item(4).firstChild.value;}catch(e){}

	// try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
	try {
		document.getElementById("txtBill_NO").value = rcells.item(5).firstChild.value;
	} catch (e) {
	}
	try {
		document.getElementById("txtBill_date").value = rcells.item(6).firstChild.value;
	} catch (e) {
	}

	try {
		document.getElementById("txtBill_type").value = rcells.item(7).firstChild.value;
	} catch (e) {
	}

	var nex = rcells.item(7).firstChild.nextSibling;
	try {
		document.getElementById("txtAgree_No").value = nex.value;
	} catch (e) {
	}
	nex = nex.nextSibling;
	try {
		document.getElementById("txtAgree_Date").value = nex.value;
	} catch (e) {
	}
	nex = nex.nextSibling;
	try {
		document.getElementById("txtsub_Amount").value = nex.value;
	} catch (e) {
	}
	nex = nex.nextSibling;
	try {
		document.getElementById("txtParticular").value = nex.value;
	} catch (e) {
	}

	try {
		document.getElementById("adjyear").value = rcells.item(8).firstChild.value;
	} catch (e) {
	}

	try {
		document.getElementById("adjmonth").value = rcells.item(9).firstChild.value;
	} catch (e) {
	}

	tohidedoc();

	if (priorsince == 1) {
		try {
			document.getElementById("paymentreceipt").value = rcells.item(10).firstChild.value;
		} catch (e) {
		}

		setTimeout("payreceipt()", 50);
		// payreceipt();

		setTimeout("callone('" + scod + "')", 450);
	} else {
		try {
			document.getElementById("paymentreceipt1").value = rcells.item(10).firstChild.value;
		} catch (e) {
		}
		try {
			document.getElementById("receiptno1").value = rcells.item(11).firstChild.value;
		} catch (e) {
		}
	}
	//alert("common_cmbSL_Code:--"+common_cmbSL_Code);
	setTimeout("setcmbSL_Code(" + common_cmbSL_Code + ")", 900);
	document.getElementById("cmbSL_Code").value = common_cmbSL_Code;
	document.frmJournal_General.cmdupdate.style.display = 'block';
	document.frmJournal_General.cmddelete.disabled = false;
	document.frmJournal_General.cmdadd.style.display = 'none';
}
function setcmbSL_Code(common_cmbSL_Code) {
	document.getElementById("cmbSL_Code").value = common_cmbSL_Code;
}
function callone(scod) {

	var r = document.getElementById(scod);
	var rcells = r.cells;
	try {
		document.getElementById("receiptno").value = rcells.item(11).firstChild.value;
	} catch (e) {
	}
}

/////////////////////////////////////////////   ADD & UPDATE & DELETE // ///////////////////////////////////////////////////
function ADD_GRID() {
	if (document.getElementById("txtAcc_HeadCode").value.length == 0) {
		alert("Enter A/c Head Code");
		return false;
	}

	//	if (document.getElementById("cmbSL_type").length > 1
	// && document.getElementById("cmbSL_type").value == "") {
	//
	// alert("Select a Sub-Ledger Type");
	// return false;
	//
	// }
	// if (document.getElementById("cmbSL_type").value != "") {
	// if (document.getElementById("cmbSL_Code").value == "") {
	// alert("Select The Sub Ledger Code");
	// return false;
	// }
	// }
var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	if ((txtAcc_HeadCode == 900109) && (txtAcc_HeadCode == 901002))  {
		if (document.getElementById("receiptno").value == "") {
			alert("Select DocType in the Field---");
			document.getElementById("paymentreceipt").focus();
			return false;
		}
		if (document.getElementById("receiptno").value == "") {
			alert("Select DocNo in the Field---");
			document.getElementById("receiptno").focus();
			return false;
		}
		if (document.getElementById("adjyear").value == "") {
			alert("Enter Originated/Accepted Year in the Field---");
			document.getElementById("adjyear").focus();
			return false;
		}
		if (document.getElementById("adjmonth").value == "") {
			alert("Select Originated/Accepted Month in the Field---");
			document.getElementById("adjmonth").focus();
			return false;
		}
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	if (document.getElementById("txtAcc_HeadDesc").value == "") {
		alert("Please Wait Account Head is Loading .......................");
		return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/*
	 * cmbSL_type=document.getElementById("cmbSL_type").value; if(cmbSL_type==1 ||
	 * cmbSL_type==2 || cmbSL_type==11) {
	 * if(document.getElementById("txtBill_NO").value.length==0) { alert("Enter
	 * the Bill number"); document.getElementById("txtBill_NO").focus(); return
	 * false; } if(document.getElementById("txtBill_date").value.length==0) {
	 * alert("Enter the Bill Date");
	 * document.getElementById("txtBill_date").focus(); return false; }
	 * if(document.getElementById("txtBill_type").value.length==0) {
	 * alert("Enter the Bill Type");
	 * document.getElementById("txtBill_type").focus(); return false; } }
	 * if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==10 || cmbSL_type==11) {
	 * if(document.getElementById("txtAgree_No").value.length==0) { alert("Enter
	 * the Agreement Number"); document.getElementById("txtAgree_No").focus();
	 * return false; }
	 * if(document.getElementById("txtAgree_Date").value.length==0) {
	 * alert("Enter the Agreement Date");
	 * document.getElementById("txtAgree_Date").focus(); return false; } }
	 */

	/*
	 * if(document.getElementById("txtsub_Recei_from").value.length==0) {
	 * alert("Enter the value in Received From Field");
	 * document.getElementById("txtsub_Recei_from").focus(); return false; }
	 */

if(cmbMas_SL_type==92 || cmbMas_SL_type==93)
{
//alert("cmbMas_SL_type");
var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
    if(txtAcc_HeadCode==900108 ||txtAcc_HeadCode==901001)
    {
        if(document.getElementById("txtUnitId").value!=document.getElementById("cmbAcc_UnitCode").value)
        {
        alert("This Unit Should be the Login Unit Only");
        return false;
        }
    }
}

	if (document.getElementById("txtsub_Amount").value.length == 0) {
		alert("Enter the Amount ");
		// document.getElementById("txtAmount").focus();
		return false;
	}

	var w = document.frmJournal_General.receiptno.selectedIndex;
	var selected_text = document.frmJournal_General.receiptno.options[w].text;

	// if(document.getElementById("receiptno").value!=document.getElementById("txtAcc_HeadCode").value)
	// {
	// alert('Account Head is not found in the Selected Voucher ');
	// return false;
	// }

	var tbody = document.getElementById("grid_body");
	// alert("CODE"+document.getElementById("txtSL_Desc").value);
	// alert("TEXT"+document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text);
	// alert("AGA"+document.getElementById("txtSL_Desc").text)
	var t = 0;
	var exist = document.getElementById("txtAcc_HeadCode").value;
	var items = new Array();
	items[0] = document.getElementById("txtAcc_HeadCode").value;
	items[1] = document.getElementById("txtAcc_HeadDesc").value;
	if (document.frmJournal_General.rad_sub_CR_DR[0].checked == true)
		items[2] = document.frmJournal_General.rad_sub_CR_DR[0].value;
	else if (document.frmJournal_General.rad_sub_CR_DR[1].checked == true)
		items[2] = document.frmJournal_General.rad_sub_CR_DR[1].value;

	items[3] = document.getElementById("cmbSL_type").value;
	if (document.getElementById("cmbSL_type").value == "") {
		//items[4]="Not Available";
		items[4] = "";// document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;
		// alert("code"+items[4]+"ff");
	} else
		items[4] = document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;

	items[5] = document.getElementById("cmbSL_Code").value;
       
	if (document.getElementById("cmbSL_Code").value == "") {
		items[6] = "";// "Not Available";
		// alert("code"+items[6]+"ff");
	} else
		items[6] = document.getElementById("cmbSL_Code").options[document
				.getElementById("cmbSL_Code").selectedIndex].text;

	// items[7]=document.getElementById("txtsub_Recei_from").value;
	items[7] = document.getElementById("txtBill_NO").value;
	items[8] = document.getElementById("txtBill_date").value;
	items[9] = document.getElementById("txtBill_type").value;
	// items[10]=document.getElementById("txtAgree_No").value;
	// items[11]=document.getElementById("txtAgree_Date").value;

	items[10] = "";
	items[11] = "";

	items[12] = document.getElementById("txtsub_Amount").value;
	items[13] = document.getElementById("txtParticular").value;

	items[14] = document.getElementById("adjyear").value;
	items[15] = document.getElementById("adjmonth").value;
	if (priorsince == 1) {
		items[16] = document.getElementById("paymentreceipt").value;
		items[17] = document.getElementById("receiptno").value;
		// items[17]=selected_text;

	} else {
		items[16] = document.getElementById("paymentreceipt1").value;
		items[17] = document.getElementById("receiptno1").value;
	}
	//items[0]=document.getElementById("txtSL_code").value;
	// items[1]=document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text;
	tbody = document.getElementById("grid_body");
	var mycurrent_row = document.createElement("TR");
	seq = seq + 1;
	mycurrent_row.id = seq;
	// alert("row ID"+mycurrent_row.id);
	var cell = document.createElement("TD");
	var anc = document.createElement("A");
	var url = "javascript:loadTable('" + mycurrent_row.id + "')";
	anc.href = url;
	var txtedit = document.createTextNode("EDIT");
	anc.appendChild(txtedit);
	cell.appendChild(anc);
	mycurrent_row.appendChild(cell);
	var i = 0;
	var cell2;

	cell2 = document.createElement("TD");

	var H_code = document.createElement("input");
	H_code.type = "hidden";
	H_code.name = "H_code";
	H_code.value = items[0];
	cell2.appendChild(H_code);
	var currentText = document.createTextNode(items[0] + "-" + items[1]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var CR_DR_type = document.createElement("input");
	CR_DR_type.type = "hidden";
	CR_DR_type.name = "CR_DR_type";
	CR_DR_type.value = items[2];
	cell2.appendChild(CR_DR_type);
	var currentText = document.createTextNode(items[2]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_type = document.createElement("input");
	SL_type.type = "hidden";
	SL_type.name = "SL_type";
	SL_type.value = items[3];
	cell2.appendChild(SL_type);
	var currentText = document.createTextNode(items[4]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "SL_code";
	SL_code.value = items[5];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[6]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var Bill_NO = document.createElement("input");
	Bill_NO.type = "hidden";
	Bill_NO.name = "Bill_NO";
	Bill_NO.value = items[7];
	cell2.appendChild(Bill_NO);
	var currentText = document.createTextNode(items[7]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var Bill_date = document.createElement("input");
	Bill_date.type = "hidden";
	Bill_date.name = "Bill_date";
	Bill_date.value = items[8];
	cell2.appendChild(Bill_date);
	var currentText = document.createTextNode(items[8]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var Bill_type = document.createElement("input");
	Bill_type.type = "hidden";
	Bill_type.name = "Bill_type";
	Bill_type.value = items[9];
	cell2.appendChild(Bill_type);

	var Agree_No = document.createElement("input");
	Agree_No.type = "hidden";
	Agree_No.name = "Agree_No";
	Agree_No.value = items[10];
	// Agree_No.style.display='none';
	cell2.appendChild(Agree_No);

	var Agree_date = document.createElement("input");
	Agree_date.type = "hidden";
	Agree_date.name = "Agree_date";
	Agree_date.value = items[11];
	// Agree_date.style.display='none';
	cell2.appendChild(Agree_date);

	var sl_amt = document.createElement("input");
	sl_amt.type = "hidden";
	sl_amt.name = "sl_amt";
	sl_amt.value = items[12];
	cell2.appendChild(sl_amt);

	var particular = document.createElement("input"); // Particulars Added to
	// grid b4 the Amount
	// Text Node but after
	// amount hidden box
	particular.type = "hidden";
	particular.name = "particular";
	particular.value = items[13];
	// particular.style.display='none';
	cell2.appendChild(particular);

	var currentText = document.createTextNode(items[12]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var adj_year = document.createElement("input");
	adj_year.type = "hidden";
	adj_year.name = "adj_year";
	adj_year.value = items[14];
	cell2.appendChild(adj_year);
	var currentText = document.createTextNode(items[14]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var adj_month = document.createElement("input");
	adj_month.type = "hidden";
	adj_month.name = "adj_month";
	adj_month.value = items[15];
	cell2.appendChild(adj_month);
	var currentText = document.createTextNode(items[15]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var doc_type = document.createElement("input");
	doc_type.type = "hidden";
	doc_type.name = "doc_type";
	doc_type.value = items[16];
	cell2.appendChild(doc_type);
	var currentText = document.createTextNode(items[16]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var doc_no = document.createElement("input");
	doc_no.type = "hidden";
	doc_no.name = "doc_no";
	doc_no.value = items[17];
	cell2.appendChild(doc_no);
	var currentText = document.createTextNode(items[17]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	tbody.appendChild(mycurrent_row);
	clear_main_fields();
}

function clear_main_fields() {
	document.frmJournal_General.rad_sub_CR_DR[0].disabled = false;
	document.frmJournal_General.rad_sub_CR_DR[1].disabled = false;
	document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
	document.getElementById("offlist_div_trans").style.display = 'none';
	document.getElementById("emplist_div_trans").style.display = 'none';

	document.getElementById("txtAcc_HeadCode").value = "";
	// document.getElementById("txtAcc_HeadCode").readOnly=false;
	document.getElementById("txtAcc_HeadDesc").value = "";
	document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
	document.getElementById("cmbSL_type").value = "";
	document.getElementById("cmbSL_Code").value = "";
	document.getElementById("txtBill_NO").value = "";
	document.getElementById("txtBill_date").value = "";
	document.getElementById("txtBill_type").value = "";
	document.getElementById("txtUnitId").value = "";
	document.getElementById("adjyear").value = "";
	document.getElementById("adjmonth").value = "";

	document.getElementById("paymentreceipt").value = "";
	document.getElementById("receiptno").value = "";

	document.getElementById("paymentreceipt1").value = "";
	document.getElementById("receiptno1").value = "";

	// document.getElementById("txtsub_Recei_from").value="";
	document.getElementById("txtsub_Amount").value = "";
	document.getElementById("txtParticular").value = "";
	var cmbSL_type = document.getElementById("cmbSL_type");
	cmbSL_type.innerHTML = "";
	var option = document.createElement("OPTION");
	option.text = "--Select Type--";
	option.value = "";
	try {
		cmbSL_type.add(option);
	} catch (errorObject) {
		cmbSL_type.add(option, null);
	}
	document.getElementById("offlist_div_trans").style.display = 'none';
	var cmbSL_Code = document.getElementById("cmbSL_Code");
	clear_Combo(cmbSL_Code);

	document.frmJournal_General.cmdadd.style.display = 'block';
	document.frmJournal_General.cmdupdate.style.display = 'none';
	document.frmJournal_General.cmddelete.disabled = true;

	document.getElementById("since").style.display = 'block';
	document.getElementById("prior").style.display = 'none';
	document.getElementById("since2007").style.display = 'block';
	document.getElementById("prior2007").style.display = 'none';
	priorsince = 1;
}

function update_GRID() {
	if (document.getElementById("txtAcc_HeadCode").value.length == 0) {
		alert("Enter A/c Head Code");
		return false;
	}
	//	if (document.getElementById("cmbSL_type").length > 1
	// && document.getElementById("cmbSL_type").value == "") {
	//
	// alert("Select a Sub-Ledger Type");
	// return false;
	//
	// }

	/*
	 * cmbSL_type=document.getElementById("cmbSL_type").value; if(cmbSL_type==1 ||
	 * cmbSL_type==2 || cmbSL_type==11) {
	 * if(document.getElementById("txtBill_NO").value.length==0) { alert("Enter
	 * the Bill number"); document.getElementById("txtBill_NO").focus(); return
	 * false; } if(document.getElementById("txtBill_date").value.length==0) {
	 * alert("Enter the Bill Date");
	 * document.getElementById("txtBill_date").focus(); return false; }
	 * if(document.getElementById("txtBill_type").value.length==0) {
	 * alert("Enter the Bill Type");
	 * document.getElementById("txtBill_type").focus(); return false; } }
	 * if(cmbSL_type==1 || cmbSL_type==2 || cmbSL_type==10 || cmbSL_type==11) {
	 * if(document.getElementById("txtAgree_No").value.length==0) { alert("Enter
	 * the Agreement Number"); document.getElementById("txtAgree_No").focus();
	 * return false; }
	 * if(document.getElementById("txtAgree_Date").value.length==0) {
	 * alert("Enter the Agreement Date");
	 * document.getElementById("txtAgree_Date").focus(); return false; } }
	 */
	/*
	 * if(document.getElementById("txtsub_Recei_from").value.length==0) {
	 * alert("Enter the value in Received From Field");
	 * document.getElementById("txtsub_Recei_from").focus(); return false; }
	 */

	if (document.getElementById("txtsub_Amount").value.length == 0) {
		alert("Enter the Amount ");
		document.getElementById("txtsub_Amount").focus();
		return false;
	}

	var w = document.frmJournal_General.receiptno.selectedIndex;
	var selected_text = document.frmJournal_General.receiptno.options[w].text;

	var exist = document.getElementById("txtAcc_HeadCode").value;
	var items = new Array();

	items[0] = document.getElementById("txtAcc_HeadCode").value;
	items[1] = document.getElementById("txtAcc_HeadDesc").value;
	if (document.frmJournal_General.rad_sub_CR_DR[0].checked == true)
		items[2] = document.frmJournal_General.rad_sub_CR_DR[0].value;
	else if (document.frmJournal_General.rad_sub_CR_DR[1].checked == true)
		items[2] = document.frmJournal_General.rad_sub_CR_DR[1].value;
	items[3] = document.getElementById("cmbSL_type").value;
	if (document.getElementById("cmbSL_type").value == "") {
		//items[4]="Not Available";
		items[4] = "";// document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;
		// alert("code"+items[4]+"ff");
	} else
		items[4] = document.getElementById("cmbSL_type").options[document
				.getElementById("cmbSL_type").selectedIndex].text;

	items[5] = document.getElementById("cmbSL_Code").value;
	if (document.getElementById("cmbSL_Code").value == "") {
		items[6] = "";// "Not Available";
		// alert("code"+items[6]+"ff");
	} else
		items[6] = document.getElementById("cmbSL_Code").options[document
				.getElementById("cmbSL_Code").selectedIndex].text;
	// items[7]=document.getElementById("txtsub_Recei_from").value;
	items[7] = document.getElementById("txtBill_NO").value;
	items[8] = document.getElementById("txtBill_date").value;
	items[9] = document.getElementById("txtBill_type").value;
	// items[10]=document.getElementById("txtAgree_No").value;
	// items[11]=document.getElementById("txtAgree_Date").value;

	items[10] = "";
	items[11] = "";

	items[12] = document.getElementById("txtsub_Amount").value;
	items[13] = document.getElementById("txtParticular").value;

	items[14] = document.getElementById("adjyear").value;
	items[15] = document.getElementById("adjmonth").value;
	if (priorsince == 1) {
		items[16] = document.getElementById("paymentreceipt").value;
		items[17] = document.getElementById("receiptno").value;
		// items[17]=selected_text;
	} else {
		items[16] = document.getElementById("paymentreceipt1").value;
		items[17] = document.getElementById("receiptno1").value;
	}

	var r = document.getElementById(com_id);
	var rcells = r.cells;
	try {
		rcells.item(1).firstChild.value = items[0];
	} catch (e) {
	}
	try {
		rcells.item(1).lastChild.nodeValue = items[0] + "-" + items[1];
	} catch (e) {
	}

	try {
		rcells.item(2).firstChild.value = items[2];
	} catch (e) {
	}
	try {
		rcells.item(2).lastChild.nodeValue = items[2];
	} catch (e) {
	}

	try {
		rcells.item(3).firstChild.value = items[3];
	} catch (e) {
	}
	try {
		rcells.item(3).lastChild.nodeValue = items[4];
	} catch (e) {
	}

	try {
		rcells.item(4).firstChild.value = items[5];
	} catch (e) {
	}
	try {
		rcells.item(4).lastChild.nodeValue = items[6];
	} catch (e) {
	}

	try {
		rcells.item(5).firstChild.value = items[7];
	} catch (e) {
	}
	try {
		rcells.item(5).lastChild.nodeValue = items[7];
	} catch (e) {
	}

	try {
		rcells.item(6).firstChild.value = items[8];
	} catch (e) {
	}
	try {
		rcells.item(6).lastChild.nodeValue = items[8];
	} catch (e) {
	}
	rcells.item(7).firstChild.value = items[9];
	var nex_cell = rcells.item(7).firstChild.nextSibling;
	nex_cell.value = items[10];
	var nex_cell = nex_cell.nextSibling;
	nex_cell.value = items[11];
	var nex_cell = nex_cell.nextSibling;
	nex_cell.value = items[12];
	var nex_cell = nex_cell.nextSibling;
	nex_cell.value = items[13];
	rcells.item(7).lastChild.nodeValue = items[12];

	try {
		rcells.item(8).firstChild.value = items[14];
	} catch (e) {
	}
	try {
		rcells.item(8).lastChild.nodeValue = items[14];
	} catch (e) {
	}

	try {
		rcells.item(9).firstChild.value = items[15];
	} catch (e) {
	}
	try {
		rcells.item(9).lastChild.nodeValue = items[15];
	} catch (e) {
	}

	try {
		rcells.item(10).firstChild.value = items[16];
	} catch (e) {
	}
	try {
		rcells.item(10).lastChild.nodeValue = items[16];
	} catch (e) {
	}

	try {
		rcells.item(11).firstChild.value = items[17];
	} catch (e) {
	}
	try {
		rcells.item(11).lastChild.nodeValue = items[17];
	} catch (e) {
	}

	alert("Record Updated");
	clearall();
}

function delete_GRID() {
	if (confirm("Do you want to delete ?")) {
		var tbody = document.getElementById("mytable");
		var r = document.getElementById(com_id);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		clearall();
	}
}


function dateCheck(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}
function check_Date(req,datechk)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert("Flag----->"+flag);
            if(flag=="success")
              {
                 //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
              }
            else if(flag=="failure")
            {
            	datechk.value=""; 
            	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
            	datechk.focus();
            	document.getElementById("butSub").disabled=true;
            	
            	document.getElementById("txtReceipt_No").value="";
                 
            }
            else if(flag=="success1")
            {
               //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
            }
           else if(flag=="failure1")
           {
        	  alert("Document Date is Greater than DATE_OF_CLOSURE");
        	  datechk.value=""; 
          		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
          		datechk.focus();
          		document.getElementById("butSub").disabled=true;
          		document.getElementById("txtReceipt_No").value="";
           }
           else 
        	   {
        	    datechk.value=""; 
        	    alert("Date Value is Null");
           		datechk.focus();
           		document.getElementById("butSub").disabled=true;
           		document.getElementById("txtReceipt_No").value="";
        	   }
        }
    }
}




/////////////////////////////////////////////   clearall() by User // ///////////////////////////////////////////////////

function clearall() {
	document.getElementById("offlist_div_trans").style.display = 'none';
	document.getElementById("emplist_div_trans").style.display = 'none';

	document.getElementById("txtAcc_HeadCode").value = "";
	// document.getElementById("txtAcc_HeadCode").readOnly=false;
	document.getElementById("txtAcc_HeadDesc").value = "";
	document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
	document.getElementById("cmbSL_type").value = "";
	document.getElementById("cmbSL_Code").value = "";
	// document.getElementById("txtsub_Recei_from").value="";
	document.getElementById("txtBill_NO").value = "";
	document.getElementById("txtBill_date").value = "";
	document.getElementById("txtBill_type").value = "";
	document.getElementById("adjyear").value = "";
	document.getElementById("adjmonth").value = 1;
	document.getElementById("txtUnitId").value = "";
	document.getElementById("paymentreceipt").value = "";
	document.getElementById("receiptno").value = "";

	document.getElementById("paymentreceipt1").value = "";
	document.getElementById("receiptno1").value = "";

	document.getElementById("txtsub_Amount").value = "";
	document.getElementById("txtParticular").value = "";
	var cmbSL_type = document.getElementById("cmbSL_type");
	cmbSL_type.innerHTML = "";
	var option = document.createElement("OPTION");
	option.text = "--Select Type--";
	option.value = "";
	try {
		cmbSL_type.add(option);
	} catch (errorObject) {
		cmbSL_type.add(option, null);
	}
	document.getElementById("offlist_div_trans").style.display = 'none';

	var cmbSL_Code = document.getElementById("cmbSL_Code");
	clear_Combo(cmbSL_Code);

	document.frmJournal_General.cmdadd.style.display = 'block';
	document.frmJournal_General.cmdupdate.style.display = 'none';
	document.frmJournal_General.cmddelete.disabled = true;

	document.getElementById("since").style.display = 'block';
	document.getElementById("prior").style.display = 'none';
	document.getElementById("since2007").style.display = 'block';
	document.getElementById("prior2007").style.display = 'none';
	priorsince = 1;

}
function call_clr() {
	// document.getElementById("txtAmount").value="";
	document.getElementById("txtCheque_NO").value = "";
	document.getElementById("txtCheque_date").value = "";
	document.getElementById("txtRemarks").value = "";

	document.getElementById("cmbMas_SL_type").value = "";
	// clear_Combo(document.getElementById("cmbMas_SL_Code"));
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
}
function clrForm() {
	if (window.confirm("Do you want to clear ALL fields ?")) {
		call_clr();
	}
}

/////////////////////////////////////////////   checkNull() by User // ///////////////////////////////////////////////////

function checkNull() {

	var tbody = document.getElementById("grid_body");
	// alert("tbody.rows.length :"+tbody.rows.length);
	if (document.getElementById("cmbAcc_UnitCode").value == "") {
		alert("Select the Account Unit code");
		// document.getElementById("txtAcc_HeadDesc").focus();
		return false;
	}
	if (document.getElementById("cmbOffice_code").value == "") {
		alert("Select the Office Code");
		// document.getElementById("cmbOffice_code").focus();
		return false;
	}
	if (document.getElementById("txtCrea_date").value.length == 0) {
		alert("Enter the Date of Creation");
		// document.getElementById("txtCrea_date").focus();
		return false;
	}
        if (document.getElementById("txtRemarks").value== "") {
		alert("Enter Remarks");
		// document.getElementById("txtCrea_date").focus();
		return false;
	}
	

	if (document.getElementById("cmbMas_SL_type").value == "") {
		//if(document.getElementById("cmbMas_SL_Code").value=="")
		// {
		alert("Select The Journal Type in General");
		return false;
		// }
	}

	if (document.getElementById("cmbMas_SL_type").value != ""
			&& (document.getElementById("cmbMas_SL_type").value == 6 || document
					.getElementById("cmbMas_SL_type").value == 7)) {
		if (document.getElementById("txtCheque_NO").value.length == 0
				|| document.getElementById("txtCheque_date").value.length == 0) {
			alert("Enter Both Cheque Number and Date in General");
			return false;
		}
	}
	if (document.getElementById("cmbMas_SL_type").value != ""
			&& (document.getElementById("cmbMas_SL_type").value == 72)) {
		if (document.getElementById("cashyear").value.length == 0
				|| document.getElementById("cashmonth").value.length == 0) {
			alert("Enter Both Adjusted Cash book Year and Adjusted Cash book Month");
			return false;
		} else {
			v = new Date();
			mn = v.getMonth();
			mn = parseInt(mn) + 1;
			yr = v.getFullYear();

			var cashyear = document.getElementById("cashyear").value;
			var cashmonth = document.getElementById("cashmonth").value;

			if (cashyear.length < 4) {
				alert("Give Correct format(YYYY) of Adjusted Cash book Year");
				return false;
			}
			if (parseInt(cashmonth) > 12 || parseInt(cashmonth) < 1) {
				alert("Give Correct format(MM) of Adjusted Cash book Month");
				return false;
			}

			if (parseInt(cashyear) > parseInt(yr)) {
				alert("Adjusted Cash book Year should not be greater than current year");
				return false;
			}
			if (parseInt(cashyear) == parseInt(yr)) {
				if (parseInt(cashmonth) > parseInt(mn)) {
					alert("Adjusted Cash book month should not be greater than current month");
					return false;
				}
			}
		}
	}

	/*if(document.getElementById("txtAmount").value.length==0)
	 {
	 alert("Enter the Total Amount in General");
	 // document.getElementById("txtAmount").focus();
	 return false;    
	 }*/
	if (tbody.rows.length == 0) {
		alert("Enter the Details Part");
		// document.getElementById("txtAmount").focus();
		return false;
	}

	if (tbody.rows.length > 0) {
		var check_amt = 0;
		var cr_amt = 0;
		var db_amt = 0;
		rows = tbody.getElementsByTagName("tr");
		for (i = 0; i < rows.length; i++) {
			var cells = rows[i].cells;
			// alert(cells.item(7).lastChild.nodeValue);
			if (cells.item(2).lastChild.nodeValue == 'CR')
				cr_amt = parseFloat(cr_amt)
						+ parseFloat(cells.item(7).lastChild.nodeValue);
			else
				db_amt = parseFloat(db_amt)
						+ parseFloat(cells.item(7).lastChild.nodeValue);

		} //alert(cr_amt+" "+db_amt);
		// alert(document.getElementById("txtAmount").value+" "+check_amt);
		/*
		 * for the case of entries of debit must if(parseFloat(db_amt)<=0) {
		 * alert("Debit amount must be specified"); return false; }
		 */
		if (parseFloat(cr_amt) != parseFloat(db_amt)) // Either CR or DR must
		// equal in total
		{
			alert("Amount doesn't Tally.. Difference "
					+ (parseFloat(cr_amt) - parseFloat(db_amt)));
			return false;
		}
		var flaf = 0;

		for (i = 0; i < rows.length; i++) {
			var cells = rows[i].cells;
			// alert(cells.item(8).lastChild.nodeValue);
			if (cells.item(8).lastChild.nodeValue != '') {
				flaf = 1;
			}

		}

		if (flaf == 0) {
			alert('Atleast One Detail Entry should have Adjustment Voucher Details');
			return false;
		}

	}
	return true;
}

function enable_cheque(Jr_type) {
var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;

if(cmbMas_SL_type==66)
{
var msg1="For TCA Accepting Head 901002(DR), Checking for Doc No with A/c Head 901001(CR) for the Selected Unit, Originated/Accepted Year and Month in Doc Type is Done Here";
document.getElementById("txtIndicationMsg").value=msg1;
document.getElementById("txtIndicationMsg").readOnly=true;
document.getElementById("txtIndicationMsg").style.color = 'red';
document.getElementById("txtIndicationMsg").style.fontWeight = 'bold';
document.getElementById("txtIndicationMsg").style.fontSize = "15px";
document.getElementById("txtIndicationMsg").style.fontFamily='courier'

}else if(cmbMas_SL_type==63)
{
var msg1="For TDA Accepting Head 900109(CR), Checking for Doc No with A/c Head 900108(DR) for the Selected Unit, Originated/Accepted Year and Month in Doc Type is Done Here";
document.getElementById("txtIndicationMsg").value=msg1;
document.getElementById("txtIndicationMsg").readOnly=true;
document.getElementById("txtIndicationMsg").style.color = 'red';
document.getElementById("txtIndicationMsg").style.fontWeight = 'bold';
document.getElementById("txtIndicationMsg").style.fontSize = "15px";
document.getElementById("txtIndicationMsg").style.fontFamily='courier'

}else if(cmbMas_SL_type==64)
{
var msg1="For TDA Sucpence Head Clearance Head 900108(DR) Checking for Doc No with A/c Head 900109(CR) for the Selected Unit, Originated/Accepted Year and Month in Doc Type is Done Here";
document.getElementById("txtIndicationMsg").value=msg1;
document.getElementById("txtIndicationMsg").readOnly=true;
document.getElementById("txtIndicationMsg").style.color = 'red';
document.getElementById("txtIndicationMsg").style.fontWeight = 'bold';
document.getElementById("txtIndicationMsg").style.fontSize = "15px";
document.getElementById("txtIndicationMsg").style.fontFamily='courier'

}else if(cmbMas_SL_type==67)
{
var msg1="For TCA Sucpence Head Clearance Head 901001(CR) Checking for Doc No with A/c Head 901002(DR) for the Selected Unit, Originated/Accepted Year and Month in Doc Type is Done Here";
document.getElementById("txtIndicationMsg").value=msg1;
document.getElementById("txtIndicationMsg").readOnly=true;
document.getElementById("txtIndicationMsg").style.color = 'red';
document.getElementById("txtIndicationMsg").style.fontWeight = 'bold';
document.getElementById("txtIndicationMsg").style.fontSize = "15px";
document.getElementById("txtIndicationMsg").style.fontFamily='courier'

}


	document.frmJournal_General.rad_sub_CR_DR[0].disabled = false;
	document.frmJournal_General.rad_sub_CR_DR[1].disabled = false;
	document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
	document.getElementById("txtAcc_HeadCode").value = "";
	if (Jr_type == 56 || Jr_type == 57)
		document.getElementById("CHD").style.display = 'block';
	else {

		document.getElementById("txtCheque_NO").value = "";
		document.getElementById("txtCheque_date").value = "";
		document.getElementById("CHD").style.display = 'none';
	}
}

function call_mainJSP_script(fromcal_dateCtrl, blr_flag) // ///////Calender
// control return
// value handling
{
	if (blr_flag == 1) // which is used to find the receipt or voucher or
	// payment (creation) date calling field,if so check
	// trial balance
	{
		call_clr();
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var TB_date = fromcal_dateCtrl.value;
		// alert(fromcal_dateCtrl.value+"b4url")
		if (fromcal_dateCtrl.value.length != 0) {
			var url = "../../../../../Receipt_SL.view?Command=check_TB&TB_date="
					+ TB_date
					+ "&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode
					+ "&cmbOffice_code=" + cmbOffice_code;
			// alert(url);
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				check_TB(req, fromcal_dateCtrl);
			}
			req.send(null);
		}
	}
}

function check_TB(req, dateCtrl) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

			if (flag == "success") {
				//doFunction('load_Receipt_No','null');                 // return
				// true;
			} else if (flag == "failure") {
				dateCtrl.value = "";
				alert("Trial Balance Closed");// return false;//
				dateCtrl.focus();
				document.getElementById("txtCrea_date").value = "";
			} else if (flag == "finyear") {
				// This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
				dateCtrl.value = "";
				alert("Cash Book Control Not Found ");// return false;//
				dateCtrl.focus();
				document.getElementById("txtCrea_date").value = "";
			}
			dateCheck(dateCtrl); 
		}
	}
}

function limit_amt_journal(field, e) {

	var Journal_Creation_date = document.getElementById("txtCrea_date").value
			.split("/");
	;
	// alert(Journal_Creation_date[1]);

	var unicode = e.charCode ? e.charCode : e.keyCode;

	if (field.value.length < 17) {
		if (field.value.length == 14 && field.value.indexOf('.') == -1)
			field.value = field.value + '.';
		if (unicode != 8 && unicode != 9) {

			if (Journal_Creation_date[1] <= 8
					&& Journal_Creation_date[2] <= 2007) {

				if (unicode < 45 || unicode == 47 || unicode > 57) // It allow
					// the
					// negative
					// amount
					return false
			} else {

				if (unicode < 45 || unicode == 47 || unicode > 57) // It won't
					// allow the
					// negative
					// amount
					return false
			}

		}
	} else
		return false;
}

function checkamount(field) {
	amt = field.value;
	if (amt.indexOf(".") != amt.lastIndexOf(".")) {
		alert("Enter a Valid Amount");
		field.value = "";
		field.focus();
	}

	var GetDate = document.getElementById("txtCrea_date").value;
	var mon = GetDate.substr(3, 2);
	var yr = GetDate.substr(6, 4);

	if (mon == "03" && (yr == "2009" || yr == "2008")) {

	} else {
		if (amt < 0) {
			alert("Negative Amount Not Allowed");
			field.value = "";
			field.focus();
		}
	}

	if (document.getElementById("cmbMas_SL_type").value != ""
			&& (document.getElementById("cmbMas_SL_type").value == 72)) {
		if (window
				.confirm("If this Amount is for more than one employee?,please give the break up by select sub-ledger type and sub-ledger code \n Do you want to select it,click 'OK'?")) {
			if (document.getElementById("cmbSL_type").value == "") {
				alert("Select a Sub-Ledger Type");
				return false;
			}
			if (document.getElementById("cmbSL_Code").value == "") {
				alert("Select a Sub-Ledger Type");
				return false;
			}

		}

	}

}

function getxmlhttpObject() {
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
function payreceipt() {
	document.getElementById("receiptno").length = 1;
	var adjyear = document.getElementById("adjyear").value;
	var adjmonth = document.getElementById("adjmonth").value;
	// var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var txtUnitId = document.getElementById("txtUnitId").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var doctype = document.getElementById("paymentreceipt").value;
	var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;

	xmlhttp = getxmlhttpObject();
	if (txtUnitId == "") {
		alert("Select Originated  / Accepted Unit in the Field");
		document.getElementById("adjyear").focus();
	} else if (adjyear == "") {
		alert("Enter Originated/Accepted Year in the Field");
		document.getElementById("adjyear").focus();
	} else if (adjmonth == "") {
		alert("Select Originated/Accepted Month in the Field");
		document.getElementById("adjmonth").focus();
	} else if (doctype == "") {
		alert("Select DocType in the Field");
		document.getElementById("paymentreceipt").focus();
	} else {
		if (cmbMas_SL_type == 63) {
			var url = "../../../../../Rectification_Journal_Create?command=paymentreceipt&cmbAcc_UnitCode="
					+ txtUnitId

					+ "&adjyear="
					+ adjyear
					+ "&adjmonth="
					+ adjmonth
					+ "&doctype=" + doctype + "&acc_hd_code=900108" + "&cr_dr_indicator=DR";
		} else if (cmbMas_SL_type == 64) {
			var url = "../../../../../Rectification_Journal_Create?command=paymentreceipt&cmbAcc_UnitCode="
					+ txtUnitId

					+ "&adjyear="
					+ adjyear
					+ "&adjmonth="
					+ adjmonth
					+ "&doctype=" + doctype + "&acc_hd_code=900109"+ "&cr_dr_indicator=CR";
		} else if (cmbMas_SL_type == 66) {
			var url = "../../../../../Rectification_Journal_Create?command=paymentreceipt&cmbAcc_UnitCode="
					+ txtUnitId

					+ "&adjyear="
					+ adjyear
					+ "&adjmonth="
					+ adjmonth
					+ "&doctype=" + doctype + "&acc_hd_code=901001"+ "&cr_dr_indicator=CR";

		} else if (cmbMas_SL_type == 67) {
			var url = "../../../../../Rectification_Journal_Create?command=paymentreceipt&cmbAcc_UnitCode="
					+ txtUnitId

					+ "&adjyear="
					+ adjyear
					+ "&adjmonth="
					+ adjmonth
					+ "&doctype=" + doctype + "&acc_hd_code=901002"+ "&cr_dr_indicator=DR";
		}
		//alert(url);
		url = url + "&sid=" + Math.random();
		xmlhttp.open("GET", url, true);
		xmlhttp.onreadystatechange = stateChanged;
		xmlhttp.send(null);
	}
}
function callHeadCode(parameter)
{
	var hCode=parameter;
	 var url="../../../../../Imprest_Journal_Create?Command=hCodeChecking&hCode="+hCode;   
	  var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
        responseCheck(req);
     }  ; 
             req.send(null);
	
}
function  responseCheck(req)
{
	if(req.readyState==4)
	   {
		       if(req.status==200)
		       { 
		    	   
		           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
		           var tagcommand=baseResponse.getElementsByTagName("command")[0];
		           var Command=tagcommand.firstChild.nodeValue;
		           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		           
			        if(flag=="success")
			        {
			         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
			         document.getElementById("txtAcc_HeadCode").value=hid;
			         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
			         var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
			         
			         var sl_man = baseResponse.getElementsByTagName("sl_man")[0].firstChild.nodeValue;
			         
			         
			         document.getElementById("txtAcc_HeadCode").value=hid;
			         document.getElementById("txtAcc_HeadDesc").value=hdesc;
			      
			       var cmbSL_type=document.getElementById("cmbSL_type");   
		     try{   
		     
		       
		       if(SL_YN=="Y")
		       {
		            
		            
		            if(sl_man == "Y" ) 
		            {
		              isMan.account_head_status = true;     
		            }
		            
		            var items_SLcode=new Array();
		            var items_SLdesc=new Array();
		            var SLCODE=baseResponse.getElementsByTagName("SLCODE");
		            var SLDESC=baseResponse.getElementsByTagName("SLDESC");
		            for(var k=0;k<SLCODE.length;k++)
		            {
		                items_SLcode[k]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;
		                items_SLdesc[k]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;
		            }
		            
		            cmbSL_type.innerHTML="";
		            var option=document.createElement("OPTION");
		            option.text="--Select Type--";
		            option.value="";
		            try
		            {
		                cmbSL_type.add(option);
		            }catch(errorObject)
		            {
		                cmbSL_type.add(option,null);
		            }
		            for(var k=0;k<SLCODE.length;k++)
		            {   
		              var option=document.createElement("OPTION");
		              option.text=items_SLdesc[k];
		              option.value=items_SLcode[k];
		               try
		              {
		                  cmbSL_type.add(option);
		              }
		              catch(errorObject)
		              {
		                  cmbSL_type.add(option,null);
		              }
		            }
		            
		            if(common_cmbSL_type=="")
		                document.getElementById("cmbSL_type").value="";
		            else
		                document.getElementById("cmbSL_type").value=common_cmbSL_type;    //set from grid
		       }
		        
		     }catch(e)
		     {  
		       alert(e.description);
		       return false;
		     }   


		        if(SL_YN=="N" || SL_YN=="null")
		           {    
		                cmbSL_type.innerHTML=""; 
		                var option=document.createElement("OPTION");
		                option.text="--Select Type--";
		                option.value="";
		                try
		                {
		                    cmbSL_type.add(option);
		                }catch(errorObject)
		                {
		                    cmbSL_type.add(option,null);
		                }
		            }
          
				    }
				     else if(flag=="failure")
				     {
				         alert("This Account HeadCode cannot be used in this module");
				         document.getElementById("txtAcc_HeadCode").value="";
				         document.getElementById("txtAcc_HeadDesc").value="";
				         document.getElementById("txtAcc_HeadCode").focus();
				     }
				     
				       common_cmbSL_type="";
					     }
					   }
	
}
function stateChanged() {
	var flag, command, response;

	if (xmlhttp.readyState == 4) {

		if (xmlhttp.status == 200) {
			response = xmlhttp.responseXML.getElementsByTagName("response")[0];
			command = response.getElementsByTagName("command")[0].firstChild.nodeValue;
			flag = response.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (command == "paymentreceipt") {

				if (flag == 'success') {

					try {

						var len = response.getElementsByTagName("receiptno").length;
						if (len != 0) {
							var billno = document.getElementById("receiptno");

							var items_id = new Array();
							var items_desc = new Array();
							for ( var i = 0; i < len; i++) {
								items_id[i] = response
										.getElementsByTagName("receiptno")[i].firstChild.nodeValue;
								items_desc[i] = response
										.getElementsByTagName("receiptno")[i].firstChild.nodeValue;
								// alert('minor'+items_desc[i]);
							}
							clear_Combo(billno);

							// alert('here second');
							for ( var k = 0; k < len; k++) {
								//alert(items_name[k]);
								var option = document.createElement("OPTION");
								option.text = items_desc[k];
								option.value = items_id[k];

								try {
									billno.add(option);

								} catch (errorObject) {
									billno.add(option, null);

									// alert('error');
								}
							}
						} else {
							alert("Doc No Does Not Exist");
						}
					} catch (e) {
						alert("Error in lat" + e);
					}

				} else {
					var billno = document.getElementById("receiptno");
					clear_Combo(billno);
				}
			} else if (command == "headcheck") {

				if (flag == 'success') {
					if (addorcheck == 1)
						ADD_GRID();
					else if (addorcheck == 2)
						update_GRID();
				} else if (flag == "rjvupdated") {
					alert('Rectification Journal Voucher already Posted');
					if (addorcheck == 1)
						ADD_GRID();
					else if (addorcheck == 2)
						update_GRID();
				} else {
					return true;
				}
				//else {
				//					alert('Account Head is not found in the Selected Voucher ');
				// document.getElementById("receiptno").value = "";
				// return false;
				// }

			}
		}
	}
}

function clear_Combo(combo) {
	//alert(combo.id)
	var cmbSL_Code = document.getElementById(combo.id);
	cmbSL_Code.innerHTML = "";
	var option = document.createElement("OPTION");
	option.text = "--Select--";
	option.value = "";
	try {
		cmbSL_Code.add(option);
	} catch (errorObject) {
		cmbSL_Code.add(option, null);
	}
}

var priorsince = 1;
function tohidedoc() {
	var adjyear = document.getElementById("adjyear").value;
	if (adjyear == "") {
		alert('Please Enter Adjustment year');
		return false;
	}

	var adjmonth = document.getElementById("adjmonth").value;
	if (adjyear > 2007) {
		document.getElementById("since").style.display = 'block';
		document.getElementById("prior").style.display = 'none';
		document.getElementById("since2007").style.display = 'block';
		document.getElementById("prior2007").style.display = 'none';
		priorsince = 1;
	} else if (adjyear == 2007) {
		if (adjmonth < 9) {
			document.getElementById("since").style.display = 'none';
			document.getElementById("prior").style.display = 'block';
			document.getElementById("since2007").style.display = 'none';
			document.getElementById("prior2007").style.display = 'block';
			priorsince = 2;
		} else {
			document.getElementById("since").style.display = 'block';
			document.getElementById("prior").style.display = 'none';
			document.getElementById("since2007").style.display = 'block';
			document.getElementById("prior2007").style.display = 'none';
			priorsince = 1;
		}
	} else if (adjyear < 2007) {
		document.getElementById("since").style.display = 'none';
		document.getElementById("prior").style.display = 'block';
		document.getElementById("since2007").style.display = 'none';
		document.getElementById("prior2007").style.display = 'block';
		priorsince = 2;
	}

}

var payreceipt;

function payreceiptdetails() {

	var w = document.frmJournal_General.receiptno.selectedIndex;
	var selected_text = document.frmJournal_General.receiptno.options[w].text;

	var adjyear = document.getElementById("adjyear").value;
	var adjmonth = document.getElementById("adjmonth").value;

	var type = document.getElementById("paymentreceipt").value;
	// var docno=document.getElementById("receiptno").value;

	var docno = selected_text;

//	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
//	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbAcc_UnitCode = document.getElementById("txtUnitId").value;
       // alert(cmbAcc_UnitCode);
	//var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	if (adjyear == "") {
		alert('Please Enter Adjustment year');
		return false;
	}
	if (type == "") {
		alert('Please Select Doc.Type');
		return false;
	}
	if (docno == "") {
		alert('Please Select Doc.No');
		return false;
	}

	if (payreceipt && payreceipt.open && !payreceipt.closed) {
		payreceipt.resizeTo(500, 500);
		payreceipt.moveTo(250, 250);
		payreceipt.focus();
	} else {
		payreceipt = null;
	}

	payreceipt = window.open(
			"Rectification_Pay_Receipt_Details.jsp?cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&yr=" + adjyear + "&mon=" + adjmonth + "&recNo=" + docno
					+ "&type=" + type, "ReceiptDateSearch",
			"status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	payreceipt.moveTo(250, 250);
	payreceipt.focus();

}

var addorcheck = 0;

function acheadcodecheck(chec) {
	var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	var adjyear = document.getElementById("adjyear").value;
	var adjmonth = document.getElementById("adjmonth").value;

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	/*
	 * if ((cmbMas_SL_type == 63) && (txtAcc_HeadCode != "900109")) {
	 * alert("Account Head Code Should be '900109' ");
	 * document.getElementById("txtAcc_HeadCode").focus(); } else if
	 * ((cmbMas_SL_type == 66) && (txtAcc_HeadCode != "901001")) {
	 * alert("Account Head Code Should be '901001' ");
	 * document.getElementById("txtAcc_HeadCode").focus(); }
	 */
         
//	if ((txtAcc_HeadCode == 900109) && (cmbMas_SL_type != 64) || (txtAcc_HeadCode == 900108) && (cmbMas_SL_type == 64) )   {
	if ((txtAcc_HeadCode == 900109) && (cmbMas_SL_type != 64))  {
		var paymentreceipt = document.getElementById("paymentreceipt").value;
		var receiptno = document.getElementById("receiptno").value;
		var txtUnitId = document.getElementById("txtUnitId").value;
		if (txtUnitId == "") {
			alert("Select Originated  / Accepted Unit in the Field~~~");
			return false;
		} else if (paymentreceipt == "") {
			alert("Select Doc Type in the Field~~~");
			return false;
		} else if (receiptno == "") {
			alert("Select Doc No in the Field~~~");
			return false;
		}
	}

    //   if ((txtAcc_HeadCode == 901002) && (cmbMas_SL_type != 67) || (txtAcc_HeadCode == 901001) && (cmbMas_SL_type == 67))  {
     if ((txtAcc_HeadCode == 901002) && (cmbMas_SL_type != 67))  {
		var paymentreceipt = document.getElementById("paymentreceipt").value;
		var receiptno = document.getElementById("receiptno").value;
		var txtUnitId = document.getElementById("txtUnitId").value;
		if (txtUnitId == "") {
			alert("Select Originated  / Accepted Unit in the Field~~~");
			return false;
		} else if (paymentreceipt == "") {
			alert("Select Doc Type in the Field~~~");
			return false;
		} else if (receiptno == "") {
			alert("Select Doc No in the Field~~~");
			return false;
		}
	}

	if (((cmbMas_SL_type == 64) && (txtAcc_HeadCode != 900108))
			&& ((cmbMas_SL_type == 64) && (txtAcc_HeadCode != 900109))) {
		alert("Account Head Code Should be '900108' or '900109' ");
		document.getElementById("txtAcc_HeadCode").focus();
	} else if (((cmbMas_SL_type == 67) && (txtAcc_HeadCode != "901001"))
			&& ((cmbMas_SL_type == 67) && (txtAcc_HeadCode != "901002"))) {
		alert("Account Head Code Should be '901001' or '901002' ");
		document.getElementById("txtAcc_HeadCode").focus();
	} else if (adjyear > year) {
		alert("Originated/Accepted  Year Should not greater than Current Year");
		document.getElementById("adjyear").focus();
	} else if ((adjmonth > month) && ((adjyear == year))) {
		alert("Originated/Accepted  Month Should not greater than Current Month");
		document.getElementById("adjmonth").focus();
	} else {
		if (chec == 1)
			addorcheck = 1;
		else if (chec == 2)
			addorcheck = 2;

		var w = document.frmJournal_General.receiptno.selectedIndex;
		var selected_text = document.frmJournal_General.receiptno.options[w].text;
		var adjyear = document.getElementById("adjyear").value;
		var adjmonth = document.getElementById("adjmonth").value;

		if (adjyear != "") {
			/*if (priorsince != 2) {
				var cmbAcc_UnitCode = document
						.getElementById("cmbAcc_UnitCode").value;
				var cmbOffice_code = document.getElementById("cmbOffice_code").value;
				var doctype = document.getElementById("paymentreceipt").value;
				var receiptno = selected_text;
				var headcode = document.getElementById("txtAcc_HeadCode").value;
			                    // alert("headcode"+headcode);
				xmlhttp = getxmlhttpObject();
				var url = "../../../../../Rectification_Journal_Create?command=headcheck&cmbAcc_UnitCode="
						+ cmbAcc_UnitCode
						+ "&cmbOffice_code="
						+ cmbOffice_code
						+ "&adjyear="
						+ adjyear
						+ "&adjmonth="
						+ adjmonth
						+ "&doctype="
						+ doctype
						+ "&headcode="
						+ headcode
						+ "&receiptno=" + receiptno + "";
				// alert(url);
				url = url + "&sid=" + Math.random();
				xmlhttp.open("GET", url, true);
				xmlhttp.onreadystatechange = stateChanged;
				xmlhttp.send(null);
			} else {*/
			if (addorcheck == 1)
				ADD_GRID();
			else if (addorcheck == 2)
				update_GRID();
			// }
		} else {
			if (addorcheck == 1)
				ADD_GRID();
			else if (addorcheck == 2)
				update_GRID();
		}
	}
}

function summa() {
	if (document.getElementById("adjyear").value.length == 0) {
		alert("Enter Adjustment Year");
		// document.getElementById("txtAmount").focus();
		return false;
	}
	if (priorsince == 1) {
		if (document.getElementById("paymentreceipt").value == "") {
			alert("Select Doc Type");
			// document.getElementById("txtAmount").focus();
			return false;
		}
		if (document.getElementById("receiptno").value == "") {
			alert("Select Receipt/Voucher No");
			// document.getElementById("txtAmount").focus();
			return false;
		}
	} else {
		if (document.getElementById("paymentreceipt1").value == "") {
			alert("Select Doc Type");
			// document.getElementById("txtAmount").focus();
			return false;
		}
		if (document.getElementById("receiptno1").value.length == "") {
			alert("Enter Adjustment Year");
			// document.getElementById("txtAmount").focus();
			return false;
		}
	}

}

function loadTransferUnit() {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	url = "../../../../../TDA_Raised_Create?command=loadTransferUnit1&txtUnitId="
			+ cmbAcc_UnitCode;

	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		TDA_Raised_ServletResponse(req);
	}
	req.send(null);
}

function TDA_Raised_ServletResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (Command == "loadTransferUnit") {
				if (flag == "success") {
					var txtUnitId = document.getElementById("txtUnitId");
					var child = txtUnitId.childNodes;
					for ( var i = child.length - 1; i > 1; i--) {
						txtUnitId.removeChild(child[i]);
					}
					var items_id = new Array();
					var items_name = new Array();
					var oid = baseResponse.getElementsByTagName("unit_id");
					for ( var k = 0; k < oid.length; k++) {
						items_id[k] = baseResponse
								.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
						items_name[k] = baseResponse
								.getElementsByTagName("unit_name")[k].firstChild.nodeValue;
						var option = document.createElement("OPTION");
						option.text = items_name[k];
						option.value = items_id[k];
						try {
							txtUnitId.add(option);
						} catch (errorObject) {
							txtUnitId.add(option, null);
						}
					}
				} else {
					document.getElementById("txtUnitId").value = "";
				}
			}
		}
	}
}

/*function toSetSubLedgerTypeCode1()
 {
 var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
 if((txtAcc_HeadCode==900109) || (txtAcc_HeadCode==901001))
 {
 var txtUnitId = document.getElementById("txtUnitId").value;		
 var txtUnitId_text = document.getElementById("txtUnitId").options[document.getElementById("txtUnitId").selectedIndex].text;	
 document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text=txtUnitId_text;
 document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].value=txtUnitId;	
 }
 }*/

function toSetSubLedgerTypeCode() {
	document.getElementById("paymentreceipt").value = "";
      var t1=  document.getElementById("cmbMas_SL_type").value;
     // alert(t1);
if(t1==92 ||t1==93){
}
else{
	var cmbSL_Code = document.getElementById("cmbSL_Code").value;
	document.getElementById("txtUnitId").value = cmbSL_Code;
        }

}

function checkSubLedgerMandatory() {
	var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
        
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
       
	if (cmbMas_SL_type == 64) {
		if ((txtAcc_HeadCode == 900109)) {
			var adjyear = document.getElementById("adjyear").value;
			var adjmonth = document.getElementById("adjmonth").value;
                        if((adjyear!="") && (adjmonth!=""))
                        {
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			} else {
				var Option = "test";
				var txtUnitId = 0;
				var txtAcc_HeadCode = document
						.getElementById("txtAcc_HeadCode").value;
				url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
						+ txtAcc_HeadCode
						+ "&Option="
						+ Option
						+ "&txtUnitId="
						+ txtUnitId;
				var req = getTransport();
				req.open("GET", url, true);
				req.onreadystatechange = function() {
					Mandatory(req);
				}
				req.send(null);
			}
                        }else{
                        var Option = "test";
				var txtUnitId = 0;
				var txtAcc_HeadCode = document
						.getElementById("txtAcc_HeadCode").value;
				url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
						+ txtAcc_HeadCode
						+ "&Option="
						+ Option
						+ "&txtUnitId="
						+ txtUnitId;
				var req = getTransport();
				req.open("GET", url, true);
				req.onreadystatechange = function() {
					Mandatory(req);
				}
				req.send(null);
                        }
		} else {
			var adjyear = document.getElementById("adjyear").value;
			var adjmonth = document.getElementById("adjmonth").value;
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007 && txtAcc_HeadCode!=900108) {
                       
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			} else {
				var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory(req);
					}
					req.send(null);
			}
		}

	} else if (cmbMas_SL_type == 67) {
		if ((txtAcc_HeadCode == 901002) || (txtAcc_HeadCode == 901001)) {
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month*****");
					return false;//false
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007 && txtAcc_HeadCode!=901001) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false; //false
			} else {
				var Option = "test";
				var txtUnitId = 0;
				var txtAcc_HeadCode = document
						.getElementById("txtAcc_HeadCode").value;
				url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
						+ txtAcc_HeadCode
						+ "&Option="
						+ Option
						+ "&txtUnitId="
						+ txtUnitId;
				var req = getTransport();
				req.open("GET", url, true);
				req.onreadystatechange = function() {
					Mandatory(req);
				}
				req.send(null);
			}
		} else {
			var adjyear = document.getElementById("adjyear").value;
			var adjmonth = document.getElementById("adjmonth").value;
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;//false
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory(req);
					}
					req.send(null);
				}
			} else if ((adjyear >= 2007) && (adjyear <= 2011)) {
				if (adjmonth > 3) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;//false
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;//false
			} else {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;//false
			}
		}
	} else {
		var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

		if ((txtAcc_HeadCode == 901001) || (txtAcc_HeadCode == 901002)
				|| (txtAcc_HeadCode == 900108) || (txtAcc_HeadCode == 900109)) {
			var adjyear = document.getElementById("adjyear").value;
			var adjmonth = document.getElementById("adjmonth").value;
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;//
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;//
			}  else {
			var Option = "test";
			var txtUnitId = 0;
			var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
			url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
					+ txtAcc_HeadCode
					+ "&Option="
					+ Option
					+ "&txtUnitId="
					+ txtUnitId;
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				Mandatory(req);
			}
			req.send(null);
		}
                        
		} else {
			var Option = "test";
			var txtUnitId = 0;
			var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
			url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
					+ txtAcc_HeadCode
					+ "&Option="
					+ Option
					+ "&txtUnitId="
					+ txtUnitId;
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				Mandatory(req);
			}
			req.send(null);
		}
	}
}

function Mandatory(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (Command == "checkSubLedgerMandatory") {
				if (flag == "success") {
					var M_flag = baseResponse.getElementsByTagName("M_flag")[0].firstChild.nodeValue;

					if (M_flag == "Madatory") {

						acheadcodecheck('1');
					} else if (M_flag == "Not_Madatory") {
						if (document.getElementById("cmbSL_type").value == "") {
							alert("Select a Sub-Ledger Type");
							return false;
						}
						if (document.getElementById("cmbSL_Code").value == "") {
							alert("Select a Sub-Ledger Code");
							return false;
						} else {
							acheadcodecheck('1');
						}
					}
				}
			}
		}
	}
}

function checkSubLedgerMandatory1() {
	var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	if (cmbMas_SL_type == 64) {
		if ((txtAcc_HeadCode == 900109)) {
			var adjyear = document.getElementById("adjyear").value;
			var adjmonth = document.getElementById("adjmonth").value;
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory1(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			} else {
				var Option = "test";
				var txtUnitId = 0;
				var txtAcc_HeadCode = document
						.getElementById("txtAcc_HeadCode").value;
				url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
						+ txtAcc_HeadCode
						+ "&Option="
						+ Option
						+ "&txtUnitId="
						+ txtUnitId;
				var req = getTransport();
				req.open("GET", url, true);
				req.onreadystatechange = function() {
					Mandatory1(req);
				}
				req.send(null);
			}
		} else {
			var adjyear = document.getElementById("adjyear").value;
			var adjmonth = document.getElementById("adjmonth").value;
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory1(req);
					}
					req.send(null);
				}
			} else if ((adjyear >= 2007) && (adjyear <= 2011)) {
				if (adjmonth > 3) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory1(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			} else {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			}
		}

	} else if (cmbMas_SL_type == 67) {
		if ((txtAcc_HeadCode == 901002)) {
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory1(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			} else {
				var Option = "test";
				var txtUnitId = 0;
				var txtAcc_HeadCode = document
						.getElementById("txtAcc_HeadCode").value;
				url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
						+ txtAcc_HeadCode
						+ "&Option="
						+ Option
						+ "&txtUnitId="
						+ txtUnitId;
				var req = getTransport();
				req.open("GET", url, true);
				req.onreadystatechange = function() {
					Mandatory1(req);
				}
				req.send(null);
			}
		} else {
			var adjyear = document.getElementById("adjyear").value;
			var adjmonth = document.getElementById("adjmonth").value;
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory1(req);
					}
					req.send(null);
				}
			} else if ((adjyear >= 2007) && (adjyear <= 2011)) {
				if (adjmonth > 3) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory1(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			} else {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			}
		}
	} else {
		var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
		if ((txtAcc_HeadCode == 901001) || (txtAcc_HeadCode == 901002)
				|| (txtAcc_HeadCode == 900108) || (txtAcc_HeadCode == 900109)) {
			var adjyear = document.getElementById("adjyear").value;
			var adjmonth = document.getElementById("adjmonth").value;
			if (adjyear == 2007) {
				if (adjmonth < 9) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory1(req);
					}
					req.send(null);
				}
			} else if ((adjyear >= 2007) && (adjyear <= 2011)) {
				if (adjmonth > 3) {
					alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
					return false;
				} else {
					var Option = "test";
					var txtUnitId = 0;
					var txtAcc_HeadCode = document
							.getElementById("txtAcc_HeadCode").value;
					url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
							+ txtAcc_HeadCode
							+ "&Option="
							+ Option
							+ "&txtUnitId=" + txtUnitId;
					var req = getTransport();
					req.open("GET", url, true);
					req.onreadystatechange = function() {
						Mandatory1(req);
					}
					req.send(null);
				}
			} else if (adjyear < 2007) {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			} else {
				alert("Enter Month and Year between Sep-2007 and as per the Originated or Accepted Year and Month");
				return false;
			}
		} else {
			var Option = "test";
			var txtUnitId = 0;
			var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
			url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
					+ txtAcc_HeadCode
					+ "&Option="
					+ Option
					+ "&txtUnitId="
					+ txtUnitId;
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				Mandatory1(req);
			}
			req.send(null);
		}
	}
}
function Mandatory1(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (Command == "checkSubLedgerMandatory") {
				if (flag == "success") {
					var M_flag = baseResponse.getElementsByTagName("M_flag")[0].firstChild.nodeValue;

					if (M_flag == "Madatory") {

						acheadcodecheck('2');
						// acheadcodecheck('2');
					} else if (M_flag == "Not_Madatory") {
						if (document.getElementById("cmbSL_type").value == "") {
							alert("Select a Sub-Ledger Type");
							return false;
						}
						if (document.getElementById("cmbSL_Code").value == "") {
							alert("Select a Sub-Ledger Code");
							return false;
						} else {
							acheadcodecheck('2');
						}

					}
				}
			}
		}
	}
}
function callThis()
{
        var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
        
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
        if((cmbMas_SL_type==64 && txtAcc_HeadCode==900108) || (cmbMas_SL_type==67 && txtAcc_HeadCode==901001))
        {
        var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
        var accText=document.getElementById("cmbAcc_UnitCode").options[document.getElementById("cmbAcc_UnitCode").selectedIndex].text;
       
         document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text=accText;
         document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].value=cmbAcc_UnitCode;
        
        }
        else  if((cmbMas_SL_type==63 && txtAcc_HeadCode==900109) || (cmbMas_SL_type==66 && txtAcc_HeadCode==901002))
        {
        
        var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
        var accText=document.getElementById("cmbAcc_UnitCode").options[document.getElementById("cmbAcc_UnitCode").selectedIndex].text;
       
        document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text=accText;
         document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].value=cmbAcc_UnitCode;
       
        }
        else
        {
          var cmbSL_type = document.getElementById("cmbSL_type").value;
        doFunction('Load_SL_Code1',cmbSL_type);
        }
}
function accHdCheck() {
	var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
       
	if(txtAcc_HeadCode==610101 || txtAcc_HeadCode==610102)
	{
		alert("This Account Head Code Not Allowed Here");
		document.getElementById("txtAcc_HeadCode").value = "";
		document.getElementById("txtAcc_HeadDesc").value = "";
	}
        
	if (cmbMas_SL_type == "") {
		alert("Select Journal Type in the field");
		document.getElementById("txtAcc_HeadCode").value = "";
	}
	if ((cmbMas_SL_type == 66) && (txtAcc_HeadCode == 901001)) {
		alert("This Account Head Code Not Allowed Here");
		document.getElementById("txtAcc_HeadCode").value = "";
	}
	if ((cmbMas_SL_type == 66) && (txtAcc_HeadCode == 901002)) {
		document.frmJournal_General.rad_sub_CR_DR[0].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
	}
	if ((cmbMas_SL_type == 67) && (txtAcc_HeadCode == 901001)) {
		document.frmJournal_General.rad_sub_CR_DR[0].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
	}
	if ((cmbMas_SL_type == 67) && (txtAcc_HeadCode == 901002)) {
		document.frmJournal_General.rad_sub_CR_DR[0].disabled = false;
		document.frmJournal_General.rad_sub_CR_DR[1].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[0].checked = true;

	}
	if ((cmbMas_SL_type == 63) && (txtAcc_HeadCode == 900108)) {
		alert("This Account Head Code Not Allowed Here");
		document.getElementById("txtAcc_HeadCode").value = "";
	}

	if ((cmbMas_SL_type == 63) && (txtAcc_HeadCode == 900109)) {
		document.frmJournal_General.rad_sub_CR_DR[1].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[0].disabled = false;
		document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
	}

	if ((cmbMas_SL_type == 64) && (txtAcc_HeadCode == 900108)) {
       
		document.frmJournal_General.rad_sub_CR_DR[1].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
             
	}
        if ((cmbMas_SL_type == 92) && (txtAcc_HeadCode == 900108)) {
       
		document.frmJournal_General.rad_sub_CR_DR[1].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
                
                document.frmJournal_General.txtUnitId.value=document.frmJournal_General.cmbAcc_UnitCode.value;
                
             
	}
         if ((cmbMas_SL_type == 93) && (txtAcc_HeadCode == 901001)) {
       
		document.frmJournal_General.rad_sub_CR_DR[0].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
                 document.frmJournal_General.txtUnitId.value=document.frmJournal_General.cmbAcc_UnitCode.value;
             
	}
	if ((cmbMas_SL_type == 64) && (txtAcc_HeadCode == 900109)) {
		document.frmJournal_General.rad_sub_CR_DR[1].disabled = false;
		document.frmJournal_General.rad_sub_CR_DR[0].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
	}
        else if( ((cmbMas_SL_type == 66) && (txtAcc_HeadCode != 901002)) || ((cmbMas_SL_type == 67) && (txtAcc_HeadCode != 901002) && (txtAcc_HeadCode != 901001)))
        {
                document.frmJournal_General.rad_sub_CR_DR[1].disabled = false;
		document.frmJournal_General.rad_sub_CR_DR[0].disabled = false;
		document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
                document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
        }
         else if( ((cmbMas_SL_type == 63) && (txtAcc_HeadCode != 900109)) || ((cmbMas_SL_type == 64) && (txtAcc_HeadCode != 900109) && (txtAcc_HeadCode != 900108)))
        {
        //alert("comesssssss");
                document.frmJournal_General.rad_sub_CR_DR[1].disabled = false;
		document.frmJournal_General.rad_sub_CR_DR[0].disabled = true;
		document.frmJournal_General.rad_sub_CR_DR[0].checked = true;
              //  document.frmJournal_General.rad_sub_CR_DR[1].checked = true;
        }
         else
         {
        	 doFunction('checkCode','null');
         }
}

