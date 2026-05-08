/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////

/**
 * Browser Type Detection
 */

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

function SupVoucherNo() {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbSubSystemType = document.getElementById("cmbSubSystemType").value;
	var txtCrea_date = document.getElementById("txtCrea_date").value;
	var ss = document.getElementById("supNo").value;
	var url = "../../../../../Authorization_for_Journal.kv?Command=voucherLoad&txtCrea_date="
			+ txtCrea_date
			+ "&cmbSubSystemType="
			+ cmbSubSystemType
			+ "&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode
			+ "&cmbOffice_code="
			+ cmbOffice_code + "&ss=" + ss;
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		handleResponse(req);
	}
	req.send(null);
}

function doFunction(Command, param) {

	if (Command == "load_Voucher_No") {
//		cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
//		cmbOffice_code = document.getElementById("cmbOffice_code").value;
//		cmbSubSystemType = document.getElementById("cmbSubSystemType").value;
//		//alert("cmbSubSystemType====>"+cmbSubSystemType);
//		txtCrea_date = document.getElementById("txtCrea_date").value;
//		IMIS_fund_option = document.getElementById("remarksauthID");
//		supNo=document.getElementById("supNo").value;
//		if (cmbSubSystemType == "") {
//			alert("Select Sub-system type ");
//			return false;
//		}
//		if (txtCrea_date.length == 0) {
//			alert("Enter the date");
//			return false;
//		}
//		if (cmbSubSystemType == "SJV") {
//			if(supNo.length==0)
//			{
//			alert("choose Supplement Number");
//			return false;
//			}
//		}
//		else if (cmbSubSystemType != "LJV") {
//			IMIS_fund_option.style.display = "none";
//			//return false;
//		}
//
//		/** Hide modify option when selecting Remittance Cancel */
//		HIDE_modify();
//
//		if (cmbSubSystemType != "" && txtCrea_date.length != 0) {
//			
//			var url = "../../../../../Authorization_for_Journal.kv?Command=load_Voucher_No&txtCrea_date="
//					+ txtCrea_date
//					+ "&cmbSubSystemType="
//					+ cmbSubSystemType
//					+ "&cmbAcc_UnitCode="
//					+ cmbAcc_UnitCode
//					+ "&cmbOffice_code=" + cmbOffice_code;
////alert(url);
//			var req = getTransport();
//			req.open("GET", url, true);
//			req.onreadystatechange = function() {
//				handleResponse(req);
//			};
//			req.send(null);
//		}
	
		cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		cmbOffice_code = document.getElementById("cmbOffice_code").value;
		cmbSubSystemType = document.getElementById("cmbSubSystemType").value;
		txtCrea_date = document.getElementById("txtCrea_date").value;
		IMIS_fund_option = document.getElementById("remarksauthID");
		supNo=document.getElementById("supNo").value;
		if (cmbSubSystemType == "") {
			alert("Select Sub-system type ");
			return false;
		}
		if (txtCrea_date.length == 0) {
			alert("Enter the date");
			return false;
		}
		if (cmbSubSystemType == "SJV") {
			
			if(supNo==""){
			alert("choose Supplement Number");
			return false;
			}
		}
		else if (cmbSubSystemType != "LJV") {
			IMIS_fund_option.style.display = "none";
			//return false;
		}

		/** Hide modify option when selecting Remittance Cancel */
		HIDE_modify();

		if ((cmbSubSystemType != "") && (txtCrea_date.length != 0)) {
			
			var url = "../../../../../Authorization_for_Journal.kv?Command=load_Voucher_No&txtCrea_date="
					+ txtCrea_date
					+ "&cmbSubSystemType="
					+ cmbSubSystemType
					+ "&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode
					+ "&cmbOffice_code=" + cmbOffice_code;
			
//alert(url);
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				handleResponse(req);
			};
			req.send(null);
		
	}
		
		
	} else if (Command == "load_Voucher_details") {
		cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		cmbOffice_code = document.getElementById("cmbOffice_code").value;
		cmbSubSystemType = document.getElementById("cmbSubSystemType").value;
		txtCrea_date = document.getElementById("txtCrea_date").value;
		txtVoucher_No = document.getElementById("txtVoucher_No").value;
		if (cmbSubSystemType == "") {
			alert("Select Sub-system type ");
			return false;
		}
		if (txtCrea_date.length == 0) {
			alert("Enter the date");
			return false;
		}

		if (cmbSubSystemType != "" && txtCrea_date.length != 0
				&& txtVoucher_No != "") {
			var url = "../../../../../Authorization_for_Journal.kv?Command=load_Voucher_details&txtCrea_date="
					+ txtCrea_date
					+ "&cmbSubSystemType="
					+ cmbSubSystemType
					+ "&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode
					+ "&cmbOffice_code="
					+ cmbOffice_code
					+ "&txtVoucher_No="
					+ txtVoucher_No;
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				handleResponse(req);
			}
			req.send(null);
		}
	}

}
function handleResponse(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;

			if (Command == "load_Voucher_No") {
				load_Voucher_No(baseResponse);
			} else if (Command == "load_Voucher_details") {
				load_Voucher_details(baseResponse);
			} else if (Command == "loadsupplement") {
				loadingSupNo(baseResponse);
			} else if (Command == "voucherLoad") {
				voucherLoading(baseResponse);
			} else if (Command == "loadsupplement_sjv") {
				loadingSupNo_sjv(baseResponse);
			}
		}
	}
}

function voucherLoading(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var txtVoucher_No = document.forms[0].txtVoucher_No;
		var voucherNo = baseResponse.getElementsByTagName("voucherNo");
		for (var i = 0; i < voucherNo.length; i++) {
			var opt = document.createElement('option');
			opt.value = voucherNo[i].firstChild.nodeValue;
			opt.innerHTML = voucherNo[i].firstChild.nodeValue; // instead of
																// using
																// textnode ,we
																// use innerhtml
			txtVoucher_No.appendChild(opt);
		}

	} else {
		alert("No Voucher Found");
	}
}

function load_Voucher_details(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	// alert(flag);
	if (flag == "success") {
		var cmbSubSystemType = baseResponse
				.getElementsByTagName("cmbSubSystemType")[0].firstChild.nodeValue;

		if (cmbSubSystemType == "GJV") {
			// alert("cmbSubSystemType"+cmbSubSystemType);
			var flagName = baseResponse.getElementsByTagName("flagName")[0].firstChild.nodeValue;
			// alert(flagName);
			if (flagName == "notAuthorize") {
				document.getElementById("com_value").value = baseResponse
						.getElementsByTagName("com_value")[0].firstChild.nodeValue;
				document.getElementById("amt").value = baseResponse
						.getElementsByTagName("amt")[0].firstChild.nodeValue;

				document.getElementById("modifyauthID").style.display = 'none';
				document.getElementById("cancelauthID").style.display = 'block';
			} else {
				document.getElementById("modifyauthID").style.display = 'block';
				document.getElementById("cancelauthID").style.display = 'block';
				document.getElementById("com_value").value = baseResponse
						.getElementsByTagName("com_value")[0].firstChild.nodeValue;
				document.getElementById("amt").value = baseResponse
						.getElementsByTagName("amt")[0].firstChild.nodeValue;
				
				
				var edit=baseResponse. getElementsByTagName("Edit")[0].firstChild.nodeValue;
				//alert(edit);
				
				if (edit=='true') {
					//alert("True..........");
					document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
					document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
					document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
					
				} else {
					//alert("false");
					document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
			        document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
			        document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
					
				}
			}
		} 
		if (cmbSubSystemType == "SJV") {
			// alert("cmbSubSystemType"+cmbSubSystemType);
			var flagName = baseResponse.getElementsByTagName("flagName")[0].firstChild.nodeValue;
			// alert(flagName);
			if (flagName == "notAuthorize") {
				document.getElementById("com_value").value = baseResponse
						.getElementsByTagName("com_value")[0].firstChild.nodeValue;
				document.getElementById("amt").value = baseResponse
						.getElementsByTagName("amt")[0].firstChild.nodeValue;

				document.getElementById("modifyauthID").style.display = 'none';
				document.getElementById("cancelauthID").style.display = 'block';
			} else {
				document.getElementById("modifyauthID").style.display = 'block';
				document.getElementById("cancelauthID").style.display = 'block';
				document.getElementById("com_value").value = baseResponse
						.getElementsByTagName("com_value")[0].firstChild.nodeValue;
				document.getElementById("amt").value = baseResponse
						.getElementsByTagName("amt")[0].firstChild.nodeValue;
				
				
				var edit=baseResponse. getElementsByTagName("Edit")[0].firstChild.nodeValue;
				//alert(edit);
				
				if (edit=='true') {
					//alert("True..........");
					document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
					document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
					document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
					
				} else {
					//alert("false");
					document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
			        document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
			        document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
					
				}
			}
		}
		
		else if (cmbSubSystemType == "RJV") {
			var typeCode = baseResponse.getElementsByTagName("typeCode")[0].firstChild.nodeValue;
			if (typeCode == 82) {
				document.getElementById("modifyauthID").style.display = 'none';
				document.getElementById("cancelauthID").style.display = 'block';
			} else {
				document.getElementById("modifyauthID").style.display = 'block';
				document.getElementById("cancelauthID").style.display = 'block';
			}
			document.getElementById("com_value").value = baseResponse
					.getElementsByTagName("com_value")[0].firstChild.nodeValue;
			document.getElementById("amt").value = baseResponse
					.getElementsByTagName("amt")[0].firstChild.nodeValue;
			/*var edit=baseResponse. getElementsByTagName("Edit")[0].firstChild.nodeValue;
			alert(edit);
			
			if (edit=='true') {
				//alert("True..........");
				document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
				document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
				document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
				
			} else {
				//alert("false");
				document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
		        document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
		        document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
				
			}
*/
		}else if (cmbSubSystemType == "LJV")
		{
			//alert(cmbSubSystemType+">>>>");
			document.getElementById("modifyauthID").style.display = 'block';
			document.getElementById("cancelauthID").style.display = 'block';
			document.getElementById("com_value").value = baseResponse.getElementsByTagName("com_value")[0].firstChild.nodeValue;
	        document.getElementById("amt").value = baseResponse.getElementsByTagName("amt")[0].firstChild.nodeValue;
	
	var edit=baseResponse. getElementsByTagName("Edit")[0].firstChild.nodeValue;
	//alert(edit);
	
	if (edit=='true') {
		//alert("True..........");
		document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
		document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
		document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
		
	} else {
		//alert("false");
		document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
        document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
        document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
		
	}
			
		} else {
			document.getElementById("modifyauthID").style.display = 'block';
			document.getElementById("cancelauthID").style.display = 'block';
			document.getElementById("com_value").value = baseResponse
					.getElementsByTagName("com_value")[0].firstChild.nodeValue;
			document.getElementById("amt").value = baseResponse
					.getElementsByTagName("amt")[0].firstChild.nodeValue;
			
			/*edit[0]=baseResponse. getElementsByTagName("Edit")[0].firstChild.nodeValue;
			alert(edit[0]);
			
			if (edit=='true') {
				alert("True..........");
				document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
				document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
				document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
				
			} else {
				alert("false");
				document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
	            document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
	            document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
				
			}*/
		}
		
	} else if (flag == "noData") {
		document.getElementById("com_value").value = "";
		document.getElementById("amt").value = "";
		alert("Details not found");
	} else {
		document.getElementById("com_value").value = "";
		document.getElementById("amt").value = "";
		alert("Failed To Display Details");
	}

}

function loadingSupNo(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "supsuccess") {
		// clear voucher field
		var txtVoucher_No = document.forms[0].txtVoucher_No;
		txtVoucher_No.length = 0;

		var supNo1 = document.forms[0].supNo;
		supNo1.length = 0;
		var supno = baseResponse.getElementsByTagName("supnumber");
		for (var i = 0; i < supno.length; i++) {
			if (supno.length == 1) {
				var opt = document.createElement('option');
				opt.value = "";
				opt.innerHTML = "Select Supplement No";
				supNo1.appendChild(opt);
			}
			var opt = document.createElement('option');
			opt.value = supno[i].firstChild.nodeValue;
			opt.innerHTML = supno[i].firstChild.nodeValue;
			supNo1.appendChild(opt);
		}

	}
}
function loadingSupNo_sjv(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var cb_dte = baseResponse.getElementsByTagName("cb_dte")[0].firstChild.nodeValue;
	//alert("cb_dte >> " + cb_dte);
	document.getElementById("txtCrea_date").value = cb_dte;
	if (flag == "supsuccess") {

		// clear voucher field
		var txtVoucher_No = document.forms[0].txtVoucher_No;
		txtVoucher_No.length = 0;

		var supNo1 = document.forms[0].supNo;
		supNo1.length = 0;
		var supno = baseResponse.getElementsByTagName("supnumber");
		for (var i = 0; i < supno.length; i++) {
			if (supno.length == 1) {
				var opt = document.createElement('option');
				opt.value = "";
				opt.innerHTML = "Select Supplement No";
				supNo1.appendChild(opt);
			}
			var opt = document.createElement('option');
			opt.value = supno[i].firstChild.nodeValue;
			opt.innerHTML = supno[i].firstChild.nodeValue;
			supNo1.appendChild(opt);
		}

	}else{
		alert('supplement No not assigned .. ');
	}

}
// //////////////////////////////////////////// load Voucher Number
// /////////////////////

function load_Voucher_No(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	var txtVoucher_No = document.getElementById("txtVoucher_No");
	if (flag == "success") {
		var items_id = new Array();
		var edit = new Array();
		var Rec_No = baseResponse.getElementsByTagName("Rec_No");
		

		for (var k = 0; k < Rec_No.length; k++) {
			items_id[k] = baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
			/* edit[k]=baseResponse.getElementsByTagName("Edit")[k].firstChild.nodeValue;
			//alert(edit[k]);
			
			if (edit=='true') {
				//alert("True..........");
				document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
				document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
				document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
				
			} else {
				//alert("false");
				document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
	            document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
	            document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
				
			}*/
			
		}
		
		
		//1 Siva newly Added on 26-02-2016 payment_for a pending bills
		/*var edit1 = baseResponse.getElementsByTagName("Edit")[0].firstChild.nodeValue;
		alert(edit1);
		if (edit1=="true") {
			alert("True");
			document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
            document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=false;
            document.frmAuthorization_JAO_Create.radAuth_MC[0].checked=true;
		} else {
			alert("false");
			document.frmAuthorization_JAO_Create.radAuth_MC[0].disabled=false;
            document.frmAuthorization_JAO_Create.radAuth_MC[1].disabled=true;
            document.frmAuthorization_JAO_Create.radAuth_MC[0]checked=true;
		}
		*/
       //1 siva end
		txtVoucher_No.innerHTML = "";
		var option = document.createElement("OPTION");
		option.text = "--Select Voucher Number--";
		option.value = "";
		try {
			txtVoucher_No.add(option);
		} catch (errorObject) {
			txtVoucher_No.add(option, null);
		}

		for (var k = 0; k < items_id.length; k++) {
			var option = document.createElement("OPTION");
			option.text = items_id[k];
			option.value = items_id[k];
			try {
				txtVoucher_No.add(option);
			} catch (errorObject) {
				txtVoucher_No.add(option, null);
			}
		}
	} else if (flag == "failure") {
		txtVoucher_No.innerHTML = "";
		var option = document.createElement("OPTION");
		option.text = "--Select Voucher Number--";
		option.value = "";
		try {
			txtVoucher_No.add(option);
		} catch (errorObject) {
			txtVoucher_No.add(option, null);
		}
		alert("No Voucher Found");
	}
}

function checkNull() {

	if (document.getElementById("cmbAcc_UnitCode").value == "") {
		alert("Select the Account Unit code");
		document.getElementById("txtAcc_HeadDesc").focus();
		return false;
	}
	if (document.getElementById("cmbOffice_code").value == "") {
		alert("Select the Office Code");
		document.getElementById("cmbOffice_code").focus();
		return false;
	}
	if (document.getElementById("txtCrea_date").value.length == 0) {
		alert("Enter the  Date");
		document.getElementById("txtCrea_date").focus();
		return false;
	}

	if (document.getElementById("cmbSubSystemType").value == "") {
		alert("Select the Sub-System type");
		document.getElementById("cmbSubSystemType").focus();
		return false;
	}
	if (document.getElementById("txtVoucher_No").value == "") {
		alert("Select the Voucher Number");
		document.getElementById("txtVoucher_No").focus();
		return false;
	}
	if (document.getElementById("txtRemak_edit").value.length == 0) {
		alert("Enter the Remarks ");
		document.getElementById("txtRemak_edit").focus();
		return false;
	}

	return true;
}

// ///////////////////////////////////////// TB_checking and Calender control
// return value handling

function call_mainJSP_script(fromcal_dateCtrl, blr_flag) // ///////Calender
															// control return
															// value handling
{
	// alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
	if (blr_flag == 1) // which is used to find the receipt or voucher or
						// payment (creation) date calling field,if so check
						// trial balance
	{
		call_clr();
		cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var TB_date = fromcal_dateCtrl.value;
		// alert(fromcal_dateCtrl.value+"b4url")
		if (fromcal_dateCtrl.value.length != 0) {
			var url = "../../../../../TB_Checking_for_Journal.kv?Command=check_TB&TB_date="
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

function call_date(dateCtrl) // TB_checking
{
	call_clr();
	if (checkdt(dateCtrl)) {
		// doFunction('check_TB',dateCtrl.value);
		cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		cmbOffice_code = document.getElementById("cmbOffice_code").value;
		// document.getElementById("cmbSubSystemType").disabled="true";
		// document.getElementById("txtVoucher_No").disabled="true";
		var TB_date = dateCtrl.value;

		if (dateCtrl.value.length != 0) {
			var url = "../../../../../TB_Checking_for_Journal.kv?Command=check_TB&TB_date="
					+ TB_date
					+ "&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode
					+ "&cmbOffice_code=" + cmbOffice_code;
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				check_TB(req, dateCtrl);
			}
			req.send(null);
		}
		// doFunction('load_Voucher_No','null');
	} else {
		var cmbSL_Code = document.getElementById("txtVoucher_No");
		cmbSL_Code.innerHTML = "";
		var option = document.createElement("OPTION");
		option.text = "-- Select Voucher Number --";
		option.value = "";
		try {
			cmbSL_Code.add(option);
		} catch (errorObject) {
			cmbSL_Code.add(option, null);
		}
	}
}

function call_clr() {
	// document.getElementById("txtVoucher_No").value="";
	var cmbSL_Code = document.getElementById("txtVoucher_No");
	cmbSL_Code.innerHTML = "";
	var option = document.createElement("OPTION");
	option.text = "-- Select Voucher Number --";
	option.value = "";
	try {
		cmbSL_Code.add(option);
	} catch (errorObject) {
		cmbSL_Code.add(option, null);
	}
	document.getElementById("cmbSubSystemType").value = "";
	document.getElementById("com_value").value = "";
	document.getElementById("amt").value = "";
	document.getElementById("txtReferNO_edit").value = "";
	document.getElementById("txtReferDate_edit").value = "";
	document.getElementById("txtRemak_edit").value = "";

}

function check_TB(req, dateCtrl) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

			if (flag == "success") {
				// here they should select sub-system type , so don't load it
				// voucher number..
				// doFunction('load_Voucher_No','null'); //return true;
			} else if (flag == "failure") {
				dateCtrl.value = "";
				alert("Trial Balance Closed");// return false;//
				dateCtrl.focus();
				var cmbSL_Code = document.getElementById("txtVoucher_No");
				cmbSL_Code.innerHTML = "";
				var option = document.createElement("OPTION");
				option.text = "-- Select Voucher Number --";
				option.value = "";
				try {
					cmbSL_Code.add(option);
				} catch (errorObject) {
					cmbSL_Code.add(option, null);
				}
			}
		}
	}
}

function clrForm() {
	document.getElementById("cmbSubSystemType").value = "";
	var cmbSL_Code = document.getElementById("txtVoucher_No");
	cmbSL_Code.innerHTML = "";
	var option = document.createElement("OPTION");
	option.text = "-- Select Voucher Number --";
	option.value = "";
	try {
		cmbSL_Code.add(option);
	} catch (errorObject) {
		cmbSL_Code.add(option, null);
	}
	document.getElementById("com_value").value = "";
	document.getElementById("amt").value = "";
	document.getElementById("txtReferNO_edit").value = "";
	document.getElementById("txtReferDate_edit").value = "";
	document.getElementById("txtRemak_edit").value = "";
}

/**
 * Hide Modify option when selecting Remittance option
 */

function HIDE_modify() {
	var Sub_System = document.getElementById("cmbSubSystemType").value;
	//alert(Sub_System)
	if (Sub_System == "CRM" || Sub_System == "BRM" || Sub_System == "FRM"
			|| Sub_System == "TDCP") {
		document.getElementById("H_Modify").style.visibility = "hidden";

	} else {
		document.getElementById("H_Modify").style.visibility = "visible";
	}

}

/**
 * Display the date as '31/3/XXXX' when selecting Supplement Journal
 */

/*
 * function loadSupNo() {alert("loadSupNo"); var
 * ss=document.getElementById("cmbSubSystemType").value; var
 * cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 * if(ss=="SJV") { document.getElementById("supplement").style.display="block";
 * var txtCrea_date=document.getElementById("txtCrea_date").value;
 * 
 * var
 * url="../../../../../Authorization_for_Journal.kv?Command=loadsupplement&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
 * var req=getTransport(); req.open("GET",url,true);
 * req.onreadystatechange=function() { handleResponse(req); } req.send(null); }
 * else { document.getElementById("supplement").style.display="none";
 * doFunction('load_Voucher_No','null'); } }
 */

function Supplement_Date() {
	
	var journal = document.getElementById("cmbSubSystemType").value;
	//alert(" journal >> "+journal);
	if (journal == "TDAOS" || journal == "TCAOS" || journal == "TDAAS"
			|| journal == "TCAAS") {
		/** Assign voucher date to a hidden text box */
		document.getElementById("hidden_date").value = document
				.getElementById("txtCrea_date").value;
		document.getElementById("hidden_voctype").value = "SJV";

		var today = new Date();
		var year = today.getFullYear();
		document.frmAuthorization_JAO_Create.txtCrea_date.value = "31/03/"
				+ year;

		document.getElementById("txtCrea_date").readonly = true;
		var Suppl_Date_Hide = document.getElementById("Suppl_Date_Hide");
		Suppl_Date_Hide.style.display = "none";

		/** SJV Trail Balance status check */
		// SJV_TB_Check();
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var txtCrea_date = document.getElementById("txtCrea_date").value;
		document.getElementById("supplement").style.display = "block";

//		var url = "../../../../../Authorization_for_Journal.kv?Command=loadsupplement&txtCrea_date="
//				+ txtCrea_date + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode;
		
//		var url = "../../../../../Authorization_for_Journal.kv?Command=loadsupplement_sjv&txtCrea_date="
//			+ txtCrea_date + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode;    /// commanded on 7 May 2019
		
		var url = "../../../../../Authorization_for_Journal.kv?Command=loadsupplement_sjv&cmbSubSystemType="
			+ journal + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode+"&txtCrea_date="+ txtCrea_date;
		//alert("URL--->"+url);
		
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		}
		req.send(null);

	} else if (journal == "SJV") {
		document.getElementById("supplement").style.display = "block";
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var txtCrea_date = document.getElementById("txtCrea_date").value;
		cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var url = "../../../../../Authorization_for_Journal.kv?Command=loadsupplement_sjv&cmbSubSystemType="
				+ journal + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="
				+ txtCrea_date;
		 //alert(url);
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		};
		req.send(null);
	} else if (journal == "GJV") {
		/*document.getElementById("supplement").style.display = "block";
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var	txtCrea_date=document.getElementById("txtCrea_date").value;
		var url = "../../../../../Authorization_for_Journal.kv?Command=loadsupplement_GJV&cmbSubSystemType="
				+ journal + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
		 //alert(url);
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		};
		req.send(null);*/
		//load_Voucher_No();
	//	doFunction('load_Voucher_No','null');

		cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		cmbOffice_code = document.getElementById("cmbOffice_code").value;
		cmbSubSystemType = document.getElementById("cmbSubSystemType").value;
		txtCrea_date = document.getElementById("txtCrea_date").value;
		//IMIS_fund_option = document.getElementById("remarksauthID");
		if (cmbSubSystemType == "") {
			alert("Select Sub-system type ");
			return false;
		}
		if (txtCrea_date.length == 0) {
			alert("Enter the date");
			return false;
		}
	

		/** Hide modify option when selecting Remittance Cancel */
		//HIDE_modify();

		if (cmbSubSystemType != "" && txtCrea_date.length != 0) {
			var url = "../../../../../Authorization_for_Journal.kv?Command=load_Voucher_No&txtCrea_date="
					+ txtCrea_date
					+ "&cmbSubSystemType="
					+ cmbSubSystemType
					+ "&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode
					+ "&cmbOffice_code=" + cmbOffice_code;
//alert(url);
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				handleResponse(req);
			};
			req.send(null);
		}
	
		
		
		
	} 
	else if (journal == "TCAABS" || journal == "TDAABS" || journal == "TDARS"
			|| journal == "TCARS") {
		/** Assign voucher date to a hidden text box */
		document.getElementById("hidden_date").value = document
				.getElementById("txtCrea_date").value;
		document.getElementById("hidden_voctype").value = "SJV";

		var today = new Date();
		var year = today.getFullYear();
		document.frmAuthorization_JAO_Create.txtCrea_date.value = "31/03/"
				+ year;

		document.getElementById("txtCrea_date").readonly = true;
		var Suppl_Date_Hide = document.getElementById("Suppl_Date_Hide");
		Suppl_Date_Hide.style.display = "none";

		/** SJV Trail Balance status check */
		// SJV_TB_Check();
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var txtCrea_date = document.getElementById("txtCrea_date").value;
		document.getElementById("supplement").style.display = "block";

//		var url = "../../../../../Authorization_for_Journal.kv?Command=loadsupplement&txtCrea_date="
//				+ txtCrea_date + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode;  // commanded on 7 May 2019
		
		var url = "../../../../../Authorization_for_Journal.kv?Command=loadsupplement_sjv&cmbSubSystemType="
			+ journal + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode+"&txtCrea_date="+ txtCrea_date;
		
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		}
		req.send(null);

	} else {
		document.getElementById("supplement").style.display = "none";
		document.getElementById("txtCrea_date").readonly = false;
		var Suppl_Date_Hide = document.getElementById("Suppl_Date_Hide");
		Suppl_Date_Hide.style.display = "block";

		if (document.getElementById("hidden_voctype").value == "SJV") {
			document.getElementById("txtCrea_date").value = document
					.getElementById("hidden_date").value;
			document.getElementById("hidden_voctype").value = "";
			document.getElementById("hidden_date").value = "";

		} else {

		}
	}
}
//added sathya on 06/04/2015 .............
function call_remarks(t)
{
var remarks_div = document.getElementById("remarksauthID");
var remarks_txtarea = document.getElementById("txtRemak_edit"); 
if(t.value == "C")
	{
	//alert("cancel option selected");
	var sub = document.getElementById("cmbSubSystemType");
	var sub_selected = sub.options[sub.selectedIndex].value;
   
	
	if( (sub_selected =="LJV")) 
		{
		remarks_div.style.display= "block";
		}
	else
		{
		remarks_div.style.display= "none";
		//document.getElementByID("radAuth_YN").checked = true;
		}
	}
else
	{
	remarks_txtarea.value = "";
	remarks_txtarea.disabled  = false;
	//alert(document.getElementById("radAuth_Y").value);
	document.getElementById("radAuth_Y").checked = false;
	remarks_div.style.display= "none";
	
	}
}

function remarks_disp(t1)
{
	var remarks_txtarea1 = document.getElementById("txtRemak_edit"); 
	if(t1.value == "Y")
	{
	remarks_txtarea1.value = "Cancel due to Want of Fund in IMIS";
	remarks_txtarea1.disabled  = true;
	}
else if(t1.value == "N")
	{
	remarks_txtarea1.value = "";
	remarks_txtarea1.disabled  = false;
	}
}
function check_authorisedto()
{

}