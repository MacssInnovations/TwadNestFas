var seq = 0;
var Part_Pyment;
var cr_count = 0;
var dr_count = 0;
// /////////////////////////////////////////// XML req
// /////////////////////////////////////////////////////
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

function GetVoucherInfo(val) {
	// alert('1');
	var doc = window.opener.document; // get the parent form

	var unitcode = doc.frmBankPay_PendingBill_create.cmbAcc_UnitCode.value;
	var offid = doc.frmBankPay_PendingBill_create.cmbOffice_code.value
	var dateval = doc.frmBankPay_PendingBill_create.txtCrea_date.value; // used
																		// to
																		// get
																		// cash
																		// book
																		// month
																		// and
																		// year
	var type_MasSL = doc.frmBankPay_PendingBill_create.cmbMas_SL_type.value;
	var code_MasSL = doc.frmBankPay_PendingBill_create.cmbMas_SL_Code.value;
	var frmDate = doc.frmBankPay_PendingBill_create.frmDate.value;
	var toDate = doc.frmBankPay_PendingBill_create.toDate.value;

	var Part_Pyment = doc.frmBankPay_PendingBill_create.hid.value;
	// alert("test"+document.getElementById("img1").style.visibility);
	document.getElementById("img1").style.visibility = "visible";
	/*
	 * if(val!="") { if(val==550401 && type_MasSL!=9) { alert("select 'Other
	 * Departments' Sub-Ledger type in General details part"); var
	 * tbody=document.getElementById("grid_body"); var t=0;
	 * for(t=tbody.rows.length-1;t>=0;t--) { tbody.deleteRow(0); } return false; }
	 * 
	 * var
	 * url="../../../../../PendingBills_Revised.view?cmbAcc_HeadCode="+val+"&cmbMaS_SL_code="+code_MasSL+"&cmbMas_SL_type="+type_MasSL+
	 * "&txtCrea_date="+dateval+"&cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid;
	 * var req=getTransport(); //alert(url); req.open("GET",url,true);
	 * 
	 * req.onreadystatechange=function() { ProcessVoucherInfo(req); }
	 * req.send(null); } else alert("select a type");
	 */
	// document.getElementById("img1").style.visibility="visible";
	if (type_MasSL != "" && code_MasSL != "") {
		var url = "../../../../../PendingBills_Revised.view?cmbAcc_HeadCode="
				+ val + "&cmbMaS_SL_code=" + code_MasSL + "&cmbMas_SL_type="
				+ type_MasSL + "&txtCrea_date=" + dateval + "&cmbAcc_UnitCode="
				+ unitcode + "&cmbOffice_code=" + offid + "&frmDate=" + frmDate
				+ "&toDate=" + toDate;
		var req = getTransport();

		req.open("GET", url, true);

		req.onreadystatechange = function() {
			ProcessVoucherInfo(req, Part_Pyment);
		};
		req.send(null);
	}
}
function disabtext(cur, tot) {

	for (var i = 1; i <= tot; i++) {
		var getrow = document.getElementById(i);
		var get_cells = getrow.cells;
		/*
		 * if(i==cur){ get_cells.item(9).lastChild.nodeValue= }
		 */
		if (i != cur) {
			get_cells.item(11).firstChild.disabled = true;
		}

	}
}
function Changebtext(cur_val, ori_val, cur, tot) {

	if (ori_val < cur_val) {
		alert('Amount Should be less than ' + ori_val);
		document.getElementById(cur).cells.item(11).firstChild.value = "";

		var nex = document.getElementById(cur).cells.item(10).firstChild.nextSibling;
		nex = nex.nextSibling;
		nex = nex.nextSibling;
		nex = nex.nextSibling;
		nex = nex.nextSibling;
		nex = nex.nextSibling;
		nex = nex.nextSibling;
		nex = nex.nextSibling;
		nex = nex.nextSibling;
		nex.value = "S";

		// document.getElementById(cur).cells.item(10).firstChild.nextSibling.value=0;
	} else {

		for (var i = 1; i <= tot; i++) {
			var getrow = document.getElementById(i);
			var get_cells = getrow.cells;
			if (i == cur) {

				if (ori_val != cur_val) {
					var nex = get_cells.item(10).firstChild.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex.value = cur_val + "-T";
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;

				} else if (ori_val == cur_val) {

					var nex = get_cells.item(10).firstChild.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex.value = cur_val + "-s";
					nex = nex.nextSibling;
					nex = nex.nextSibling;
					nex = nex.nextSibling;

				}
				/*
				 * if(ori_val!=cur_val) { nex.value="T"; }else
				 * if(ori_val==cur_val){ nex.value="S"; }
				 */
			}
		}

	}

}
function ProcessVoucherInfo(req, part) {
	document.getElementById("img1").style.visibility = "hidden";
	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

			if (flag == "success") {
				var cnt = baseResponse.getElementsByTagName("cnt")[0].firstChild.nodeValue;
				// document.getElementById("noof_Rec").value=cnt;
				var tbody = document.getElementById("grid_body");
				var t = 0;
				for (t = tbody.rows.length - 1; t >= 0; t--) {

					tbody.deleteRow(0);
				}

				var AHcode = baseResponse.getElementsByTagName("AHcode");

				var items = new Array();
				// alert(AHcode.length)
				for (var k = 0; k < AHcode.length; k++) {
					items[0] = baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;
					items[1] = baseResponse.getElementsByTagName("AHdesc")[k].firstChild.nodeValue;
					items[2] = baseResponse.getElementsByTagName("CR_DR_ind")[k].firstChild.nodeValue;
					items[3] = baseResponse.getElementsByTagName("SL_Type")[k].firstChild.nodeValue;
					if (items[3] == 0)
						items[3] = "";

					items[4] = baseResponse.getElementsByTagName("SL_Desc")[k].firstChild.nodeValue;
					if (items[4] == "null")
						items[4] = "";

					items[5] = baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue;
					if (items[5] == 0)
						items[5] = "";

					items[6] = baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
					if (items[6] == "null")
						items[6] = "";
					// items[7]=baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild.nodeValue;
					// if(items[7]=="null")
					// items[7]="";
					// items[7]="";
					// items[8]="";
					// items[9]="";
					items[10] = baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue;
					items[11] = baseResponse.getElementsByTagName("Bill_date")[k].firstChild.nodeValue;
					items[12] = baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
					items[13] = baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
					items[14] = baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
					items[15] = "";
					items[16] = baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
					items[17] = baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;

					items[18] = baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;
					items[19] = baseResponse
							.getElementsByTagName("payment_date")[k].firstChild.nodeValue;
					items[20] = baseResponse.getElementsByTagName("vou_date")[k].firstChild.nodeValue;
					items[21] = baseResponse
							.getElementsByTagName("JOURNAL_TYPE")[k].firstChild.nodeValue;
					items[22] = baseResponse.getElementsByTagName("SL_NO")[k].firstChild.nodeValue;
					items[23] = baseResponse
							.getElementsByTagName("JOURNAL_TYPE_CODE")[k].firstChild.nodeValue;
					items[24] = baseResponse.getElementsByTagName("vrAMOUNT")[k].firstChild.nodeValue;
					items[25] = baseResponse
							.getElementsByTagName("MULTIPLE_PVR_DETAILS")[k].firstChild.nodeValue;

					if (items[10] == "null")
						items[10] = "";
					if (items[11] == "null")
						items[11] = "";
					if (items[12] == "null")
						items[12] = "";
					if (items[13] == "null")
						items[13] = "";
					if (items[14] == "null")
						items[14] = "";
					if (items[15] == "null")
						items[15] = "";
					if (items[17] == "null")
						items[17] = "";
					if (items[19] == "null")
						items[19] = "Under 1st.pmt.";
					if (items[25] == "null")
						items[25] = "";
					// alert("Pp "+items[24]);
					tbody = document.getElementById("grid_body");
					var mycurrent_row = document.createElement("TR");
					seq = seq + 1;
					mycurrent_row.id = seq;
					// alert("row ID"+mycurrent_row.id);
					var cell = document.createElement("TD");
					var vou_no = document.createElement("input");
					vou_no.type = "checkbox";
					vou_no.name = "vou_no";
					vou_no.id = "vou_no" + seq;
					vou_no.value = seq;
					/* @NK included for onchange function */
					vou_no
							.setAttribute("onchange",
									"checkboxAlert(this.value)");
					/* @NK included for onchange function */
					cell.appendChild(vou_no);
					mycurrent_row.appendChild(cell);
					var i = 0;
					var cell2;
					var mvrFlag = "s";
					// 1
					cell2 = document.createElement("TD");
					cell2.align = "CENTER";
					var vou_type = document.createElement("input");
					vou_type.type = "hidden";
					// vou_type.align="right"; // Not working
					vou_type.name = "vou_type";
					vou_type.value = items[21];
					// cell2.style.display="none";
					cell2.appendChild(vou_type);
					var currentText = document.createTextNode(items[21]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);
					// 2
					cell2 = document.createElement("TD");
					var vou_date = document.createElement("input");
					vou_date.type = "hidden";
					vou_date.name = "vou_date";
					vou_date.value = items[20];
					cell2.appendChild(vou_date);
					var currentText = document.createTextNode(items[20]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);

					// 3
					cell2 = document.createElement("TD");
					var pmt_date = document.createElement("input");
					pmt_date.type = "hidden";
					pmt_date.name = "pmt_date";
					pmt_date.id = "pmt_date" + seq;
					pmt_date.value = items[19];
					cell2.appendChild(pmt_date);
					var currentText = document.createTextNode(items[19]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);

					// 4
					cell2 = document.createElement("TD");
					cell2.align = "CENTER";
					var Voucher_no = document.createElement("input");
					Voucher_no.type = "hidden";
					Voucher_no.name = "Voucher_no";
					Voucher_no.value = items[18];
					cell2.appendChild(Voucher_no);
					var currentText = document.createTextNode(items[18]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);
					// 5

					cell2 = document.createElement("TD"); // added on 27th
															// April 07
					cell2.align = "CENTER";
					var SL_NO = document.createElement("input");
					SL_NO.type = "hidden";
					SL_NO.name = "SL_NO";
					SL_NO.value = items[22];
					/* cell2.style.display="none"; *//*
													 * <!-- @NK HIDE ON 01072019
													 * -->
													 */
					cell2.appendChild(SL_NO);
					var currentText = document.createTextNode(items[22]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);

					// 6

					cell2 = document.createElement("TD");

					var H_code = document.createElement("input");
					H_code.type = "hidden";
					H_code.name = "H_code";
					H_code.value = items[0];
					cell2.appendChild(H_code);
					var currentText = document.createTextNode(items[0] + ' - '
							+ items[1]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);

					// 7
					cell2 = document.createElement("TD");
					var CR_DR_type = document.createElement("input");
					CR_DR_type.type = "hidden";
					CR_DR_type.name = "CR_DR_type";

					CR_DR_type.value = items[2];
					cell2.appendChild(CR_DR_type);
					// Joan Change on 22 Sep 2014
					// var currentText=document.createTextNode(items[2]);
					// alert('test'+items[20]+" "+items[22]+" "+items[2]);
					// var currentText;

					// GJV changes On 05-01-2018 KT Discussion changes

					// if(items[20]=="GJV") {
					// if(items[22]!=62)
					// currentText=document.createTextNode("CR");
					// else if(items[22]==62)
					// currentText=document.createTextNode("DR");
					// }
					// else {
					if (items[2] == 'CR')
						currentText = document.createTextNode("CR");
					else if (items[2] == 'DR')
						currentText = document.createTextNode("DR");
					// }//GJV changes On 05-01-2018 KT Discussion changes

					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);
					// 8
					cell2 = document.createElement("TD");
					var SL_type = document.createElement("input");
					SL_type.type = "hidden";
					SL_type.name = "SL_type";
					SL_type.value = items[3];
					cell2.appendChild(SL_type);
					var currentText = document.createTextNode(items[4]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);
					// 9
					cell2 = document.createElement("TD");
					var SL_code = document.createElement("input");
					SL_code.type = "hidden";
					SL_code.name = "SL_code";
					SL_code.value = items[5];
					cell2.appendChild(SL_code);
					var currentText = document.createTextNode(items[6]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);

					/*
					 * cell2=document.createElement("TD"); var
					 * Cheque_DD=document.createElement("input");
					 * Cheque_DD.type="hidden"; Cheque_DD.name="Cheque_DD";
					 * Cheque_DD.value=items[7]; cell2.appendChild(Cheque_DD); /*
					 * within the star indicates to avoid the column added in
					 * b/w two cells //var
					 * currentText=document.createTextNode(items[7]);
					 * //cell2.appendChild(currentText);
					 * //mycurrent_row.appendChild(cell2);
					 * //cell2=document.createElement("TD");
					 * 
					 * var Cheque_DD_NO=document.createElement("input");
					 * Cheque_DD_NO.type="hidden";
					 * Cheque_DD_NO.name="Cheque_DD_NO";
					 * Cheque_DD_NO.value=items[8];
					 * cell2.appendChild(Cheque_DD_NO); var
					 * currentText=document.createTextNode(items[8]);
					 * cell2.appendChild(currentText);
					 * mycurrent_row.appendChild(cell2);
					 * 
					 * cell2=document.createElement("TD"); var
					 * Cheque_DD_date=document.createElement("input");
					 * Cheque_DD_date.type="hidden";
					 * Cheque_DD_date.name="Cheque_DD_date";
					 * Cheque_DD_date.value=items[9];
					 * cell2.appendChild(Cheque_DD_date); var
					 * currentText=document.createTextNode(items[9]);
					 * cell2.appendChild(currentText);
					 * mycurrent_row.appendChild(cell2);
					 */
					// 10
					cell2 = document.createElement("TD");
					var Bill_NO = document.createElement("input");
					Bill_NO.type = "hidden";
					Bill_NO.name = "Bill_NO";
					Bill_NO.value = items[10];
					cell2.appendChild(Bill_NO);
					// var currentText=document.createTextNode(items[7]);
					// cell2.appendChild(currentText);
					// mycurrent_row.appendChild(cell2);

					// cell2=document.createElement("TD");
					var Bill_date = document.createElement("input");
					Bill_date.type = "hidden";
					Bill_date.name = "Bill_date";
					Bill_date.value = items[11];
					cell2.appendChild(Bill_date);
					// var currentText=document.createTextNode(items[8]);
					// cell2.appendChild(currentText);
					// mycurrent_row.appendChild(cell2);

					// cell2=document.createElement("TD");
					var Bill_type = document.createElement("input");
					Bill_type.type = "hidden";
					Bill_type.name = "Bill_type";
					Bill_type.value = items[12];
					cell2.appendChild(Bill_type);

					// cell2=document.createElement("TD");
					var Agree_No = document.createElement("input");
					Agree_No.type = "hidden";
					Agree_No.name = "Agree_No";
					Agree_No.value = items[13];
					cell2.appendChild(Agree_No);

					var Agree_date = document.createElement("input");
					Agree_date.type = "hidden";
					Agree_date.name = "Agree_date";
					Agree_date.value = items[14];
					cell2.appendChild(Agree_date);

					var sub_paid = document.createElement("input");
					sub_paid.type = "hidden";
					sub_paid.name = "sub_paid";
					sub_paid.value = items[15];
					cell2.appendChild(sub_paid);

					var sl_amt = document.createElement("input");
					sl_amt.type = "hidden";
					sl_amt.name = "sl_amt";
					sl_amt.value = items[16] + "-s";
					cell2.appendChild(sl_amt);

					var particular = document.createElement("input"); // Particulars
																		// Added
																		// to
																		// grid
																		// b4
																		// the
																		// Amount
																		// Text
																		// Node
																		// but
																		// after
																		// amount
																		// text
																		// box
					particular.type = "hidden";
					particular.name = "particular";
					particular.value = items[17];
					cell2.appendChild(particular);

					var MULTIPLE_PVR_DETAILS = document.createElement("input"); // Particulars
																				// Added
																				// to
																				// grid
																				// b4
																				// the
																				// Amount
																				// Text
																				// Node
																				// but
																				// after
																				// amount
																				// text
																				// box
					MULTIPLE_PVR_DETAILS.type = "hidden";
					MULTIPLE_PVR_DETAILS.name = "MULTIPLE_PVR_DETAILS";
					MULTIPLE_PVR_DETAILS.value = items[25];
					cell2.appendChild(MULTIPLE_PVR_DETAILS);

					var hid_MVR = document.createElement("input");
					hid_MVR.type = "hidden";
					hid_MVR.name = "hid_MVR";
					hid_MVR.value = mvrFlag;
					cell2.appendChild(hid_MVR);
					// mycurrent_row.appendChild(cell2);

					var currentText = document.createTextNode(items[16]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);
					// 10
					cell2 = document.createElement("TD");

					var Vr_amt = document.createElement("input");
					Vr_amt.type = "text";
					Vr_amt.name = "Vr_amt";
					Vr_amt.id = "Vr_amt";
					Vr_amt.setAttribute("size", "9");
					Vr_amt.setAttribute("onclick", "disabtext(" + seq + ","
							+ cnt + ");");

					if (part == 'N') {
						Vr_amt.setAttribute("disabled", true);
					}
					Vr_amt.setAttribute("onchange", "Changebtext(this.value,"
							+ items[24] + "," + seq + "," + cnt + ");");
					Vr_amt.value = items[24];

					cell2.appendChild(Vr_amt);
					mycurrent_row.appendChild(cell2);
					// cell2=document.createElement("TD");

					tbody.appendChild(mycurrent_row);
				}
			}

			else if (flag == "failure") {
				alert("No data found");
				var tbody = document.getElementById("grid_body");
				var t = 0;
				for (t = tbody.rows.length - 1; t >= 0; t--) {
					tbody.deleteRow(0);
				}
			}
		}
	}
}
function btnsubmit() {

	var sele = document.getElementsByName("vou_no").length; // "vou_no" is check
															// box name
	var val = 0;
	// var
	// vouNO[20][],vouDATE[20][],vouTYPE[20][],AcHeadCode[20][],AcHeadName[20][],CR_DB_indic[20][],subLed_type[20][],subLed_typeName[20][];
	// var
	// subLed_code[20][],subLed_codeName[20][],cheque_dd[20][],cheque_dd_No[20][],cheque_dd_date[20][],billNO[20][],billdate[20][],billtype[20][];
	// var
	// agree_NO[20][],agree_date[20][],paid_to[20][],subamount[20][],particular[20][];
	var vouNO, vouDATE, vouTYPE, AcHeadCode, AcHeadName, CR_DB_indic, subLed_type, subLed_typeName;
	var subLed_code, subLed_codeName, cheque_dd, cheque_dd_No, cheque_dd_date, billNO, billdate, billtype;
	var agree_NO, agree_date, paid_to, subamount, particular, vrAmt, MULTIPLE_PVR_DETAILS, MVR_no;
	var vouSL_NO;
	vouNO = new Array();
	vouDATE = new Array();
	vouTYPE = new Array();
	vouSL_NO = new Array();
	AcHeadCode = new Array();
	AcHeadName = new Array();
	CR_DB_indic = new Array();
	subLed_type = new Array();
	subLed_typeName = new Array();
	subLed_code = new Array();
	subLed_codeName = new Array();
	cheque_dd = new Array();
	cheque_dd_No = new Array();
	cheque_dd_date = new Array();
	billNO = new Array();
	billdate = new Array();
	billtype = new Array();
	agree_NO = new Array();
	agree_date = new Array();
	paid_to = new Array();
	subamount = new Array();
	particular = new Array();

	MULTIPLE_PVR_DETAILS = new Array();
	vrAmt = new Array();
	MVR_no = new Array();
	var tot_amount = 0;
	// alert(sele);
	if (sele > 0) {
		var j = 0;
		if (sele == 1) {

			// document.frmPending_Bills_revised.vou_no.checked=true;
			// alert("here");
			if (document.frmPending_Bills_revised.vou_no.checked == true) {
				// vouNO[0]=document.frmPending_Bills_revised.vou_no.value;
				r = document
						.getElementById(document.frmPending_Bills_revised.vou_no.value); // choose
																							// the
																							// particular
																							// row
				rcells = r.cells;

				vouNO[0] = rcells.item(4).firstChild.value;
				vouSL_NO[0] = rcells.item(5).firstChild.value;
				vouDATE[0] = rcells.item(2).firstChild.value;
				vouTYPE[0] = rcells.item(1).firstChild.value;

				AcHeadCode[0] = rcells.item(6).firstChild.value;
				AcHeadName[0] = rcells.item(6).lastChild.nodeValue;
				CR_DB_indic[0] = rcells.item(7).firstChild.value;
				subLed_type[0] = rcells.item(8).firstChild.value;
				subLed_typeName[0] = rcells.item(8).lastChild.nodeValue;
				subLed_code[0] = rcells.item(9).firstChild.value;
				subLed_codeName[0] = rcells.item(9).lastChild.nodeValue;
				// cheque_dd[0]=rcells.item(8).firstChild.value;

				// cheque_dd_No[0]=rcells.item(8).lastChild.value;
				// cheque_dd_date[0]=rcells.item(9).firstChild.value;
				billNO[0] = rcells.item(10).firstChild.value;
				var nex = rcells.item(10).firstChild.nextSibling;

				billdate[0] = nex.value; // rcells.item(3).firstChild.value;
				nex = nex.nextSibling;
				billtype[0] = nex.value; // rcells.item(3).firstChild.value;
				nex = nex.nextSibling;
				agree_NO[0] = nex.value; // rcells.item(3).firstChild.value;
				nex = nex.nextSibling;
				agree_date[0] = nex.value; // rcells.item(3).firstChild.value;
				nex = nex.nextSibling;
				paid_to[0] = nex.value; // rcells.item(3).firstChild.value;
				nex = nex.nextSibling;
				subamount[0] = nex.value; // rcells.item(3).firstChild.value;
				nex = nex.nextSibling;
				particular[0] = nex.value;
				nex = nex.nextSibling;

				MULTIPLE_PVR_DETAILS[0] = nex.value;
				nex = nex.nextSibling;
				MVR_no[0] = nex.value;
				vrAmt[0] = rcells.item(11).firstChild.value;
				// alert("testing "+vrAmt[0]);
				tot_amount = parseFloat(vrAmt[0]);
				// MVR_no[0]=rcells.item(10).firstChild.nextSibling.value;
				// rcells.item(3).firstChild.value;
				// j++;
				// alert(tot_amount);
			} else {
				alert("select the Journal voucher");
				return false;
			}
		} else { // alert("else"+sele);

			var dr_amt = 0;
			var cr_amt = 0;

			for (var i = 0; i < sele; i++) {

				// alert(document.frmPending_Bills_revised.choice[i].checked)
				if (document.frmPending_Bills_revised.vou_no[i].checked == true) {
					// vouNO[j]=document.frmPending_Bills_revised.vou_no[i].value;
					r = document
							.getElementById(document.frmPending_Bills_revised.vou_no[i].value);
					rcells = r.cells;

					vouNO[j] = rcells.item(4).firstChild.value;
					vouSL_NO[j] = rcells.item(5).firstChild.value;
					vouDATE[j] = rcells.item(2).firstChild.value;
					vouTYPE[j] = rcells.item(1).firstChild.value;
					// alert("vouTYPE[j] "+vouTYPE[j]);
					// alert("vouTYPE[j] "+vouNO[j]);
					if (vouTYPE[j] == 'GJV')
						cr_count = cr_count + 1;
					else if (vouTYPE[j] == 'LJV')
						dr_count = dr_count + 1;

					AcHeadCode[j] = rcells.item(6).firstChild.value;
					AcHeadName[j] = rcells.item(6).lastChild.nodeValue;
					CR_DB_indic[j] = rcells.item(7).firstChild.value;

					subLed_type[j] = rcells.item(8).firstChild.value;
					subLed_typeName[j] = rcells.item(8).lastChild.nodeValue;
					subLed_code[j] = rcells.item(9).firstChild.value;
					subLed_codeName[j] = rcells.item(9).lastChild.nodeValue;
					// cheque_dd[j]=rcells.item(8).firstChild.value;

					// cheque_dd_No[j]=rcells.item(8).lastChild.value;
					// cheque_dd_date[j]=rcells.item(9).firstChild.value;
					billNO[j] = rcells.item(10).firstChild.value;
					var nex = rcells.item(10).firstChild.nextSibling;

					billdate[j] = nex.value; // rcells.item(3).firstChild.value;
					nex = nex.nextSibling;
					billtype[j] = nex.value; // rcells.item(3).firstChild.value;
					nex = nex.nextSibling;
					agree_NO[j] = nex.value; // rcells.item(3).firstChild.value;
					nex = nex.nextSibling;
					agree_date[j] = nex.value; // rcells.item(3).firstChild.value;
					nex = nex.nextSibling;
					paid_to[j] = nex.value; // rcells.item(3).firstChild.value;
					nex = nex.nextSibling;
					subamount[j] = nex.value; // rcells.item(3).firstChild.value;
					nex = nex.nextSibling;
					particular[j] = nex.value;
					nex = nex.nextSibling;

					MULTIPLE_PVR_DETAILS[j] = nex.value;
					nex = nex.nextSibling;
					MVR_no[j] = nex.value;
					vrAmt[j] = rcells.item(11).firstChild.value;

					// alert(""+ MVR_no[j]);
					if (CR_DB_indic[j] == "DR") {
						dr_amt = parseFloat(dr_amt) + parseFloat(vrAmt[j]);
					} else if (CR_DB_indic[j] == "CR") {
						cr_amt = parseFloat(cr_amt) + parseFloat(vrAmt[j]);
					}
					// alert(dr_amt+" "+cr_amt);
					tot_amount = parseFloat(dr_amt) - parseFloat(cr_amt);
					// alert(tot_amount);
					// rcells.item(3).firstChild.value;
					// alert(tot_amount);
					j++;
					// opener.doParentPendingbills(vouNO,vouDATE,vouTYPE,AcHeadCode,AcHeadName,CR_DB_indic,subLed_type,subLed_typeName,subLed_code,subLed_codeName,cheque_dd,cheque_dd_No,cheque_dd_date,billNO,billdate,billtype,agree_NO,agree_date,paid_to,subamount,particular,i);
				}
			}
			if (j == 0) {
				alert("select the Journal voucher");
				return false;
			}
		}

	} else {
		alert("select the Journal voucher");
		return false;
	}

	if (tot_amount < 0) {
		alert("Debit Amount should be greater than Credit Amount .. ");
	}
	
	else if (tot_amount >= 0) {
		// document.getElementById("cmdsubmit").disabled=false;
		var flag = document.getElementById("hid_value").value;
		// alert("flag"+flag);
		opener.doParentPendingbills(vouNO, vouSL_NO, vouDATE, vouTYPE,
				AcHeadCode, AcHeadName, CR_DB_indic, subLed_type,
				subLed_typeName, subLed_code, subLed_codeName, billNO,
				billdate, billtype, agree_NO, agree_date, paid_to, subamount,
				particular, vrAmt, MULTIPLE_PVR_DETAILS, MVR_no, j, flag);
		window.history.go();

		self.close();
	}

	// alert("test2")
	// return true;
}

/*
 * var VOUCHER_NO=baseResponse.getElementsByTagName("VOUCHER_NO");
 * 
 * var items=new Array();
 * 
 * for(var k=0;k<VOUCHER_NO.length;k++) {
 * 
 * 
 * items[0]=baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;
 * items[1]=baseResponse.getElementsByTagName("vou_date")[k].firstChild.nodeValue;
 * items[2]=baseResponse.getElementsByTagName("JOURNAL_TYPE_CODE")[k].firstChild.nodeValue;
 * items[3]=baseResponse.getElementsByTagName("AMOUNT")[k].firstChild.nodeValue;
 * items[4]=baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;
 * 
 * 
 * 
 * 
 * 
 * if(items[4]=="null") items[4]="";
 * 
 * 
 * 
 * tbody=document.getElementById("grid_body"); var
 * mycurrent_row=document.createElement("TR"); mycurrent_row.id=items[0];
 * 
 * cell2=document.createElement("TD"); cell2.align="CENTER"; var
 * Vou_no=document.createElement("input"); Vou_no.type="checkbox";
 * 
 * Vou_no.id="Vou_no"; Vou_no.name="Vou_no"; Vou_no.value=items[0];
 * cell2.appendChild(Vou_no); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[0]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[1]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[2]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[3]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[4]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * tbody.appendChild(mycurrent_row);
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * function loading_Journal_Details() { var
 * flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 * if(flag=="success") { var tbody=document.getElementById("grid_body"); var
 * t=0; for(t=tbody.rows.length-1;t>=0;t--) { tbody.deleteRow(0); }
 * 
 * var VOUCHER_NO=baseResponse.getElementsByTagName("VOUCHER_NO");
 * 
 * var items=new Array(); for(var k=0;k<VOUCHER_NO.length;k++) {
 * items[0]=baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;
 * items[1]=baseResponse.getElementsByTagName("AMOUNT")[k].firstChild.nodeValue;
 * items[2]=baseResponse.getElementsByTagName("vou_date")[k].firstChild.nodeValue;
 * items[3]=baseResponse.getElementsByTagName("JOURNAL_TYPE_CODE")[k].firstChild.nodeValue;
 * items[4]=baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;
 * if(items[4]="null") items[4]="";
 * 
 * tbody=document.getElementById("grid_body"); var
 * mycurrent_row=document.createElement("TR"); mycurrent_row.id=items[0];
 * 
 * cell2=document.createElement("TD"); var
 * VOUCHER_NO=document.createElement("input"); VOUCHER_NO.type="checkbox";
 * VOUCHER_NO.id="VOUCHER_NO"; VOUCHER_NO.name="VOUCHER_NO";
 * VOUCHER_NO.value=items[0]; cell2.appendChild(VOUCHER_NO);
 * mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[0]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[1]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[2]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[3]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * cell2=document.createElement("TD"); var
 * currentText=document.createTextNode(items[4]);
 * cell2.appendChild(currentText); mycurrent_row.appendChild(cell2);
 * 
 * tbody.appendChild(mycurrent_row); } } }
 */

/*
 * function btnsubmit() {
 * 
 * var sele=document.getElementsByName("vou_no").length; var val=0; //var
 * vouNO[20][],vouDATE[20][],vouTYPE[20][],AcHeadCode[20][],AcHeadName[20][],CR_DB_indic[20][],subLed_type[20][],subLed_typeName[20][];
 * //var
 * subLed_code[20][],subLed_codeName[20][],cheque_dd[20][],cheque_dd_No[20][],cheque_dd_date[20][],billNO[20][],billdate[20][],billtype[20][];
 * //var
 * agree_NO[20][],agree_date[20][],paid_to[20][],subamount[20][],particular[20][];
 * var
 * vouNO,vouDATE,vouTYPE,AcHeadCode,AcHeadName,CR_DB_indic,subLed_type,subLed_typeName;
 * var
 * subLed_code,subLed_codeName,cheque_dd,cheque_dd_No,cheque_dd_date,billNO,billdate,billtype;
 * var agree_NO,agree_date,paid_to,subamount,particular; vouNO=new Array();
 * vouDATE=new Array(); vouTYPE=new Array(); AcHeadCode=new Array();
 * AcHeadName=new Array(); CR_DB_indic=new Array(); subLed_type=new Array();
 * subLed_typeName=new Array(); subLed_code=new Array(); subLed_codeName=new
 * Array(); cheque_dd=new Array(); cheque_dd_No=new Array(); cheque_dd_date=new
 * Array(); billNO=new Array(); billdate=new Array(); billtype=new Array();
 * agree_NO=new Array(); agree_date=new Array(); paid_to=new Array();
 * subamount=new Array(); particular=new Array();
 * 
 * if(sele>0) { if(sele==1) {
 * //document.frmPending_Bills_revised.vou_no.checked=true; //alert("here");
 * if(document.frmPending_Bills_revised.vou_no.checked==true) {
 * vouNO[0][1]=document.frmPending_Bills_revised.vou_no.value;
 * r=document.getElementById(vou_no); rcells=r.cells;
 * 
 * vouDATE[0][2]=rcells.item(2).firstChild.value;
 * vouTYPE[0][3]=rcells.item(3).firstChild.value;
 * AcHeadCode[0][4]=rcells.item(4).firstChild.value;
 * AcHeadName[0][5]=rcells.item(5).firstChild.value;
 * CR_DB_indic[0][6]=rcells.item(6).firstChild.value;
 * subLed_type[0][7]=rcells.item(7).firstChild.value;
 * subLed_typeName[0][8]=rcells.item(8).firstChild.value;
 * subLed_code[0][9]=rcells.item(9).firstChild.value;
 * subLed_codeName[0][10]=rcells.item(10).firstChild.value;
 * cheque_dd[0][11]=rcells.item(11).firstChild.value;
 * cheque_dd_No[0][12]=rcells.item(12).firstChild.value;
 * cheque_dd_date[0][13]=rcells.item(13).firstChild.value;
 * billNO[0][14]=rcells.item(14).firstChild.value;
 * billdate[0][15]=rcells.item(15).firstChild.value;
 * billtype[0][16]=rcells.item(16).firstChild.value;
 * agree_NO[0][17]=rcells.item(17).firstChild.value;
 * agree_date[0][18]=rcells.item(18).firstChild.value;
 * paid_to[0][19]=rcells.item(19).firstChild.value;
 * subamount[0][20]=rcells.item(20).firstChild.value;
 * particular[0][21]=rcells.item(21).firstChild.value; } } else {
 * //alert("else"+sele); var i=0; for(j=0;j<sele;j++) {
 * //alert(document.frmPending_Bills_revised.choice[i].checked)
 * if(document.frmPending_Bills_revised.vou_no[i].checked==true) {
 * vouNO[i][1]=document.frmPending_Bills_revised.vou_no[i].value;
 * r=document.getElementById(vou_no[i]); rcells=r.cells;
 * vouDATE[i][2]=rcells.item(2).firstChild.value;
 * vouTYPE[i][3]=rcells.item(3).firstChild.value;
 * AcHeadCode[i][4]=rcells.item(3).firstChild.value;
 * AcHeadName[i][5]=rcells.item(3).firstChild.value;
 * CR_DB_indic[i][6]=rcells.item(3).firstChild.value;
 * subLed_type[i][7]=rcells.item(3).firstChild.value;
 * subLed_typeName[i][8]=rcells.item(3).firstChild.value;
 * subLed_code[i][9]=rcells.item(3).firstChild.value;
 * subLed_codeName[i][10]=rcells.item(3).firstChild.value;
 * cheque_dd[i][11]=rcells.item(3).firstChild.value;
 * cheque_dd_No[i][12]=rcells.item(3).firstChild.value;
 * cheque_dd_date[i][13]=rcells.item(3).firstChild.value;
 * billNO[i][14]=rcells.item(3).firstChild.value;
 * billdate[i][15]=rcells.item(3).firstChild.value;
 * billtype[i][16]=rcells.item(3).firstChild.value;
 * agree_NO[i][17]=rcells.item(3).firstChild.value;
 * agree_date[i][18]=rcells.item(3).firstChild.value;
 * paid_to[i][19]=rcells.item(3).firstChild.value;
 * subamount[i][20]=rcells.item(3).firstChild.value;
 * particular[i][21]=rcells.item(3).firstChild.value; i++; } } } }
 * 
 * Minimize(); //opener.doParentPendingbills(vouNO,vouDATE,vouTYPE);
 * opener.doParentPendingbills(vouNO,vouDATE,vouTYPE,AcHeadCode,AcHeadName,CR_DB_indic,subLed_type,subLed_typeName,subLed_code,subLed_codeName,cheque_dd,cheque_dd_No,cheque_dd_date,billNO,billdate,billtype,agree_NO,agree_date,paid_to,subamount,particular,i);
 * return true; }
 */

function sel() {
	var sele = document.getElementsByName("vou_no").length;
	for (var j = 0; j < sele; j++) {

		document.frmPending_Bills_revised.vou_no[j].checked = true;
	}
	checkboxAlert(val);/* @NK Included on 26062019 */
}

function Unsel() {
	var sele = document.getElementsByName("vou_no").length;
	for (var j = 0; j < sele; j++) {

		document.frmPending_Bills_revised.vou_no[j].checked = false;
	}
	checkboxAlert(val);/* @NK Included on 26062019 */
}

/* @NK Included on 26062019 */
function checkboxAlert(val) {
	// var val;
	var sele = document.getElementsByName("vou_no").length;

	var vouNO = new Array();
	var vouTYPE = new Array();
	var checkedvouTYPE = new Array();

	if (GetPayDate(val)) {

		if (sele > 0) {
			if (sele == 1) {
				return true;

			} else {
				var j = 0;
				for (var i = 0; i < sele; i++) {

					if (document.frmPending_Bills_revised.vou_no[i].checked == true) {
						r = document
								.getElementById(document.frmPending_Bills_revised.vou_no[i].value);
						rcells = r.cells;

						vouTYPE[j] = rcells.item(1).firstChild.value;
						if (vouTYPE[j] == 'GJV') {
							checkedvouTYPE.push("JV");
						} else if (vouTYPE[j] == 'LJV') {
							checkedvouTYPE.push("JV");
						} else if (vouTYPE[j] == 'TDCAJ') {
							checkedvouTYPE.push("JV");
						} else {
							checkedvouTYPE.push(vouTYPE[j]);
						}

					}

					j++;

				}

				for (var i = 0; i < checkedvouTYPE.length; i++) {
					for (var j = 1; j < checkedvouTYPE.length; j++) {
						if (checkedvouTYPE[i] != checkedvouTYPE[j]) {
							for (var i = 0; i < sele; i++) {

								if (document.frmPending_Bills_revised.vou_no[i].checked == true) {
									document.frmPending_Bills_revised.vou_no[i].checked = false;
								}
							}
							functionAlert();
							return false;
						}
					}
				}

			}

		}

	}

}

function functionAlert(msg, myYes) {
	var confirmBox = $("#confirm");
	confirmBox.find(".message").text(msg);
	confirmBox.find(".yes").unbind().click(function() {
		confirmBox.hide();
	});
	confirmBox.find(".yes").click(myYes);
	confirmBox.show();
}

/* @NK Included on 26062019 */

/* @NK Included on 18122019 */

function GetPayDate(val) {
	var pmtDateSplit = new Array();
	var pmtDateFormat;
	var pmtpaidDate;

	var paycurrentSplit = new Array();
	var pmtcurrentFormat;
	var paycurrentDate;

	var result;
	var doc = window.opener.document; // get the parent form
	var dateval = doc.frmBankPay_PendingBill_create.txtCrea_date.value; // used
																		// to
																		// get
																		// cash
																		// book
																		// month
																		// and
																		// year
	var tableRow = document.getElementById("grid_body").children[val - 1];
	var pmtDate = tableRow.children[3].firstChild.value;
	var chkstuss = tableRow.children[0].firstChild.checked;
	var paycurrent = dateval;
	// var paycurrent= dateval+" 00:00";
	// pmtDate=pmtDate+" 00:00";
	if (pmtDate != "Under 1st.pmt.") {
		pmtDateSplit = pmtDate.split("/");
	}

	paycurrentSplit = paycurrent.split("/");

	if (pmtDateSplit[1] == 01) {
		pmtDateFormat = pmtDateSplit[0] + "/January/" + pmtDateSplit[2]

	}
	if (paycurrentSplit[1] == 01) {
		paycurrentFormat = paycurrentSplit[0] + "/January/"
				+ paycurrentSplit[2]
	}
	if (pmtDateSplit[1] == 02) {
		pmtDateFormat = pmtDateSplit[0] + "/February/" + pmtDateSplit[2]

	}
	if (paycurrentSplit[1] == 02) {
		paycurrentFormat = paycurrentSplit[0] + "/February/"+ paycurrentSplit[2]
	}

	if (pmtDateSplit[1] == 03) {
		pmtDateFormat = pmtDateSplit[0] + "/March/" + pmtDateSplit[2]
		
	}
	if (paycurrentSplit[1] == 03) {
		paycurrentFormat = paycurrentSplit[0] + "/March/" + paycurrentSplit[2]
	}

	if (pmtDateSplit[1] == 04) {
		pmtDateFormat = pmtDateSplit[0] + "/April/" + pmtDateSplit[2]

	}
	if (paycurrentSplit[1] == 04) {

		paycurrentFormat = paycurrentSplit[0] + "/April/" + paycurrentSplit[2]
	}

	if (pmtDateSplit[1] == 05  ) {
		pmtDateFormat = pmtDateSplit[0] + "/May/" + pmtDateSplit[2]
		
	}
	if (paycurrentSplit[1] == 05) {
		paycurrentFormat = paycurrentSplit[0] + "/May/" + paycurrentSplit[2]
	}
	
	if (pmtDateSplit[1] == 06) {
		pmtDateFormat = pmtDateSplit[0] + "/June/" + pmtDateSplit[2]
		
	}
	
	if  (paycurrentSplit[1] == 06) {
		
		paycurrentFormat = paycurrentSplit[0] + "/June/" + paycurrentSplit[2]
	}
	if (pmtDateSplit[1] == 07) {
		pmtDateFormat = pmtDateSplit[0] + "/July/" + pmtDateSplit[2]
		
	}
	
	if  (paycurrentSplit[1] == 07) {
		
		paycurrentFormat = paycurrentSplit[0] + "/July/" + paycurrentSplit[2]
	}
	if (pmtDateSplit[1] == 08 ) {
		pmtDateFormat = pmtDateSplit[0] + "/August/" + pmtDateSplit[2]
		
	}
	if (paycurrentSplit[1] == 08) {
		
		paycurrentFormat = paycurrentSplit[0] + "/August/" + paycurrentSplit[2]
	}
	if (pmtDateSplit[1] == 09) {
		pmtDateFormat = pmtDateSplit[0] + "/September/" + pmtDateSplit[2]
		
	}
	if (paycurrentSplit[1] == 09) {
		
		paycurrentFormat = paycurrentSplit[0] + "/September/"+ paycurrentSplit[2]
	}
	if (pmtDateSplit[1] == 10) {
		pmtDateFormat = pmtDateSplit[0] + "/October/" + pmtDateSplit[2]
		
	}
	if  (paycurrentSplit[1] == 10) {
		
		paycurrentFormat = paycurrentSplit[0] + "/October/"
				+ paycurrentSplit[2]
	}
	if (pmtDateSplit[1] == 11 ) {
		pmtDateFormat = pmtDateSplit[0] + "/November/" + pmtDateSplit[2]
		
	}
	if (paycurrentSplit[1] == 11) {
		
		paycurrentFormat = paycurrentSplit[0] + "/November/"
				+ paycurrentSplit[2]
	}
	if (pmtDateSplit[1] == 12) {
		pmtDateFormat = pmtDateSplit[0] + "/December/" + pmtDateSplit[2]
		
	}
	if  (paycurrentSplit[1] == 12) {
		
		paycurrentFormat = paycurrentSplit[0] + "/December/"
				+ paycurrentSplit[2]
	}

	var paycurrentDate;
	pmtpaidDate = Date.parse(pmtDateFormat);
	paycurrentDate = Date.parse(paycurrentFormat);

	// if (paycurrent < pmtDate && pmtDate!="Under 1st.pmt. 00:00") {
	if (paycurrentDate < pmtpaidDate && pmtDate != "Under 1st.pmt.") {
		tableRow.children[0].firstChild.checked = false;
		alert("please select suitable date against payment");
		result = false;
	} else {
		result = true;
	}

	return result;
}

/* @NK Included on 18122019 */